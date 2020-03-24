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

    static boolean isBold() { return IS_BOLD; }
    static boolean isItalic() { return IS_ITALIC; }

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
    static void toggleBolded() { IS_BOLD = !IS_BOLD; }
    static void toggleItalic() { IS_ITALIC = !IS_ITALIC; }
}
