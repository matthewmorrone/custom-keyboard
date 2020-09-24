package com.custom.keyboard;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class Font {


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
        if (Util.isDigit(primaryCode)) {
            primaryCode -= 120764;
        }
        else if      (primaryCode >= 120276 && primaryCode <= 120301) {
            primaryCode -= 120211;
        }
        else if (primaryCode >= 120302 && primaryCode <= 120327) {
            primaryCode -= 120205;
        }
        return primaryCode;
    }
    static int getBold(int primaryCode) {
        if (Util.isDigit(primaryCode)) {
            primaryCode += 120764;
        }
        else if (primaryCode >= 65 && primaryCode <= 90) {
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


    public static String unbolden(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(new String(Character.toChars(getUnbold((ch)))));
            i += Character.charCount(ch);
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String bolden(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int ch : chars) {
            result.add(new String(Character.toChars(getBold((int)ch))));
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }

    public static String unitalicize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(new String(Character.toChars(getUnitalic((ch)))));
            i += Character.charCount(ch);
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String italicize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (char ch : chars) {
            result.add(new String(Character.toChars(getItalic((int)ch))));
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }

    public static String unemphasize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(new String(Character.toChars(getUnemphasized((ch)))));
            i += Character.charCount(ch);
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String emphasize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (char ch : chars) {
            result.add(new String(Character.toChars(getEmphasized((int)ch))));
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }

    public static String unstrikethrough(String text) {
        return text.replaceAll("̶", "");
    }

    public static String strikethrough(String text) {
        if (text.contains("̶")) {
            return text.replaceAll("̶", "");
        }
        return text.replaceAll("(.)", "$1̶");
    }

    public static String ununderline(String text) {
        return text.replaceAll("̲", "");
    }

    public static String underline(String text) {
        if (text.contains("̲")) {
            return text.replaceAll("̲", "");
        }
        return text.replaceAll("(.)", "$1̲");
    }

    public static String ununderscore(String text) {
        return text.replaceAll("꯭", "");
    }

    public static String underscore(String text) {
        if (text.contains("꯭")) {
            return text.replaceAll("꯭", "");
        }
        if (text.length() < 4) return text.replaceAll("(.)", "$1꯭");
        String first = text.substring(0, text.length() - 1);
        String secnd = text.substring(text.length() - 1, text.length());
        first = first.replaceAll("(.)", "$1꯭");
        return first + secnd;
    }

    public int toBoldSerif(int primaryCode, boolean shift) {
        if (shift) primaryCode += (119808 - 65);
        else primaryCode += (119808 - 71);
        return primaryCode;

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

        if (Variables.is119808()    && primaryCode ==  400) {return 120492;} // 𝚬
        if (Variables.is119808()    && primaryCode ==  406) {return 120496;} // 𝚰
        if (Variables.is119808()    && primaryCode ==  603) {return 120518;} // 𝛆
        if (Variables.is119808()    && primaryCode ==  617) {return 120522;} // 𝛊
        if (Variables.is119808()    && primaryCode ==  913) {return 120488;} // 𝚨
        if (Variables.is119808()    && primaryCode ==  914) {return 120489;} // 𝚩
        if (Variables.is119808()    && primaryCode ==  915) {return 120490;} // 𝚪
        if (Variables.is119808()    && primaryCode ==  916) {return 120491;} // 𝚫
        if (Variables.is119808()    && primaryCode ==  918) {return 120493;} // 𝚭
        if (Variables.is119808()    && primaryCode ==  919) {return 120494;} // 𝚮
        if (Variables.is119808()    && primaryCode ==  920) {return 120495;} // 𝚯
        if (Variables.is119808()    && primaryCode ==  922) {return 120497;} // 𝚱
        if (Variables.is119808()    && primaryCode ==  923) {return 120498;} // 𝚲
        if (Variables.is119808()    && primaryCode ==  924) {return 120499;} // 𝚳
        if (Variables.is119808()    && primaryCode ==  925) {return 120500;} // 𝚴
        if (Variables.is119808()    && primaryCode ==  926) {return 120501;} // 𝚵
        if (Variables.is119808()    && primaryCode ==  927) {return 120502;} // 𝚶
        if (Variables.is119808()    && primaryCode ==  928) {return 120503;} // 𝚷
        if (Variables.is119808()    && primaryCode ==  929) {return 120504;} // 𝚸
        if (Variables.is119808()    && primaryCode ==  931) {return 120506;} // 𝚺
        if (Variables.is119808()    && primaryCode ==  932) {return 120507;} // 𝚻
        if (Variables.is119808()    && primaryCode ==  933) {return 120508;} // 𝚼
        if (Variables.is119808()    && primaryCode ==  934) {return 120509;} // 𝚽
        if (Variables.is119808()    && primaryCode ==  935) {return 120510;} // 𝚾
        if (Variables.is119808()    && primaryCode ==  936) {return 120511;} // 𝚿
        if (Variables.is119808()    && primaryCode ==  937) {return 120512;} // 𝛀
        if (Variables.is119808()    && primaryCode ==  945) {return 120514;} // 𝛂
        if (Variables.is119808()    && primaryCode ==  946) {return 120515;} // 𝛃
        if (Variables.is119808()    && primaryCode ==  947) {return 120516;} // 𝛄
        if (Variables.is119808()    && primaryCode ==  948) {return 120517;} // 𝛅
        if (Variables.is119808()    && primaryCode ==  950) {return 120519;} // 𝛇
        if (Variables.is119808()    && primaryCode ==  951) {return 120520;} // 𝛈
        if (Variables.is119808()    && primaryCode ==  952) {return 120521;} // 𝛉
        if (Variables.is119808()    && primaryCode ==  954) {return 120523;} // 𝛋
        if (Variables.is119808()    && primaryCode ==  955) {return 120524;} // 𝛌
        if (Variables.is119808()    && primaryCode ==  956) {return 120525;} // 𝛍
        if (Variables.is119808()    && primaryCode ==  957) {return 120526;} // 𝛎
        if (Variables.is119808()    && primaryCode ==  958) {return 120527;} // 𝛏
        if (Variables.is119808()    && primaryCode ==  959) {return 120528;} // 𝛐
        if (Variables.is119808()    && primaryCode ==  960) {return 120529;} // 𝛑
        if (Variables.is119808()    && primaryCode ==  961) {return 120530;} // 𝛒
        if (Variables.is119808()    && primaryCode ==  962) {return 120531;} // 𝛓
        if (Variables.is119808()    && primaryCode ==  963) {return 120532;} // 𝛔
        if (Variables.is119808()    && primaryCode ==  964) {return 120533;} // 𝛕
        if (Variables.is119808()    && primaryCode ==  965) {return 120534;} // 𝛖
        if (Variables.is119808()    && primaryCode ==  966) {return 120535;} // 𝛗
        if (Variables.is119808()    && primaryCode ==  967) {return 120536;} // 𝛘
        if (Variables.is119808()    && primaryCode ==  968) {return 120537;} // 𝛙
        if (Variables.is119808()    && primaryCode ==  969) {return 120538;} // 𝛚
        if (Variables.is119808()    && primaryCode ==  977) {return 120541;} // 𝛝
        if (Variables.is119808()    && primaryCode ==  981) {return 120543;} // 𝛟
        if (Variables.is119808()    && primaryCode ==  982) {return 120545;} // 𝛡
        if (Variables.is119808()    && primaryCode == 1008) {return 120542;} // 𝛞
        if (Variables.is119808()    && primaryCode == 1009) {return 120544;} // 𝛠
        if (Variables.is119808()    && primaryCode == 1012) {return 120505;} // 𝚹
        if (Variables.is119808()    && primaryCode == 1013) {return 120540;} // 𝛜
        if (Variables.is119808()    && primaryCode == 8706) {return 120539;} // 𝛛
        if (Variables.is119808()    && primaryCode == 8711) {return 120513;} // 𝛁

    }

    public int toItalicSerif(int primaryCode, boolean shift) {
        if (shift) primaryCode += (119860 - 65);
        else primaryCode += (119860 - 71);
        return primaryCode;


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


    }

    public int toBoldItalicSerif(int primaryCode, boolean shift) {
        if (shift) primaryCode += (119912 - 65);
        else primaryCode += (119912 - 71);
        return primaryCode;

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

    }

    public int toSans(int primaryCode, boolean shift) {
        if (shift) primaryCode += (120224 - 65);
        else primaryCode += (120224 - 71);
        return primaryCode;

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

    }

    public int toBoldSans(int primaryCode, boolean shift) {
        if (shift) primaryCode += (120276 - 65);
        else primaryCode += (120276 - 71);
        return primaryCode;

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


        if (Variables.is120276()    && primaryCode ==  400) {return 120666;} // 𝝚
        if (Variables.is120276()    && primaryCode ==  406) {return 120670;} // 𝝞
        if (Variables.is120276()    && primaryCode ==  603) {return 120692;} // 𝝴
        if (Variables.is120276()    && primaryCode ==  617) {return 120696;} // 𝝸
        if (Variables.is120276()    && primaryCode ==  913) {return 120662;} // 𝝖
        if (Variables.is120276()    && primaryCode ==  914) {return 120663;} // 𝝗
        if (Variables.is120276()    && primaryCode ==  915) {return 120664;} // 𝝘
        if (Variables.is120276()    && primaryCode ==  916) {return 120665;} // 𝝙
        if (Variables.is120276()    && primaryCode ==  918) {return 120667;} // 𝝛
        if (Variables.is120276()    && primaryCode ==  919) {return 120668;} // 𝝜
        if (Variables.is120276()    && primaryCode ==  920) {return 120669;} // 𝝝
        if (Variables.is120276()    && primaryCode ==  922) {return 120671;} // 𝝟
        if (Variables.is120276()    && primaryCode ==  923) {return 120672;} // 𝝠
        if (Variables.is120276()    && primaryCode ==  924) {return 120673;} // 𝝡
        if (Variables.is120276()    && primaryCode ==  925) {return 120674;} // 𝝢
        if (Variables.is120276()    && primaryCode ==  926) {return 120675;} // 𝝣
        if (Variables.is120276()    && primaryCode ==  927) {return 120676;} // 𝝤
        if (Variables.is120276()    && primaryCode ==  928) {return 120677;} // 𝝥
        if (Variables.is120276()    && primaryCode ==  929) {return 120678;} // 𝝦
        if (Variables.is120276()    && primaryCode ==  931) {return 120680;} // 𝝨
        if (Variables.is120276()    && primaryCode ==  932) {return 120681;} // 𝝩
        if (Variables.is120276()    && primaryCode ==  933) {return 120682;} // 𝝪
        if (Variables.is120276()    && primaryCode ==  934) {return 120683;} // 𝝫
        if (Variables.is120276()    && primaryCode ==  935) {return 120684;} // 𝝬
        if (Variables.is120276()    && primaryCode ==  936) {return 120685;} // 𝝭
        if (Variables.is120276()    && primaryCode ==  937) {return 120686;} // 𝝮
        if (Variables.is120276()    && primaryCode ==  945) {return 120688;} // 𝝰
        if (Variables.is120276()    && primaryCode ==  946) {return 120689;} // 𝝱
        if (Variables.is120276()    && primaryCode ==  947) {return 120690;} // 𝝲
        if (Variables.is120276()    && primaryCode ==  948) {return 120691;} // 𝝳
        if (Variables.is120276()    && primaryCode ==  950) {return 120693;} // 𝝵
        if (Variables.is120276()    && primaryCode ==  951) {return 120694;} // 𝝶
        if (Variables.is120276()    && primaryCode ==  952) {return 120695;} // 𝝷
        if (Variables.is120276()    && primaryCode ==  954) {return 120697;} // 𝝹
        if (Variables.is120276()    && primaryCode ==  955) {return 120698;} // 𝝺
        if (Variables.is120276()    && primaryCode ==  956) {return 120699;} // 𝝻
        if (Variables.is120276()    && primaryCode ==  957) {return 120700;} // 𝝼
        if (Variables.is120276()    && primaryCode ==  958) {return 120701;} // 𝝽
        if (Variables.is120276()    && primaryCode ==  959) {return 120702;} // 𝝾
        if (Variables.is120276()    && primaryCode ==  960) {return 120703;} // 𝝿
        if (Variables.is120276()    && primaryCode ==  961) {return 120704;} // 𝞀
        if (Variables.is120276()    && primaryCode ==  962) {return 120705;} // 𝞁
        if (Variables.is120276()    && primaryCode ==  963) {return 120706;} // 𝞂
        if (Variables.is120276()    && primaryCode ==  964) {return 120707;} // 𝞃
        if (Variables.is120276()    && primaryCode ==  965) {return 120708;} // 𝞄
        if (Variables.is120276()    && primaryCode ==  966) {return 120709;} // 𝞅
        if (Variables.is120276()    && primaryCode ==  967) {return 120710;} // 𝞆
        if (Variables.is120276()    && primaryCode ==  968) {return 120711;} // 𝞇
        if (Variables.is120276()    && primaryCode ==  969) {return 120712;} // 𝞈
        if (Variables.is120276()    && primaryCode ==  977) {return 120715;} // 𝞋
        if (Variables.is120276()    && primaryCode ==  981) {return 120717;} // 𝞍
        if (Variables.is120276()    && primaryCode ==  982) {return 120719;} // 𝞏
        if (Variables.is120276()    && primaryCode == 1008) {return 120716;} // 𝞌
        if (Variables.is120276()    && primaryCode == 1009) {return 120718;} // 𝞎
        if (Variables.is120276()    && primaryCode == 1012) {return 120679;} // 𝝧
        if (Variables.is120276()    && primaryCode == 1013) {return 120714;} // 𝞊
        if (Variables.is120276()    && primaryCode == 8706) {return 120713;} // 𝞉
        if (Variables.is120276()    && primaryCode == 8711) {return 120687;} // 𝝯



    }

    public int toItalicSans(int primaryCode, boolean shift) {
        if (shift) primaryCode += (120328 - 65);
        else primaryCode += (120328 - 71);
        return primaryCode;




    }

    public int toBoldItalicSans(int primaryCode, boolean shift) {
        if (shift) primaryCode += (120380 - 65);
        else primaryCode += (120380 - 71);
        return primaryCode;


        if (Variables.is120380()    && primaryCode ==  400) {return 120724;} // 𝞔
        if (Variables.is120380()    && primaryCode ==  406) {return 120728;} // 𝞘
        if (Variables.is120380()    && primaryCode ==  603) {return 120750;} // 𝞮
        if (Variables.is120380()    && primaryCode ==  617) {return 120754;} // 𝞲
        if (Variables.is120380()    && primaryCode ==  913) {return 120720;} // 𝞐
        if (Variables.is120380()    && primaryCode ==  914) {return 120721;} // 𝞑
        if (Variables.is120380()    && primaryCode ==  915) {return 120722;} // 𝞒
        if (Variables.is120380()    && primaryCode ==  916) {return 120723;} // 𝞓
        if (Variables.is120380()    && primaryCode ==  918) {return 120725;} // 𝞕
        if (Variables.is120380()    && primaryCode ==  919) {return 120726;} // 𝞖
        if (Variables.is120380()    && primaryCode ==  920) {return 120727;} // 𝞗
        if (Variables.is120380()    && primaryCode ==  922) {return 120729;} // 𝞙
        if (Variables.is120380()    && primaryCode ==  923) {return 120730;} // 𝞚
        if (Variables.is120380()    && primaryCode ==  924) {return 120731;} // 𝞛
        if (Variables.is120380()    && primaryCode ==  925) {return 120732;} // 𝞜
        if (Variables.is120380()    && primaryCode ==  926) {return 120733;} // 𝞝
        if (Variables.is120380()    && primaryCode ==  927) {return 120734;} // 𝞞
        if (Variables.is120380()    && primaryCode ==  928) {return 120735;} // 𝞟
        if (Variables.is120380()    && primaryCode ==  929) {return 120736;} // 𝞠
        if (Variables.is120380()    && primaryCode ==  931) {return 120738;} // 𝞢
        if (Variables.is120380()    && primaryCode ==  932) {return 120739;} // 𝞣
        if (Variables.is120380()    && primaryCode ==  933) {return 120740;} // 𝞤
        if (Variables.is120380()    && primaryCode ==  934) {return 120741;} // 𝞥
        if (Variables.is120380()    && primaryCode ==  935) {return 120742;} // 𝞦
        if (Variables.is120380()    && primaryCode ==  936) {return 120743;} // 𝞧
        if (Variables.is120380()    && primaryCode ==  937) {return 120744;} // 𝞨
        if (Variables.is120380()    && primaryCode ==  945) {return 120746;} // 𝞪
        if (Variables.is120380()    && primaryCode ==  946) {return 120747;} // 𝞫
        if (Variables.is120380()    && primaryCode ==  947) {return 120748;} // 𝞬
        if (Variables.is120380()    && primaryCode ==  948) {return 120749;} // 𝞭
        if (Variables.is120380()    && primaryCode ==  950) {return 120751;} // 𝞯
        if (Variables.is120380()    && primaryCode ==  951) {return 120752;} // 𝞰
        if (Variables.is120380()    && primaryCode ==  952) {return 120753;} // 𝞱
        if (Variables.is120380()    && primaryCode ==  954) {return 120755;} // 𝞳
        if (Variables.is120380()    && primaryCode ==  955) {return 120756;} // 𝞴
        if (Variables.is120380()    && primaryCode ==  956) {return 120757;} // 𝞵
        if (Variables.is120380()    && primaryCode ==  957) {return 120758;} // 𝞶
        if (Variables.is120380()    && primaryCode ==  958) {return 120759;} // 𝞷
        if (Variables.is120380()    && primaryCode ==  959) {return 120760;} // 𝞸
        if (Variables.is120380()    && primaryCode ==  960) {return 120761;} // 𝞹
        if (Variables.is120380()    && primaryCode ==  961) {return 120762;} // 𝞺
        if (Variables.is120380()    && primaryCode ==  962) {return 120763;} // 𝞻
        if (Variables.is120380()    && primaryCode ==  963) {return 120764;} // 𝞼
        if (Variables.is120380()    && primaryCode ==  964) {return 120765;} // 𝞽
        if (Variables.is120380()    && primaryCode ==  965) {return 120766;} // 𝞾
        if (Variables.is120380()    && primaryCode ==  966) {return 120767;} // 𝞿
        if (Variables.is120380()    && primaryCode ==  967) {return 120768;} // 𝟀
        if (Variables.is120380()    && primaryCode ==  968) {return 120769;} // 𝟁
        if (Variables.is120380()    && primaryCode ==  969) {return 120770;} // 𝟂
        if (Variables.is120380()    && primaryCode ==  977) {return 120773;} // 𝟅
        if (Variables.is120380()    && primaryCode ==  981) {return 120775;} // 𝟇
        if (Variables.is120380()    && primaryCode ==  982) {return 120777;} // 𝟉
        if (Variables.is120380()    && primaryCode ==  988) {return 120778;} // 𝟊
        if (Variables.is120380()    && primaryCode ==  989) {return 120779;} // 𝟋
        if (Variables.is120380()    && primaryCode == 1008) {return 120774;} // 𝟆
        if (Variables.is120380()    && primaryCode == 1009) {return 120776;} // 𝟈
        if (Variables.is120380()    && primaryCode == 1012) {return 120737;} // 𝞡
        if (Variables.is120380()    && primaryCode == 1013) {return 120772;} // 𝟄
        if (Variables.is120380()    && primaryCode == 8706) {return 120771;} // 𝟃
        if (Variables.is120380()    && primaryCode == 8711) {return 120745;} // 𝞩







    }

    public int toScript(int primaryCode, boolean shift) {
        if (shift) primaryCode += (119964 - 65);
        else primaryCode += (119964 - 71);
        return primaryCode;
    }

    public int toScriptBold(int primaryCode, boolean shift) {
        if (shift) primaryCode += (120016 - 65);
        else primaryCode += (120016 - 71);
        return primaryCode;
    }

    public int toFraktur(int primaryCode, boolean shift) {
        if (shift) primaryCode += (120068 - 65);
        else primaryCode += (120068 - 71);
        return primaryCode;
    }

    public int toFrakturBold(int primaryCode, boolean shift) {
        if (shift) primaryCode += (120172 - 65);
        else primaryCode += (120172 - 71);
        return primaryCode;
    }

    public int toMonospace(int primaryCode, boolean shift) {
        if (shift) primaryCode += (120432 - 65);
        else primaryCode += (120432 - 71);
        return primaryCode;



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

    }

    public int toDoublestruck(int primaryCode, boolean shift) {
        if (primaryCode >= 48 && primaryCode <= 57) {return primaryCode + (120792-48);}
        if (shift) primaryCode += (120120 - 65);
        else primaryCode += (120120 - 71);
        return primaryCode;

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

    }


    public int toCaps(int primaryCode) {
        switch(primaryCode) {
            case   81: return   1192; // Ҩ
            case   97: return   7424; // ᴀ
            case   98: return    665; // ʙ
            case   99: return   7428; // ᴄ
            case  100: return   7429; // ᴅ
            case  101: return   7431; // ᴇ
            case  102: return  42800; // ꜰ
            case  103: return    610; // ɢ
            case  104: return    668; // ʜ
            case  105: return    618; // ɪ
            case  106: return   7434; // ᴊ
            case  107: return   7435; // ᴋ
            case  108: return    671; // ʟ
            case  109: return   7437; // ᴍ
            case  110: return    628; // ɴ
            case  111: return   7439; // ᴏ
            case  112: return   7448; // ᴘ
            case  113: return   1193; // ҩ
            case  114: return    640; // ʀ
            case  115: return  42801; // ꜱ
            case  116: return   7451; // ᴛ
            case  117: return   7452; // ᴜ
            case  118: return   7456; // ᴠ
            case  119: return   7457; // ᴡ
            case  121: return    655; // ʏ
            case  122: return   7458; // ᴢ
        }
        return primaryCode;
    }

    public int toParentheses(int primaryCode) {
        switch(primaryCode) {
            case   65: return 127248; // 🄐
            case   66: return 127249; // 🄑
            case   67: return 127250; // 🄒
            case   68: return 127251; // 🄓
            case   69: return 127252; // 🄔
            case   70: return 127253; // 🄕
            case   71: return 127254; // 🄖
            case   72: return 127255; // 🄗
            case   73: return 127256; // 🄘
            case   74: return 127257; // 🄙
            case   75: return 127258; // 🄚
            case   76: return 127259; // 🄛
            case   77: return 127260; // 🄜
            case   78: return 127261; // 🄝
            case   79: return 127262; // 🄞
            case   80: return 127263; // 🄟
            case   81: return 127264; // 🄠
            case   82: return 127265; // 🄡
            case   83: return 127266; // 🄢
            case   84: return 127267; // 🄣
            case   85: return 127268; // 🄤
            case   86: return 127269; // 🄥
            case   87: return 127270; // 🄦
            case   88: return 127271; // 🄧
            case   89: return 127272; // 🄨
            case   90: return 127273; // 🄩
            case   97: return   9372; // ⒜
            case   98: return   9373; // ⒝
            case   99: return   9374; // ⒞
            case  100: return   9375; // ⒟
            case  101: return   9376; // ⒠
            case  102: return   9377; // ⒡
            case  103: return   9378; // ⒢
            case  104: return   9379; // ⒣
            case  105: return   9380; // ⒤
            case  106: return   9381; // ⒥
            case  107: return   9382; // ⒦
            case  108: return   9383; // ⒧
            case  109: return   9384; // ⒨
            case  110: return   9385; // ⒩
            case  111: return   9386; // ⒪
            case  112: return   9387; // ⒫
            case  113: return   9388; // ⒬
            case  114: return   9389; // ⒭
            case  115: return   9390; // ⒮
            case  116: return   9391; // ⒯
            case  117: return   9392; // ⒰
            case  118: return   9393; // ⒱
            case  119: return   9394; // ⒲
            case  120: return   9395; // ⒳
            case  121: return   9396; // ⒴
            case  122: return   9397; // ⒵
        }
        return primaryCode;
    }

    public int encircle(int primaryCode) {
        switch(primaryCode) {
            case   65: return   9398; // Ⓐ
            case   66: return   9399; // Ⓑ
            case   67: return   9400; // Ⓒ
            case   68: return   9401; // Ⓓ
            case   69: return   9402; // Ⓔ
            case   70: return   9403; // Ⓕ
            case   71: return   9404; // Ⓖ
            case   72: return   9405; // Ⓗ
            case   73: return   9406; // Ⓘ
            case   74: return   9407; // Ⓙ
            case   75: return   9408; // Ⓚ
            case   76: return   9409; // Ⓛ
            case   77: return   9410; // Ⓜ
            case   78: return   9411; // Ⓝ
            case   79: return   9412; // Ⓞ
            case   80: return   9413; // Ⓟ
            case   81: return   9414; // Ⓠ
            case   82: return   9415; // Ⓡ
            case   83: return   9416; // Ⓢ
            case   84: return   9417; // Ⓣ
            case   85: return   9418; // Ⓤ
            case   86: return   9419; // Ⓥ
            case   87: return   9420; // Ⓦ
            case   88: return   9421; // Ⓧ
            case   89: return   9422; // Ⓨ
            case   90: return   9423; // Ⓩ
            case   97: return   9424; // ⓐ
            case   98: return   9425; // ⓑ
            case   99: return   9426; // ⓒ
            case  100: return   9427; // ⓓ
            case  101: return   9428; // ⓔ
            case  102: return   9429; // ⓕ
            case  103: return   9430; // ⓖ
            case  104: return   9431; // ⓗ
            case  105: return   9432; // ⓘ
            case  106: return   9433; // ⓙ
            case  107: return   9434; // ⓚ
            case  108: return   9435; // ⓛ
            case  109: return   9436; // ⓜ
            case  110: return   9437; // ⓝ
            case  111: return   9438; // ⓞ
            case  112: return   9439; // ⓟ
            case  113: return   9440; // ⓠ
            case  114: return   9441; // ⓡ
            case  115: return   9442; // ⓢ
            case  116: return   9443; // ⓣ
            case  117: return   9444; // ⓤ
            case  118: return   9445; // ⓥ
            case  119: return   9446; // ⓦ
            case  120: return   9447; // ⓧ
            case  121: return   9448; // ⓨ
            case  122: return   9449; // ⓩ
        }
        return primaryCode;
    }

    public int toSmallCaps(int primaryCode) {
        switch(primaryCode) {
            case   65: return 127462; // 🇦
            case   66: return 127463; // 🇧
            case   67: return 127464; // 🇨
            case   68: return 127465; // 🇩
            case   69: return 127466; // 🇪
            case   70: return 127467; // 🇫
            case   71: return 127468; // 🇬
            case   72: return 127469; // 🇭
            case   73: return 127470; // 🇮
            case   74: return 127471; // 🇯
            case   75: return 127472; // 🇰
            case   76: return 127473; // 🇱
            case   77: return 127474; // 🇲
            case   78: return 127475; // 🇳
            case   79: return 127476; // 🇴
            case   80: return 127477; // 🇵
            case   81: return 127478; // 🇶
            case   82: return 127479; // 🇷
            case   83: return 127480; // 🇸
            case   84: return 127481; // 🇹
            case   85: return 127482; // 🇺
            case   86: return 127483; // 🇻
            case   87: return 127484; // 🇼
            case   88: return 127485; // 🇽
            case   89: return 127486; // 🇾
            case   90: return 127487; // 🇿
            case   97: return 127462; // 🇦
            case   98: return 127463; // 🇧
            case   99: return 127464; // 🇨
            case  100: return 127465; // 🇩
            case  101: return 127466; // 🇪
            case  102: return 127467; // 🇫
            case  103: return 127468; // 🇬
            case  104: return 127469; // 🇭
            case  105: return 127470; // 🇮
            case  106: return 127471; // 🇯
            case  107: return 127472; // 🇰
            case  108: return 127473; // 🇱
            case  109: return 127474; // 🇲
            case  110: return 127475; // 🇳
            case  111: return 127476; // 🇴
            case  112: return 127477; // 🇵
            case  113: return 127478; // 🇶
            case  114: return 127479; // 🇷
            case  115: return 127480; // 🇸
            case  116: return 127481; // 🇹
            case  117: return 127482; // 🇺
            case  118: return 127483; // 🇻
            case  119: return 127484; // 🇼
            case  120: return 127485; // 🇽
            case  121: return 127486; // 🇾
            case  122: return 127487; // 🇿
        }
        return primaryCode;
    }

    public int ensquare(int primaryCode) {
        switch(primaryCode) {
            case   65: return 127280; // 🄰
            case   66: return 127281; // 🄱
            case   67: return 127282; // 🄲
            case   68: return 127283; // 🄳
            case   69: return 127284; // 🄴
            case   70: return 127285; // 🄵
            case   71: return 127286; // 🄶
            case   72: return 127287; // 🄷
            case   73: return 127288; // 🄸
            case   74: return 127289; // 🄹
            case   75: return 127290; // 🄺
            case   76: return 127291; // 🄻
            case   77: return 127292; // 🄼
            case   78: return 127293; // 🄽
            case   79: return 127294; // 🄾
            case   80: return 127295; // 🄿
            case   81: return 127296; // 🅀
            case   82: return 127297; // 🅁
            case   83: return 127298; // 🅂
            case   84: return 127299; // 🅃
            case   85: return 127300; // 🅄
            case   86: return 127301; // 🅅
            case   87: return 127302; // 🅆
            case   88: return 127303; // 🅇
            case   89: return 127304; // 🅈
            case   90: return 127305; // 🅉
            case   97: return 127280; // 🄰
            case   98: return 127281; // 🄱
            case   99: return 127282; // 🄲
            case  100: return 127283; // 🄳
            case  101: return 127284; // 🄴
            case  102: return 127285; // 🄵
            case  103: return 127286; // 🄶
            case  104: return 127287; // 🄷
            case  105: return 127288; // 🄸
            case  106: return 127289; // 🄹
            case  107: return 127290; // 🄺
            case  108: return 127291; // 🄻
            case  109: return 127292; // 🄼
            case  110: return 127293; // 🄽
            case  111: return 127294; // 🄾
            case  112: return 127295; // 🄿
            case  113: return 127296; // 🅀
            case  114: return 127297; // 🅁
            case  115: return 127298; // 🅂
            case  116: return 127299; // 🅃
            case  117: return 127300; // 🅄
            case  118: return 127301; // 🅅
            case  119: return 127302; // 🅆
            case  120: return 127303; // 🅇
            case  121: return 127304; // 🅈
            case  122: return 127305; // 🅉
        }
        return primaryCode;
    }

    public int toCircularStampLetters(int primaryCode) {
        switch(primaryCode) {
            case   65: return 127312; // 🅐
            case   66: return 127313; // 🅑
            case   67: return 127314; // 🅒
            case   68: return 127315; // 🅓
            case   69: return 127316; // 🅔
            case   70: return 127317; // 🅕
            case   71: return 127318; // 🅖
            case   72: return 127319; // 🅗
            case   73: return 127320; // 🅘
            case   74: return 127321; // 🅙
            case   75: return 127322; // 🅚
            case   76: return 127323; // 🅛
            case   77: return 127324; // 🅜
            case   78: return 127325; // 🅝
            case   79: return 127326; // 🅞
            case   80: return 127327; // 🅟
            case   81: return 127328; // 🅠
            case   82: return 127329; // 🅡
            case   83: return 127330; // 🅢
            case   84: return 127331; // 🅣
            case   85: return 127332; // 🅤
            case   86: return 127333; // 🅥
            case   87: return 127334; // 🅦
            case   88: return 127335; // 🅧
            case   89: return 127336; // 🅨
            case   90: return 127337; // 🅩
            case   97: return 127312; // 🅐
            case   98: return 127313; // 🅑
            case   99: return 127314; // 🅒
            case  100: return 127315; // 🅓
            case  101: return 127316; // 🅔
            case  102: return 127317; // 🅕
            case  103: return 127318; // 🅖
            case  104: return 127319; // 🅗
            case  105: return 127320; // 🅘
            case  106: return 127321; // 🅙
            case  107: return 127322; // 🅚
            case  108: return 127323; // 🅛
            case  109: return 127324; // 🅜
            case  110: return 127325; // 🅝
            case  111: return 127326; // 🅞
            case  112: return 127327; // 🅟
            case  113: return 127328; // 🅠
            case  114: return 127329; // 🅡
            case  115: return 127330; // 🅢
            case  116: return 127331; // 🅣
            case  117: return 127332; // 🅤
            case  118: return 127333; // 🅥
            case  119: return 127334; // 🅦
            case  120: return 127335; // 🅧
            case  121: return 127336; // 🅨
            case  122: return 127337; // 🅩
        }
        return primaryCode;
    }

    public int toReflected(int primaryCode) {
        switch(primaryCode) {
            case   33: return    161; // ¡
            case   40: return     41; // )
            case   41: return     40; // (
            case   60: return     62; // >
            case   62: return     60; // <
            case   63: return    191; // ¿
            case   65: return  11375; // Ɐ
            case   66: return  42221; // ꓭ
            case   67: return    390; // Ɔ
            case   68: return  42231; // ꓷ
            case   69: return    398; // Ǝ
            case   70: return   8498; // Ⅎ
            case   71: return  42216; // ꓨ
            case   72: return     72; // H
            case   73: return     73; // I
            case   74: return   1360; // Ր
            case   75: return  42928; // Ʞ
            case   76: return   8514; // ⅂
            case   77: return  43005; // ꟽ
            case   78: return     78; // N
            case   79: return     79; // O
            case   80: return   1280; // Ԁ
            case   81: return    210; // Ò
            case   82: return  42212; // ꓤ
            case   83: return     83; // S
            case   84: return  42929; // Ʇ
            case   85: return 119365; // 𝉅
            case   86: return    581; // Ʌ
            case   87: return  66224; // 𐊰
            case   88: return     88; // X
            case   89: return   8516; // ⅄
            case   90: return     90; // Z
            case   91: return     93; // ]
            case   93: return     91; // [
            case   97: return    592; // ɐ
            case   98: return    113; // q
            case   99: return    596; // ɔ
            case  100: return    112; // p
            case  101: return    601; // ə
            case  102: return    607; // ɟ
            case  103: return   7543; // ᵷ
            case  104: return    613; // ɥ
            case  105: return   7433; // ᴉ
            case  106: return    383; // ſ
            case  107: return    670; // ʞ
            case  108: return  42881; // ꞁ
            case  109: return    623; // ɯ
            case  110: return    117; // u
            case  111: return    111; // o
            case  112: return    100; // d
            case  113: return     98; // b
            case  114: return    633; // ɹ
            case  115: return    115; // s
            case  116: return    647; // ʇ
            case  117: return    110; // n
            case  118: return    652; // ʌ
            case  119: return    653; // ʍ
            case  120: return    120; // x
            case  121: return    654; // ʎ
            case  122: return    122; // z
            case  123: return    125; // }
            case  125: return    123; // {
            case  171: return    187; // »
            case  187: return    171; // «
        }
        return primaryCode;
    }

    public int toRectangularStampLetters(int primaryCode) {
        switch(primaryCode) {
            case   65: return 127344; // 🅰
            case   66: return 127345; // 🅱
            case   67: return 127346; // 🅲
            case   68: return 127347; // 🅳
            case   69: return 127348; // 🅴
            case   70: return 127349; // 🅵
            case   71: return 127350; // 🅶
            case   72: return 127351; // 🅷
            case   73: return 127352; // 🅸
            case   74: return 127353; // 🅹
            case   75: return 127354; // 🅺
            case   76: return 127355; // 🅻
            case   77: return 127356; // 🅼
            case   78: return 127357; // 🅽
            case   79: return 127358; // 🅾
            case   80: return 127359; // 🅿
            case   81: return 127360; // 🆀
            case   82: return 127361; // 🆁
            case   83: return 127362; // 🆂
            case   84: return 127363; // 🆃
            case   85: return 127364; // 🆄
            case   86: return 127365; // 🆅
            case   87: return 127366; // 🆆
            case   88: return 127367; // 🆇
            case   89: return 127368; // 🆈
            case   90: return 127369; // 🆉
            case   97: return 127344; // 🅰
            case   98: return 127345; // 🅱
            case   99: return 127346; // 🅲
            case  100: return 127347; // 🅳
            case  101: return 127348; // 🅴
            case  102: return 127349; // 🅵
            case  103: return 127350; // 🅶
            case  104: return 127351; // 🅷
            case  105: return 127352; // 🅸
            case  106: return 127353; // 🅹
            case  107: return 127354; // 🅺
            case  108: return 127355; // 🅻
            case  109: return 127356; // 🅼
            case  110: return 127357; // 🅽
            case  111: return 127358; // 🅾
            case  112: return 127359; // 🅿
            case  113: return 127360; // 🆀
            case  114: return 127361; // 🆁
            case  115: return 127362; // 🆂
            case  116: return 127363; // 🆃
            case  117: return 127364; // 🆄
            case  118: return 127365; // 🆅
            case  119: return 127366; // 🆆
            case  120: return 127367; // 🆇
            case  121: return 127368; // 🆈
            case  122: return 127369; // 🆉
        }
        return primaryCode;
    }

    public int exceptions(int primaryCode) {
        switch (primaryCode) {
            case 119893: return 8462;      // ℎ
            case 119965: return 8492;      // ℬ
            case 119968: return 8496;      // ℰ
            case 119969: return 8497;      // ℱ
            case 119971: return 8459;      // ℋ
            case 119972: return 8464;      // ℐ
            case 119975: return 8466;      // ℒ
            case 119976: return 8499;      // ℳ
            case 119981: return 8475;      // ℛ
            case 119994: return 8495;      // ℯ
            case 119996: return 8458;      // ℊ
            case 120004: return 8500;      // ℴ
            case 120070: return 8493;      // ℭ
            case 120075: return 8460;      // ℌ
            case 120076: return 8465;      // ℑ
            case 120085: return 8476;      // ℜ
            case 120093: return 8488;      // ℨ
            case 120122: return 8450;      // ℂ
            case 120127: return 8461;      // ℍ
            case 120133: return 8469;      // ℕ
            case 120135: return 8473;      // ℙ
            case 120136: return 8474;      // ℚ
            case 120137: return 8477;      // ℝ
            case 120145: return 8484;      // ℤ
            default:     return primaryCode;
        }
    }



}
