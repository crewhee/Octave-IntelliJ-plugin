package com.crewhee.octave.ide;

import com.crewhee.octave.lang.OctaveTokenType;
import com.intellij.codeInsight.highlighting.PairedBraceMatcherAdapter;
import com.intellij.lang.BracePair;
import com.intellij.lang.Language;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class OctaveBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] pairs = {
            new BracePair(OctaveTokenType.getLBRACE(), OctaveTokenType.getRBRACE(), false),
            new BracePair(OctaveTokenType.getLBRACKET(), OctaveTokenType.getRBRACKET(), false),
            new BracePair(OctaveTokenType.getLPARENTH(), OctaveTokenType.getRPARENTH(), false)
    };

    @Override
    public BracePair @NotNull [] getPairs() {
        return pairs;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile psiFile, int i) {
        return 0;
    }

}
