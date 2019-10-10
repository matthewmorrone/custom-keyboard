package com.custom.keyboard;

import java.util.Random;
import java.util.regex.*;
import org.apache.commons.lang3.StringUtils;

class Util {

    // static int generateRandomIntExclusive(int max) {
    //     return new Random().nextInt(max);
    // }

    private static int countLines(String str) {
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
    }

    private static int generateRandomInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    static String rollADie() {
        return String.valueOf("⚀⚁⚂⚃⚄⚅".charAt(generateRandomInt(1, 6)-1));
    }

    static String flipACoin() {
        return String.valueOf("ⒽⓉ".charAt(generateRandomInt(0, 1)));
    }

    static String replaceLinebreaks(String text)    {return text.replaceAll("\n", "");}
    static String underscoresToSpaces(String text)  {return text.replaceAll("_", " ");}
    static String dashesToSpaces(String text)       {return text.replaceAll("-", " ");}
    static String spacesToUnderscores(String text)  {return text.replaceAll(" ", "_");}
    static String spacesToDashes(String text)       {return text.replaceAll(" ", "-");}
    static String removeSpaces(String text)         {return text.replaceAll(" ",  "");}

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
/*
    public void outputToFile(String filename, String fileContents) {
        try {
            FileOutputStream outputStream = FileOutputStream.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        }
        catch (Exception e) { e.printStackTrace();}
    }
*/
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
            return String.valueOf((char)(int)Integer.decode("0x"+ StringUtils.leftPad(number, 4,"0")));
        }
        catch (Exception e) {
            return number;
        }
    }

    static String camelToSnake(String str) {
        return str.replaceAll("([A-Z])", "_$1").toLowerCase();
    }

    static String doubleCharacters(String str) {
        return str.replaceAll("(.)", "$1$1");
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

    static String joinWithSpaces(String str) {
        return str.replaceAll("[     ]", "");
    }

    static String splitWithSpaces(String str) {
        return str.replaceAll("(.)", "$1 ");
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

    static String morseToChar(String buffer) {
        String result = "";
        switch (buffer) {
            case "·":    result = "e"; break;
            case "-":    result = "t"; break;
            case "··":   result = "i"; break;
            case "·-":   result = "a"; break;
            case "-·":   result = "n"; break;
            case "--":   result = "m"; break;
            case "···":  result = "s"; break;
            case "··-":  result = "u"; break;
            case "·-·":  result = "r"; break;
            case "·--":  result = "w"; break;
            case "-··":  result = "d"; break;
            case "-·-":  result = "k"; break;
            case "--·":  result = "g"; break;
            case "---":  result = "o"; break;
            case "····": result = "h"; break;
            case "···-": result = "v"; break;
            case "··-·": result = "f"; break;
            case "··--": result = ","; break;
            case "·-··": result = "l"; break;
            case "·-·-": result = "."; break;
            case "·--·": result = "p"; break;
            case "·---": result = "j"; break;
            case "-···": result = "b"; break;
            case "-··-": result = "x"; break;
            case "-·-·": result = "c"; break;
            case "-·--": result = "y"; break;
            case "--··": result = "z"; break;
            case "--·-": result = "q"; break;
            case "---·": result = "?"; break;
            case "----": result = "!"; break;
            default: break;
        }

        return result;
    }
}