package com.vlath.keyboard;

public class Variables {

  public static int cursorStart = -1;
  public static int cursorEnd = -1;
  public static boolean IS_SELECTING = false;
  public static boolean isSelecting() {return IS_SELECTING;}
  public static void toggleSelecting() {IS_SELECTING = !IS_SELECTING;}

  public static boolean IS_CTRL  = false;
  public static boolean IS_ALT   = false;
  public static boolean IS_SHIFT = false;
  public static boolean IS_BOLD = false;
  public static boolean IS_ITALIC = false;
  public static boolean IS_REFLECTED = false;
  public static boolean IS_SMALLCAPS = false;
  public static boolean IS_119808 = false;
  public static boolean IS_119860 = false;
  public static boolean IS_119912 = false;
  public static boolean IS_119964 = false;
  public static boolean IS_120016 = false;
  public static boolean IS_120068 = false;
  public static boolean IS_120120 = false;
  public static boolean IS_120172 = false;
  public static boolean IS_120224 = false;
  public static boolean IS_120276 = false;
  public static boolean IS_120328 = false;
  public static boolean IS_120380 = false;
  public static boolean IS_120432 = false;

  public static boolean is119808() {return IS_119808;}
  public static boolean is119860() {return IS_119860;}
  public static boolean is119912() {return IS_119912;}
  public static boolean is119964() {return IS_119964;}
  public static boolean is120016() {return IS_120016;}
  public static boolean is120068() {return IS_120068;}
  public static boolean is120120() {return IS_120120;}
  public static boolean is120172() {return IS_120172;}
  public static boolean is120224() {return IS_120224;}
  public static boolean is120276() {return IS_120276;}
  public static boolean is120328() {return IS_120328;}
  public static boolean is120380() {return IS_120380;}
  public static boolean is120432() {return IS_120432;}
  public static boolean isReflected() {return IS_REFLECTED;}
  public static boolean isSmallcaps() {return IS_SMALLCAPS;}

  public static void setFontsOff() {
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
  }

  public static void setAllOff() {
    IS_BOLD = false;
    IS_ITALIC = false;
    IS_REFLECTED = false;
    IS_SMALLCAPS = false;
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
  }

  
  public static boolean isAnyOn() {return IS_CTRL || IS_ALT;}
  public static boolean isCtrl() {return IS_CTRL;}
  public static boolean isAlt() {return IS_ALT;}
  public static boolean isShift() {return IS_SHIFT;}
  public static boolean isBold() {return IS_BOLD;}
  public static boolean isItalic() {return IS_ITALIC;}
  public static void setCtrlOn() {IS_CTRL = true;}
  public static void setCtrlOff() {IS_CTRL = false;}
  public static void setAltOn() {IS_ALT = true;}
  public static void setAltOff() {IS_ALT = false;}
  public static void setShiftOn() {IS_SHIFT = true;}
  public static void setShiftOff() {IS_SHIFT = false;}
  public static void setBoldOff() {IS_BOLD = false;}
  public static void setItalicOff() {IS_ITALIC = false;}

  public static void toggleIsBold() {setFontsOff(); IS_BOLD = !IS_BOLD;}
  public static void toggleIsItalic() {setFontsOff(); IS_ITALIC = !IS_ITALIC;}

  public static void toggleIsReflected() {IS_REFLECTED = !IS_REFLECTED;}
  public static void toggleIsSmallcaps() {IS_SMALLCAPS = !IS_SMALLCAPS;}



  public static void toggle119808() {setAllOff(); IS_119808 = !IS_119808;}
  public static void toggle119860() {setAllOff(); IS_119860 = !IS_119860;}
  public static void toggle119912() {setAllOff(); IS_119912 = !IS_119912;}
  public static void toggle119964() {setAllOff(); IS_119964 = !IS_119964;}
  public static void toggle120016() {setAllOff(); IS_120016 = !IS_120016;}
  public static void toggle120068() {setAllOff(); IS_120068 = !IS_120068;}
  public static void toggle120120() {setAllOff(); IS_120120 = !IS_120120;}
  public static void toggle120172() {setAllOff(); IS_120172 = !IS_120172;}
  public static void toggle120224() {setAllOff(); IS_120224 = !IS_120224;}
  public static void toggle120276() {setAllOff(); IS_120276 = !IS_120276;}
  public static void toggle120328() {setAllOff(); IS_120328 = !IS_120328;}
  public static void toggle120380() {setAllOff(); IS_120380 = !IS_120380;}
  public static void toggle120432() {setAllOff(); IS_120432 = !IS_120432;}
}



