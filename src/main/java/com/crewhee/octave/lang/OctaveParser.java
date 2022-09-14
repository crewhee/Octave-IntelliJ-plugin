package com.crewhee.octave.lang;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class OctaveParser implements PsiParser {

    @Override
    public @NotNull ASTNode parse(@NotNull IElementType iElementType, @NotNull PsiBuilder psiBuilder) {
        new InnerParser(psiBuilder).parseInput();
        return psiBuilder.getTreeBuilt();
    }

    static class InnerParser {
        private final PsiBuilder builder;
        private boolean readingScriptFile = true;
        private boolean readingFunctionFile = false;
        private boolean readingClassdefFile = false;


        public InnerParser(@NotNull PsiBuilder psiBuilder) {
            this.builder = psiBuilder;
            builder.setDebugMode(true);
            if (builder.getTokenType() == OctaveTokenType.getCLASSDEF()) {
                readingClassdefFile = true;
                readingScriptFile = false;
            } else if (builder.getTokenType() == OctaveTokenType.getFUNCTION()) {
                readingFunctionFile = true;
                readingScriptFile = false;
            }
        }


        /*
            input ::= simple_list '\n'
                    | simple_list END_OF_INPUT
                    | parse_error
         */
        public void parseInput() {
            var marker = builder.mark();
            parseSimpleList();
            while (!builder.eof()) {
                if (builder.getTokenType() == OctaveTokenType.getNEWLINE()) {
                    advance();
                    parseSimpleList();
                } else {
                    builder.error("Can't parse file");
//                    marker.drop();
                    parseGarbage();
                }
            }
            marker.done(OctaveElementType.OCTAVE_FILE);
        }

        /*
            simple_list ::= (statement sep_no_nl)* statement opt_sep_no_nl
         */
        private void parseSimpleList() {
            var marker = builder.mark();
            var sepFlag = false;
            do {
                if (sepFlag) {
                    advance();
                }
                boolean flag = tryParseStatement();
                if (!flag) {
                    marker.drop();
                    break;
                }
                sepFlag = builder.getTokenType() == OctaveTokenType.getSEMICOLON() || builder.getTokenType() == OctaveTokenType.getNEWLINE();
                if (!sepFlag) {
                    marker.done(OctaveElementType.SIMPLE_LIST);
                    parseGarbage();
                    return;
                }
            } while (true);
        }

        private boolean tryParseStatement() {
            var marker = builder.mark();
            if (tryParseExpression() || tryParseCommand() || tryParseWordListCommand()) {
                marker.done(OctaveElementType.STATEMENT);
                return true;
            }
            marker.rollbackTo();
            return false;
        }

        private boolean tryParseWordListCommand() {
            // TODO
            return false;
        }

        /*
        command ::= declaration
                | select_command
                | loop_command
                | jump_command
                | spmd_command
                | except_command
                | function
                | file
         */
        private boolean tryParseCommand() {
            var marker = builder.mark();
            if (tryParseDeclaration() || tryParseSelectCommand() || tryParseFunction()) {
                marker.done(OctaveElementType.COMMAND);
                return true;
            }
            marker.rollbackTo();
            return false;
        }

        /*
        function ::=
                    function_beg stash_comment fcn_name
                  opt_param_list opt_sep function_body function_end

                  | function_beg stash_comment return_list '=' fcn_name
                  opt_param_list opt_sep function_body function_end
         */
        private boolean tryParseFunction() {
            var marker = builder.mark();
            if (builder.getTokenType() == OctaveTokenType.getFUNCTION()) {
                advance();
                parseReturnAndFunctionName();
                parseParamList();
                parseFunctionBody();

                if (builder.getTokenType() == OctaveTokenType.getEND() || builder.getTokenType() == OctaveTokenType.getENDFUNCTION()) {
                    advance();
                    marker.done(OctaveElementType.FUNCTION);
                    return true;
                }
            }
            marker.rollbackTo();
            return false;
        }

        private void parseFunctionBody() {
            while (OctaveTokenType.isSeparator(builder.getTokenType())) {
                advance();
            }
            var marker = builder.mark();
            while (tryParseStatement()) {
                while (OctaveTokenType.isSeparator(builder.getTokenType())) {
                    advance();
                }
            }
            marker.done(OctaveElementType.FUNCTION_BODY);
        }

        private void parseReturnAndFunctionName() {
            var marker = builder.mark();
            boolean returnListFlag = false;
            if (builder.getTokenType() == OctaveTokenType.getLBRACKET()) {
                parseReturnList();
                expectAdvance(OctaveTokenType.getRBRACKET(), "rbracket ']'");
            } else if (builder.getTokenType() == OctaveTokenType.getIDENTIFIER())
                if (builder.getTokenType() != OctaveTokenType.getIDENTIFIER()) {
                    builder.error("Bad function name");
                    return;
                }
            advance();
            if (builder.getTokenType() == OctaveTokenType.getEQUAL()) {
                advance();
                expectAdvance(OctaveTokenType.getIDENTIFIER(), "function name");
            }
            marker.done(OctaveElementType.FUNCTION_NAME);
        }

        private void parseParamList() {
            var marker = builder.mark();
            if (builder.getTokenType() == OctaveTokenType.getLPARENTH()) {
                assertAdvance(OctaveTokenType.getLPARENTH());
                while (true) {
                    if (builder.getTokenType() == OctaveTokenType.getRPARENTH()) break;
                    if (builder.getTokenType() != OctaveTokenType.getIDENTIFIER()) {
                        builder.error("Can't parse identifier for parameter");
                    } else break;
                    advance();
                    if (builder.getTokenType() == OctaveTokenType.getEQUAL()) {
                        advance();
                        tryParseExpression();
                    }
                }
                marker.done(OctaveElementType.PARAM_LIST);
                return;
            }
            marker.rollbackTo();
        }

        private void parseReturnList() {
            var marker = builder.mark();
            boolean flag = false;
            while (builder.getTokenType() == OctaveTokenType.getIDENTIFIER()) {
                flag = true;
                advance();
                if (builder.getTokenType() == OctaveTokenType.getCOMMA()) {
                    advance();
                }
            }
            if (flag) marker.done(OctaveElementType.FUNCTION_RETURN_LIST);
            else marker.rollbackTo();
        }

        private boolean tryParseSelectCommand() {
            var marker = builder.mark();
            if (tryParseIfCommand()) {
                marker.done(OctaveElementType.SELECT_COMMAND);
                return true;
            }
            marker.rollbackTo();
            return false;
        }

        private boolean tryParseIfCommand() {
            var marker = builder.mark();
            if (builder.getTokenType() == OctaveTokenType.getIF()) {
                assertAdvance(OctaveTokenType.getIF());
                if (parseIfCommandList() &&
                        (builder.getTokenType() == OctaveTokenType.getEND() ||
                                builder.getTokenType() == OctaveTokenType.getENDIF())) {
                    advance();
                    marker.done(OctaveElementType.IF_COMMAND);
                    return true;
                }
            }
            marker.rollbackTo();
            return false;
        }

        private boolean parseIfCommandList() {
            boolean flag = false;
            while (builder.getTokenType() != OctaveTokenType.getEND() &&
                    builder.getTokenType() != OctaveTokenType.getENDIF() &&
                    builder.getTokenType() != OctaveTokenType.getELSE() &&
                    builder.getTokenType() != OctaveTokenType.getELSEIF()) {
                var marker = builder.mark();
                if (!tryParseExpression()) {
                    marker.rollbackTo();
                    break;
                }
                flag = true;
                marker.done(OctaveElementType.EXPRESSION);
                advance();
                while (builder.getTokenType() == OctaveTokenType.getNEWLINE()) {
                    advance();
                }
            }
            while (builder.getTokenType() == OctaveTokenType.getELSEIF()) {
                parseElseIfClause();
            }
            if (builder.getTokenType() == OctaveTokenType.getELSE()) {
                parseElseClause();
            }
            return flag;
        }

        private void parseElseIfClause() {
            var marker = builder.mark();
            assertAdvance(OctaveTokenType.getELSEIF());
            while (atSeparator()) advance();
            if (!tryParseExpression()) {
                builder.error("Can't get expression for elseif clause");
            }
            while (true) {
                tryParseStatement();
                if (!atSeparator()) {
                    break;
                }
                while (atSeparator()) advance();
            }
            marker.done(OctaveElementType.ELSE_IF_COMMAND);
        }

        private void parseElseClause() {
            var marker = builder.mark();
            assertAdvance(OctaveTokenType.getELSE());
            while (atSeparator()) advance();
            while (true) {
                tryParseStatement();
                if (!atSeparator()) break;
                while (atSeparator()) advance();
            }
//            if (builder.getTokenType() == OctaveTokenType.getEND() ||
//                builder.getTokenType() == OctaveTokenType.getENDIF()) {
//                advance();
//            } else {
//                builder.error("Bad end of else statement");
//            }
            marker.done(OctaveElementType.ELSE_COMMAND);
        }


        private boolean tryParseDeclaration() {
            var marker = builder.mark();
            if (builder.getTokenType() == OctaveTokenType.getGLOBAL() || builder.getTokenType() == OctaveTokenType.getPERSISTENT()) {
                advance();
                parseDeclInitList();
                marker.done(OctaveElementType.DECLARATION);
                return true;
            }
            marker.rollbackTo();
            return false;
        }

        private void parseDeclInitList() {
            while (builder.getTokenType() == OctaveTokenType.getIDENTIFIER()) {
                var marker = builder.mark();
                advance();
                if (builder.getTokenType() == OctaveTokenType.getEQUAL()) {
                    tryParseStatement();
                }
                marker.done(OctaveElementType.DECLARATION);
            }
        }

        private boolean tryParseExpression() {
            var marker = builder.mark();
            if (tryParseSimpleExpression() || tryParseAssignExpression() || tryParseAnonFuncHandle()) {
                marker.done(OctaveElementType.EXPRESSION);
                return true;
            }
            marker.rollbackTo();
//            marker.error("Failed to parse expression");
            return false;
        }

        private boolean tryParseAnonFuncHandle() {
            // TODO
            return false;
        }

        private boolean tryParseAssignExpression() {
            //begins with assign_lhs = identifier
            // TODO
            return false;
        }

        /*
            simple_expr ::= oper_expr
                | colon_expr
                | simple_expr < simple_expr
                | (other bool expresions)
         */
        private boolean tryParseSimpleExpression() {
            var marker = builder.mark();
            if (tryParseOperExpression() || tryParseBooleanExpr()) {
                marker.done(OctaveElementType.SIMPLE_EXPRESSION);
                return true;
            }
            marker.rollbackTo();
            return false;
        }


        private boolean tryParseBooleanExpr() {
            // TODO
            return false;
        }


        private boolean tryParseOperExpression() {
            IElementType tokenType = builder.getTokenType();
            var marker = builder.mark();
            PsiBuilder.Marker prevMarker = null;

            if (OctaveTokenType.isBinOp2(tokenType)) {
                prevMarker = marker.precede();
            } else if (tokenType == OctaveTokenType.getPLUSPLUS() || tokenType == OctaveTokenType.getMINUSMINUS() || tokenType == OctaveTokenType.getNOT()) {
                advance();
                parsePrimaryExpression();
                marker.done(OctaveElementType.OPERAND_EXPRESSION);
                return true;
            }

            parsePrimaryExpression();
            tokenType = builder.getTokenType();
            while (OctaveTokenType.isBinOp1(tokenType)) {
                advance();
                PsiBuilder.Marker operExpr = marker.precede();
                parsePrimaryExpression();
                operExpr.done(OctaveElementType.OPERAND_EXPRESSION);
                marker = operExpr;
                return true;
            }

            if (tokenType == OctaveTokenType.getPLUS()) {
                advance();
                parsePrimaryExpression();
                marker.done(OctaveElementType.OPERAND_EXPRESSION);
                return true;
            } else if (tokenType == OctaveTokenType.getMINUS()) {
                advance();
                parsePrimaryExpression();
                marker.done(OctaveElementType.OPERAND_EXPRESSION);
                return true;
            } else if (tokenType == OctaveTokenType.getRPARENTH() || OctaveTokenType.isSeparator(tokenType)
                    || tokenType == OctaveTokenType.getNEWLINE()) {
//                advance();    //TODO
                marker.done(OctaveElementType.OPERAND_EXPRESSION);
                return true;
            }
            marker.rollbackTo();
            return false;
        }


        private void parseClass() {
            // TODO
//            var marker = builder.mark();
//            if (!readingClassdefFile) {
//                builder.error("Classdef must appear inside a file containing only a class definition");
//            }
        }

        /*
            primary_expr    : identifier
                | constant
                | fcn_handle
                | matrix
                | cell
                | meta_identifier
                | superclass_identifier
                | '(' expression ')'
         */
        private void parsePrimaryExpression() {
            // identifier: NAME
            var marker = builder.mark();
            IElementType currentTokenType = builder.getTokenType();
            if (currentTokenType == OctaveTokenType.getIDENTIFIER()) {
                advance();
                marker.done(OctaveElementType.NAME);
                return;
            }
            // constant
            else if (currentTokenType == OctaveTokenType.getINTEGER() || currentTokenType == OctaveTokenType.getFLOAT() || currentTokenType == OctaveTokenType.getFLOAT_EXPONENTIAL()) {
                advance();
                marker.done(OctaveElementType.CONSTANT);
                return;
            } else if (currentTokenType == OctaveTokenType.getLPARENTH()) {
                advance();
                tryParseExpression();
                if (builder.getTokenType() != OctaveTokenType.getRPARENTH()) {
                    builder.error("Expected ) to close expression");
                } else {
                    advance();
                }
                marker.done(OctaveElementType.PRIMARY_EXPRESSION);
                return;
            }
            // function handle
            else if (currentTokenType == OctaveTokenType.getAT()) {
                advance();
                assertAdvance(OctaveTokenType.getIDENTIFIER());
                marker.done(OctaveElementType.FUNCTION_HANDLE);
                return;
            }
            builder.error("Bad data");
            marker.drop();
        }

        private boolean expectAdvance(OctaveTokenType expectedTt, String expectedName) {
            if (builder.getTokenType() == expectedTt) {
                advance();
                return true;
            } else {
                builder.error("Expected " + expectedName);
                return false;
            }
        }

        private void assertAdvance(OctaveTokenType token) {
            assert (token == builder.getTokenType());
            advance();
        }

        private boolean atSeparator() {
            var token = builder.getTokenType();
            return token == OctaveTokenType.getCOMMA() || token == OctaveTokenType.getSEMICOLON() || token == OctaveTokenType.getNEWLINE();
        }

        private IElementType advance() {
            IElementType result = builder.getTokenType();
            builder.advanceLexer();
            while (builder.getTokenType() == TokenType.BAD_CHARACTER) {
                var badMark = builder.mark();
                builder.advanceLexer();
                badMark.error("Unexpected character");
            }
            return result;
        }

        private void parseGarbage() {
            var marker = builder.mark();
            while (builder.getTokenType() != null) {
                advance();
            }
            marker.done(OctaveElementType.GARBAGE);
        }
    }
}
