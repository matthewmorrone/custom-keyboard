package com.custom.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;

public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    
    Context baseContext;
    SharedPreferences sharedPreferences;
    
    ListPreference listTheme;

    EditTextPreference bg;
    EditTextPreference fg;

    EditTextPreference text_size;
    EditTextPreference seps;
    EditTextPreference popup1;
    EditTextPreference popup2;
    EditTextPreference popup3;

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
    
    // CheckboxPreference[] layouts;
    
    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);
        baseContext = getActivity().getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);

        try {
            addPreferencesFromResource(R.xml.preferences);

            // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(baseContext);
            // prefs.edit().clear().apply();
            // PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);
        }
        catch (Exception ignored) {}
        try {
            listTheme = (ListPreference)findPreference("theme");
            listTheme.setSummary(listTheme.getEntry());
        }
        catch (Exception ignored) {}

        bg = (EditTextPreference)findPreference("bg");
        fg = (EditTextPreference)findPreference("fg");

        text_size = (EditTextPreference)findPreference("text_size");
        seps = (EditTextPreference)findPreference("seps");
        popup1 = (EditTextPreference)findPreference("popup1");
        popup2 = (EditTextPreference)findPreference("popup2");
        popup3 = (EditTextPreference)findPreference("popup3");
        
        k1 = (EditTextPreference)findPreference("k1");
        k2 = (EditTextPreference)findPreference("k2");
        k3 = (EditTextPreference)findPreference("k3");
        k4 = (EditTextPreference)findPreference("k4");
        k5 = (EditTextPreference)findPreference("k5");
        k6 = (EditTextPreference)findPreference("k6");
        k7 = (EditTextPreference)findPreference("k7");
        k8 = (EditTextPreference)findPreference("k8");

        name = (EditTextPreference)findPreference("name");
        name.setSummary(sharedPreferences.getString("name", ""));
        email = (EditTextPreference)findPreference("email");
        email.setSummary(sharedPreferences.getString("email", ""));
        phone = (EditTextPreference)findPreference("phone");
        phone.setSummary(sharedPreferences.getString("phone", ""));
        address = (EditTextPreference)findPreference("address");
        address.setSummary(sharedPreferences.getString("address", ""));

        try {
            bg.setSummary(sharedPreferences.getString("bg", ""));
            fg.setSummary(sharedPreferences.getString("fg", ""));
        }
        catch (Exception ignored) {}
        try {
            text_size.setSummary(sharedPreferences.getString("text_size", ""));
            seps.setSummary(sharedPreferences.getString("seps", ""));
            popup1.setSummary(sharedPreferences.getString("popup1", ""));
            popup2.setSummary(sharedPreferences.getString("popup2", ""));
            popup3.setSummary(sharedPreferences.getString("popup3", ""));
        }
        catch (Exception ignored) {}
        try {
            k1.setSummary(sharedPreferences.getString("k1", ""));
            k2.setSummary(sharedPreferences.getString("k2", ""));
            k3.setSummary(sharedPreferences.getString("k3", ""));
            k4.setSummary(sharedPreferences.getString("k4", ""));
            k5.setSummary(sharedPreferences.getString("k5", ""));
            k6.setSummary(sharedPreferences.getString("k6", ""));
            k7.setSummary(sharedPreferences.getString("k7", ""));
            k8.setSummary(sharedPreferences.getString("k8", ""));
        }
        catch (Exception ignored) {}
/*
        PreferenceCategory preferences = (PreferenceCategory)findPreference("layouts");
        for(int i = 0; i < CustomInputMethodService.layouts.size(); i++) {
            CustomKeyboard kv = CustomInputMethodService.layouts.get(i);
            CheckBoxPreference preference;
            try {
                preference = (CheckBoxPreference)(preferences.findPreference(kv.key));
                preference.setOrder(preferences.getPreferenceCount()-kv.order);
            }
            catch (Exception ignored) {}

        }
*/
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        try {
            // addPreferencesFromResource(R.xml.preferences);
        }
        catch (Exception ignored) {}
        try {
            bg.setSummary(sharedPreferences.getString("bg", ""));
            fg.setSummary(sharedPreferences.getString("fg", ""));
        }
        catch (Exception ignored) {}
        try {
            listTheme = (ListPreference)findPreference("theme");
            listTheme.setSummary(listTheme.getEntry());
        }
        catch (Exception ignored) {}
        try {
            text_size.setSummary(sharedPreferences.getString("text_size", ""));
            seps.setSummary(sharedPreferences.getString("seps", ""));
            popup1.setSummary(sharedPreferences.getString("popup1", ""));
            popup2.setSummary(sharedPreferences.getString("popup2", ""));
            popup3.setSummary(sharedPreferences.getString("popup3", ""));

            name.setSummary(sharedPreferences.getString("title", ""));
            email.setSummary(sharedPreferences.getString("email", ""));
            phone.setSummary(sharedPreferences.getString("phone", ""));
            address.setSummary(sharedPreferences.getString("address", ""));
        }
        catch (Exception ignored) {}
        try {
            k1.setSummary(sharedPreferences.getString("k1", ""));
            k2.setSummary(sharedPreferences.getString("k2", ""));
            k3.setSummary(sharedPreferences.getString("k3", ""));
            k4.setSummary(sharedPreferences.getString("k4", ""));
            k5.setSummary(sharedPreferences.getString("k5", ""));
            k6.setSummary(sharedPreferences.getString("k6", ""));
            k7.setSummary(sharedPreferences.getString("k7", ""));
            k8.setSummary(sharedPreferences.getString("k8", ""));
        }
        catch (Exception ignored) {}
    }
}
