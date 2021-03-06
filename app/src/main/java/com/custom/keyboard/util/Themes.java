package com.custom.keyboard.util;

// import android.graphics.Color;

import com.custom.keyboard.R;
import com.custom.keyboard.util.Util;

public class Themes {

    /*
    int bg = sharedPreferences.getInt(background_color, 0xFF000000);
    int fg = sharedPreferences.getInt(foreground_color, 0xFFFFFFFF);

    Color background = Color.valueOf(bg);
    Color foreground = Color.valueOf(fg);


    */


    //     if (background != null) {
    //         background.setColor(bg);
    //         if (background.colorView != null) {
    //             background.colorView.setBackgroundColor(bg);
    //             background.colorView.invalidate();
    //         }
    //         background.callChangeListener(bg);
    //     }
    //
    //     if (foreground != null) {
    //         foreground.setColor(fg);
    //         if (foreground.colorView != null) {
    //             foreground.colorView.setBackgroundColor(fg);
    //             foreground.colorView.invalidate();
    //         }
    //         background.callChangeListener(fg);
    //     }
    //
    //     synchronized(background) {
    //         background.notify();
    //     }
    //     synchronized(foreground) {
    //         foreground.notify();
    //     }
    // }

    //     ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(mDefaultFilter);
    //     Paint mPaint = new Paint();
    //     mPaint.setColorFilter(colorFilter);
    //
    //     System.out.println(Arrays.toString(mDefaultFilter));
    //     if (inverted) {
    //         mDefaultFilter[0] = -mDefaultFilter[0];
    //         mDefaultFilter[6] = -mDefaultFilter[6];
    //         mDefaultFilter[12] = -mDefaultFilter[12];
    //     }
    //     System.out.println(Arrays.toString(mDefaultFilter));
    //
    //     // float transparency = sharedPreferences.getInt("transparency", 100) / 100f;
    //     if (kv != null) {
    //         float transparency = sharedPreferences.getInt("transparency", 100) / 100f;
    //         kv.setBackgroundColor(Color.argb(transparency, 0, 0, 0));
    //
    //         // if (inverted) {
    //         //     bgColor.red((int)(255-bgColor.red()));
    //         //     bgColor.green((int)(255-bgColor.green()));
    //         //     bgColor.blue((int)(255-bgColor.blue()));
    //         // }
    //         // kv.setBackgroundColor(Color.argb(1, bgColor.red(), bgColor.green(), bgColor.blue()));
    //     }

    // public static float[] invertColorMatrix(float[] colorArray) {
    //     colorArray[ 0] = -colorArray[ 0];
    //     colorArray[ 6] = -colorArray[ 6];
    //     colorArray[12] = -colorArray[12];
    //     colorArray[18] = -colorArray[18];
    //     return colorArray;
    // }

    // public static String displayColorAsHex(float[] colorArray) {
    //     return "0xff"+Integer.toString((int)colorArray[4], 16)+
    //     Integer.toString((int)colorArray[9], 16)+
    //     Integer.toString((int)colorArray[14], 16);
    // }
    //
    // public static String displayColorAsHex(int color) {
    //     return "0xff"+Integer.toString(color, 16);
    // }
    //


    public static int randomBackground() {
        return RandomUtils.choose(new int[] {
            R.drawable.background_1,
            R.drawable.background_2,
            R.drawable.background_3,
            R.drawable.background_4,
            R.drawable.background_5,
            R.drawable.background_6,
            R.drawable.background_7,
            R.drawable.background_8,
            R.drawable.background_9,
            R.drawable.background_10,
            R.drawable.background_11,
            R.drawable.background_12,
            R.drawable.background_13,
            R.drawable.background_14,
            R.drawable.background_15,
            R.drawable.background_16
        });
    }

    public static String extractBackgroundColor(float[] colorArray) {
        float[] argbF = {colorArray[19]*255, colorArray[4], colorArray[9], colorArray[14]};
        int[] argbI = {(int)argbF[0], (int)argbF[1], (int)argbF[2], (int)argbF[3]};
        String[] argbS = {
            String.format("%02x", argbI[0]), //Integer.toString(argbI[0], 16)),
            String.format("%02x", argbI[1]), //Integer.toString(argbI[1], 16)),
            String.format("%02x", argbI[2]), //Integer.toString(argbI[2], 16)),
            String.format("%02x", argbI[3]) //Integer.toString(argbI[3], 16)),
        };
        String argb = argbS[0]+argbS[1]+argbS[2]+argbS[3];
        return argb;
    }
    public static String extractForegroundColor(float[] colorArray) {
        return colorArray[0] > 0 ? "ffffffff" : "ff000000";
    }

    // String sPositiveColorBg = "#000000"
    // String sPositiveColorFg = "#ffffff"
    public static float[] sPositiveColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f,   0, // red
        +0.0f, +1.0f, +0.0f, +0.0f,   0, // green
        +0.0f, +0.0f, +1.0f, +0.0f,   0, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    // String sNegativeColorBg = "#ffffff"
    // String sNegativeColorFg = "#000000"
    public static float[] sNegativeColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f, 255, // red
        +0.0f, -1.0f, +0.0f, +0.0f, 255, // green
        +0.0f, +0.0f, -1.0f, +0.0f, 255, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };

    // #2980b9
    public static float[] sBlueWhiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f,  41, // red
        +0.0f, +1.0f, +0.0f, +0.0f, 128, // green
        +0.0f, +0.0f, +1.0f, +0.0f, 185, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sBlueBlackColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f,  41, // red
        +0.0f, -1.0f, +0.0f, +0.0f, 128, // green
        +0.0f, +0.0f, -1.0f, +0.0f, 185, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   0  // alpha
    };

    // #80b929
    public static float[] sGreenWhiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f, 128, // red
        +0.0f, +1.0f, +0.0f, +0.0f, 185, // green
        +0.0f, +0.0f, +1.0f, +0.0f,  41, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sGreenBlackColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f, 128, // red
        +0.0f, -1.0f, +0.0f, +0.0f, 185, // green
        +0.0f, +0.0f, -1.0f, +0.0f,  41, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   0  // alpha
    };

    // #c0392b
    public static float[] sRedWhiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f, 192, // red
        +0.0f, +1.0f, +0.0f, +0.0f,  57, // green
        +0.0f, +0.0f, +1.0f, +0.0f,  43, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sRedBlackColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f, 192, // red
        +0.0f, -1.0f, +0.0f, +0.0f,  57, // green
        +0.0f, +0.0f, -1.0f, +0.0f,  43, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   0  // alpha
    };

    // #2bb1c0
    public static float[] sCyanWhiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f,  43, // red
        +0.0f, +1.0f, +0.0f, +0.0f, 177, // green
        +0.0f, +0.0f, +1.0f, +0.0f, 192, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sCyanBlackColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f,  43, // red
        +0.0f, -1.0f, +0.0f, +0.0f, 177, // green
        +0.0f, +0.0f, -1.0f, +0.0f, 192, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   0  // alpha
    };

    // #b92980
    public static float[] sMagentaWhiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f, 185, // red
        +0.0f, +1.0f, +0.0f, +0.0f,  41, // green
        +0.0f, +0.0f, +1.0f, +0.0f, 128, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sMagentaBlackColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f, 185, // red
        +0.0f, -1.0f, +0.0f, +0.0f,  41, // green
        +0.0f, +0.0f, -1.0f, +0.0f, 128, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   0  // alpha
    };

    // #b9ab29
    public static float[] sYellowWhiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f, 185, // red
        +0.0f, +1.0f, +0.0f, +0.0f, 171, // green
        +0.0f, +0.0f, +1.0f, +0.0f,  41, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sYellowBlackColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f, 185, // red
        +0.0f, -1.0f, +0.0f, +0.0f, 171, // green
        +0.0f, +0.0f, -1.0f, +0.0f,  41, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   0  // alpha
    };

    // #982abd
    public static float[] sPurpleWhiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f,  99, // red
        +0.0f, +1.0f, +0.0f, +0.0f,  41, // green
        +0.0f, +0.0f, +1.0f, +0.0f, 185, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sPurpleBlackColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f,  99, // red
        +0.0f, -1.0f, +0.0f, +0.0f,  41, // green
        +0.0f, +0.0f, -1.0f, +0.0f, 185, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   0  // alpha
    };

    // #e63eb9
    public static float[] sPinkWhiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f, 230, // red
        +0.0f, +1.0f, +0.0f, +0.0f,  62, // green
        +0.0f, +0.0f, +1.0f, +0.0f, 185, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sPinkBlackColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f, 230, // red
        +0.0f, -1.0f, +0.0f, +0.0f,  62, // green
        +0.0f, +0.0f, -1.0f, +0.0f, 185, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   0  // alpha
    };

    // #e67e22
    public static float[] sOrangeWhiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f, 230, // red
        +0.0f, +1.0f, +0.0f, +0.0f, 126, // green
        +0.0f, +0.0f, +1.0f, +0.0f,  34, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sOrangeBlackColorArray = {
        -1.0f, +0.0f, +0.0f, +0.0f, 230, // red
        +0.0f, -1.0f, +0.0f, +0.0f, 126, // green
        +0.0f, +0.0f, -1.0f, +0.0f,  34, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   0  // alpha
    };

    // #37474f
    public static float[] sMaterialLiteColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f,  55, // red
        +0.0f, +1.0f, +0.0f, +0.0f,  71, // green
        +0.0f, +0.0f, +1.0f, +0.0f,  79, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
    public static float[] sMaterialDarkColorArray = {
        +1.0f, +0.0f, +0.0f, +0.0f,  55, // red
        +0.0f, +1.0f, +0.0f, +0.0f,  71, // green
        +0.0f, +0.0f, +1.0f, +0.0f,  79, // blue
        +0.0f, +0.0f, +0.0f, +1.0f,   1  // alpha
    };
}
