package com.vlath.keyboard;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.KeyboardView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import java.util.List;

public class CustomKeyboard extends KeyboardView {

  Drawable mTransparent = new ColorDrawable(Color.TRANSPARENT);
  Paint mPaint = new Paint();


  private static Context kcontext;


  public void assignCustomKeys() {
    kcontext = getContext();
    List<Key> keys = getKeyboard().getKeys();

    for (Key key : keys) {
      try {
        if (key.codes[0] == -501) key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k1", "");
        if (key.codes[0] == -502) key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k2", "");
        if (key.codes[0] == -503) key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k3", "");
        if (key.codes[0] == -504) key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k4", "");
        if (key.codes[0] == -505) key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k5", "");
        if (key.codes[0] == -506) key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k6", "");
        if (key.codes[0] == -507) key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k7", "");
        if (key.codes[0] == -508) key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k8", "");
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  public CustomKeyboard(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomKeyboard(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public LatinKeyboard getLatinKeyboard() {
    return (LatinKeyboard)getKeyboard();
  }


  @Override
  protected boolean onLongPress(Key key) {
    System.out.println(key.codes+" "+key.label+" "+key.popupCharacters);
    if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
      getOnKeyboardActionListener().onKey(-100, null);
      // getOnKeyboardActionListener().onRelease(key.codes[0], null);, ((String)key.popupCharacters)
      return true;
    }
    return super.onLongPress(key);
  }

  @Override
  public void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPaint.setTextAlign(Paint.Align.CENTER);
    mPaint.setTextSize(28);
    mPaint.setColor(Color.parseColor("#a5a7aa"));

    assignCustomKeys();

    List<Key> keys = getKeyboard().getKeys();

    for (Key key : keys) {
      if (key.label != null) {
        if (key.codes[0] == -113) {
          mTransparent.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
          mTransparent.draw(canvas);
        }
        if (key.codes[0] == -114) {
          mTransparent.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
          mTransparent.draw(canvas);
        }
      }
    }
  }
}