package com.custom.keyboard;

public class Generator {

    static String toKeyboard(String str) {

        String[]      rows     = str.split("_");

        StringBuilder keyboard = new StringBuilder(
              "<Keyboard xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
            + "android:keyWidth=\"10%p\"\n" + "android:keyHeight=\"7%p\"\n"
            + "android:layout_width=\"wrap_content\"\n"
            + "android:layout_height=\"wrap_content\">\n"
        );

        for(String row : rows) {
            String[] keys = row.split(" ");
            keyboard.append("<Row>\n");
            for(String key : keys) {
                String popup = "";
                if (key.length() > 1) {
                    popup = key.substring(1);
                    key = key.substring(0, 1);
                }
                if (!key.equals(" ") && !key.equals("")) {
                    keyboard.append("<Key android:codes=\"")
                            .append(key)
                            .append("\" android:keyLabel=\"")
                            .append(key)
                            .append("\" ");
                    if (!popup.equals("")) {
                        keyboard.append("android:popupCharacters=\"")
                                .append(popup)
                                .append("\" android:popupKeyboard=\"@layout/popup_template\"");
                    }


                    keyboard.append(" />\n");
                }
            }
            keyboard.append("</Row>\n");
        }

        keyboard.append("</Keyboard>\n");

        return keyboard.toString();
    }
}
