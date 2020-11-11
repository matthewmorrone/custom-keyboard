package com.custom.keyboard;

import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.webkit.URLUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class Util {

    // general
    public static void noop() {}

    static String readFile(String path) {
        return readFile(path, StandardCharsets.US_ASCII);
    }

    static String readFile(String path, Charset encoding) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        }
        catch(IOException ignored) {}
        return "";
    }


    public static <T> boolean notNull(T value) {
        return (value != null);
    }
    public static <T> T orNull(T value, T defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }
    public static String[][] to2Darray(String source, String outerdelim, String innerdelim) {
        String[][] result = new String[source.replaceAll("[^" + outerdelim + "]", "").length() + 1][];
        int count = 0;
        for (String line : source.split ("[" + outerdelim + "]")) {
            result [count++] = line.split(innerdelim);
        }
        return result;
    }

    public static String serialize(List<String> dataList) {
        StringBuilder xmlBuilder = new StringBuilder("<root>");
        for (String data : dataList) {
            xmlBuilder.append("<data>").append(data).append("</data>");
        }
        return xmlBuilder.append("</root>").toString();
    }

    public static List<String> deserialize(String xml) {
        final List<String> dataList = new ArrayList<>();
        try {
            final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
            final XPathExpression xPathExpression = XPathFactory.newInstance().newXPath().compile("//data/text()");
            final NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); ++i) {
                dataList.add(nodeList.item(i).getNodeValue());
            }
        }
        catch (SAXException | ParserConfigurationException | IOException | XPathExpressionException ignored) {}
        return dataList;
    }

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
        // char[] chars = text.toCharArray();
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
        // char[] chars = text.toCharArray();
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
    public static boolean isPhoneNumber(String s) {
        return PhoneNumberUtils.isGlobalPhoneNumber(s);
    }
    public static String formatPhoneNumber(String unformattedNumber) {
        return PhoneNumberUtils.formatNumber(unformattedNumber);
    }
    public static boolean isValidPhoneNumber(String s) {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
/*
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
*/
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
        // StringBuilder result = new StringBuilder();
        // for(int i = 0; i < text.length(); i++) {
        //     result.append(unidata((int)text.codePointAt(i))).append("\n");
        // }
        // return result.toString();

        if (Character.isHighSurrogate(text.charAt(0))
        ||  Character.isLowSurrogate(text.charAt(0))) {
            return unidata(text.codePointAt(0));
        }
        return unidata(text.charAt(0));
    }
    public static String unidata(int primaryCode) {
        return toTitleCase(Util.orNull(Character.getName(primaryCode), ""))
            +"\t\t"+
            primaryCode
            +"\t\t0x"+
            padLeft(convertNumberBase(String.valueOf(primaryCode), 10, 16), 4).trim();
    }

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
        text = text.toLowerCase();
        array = text.toCharArray();

        for (int i = 0; i < array.length; i += 1) {
            // if (array[i] == ' ') i++;
            
            if (i % 2 == seed) array[i] = Character.toLowerCase(array[i]);
            if (i % 2 != seed) array[i] = Character.toUpperCase(array[i]);
        }

        text = new String(array);
        return text;
    }

    // char/word/line manipulation
    public static int countChars(String text) {
        return text.codePointCount(0, text.length());
    }
    public static int countWords(String text) {
        return text.split(" ").length;
    }
    public static int countLines(String text) {
        return text.split("\r\n|\r|\n").length;
    }
    public static String[] getChars(String text) {
        return text.split("");
        // return text.split("(?!^)");
    }
    public static String[] getWords(String text) {
        return text.split(" ");
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

    public static String unbolden(String text) {
        if (text.length() < 1) return text;
        // char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(new String(Character.toChars(FontVariants.getUnbold((ch)))));
            i += Character.charCount(ch);
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String bolden(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int ch : chars) {
            result.add(new String(Character.toChars(FontVariants.getBold((int)ch))));
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }

    public static String unitalicize(String text) {
        if (text.length() < 1) return text;
        // char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(new String(Character.toChars(FontVariants.getUnitalic((ch)))));
            i += Character.charCount(ch);
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String italicize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (char ch : chars) {
            result.add(new String(Character.toChars(FontVariants.getItalic((int)ch))));
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }

    public static String unemphasize(String text) {
        if (text.length() < 1) return text;
        // char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < text.length();) {
            int ch = text.codePointAt(i);
            result.add(new String(Character.toChars(FontVariants.getUnemphasized((ch)))));
            i += Character.charCount(ch);
        }
        return StringUtils.join(result.toArray(new String[0]), "");
    }
    public static String emphasize(String text) {
        if (text.length() < 1) return text;
        char[] chars = text.toCharArray();
        ArrayList<String> result = new ArrayList<>();
        for (char ch : chars) {
            result.add(new String(Character.toChars(FontVariants.getEmphasized((int)ch))));
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
            className = className.split("\\.")[0];
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
