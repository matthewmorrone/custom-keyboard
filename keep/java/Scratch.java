private static int fibonacci(int n) {
    if (n <= 1) return n;
    else return fibonacci(n-1) + fibonacci(n-2);
}

static int factorial(int number) {
    int result = 1;
    for (int factor = 2; factor <= number; factor++) {
        result *= factor;
    }
    return result;
}

static Integer[] performLottery(int numNumbers, int numbersToPick) {
    List<Integer> numbers = new ArrayList<>();
    for(int i = 0; i < numNumbers; i++) numbers.add(i + 1);
    Collections.shuffle(numbers);
    return numbers.subList(0, numbersToPick).toArray(new Integer[numbersToPick]);
}


static boolean isPrime(int number) {
    if (number < 3) return true;
    if (number % 2 == 0) return false;
    for (int i = 3; i * i <= number; i += 2) {
        if (number % i == 0) {
            return false;
        }
    }
    return true;
}

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


String bg = sharedPreferences.getString("bg", "#FF000000");
int[] bgc = Util.fromColor(bg);
float[] sCustomColorArray = {
1.0f,      0,      0,       0,      bgc[1], // red
0,      1.0f,      0,       0,      bgc[2], // green
0,         0,   1.0f,       0,      bgc[3], // blue
0,         0,      0,    1.0f,      bgc[0]  // alpha
};
mDefaultFilter = sCustomColorArray;