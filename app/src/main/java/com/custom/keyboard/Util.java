package com.custom.keyboard;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.URLUtil;

import java.lang.reflect.Field;
import java.net.URL;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Util {

    // general
    public static void noop() {}
    public static boolean contains(String haystack, int primaryCode) {
        return haystack.contains(String.valueOf(primaryCode));
    }
    public static boolean contains(String haystack, String needle) {
        return haystack.contains(needle);
    }
    public static boolean contains(int[] arr, int item) {
        for (int n : arr) {
            if (item == n) {
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Integer> asUnicodeArray(String text) {
        ArrayList<Integer> result = new ArrayList<>();
        if (text.length() < 1) return result;
        char[] chars = text.toCharArray();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(ch);
            i += Character.charCount(ch);
        }
        return result; // result.toArray(new String[0]);
    }
    public static ArrayList<Character> asUnicodeCharArray(String text) {
        ArrayList<Character> result = new ArrayList<>();
        if (text.length() < 1) return result;
        char[] chars = text.toCharArray();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add((char)ch);
            i += Character.charCount(ch);
        }
        return result; // result.toArray(new String[0]);
    }
    public static CharSequence asCharSequence(String text) {
        Bundle bundle = new Bundle();
        return bundle.getCharSequence(text);
    }
    public static String convertNumberBase(String number, int base1, int base2) {
        try {
            return Integer.toString(Integer.parseInt(number, base1), base2).toUpperCase();
        }
        catch (Exception e) {
            return number;
        }
    }
    public static String convertFromUnicodeToNumber(String glyph) {
        try {
            return String.valueOf(glyph.codePointAt(0));
        }
        catch (Exception e) {
            return glyph;
        }
    }
    public static String convertFromNumberToUnicode(String number) {
        try {
            // Util.largeIntToChar(primaryCode)
            return String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(number, 4, "0")));
        }
        catch (Exception e) {
            return number;
        }
    }
    public static String largeIntToChar(int primaryCode) {
        return new String(Character.toChars(primaryCode));
    }

    // is
    public static boolean containsNumber(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public static boolean isDigit(int code) {
        return Character.isDigit(code);
    }
    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        }
        catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
    public static boolean isAlphaNumeric(int primaryCode) {
        return (isLetter(primaryCode) || isDigit(primaryCode));
    }
    public static boolean isLetter(int primaryCode) {
        if (primaryCode >= 65 && primaryCode <= 91) {
            return true;
        }
        return primaryCode >= 97 && primaryCode <= 123;
    }
    public static boolean isAstralCharacter(String ch) {
        int value = Integer.parseInt(ch, 16);
        char[] codeUnits = Character.toChars(value);
        return codeUnits.length > 1;
    }
    public static boolean isValidUrl(String url) {
        return URLUtil.isValidUrl(url);
    }
    public static boolean isValidUri(String uri) {
        final URL url;
        try {
            url = new URL(uri);
        }
        catch (Exception e1) {
            return false;
        }
        return "http".equals(url.getProtocol());
    }
    public static boolean isValidPhoneNumber(String s) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
    static String mWordSeparators = "\\u0009.,;:!?\\n()[]*&amp;@{}/&lt;&gt;_+=|&quot;";
    public static String getWordSeparators() {
        return mWordSeparators;
    }
    public static boolean isWordSeparator(int primaryCode) {
        return mWordSeparators.contains(String.valueOf((char) primaryCode));
    }
    public static boolean isWordSeparator(String text) {
        // return text.contains(". ") || text.contains("? ") || text.contains("! ");
        return mWordSeparators.contains(text);
    }
    public static boolean isWordSeparator(int primaryCode, String delimiters) {
        // Util.largeIntToChar(primaryCode)
        return delimiters.contains(String.valueOf((char)primaryCode));
    }
    public static boolean isWordSeparator(String text, String delimiters) {
        return delimiters.contains(text);
    }

    // info
    public static HashMap<Character, Integer> getCharacterFrequencies(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer val = map.get(c);
            if (val != null) map.put(c, val + 1);
            else map.put(c, 1);
        }
        return map;
    }
    public static Map<String, Integer> getWordFrequencies(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (String w : words) {
            Integer n = map.get(w);
            n = (n == null) ? 1 : ++n;
            map.put(w, n);
        }
        return map;
    }
    public static String unidata(String text) {
        if (text.length() < 1) return "";

        if (Character.isHighSurrogate(text.charAt(0))
        ||  Character.isLowSurrogate(text.charAt(0))) {
            return unidata((int)text.codePointAt(0));
        }

        // Util.largeIntToChar(primaryCode)
        return unidata((int)text.charAt(0));
    }
    public static String unidata(int primaryCode) {
        // Util.largeIntToChar(primaryCode)
        return 
toTitleCase(Character.getName(primaryCode))
+"\n"+
primaryCode
+"\t0x"+
padLeft(convertNumberBase(String.valueOf(primaryCode), 10, 16), 4).trim();
        /*
        return ""+
            toTitleCase(Character.getName(primaryCode))+
            "\n"+
            primaryCode+
            "\t0x"+padLeft(convertNumberBase(String.valueOf(primaryCode), 10, 16), 4).trim()+
            ""+

               ""+toTitleCase(underscoresToSpaces(Character.UnicodeBlock.of(primaryCode).toString()))+"\n"+
               ""+toTitleCase(underscoresToSpaces(getCharacterType((byte)Character.getType(primaryCode))))+"\n"+
               (Character.isUpperCase(primaryCode) ? "Uppercase " : "") +
               (Character.isTitleCase(primaryCode) ? "Titlecase " : "") +
               (Character.isLowerCase(primaryCode) ? "Lowercase " : "") +
               "Upper: " + Character.toUpperCase(primaryCode)+", " +
               "Title: " + Character.toTitleCase(primaryCode)+", " +
               "Lower: " + Character.toLowerCase(primaryCode)+", " +
               (Character.isLetter(primaryCode) ? "Letter, " : "") +
               (Character.isDigit(primaryCode) ? "Digit, " : "") +
               (Character.isSpaceChar(primaryCode) ? "Space Char, " : "") +
               (Character.isWhitespace(primaryCode) ? "Whitespace, " : "") +
               (Character.isAlphabetic(primaryCode) ? "Alphabetic, " : "") +
               (Character.isBmpCodePoint(primaryCode) ? "Bmp Code Point, " : "") +
               (Character.isDefined(primaryCode) ? "Defined, " : "") +
               (Character.isIdentifierIgnorable(primaryCode) ? "Identifier Ignorable, " : "") +
               (Character.isIdeographic(primaryCode) ? "Ideographic, " : "") +
               (Character.isISOControl(primaryCode) ? "ISO Control, " : "") +
               (Character.isJavaIdentifierPart(primaryCode) ? "Java Identifier Part, " : "") +
               (Character.isJavaIdentifierStart(primaryCode) ? "Java Identifier Start, " : "") +
               (Character.isMirrored(primaryCode) ? "Mirrored, " : "") +
               (Character.isSupplementaryCodePoint(primaryCode) ? "Supplementary Code Point, " : "") +
               (Character.isUnicodeIdentifierPart(primaryCode) ? "Unicode Identifier Part, " : "") +
               (Character.isUnicodeIdentifierStart(primaryCode) ? "Unicode Identifier Start, " : "") +
               (Character.isValidCodePoint(primaryCode) ? "ValidCodePoint, " : "") +
               "Value " + Character.getNumericValue(primaryCode)+", " +
               "Direction " + Character.getDirectionality(primaryCode)+""+

               ""
               ;
               */

    }
/*
    public String getCharType(byte ch)} {
        return "";
    }
*/
    public static String getCharType(byte ch) {
        switch (ch) {
            case 8:      return "Mc";
            case 23:     return "Pc";
            case 15:     return "Cc";
            case 26:     return "Sc";
            case 20:     return "Pd";
            case 9:      return "Nd";
            case 7:      return "Me";
            case 22:     return "Pe";
            case 30:     return "Pf";
            case 16:     return "Cf";
            case 29:     return "Pi";
            case 10:     return "Nl";
            case 13:     return "Zl";
            case 2:      return "Ll";
            case 25:     return "Sm";
            case 4:      return "Lm";
            case 27:     return "Sk";
            case 6:      return "Mn";
            case 5:      return "Lo";
            case 11:     return "No";
            case 24:     return "Po";
            case 28:     return "So";
            case 14:     return "Zp";
            case 18:     return "Co";
            case 12:     return "Zs";
            case 21:     return "Ps";
            case 19:     return "Cs";
            case 3:      return "Lt";
            case 0:      return "Cn";
            case 1:      return "Lu";
            default:     return "";
        }
    }
    public static String getCharacterType(byte ch) {
        switch (ch) {
            case 8:      return "COMBINING_SPACING_MARK";
            case 23:     return "CONNECTOR_PUNCTUATION";
            case 15:     return "CONTROL";
            case 26:     return "CURRENCY_SYMBOL";
            case 20:     return "DASH_PUNCTUATION";
            case 9:      return "DECIMAL_DIGIT_NUMBER";
            case 7:      return "ENCLOSING_MARK";
            case 22:     return "END_PUNCTUATION";
            case 30:     return "FINAL_QUOTE_PUNCTUATION";
            case 16:     return "FORMAT";
            case 29:     return "INITIAL_QUOTE_PUNCTUATION";
            case 10:     return "LETTER_NUMBER";
            case 13:     return "LINE_SEPARATOR";
            case 2:      return "LOWERCASE_LETTER";
            case 25:     return "MATH_SYMBOL";
            case 4:      return "MODIFIER_LETTER";
            case 27:     return "MODIFIER_SYMBOL";
            case 6:      return "NON_SPACING_MARK";
            case 5:      return "OTHER_LETTER";
            case 11:     return "OTHER_NUMBER";
            case 24:     return "OTHER_PUNCTUATION";
            case 28:     return "OTHER_SYMBOL";
            case 14:     return "PARAGRAPH_SEPARATOR";
            case 18:     return "PRIVATE_USE";
            case 12:     return "SPACE_SEPARATOR";
            case 21:     return "START_PUNCTUATION";
            case 19:     return "SURROGATE";
            case 3:      return "TITLECASE_LETTER";
            case 0:      return "UNASSIGNED";
            case 1:      return "UPPERCASE_LETTER";
            default:     return "";
        }
    }

    // case
    public static boolean isLowerCase(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isLowerCase(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public static boolean isTitleCase(String text) {
        if (text == null || text.length() < 1) return false;
        if (!Character.isUpperCase(text.charAt(0))) {
            return false;
        }
        for (int i = 1; i < text.length(); i++) {
            if (!Character.isLowerCase(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public static boolean isUpperCase(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isUpperCase(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public static boolean containsLowerCase(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLowerCase(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public static boolean containsUpperCase(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    public static String toLowerCase(String text) {
        return text.toLowerCase();
    }
    public static String toTitleCase(String input) {
        input = toLowerCase(input);
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } 
            else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }
    public static String toUpperCase(String text) {
        return text.toUpperCase();
    }
    public static String toAlterCase(String text) {
        char[] array = new char[]{};

        int seed = generateRandomInt(0, 1);
        array = text.toCharArray();

        for (int i = seed; i < array.length - seed; i += 2) {
            if (array[i] == ' ') {
                i++;
            }
            if (i % 2 == 0) array[i] = Character.toLowerCase(array[i]);
            if (i % 2 == 1) array[i] = Character.toUpperCase(array[i]);
        }

        text = new String(array);
        return text;
    }

    // char/word/line manipulation
    public static int countChars(String text) {
        return text.codePointCount(0, text.length());
    }
    public static int countWords(String text) {
        return text.split("[\\u0009.,;:!?\\n()\\[\\]*&@{}/<>_+=|\"]").length;
    }
    public static int countLines(String text) {
        return text.split("\r\n|\r|\n").length;
    }
    public static String[] getChars(String text) {
        return text.split("");
        // return text.split("(?!^)");
    }
    public static String[] getWords(String text) {
        return text.split("[\\u0009.,;:!?\\n()\\[\\]*&@{}/<>_+=|\"]");
    }
    public static String[] getLines(String text) {
        return text.split("\r\n|\r|\n");
    }
    public static String sortChars(String text) {
        String[] lines = getChars(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.sort(result);
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String sortWords(String text) {
        String[] lines = getWords(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.sort(result);
        return StringUtils.join(result.toArray(new String[0]), " ");
    }
    public static String sortLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.sort(result);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String reverseChars(String text) {
        return new StringBuilder(text).reverse().toString();
    }
    public static String reverseWords(String text) {
        String[] lines = getWords(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.reverse(result);
        return StringUtils.join(result.toArray(new String[0]), " ");
    }
    public static String reverseLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.reverse(result);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String shuffleChars(String text) {
        String[] lines = getChars(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.shuffle(result);
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String shuffleWords(String text) {
        String[] lines = getWords(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.shuffle(result);
        return StringUtils.join(result.toArray(new String[0]), " ");
    }
    public static String shuffleLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.shuffle(result);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }

    // @TODO: removeDuplicateChars or uniqueChars, pick one and ensure consistency
    public static String removeDuplicateChars(String text) {
        char[] str = text.toCharArray();
        int n = str.length;
        int index = 0, i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < i; j++) {
                if (str[i] == str[j]) {
                    break;
                }
            }
            if (j == i) {
                str[index++] = str[i];
            }
        }
        return String.valueOf(Arrays.copyOf(str, index));
    }
    public static String uniqueChars(String text) {
        String[] lines = getChars(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Set<String> unique = new LinkedHashSet<>(result);
        return StringUtils.join(unique.toArray(new String[0]), "");
    }
    public static String uniqueWords(String text) {
        String[] lines = getWords(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Set<String> unique = new LinkedHashSet<>(result);
        return StringUtils.join(unique.toArray(new String[0]), " ");
    }
    public static String uniqueLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Set<String> unique = new LinkedHashSet<>(result);
        return StringUtils.join(unique.toArray(new String[0]), "\n");
    }
    public static String doubleChars(String text) {
        return text.replaceAll("(.)", "$1$1");
    }
    public static String doubleWords(String text) {
        String[] lines = getWords(text);
        ArrayList<String> result = new ArrayList<>();
        for(String line : lines) {
            result.add(line);
            result.add(line);
        }
        return StringUtils.join(result.toArray(new String[0]), " ");
    }
    public static String doubleLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        for(String line : lines) {
            result.add(line);
            result.add(line);
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }



    // spacing stuff
    public static String trim(String text) {
        text = text.trim()
            .replaceAll("^\u00A0+", "")
            .replaceAll("^\u0020+", "")
            .replaceAll("^\u0009+", "")
            .replaceAll("^\u0010+", "")
            .replaceAll("\u00A0+$", "")
            .replaceAll("\u0020+$", "")
            .replaceAll("\u0009+$", "")
            .replaceAll("\u0010+$", "");
        return text;
    }
    public static String trimEndingWhitespace(String text) {
        return text.replaceAll("[ \t]+\n", "\n")
            .replaceAll("[ \t]+$",  "");
    }
    public static String trimTrailingWhitespace(String text) {
        return text.replaceAll("([^ \t\r\n])[ \t]+\n", "\n")
            .replaceAll("([^ \t\r\n])[ \t]+$",  "$1");
    }
    public static String getIndentation(String line) {
        String regex = "^(\\s+).+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        if (m.find()) {
            line = line.replaceAll(regex, "$1");
            /*if (line.length() % 4 != 0) {
                line += " ";
            }*/
            return line;
        }
        return "";
    }
    public static String increaseIndentation(String text, String indentation) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line.replaceAll("^", indentation));
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String decreaseIndentation(String text, String indentation) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line.replaceAll("^"+indentation, ""));
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String padLeft(String text, int length) {
        return padLeft(text, length, " ");
    }
    public static String padLeft(String text, int length, String pad) {
        if (text.length() >= length) {
            return text;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - text.length()) {
            sb.append(pad);
        }
        sb.append(text);
        return sb.toString();
    }
    public static String padRight(String text, int length) {
        return padRight(text, length, " ");
    }
    public static String padRight(String text, int length, String pad) {
        if (text.length() >= length) {
            return text;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        while (sb.length() < length - text.length()) {
            sb.append(pad);
        }
        return sb.toString();
    }
    public static String splitWithLinebreaks(String text){
        return text.replaceAll("(.)", "$1\n");
    }
    public static String splitWithSpaces(String text) {
        return text.replaceAll("(.)", "$1 ");
    }
    public static String removeSpaces(String text) {
        return text.replaceAll(" ", "");
    }
    public static String removeLinebreaks(String text) {
        return text.replaceAll("\n", "");
    }
    public static String reduceSpaces(String text) {
        return text.replaceAll(" +", " ");
    }
    public static String reduceLinebreaks(String text) {
        return text.replaceAll("\n+", "\n");
    }
    public static String underscoresToSpaces(String text) {
        return text.replaceAll("_", " ");
    }
    public static String spacesToUnderscores(String text) {
        return text.replaceAll(" ", "_");
    }
    public static String dashesToSpaces(String text) {
        return text.replaceAll("-", " ");
    }
    public static String spacesToDashes(String text) {
        return text.replaceAll(" ", "-");
    }
    public static String spacesToLinebreaks(String text) {
        return text.replaceAll(" ", "\n");
    }
    public static String linebreaksToSpaces(String text) {
        return text.replaceAll("\n", " ");
    }
    public static String spacesToTabs(String text) {
        return text.replaceAll(" ", "\t");
    }
    public static String tabsToSpaces(String text) {
        return text.replaceAll("\t", " ");
    }
    public static String replaceNbsp(String text) {
        // String nbsp = "&nbsp;"
        // text.replaceAll("&nbsp;", " ");
        return text.replaceAll("\u00a0", " ");
    }
    public static boolean hasZWSP(String text) {
        return text.contains("");
    }
    public static String replaceZWSP(String text, String ins) {
        return text.replaceAll("", "" + ins);
    }
    public static String removeZWSP(String text) {
        return text.replaceAll("", "");
    }

    // text manipulation
    public static String rotateLinesBackward(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.rotate(result, -1);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String rotateLinesForward(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.rotate(result, 1);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String addLineNumbers(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        int index = 0;
        for (String line : lines) {
            result.add(++index + " " + line);
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String removeLineNumbers(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line.replaceAll("^\\d+\\s*", ""));
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String toggleJavaComment(String text) {
        int lineCount = countLines(text);
        String regex;
        if (lineCount < 2) {
            regex = "^/\\*\\s*(.+)\\s*\\*/$";
        }
        else {
            regex = "^/\\*\n(.+)\n\\*/$";
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        if (m.find()) {
            return text.replaceAll(regex, "$1");
        }
        if (lineCount < 2) {
            return "/* " + text + " */";
        }
        else {
            return "/*\n" + text + "\n*/";
        }
    }
    public static String toggleHtmlComment(String text) {
        int lineCount = countLines(text);
        String regex;
        if (lineCount < 2) {
            regex = "^<!--\\s*(.+)\\s*-->$";
        }
        else {
            regex = "^<!--\n(.+)\n-->$";
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        if (m.find()) {
            return text.replaceAll(regex, "$1");
        }
        if (lineCount < 2) {
            return "<!-- " + text + " -->";
        }
        else {
            return "<!--\n" + text + "\n-->";
        }
    }
    public static String toggleLineComment(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        String regexWith = "(^|\\n)(\\s*)// (.+?)(\\n|$)";
        String regexSans = "(^|\\n)(\\s*)(.+?)(\\n|$)";
        Pattern p = Pattern.compile(regexWith);
        Matcher m = p.matcher(lines[0]);
        boolean found = m.find();
        p = Pattern.compile(found ? regexWith : regexSans);
        for (String line : lines) {
            m = p.matcher(line);
            if (m.find()) {
                result.add(line.replaceAll(found ? regexWith : regexSans, found ? "$1$2$3$4" : "$1$2// $3$4"));
            }
            else {
                result.add(line);
            }
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String camelToSnake(String text) {
        return text.replaceAll("([A-Z])", "_$1").toLowerCase();
    }
    public static String snakeToCamel(String text) {
        StringBuilder nameBuilder = new StringBuilder(text.length());
        boolean capitalizeNextChar = false;
        for (char c : text.toCharArray()) {
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
    public static String normalize(String text) {
        // String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        // return normalized.toLowerCase(Locale.ENGLISH);
        return Normalizer.normalize(text, Normalizer.Form.NFD);
        //.replaceAll("[^\\p{ASCII}]", "")
        //.toLowerCase();
    }
    public static String slug(String text) {
        return text.replaceAll("[ÀÁÂÃÄÅĀĂĄḀẠẢẤẦẨẪẬẮẰẲẴẶǍǺȦȀȂǞǠǢǼ]", "A")
            .replaceAll("[àáâãäåāăąḁạảấầẩẫậắằẳẵặẚǎǻȧȁȃǟǡǣǽ]", "a")
            .replaceAll("[ḂḄḆ]", "B")
            .replaceAll("[ḃḅḇ]", "b")
            .replaceAll("[ḉćĉċč]", "c")
            .replaceAll("[ḈĆĈĊČ]", "C")
            .replaceAll("[ḋḍḏḑḓď]", "d")
            .replaceAll("[ḊḌḎḐḒĎ]", "D")
            .replaceAll("[ȆĒĔĖĘĚÈÉÊËḔḖḘḚḜẸẺẼẾỀỂỄỆȨȄ]", "E")
            .replaceAll("[ȇēĕėęěèéêëḕḗḙḛḝẹẻẽếềểễệȩȅ]", "e")
            .replaceAll("[Ḟ]", "F")
            .replaceAll("[ḟ]", "f")
            .replaceAll("[ḠǴǦĜĞĠĢ]", "G")
            .replaceAll("[ḡǵǧĝğġģ]", "g")
            .replaceAll("[ḢḤḦḨḪĤȞ]", "H")
            .replaceAll("[ḣḥḧḩḫẖĥȟ]", "h")
            .replaceAll("[İÌÍÎÏḬḮỈỊǏȈȊĨĪĬĮ]", "I")
            .replaceAll("[ıìíîïḭḯỉịǐȉȋĩīĭį]", "i")
            .replaceAll("[Ĵ]", "J")
            .replaceAll("[ǰĵ]", "j")
            .replaceAll("[ḰḲḴǨĶ]", "K")
            .replaceAll("[ḱḳḵǩķ]", "k")
            .replaceAll("[ḶḸḺḼĹĻĽĿŁ]", "L")
            .replaceAll("[ḷḹḻḽĺļľŀł]", "l")
            .replaceAll("[ḿṁṃ]", "m")
            .replaceAll("[ḾṀṂ]", "M")
            .replaceAll("[ṄṆṈṊÑǸŃŅŇ]", "N")
            .replaceAll("[ṅṇṉṋñǹńņňŉ]", "n")
            .replaceAll("[ÒÓÔÕÖṌṎṐṒỌỎỐỒỔỖỘỚỜỞỠỢǑǪǬȌȎŌŎŐȪȬȮȰǾ]", "O")
            .replaceAll("[òóôõöṍṏṑṓọỏốồổỗộớờởỡợǒǫǭȍȏōŏőȫȭȯȱǿ]", "o")
            .replaceAll("[ṕṗ]", "p")
            .replaceAll("[ṔṖ]", "P")
            .replaceAll("[ṙṛṝṟȑȓŕŗř]", "r")
            .replaceAll("[ṘṚṜṞȐȒŔŖŘ]", "R")
            .replaceAll("[ṡṣṥṧṩșśŝşš]", "s")
            .replaceAll("[ṠṢṤṦṨȘŚŜŞŠ]", "S")
            .replaceAll("[ṪṬṮṰȚŢŤ]", "T")
            .replaceAll("[ṫṭṯṱẗțţť]", "t")
            .replaceAll("[ùúûüũūŭůűṳṵṷṹṻụủứừửữựȕȗǔǖǘǚǜų]", "u")
            .replaceAll("[ÙÚÛÜŨŪŬŮŰṲṴṶṸṺỤỦỨỪỬỮỰȔȖǓǕǗǙǛŲ]", "U")
            .replaceAll("[ṼṾ]", "V")
            .replaceAll("[ṽṿ]", "v")
            .replaceAll("[ẀẂẄẆẈŴ]", "W")
            .replaceAll("[ẁẃẅẇẉẘŵ]", "w")
            .replaceAll("[ẋẍ]", "x")
            .replaceAll("[ẊẌ]", "X")
            .replaceAll("[ẎỲỴỶỸÝȲŶŸ]", "Y")
            .replaceAll("[ẏỳỵỷỹẙýȳŷÿ]", "y")
            .replaceAll("[ẑẓẕźżžǯ]", "z")
            .replaceAll("[ẐẒẔŹŻŽǮ]", "Z")
            .replaceAll("[ΆἈἉἊἋἌἍἎἏᾈᾉᾊᾋᾌᾍᾎᾏᾸᾹᾺΆᾼ]", "Α")
            .replaceAll("[άἀἁἂἃἄἅἆἇὰάᾀᾁᾂᾃᾄᾅᾆᾇᾰᾱᾲᾳᾴᾶᾷ]", "α")
            .replaceAll("[ΈἘἙἚἛἜἝῈΈ]", "Ε")
            .replaceAll("[έἐἑἒἓἔἕὲέ]", "ε")
            .replaceAll("[ΉἨἩἪἫἬἭἮἯᾘᾙᾚᾛᾜᾝᾞᾟῊΉῌ]", "Η")
            .replaceAll("[ήἠἡἢἣἤἥἦἧὴήᾐᾑᾒᾓᾔᾕᾖᾗῂῃῄῆῇ]", "η")
            .replaceAll("[ΪΊἸἹἺἻἼἽἾἿῘῙῚΊ]", "Ι")
            .replaceAll("[ίϊΐἰἱἲἳἴἵἶἷὶίῐῑῒΐῖῗ]", "ι")
            .replaceAll("[ΌὈὉὊὋὌὍ]", "Ο")
            .replaceAll("[όὀὁὂὃὄὅὸό]", "ο")
            .replaceAll("[Ῥ]", "Ρ")
            .replaceAll("[ῤῥ]", "ρ")
            .replaceAll("[ΫΎὙὛὝὟῨῩῪΎϒϓϔ]", "Υ")
            .replaceAll("[ΰϋύὐὑὒὓὔὕὖὗὺύῠῡῢΰῦῧ]", "υ")
            .replaceAll("[ΏὨὩὪὫὬὭὮὯᾨᾩᾪᾫᾬᾭᾮᾯῸΌῺΏῼ]", "Ω")
            .replaceAll("[ώὠὡὢὣὤὥὦὧὼώᾠᾡᾢᾣᾤᾥᾦᾧῲῳῴῶῷ]", "ω")
            .replaceAll("[ӐӒ]", "А")
            .replaceAll("[Ӛ]", "Ә")
            .replaceAll("[ЃҐҒӺҔӶ]", "Г")
            .replaceAll("[ЀӖЁ]", "Е")
            .replaceAll("[ӁӜҖ]", "Ж")
            .replaceAll("[Ӟ]", "З")
            .replaceAll("[ЍӤӢҊЙ]", "И")
            .replaceAll("[Ї]", "І")
            .replaceAll("[ЌҚҠҞҜ]", "К")
            .replaceAll("[ӉҢӇҤЊ]", "Н")
            .replaceAll("[Ӧ]", "О")
            .replaceAll("[Ӫ]", "Ө")
            .replaceAll("[Ҧ]", "П")
            .replaceAll("[Ҏ]", "Р")
            .replaceAll("[Ҫ]", "С")
            .replaceAll("[Ҭ]", "Т")
            .replaceAll("[ЎӰӲӮ]", "У")
            .replaceAll("[Ұ]", "Ү")
            .replaceAll("[ӼӾҲ]", "Х")
            .replaceAll("[ѾѼ]", "Ѡ")
            .replaceAll("[Ҵ]", "Ц")
            .replaceAll("[ӴҶӋҸ]", "Ч")
            .replaceAll("[Ҿ]", "Ҽ")
            .replaceAll("[Ӹ]", "Ы")
            .replaceAll("[ҌѢ]", "Ь")
            .replaceAll("[Ӭ]", "Э")
            .replaceAll("[Ѷ]", "Ѵ");
    }
    public static String truncate(String value, int length) {
        return value.length() > length ? value.substring(0, length) : value;
    }
    public static String formatJson(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.toString(4);
    }
    public static String rot13(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'a' && c <= 'm') c += 13;
            else if (c >= 'A' && c <= 'M') c += 13;
            else if (c >= 'n' && c <= 'z') c -= 13;
            else if (c >= 'N' && c <= 'Z') c -= 13;
            sb.append(c);
        }
        return sb.toString();
    }

    // random stuff
    public static int generateRandomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
    public static String pickALetter() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        return String.valueOf(letters.charAt(generateRandomInt(1, 26) - 1));
    }
    public static String pickALetter(boolean shift) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        if (shift) letters = letters.toUpperCase();
        return String.valueOf(letters.charAt(generateRandomInt(1, 26) - 1));
    }
    public static String rollADie() {
        return String.valueOf("⚀⚁⚂⚃⚄⚅".charAt(generateRandomInt(1, 6) - 1));
    }
    public static String flipACoin() {
        return String.valueOf("ⒽⓉ".charAt(generateRandomInt(1, 2) - 1));
    }
    public static String castALot() {
        return String.valueOf("⚊⚋".charAt(generateRandomInt(1, 2) - 1));
    }
    public static String pickACard() {
        String cards = "🂡🂢🂣🂤🂥🂦🂧🂨🂩🂪🂫🂬🂭🂮🂱🂲🂳🂴🂵🂶🂷🂸🂹🂺🂻🂼🂽🂾🃁🃂🃃🃄🃅🃆🃇🃈🃉🃊🃋🃌🃍🃎🃑🃒🃓🃔🃕🃖🃗🃘🃙🃚🃛🃜🃝🃞"; //  🃟🃏🂠
        return Util.largeIntToChar(cards.codePointAt(generateRandomInt(1, cards.codePointCount(0, cards.length())) - 1));
    }
    public static String shake8Ball() {
        return "" + Constants.answers[generateRandomInt(1, 20) - 1];
    }

    // time-related stuff
    public static long nowAsLong() {
        return Instant.now().getEpochSecond();
    }
    public static int nowAsInt() {
        // new Date().getTime() / 1000;
        return (int) (System.currentTimeMillis() / 1000L);
    }
    public static Date stringToDate(String date, String format) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        return simpleDateFormat.parse(date);
    }
    public static String getDateString(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        return sdf.format(cal.getTime());
    }
    public static String getTimeString(String timeFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        return sdf.format(cal.getTime());
    }
    public static String timemoji() {
        String clocks = "🕐🕜🕑🕝🕒🕞🕓🕟🕔🕠🕕🕡🕖🕢🕗🕣🕘🕤🕙🕥🕚🕦🕛🕧";

        Calendar rightNow = Calendar.getInstance();
        rightNow.getTime();
        int hours = rightNow.get(Calendar.HOUR_OF_DAY);
        if (hours == 0) hours = 12;
        if (hours > 12) hours -= 12;
        int minutes = rightNow.get(Calendar.MINUTE);

        // 0 thru 29, 30 thru 59
        int which = (((hours - 1) * 2) + (minutes / 30)) * 2;

        return largeIntToChar(clocks.codePointAt(which));
    }

    // color
    public static Color invertColor(Color color) {
        color.red((int)(255-color.red()));
        color.blue((int)(255-color.blue()));
        color.green((int)(255-color.green()));
        return color;
    }
    public static int invertColor(int color) {
        return (0x00FFFFFF - (color | 0xFF000000)) | (color & 0xFF000000);
    }



    public static String toColor(int r, int g, int b) {
        String rs = StringUtils.leftPad(Integer.toHexString(r), 2, "0").toUpperCase();
        String gs = StringUtils.leftPad(Integer.toHexString(g), 2, "0").toUpperCase();
        String bs = StringUtils.leftPad(Integer.toHexString(b), 2, "0").toUpperCase();
        return "#" + rs + gs + bs;
    }
    public static String toColor(int a, int r, int g, int b) {
        String as = StringUtils.leftPad(Integer.toHexString(a), 2, "0").toUpperCase();
        String rs = StringUtils.leftPad(Integer.toHexString(r), 2, "0").toUpperCase();
        String gs = StringUtils.leftPad(Integer.toHexString(g), 2, "0").toUpperCase();
        String bs = StringUtils.leftPad(Integer.toHexString(b), 2, "0").toUpperCase();
        return "#" + as + rs + gs + bs;
    }
    public static Color stringToColor(String value) {
        if (value == null) {
            return Color.valueOf(Color.BLACK);
        }
        try {
            // get color by hex or octal value
            return Color.valueOf(Color.parseColor(value));
        }
        catch (NumberFormatException nfe) {
            // if we can't decode lets try to get it by name
            try {
                // try to get a color by name using reflection
                final Field f = Color.class.getField(value);
                return (Color)f.get(null);
            }
            catch (Exception ce) {
                // if we can't get any color return black
                return Color.valueOf(Color.BLACK);
            }
        }
    }
    public static String colorToString(Color color) {
        return String.format("#%08X", color.toArgb());
    }
    public static int[] fromColor(String color) {
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
        else {
            return null;
        }
        ai = Integer.decode("0x" + as);
        ri = Integer.decode("0x" + rs);
        gi = Integer.decode("0x" + gs);
        bi = Integer.decode("0x" + bs);
        return new int[]{ai, ri, gi, bi};
    }

    // encoding
    public static String escapeHtml(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            }
            else {
                out.append(c);
            }
        }
        return out.toString();
    }
    public static String unescapeHtml(String encodedHtml) {
        return StringEscapeUtils.unescapeHtml4(encodedHtml).trim();
    }
    public static String encodeUrl(String decodedString) {
        return Base64.getUrlEncoder().encodeToString(decodedString.getBytes());
    }
    public static String decodeUrl(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }
    public static String encodeBase64(String decodedString) {
        return Base64.getEncoder().encodeToString(decodedString.getBytes());
    }
    public static String decodeBase64(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString));
    }
    public static String encodeMime(String decodedString) {
        return Base64.getMimeEncoder().encodeToString(decodedString.getBytes());
    }
    public static String decodeMime(String encodedString) {
        return new String(Base64.getMimeDecoder().decode(encodedString));
    }

    // low level stuff
    public static boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }



    public static String unbolden(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(new String(Character.toChars(Font.getUnbold((ch)))));
            i += Character.charCount(ch);
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String bolden(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int ch : chars) {
            result.add(new String(Character.toChars(Font.getBold((int)ch))));
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }

    public static String unitalicize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(new String(Character.toChars(Font.getUnitalic((ch)))));
            i += Character.charCount(ch);
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String italicize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (char ch : chars) {
            result.add(new String(Character.toChars(Font.getItalic((int)ch))));
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }

    public static String unemphasize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(new String(Character.toChars(Font.getUnemphasized((ch)))));
            i += Character.charCount(ch);
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String emphasize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (char ch : chars) {
            result.add(new String(Character.toChars(Font.getEmphasized((int)ch))));
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
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        if (resolveInfo.size() != 1) {
            return null;
        }

        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        Intent explicitIntent = new Intent(implicitIntent);

        explicitIntent.setComponent(component);

        return explicitIntent;
    }
    public static String getClassName() {
        Class<?> enclosingClass = Util.class.getEnclosingClass();
        String className;
        if (enclosingClass != null) {
            className = enclosingClass.getName();
        }
        else {
            className = Util.class.getName();
        }
        try {
            className = className.split(".")[0];
        }
        catch (Exception ignored) {}
        return className;
    }
    public static int getLineNumber() {
        return ___8drrd3148796d_Xaf();
    }
    public static int ___8drrd3148796d_Xaf() {
        boolean thisOne = false;
        int thisOneCountDown = 1;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : elements) {
            String methodName = element.getMethodName();
            int lineNum = element.getLineNumber();
            if (thisOne && (thisOneCountDown == 0)) {
                return lineNum;
            }
            else if (thisOne) {
                thisOneCountDown--;
            }
            if (methodName.equals("___8drrd3148796d_Xaf")) {
                thisOne = true;
            }
        }
        return -1;
    }
    public static String methodName() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }
    public String getMethodName() {
        return new Throwable().getStackTrace()[1].getMethodName();
    }
    public String getMethodName(int depth) {
        return new Throwable().getStackTrace()[depth].getMethodName();
    }
    public static String sanitize(String text) {
        text = text.replaceAll("×", "*");
        text = text.replaceAll("÷", "/");
        text = text.replaceAll("π", "Math.PI");
        text = text.replaceAll("ℯ", "Math.E");
        // text = text.replaceAll("¹", "^1");
        // text = text.replaceAll("²", "^2");
        // text = text.replaceAll("³", "^3");
        // text = text.replaceAll("⁴", "^4");
        // text = text.replaceAll("⁵", "^5");
        // text = text.replaceAll("⁶", "^6");
        // text = text.replaceAll("⁷", "^7");
        // text = text.replaceAll("⁸", "^8");
        // text = text.replaceAll("⁹", "^9");
        // text = text.replaceAll("⁰", "^0");
        text = text.replaceAll("㏑", "ln");
        text = text.replaceAll("㏒", "log");
        text = text.replaceAll("½", "1/2");
        text = text.replaceAll("⁄", "/");

        return text;
    }
    public static String evalScript(String text) {
        // text = sanitize(text);

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("js");
        Object result = null;
        try {
            result = engine.eval(text);
        }
        catch (ScriptException e) {
            result = text;
        }
        text = result.toString();

        if (checkInteger(Double.parseDouble(text))) {
            int resultInt = (int)Double.parseDouble(text);
            return String.valueOf(resultInt);
        }
        return text;
    }

    public static double evalParser(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) return Double.NaN;
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            // @TODO: implement these
            // ** exponentiation
            // bitwise operators >> << >>>
            // Math.LN2: Natural logarithm of 2; approximately 0.693.
            // Math.LN10: Natural logarithm of 10; approximately 2.303.
            // Math.LOG2E: Base-2 logarithm of E; approximately 1.443.
            // Math.LOG10E: Base-10 logarithm of E; approximately 0.434.
            // √½ Math.SQRT1_2: Square root of ½ (or equivalently, 1/√2); approximately 0.707.
            // √2 Math.SQRT2: Square root of 2; approximately 1.414.
            // Math.random();
            // Math.imul(x, y): Returns the result of the 32-bit integer multiplication of x and y.
            // Math.max([x[, y[, …]]]): Returns the largest of zero or more numbers.
            // Math.min([x[, y[, …]]]): Returns the smallest of zero or more numbers.
            // Math.hypot([x[, y[, …]]]): Returns the square root of the sum of squares of its arguments.

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus
                if (eat('~')) return ~(long)parseFactor(); // unary round
                if (eat('≈')) return Math.round(parseFactor()); // unary round
                if (eat('!')) return factorial(parseFactor()); // unary factorial
                if (eat('√')) return Math.sqrt(parseFactor()); // unary square root
                if (eat('∛')) return Math.cbrt(parseFactor()); // unary cube root
                if (eat('∜')) return Math.pow(parseFactor(), .25); // unary tesseract root

                double x;
                int startPos = this.pos;
                if (eat('(')) { x = parseExpression(); eat(')'); }
                if (eat('[')) { x = parseExpression(); eat(']'); }
                if (eat('{')) { x = parseExpression(); eat('}'); }
                if (eat('|')) { x = Math.abs(parseExpression()); eat('|'); }
                if (eat('⌈')) { x = Math.ceil(parseExpression()); eat('⌉'); }
                if (eat('⌊')) { x = Math.ceil(parseExpression()); eat('⌋'); }

                // implement other bases
                else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                }
                else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sqrt":  x = Math.sqrt(x); break;
                        case "sin":   x = Math.sin(Math.toRadians(x)); break;
                        case "cos":   x = Math.cos(Math.toRadians(x)); break;
                        case "tan":   x = Math.tan(Math.toRadians(x)); break;
                        case "acos":  x = Math.acos(Math.toRadians(x)); break;
                        case "acosh": x = acosh(Math.toRadians(x)); break;
                        case "asin":  x = Math.asin(Math.toRadians(x)); break;
                        case "asinh": x = asinh(Math.toRadians(x)); break;
                        case "atan":  x = Math.atan(Math.toRadians(x)); break;
                        case "atanh": x = atanh(Math.toRadians(x)); break;
                        case "cosh":  x = Math.cosh(Math.toRadians(x)); break;
                        case "sinh":  x = Math.sinh(Math.toRadians(x)); break;
                        case "tanh":  x = Math.tanh(Math.toRadians(x)); break;
                        case "exp":   x = Math.exp(x); break;
                        case "expm1": x = Math.expm1(x); break;
                        case "log2":  x = log2(x); break;
                        case "log10": x = Math.log10(x); break;
                        case "log":   x = Math.log(x); break;
                        case "log1p": x = Math.log1p(x); break;
                        case "sign":  x = Math.signum(x); break;
                        case "round": x = Math.round(x); break;
                        case "trunc": x = truncate(x); break;
                        case "clz32": x = Integer.numberOfLeadingZeros((int)x); break;
                        case "ctz32": x = Integer.numberOfTrailingZeros((int)x); break;
                        default: return Double.NaN;
                    }
                }
                else {
                    return Double.NaN;
                }

                // x operator x

                if (eat('n') && eat('C') && eat('r')) x = nCr((long)x, (long)parseFactor());
                if (eat('n') && eat('P') && eat('r')) x = nPr((long)x, (long)parseFactor());
                if (eat('>') && eat('>') && eat('>')) x = ((long)x) >>> ((long)x);
                if (eat('<') && eat('<')) x = ((long)x) << ((long)x);
                if (eat('>') && eat('>')) x = ((long)x) >> ((long)x);
                if (eat('*') && eat('*')) Math.pow(x, parseFactor()); // exponentiation
                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                if (eat('%')) x = x % parseFactor(); // modulus
                if (eat('|')) x = ((long)x) | ((long)parseFactor()); // modulus
                if (eat('&')) x = ((long)x) & ((long)parseFactor()); // modulus

                return x;
            }
        }.parse();
    }
    public static double factorial(double number) {
        double result = 1;
        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }
        return result;
    }
    public static long nCr(long total, long choose) {
        if (total < choose) return 0;
        if (choose == 0 || choose == total) return 1;
        return nCr(total - 1, choose - 1) + nCr(total-1, choose);
    }
    public static double nPr(double n, double r) {
        return factorial(n) / factorial(n - r);
    }
    public static double asinh(double n) {
        return Math.log(n + Math.sqrt(n * n + 1.0));
    }
    public static double acosh(double n) {
        return Math.log(Math.sqrt(n * n - 1.0d) + n);
    }
    public static double atanh(double n) {
        return (StrictMath.log((1.0 + n) * StrictMath.sqrt(1.0 / (1.0 - n * n))));
    }
    public static double log2(double N) {
        double result = (Math.log(N) / Math.log(2));
        return result;
    }
    public static double truncate(double unroundedNumber) {
        return truncate(unroundedNumber, 0);
    }
    public static double truncate(double unroundedNumber, int decimalPlaces) {
        int truncatedNumberInt = (int)(unroundedNumber * Math.pow(10, decimalPlaces));
        double truncatedNumber = (double)(truncatedNumberInt / Math.pow(10, decimalPlaces));
        return truncatedNumber;
    }

    public static boolean checkInteger(double variable) {
        return (variable == Math.floor(variable)) && !Double.isInfinite(variable);
    }
    public static <T> T orNull(T value, T defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }
    public static double degToRad(double degrees) {
        return degrees * (Math.PI / 180);
    };
    public static double radToDeg(double radians) {
        return radians / (Math.PI / 180);
    };
    public static String evaluate(String text) {
        text = sanitize(text);

        double result = evalParser(text);
        if (checkInteger(result)) {
            int resultInt = (int)result;
            return String.valueOf(resultInt);
        }
        return String.valueOf(result);
    }

    public static int round(int n, int m) {
        int a = (n / m) * m; // Smaller multiple
        int b = a + m;       // Larger multiple
        return (n - a > b - n)? b : a; // Return of closest of two
    }
}
