package com.custom.keyboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    Context baseContext;
    SharedPreferences sharedPreferences;

    EditTextPreference seps;

    ListPreference theme;
    EditTextPreference foreground;
    EditTextPreference background;

    EditTextPreference popup_first;
    EditTextPreference popup_second;
    EditTextPreference popup_third;

    EditTextPreference name;
    EditTextPreference email;
    EditTextPreference phone;
    EditTextPreference address;

    EditTextPreference k1;
    EditTextPreference k2;
    EditTextPreference k3;
    EditTextPreference k4;
    EditTextPreference k5;
    EditTextPreference k6;
    EditTextPreference k7;
    EditTextPreference k8;

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
            foreground = (EditTextPreference)findPreference("foreground");
            background = (EditTextPreference)findPreference("background");
            popup_first = (EditTextPreference)findPreference("popup_first");
            popup_second = (EditTextPreference)findPreference("popup_second");
            popup_third = (EditTextPreference)findPreference("popup_third");
            name = (EditTextPreference)findPreference("name");
            email = (EditTextPreference)findPreference("email");
            phone = (EditTextPreference)findPreference("phone");
            address = (EditTextPreference)findPreference("address");
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
            theme.setSummary(sharedPreferences.getString("theme", "2"));
            background.setSummary(sharedPreferences.getString("background", ""));
            foreground.setSummary(sharedPreferences.getString("foreground", ""));
            popup_first.setSummary(sharedPreferences.getString("popup_first", ""));
            popup_second.setSummary(sharedPreferences.getString("popup_second", ""));
            popup_third.setSummary(sharedPreferences.getString("popup_third", ""));
            name.setSummary(sharedPreferences.getString("name", ""));
            email.setSummary(sharedPreferences.getString("email", ""));
            phone.setSummary(sharedPreferences.getString("phone", ""));
            address.setSummary(sharedPreferences.getString("address", ""));
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
            theme.setSummary(sharedPreferences.getString("theme", "2"));
            background.setSummary(sharedPreferences.getString("background", ""));
            foreground.setSummary(sharedPreferences.getString("foreground", ""));
            popup_first.setSummary(sharedPreferences.getString("popup_first", ""));
            popup_second.setSummary(sharedPreferences.getString("popup_second", ""));
            popup_third.setSummary(sharedPreferences.getString("popup_third", ""));
            name.setSummary(sharedPreferences.getString("title", ""));
            email.setSummary(sharedPreferences.getString("email", ""));
            phone.setSummary(sharedPreferences.getString("phone", ""));
            address.setSummary(sharedPreferences.getString("address", ""));
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
