package com.custom.keyboard;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.inputmethodservice.KeyboardView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class CustomKeyboardView extends KeyboardView {
    Paint mPaint = new Paint();
    Canvas canvas;
    Context kcontext;
    SharedPreferences sharedPreferences;

    int currentKeyboardLayout = R.layout.primary;
    int textSize, theme, selected, borderColor, foreground, background, borderWidth, paddingWidth, borderRadius;

    int[] repeatable = new int[] {-13, -14, -15, -16, -5, -7};
    Paint paint = new Paint();
    Rect textBounds = new Rect();

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        textSize = Integer.parseInt(Util.orNull(sharedPreferences.getString("textSize", "48"), "48"));
        theme = Integer.parseInt(Util.orNull(sharedPreferences.getString("theme", "1"), "1"));
        selected = Color.parseColor("#80FFFFFF");
        borderColor = sharedPreferences.getInt("bdcolor", Color.WHITE);
        background = sharedPreferences.getInt("bgcolor", Color.BLACK);
        foreground = sharedPreferences.getInt("fgcolor", Color.WHITE);

        borderWidth = Integer.parseInt(Util.orNull(sharedPreferences.getString("borderWidth", "0"), "0"));
        paddingWidth = Integer.parseInt(Util.orNull(sharedPreferences.getString("paddingWidth", "0"), "0"));
        borderRadius = Integer.parseInt(Util.orNull(sharedPreferences.getString("borderRadius", "0"), "0"));
    }

    public CustomKeyboard getCustomKeyboard() {
        return (CustomKeyboard)getKeyboard();
    }

    public ArrayList<String> getClipboardHistory() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(kcontext);
        LinkedHashSet<String> clipboardHistory = new LinkedHashSet<String>(Util.orNull(sharedPreferences.getStringSet("clipboardHistory", new LinkedHashSet<String>()), new LinkedHashSet<String>()));
        ArrayList<String> result = new ArrayList<String>(clipboardHistory);
        Collections.reverse(result);
        return result;
    }

    @Override
    protected boolean onLongPress(Key key) {

        if (key.codes[0] == -2
         || key.codes[0] == -12
         || key.codes[0] == -15
         || key.codes[0] == -16
         || key.codes[0] == -200) {
            return false; // super.onLongPress(key);
        }

        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(CustomKeyboard.KEYCODE_OPTIONS, null);
            return true;
        }
        if (key.popupCharacters == null || key.popupCharacters.length() == 0) {
            // getOnKeyboardActionListener().onKey(key.codes[0], null);
            return true;
        }
        if (key.popupCharacters != null && key.popupCharacters.length() == 1) {
            if (sharedPreferences.getBoolean("single_hint", true)) {
                getOnKeyboardActionListener().onKey(key.popupCharacters.charAt(0), null);
                return true;
            }
        }
        if (key.popupCharacters.length() > 8) {
            key.popupResId = R.layout.popup_template;
        }
        return super.onLongPress(key);
    }

    public void drawKey(Drawable drawable, Key key) {
        drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        drawable.draw(canvas);
    }

    public void selectKey(Key key, int corner) {
        canvas.save();
        mPaint.setColor(selected);
        canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
        if (corner > 0) {
            canvas.drawRoundRect(key.x, key.y, key.x+key.width, key.y+key.height, corner, corner, mPaint);
        }
        else {
            canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);
        }
        canvas.restore();
    }

    public void drawable(Key key) {
        int center = key.x+(key.width/2);
        int middle = key.y+(key.height/2);
        int left   = center-(key.width/4);
        int top    = middle-(key.height/4);
        int right  = center+(key.width/4);
        int bottom = middle+(key.height/4);

        canvas.save();

        key.icon.setBounds(left, top, right, bottom);
        key.icon.setColorFilter(foreground, PorterDuff.Mode.SRC_ATOP);
        key.icon.draw(canvas);
        invalidateDrawable(key.icon);

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return super.onTouchEvent(me);

    }

    public void drawable(Key key, int drawable) {
        int center = key.x+(key.width/2);
        int middle = key.y+(key.height/2);
        int left   = center-(key.width/4);
        int top    = middle-(key.height/4);
        int right  = center+(key.width/4);
        int bottom = middle+(key.height/4);

        canvas.save();

        Drawable dr = getResources().getDrawable(drawable, null);
        dr.setBounds(left, top, right, bottom);
        dr.draw(canvas);
        invalidateDrawable(key.icon);

        canvas.restore();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextAlign(Paint.Align.CENTER);

        List<Key> keys = getKeyboard().getKeys();

        kcontext = getContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(kcontext);

        this.canvas = canvas;

        mPaint.setTextAlign(Paint.Align.CENTER);

        LayerDrawable pressDrawable = (LayerDrawable)getResources().getDrawable(R.drawable.press);
        GradientDrawable gradientDrawable = (GradientDrawable)pressDrawable.findDrawableByLayerId(R.id.keyPressDrawable);
        gradientDrawable.setCornerRadius(borderRadius);
        gradientDrawable.setStroke(borderWidth, selected);

        ArrayList<String> clipboardHistoryList = getClipboardHistory();
        // if (getCustomKeyboard().title != null && getCustomKeyboard().title.equals("Clipboard")) { }

        for (Key key : keys) {
/*
            mPaint.setColor(borderColor);
            canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);
            mPaint.setColor(background);
            canvas.drawRect(key.x+borderWidth, key.y+borderWidth, key.x+key.width-borderWidth, key.y+key.height-borderWidth, mPaint);

            if (key.label != null) {
                mPaint.setColor(foreground);
                mPaint.setTextSize(textSize);
                mPaint.setTextAlign(Paint.Align.CENTER);
                mPaint.getTextBounds(key.label.toString(), 0, key.label.toString().length(), textBounds);
                canvas.drawText(key.label.toString(), key.x + (key.width / 2), key.y + (13 * (key.height / 16)), mPaint);
            }
            else if (key.icon != null) {
                drawable(key);
            }
*/
/*
            canvas.save();
            canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
            // canvas.clipOutRect(key.x+(border*4)+corner, key.y+(border*4)+corner, key.x+key.width-(border*4)-corner, key.y+key.height-(border*4)-corner);
            mPaint.setColor(Color.parseColor(selected));
            canvas.drawRoundRect(key.x+(border*2), key.y+(border*2), key.x+key.width-(border*2), key.y+key.height-(border*2), corner, corner, mPaint);
            // canvas.drawRoundRect(key.x+(border*4), key.y+(border*4), key.x+key.width-(border*4), key.y+key.height-(border*4), corner, corner, mPaint);
            canvas.restore();
*/
/*
            if (Util.contains(repeatable, key.codes[0])) {
                key.repeatable = true;
            }
*/
            if (borderRadius > 0 || borderWidth > 0 || paddingWidth > 0) {
                canvas.save();
                mPaint.setColor(selected);
                if (borderWidth > 0) {
                    // @TODO: how to clipOutRoundedRect?
                    canvas.clipOutRect(key.x+paddingWidth+borderWidth, key.y+paddingWidth+borderWidth, key.x+key.width-paddingWidth-borderWidth, key.y+key.height-paddingWidth-borderWidth);
                }
                canvas.drawRoundRect(key.x+paddingWidth, key.y+paddingWidth, key.x+key.width-paddingWidth, key.y+key.height-paddingWidth, borderRadius, borderRadius, mPaint);
                canvas.restore();
            }

            if (key.codes[0] ==  -73)   key.label = Util.timemoji();
            if (getCustomKeyboard().title != null && getCustomKeyboard().title.equals("Clipboard")) {
                if (key.codes[0] == -301 && clipboardHistoryList.size() >  0) key.label = clipboardHistoryList.get( 0);
                if (key.codes[0] == -302 && clipboardHistoryList.size() >  1) key.label = clipboardHistoryList.get( 1);
                if (key.codes[0] == -303 && clipboardHistoryList.size() >  2) key.label = clipboardHistoryList.get( 2);
                if (key.codes[0] == -304 && clipboardHistoryList.size() >  3) key.label = clipboardHistoryList.get( 3);
                if (key.codes[0] == -305 && clipboardHistoryList.size() >  4) key.label = clipboardHistoryList.get( 4);
                if (key.codes[0] == -306 && clipboardHistoryList.size() >  5) key.label = clipboardHistoryList.get( 5);
                if (key.codes[0] == -307 && clipboardHistoryList.size() >  6) key.label = clipboardHistoryList.get( 6);
                if (key.codes[0] == -308 && clipboardHistoryList.size() >  7) key.label = clipboardHistoryList.get( 7);
                if (key.codes[0] == -309 && clipboardHistoryList.size() >  8) key.label = clipboardHistoryList.get( 8);
                if (key.codes[0] == -310 && clipboardHistoryList.size() >  9) key.label = clipboardHistoryList.get( 9);
                if (key.codes[0] == -311 && clipboardHistoryList.size() > 10) key.label = clipboardHistoryList.get(10);
                if (key.codes[0] == -312 && clipboardHistoryList.size() > 11) key.label = clipboardHistoryList.get(11);
                if (key.codes[0] == -313 && clipboardHistoryList.size() > 12) key.label = clipboardHistoryList.get(12);
                if (key.codes[0] == -314 && clipboardHistoryList.size() > 13) key.label = clipboardHistoryList.get(13);
                if (key.codes[0] == -315 && clipboardHistoryList.size() > 14) key.label = clipboardHistoryList.get(14);
                if (key.codes[0] == -316 && clipboardHistoryList.size() > 15) key.label = clipboardHistoryList.get(15);
            }

            if (key.codes[0] == -501)   key.text = sharedPreferences.getString("k1", "");
            if (key.codes[0] == -502)   key.text = sharedPreferences.getString("k2", "");
            if (key.codes[0] == -503)   key.text = sharedPreferences.getString("k3", "");
            if (key.codes[0] == -504)   key.text = sharedPreferences.getString("k4", "");
            if (key.codes[0] == -505)   key.text = sharedPreferences.getString("k5", "");
            if (key.codes[0] == -506)   key.text = sharedPreferences.getString("k6", "");
            if (key.codes[0] == -507)   key.text = sharedPreferences.getString("k7", "");
            if (key.codes[0] == -508)   key.text = sharedPreferences.getString("k8", "");
            if (key.codes[0] == -509)   key.text = sharedPreferences.getString("name", "");
            if (key.codes[0] == -510)   key.text = sharedPreferences.getString("email", "");
            if (key.codes[0] == -511)   key.text = sharedPreferences.getString("phone", "");
            if (key.codes[0] == -512)   key.text = sharedPreferences.getString("address", "");
            if (key.codes[0] == -513)   key.text = sharedPreferences.getString("password", "");

            if (key.codes[0] == 32 && sharedPreferences.getBoolean("space", false)) selectKey(key, borderRadius);
            if (key.codes[0] == -1) {
                if (Variables.isShift()) selectKey(key, borderRadius);
                if (getKeyboard().isShifted()) drawable(key, R.drawable.ic_shift_lock);
            }
            if (key.codes[0] == -11) {if (Variables.isSelecting()) {selectKey(key, borderRadius);}}

            if (key.codes[0] == -94) {if (Variables.isBold()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -95) {if (Variables.isItalic()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -96) {if (Variables.isEmphasized()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -97) {if (Variables.isUnderlined()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -98) {if (Variables.isUnderscored()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -99) {if (Variables.isStrikethrough()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -145) {if (Variables.isBoldSerif()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -146) {if (Variables.isItalicSerif()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -147) {if (Variables.isBoldItalicSerif()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -148) {if (Variables.isScript()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -149) {if (Variables.isScriptBold()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -150) {if (Variables.isFraktur()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -151) {if (Variables.isFrakturBold()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -152) {if (Variables.isSans()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -153) {if (Variables.isMonospace()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -154) {if (Variables.isDoublestruck()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -155) {if (Variables.isEnsquare()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -156) {if (Variables.isCircularStampLetters()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -157) {if (Variables.isRectangularStampLetters()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -158) {if (Variables.isSmallCaps()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -159) {if (Variables.isParentheses()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -160) {if (Variables.isEncircle()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -161) {if (Variables.isReflected()) {selectKey(key, borderRadius);}}
            if (key.codes[0] == -162) {if (Variables.isCaps()) {selectKey(key, borderRadius);}}

            mPaint.setTextAlign(Paint.Align.CENTER);

            if (getCustomKeyboard().title != null && getCustomKeyboard().title.equals("Calculator")) continue;

            if (key.popupCharacters != null
                && key.codes != null
                && key.codes[0] != -1
                && key.codes[0] !=  7
                && key.codes[0] != 10
                && key.codes[0] != 32
                && sharedPreferences.getBoolean("hints", true)
            ) {
                canvas.save();
                mPaint.setTextSize(32);
                if (key.popupCharacters.length() >= 1 
                &&   sharedPreferences.getBoolean("hint1", true)
                &&  !sharedPreferences.getBoolean("hint2", false)
                &&  !sharedPreferences.getBoolean("hint3", false)
                &&  !sharedPreferences.getBoolean("hint4", false)
                ) {
                    mPaint.setColor(foreground);
                    canvas.drawText(((getKeyboard().isShifted())
                        ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase()
                        : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+key.width/2, key.y+36, mPaint);
                }
                else {
                    mPaint.setColor(foreground);
                    if (key.popupCharacters.length() > 0 && sharedPreferences.getBoolean("hint1", false)) {
                        canvas.drawText(((getKeyboard().isShifted())
                            ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase()
                            : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+20, key.y+30, mPaint);
                    }
                    if (key.popupCharacters.length() > 1 && sharedPreferences.getBoolean("hint2", false)) {
                        canvas.drawText((getKeyboard().isShifted()
                            ? String.valueOf(key.popupCharacters.charAt(1)).toUpperCase()
                            : String.valueOf(key.popupCharacters.charAt(1)).toLowerCase()), key.x+(key.width-20), key.y+30, mPaint);
                    }
                    if (key.popupCharacters.length() > 2 && sharedPreferences.getBoolean("hint3", false)) {
                        canvas.drawText((getKeyboard().isShifted()
                            ? String.valueOf(key.popupCharacters.charAt(2)).toUpperCase()
                            : String.valueOf(key.popupCharacters.charAt(2)).toLowerCase()), key.x+20, key.y+key.height-15, mPaint);
                    }
                    if (key.popupCharacters.length() > 3 && sharedPreferences.getBoolean("hint4", false)) {
                        canvas.drawText((getKeyboard().isShifted()
                            ? String.valueOf(key.popupCharacters.charAt(3)).toUpperCase()
                            : String.valueOf(key.popupCharacters.charAt(3)).toLowerCase()), key.x+(key.width-20), key.y+key.height-15, mPaint);
                    }
                }
                canvas.restore();
            }
        }
    }
}