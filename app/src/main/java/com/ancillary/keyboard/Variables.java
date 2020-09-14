package com.ancillary.keyboard;

public class Variables {

    static int cursorStart = -1;

    private static boolean IS_CTRL = false;
    private static boolean IS_ALT = false;
    private static boolean IS_SHIFT = false;
    private static boolean IS_SELECTING = false;

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
    static void setSelectingOn(int selectionStart) {
        IS_SELECTING  = true;
        cursorStart = selectionStart;
    }
    static void setSelectingOff() {
        IS_SELECTING = false;
        cursorStart = -1;
    }

    static void toggleSelecting() {
        IS_SELECTING = !IS_SELECTING;
    }
    static void toggleSelecting(int selectionStart) {
        IS_SELECTING = !IS_SELECTING;
        if (IS_SELECTING) { cursorStart = selectionStart; }
        else { cursorStart = -1; }
    }

    private static boolean IS_BOLD = false;
    static boolean isBold() { return IS_BOLD; }
    static void setBoldOn() { IS_BOLD = true;}
    static void setBoldOff() { IS_BOLD = false;}
    static void toggleBold() { IS_BOLD = !IS_BOLD; }

    private static boolean IS_ITALIC = false;
    static boolean isItalic() { return IS_ITALIC; }
    static void setItalicOn() {IS_ITALIC = true;}
    static void setItalicOff() {IS_ITALIC = false;}
    static void toggleItalic() { IS_ITALIC = !IS_ITALIC; }

    private static boolean IS_EMPHASIZED = false;
    static boolean isEmphasized() { return IS_EMPHASIZED; }
    static void setEmphasizedOn() {IS_EMPHASIZED = true;}
    static void setEmphasizedOff() {IS_EMPHASIZED = false;}
    static void toggleEmphasized() { IS_EMPHASIZED = !IS_EMPHASIZED; }

    private static boolean IS_STRIKETHROUGH = false;
    static boolean isStrikethrough() {return IS_STRIKETHROUGH;}
    static void setStrikethroughOn()  {IS_STRIKETHROUGH = true;}
    static void setStrikethroughOff() {IS_STRIKETHROUGH = false;}
    static void toggleStrikethrough() { IS_STRIKETHROUGH = !IS_STRIKETHROUGH; }

    private static boolean IS_UNDERLINED = false;
    static boolean isUnderlined() {return IS_UNDERLINED;}
    static void setUnderlinedOn()  {IS_UNDERLINED = true;}
    static void setUnderlinedOff() {IS_UNDERLINED = false;}
    static void toggleUnderlined() { IS_UNDERLINED = !IS_UNDERLINED; }

    private static boolean IS_UNDERSCORED = false;
    static boolean isUnderscored() {return IS_UNDERSCORED;}
    static void setUnderscoredOn()  {IS_UNDERSCORED = true;}
    static void setUnderscoredOff() {IS_UNDERSCORED = false;}
    static void toggleUnderscored() { IS_UNDERSCORED = !IS_UNDERSCORED; }

    static void setAllOff() {
        setBoldOff();
        setItalicOff();
        setEmphasizedOff();
        setStrikethroughOff();
        setUnderlinedOff();
        setUnderscoredOff();
    }




}



