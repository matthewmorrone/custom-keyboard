package com.custom.keyboard;

import android.view.KeyEvent;

class KeyCodes {

    static final int[] hexPasses = new int[] {
            7,    9,   10,   32,
           -1,   -4,   -5,   -7,
           -8,   -9,  -10,  -11,
         -101, -102, -103,  -25,
          -26,  -76,  -93, -107,
         -108, -109, -110, -111,
         -174,
    };
    static final int[] hexCaptures = new int[] {
         48, 49, 50,  51,  52,  53, 54, 55,
         56, 57, 65,  66,  67,  68, 69, 70,
         97, 98, 99, 100, 101, 102
    };

    static int getHardKeyCode(int keycode) {
        char code = (char)keycode;
        switch (String.valueOf(code)) {
            case "a": return KeyEvent.KEYCODE_A;
            case "b": return KeyEvent.KEYCODE_B;
            case "c": return KeyEvent.KEYCODE_C;
            case "d": return KeyEvent.KEYCODE_D;
            case "e": return KeyEvent.KEYCODE_E;
            case "f": return KeyEvent.KEYCODE_F;
            case "g": return KeyEvent.KEYCODE_G;
            case "h": return KeyEvent.KEYCODE_H;
            case "i": return KeyEvent.KEYCODE_I;
            case "j": return KeyEvent.KEYCODE_J;
            case "k": return KeyEvent.KEYCODE_K;
            case "l": return KeyEvent.KEYCODE_L;
            case "m": return KeyEvent.KEYCODE_M;
            case "n": return KeyEvent.KEYCODE_N;
            case "o": return KeyEvent.KEYCODE_O;
            case "p": return KeyEvent.KEYCODE_P;
            case "q": return KeyEvent.KEYCODE_Q;
            case "r": return KeyEvent.KEYCODE_R;
            case "s": return KeyEvent.KEYCODE_S;
            case "t": return KeyEvent.KEYCODE_T;
            case "u": return KeyEvent.KEYCODE_U;
            case "v": return KeyEvent.KEYCODE_V;
            case "w": return KeyEvent.KEYCODE_W;
            case "x": return KeyEvent.KEYCODE_X;
            case "y": return KeyEvent.KEYCODE_Y;
            case "z": return KeyEvent.KEYCODE_Z;
            default:  return keycode;
        }
    }

    static int getUnbold(int primaryCode) {
        if      (primaryCode >= 120276 && primaryCode <= 120301) {
            primaryCode -= 120211;
        }
        else if (primaryCode >= 120302 && primaryCode <= 120327) {
            primaryCode -= 120205;
        }
        return primaryCode;
    }
    static int getBold(int primaryCode) {
        if      (primaryCode >= 65 && primaryCode <= 90) {
            primaryCode += 120211;
        }
        else if (primaryCode >= 97 && primaryCode <= 122) {
            primaryCode += 120205;
        }
        return primaryCode;
    }

    static int getUnitalic(int primaryCode) {
        if      (primaryCode >= 120328 && primaryCode <= 120353) {
            primaryCode -= 120263;
        }
        else if (primaryCode >= 120354 && primaryCode <= 120379) {
            primaryCode -= 120257;
        }
        return primaryCode;
    }
    static int getItalic(int primaryCode) {
        if      (primaryCode >= 65 && primaryCode <= 90) {
            primaryCode += 120263;
        }
        else if (primaryCode >= 97 && primaryCode <= 122) {
            primaryCode += 120257;
        }
        return primaryCode;
    }

    static int handleCharacter(CustomKeyboardView kv, int primaryCode) {
        if (Util.isAlphaNumeric(primaryCode)) {
            if (Util.isDigit(primaryCode) && Variables.isBold()) primaryCode += 120764;

            else if (kv.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
                if (Variables.isBold() && Variables.isItalic()) primaryCode += 120315;
                else if (Variables.isBold()) primaryCode += 120211;
                else if (Variables.isItalic()) primaryCode += 120263;
            }
            else {
                if (Variables.isBold() && Variables.isItalic()) primaryCode += 120309;
                else if (Variables.isBold()) primaryCode += 120205;
                else if (Variables.isItalic()) primaryCode += 120257;
            }
        }
        return blockException(primaryCode);
    }

    // private static void noop() {}

    private static int blockException(int primaryCode) {
        switch (primaryCode) {
            case 119893: return 8462;
            case 119965: return 8492;
            case 119968: return 8496;
            case 119969: return 8497;
            case 119971: return 8459;
            case 119972: return 8464;
            case 119975: return 8466;
            case 119976: return 8499;
            case 119981: return 8475;
            case 119994: return 8495;
            case 119996: return 8458;
            case 120004: return 8500;
            case 120070: return 8493;
            case 120075: return 8460;
            case 120076: return 8465;
            case 120085: return 8476;
            case 120093: return 8488;
            case 120122: return 8450;
            case 120127: return 8461;
            case 120133: return 8469;
            case 120135: return 8473;
            case 120136: return 8474;
            case 120137: return 8477;
            case 120145: return 8484;
            default:     return primaryCode;
        }
    }
}
