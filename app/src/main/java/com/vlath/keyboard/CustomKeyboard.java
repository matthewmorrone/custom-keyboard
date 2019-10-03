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

import static com.vlath.keyboard.PCKeyboard.layouts;
import static com.vlath.keyboard.PCKeyboard.hexBuffer;
import static com.vlath.keyboard.PCKeyboard.morseBuffer;
import static com.vlath.keyboard.Variables.isShift;
import static com.vlath.keyboard.Variables.isSelecting;
import static com.vlath.keyboard.Variables.isBold;
import static com.vlath.keyboard.Variables.isItalic;

public class CustomKeyboard extends KeyboardView {

    Paint mPaint = new Paint();
    private static Context kcontext;

    public CustomKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.parseColor("#80ffffff")); 
    }

    public CustomKeyboard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint.setColor(Color.parseColor("#80ffffff")); 
    }

    public void assignCustomKeys() {
        kcontext = getContext();
        List<Key> keys = getKeyboard().getKeys();

        for (Key key : keys) {
            try {
                if (key.codes[0] == -501) { key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k1", ""); }
                if (key.codes[0] == -502) { key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k2", ""); }
                if (key.codes[0] == -503) { key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k3", ""); }
                if (key.codes[0] == -504) { key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k4", ""); }
                if (key.codes[0] == -505) { key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k5", ""); }
                if (key.codes[0] == -506) { key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k6", ""); }
                if (key.codes[0] == -507) { key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k7", ""); }
                if (key.codes[0] == -508) { key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k8", ""); }
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

    public void selectKey(Canvas canvas, Key key) {
        canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
        // canvas.drawColor(0x60ffffff);
        // mPaint.setColor(Color.parseColor("#a0ffffff")); 
        canvas.drawRoundRect(key.x, key.y, key.x+key.width, key.y+key.height, 5, 5, mPaint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        assignCustomKeys();

        kcontext = getContext();
        List<Key> keys = getKeyboard().getKeys();
        for (Key key : keys) {
            if (key.codes[0] == -1) {
                if (getKeyboard().isShifted()) {
                    selectKey(canvas, key);
                }
            }

            // if (key.codes[0] == -1)  {if (Variables.isShift())      {selectKey(canvas, key);}}
            if (key.codes[0] == -12) {if (Variables.isBold())       {selectKey(canvas, key);}}
            if (key.codes[0] == -13) {if (Variables.isItalic())     {selectKey(canvas, key);}}
            if (key.codes[0] == -67) {if (Variables.isSelecting())  {selectKey(canvas, key);}}
            if (key.codes[0] == -35) {if (Variables.is119808())     {selectKey(canvas, key);}}
            if (key.codes[0] == -36) {if (Variables.is119860())     {selectKey(canvas, key);}}
            if (key.codes[0] == -37) {if (Variables.is119912())     {selectKey(canvas, key);}}
            if (key.codes[0] == -38) {if (Variables.is119964())     {selectKey(canvas, key);}}
            if (key.codes[0] == -39) {if (Variables.is120016())     {selectKey(canvas, key);}}
            if (key.codes[0] == -40) {if (Variables.is120068())     {selectKey(canvas, key);}}
            if (key.codes[0] == -41) {if (Variables.is120120())     {selectKey(canvas, key);}}
            if (key.codes[0] == -42) {if (Variables.is120172())     {selectKey(canvas, key);}}
            if (key.codes[0] == -43) {if (Variables.is120224())     {selectKey(canvas, key);}}
            if (key.codes[0] == -44) {if (Variables.is120276())     {selectKey(canvas, key);}}
            if (key.codes[0] == -45) {if (Variables.is120328())     {selectKey(canvas, key);}}
            if (key.codes[0] == -46) {if (Variables.is120380())     {selectKey(canvas, key);}}
            if (key.codes[0] == -47) {if (Variables.is120432())     {selectKey(canvas, key);}}
            if (key.codes[0] == -68) {if (Variables.is127280())     {selectKey(canvas, key);}}
            if (key.codes[0] == -69) {if (Variables.is127312())     {selectKey(canvas, key);}}
            if (key.codes[0] == -70) {if (Variables.is127344())     {selectKey(canvas, key);}}
            if (key.codes[0] == -71) {if (Variables.is127462())     {selectKey(canvas, key);}}
            if (key.codes[0] == -72) {if (Variables.is9372())       {selectKey(canvas, key);}}
            if (key.codes[0] == -73) {if (Variables.is9398())       {selectKey(canvas, key);}}
            if (key.codes[0] == -50) {if (Variables.isReflected())  {selectKey(canvas, key);}}
            if (key.codes[0] == -57) {if (Variables.isSmallcaps())  {selectKey(canvas, key);}}

            try {
                mPaint.setTextAlign(Paint.Align.CENTER);
                mPaint.setTextSize(28);
                if (key.popupCharacters != null && PreferenceManager.getDefaultSharedPreferences(kcontext).getBoolean("hints", false)) {
                    if (key.popupCharacters.length() > 0 && PreferenceManager.getDefaultSharedPreferences(kcontext).getBoolean("hint1", false)) {
                        canvas.drawText((Variables.isShift() ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase() : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+20,             key.y+30,              mPaint);
                    }
                    if (key.popupCharacters.length() > 1 && PreferenceManager.getDefaultSharedPreferences(kcontext).getBoolean("hint2", false)) {
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
             
            try {
                if (key.codes[0] == -400) { key.label = layouts.get(0).name; }
                if (key.codes[0] == -401) { key.label = layouts.get(1).name; }
                if (key.codes[0] == -402) { key.label = layouts.get(2).name; }
                if (key.codes[0] == -403) { key.label = layouts.get(3).name; }
                if (key.codes[0] == -404) { key.label = layouts.get(4).name; }
                if (key.codes[0] == -405) { key.label = layouts.get(5).name; }
                if (key.codes[0] == -406) { key.label = layouts.get(6).name; }
                if (key.codes[0] == -407) { key.label = layouts.get(7).name; }
                if (key.codes[0] == -408) { key.label = layouts.get(8).name; }
                if (key.codes[0] == -409) { key.label = layouts.get(9).name; }
                if (key.codes[0] == -410) { key.label = layouts.get(10).name; }
                if (key.codes[0] == -411) { key.label = layouts.get(11).name; }
                if (key.codes[0] == -412) { key.label = layouts.get(12).name; }
                if (key.codes[0] == -413) { key.label = layouts.get(13).name; }
                if (key.codes[0] == -414) { key.label = layouts.get(14).name; }
                if (key.codes[0] == -415) { key.label = layouts.get(15).name; }
                if (key.codes[0] == -416) { key.label = layouts.get(16).name; }
                if (key.codes[0] == -417) { key.label = layouts.get(17).name; }
                if (key.codes[0] == -418) { key.label = layouts.get(18).name; }
                if (key.codes[0] == -419) { key.label = layouts.get(19).name; }
            }
            catch (Exception ignored) {}
            
/*
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
*/
            if (key.codes[0] == 32 && morseBuffer.length() > 0) {
                key.label = morseBuffer;
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
