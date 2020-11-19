package com.custom.keyboard;

import android.annotation.SuppressLint;
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
import android.inputmethodservice.Keyboard.Key;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressLint("DrawAllocation")
public class CustomKeyboardView extends KeyboardView {
    Paint mPaint = new Paint();
    Canvas canvas;
    Context mContext;
    SharedPreferences sharedPreferences;
    CustomInputMethodService cims;

    int currentKeyboardLayout = R.layout.primary;
    int fontSize, hintFontSize, borderWidth, paddingWidth, borderRadius;
    int theme, selected, borderColor, foreground, background;

    int[] repeatable = new int[] {-13, -14, -15, -16, -5, -7};
    Paint paint = new Paint();
    Rect textBounds = new Rect();

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        fontSize = Integer.parseInt(Util.orNull(sharedPreferences.getString("font_size", "48"), "48"));
        hintFontSize = Integer.parseInt(Util.orNull(sharedPreferences.getString("hint_font_size", "32"), "32"));
        theme = Integer.parseInt(Util.orNull(sharedPreferences.getString("theme", "1"), "1"));
        selected = Color.parseColor("#80FFFFFF");
        borderColor = sharedPreferences.getInt("border_color",     Color.WHITE);
        background  = sharedPreferences.getInt("background_color", Color.BLACK);
        foreground  = sharedPreferences.getInt("foreground_color", Color.WHITE);

        borderWidth  = Integer.parseInt(Util.orNull(sharedPreferences.getString("border_width",  "0"), "0"));
        paddingWidth = Integer.parseInt(Util.orNull(sharedPreferences.getString("padding_width", "0"), "0"));
        borderRadius = Integer.parseInt(Util.orNull(sharedPreferences.getString("border_radius", "0"), "0"));

        this.setClipToOutline(true);
        this.setFitsSystemWindows(true);

        // this.textBounds
    }

    public CustomKeyboard getCustomKeyboard() {
        return (CustomKeyboard)getKeyboard();
    }

    public ArrayList<String> getClipboardHistory() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String clipboardHistory = Util.orNull(sharedPreferences.getString("clipboard_history", ""), "");
        ArrayList<String> clipboardHistoryArray = new ArrayList<String>(Util.deserialize(clipboardHistory));
        Collections.reverse(clipboardHistoryArray);
        return clipboardHistoryArray;
    }

    int[] longPressKeys = {-2, -5, -7, -12, -15, -16, -200, -299, -501, -502, -503, -504, -505, -506, -507, -508, -509, -510, -511, -512, -513};

    @Override
    protected boolean onLongPress(Key key) {
        if (key.popupCharacters != null) {
            key.popupCharacters = key.popupCharacters.toString().replace("◌", "");
            key.popupCharacters = Util.uniqueChars(key.popupCharacters.toString());
        }
        if (key.codes == null) {
            return true;
        }
        if (Util.contains(longPressKeys, key.codes[0])) {
            return true;
        }
        if (key.popupCharacters == null || key.popupCharacters.length() == 0) {
            return true;
        }
        if (key.popupCharacters != null && key.popupCharacters.length() == 1) {
            getOnKeyboardActionListener().onKey(key.popupCharacters.charAt(0), null);
            return true;
        }
        if (key.popupCharacters != null && key.popupCharacters.length() > 1) {
            if (sharedPreferences.getBoolean("single_hint", true)) {
                getOnKeyboardActionListener().onKey(key.popupCharacters.charAt(0), null);
                return true;
            }
        }


            // if (key.popupCharacters.length() > 8) key.popupResId = R.layout.popup_template;
        return super.onLongPress(key);
    }

    public void selectKey(Key key, int corner) {
        canvas.save();
        mPaint.setColor(selected);
        canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
        if (corner > 0) canvas.drawRoundRect(key.x, key.y, key.x+key.width, key.y+key.height, corner, corner, mPaint);
        else canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);
        canvas.restore();
    }

    public void drawable(Key key) {
        int center = key.x+(key.width/2);
        int middle = key.y+(key.height/2);
        int left   = center-(key.height/4);
        int top    = middle-(key.height/4);
        int right  = center+(key.height/4);
        int bottom = middle+(key.height/4);

        canvas.save();

        key.icon.setBounds(left, top, right, bottom);
        key.icon.setColorFilter(foreground, PorterDuff.Mode.SRC_ATOP);
        key.icon.draw(canvas);
        invalidateDrawable(key.icon);

        canvas.restore();
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

        mContext = getContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        this.canvas = canvas;

        mPaint.setTextAlign(Paint.Align.CENTER);

        LayerDrawable pressDrawable = (LayerDrawable)getResources().getDrawable(R.drawable.press);
        GradientDrawable gradientDrawable = (GradientDrawable)pressDrawable.findDrawableByLayerId(R.id.keyPressDrawable);
        gradientDrawable.setCornerRadius(borderRadius);
        gradientDrawable.setStroke(borderWidth, selected);

        ArrayList<String> clipboardHistoryList = getClipboardHistory();

        for (Key key : keys) {
            int primaryCode = key.codes[0];
/*
            mPaint.setColor(borderColor);
            canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);
            mPaint.setColor(background);
            canvas.drawRect(key.x+borderWidth, key.y+borderWidth, key.x+key.width-borderWidth, key.y+key.height-borderWidth, mPaint);

            if (key.label != null) {
                mPaint.setColor(foreground);
                mPaint.setTextSize(fontSize);
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
            if (Util.contains(repeatable, primaryCode)) {
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

            if (getCustomKeyboard().title != null && getCustomKeyboard().title.equals("IPA")) {
                if (sharedPreferences.getBoolean("include_self", false) && key.popupCharacters != null) {
                    StringBuilder sb = new StringBuilder((String)key.popupCharacters);
                    if (sb.indexOf(String.valueOf((char)key.codes[0])) > -1) {
                        sb.deleteCharAt(sb.indexOf(String.valueOf((char)key.codes[0])));
                    }
                    sb.insert(0, (char)key.codes[0]);
                    key.popupCharacters = sb.toString();
                }
            }

            if (primaryCode == -73)   key.label = Util.timemoji();
            if (getCustomKeyboard().title != null && getCustomKeyboard().title.equals("Clipboard")) {
                if (primaryCode == -301 && clipboardHistoryList.size() >  0) key.label = clipboardHistoryList.get( 0);
                if (primaryCode == -302 && clipboardHistoryList.size() >  1) key.label = clipboardHistoryList.get( 1);
                if (primaryCode == -303 && clipboardHistoryList.size() >  2) key.label = clipboardHistoryList.get( 2);
                if (primaryCode == -304 && clipboardHistoryList.size() >  3) key.label = clipboardHistoryList.get( 3);
                if (primaryCode == -305 && clipboardHistoryList.size() >  4) key.label = clipboardHistoryList.get( 4);
                if (primaryCode == -306 && clipboardHistoryList.size() >  5) key.label = clipboardHistoryList.get( 5);
                if (primaryCode == -307 && clipboardHistoryList.size() >  6) key.label = clipboardHistoryList.get( 6);
                if (primaryCode == -308 && clipboardHistoryList.size() >  7) key.label = clipboardHistoryList.get( 7);
                if (primaryCode == -309 && clipboardHistoryList.size() >  8) key.label = clipboardHistoryList.get( 8);
                if (primaryCode == -310 && clipboardHistoryList.size() >  9) key.label = clipboardHistoryList.get( 9);
                if (primaryCode == -311 && clipboardHistoryList.size() > 10) key.label = clipboardHistoryList.get(10);
                if (primaryCode == -312 && clipboardHistoryList.size() > 11) key.label = clipboardHistoryList.get(11);
                if (primaryCode == -313 && clipboardHistoryList.size() > 12) key.label = clipboardHistoryList.get(12);
                if (primaryCode == -314 && clipboardHistoryList.size() > 13) key.label = clipboardHistoryList.get(13);
                if (primaryCode == -315 && clipboardHistoryList.size() > 14) key.label = clipboardHistoryList.get(14);
                if (primaryCode == -316 && clipboardHistoryList.size() > 15) key.label = clipboardHistoryList.get(15);
            }

            if (getCustomKeyboard().title != null && getCustomKeyboard().title.equals("Macros")) {
                if (primaryCode == -501) key.text = sharedPreferences.getString("k1", sharedPreferences.getString("k1", getResources().getString(R.string.k1)));
                if (primaryCode == -502) key.text = sharedPreferences.getString("k2", sharedPreferences.getString("k2", getResources().getString(R.string.k2)));
                if (primaryCode == -503) key.text = sharedPreferences.getString("k3", sharedPreferences.getString("k3", getResources().getString(R.string.k3)));
                if (primaryCode == -504) key.text = sharedPreferences.getString("k4", sharedPreferences.getString("k4", getResources().getString(R.string.k4)));
                if (primaryCode == -505) key.text = sharedPreferences.getString("k5", sharedPreferences.getString("k5", getResources().getString(R.string.k5)));
                if (primaryCode == -506) key.text = sharedPreferences.getString("k6", sharedPreferences.getString("k6", getResources().getString(R.string.k6)));
                if (primaryCode == -507) key.text = sharedPreferences.getString("k7", sharedPreferences.getString("k7", getResources().getString(R.string.k7)));
                if (primaryCode == -508) key.text = sharedPreferences.getString("k8", sharedPreferences.getString("k8", getResources().getString(R.string.k8)));
                if (primaryCode == -509) key.text = sharedPreferences.getString("name", sharedPreferences.getString("name", getResources().getString(R.string.name)));
                if (primaryCode == -510) key.text = sharedPreferences.getString("email", sharedPreferences.getString("email", getResources().getString(R.string.email)));
                if (primaryCode == -511) key.text = sharedPreferences.getString("phone", sharedPreferences.getString("phone", getResources().getString(R.string.phone)));
                if (primaryCode == -512) key.text = sharedPreferences.getString("address", sharedPreferences.getString("address", getResources().getString(R.string.address)));
                if (primaryCode == -513) key.text = sharedPreferences.getString("password", sharedPreferences.getString("password", getResources().getString(R.string.password)));

                if (primaryCode > -501 || primaryCode < -508) continue;

                if (key.icon == null) key.label = key.text;
                String text = Util.orNull(key.label, "").toString();
                int containingWidth = key.width;
                Rect bounds = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), bounds);
                int boundsWidth = bounds.width();
                // System.out.println(primaryCode+" "+containingWidth+" "+boundsWidth+" "+text);
                if (boundsWidth > containingWidth - 50) key.label = Util.safeSubstring(key.text.toString(), 0, 10);
            }

            if (primaryCode == 32 && sharedPreferences.getBoolean("space", false)) selectKey(key, borderRadius);
            if (primaryCode == -1) {
                if (Variables.isShift()) selectKey(key, borderRadius);
                if (getKeyboard().isShifted()) drawable(key, R.drawable.ic_shift_lock);
            }
            if (primaryCode ==  -11) {if (Variables.isSelecting()) {selectKey(key, borderRadius);}}

            if (primaryCode ==  -94) {if (Variables.isBold()) {selectKey(key, borderRadius);}}
            if (primaryCode ==  -95) {if (Variables.isItalic()) {selectKey(key, borderRadius);}}
            if (primaryCode ==  -96) {if (Variables.isEmphasized()) {selectKey(key, borderRadius);}}
            if (primaryCode ==  -97) {if (Variables.isUnderlined()) {selectKey(key, borderRadius);}}
            if (primaryCode ==  -98) {if (Variables.isUnderscored()) {selectKey(key, borderRadius);}}
            if (primaryCode ==  -99) {if (Variables.isStrikethrough()) {selectKey(key, borderRadius);}}
            if (primaryCode == -145) {if (Variables.isBoldSerif()) {selectKey(key, borderRadius);}}
            if (primaryCode == -146) {if (Variables.isItalicSerif()) {selectKey(key, borderRadius);}}
            if (primaryCode == -147) {if (Variables.isBoldItalicSerif()) {selectKey(key, borderRadius);}}
            if (primaryCode == -148) {if (Variables.isScript()) {selectKey(key, borderRadius);}}
            if (primaryCode == -149) {if (Variables.isScriptBold()) {selectKey(key, borderRadius);}}
            if (primaryCode == -150) {if (Variables.isFraktur()) {selectKey(key, borderRadius);}}
            if (primaryCode == -151) {if (Variables.isFrakturBold()) {selectKey(key, borderRadius);}}
            if (primaryCode == -152) {if (Variables.isSans()) {selectKey(key, borderRadius);}}
            if (primaryCode == -153) {if (Variables.isMonospace()) {selectKey(key, borderRadius);}}
            if (primaryCode == -154) {if (Variables.isDoublestruck()) {selectKey(key, borderRadius);}}
            if (primaryCode == -155) {if (Variables.isEnsquare()) {selectKey(key, borderRadius);}}
            if (primaryCode == -156) {if (Variables.isCircularStampLetters()) {selectKey(key, borderRadius);}}
            if (primaryCode == -157) {if (Variables.isRectangularStampLetters()) {selectKey(key, borderRadius);}}
            if (primaryCode == -158) {if (Variables.isSmallCaps()) {selectKey(key, borderRadius);}}
            if (primaryCode == -159) {if (Variables.isParentheses()) {selectKey(key, borderRadius);}}
            if (primaryCode == -160) {if (Variables.isEncircle()) {selectKey(key, borderRadius);}}
            if (primaryCode == -161) {if (Variables.isReflected()) {selectKey(key, borderRadius);}}
            if (primaryCode == -162) {if (Variables.isCaps()) {selectKey(key, borderRadius);}}

            mPaint.setTextAlign(Paint.Align.CENTER);

            if (getCustomKeyboard().title != null && getCustomKeyboard().title.equals("Calculator")) continue;

            if (key.popupCharacters != null
                && key.codes != null
                && primaryCode != -1
                && primaryCode !=  7
                && primaryCode != 10
                && primaryCode != 32
                && sharedPreferences.getBoolean("hints", true)
            ) {
                canvas.save();
                mPaint.setTextSize(hintFontSize);
                if (key.popupCharacters.length() >= 1
                &&  sharedPreferences.getBoolean("hint1", true)
                && !sharedPreferences.getBoolean("hint2", false)
                && !sharedPreferences.getBoolean("hint3", false)
                && !sharedPreferences.getBoolean("hint4", false)
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