package com.custom.keyboard;

class Variables {

    static int cursorStart = -1;

    private static boolean IS_SHIFT = false;
    private static boolean IS_SELECT = false;
    private static boolean IS_CTRL = false;
    private static boolean IS_ALT = false;
    private static boolean IS_META = false;
    private static boolean IS_BOLD = false;
    private static boolean IS_ITALIC = false;
    private static boolean IS_EMPHASIZED = false;
    private static boolean IS_STRIKETHROUGH = false;
    private static boolean IS_UNDERLINED = false;
    private static boolean IS_UNDERSCORED = false;
    private static boolean IS_CAPS = false;
    private static boolean IS_REFLECTED = false;
    private static boolean IS_119808 = false;
    private static boolean IS_119860 = false;
    private static boolean IS_119912 = false;
    private static boolean IS_119964 = false;
    private static boolean IS_120016 = false;
    private static boolean IS_120068 = false;
    private static boolean IS_120120 = false;
    private static boolean IS_120172 = false;
    private static boolean IS_120224 = false;
    private static boolean IS_120276 = false;
    private static boolean IS_120328 = false;
    private static boolean IS_120380 = false;
    private static boolean IS_120432 = false;
    private static boolean IS_127280 = false;
    private static boolean IS_127312 = false;
    private static boolean IS_127344 = false;
    private static boolean IS_127462 = false;
    private static boolean IS_009372 = false;
    private static boolean IS_009398 = false;

    static boolean isBold() { return IS_BOLD; }
    static boolean isItalic() { return IS_ITALIC; }
    static boolean isEmphasized() { return IS_EMPHASIZED; }
    static boolean isStrikethrough() {return IS_STRIKETHROUGH;}
    static boolean isUnderlined() {return IS_UNDERLINED;}
    static boolean isUnderscored() {return IS_UNDERSCORED;}
    static boolean isCaps() { return IS_CAPS; }
    static boolean isReflected() { return IS_REFLECTED; }

    static boolean is119808() { return IS_119808; }
    static boolean is119860() { return IS_119860; }
    static boolean is119912() { return IS_119912; }
    static boolean is119964() { return IS_119964; }
    static boolean is120016() { return IS_120016; }
    static boolean is120068() { return IS_120068; }
    static boolean is120120() { return IS_120120; }
    static boolean is120172() { return IS_120172; }
    static boolean is120224() { return IS_120224; }
    static boolean is120276() { return IS_120276; }
    static boolean is120328() { return IS_120328; }
    static boolean is120380() { return IS_120380; }
    static boolean is120432() { return IS_120432; }
    static boolean is127280() { return IS_127280; }
    static boolean is127312() { return IS_127312; }
    static boolean is127344() { return IS_127344; }
    static boolean is127462() { return IS_127462; }
    static boolean is009372() { return IS_009372; }
    static boolean is009398() { return IS_009398; }

    static boolean isShift()  { return IS_SHIFT; }
    static boolean isSelect() { return IS_SELECT; }
    static boolean isCtrl()   { return IS_CTRL; }
    static boolean isAlt()    { return IS_ALT; }
    static boolean isMeta()   { return IS_META; }

    static void setMetaOn()   { IS_META = true; }
    static void setMetaOff()  { IS_META = false; }

    static void setCtrlOn()   { IS_CTRL = true; }
    static void setCtrlOff()  { IS_CTRL = false; }

    static void setAltOn()    { IS_ALT = true; }
    static void setAltOff()   { IS_ALT = false; }

    static void setShiftOn()  { IS_SHIFT = true; }
    static void setShiftOff() { IS_SHIFT = false; }

    static void setSelectOn() { IS_SELECT = true; }
    static void setSelectOn(int selectionStart) { 
        IS_SELECT  = true; 
        cursorStart = selectionStart;
    }
    static void setSelectOff() { 
        IS_SELECT = false;
        cursorStart = -1;
    }

    static void setAllEmOff() {
        IS_BOLD = false;
        IS_ITALIC = false;
        IS_EMPHASIZED = false;
        IS_UNDERLINED = false;
        IS_UNDERSCORED = false;
        IS_STRIKETHROUGH = false;
        IS_CAPS = false;
        IS_REFLECTED = false;

        IS_119808 = false;
        IS_119860 = false;
        IS_119912 = false;
        IS_119964 = false;
        IS_120016 = false;
        IS_120068 = false;
        IS_120120 = false;
        IS_120172 = false;
        IS_120224 = false;
        IS_120276 = false;
        IS_120328 = false;
        IS_120380 = false;
        IS_120432 = false;
        IS_127280 = false;
        IS_127312 = false;
        IS_127344 = false;
        IS_127462 = false;
        IS_009372 = false;
        IS_009398 = false;
    }

    private static void setFontsOff() {
        IS_CAPS = false;
        IS_REFLECTED = false;
        IS_UNDERLINED = false;
        IS_UNDERSCORED = false;
        IS_STRIKETHROUGH = false;

        IS_119808 = false;
        IS_119860 = false;
        IS_119912 = false;
        IS_119964 = false;
        IS_120016 = false;
        IS_120068 = false;
        IS_120120 = false;
        IS_120172 = false;
        IS_120224 = false;
        IS_120276 = false;
        IS_120328 = false;
        IS_120380 = false;
        IS_120432 = false;
        IS_127280 = false;
        IS_127312 = false;
        IS_127344 = false;
        IS_127462 = false;
        IS_009372 = false;
        IS_009398 = false;
    }

    static void toggleSelect() {
        IS_SELECT = !IS_SELECT;
    }
    static void toggleSelect(int selectionStart) {
        IS_SELECT = !IS_SELECT;
        if (IS_SELECT) {
            cursorStart = selectionStart;
        }
        else {
            cursorStart = -1;
        }
    }

    static void setBoldOn() {setAllEmOff(); IS_BOLD = true;}
    static void setBoldOff() {setAllEmOff(); IS_BOLD = false;}
    static void setItalicOn() {setAllEmOff(); IS_ITALIC = true;}
    static void setItalicOff() {setAllEmOff(); IS_ITALIC = false;}
    static void setEmphasizedOn() {setAllEmOff(); IS_EMPHASIZED = true;}
    static void setEmphasizedOff() {setAllEmOff(); IS_EMPHASIZED = false;}

    static void setShift(boolean shift) { IS_SHIFT = shift; }
    static void toggleShift() { IS_SHIFT = !IS_SHIFT; }


    static void toggleBold() { setFontsOff(); IS_BOLD = !IS_BOLD; }
    static void toggleItalic() { setFontsOff(); IS_ITALIC = !IS_ITALIC; }
    static void toggleEmphasized() { setFontsOff(); IS_EMPHASIZED = !IS_EMPHASIZED; }
    static void toggleUnderlined() { setFontsOff(); IS_UNDERLINED = !IS_UNDERLINED; }
    static void toggleUnderscored() { setFontsOff(); IS_UNDERSCORED = !IS_UNDERSCORED; }
    static void toggleStrikethrough() { setFontsOff(); IS_STRIKETHROUGH = !IS_STRIKETHROUGH; }
    static void toggleCaps() { if (IS_CAPS) { setAllEmOff(); }  else { setAllEmOff(); IS_CAPS = true; } }
    static void toggleReflected() { if (IS_REFLECTED) { setAllEmOff(); }  else { setAllEmOff(); IS_REFLECTED = true; } }

    static void toggle119808() { if (IS_119808) { setAllEmOff(); }  else { setAllEmOff(); IS_119808 = true; } }
    static void toggle119860() { if (IS_119860) { setAllEmOff(); }  else { setAllEmOff(); IS_119860 = true; } }
    static void toggle119912() { if (IS_119912) { setAllEmOff(); }  else { setAllEmOff(); IS_119912 = true; } }
    static void toggle119964() { if (IS_119964) { setAllEmOff(); }  else { setAllEmOff(); IS_119964 = true; } }
    static void toggle120016() { if (IS_120016) { setAllEmOff(); }  else { setAllEmOff(); IS_120016 = true; } }
    static void toggle120068() { if (IS_120068) { setAllEmOff(); }  else { setAllEmOff(); IS_120068 = true; } }
    static void toggle120120() { if (IS_120120) { setAllEmOff(); }  else { setAllEmOff(); IS_120120 = true; } }
    static void toggle120172() { if (IS_120172) { setAllEmOff(); }  else { setAllEmOff(); IS_120172 = true; } }
    static void toggle120224() { if (IS_120224) { setAllEmOff(); }  else { setAllEmOff(); IS_120224 = true; } }
    static void toggle120276() { if (IS_120276) { setAllEmOff(); }  else { setAllEmOff(); IS_120276 = true; } }
    static void toggle120328() { if (IS_120328) { setAllEmOff(); }  else { setAllEmOff(); IS_120328 = true; } }
    static void toggle120380() { if (IS_120380) { setAllEmOff(); }  else { setAllEmOff(); IS_120380 = true; } }
    static void toggle120432() { if (IS_120432) { setAllEmOff(); }  else { setAllEmOff(); IS_120432 = true; } }
    static void toggle127280() { if (IS_127280) { setAllEmOff(); }  else { setAllEmOff(); IS_127280 = true; } }
    static void toggle127312() { if (IS_127312) { setAllEmOff(); }  else { setAllEmOff(); IS_127312 = true; } }
    static void toggle127344() { if (IS_127344) { setAllEmOff(); }  else { setAllEmOff(); IS_127344 = true; } }
    static void toggle127462() { if (IS_127462) { setAllEmOff(); }  else { setAllEmOff(); IS_127462 = true; } }
    static void toggle009372() { if (IS_009372) { setAllEmOff(); }  else { setAllEmOff(); IS_009372 = true; } }
    static void toggle009398() { if (IS_009398) { setAllEmOff(); }  else { setAllEmOff(); IS_009398 = true; } }
}
