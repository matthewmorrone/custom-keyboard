package com.vlath.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;

public class LatinKeyboard extends Keyboard {
    
    // private Key mEnterKey;
    // private Key mSpaceKey;
    private static short rowNumber = 6;
    
    public String name;    
    public String label;
    
    // private Key mModeChangeKey;

    // private Key mLanguageSwitchKey;

    // private Key mSavedModeChangeKey;

    // private Key mSavedLanguageSwitchKey;
    
    // private Key k1;
    // private Key k2;
    // private Key k3;
    // private Key k4;
    // private Key k5;
    // private Key k6;
    // private Key k7;
    // private Key k8;
    
    LatinKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }
    
    LatinKeyboard(Context context, int xmlLayoutResId, String name) {
        super(context, xmlLayoutResId);
        this.name = name;
    }    
    
    LatinKeyboard(Context context, int xmlLayoutResId, String name, String label) {
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
