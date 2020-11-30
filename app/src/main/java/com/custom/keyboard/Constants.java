package com.custom.keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
    public static ArrayList<String> colors = new ArrayList<>(Arrays.asList(
        "White", "Black", "Red", "Green", "Blue", "Cyan", "Magenta", "Yellow"
    ));
    // public static ArrayList<String> topRowKeyDefault = new ArrayList<>(Arrays.asList("-20", "-21", "-13", "-14", "-15", "-16", "-8", "-9", "-10", "-11", "-12", "-23" /*,  "-70",  "-34", "-175", "-127", "-143", "-103", "-93",  "-135", "-137", "-136", "-144", "-142", "-22"*/));
    // public static ArrayList<String> topRowKeyChoices = new ArrayList<>(Arrays.asList("-20", "-21", "-13", "-14", "-15", "-16", "-8", "-9", "-10", "-11", "-12", "-23" /*,  "-70",  "-34", "-175", "-127", "-143", "-103", "-93",  "-135", "-137", "-136", "-144", "-142", "-22"*/));
    // public static ArrayList<String> customKeyChoices = new ArrayList<>(Arrays.asList("-22", "-23", "-25", "-26", "-27", "-84", "-103", "-133", "-134", "-135", "-136", "-137", "-138", "-139", "-140", "-142", "-143", "-144", "-174", "-175"));

    public static int[] topRowKeyDefault = new int[] {
        -20, -21, -13, -14, -15, -16, -8, -9, -10, -11, -12, -23
        /*,  -70,  -34, -175, -127, -143, -103, -93,  -135, -137, -136, -144, -142, -22*/
    };
    public static int[] topRowKeyChoices = new int[] {
        -20, -21, -13, -14, -15, -16, -8, -9, -10, -11, -12, -23
        /*,  -70,  -34, -175, -127, -143, -103, -93,  -135, -137, -136, -144, -142, -22*/
    };

    public static int[] customKeyChoices = new int[] {
        -22, -23, -25, -26, -27, -84, -103, -133, -134, -135, -136, -137, -138, -139, -140, -142, -143, -144, -174, -175
    };

    public static int[] calcPasses = new int[] {
        -101, -22, -12, 10
    };
    public static int[] calcCaptures = new int[] {
        -200, -201, -202, -203, -204, -205, -206, -207, -208, -209, -5, -7, -8, -9, -10, -11, 32, 37, 43, 45, 46, 48, 49,
        50, 51, 52, 53, 54, 55, 56, 57, 61, 94, 215, 247
    };
    public static int[] calcOperators = new int[] {
        43, 45, 215, 37, 247, 94, 61
    };
    public static int[] hexPasses = new int[] {
        -175, -101, -23, -22, -20, -21, -13, -14, -15, -16, -12, -11, -10, -9, -7, -5, -8, 10, -2, -126, -127, -128, -129, -130, -131
    };
    public static int[] hexCaptures = new int[] {
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, -201, -202, -203, -204, -205, -206
    };
}