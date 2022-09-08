// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.crewhee.octave.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class OctaveFileType extends LanguageFileType {

    public static final OctaveFileType INSTANCE = new OctaveFileType();

    private OctaveFileType() {
        super(OctaveLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Octave File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Octave (or Matlab) language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "m";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return OctaveIcons.FILE;
    }

}