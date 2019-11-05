package com.custom.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.preference.PreferenceManager;

public class CustomKeyboard extends Keyboard implements Comparable<CustomKeyboard> {
    
    private static short rowNumber = 6;
    
    public String key;
    String title;
    String label;
    int order = 1024;
    Category category;
    SharedPreferences sharedPreferences;

    CustomKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    CustomKeyboard(Context context, int xmlLayoutResId, String title) {
        super(context, xmlLayoutResId);
        this.key = title.toLowerCase();
        this.title = title;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }    
    
    CustomKeyboard(Context context, int xmlLayoutResId, String title, String label) {
        super(context, xmlLayoutResId);
        this.key = title.toLowerCase();
        this.title = title;
        this.label = label;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String title, int order) {
        super(context, xmlLayoutResId);
        this.key = title.toLowerCase();
        this.title = title;
        this.label = title;
        this.order = order;
        if (this.order < 0) {
            this.order = 1024 + this.order;
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String title, String label, int order) {
        super(context, xmlLayoutResId);
        this.key = title.toLowerCase();
        this.title = title;
        this.label = label;
        this.order = order;
        if (this.order < 0) {
            this.order = 1024 + this.order;
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String key, String title, String label) {
        super(context, xmlLayoutResId);
        this.key = key;
        this.title = title;
        this.label = label;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String key, String title, String label, int order) {
        super(context, xmlLayoutResId);
        this.key = key;
        this.title = title;
        this.label = label;
        this.order = order;
        if (this.order < 0) {
            this.order = 1024 + this.order;
        }
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
    
    void modifyKeyHeight(int keyHeight) {
        int height = 0;
        for(Keyboard.Key key : getKeys()) {
            key.y += (keyHeight - key.height);
            key.height = keyHeight;
            
        }
        setKeyHeight(height);
        getNearestKeys(0, 0); //somehow adding this fixed a weird bug where bottom row keys could not be pressed if keyboard height is too tall.. from the Keyboard source code seems like calling this will recalculate some values used in keypress detection calculation
    }
    
    void changeKeyHeight(double height_modifier) {
        int height = 0;
        for(Keyboard.Key key : getKeys()) {
            key.height *= height_modifier;
            key.y *= height_modifier;
            height = key.height;
        }
        setKeyHeight(height);
        getNearestKeys(0, 0); //somehow adding this fixed a weird bug where bottom row keys could not be pressed if keyboard height is too tall.. from the Keyboard source code seems like calling this will recalculate some values used in keypress detection calculation
    }

    @Override
    public int getHeight() {
        return getKeyHeight() * rowNumber;
    }
    
    public void setKeyHeight(int height) {
        super.setKeyHeight(height);
    }
}
