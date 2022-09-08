package com.crewhee.octave.lang;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class OctaveFile extends PsiFileBase {

    public OctaveFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, OctaveLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return OctaveFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Octave File";
    }
}
