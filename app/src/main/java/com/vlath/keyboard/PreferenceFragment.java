package com.vlath.keyboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.*;

public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

  ListPreference listTheme;

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
    addPreferencesFromResource(R.xml.ime_preferences);
    listTheme = (ListPreference)findPreference("theme");
    listTheme.setSummary(listTheme.getEntry());

    k1 = (EditTextPreference)findPreference("k1");
    k2 = (EditTextPreference)findPreference("k2");
    k3 = (EditTextPreference)findPreference("k3");
    k4 = (EditTextPreference)findPreference("k4");
    k5 = (EditTextPreference)findPreference("k5");
    k6 = (EditTextPreference)findPreference("k6");
    k7 = (EditTextPreference)findPreference("k7");
    k8 = (EditTextPreference)findPreference("k8");

    k1.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k1", ""));
    k2.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k2", ""));
    k3.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k3", ""));
    k4.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k4", ""));
    k5.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k5", ""));
    k6.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k6", ""));
    k7.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k7", ""));
    k8.setTitle(PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).getString("k8", ""));

    PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
    listTheme = (ListPreference)findPreference("theme");
    listTheme.setSummary(listTheme.getEntry());

    System.out.println(sharedPreferences+" "+s);

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
    catch (NullPointerException e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }
}
