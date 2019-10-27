package com.custom.keyboard;

import java.util.Arrays;

public class Generator {

    static String toKeyboard(String str) {

        String[]      rows     = str.split(":");
//        System.out.println(Arrays.toString(rows));

        StringBuilder keyboard = new StringBuilder(
            "<Keyboard xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "android:keyWidth=\"10%p\"\n" + "android:keyHeight=\"7%p\"\n"
            + "android:layout_width=\"wrap_content\"\n"
            + "android:layout_height=\"wrap_content\">\n"
        );

        for(String row : rows) {
            System.out.println(row);
            String[] keys = row.split("(?!^)");
            keyboard.append("<Row>\n");
            for(String key : keys) {
                if (!key.equals(" ")) {
                    keyboard.append("<Key android:codes=\"")
                            .append(key)
                            .append("\" android:keyLabel=\"")
                            .append(key)
                            .append("\" />\n");
                }
            }
            keyboard.append("</Row>\n");
        }

        keyboard.append("</Keyboard>\n");

        return keyboard.toString();
    }
}
