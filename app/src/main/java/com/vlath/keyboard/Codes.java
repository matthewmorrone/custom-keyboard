package com.vlath.keyboard;

import android.view.KeyEvent;

class Codes {

    static final int[] hexPasses = {
         -5, -7, -107, -108, -109, -111, -101, -102, -103, -8, -9, -10, -11, -499, 32, 9, 10
    };
    static final int[] hexCaptures = {
         48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70
    };

    static int getHardKeyCode(int keycode) {
        char code = (char) keycode;
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

    static int handleCharacter(CustomKeyboard kv, int primaryCode) {
        if (Util.isAlphaNumeric(primaryCode)) {
            if (Util.isDigit(primaryCode)) {
                if (Variables.isBold()) {
                    primaryCode += 120764;
                }
            }
            else if (kv.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
                if (Variables.isBold() && Variables.isItalic()) {
                    primaryCode += 120315;
                }
                else if (Variables.isBold()) {
                    primaryCode += 120211;
                }
                else if (Variables.isItalic()) {
                    primaryCode += 120263;
                }
            }
            else {
                if (Variables.isBold() && Variables.isItalic()) {
                    primaryCode += 120309;
                }
                else if (Variables.isBold()) {
                    primaryCode += 120205;
                }
                else if (Variables.isItalic()) {
                    primaryCode += 120257;
                }
            }

            if (Variables.is119808()) {
                if (kv.isShifted()) {
                    primaryCode += (119808 - 65);
                }
                else {
                    primaryCode += (119808 - 71);
                }
            }
            if (Variables.is119860()) {
                if (kv.isShifted()) {
                    primaryCode += (119860 - 65);
                }
                else {
                    primaryCode += (119860 - 71);
                }
            }
            if (Variables.is119912()) {
                if (kv.isShifted()) {
                    primaryCode += (119912 - 65);
                }
                else {
                    primaryCode += (119912 - 71);
                }
            }
            if (Variables.is119964()) {
                if (kv.isShifted()) {
                    primaryCode += (119964 - 65);
                }
                else {
                    primaryCode += (119964 - 71);
                }
            }
            if (Variables.is120016()) {
                if (kv.isShifted()) {
                    primaryCode += (120016 - 65);
                }
                else {
                    primaryCode += (120016 - 71);
                }
            }
            if (Variables.is120068()) {
                if (kv.isShifted()) {
                    primaryCode += (120068 - 65);
                }
                else {
                    primaryCode += (120068 - 71);
                }
            }
            if (Variables.is120120()) {
                if (kv.isShifted()) {
                    primaryCode += (120120 - 65);
                }
                else {
                    primaryCode += (120120 - 71);
                }
            }
            if (Variables.is120172()) {
                if (kv.isShifted()) {
                    primaryCode += (120172 - 65);
                }
                else {
                    primaryCode += (120172 - 71);
                }
            }
            if (Variables.is120224()) {
                if (kv.isShifted()) {
                    primaryCode += (120224 - 65);
                }
                else {
                    primaryCode += (120224 - 71);
                }
            }
            if (Variables.is120276()) {
                if (kv.isShifted()) {
                    primaryCode += (120276 - 65);
                }
                else {
                    primaryCode += (120276 - 71);
                }
            }
            if (Variables.is120328()) {
                if (kv.isShifted()) {
                    primaryCode += (120328 - 65);
                }
                else {
                    primaryCode += (120328 - 71);
                }
            }
            if (Variables.is120380()) {
                if (kv.isShifted()) {
                    primaryCode += (120380 - 65);
                }
                else {
                    primaryCode += (120380 - 71);
                }
            }
            if (Variables.is120432()) {
                if (kv.isShifted()) {
                    primaryCode += (120432 - 65);
                }
                else {
                    primaryCode += (120432 - 71);
                }
            }
        }
        return blockException(primaryCode);
    }


    private static int blockException(int primaryCode) {
        if (Variables.is009372()      && primaryCode ==   65) {return   9372;}
        if (Variables.is009372()      && primaryCode ==   66) {return   9373;}
        if (Variables.is009372()      && primaryCode ==   67) {return   9374;}
        if (Variables.is009372()      && primaryCode ==   68) {return   9375;}
        if (Variables.is009372()      && primaryCode ==   69) {return   9376;}
        if (Variables.is009372()      && primaryCode ==   70) {return   9377;}
        if (Variables.is009372()      && primaryCode ==   71) {return   9378;}
        if (Variables.is009372()      && primaryCode ==   72) {return   9379;}
        if (Variables.is009372()      && primaryCode ==   73) {return   9380;}
        if (Variables.is009372()      && primaryCode ==   74) {return   9381;}
        if (Variables.is009372()      && primaryCode ==   75) {return   9382;}
        if (Variables.is009372()      && primaryCode ==   76) {return   9383;}
        if (Variables.is009372()      && primaryCode ==   77) {return   9384;}
        if (Variables.is009372()      && primaryCode ==   78) {return   9385;}
        if (Variables.is009372()      && primaryCode ==   79) {return   9386;}
        if (Variables.is009372()      && primaryCode ==   80) {return   9387;}
        if (Variables.is009372()      && primaryCode ==   81) {return   9388;}
        if (Variables.is009372()      && primaryCode ==   82) {return   9389;}
        if (Variables.is009372()      && primaryCode ==   83) {return   9390;}
        if (Variables.is009372()      && primaryCode ==   84) {return   9391;}
        if (Variables.is009372()      && primaryCode ==   85) {return   9392;}
        if (Variables.is009372()      && primaryCode ==   86) {return   9393;}
        if (Variables.is009372()      && primaryCode ==   87) {return   9394;}
        if (Variables.is009372()      && primaryCode ==   88) {return   9395;}
        if (Variables.is009372()      && primaryCode ==   89) {return   9396;}
        if (Variables.is009372()      && primaryCode ==   90) {return   9397;}
        if (Variables.is009372()      && primaryCode ==   97) {return   9372;}
        if (Variables.is009372()      && primaryCode ==   98) {return   9373;}
        if (Variables.is009372()      && primaryCode ==   99) {return   9374;}
        if (Variables.is009372()      && primaryCode ==  100) {return   9375;}
        if (Variables.is009372()      && primaryCode ==  101) {return   9376;}
        if (Variables.is009372()      && primaryCode ==  102) {return   9377;}
        if (Variables.is009372()      && primaryCode ==  103) {return   9378;}
        if (Variables.is009372()      && primaryCode ==  104) {return   9379;}
        if (Variables.is009372()      && primaryCode ==  105) {return   9380;}
        if (Variables.is009372()      && primaryCode ==  106) {return   9381;}
        if (Variables.is009372()      && primaryCode ==  107) {return   9382;}
        if (Variables.is009372()      && primaryCode ==  108) {return   9383;}
        if (Variables.is009372()      && primaryCode ==  109) {return   9384;}
        if (Variables.is009372()      && primaryCode ==  110) {return   9385;}
        if (Variables.is009372()      && primaryCode ==  111) {return   9386;}
        if (Variables.is009372()      && primaryCode ==  112) {return   9387;}
        if (Variables.is009372()      && primaryCode ==  113) {return   9388;}
        if (Variables.is009372()      && primaryCode ==  114) {return   9389;}
        if (Variables.is009372()      && primaryCode ==  115) {return   9390;}
        if (Variables.is009372()      && primaryCode ==  116) {return   9391;}
        if (Variables.is009372()      && primaryCode ==  117) {return   9392;}
        if (Variables.is009372()      && primaryCode ==  118) {return   9393;}
        if (Variables.is009372()      && primaryCode ==  119) {return   9394;}
        if (Variables.is009372()      && primaryCode ==  120) {return   9395;}
        if (Variables.is009372()      && primaryCode ==  121) {return   9396;}
        if (Variables.is009372()      && primaryCode ==  122) {return   9397;}
        if (Variables.is009398()      && primaryCode ==   65) {return   9398;}
        if (Variables.is009398()      && primaryCode ==   66) {return   9399;}
        if (Variables.is009398()      && primaryCode ==   67) {return   9400;}
        if (Variables.is009398()      && primaryCode ==   68) {return   9401;}
        if (Variables.is009398()      && primaryCode ==   69) {return   9402;}
        if (Variables.is009398()      && primaryCode ==   70) {return   9403;}
        if (Variables.is009398()      && primaryCode ==   71) {return   9404;}
        if (Variables.is009398()      && primaryCode ==   72) {return   9405;}
        if (Variables.is009398()      && primaryCode ==   73) {return   9406;}
        if (Variables.is009398()      && primaryCode ==   74) {return   9407;}
        if (Variables.is009398()      && primaryCode ==   75) {return   9408;}
        if (Variables.is009398()      && primaryCode ==   76) {return   9409;}
        if (Variables.is009398()      && primaryCode ==   77) {return   9410;}
        if (Variables.is009398()      && primaryCode ==   78) {return   9411;}
        if (Variables.is009398()      && primaryCode ==   79) {return   9412;}
        if (Variables.is009398()      && primaryCode ==   80) {return   9413;}
        if (Variables.is009398()      && primaryCode ==   81) {return   9414;}
        if (Variables.is009398()      && primaryCode ==   82) {return   9415;}
        if (Variables.is009398()      && primaryCode ==   83) {return   9416;}
        if (Variables.is009398()      && primaryCode ==   84) {return   9417;}
        if (Variables.is009398()      && primaryCode ==   85) {return   9418;}
        if (Variables.is009398()      && primaryCode ==   86) {return   9419;}
        if (Variables.is009398()      && primaryCode ==   87) {return   9420;}
        if (Variables.is009398()      && primaryCode ==   88) {return   9421;}
        if (Variables.is009398()      && primaryCode ==   89) {return   9422;}
        if (Variables.is009398()      && primaryCode ==   90) {return   9423;}
        if (Variables.is009398()      && primaryCode ==   97) {return   9424;}
        if (Variables.is009398()      && primaryCode ==   98) {return   9425;}
        if (Variables.is009398()      && primaryCode ==   99) {return   9426;}
        if (Variables.is009398()      && primaryCode ==  100) {return   9427;}
        if (Variables.is009398()      && primaryCode ==  101) {return   9428;}
        if (Variables.is009398()      && primaryCode ==  102) {return   9429;}
        if (Variables.is009398()      && primaryCode ==  103) {return   9430;}
        if (Variables.is009398()      && primaryCode ==  104) {return   9431;}
        if (Variables.is009398()      && primaryCode ==  105) {return   9432;}
        if (Variables.is009398()      && primaryCode ==  106) {return   9433;}
        if (Variables.is009398()      && primaryCode ==  107) {return   9434;}
        if (Variables.is009398()      && primaryCode ==  108) {return   9435;}
        if (Variables.is009398()      && primaryCode ==  109) {return   9436;}
        if (Variables.is009398()      && primaryCode ==  110) {return   9437;}
        if (Variables.is009398()      && primaryCode ==  111) {return   9438;}
        if (Variables.is009398()      && primaryCode ==  112) {return   9439;}
        if (Variables.is009398()      && primaryCode ==  113) {return   9440;}
        if (Variables.is009398()      && primaryCode ==  114) {return   9441;}
        if (Variables.is009398()      && primaryCode ==  115) {return   9442;}
        if (Variables.is009398()      && primaryCode ==  116) {return   9443;}
        if (Variables.is009398()      && primaryCode ==  117) {return   9444;}
        if (Variables.is009398()      && primaryCode ==  118) {return   9445;}
        if (Variables.is009398()      && primaryCode ==  119) {return   9446;}
        if (Variables.is009398()      && primaryCode ==  120) {return   9447;}
        if (Variables.is009398()      && primaryCode ==  121) {return   9448;}
        if (Variables.is009398()      && primaryCode ==  122) {return   9449;}
        if (Variables.is119808()    && primaryCode ==   48) {return 120782;}
        if (Variables.is119808()    && primaryCode ==   49) {return 120783;}
        if (Variables.is119808()    && primaryCode ==   50) {return 120784;}
        if (Variables.is119808()    && primaryCode ==   51) {return 120785;}
        if (Variables.is119808()    && primaryCode ==   52) {return 120786;}
        if (Variables.is119808()    && primaryCode ==   53) {return 120787;}
        if (Variables.is119808()    && primaryCode ==   54) {return 120788;}
        if (Variables.is119808()    && primaryCode ==   55) {return 120789;}
        if (Variables.is119808()    && primaryCode ==   56) {return 120790;}
        if (Variables.is119808()    && primaryCode ==   57) {return 120791;}
        if (Variables.is119912()    && primaryCode ==  400) {return 120550;}
        if (Variables.is119912()    && primaryCode ==  406) {return 120554;}
        if (Variables.is119912()    && primaryCode ==  603) {return 120576;}
        if (Variables.is119912()    && primaryCode ==  617) {return 120580;}
        if (Variables.is119912()    && primaryCode ==  913) {return 120546;}
        if (Variables.is119912()    && primaryCode ==  914) {return 120547;}
        if (Variables.is119912()    && primaryCode ==  915) {return 120548;}
        if (Variables.is119912()    && primaryCode ==  916) {return 120549;}
        if (Variables.is119912()    && primaryCode ==  918) {return 120551;}
        if (Variables.is119912()    && primaryCode ==  919) {return 120552;}
        if (Variables.is119912()    && primaryCode ==  920) {return 120553;}
        if (Variables.is119912()    && primaryCode ==  922) {return 120555;}
        if (Variables.is119912()    && primaryCode ==  923) {return 120556;}
        if (Variables.is119912()    && primaryCode ==  924) {return 120557;}
        if (Variables.is119912()    && primaryCode ==  925) {return 120558;}
        if (Variables.is119912()    && primaryCode ==  926) {return 120559;}
        if (Variables.is119912()    && primaryCode ==  927) {return 120560;}
        if (Variables.is119912()    && primaryCode ==  928) {return 120561;}
        if (Variables.is119912()    && primaryCode ==  929) {return 120562;}
        if (Variables.is119912()    && primaryCode ==  931) {return 120564;}
        if (Variables.is119912()    && primaryCode ==  932) {return 120565;}
        if (Variables.is119912()    && primaryCode ==  933) {return 120566;}
        if (Variables.is119912()    && primaryCode ==  934) {return 120567;}
        if (Variables.is119912()    && primaryCode ==  935) {return 120568;}
        if (Variables.is119912()    && primaryCode ==  936) {return 120569;}
        if (Variables.is119912()    && primaryCode ==  937) {return 120570;}
        if (Variables.is119912()    && primaryCode ==  945) {return 120572;}
        if (Variables.is119912()    && primaryCode ==  946) {return 120573;}
        if (Variables.is119912()    && primaryCode ==  947) {return 120574;}
        if (Variables.is119912()    && primaryCode ==  948) {return 120575;}
        if (Variables.is119912()    && primaryCode ==  950) {return 120577;}
        if (Variables.is119912()    && primaryCode ==  951) {return 120578;}
        if (Variables.is119912()    && primaryCode ==  952) {return 120579;}
        if (Variables.is119912()    && primaryCode ==  954) {return 120581;}
        if (Variables.is119912()    && primaryCode ==  955) {return 120582;}
        if (Variables.is119912()    && primaryCode ==  956) {return 120583;}
        if (Variables.is119912()    && primaryCode ==  957) {return 120584;}
        if (Variables.is119912()    && primaryCode ==  958) {return 120585;}
        if (Variables.is119912()    && primaryCode ==  959) {return 120586;}
        if (Variables.is119912()    && primaryCode ==  960) {return 120587;}
        if (Variables.is119912()    && primaryCode ==  961) {return 120588;}
        if (Variables.is119912()    && primaryCode ==  962) {return 120589;}
        if (Variables.is119912()    && primaryCode ==  963) {return 120590;}
        if (Variables.is119912()    && primaryCode ==  964) {return 120591;}
        if (Variables.is119912()    && primaryCode ==  965) {return 120592;}
        if (Variables.is119912()    && primaryCode ==  966) {return 120593;}
        if (Variables.is119912()    && primaryCode ==  967) {return 120594;}
        if (Variables.is119912()    && primaryCode ==  968) {return 120595;}
        if (Variables.is119912()    && primaryCode ==  969) {return 120596;}
        if (Variables.is119912()    && primaryCode ==  977) {return 120599;}
        if (Variables.is119912()    && primaryCode ==  981) {return 120601;}
        if (Variables.is119912()    && primaryCode ==  982) {return 120603;}
        if (Variables.is119912()    && primaryCode == 1008) {return 120600;}
        if (Variables.is119912()    && primaryCode == 1009) {return 120602;}
        if (Variables.is119912()    && primaryCode == 1012) {return 120563;}
        if (Variables.is119912()    && primaryCode == 1013) {return 120598;}
        if (Variables.is119912()    && primaryCode == 8706) {return 120597;}
        if (Variables.is119912()    && primaryCode == 8711) {return 120571;}
        if (Variables.is120120()    && primaryCode ==   48) {return 120792;}
        if (Variables.is120120()    && primaryCode ==   49) {return 120793;}
        if (Variables.is120120()    && primaryCode ==   50) {return 120794;}
        if (Variables.is120120()    && primaryCode ==   51) {return 120795;}
        if (Variables.is120120()    && primaryCode ==   52) {return 120796;}
        if (Variables.is120120()    && primaryCode ==   53) {return 120797;}
        if (Variables.is120120()    && primaryCode ==   54) {return 120798;}
        if (Variables.is120120()    && primaryCode ==   55) {return 120799;}
        if (Variables.is120120()    && primaryCode ==   56) {return 120800;}
        if (Variables.is120120()    && primaryCode ==   57) {return 120801;}
        if (Variables.is120120()    && primaryCode ==  400) {return 120724;}
        if (Variables.is120120()    && primaryCode ==  406) {return 120728;}
        if (Variables.is120120()    && primaryCode ==  603) {return 120750;}
        if (Variables.is120120()    && primaryCode ==  617) {return 120754;}
        if (Variables.is120120()    && primaryCode ==  913) {return 120720;}
        if (Variables.is120120()    && primaryCode ==  914) {return 120721;}
        if (Variables.is120120()    && primaryCode ==  915) {return 120722;}
        if (Variables.is120120()    && primaryCode ==  916) {return 120723;}
        if (Variables.is120120()    && primaryCode ==  918) {return 120725;}
        if (Variables.is120120()    && primaryCode ==  919) {return 120726;}
        if (Variables.is120120()    && primaryCode ==  920) {return 120727;}
        if (Variables.is120120()    && primaryCode ==  922) {return 120729;}
        if (Variables.is120120()    && primaryCode ==  923) {return 120730;}
        if (Variables.is120120()    && primaryCode ==  924) {return 120731;}
        if (Variables.is120120()    && primaryCode ==  925) {return 120732;}
        if (Variables.is120120()    && primaryCode ==  926) {return 120733;}
        if (Variables.is120120()    && primaryCode ==  927) {return 120734;}
        if (Variables.is120120()    && primaryCode ==  928) {return 120735;}
        if (Variables.is120120()    && primaryCode ==  929) {return 120736;}
        if (Variables.is120120()    && primaryCode ==  931) {return 120738;}
        if (Variables.is120120()    && primaryCode ==  932) {return 120739;}
        if (Variables.is120120()    && primaryCode ==  933) {return 120740;}
        if (Variables.is120120()    && primaryCode ==  934) {return 120741;}
        if (Variables.is120120()    && primaryCode ==  935) {return 120742;}
        if (Variables.is120120()    && primaryCode ==  936) {return 120743;}
        if (Variables.is120120()    && primaryCode ==  937) {return 120744;}
        if (Variables.is120120()    && primaryCode ==  945) {return 120746;}
        if (Variables.is120120()    && primaryCode ==  946) {return 120747;}
        if (Variables.is120120()    && primaryCode ==  947) {return 120748;}
        if (Variables.is120120()    && primaryCode ==  948) {return 120749;}
        if (Variables.is120120()    && primaryCode ==  950) {return 120751;}
        if (Variables.is120120()    && primaryCode ==  951) {return 120752;}
        if (Variables.is120120()    && primaryCode ==  952) {return 120753;}
        if (Variables.is120120()    && primaryCode ==  954) {return 120755;}
        if (Variables.is120120()    && primaryCode ==  955) {return 120756;}
        if (Variables.is120120()    && primaryCode ==  956) {return 120757;}
        if (Variables.is120120()    && primaryCode ==  957) {return 120758;}
        if (Variables.is120120()    && primaryCode ==  958) {return 120759;}
        if (Variables.is120120()    && primaryCode ==  959) {return 120760;}
        if (Variables.is120120()    && primaryCode ==  960) {return 120761;}
        if (Variables.is120120()    && primaryCode ==  961) {return 120762;}
        if (Variables.is120120()    && primaryCode ==  962) {return 120763;}
        if (Variables.is120120()    && primaryCode ==  963) {return 120764;}
        if (Variables.is120120()    && primaryCode ==  964) {return 120765;}
        if (Variables.is120120()    && primaryCode ==  965) {return 120766;}
        if (Variables.is120120()    && primaryCode ==  966) {return 120767;}
        if (Variables.is120120()    && primaryCode ==  967) {return 120768;}
        if (Variables.is120120()    && primaryCode ==  968) {return 120769;}
        if (Variables.is120120()    && primaryCode ==  969) {return 120770;}
        if (Variables.is120120()    && primaryCode ==  977) {return 120773;}
        if (Variables.is120120()    && primaryCode ==  981) {return 120775;}
        if (Variables.is120120()    && primaryCode ==  982) {return 120777;}
        if (Variables.is120120()    && primaryCode ==  988) {return 120778;}
        if (Variables.is120120()    && primaryCode ==  989) {return 120779;}
        if (Variables.is120120()    && primaryCode == 1008) {return 120774;}
        if (Variables.is120120()    && primaryCode == 1009) {return 120776;}
        if (Variables.is120120()    && primaryCode == 1012) {return 120737;}
        if (Variables.is120120()    && primaryCode == 1013) {return 120772;}
        if (Variables.is120120()    && primaryCode == 8706) {return 120771;}
        if (Variables.is120120()    && primaryCode == 8711) {return 120745;}
        if (Variables.is120224()    && primaryCode ==   48) {return 120802;}
        if (Variables.is120224()    && primaryCode ==   49) {return 120803;}
        if (Variables.is120224()    && primaryCode ==   50) {return 120804;}
        if (Variables.is120224()    && primaryCode ==   51) {return 120805;}
        if (Variables.is120224()    && primaryCode ==   52) {return 120806;}
        if (Variables.is120224()    && primaryCode ==   53) {return 120807;}
        if (Variables.is120224()    && primaryCode ==   54) {return 120808;}
        if (Variables.is120224()    && primaryCode ==   55) {return 120809;}
        if (Variables.is120224()    && primaryCode ==   56) {return 120810;}
        if (Variables.is120224()    && primaryCode ==   57) {return 120811;}
        if (Variables.is120276()    && primaryCode ==   48) {return 120812;}
        if (Variables.is120276()    && primaryCode ==   49) {return 120813;}
        if (Variables.is120276()    && primaryCode ==   50) {return 120814;}
        if (Variables.is120276()    && primaryCode ==   51) {return 120815;}
        if (Variables.is120276()    && primaryCode ==   52) {return 120816;}
        if (Variables.is120276()    && primaryCode ==   53) {return 120817;}
        if (Variables.is120276()    && primaryCode ==   54) {return 120818;}
        if (Variables.is120276()    && primaryCode ==   55) {return 120819;}
        if (Variables.is120276()    && primaryCode ==   56) {return 120820;}
        if (Variables.is120276()    && primaryCode ==   57) {return 120821;}
        if (Variables.is120276()    && primaryCode ==  400) {return 120608;}
        if (Variables.is120276()    && primaryCode ==  406) {return 120612;}
        if (Variables.is120276()    && primaryCode ==  603) {return 120634;}
        if (Variables.is120276()    && primaryCode ==  617) {return 120638;}
        if (Variables.is120276()    && primaryCode ==  913) {return 120604;}
        if (Variables.is120276()    && primaryCode ==  914) {return 120605;}
        if (Variables.is120276()    && primaryCode ==  915) {return 120606;}
        if (Variables.is120276()    && primaryCode ==  916) {return 120607;}
        if (Variables.is120276()    && primaryCode ==  918) {return 120609;}
        if (Variables.is120276()    && primaryCode ==  919) {return 120610;}
        if (Variables.is120276()    && primaryCode ==  920) {return 120611;}
        if (Variables.is120276()    && primaryCode ==  922) {return 120613;}
        if (Variables.is120276()    && primaryCode ==  923) {return 120614;}
        if (Variables.is120276()    && primaryCode ==  924) {return 120615;}
        if (Variables.is120276()    && primaryCode ==  925) {return 120616;}
        if (Variables.is120276()    && primaryCode ==  926) {return 120617;}
        if (Variables.is120276()    && primaryCode ==  927) {return 120618;}
        if (Variables.is120276()    && primaryCode ==  928) {return 120619;}
        if (Variables.is120276()    && primaryCode ==  929) {return 120620;}
        if (Variables.is120276()    && primaryCode ==  931) {return 120622;}
        if (Variables.is120276()    && primaryCode ==  932) {return 120623;}
        if (Variables.is120276()    && primaryCode ==  933) {return 120624;}
        if (Variables.is120276()    && primaryCode ==  934) {return 120625;}
        if (Variables.is120276()    && primaryCode ==  935) {return 120626;}
        if (Variables.is120276()    && primaryCode ==  936) {return 120627;}
        if (Variables.is120276()    && primaryCode ==  937) {return 120628;}
        if (Variables.is120276()    && primaryCode ==  945) {return 120630;}
        if (Variables.is120276()    && primaryCode ==  946) {return 120631;}
        if (Variables.is120276()    && primaryCode ==  947) {return 120632;}
        if (Variables.is120276()    && primaryCode ==  948) {return 120633;}
        if (Variables.is120276()    && primaryCode ==  950) {return 120635;}
        if (Variables.is120276()    && primaryCode ==  951) {return 120636;}
        if (Variables.is120276()    && primaryCode ==  952) {return 120637;}
        if (Variables.is120276()    && primaryCode ==  954) {return 120639;}
        if (Variables.is120276()    && primaryCode ==  955) {return 120640;}
        if (Variables.is120276()    && primaryCode ==  956) {return 120641;}
        if (Variables.is120276()    && primaryCode ==  957) {return 120642;}
        if (Variables.is120276()    && primaryCode ==  958) {return 120643;}
        if (Variables.is120276()    && primaryCode ==  959) {return 120644;}
        if (Variables.is120276()    && primaryCode ==  960) {return 120645;}
        if (Variables.is120276()    && primaryCode ==  961) {return 120646;}
        if (Variables.is120276()    && primaryCode ==  962) {return 120647;}
        if (Variables.is120276()    && primaryCode ==  963) {return 120648;}
        if (Variables.is120276()    && primaryCode ==  964) {return 120649;}
        if (Variables.is120276()    && primaryCode ==  965) {return 120650;}
        if (Variables.is120276()    && primaryCode ==  966) {return 120651;}
        if (Variables.is120276()    && primaryCode ==  967) {return 120652;}
        if (Variables.is120276()    && primaryCode ==  968) {return 120653;}
        if (Variables.is120276()    && primaryCode ==  969) {return 120654;}
        if (Variables.is120276()    && primaryCode ==  977) {return 120657;}
        if (Variables.is120276()    && primaryCode ==  981) {return 120659;}
        if (Variables.is120276()    && primaryCode ==  982) {return 120661;}
        if (Variables.is120276()    && primaryCode == 1008) {return 120658;}
        if (Variables.is120276()    && primaryCode == 1009) {return 120660;}
        if (Variables.is120276()    && primaryCode == 1012) {return 120621;}
        if (Variables.is120276()    && primaryCode == 1013) {return 120656;}
        if (Variables.is120276()    && primaryCode == 8706) {return 120655;}
        if (Variables.is120276()    && primaryCode == 8711) {return 120629;}
        if (Variables.is120380()    && primaryCode ==  400) {return 120666;}
        if (Variables.is120380()    && primaryCode ==  406) {return 120670;}
        if (Variables.is120380()    && primaryCode ==  603) {return 120692;}
        if (Variables.is120380()    && primaryCode ==  617) {return 120696;}
        if (Variables.is120380()    && primaryCode ==  913) {return 120662;}
        if (Variables.is120380()    && primaryCode ==  914) {return 120663;}
        if (Variables.is120380()    && primaryCode ==  915) {return 120664;}
        if (Variables.is120380()    && primaryCode ==  916) {return 120665;}
        if (Variables.is120380()    && primaryCode ==  918) {return 120667;}
        if (Variables.is120380()    && primaryCode ==  919) {return 120668;}
        if (Variables.is120380()    && primaryCode ==  920) {return 120669;}
        if (Variables.is120380()    && primaryCode ==  922) {return 120671;}
        if (Variables.is120380()    && primaryCode ==  923) {return 120672;}
        if (Variables.is120380()    && primaryCode ==  924) {return 120673;}
        if (Variables.is120380()    && primaryCode ==  925) {return 120674;}
        if (Variables.is120380()    && primaryCode ==  926) {return 120675;}
        if (Variables.is120380()    && primaryCode ==  927) {return 120676;}
        if (Variables.is120380()    && primaryCode ==  928) {return 120677;}
        if (Variables.is120380()    && primaryCode ==  929) {return 120678;}
        if (Variables.is120380()    && primaryCode ==  931) {return 120680;}
        if (Variables.is120380()    && primaryCode ==  932) {return 120681;}
        if (Variables.is120380()    && primaryCode ==  933) {return 120682;}
        if (Variables.is120380()    && primaryCode ==  934) {return 120683;}
        if (Variables.is120380()    && primaryCode ==  935) {return 120684;}
        if (Variables.is120380()    && primaryCode ==  936) {return 120685;}
        if (Variables.is120380()    && primaryCode ==  937) {return 120686;}
        if (Variables.is120380()    && primaryCode ==  945) {return 120688;}
        if (Variables.is120380()    && primaryCode ==  946) {return 120689;}
        if (Variables.is120380()    && primaryCode ==  947) {return 120690;}
        if (Variables.is120380()    && primaryCode ==  948) {return 120691;}
        if (Variables.is120380()    && primaryCode ==  950) {return 120693;}
        if (Variables.is120380()    && primaryCode ==  951) {return 120694;}
        if (Variables.is120380()    && primaryCode ==  952) {return 120695;}
        if (Variables.is120380()    && primaryCode ==  954) {return 120697;}
        if (Variables.is120380()    && primaryCode ==  955) {return 120698;}
        if (Variables.is120380()    && primaryCode ==  956) {return 120699;}
        if (Variables.is120380()    && primaryCode ==  957) {return 120700;}
        if (Variables.is120380()    && primaryCode ==  958) {return 120701;}
        if (Variables.is120380()    && primaryCode ==  959) {return 120702;}
        if (Variables.is120380()    && primaryCode ==  960) {return 120703;}
        if (Variables.is120380()    && primaryCode ==  961) {return 120704;}
        if (Variables.is120380()    && primaryCode ==  962) {return 120705;}
        if (Variables.is120380()    && primaryCode ==  963) {return 120706;}
        if (Variables.is120380()    && primaryCode ==  964) {return 120707;}
        if (Variables.is120380()    && primaryCode ==  965) {return 120708;}
        if (Variables.is120380()    && primaryCode ==  966) {return 120709;}
        if (Variables.is120380()    && primaryCode ==  967) {return 120710;}
        if (Variables.is120380()    && primaryCode ==  968) {return 120711;}
        if (Variables.is120380()    && primaryCode ==  969) {return 120712;}
        if (Variables.is120380()    && primaryCode ==  977) {return 120715;}
        if (Variables.is120380()    && primaryCode ==  981) {return 120717;}
        if (Variables.is120380()    && primaryCode ==  982) {return 120719;}
        if (Variables.is120380()    && primaryCode == 1008) {return 120716;}
        if (Variables.is120380()    && primaryCode == 1009) {return 120718;}
        if (Variables.is120380()    && primaryCode == 1012) {return 120679;}
        if (Variables.is120380()    && primaryCode == 1013) {return 120714;}
        if (Variables.is120380()    && primaryCode == 8706) {return 120713;}
        if (Variables.is120380()    && primaryCode == 8711) {return 120687;}
        if (Variables.is120432()    && primaryCode ==   48) {return 120822;}
        if (Variables.is120432()    && primaryCode ==   49) {return 120823;}
        if (Variables.is120432()    && primaryCode ==   50) {return 120824;}
        if (Variables.is120432()    && primaryCode ==   51) {return 120825;}
        if (Variables.is120432()    && primaryCode ==   52) {return 120826;}
        if (Variables.is120432()    && primaryCode ==   53) {return 120827;}
        if (Variables.is120432()    && primaryCode ==   54) {return 120828;}
        if (Variables.is120432()    && primaryCode ==   55) {return 120829;}
        if (Variables.is120432()    && primaryCode ==   56) {return 120830;}
        if (Variables.is120432()    && primaryCode ==   57) {return 120831;}
        if (Variables.is120432()    && primaryCode ==  400) {return 120492;}
        if (Variables.is120432()    && primaryCode ==  406) {return 120496;}
        if (Variables.is120432()    && primaryCode ==  603) {return 120518;}
        if (Variables.is120432()    && primaryCode ==  617) {return 120522;}
        if (Variables.is120432()    && primaryCode ==  913) {return 120488;}
        if (Variables.is120432()    && primaryCode ==  914) {return 120489;}
        if (Variables.is120432()    && primaryCode ==  915) {return 120490;}
        if (Variables.is120432()    && primaryCode ==  916) {return 120491;}
        if (Variables.is120432()    && primaryCode ==  918) {return 120493;}
        if (Variables.is120432()    && primaryCode ==  919) {return 120494;}
        if (Variables.is120432()    && primaryCode ==  920) {return 120495;}
        if (Variables.is120432()    && primaryCode ==  922) {return 120497;}
        if (Variables.is120432()    && primaryCode ==  923) {return 120498;}
        if (Variables.is120432()    && primaryCode ==  924) {return 120499;}
        if (Variables.is120432()    && primaryCode ==  925) {return 120500;}
        if (Variables.is120432()    && primaryCode ==  926) {return 120501;}
        if (Variables.is120432()    && primaryCode ==  927) {return 120502;}
        if (Variables.is120432()    && primaryCode ==  928) {return 120503;}
        if (Variables.is120432()    && primaryCode ==  929) {return 120504;}
        if (Variables.is120432()    && primaryCode ==  931) {return 120506;}
        if (Variables.is120432()    && primaryCode ==  932) {return 120507;}
        if (Variables.is120432()    && primaryCode ==  933) {return 120508;}
        if (Variables.is120432()    && primaryCode ==  934) {return 120509;}
        if (Variables.is120432()    && primaryCode ==  935) {return 120510;}
        if (Variables.is120432()    && primaryCode ==  936) {return 120511;}
        if (Variables.is120432()    && primaryCode ==  937) {return 120512;}
        if (Variables.is120432()    && primaryCode ==  945) {return 120514;}
        if (Variables.is120432()    && primaryCode ==  946) {return 120515;}
        if (Variables.is120432()    && primaryCode ==  947) {return 120516;}
        if (Variables.is120432()    && primaryCode ==  948) {return 120517;}
        if (Variables.is120432()    && primaryCode ==  950) {return 120519;}
        if (Variables.is120432()    && primaryCode ==  951) {return 120520;}
        if (Variables.is120432()    && primaryCode ==  952) {return 120521;}
        if (Variables.is120432()    && primaryCode ==  954) {return 120523;}
        if (Variables.is120432()    && primaryCode ==  955) {return 120524;}
        if (Variables.is120432()    && primaryCode ==  956) {return 120525;}
        if (Variables.is120432()    && primaryCode ==  957) {return 120526;}
        if (Variables.is120432()    && primaryCode ==  958) {return 120527;}
        if (Variables.is120432()    && primaryCode ==  959) {return 120528;}
        if (Variables.is120432()    && primaryCode ==  960) {return 120529;}
        if (Variables.is120432()    && primaryCode ==  961) {return 120530;}
        if (Variables.is120432()    && primaryCode ==  962) {return 120531;}
        if (Variables.is120432()    && primaryCode ==  963) {return 120532;}
        if (Variables.is120432()    && primaryCode ==  964) {return 120533;}
        if (Variables.is120432()    && primaryCode ==  965) {return 120534;}
        if (Variables.is120432()    && primaryCode ==  966) {return 120535;}
        if (Variables.is120432()    && primaryCode ==  967) {return 120536;}
        if (Variables.is120432()    && primaryCode ==  968) {return 120537;}
        if (Variables.is120432()    && primaryCode ==  969) {return 120538;}
        if (Variables.is120432()    && primaryCode ==  977) {return 120541;}
        if (Variables.is120432()    && primaryCode ==  981) {return 120543;}
        if (Variables.is120432()    && primaryCode ==  982) {return 120545;}
        if (Variables.is120432()    && primaryCode == 1008) {return 120542;}
        if (Variables.is120432()    && primaryCode == 1009) {return 120544;}
        if (Variables.is120432()    && primaryCode == 1012) {return 120505;}
        if (Variables.is120432()    && primaryCode == 1013) {return 120540;}
        if (Variables.is120432()    && primaryCode == 8706) {return 120539;}
        if (Variables.is120432()    && primaryCode == 8711) {return 120513;}
        if (Variables.is127280()    && primaryCode ==   65) {return 127280;}
        if (Variables.is127280()    && primaryCode ==   66) {return 127281;}
        if (Variables.is127280()    && primaryCode ==   67) {return 127282;}
        if (Variables.is127280()    && primaryCode ==   68) {return 127283;}
        if (Variables.is127280()    && primaryCode ==   69) {return 127284;}
        if (Variables.is127280()    && primaryCode ==   70) {return 127285;}
        if (Variables.is127280()    && primaryCode ==   71) {return 127286;}
        if (Variables.is127280()    && primaryCode ==   72) {return 127287;}
        if (Variables.is127280()    && primaryCode ==   73) {return 127288;}
        if (Variables.is127280()    && primaryCode ==   74) {return 127289;}
        if (Variables.is127280()    && primaryCode ==   75) {return 127290;}
        if (Variables.is127280()    && primaryCode ==   76) {return 127291;}
        if (Variables.is127280()    && primaryCode ==   77) {return 127292;}
        if (Variables.is127280()    && primaryCode ==   78) {return 127293;}
        if (Variables.is127280()    && primaryCode ==   79) {return 127294;}
        if (Variables.is127280()    && primaryCode ==   80) {return 127295;}
        if (Variables.is127280()    && primaryCode ==   81) {return 127296;}
        if (Variables.is127280()    && primaryCode ==   82) {return 127297;}
        if (Variables.is127280()    && primaryCode ==   83) {return 127298;}
        if (Variables.is127280()    && primaryCode ==   84) {return 127299;}
        if (Variables.is127280()    && primaryCode ==   85) {return 127300;}
        if (Variables.is127280()    && primaryCode ==   86) {return 127301;}
        if (Variables.is127280()    && primaryCode ==   87) {return 127302;}
        if (Variables.is127280()    && primaryCode ==   88) {return 127303;}
        if (Variables.is127280()    && primaryCode ==   89) {return 127304;}
        if (Variables.is127280()    && primaryCode ==   90) {return 127305;}
        if (Variables.is127280()    && primaryCode ==   97) {return 127280;}
        if (Variables.is127280()    && primaryCode ==   98) {return 127281;}
        if (Variables.is127280()    && primaryCode ==   99) {return 127282;}
        if (Variables.is127280()    && primaryCode ==  100) {return 127283;}
        if (Variables.is127280()    && primaryCode ==  101) {return 127284;}
        if (Variables.is127280()    && primaryCode ==  102) {return 127285;}
        if (Variables.is127280()    && primaryCode ==  103) {return 127286;}
        if (Variables.is127280()    && primaryCode ==  104) {return 127287;}
        if (Variables.is127280()    && primaryCode ==  105) {return 127288;}
        if (Variables.is127280()    && primaryCode ==  106) {return 127289;}
        if (Variables.is127280()    && primaryCode ==  107) {return 127290;}
        if (Variables.is127280()    && primaryCode ==  108) {return 127291;}
        if (Variables.is127280()    && primaryCode ==  109) {return 127292;}
        if (Variables.is127280()    && primaryCode ==  110) {return 127293;}
        if (Variables.is127280()    && primaryCode ==  111) {return 127294;}
        if (Variables.is127280()    && primaryCode ==  112) {return 127295;}
        if (Variables.is127280()    && primaryCode ==  113) {return 127296;}
        if (Variables.is127280()    && primaryCode ==  114) {return 127297;}
        if (Variables.is127280()    && primaryCode ==  115) {return 127298;}
        if (Variables.is127280()    && primaryCode ==  116) {return 127299;}
        if (Variables.is127280()    && primaryCode ==  117) {return 127300;}
        if (Variables.is127280()    && primaryCode ==  118) {return 127301;}
        if (Variables.is127280()    && primaryCode ==  119) {return 127302;}
        if (Variables.is127280()    && primaryCode ==  120) {return 127303;}
        if (Variables.is127280()    && primaryCode ==  121) {return 127304;}
        if (Variables.is127280()    && primaryCode ==  122) {return 127305;}
        if (Variables.is127312()    && primaryCode ==   65) {return 127312;}
        if (Variables.is127312()    && primaryCode ==   66) {return 127313;}
        if (Variables.is127312()    && primaryCode ==   67) {return 127314;}
        if (Variables.is127312()    && primaryCode ==   68) {return 127315;}
        if (Variables.is127312()    && primaryCode ==   69) {return 127316;}
        if (Variables.is127312()    && primaryCode ==   70) {return 127317;}
        if (Variables.is127312()    && primaryCode ==   71) {return 127318;}
        if (Variables.is127312()    && primaryCode ==   72) {return 127319;}
        if (Variables.is127312()    && primaryCode ==   73) {return 127320;}
        if (Variables.is127312()    && primaryCode ==   74) {return 127321;}
        if (Variables.is127312()    && primaryCode ==   75) {return 127322;}
        if (Variables.is127312()    && primaryCode ==   76) {return 127323;}
        if (Variables.is127312()    && primaryCode ==   77) {return 127324;}
        if (Variables.is127312()    && primaryCode ==   78) {return 127325;}
        if (Variables.is127312()    && primaryCode ==   79) {return 127326;}
        if (Variables.is127312()    && primaryCode ==   80) {return 127327;}
        if (Variables.is127312()    && primaryCode ==   81) {return 127328;}
        if (Variables.is127312()    && primaryCode ==   82) {return 127329;}
        if (Variables.is127312()    && primaryCode ==   83) {return 127330;}
        if (Variables.is127312()    && primaryCode ==   84) {return 127331;}
        if (Variables.is127312()    && primaryCode ==   85) {return 127332;}
        if (Variables.is127312()    && primaryCode ==   86) {return 127333;}
        if (Variables.is127312()    && primaryCode ==   87) {return 127334;}
        if (Variables.is127312()    && primaryCode ==   88) {return 127335;}
        if (Variables.is127312()    && primaryCode ==   89) {return 127336;}
        if (Variables.is127312()    && primaryCode ==   90) {return 127337;}
        if (Variables.is127312()    && primaryCode ==   97) {return 127312;}
        if (Variables.is127312()    && primaryCode ==   98) {return 127313;}
        if (Variables.is127312()    && primaryCode ==   99) {return 127314;}
        if (Variables.is127312()    && primaryCode ==  100) {return 127315;}
        if (Variables.is127312()    && primaryCode ==  101) {return 127316;}
        if (Variables.is127312()    && primaryCode ==  102) {return 127317;}
        if (Variables.is127312()    && primaryCode ==  103) {return 127318;}
        if (Variables.is127312()    && primaryCode ==  104) {return 127319;}
        if (Variables.is127312()    && primaryCode ==  105) {return 127320;}
        if (Variables.is127312()    && primaryCode ==  106) {return 127321;}
        if (Variables.is127312()    && primaryCode ==  107) {return 127322;}
        if (Variables.is127312()    && primaryCode ==  108) {return 127323;}
        if (Variables.is127312()    && primaryCode ==  109) {return 127324;}
        if (Variables.is127312()    && primaryCode ==  110) {return 127325;}
        if (Variables.is127312()    && primaryCode ==  111) {return 127326;}
        if (Variables.is127312()    && primaryCode ==  112) {return 127327;}
        if (Variables.is127312()    && primaryCode ==  113) {return 127328;}
        if (Variables.is127312()    && primaryCode ==  114) {return 127329;}
        if (Variables.is127312()    && primaryCode ==  115) {return 127330;}
        if (Variables.is127312()    && primaryCode ==  116) {return 127331;}
        if (Variables.is127312()    && primaryCode ==  117) {return 127332;}
        if (Variables.is127312()    && primaryCode ==  118) {return 127333;}
        if (Variables.is127312()    && primaryCode ==  119) {return 127334;}
        if (Variables.is127312()    && primaryCode ==  120) {return 127335;}
        if (Variables.is127312()    && primaryCode ==  121) {return 127336;}
        if (Variables.is127312()    && primaryCode ==  122) {return 127337;}
        if (Variables.is127344()    && primaryCode ==   65) {return 127344;}
        if (Variables.is127344()    && primaryCode ==   66) {return 127345;}
        if (Variables.is127344()    && primaryCode ==   67) {return 127346;}
        if (Variables.is127344()    && primaryCode ==   68) {return 127347;}
        if (Variables.is127344()    && primaryCode ==   69) {return 127348;}
        if (Variables.is127344()    && primaryCode ==   70) {return 127349;}
        if (Variables.is127344()    && primaryCode ==   71) {return 127350;}
        if (Variables.is127344()    && primaryCode ==   72) {return 127351;}
        if (Variables.is127344()    && primaryCode ==   73) {return 127352;}
        if (Variables.is127344()    && primaryCode ==   74) {return 127353;}
        if (Variables.is127344()    && primaryCode ==   75) {return 127354;}
        if (Variables.is127344()    && primaryCode ==   76) {return 127355;}
        if (Variables.is127344()    && primaryCode ==   77) {return 127356;}
        if (Variables.is127344()    && primaryCode ==   78) {return 127357;}
        if (Variables.is127344()    && primaryCode ==   79) {return 127358;}
        if (Variables.is127344()    && primaryCode ==   80) {return 127359;}
        if (Variables.is127344()    && primaryCode ==   81) {return 127360;}
        if (Variables.is127344()    && primaryCode ==   82) {return 127361;}
        if (Variables.is127344()    && primaryCode ==   83) {return 127362;}
        if (Variables.is127344()    && primaryCode ==   84) {return 127363;}
        if (Variables.is127344()    && primaryCode ==   85) {return 127364;}
        if (Variables.is127344()    && primaryCode ==   86) {return 127365;}
        if (Variables.is127344()    && primaryCode ==   87) {return 127366;}
        if (Variables.is127344()    && primaryCode ==   88) {return 127367;}
        if (Variables.is127344()    && primaryCode ==   89) {return 127368;}
        if (Variables.is127344()    && primaryCode ==   90) {return 127369;}
        if (Variables.is127344()    && primaryCode ==   97) {return 127344;}
        if (Variables.is127344()    && primaryCode ==   98) {return 127345;}
        if (Variables.is127344()    && primaryCode ==   99) {return 127346;}
        if (Variables.is127344()    && primaryCode ==  100) {return 127347;}
        if (Variables.is127344()    && primaryCode ==  101) {return 127348;}
        if (Variables.is127344()    && primaryCode ==  102) {return 127349;}
        if (Variables.is127344()    && primaryCode ==  103) {return 127350;}
        if (Variables.is127344()    && primaryCode ==  104) {return 127351;}
        if (Variables.is127344()    && primaryCode ==  105) {return 127352;}
        if (Variables.is127344()    && primaryCode ==  106) {return 127353;}
        if (Variables.is127344()    && primaryCode ==  107) {return 127354;}
        if (Variables.is127344()    && primaryCode ==  108) {return 127355;}
        if (Variables.is127344()    && primaryCode ==  109) {return 127356;}
        if (Variables.is127344()    && primaryCode ==  110) {return 127357;}
        if (Variables.is127344()    && primaryCode ==  111) {return 127358;}
        if (Variables.is127344()    && primaryCode ==  112) {return 127359;}
        if (Variables.is127344()    && primaryCode ==  113) {return 127360;}
        if (Variables.is127344()    && primaryCode ==  114) {return 127361;}
        if (Variables.is127344()    && primaryCode ==  115) {return 127362;}
        if (Variables.is127344()    && primaryCode ==  116) {return 127363;}
        if (Variables.is127344()    && primaryCode ==  117) {return 127364;}
        if (Variables.is127344()    && primaryCode ==  118) {return 127365;}
        if (Variables.is127344()    && primaryCode ==  119) {return 127366;}
        if (Variables.is127344()    && primaryCode ==  120) {return 127367;}
        if (Variables.is127344()    && primaryCode ==  121) {return 127368;}
        if (Variables.is127344()    && primaryCode ==  122) {return 127369;}
        if (Variables.is127462()    && primaryCode ==   65) {return 127462;}
        if (Variables.is127462()    && primaryCode ==   66) {return 127463;}
        if (Variables.is127462()    && primaryCode ==   67) {return 127464;}
        if (Variables.is127462()    && primaryCode ==   68) {return 127465;}
        if (Variables.is127462()    && primaryCode ==   69) {return 127466;}
        if (Variables.is127462()    && primaryCode ==   70) {return 127467;}
        if (Variables.is127462()    && primaryCode ==   71) {return 127468;}
        if (Variables.is127462()    && primaryCode ==   72) {return 127469;}
        if (Variables.is127462()    && primaryCode ==   73) {return 127470;}
        if (Variables.is127462()    && primaryCode ==   74) {return 127471;}
        if (Variables.is127462()    && primaryCode ==   75) {return 127472;}
        if (Variables.is127462()    && primaryCode ==   76) {return 127473;}
        if (Variables.is127462()    && primaryCode ==   77) {return 127474;}
        if (Variables.is127462()    && primaryCode ==   78) {return 127475;}
        if (Variables.is127462()    && primaryCode ==   79) {return 127476;}
        if (Variables.is127462()    && primaryCode ==   80) {return 127477;}
        if (Variables.is127462()    && primaryCode ==   81) {return 127478;}
        if (Variables.is127462()    && primaryCode ==   82) {return 127479;}
        if (Variables.is127462()    && primaryCode ==   83) {return 127480;}
        if (Variables.is127462()    && primaryCode ==   84) {return 127481;}
        if (Variables.is127462()    && primaryCode ==   85) {return 127482;}
        if (Variables.is127462()    && primaryCode ==   86) {return 127483;}
        if (Variables.is127462()    && primaryCode ==   87) {return 127484;}
        if (Variables.is127462()    && primaryCode ==   88) {return 127485;}
        if (Variables.is127462()    && primaryCode ==   89) {return 127486;}
        if (Variables.is127462()    && primaryCode ==   90) {return 127487;}
        if (Variables.is127462()    && primaryCode ==   97) {return 127462;}
        if (Variables.is127462()    && primaryCode ==   98) {return 127463;}
        if (Variables.is127462()    && primaryCode ==   99) {return 127464;}
        if (Variables.is127462()    && primaryCode ==  100) {return 127465;}
        if (Variables.is127462()    && primaryCode ==  101) {return 127466;}
        if (Variables.is127462()    && primaryCode ==  102) {return 127467;}
        if (Variables.is127462()    && primaryCode ==  103) {return 127468;}
        if (Variables.is127462()    && primaryCode ==  104) {return 127469;}
        if (Variables.is127462()    && primaryCode ==  105) {return 127470;}
        if (Variables.is127462()    && primaryCode ==  106) {return 127471;}
        if (Variables.is127462()    && primaryCode ==  107) {return 127472;}
        if (Variables.is127462()    && primaryCode ==  108) {return 127473;}
        if (Variables.is127462()    && primaryCode ==  109) {return 127474;}
        if (Variables.is127462()    && primaryCode ==  110) {return 127475;}
        if (Variables.is127462()    && primaryCode ==  111) {return 127476;}
        if (Variables.is127462()    && primaryCode ==  112) {return 127477;}
        if (Variables.is127462()    && primaryCode ==  113) {return 127478;}
        if (Variables.is127462()    && primaryCode ==  114) {return 127479;}
        if (Variables.is127462()    && primaryCode ==  115) {return 127480;}
        if (Variables.is127462()    && primaryCode ==  116) {return 127481;}
        if (Variables.is127462()    && primaryCode ==  117) {return 127482;}
        if (Variables.is127462()    && primaryCode ==  118) {return 127483;}
        if (Variables.is127462()    && primaryCode ==  119) {return 127484;}
        if (Variables.is127462()    && primaryCode ==  120) {return 127485;}
        if (Variables.is127462()    && primaryCode ==  121) {return 127486;}
        if (Variables.is127462()    && primaryCode ==  122) {return 127487;}
        if (Variables.isRflctd() && primaryCode ==   33) {return    161;}
        if (Variables.isRflctd() && primaryCode ==   40) {return     41;}
        if (Variables.isRflctd() && primaryCode ==   41) {return     40;}
        if (Variables.isRflctd() && primaryCode ==   60) {return     62;}
        if (Variables.isRflctd() && primaryCode ==   62) {return     60;}
        if (Variables.isRflctd() && primaryCode ==   63) {return    191;}
        if (Variables.isRflctd() && primaryCode ==   65) {return  11375;}
        if (Variables.isRflctd() && primaryCode ==   66) {return  42221;}
        if (Variables.isRflctd() && primaryCode ==   67) {return    390;}
        if (Variables.isRflctd() && primaryCode ==   68) {return  42231;}
        if (Variables.isRflctd() && primaryCode ==   69) {return    398;}
        if (Variables.isRflctd() && primaryCode ==   70) {return   8498;}
        if (Variables.isRflctd() && primaryCode ==   71) {return  42216;}
        if (Variables.isRflctd() && primaryCode ==   72) {return     72;}
        if (Variables.isRflctd() && primaryCode ==   73) {return     73;}
        if (Variables.isRflctd() && primaryCode ==   74) {return   1360;}
        if (Variables.isRflctd() && primaryCode ==   75) {return  42928;}
        if (Variables.isRflctd() && primaryCode ==   76) {return   8514;}
        if (Variables.isRflctd() && primaryCode ==   77) {return  43005;}
        if (Variables.isRflctd() && primaryCode ==   78) {return     78;}
        if (Variables.isRflctd() && primaryCode ==   79) {return     79;}
        if (Variables.isRflctd() && primaryCode ==   80) {return   1280;}
        if (Variables.isRflctd() && primaryCode ==   81) {return    210;}
        if (Variables.isRflctd() && primaryCode ==   82) {return  42212;}
        if (Variables.isRflctd() && primaryCode ==   83) {return     83;}
        if (Variables.isRflctd() && primaryCode ==   84) {return  42929;}
        if (Variables.isRflctd() && primaryCode ==   85) {return 119365;}
        if (Variables.isRflctd() && primaryCode ==   86) {return    581;}
        if (Variables.isRflctd() && primaryCode ==   87) {return  66224;}
        if (Variables.isRflctd() && primaryCode ==   88) {return     88;}
        if (Variables.isRflctd() && primaryCode ==   89) {return   8516;}
        if (Variables.isRflctd() && primaryCode ==   90) {return     90;}
        if (Variables.isRflctd() && primaryCode ==   91) {return     93;}
        if (Variables.isRflctd() && primaryCode ==   93) {return     91;}
        if (Variables.isRflctd() && primaryCode ==   97) {return    592;}
        if (Variables.isRflctd() && primaryCode ==   98) {return    113;}
        if (Variables.isRflctd() && primaryCode ==   99) {return    596;}
        if (Variables.isRflctd() && primaryCode ==  100) {return    112;}
        if (Variables.isRflctd() && primaryCode ==  101) {return    601;}
        if (Variables.isRflctd() && primaryCode ==  102) {return    607;}
        if (Variables.isRflctd() && primaryCode ==  103) {return   7543;}
        if (Variables.isRflctd() && primaryCode ==  104) {return    613;}
        if (Variables.isRflctd() && primaryCode ==  105) {return   7433;}
        if (Variables.isRflctd() && primaryCode ==  106) {return    383;}
        if (Variables.isRflctd() && primaryCode ==  107) {return    670;}
        if (Variables.isRflctd() && primaryCode ==  108) {return  42881;}
        if (Variables.isRflctd() && primaryCode ==  109) {return    623;}
        if (Variables.isRflctd() && primaryCode ==  110) {return    117;}
        if (Variables.isRflctd() && primaryCode ==  111) {return    111;}
        if (Variables.isRflctd() && primaryCode ==  112) {return    100;}
        if (Variables.isRflctd() && primaryCode ==  113) {return     98;}
        if (Variables.isRflctd() && primaryCode ==  114) {return    633;}
        if (Variables.isRflctd() && primaryCode ==  115) {return    115;}
        if (Variables.isRflctd() && primaryCode ==  116) {return    647;}
        if (Variables.isRflctd() && primaryCode ==  117) {return    110;}
        if (Variables.isRflctd() && primaryCode ==  118) {return    652;}
        if (Variables.isRflctd() && primaryCode ==  119) {return    653;}
        if (Variables.isRflctd() && primaryCode ==  120) {return    120;}
        if (Variables.isRflctd() && primaryCode ==  121) {return    654;}
        if (Variables.isRflctd() && primaryCode ==  122) {return    122;}
        if (Variables.isRflctd() && primaryCode ==  123) {return    125;}
        if (Variables.isRflctd() && primaryCode ==  125) {return    123;}
        if (Variables.isRflctd() && primaryCode ==  171) {return    187;}
        if (Variables.isRflctd() && primaryCode ==  187) {return    171;}
        if (Variables.isSmlcap() && primaryCode ==   65) {return     65;}
        if (Variables.isSmlcap() && primaryCode ==   66) {return     66;}
        if (Variables.isSmlcap() && primaryCode ==   67) {return     67;}
        if (Variables.isSmlcap() && primaryCode ==   68) {return     68;}
        if (Variables.isSmlcap() && primaryCode ==   69) {return     69;}
        if (Variables.isSmlcap() && primaryCode ==   70) {return     70;}
        if (Variables.isSmlcap() && primaryCode ==   71) {return     71;}
        if (Variables.isSmlcap() && primaryCode ==   72) {return     72;}
        if (Variables.isSmlcap() && primaryCode ==   73) {return     73;}
        if (Variables.isSmlcap() && primaryCode ==   74) {return     74;}
        if (Variables.isSmlcap() && primaryCode ==   75) {return     75;}
        if (Variables.isSmlcap() && primaryCode ==   76) {return     76;}
        if (Variables.isSmlcap() && primaryCode ==   77) {return     77;}
        if (Variables.isSmlcap() && primaryCode ==   78) {return     78;}
        if (Variables.isSmlcap() && primaryCode ==   79) {return     79;}
        if (Variables.isSmlcap() && primaryCode ==   80) {return     80;}
        if (Variables.isSmlcap() && primaryCode ==   81) {return   1192;}
        if (Variables.isSmlcap() && primaryCode ==   82) {return     82;}
        if (Variables.isSmlcap() && primaryCode ==   83) {return     83;}
        if (Variables.isSmlcap() && primaryCode ==   84) {return     84;}
        if (Variables.isSmlcap() && primaryCode ==   85) {return     85;}
        if (Variables.isSmlcap() && primaryCode ==   86) {return     86;}
        if (Variables.isSmlcap() && primaryCode ==   87) {return     87;}
        if (Variables.isSmlcap() && primaryCode ==   88) {return     88;}
        if (Variables.isSmlcap() && primaryCode ==   89) {return     89;}
        if (Variables.isSmlcap() && primaryCode ==   90) {return     90;}
        if (Variables.isSmlcap() && primaryCode ==   97) {return   7424;}
        if (Variables.isSmlcap() && primaryCode ==   98) {return    665;}
        if (Variables.isSmlcap() && primaryCode ==   99) {return   7428;}
        if (Variables.isSmlcap() && primaryCode ==  100) {return   7429;}
        if (Variables.isSmlcap() && primaryCode ==  101) {return   7431;}
        if (Variables.isSmlcap() && primaryCode ==  102) {return  42800;}
        if (Variables.isSmlcap() && primaryCode ==  103) {return    610;}
        if (Variables.isSmlcap() && primaryCode ==  104) {return    668;}
        if (Variables.isSmlcap() && primaryCode ==  105) {return    618;}
        if (Variables.isSmlcap() && primaryCode ==  106) {return   7434;}
        if (Variables.isSmlcap() && primaryCode ==  107) {return   7435;}
        if (Variables.isSmlcap() && primaryCode ==  108) {return    671;}
        if (Variables.isSmlcap() && primaryCode ==  109) {return   7437;}
        if (Variables.isSmlcap() && primaryCode ==  110) {return    628;}
        if (Variables.isSmlcap() && primaryCode ==  111) {return   7439;}
        if (Variables.isSmlcap() && primaryCode ==  112) {return   7448;}
        if (Variables.isSmlcap() && primaryCode ==  113) {return   1193;}
        if (Variables.isSmlcap() && primaryCode ==  114) {return    640;}
        if (Variables.isSmlcap() && primaryCode ==  115) {return  42801;}
        if (Variables.isSmlcap() && primaryCode ==  116) {return   7451;}
        if (Variables.isSmlcap() && primaryCode ==  117) {return   7452;}
        if (Variables.isSmlcap() && primaryCode ==  118) {return   7456;}
        if (Variables.isSmlcap() && primaryCode ==  119) {return   7457;}
        if (Variables.isSmlcap() && primaryCode ==  120) {return    120;}
        if (Variables.isSmlcap() && primaryCode ==  121) {return    655;}
        if (Variables.isSmlcap() && primaryCode ==  122) {return   7458;}

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
