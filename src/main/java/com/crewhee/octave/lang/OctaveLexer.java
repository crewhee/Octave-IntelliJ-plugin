package com.crewhee.octave.lang;


import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;

import com.crewhee.octave.lang._OctaveLexer;

public class OctaveLexer extends FlexAdapter {
    public OctaveLexer() {
        super(new _OctaveLexer());
    }
}
