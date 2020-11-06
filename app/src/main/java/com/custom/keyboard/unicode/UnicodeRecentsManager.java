package com.custom.keyboard.unicode;

import android.content.Context;
import android.content.SharedPreferences;

// import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class UnicodeRecentsManager extends ArrayList<Unicode> {

    private static final String PREFERENCE_NAME = "unicode";
    private static final String PREF_RECENTS = "recent_unicode";
    private static final String PREF_RECENTS_PAGE = "recent_page";

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
        return getPreferences().getInt(PREF_RECENTS_PAGE, 0);
    }

    public void setRecentPage(int page) {
        getPreferences().edit().putInt(PREF_RECENTS_PAGE, page).apply();
    }

    public void push(Unicode object) {
        if (contains(object)) {
            super.remove(object);
        }
        add(0, object);
    }

    @Override
    public boolean add(Unicode object) {
        boolean ret = super.add(object);
        return ret;
    }

    @Override
    public void add(int index, Unicode object) {
        super.add(index, object);
    }

    @Override
    public boolean remove(Object object) {
        boolean ret = super.remove(object);
        return ret;
    }

    private SharedPreferences getPreferences() {
        return mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    private void loadRecents() {
        SharedPreferences prefs = getPreferences();
        String str = prefs.getString(PREF_RECENTS, "");
        StringTokenizer tokenizer = new StringTokenizer(str, "~");
        while (tokenizer.hasMoreTokens()) {
            try {
                add(new Unicode(tokenizer.nextToken()));
            }
            catch (NumberFormatException e) {
                // ignored
            }
        }
    }

    public void saveRecents() {
        StringBuilder str = new StringBuilder();
        int c = size();
        for (int i = 0; i < c; i++) {
            Unicode e = get(i);
            str.append(e.getUnicode());
            if (i < (c - 1)) {
                str.append('~');
            }
        }
        SharedPreferences prefs = getPreferences();
        prefs.edit().putString(PREF_RECENTS, str.toString()).apply();
    }

}
