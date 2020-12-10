package com.custom.keyboard;

public class Constants {

    public static int[] topRowKeyDefault = {
        -20, -21, -13, -14, -15, -16, -8, -9, -10, -11, -12, -23
    };
    public static int[] topRowKeyChoices = {
        -20, -21, -13, -14, -15, -16, -8, -9, -10, -11, -12, -23
    };
    public static String[] topRowKeyLabels = {
        "Home", "End", "Up", "Down", "Left", "Right", "Cut", "Copy", "Paste", "Select", "Select All", "Voice"
    };
    public static int[] customKeyChoices = {
        -22, -23, -25, -26, -27, -84, -130, -132, -133, -134, -135, -136, -137, -138, -139, -140, -142, -143, -144, -174
    };
    public static int[] longPressKeys = {
        32, -2, -5, -7, -11, -12, -15, -16, -200, -299, -501, -502, -503, -504, -505, -506, -507, -508, -509, -510, -511, -512, -513
    };


    public static int[] calcPasses = {
        -128, -22, -12, 10
    };
    public static int[] calcCaptures = {
        -200, -201, -202, -203, -204, -205, -206, -207, -208, -209, -5, -7, -8, -9, -10, -11, 32, 37, 43, 45, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 61, 94, 215, 247
    };
    public static int[] calcOperators = {
        43, 45, 215, 37, 247, 94, 61
    };
    public static int[] hexPasses = {
        -174, -128, -23, -22, -20, -21, -13, -14, -15, -16, -12, -11, -10, -9, -7, -5, -8, 10, -2, -122, -123, -124, -125, -126, -127
    };
    public static int[] hexCaptures = {
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, -201, -202, -203, -204, -205, -206
    };
}
