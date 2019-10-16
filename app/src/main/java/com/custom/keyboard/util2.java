import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.Instant;
// import org.apache.commons.codec.binary.Base64;
// import org.apache.commons.lang.StringEscapeUtils.escapeHtml;
import java.util.*;
import java.io.*;
  
// JSONObject json = new JSONObject(); 
// json.put("city","Mumbai"); 
// json.put("country", "India");
// String output = json.toString();

// JSONObject jsonObject = new JSONObject(jsonString);
// jsonObject.toString(4));

// JsonParser parser = new JsonParser();
// JsonObject json = parser.parse(jsonString).getAsJsonObject();
// Gson gson = new GsonBuilder().setPrettyPrinting().create();
// String prettyJson = gson.toJson(json);

class Util2 {


static String methodName() {
    return Thread.currentThread().getStackTrace()[1].getMethodName();
}


/*
static byte[] encodeBase64(String str) {
    // String BasicBase64format = Base64.getEncoder().encodeToString("actualString".getBytes());
    byte[] encodedBytes = Base64.encodeBase64(str.getBytes());
    return encodedBytes;
}
static String decodeBase64(byte[] encodedBytes) {
    // byte[] actualByte = Base64.getDecoder().decode(encodedString);
    byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
    return new String(decodedBytes);
}
*/
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

// Palindrome check
static boolean isPalindrome(String s) {
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
        if (Character.isLetter(c)) {
            sb.append(c);
        }
    }
    String forward = sb.toString().toLowerCase();
    String backward = sb.reverse().toString().toLowerCase();
    return forward.equals(backward);
}


// Reverse string
static String reverseString(String s) {
    return new StringBuilder(s).reverse().toString();
}


// String to date
static Date stringToDate(String date, String format) throws Exception {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    return simpleDateFormat.parse(date);
}

// Fibonacci
static int fibonacci(int n) {
    if (n <= 1) return n;
    else return fibonacci(n-1) + fibonacci(n-2);
}


// Factorial
static int factorial(int number) {
    int result = 1;
    for (int factor = 2; factor <= number; factor++) {
        result *= factor;
    }
    return result;
}


// Lottery
static Integer[] performLottery(int numNumbers, int numbersToPick) {
    List<Integer> numbers = new ArrayList<>();
    for(int i = 0; i < numNumbers; i++) {
        numbers.add(i+1);
    }
    Collections.shuffle(numbers);
    return numbers.subList(0, numbersToPick).toArray(new Integer[numbersToPick]);
}


// Prime
static boolean isPrime(int number) {
    if (number < 3) {
        return true;
    }
    // check if n is a multiple of 2
    if (number % 2 == 0) {
        return false;
    }
    // if not, then just check the odds
    for (int i = 3; i * i <= number; i += 2) {
        if (number % i == 0) {
            return false;
        }
    }
    return true;
}


static HashMap<Character,Integer> getFrequencies(String s) {
    HashMap<Character,Integer> map = new HashMap<Character,Integer>();
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        Integer val = map.get(c);
        if (val != null) {
            map.put(c, new Integer(val + 1));
        }
        else {
            map.put(c, 1);
        }
    }
    return map;
}

/*
Multiset<Character> chars = HashMultiset.create();
for (int i = 0; i < string.length(); i++) { chars.add(string.charAt(i)); }

Map<Character,Integer> frequencies = new HashMap<>();
for (char ch : input.toCharArray()) { frequencies.put(ch,frequencies.getOrDefault(ch, 0) + 1); }
*/

/*
// List directories
static File[] listDirectories(String path) {
    return new File(path).listFiles(File::isDirectory);
}


// List files in directory
static File[] listFilesInDirectory(final File folder) {
    return folder.listFiles(File::isFile);
}


// List files in directory recursively
static List<File> listAllFiles(String path) {
    List<File> all = new ArrayList<>();
    File[] list = new File(path).listFiles();
    if (list != null) {  // In case of access error, list is null
        for (File f : list) {
            if (f.isDirectory()) {
                all.addAll(listAllFiles(f.getAbsolutePath()));
            } else {
                all.add(f.getAbsoluteFile());
            }
        }
    }
    return all;
}

// Read lines from file to string list
static List<String> readLines(String filename) throws IOException {
    return Files.readAllLines(new File(filename).toPath());
}
*/

static String encodeUrl(String url) throws Exception {
    return URLEncoder.encode(url, "UTF-8");
}
/*
static String decodeUrl(String url) throws Exception {
    return URLEncoder.decode(url, "UTF-8");
}
*/

static long nowAsLong() {
    return Instant.now().getEpochSecond();
}
static int nowAsInt() {
    // new Date().getTime() / 1000;
    return (int)(System.currentTimeMillis() / 1000L);
}

static List<String> reverse1(String[] a) { 
    List<String> result = Arrays.asList(a);
    Collections.reverse(result); 
    return result;
}


static Map<String, Integer> wordFreq1(String[] words) {
    Map<String, Integer> map = new HashMap<>();
    for (String w : words) {
        Integer n = map.get(w);
        n = (n == null) ? 1 : ++n;
        map.put(w, n);
    }
    return map;
}


// String escaped = escapeHtml(str);

// Html.escapeHtml(str);
// Html.unescapeHtml(str);
// TextUtils.htmlEncode(str);
// TextUtils.htmlDecode(str);

static String escapeHTML(String s) {
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

}
