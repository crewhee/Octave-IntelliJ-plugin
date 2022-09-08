// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.crewhee.octave.lang;

import com.intellij.lang.Language;

public class OctaveLanguage extends Language {

    public static final OctaveLanguage INSTANCE = new OctaveLanguage();

    private OctaveLanguage() {
        super("Octave");
    }

}