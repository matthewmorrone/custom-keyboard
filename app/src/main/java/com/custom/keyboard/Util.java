package com.custom.keyboard;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

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
import java.util.Calendar; 
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

    String spaces = "    ";
    String tab = "	";

    static void noop() {}

    static String normalize(String input) {
        // String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // return normalized.toLowerCase(Locale.ENGLISH);
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                         .replaceAll("[^\\p{ASCII}]", "")
                         .toLowerCase();
    }

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

    static String unidata(String text) {
        if (text.length() < 1) return "";
        return unidata((int)text.charAt(0));
    }

    static String unidata(int primaryCode) {
        return ""+primaryCode+"\n" +
             ""+convertNumberBase(String.valueOf(primaryCode), 10, 16)+"\n" +
             ""+toTitleCase(Character.getName(primaryCode))+"\n" +
             ""+Character.getType(primaryCode)+"\n" +
             ""+toTitleCase(underscoresToSpaces(getCharacterType((byte)Character.getType(primaryCode))))+"\n" +
             ""+toTitleCase(underscoresToSpaces(Character.UnicodeBlock.of(primaryCode).toString()))+"\n" +
             /*
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
              */
              ""
             ;
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
        return (isLetter(primaryCode) || isDigit(primaryCode));
    }

    static Boolean isLetter(int primaryCode) {
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
    
    static String pickACard() {
        String letters = "🂡🂢🂣🂤🂥🂦🂧🂨🂩🂪🂫🂬🂭🂮🂱🂲🂳🂴🂵🂶🂷🂸🂹🂺🂻🂼🂽🂾🃁🃂🃃🃄🃅🃆🃇🃈🃉🃊🃋🃌🃍🃎🃑🃒🃓🃔🃕🃖🃗🃘🃙🃚🃛🃜🃝🃞"; //  🃟🃏🂠 
        return String.valueOf(letters.charAt(generateRandomInt(1, 52)-1));
    }
    
    static String timemoji() {
        String clocks = "🕐🕜🕑🕝🕒🕞🕓🕟🕔🕠🕕🕡🕖🕢🕗🕣🕘🕤🕙🕥🕚🕦🕛🕧";
    
    
        Calendar rightNow = Calendar.getInstance(); 
        rightNow.getTime(); 
        int hours = rightNow.get(Calendar.HOUR_OF_DAY); 
        if (hours == 0) hours = 12;
        if (hours > 12) hours -= 12;
        int minutes = rightNow.get(Calendar.MINUTE); 
        
        // 0 thru 29, 30 thru 59
        int which = (((hours-1)*2)+(minutes/30));
    
        return which+" "+clocks.codePointAt(which)+" "+clocks.codePointCount(0, clocks.length());
    }
    
    static String[] answers = new String[]{
        "It is certain. ",
        "It is decidedly so. ",
        "Without a doubt. ",
        "Yes; definitely. ",
        "You may rely on it. ",
        "As I see it, yes. ",
        "Most likely. ",
        "Outlook good. ",
        "Yes. ",
        "Signs point to yes. ",
        "Reply hazy, try again. ",
        "Ask again later. ",
        "Better not tell you now. ",
        "Cannot predict now. ",
        "Concentrate and ask again. ",
        "Don't count on it. ",
        "My reply is no. ",
        "My sources say no. ",
        "Outlook not so good. ",
        "Very doubtful. "
    };
    
    static String shake8Ball() {
        return ""+answers[generateRandomInt(1, 20)-1];
    }

    static String removeDuplicates(String text) {
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

    public String getClassName() {
        Class<?> enclosingClass = getClass().getEnclosingClass();
        String className;
        if (enclosingClass != null) { className = enclosingClass.getName(); }
        else { className = getClass().getName(); }
        try {
            className = className.split(".")[0];
        }
        catch (Exception ignored) {}
        return className;
    }
    public static int getLineNumber() {
        return ___8drrd3148796d_Xaf();
    }
    private static int ___8drrd3148796d_Xaf() {
        boolean thisOne = false;
        int thisOneCountDown = 1;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for(StackTraceElement element : elements) {
            String methodName = element.getMethodName();
            int lineNum = element.getLineNumber();
            if(thisOne && (thisOneCountDown == 0)) { return lineNum; }
            else if(thisOne) { thisOneCountDown--; }
            if(methodName.equals("___8drrd3148796d_Xaf")) { thisOne = true; }
        }
        return -1;
    }
    public String getMethodName() {
        return new Throwable().getStackTrace()[1].getMethodName();
    }
    public String getMethodName(int depth) {
        return new Throwable().getStackTrace()[depth].getMethodName();
    }
    public void whereami() {
        System.out.println(getClassName()+":"+getMethodName(2)+" "+___8drrd3148796d_Xaf());
    }


    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        if (resolveInfo == null || resolveInfo.size() != 1) {
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

    public static String getCharType(byte ch) {
        switch(ch) {
            case  8: return "Mc";
            case 23: return "Pc";
            case 15: return "Cc";
            case 26: return "Sc";
            case 20: return "Pd";
            case  9: return "Nd";
            case  7: return "Me";
            case 22: return "Pe";
            case 30: return "Pf";
            case 16: return "Cf";
            case 29: return "Pi";
            case 10: return "Nl";
            case 13: return "Zl";
            case  2: return "Ll";
            case 25: return "Sm";
            case  4: return "Lm";
            case 27: return "Sk";
            case  6: return "Mn";
            case  5: return "Lo";
            case 11: return "No";
            case 24: return "Po";
            case 28: return "So";
            case 14: return "Zp";
            case 18: return "Co";
            case 12: return "Zs";
            case 21: return "Ps";
            case 19: return "Cs";
            case  3: return "Lt";
            case  0: return "Cn";
            case  1: return "Lu";
            default: return "";
        }
    }


    public static String getCharacterType(byte ch) {
        switch(ch) {
            case  8: return "COMBINING_SPACING_MARK";
            case 23: return "CONNECTOR_PUNCTUATION";
            case 15: return "CONTROL";
            case 26: return "CURRENCY_SYMBOL";
            case 20: return "DASH_PUNCTUATION";
            case  9: return "DECIMAL_DIGIT_NUMBER";
            case  7: return "ENCLOSING_MARK";
            case 22: return "END_PUNCTUATION";
            case 30: return "FINAL_QUOTE_PUNCTUATION";
            case 16: return "FORMAT";
            case 29: return "INITIAL_QUOTE_PUNCTUATION";
            case 10: return "LETTER_NUMBER";
            case 13: return "LINE_SEPARATOR";
            case  2: return "LOWERCASE_LETTER";
            case 25: return "MATH_SYMBOL";
            case  4: return "MODIFIER_LETTER";
            case 27: return "MODIFIER_SYMBOL";
            case  6: return "NON_SPACING_MARK";
            case  5: return "OTHER_LETTER";
            case 11: return "OTHER_NUMBER";
            case 24: return "OTHER_PUNCTUATION";
            case 28: return "OTHER_SYMBOL";
            case 14: return "PARAGRAPH_SEPARATOR";
            case 18: return "PRIVATE_USE";
            case 12: return "SPACE_SEPARATOR";
            case 21: return "START_PUNCTUATION";
            case 19: return "SURROGATE";
            case  3: return "TITLECASE_LETTER";
            case  0: return "UNASSIGNED";
            case  1: return "UPPERCASE_LETTER";
            default: return "";
        }
    }



}
