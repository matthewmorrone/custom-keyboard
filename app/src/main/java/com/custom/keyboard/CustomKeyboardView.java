package com.custom.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

import java.util.List;

public class CustomKeyboardView extends KeyboardView {

    Canvas canvas;
    Context kcontext;
    SharedPreferences sharedPreferences;
    Paint mPaint = new Paint();
    
    private String selected = "#80FFFFFF";
    
    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.parseColor(selected));
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPaint.setColor(Color.parseColor(selected));
    }
    
    public CustomKeyboard getCustomKeyboard() {
        return (CustomKeyboard)getKeyboard();
    }
    
    
    @Override
    protected boolean onLongPress(Key key) {
        /*
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(-100, null);
            return true;
        }
        */
        if (key.codes[0] == -192
        ||  key.codes[0] == -300) {
            return super.onLongPress(key);
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

    public void selectKey(Key key, int corner) {
        int theme = Integer.parseInt(sharedPreferences.getString("theme", "1"));
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
        int theme = Integer.parseInt(sharedPreferences.getString("theme", "1"));
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

    @Override
    public void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.canvas = canvas;

        kcontext = getContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(kcontext);

        boolean borders = sharedPreferences.getBoolean("borders", false);
        boolean padding = sharedPreferences.getBoolean("padding", false);
        boolean corners = sharedPreferences.getBoolean("corners", false);
        boolean keyback = sharedPreferences.getBoolean("keyback", false);
        int border = 2;  // borders || padding ? 2 : 0;
        int corner = 16; // corners ? 16 : 0;

        List<Key> keys = getKeyboard().getKeys();
        for (Key key : keys) {

            // !keyback corners !padding borders
            if (borders) {
                canvas.save();
                canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
                canvas.clipOutRect(key.x+border, key.y+border, key.x+key.width-border, key.y+key.height-border);
                mPaint.setColor(Color.parseColor("#ffffffff"));
                canvas.drawRect(key.x, key.y, key.x+key.width, key.y+key.height, mPaint);
                canvas.restore();
            }

            // !keyback !corners padding borders
            if (padding) {
                canvas.save();
                canvas.clipRect(key.x+(border*2), key.y+(border*2), key.x+key.width-(border*2), key.y+key.height-(border*2));
                canvas.clipOutRect(key.x+(border*4), key.y+(border*4), key.x+key.width-(border*4), key.y+key.height-(border*4));
                mPaint.setColor(Color.parseColor("#ffffffff"));
                canvas.drawRect(key.x+(border*2), key.y+(border*2), key.x+key.width-(border*2), key.y+key.height-(border*2), mPaint);
                canvas.restore();
            }

            // !keyback corners padding borders
            if (corners) {
                canvas.save();
                canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
                canvas.clipOutRect(key.x+(border*4)+corner, key.y+(border*4)+corner, key.x+key.width-(border*4)-corner, key.y+key.height-(border*4)-corner);
                mPaint.setColor(Color.parseColor("#ffffffff"));
                canvas.drawRoundRect(key.x+(border*2), key.y+(border*2), key.x+key.width-(border*2), key.y+key.height-(border*2), corner, corner, mPaint);
                mPaint.setColor(Color.parseColor("#ff000000"));
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

            /*
            border = 2;
            canvas.save();
            mPaint.setColor(Color.parseColor("#ff000000"));
            mPaint.setColor(Color.parseColor(selected));
            mPaint.setColor(Color.parseColor("#ffffffff"));
            canvas.clipRect(key.x, key.y, key.x+key.width, key.y+key.height);
            canvas.clipOutRect(key.x, key.y, key.x+key.width, key.y+key.height);
            canvas.clipOutRect(key.x+(border*4)+corner, key.y+(border*4)+corner, key.x+key.width-(border*4)-corner, key.y+key.height-(border*4)-corner);
            canvas.drawRect(key.x,            key.y,            key.x+key.width,            key.y+key.height,            mPaint);
            canvas.drawRect(key.x+(border*2), key.y+(border*2), key.x+key.width-(border*2), key.y+key.height-(border*2), mPaint);
            canvas.drawRect(key.x+(border*4), key.y+(border*4), key.x+key.width-(border*4), key.y+key.height-(border*4), mPaint);
            canvas.drawRect(key.x+(border*8), key.y+(border*8), key.x+key.width-(border*8), key.y+key.height-(border*8), mPaint);
            canvas.drawRoundRect(key.x,            key.y,            key.x+key.width,            key.y+key.height,            corner, corner, mPaint);
            canvas.drawRoundRect(key.x+(border*2), key.y+(border*2), key.x+key.width-(border*2), key.y+key.height-(border*2), corner, corner, mPaint);
            canvas.drawRoundRect(key.x+(border*4), key.y+(border*4), key.x+key.width-(border*4), key.y+key.height-(border*4), corner, corner, mPaint);
            canvas.drawRoundRect(key.x+(border*8), key.y+(border*8), key.x+key.width-(border*8), key.y+key.height-(border*8), corner, corner, mPaint);
            canvas.restore();
            */

            // if (key.codes == null) {continue;}

            if (key.codes[0] == -1) {
                if (Variables.isShift()) {
                    selectKey(key, corner);
                }
                if (getKeyboard().isShifted()) {
                    drawable(key, R.drawable.ic_shift_lock, 35);
                }
            }

            if (key.codes[0] == -12) {if (Variables.isBold())      {selectKey(key, corner);}}
            if (key.codes[0] == -13) {if (Variables.isItalic())    {selectKey(key, corner);}}
            if (key.codes[0] == -76) {if (Variables.isSelect())    {selectKey(key, corner);}}
            if (key.codes[0] == -35) {if (Variables.is119808())    {selectKey(key, corner);}}
            if (key.codes[0] == -36) {if (Variables.is119860())    {selectKey(key, corner);}}
            if (key.codes[0] == -37) {if (Variables.is119912())    {selectKey(key, corner);}}
            if (key.codes[0] == -38) {if (Variables.is119964())    {selectKey(key, corner);}}
            if (key.codes[0] == -39) {if (Variables.is120016())    {selectKey(key, corner);}}
            if (key.codes[0] == -40) {if (Variables.is120068())    {selectKey(key, corner);}}
            if (key.codes[0] == -41) {if (Variables.is120120())    {selectKey(key, corner);}}
            if (key.codes[0] == -42) {if (Variables.is120172())    {selectKey(key, corner);}}
            if (key.codes[0] == -43) {if (Variables.is120224())    {selectKey(key, corner);}}
            if (key.codes[0] == -44) {if (Variables.is120276())    {selectKey(key, corner);}}
            if (key.codes[0] == -45) {if (Variables.is120328())    {selectKey(key, corner);}}
            if (key.codes[0] == -46) {if (Variables.is120380())    {selectKey(key, corner);}}
            if (key.codes[0] == -47) {if (Variables.is120432())    {selectKey(key, corner);}}
            if (key.codes[0] == -50) {if (Variables.isReflected()) {selectKey(key, corner);}}
            if (key.codes[0] == -57) {if (Variables.isCaps())      {selectKey(key, corner);}}
            if (key.codes[0] == -68) {if (Variables.is127280())    {selectKey(key, corner);}}
            if (key.codes[0] == -69) {if (Variables.is127312())    {selectKey(key, corner);}}
            if (key.codes[0] == -70) {if (Variables.is127344())    {selectKey(key, corner);}}
            if (key.codes[0] == -71) {if (Variables.is127462())    {selectKey(key, corner);}}
            if (key.codes[0] == -72) {if (Variables.is009372())    {selectKey(key, corner);}}
            if (key.codes[0] == -66) {if (Variables.is009398())    {selectKey(key, corner);}}

            mPaint.setTextAlign(Paint.Align.CENTER);
            if (key.popupCharacters != null 
            && key.codes != null
            && key.codes[0] != -1
            && key.codes[0] !=  7
            && key.codes[0] != 10
            && key.codes[0] != 32
            && sharedPreferences.getBoolean("hints", true)
            ) {
                canvas.save();
                if (getCustomKeyboard().title.contains("Shift")) {
                    continue;
                }
                if (key.popupCharacters.length() >= 1 
                &&   sharedPreferences.getBoolean("hint1", true)
                &&  !sharedPreferences.getBoolean("hint2", false)
                &&  !sharedPreferences.getBoolean("hint3", false)
                &&  !sharedPreferences.getBoolean("hint4", false)
                ) {
                    mPaint.setTextSize(32);
                    mPaint.setColor(Color.parseColor("#ddffffff"));
                    canvas.drawText(((getKeyboard().isShifted())
                        ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase()
                        : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+(key.width/2), key.y+36, mPaint);
                }

                else {
                    mPaint.setTextSize(28);
                    mPaint.setColor(Color.parseColor("#bbffffff"));
                    if (key.popupCharacters.length() > 0 && sharedPreferences.getBoolean("hint1", false)) {
                        canvas.drawText(((getKeyboard().isShifted())
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
                }
                canvas.restore();
            }
        }
    }
}