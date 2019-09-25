package com.vlath.keyboard;

import android.inputmethodservice.KeyboardView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.vlath.keyboard.PCKeyboard.hexBuffer;
import static com.vlath.keyboard.Variables.isShift;
import static com.vlath.keyboard.Variables.isSelecting;
import static com.vlath.keyboard.Variables.isBold;
import static com.vlath.keyboard.Variables.isItalic;

public class CustomKeyboard extends KeyboardView {

 Paint mPaint = new Paint();
 private static Context kcontext;

 public CustomKeyboard(Context context, AttributeSet attrs) {
  super(context, attrs);
 }

 public CustomKeyboard(Context context, AttributeSet attrs, int defStyle) {
  super(context, attrs, defStyle);
 }

 public void assignCustomKeys() {
  kcontext = getContext();
  List<Key> keys = getKeyboard().getKeys();

  for (Key key : keys) {
   try {
    if (key.codes[0] == -501) {
     key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k1", "");
    }
    if (key.codes[0] == -502) {
     key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k2", "");
    }
    if (key.codes[0] == -503) {
     key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k3", "");
    }
    if (key.codes[0] == -504) {
     key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k4", "");
    }
    if (key.codes[0] == -505) {
     key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k5", "");
    }
    if (key.codes[0] == -506) {
     key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k6", "");
    }
    if (key.codes[0] == -507) {
     key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k7", "");
    }
    if (key.codes[0] == -508) {
     key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k8", "");
    }
   }
   catch (Exception ignored) {}
  }
 }

 @Override
 protected boolean onLongPress(Key key) {
  if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
   getOnKeyboardActionListener().onKey(-100, null);
   return true;
  }
  return super.onLongPress(key);
 }

 @Override
 public void onDraw(Canvas canvas) {
  super.onDraw(canvas);

  assignCustomKeys();

  kcontext = getContext();
  List<Key> keys = getKeyboard().getKeys();
  for (Key key : keys) {
   try {
    mPaint.setTextAlign(Paint.Align.CENTER);
    mPaint.setTextSize(28);
    mPaint.setColor(Color.parseColor("#a0ffffff")); //a5a7aa
    if (key.popupCharacters != null && PreferenceManager.getDefaultSharedPreferences(kcontext).getBoolean("hints", true)) {
     if (key.popupCharacters.length() > 0 && PreferenceManager.getDefaultSharedPreferences(kcontext).getBoolean("hint1", true)) {
      canvas.drawText((Variables.isShift() ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase() : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+20,             key.y+30,              mPaint);
     }
     if (key.popupCharacters.length() > 1 && PreferenceManager.getDefaultSharedPreferences(kcontext).getBoolean("hint2", true)) {
      canvas.drawText((Variables.isShift() ? String.valueOf(key.popupCharacters.charAt(1)).toUpperCase() : String.valueOf(key.popupCharacters.charAt(1)).toLowerCase()), key.x+(key.width-20), key.y+30,              mPaint);
     }
     if (key.popupCharacters.length() > 2 && PreferenceManager.getDefaultSharedPreferences(kcontext).getBoolean("hint3", false)) {
      canvas.drawText((Variables.isShift() ? String.valueOf(key.popupCharacters.charAt(2)).toUpperCase() : String.valueOf(key.popupCharacters.charAt(2)).toLowerCase()), key.x+20,             key.y+(key.height-15), mPaint);
     }
     if (key.popupCharacters.length() > 3 && PreferenceManager.getDefaultSharedPreferences(kcontext).getBoolean("hint4", false)) {
      canvas.drawText((Variables.isShift() ? String.valueOf(key.popupCharacters.charAt(3)).toUpperCase() : String.valueOf(key.popupCharacters.charAt(3)).toLowerCase()), key.x+(key.width-20), key.y+(key.height-15), mPaint);
     }
    }
   }
   catch(Exception ignored) {}
   if (key.codes[0] == -12) {
    if (isBold()) {
     canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
     canvas.drawColor(0x80ffffff);
     canvas.drawRoundRect(key.x, key.y, key.x+key.width, key.y+key.height, 5, 5, mPaint);
    }
   }
   if (key.codes[0] == -13) {
    if (isItalic()) {
     canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
     canvas.drawColor(0x80ffffff);
     canvas.drawRoundRect(key.x, key.y, key.x+key.width, key.y+key.height, 5, 5, mPaint);
    }
   }

   if (key.codes[0] == -67) {
    if (isSelecting()) {
     canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
     canvas.drawColor(0x80ffffff);
     canvas.drawRoundRect(key.x, key.y, key.x+key.width, key.y+key.height, 5, 5, mPaint);
    }
   }
   if (key.codes[0] == -1) {
    if (isShift()) {
     Drawable dr = (Drawable)kcontext.getResources().getDrawable(R.drawable.ic_shift_lock); 
     dr.setBounds(key.x+20, key.y+40, key.x+90, key.y+110); 
     dr.draw(canvas);
    }
    else {
     Drawable dr = (Drawable)kcontext.getResources().getDrawable(R.drawable.ic_shift); 
     dr.setBounds(key.x+20, key.y+30, key.x+90, key.y+100); 
     dr.draw(canvas); 
    }
   }
   if (hexBuffer.length() > 0) {
    if (key.codes[0] == -2001) {
     try {
      key.label = StringUtils.leftPad(hexBuffer, 4, "0");
     }
     catch (NumberFormatException e) {
      key.label = "0x0000";
     }
    }
    if (key.codes[0] == -2002) {
     try {
      key.label = String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(hexBuffer, 4, "0")));
     }
     catch (NumberFormatException e) {
      key.label = "";
     }
    }
   }
  }
 }
}