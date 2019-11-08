package com.custom.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import java.util.Arrays;
import java.util.List;

public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    Context baseContext;
    SharedPreferences sharedPreferences;

    ListPreference listTheme;
    ListPreference listDefaultLayout;

    EditTextPreference bg;
    EditTextPreference fg;

    EditTextPreference text_size;
    EditTextPreference seps;
    EditTextPreference popup_a;
    EditTextPreference popup_b;
    EditTextPreference popup_c;

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

    List<String> required = Arrays.asList(
         "primary", "numeric", "url"
    );

    List<String> primary = Arrays.asList(
         "extra", "fonts", "function", "hex",
         "macros", "navigation", "utility", "unicode"
    );

    List<String> secondary = Arrays.asList(
         "armenian", "braille", "cherokee", "coptic",
         "cree", "cyrillic", "deseret", "devanagari",
         "etruscan", "futhark", "georgian", "glagolitic",
         "gothic", "greek", "hiragana", "katakana",
         "lisu", "ogham", "tifinagh", "zhuyin"
    );

    List<String> tertiary = Arrays.asList(
         "clipboard", "mirror", "qwerty", "shift",
         "accents", "coding", "dvorak", "emoji",
         "ipa", "math", "symbol"
    );

    List<String> forthary = Arrays.asList(
         "caps", "morse", "drawing", "insular",
         "pointy", "rotated", "stealth", "strike",
         "tails"
    );

    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);
        baseContext = getActivity().getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);

        try {
            addPreferencesFromResource(R.xml.preferences);
            PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);
        }
        catch (Exception ignored) {}
        try {
            listDefaultLayout = (ListPreference)findPreference("default_layout");
            listDefaultLayout.setSummary(listDefaultLayout.getEntry());
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
        popup_a = (EditTextPreference)findPreference("popup_a");
        popup_b = (EditTextPreference)findPreference("popup_b");
        popup_c = (EditTextPreference)findPreference("popup_c");

        k1 = (EditTextPreference)findPreference("k1");
        k2 = (EditTextPreference)findPreference("k2");
        k3 = (EditTextPreference)findPreference("k3");
        k4 = (EditTextPreference)findPreference("k4");
        k5 = (EditTextPreference)findPreference("k5");
        k6 = (EditTextPreference)findPreference("k6");
        k7 = (EditTextPreference)findPreference("k7");
        k8 = (EditTextPreference)findPreference("k8");

        try {
            name = (EditTextPreference)findPreference("name");
            name.setSummary(sharedPreferences.getString("name", ""));
            email = (EditTextPreference)findPreference("email");
            email.setSummary(sharedPreferences.getString("email", ""));
            phone = (EditTextPreference)findPreference("phone");
            phone.setSummary(sharedPreferences.getString("phone", ""));
            address = (EditTextPreference)findPreference("address");
            address.setSummary(sharedPreferences.getString("address", ""));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            bg.setSummary(sharedPreferences.getString("bg", ""));
            fg.setSummary(sharedPreferences.getString("fg", ""));
        }
        catch (Exception ignored) {}
        try {
            text_size.setSummary(sharedPreferences.getString("text_size", ""));
            seps.setSummary(sharedPreferences.getString("seps", ""));
            popup_a.setSummary(sharedPreferences.getString("popup_a", ""));
            popup_b.setSummary(sharedPreferences.getString("popup_b", ""));
            popup_c.setSummary(sharedPreferences.getString("popup_c", ""));
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



        PreferenceCategory preferences = (PreferenceCategory)findPreference("layouts");
        if (sharedPreferences.getBoolean("custom_order", false)) {
            for(int i = 0; i < CustomInputMethodService.layouts.size(); i++) {
                CustomKeyboard kv = CustomInputMethodService.layouts.get(i);
                CheckBoxPreference preference;
                try {
                    preference = (CheckBoxPreference)(preferences.findPreference(kv.key));
                    preference.setOrder(preferences.getPreferenceCount()-kv.order);
                }
                catch (Exception ignored) {}
            }
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        CheckBoxPreference preference = (CheckBoxPreference)findPreference("all");

        if (s.equals("primary")) {
            PreferenceCategory preferences = (PreferenceCategory)findPreference("layouts");
            boolean isChecked = ((CheckBoxPreference)findPreference("primary")).isChecked();
            for(int i = 0; i < preferences.getPreferenceCount(); i++) {
                preference = (CheckBoxPreference)(preferences.getPreference(i));
                if (preference == null) continue;
                if (primary.contains(preference.getKey())) {
                    preference.setChecked(isChecked);
                }
            }
        }
        if (s.equals("secondary")) {
            PreferenceCategory preferences = (PreferenceCategory)findPreference("layouts");
            boolean isChecked = ((CheckBoxPreference)findPreference("secondary")).isChecked();
            for(int i = 0; i < preferences.getPreferenceCount(); i++) {
                preference = (CheckBoxPreference)(preferences.getPreference(i));
                if (preference == null) continue;
                if (secondary.contains(preference.getKey())) {
                    preference.setChecked(isChecked);
                }
            }
        }
        if (s.equals("tertiary")) {
            PreferenceCategory preferences = (PreferenceCategory)findPreference("layouts");
            boolean isChecked = ((CheckBoxPreference)findPreference("tertiary")).isChecked();
            for(int i = 0; i < preferences.getPreferenceCount(); i++) {
                preference = (CheckBoxPreference)(preferences.getPreference(i));
                if (preference == null) continue;
                if (tertiary.contains(preference.getKey())) {
                    preference.setChecked(isChecked);
                }
            }
        }
        if (s.equals("forthary")) {
            PreferenceCategory preferences = (PreferenceCategory)findPreference("layouts");
            boolean isChecked = ((CheckBoxPreference)findPreference("forthary")).isChecked();
            for(int i = 0; i < preferences.getPreferenceCount(); i++) {
                preference = (CheckBoxPreference)(preferences.getPreference(i));
                if (preference == null) continue;
                if (forthary.contains(preference.getKey())) {
                    preference.setChecked(isChecked);
                }
            }
        }

        if (s.equals("all")) {
            StringBuilder layoutOrder = new StringBuilder();
            PreferenceCategory preferences = (PreferenceCategory)findPreference("layouts");
            boolean isChecked = ((CheckBoxPreference)findPreference("all")).isChecked();
            int start = preference.getOrder();
            for(int i = 0; i < preferences.getPreferenceCount(); i++) {
                preference = (CheckBoxPreference)(preferences.getPreference(i));
                if (preference == null) continue;
                preference.setChecked(isChecked);
                layoutOrder.append(preference.getTitle()).append(" ").append(i).append("\n");
            }
            ((CheckBoxPreference)findPreference("primary")).setChecked(isChecked);
            ((CheckBoxPreference)findPreference("secondary")).setChecked(isChecked);
            ((CheckBoxPreference)findPreference("tertiary")).setChecked(isChecked);
            ((CheckBoxPreference)findPreference("forthary")).setChecked(isChecked);
            EditTextPreference layoutList = (EditTextPreference)findPreference("layout_order");
            layoutList.setText(layoutOrder.toString().trim());
        }
        try {
            listDefaultLayout = (ListPreference)findPreference("default_layout");
            listDefaultLayout.setSummary(listDefaultLayout.getEntry());
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
            popup_a.setSummary(sharedPreferences.getString("popup_a", ""));
            popup_b.setSummary(sharedPreferences.getString("popup_b", ""));
            popup_c.setSummary(sharedPreferences.getString("popup_c", ""));
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
