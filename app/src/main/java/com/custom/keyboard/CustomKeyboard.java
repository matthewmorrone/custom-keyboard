package com.custom.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;

public class CustomKeyboard extends Keyboard implements Comparable<CustomKeyboard> {
    
    private static short rowNumber = 6;
    
    public String key;
    String title;
    String label;
    int order = 1024;
    
    CustomKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }
    
    CustomKeyboard(Context context, int xmlLayoutResId, String title) {
        super(context, xmlLayoutResId);
        this.key = title.toLowerCase();
        this.title = title;
    }    
    
    CustomKeyboard(Context context, int xmlLayoutResId, String title, String label) {
        super(context, xmlLayoutResId);
        this.key = title.toLowerCase();
        this.title = title;
        this.label = label;
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
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String key, String title, String label) {
        super(context, xmlLayoutResId);
        this.key = key;
        this.title = title;
        this.label = label;
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
    }

    @Override
    public int compareTo(CustomKeyboard kb) {
        if (this.order != kb.order) {
            return this.order - kb.order;
        }
        return this.title.compareTo(kb.title);
    }
    
    void setRowNumber(short number) {
        rowNumber = number;
    }
    
    void changeKeyHeight(double height_modifier){
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
