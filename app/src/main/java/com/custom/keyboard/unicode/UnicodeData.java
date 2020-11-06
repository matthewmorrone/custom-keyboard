package com.custom.keyboard.unicode;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UnicodeData {
    static int defaultMax = (int)Math.pow(2, 10);
    public static Unicode[] stringToUnicodes(String input) {
        Unicode[] data = new Unicode[input.length()];
        for(int i = 0; i < input.length(); i++) {
            data[i] = Unicode.fromCodePoint(input.charAt(i));
        }
        return data;
    }
    public static Unicode[] getData() {
        Unicode[] data = new Unicode[defaultMax];
        for(int i = 0; i < defaultMax; i++) {
            data[i] = Unicode.fromCodePoint(i);
        }
        return data;
    }
    public static Unicode[] getCount(int size) {
        Unicode[] data = new Unicode[size];
        for(int i = 0; i < size; i++) {
            data[i] = Unicode.fromCodePoint(i);
        }
        return data;
    }
    public static Unicode[] getCount(int start, int size) {
        Unicode[] data = new Unicode[size];
        for(int i = 0; i < size; i++) {
            data[i] = Unicode.fromCodePoint(start + i);
        }
        return data;
    }
    public static Unicode[] getRange(int max) {
        Unicode[] data = new Unicode[max];
        for(int i = 0; i < max; i++) {
            data[i] = Unicode.fromCodePoint(i);
        }
        return data;
    }
    public static Unicode[] getRange(int min, int max) {
        Unicode[] data = new Unicode[max-min];
        System.out.println(min+" "+max+" "+(max-min));
        for(int i = min; i < max; i++) {
            // System.out.println(i);
            data[i-min] = Unicode.fromCodePoint(i);
        }
        return data;
    }
}

