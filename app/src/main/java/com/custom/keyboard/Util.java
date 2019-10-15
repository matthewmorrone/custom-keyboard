package com.custom.keyboard;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.*;
import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
class Util {

    static String getDateString(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    static String getTimeString(String timeFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        return sdf.format(cal.getTime());
    }

    static String getIndentation(String line) {
        String regex = "^(\\s+).+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        if (m.find()) {
            line = line.replaceAll(regex, "$1");
            if (line.length() % 4 != 0) {
                line += " ";
            }
            return line;
        }
        return "";
    }

    private static int countLines(String str) {
        return str.split("\r\n|\r|\n").length;
    }

    static String toColor(int r, int g, int b) {
        String rs = StringUtils.leftPad(Integer.toHexString(r), 2, "0").toUpperCase();
        String gs = StringUtils.leftPad(Integer.toHexString(g), 2, "0").toUpperCase();
        String bs = StringUtils.leftPad(Integer.toHexString(b), 2, "0").toUpperCase();
        return "#"+rs+gs+bs;
    }

    static String toColor(int a, int r, int g, int b) {
        String as = StringUtils.leftPad(Integer.toHexString(a), 2, "0").toUpperCase();
        String rs = StringUtils.leftPad(Integer.toHexString(r), 2, "0").toUpperCase();
        String gs = StringUtils.leftPad(Integer.toHexString(g), 2, "0").toUpperCase();
        String bs = StringUtils.leftPad(Integer.toHexString(b), 2, "0").toUpperCase();
        return "#"+as+rs+gs+bs;
    }

    static int[] fromColor(String color) {
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
        else {return null;}
        ai = Integer.decode("0x"+as);
        ri = Integer.decode("0x"+rs);
        gi = Integer.decode("0x"+gs);
        bi = Integer.decode("0x"+bs);
        return new int[] {ai, ri, gi, bi};
    }
                
    static int generateRandomInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    static String padRight(String s, int n) {
        return String.format("%-0" + n + "s", s);
    }
    static String padLeft(String s, int n) {
        return String.format("%0" + n + "s", s);
    }

    static String toggleJavaComment(String text) {
        int lineCount = countLines(text);
        String regex;
        if (lineCount < 1) {
            regex = "^\\/\\*\\s*(.+)\\s*\\*\\/$";
        }
        else {
            regex = "^\\/\\*\n(.+)\n\\*\\/$";
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        if (m.find()) {
            return text.replaceAll(regex, "$1");
        }
        if (lineCount < 1) {
            return "/* "+text+" */";
        }
        else {
            return "/*\n"+text+"\n*/";
        }
    }

    static String toggleHtmlComment(String text) {
        int lineCount = countLines(text);
        String regex;
        if (lineCount < 1) {
            regex = "^<\\!--\\s*(.+)\\s*-->$";
        }
        else {
            regex = "^<\\!--\n(.+)\n-->$";
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        if (m.find()) {
            return text.replaceAll(regex, "$1");
        }
        if (lineCount < 1) {
            return "<!-- "+text+" -->";
        }
        else {
            return "<!--\n"+text+"\n-->";
        }
    }

    static String toTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        StringBuilder converted = new StringBuilder();
        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            }
            else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            }
            else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }
        return converted.toString();
    }

    static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        }
        catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    static String truncate(String value, int length) {
        return value.length() > length ? value.substring(0, length) : value;
    }

    static boolean contains(int[] arr, int item) {
        for (int n : arr) {
            if (item == n) {
                return true;
            }
        }
        return false;
    }

    static boolean isDigit(int code) {
        return Character.isDigit(code);
    }

    static Boolean isAlphaNumeric(int primaryCode) {
        return (isAlphabet(primaryCode) || isDigit(primaryCode));
    }

    static Boolean isAlphabet(int primaryCode) {
        if (primaryCode >= 65 && primaryCode <= 91) {
            return true;
        }
        return primaryCode >= 97 && primaryCode <= 123;
    }


    static String convertNumberBase(String number, int base1, int base2) {
        try {
            return Integer.toString(Integer.parseInt(number, base1), base2).toUpperCase();
        }
        catch (Exception e) {
            return number;
        }
    }

    static String convertFromUnicodeToNumber(String glyph) {
        try {
            return String.valueOf(glyph.codePointAt(0));
        }
        catch (Exception e) {
            return glyph;
        }
    }

    static String convertFromNumberToUnicode(String number) {
        try {
            return String.valueOf((char)(int)Integer.decode("0x"+ StringUtils.leftPad(number, 4, "0")));
        }
        catch (Exception e) {
            return number;
        }
    }

    static String doubleCharacters(String str) {
        return str.replaceAll("(.)", "$1$1");
    }

    static String camelToSnake(String str) {
        return str.replaceAll("([A-Z])", "_$1").toLowerCase();
    }

    static String snakeToCamel(String str) {
        StringBuilder nameBuilder = new StringBuilder(str.length());
        boolean capitalizeNextChar = false;
        for (char c:str.toCharArray()) {
            if (c == '_') {
                capitalizeNextChar = true;
                continue;
            }
            if (capitalizeNextChar) {
                nameBuilder.append(Character.toUpperCase(c));
            }
            else {
                nameBuilder.append(c);
            }
            capitalizeNextChar = false;
        }
        return nameBuilder.toString();
    }

    static String underscoresToSpaces(String text)  {return text.replaceAll("_", " ");}
    static String spacesToUnderscores(String text)  {return text.replaceAll(" ", "_");}

    static String dashesToSpaces(String text)       {return text.replaceAll("-", " ");}
    static String spacesToDashes(String text)       {return text.replaceAll(" ", "-");}

    static String spacesToLinebreaks(String str)    {return str.replaceAll(" ", "\n");}
    static String linebreaksToSpaces(String str)    {return str.replaceAll("\n", " ");}

    static String spacesToTabs(String str)          {return str.replaceAll(" ", "\t");}
    static String tabsToSpaces(String str)          {return str.replaceAll("\t", " ");}

    static String splitWithLinebreaks(String str)   {return str.replaceAll("(.)", "$1\n");}
    static String removeLinebreaks(String str)      {return str.replaceAll("\n", "");}

    static String splitWithSpaces(String str)       {return str.replaceAll("(.)", "$1 ");}
    static String removeSpaces(String str)          {return str.replaceAll(" ",  "");}

    static String trimEndingWhitespace(String str) {
        return str.replaceAll("[ \t]+\n", "\n")
                  .replaceAll("[ \t]+$",  "");
    }
    static String trimTrailingWhitespace(String str) {
        return str.replaceAll("([^ \t\r\n])[ \t]+\n",  "\n")
                  .replaceAll("([^ \t\r\n])[ \t]+$",  "$1");
    }

    static String reverse(String str) {
        StringBuilder reverse = new StringBuilder();
        for(int i = str.length() - 1; i >= 0; i--)     {
            reverse.append(str.charAt(i));
        }
        return reverse.toString();
    }

    static boolean contains(String haystack, int primaryCode) {
        return haystack.contains(String.valueOf((char)primaryCode));
    }
    static boolean contains(String haystack, String needle) {
        return haystack.contains(needle);
    }

    static boolean isWordSeparator(int primaryCode) {
        return "\\u0009.,;:!?\\n()[]*&amp;@{}/&lt;&gt;_+=|&quot;".contains(String.valueOf((char)primaryCode));
    }
    static boolean isWordSeparator(String str) {
        return "\\u0009.,;:!?\\n()[]*&amp;@{}/&lt;&gt;_+=|&quot;".contains(str);
    }

    static String buildDigram(String monograms) {
        switch (monograms) {
            case "⚊⚊":   return "⚌";
            case "⚋⚊":   return "⚍";
            case "⚊⚋":   return "⚎";
            case "⚋⚋":   return "⚏";
            default:       return "";
        }
    }
    
    static String buildTrigram(String monograms) {
        switch (monograms) {
            case "⚊⚌":
            case "⚌⚊":
            case "⚊⚊⚊": return "☰";
            case "⚍⚊":
            case "⚋⚌":
            case "⚋⚊⚊": return "☱";
            case "⚊⚍":
            case "⚎⚊":
            case "⚊⚋⚊": return "☲";
            case "⚏⚊":
            case "⚋⚍":
            case "⚋⚋⚊": return "☳";
            case "⚌⚋":
            case "⚊⚎":
            case "⚊⚊⚋": return "☴";
            case "⚍⚋":
            case "⚋⚎":
            case "⚋⚊⚋": return "☵";
            case "⚎⚋":
            case "⚊⚏":
            case "⚊⚋⚋": return "☶";
            case "⚏⚋":
            case "⚋⚏":
            case "⚋⚋⚋": return "☷";
            default:      return "";
        }
    }

    static String buildHexagram(String trigrams) {
        switch(trigrams) {
            case "☰☰": return "䷀";
            case "☷☰": return "䷁";
            case "☳☰": return "䷂";
            case "☵☰": return "䷃";
            case "☶☰": return "䷄";
            case "☴☰": return "䷅";
            case "☲☰": return "䷆";
            case "☱☰": return "䷇";
            case "☰☷": return "䷈";
            case "☷☷": return "䷉";
            case "☳☷": return "䷊";
            case "☵☷": return "䷋";
            case "☶☷": return "䷌";
            case "☴☷": return "䷍";
            case "☲☷": return "䷎";
            case "☱☷": return "䷏";
            case "☰☳": return "䷐";
            case "☷☳": return "䷑";
            case "☳☳": return "䷒";
            case "☵☳": return "䷓";
            case "☶☳": return "䷔";
            case "☴☳": return "䷕";
            case "☲☳": return "䷖";
            case "☱☳": return "䷗";
            case "☰☵": return "䷘";
            case "☷☵": return "䷙";
            case "☳☵": return "䷚";
            case "☵☵": return "䷛";
            case "☶☵": return "䷜";
            case "☴☵": return "䷝";
            case "☲☵": return "䷞";
            case "☱☵": return "䷟";
            case "☰☶": return "䷠";
            case "☷☶": return "䷡";
            case "☳☶": return "䷢";
            case "☵☶": return "䷣";
            case "☶☶": return "䷤";
            case "☴☶": return "䷥";
            case "☲☶": return "䷦";
            case "☱☶": return "䷧";
            case "☰☴": return "䷨";
            case "☷☴": return "䷩";
            case "☳☴": return "䷪";
            case "☵☴": return "䷫";
            case "☶☴": return "䷬";
            case "☴☴": return "䷭";
            case "☲☴": return "䷮";
            case "☱☴": return "䷯";
            case "☰☲": return "䷰";
            case "☷☲": return "䷱";
            case "☳☲": return "䷲";
            case "☵☲": return "䷳";
            case "☶☲": return "䷴";
            case "☴☲": return "䷵";
            case "☲☲": return "䷶";
            case "☱☲": return "䷷";
            case "☰☱": return "䷸";
            case "☷☱": return "䷹";
            case "☳☱": return "䷺";
            case "☵☱": return "䷻";
            case "☶☱": return "䷼";
            case "☴☱": return "䷽";
            case "☲☱": return "䷾";
            case "☱☱": return "䷿";
            default:    return "";
        }
    }

    static String morseToChar(String buffer) {
        switch (buffer) {
            case "·-": return "a";
            case "-···": return "b";
            case "-·-·": return "c";
            case "-··": return "d";
            case "·": return "e";
            case "··-·": return "f";
            case "--·": return "g";
            case "····": return "h";
            case "··": return "i";
            case "·---": return "j";
            case "-·-": return "k";
            case "·-··": return "l";
            case "--": return "m";
            case "-·": return "n";
            case "---": return "o";
            case "·--·": return "p";
            case "--·-": return "q";
            case "·-·": return "r";
            case "···": return "s";
            case "-": return "t";
            case "··-": return "u";
            case "···-": return "v";
            case "·--": return "w";
            case "-··-": return "x";
            case "-·--": return "y";
            case "--··": return "z";
            case "-----": return "0";
            case "·----": return "1";
            case "··---": return "2";
            case "···--": return "3";
            case "····-": return "4";
            case "·····": return "5";
            case "-····": return "6";
            case "--···": return "7";
            case "---··": return "8";
            case "----·": return "9";
            case "·-·-·-": return ".";
            case "--··--": return ",";
            case "··--··": return "?";
            case "·----·": return "'";
            case "-·-·--": return "!";
            case "-··-·": return "/";
            case "-·--·": return "(";
            case "-·--·-": return ")";
            case "·-···": return "&";
            case "---···": return ":";
            case "-·-·-·": return ";";
            case "-···-": return "=";
            case "·-·-·": return "+";
            case "-····-": return "-";
            case "··--·-": return "_";
            case "·-··-·": return "\"";
            case "···-··-": return "$";
            case "·--·-·": return "@";
            case "·--·-": return "à";
            // case "·-·-": return "ä";
            // case "·--·-": return "å";
            // case "·-·-": return "ą";
            case "·-·-": return "æ";
            // case "-·-··": return "ć";
            // case "-·-··": return "ĉ";
            case "-·-··": return "ç";
            // case "----": return "ch";
            // case "··-··": return "đ";
            case "··--·": return "ð";
            case "··-··": return "é";
            // case "·-··-": return "è";
            // case "··-··": return "ę";
            case "--·-·": return "ĝ";
            // case "----": return "ĥ";
            case "·---·": return "ĵ";
            case "·-··-": return "ł";
            // case "--·--": return "ń";
            case "--·--": return "ñ";
            // case "---·": return "ó";
            // case "---·": return "ö";
            case "---·": return "ø";
            case "···-···": return "ś";
            case "···-·": return "ŝ";
            case "----": return "š";
            case "·--··": return "þ";
            case "··--": return "ü";
            // case "··--": return "ŭ";
            case "--··-·": return "ź";
            case "--··-": return "ż";
            default: return "";
        }
    }


    static String charToMorse(String buffer) {
        switch (buffer) {
            case " ": return " ";
            case "e": return "·";
            case "t": return "-";
            case "i": return "··";
            case "a": return "·-";
            case "n": return "-·";
            case "m": return "--";
            case "s": return "···";
            case "u": return "··-";
            case "r": return "·-·";
            case "w": return "·--";
            case "d": return "-··";
            case "k": return "-·-";
            case "g": return "--·";
            case "o": return "---";
            case "h": return "····";
            case "v": return "···-";
            case "f": return "··-·";
            case "l": return "·-··";
            case "p": return "·--·";
            case "j": return "·---";
            case "b": return "-···";
            case "x": return "-··-";
            case "c": return "-·-·";
            case "y": return "-·--";
            case "z": return "--··";
            case "q": return "--·-";
            case "0": return "-----";
            case "1": return "·----";
            case "2": return "··---";
            case "3": return "···--";
            case "4": return "····-";
            case "5": return "·····";
            case "6": return "-····";
            case "7": return "--···";
            case "8": return "---··";
            case "9": return "----·";
            case "ä": return "·-·-";
            case "ą": return "·-·-";
            case "æ": return "·-·-";
            case "ch": return "----";
            case "ĥ": return "----";
            case "ó": return "---·";
            case "ö": return "---·";
            case "ø": return "---·";
            case "š": return "----";
            case "ü": return "··--";
            case "ŭ": return "··--";
            case "/": return "-··-·";
            case "(": return "-·--·";
            case "&": return "·-···";
            case "=": return "-···-";
            case "+": return "·-·-·";
            case "à": return "·--·-";
            case "å": return "·--·-";
            case "ć": return "-·-··";
            case "ĉ": return "-·-··";
            case "ç": return "-·-··";
            case "đ": return "··-··";
            case "ð": return "··--·";
            case "é": return "··-··";
            case "è": return "·-··-";
            case "ę": return "··-··";
            case "ĝ": return "--·-·";
            case "ĵ": return "·---·";
            case "ł": return "·-··-";
            case "ń": return "--·--";
            case "ñ": return "--·--";
            case "ŝ": return "···-·";
            case "þ": return "·--··";
            case "ż": return "--··-";
            case ".": return "·-·-·-";
            case ",": return "--··--";
            case "?": return "··--··";
            case "'": return "·----·";
            case "!": return "-·-·--";
            case ")": return "-·--·-";
            case ":": return "---···";
            case ";": return "-·-·-·";
            case "-": return "-····-";
            case "\"": return "·-··-·";
            case "@": return "·--·-·";
            case "ź": return "--··-·";
            case "$": return "···-··-";
            case "ś": return "···-···";
            default:  return "";
        }
    }


    static String rollADie() {
        return String.valueOf("⚀⚁⚂⚃⚄⚅".charAt(generateRandomInt(1, 6)-1));
    }

    static String flipACoin() {
        return String.valueOf("ⒽⓉ".charAt(generateRandomInt(0, 1)));
    }

    static String castALot() {
        return String.valueOf("⚊⚋".charAt(generateRandomInt(0, 1)));
    }


}
