package com.custom.keyboard.util;

import android.graphics.Color;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class ColorUtils {

    // color
    public static Color invertColor(Color color) {
        color.red((int)(255-color.red()));
        color.blue((int)(255-color.blue()));
        color.green((int)(255-color.green()));
        return color;
    }
    public static int invertColor(int color) {
        return (0x00FFFFFF - (color | 0xFF000000)) | (color & 0xFF000000);
    }

    public static String toColor(int r, int g, int b) {
        String rs = StringUtils.leftPad(Integer.toHexString(r), 2, "0").toUpperCase();
        String gs = StringUtils.leftPad(Integer.toHexString(g), 2, "0").toUpperCase();
        String bs = StringUtils.leftPad(Integer.toHexString(b), 2, "0").toUpperCase();
        return "#" + rs + gs + bs;
    }
    public static String toColor(int a, int r, int g, int b) {
        String as = StringUtils.leftPad(Integer.toHexString(a), 2, "0").toUpperCase();
        String rs = StringUtils.leftPad(Integer.toHexString(r), 2, "0").toUpperCase();
        String gs = StringUtils.leftPad(Integer.toHexString(g), 2, "0").toUpperCase();
        String bs = StringUtils.leftPad(Integer.toHexString(b), 2, "0").toUpperCase();
        return "#" + as + rs + gs + bs;
    }
    public static Color stringToColor(String value) {
        if (value == null) {
            return Color.valueOf(Color.BLACK);
        }
        try {
            // get color by hex or octal value
            return Color.valueOf(Color.parseColor(value));
        }
        catch (NumberFormatException nfe) {
            // if we can't decode lets try to get it by name
            try {
                // try to get a color by name using reflection
                final Field f = Color.class.getField(value);
                return (Color)f.get(null);
            }
            catch (Exception ce) {
                // if we can't get any color return black
                return Color.valueOf(Color.BLACK);
            }
        }
    }
    public static String colorToString(Color color) {
        return String.format("#%08X", color.toArgb());
    }
    public static int[] fromColor(String color) {
        color = color.toUpperCase();
        String as, rs, gs, bs;
        int ai, ri, gi, bi;
        if (color.length() == 6) {
            as = "FF";
            rs = color.substring(0, 2);
            gs = color.substring(2, 4);
            bs = color.substring(4, 6);
        }
        else if (color.length() == 8) {
            as = color.substring(0, 2);
            rs = color.substring(2, 4);
            bs = color.substring(4, 6);
            gs = color.substring(6, 8);
        }
        else {
            return null;
        }
        ai = Integer.decode("0x" + as);
        ri = Integer.decode("0x" + rs);
        gi = Integer.decode("0x" + gs);
        bi = Integer.decode("0x" + bs);
        return new int[]{ai, ri, gi, bi};
    }

}
