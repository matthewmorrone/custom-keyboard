package com.custom.keyboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    Context baseContext;
    SharedPreferences sharedPreferences;

    ListPreference listTheme;
    ListPreference listDefaultLayout;

    EditTextPreference bg;
    EditTextPreference fg;

    EditTextPreference text_size;
    EditTextPreference seps;
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

    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);
        baseContext = getActivity().getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);

        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);

        listTheme = (ListPreference)findPreference("theme");
        listTheme.setSummary(listTheme.getEntry());

        bg = (EditTextPreference)findPreference("bg");
        fg = (EditTextPreference)findPreference("fg");

        text_size = (EditTextPreference)findPreference("text_size");
        seps = (EditTextPreference)findPreference("seps");
        popup_first = (EditTextPreference)findPreference("popup_first");
        popup_second = (EditTextPreference)findPreference("popup_second");
        popup_third = (EditTextPreference)findPreference("popup_third");

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

        popup_first.setSummary(sharedPreferences.getString("popup_first", ""));
        popup_second.setSummary(sharedPreferences.getString("popup_second", ""));
        popup_third.setSummary(sharedPreferences.getString("popup_third", ""));
        k1.setSummary(sharedPreferences.getString("k1", ""));
        k2.setSummary(sharedPreferences.getString("k2", ""));
        k3.setSummary(sharedPreferences.getString("k3", ""));
        k4.setSummary(sharedPreferences.getString("k4", ""));
        k5.setSummary(sharedPreferences.getString("k5", ""));
        k6.setSummary(sharedPreferences.getString("k6", ""));
        k7.setSummary(sharedPreferences.getString("k7", ""));
        k8.setSummary(sharedPreferences.getString("k8", ""));

        PreferenceScreen preferences = (PreferenceScreen)findPreference("layouts");
        if (sharedPreferences.getBoolean("custom_order", false)) {
            for(int i = 0; i < CustomInputMethodService.layouts.size(); i++) {
                CustomKeyboard kv = CustomInputMethodService.layouts.get(i);
                CheckBoxPreference preference;
                preference = (CheckBoxPreference)(preferences.findPreference(kv.key));
                preference.setOrder(preferences.getPreferenceCount()-kv.order);
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        CheckBoxPreference preference;

        if (s.equals("all")) {
            boolean isChecked = ((CheckBoxPreference)findPreference("all")).isChecked();
            ((CheckBoxPreference)findPreference("primary")).setChecked(isChecked);
        }

        if (s.equals("primary")) {
            PreferenceCategory preferences = (PreferenceCategory)findPreference("primary_category");
            boolean isChecked = ((CheckBoxPreference)findPreference("primary")).isChecked();
            for(int i = 0; i < preferences.getPreferenceCount(); i++) {
                preference = (CheckBoxPreference)(preferences.getPreference(i));
                preference.setChecked(isChecked);
            }
        }

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

        Intent intent = new Intent("updateKeyboard");
        getContext().sendBroadcast(intent);

    }
}
