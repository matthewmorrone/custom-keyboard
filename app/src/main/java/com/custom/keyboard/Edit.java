package com.custom.keyboard;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Edit {

    static Map<String,String> typos = new HashMap<>();

    private void readFile(Context context, int id) {
        InputStream inputStream = context.getResources().openRawResource(id);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        try {
            String[] pair;
            while ((string = bufferedReader.readLine()) != null) {
                pair = string.split(",");
                if (pair.length > 1 && typos.get(pair[0]) == null) {
                    typos.put(pair[0], pair[1]);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Edit(Context context) {
        readFile(context, R.raw.typos);
        readFile(context, R.raw.typos_more);
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
