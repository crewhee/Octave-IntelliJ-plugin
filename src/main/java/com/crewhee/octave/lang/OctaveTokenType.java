package com.crewhee.octave.lang;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import java.util.Set;

public class OctaveTokenType extends IElementType {
    // Keywords: 1 - 46
    private static final OctaveTokenType __FILE__ = new OctaveTokenType("__FILE__");
    private static final OctaveTokenType __LINE__ = new OctaveTokenType("__LINE__");

    private static final OctaveTokenType SWITCH = new OctaveTokenType("SWITCH");
    private static final OctaveTokenType UNTIL = new OctaveTokenType("UNTIL");

    private static final OctaveTokenType RETURN = new OctaveTokenType("RETURN");
    private static final OctaveTokenType CASE = new OctaveTokenType("CASE");
    private static final OctaveTokenType CATCH = new OctaveTokenType("CATCH");
    private static final OctaveTokenType DO = new OctaveTokenType("DO");
    private static final OctaveTokenType CONTINUE = new OctaveTokenType("CONTINUE");
    private static final OctaveTokenType BREAK = new OctaveTokenType("BREAK");

    private static final OctaveTokenType IF = new OctaveTokenType("IF");
    private static final OctaveTokenType ELSEIF = new OctaveTokenType("ELSEIF");
    private static final OctaveTokenType ELSE = new OctaveTokenType("ELSE");
    private static final OctaveTokenType ENDIF = new OctaveTokenType("ENDIF");

    private static final OctaveTokenType FOR = new OctaveTokenType("FOR");
    private static final OctaveTokenType ENDFOR = new OctaveTokenType("ENDFOR");

    private static final OctaveTokenType TRY = new OctaveTokenType("TRY");
    private static final OctaveTokenType END_TRY_CATCH = new OctaveTokenType("END_TRY_CATCH");

    private static final OctaveTokenType CLASSDEF = new OctaveTokenType("CLASSDEF");
    private static final OctaveTokenType ENDCLASSDEF = new OctaveTokenType("ENDCLASSDEF");

    private static final OctaveTokenType ENUMERATION = new OctaveTokenType("ENUMERATION");
    private static final OctaveTokenType ENDENUMERATION = new OctaveTokenType("ENDENUMERATION");

    private static final OctaveTokenType EVENTS = new OctaveTokenType("EVENTS");
    private static final OctaveTokenType ENDEVENTS = new OctaveTokenType("ENDEVENTS");
    private static final OctaveTokenType ENDFUNCTION = new OctaveTokenType("ENDFUNCTION");
    private static final OctaveTokenType ENDMETHODS = new OctaveTokenType("ENDMETHODS");
    private static final OctaveTokenType ENDPARFOR = new OctaveTokenType("ENDPARFOR");
    private static final OctaveTokenType ENDPROPERTIES = new OctaveTokenType("ENDPROPERTIES");
    private static final OctaveTokenType ENDSWITCH = new OctaveTokenType("ENDSWITCH");
    private static final OctaveTokenType END_UNWIND_PROTECT = new OctaveTokenType("END_UNWIND_PROTECT");
    private static final OctaveTokenType END = new OctaveTokenType("END");

    private static final OctaveTokenType FUNCTION = new OctaveTokenType("FUNCTION");
    private static final OctaveTokenType GLOBAL = new OctaveTokenType("GLOBAL");
    private static final OctaveTokenType METHODS = new OctaveTokenType("METHODS");
    private static final OctaveTokenType OTHERWISE = new OctaveTokenType("OTHERWISE");

    private static final OctaveTokenType WHILE = new OctaveTokenType("WHILE");
    private static final OctaveTokenType ENDWHILE = new OctaveTokenType("ENDWHILE");

    private static final OctaveTokenType PARFOR = new OctaveTokenType("PARFOR");
    private static final OctaveTokenType PERSISTENT = new OctaveTokenType("PERSISTENT");
    private static final OctaveTokenType PROPERTIES = new OctaveTokenType("PROPERTIES");
    private static final OctaveTokenType UNWIND_PROTECT = new OctaveTokenType("UNTIL");
    private static final OctaveTokenType UNWIND_PROTECT_CLEANUP = new OctaveTokenType("UNWIND_PROTECT_CLEANUP");

    // Comment
    private static final OctaveTokenType COMMENT = new OctaveTokenType("COMMENT");
    private static final OctaveTokenType BLOCK_COMMENT = new OctaveTokenType("BLOCK_COMMENT");

    // Other tokens
    private static final OctaveTokenType IDENTIFIER = new OctaveTokenType("IDENTIFIER");
    private static final OctaveTokenType OPERATOR = new OctaveTokenType("OPERATOR");

    private static final OctaveTokenType LOAD = new OctaveTokenType("LOAD");
    private static final OctaveTokenType DIR = new OctaveTokenType("DIR");
    private static final OctaveTokenType LS = new OctaveTokenType("LS");
    private static final OctaveTokenType CD = new OctaveTokenType("CD");

    private static final OctaveTokenType ELLIPSIS = new OctaveTokenType("ELLIPSIS");

    private static final OctaveTokenType SINGLE_QUOTE_STRING = new OctaveTokenType("SINGLE_QUOTE_STRING");
    private static final OctaveTokenType DOUBLE_QUOTE_STRING = new OctaveTokenType("DOUBLE_QUOTE_STRING");

    private static final OctaveTokenType FLOAT_EXPONENTIAL = new OctaveTokenType("FLOAT_EXPONENTIAL");
    private static final OctaveTokenType FLOAT = new OctaveTokenType("FLOAT");
    private static final OctaveTokenType INTEGER = new OctaveTokenType("INTEGER");

    private static final OctaveTokenType FILE_NAME = new OctaveTokenType("FILE_NAME");
    private static final OctaveTokenType NEWLINE = new OctaveTokenType("NEWLINE");
    private static final OctaveTokenType WHITE_SPACE = new OctaveTokenType("WHITE_SPACE");

    private static final OctaveTokenType FUNCTION_HANDLER = new OctaveTokenType("AT");

    private static final OctaveTokenType LESS = new OctaveTokenType("LESS");
    private static final OctaveTokenType LESS_OR_EQUAL = new OctaveTokenType("LESS_OR_EQUAL");
    private static final OctaveTokenType EQUAL = new OctaveTokenType("EQUAL");
    private static final OctaveTokenType MORE_OR_EQUAL = new OctaveTokenType("MORE_OR_EQUAL");
    private static final OctaveTokenType MORE = new OctaveTokenType("MORE");
    private static final OctaveTokenType NOT_EQUAL = new OctaveTokenType("NOT_EQUAL");

    private static final OctaveTokenType AND = new OctaveTokenType("AND");
    private static final OctaveTokenType OR = new OctaveTokenType("OR");
    private static final OctaveTokenType NOT = new OctaveTokenType("NOT");
    private static final OctaveTokenType MATRIX_OR = new OctaveTokenType("MATRIX_OR");
    private static final OctaveTokenType MATRIX_AND = new OctaveTokenType("MATRIX_AND");

    private static final OctaveTokenType DOT_LDIV = new OctaveTokenType("DOT_LDIV");
    private static final OctaveTokenType DOT_RDIV= new OctaveTokenType("DOT_RDIV");
    private static final OctaveTokenType DOT_MUL = new OctaveTokenType("DOT_MUL");
    private static final OctaveTokenType DOT_POW = new OctaveTokenType("DOT_POW");

    private static final OctaveTokenType PLUSPLUS = new OctaveTokenType("PLUSPLUS");
    private static final OctaveTokenType MINUSMINUS = new OctaveTokenType("MINUSMINUS");

    private static final OctaveTokenType TRANS = new OctaveTokenType("TRANS");
    private static final OctaveTokenType AT = new OctaveTokenType("AT");

    private static final OctaveTokenType LBRACKET = new OctaveTokenType("LBRACKET");
    private static final OctaveTokenType RBRACKET = new OctaveTokenType("RBRACKET");
    private static final OctaveTokenType LBRACE = new OctaveTokenType("LBRACE");
    private static final OctaveTokenType RBRACE = new OctaveTokenType("RBRACE");
    private static final OctaveTokenType LPARENTH = new OctaveTokenType("LPARENTH");
    private static final OctaveTokenType RPARENTH = new OctaveTokenType("RPARENTH");

    private static final OctaveTokenType SEMICOLON = new OctaveTokenType("SEMICOLON");
    private static final OctaveTokenType COLON = new OctaveTokenType("COLON");

    private static final OctaveTokenType COMMA = new OctaveTokenType("COMMA");

    private static final OctaveTokenType DOT = new OctaveTokenType("DOT");
    private static final OctaveTokenType POW = new OctaveTokenType("POW");
    private static final OctaveTokenType MUL = new OctaveTokenType("MUL");
    private static final OctaveTokenType RDIV = new OctaveTokenType("RDIV");
    private static final OctaveTokenType LDIV = new OctaveTokenType("LDIV");
    private static final OctaveTokenType MINUS = new OctaveTokenType("MINUS");
    private static final OctaveTokenType PLUS = new OctaveTokenType("PLUS");

    private static final OctaveTokenType ASSIGN = new OctaveTokenType("ASSIGN");

    private static final OctaveTokenType BAD_CHARACTER = new OctaveTokenType("BAD_CHARACTER");

    private static final Set<IElementType> keywords = Set.of(
        __FILE__, __LINE__, SWITCH, UNTIL, RETURN, CASE, CATCH, DO, CONTINUE, BREAK,
            IF, ELSEIF, ELSE, ENDIF, FOR, ENDFOR, TRY, END_TRY_CATCH, CLASSDEF, ENDCLASSDEF,
            ENUMERATION, ENDENUMERATION, EVENTS, ENDEVENTS, ENDFUNCTION, ENDMETHODS, ENDPARFOR,
            ENDPROPERTIES, ENDSWITCH, END_UNWIND_PROTECT, END, FUNCTION, GLOBAL, METHODS,
            OTHERWISE, WHILE, ENDWHILE, PARFOR, PERSISTENT, PROPERTIES, UNWIND_PROTECT, UNWIND_PROTECT_CLEANUP
    );

    private static final Set<IElementType> strings = Set.of(SINGLE_QUOTE_STRING, DOUBLE_QUOTE_STRING);

    private static final Set<IElementType> syscommands = Set.of(LOAD, DIR, LS, CD);

    private static final Set<IElementType> operators = Set.of(OPERATOR, ELLIPSIS, LESS, LESS_OR_EQUAL, EQUAL, MORE_OR_EQUAL, MORE, NOT_EQUAL,
            AND, OR, NOT, MATRIX_OR, MATRIX_AND, DOT_LDIV, DOT_RDIV, DOT_MUL, DOT_POW, PLUSPLUS, MINUSMINUS, TRANS, AT, DOT, POW, MUL,
            RDIV, LDIV, MINUS, PLUS, ASSIGN);

    private static final Set<IElementType> binops1 = Set.of(
            POW, DOT_POW, MUL, RDIV, DOT_MUL, DOT_RDIV, LDIV, DOT_LDIV
    );

    private static final Set<IElementType> binops2 = Set.of(
            PLUS, MINUS
    );

    private static final Set<IElementType> binops3 = Set.of(
            LESS, LESS_OR_EQUAL, EQUAL, MORE_OR_EQUAL, MORE, NOT_EQUAL,
            AND, OR, NOT, MATRIX_OR, MATRIX_AND
    );

    private static final Set<IElementType> numbers = Set.of(FLOAT_EXPONENTIAL, FLOAT, INTEGER);

    private static final Set<IElementType> brackets = Set.of(LBRACKET, RBRACKET);

    private static final Set<IElementType> parenthesis = Set.of(LPARENTH, RPARENTH);

    private static final Set<IElementType> braces = Set.of(LBRACE, RBRACE);
    private static final Set<IElementType> separators = Set.of(
            COMMA, SEMICOLON, NEWLINE
    );


    public OctaveTokenType(@NonNls @NotNull String debugName) {
        super(debugName, OctaveLanguage.INSTANCE, true);
    }

    public static OctaveTokenType get__FILE__() {
        return __FILE__;
    }

    public static OctaveTokenType get__LINE__() {
        return __LINE__;
    }

    public static OctaveTokenType getSWITCH() {
        return SWITCH;
    }

    public static OctaveTokenType getUNTIL() {
        return UNTIL;
    }

    public static OctaveTokenType getRETURN() {
        return RETURN;
    }

    public static OctaveTokenType getCASE() {
        return CASE;
    }

    public static OctaveTokenType getCATCH() {
        return CATCH;
    }

    public static OctaveTokenType getDO() {
        return DO;
    }

    public static OctaveTokenType getCONTINUE() {
        return CONTINUE;
    }

    public static OctaveTokenType getBREAK() {
        return BREAK;
    }

    public static OctaveTokenType getIF() {
        return IF;
    }

    public static OctaveTokenType getELSEIF() {
        return ELSEIF;
    }

    public static OctaveTokenType getELSE() {
        return ELSE;
    }

    public static OctaveTokenType getENDIF() {
        return ENDIF;
    }

    public static OctaveTokenType getFOR() {
        return FOR;
    }

    public static OctaveTokenType getENDFOR() {
        return ENDFOR;
    }

    public static OctaveTokenType getTRY() {
        return TRY;
    }

    public static OctaveTokenType getEND_TRY_CATCH() {
        return END_TRY_CATCH;
    }

    public static OctaveTokenType getCLASSDEF() {
        return CLASSDEF;
    }

    public static OctaveTokenType getENDCLASSDEF() {
        return ENDCLASSDEF;
    }

    public static OctaveTokenType getENUMERATION() {
        return ENUMERATION;
    }

    public static OctaveTokenType getENDENUMERATION() {
        return ENDENUMERATION;
    }

    public static OctaveTokenType getEVENTS() {
        return EVENTS;
    }

    public static OctaveTokenType getENDEVENTS() {
        return ENDEVENTS;
    }

    public static OctaveTokenType getENDFUNCTION() {
        return ENDFUNCTION;
    }

    public static OctaveTokenType getENDMETHODS() {
        return ENDMETHODS;
    }

    public static OctaveTokenType getENDPARFOR() {
        return ENDPARFOR;
    }

    public static OctaveTokenType getENDPROPERTIES() {
        return ENDPROPERTIES;
    }

    public static OctaveTokenType getENDSWITCH() {
        return ENDSWITCH;
    }

    public static OctaveTokenType getEND_UNWIND_PROTECT() {
        return END_UNWIND_PROTECT;
    }

    public static OctaveTokenType getFUNCTION() {
        return FUNCTION;
    }

    public static OctaveTokenType getGLOBAL() {
        return GLOBAL;
    }

    public static OctaveTokenType getMETHODS() {
        return METHODS;
    }

    public static OctaveTokenType getOTHERWISE() {
        return OTHERWISE;
    }

    public static OctaveTokenType getWHILE() {
        return WHILE;
    }

    public static OctaveTokenType getENDWHILE() {
        return ENDWHILE;
    }

    public static OctaveTokenType getPARFOR() {
        return PARFOR;
    }

    public static OctaveTokenType getPERSISTENT() {
        return PERSISTENT;
    }

    public static OctaveTokenType getPROPERTIES() {
        return PROPERTIES;
    }

    public static OctaveTokenType getUNWIND_PROTECT() {
        return UNWIND_PROTECT;
    }

    public static OctaveTokenType getUNWIND_PROTECT_CLEANUP() {
        return UNWIND_PROTECT_CLEANUP;
    }

    public static OctaveTokenType getFUNCTION_HANDLER() {
        return FUNCTION_HANDLER;
    }

    public static OctaveTokenType getCOMMENT() {
        return COMMENT;
    }

    public static OctaveTokenType getBLOCK_COMMENT() { return BLOCK_COMMENT; }

    public static OctaveTokenType getIDENTIFIER() {
        return IDENTIFIER;
    }

    public static OctaveTokenType getLOAD() {
        return LOAD;
    }

    public static OctaveTokenType getDIR() {
        return DIR;
    }

    public static OctaveTokenType getLS() {
        return LS;
    }

    public static OctaveTokenType getCD() {
        return CD;
    }

    public static OctaveTokenType getELLIPSIS() {
        return ELLIPSIS;
    }

    public static OctaveTokenType getSINGLE_QUOTE_STRING() {
        return SINGLE_QUOTE_STRING;
    }

    public static OctaveTokenType getDOUBLE_QUOTE_STRING() {
        return DOUBLE_QUOTE_STRING;
    }

    public static OctaveTokenType getFLOAT_EXPONENTIAL() {
        return FLOAT_EXPONENTIAL;
    }

    public static OctaveTokenType getEND() {
        return END;
    }

    public static OctaveTokenType getFLOAT() {
        return FLOAT;
    }

    public static OctaveTokenType getINTEGER() {
        return INTEGER;
    }

    public static OctaveTokenType getFILE_NAME() {
        return FILE_NAME;
    }

    public static OctaveTokenType getNEWLINE() {
        return NEWLINE;
    }

    public static OctaveTokenType getWHITE_SPACE() {
        return WHITE_SPACE;
    }

    public static OctaveTokenType getLESS() {
        return LESS;
    }

    public static OctaveTokenType getLESS_OR_EQUAL() {
        return LESS_OR_EQUAL;
    }

    public static OctaveTokenType getEQUAL() {
        return EQUAL;
    }

    public static OctaveTokenType getMORE_OR_EQUAL() {
        return MORE_OR_EQUAL;
    }

    public static OctaveTokenType getMORE() {
        return MORE;
    }

    public static OctaveTokenType getNOT_EQUAL() {
        return NOT_EQUAL;
    }

    public static OctaveTokenType getAND() {
        return AND;
    }

    public static OctaveTokenType getOR() {
        return OR;
    }

    public static OctaveTokenType getNOT() {
        return NOT;
    }

    public static OctaveTokenType getMATRIX_OR() {
        return MATRIX_OR;
    }

    public static OctaveTokenType getMATRIX_AND() {
        return MATRIX_AND;
    }

    public static OctaveTokenType getDOT_LDIV() {
        return DOT_LDIV;
    }

    public static OctaveTokenType getDOT_RDIV() {
        return DOT_RDIV;
    }

    public static OctaveTokenType getDOT_MUL() {
        return DOT_MUL;
    }

    public static OctaveTokenType getDOT_POW() {
        return DOT_POW;
    }

    public static OctaveTokenType getPLUSPLUS() {
        return PLUSPLUS;
    }

    public static OctaveTokenType getMINUSMINUS() {
        return MINUSMINUS;
    }

    public static OctaveTokenType getTRANS() {
        return TRANS;
    }

    public static OctaveTokenType getAT() {
        return AT;
    }

    public static OctaveTokenType getLBRACKET() {
        return LBRACKET;
    }

    public static OctaveTokenType getRBRACKET() {
        return RBRACKET;
    }

    public static OctaveTokenType getSEMICOLON() {
        return SEMICOLON;
    }

    public static OctaveTokenType getCOLON() {
        return COLON;
    }

    public static OctaveTokenType getCOMMA() {
        return COMMA;
    }

    public static OctaveTokenType getDOT() {
        return DOT;
    }

    public static OctaveTokenType getPOW() {
        return POW;
    }

    public static OctaveTokenType getMUL() {
        return MUL;
    }

    public static OctaveTokenType getRDIV() {
        return RDIV;
    }

    public static OctaveTokenType getLDIV() {
        return LDIV;
    }

    public static OctaveTokenType getMINUS() {
        return MINUS;
    }

    public static OctaveTokenType getPLUS() {
        return PLUS;
    }

    public static OctaveTokenType getBAD_CHARACTER() {
        return BAD_CHARACTER;
    }

    public static OctaveTokenType getLBRACE() {
        return LBRACE;
    }

    public static OctaveTokenType getRBRACE() {
        return RBRACE;
    }

    public static OctaveTokenType getLPARENTH() {
        return LPARENTH;
    }

    public static OctaveTokenType getRPARENTH() {
        return RPARENTH;
    }

    public static OctaveTokenType getASSIGN() {
        return ASSIGN;
    }

    public static boolean isKeyword(IElementType t) { return keywords.contains(t); }
    public static boolean isString(IElementType t) { return strings.contains(t); }
    public static boolean isSystemCommand(IElementType t) { return syscommands.contains(t); }
    public static boolean isOperator(IElementType t) { return operators.contains(t); }
    public static boolean isNumber(IElementType t) { return numbers.contains(t); }
    public static boolean isBracket(IElementType t) { return brackets.contains(t); }
    public static boolean isParenth(IElementType t) { return parenthesis.contains(t); }
    public static boolean isBrace(IElementType t) { return braces.contains(t); }

    public static boolean isBinOpBool (IElementType t) { return binops3.contains(t); }
    public static boolean isBinOp2(IElementType t) { return binops2.contains(t); }
    public static boolean isBinOp1(IElementType t) { return binops1.contains(t); }

    public static boolean isSeparator(IElementType t) {
        return separators.contains(t);
    }

    @Override
    public String toString() {
        return "OctaveTokenType." + super.toString();
    }
}