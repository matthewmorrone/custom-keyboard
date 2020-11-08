package com.custom.keyboard.unicode;

import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Unicode implements Serializable {
    private static final long serialVersionUID = 1L;
    public int codePoint;
    public String unicode;
    public String hex = "";
    public String description = "";

    public Unicode() {
    }

    public Unicode(String unicode) {
        this.unicode = unicode;
    }

    public static Unicode fromCodePoint(int codePoint) {
        Unicode unicode = new Unicode();
        unicode.codePoint = codePoint;
        unicode.unicode = newString(codePoint);
        return unicode;
    }

    public static Unicode fromCodePoint(int codePoint, String description) {
        Unicode unicode = new Unicode();
        unicode.codePoint = codePoint;
        unicode.unicode = newString(codePoint);
        unicode.description = description;
        unicode.hex = Integer.toHexString(codePoint);
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
        if (Character.charCount(codePoint) == 1) return String.valueOf((char)codePoint);
        else return new String(Character.toChars(codePoint));
    }

    @NonNull
    @Override
    public String toString() {
        return description != null ? description : String.valueOf(codePoint);
    }
}
