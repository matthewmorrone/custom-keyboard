package com.custom.keyboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
// import android.widget.EditText;

public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    Context baseContext;
    SharedPreferences sharedPreferences;

    EditTextPreference seps;

    ListPreference theme;
    SeekPreference height;
    SeekPreference transparency;
    EditTextPreference indentWidth;
    EditTextPreference borderWidth;
    EditTextPreference paddingWidth;
    EditTextPreference borderRadius;
    EditTextPreference textSize;
    EditTextPreference emoticonFontSize;
    EditTextPreference unicodeFontSize;
    ColorPreference background;
    ColorPreference foreground;
    ColorPreference borderColor;

    Preference resetEmoticonHistory;
    Preference resetUnicodeHistory;
    EditTextPreference emoticonRecents;
    EditTextPreference unicodeRecents;
    EditTextPreference emoticonFavorites;
    EditTextPreference unicodeFavorites;
    EditTextPreference customKeys;
    EditTextPreference popupFirst;
    EditTextPreference popupSecond;
    EditTextPreference popupThird;
    EditTextPreference name;
    EditTextPreference email;
    EditTextPreference phone;
    EditTextPreference address;
    EditTextPreference password;
    EditTextPreference k1;
    EditTextPreference k2;
    EditTextPreference k3;
    EditTextPreference k4;
    EditTextPreference k5;
    EditTextPreference k6;
    EditTextPreference k7;
    EditTextPreference k8;

    String[] themes;

    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);

        baseContext = getActivity().getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        themes = getResources().getStringArray(R.array.theme_names);

        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);

        String[] themes = getResources().getStringArray(R.array.theme_names);

        seps = (EditTextPreference)findPreference("seps");
        height = (SeekPreference)findPreference("height");
        transparency = (SeekPreference)findPreference("transparency");
        theme = (ListPreference)findPreference("theme");

        indentWidth = (EditTextPreference)findPreference("indentWidth");
        borderWidth = (EditTextPreference)findPreference("borderWidth");
        paddingWidth = (EditTextPreference)findPreference("paddingWidth");
        borderRadius = (EditTextPreference)findPreference("borderRadius");

        textSize = (EditTextPreference)findPreference("textSize");
        emoticonFontSize = (EditTextPreference)findPreference("emoticonFontSize");
        unicodeFontSize = (EditTextPreference)findPreference("unicodeFontSize");
        background = (ColorPreference)findPreference("bgcolor");
        foreground = (ColorPreference)findPreference("fgcolor");
        borderColor = (ColorPreference)findPreference("bdcolor");

        emoticonRecents = (EditTextPreference)findPreference("emoticon_recents");
        unicodeRecents = (EditTextPreference)findPreference("unicode_recents");
        emoticonFavorites = (EditTextPreference)findPreference("emoticon_favorites");
        unicodeFavorites = (EditTextPreference)findPreference("unicode_favorites");
        customKeys = (EditTextPreference)findPreference("custom_keys");
        popupFirst = (EditTextPreference)findPreference("popup_first");
        popupSecond = (EditTextPreference)findPreference("popup_second");
        popupThird = (EditTextPreference)findPreference("popup_third");
        name = (EditTextPreference)findPreference("name");
        email = (EditTextPreference)findPreference("email");
        phone = (EditTextPreference)findPreference("phone");
        address = (EditTextPreference)findPreference("address");
        password = (EditTextPreference)findPreference("password");

        resetEmoticonHistory = findPreference("resetEmoticonHistory");
        if (resetEmoticonHistory != null) {
            resetEmoticonHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sharedPreferences.edit().putString("recent_emoticons", "").apply();
                    return true;
                }
            });
        }

        resetUnicodeHistory = findPreference("resetUnicodeHistory");
        if (resetUnicodeHistory != null) {
            resetUnicodeHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    sharedPreferences.edit().putString("recent_unicode", "").apply();
                    return true;
                }
            });
        }

        k1 = (EditTextPreference)findPreference("k1");
        k2 = (EditTextPreference)findPreference("k2");
        k3 = (EditTextPreference)findPreference("k3");
        k4 = (EditTextPreference)findPreference("k4");
        k5 = (EditTextPreference)findPreference("k5");
        k6 = (EditTextPreference)findPreference("k6");
        k7 = (EditTextPreference)findPreference("k7");
        k8 = (EditTextPreference)findPreference("k8");

        if (seps != null) seps.setSummary(sharedPreferences.getString("seps", ""));

        if (height != null) height.setValue(sharedPreferences.getInt("height", 100));
        if (transparency != null) transparency.setValue(sharedPreferences.getInt("transparency", 100));

        String themeString = sharedPreferences.getString("theme", "1");
        if (themeString != null) {
            if (theme != null) theme.setSummary(themes[Integer.parseInt(themeString)-1]);
        }

        if (indentWidth != null) indentWidth.setSummary(sharedPreferences.getString("indentWidth", ""));
        if (borderWidth != null) borderWidth.setSummary(sharedPreferences.getString("borderWidth", ""));
        if (paddingWidth != null) paddingWidth.setSummary(sharedPreferences.getString("paddingWidth", ""));
        if (borderRadius != null) borderRadius.setSummary(sharedPreferences.getString("borderRadius", ""));
        if (textSize != null) textSize.setSummary(sharedPreferences.getString("textSize", "56"));
        if (emoticonFontSize != null) emoticonFontSize.setSummary(sharedPreferences.getString("emoticonFontSize", "24"));
        if (unicodeFontSize != null) unicodeFontSize.setSummary(sharedPreferences.getString("unicodeFontSize", "24"));
        if (background != null) background.setColor(sharedPreferences.getInt("bgcolor", 0xFF000000));
        if (foreground != null) foreground.setColor(sharedPreferences.getInt("fgcolor", 0xFFFFFFFF));
        if (borderColor != null) borderColor.setColor(sharedPreferences.getInt("bdcolor", 0xFF000000));

        if (emoticonRecents != null) emoticonRecents.setSummary(sharedPreferences.getString("emoticon_recents", ""));
        if (unicodeRecents != null) unicodeRecents.setSummary(sharedPreferences.getString("unicode_recents", ""));
        if (emoticonFavorites != null) emoticonFavorites.setSummary(sharedPreferences.getString("emoticon_favorites", ""));
        if (unicodeFavorites != null) unicodeFavorites.setSummary(sharedPreferences.getString("unicode_favorites", ""));
        if (customKeys != null) customKeys.setSummary(sharedPreferences.getString("custom_keys", ""));

        if (popupFirst != null) popupFirst.setSummary(sharedPreferences.getString("popup_first", ""));
        if (popupSecond != null) popupSecond.setSummary(sharedPreferences.getString("popup_second", ""));
        if (popupThird != null) popupThird.setSummary(sharedPreferences.getString("popup_third", ""));
        if (name != null) name.setSummary(sharedPreferences.getString("title", ""));
        if (email != null) email.setSummary(sharedPreferences.getString("email", ""));
        if (phone != null) phone.setSummary(sharedPreferences.getString("phone", ""));
        if (address != null) address.setSummary(sharedPreferences.getString("address", ""));

        String pwd = sharedPreferences.getString("password", "");
        if (pwd != null) {
            String redaction = new String(new char[pwd.length()]).replace("\0", "*");
            if (password != null) password.setSummary(redaction);
        }

        if (k1 != null) k1.setSummary(sharedPreferences.getString("k1", ""));
        if (k2 != null) k2.setSummary(sharedPreferences.getString("k2", ""));
        if (k3 != null) k3.setSummary(sharedPreferences.getString("k3", ""));
        if (k4 != null) k4.setSummary(sharedPreferences.getString("k4", ""));
        if (k5 != null) k5.setSummary(sharedPreferences.getString("k5", ""));
        if (k6 != null) k6.setSummary(sharedPreferences.getString("k6", ""));
        if (k7 != null) k7.setSummary(sharedPreferences.getString("k7", ""));
        if (k8 != null) k8.setSummary(sharedPreferences.getString("k8", ""));

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        Intent intent = new Intent("updateKeyboard");
        if (getContext() != null) getContext().sendBroadcast(intent);

        if (seps != null) seps.setSummary(sharedPreferences.getString("seps", ""));

        if (height != null) height.setValue(sharedPreferences.getInt("height", 100));
        if (transparency != null) transparency.setValue(sharedPreferences.getInt("transparency", 100));

        String themeString = sharedPreferences.getString("theme", "1");
        if (themeString != null) {
            int themeInt = Integer.parseInt(themeString);
            if (theme != null) theme.setSummary(themes[themeInt-1]);
        }

        if (indentWidth != null) indentWidth.setSummary(sharedPreferences.getString("indentWidth", ""));
        if (borderWidth != null) borderWidth.setSummary(sharedPreferences.getString("borderWidth", ""));
        if (paddingWidth != null) paddingWidth.setSummary(sharedPreferences.getString("paddingWidth", ""));
        if (borderRadius != null) borderRadius.setSummary(sharedPreferences.getString("borderRadius", ""));

        if (textSize != null) textSize.setSummary(sharedPreferences.getString("textSize", "56"));
        if (emoticonFontSize != null) emoticonFontSize.setSummary(sharedPreferences.getString("emoticonFontSize", "24"));
        if (unicodeFontSize != null) unicodeFontSize.setSummary(sharedPreferences.getString("unicodeFontSize", "24"));
        if (background != null) background.setColor(sharedPreferences.getInt("bgcolor", 0xFF000000));
        if (foreground != null) foreground.setColor(sharedPreferences.getInt("fgcolor", 0xFFFFFFFF));
        if (borderColor != null) borderColor.setColor(sharedPreferences.getInt("bdcolor", 0xFF000000));

        if (emoticonRecents != null) emoticonRecents.setSummary(sharedPreferences.getString("emoticon_recents", ""));
        if (unicodeRecents != null) unicodeRecents.setSummary(sharedPreferences.getString("unicode_recents", ""));
        if (emoticonFavorites != null) emoticonFavorites.setSummary(sharedPreferences.getString("emoticon_favorites", ""));
        if (unicodeFavorites != null) unicodeFavorites.setSummary(sharedPreferences.getString("unicode_favorites", ""));
        if (customKeys != null) customKeys.setSummary(sharedPreferences.getString("custom_keys", ""));


        if (popupFirst != null) popupFirst.setSummary(sharedPreferences.getString("popup_first", ""));
        if (popupSecond != null) popupSecond.setSummary(sharedPreferences.getString("popup_second", ""));
        if (popupThird != null) popupThird.setSummary(sharedPreferences.getString("popup_third", ""));
        if (name != null) name.setSummary(sharedPreferences.getString("title", ""));
        if (email != null) email.setSummary(sharedPreferences.getString("email", ""));
        if (phone != null) phone.setSummary(sharedPreferences.getString("phone", ""));
        if (address != null) address.setSummary(sharedPreferences.getString("address", ""));

        String pwd = sharedPreferences.getString("password", "");
        if (pwd != null) {
            String redaction = new String(new char[pwd.length()]).replace("\0", "*");
            if (password != null) password.setSummary(redaction);
        }

        if (k1 != null) k1.setSummary(sharedPreferences.getString("k1", ""));
        if (k2 != null) k2.setSummary(sharedPreferences.getString("k2", ""));
        if (k3 != null) k3.setSummary(sharedPreferences.getString("k3", ""));
        if (k4 != null) k4.setSummary(sharedPreferences.getString("k4", ""));
        if (k5 != null) k5.setSummary(sharedPreferences.getString("k5", ""));
        if (k6 != null) k6.setSummary(sharedPreferences.getString("k6", ""));
        if (k7 != null) k7.setSummary(sharedPreferences.getString("k7", ""));
        if (k8 != null) k8.setSummary(sharedPreferences.getString("k8", ""));
    }
}
