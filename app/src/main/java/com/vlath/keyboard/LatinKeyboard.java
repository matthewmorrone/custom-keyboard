package com.vlath.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;

public class LatinKeyboard extends Keyboard {

  private static short rowNumber = 4;

  public String name;

  LatinKeyboard(Context context, int xmlLayoutResId) {
    super(context, xmlLayoutResId);
  }

  LatinKeyboard(Context context, int xmlLayoutResId, String name) {
    super(context, xmlLayoutResId);
    this.name = name;
  }

  void setRowNumber(short number) {
    rowNumber = number;
  }

  @Override
  public int getHeight() {
    return getKeyHeight() * rowNumber;
  }

  public void setKeyHeight(int height) {
    super.setKeyHeight(height);
  }
}