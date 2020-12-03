package com.custom.keyboard.emoticon;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EmoticonRecentsManager extends ArrayList<Emoticon> {

    private static final Object LOCK = new Object();
    private static EmoticonRecentsManager sInstance;

    private Context context;

    private EmoticonRecentsManager(Context context) {
        this.context = context;
        loadRecents();
    }

    public static EmoticonRecentsManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new EmoticonRecentsManager(context);
                }
            }
        }
        return sInstance;
    }

    public int getRecentPage() {
        return getSharedPreferences().getInt("emoticon_recent_page", 0);
    }

    public void setRecentPage(int page) {
        getSharedPreferences().edit().putInt("emoticon_recent_page", page).apply();
    }

    public void push(Emoticon object) {
        if (contains(object)) super.remove(object);
        add(0, object);
    }

    @Override
    public boolean add(Emoticon object) {
        return super.add(object);
    }

    @Override
    public void add(int index, Emoticon object) {
        super.add(index, object);
    }

    @Override
    public boolean remove(Object object) {
        return super.remove(object);
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private void loadRecents() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String string = sharedPreferences.getString("emoticon_recents", "");

        System.out.println("loadRecents: "+string);

        StringTokenizer tokenizer = new StringTokenizer(string, "~");
        while (tokenizer.hasMoreTokens()) {
            add(new Emoticon(tokenizer.nextToken()));
        }
    }

    public void saveRecents() {
        StringBuilder string = new StringBuilder();
        int c = size();
        for (int i = 0; i < c; i++) {
            Emoticon e = get(i);
            string.append(e.getEmoticon());
            if (i < (c - 1)) {
                string.append('~');
            }
        }

        System.out.println("saveRecents: "+string);

        SharedPreferences sharedPreferences = getSharedPreferences();
        sharedPreferences.edit().putString("emoticon_recents", string.toString()).apply();
    }
}
