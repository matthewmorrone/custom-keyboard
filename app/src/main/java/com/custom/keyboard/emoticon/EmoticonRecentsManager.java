package com.custom.keyboard.emoticon;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EmoticonRecentsManager extends ArrayList<Emoticon> {

    private static final String PREFERENCE_NAME = "emoticon";
    private static final String PREF_RECENTS = "recent_emoticons";
    private static final String PREF_PAGE = "recent_page";

    private static final Object LOCK = new Object();
    private static EmoticonRecentsManager sInstance;

    private Context mContext;

    private EmoticonRecentsManager(Context context) {
        mContext = context.getApplicationContext();
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
        return getSharedPreferences().getInt(PREF_PAGE, 0);
    }

    public void setRecentPage(int page) {
        getSharedPreferences().edit().putInt(PREF_PAGE, page).apply();
    }

    public void push(Emoticon object) {
        // FIXME totally inefficient way of adding the emoticon to the adapter
        // TODO this should be probably replaced by a deque
        if (contains(object)) {
            super.remove(object);
        }
        add(0, object);
    }

    @Override
    public boolean add(Emoticon object) {
        boolean ret = super.add(object);
        return ret;
    }

    @Override
    public void add(int index, Emoticon object) {
        super.add(index, object);
    }

    @Override
    public boolean remove(Object object) {
        boolean ret = super.remove(object);
        return ret;
    }

    private SharedPreferences getSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences;
    }

    private void loadRecents() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String str = sharedPreferences.getString(PREF_RECENTS, "");
        StringTokenizer tokenizer = new StringTokenizer(str, "~");
        while (tokenizer.hasMoreTokens()) {
            try {
                add(new Emoticon(tokenizer.nextToken()));
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
            Emoticon e = get(i);
            str.append(e.getEmoticon());
            if (i < (c - 1)) {
                str.append('~');
            }
        }
        SharedPreferences prefs = getSharedPreferences();
        prefs.edit().putString(PREF_RECENTS, str.toString()).apply();
    }

}
