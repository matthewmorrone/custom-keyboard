package com.custom.keyboard.unicode;

import android.graphics.Paint;

import com.custom.keyboard.Util;

import java.io.Serializable;

public class Unicode implements Serializable {
    private static final long serialVersionUID = 1L;
    private String unicode;
    private static boolean renderable;

    private Unicode() {
    }

    public static Unicode fromCodePoint(int codePoint) {
        Unicode unicode = new Unicode();
        unicode.unicode = newString(codePoint);
        return unicode;
    }

    public static Unicode fromCodePoints(int ...codepoints) {
        Unicode unicode = new Unicode();
        unicode.unicode = new String(codepoints, 0, codepoints.length);
        return unicode;
    }

    public boolean isRenderable() {
        return new Paint().measureText(unicode) > 7;
    }

    public Unicode(String unicode) {
        this.unicode = unicode;
    }

    public String getUnicode() {
        return unicode;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Unicode && unicode.equals(((Unicode)o).unicode);
    }

    @Override
    public int hashCode() {
        return unicode.hashCode();
    }

    public static String newString(int codePoint) {
        if (Character.charCount(codePoint) == 1) {
            return String.valueOf((char)codePoint);
        }
        else {
            return new String(Character.toChars(codePoint));
        }
    }
}
