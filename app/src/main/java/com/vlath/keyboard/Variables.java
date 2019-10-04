package com.vlath.keyboard;

class Variables {
    
    static int cursorStart = -1;

    private static boolean IS__SHIFT = false;
    private static boolean IS_SELECT = false;
    private static boolean IS___CTRL = false;
    private static boolean IS____ALT = false;
    private static boolean IS_BOLDED = false;
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
    private static boolean IS_009372 = false;
    private static boolean IS_009398 = false;
    private static boolean IS_RFLCTD = false;
    private static boolean IS_SMLCPS = false;

    static boolean isBolded() { return IS_BOLDED; }
    static boolean isItalic() { return IS_ITALIC; }
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
    static boolean isRflctd() { return IS_RFLCTD; }
    static boolean isSmlcap() { return IS_SMLCPS; }

    static void set__CtrlOnn() { IS___CTRL =  true; }
    static void set___AltOnn() { IS____ALT =  true; }
    static void set_ShiftOnn() { IS__SHIFT =  true; }
    // static void setSelectOnn() { IS_SELECT  = true; }
    static void set__CtrlOff() { IS___CTRL = false; }
    static void set___AltOff() { IS____ALT = false; }
    static void set_ShiftOff() { IS__SHIFT = false; }
    static void setSelectOff() { IS_SELECT = false; }

    static void setAllEmOff() {
        IS_BOLDED = false;
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
        IS_009372 = false;
        IS_009398 = false;
        IS_RFLCTD = false;
        IS_SMLCPS = false;
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
        IS_009372 = false;
        IS_009398 = false;
        IS_RFLCTD = false;
        IS_SMLCPS = false;
    }

    static boolean is_Shift() { return IS__SHIFT; }
    static boolean isSelect() { return IS_SELECT; }
    static boolean is__Ctrl() { return IS___CTRL; }
    static boolean is___Alt() { return IS____ALT; }

    static void toggleSelect() { IS_SELECT = !IS_SELECT; }
    static void toggleBolded() { if (IS_BOLDED) { setFontsOff(); }  else { setFontsOff(); IS_BOLDED = true; } }
    static void toggleItalic() { if (IS_ITALIC) { setFontsOff(); }  else { setFontsOff(); IS_ITALIC = true; } }
    static void toggle119808() { if (IS_119860) { setAllEmOff(); }  else { setAllEmOff(); IS_119808 = true; } }
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
    static void toggleRflctd() { if (IS_RFLCTD) { setAllEmOff(); }  else { setAllEmOff(); IS_RFLCTD = true; } }
    static void toggleSmlcps() { if (IS_SMLCPS) { setAllEmOff(); }  else { setAllEmOff(); IS_SMLCPS = true; } }
}
