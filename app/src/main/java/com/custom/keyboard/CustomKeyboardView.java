package com.custom.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.preference.PreferenceManager;
import androidx.core.content.ContextCompat;
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
        if (key.codes[0] == 32) {

        }
        if (key.codes[0] == -192
        ||  key.codes[0] == -300
        ||  key.codes[0] ==   10) {
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

    public void capsHack() {
        List<Key> keys = getKeyboard().getKeys();
        for (Key key : keys) {
            if (key.codes[0] == -1) {
                if (Variables.isShift()) {
                    selectKey(key, corner);
                }
                if (getKeyboard().isShifted()) {
                    drawable(key, R.drawable.ic_shift_lock_, 24);
                }
            }
        }
    }

    int border = 2;  // borders || padding ? 2 : 0;
    int corner = 16; // corners ? 16 : 0;

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

        int fg = sharedPreferences.getInt("fg", -1677216);
        int bg = sharedPreferences.getInt("bg", -1);
        String foreground = "#"+Integer.toHexString(fg);
        String background = "#"+Integer.toHexString(bg);


        boolean borders = sharedPreferences.getBoolean("borders", false);
        boolean padding = sharedPreferences.getBoolean("padding", false);
        boolean corners = sharedPreferences.getBoolean("corners", false);
        boolean keyback = sharedPreferences.getBoolean("keyback", false);
        String iconId;
        int imageId;

        List<Key> keys = getKeyboard().getKeys();
        for (Key key : keys) {

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
                    selectKey(key, corner);
                    drawable(key, R.drawable.ic_shift_lock_, 35);
                }
                if (getKeyboard().isShifted()) {
                    drawable(key, R.drawable.ic_shift_lock_, 35);
                }
            }

            if (key.codes[0] == 32) {
                key.text = sharedPreferences.getString(Constants.SPACEBARTEXT, " ");
                key.popupCharacters = sharedPreferences.getString(Constants.SPACEBARPOPUP, getContext().getString(R.string.popup_first));

                iconId = sharedPreferences.getString(Constants.SPACEBARICON, "ic_space");
                if (!iconId.isEmpty()) {
                    imageId = getResources().getIdentifier(iconId, "drawable", getContext().getPackageName());
                    key.icon = ContextCompat.getDrawable(getContext(), imageId);
                }
                else {
                    key.label = sharedPreferences.getString(Constants.SPACEBARLABEL, ""); //  getContext().getString(R.string.popup_first)
                }
            }
            if (key.codes[0] == 7) {
                key.text = sharedPreferences.getString(Constants.TABKEYTEXT, "\t");
                key.popupCharacters = sharedPreferences.getString(Constants.TABKEYPOPUP, getContext().getString(R.string.popup_second));

                iconId = sharedPreferences.getString(Constants.TABKEYICON, "ic_tab");
                if (!iconId.isEmpty()) {
                    imageId = getResources().getIdentifier(iconId, "drawable", getContext().getPackageName());
                    key.icon = ContextCompat.getDrawable(getContext(), imageId);
                }
                else {
                    key.label = sharedPreferences.getString(Constants.TABKEYLABEL, ""); // getContext().getString(R.string.popup_second)
                }
            }
            if (key.codes[0] == 10) {
                key.text = sharedPreferences.getString(Constants.ENTERKEYTEXT, "\n");
                key.popupCharacters = sharedPreferences.getString(Constants.ENTERKEYPOPUP, getContext().getString(R.string.popup_third));

                iconId = sharedPreferences.getString(Constants.ENTERKEYICON, "ic_enter");
                if (!iconId.isEmpty()) {
                    imageId = getResources().getIdentifier(iconId, "drawable", getContext().getPackageName());
                    key.icon = ContextCompat.getDrawable(getContext(), imageId);
                }
                else {
                    key.label = sharedPreferences.getString(Constants.ENTERKEYLABEL, ""); // getContext().getString(R.string.popup_third)
                }
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


            if (key.codes[0] == -12) {if (Variables.isBold())      {selectKey(key, corner);}}
            if (key.codes[0] == -13) {if (Variables.isItalic())    {selectKey(key, corner);}}
            if (key.codes[0] == -76) {if (Variables.isSelect())    {selectKey(key, corner);}}

            mPaint.setTextAlign(Paint.Align.CENTER);
            if (key.popupCharacters != null 
            && key.codes != null
            && key.codes[0] != -1
            && key.codes[0] !=  7
            && key.codes[0] != 10
            && key.codes[0] != 32
            && !(key.codes[0] <= -500 && key.codes[0] >= -512)
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
                    mPaint.setColor(Color.parseColor(foreground));
                    canvas.drawText(((getKeyboard().isShifted())
                        ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase()
                        : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+(key.width/2), key.y+(key.height/4), mPaint);
                }

                else {
                    mPaint.setTextSize(28);
                    mPaint.setColor(Color.parseColor(foreground));

                    if (key.popupCharacters.length() > 0 && sharedPreferences.getBoolean("hint1", false)) {
                        canvas.drawText(((getKeyboard().isShifted())
                             ? String.valueOf(key.popupCharacters.charAt(0)).toUpperCase()
                             : String.valueOf(key.popupCharacters.charAt(0)).toLowerCase()), key.x+20,             key.y+15,              mPaint);
                    }
                    if (key.popupCharacters.length() > 1 && sharedPreferences.getBoolean("hint2", false)) {
                        canvas.drawText((getKeyboard().isShifted()
                             ? String.valueOf(key.popupCharacters.charAt(1)).toUpperCase()
                             : String.valueOf(key.popupCharacters.charAt(1)).toLowerCase()), key.x+(key.width-20), key.y+15,              mPaint);
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