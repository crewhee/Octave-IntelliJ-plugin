package com.crewhee.octave.ide;

import com.crewhee.octave.lang.OctaveLanguage;
import com.intellij.codeInsight.highlighting.PairedBraceMatcherAdapter;
import com.intellij.lang.Language;
import com.intellij.lang.PairedBraceMatcher;
import org.jetbrains.annotations.NotNull;

public class OctavePairedBraceMatcher extends PairedBraceMatcherAdapter {
    public OctavePairedBraceMatcher() {
        super(new OctaveBraceMatcher(), OctaveLanguage.INSTANCE);
    }
}
