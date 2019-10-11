package com.custom.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;

public class CustomKeyboard extends Keyboard {
    
    private static short rowNumber = 6;
    
    public String name;    
    public String label;
    
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
