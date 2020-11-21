package com.custom.keyboard.util;

import java.util.Random;

public class RandomUtils {

    public static int choose(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
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
        return StringUtils.largeIntToChar(cards.codePointAt(generateRandomInt(1, cards.codePointCount(0, cards.length())) - 1));
    }
    public static String shake8Ball() {
        return "" + Constants.answers[generateRandomInt(1, 20) - 1];
    }

}
