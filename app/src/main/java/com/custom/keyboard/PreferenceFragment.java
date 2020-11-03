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

    EditTextPreference popup_first;
    EditTextPreference popup_second;
    EditTextPreference popup_third;

    EditTextPreference name;
    EditTextPreference email;
    EditTextPreference phone;
    EditTextPreference address;
    EditTextPreference password;

    SeekPreference height;
    SeekPreference transparency;

    EditTextPreference indentWidth;
    EditTextPreference borderWidth;
    EditTextPreference paddingWidth;
    EditTextPreference borderRadius;

    ColorPreference background;
    ColorPreference foreground;
    ColorPreference borderColor;

    EditTextPreference textSize;
    EditTextPreference emoticonFontSize;
    EditTextPreference unicodeFontSize;

    Preference resetEmoticonHistory;
    Preference resetUnicodeHistory;

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

        try {
            addPreferencesFromResource(R.xml.preferences);
            PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        String[] themes = getResources().getStringArray(R.array.theme_names);

        try {
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

            popup_first = (EditTextPreference)findPreference("popup_first");
            popup_second = (EditTextPreference)findPreference("popup_second");
            popup_third = (EditTextPreference)findPreference("popup_third");
            name = (EditTextPreference)findPreference("name");
            email = (EditTextPreference)findPreference("email");
            phone = (EditTextPreference)findPreference("phone");
            address = (EditTextPreference)findPreference("address");
            password = (EditTextPreference)findPreference("password");

            resetEmoticonHistory = findPreference("resetEmoticonHistory");
            resetEmoticonHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    System.out.println(preference);
                    System.out.println(sharedPreferences.getString("recent_emoticons", ""));
                    sharedPreferences.edit().putString("recent_emoticons", "").apply();
                    return true;
                }
            });

            resetUnicodeHistory = findPreference("resetUnicodeHistory");
            resetUnicodeHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    System.out.println(preference);
                    System.out.println(sharedPreferences.getString("recent_unicode", ""));
                    sharedPreferences.edit().putString("recent_unicode", "").apply();
                    return true;
                }
            });

            k1 = (EditTextPreference)findPreference("k1");
            k2 = (EditTextPreference)findPreference("k2");
            k3 = (EditTextPreference)findPreference("k3");
            k4 = (EditTextPreference)findPreference("k4");
            k5 = (EditTextPreference)findPreference("k5");
            k6 = (EditTextPreference)findPreference("k6");
            k7 = (EditTextPreference)findPreference("k7");
            k8 = (EditTextPreference)findPreference("k8");
        }
        catch (Exception e) {
            System.out.println(e);
        }
        try {
            seps.setSummary(sharedPreferences.getString("seps", ""));

            height.setValue(sharedPreferences.getInt("height", 100));
            transparency.setValue(sharedPreferences.getInt("transparency", 100));

            String themeString = sharedPreferences.getString("theme", "1");
            if (themeString != null) {
                int themeInt = Integer.parseInt(themeString);
                theme.setSummary(themes[themeInt-1]);
            }
            
            indentWidth.setSummary(sharedPreferences.getString("indentWidth", ""));
            borderWidth.setSummary(sharedPreferences.getString("borderWidth", ""));
            paddingWidth.setSummary(sharedPreferences.getString("paddingWidth", ""));
            borderRadius.setSummary(sharedPreferences.getString("borderRadius", ""));

            textSize.setSummary(sharedPreferences.getString("textSize", "56"));
            emoticonFontSize.setSummary(sharedPreferences.getString("emoticonFontSize", "24"));
            unicodeFontSize.setSummary(sharedPreferences.getString("unicodeFontSize", "24"));

            background.setColor(sharedPreferences.getInt("bgcolor", 0xFF000000));
            foreground.setColor(sharedPreferences.getInt("fgcolor", 0xFFFFFFFF));
            borderColor.setColor(sharedPreferences.getInt("bdcolor", 0xFF000000));

            popup_first.setSummary(sharedPreferences.getString("popup_first", ""));
            popup_second.setSummary(sharedPreferences.getString("popup_second", ""));
            popup_third.setSummary(sharedPreferences.getString("popup_third", ""));
            name.setSummary(sharedPreferences.getString("name", ""));
            email.setSummary(sharedPreferences.getString("email", ""));
            phone.setSummary(sharedPreferences.getString("phone", ""));
            address.setSummary(sharedPreferences.getString("address", ""));

            String pwd = sharedPreferences.getString("password", "");
            if (pwd != null) {
                int pwdLen = pwd.length();
                String redaction = new String(new char[pwdLen]).replace("\0", "*");
                password.setSummary(redaction);
            }

            k1.setSummary(sharedPreferences.getString("k1", ""));
            k2.setSummary(sharedPreferences.getString("k2", ""));
            k3.setSummary(sharedPreferences.getString("k3", ""));
            k4.setSummary(sharedPreferences.getString("k4", ""));
            k5.setSummary(sharedPreferences.getString("k5", ""));
            k6.setSummary(sharedPreferences.getString("k6", ""));
            k7.setSummary(sharedPreferences.getString("k7", ""));
            k8.setSummary(sharedPreferences.getString("k8", ""));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        try {
            Intent intent = new Intent("updateKeyboard");
            getContext().sendBroadcast(intent);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        try {
            seps.setSummary(sharedPreferences.getString("seps", ""));

            height.setValue(sharedPreferences.getInt("height", 100));
            transparency.setValue(sharedPreferences.getInt("transparency", 100));

            String themeString = sharedPreferences.getString("theme", "1");
            if (themeString != null) {
                int themeInt = Integer.parseInt(themeString);
                theme.setSummary(themes[themeInt-1]);
            }

            indentWidth.setSummary(sharedPreferences.getString("indentWidth", ""));
            borderWidth.setSummary(sharedPreferences.getString("borderWidth", ""));
            paddingWidth.setSummary(sharedPreferences.getString("paddingWidth", ""));
            borderRadius.setSummary(sharedPreferences.getString("borderRadius", ""));

            textSize.setSummary(sharedPreferences.getString("textSize", "56"));
            emoticonFontSize.setSummary(sharedPreferences.getString("emoticonFontSize", "24"));
            unicodeFontSize.setSummary(sharedPreferences.getString("unicodeFontSize", "24"));
            
            background.setColor(sharedPreferences.getInt("bgcolor", 0xFF000000));
            foreground.setColor(sharedPreferences.getInt("fgcolor", 0xFFFFFFFF));
            borderColor.setColor(sharedPreferences.getInt("bdcolor", 0xFF000000));

            popup_first.setSummary(sharedPreferences.getString("popup_first", ""));
            popup_second.setSummary(sharedPreferences.getString("popup_second", ""));
            popup_third.setSummary(sharedPreferences.getString("popup_third", ""));
            name.setSummary(sharedPreferences.getString("title", ""));
            email.setSummary(sharedPreferences.getString("email", ""));
            phone.setSummary(sharedPreferences.getString("phone", ""));
            address.setSummary(sharedPreferences.getString("address", ""));

            String pwd = sharedPreferences.getString("password", "");
            if (pwd != null) {
                int pwdLen = pwd.length();
                String redaction = new String(new char[pwdLen]).replace("\0", "*");
                password.setSummary(redaction);
            }

            k1.setSummary(sharedPreferences.getString("k1", ""));
            k2.setSummary(sharedPreferences.getString("k2", ""));
            k3.setSummary(sharedPreferences.getString("k3", ""));
            k4.setSummary(sharedPreferences.getString("k4", ""));
            k5.setSummary(sharedPreferences.getString("k5", ""));
            k6.setSummary(sharedPreferences.getString("k6", ""));
            k7.setSummary(sharedPreferences.getString("k7", ""));
            k8.setSummary(sharedPreferences.getString("k8", ""));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
