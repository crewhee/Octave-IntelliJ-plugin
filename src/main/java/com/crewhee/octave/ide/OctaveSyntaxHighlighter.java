// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.crewhee.octave.ide;

import com.crewhee.octave.lang.OctaveLexer;
import com.crewhee.octave.lang.OctaveTokenType;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class OctaveSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey OPERATOR =
            createTextAttributesKey("OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey NUMBER =
            createTextAttributesKey("NUM", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BLOCK_COMMENT =
            createTextAttributesKey("BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey STRING =
            createTextAttributesKey("STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey SYSTEM_COMMAND =
            createTextAttributesKey("SYSTEM_COMMAND", DefaultLanguageHighlighterColors.FUNCTION_CALL);
    public static final TextAttributesKey BRACKETS =
            createTextAttributesKey("BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey BRACES =
            createTextAttributesKey("BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey PARENTHESES =
            createTextAttributesKey("PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey IDENTIFIER =
            createTextAttributesKey("IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("OCTAVE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);


    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[]{OPERATOR};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] BLOCK_COMMENT_KEYS = new TextAttributesKey[]{BLOCK_COMMENT};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};
    private static final TextAttributesKey[] BRACES_KEYS = new TextAttributesKey[]{BRACES};
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESES};
    private static final TextAttributesKey[] SYSTEM_COMMAND_KEYS = new TextAttributesKey[]{SYSTEM_COMMAND};
    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};

    private static final TextAttributesKey[] BAD_CHARACTER_KEYS = new TextAttributesKey[]{BAD_CHARACTER};

    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new OctaveLexer();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (OctaveTokenType.isKeyword(tokenType)) {
            return KEYWORD_KEYS;
        } else if (OctaveTokenType.isOperator(tokenType)) {
            return OPERATOR_KEYS;
        } else if (OctaveTokenType.isNumber(tokenType)) {
            return NUMBER_KEYS;
        } else if (tokenType.equals(OctaveTokenType.getCOMMENT())) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(OctaveTokenType.getBLOCK_COMMENT())) {
            return BLOCK_COMMENT_KEYS;
        }
        else if (OctaveTokenType.isString(tokenType)) {
            return STRING_KEYS;
        } else if (OctaveTokenType.isBracket(tokenType)) {
            return BRACKETS_KEYS;
        } else if (OctaveTokenType.isBrace(tokenType)) {
            return BRACES_KEYS;
        } else if (OctaveTokenType.isParenth(tokenType)) {
            return PARENTHESES_KEYS;
        } else if (OctaveTokenType.isSystemCommand(tokenType)) {
            return SYSTEM_COMMAND_KEYS;
        } else if (tokenType.equals(OctaveTokenType.getIDENTIFIER())) {
            return IDENTIFIER_KEYS;
        } else if (tokenType.equals(OctaveTokenType.getBAD_CHARACTER())) {
            return BAD_CHARACTER_KEYS;
        }
        return EMPTY_KEYS;
    }

}