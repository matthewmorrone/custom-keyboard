package com.custom.keyboard.util;

import java.util.Stack;

// import javax.script.ScriptEngine;
// import javax.script.ScriptEngineManager;
// import javax.script.ScriptException;

public class Calculator {
    public static String sanitize(String text) {
        text = text.replaceAll("–", "-");
        text = text.replaceAll("×", "*");
        text = text.replaceAll("÷", "/");
        text = text.replaceAll("π", "Math.PI");
        text = text.replaceAll("ℯ", "Math.E");
        // text = text.replaceAll("²", "^2");
        text = text.replaceAll("㏑", "ln");
        text = text.replaceAll("㏒", "log");
        text = text.replaceAll("½", "1/2");
        text = text.replaceAll("⁄", "/");
        text = text.replaceAll("∏", "prod"); // Π
        text = text.replaceAll("Ʃ", "sum"); // ∑
        text = text.replaceAll("ʃ", "int"); // ∫
        text = text.replaceAll("ɸ", "[phi]");
        text = text.replaceAll("δ", "der"); // ∂
        text = text.replaceAll("√", "sqrt"); // ⎷
        text = text.replaceAll("∞", "Math.INFINITY");
        text = text.replaceAll("𝚜", "sin"); // ⏦
        text = text.replaceAll("𝚌", "cos");
        text = text.replaceAll("𝚝", "tan");
        text = text.replaceAll("⨁", "(+)");
        text = text.replaceAll("→", "-->");
        text = text.replaceAll("←", "<--");
        text = text.replaceAll("∧", "/\\");
        text = text.replaceAll("∨", "\\/"); 
        text = text.replaceAll("¬", "~"); 
        text = text.replaceAll("↛", "-/>"); 
        text = text.replaceAll("↚", "</-"); 
        text = text.replaceAll("≤", "<="); 
        text = text.replaceAll("≥", ">="); 
        text = text.replaceAll("≠", "~="); 
        text = text.replaceAll("㎭", "rad"); 
        text = text.replaceAll("%", "mod"); 
        text = text.replaceAll("⅓", "1/3"); 
        text = text.replaceAll("⅔", "2/3"); 
        text = text.replaceAll("¼", "1/4"); 
        text = text.replaceAll("¾", "3/4"); 
        text = text.replaceAll("⅕", "1/5"); 
        text = text.replaceAll("⅖", "2/5"); 
        text = text.replaceAll("⅗", "3/5"); 
        text = text.replaceAll("⅘", "4/5"); 
        text = text.replaceAll("⅙", "1/6"); 
        text = text.replaceAll("⅚", "5/6"); 
        text = text.replaceAll("⅐", "1/7"); 
        text = text.replaceAll("⅛", "1/8"); 
        text = text.replaceAll("⅜", "3/8"); 
        text = text.replaceAll("⅝", "5/8"); 
        text = text.replaceAll("⅞", "7/8"); 
        text = text.replaceAll("⅑", "1/9"); 
        text = text.replaceAll("⅒", "1/10"); 
        text = text.replaceAll("㎜", "[mm]"); 
        text = text.replaceAll("㎝", "[cm]"); 
        text = text.replaceAll("㎞", "[km]"); 
        text = text.replaceAll("㎟", "[mm2]"); 
        text = text.replaceAll("㎠", "[cm2]"); 
        text = text.replaceAll("㎡", "[m2]"); 
        text = text.replaceAll("㎢", "[km2]"); 
        text = text.replaceAll("㎣", "[mm3]"); 
        text = text.replaceAll("㎤", "[cm3]"); 
        text = text.replaceAll("㎥", "[m3]"); 
        text = text.replaceAll("㎦", "[km3]"); 
        text = text.replaceAll("㎎", "[mg]"); 
        text = text.replaceAll("㎏", "[kg]"); 
        text = text.replaceAll("㎅", "[kB]"); 
        text = text.replaceAll("㎆", "[MB]"); 
        text = text.replaceAll("㎇", "[GB]"); 
        text = text.replaceAll("㎧", "[m/s]"); 
        text = text.replaceAll("㎨", "[m/s2]"); 
        text = text.replaceAll("°", "[deg]"); 
        text = text.replaceAll("㎭", "[rad]"); 
        text = text.replaceAll("∅", "null"); 
        text = text.replaceAll("⊤", "[true]"); 
        text = text.replaceAll("⊥", "[false]"); 
        // text = text.replaceAll("", ""); 
        
        return text;
    }
    public static String evalScript(String text) {
        // text = sanitize(text);

        /*
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
        */
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
    public static double degToRad(double degrees) {
        return degrees * (Math.PI / 180);
    };
    public static double radToDeg(double radians) {
        return radians / (Math.PI / 180);
    };
    public static int round(int n, int m) {
        int a = (n / m) * m; // Smaller multiple
        int b = a + m;       // Larger multiple
        return (n - a > b - n)? b : a; // Return of closest of two
    }

/*
    public static int pxFromDp(Context context, int dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density);
    }
    private static int dpFromPx(Context context, int px) {
        float dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, context.getResources().getDisplayMetrics());
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp / density);
    }
*/
    public static int evalRPN(String tokens) {
        // tokens = tokens.replace(" ", "");
        return evalRPN(tokens.split(" "));
    }
    public static int evalRPN(String[] tokens) {
        int returnValue = 0;
        String operators = "+-*/^%&|<>";
        Stack<String> stack = new Stack<String>();
        for (String t : tokens) {
            if (!operators.contains(t)) stack.push(t);
            else {
                int a = Integer.parseInt(stack.pop());
                int b = Integer.parseInt(stack.pop());
                int index = operators.indexOf(t);
                switch(index) {
                    case 0: stack.push(String.valueOf(a + b)); break;
                    case 1: stack.push(String.valueOf(b - a)); break;
                    case 2: stack.push(String.valueOf(a * b)); break;
                    case 3: stack.push(String.valueOf(b / a)); break;
                    case 4: stack.push(String.valueOf(Math.pow(a, b))); break;
                    case 5: stack.push(String.valueOf(a % b)); break;
                    case 6: stack.push(String.valueOf(a & b)); break;
                    case 7: stack.push(String.valueOf(a | b)); break;
                    case 8: stack.push(String.valueOf(a << b)); break;
                    case 9: stack.push(String.valueOf(a >> b)); break;
                }
            }
        }
        returnValue = Integer.parseInt(stack.pop());
        return returnValue;
    }
}