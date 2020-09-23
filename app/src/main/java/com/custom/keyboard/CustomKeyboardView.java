package com.custom.keyboard;

import android.content.SharedPreferences;
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
import java.util.Objects;

public class CustomKeyboardView extends KeyboardView {

    Drawable mTransparent = new ColorDrawable(Color.TRANSPARENT);
    Paint mPaint = new Paint();
    Context kcontext;
    SharedPreferences sharedPreferences;
    String selected = "#80FFFFFF";
    String foreground = "#FFFFFFFF";
    String background = "#FF000000";

    Canvas canvas;

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomKeyboard getCustomKeyboard() {
        return (CustomKeyboard)getKeyboard();
    }

    @Override
    protected boolean onLongPress(Key key) {

        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(CustomKeyboard.KEYCODE_OPTIONS, null);
            return true;
        }
        if (key.popupCharacters == null || key.popupCharacters.length() == 0) {
            getOnKeyboardActionListener().onKey(key.codes[0], null);
            return true;
        }
        if (key.popupCharacters != null && key.popupCharacters.length() == 1) {
            if (sharedPreferences.getBoolean("single_hint", true)) {
                getOnKeyboardActionListener().onKey(key.popupCharacters.charAt(0), null);
                return true;
            }
        }
        return super.onLongPress(key);
    }

    public void drawKey(Drawable background, Key key) {
        background.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        background.draw(canvas);
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

        canvas.restore();
    }

    public void selectKey(Key key, int corner) {
        int theme = Integer.parseInt(Objects.requireNonNull(sharedPreferences.getString("theme", "1")));
        String color = theme % 2 == 1 ? selected : selected;

        canvas.save();
        mPaint.setColor(Color.parseColor(color));
        canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
        if (corner > 0) {
            canvas.drawRoundRect(key.x, key.y, key.x+key.width, key.y+key.height, corner, corner, mPaint);
        }
        else {
            canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);
        }
        canvas.restore();
    }

    public void selectKey(Key key) {
        int theme = Integer.parseInt(Objects.requireNonNull(sharedPreferences.getString("theme", "1")));
        String color = theme % 2 == 1 ? selected : selected;

        canvas.save();
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

    int[] repeatable = new int[] {-13, -14, -15, -16, -5, -7};

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(28);
        mPaint.setColor(Color.parseColor(foreground));

        List<Key> keys = getKeyboard().getKeys();

        kcontext = getContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(kcontext);

        this.canvas = canvas;

        boolean borders = sharedPreferences.getBoolean("borders", false);
        boolean padding = sharedPreferences.getBoolean("padding", false);
        boolean corners = sharedPreferences.getBoolean("corners", false);
        boolean keyback = sharedPreferences.getBoolean("keyback", false);
        boolean space = sharedPreferences.getBoolean("space", false);

        int border = 2;  // borders || padding ? 2 : 0;
        int corner = 16; // corners ? 16 : 0;

        for (Key key : keys) {

            if (Util.contains(repeatable, key.codes[0])) {
                key.repeatable = true;
            }

            // !keyback corners !padding borders
            if (borders) {
                canvas.save();
                canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
                canvas.clipOutRect(key.x+border, key.y+border, key.x+key.width-border, key.y+key.height-border);
                mPaint.setColor(Color.parseColor(foreground));
                canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);
                canvas.restore();
            }

            // !keyback !corners padding borders
            if (padding) {
                canvas.save();
                canvas.clipRect(key.x+(border*2), key.y+(border*2), key.x+key.width-(border*2), key.y+key.height-(border*2));
                canvas.clipOutRect(key.x+(border*4), key.y+(border*4), key.x+key.width-(border*4), key.y+key.height-(border*4));
                mPaint.setColor(Color.parseColor(foreground));
                canvas.drawRect(key.x+(border*2), key.y+(border*2), key.x+key.width-(border*2), key.y+key.height-(border*2), mPaint);
                canvas.restore();
            }

            // !keyback corners padding borders
            if (corners) {
                canvas.save();
                canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
                canvas.clipOutRect(key.x+(border*4)+corner, key.y+(border*4)+corner, key.x+key.width-(border*4)-corner, key.y+key.height-(border*4)-corner);
                mPaint.setColor(Color.parseColor(foreground));
                canvas.drawRoundRect(key.x+(border*2), key.y+(border*2), key.x+key.width-(border*2), key.y+key.height-(border*2), corner, corner, mPaint);
                mPaint.setColor(Color.parseColor(background));
                canvas.drawRoundRect(key.x+(border*4), key.y+(border*4), key.x+key.width-(border*4), key.y+key.height-(border*4), corner, corner, mPaint);
                canvas.restore();
            }

            // keyback corners padding !borders
            if (keyback) {
                canvas.save();
                canvas.clipRect(key.x+border, key.y+border, key.x+key.width-border, key.y+key.height-border);
                mPaint.setColor(Color.parseColor(selected));
                canvas.drawRoundRect(key.x+border, key.y+border, key.x+key.width-border, key.y+key.height-border, corner, corner, mPaint);
                canvas.restore();
            }

            if (key.codes[0] == -1) {
                if (Variables.isShift()) {
                    selectKey(key);
                }
                if (getKeyboard().isShifted()) {
                    drawable(key, R.drawable.ic_shift_lock);
                }
            }
            if (key.codes[0] == -11) {
                if (Variables.isSelecting()) {
                    selectKey(key);
                }
            }
            if (key.codes[0] == 32 && sharedPreferences.getBoolean("space", false)) {
                selectKey(key);
            }
            if (Variables.isAnyOn()) {
                if (Variables.isCtrl()) {
                    if (key.codes[0] == -113) {
                        selectKey(key);
                    }
                }
                if (Variables.isAlt()) {
                    if (key.codes[0] == -114) {
                        selectKey(key);
                    }
                }
            }
            else if (key.codes[0] == -113) {
                drawKey(mTransparent, key);
            }
            else if (key.codes[0] == -114) {
                drawKey(mTransparent, key);
            }

            if (key.popupCharacters != null
                && key.codes != null
                && key.codes[0] != -1
                && key.codes[0] !=  7
                && key.codes[0] != 10
                && key.codes[0] != 32
                && sharedPreferences.getBoolean("hints", true)
            ) {
                canvas.save();
                if (key.popupCharacters.length() >= 1 
                &&   sharedPreferences.getBoolean("hint1", true)
                &&  !sharedPreferences.getBoolean("hint2", false)
                &&  !sharedPreferences.getBoolean("hint3", false)
                &&  !sharedPreferences.getBoolean("hint4", false)
                ) {
                    mPaint.setTextSize(32);
                    mPaint.setColor(Color.parseColor(foreground));
                    canvas.drawText(((getKeyboard().isShifted())
                        ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase()
                        : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+key.width/2, key.y+36, mPaint);
                }

                else {
                    mPaint.setTextSize(28);
                    mPaint.setColor(Color.parseColor(foreground));
                    if (key.popupCharacters.length() > 0 && sharedPreferences.getBoolean("hint1", false)) {
                        canvas.drawText(((getKeyboard().isShifted())
                             ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase()
                             : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+20,             key.y+30,            mPaint);
                    }
                    if (key.popupCharacters.length() > 1 && sharedPreferences.getBoolean("hint2", false)) {
                        canvas.drawText((getKeyboard().isShifted()
                             ? String.valueOf(key.popupCharacters.charAt(1)).toUpperCase()
                             : String.valueOf(key.popupCharacters.charAt(1)).toLowerCase()), key.x+(key.width-20), key.y+30,            mPaint);
                    }
                    if (key.popupCharacters.length() > 2 && sharedPreferences.getBoolean("hint3", false)) {
                        canvas.drawText((getKeyboard().isShifted()
                             ? String.valueOf(key.popupCharacters.charAt(2)).toUpperCase()
                             : String.valueOf(key.popupCharacters.charAt(2)).toLowerCase()), key.x+20,             key.y+key.height-15, mPaint);
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