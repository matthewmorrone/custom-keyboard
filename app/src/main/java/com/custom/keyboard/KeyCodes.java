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

    static boolean isBold(int primaryCode) {
        if      (primaryCode >= 120276 && primaryCode <= 120301) {
            return true;
        }
        else if (primaryCode >= 120302 && primaryCode <= 120327) {
            return true;
        }
        return false;
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

    static boolean isItalic(int primaryCode) {
        if      (primaryCode >= 120328 && primaryCode <= 120353) {
            return true; 
        }
        else if (primaryCode >= 120354 && primaryCode <= 120379) {
            return true;
        }
        return false;
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


    static boolean isEmphasized(int primaryCode) {
        if      (primaryCode >= 120380 && primaryCode <= 120405) {
            return true;
        }
        else if (primaryCode >= 120406 && primaryCode <= 120431) {
            return true;
        }
        return false;
    }
    static int getUnemphasized(int primaryCode) {
        if      (primaryCode >= 120380 && primaryCode <= 120405) {
            primaryCode -= 120315;
        }
        else if (primaryCode >= 120406 && primaryCode <= 120431) {
            primaryCode -= 120309;
        }
        return primaryCode;
    }

    static int getEmphasized(int primaryCode) {
        if      (primaryCode >= 65 && primaryCode <= 90) {
            primaryCode += 120315;
        }
        else if (primaryCode >= 97 && primaryCode <= 122) {
            primaryCode += 120309;
        }
        return primaryCode;
    }

    static int handleCharacter(CustomKeyboardView kv, int primaryCode) {
        if (Util.isAlphaNumeric(primaryCode)) {
            if (Util.isDigit(primaryCode) && Variables.isBold()) primaryCode += 120764;

            else if (kv.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
                // if (Variables.isBold() && Variables.isItalic()) primaryCode += 120315;
                if (Variables.isEmphasized()) primaryCode += 120315;
                else if (Variables.isBold()) primaryCode += 120211;
                else if (Variables.isItalic()) primaryCode += 120263;
            }
            else {
                // if (Variables.isBold() && Variables.isItalic()) primaryCode += 120309;
                if (Variables.isEmphasized()) primaryCode += 120309;
                else if (Variables.isBold()) primaryCode += 120205;
                else if (Variables.isItalic()) primaryCode += 120257;
            }

            if (Variables.is119808()) {
                if (kv.isShifted()) primaryCode += (119808 - 65);
                else primaryCode += (119808 - 71);
            }
            if (Variables.is119860()) {
                if (kv.isShifted()) primaryCode += (119860 - 65);
                else primaryCode += (119860 - 71);
            }
            if (Variables.is119912()) {
                if (kv.isShifted()) primaryCode += (119912 - 65);
                else primaryCode += (119912 - 71);
            }
            if (Variables.is119964()) {
                if (kv.isShifted()) primaryCode += (119964 - 65);
                else primaryCode += (119964 - 71);
            }
            if (Variables.is120016()) {
                if (kv.isShifted()) primaryCode += (120016 - 65);
                else primaryCode += (120016 - 71);
            }
            if (Variables.is120068()) {
                if (kv.isShifted()) primaryCode += (120068 - 65);
                else primaryCode += (120068 - 71);
            }
            if (Variables.is120120()) {
                if (kv.isShifted()) primaryCode += (120120 - 65);
                else primaryCode += (120120 - 71);
            }
            if (Variables.is120172()) {
                if (kv.isShifted()) primaryCode += (120172 - 65);
                else primaryCode += (120172 - 71);
            }
            if (Variables.is120224()) {
                if (kv.isShifted()) primaryCode += (120224 - 65);
                else primaryCode += (120224 - 71);
            }
            if (Variables.is120276()) {
                if (kv.isShifted()) primaryCode += (120276 - 65);
                else primaryCode += (120276 - 71);
            }
            if (Variables.is120328()) {
                if (kv.isShifted()) primaryCode += (120328 - 65);
                else primaryCode += (120328 - 71);
            }
            if (Variables.is120380()) {
                if (kv.isShifted()) primaryCode += (120380 - 65);
                else primaryCode += (120380 - 71);
            }
            if (Variables.is120432()) {
                if (kv.isShifted()) primaryCode += (120432 - 65);
                else primaryCode += (120432 - 71);
            }
        }
        return blockException(primaryCode);
    }

    // private static void noop() {}

    private static int blockException(int primaryCode) {
        if (Variables.is009372()    && primaryCode ==   65) {return 127248;} // 🄐
        if (Variables.is009372()    && primaryCode ==   66) {return 127249;} // 🄑
        if (Variables.is009372()    && primaryCode ==   67) {return 127250;} // 🄒
        if (Variables.is009372()    && primaryCode ==   68) {return 127251;} // 🄓
        if (Variables.is009372()    && primaryCode ==   69) {return 127252;} // 🄔
        if (Variables.is009372()    && primaryCode ==   70) {return 127253;} // 🄕
        if (Variables.is009372()    && primaryCode ==   71) {return 127254;} // 🄖
        if (Variables.is009372()    && primaryCode ==   72) {return 127255;} // 🄗
        if (Variables.is009372()    && primaryCode ==   73) {return 127256;} // 🄘
        if (Variables.is009372()    && primaryCode ==   74) {return 127257;} // 🄙
        if (Variables.is009372()    && primaryCode ==   75) {return 127258;} // 🄚
        if (Variables.is009372()    && primaryCode ==   76) {return 127259;} // 🄛
        if (Variables.is009372()    && primaryCode ==   77) {return 127260;} // 🄜
        if (Variables.is009372()    && primaryCode ==   78) {return 127261;} // 🄝
        if (Variables.is009372()    && primaryCode ==   79) {return 127262;} // 🄞
        if (Variables.is009372()    && primaryCode ==   80) {return 127263;} // 🄟
        if (Variables.is009372()    && primaryCode ==   81) {return 127264;} // 🄠
        if (Variables.is009372()    && primaryCode ==   82) {return 127265;} // 🄡
        if (Variables.is009372()    && primaryCode ==   83) {return 127266;} // 🄢
        if (Variables.is009372()    && primaryCode ==   84) {return 127267;} // 🄣
        if (Variables.is009372()    && primaryCode ==   85) {return 127268;} // 🄤
        if (Variables.is009372()    && primaryCode ==   86) {return 127269;} // 🄥
        if (Variables.is009372()    && primaryCode ==   87) {return 127270;} // 🄦
        if (Variables.is009372()    && primaryCode ==   88) {return 127271;} // 🄧
        if (Variables.is009372()    && primaryCode ==   89) {return 127272;} // 🄨
        if (Variables.is009372()    && primaryCode ==   90) {return 127273;} // 🄩
        if (Variables.is009372()    && primaryCode ==   97) {return   9372;} // ⒜
        if (Variables.is009372()    && primaryCode ==   98) {return   9373;} // ⒝
        if (Variables.is009372()    && primaryCode ==   99) {return   9374;} // ⒞
        if (Variables.is009372()    && primaryCode ==  100) {return   9375;} // ⒟
        if (Variables.is009372()    && primaryCode ==  101) {return   9376;} // ⒠
        if (Variables.is009372()    && primaryCode ==  102) {return   9377;} // ⒡
        if (Variables.is009372()    && primaryCode ==  103) {return   9378;} // ⒢
        if (Variables.is009372()    && primaryCode ==  104) {return   9379;} // ⒣
        if (Variables.is009372()    && primaryCode ==  105) {return   9380;} // ⒤
        if (Variables.is009372()    && primaryCode ==  106) {return   9381;} // ⒥
        if (Variables.is009372()    && primaryCode ==  107) {return   9382;} // ⒦
        if (Variables.is009372()    && primaryCode ==  108) {return   9383;} // ⒧
        if (Variables.is009372()    && primaryCode ==  109) {return   9384;} // ⒨
        if (Variables.is009372()    && primaryCode ==  110) {return   9385;} // ⒩
        if (Variables.is009372()    && primaryCode ==  111) {return   9386;} // ⒪
        if (Variables.is009372()    && primaryCode ==  112) {return   9387;} // ⒫
        if (Variables.is009372()    && primaryCode ==  113) {return   9388;} // ⒬
        if (Variables.is009372()    && primaryCode ==  114) {return   9389;} // ⒭
        if (Variables.is009372()    && primaryCode ==  115) {return   9390;} // ⒮
        if (Variables.is009372()    && primaryCode ==  116) {return   9391;} // ⒯
        if (Variables.is009372()    && primaryCode ==  117) {return   9392;} // ⒰
        if (Variables.is009372()    && primaryCode ==  118) {return   9393;} // ⒱
        if (Variables.is009372()    && primaryCode ==  119) {return   9394;} // ⒲
        if (Variables.is009372()    && primaryCode ==  120) {return   9395;} // ⒳
        if (Variables.is009372()    && primaryCode ==  121) {return   9396;} // ⒴
        if (Variables.is009372()    && primaryCode ==  122) {return   9397;} // ⒵
        if (Variables.is009398()    && primaryCode ==   65) {return   9398;} // Ⓐ
        if (Variables.is009398()    && primaryCode ==   66) {return   9399;} // Ⓑ
        if (Variables.is009398()    && primaryCode ==   67) {return   9400;} // Ⓒ
        if (Variables.is009398()    && primaryCode ==   68) {return   9401;} // Ⓓ
        if (Variables.is009398()    && primaryCode ==   69) {return   9402;} // Ⓔ
        if (Variables.is009398()    && primaryCode ==   70) {return   9403;} // Ⓕ
        if (Variables.is009398()    && primaryCode ==   71) {return   9404;} // Ⓖ
        if (Variables.is009398()    && primaryCode ==   72) {return   9405;} // Ⓗ
        if (Variables.is009398()    && primaryCode ==   73) {return   9406;} // Ⓘ
        if (Variables.is009398()    && primaryCode ==   74) {return   9407;} // Ⓙ
        if (Variables.is009398()    && primaryCode ==   75) {return   9408;} // Ⓚ
        if (Variables.is009398()    && primaryCode ==   76) {return   9409;} // Ⓛ
        if (Variables.is009398()    && primaryCode ==   77) {return   9410;} // Ⓜ
        if (Variables.is009398()    && primaryCode ==   78) {return   9411;} // Ⓝ
        if (Variables.is009398()    && primaryCode ==   79) {return   9412;} // Ⓞ
        if (Variables.is009398()    && primaryCode ==   80) {return   9413;} // Ⓟ
        if (Variables.is009398()    && primaryCode ==   81) {return   9414;} // Ⓠ
        if (Variables.is009398()    && primaryCode ==   82) {return   9415;} // Ⓡ
        if (Variables.is009398()    && primaryCode ==   83) {return   9416;} // Ⓢ
        if (Variables.is009398()    && primaryCode ==   84) {return   9417;} // Ⓣ
        if (Variables.is009398()    && primaryCode ==   85) {return   9418;} // Ⓤ
        if (Variables.is009398()    && primaryCode ==   86) {return   9419;} // Ⓥ
        if (Variables.is009398()    && primaryCode ==   87) {return   9420;} // Ⓦ
        if (Variables.is009398()    && primaryCode ==   88) {return   9421;} // Ⓧ
        if (Variables.is009398()    && primaryCode ==   89) {return   9422;} // Ⓨ
        if (Variables.is009398()    && primaryCode ==   90) {return   9423;} // Ⓩ
        if (Variables.is009398()    && primaryCode ==   97) {return   9424;} // ⓐ
        if (Variables.is009398()    && primaryCode ==   98) {return   9425;} // ⓑ
        if (Variables.is009398()    && primaryCode ==   99) {return   9426;} // ⓒ
        if (Variables.is009398()    && primaryCode ==  100) {return   9427;} // ⓓ
        if (Variables.is009398()    && primaryCode ==  101) {return   9428;} // ⓔ
        if (Variables.is009398()    && primaryCode ==  102) {return   9429;} // ⓕ
        if (Variables.is009398()    && primaryCode ==  103) {return   9430;} // ⓖ
        if (Variables.is009398()    && primaryCode ==  104) {return   9431;} // ⓗ
        if (Variables.is009398()    && primaryCode ==  105) {return   9432;} // ⓘ
        if (Variables.is009398()    && primaryCode ==  106) {return   9433;} // ⓙ
        if (Variables.is009398()    && primaryCode ==  107) {return   9434;} // ⓚ
        if (Variables.is009398()    && primaryCode ==  108) {return   9435;} // ⓛ
        if (Variables.is009398()    && primaryCode ==  109) {return   9436;} // ⓜ
        if (Variables.is009398()    && primaryCode ==  110) {return   9437;} // ⓝ
        if (Variables.is009398()    && primaryCode ==  111) {return   9438;} // ⓞ
        if (Variables.is009398()    && primaryCode ==  112) {return   9439;} // ⓟ
        if (Variables.is009398()    && primaryCode ==  113) {return   9440;} // ⓠ
        if (Variables.is009398()    && primaryCode ==  114) {return   9441;} // ⓡ
        if (Variables.is009398()    && primaryCode ==  115) {return   9442;} // ⓢ
        if (Variables.is009398()    && primaryCode ==  116) {return   9443;} // ⓣ
        if (Variables.is009398()    && primaryCode ==  117) {return   9444;} // ⓤ
        if (Variables.is009398()    && primaryCode ==  118) {return   9445;} // ⓥ
        if (Variables.is009398()    && primaryCode ==  119) {return   9446;} // ⓦ
        if (Variables.is009398()    && primaryCode ==  120) {return   9447;} // ⓧ
        if (Variables.is009398()    && primaryCode ==  121) {return   9448;} // ⓨ
        if (Variables.is009398()    && primaryCode ==  122) {return   9449;} // ⓩ
        if (Variables.is119808()    && primaryCode ==   48) {return 120782;} // 𝟎
        if (Variables.is119808()    && primaryCode ==   49) {return 120783;} // 𝟏
        if (Variables.is119808()    && primaryCode ==   50) {return 120784;} // 𝟐
        if (Variables.is119808()    && primaryCode ==   51) {return 120785;} // 𝟑
        if (Variables.is119808()    && primaryCode ==   52) {return 120786;} // 𝟒
        if (Variables.is119808()    && primaryCode ==   53) {return 120787;} // 𝟓
        if (Variables.is119808()    && primaryCode ==   54) {return 120788;} // 𝟔
        if (Variables.is119808()    && primaryCode ==   55) {return 120789;} // 𝟕
        if (Variables.is119808()    && primaryCode ==   56) {return 120790;} // 𝟖
        if (Variables.is119808()    && primaryCode ==   57) {return 120791;} // 𝟗
        if (Variables.is119912()    && primaryCode ==  400) {return 120550;} // 𝛦
        if (Variables.is119912()    && primaryCode ==  406) {return 120554;} // 𝛪
        if (Variables.is119912()    && primaryCode ==  603) {return 120576;} // 𝜀
        if (Variables.is119912()    && primaryCode ==  617) {return 120580;} // 𝜄
        if (Variables.is119912()    && primaryCode ==  913) {return 120546;} // 𝛢
        if (Variables.is119912()    && primaryCode ==  914) {return 120547;} // 𝛣
        if (Variables.is119912()    && primaryCode ==  915) {return 120548;} // 𝛤
        if (Variables.is119912()    && primaryCode ==  916) {return 120549;} // 𝛥
        if (Variables.is119912()    && primaryCode ==  918) {return 120551;} // 𝛧
        if (Variables.is119912()    && primaryCode ==  919) {return 120552;} // 𝛨
        if (Variables.is119912()    && primaryCode ==  920) {return 120553;} // 𝛩
        if (Variables.is119912()    && primaryCode ==  922) {return 120555;} // 𝛫
        if (Variables.is119912()    && primaryCode ==  923) {return 120556;} // 𝛬
        if (Variables.is119912()    && primaryCode ==  924) {return 120557;} // 𝛭
        if (Variables.is119912()    && primaryCode ==  925) {return 120558;} // 𝛮
        if (Variables.is119912()    && primaryCode ==  926) {return 120559;} // 𝛯
        if (Variables.is119912()    && primaryCode ==  927) {return 120560;} // 𝛰
        if (Variables.is119912()    && primaryCode ==  928) {return 120561;} // 𝛱
        if (Variables.is119912()    && primaryCode ==  929) {return 120562;} // 𝛲
        if (Variables.is119912()    && primaryCode ==  931) {return 120564;} // 𝛴
        if (Variables.is119912()    && primaryCode ==  932) {return 120565;} // 𝛵
        if (Variables.is119912()    && primaryCode ==  933) {return 120566;} // 𝛶
        if (Variables.is119912()    && primaryCode ==  934) {return 120567;} // 𝛷
        if (Variables.is119912()    && primaryCode ==  935) {return 120568;} // 𝛸
        if (Variables.is119912()    && primaryCode ==  936) {return 120569;} // 𝛹
        if (Variables.is119912()    && primaryCode ==  937) {return 120570;} // 𝛺
        if (Variables.is119912()    && primaryCode ==  945) {return 120572;} // 𝛼
        if (Variables.is119912()    && primaryCode ==  946) {return 120573;} // 𝛽
        if (Variables.is119912()    && primaryCode ==  947) {return 120574;} // 𝛾
        if (Variables.is119912()    && primaryCode ==  948) {return 120575;} // 𝛿
        if (Variables.is119912()    && primaryCode ==  950) {return 120577;} // 𝜁
        if (Variables.is119912()    && primaryCode ==  951) {return 120578;} // 𝜂
        if (Variables.is119912()    && primaryCode ==  952) {return 120579;} // 𝜃
        if (Variables.is119912()    && primaryCode ==  954) {return 120581;} // 𝜅
        if (Variables.is119912()    && primaryCode ==  955) {return 120582;} // 𝜆
        if (Variables.is119912()    && primaryCode ==  956) {return 120583;} // 𝜇
        if (Variables.is119912()    && primaryCode ==  957) {return 120584;} // 𝜈
        if (Variables.is119912()    && primaryCode ==  958) {return 120585;} // 𝜉
        if (Variables.is119912()    && primaryCode ==  959) {return 120586;} // 𝜊
        if (Variables.is119912()    && primaryCode ==  960) {return 120587;} // 𝜋
        if (Variables.is119912()    && primaryCode ==  961) {return 120588;} // 𝜌
        if (Variables.is119912()    && primaryCode ==  962) {return 120589;} // 𝜍
        if (Variables.is119912()    && primaryCode ==  963) {return 120590;} // 𝜎
        if (Variables.is119912()    && primaryCode ==  964) {return 120591;} // 𝜏
        if (Variables.is119912()    && primaryCode ==  965) {return 120592;} // 𝜐
        if (Variables.is119912()    && primaryCode ==  966) {return 120593;} // 𝜑
        if (Variables.is119912()    && primaryCode ==  967) {return 120594;} // 𝜒
        if (Variables.is119912()    && primaryCode ==  968) {return 120595;} // 𝜓
        if (Variables.is119912()    && primaryCode ==  969) {return 120596;} // 𝜔
        if (Variables.is119912()    && primaryCode ==  977) {return 120599;} // 𝜗
        if (Variables.is119912()    && primaryCode ==  981) {return 120601;} // 𝜙
        if (Variables.is119912()    && primaryCode ==  982) {return 120603;} // 𝜛
        if (Variables.is119912()    && primaryCode == 1008) {return 120600;} // 𝜘
        if (Variables.is119912()    && primaryCode == 1009) {return 120602;} // 𝜚
        if (Variables.is119912()    && primaryCode == 1012) {return 120563;} // 𝛳
        if (Variables.is119912()    && primaryCode == 1013) {return 120598;} // 𝜖
        if (Variables.is119912()    && primaryCode == 8706) {return 120597;} // 𝜕
        if (Variables.is119912()    && primaryCode == 8711) {return 120571;} // 𝛻
        if (Variables.is120120()    && primaryCode ==   48) {return 120792;} // 𝟘
        if (Variables.is120120()    && primaryCode ==   49) {return 120793;} // 𝟙
        if (Variables.is120120()    && primaryCode ==   50) {return 120794;} // 𝟚
        if (Variables.is120120()    && primaryCode ==   51) {return 120795;} // 𝟛
        if (Variables.is120120()    && primaryCode ==   52) {return 120796;} // 𝟜
        if (Variables.is120120()    && primaryCode ==   53) {return 120797;} // 𝟝
        if (Variables.is120120()    && primaryCode ==   54) {return 120798;} // 𝟞
        if (Variables.is120120()    && primaryCode ==   55) {return 120799;} // 𝟟
        if (Variables.is120120()    && primaryCode ==   56) {return 120800;} // 𝟠
        if (Variables.is120120()    && primaryCode ==   57) {return 120801;} // 𝟡
        if (Variables.is120120()    && primaryCode ==  400) {return 120724;} // 𝞔
        if (Variables.is120120()    && primaryCode ==  406) {return 120728;} // 𝞘
        if (Variables.is120120()    && primaryCode ==  603) {return 120750;} // 𝞮
        if (Variables.is120120()    && primaryCode ==  617) {return 120754;} // 𝞲
        if (Variables.is120120()    && primaryCode ==  913) {return 120720;} // 𝞐
        if (Variables.is120120()    && primaryCode ==  914) {return 120721;} // 𝞑
        if (Variables.is120120()    && primaryCode ==  915) {return 120722;} // 𝞒
        if (Variables.is120120()    && primaryCode ==  916) {return 120723;} // 𝞓
        if (Variables.is120120()    && primaryCode ==  918) {return 120725;} // 𝞕
        if (Variables.is120120()    && primaryCode ==  919) {return 120726;} // 𝞖
        if (Variables.is120120()    && primaryCode ==  920) {return 120727;} // 𝞗
        if (Variables.is120120()    && primaryCode ==  922) {return 120729;} // 𝞙
        if (Variables.is120120()    && primaryCode ==  923) {return 120730;} // 𝞚
        if (Variables.is120120()    && primaryCode ==  924) {return 120731;} // 𝞛
        if (Variables.is120120()    && primaryCode ==  925) {return 120732;} // 𝞜
        if (Variables.is120120()    && primaryCode ==  926) {return 120733;} // 𝞝
        if (Variables.is120120()    && primaryCode ==  927) {return 120734;} // 𝞞
        if (Variables.is120120()    && primaryCode ==  928) {return 120735;} // 𝞟
        if (Variables.is120120()    && primaryCode ==  929) {return 120736;} // 𝞠
        if (Variables.is120120()    && primaryCode ==  931) {return 120738;} // 𝞢
        if (Variables.is120120()    && primaryCode ==  932) {return 120739;} // 𝞣
        if (Variables.is120120()    && primaryCode ==  933) {return 120740;} // 𝞤
        if (Variables.is120120()    && primaryCode ==  934) {return 120741;} // 𝞥
        if (Variables.is120120()    && primaryCode ==  935) {return 120742;} // 𝞦
        if (Variables.is120120()    && primaryCode ==  936) {return 120743;} // 𝞧
        if (Variables.is120120()    && primaryCode ==  937) {return 120744;} // 𝞨
        if (Variables.is120120()    && primaryCode ==  945) {return 120746;} // 𝞪
        if (Variables.is120120()    && primaryCode ==  946) {return 120747;} // 𝞫
        if (Variables.is120120()    && primaryCode ==  947) {return 120748;} // 𝞬
        if (Variables.is120120()    && primaryCode ==  948) {return 120749;} // 𝞭
        if (Variables.is120120()    && primaryCode ==  950) {return 120751;} // 𝞯
        if (Variables.is120120()    && primaryCode ==  951) {return 120752;} // 𝞰
        if (Variables.is120120()    && primaryCode ==  952) {return 120753;} // 𝞱
        if (Variables.is120120()    && primaryCode ==  954) {return 120755;} // 𝞳
        if (Variables.is120120()    && primaryCode ==  955) {return 120756;} // 𝞴
        if (Variables.is120120()    && primaryCode ==  956) {return 120757;} // 𝞵
        if (Variables.is120120()    && primaryCode ==  957) {return 120758;} // 𝞶
        if (Variables.is120120()    && primaryCode ==  958) {return 120759;} // 𝞷
        if (Variables.is120120()    && primaryCode ==  959) {return 120760;} // 𝞸
        if (Variables.is120120()    && primaryCode ==  960) {return 120761;} // 𝞹
        if (Variables.is120120()    && primaryCode ==  961) {return 120762;} // 𝞺
        if (Variables.is120120()    && primaryCode ==  962) {return 120763;} // 𝞻
        if (Variables.is120120()    && primaryCode ==  963) {return 120764;} // 𝞼
        if (Variables.is120120()    && primaryCode ==  964) {return 120765;} // 𝞽
        if (Variables.is120120()    && primaryCode ==  965) {return 120766;} // 𝞾
        if (Variables.is120120()    && primaryCode ==  966) {return 120767;} // 𝞿
        if (Variables.is120120()    && primaryCode ==  967) {return 120768;} // 𝟀
        if (Variables.is120120()    && primaryCode ==  968) {return 120769;} // 𝟁
        if (Variables.is120120()    && primaryCode ==  969) {return 120770;} // 𝟂
        if (Variables.is120120()    && primaryCode ==  977) {return 120773;} // 𝟅
        if (Variables.is120120()    && primaryCode ==  981) {return 120775;} // 𝟇
        if (Variables.is120120()    && primaryCode ==  982) {return 120777;} // 𝟉
        if (Variables.is120120()    && primaryCode ==  988) {return 120778;} // 𝟊
        if (Variables.is120120()    && primaryCode ==  989) {return 120779;} // 𝟋
        if (Variables.is120120()    && primaryCode == 1008) {return 120774;} // 𝟆
        if (Variables.is120120()    && primaryCode == 1009) {return 120776;} // 𝟈
        if (Variables.is120120()    && primaryCode == 1012) {return 120737;} // 𝞡
        if (Variables.is120120()    && primaryCode == 1013) {return 120772;} // 𝟄
        if (Variables.is120120()    && primaryCode == 8706) {return 120771;} // 𝟃
        if (Variables.is120120()    && primaryCode == 8711) {return 120745;} // 𝞩
        if (Variables.is120224()    && primaryCode ==   48) {return 120802;} // 𝟢
        if (Variables.is120224()    && primaryCode ==   49) {return 120803;} // 𝟣
        if (Variables.is120224()    && primaryCode ==   50) {return 120804;} // 𝟤
        if (Variables.is120224()    && primaryCode ==   51) {return 120805;} // 𝟥
        if (Variables.is120224()    && primaryCode ==   52) {return 120806;} // 𝟦
        if (Variables.is120224()    && primaryCode ==   53) {return 120807;} // 𝟧
        if (Variables.is120224()    && primaryCode ==   54) {return 120808;} // 𝟨
        if (Variables.is120224()    && primaryCode ==   55) {return 120809;} // 𝟩
        if (Variables.is120224()    && primaryCode ==   56) {return 120810;} // 𝟪
        if (Variables.is120224()    && primaryCode ==   57) {return 120811;} // 𝟫
        if (Variables.is120276()    && primaryCode ==   48) {return 120812;} // 𝟬
        if (Variables.is120276()    && primaryCode ==   49) {return 120813;} // 𝟭
        if (Variables.is120276()    && primaryCode ==   50) {return 120814;} // 𝟮
        if (Variables.is120276()    && primaryCode ==   51) {return 120815;} // 𝟯
        if (Variables.is120276()    && primaryCode ==   52) {return 120816;} // 𝟰
        if (Variables.is120276()    && primaryCode ==   53) {return 120817;} // 𝟱
        if (Variables.is120276()    && primaryCode ==   54) {return 120818;} // 𝟲
        if (Variables.is120276()    && primaryCode ==   55) {return 120819;} // 𝟳
        if (Variables.is120276()    && primaryCode ==   56) {return 120820;} // 𝟴
        if (Variables.is120276()    && primaryCode ==   57) {return 120821;} // 𝟵
        if (Variables.is120276()    && primaryCode ==  400) {return 120608;} // 𝜠
        if (Variables.is120276()    && primaryCode ==  406) {return 120612;} // 𝜤
        if (Variables.is120276()    && primaryCode ==  603) {return 120634;} // 𝜺
        if (Variables.is120276()    && primaryCode ==  617) {return 120638;} // 𝜾
        if (Variables.is120276()    && primaryCode ==  913) {return 120604;} // 𝜜
        if (Variables.is120276()    && primaryCode ==  914) {return 120605;} // 𝜝
        if (Variables.is120276()    && primaryCode ==  915) {return 120606;} // 𝜞
        if (Variables.is120276()    && primaryCode ==  916) {return 120607;} // 𝜟
        if (Variables.is120276()    && primaryCode ==  918) {return 120609;} // 𝜡
        if (Variables.is120276()    && primaryCode ==  919) {return 120610;} // 𝜢
        if (Variables.is120276()    && primaryCode ==  920) {return 120611;} // 𝜣
        if (Variables.is120276()    && primaryCode ==  922) {return 120613;} // 𝜥
        if (Variables.is120276()    && primaryCode ==  923) {return 120614;} // 𝜦
        if (Variables.is120276()    && primaryCode ==  924) {return 120615;} // 𝜧
        if (Variables.is120276()    && primaryCode ==  925) {return 120616;} // 𝜨
        if (Variables.is120276()    && primaryCode ==  926) {return 120617;} // 𝜩
        if (Variables.is120276()    && primaryCode ==  927) {return 120618;} // 𝜪
        if (Variables.is120276()    && primaryCode ==  928) {return 120619;} // 𝜫
        if (Variables.is120276()    && primaryCode ==  929) {return 120620;} // 𝜬
        if (Variables.is120276()    && primaryCode ==  931) {return 120622;} // 𝜮
        if (Variables.is120276()    && primaryCode ==  932) {return 120623;} // 𝜯
        if (Variables.is120276()    && primaryCode ==  933) {return 120624;} // 𝜰
        if (Variables.is120276()    && primaryCode ==  934) {return 120625;} // 𝜱
        if (Variables.is120276()    && primaryCode ==  935) {return 120626;} // 𝜲
        if (Variables.is120276()    && primaryCode ==  936) {return 120627;} // 𝜳
        if (Variables.is120276()    && primaryCode ==  937) {return 120628;} // 𝜴
        if (Variables.is120276()    && primaryCode ==  945) {return 120630;} // 𝜶
        if (Variables.is120276()    && primaryCode ==  946) {return 120631;} // 𝜷
        if (Variables.is120276()    && primaryCode ==  947) {return 120632;} // 𝜸
        if (Variables.is120276()    && primaryCode ==  948) {return 120633;} // 𝜹
        if (Variables.is120276()    && primaryCode ==  950) {return 120635;} // 𝜻
        if (Variables.is120276()    && primaryCode ==  951) {return 120636;} // 𝜼
        if (Variables.is120276()    && primaryCode ==  952) {return 120637;} // 𝜽
        if (Variables.is120276()    && primaryCode ==  954) {return 120639;} // 𝜿
        if (Variables.is120276()    && primaryCode ==  955) {return 120640;} // 𝝀
        if (Variables.is120276()    && primaryCode ==  956) {return 120641;} // 𝝁
        if (Variables.is120276()    && primaryCode ==  957) {return 120642;} // 𝝂
        if (Variables.is120276()    && primaryCode ==  958) {return 120643;} // 𝝃
        if (Variables.is120276()    && primaryCode ==  959) {return 120644;} // 𝝄
        if (Variables.is120276()    && primaryCode ==  960) {return 120645;} // 𝝅
        if (Variables.is120276()    && primaryCode ==  961) {return 120646;} // 𝝆
        if (Variables.is120276()    && primaryCode ==  962) {return 120647;} // 𝝇
        if (Variables.is120276()    && primaryCode ==  963) {return 120648;} // 𝝈
        if (Variables.is120276()    && primaryCode ==  964) {return 120649;} // 𝝉
        if (Variables.is120276()    && primaryCode ==  965) {return 120650;} // 𝝊
        if (Variables.is120276()    && primaryCode ==  966) {return 120651;} // 𝝋
        if (Variables.is120276()    && primaryCode ==  967) {return 120652;} // 𝝌
        if (Variables.is120276()    && primaryCode ==  968) {return 120653;} // 𝝍
        if (Variables.is120276()    && primaryCode ==  969) {return 120654;} // 𝝎
        if (Variables.is120276()    && primaryCode ==  977) {return 120657;} // 𝝑
        if (Variables.is120276()    && primaryCode ==  981) {return 120659;} // 𝝓
        if (Variables.is120276()    && primaryCode ==  982) {return 120661;} // 𝝕
        if (Variables.is120276()    && primaryCode == 1008) {return 120658;} // 𝝒
        if (Variables.is120276()    && primaryCode == 1009) {return 120660;} // 𝝔
        if (Variables.is120276()    && primaryCode == 1012) {return 120621;} // 𝜭
        if (Variables.is120276()    && primaryCode == 1013) {return 120656;} // 𝝐
        if (Variables.is120276()    && primaryCode == 8706) {return 120655;} // 𝝏
        if (Variables.is120276()    && primaryCode == 8711) {return 120629;} // 𝜵
        if (Variables.is120380()    && primaryCode ==  400) {return 120666;} // 𝝚
        if (Variables.is120380()    && primaryCode ==  406) {return 120670;} // 𝝞
        if (Variables.is120380()    && primaryCode ==  603) {return 120692;} // 𝝴
        if (Variables.is120380()    && primaryCode ==  617) {return 120696;} // 𝝸
        if (Variables.is120380()    && primaryCode ==  913) {return 120662;} // 𝝖
        if (Variables.is120380()    && primaryCode ==  914) {return 120663;} // 𝝗
        if (Variables.is120380()    && primaryCode ==  915) {return 120664;} // 𝝘
        if (Variables.is120380()    && primaryCode ==  916) {return 120665;} // 𝝙
        if (Variables.is120380()    && primaryCode ==  918) {return 120667;} // 𝝛
        if (Variables.is120380()    && primaryCode ==  919) {return 120668;} // 𝝜
        if (Variables.is120380()    && primaryCode ==  920) {return 120669;} // 𝝝
        if (Variables.is120380()    && primaryCode ==  922) {return 120671;} // 𝝟
        if (Variables.is120380()    && primaryCode ==  923) {return 120672;} // 𝝠
        if (Variables.is120380()    && primaryCode ==  924) {return 120673;} // 𝝡
        if (Variables.is120380()    && primaryCode ==  925) {return 120674;} // 𝝢
        if (Variables.is120380()    && primaryCode ==  926) {return 120675;} // 𝝣
        if (Variables.is120380()    && primaryCode ==  927) {return 120676;} // 𝝤
        if (Variables.is120380()    && primaryCode ==  928) {return 120677;} // 𝝥
        if (Variables.is120380()    && primaryCode ==  929) {return 120678;} // 𝝦
        if (Variables.is120380()    && primaryCode ==  931) {return 120680;} // 𝝨
        if (Variables.is120380()    && primaryCode ==  932) {return 120681;} // 𝝩
        if (Variables.is120380()    && primaryCode ==  933) {return 120682;} // 𝝪
        if (Variables.is120380()    && primaryCode ==  934) {return 120683;} // 𝝫
        if (Variables.is120380()    && primaryCode ==  935) {return 120684;} // 𝝬
        if (Variables.is120380()    && primaryCode ==  936) {return 120685;} // 𝝭
        if (Variables.is120380()    && primaryCode ==  937) {return 120686;} // 𝝮
        if (Variables.is120380()    && primaryCode ==  945) {return 120688;} // 𝝰
        if (Variables.is120380()    && primaryCode ==  946) {return 120689;} // 𝝱
        if (Variables.is120380()    && primaryCode ==  947) {return 120690;} // 𝝲
        if (Variables.is120380()    && primaryCode ==  948) {return 120691;} // 𝝳
        if (Variables.is120380()    && primaryCode ==  950) {return 120693;} // 𝝵
        if (Variables.is120380()    && primaryCode ==  951) {return 120694;} // 𝝶
        if (Variables.is120380()    && primaryCode ==  952) {return 120695;} // 𝝷
        if (Variables.is120380()    && primaryCode ==  954) {return 120697;} // 𝝹
        if (Variables.is120380()    && primaryCode ==  955) {return 120698;} // 𝝺
        if (Variables.is120380()    && primaryCode ==  956) {return 120699;} // 𝝻
        if (Variables.is120380()    && primaryCode ==  957) {return 120700;} // 𝝼
        if (Variables.is120380()    && primaryCode ==  958) {return 120701;} // 𝝽
        if (Variables.is120380()    && primaryCode ==  959) {return 120702;} // 𝝾
        if (Variables.is120380()    && primaryCode ==  960) {return 120703;} // 𝝿
        if (Variables.is120380()    && primaryCode ==  961) {return 120704;} // 𝞀
        if (Variables.is120380()    && primaryCode ==  962) {return 120705;} // 𝞁
        if (Variables.is120380()    && primaryCode ==  963) {return 120706;} // 𝞂
        if (Variables.is120380()    && primaryCode ==  964) {return 120707;} // 𝞃
        if (Variables.is120380()    && primaryCode ==  965) {return 120708;} // 𝞄
        if (Variables.is120380()    && primaryCode ==  966) {return 120709;} // 𝞅
        if (Variables.is120380()    && primaryCode ==  967) {return 120710;} // 𝞆
        if (Variables.is120380()    && primaryCode ==  968) {return 120711;} // 𝞇
        if (Variables.is120380()    && primaryCode ==  969) {return 120712;} // 𝞈
        if (Variables.is120380()    && primaryCode ==  977) {return 120715;} // 𝞋
        if (Variables.is120380()    && primaryCode ==  981) {return 120717;} // 𝞍
        if (Variables.is120380()    && primaryCode ==  982) {return 120719;} // 𝞏
        if (Variables.is120380()    && primaryCode == 1008) {return 120716;} // 𝞌
        if (Variables.is120380()    && primaryCode == 1009) {return 120718;} // 𝞎
        if (Variables.is120380()    && primaryCode == 1012) {return 120679;} // 𝝧
        if (Variables.is120380()    && primaryCode == 1013) {return 120714;} // 𝞊
        if (Variables.is120380()    && primaryCode == 8706) {return 120713;} // 𝞉
        if (Variables.is120380()    && primaryCode == 8711) {return 120687;} // 𝝯
        if (Variables.is120432()    && primaryCode ==   48) {return 120822;} // 𝟶
        if (Variables.is120432()    && primaryCode ==   49) {return 120823;} // 𝟷
        if (Variables.is120432()    && primaryCode ==   50) {return 120824;} // 𝟸
        if (Variables.is120432()    && primaryCode ==   51) {return 120825;} // 𝟹
        if (Variables.is120432()    && primaryCode ==   52) {return 120826;} // 𝟺
        if (Variables.is120432()    && primaryCode ==   53) {return 120827;} // 𝟻
        if (Variables.is120432()    && primaryCode ==   54) {return 120828;} // 𝟼
        if (Variables.is120432()    && primaryCode ==   55) {return 120829;} // 𝟽
        if (Variables.is120432()    && primaryCode ==   56) {return 120830;} // 𝟾
        if (Variables.is120432()    && primaryCode ==   57) {return 120831;} // 𝟿
        if (Variables.is120432()    && primaryCode ==  400) {return 120492;} // 𝚬
        if (Variables.is120432()    && primaryCode ==  406) {return 120496;} // 𝚰
        if (Variables.is120432()    && primaryCode ==  603) {return 120518;} // 𝛆
        if (Variables.is120432()    && primaryCode ==  617) {return 120522;} // 𝛊
        if (Variables.is120432()    && primaryCode ==  913) {return 120488;} // 𝚨
        if (Variables.is120432()    && primaryCode ==  914) {return 120489;} // 𝚩
        if (Variables.is120432()    && primaryCode ==  915) {return 120490;} // 𝚪
        if (Variables.is120432()    && primaryCode ==  916) {return 120491;} // 𝚫
        if (Variables.is120432()    && primaryCode ==  918) {return 120493;} // 𝚭
        if (Variables.is120432()    && primaryCode ==  919) {return 120494;} // 𝚮
        if (Variables.is120432()    && primaryCode ==  920) {return 120495;} // 𝚯
        if (Variables.is120432()    && primaryCode ==  922) {return 120497;} // 𝚱
        if (Variables.is120432()    && primaryCode ==  923) {return 120498;} // 𝚲
        if (Variables.is120432()    && primaryCode ==  924) {return 120499;} // 𝚳
        if (Variables.is120432()    && primaryCode ==  925) {return 120500;} // 𝚴
        if (Variables.is120432()    && primaryCode ==  926) {return 120501;} // 𝚵
        if (Variables.is120432()    && primaryCode ==  927) {return 120502;} // 𝚶
        if (Variables.is120432()    && primaryCode ==  928) {return 120503;} // 𝚷
        if (Variables.is120432()    && primaryCode ==  929) {return 120504;} // 𝚸
        if (Variables.is120432()    && primaryCode ==  931) {return 120506;} // 𝚺
        if (Variables.is120432()    && primaryCode ==  932) {return 120507;} // 𝚻
        if (Variables.is120432()    && primaryCode ==  933) {return 120508;} // 𝚼
        if (Variables.is120432()    && primaryCode ==  934) {return 120509;} // 𝚽
        if (Variables.is120432()    && primaryCode ==  935) {return 120510;} // 𝚾
        if (Variables.is120432()    && primaryCode ==  936) {return 120511;} // 𝚿
        if (Variables.is120432()    && primaryCode ==  937) {return 120512;} // 𝛀
        if (Variables.is120432()    && primaryCode ==  945) {return 120514;} // 𝛂
        if (Variables.is120432()    && primaryCode ==  946) {return 120515;} // 𝛃
        if (Variables.is120432()    && primaryCode ==  947) {return 120516;} // 𝛄
        if (Variables.is120432()    && primaryCode ==  948) {return 120517;} // 𝛅
        if (Variables.is120432()    && primaryCode ==  950) {return 120519;} // 𝛇
        if (Variables.is120432()    && primaryCode ==  951) {return 120520;} // 𝛈
        if (Variables.is120432()    && primaryCode ==  952) {return 120521;} // 𝛉
        if (Variables.is120432()    && primaryCode ==  954) {return 120523;} // 𝛋
        if (Variables.is120432()    && primaryCode ==  955) {return 120524;} // 𝛌
        if (Variables.is120432()    && primaryCode ==  956) {return 120525;} // 𝛍
        if (Variables.is120432()    && primaryCode ==  957) {return 120526;} // 𝛎
        if (Variables.is120432()    && primaryCode ==  958) {return 120527;} // 𝛏
        if (Variables.is120432()    && primaryCode ==  959) {return 120528;} // 𝛐
        if (Variables.is120432()    && primaryCode ==  960) {return 120529;} // 𝛑
        if (Variables.is120432()    && primaryCode ==  961) {return 120530;} // 𝛒
        if (Variables.is120432()    && primaryCode ==  962) {return 120531;} // 𝛓
        if (Variables.is120432()    && primaryCode ==  963) {return 120532;} // 𝛔
        if (Variables.is120432()    && primaryCode ==  964) {return 120533;} // 𝛕
        if (Variables.is120432()    && primaryCode ==  965) {return 120534;} // 𝛖
        if (Variables.is120432()    && primaryCode ==  966) {return 120535;} // 𝛗
        if (Variables.is120432()    && primaryCode ==  967) {return 120536;} // 𝛘
        if (Variables.is120432()    && primaryCode ==  968) {return 120537;} // 𝛙
        if (Variables.is120432()    && primaryCode ==  969) {return 120538;} // 𝛚
        if (Variables.is120432()    && primaryCode ==  977) {return 120541;} // 𝛝
        if (Variables.is120432()    && primaryCode ==  981) {return 120543;} // 𝛟
        if (Variables.is120432()    && primaryCode ==  982) {return 120545;} // 𝛡
        if (Variables.is120432()    && primaryCode == 1008) {return 120542;} // 𝛞
        if (Variables.is120432()    && primaryCode == 1009) {return 120544;} // 𝛠
        if (Variables.is120432()    && primaryCode == 1012) {return 120505;} // 𝚹
        if (Variables.is120432()    && primaryCode == 1013) {return 120540;} // 𝛜
        if (Variables.is120432()    && primaryCode == 8706) {return 120539;} // 𝛛
        if (Variables.is120432()    && primaryCode == 8711) {return 120513;} // 𝛁
        if (Variables.is127280()    && primaryCode ==   65) {return 127280;} // 🄰
        if (Variables.is127280()    && primaryCode ==   66) {return 127281;} // 🄱
        if (Variables.is127280()    && primaryCode ==   67) {return 127282;} // 🄲
        if (Variables.is127280()    && primaryCode ==   68) {return 127283;} // 🄳
        if (Variables.is127280()    && primaryCode ==   69) {return 127284;} // 🄴
        if (Variables.is127280()    && primaryCode ==   70) {return 127285;} // 🄵
        if (Variables.is127280()    && primaryCode ==   71) {return 127286;} // 🄶
        if (Variables.is127280()    && primaryCode ==   72) {return 127287;} // 🄷
        if (Variables.is127280()    && primaryCode ==   73) {return 127288;} // 🄸
        if (Variables.is127280()    && primaryCode ==   74) {return 127289;} // 🄹
        if (Variables.is127280()    && primaryCode ==   75) {return 127290;} // 🄺
        if (Variables.is127280()    && primaryCode ==   76) {return 127291;} // 🄻
        if (Variables.is127280()    && primaryCode ==   77) {return 127292;} // 🄼
        if (Variables.is127280()    && primaryCode ==   78) {return 127293;} // 🄽
        if (Variables.is127280()    && primaryCode ==   79) {return 127294;} // 🄾
        if (Variables.is127280()    && primaryCode ==   80) {return 127295;} // 🄿
        if (Variables.is127280()    && primaryCode ==   81) {return 127296;} // 🅀
        if (Variables.is127280()    && primaryCode ==   82) {return 127297;} // 🅁
        if (Variables.is127280()    && primaryCode ==   83) {return 127298;} // 🅂
        if (Variables.is127280()    && primaryCode ==   84) {return 127299;} // 🅃
        if (Variables.is127280()    && primaryCode ==   85) {return 127300;} // 🅄
        if (Variables.is127280()    && primaryCode ==   86) {return 127301;} // 🅅
        if (Variables.is127280()    && primaryCode ==   87) {return 127302;} // 🅆
        if (Variables.is127280()    && primaryCode ==   88) {return 127303;} // 🅇
        if (Variables.is127280()    && primaryCode ==   89) {return 127304;} // 🅈
        if (Variables.is127280()    && primaryCode ==   90) {return 127305;} // 🅉
        if (Variables.is127280()    && primaryCode ==   97) {return 127280;} // 🄰
        if (Variables.is127280()    && primaryCode ==   98) {return 127281;} // 🄱
        if (Variables.is127280()    && primaryCode ==   99) {return 127282;} // 🄲
        if (Variables.is127280()    && primaryCode ==  100) {return 127283;} // 🄳
        if (Variables.is127280()    && primaryCode ==  101) {return 127284;} // 🄴
        if (Variables.is127280()    && primaryCode ==  102) {return 127285;} // 🄵
        if (Variables.is127280()    && primaryCode ==  103) {return 127286;} // 🄶
        if (Variables.is127280()    && primaryCode ==  104) {return 127287;} // 🄷
        if (Variables.is127280()    && primaryCode ==  105) {return 127288;} // 🄸
        if (Variables.is127280()    && primaryCode ==  106) {return 127289;} // 🄹
        if (Variables.is127280()    && primaryCode ==  107) {return 127290;} // 🄺
        if (Variables.is127280()    && primaryCode ==  108) {return 127291;} // 🄻
        if (Variables.is127280()    && primaryCode ==  109) {return 127292;} // 🄼
        if (Variables.is127280()    && primaryCode ==  110) {return 127293;} // 🄽
        if (Variables.is127280()    && primaryCode ==  111) {return 127294;} // 🄾
        if (Variables.is127280()    && primaryCode ==  112) {return 127295;} // 🄿
        if (Variables.is127280()    && primaryCode ==  113) {return 127296;} // 🅀
        if (Variables.is127280()    && primaryCode ==  114) {return 127297;} // 🅁
        if (Variables.is127280()    && primaryCode ==  115) {return 127298;} // 🅂
        if (Variables.is127280()    && primaryCode ==  116) {return 127299;} // 🅃
        if (Variables.is127280()    && primaryCode ==  117) {return 127300;} // 🅄
        if (Variables.is127280()    && primaryCode ==  118) {return 127301;} // 🅅
        if (Variables.is127280()    && primaryCode ==  119) {return 127302;} // 🅆
        if (Variables.is127280()    && primaryCode ==  120) {return 127303;} // 🅇
        if (Variables.is127280()    && primaryCode ==  121) {return 127304;} // 🅈
        if (Variables.is127280()    && primaryCode ==  122) {return 127305;} // 🅉
        if (Variables.is127312()    && primaryCode ==   65) {return 127312;} // 🅐
        if (Variables.is127312()    && primaryCode ==   66) {return 127313;} // 🅑
        if (Variables.is127312()    && primaryCode ==   67) {return 127314;} // 🅒
        if (Variables.is127312()    && primaryCode ==   68) {return 127315;} // 🅓
        if (Variables.is127312()    && primaryCode ==   69) {return 127316;} // 🅔
        if (Variables.is127312()    && primaryCode ==   70) {return 127317;} // 🅕
        if (Variables.is127312()    && primaryCode ==   71) {return 127318;} // 🅖
        if (Variables.is127312()    && primaryCode ==   72) {return 127319;} // 🅗
        if (Variables.is127312()    && primaryCode ==   73) {return 127320;} // 🅘
        if (Variables.is127312()    && primaryCode ==   74) {return 127321;} // 🅙
        if (Variables.is127312()    && primaryCode ==   75) {return 127322;} // 🅚
        if (Variables.is127312()    && primaryCode ==   76) {return 127323;} // 🅛
        if (Variables.is127312()    && primaryCode ==   77) {return 127324;} // 🅜
        if (Variables.is127312()    && primaryCode ==   78) {return 127325;} // 🅝
        if (Variables.is127312()    && primaryCode ==   79) {return 127326;} // 🅞
        if (Variables.is127312()    && primaryCode ==   80) {return 127327;} // 🅟
        if (Variables.is127312()    && primaryCode ==   81) {return 127328;} // 🅠
        if (Variables.is127312()    && primaryCode ==   82) {return 127329;} // 🅡
        if (Variables.is127312()    && primaryCode ==   83) {return 127330;} // 🅢
        if (Variables.is127312()    && primaryCode ==   84) {return 127331;} // 🅣
        if (Variables.is127312()    && primaryCode ==   85) {return 127332;} // 🅤
        if (Variables.is127312()    && primaryCode ==   86) {return 127333;} // 🅥
        if (Variables.is127312()    && primaryCode ==   87) {return 127334;} // 🅦
        if (Variables.is127312()    && primaryCode ==   88) {return 127335;} // 🅧
        if (Variables.is127312()    && primaryCode ==   89) {return 127336;} // 🅨
        if (Variables.is127312()    && primaryCode ==   90) {return 127337;} // 🅩
        if (Variables.is127312()    && primaryCode ==   97) {return 127312;} // 🅐
        if (Variables.is127312()    && primaryCode ==   98) {return 127313;} // 🅑
        if (Variables.is127312()    && primaryCode ==   99) {return 127314;} // 🅒
        if (Variables.is127312()    && primaryCode ==  100) {return 127315;} // 🅓
        if (Variables.is127312()    && primaryCode ==  101) {return 127316;} // 🅔
        if (Variables.is127312()    && primaryCode ==  102) {return 127317;} // 🅕
        if (Variables.is127312()    && primaryCode ==  103) {return 127318;} // 🅖
        if (Variables.is127312()    && primaryCode ==  104) {return 127319;} // 🅗
        if (Variables.is127312()    && primaryCode ==  105) {return 127320;} // 🅘
        if (Variables.is127312()    && primaryCode ==  106) {return 127321;} // 🅙
        if (Variables.is127312()    && primaryCode ==  107) {return 127322;} // 🅚
        if (Variables.is127312()    && primaryCode ==  108) {return 127323;} // 🅛
        if (Variables.is127312()    && primaryCode ==  109) {return 127324;} // 🅜
        if (Variables.is127312()    && primaryCode ==  110) {return 127325;} // 🅝
        if (Variables.is127312()    && primaryCode ==  111) {return 127326;} // 🅞
        if (Variables.is127312()    && primaryCode ==  112) {return 127327;} // 🅟
        if (Variables.is127312()    && primaryCode ==  113) {return 127328;} // 🅠
        if (Variables.is127312()    && primaryCode ==  114) {return 127329;} // 🅡
        if (Variables.is127312()    && primaryCode ==  115) {return 127330;} // 🅢
        if (Variables.is127312()    && primaryCode ==  116) {return 127331;} // 🅣
        if (Variables.is127312()    && primaryCode ==  117) {return 127332;} // 🅤
        if (Variables.is127312()    && primaryCode ==  118) {return 127333;} // 🅥
        if (Variables.is127312()    && primaryCode ==  119) {return 127334;} // 🅦
        if (Variables.is127312()    && primaryCode ==  120) {return 127335;} // 🅧
        if (Variables.is127312()    && primaryCode ==  121) {return 127336;} // 🅨
        if (Variables.is127312()    && primaryCode ==  122) {return 127337;} // 🅩
        if (Variables.is127344()    && primaryCode ==   65) {return 127344;} // 🅰
        if (Variables.is127344()    && primaryCode ==   66) {return 127345;} // 🅱
        if (Variables.is127344()    && primaryCode ==   67) {return 127346;} // 🅲
        if (Variables.is127344()    && primaryCode ==   68) {return 127347;} // 🅳
        if (Variables.is127344()    && primaryCode ==   69) {return 127348;} // 🅴
        if (Variables.is127344()    && primaryCode ==   70) {return 127349;} // 🅵
        if (Variables.is127344()    && primaryCode ==   71) {return 127350;} // 🅶
        if (Variables.is127344()    && primaryCode ==   72) {return 127351;} // 🅷
        if (Variables.is127344()    && primaryCode ==   73) {return 127352;} // 🅸
        if (Variables.is127344()    && primaryCode ==   74) {return 127353;} // 🅹
        if (Variables.is127344()    && primaryCode ==   75) {return 127354;} // 🅺
        if (Variables.is127344()    && primaryCode ==   76) {return 127355;} // 🅻
        if (Variables.is127344()    && primaryCode ==   77) {return 127356;} // 🅼
        if (Variables.is127344()    && primaryCode ==   78) {return 127357;} // 🅽
        if (Variables.is127344()    && primaryCode ==   79) {return 127358;} // 🅾
        if (Variables.is127344()    && primaryCode ==   80) {return 127359;} // 🅿
        if (Variables.is127344()    && primaryCode ==   81) {return 127360;} // 🆀
        if (Variables.is127344()    && primaryCode ==   82) {return 127361;} // 🆁
        if (Variables.is127344()    && primaryCode ==   83) {return 127362;} // 🆂
        if (Variables.is127344()    && primaryCode ==   84) {return 127363;} // 🆃
        if (Variables.is127344()    && primaryCode ==   85) {return 127364;} // 🆄
        if (Variables.is127344()    && primaryCode ==   86) {return 127365;} // 🆅
        if (Variables.is127344()    && primaryCode ==   87) {return 127366;} // 🆆
        if (Variables.is127344()    && primaryCode ==   88) {return 127367;} // 🆇
        if (Variables.is127344()    && primaryCode ==   89) {return 127368;} // 🆈
        if (Variables.is127344()    && primaryCode ==   90) {return 127369;} // 🆉
        if (Variables.is127344()    && primaryCode ==   97) {return 127344;} // 🅰
        if (Variables.is127344()    && primaryCode ==   98) {return 127345;} // 🅱
        if (Variables.is127344()    && primaryCode ==   99) {return 127346;} // 🅲
        if (Variables.is127344()    && primaryCode ==  100) {return 127347;} // 🅳
        if (Variables.is127344()    && primaryCode ==  101) {return 127348;} // 🅴
        if (Variables.is127344()    && primaryCode ==  102) {return 127349;} // 🅵
        if (Variables.is127344()    && primaryCode ==  103) {return 127350;} // 🅶
        if (Variables.is127344()    && primaryCode ==  104) {return 127351;} // 🅷
        if (Variables.is127344()    && primaryCode ==  105) {return 127352;} // 🅸
        if (Variables.is127344()    && primaryCode ==  106) {return 127353;} // 🅹
        if (Variables.is127344()    && primaryCode ==  107) {return 127354;} // 🅺
        if (Variables.is127344()    && primaryCode ==  108) {return 127355;} // 🅻
        if (Variables.is127344()    && primaryCode ==  109) {return 127356;} // 🅼
        if (Variables.is127344()    && primaryCode ==  110) {return 127357;} // 🅽
        if (Variables.is127344()    && primaryCode ==  111) {return 127358;} // 🅾
        if (Variables.is127344()    && primaryCode ==  112) {return 127359;} // 🅿
        if (Variables.is127344()    && primaryCode ==  113) {return 127360;} // 🆀
        if (Variables.is127344()    && primaryCode ==  114) {return 127361;} // 🆁
        if (Variables.is127344()    && primaryCode ==  115) {return 127362;} // 🆂
        if (Variables.is127344()    && primaryCode ==  116) {return 127363;} // 🆃
        if (Variables.is127344()    && primaryCode ==  117) {return 127364;} // 🆄
        if (Variables.is127344()    && primaryCode ==  118) {return 127365;} // 🆅
        if (Variables.is127344()    && primaryCode ==  119) {return 127366;} // 🆆
        if (Variables.is127344()    && primaryCode ==  120) {return 127367;} // 🆇
        if (Variables.is127344()    && primaryCode ==  121) {return 127368;} // 🆈
        if (Variables.is127344()    && primaryCode ==  122) {return 127369;} // 🆉
        if (Variables.is127462()    && primaryCode ==   65) {return 127462;} // 🇦
        if (Variables.is127462()    && primaryCode ==   66) {return 127463;} // 🇧
        if (Variables.is127462()    && primaryCode ==   67) {return 127464;} // 🇨
        if (Variables.is127462()    && primaryCode ==   68) {return 127465;} // 🇩
        if (Variables.is127462()    && primaryCode ==   69) {return 127466;} // 🇪
        if (Variables.is127462()    && primaryCode ==   70) {return 127467;} // 🇫
        if (Variables.is127462()    && primaryCode ==   71) {return 127468;} // 🇬
        if (Variables.is127462()    && primaryCode ==   72) {return 127469;} // 🇭
        if (Variables.is127462()    && primaryCode ==   73) {return 127470;} // 🇮
        if (Variables.is127462()    && primaryCode ==   74) {return 127471;} // 🇯
        if (Variables.is127462()    && primaryCode ==   75) {return 127472;} // 🇰
        if (Variables.is127462()    && primaryCode ==   76) {return 127473;} // 🇱
        if (Variables.is127462()    && primaryCode ==   77) {return 127474;} // 🇲
        if (Variables.is127462()    && primaryCode ==   78) {return 127475;} // 🇳
        if (Variables.is127462()    && primaryCode ==   79) {return 127476;} // 🇴
        if (Variables.is127462()    && primaryCode ==   80) {return 127477;} // 🇵
        if (Variables.is127462()    && primaryCode ==   81) {return 127478;} // 🇶
        if (Variables.is127462()    && primaryCode ==   82) {return 127479;} // 🇷
        if (Variables.is127462()    && primaryCode ==   83) {return 127480;} // 🇸
        if (Variables.is127462()    && primaryCode ==   84) {return 127481;} // 🇹
        if (Variables.is127462()    && primaryCode ==   85) {return 127482;} // 🇺
        if (Variables.is127462()    && primaryCode ==   86) {return 127483;} // 🇻
        if (Variables.is127462()    && primaryCode ==   87) {return 127484;} // 🇼
        if (Variables.is127462()    && primaryCode ==   88) {return 127485;} // 🇽
        if (Variables.is127462()    && primaryCode ==   89) {return 127486;} // 🇾
        if (Variables.is127462()    && primaryCode ==   90) {return 127487;} // 🇿
        if (Variables.is127462()    && primaryCode ==   97) {return 127462;} // 🇦
        if (Variables.is127462()    && primaryCode ==   98) {return 127463;} // 🇧
        if (Variables.is127462()    && primaryCode ==   99) {return 127464;} // 🇨
        if (Variables.is127462()    && primaryCode ==  100) {return 127465;} // 🇩
        if (Variables.is127462()    && primaryCode ==  101) {return 127466;} // 🇪
        if (Variables.is127462()    && primaryCode ==  102) {return 127467;} // 🇫
        if (Variables.is127462()    && primaryCode ==  103) {return 127468;} // 🇬
        if (Variables.is127462()    && primaryCode ==  104) {return 127469;} // 🇭
        if (Variables.is127462()    && primaryCode ==  105) {return 127470;} // 🇮
        if (Variables.is127462()    && primaryCode ==  106) {return 127471;} // 🇯
        if (Variables.is127462()    && primaryCode ==  107) {return 127472;} // 🇰
        if (Variables.is127462()    && primaryCode ==  108) {return 127473;} // 🇱
        if (Variables.is127462()    && primaryCode ==  109) {return 127474;} // 🇲
        if (Variables.is127462()    && primaryCode ==  110) {return 127475;} // 🇳
        if (Variables.is127462()    && primaryCode ==  111) {return 127476;} // 🇴
        if (Variables.is127462()    && primaryCode ==  112) {return 127477;} // 🇵
        if (Variables.is127462()    && primaryCode ==  113) {return 127478;} // 🇶
        if (Variables.is127462()    && primaryCode ==  114) {return 127479;} // 🇷
        if (Variables.is127462()    && primaryCode ==  115) {return 127480;} // 🇸
        if (Variables.is127462()    && primaryCode ==  116) {return 127481;} // 🇹
        if (Variables.is127462()    && primaryCode ==  117) {return 127482;} // 🇺
        if (Variables.is127462()    && primaryCode ==  118) {return 127483;} // 🇻
        if (Variables.is127462()    && primaryCode ==  119) {return 127484;} // 🇼
        if (Variables.is127462()    && primaryCode ==  120) {return 127485;} // 🇽
        if (Variables.is127462()    && primaryCode ==  121) {return 127486;} // 🇾
        if (Variables.is127462()    && primaryCode ==  122) {return 127487;} // 🇿
        if (Variables.isReflected() && primaryCode ==   33) {return    161;} // ¡
        if (Variables.isReflected() && primaryCode ==   40) {return     41;} // )
        if (Variables.isReflected() && primaryCode ==   41) {return     40;} // (
        if (Variables.isReflected() && primaryCode ==   60) {return     62;} // >
        if (Variables.isReflected() && primaryCode ==   62) {return     60;} // <
        if (Variables.isReflected() && primaryCode ==   63) {return    191;} // ¿
        if (Variables.isReflected() && primaryCode ==   65) {return  11375;} // Ɐ
        if (Variables.isReflected() && primaryCode ==   66) {return  42221;} // ꓭ
        if (Variables.isReflected() && primaryCode ==   67) {return    390;} // Ɔ
        if (Variables.isReflected() && primaryCode ==   68) {return  42231;} // ꓷ
        if (Variables.isReflected() && primaryCode ==   69) {return    398;} // Ǝ
        if (Variables.isReflected() && primaryCode ==   70) {return   8498;} // Ⅎ
        if (Variables.isReflected() && primaryCode ==   71) {return  42216;} // ꓨ
        if (Variables.isReflected() && primaryCode ==   72) {return     72;} // H
        if (Variables.isReflected() && primaryCode ==   73) {return     73;} // I
        if (Variables.isReflected() && primaryCode ==   74) {return   1360;} // Ր
        if (Variables.isReflected() && primaryCode ==   75) {return  42928;} // Ʞ
        if (Variables.isReflected() && primaryCode ==   76) {return   8514;} // ⅂
        if (Variables.isReflected() && primaryCode ==   77) {return  43005;} // ꟽ
        if (Variables.isReflected() && primaryCode ==   78) {return     78;} // N
        if (Variables.isReflected() && primaryCode ==   79) {return     79;} // O
        if (Variables.isReflected() && primaryCode ==   80) {return   1280;} // Ԁ
        if (Variables.isReflected() && primaryCode ==   81) {return    210;} // Ò
        if (Variables.isReflected() && primaryCode ==   82) {return  42212;} // ꓤ
        if (Variables.isReflected() && primaryCode ==   83) {return     83;} // S
        if (Variables.isReflected() && primaryCode ==   84) {return  42929;} // Ʇ
        if (Variables.isReflected() && primaryCode ==   85) {return 119365;} // 𝉅
        if (Variables.isReflected() && primaryCode ==   86) {return    581;} // Ʌ
        if (Variables.isReflected() && primaryCode ==   87) {return  66224;} // 𐊰
        if (Variables.isReflected() && primaryCode ==   88) {return     88;} // X
        if (Variables.isReflected() && primaryCode ==   89) {return   8516;} // ⅄
        if (Variables.isReflected() && primaryCode ==   90) {return     90;} // Z
        if (Variables.isReflected() && primaryCode ==   91) {return     93;} // ]
        if (Variables.isReflected() && primaryCode ==   93) {return     91;} // [
        if (Variables.isReflected() && primaryCode ==   97) {return    592;} // ɐ
        if (Variables.isReflected() && primaryCode ==   98) {return    113;} // q
        if (Variables.isReflected() && primaryCode ==   99) {return    596;} // ɔ
        if (Variables.isReflected() && primaryCode ==  100) {return    112;} // p
        if (Variables.isReflected() && primaryCode ==  101) {return    601;} // ə
        if (Variables.isReflected() && primaryCode ==  102) {return    607;} // ɟ
        if (Variables.isReflected() && primaryCode ==  103) {return   7543;} // ᵷ
        if (Variables.isReflected() && primaryCode ==  104) {return    613;} // ɥ
        if (Variables.isReflected() && primaryCode ==  105) {return   7433;} // ᴉ
        if (Variables.isReflected() && primaryCode ==  106) {return    383;} // ſ
        if (Variables.isReflected() && primaryCode ==  107) {return    670;} // ʞ
        if (Variables.isReflected() && primaryCode ==  108) {return  42881;} // ꞁ
        if (Variables.isReflected() && primaryCode ==  109) {return    623;} // ɯ
        if (Variables.isReflected() && primaryCode ==  110) {return    117;} // u
        if (Variables.isReflected() && primaryCode ==  111) {return    111;} // o
        if (Variables.isReflected() && primaryCode ==  112) {return    100;} // d
        if (Variables.isReflected() && primaryCode ==  113) {return     98;} // b
        if (Variables.isReflected() && primaryCode ==  114) {return    633;} // ɹ
        if (Variables.isReflected() && primaryCode ==  115) {return    115;} // s
        if (Variables.isReflected() && primaryCode ==  116) {return    647;} // ʇ
        if (Variables.isReflected() && primaryCode ==  117) {return    110;} // n
        if (Variables.isReflected() && primaryCode ==  118) {return    652;} // ʌ
        if (Variables.isReflected() && primaryCode ==  119) {return    653;} // ʍ
        if (Variables.isReflected() && primaryCode ==  120) {return    120;} // x
        if (Variables.isReflected() && primaryCode ==  121) {return    654;} // ʎ
        if (Variables.isReflected() && primaryCode ==  122) {return    122;} // z
        if (Variables.isReflected() && primaryCode ==  123) {return    125;} // }
        if (Variables.isReflected() && primaryCode ==  125) {return    123;} // {
        if (Variables.isReflected() && primaryCode ==  171) {return    187;} // »
        if (Variables.isReflected() && primaryCode ==  187) {return    171;} // «
        if (Variables.isCaps()      && primaryCode ==   65) {return     65;} // A
        if (Variables.isCaps()      && primaryCode ==   66) {return     66;} // B
        if (Variables.isCaps()      && primaryCode ==   67) {return     67;} // C
        if (Variables.isCaps()      && primaryCode ==   68) {return     68;} // D
        if (Variables.isCaps()      && primaryCode ==   69) {return     69;} // E
        if (Variables.isCaps()      && primaryCode ==   70) {return     70;} // F
        if (Variables.isCaps()      && primaryCode ==   71) {return     71;} // G
        if (Variables.isCaps()      && primaryCode ==   72) {return     72;} // H
        if (Variables.isCaps()      && primaryCode ==   73) {return     73;} // I
        if (Variables.isCaps()      && primaryCode ==   74) {return     74;} // J
        if (Variables.isCaps()      && primaryCode ==   75) {return     75;} // K
        if (Variables.isCaps()      && primaryCode ==   76) {return     76;} // L
        if (Variables.isCaps()      && primaryCode ==   77) {return     77;} // M
        if (Variables.isCaps()      && primaryCode ==   78) {return     78;} // N
        if (Variables.isCaps()      && primaryCode ==   79) {return     79;} // O
        if (Variables.isCaps()      && primaryCode ==   80) {return     80;} // P
        if (Variables.isCaps()      && primaryCode ==   81) {return   1192;} // Ҩ
        if (Variables.isCaps()      && primaryCode ==   82) {return     82;} // R
        if (Variables.isCaps()      && primaryCode ==   83) {return     83;} // S
        if (Variables.isCaps()      && primaryCode ==   84) {return     84;} // T
        if (Variables.isCaps()      && primaryCode ==   85) {return     85;} // U
        if (Variables.isCaps()      && primaryCode ==   86) {return     86;} // V
        if (Variables.isCaps()      && primaryCode ==   87) {return     87;} // W
        if (Variables.isCaps()      && primaryCode ==   88) {return     88;} // X
        if (Variables.isCaps()      && primaryCode ==   89) {return     89;} // Y
        if (Variables.isCaps()      && primaryCode ==   90) {return     90;} // Z
        if (Variables.isCaps()      && primaryCode ==   97) {return   7424;} // ᴀ
        if (Variables.isCaps()      && primaryCode ==   98) {return    665;} // ʙ
        if (Variables.isCaps()      && primaryCode ==   99) {return   7428;} // ᴄ
        if (Variables.isCaps()      && primaryCode ==  100) {return   7429;} // ᴅ
        if (Variables.isCaps()      && primaryCode ==  101) {return   7431;} // ᴇ
        if (Variables.isCaps()      && primaryCode ==  102) {return  42800;} // ꜰ
        if (Variables.isCaps()      && primaryCode ==  103) {return    610;} // ɢ
        if (Variables.isCaps()      && primaryCode ==  104) {return    668;} // ʜ
        if (Variables.isCaps()      && primaryCode ==  105) {return    618;} // ɪ
        if (Variables.isCaps()      && primaryCode ==  106) {return   7434;} // ᴊ
        if (Variables.isCaps()      && primaryCode ==  107) {return   7435;} // ᴋ
        if (Variables.isCaps()      && primaryCode ==  108) {return    671;} // ʟ
        if (Variables.isCaps()      && primaryCode ==  109) {return   7437;} // ᴍ
        if (Variables.isCaps()      && primaryCode ==  110) {return    628;} // ɴ
        if (Variables.isCaps()      && primaryCode ==  111) {return   7439;} // ᴏ
        if (Variables.isCaps()      && primaryCode ==  112) {return   7448;} // ᴘ
        if (Variables.isCaps()      && primaryCode ==  113) {return   1193;} // ҩ
        if (Variables.isCaps()      && primaryCode ==  114) {return    640;} // ʀ
        if (Variables.isCaps()      && primaryCode ==  115) {return  42801;} // ꜱ
        if (Variables.isCaps()      && primaryCode ==  116) {return   7451;} // ᴛ
        if (Variables.isCaps()      && primaryCode ==  117) {return   7452;} // ᴜ
        if (Variables.isCaps()      && primaryCode ==  118) {return   7456;} // ᴠ
        if (Variables.isCaps()      && primaryCode ==  119) {return   7457;} // ᴡ
        if (Variables.isCaps()      && primaryCode ==  120) {return    120;} // x
        if (Variables.isCaps()      && primaryCode ==  121) {return    655;} // ʏ
        if (Variables.isCaps()      && primaryCode ==  122) {return   7458;} // ᴢ

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
