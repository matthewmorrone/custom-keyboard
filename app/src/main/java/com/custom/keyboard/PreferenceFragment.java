package com.custom.keyboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Objects;

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

    EditTextPreference indentWidth;

    EditTextPreference k1;
    EditTextPreference k2;
    EditTextPreference k3;
    EditTextPreference k4;
    EditTextPreference k5;
    EditTextPreference k6;
    EditTextPreference k7;
    EditTextPreference k8;

    ColorPreference background;
    ColorPreference foreground;

    ArrayList<String> themes = new ArrayList<>();


    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);

        baseContext = getActivity().getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);

        try {
            addPreferencesFromResource(R.xml.preferences);
            PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        try {
            seps = (EditTextPreference)findPreference("seps");
            theme = (ListPreference)findPreference("theme");
            // for(CharSequence entry : theme.getEntryValues()) {
            //     themes.add(String.valueOf(entry));
            // }
            background = (ColorPreference)findPreference("bgcolor");
            foreground = (ColorPreference)findPreference("fgcolor");
            popup_first = (EditTextPreference)findPreference("popup_first");
            popup_second = (EditTextPreference)findPreference("popup_second");
            popup_third = (EditTextPreference)findPreference("popup_third");
            name = (EditTextPreference)findPreference("name");
            email = (EditTextPreference)findPreference("email");
            phone = (EditTextPreference)findPreference("phone");
            address = (EditTextPreference)findPreference("address");
            password = (EditTextPreference)findPreference("password");
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
            theme.setSummary(sharedPreferences.getString("theme", "1"));

            background.setColor(sharedPreferences.getInt("bgcolor", 0xFF000000));
            foreground.setColor(sharedPreferences.getInt("fgcolor", 0xFFFFFFFF));

            popup_first.setSummary(sharedPreferences.getString("popup_first", ""));
            popup_second.setSummary(sharedPreferences.getString("popup_second", ""));
            popup_third.setSummary(sharedPreferences.getString("popup_third", ""));
            name.setSummary(sharedPreferences.getString("name", ""));
            email.setSummary(sharedPreferences.getString("email", ""));
            phone.setSummary(sharedPreferences.getString("phone", ""));
            address.setSummary(sharedPreferences.getString("address", ""));
            String redaction = new String(new char[sharedPreferences.getString("password", "").length()]).replace("\0", "*");
            password.setSummary(redaction);
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
            theme.setSummary(sharedPreferences.getString("theme", "1"));

            background.setColor(sharedPreferences.getInt("bgcolor", 0xFF000000));
            foreground.setColor(sharedPreferences.getInt("fgcolor", 0xFFFFFFFF));

            popup_first.setSummary(sharedPreferences.getString("popup_first", ""));
            popup_second.setSummary(sharedPreferences.getString("popup_second", ""));
            popup_third.setSummary(sharedPreferences.getString("popup_third", ""));
            name.setSummary(sharedPreferences.getString("title", ""));
            email.setSummary(sharedPreferences.getString("email", ""));
            phone.setSummary(sharedPreferences.getString("phone", ""));
            address.setSummary(sharedPreferences.getString("address", ""));
            String redaction = new String(new char[Objects.requireNonNull(sharedPreferences.getString("password", "")).length()]).replace("\0", "*");
            password.setSummary(redaction);
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
