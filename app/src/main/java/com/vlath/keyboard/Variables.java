package com.vlath.keyboard;

class Variables {
    
    static int cursorStart = -1;
    private static boolean IS_SHIFT = false;
    private static boolean IS_SELECTING = false;
    static boolean isSelecting() {return IS_SELECTING;}
    static void toggleSelecting() {IS_SELECTING = !IS_SELECTING;}
    static void setSelectingOff() {IS_SELECTING = false;}
    static void setSelectingOn() {IS_SELECTING = true;}
    static boolean isShift() {return IS_SHIFT;}

    private static boolean IS_CTRL = false;
    private static boolean IS_ALT = false;

    static boolean isAnyOn() {return IS_CTRL || IS_ALT;}
    static boolean isCtrl()  {return IS_CTRL;}
    static boolean isAlt()   {return IS_ALT;}


    private static boolean IS_BOLD = false;
    private static boolean IS_ITALIC = false;

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
    private static boolean IS_9372 = false;
    private static boolean IS_9398 = false;
    private static boolean IS_REFLECTED = false;
    private static boolean IS_SMALLCAPS = false;

    static boolean isBold()      {return IS_BOLD;}
    static boolean isItalic()    {return IS_ITALIC;}
    static boolean is119808()    {return IS_119808;}
    static boolean is119860()    {return IS_119860;}
    static boolean is119912()    {return IS_119912;}
    static boolean is119964()    {return IS_119964;}
    static boolean is120016()    {return IS_120016;}
    static boolean is120068()    {return IS_120068;}
    static boolean is120120()    {return IS_120120;}
    static boolean is120172()    {return IS_120172;}
    static boolean is120224()    {return IS_120224;}
    static boolean is120276()    {return IS_120276;}
    static boolean is120328()    {return IS_120328;}
    static boolean is120380()    {return IS_120380;}
    static boolean is120432()    {return IS_120432;}
    static boolean is127280()    {return IS_127280;}
    static boolean is127312()    {return IS_127312;}
    static boolean is127344()    {return IS_127344;}
    static boolean is127462()    {return IS_127462;}
    static boolean is9372()      {return IS_9372;}
    static boolean is9398()      {return IS_9398;}
    static boolean isReflected() {return IS_REFLECTED;}
    static boolean isSmallcaps() {return IS_SMALLCAPS;}

    static void setAllOff() {
        IS_BOLD = false;
        IS_ITALIC = false;
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
        IS_9372 = false;
        IS_9398 = false;
        IS_REFLECTED = false;
        IS_SMALLCAPS = false;
    }

    private static void setFontsOff() {
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
        IS_9372 = false;
        IS_9398 = false;
        IS_REFLECTED = false;
        IS_SMALLCAPS = false;
    }


    static void setCtrlOn()         {IS_CTRL   = true;}
    static void setCtrlOff()        {IS_CTRL   = false;}
    static void setAltOn()          {IS_ALT    = true;}
    static void setAltOff()         {IS_ALT    = false;}
    static void setShiftOn()        {IS_SHIFT  = true;}
    static void setShiftOff()       {IS_SHIFT  = false;}

    static void toggleIsBold()      { if (IS_BOLD)      { setFontsOff(); } else { setFontsOff(); IS_BOLD = true; } }
    static void toggleIsItalic()    { if (IS_ITALIC)    { setFontsOff(); } else { setFontsOff(); IS_ITALIC = true; } }

    static void toggle119808()      { if (IS_119860)    { setAllOff(); } else { setAllOff(); IS_119808 = true; } }
    static void toggle119860()      { if (IS_119860)    { setAllOff(); } else { setAllOff(); IS_119860 = true; } }
    static void toggle119912()      { if (IS_119912)    { setAllOff(); } else { setAllOff(); IS_119912 = true; } }
    static void toggle119964()      { if (IS_119964)    { setAllOff(); } else { setAllOff(); IS_119964 = true; } }
    static void toggle120016()      { if (IS_120016)    { setAllOff(); } else { setAllOff(); IS_120016 = true; } }
    static void toggle120068()      { if (IS_120068)    { setAllOff(); } else { setAllOff(); IS_120068 = true; } }
    static void toggle120120()      { if (IS_120120)    { setAllOff(); } else { setAllOff(); IS_120120 = true; } }
    static void toggle120172()      { if (IS_120172)    { setAllOff(); } else { setAllOff(); IS_120172 = true; } }
    static void toggle120224()      { if (IS_120224)    { setAllOff(); } else { setAllOff(); IS_120224 = true; } }
    static void toggle120276()      { if (IS_120276)    { setAllOff(); } else { setAllOff(); IS_120276 = true; } }
    static void toggle120328()      { if (IS_120328)    { setAllOff(); } else { setAllOff(); IS_120328 = true; } }
    static void toggle120380()      { if (IS_120380)    { setAllOff(); } else { setAllOff(); IS_120380 = true; } }
    static void toggle120432()      { if (IS_120432)    { setAllOff(); } else { setAllOff(); IS_120432 = true; } }
    static void toggle127280()      { if (IS_127280)    { setAllOff(); } else { setAllOff(); IS_127280 = true; } }
    static void toggle127312()      { if (IS_127312)    { setAllOff(); } else { setAllOff(); IS_127312 = true; } }
    static void toggle127344()      { if (IS_127344)    { setAllOff(); } else { setAllOff(); IS_127344 = true; } }
    static void toggle127462()      { if (IS_127462)    { setAllOff(); } else { setAllOff(); IS_127462 = true; } }
    static void toggle9372()        { if (IS_9372)      { setAllOff(); } else { setAllOff(); IS_9372 = true; } }
    static void toggle9398()        { if (IS_9398)      { setAllOff(); } else { setAllOff(); IS_9398 = true; } }
    static void toggleIsReflected() { if (IS_REFLECTED) { setAllOff(); } else { setAllOff(); IS_REFLECTED = true; } }
    static void toggleIsSmallcaps() { if (IS_SMALLCAPS) { setAllOff(); } else { setAllOff(); IS_SMALLCAPS = true; } }

}
