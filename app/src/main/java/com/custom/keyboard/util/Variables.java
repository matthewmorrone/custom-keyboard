package com.custom.keyboard.util;

public class Variables {

    public static int cursorStart = -1;

    public static boolean IS_CTRL = false;
    public static boolean IS_ALT = false;
    public static boolean IS_SHIFT = false;
    public static boolean IS_SELECTING = false;

    public static boolean isAlt() { return IS_ALT; }
    public static void setAltOn() { IS_ALT = true; }
    public static void setAltOff() { IS_ALT = false; }

    public static boolean isCtrl() { return IS_CTRL; }
    public static void setCtrlOn() { IS_CTRL = true; }
    public static void setCtrlOff() { IS_CTRL = false; }

    public static boolean isAnyOn() { return IS_CTRL || IS_ALT; }

    public static boolean isShift() { return IS_SHIFT; }
    public static void setShiftOn() { IS_SHIFT = true; }
    public static void setShiftOff() { IS_SHIFT = false; }

    public static boolean isSelecting() { return IS_SELECTING; }
    public static void setSelectingOn(int selectionStart) {
        IS_SELECTING  = true;
        cursorStart = selectionStart;
    }
    public static void setSelectingOff() {
        IS_SELECTING = false;
        cursorStart = -1;
    }

    public static void toggleSelecting() {
        IS_SELECTING = !IS_SELECTING;
    }
    public static void toggleSelecting(int selectionStart) {
        IS_SELECTING = !IS_SELECTING;
        if (IS_SELECTING) { cursorStart = selectionStart; }
        else { cursorStart = -1; }
    }

    public static boolean temp;

    public static boolean IS_BOLD = false;
    public static boolean isBold() { return IS_BOLD; }
    public static void setBoldOn() { IS_BOLD = true;}
    public static void setBoldOff() { IS_BOLD = false;}
    public static void toggleBold() { temp = IS_BOLD; setAllOff(); IS_BOLD = !temp;}

    public static boolean IS_ITALIC = false;
    public static boolean isItalic() { return IS_ITALIC; }
    public static void setItalicOn() {IS_ITALIC = true;}
    public static void setItalicOff() {IS_ITALIC = false;}
    public static void toggleItalic() {temp = IS_ITALIC; setAllOff(); IS_ITALIC = !temp;}

    public static boolean IS_EMPHASIZED = false;
    public static boolean isEmphasized() { return IS_EMPHASIZED; }
    public static void setEmphasizedOn() {IS_EMPHASIZED = true;}
    public static void setEmphasizedOff() {IS_EMPHASIZED = false;}
    public static void toggleEmphasized() {temp = IS_EMPHASIZED; setAllOff(); IS_EMPHASIZED = !temp;}

    public static boolean IS_STRIKETHROUGH = false;
    public static boolean isStrikethrough() {return IS_STRIKETHROUGH;}
    public static void setStrikethroughOn()  {IS_STRIKETHROUGH = true;}
    public static void setStrikethroughOff() {IS_STRIKETHROUGH = false;}
    public static void toggleStrikethrough() { IS_STRIKETHROUGH = !IS_STRIKETHROUGH; }

    public static boolean IS_UNDERLINED = false;
    public static boolean isUnderlined() {return IS_UNDERLINED;}
    public static void setUnderlinedOn()  {IS_UNDERLINED = true;}
    public static void setUnderlinedOff() {IS_UNDERLINED = false;}
    public static void toggleUnderlined() { IS_UNDERLINED = !IS_UNDERLINED; }

    public static boolean IS_UNDERSCORED = false;
    public static boolean isUnderscored() {return IS_UNDERSCORED;}
    public static void setUnderscoredOn()  {IS_UNDERSCORED = true;}
    public static void setUnderscoredOff() {IS_UNDERSCORED = false;}
    public static void toggleUnderscored() { IS_UNDERSCORED = !IS_UNDERSCORED; }

    public static boolean IS_BOLD_SERIF = false;
    public static boolean IS_ITALIC_SERIF = false;
    public static boolean IS_BOLD_ITALIC_SERIF = false;
    public static boolean IS_SANS = false;
    public static boolean IS_BOLD_SANS = false;
    public static boolean IS_ITALIC_SANS = false;
    public static boolean IS_BOLD_ITALIC_SANS = false;
    public static boolean IS_SCRIPT = false;
    public static boolean IS_SCRIPT_BOLD = false;
    public static boolean IS_FRAKTUR = false;
    public static boolean IS_FRAKTUR_BOLD = false;
    public static boolean IS_MONOSPACE = false;
    public static boolean IS_DOUBLESTRUCK = false;
    public static boolean IS_CAPS = false;
    public static boolean IS_PARENTHESES = false;
    public static boolean IS_ENCIRCLED = false;
    public static boolean IS_SMALL_CAPS = false;
    public static boolean IS_ENSQUARED = false;
    public static boolean IS_CIRCULAR_STAMP_LETTERS = false;
    public static boolean IS_REFLECTED = false;
    public static boolean IS_RECTANGULAR_STAMP_LETTERS = false;

    public static boolean isBoldSerif() {return IS_BOLD_SERIF;}
    public static boolean isItalicSerif() {return IS_ITALIC_SERIF;}
    public static boolean isBoldItalicSerif() {return IS_BOLD_ITALIC_SERIF;}
    public static boolean isSans() {return IS_SANS;}
    public static boolean isBoldSans() {return IS_BOLD_SANS;}
    public static boolean isItalicSans() {return IS_ITALIC_SANS;}
    public static boolean isBoldItalicSans() {return IS_BOLD_ITALIC_SANS;}
    public static boolean isScript() {return IS_SCRIPT;}
    public static boolean isScriptBold() {return IS_SCRIPT_BOLD;}
    public static boolean isFraktur() {return IS_FRAKTUR;}
    public static boolean isFrakturBold() {return IS_FRAKTUR_BOLD;}
    public static boolean isMonospace() {return IS_MONOSPACE;}
    public static boolean isDoublestruck() {return IS_DOUBLESTRUCK;}
    public static boolean isCaps() {return IS_CAPS;}
    public static boolean isParentheses() {return IS_PARENTHESES;}
    public static boolean isEncircle() {return IS_ENCIRCLED;}
    public static boolean isSmallCaps() {return IS_SMALL_CAPS;}
    public static boolean isEnsquare() {return IS_ENSQUARED;}
    public static boolean isCircularStampLetters() {return IS_CIRCULAR_STAMP_LETTERS;}
    public static boolean isReflected() {return IS_REFLECTED;}
    public static boolean isRectangularStampLetters() {return IS_RECTANGULAR_STAMP_LETTERS;}



    public static void toggleBoldSerif() {temp = IS_BOLD_SERIF; setAllOff(); IS_BOLD_SERIF = !temp;}
    public static void toggleItalicSerif() {temp = IS_ITALIC_SERIF; setAllOff(); IS_ITALIC_SERIF = !temp;}
    public static void toggleBoldItalicSerif() {temp = IS_BOLD_ITALIC_SERIF; setAllOff(); IS_BOLD_ITALIC_SERIF = !temp;}
    public static void toggleSans() {temp = IS_SANS; setAllOff(); IS_SANS = !temp;}
    public static void toggleBoldSans() {temp = IS_BOLD_SANS; setAllOff(); IS_BOLD_SANS = !temp;}
    public static void toggleItalicSans() {temp = IS_ITALIC_SANS; setAllOff(); IS_ITALIC_SANS = !temp;}
    public static void toggleBoldItalicSans() {temp = IS_BOLD_ITALIC_SANS; setAllOff(); IS_BOLD_ITALIC_SANS = !temp;}
    public static void toggleScript() {temp = IS_SCRIPT; setAllOff(); IS_SCRIPT = !temp;}
    public static void toggleScriptBold() {temp = IS_SCRIPT_BOLD; setAllOff(); IS_SCRIPT_BOLD = !temp;}
    public static void toggleFraktur() {temp = IS_FRAKTUR; setAllOff(); IS_FRAKTUR = !temp;}
    public static void toggleFrakturBold() {temp = IS_FRAKTUR_BOLD; setAllOff(); IS_FRAKTUR_BOLD = !temp;}
    public static void toggleMonospace() {temp = IS_MONOSPACE; setAllOff(); IS_MONOSPACE = !temp;}
    public static void toggleDoublestruck() {temp = IS_DOUBLESTRUCK; setAllOff(); IS_DOUBLESTRUCK = !temp;}
    public static void toggleCaps() {temp = IS_CAPS; setAllOff(); IS_CAPS = !temp;}
    public static void toggleParentheses() {temp = IS_PARENTHESES; setAllOff(); IS_PARENTHESES = !temp;}
    public static void toggleEncircle() {temp = IS_ENCIRCLED; setAllOff(); IS_ENCIRCLED = !temp;}
    public static void toggleSmallCaps() {temp = IS_SMALL_CAPS; setAllOff(); IS_SMALL_CAPS = !temp;}
    public static void toggleEnsquare() {temp = IS_ENSQUARED; setAllOff(); IS_ENSQUARED = !temp;}
    public static void toggleCircularStampLetters() {temp = IS_CIRCULAR_STAMP_LETTERS; setAllOff(); IS_CIRCULAR_STAMP_LETTERS = !temp;}
    public static void toggleReflected() {temp = IS_REFLECTED; setAllOff(); IS_REFLECTED = !temp;}
    public static void toggleRectangularStampLetters() {temp = IS_RECTANGULAR_STAMP_LETTERS; setAllOff(); IS_RECTANGULAR_STAMP_LETTERS = !temp;}



    public static void setAllOff() {
        setBoldOff();
        setItalicOff();
        setEmphasizedOff();
        setStrikethroughOff();
        setUnderlinedOff();
        setUnderscoredOff();

        IS_BOLD_SERIF = false;
        IS_ITALIC_SERIF = false;
        IS_BOLD_ITALIC_SERIF = false;
        IS_SANS = false;
        IS_BOLD_SANS = false;
        IS_ITALIC_SANS = false;
        IS_BOLD_ITALIC_SANS = false;
        IS_SCRIPT = false;
        IS_SCRIPT_BOLD = false;
        IS_FRAKTUR = false;
        IS_FRAKTUR_BOLD = false;
        IS_MONOSPACE = false;
        IS_DOUBLESTRUCK = false;
        IS_CAPS = false;
        IS_PARENTHESES = false;
        IS_ENCIRCLED = false;
        IS_SMALL_CAPS = false;
        IS_ENSQUARED = false;
        IS_CIRCULAR_STAMP_LETTERS = false;
        IS_REFLECTED = false;
        IS_RECTANGULAR_STAMP_LETTERS = false;
    }
}



