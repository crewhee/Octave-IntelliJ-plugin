package com.crewhee.octave.lang;

import com.crewhee.octave.lang.OctaveElements.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class OctaveElementType extends IElementType {
    public final static IFileElementType OCTAVE_FILE = new IFileElementType(OctaveLanguage.INSTANCE);

    public static final OctaveElementType SIMPLE_LIST = new OctaveElementType("simple_list");
    public static final OctaveElementType GARBAGE = new OctaveElementType("garbage");
    public static final OctaveElementType STATEMENT = new OctaveElementType("statement");
    public final static OctaveElementType EXPRESSION = new OctaveElementType("expression");
    public static final OctaveElementType SIMPLE_EXPRESSION = new OctaveElementType("simple_expr");
    public final static OctaveElementType PRIMARY_EXPRESSION = new OctaveElementType("primary_expr");
    public final static OctaveElementType OPERAND_EXPRESSION = new OctaveElementType("oper_expr");
    public static final OctaveElementType CONSTANT = new OctaveElementType("constant");
    public final static OctaveElementType NAME = new OctaveElementType("name");


    public static final OctaveElementType FUNCTION_HANDLE = new OctaveElementType("function_handle");
    public static final OctaveElementType COMMAND = new OctaveElementType("command");
    public static final OctaveElementType DECLARATION = new OctaveElementType("declaration");
    public static final OctaveElementType SELECT_COMMAND = new OctaveElementType("select_command");
    public static final OctaveElementType IF_COMMAND = new OctaveElementType("if_command");
    public static final OctaveElementType ELSE_COMMAND = new OctaveElementType("else_command");
    public static final OctaveElementType ELSE_IF_COMMAND = new OctaveElementType("else_if_command");
    public static final OctaveElementType FUNCTION = new OctaveElementType("function");
    public static final OctaveElementType PARAM_LIST = new OctaveElementType("param_list");
    public static final OctaveElementType FUNCTION_NAME = new OctaveElementType("function_name");
    public static final OctaveElementType FUNCTION_RETURN_LIST = new OctaveElementType("function_return_list");
    public static final OctaveElementType FUNCTION_BODY = new OctaveElementType("function_body");

    public OctaveElementType(@NonNls @NotNull String debugName) {
        super(debugName, OctaveLanguage.INSTANCE);
    }

    @Contract(pure = true)
    public static PsiElement createElement(ASTNode node) {
        IElementType type = node.getElementType();
        if (type == SIMPLE_LIST) {
            return new OctavePsiElementStatementList(node);
        } else if (type == GARBAGE) {
            return new OctavePsiElementToken(node);
        } else if (type == STATEMENT) {
            return new OctavePsiElementStatement(node);
        } else if (type == EXPRESSION) {
            return new OctavePsiElementExpression(node);
        } else if (type == SIMPLE_EXPRESSION) {
            return new OctavePsiElementExpression(node);
        } else if (type == OPERAND_EXPRESSION) {
            return new OctavePsiElementExpression(node);
        } else if (type == PRIMARY_EXPRESSION) {
            return new OctavePsiElementToken(node);
        } else if (type == CONSTANT) {
            return new OctavePsiElementConstant(node);
        } else if (type == NAME) {
            return new OctavePsiElementToken(node);
        } else if (type == FUNCTION_HANDLE) {
            return new OctavePsiElementToken(node);
        } else if (type == COMMAND) {
            return new OctavePsiElementToken(node);
        } else if (type == DECLARATION) {
            return new OctavePsiElementToken(node);
        } else if (type == SELECT_COMMAND) {
            return new OctavePsiElementToken(node);
        } else if (type == IF_COMMAND) {
            return new OctavePsiElementToken(node);
        } else if (type == ELSE_COMMAND) {
            return new OctavePsiElementToken(node);
        } else if (type == ELSE_IF_COMMAND) {
            return new OctavePsiElementToken(node);
        } else if (type == FUNCTION) {
            return new OctavePsiElementToken(node);
        } else if (type == PARAM_LIST) {
            return new OctavePsiElementToken(node);
        } else if (type == FUNCTION_NAME) {
            return new OctavePsiElementToken(node);
        } else if (type == FUNCTION_RETURN_LIST) {
            return new OctavePsiElementToken(node);
        } else if (type == FUNCTION_BODY) {
            return new OctavePsiElementToken(node);
        } else if (type == OctaveTokenType.getEQUAL()
                || type == OctaveTokenType.getSEMICOLON()
                || type == OctaveTokenType.getMINUS()
                || type == OctaveTokenType.getPLUS()
                || type == OctaveTokenType.getMUL()
                || type == OctaveTokenType.getLDIV()) {
            return new OctavePsiElementToken(node);
        } else if (type == OctaveTokenType.getLPARENTH()
                || type == OctaveTokenType.getRPARENTH()
                || type == OctaveTokenType.getLBRACKET()
                || type == OctaveTokenType.getRBRACKET()
                || type == OctaveTokenType.getLBRACE()
                || type == OctaveTokenType.getRBRACE()) {
            return new OctavePsiElementToken(node);
        } else if (type == OctaveTokenType.getDOT()
                || type == OctaveTokenType.getAT()) {
            return new OctavePsiElementToken(node);
        } else if (type == OctaveTokenType.getFLOAT()
                || type == OctaveTokenType.getFLOAT_EXPONENTIAL()
                || type == OctaveTokenType.getINTEGER()) {
            return new OctavePsiElementToken(node);
        }
        throw new RuntimeException("Unsupported token type " + type.getDebugName());
    }
}