package com.vlath.keyboard;

public class Variables {
  private static boolean IS_CTRL  = false;
  private static boolean IS_ALT   = false;
  private static boolean IS_SHIFT = false;
  private static boolean IS_BOLD = false;
  private static boolean IS_ITALIC = false;

  public static boolean isAnyOn() {
    return IS_CTRL || IS_ALT;
  }
  public static boolean isCtrl() {
    return IS_CTRL;
  }
  public static boolean isAlt() {
    return IS_ALT;
  }
  public static boolean isShift() { return IS_SHIFT; }
  public static boolean isBold() { return IS_BOLD; }
  public static boolean isItalic() { return IS_ITALIC; }

  public static void setIsCtrl(boolean on) {
    IS_CTRL = on;
  }
  public static void setIsAlt(boolean on) {
    IS_ALT = on;
  }
  public static void setIsShift(boolean on) { IS_SHIFT = on; }
  public static void setIsBold(boolean on) { IS_BOLD = on; }
  public static void setIsItalic(boolean on) { IS_ITALIC = on; }

  public static void setCtrlOn() {
    IS_CTRL = true;
  }
  public static void setCtrlOff() { IS_CTRL = false; }
  public static void setAltOn() {
    IS_ALT = true;
  }
  public static void setAltOff() {
    IS_ALT = false;
  }
  public static void setShiftOn() {
    IS_SHIFT = true;
  }
  public static void setShiftOff() {
    IS_SHIFT = false;
  }
  public static void setBoldOn() { IS_BOLD = true; }
  public static void setBoldOff() { IS_BOLD = false; }
  public static void setItalicOn() { IS_ITALIC = true; }
  public static void setItalicOff() { IS_ITALIC = false; }

  public static void toggleIsBold() { IS_BOLD = !IS_BOLD; }
  public static void toggleIsItalic() { IS_ITALIC = !IS_ITALIC; }


}



