package com.custom.keyboard;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Util {

    static void noop() {}

    

    static HashMap<Character,Integer> getCharacterFrequencies(String s) {
        HashMap<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Integer val = map.get(c);
            if (val != null) map.put(c, val + 1);
            else map.put(c, 1);
        }
        return map;
    }

    static Map<String, Integer> getWordFrequencies(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        for (String w : words) {
            Integer n = map.get(w);
            n = (n == null) ? 1 : ++n;
            map.put(w, n);
        }
        return map;
    }

    
    static Date stringToDate(String date, String format) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        return simpleDateFormat.parse(date);
    }

    static String methodName() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }

    static String encodeUrl(String decodedString) {
        return Base64.getUrlEncoder().encodeToString(decodedString.getBytes());
    }

    static String decodeUrl(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    static String encodeBase64(String decodedString) {
        return Base64.getEncoder().encodeToString(decodedString.getBytes());
    }

    static String decodeBase64(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString));
    }

    static String encodeMime(String decodedString) {
        return Base64.getMimeEncoder().encodeToString(decodedString.getBytes());
    }

    static String decodeMime(String encodedString) {
        return new String(Base64.getMimeDecoder().decode(encodedString));
    }
    
    static String spaceReplace(String text) {
        return text.replaceAll(" +", " ");
    }

    static String escapeHtml(String s) {
        // return Html.escapeHtml(html).trim();
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

    static String unescapeHtml(String encodedHtml) {
        return StringEscapeUtils.unescapeHtml4(encodedHtml).trim();
    }

    static String formatJson(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.toString(4);
    }

    static String rot13(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            sb.append(c);
        }
        return sb.toString();
    }

    static String getDateString(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        return sdf.format(cal.getTime());
    }

    static String getTimeString(String timeFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
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

    private static String[] getLines(String text) {
        return text.split("\r\n|\r|\n");
    }
    
    static String uniqueLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        
        Set<String> unique = new HashSet<>(result);

        
        return StringUtils.join(unique.toArray(new String[0]), "\n");
    }

    static String sortLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.sort(result);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    
    static String shuffleLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.shuffle(result);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    
    static String rotateLinesBackward(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.rotate(result, -1);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }
    
    static String rotateLinesForward(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.rotate(result, 1);
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }

    static String increaseIndentation(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line.replaceAll("^", "    "));
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }

    static String decreaseIndentation(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line.replaceAll("^ {4}", ""));
        }
        return StringUtils.join(result.toArray(new String[0]), "\n");
    }

    static int countChars(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).length();
    }

    static String[] getWords(String text) {
        return text.split("[\\u0009.,;:!?\\n()\\[\\]*&@{}/<>_+=|\"]");
    }

    static int countWords(String text) {
        return text.split("[\\u0009.,;:!?\\n()\\[\\]*&@{}/<>_+=|\"]").length;
    }

    static int countLines(String text) {
        return text.split("\r\n|\r|\n").length;
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
        return new Random().nextInt((max - min) + 1) + min;
    }

    static String padRight(String s, int n) {
        return String.format("%-0" + n + "s", s);
    }

    static String padLeft(String s, int n) {
        return String.format("%0" + n + "s", s);
    }

    static long nowAsLong() {
        return Instant.now().getEpochSecond();
    }

    static int nowAsInt() {
        // new Date().getTime() / 1000;
        return (int)(System.currentTimeMillis() / 1000L);
    }

    static String toggleJavaComment(String text) {
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
            return "/* "+text+" */";
        }
        else {
            return "/*\n"+text+"\n*/";
        }
    }

    static String toggleHtmlComment(String text) {
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
            return "<!-- "+text+" -->";
        }
        else {
            return "<!--\n"+text+"\n-->";
        }
    }

    static String toggleLineComment(String text) {
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

    static String doubleCharacters(String text) {
        return text.replaceAll("(.)", "$1$1");
    }

    static String camelToSnake(String text) {
        return text.replaceAll("([A-Z])", "_$1").toLowerCase();
    }

    static String snakeToCamel(String text) {
        StringBuilder nameBuilder = new StringBuilder(text.length());
        boolean capitalizeNextChar = false;
        for (char c:text.toCharArray()) {
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

    static String underscoresToSpaces(String text) {return text.replaceAll("_", " ");}
    static String spacesToUnderscores(String text) {return text.replaceAll(" ", "_");}

    static String dashesToSpaces(String text)      {return text.replaceAll("-", " ");}
    static String spacesToDashes(String text)      {return text.replaceAll(" ", "-");}

    static String spacesToLinebreaks(String text)  {return text.replaceAll(" ", "\n");}
    static String linebreaksToSpaces(String text)  {return text.replaceAll("\n", " ");}

    static String spacesToTabs(String text)        {return text.replaceAll(" ", "\t");}
    static String tabsToSpaces(String text)        {return text.replaceAll("\t", " ");}

    static String splitWithLinebreaks(String text) {return text.replaceAll("(.)", "$1\n");}
    static String removeLinebreaks(String text)    {return text.replaceAll("\n", "");}

    static String splitWithSpaces(String text)     {return text.replaceAll("(.)", "$1 ");}
    static String removeSpaces(String text)        {return text.replaceAll(" ",  "");}

    static String trimEndingWhitespace(String text) {
        return text.replaceAll("[ \t]+\n", "\n")
                   .replaceAll("[ \t]+$",  "");
    }
    static String trimTrailingWhitespace(String text) {
        return text.replaceAll("([^ \t\r\n])[ \t]+\n", "\n")
                   .replaceAll("([^ \t\r\n])[ \t]+$",  "$1");
    }
    
    static boolean hasZWSP(String text) {
        return text.contains(" ");
    }
    static String replaceZWSP(String text, String ins) {
        return text.replaceAll(" ", " "+ins);
    }
    static String removeZWSP(String text) {
        return text.replaceAll(" ", "");
    }

    static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    static List<String> reverse(String[] a) {
        List<String> result = Arrays.asList(a);
        Collections.reverse(result);
        return result;
    }

    static String reverseLines(String text) {
        String[] lines = getLines(text);
        ArrayList<String> result = new ArrayList<>();
        Collections.addAll(result, lines);
        Collections.reverse(result);
        return StringUtils.join(result.toArray(new String[0]), "\n");
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
    static boolean isWordSeparator(String text) {
        return "\\u0009.,;:!?\\n()[]*&amp;@{}/&lt;&gt;_+=|&quot;".contains(text);
    }

    static boolean isWordSeparator(int primaryCode, String delimiters) {
        return delimiters.contains(String.valueOf((char)primaryCode));
    }
    static boolean isWordSeparator(String text, String delimiters) {
        return delimiters.contains(text);
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
            case "☰☱": return "䷸";
            case "☰☲": return "䷰";
            case "☰☳": return "䷐";
            case "☰☴": return "䷨";
            case "☰☵": return "䷘";
            case "☰☶": return "䷠";
            case "☰☷": return "䷈";
            case "☱☰": return "䷇";
            case "☱☱": return "䷿";
            case "☱☲": return "䷷";
            case "☱☳": return "䷗";
            case "☱☴": return "䷯";
            case "☱☵": return "䷟";
            case "☱☶": return "䷧";
            case "☱☷": return "䷏";
            case "☲☰": return "䷆";
            case "☲☱": return "䷾";
            case "☲☲": return "䷶";
            case "☲☳": return "䷖";
            case "☲☴": return "䷮";
            case "☲☵": return "䷞";
            case "☲☶": return "䷦";
            case "☲☷": return "䷎";
            case "☳☰": return "䷂";
            case "☳☱": return "䷺";
            case "☳☲": return "䷲";
            case "☳☳": return "䷒";
            case "☳☴": return "䷪";
            case "☳☵": return "䷚";
            case "☳☶": return "䷢";
            case "☳☷": return "䷊";
            case "☴☰": return "䷅";
            case "☴☱": return "䷽";
            case "☴☲": return "䷵";
            case "☴☳": return "䷕";
            case "☴☴": return "䷭";
            case "☴☵": return "䷝";
            case "☴☶": return "䷥";
            case "☴☷": return "䷍";
            case "☵☰": return "䷃";
            case "☵☱": return "䷻";
            case "☵☲": return "䷳";
            case "☵☳": return "䷓";
            case "☵☴": return "䷫";
            case "☵☵": return "䷛";
            case "☵☶": return "䷣";
            case "☵☷": return "䷋";
            case "☶☰": return "䷄";
            case "☶☱": return "䷼";
            case "☶☲": return "䷴";
            case "☶☳": return "䷔";
            case "☶☴": return "䷬";
            case "☶☵": return "䷜";
            case "☶☶": return "䷤";
            case "☶☷": return "䷌";
            case "☷☰": return "䷁";
            case "☷☱": return "䷹";
            case "☷☲": return "䷱";
            case "☷☳": return "䷑";
            case "☷☴": return "䷩";
            case "☷☵": return "䷙";
            case "☷☶": return "䷡";
            case "☷☷": return "䷉";
            default:    return "";
        }
    }

    static String morseToChar(String buffer) {
        switch (buffer) {
            case "-": return "t";
            case "·": return "e";
            case "--": return "m";
            case "-·": return "n";
            case "·-": return "a";
            case "··": return "i";
            case "---": return "o";
            case "--·": return "g";
            case "-·-": return "k";
            case "-··": return "d";
            case "·--": return "w";
            case "·-·": return "r";
            case "··-": return "u";
            case "···": return "s";
            case "--·-": return "q";
            case "--··": return "z";
            case "-·--": return "y";
            case "-·-·": return "c";
            case "-··-": return "x";
            case "-···": return "b";
            case "·---": return "j";
            case "·--·": return "p";
            case "·-··": return "l";
            case "··-·": return "f";
            case "···-": return "v";
            case "····": return "h";
            case "-----": return "0";
            case "----·": return "9";
            case "---··": return "8";
            case "--···": return "7";
            case "-····": return "6";
            case "·----": return "1";
            case "··---": return "2";
            case "···--": return "3";
            case "····-": return "4";
            case "·····": return "5";
            // case "----": return "ch";
            // case "----": return "ĥ";
            case "----": return "š";
            case "---·": return "ø";
            // case "---·": return "ó";
            // case "---·": return "ö";
            // case "·-·-": return "ä";
            // case "·-·-": return "ą";
            case "·-·-": return "æ";
            case "··--": return "ü";
                // case "··--": return "ŭ";
            case "--·--": return "ñ";
            // case "--·--": return "ń";
            case "--·-·": return "ĝ";
            case "--··-": return "ż";
            case "-·--·": return "(";
            case "-·-··": return "ç";
            // case "-·-··": return "ć";
            // case "-·-··": return "ĉ";
            case "-··-·": return "/";
            case "-···-": return "=";
            case "·---·": return "ĵ";
            case "·--·-": return "à";
            // case "·--·-": return "å";
            case "·--··": return "þ";
            case "·-·-·": return "+";
            // case "·-··-": return "è";
            case "·-··-": return "ł";
            case "·-···": return "&";
            case "··--·": return "ð";
            // case "··-··": return "đ";
            case "··-··": return "é";
            // case "··-··": return "ę";
            case "···-·": return "ŝ";
            case "---···": return ":";
            case "--··--": return ",";
            case "--··-·": return "ź";
            case "-·--·-": return ")";
            case "-·-·--": return "!";
            case "-·-·-·": return ";";
            case "-····-": return "-";
            case "·----·": return "'";
            case "·--·-·": return "@";
            case "·-·-·-": return ".";
            case "·-··-·": return "\"";
            case "··--·-": return "_";
            case "··--··": return "?";
            case "···-··-": return "$";
            case "···-···": return "ś";
            default: return "";
        }
    }


    static String charToMorse(String buffer) {
        switch (buffer) {
            case " ": return " ";
            case "e": return "·";
            case "t": return "-";
            case "a": return "·-";
            case "i": return "··";
            case "m": return "--";
            case "n": return "-·";
            case "d": return "-··";
            case "g": return "--·";
            case "k": return "-·-";
            case "o": return "---";
            case "r": return "·-·";
            case "s": return "···";
            case "u": return "··-";
            case "w": return "·--";
            case "b": return "-···";
            case "c": return "-·-·";
            case "f": return "··-·";
            case "h": return "····";
            case "j": return "·---";
            case "l": return "·-··";
            case "p": return "·--·";
            case "q": return "--·-";
            case "v": return "···-";
            case "x": return "-··-";
            case "y": return "-·--";
            case "z": return "--··";
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
            case "ĥ": return "----";
            case "ó": return "---·";
            case "ö": return "---·";
            case "ø": return "---·";
            case "š": return "----";
            case "ŭ": return "··--";
            case "ü": return "··--";
            case "(": return "-·--·";
            case "/": return "-··-·";
            case "&": return "·-···";
            case "+": return "·-·-·";
            case "=": return "-···-";
            case "à": return "·--·-";
            case "å": return "·--·-";
            case "ć": return "-·-··";
            case "ĉ": return "-·-··";
            case "ç": return "-·-··";
            case "ð": return "··--·";
            case "đ": return "··-··";
            case "è": return "·-··-";
            case "é": return "··-··";
            case "ę": return "··-··";
            case "ĝ": return "--·-·";
            case "ĵ": return "·---·";
            case "ł": return "·-··-";
            case "ń": return "--·--";
            case "ñ": return "--·--";
            case "ŝ": return "···-·";
            case "ż": return "--··-";
            case "þ": return "·--··";
            case "-": return "-····-";
            case ",": return "--··--";
            case ";": return "-·-·-·";
            case ":": return "---···";
            case "!": return "-·-·--";
            case "?": return "··--··";
            case ".": return "·-·-·-";
            case "'": return "·----·";
            case ")": return "-·--·-";
            case "@": return "·--·-·";
            case "\"": return "·-··-·";
            case "ź": return "--··-·";
            case "$": return "···-··-";
            case "ś": return "···-···";
            default:  return "";
        }
    }

    static String pickALetter() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        return String.valueOf(letters.charAt(generateRandomInt(1, 26)-1));
    }

    static String pickALetter(boolean shift) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        if (shift) letters = letters.toUpperCase();
        return String.valueOf(letters.charAt(generateRandomInt(1, 26)-1));
    }

    static String rollADie() {
        return String.valueOf("⚀⚁⚂⚃⚄⚅".charAt(generateRandomInt(1, 6)-1));
    }

    static String flipACoin() {
        return String.valueOf("ⒽⓉ".charAt(generateRandomInt(1, 2)-1));
    }

    static String castALot() {
        return String.valueOf("⚊⚋".charAt(generateRandomInt(1, 2)-1));
    }
    
    /*
    static String removeDuplicates(String input){
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            if(!result.contains(String.valueOf(input.charAt(i)))) {
                result += String.valueOf(input.charAt(i));
            }
        }
        return result;
    }
    */


    static String removeDuplicates(String text) { 
        char str[] = text.toCharArray();
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

}
