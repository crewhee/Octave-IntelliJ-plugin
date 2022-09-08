package com.crewhee.octave.lang;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import java.util.Stack;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;

%%

%{
    private Stack<Integer> stack = new Stack<>();
    private boolean hasUnmatchedText = false;

    public static FlexAdapter getAdapter() {
        return new FlexAdapter(new _OctaveLexer());
    }

    public _OctaveLexer() {
        this(null);
    }

    private void yypushState(int newState) {
      stack.push(yystate());
      yybegin(newState);
    }

    private void yypopState() {
        if (stack.isEmpty()) return;
        yybegin(stack.pop());
    }

    private void yyclearStack() {
        while (!stack.isEmpty()) stack.pop();
    }

    private void stopLookForCtrans() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
    }

    private void lookForCtrans() {
        if (yystate() != LOOK_FOR_CTRANS) yypushState(LOOK_FOR_CTRANS);
    }

    private void startWsDoesNotMatter() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
        yypushState(YYINITIAL);
    }

    private void stopWsDoesNotMatter() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
        if (yystate() == YYINITIAL) yypopState();
    }

    private void startWsMatters() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
        yypushState(WS_MATTERS);
    }

    private void stopWsMatters() {
        if (yystate() == LOOK_FOR_CTRANS) yypopState();
        if (yystate() == WS_MATTERS) yypopState();
    }
%}

%public
%class _OctaveLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

NEWLINE=(\R( \t)*)
WHITE_SPACE=[ \t\x0B\f]+ // do not match new line
SINGLE_QUOTE='
LINE_COMMENT=%.*
BLOCK_COMMENT_PREFIX={WHITE_SPACE}* "%{" {WHITE_SPACE}*
BLOCK_COMMENT_SUFFIX={WHITE_SPACE}* "%}" {WHITE_SPACE}*
LINE_HASH_COMMENT=#.*
BLOCK_HASH_COMMENT_PREFIX={WHITE_SPACE}* "#{" {WHITE_SPACE}*
BLOCK_HASH_COMMENT_SUFFIX={WHITE_SPACE}* "#}" {WHITE_SPACE}*
FLOAT=(([\d]*\.[\d]+)|([\d]+\.))i?
FLOAT_EXPONENTIAL=(([\d]*\.[\d]+)|([\d]+\.)|\d+)e[\+-]?[\d]+i?
IDENTIFIER = [:jletter:] [:jletterdigit:]*
INTEGER=[0-9]+i?

/* double quote literal does not allow single \ character. Sequence \" gives double quote */
ESCAPE_SEQUENCE = \\[^\r\n]
DOUBLE_QUOTE_STRING = \" ([^\\\"\r\n] | {ESCAPE_SEQUENCE})* \"?
/* single quote literal allows single \ character. Sequence '' gives single quote */
SINGLE_QUOTE_ESCAPE_SEQUENCE=\\[\\bfnrt]|''

%state WS_MATTERS
%state LOOK_FOR_CTRANS
%state SINGLE_QOUTE_STRING_STATE
%state BLOCKCOMMENT_STATE
%state BLOCK_HASH_COMMENT_STATE
%state LOOK_FOR_LINECOMMENT
%state LINECOMMENT_STATE
%state FILE_NAME_STATE

%%
<YYINITIAL,WS_MATTERS,LOOK_FOR_CTRANS> {
  {WHITE_SPACE}         { if (yystate() == LOOK_FOR_CTRANS &&
                                    !stack.isEmpty() &&
                                    stack.peek() == WS_MATTERS) {
                                yypopState();
                           }
                            return OctaveTokenType.getWHITE_SPACE();
                        }

  {SINGLE_QUOTE} / \n   { if (yystate() == LOOK_FOR_CTRANS) {
                              return OctaveTokenType.getTRANS();
                          } else {
                              return OctaveTokenType.getSINGLE_QUOTE_STRING();
                          }
                        }

  {SINGLE_QUOTE}        { if (yystate() == LOOK_FOR_CTRANS) {
                                return OctaveTokenType.getTRANS();
                            } else {
                                yypushState(SINGLE_QOUTE_STRING_STATE);
                            }
                        }

  __FILE__              { lookForCtrans(); return OctaveTokenType.get__FILE__(); }
  __LINE__              { lookForCtrans(); return OctaveTokenType.get__LINE__(); }

  function              { stopLookForCtrans(); return OctaveTokenType.getFUNCTION(); }
  elseif                { stopLookForCtrans(); return OctaveTokenType.getELSEIF(); }
  else                  { stopLookForCtrans(); return OctaveTokenType.getELSE(); }
  end                   { stopLookForCtrans(); return OctaveTokenType.getEND(); }
  if                    { stopLookForCtrans(); return OctaveTokenType.getIF(); }
  for                   { stopLookForCtrans(); return OctaveTokenType.getFOR(); }
  while                 { stopLookForCtrans(); return OctaveTokenType.getWHILE(); }
  parfor                { stopLookForCtrans(); return OctaveTokenType.getPARFOR(); }
  classdef              { stopLookForCtrans(); return OctaveTokenType.getCLASSDEF(); }
  switch                { stopLookForCtrans(); return OctaveTokenType.getSWITCH(); }
  case                  { stopLookForCtrans(); return OctaveTokenType.getCASE(); }
  otherwise             { stopLookForCtrans(); return OctaveTokenType.getOTHERWISE(); }
  try                   { stopLookForCtrans(); return OctaveTokenType.getTRY(); }
  catch                 { stopLookForCtrans(); return OctaveTokenType.getCATCH(); }
  global                { stopLookForCtrans(); return OctaveTokenType.getGLOBAL(); }
  return                { stopLookForCtrans(); return OctaveTokenType.getRETURN(); }
  continue              { stopLookForCtrans(); return OctaveTokenType.getCONTINUE(); }
  break                 { stopLookForCtrans(); return OctaveTokenType.getBREAK(); }
  load/" "+[^ (]        { stopLookForCtrans(); yypushState(FILE_NAME_STATE); return OctaveTokenType.getLOAD(); }
  dir/" "+[^ (]         { stopLookForCtrans(); yypushState(FILE_NAME_STATE); return OctaveTokenType.getDIR(); }
  ls/" "+[^ (]          { stopLookForCtrans(); yypushState(FILE_NAME_STATE); return OctaveTokenType.getLS(); }
  cd/" "+[^ (]          { stopLookForCtrans(); yypushState(FILE_NAME_STATE); return OctaveTokenType.getCD(); }

  endif                 { stopLookForCtrans(); return OctaveTokenType.getENDIF(); }
  endfor                { stopLookForCtrans(); return OctaveTokenType.getENDFOR(); }
  end_try_catch         { stopLookForCtrans(); return OctaveTokenType.getEND_TRY_CATCH(); }
  end_unwind_protect    { stopLookForCtrans(); return OctaveTokenType.getEND_UNWIND_PROTECT(); }
  endclassdef           { stopLookForCtrans(); return OctaveTokenType.getENDCLASSDEF(); }
  endenumeration        { stopLookForCtrans(); return OctaveTokenType.getENDENUMERATION(); }
  endevents             { stopLookForCtrans(); return OctaveTokenType.getENDEVENTS(); }
  endfunction           { stopLookForCtrans(); return OctaveTokenType.getENDFUNCTION(); }
  endmethods            { stopLookForCtrans(); return OctaveTokenType.getENDMETHODS(); }
  endparfor             { stopLookForCtrans(); return OctaveTokenType.getENDPARFOR(); }
  endswitch             { stopLookForCtrans(); return OctaveTokenType.getENDSWITCH(); }
  endwhile              { stopLookForCtrans(); return OctaveTokenType.getENDWHILE(); }

  do                    { stopLookForCtrans(); return OctaveTokenType.getDO(); }
  until                 { stopLookForCtrans(); return OctaveTokenType.getUNTIL(); }
  persistent            { stopLookForCtrans(); return OctaveTokenType.getPERSISTENT(); }

  // Not really keywords, but usually used as it
  enumaration           { stopLookForCtrans(); return OctaveTokenType.getENUMERATION(); }
  events                { stopLookForCtrans(); return OctaveTokenType.getEVENTS(); }
  methods               { stopLookForCtrans(); return OctaveTokenType.getMETHODS(); }
  properties            { stopLookForCtrans(); return OctaveTokenType.getPROPERTIES(); }
  endproperties         { stopLookForCtrans(); return OctaveTokenType.getENDPROPERTIES(); }

  unwind_protect        { stopLookForCtrans(); return OctaveTokenType.getUNWIND_PROTECT(); }
  unwind_protect_cleanup { stopLookForCtrans(); return OctaveTokenType.getUNWIND_PROTECT_CLEANUP(); }



  "("                   { startWsDoesNotMatter(); return OctaveTokenType.getLPARENTH(); }
  ")"                   { stopWsDoesNotMatter(); lookForCtrans(); return OctaveTokenType.getRPARENTH(); }
  "."                   { stopLookForCtrans(); return OctaveTokenType.getDOT(); }
  "<="                  { stopLookForCtrans(); return OctaveTokenType.getLESS_OR_EQUAL(); }
  "-"                   { stopLookForCtrans(); return OctaveTokenType.getMINUS(); }
  "--"                  { stopLookForCtrans(); return OctaveTokenType.getMINUSMINUS(); }
  "+"                   { stopLookForCtrans(); return OctaveTokenType.getPLUS(); }
  "++"                  { stopLookForCtrans(); return OctaveTokenType.getPLUSPLUS(); }
  "./"                  { stopLookForCtrans(); return OctaveTokenType.getDOT_RDIV(); }
  "/"                   { stopLookForCtrans(); return OctaveTokenType.getRDIV(); }
  "\\"                  { stopLookForCtrans(); return OctaveTokenType.getLDIV(); }
  ".\\"                 { stopLookForCtrans(); return OctaveTokenType.getDOT_LDIV(); }
  ".*"                  { stopLookForCtrans(); return OctaveTokenType.getDOT_MUL(); }
  "*"                   { stopLookForCtrans(); return OctaveTokenType.getMUL(); }
  ".^"                  { stopLookForCtrans(); return OctaveTokenType.getDOT_POW(); }
  "^"                   { stopLookForCtrans(); return OctaveTokenType.getPOW(); }
  "&&"                  { stopLookForCtrans(); return OctaveTokenType.getAND(); }
  "&"                   { stopLookForCtrans(); return OctaveTokenType.getMATRIX_AND(); }
  "||"                  { stopLookForCtrans(); return OctaveTokenType.getOR(); }
  "|"                   { stopLookForCtrans(); return OctaveTokenType.getMATRIX_OR(); }
  ".'"                  { stopLookForCtrans(); return OctaveTokenType.getTRANS(); }
  "~"                   { stopLookForCtrans(); return OctaveTokenType.getNOT(); }
  "!"                   { stopLookForCtrans(); return OctaveTokenType.getNOT(); }
  "="                   { stopLookForCtrans(); return OctaveTokenType.getASSIGN(); }
  ">="                  { stopLookForCtrans(); return OctaveTokenType.getMORE_OR_EQUAL(); }
  ">"                   { stopLookForCtrans(); return OctaveTokenType.getMORE(); }
  "<"                   { stopLookForCtrans(); return OctaveTokenType.getLESS(); }
  "=="                  { stopLookForCtrans(); return OctaveTokenType.getEQUAL(); }
  "~="                  { stopLookForCtrans(); return OctaveTokenType.getNOT_EQUAL(); }
  "!="                  { stopLookForCtrans(); return OctaveTokenType.getNOT_EQUAL(); }
  ","                   { stopLookForCtrans(); return OctaveTokenType.getCOMMA(); }
  ":"                   { stopLookForCtrans(); return OctaveTokenType.getCOLON(); }
  ";"                   { stopLookForCtrans(); return OctaveTokenType.getSEMICOLON(); }
  "["                   { startWsMatters(); return OctaveTokenType.getLBRACKET(); }
  "]"                   { stopWsMatters(); lookForCtrans(); return OctaveTokenType.getRBRACKET(); }
  "{"                   { startWsMatters(); return OctaveTokenType.getLBRACE(); }
  "}"                   { stopWsMatters(); lookForCtrans(); return OctaveTokenType.getRBRACE(); }
  "..." / {WHITE_SPACE}? \n   { stopLookForCtrans(); return OctaveTokenType.getELLIPSIS(); }
  "..."                 { stopLookForCtrans(); yypushState(LOOK_FOR_LINECOMMENT); return OctaveTokenType.getELLIPSIS(); }
  "@"                   { stopLookForCtrans(); return OctaveTokenType.getAT(); }

  {NEWLINE}             { stopLookForCtrans(); return OctaveTokenType.getNEWLINE(); }
  {LINE_COMMENT}        { stopLookForCtrans(); return OctaveTokenType.getCOMMENT(); }
  {LINE_HASH_COMMENT}   { stopLookForCtrans(); return OctaveTokenType.getCOMMENT(); }
  ^{BLOCK_COMMENT_PREFIX}$ { stopLookForCtrans(); yypushState(BLOCKCOMMENT_STATE); }
  ^{BLOCK_HASH_COMMENT_PREFIX}$ { stopLookForCtrans(); yypushState(BLOCK_HASH_COMMENT_STATE); }
  {FLOAT_EXPONENTIAL}   { lookForCtrans(); return OctaveTokenType.getFLOAT_EXPONENTIAL(); }
  {FLOAT}               { lookForCtrans(); return OctaveTokenType.getFLOAT(); }
  {INTEGER}             { lookForCtrans(); return OctaveTokenType.getINTEGER(); }
  {IDENTIFIER}          { lookForCtrans(); return OctaveTokenType.getIDENTIFIER(); }
  {DOUBLE_QUOTE_STRING} { lookForCtrans(); return OctaveTokenType.getDOUBLE_QUOTE_STRING(); }

  <<EOF>>               { return null; }
}

<SINGLE_QOUTE_STRING_STATE> {
    {SINGLE_QUOTE_ESCAPE_SEQUENCE} / \n  { yypopState(); return OctaveTokenType.getSINGLE_QUOTE_STRING(); }
    {SINGLE_QUOTE_ESCAPE_SEQUENCE}       {  }
    "'"                                  { yypopState(); lookForCtrans(); return OctaveTokenType.getSINGLE_QUOTE_STRING(); }
    <<EOF>>                              { yyclearStack(); yybegin(YYINITIAL); return OctaveTokenType.getSINGLE_QUOTE_STRING(); }

    /* line should not consume \n character */
    . / \n                               { yypopState(); return OctaveTokenType.getSINGLE_QUOTE_STRING(); }
    .                                    {  }
}

<LOOK_FOR_LINECOMMENT,LINECOMMENT_STATE> {
    <<EOF>>                              { yyclearStack(); yybegin(YYINITIAL); return OctaveTokenType.getCOMMENT(); }
}

<LOOK_FOR_LINECOMMENT> {
    {WHITE_SPACE}                        { return OctaveTokenType.getWHITE_SPACE(); }
    . / \n                               { yypopState(); return OctaveTokenType.getCOMMENT(); }
    .                                    { yypopState(); yypushState(LINECOMMENT_STATE); }
}

<LINECOMMENT_STATE> {
    . / \n                               { yypopState(); return OctaveTokenType.getCOMMENT(); }
    .                                    {  }
}

<BLOCKCOMMENT_STATE> {
    ^{BLOCK_COMMENT_PREFIX}$ { yypushState(BLOCKCOMMENT_STATE); }

    ^{BLOCK_COMMENT_SUFFIX}$ { yypopState();
                               if (yystate() != BLOCKCOMMENT_STATE) return OctaveTokenType.getBLOCK_COMMENT();
                             }
    <<EOF>>                  { yyclearStack(); yybegin(YYINITIAL); return OctaveTokenType.getBLOCK_COMMENT(); }

    [^]                      {  }
}

<BLOCK_HASH_COMMENT_STATE> {
    ^{BLOCK_HASH_COMMENT_PREFIX}$ { yypushState(BLOCK_HASH_COMMENT_STATE); }

    ^{BLOCK_HASH_COMMENT_SUFFIX}$ { yypopState();
                                   if (yystate() != BLOCK_HASH_COMMENT_STATE) return OctaveTokenType.getBLOCK_COMMENT();
                                 }
    <<EOF>>                  { yyclearStack(); yybegin(YYINITIAL); return OctaveTokenType.getBLOCK_COMMENT(); }

    [^]                      {  }
}

<FILE_NAME_STATE> {
    [^(]/[\n ]        { yypopState(); return OctaveTokenType.getFILE_NAME(); }
    "("               { yypopState(); }
    <<EOF>>           { yyclearStack(); yybegin(YYINITIAL); return OctaveTokenType.getFILE_NAME(); }
    .                 {  }
}

[^] { return OctaveTokenType.getBAD_CHARACTER(); }