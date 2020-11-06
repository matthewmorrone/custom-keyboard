package com.custom.keyboard.emoticon;

import android.graphics.Paint;

// import com.custom.keyboard.Util;

import java.io.Serializable;

public class Emoticon implements Serializable {
    private static final long serialVersionUID = 1L;
    private String emoticon;

    private Emoticon() {
    }

    public static Emoticon fromCodePoint(int codePoint) {
        Emoticon emoticon = new Emoticon();
        emoticon.emoticon = newString(codePoint);
        return emoticon;
    }

    public static Emoticon fromCodePoints(int ...codepoints) {
        Emoticon emoticon = new Emoticon();
        emoticon.emoticon = new String(codepoints, 0, codepoints.length);
        return emoticon;
    }

    public boolean isRenderable() {
        return new Paint().measureText(emoticon) > 7;
    }

    public Emoticon(String emoticon) {
        this.emoticon = emoticon;
    }

    public String getEmoticon() {
        return emoticon;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Emoticon && emoticon.equals(((Emoticon)o).emoticon);
    }

    @Override
    public int hashCode() {
        return emoticon.hashCode();
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
