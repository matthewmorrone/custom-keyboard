package com.custom.keyboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.*;

public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    
    ListPreference listTheme;

    EditTextPreference text_size;
    EditTextPreference seps;
    EditTextPreference popup1;
    EditTextPreference popup2;
    EditTextPreference popup3;
    
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

        try {
            addPreferencesFromResource(R.xml.ime_preferences);
        }
        catch (Exception ignored) {}
        try {
            listTheme = (ListPreference)findPreference("theme");
            listTheme.setSummary(listTheme.getEntry());
        }
        catch (Exception ignored) {}

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

        try {
            text_size.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("text_size", ""));
            seps.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("seps", ""));
            popup1.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("popup1", ""));
            popup2.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("popup2", ""));
            popup3.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("popup3", ""));
        }
        catch (Exception ignored) {}
        try {
            k1.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k1", ""));
            k2.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k2", ""));
            k3.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k3", ""));
            k4.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k4", ""));
            k5.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k5", ""));
            k6.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k6", ""));
            k7.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k7", ""));
            k8.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k8", ""));
        }
        catch (Exception ignored) {}
        
        PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).registerOnSharedPreferenceChangeListener(this);
    }
    
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        try {
            addPreferencesFromResource(R.xml.ime_preferences);
        }
        catch (Exception ignored) {}
        try {
            listTheme = (ListPreference)findPreference("theme");
            listTheme.setSummary(listTheme.getEntry());
        }
        catch (Exception ignored) {}
        try {
            text_size.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("text_size", ""));
            seps.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("seps", ""));
            popup1.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("popup1", ""));
            popup2.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("popup2", ""));
            popup3.setSummary(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("popup3", ""));
        }
        catch (Exception ignored) {}
        try {
            k1.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k1", ""));
            k2.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k2", ""));
            k3.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k3", ""));
            k4.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k4", ""));
            k5.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k5", ""));
            k6.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k6", ""));
            k7.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k7", ""));
            k8.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k8", ""));
        }
        catch (Exception ignored) {}
    }
}
