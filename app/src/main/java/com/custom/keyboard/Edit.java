package com.custom.keyboard;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Edit {

    static Map<String,String> typos = new HashMap<>();

    public Edit(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.typos);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        try {
            String[] pair;
            while ((string = bufferedReader.readLine()) != null) {
                pair = string.split(",");
                if (pair.length > 1) {
                    typos.put(pair[0], pair[1]);
                }
                else System.out.println(string);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(String src, String trg) {
        src = src.trim();
        trg = trg.trim();
        typos.put(src, trg);
    }

    public String check(String word) {
        word = word.trim();
        String repl = typos.get(word);
        if (typos.get(word) != null) {
            return repl;
        }
        return word;
    }

    private static void changeKey(String oldKey, String newKey) {
        String value = typos.remove(oldKey);
        typos.put(newKey, value);
    }
}
