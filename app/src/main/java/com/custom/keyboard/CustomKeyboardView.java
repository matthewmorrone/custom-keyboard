package com.custom.keyboard;

import android.content.SharedPreferences;
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

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.custom.keyboard.CustomInputMethodService.hexBuffer;
import static com.custom.keyboard.CustomInputMethodService.morseBuffer;
import static com.custom.keyboard.Variables.isShift;



public class CustomKeyboardView extends KeyboardView {

    Canvas canvas;
    Context kcontext;
    SharedPreferences sharedPreferences;
    Paint mPaint = new Paint();

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.parseColor("#80ffffff")); 
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint.setColor(Color.parseColor("#80ffffff")); 
    }

    @Override
    protected boolean onLongPress(Key key) {
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(-100, null);
            return true;
        }
        return super.onLongPress(key);
    }

    public void selectKey(Key key) {
        canvas.save();


        int theme = Integer.parseInt(sharedPreferences.getString("theme", "1"));
        String color = theme % 2 == 1 ? "#40ffffff" : "#40ffffff";
        mPaint.setColor(Color.parseColor(color));
        canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
        canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);

        canvas.restore();
    }

    public void drawable(Key key, int drawable, int size) {
        int center = key.x+(key.width/2);
        int middle = key.y+(key.height/2);
        int left   = center-size;
        int top    = middle-size;
        int right  = center+size;
        int bottom = middle+size;

        canvas.save();

        Drawable dr = getResources().getDrawable(drawable, null);
        dr.setBounds(left, top, right, bottom);
        dr.draw(canvas);

        canvas.restore();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        kcontext = getContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(kcontext);
        // System.out.println(sharedPreferences.getAll());

        List<Key> keys = getKeyboard().getKeys();
        for (Key key : keys) {
            
            if (key.codes[0] == -1) {
                if (isShift()) {    
                    canvas.save();
                    mPaint.setColor(Color.parseColor("#40000000"));
                    canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
                    canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);
                    canvas.restore();
                    selectKey(key);
                    drawable(key, R.drawable.ic_shift_lock, 35);
                }
                else if (getKeyboard().isShifted()) {
                    // selectKey(key);
                    drawable(key, R.drawable.ic_shift_lock, 35);
                }
            }

            if (key.codes[0] == -12) {if (Variables.isBold())   {selectKey(key);}}
            if (key.codes[0] == -13) {if (Variables.isItalic()) {selectKey(key);}}
            if (key.codes[0] == -67) {if (Variables.isSelect()) {selectKey(key);}}
            if (key.codes[0] == -35) {if (Variables.is119808()) {selectKey(key);}}
            if (key.codes[0] == -36) {if (Variables.is119860()) {selectKey(key);}}
            if (key.codes[0] == -37) {if (Variables.is119912()) {selectKey(key);}}
            if (key.codes[0] == -38) {if (Variables.is119964()) {selectKey(key);}}
            if (key.codes[0] == -39) {if (Variables.is120016()) {selectKey(key);}}
            if (key.codes[0] == -40) {if (Variables.is120068()) {selectKey(key);}}
            if (key.codes[0] == -41) {if (Variables.is120120()) {selectKey(key);}}
            if (key.codes[0] == -42) {if (Variables.is120172()) {selectKey(key);}}
            if (key.codes[0] == -43) {if (Variables.is120224()) {selectKey(key);}}
            if (key.codes[0] == -44) {if (Variables.is120276()) {selectKey(key);}}
            if (key.codes[0] == -45) {if (Variables.is120328()) {selectKey(key);}}
            if (key.codes[0] == -46) {if (Variables.is120380()) {selectKey(key);}}
            if (key.codes[0] == -47) {if (Variables.is120432()) {selectKey(key);}}
            if (key.codes[0] == -50) {if (Variables.isReflected()) {selectKey(key);}}
            if (key.codes[0] == -57) {if (Variables.isSmallcaps()) {selectKey(key);}}
            if (key.codes[0] == -68) {if (Variables.is127280()) {selectKey(key);}}
            if (key.codes[0] == -69) {if (Variables.is127312()) {selectKey(key);}}
            if (key.codes[0] == -70) {if (Variables.is127344()) {selectKey(key);}}
            if (key.codes[0] == -71) {if (Variables.is127462()) {selectKey(key);}}
            if (key.codes[0] == -72) {if (Variables.is009372()) {selectKey(key);}}
            if (key.codes[0] == -73) {if (Variables.is009398()) {selectKey(key);}}

            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTextSize(28);
            mPaint.setColor(Color.parseColor("#80ffffff"));
            if (key.popupCharacters != null && sharedPreferences.getBoolean("hints", false)) {
                canvas.save();
                if (key.popupCharacters.length() > 0 && sharedPreferences.getBoolean("hint1", false)) {
                    canvas.drawText((getKeyboard().isShifted()
                         ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase()
                         : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+20,             key.y+30,              mPaint);
                }
                if (key.popupCharacters.length() > 1 && sharedPreferences.getBoolean("hint2", false)) {
                    canvas.drawText((getKeyboard().isShifted()
                         ? String.valueOf(key.popupCharacters.charAt(1)).toUpperCase()
                         : String.valueOf(key.popupCharacters.charAt(1)).toLowerCase()), key.x+(key.width-20), key.y+30,              mPaint);
                }
                if (key.popupCharacters.length() > 2 && sharedPreferences.getBoolean("hint3", false)) {
                    canvas.drawText((getKeyboard().isShifted()
                         ? String.valueOf(key.popupCharacters.charAt(2)).toUpperCase()
                         : String.valueOf(key.popupCharacters.charAt(2)).toLowerCase()), key.x+20,             key.y+(key.height-15), mPaint);
                }
                if (key.popupCharacters.length() > 3 && sharedPreferences.getBoolean("hint4", false)) {
                    canvas.drawText((getKeyboard().isShifted()
                         ? String.valueOf(key.popupCharacters.charAt(3)).toUpperCase()
                         : String.valueOf(key.popupCharacters.charAt(3)).toLowerCase()), key.x+(key.width-20), key.y+(key.height-15), mPaint);
                }
                canvas.restore();
            }

            if (key.codes[0] == 32 && morseBuffer.length() > 0) {
                key.label = morseBuffer;
            }
            
            if (hexBuffer.length() > 0) {
                if (key.codes[0] == -2001) { try { key.label = StringUtils.leftPad(hexBuffer, 4, "0"); } catch (NumberFormatException e) { key.label = "0x0000"; } }
                if (key.codes[0] == -2002) { try { key.label = String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(hexBuffer, 4, "0"))); } catch (NumberFormatException e) { key.label = ""; } }
            }
        }
    }
}