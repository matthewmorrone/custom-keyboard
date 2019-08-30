package com.vlath.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.view.inputmethod.EditorInfo;

public class LatinKeyboard extends Keyboard {

  private Key mEnterKey;
  private Key mSpaceKey;
  private static short rowNumber = 4;

  public String name;

  private Key mModeChangeKey;

  private Key mLanguageSwitchKey;

  private Key mSavedModeChangeKey;

  private Key mSavedLanguageSwitchKey;


  private Key k1;
  private Key k2;
  private Key k3;
  private Key k4;
  private Key k5;
  private Key k6;
  private Key k7;
  private Key k8;

  public LatinKeyboard(Context context, int xmlLayoutResId) {
    super(context, xmlLayoutResId);
  }

  public LatinKeyboard(Context context, int xmlLayoutResId, String name) {
    super(context, xmlLayoutResId);
    this.name = name;
  }

  @Override
  protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
    Key key = new LatinKey(res, parent, x, y, parser);
    if (key.codes[0] == -501) k1 = key;
    if (key.codes[0] == -502) k2 = key;
    if (key.codes[0] == -503) k3 = key;
    if (key.codes[0] == -504) k4 = key;
    if (key.codes[0] == -505) k5 = key;
    if (key.codes[0] == -506) k6 = key;
    if (key.codes[0] == -507) k7 = key;
    if (key.codes[0] == -508) k8 = key;
    if (key.codes[0] == 10) {
      mEnterKey = key;
    }
    else if (key.codes[0] == ' ') {
      mSpaceKey = key;
    }
    else if (key.codes[0] == Keyboard.KEYCODE_MODE_CHANGE) {
      mModeChangeKey = key;
      mSavedModeChangeKey = new LatinKey(res, parent, x, y, parser);
    }
    else if (key.codes[0] == -101) {
      mLanguageSwitchKey = key;
      mSavedLanguageSwitchKey = new LatinKey(res, parent, x, y, parser);
    }
    return key;
  }

  public void setRowNumber(short number) {
    rowNumber = number;
  }

  public void changeKeyHeight(double height_modifier) {
    int height = 0;
    for (Keyboard.Key key : getKeys()) {
      key.height *= height_modifier;
      key.y *= height_modifier;
      height = key.height;
    }
    setKeyHeight(height);
    getNearestKeys(0, 0); //somehow adding this fixed a weird bug where bottom row keys could not be pressed if keyboard height is too tall.. from the Keyboard source code seems like calling this will recalculate some values used in keypress detection calculation
  }

  @Override
  public int getHeight() {
    return getKeyHeight() * rowNumber;
  }

  public void setKeyHeight(int height) {
    super.setKeyHeight(height);
  }

  static class LatinKey extends Keyboard.Key {

    public LatinKey(Resources res, Keyboard.Row parent, int x, int y, XmlResourceParser parser) {
      super(res, parent, x, y, parser);
    }

    @Override
    public boolean isInside(int x, int y) {
      return super.isInside(x, codes[0] == KEYCODE_CANCEL ? y - 10 : y);
    }
  }

}