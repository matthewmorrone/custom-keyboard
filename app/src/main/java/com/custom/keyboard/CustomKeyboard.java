package com.custom.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.preference.PreferenceManager;
import com.custom.keyboard.CustomInputMethodService.Category;

public class CustomKeyboard extends Keyboard implements Comparable<CustomKeyboard> {
    
    private static short rowNumber = 6;
    
    public String key;
    String title;
    String label;
    int order = 1024;
    Category category = Category.Misc;
    SharedPreferences sharedPreferences;
    int layoutId;

    CustomKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
        layoutId = xmlLayoutResId;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    CustomKeyboard(Context context, int xmlLayoutResId, String title) {
        super(context, xmlLayoutResId);
        this.key = Util.toLowerCase(title);
        this.title = Util.toTitleCase(title);
        layoutId = xmlLayoutResId;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }    
    
    CustomKeyboard(Context context, int xmlLayoutResId, String title, String label) {
        super(context, xmlLayoutResId);
        this.key = Util.toLowerCase(title);
        this.title = Util.toTitleCase(title);
        this.label = Util.toTitleCase(label);
        layoutId = xmlLayoutResId;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String title, int order) {
        super(context, xmlLayoutResId);
        this.key = Util.toLowerCase(title);
        this.title = Util.toTitleCase(title);
        this.label = Util.toTitleCase(title);
        this.order = order;
        if (this.order < 0) {
            this.order = 1024 + this.order;
        }
        layoutId = xmlLayoutResId;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String title, String label, int order) {
        super(context, xmlLayoutResId);
        this.key = Util.toLowerCase(title);
        this.title = Util.toTitleCase(title);
        this.label = Util.toTitleCase(title);
        this.order = order;
        if (this.order < 0) {
            this.order = 1024 + this.order;
        }
        layoutId = xmlLayoutResId;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String key, String title, String label) {
        super(context, xmlLayoutResId);
        this.key = Util.toLowerCase(title);
        this.title = Util.toTitleCase(title);
        this.label = Util.toTitleCase(title);
        layoutId = xmlLayoutResId;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String key, String title, String label, int order) {
        super(context, xmlLayoutResId);
        this.key = Util.toLowerCase(title);
        this.title = Util.toTitleCase(title);
        this.label = Util.toTitleCase(title);
        this.order = order;
        if (this.order < 0) {
            this.order = 1024 + this.order;
        }
        layoutId = xmlLayoutResId;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
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

    public int getOrder() {
        return order;
    }

    public CustomKeyboard setOrder(int order) {
        this.order = order;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public CustomKeyboard setCategory(Category category) {
        this.category = category;
        return this;
    }

    void setRowNumber(short number) {
        rowNumber = number;
    }

    @Override
    public int getHeight() {
        return getKeyHeight() * rowNumber;
    }
    
    public void setKeyHeight(int height) {
        super.setKeyHeight(height);
    }

}
