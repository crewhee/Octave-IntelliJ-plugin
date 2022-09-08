// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.crewhee.octave.ide;

import com.crewhee.octave.lang.OctaveIcons;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class OctaveColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Keyword", OctaveSyntaxHighlighter.KEYWORD),
            new AttributesDescriptor("Operator", OctaveSyntaxHighlighter.OPERATOR),
            new AttributesDescriptor("Number", OctaveSyntaxHighlighter.NUMBER),
            new AttributesDescriptor("Comment", OctaveSyntaxHighlighter.COMMENT),
            new AttributesDescriptor("String", OctaveSyntaxHighlighter.STRING),
            new AttributesDescriptor("System command", OctaveSyntaxHighlighter.SYSTEM_COMMAND),
            new AttributesDescriptor("Identifier", OctaveSyntaxHighlighter.IDENTIFIER),
            new AttributesDescriptor("Bad value", OctaveSyntaxHighlighter.BAD_CHARACTER)
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return OctaveIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new OctaveSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "function retval = foo(arg)\n" +
                "\"some_string\"\n"+
               "    for\n" +
               "        retval = retval + arg;\n" +
               "    endfor\n" +
               "endfunction\n";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @Override
    public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @Override
    public ColorDescriptor @NotNull [] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Octave";
    }

}