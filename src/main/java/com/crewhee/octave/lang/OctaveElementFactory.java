package com.crewhee.octave.lang;

import com.crewhee.octave.lang.OctaveElements.OctavePsiElementIdentifier;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.annotations.NotNull;

public class OctaveElementFactory {
    private final PsiFileFactory factory;

    private <T extends PsiElement> T create(String code) {
        return (T) factory
                .createFileFromText("tmp.octave", OctaveFileType.INSTANCE, code);
    }

    public OctaveElementFactory(Project project) {
        factory = PsiFileFactory.getInstance(project);
    }

    @NotNull
    public OctavePsiElementIdentifier createIdentifier(String name) {
        return this.create(name);
    }

}
