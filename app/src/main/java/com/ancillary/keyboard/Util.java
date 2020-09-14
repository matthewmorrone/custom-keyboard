package com.ancillary.keyboard;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Util {

    public static boolean contains(String haystack, int primaryCode) {
        return haystack.contains(String.valueOf((char) primaryCode));
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

    public static boolean containsNumber(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // info
    public static int countChars(String text) {
        return text.codePointCount(0, text.length());
    }
    public static int countWords(String text) {
        return text.split("[\\u0009.,;:!?\\n()\\[\\]*&@{}/<>_+=|\"]").length;
    }
    public static int countLines(String text) {
        return text.split("\r\n|\r|\n").length;
    }
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

    public static String unidata(String text) {
        if (text.length() < 1) return "";

        if (Character.isHighSurrogate(text.charAt(0))
            ||  Character.isLowSurrogate(text.charAt(0))) {
            return unidata((int)text.codePointAt(0));
        }

        return unidata((int)text.charAt(0));
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
            return String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(number, 4, "0")));
        }
        catch (Exception e) {
            return number;
        }
    }

    public static String unidata(int primaryCode) {
        return toTitleCase(Character.getName(primaryCode))+"\n"+
            primaryCode+"\t0x"+padLeft(convertNumberBase(String.valueOf(primaryCode), 10, 16), 4).trim();
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

    public static String[] getWords(String text) {
        return text.split("[\\u0009.,;:!?\\n()\\[\\]*&@{}/<>_+=|\"]");
    }
    public static String[] getLines(String text) {
        return text.split("\r\n|\r|\n");
    }
    public static String[] getChars(String text) {
        return text.split("");
        // return text.split("(?!^)");
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


    public static String getIndentation(String line) {
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

    public static String increaseIndentation(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line.replaceAll("^", "    "));
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }

    public static String decreaseIndentation(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line.replaceAll("^ {4}", ""));
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String removeSpaces(String text) {
        return text.replaceAll(" ", "");
    }
    public static String trimEndingWhitespace(String text) {
        return text.replaceAll("[ \t]+\n", "\n")
            .replaceAll("[ \t]+$",  "");
    }
    public static String trimTrailingWhitespace(String text) {
        return text.replaceAll("([^ \t\r\n])[ \t]+\n", "\n")
            .replaceAll("([^ \t\r\n])[ \t]+$",  "$1");
    }

    // manipulations
    public static String removeDuplicates(String text) {
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


    public static String uniqueLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Set<String> unique = new LinkedHashSet<>(result);
        return StringUtils.join(unique.toArray(new String[0]), "\n");
    }
    public static String sortLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.sort(result);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String shuffleLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.shuffle(result);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }

    public static String reverseChars(String s) {
        return new StringBuilder(s).reverse().toString();
    }
    public static List<String> reverse(String[] a) {
        List<String> result = Arrays.asList(a);
        Collections.reverse(result);
        return result;
    }
    public static String reverseLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.reverse(result);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    public static String doubleChars(String text) {
        return text.replaceAll("(.)", "$1$1");
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

    public static String splitWithLinebreaks(String text){
        return text.replaceAll("(.)", "$1\n");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long nowAsLong() {
        return Instant.now().getEpochSecond();
    }
    public static int nowAsInt() {
        // new Date().getTime() / 1000;
        return (int) (System.currentTimeMillis() / 1000L);
    }
    public static String removeLinebreaks(String text) {
        return text.replaceAll("\n", "");
    }
    public static String splitWithSpaces(String text) {
        return text.replaceAll("(.)", "$1 ");
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

    public static String toUpperCase(String text) {
        return text.toUpperCase();
    }
    public static String toLowerCase(String text) {
        return text.toLowerCase();
    }

    public static int generateRandomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static String toTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        StringBuilder converted = new StringBuilder();
        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch) || String.valueOf(ch).equals("\n")) {
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


    public static String normalize(String text) {
        // String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        // return normalized.toLowerCase(Locale.ENGLISH);
        return Normalizer.normalize(text, Normalizer.Form.NFD);
        //.replaceAll("[^\\p{ASCII}]", "")
        //.toLowerCase();
    }
    public static String replaceNbsp(String text) {
        // String nbsp = "&nbsp;"
        // text.replaceAll("&nbsp;", " ");
        return text.replaceAll("\u00a0", " ");
    }


    public static String spaceReplace(String text) {
        return text.replaceAll(" +", " ");
    }

    public static String sortChars(String text) {
        String[] lines = getChars(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.sort(result);
        return StringUtils.join(result.toArray(new String[0]), "");
    }

    public static String shuffleChars(String text) {
        String[] lines = getChars(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.shuffle(result);
        return StringUtils.join(result.toArray(new String[0]), "");
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


    public static String timemoji() {
        String clocks = "🕐🕜🕑🕝🕒🕞🕓🕟🕔🕠🕕🕡🕖🕢🕗🕣🕘🕤🕙🕥🕚🕦🕛🕧";

        Calendar rightNow = Calendar.getInstance();
        rightNow.getTime();
        int hours = rightNow.get(Calendar.HOUR_OF_DAY);
        if (hours == 0) hours = 12;
        if (hours > 12) hours -= 12;
        int minutes = rightNow.get(Calendar.MINUTE);

        // 0 thru 29, 30 thru 59
        int which = (((hours - 1) * 2) + (minutes / 30));

        return String.valueOf((char) clocks.codePointAt(which));
    }


    public static boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
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

    // debug
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





}
