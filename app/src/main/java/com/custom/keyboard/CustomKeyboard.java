package com.custom.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
// import java.util.*;

public class CustomKeyboard extends Keyboard implements Comparable<CustomKeyboard> {
    
    private static short rowNumber = 6;
    
    public String name;    
    public String label;
    public int order = Integer.MAX_VALUE;
    
    CustomKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }
    
    CustomKeyboard(Context context, int xmlLayoutResId, String name) {
        super(context, xmlLayoutResId);
        this.name = name;
    }    
    
    CustomKeyboard(Context context, int xmlLayoutResId, String name, String label) {
        super(context, xmlLayoutResId);
        this.name = name;    
        this.label = label;
    }

    CustomKeyboard(Context context, int xmlLayoutResId, String name, String label, int order) {
        super(context, xmlLayoutResId);
        this.name = name;    
        this.label = label;
        this.order = order;
    }

    // @Override
    public int compareTo(CustomKeyboard kb) {
        if (this.order != kb.order) {
            return this.order - kb.order;
        }
        return this.name.compareTo(kb.name);
    }
    
    void setRowNumber(short number) {
        rowNumber = number;
    }
    
    public void changeKeyHeight(double height_modifier){
        int height = 0;
        for(Keyboard.Key key : getKeys()) {
            key.height *= height_modifier;
            key.y *= height_modifier;
            height = key.height;
        }
        setKeyHeight(height);
        getNearestKeys(0, 0); //somehow adding this fixed a weird bug where bottom row keys could not be pressed if keyboard height is too tall.. from the Keyboard source code seems like calling this will recalculate some values used in keypress detection calculation
    }

    /** This piece of code is intended to help us to resize the keyboard at runtime,
     *  thus giving us the opportunity to customize its height. It's a bit tricky though.
     *  And StackOverflow inspired me to be honest.
     * **/

    @Override
    public int getHeight() {
        return getKeyHeight() * rowNumber;
    }
    
    public void setKeyHeight(int height) {
        super.setKeyHeight(height);
    }
}
