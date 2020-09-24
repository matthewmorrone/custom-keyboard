package com.custom.keyboard;

class Fonts {

    static int handleCharacter(CustomKeyboardView kv, int primaryCode) {
        if (Util.isAlphaNumeric(primaryCode)) {

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
        if (Variables.isParenthesized()    && primaryCode ==   65) {return 127248;} // 🄐
        if (Variables.isParenthesized()    && primaryCode ==   66) {return 127249;} // 🄑
        if (Variables.isParenthesized()    && primaryCode ==   67) {return 127250;} // 🄒
        if (Variables.isParenthesized()    && primaryCode ==   68) {return 127251;} // 🄓
        if (Variables.isParenthesized()    && primaryCode ==   69) {return 127252;} // 🄔
        if (Variables.isParenthesized()    && primaryCode ==   70) {return 127253;} // 🄕
        if (Variables.isParenthesized()    && primaryCode ==   71) {return 127254;} // 🄖
        if (Variables.isParenthesized()    && primaryCode ==   72) {return 127255;} // 🄗
        if (Variables.isParenthesized()    && primaryCode ==   73) {return 127256;} // 🄘
        if (Variables.isParenthesized()    && primaryCode ==   74) {return 127257;} // 🄙
        if (Variables.isParenthesized()    && primaryCode ==   75) {return 127258;} // 🄚
        if (Variables.isParenthesized()    && primaryCode ==   76) {return 127259;} // 🄛
        if (Variables.isParenthesized()    && primaryCode ==   77) {return 127260;} // 🄜
        if (Variables.isParenthesized()    && primaryCode ==   78) {return 127261;} // 🄝
        if (Variables.isParenthesized()    && primaryCode ==   79) {return 127262;} // 🄞
        if (Variables.isParenthesized()    && primaryCode ==   80) {return 127263;} // 🄟
        if (Variables.isParenthesized()    && primaryCode ==   81) {return 127264;} // 🄠
        if (Variables.isParenthesized()    && primaryCode ==   82) {return 127265;} // 🄡
        if (Variables.isParenthesized()    && primaryCode ==   83) {return 127266;} // 🄢
        if (Variables.isParenthesized()    && primaryCode ==   84) {return 127267;} // 🄣
        if (Variables.isParenthesized()    && primaryCode ==   85) {return 127268;} // 🄤
        if (Variables.isParenthesized()    && primaryCode ==   86) {return 127269;} // 🄥
        if (Variables.isParenthesized()    && primaryCode ==   87) {return 127270;} // 🄦
        if (Variables.isParenthesized()    && primaryCode ==   88) {return 127271;} // 🄧
        if (Variables.isParenthesized()    && primaryCode ==   89) {return 127272;} // 🄨
        if (Variables.isParenthesized()    && primaryCode ==   90) {return 127273;} // 🄩
        if (Variables.isParenthesized()    && primaryCode ==   97) {return   9372;} // ⒜
        if (Variables.isParenthesized()    && primaryCode ==   98) {return   9373;} // ⒝
        if (Variables.isParenthesized()    && primaryCode ==   99) {return   9374;} // ⒞
        if (Variables.isParenthesized()    && primaryCode ==  100) {return   9375;} // ⒟
        if (Variables.isParenthesized()    && primaryCode ==  101) {return   9376;} // ⒠
        if (Variables.isParenthesized()    && primaryCode ==  102) {return   9377;} // ⒡
        if (Variables.isParenthesized()    && primaryCode ==  103) {return   9378;} // ⒢
        if (Variables.isParenthesized()    && primaryCode ==  104) {return   9379;} // ⒣
        if (Variables.isParenthesized()    && primaryCode ==  105) {return   9380;} // ⒤
        if (Variables.isParenthesized()    && primaryCode ==  106) {return   9381;} // ⒥
        if (Variables.isParenthesized()    && primaryCode ==  107) {return   9382;} // ⒦
        if (Variables.isParenthesized()    && primaryCode ==  108) {return   9383;} // ⒧
        if (Variables.isParenthesized()    && primaryCode ==  109) {return   9384;} // ⒨
        if (Variables.isParenthesized()    && primaryCode ==  110) {return   9385;} // ⒩
        if (Variables.isParenthesized()    && primaryCode ==  111) {return   9386;} // ⒪
        if (Variables.isParenthesized()    && primaryCode ==  112) {return   9387;} // ⒫
        if (Variables.isParenthesized()    && primaryCode ==  113) {return   9388;} // ⒬
        if (Variables.isParenthesized()    && primaryCode ==  114) {return   9389;} // ⒭
        if (Variables.isParenthesized()    && primaryCode ==  115) {return   9390;} // ⒮
        if (Variables.isParenthesized()    && primaryCode ==  116) {return   9391;} // ⒯
        if (Variables.isParenthesized()    && primaryCode ==  117) {return   9392;} // ⒰
        if (Variables.isParenthesized()    && primaryCode ==  118) {return   9393;} // ⒱
        if (Variables.isParenthesized()    && primaryCode ==  119) {return   9394;} // ⒲
        if (Variables.isParenthesized()    && primaryCode ==  120) {return   9395;} // ⒳
        if (Variables.isParenthesized()    && primaryCode ==  121) {return   9396;} // ⒴
        if (Variables.isParenthesized()    && primaryCode ==  122) {return   9397;} // ⒵
        if (Variables.isCircled()    && primaryCode ==   65) {return   9398;} // Ⓐ
        if (Variables.isCircled()    && primaryCode ==   66) {return   9399;} // Ⓑ
        if (Variables.isCircled()    && primaryCode ==   67) {return   9400;} // Ⓒ
        if (Variables.isCircled()    && primaryCode ==   68) {return   9401;} // Ⓓ
        if (Variables.isCircled()    && primaryCode ==   69) {return   9402;} // Ⓔ
        if (Variables.isCircled()    && primaryCode ==   70) {return   9403;} // Ⓕ
        if (Variables.isCircled()    && primaryCode ==   71) {return   9404;} // Ⓖ
        if (Variables.isCircled()    && primaryCode ==   72) {return   9405;} // Ⓗ
        if (Variables.isCircled()    && primaryCode ==   73) {return   9406;} // Ⓘ
        if (Variables.isCircled()    && primaryCode ==   74) {return   9407;} // Ⓙ
        if (Variables.isCircled()    && primaryCode ==   75) {return   9408;} // Ⓚ
        if (Variables.isCircled()    && primaryCode ==   76) {return   9409;} // Ⓛ
        if (Variables.isCircled()    && primaryCode ==   77) {return   9410;} // Ⓜ
        if (Variables.isCircled()    && primaryCode ==   78) {return   9411;} // Ⓝ
        if (Variables.isCircled()    && primaryCode ==   79) {return   9412;} // Ⓞ
        if (Variables.isCircled()    && primaryCode ==   80) {return   9413;} // Ⓟ
        if (Variables.isCircled()    && primaryCode ==   81) {return   9414;} // Ⓠ
        if (Variables.isCircled()    && primaryCode ==   82) {return   9415;} // Ⓡ
        if (Variables.isCircled()    && primaryCode ==   83) {return   9416;} // Ⓢ
        if (Variables.isCircled()    && primaryCode ==   84) {return   9417;} // Ⓣ
        if (Variables.isCircled()    && primaryCode ==   85) {return   9418;} // Ⓤ
        if (Variables.isCircled()    && primaryCode ==   86) {return   9419;} // Ⓥ
        if (Variables.isCircled()    && primaryCode ==   87) {return   9420;} // Ⓦ
        if (Variables.isCircled()    && primaryCode ==   88) {return   9421;} // Ⓧ
        if (Variables.isCircled()    && primaryCode ==   89) {return   9422;} // Ⓨ
        if (Variables.isCircled()    && primaryCode ==   90) {return   9423;} // Ⓩ
        if (Variables.isCircled()    && primaryCode ==   97) {return   9424;} // ⓐ
        if (Variables.isCircled()    && primaryCode ==   98) {return   9425;} // ⓑ
        if (Variables.isCircled()    && primaryCode ==   99) {return   9426;} // ⓒ
        if (Variables.isCircled()    && primaryCode ==  100) {return   9427;} // ⓓ
        if (Variables.isCircled()    && primaryCode ==  101) {return   9428;} // ⓔ
        if (Variables.isCircled()    && primaryCode ==  102) {return   9429;} // ⓕ
        if (Variables.isCircled()    && primaryCode ==  103) {return   9430;} // ⓖ
        if (Variables.isCircled()    && primaryCode ==  104) {return   9431;} // ⓗ
        if (Variables.isCircled()    && primaryCode ==  105) {return   9432;} // ⓘ
        if (Variables.isCircled()    && primaryCode ==  106) {return   9433;} // ⓙ
        if (Variables.isCircled()    && primaryCode ==  107) {return   9434;} // ⓚ
        if (Variables.isCircled()    && primaryCode ==  108) {return   9435;} // ⓛ
        if (Variables.isCircled()    && primaryCode ==  109) {return   9436;} // ⓜ
        if (Variables.isCircled()    && primaryCode ==  110) {return   9437;} // ⓝ
        if (Variables.isCircled()    && primaryCode ==  111) {return   9438;} // ⓞ
        if (Variables.isCircled()    && primaryCode ==  112) {return   9439;} // ⓟ
        if (Variables.isCircled()    && primaryCode ==  113) {return   9440;} // ⓠ
        if (Variables.isCircled()    && primaryCode ==  114) {return   9441;} // ⓡ
        if (Variables.isCircled()    && primaryCode ==  115) {return   9442;} // ⓢ
        if (Variables.isCircled()    && primaryCode ==  116) {return   9443;} // ⓣ
        if (Variables.isCircled()    && primaryCode ==  117) {return   9444;} // ⓤ
        if (Variables.isCircled()    && primaryCode ==  118) {return   9445;} // ⓥ
        if (Variables.isCircled()    && primaryCode ==  119) {return   9446;} // ⓦ
        if (Variables.isCircled()    && primaryCode ==  120) {return   9447;} // ⓧ
        if (Variables.isCircled()    && primaryCode ==  121) {return   9448;} // ⓨ
        if (Variables.isCircled()    && primaryCode ==  122) {return   9449;} // ⓩ

        if (Variables.isBlocked()    && primaryCode ==   65) {return 127280;} // 🄰
        if (Variables.isBlocked()    && primaryCode ==   66) {return 127281;} // 🄱
        if (Variables.isBlocked()    && primaryCode ==   67) {return 127282;} // 🄲
        if (Variables.isBlocked()    && primaryCode ==   68) {return 127283;} // 🄳
        if (Variables.isBlocked()    && primaryCode ==   69) {return 127284;} // 🄴
        if (Variables.isBlocked()    && primaryCode ==   70) {return 127285;} // 🄵
        if (Variables.isBlocked()    && primaryCode ==   71) {return 127286;} // 🄶
        if (Variables.isBlocked()    && primaryCode ==   72) {return 127287;} // 🄷
        if (Variables.isBlocked()    && primaryCode ==   73) {return 127288;} // 🄸
        if (Variables.isBlocked()    && primaryCode ==   74) {return 127289;} // 🄹
        if (Variables.isBlocked()    && primaryCode ==   75) {return 127290;} // 🄺
        if (Variables.isBlocked()    && primaryCode ==   76) {return 127291;} // 🄻
        if (Variables.isBlocked()    && primaryCode ==   77) {return 127292;} // 🄼
        if (Variables.isBlocked()    && primaryCode ==   78) {return 127293;} // 🄽
        if (Variables.isBlocked()    && primaryCode ==   79) {return 127294;} // 🄾
        if (Variables.isBlocked()    && primaryCode ==   80) {return 127295;} // 🄿
        if (Variables.isBlocked()    && primaryCode ==   81) {return 127296;} // 🅀
        if (Variables.isBlocked()    && primaryCode ==   82) {return 127297;} // 🅁
        if (Variables.isBlocked()    && primaryCode ==   83) {return 127298;} // 🅂
        if (Variables.isBlocked()    && primaryCode ==   84) {return 127299;} // 🅃
        if (Variables.isBlocked()    && primaryCode ==   85) {return 127300;} // 🅄
        if (Variables.isBlocked()    && primaryCode ==   86) {return 127301;} // 🅅
        if (Variables.isBlocked()    && primaryCode ==   87) {return 127302;} // 🅆
        if (Variables.isBlocked()    && primaryCode ==   88) {return 127303;} // 🅇
        if (Variables.isBlocked()    && primaryCode ==   89) {return 127304;} // 🅈
        if (Variables.isBlocked()    && primaryCode ==   90) {return 127305;} // 🅉
        if (Variables.isBlocked()    && primaryCode ==   97) {return 127280;} // 🄰
        if (Variables.isBlocked()    && primaryCode ==   98) {return 127281;} // 🄱
        if (Variables.isBlocked()    && primaryCode ==   99) {return 127282;} // 🄲
        if (Variables.isBlocked()    && primaryCode ==  100) {return 127283;} // 🄳
        if (Variables.isBlocked()    && primaryCode ==  101) {return 127284;} // 🄴
        if (Variables.isBlocked()    && primaryCode ==  102) {return 127285;} // 🄵
        if (Variables.isBlocked()    && primaryCode ==  103) {return 127286;} // 🄶
        if (Variables.isBlocked()    && primaryCode ==  104) {return 127287;} // 🄷
        if (Variables.isBlocked()    && primaryCode ==  105) {return 127288;} // 🄸
        if (Variables.isBlocked()    && primaryCode ==  106) {return 127289;} // 🄹
        if (Variables.isBlocked()    && primaryCode ==  107) {return 127290;} // 🄺
        if (Variables.isBlocked()    && primaryCode ==  108) {return 127291;} // 🄻
        if (Variables.isBlocked()    && primaryCode ==  109) {return 127292;} // 🄼
        if (Variables.isBlocked()    && primaryCode ==  110) {return 127293;} // 🄽
        if (Variables.isBlocked()    && primaryCode ==  111) {return 127294;} // 🄾
        if (Variables.isBlocked()    && primaryCode ==  112) {return 127295;} // 🄿
        if (Variables.isBlocked()    && primaryCode ==  113) {return 127296;} // 🅀
        if (Variables.isBlocked()    && primaryCode ==  114) {return 127297;} // 🅁
        if (Variables.isBlocked()    && primaryCode ==  115) {return 127298;} // 🅂
        if (Variables.isBlocked()    && primaryCode ==  116) {return 127299;} // 🅃
        if (Variables.isBlocked()    && primaryCode ==  117) {return 127300;} // 🅄
        if (Variables.isBlocked()    && primaryCode ==  118) {return 127301;} // 🅅
        if (Variables.isBlocked()    && primaryCode ==  119) {return 127302;} // 🅆
        if (Variables.isBlocked()    && primaryCode ==  120) {return 127303;} // 🅇
        if (Variables.isBlocked()    && primaryCode ==  121) {return 127304;} // 🅈
        if (Variables.isBlocked()    && primaryCode ==  122) {return 127305;} // 🅉
        if (Variables.isCircleStamped()    && primaryCode ==   65) {return 127312;} // 🅐
        if (Variables.isCircleStamped()    && primaryCode ==   66) {return 127313;} // 🅑
        if (Variables.isCircleStamped()    && primaryCode ==   67) {return 127314;} // 🅒
        if (Variables.isCircleStamped()    && primaryCode ==   68) {return 127315;} // 🅓
        if (Variables.isCircleStamped()    && primaryCode ==   69) {return 127316;} // 🅔
        if (Variables.isCircleStamped()    && primaryCode ==   70) {return 127317;} // 🅕
        if (Variables.isCircleStamped()    && primaryCode ==   71) {return 127318;} // 🅖
        if (Variables.isCircleStamped()    && primaryCode ==   72) {return 127319;} // 🅗
        if (Variables.isCircleStamped()    && primaryCode ==   73) {return 127320;} // 🅘
        if (Variables.isCircleStamped()    && primaryCode ==   74) {return 127321;} // 🅙
        if (Variables.isCircleStamped()    && primaryCode ==   75) {return 127322;} // 🅚
        if (Variables.isCircleStamped()    && primaryCode ==   76) {return 127323;} // 🅛
        if (Variables.isCircleStamped()    && primaryCode ==   77) {return 127324;} // 🅜
        if (Variables.isCircleStamped()    && primaryCode ==   78) {return 127325;} // 🅝
        if (Variables.isCircleStamped()    && primaryCode ==   79) {return 127326;} // 🅞
        if (Variables.isCircleStamped()    && primaryCode ==   80) {return 127327;} // 🅟
        if (Variables.isCircleStamped()    && primaryCode ==   81) {return 127328;} // 🅠
        if (Variables.isCircleStamped()    && primaryCode ==   82) {return 127329;} // 🅡
        if (Variables.isCircleStamped()    && primaryCode ==   83) {return 127330;} // 🅢
        if (Variables.isCircleStamped()    && primaryCode ==   84) {return 127331;} // 🅣
        if (Variables.isCircleStamped()    && primaryCode ==   85) {return 127332;} // 🅤
        if (Variables.isCircleStamped()    && primaryCode ==   86) {return 127333;} // 🅥
        if (Variables.isCircleStamped()    && primaryCode ==   87) {return 127334;} // 🅦
        if (Variables.isCircleStamped()    && primaryCode ==   88) {return 127335;} // 🅧
        if (Variables.isCircleStamped()    && primaryCode ==   89) {return 127336;} // 🅨
        if (Variables.isCircleStamped()    && primaryCode ==   90) {return 127337;} // 🅩
        if (Variables.isCircleStamped()    && primaryCode ==   97) {return 127312;} // 🅐
        if (Variables.isCircleStamped()    && primaryCode ==   98) {return 127313;} // 🅑
        if (Variables.isCircleStamped()    && primaryCode ==   99) {return 127314;} // 🅒
        if (Variables.isCircleStamped()    && primaryCode ==  100) {return 127315;} // 🅓
        if (Variables.isCircleStamped()    && primaryCode ==  101) {return 127316;} // 🅔
        if (Variables.isCircleStamped()    && primaryCode ==  102) {return 127317;} // 🅕
        if (Variables.isCircleStamped()    && primaryCode ==  103) {return 127318;} // 🅖
        if (Variables.isCircleStamped()    && primaryCode ==  104) {return 127319;} // 🅗
        if (Variables.isCircleStamped()    && primaryCode ==  105) {return 127320;} // 🅘
        if (Variables.isCircleStamped()    && primaryCode ==  106) {return 127321;} // 🅙
        if (Variables.isCircleStamped()    && primaryCode ==  107) {return 127322;} // 🅚
        if (Variables.isCircleStamped()    && primaryCode ==  108) {return 127323;} // 🅛
        if (Variables.isCircleStamped()    && primaryCode ==  109) {return 127324;} // 🅜
        if (Variables.isCircleStamped()    && primaryCode ==  110) {return 127325;} // 🅝
        if (Variables.isCircleStamped()    && primaryCode ==  111) {return 127326;} // 🅞
        if (Variables.isCircleStamped()    && primaryCode ==  112) {return 127327;} // 🅟
        if (Variables.isCircleStamped()    && primaryCode ==  113) {return 127328;} // 🅠
        if (Variables.isCircleStamped()    && primaryCode ==  114) {return 127329;} // 🅡
        if (Variables.isCircleStamped()    && primaryCode ==  115) {return 127330;} // 🅢
        if (Variables.isCircleStamped()    && primaryCode ==  116) {return 127331;} // 🅣
        if (Variables.isCircleStamped()    && primaryCode ==  117) {return 127332;} // 🅤
        if (Variables.isCircleStamped()    && primaryCode ==  118) {return 127333;} // 🅥
        if (Variables.isCircleStamped()    && primaryCode ==  119) {return 127334;} // 🅦
        if (Variables.isCircleStamped()    && primaryCode ==  120) {return 127335;} // 🅧
        if (Variables.isCircleStamped()    && primaryCode ==  121) {return 127336;} // 🅨
        if (Variables.isCircleStamped()    && primaryCode ==  122) {return 127337;} // 🅩
        if (Variables.isSquaredStamped()    && primaryCode ==   65) {return 127344;} // 🅰
        if (Variables.isSquaredStamped()    && primaryCode ==   66) {return 127345;} // 🅱
        if (Variables.isSquaredStamped()    && primaryCode ==   67) {return 127346;} // 🅲
        if (Variables.isSquaredStamped()    && primaryCode ==   68) {return 127347;} // 🅳
        if (Variables.isSquaredStamped()    && primaryCode ==   69) {return 127348;} // 🅴
        if (Variables.isSquaredStamped()    && primaryCode ==   70) {return 127349;} // 🅵
        if (Variables.isSquaredStamped()    && primaryCode ==   71) {return 127350;} // 🅶
        if (Variables.isSquaredStamped()    && primaryCode ==   72) {return 127351;} // 🅷
        if (Variables.isSquaredStamped()    && primaryCode ==   73) {return 127352;} // 🅸
        if (Variables.isSquaredStamped()    && primaryCode ==   74) {return 127353;} // 🅹
        if (Variables.isSquaredStamped()    && primaryCode ==   75) {return 127354;} // 🅺
        if (Variables.isSquaredStamped()    && primaryCode ==   76) {return 127355;} // 🅻
        if (Variables.isSquaredStamped()    && primaryCode ==   77) {return 127356;} // 🅼
        if (Variables.isSquaredStamped()    && primaryCode ==   78) {return 127357;} // 🅽
        if (Variables.isSquaredStamped()    && primaryCode ==   79) {return 127358;} // 🅾
        if (Variables.isSquaredStamped()    && primaryCode ==   80) {return 127359;} // 🅿
        if (Variables.isSquaredStamped()    && primaryCode ==   81) {return 127360;} // 🆀
        if (Variables.isSquaredStamped()    && primaryCode ==   82) {return 127361;} // 🆁
        if (Variables.isSquaredStamped()    && primaryCode ==   83) {return 127362;} // 🆂
        if (Variables.isSquaredStamped()    && primaryCode ==   84) {return 127363;} // 🆃
        if (Variables.isSquaredStamped()    && primaryCode ==   85) {return 127364;} // 🆄
        if (Variables.isSquaredStamped()    && primaryCode ==   86) {return 127365;} // 🆅
        if (Variables.isSquaredStamped()    && primaryCode ==   87) {return 127366;} // 🆆
        if (Variables.isSquaredStamped()    && primaryCode ==   88) {return 127367;} // 🆇
        if (Variables.isSquaredStamped()    && primaryCode ==   89) {return 127368;} // 🆈
        if (Variables.isSquaredStamped()    && primaryCode ==   90) {return 127369;} // 🆉
        if (Variables.isSquaredStamped()    && primaryCode ==   97) {return 127344;} // 🅰
        if (Variables.isSquaredStamped()    && primaryCode ==   98) {return 127345;} // 🅱
        if (Variables.isSquaredStamped()    && primaryCode ==   99) {return 127346;} // 🅲
        if (Variables.isSquaredStamped()    && primaryCode ==  100) {return 127347;} // 🅳
        if (Variables.isSquaredStamped()    && primaryCode ==  101) {return 127348;} // 🅴
        if (Variables.isSquaredStamped()    && primaryCode ==  102) {return 127349;} // 🅵
        if (Variables.isSquaredStamped()    && primaryCode ==  103) {return 127350;} // 🅶
        if (Variables.isSquaredStamped()    && primaryCode ==  104) {return 127351;} // 🅷
        if (Variables.isSquaredStamped()    && primaryCode ==  105) {return 127352;} // 🅸
        if (Variables.isSquaredStamped()    && primaryCode ==  106) {return 127353;} // 🅹
        if (Variables.isSquaredStamped()    && primaryCode ==  107) {return 127354;} // 🅺
        if (Variables.isSquaredStamped()    && primaryCode ==  108) {return 127355;} // 🅻
        if (Variables.isSquaredStamped()    && primaryCode ==  109) {return 127356;} // 🅼
        if (Variables.isSquaredStamped()    && primaryCode ==  110) {return 127357;} // 🅽
        if (Variables.isSquaredStamped()    && primaryCode ==  111) {return 127358;} // 🅾
        if (Variables.isSquaredStamped()    && primaryCode ==  112) {return 127359;} // 🅿
        if (Variables.isSquaredStamped()    && primaryCode ==  113) {return 127360;} // 🆀
        if (Variables.isSquaredStamped()    && primaryCode ==  114) {return 127361;} // 🆁
        if (Variables.isSquaredStamped()    && primaryCode ==  115) {return 127362;} // 🆂
        if (Variables.isSquaredStamped()    && primaryCode ==  116) {return 127363;} // 🆃
        if (Variables.isSquaredStamped()    && primaryCode ==  117) {return 127364;} // 🆄
        if (Variables.isSquaredStamped()    && primaryCode ==  118) {return 127365;} // 🆅
        if (Variables.isSquaredStamped()    && primaryCode ==  119) {return 127366;} // 🆆
        if (Variables.isSquaredStamped()    && primaryCode ==  120) {return 127367;} // 🆇
        if (Variables.isSquaredStamped()    && primaryCode ==  121) {return 127368;} // 🆈
        if (Variables.isSquaredStamped()    && primaryCode ==  122) {return 127369;} // 🆉
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
