package com.custom.keyboard.unicode;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class UnicodeRecentsManager extends ArrayList<Unicode> {

    private static final String PREFERENCE_NAME = "unicode";
    private static final String PREF_RECENTS = "unicode_recents";
    private static final String PREF_RECENTS_PAGE = "unicode_recent_page";

    private static final Object LOCK = new Object();
    private static UnicodeRecentsManager sInstance;

    private Context mContext;

    private UnicodeRecentsManager(Context context) {
        mContext = context.getApplicationContext();
        loadRecents();
    }

    public static UnicodeRecentsManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new UnicodeRecentsManager(context);
                }
            }
        }
        return sInstance;
    }

    public int getRecentPage() {
        return getSharedPreferences().getInt(PREF_RECENTS_PAGE, 0);
    }

    public void setRecentPage(int page) {
        getSharedPreferences().edit().putInt(PREF_RECENTS_PAGE, page).apply();
    }

    public void push(Unicode object) {
        if (contains(object)) {
            super.remove(object);
        }
        add(0, object);
    }

    @Override
    public boolean add(Unicode object) {
        boolean result = super.add(object);
        return result;
    }

    @Override
    public void add(int index, Unicode object) {
        super.add(index, object);
    }

    @Override
    public boolean remove(Object object) {
        boolean result = super.remove(object);
        return result;
    }

    private SharedPreferences getSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences;
    }

    private void loadRecents() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String string = sharedPreferences.getString(PREF_RECENTS, "");
        StringTokenizer tokenizer = new StringTokenizer(string, "~");
        while (tokenizer.hasMoreTokens()) {
            try {
                add(new Unicode(tokenizer.nextToken()));
            }
            catch (NumberFormatException ignored) {

            }
        }
    }

    public void saveRecents() {
        StringBuilder sb = new StringBuilder();
        int c = size();
        for (int i = 0; i < c; i++) {
            Unicode e = get(i);
            sb.append(e.getUnicode());
            if (i < (c - 1)) {
                sb.append('~');
            }
        }
        SharedPreferences sharedPreferences = getSharedPreferences();
        sharedPreferences.edit().putString(PREF_RECENTS, sb.toString()).apply();
    }
}