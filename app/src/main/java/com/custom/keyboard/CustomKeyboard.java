package com.custom.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;
import android.preference.PreferenceManager;
import android.view.inputmethod.EditorInfo;

import com.custom.keyboard.util.CustomKey;
import com.custom.keyboard.util.StringUtils;
import com.custom.keyboard.util.Util;

public class CustomKeyboard extends Keyboard implements Comparable<CustomKeyboard> {

    static final int KEYCODE_OPTIONS = -100;
    static final int KEYCODE_LAYOUT_SWITCH = -101;

    SharedPreferences sharedPreferences;
    public int xmlLayoutResId = R.layout.primary;
    public String title;

    int order = 1024;

    public static short rowNumber = 6;

    public CustomKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
        this.xmlLayoutResId = xmlLayoutResId;
    }

    public CustomKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    public int[] getDimensions() {
        return new int[]{this.getMinWidth(), this.getHeight()};
    }

    public CustomKeyboard(Context context, int xmlLayoutResId, String title, String label, int order) {
        super(context, xmlLayoutResId);
        this.xmlLayoutResId = xmlLayoutResId;
        this.title = !StringUtils.containsLowerCase(title) ? title : StringUtils.toTitleCase(title);
        this.order = order;
        if (this.order < 0) {
            this.order = 1024 + this.order;
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getOrder() {
        return order;
    }

    public CustomKeyboard setOrder(int order) {
        this.order = order;
        return this;
    }

    @Override
    public int compareTo(CustomKeyboard kb) {
        if (this.order == 0) return -1024;
        if (sharedPreferences.getBoolean("custom_order", false)) {
            if (this.order != kb.order) {
                return this.order - kb.order;
            }
        }
        return this.title.compareTo(kb.title);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        Key key = new CustomKey(res, parent, x, y, parser);
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

    public int getWidth() {
        return getMinWidth();
    }

    @Override
    public int getHeight() {
        return getKeyHeight() * rowNumber;
    }

    public void setKeyHeight(int height) {
        super.setKeyHeight(height);
    }
}