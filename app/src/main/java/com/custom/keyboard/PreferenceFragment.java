package com.custom.keyboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.File;

public class PreferenceFragment
    extends android.preference.PreferenceFragment
    implements SharedPreferences.OnSharedPreferenceChangeListener {

    Context baseContext;
    SharedPreferences sharedPreferences;

    EditTextPreference wordSeparators;

    ListPreference theme;
    SeekPreference height;
    SeekPreference transparency;
    EditTextPreference indentWidth;
    EditTextPreference borderWidth;
    EditTextPreference paddingWidth;
    EditTextPreference borderRadius;
    EditTextPreference fontSize;
    EditTextPreference hintFontSize;
    EditTextPreference emoticonFontSize;
    EditTextPreference unicodeFontSize;
    ColorPreference background;
    ColorPreference foreground;
    ColorPreference borderColor;

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

    Preference resetClipboardHistory;
    Preference resetEmoticonHistory;
    Preference resetUnicodeHistory;
    Preference resetAllPreferences;

    String[] themes;

    Toast toast;

    public void toastIt(String ...args) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        if (!sharedPreferences.getBoolean("debug", false)) return;
        String text;
        if (args.length > 1) {
            StringBuilder result = new StringBuilder();
            for(String n: args) {
                result.append(n).append(" ");
            }
            text = result.toString().trim();
        }
        else {
            text = args[0];
        }
        if (toast != null) toast.cancel();
        toast = Toast.makeText(baseContext, text, Toast.LENGTH_LONG);
        toast.show();
    }

    public void resetClipboardHistory() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        sharedPreferences.edit().remove("clipboard_history").apply();
        toastIt("Clipboard History Reset");
        onSharedPreferenceChanged(sharedPreferences, "");
    }

    public void resetEmoticonHistory() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        sharedPreferences.edit().putString("recent_emoticons", "").apply();
        toastIt("Emoticon History Reset");
        onSharedPreferenceChanged(sharedPreferences, "");
    }

    public void resetUnicodeHistory() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        sharedPreferences.edit().putString("recent_unicode", "").apply();
        toastIt("Unicode History Reset");
        onSharedPreferenceChanged(sharedPreferences, "");
    }

    public void resetAllPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        sharedPreferences.edit().clear().apply();
        String dir = "shared_prefs";
        File root = baseContext.getFilesDir().getParentFile();
        String path = new File(root, dir).getPath();
        String[] sharedPreferencesFileNames = new File(root, dir).list();
        for (String fileName : sharedPreferencesFileNames) {
            String filePath = path+"/"+fileName;
            baseContext.getSharedPreferences(fileName.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().apply();
        }
        baseContext.deleteSharedPreferences("shared_prefs");
        PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);
        toastIt("All Preferences Reset!");
        onSharedPreferenceChanged(sharedPreferences, "");
    }

    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);

        baseContext = getActivity().getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        themes = getResources().getStringArray(R.array.theme_names);

        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);

        String[] themes = getResources().getStringArray(R.array.theme_names);

        wordSeparators = (EditTextPreference)findPreference("word_separators");
        height = (SeekPreference)findPreference("height");
        transparency = (SeekPreference)findPreference("transparency");
        theme = (ListPreference)findPreference("theme");

        indentWidth = (EditTextPreference)findPreference("indent_width");
        borderWidth = (EditTextPreference)findPreference("border_width");
        paddingWidth = (EditTextPreference)findPreference("padding_width");
        borderRadius = (EditTextPreference)findPreference("border_radius");

        fontSize = (EditTextPreference)findPreference("font_size");
        hintFontSize = (EditTextPreference)findPreference("hint_font_size");
        emoticonFontSize = (EditTextPreference)findPreference("emoticon_font_size");
        unicodeFontSize = (EditTextPreference)findPreference("unicode_font_size");
        background = (ColorPreference)findPreference("background_color");
        foreground = (ColorPreference)findPreference("foreground_color");
        borderColor = (ColorPreference)findPreference("border_color");

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

        resetClipboardHistory = findPreference("reset_clipboard_history");
        resetEmoticonHistory = findPreference("reset_emoticon_history");
        resetUnicodeHistory = findPreference("reset_unicode_history");
        resetAllPreferences = findPreference("reset_all_preferences");

        k1 = (EditTextPreference)findPreference("k1");
        k2 = (EditTextPreference)findPreference("k2");
        k3 = (EditTextPreference)findPreference("k3");
        k4 = (EditTextPreference)findPreference("k4");
        k5 = (EditTextPreference)findPreference("k5");
        k6 = (EditTextPreference)findPreference("k6");
        k7 = (EditTextPreference)findPreference("k7");
        k8 = (EditTextPreference)findPreference("k8");

        if (wordSeparators != null) wordSeparators.setText(sharedPreferences.getString("word_separators", ""));
        if (wordSeparators != null) wordSeparators.setSummary(sharedPreferences.getString("word_separators", ""));

        if (height != null) height.setValue(sharedPreferences.getInt("height", 100));
        if (height != null) height.setSummary(""+sharedPreferences.getInt("height", 100));
        if (transparency != null) transparency.setValue(sharedPreferences.getInt("transparency", 100));
        if (transparency != null) transparency.setSummary(""+sharedPreferences.getInt("transparency", 100));

        String themeString = Util.orNull(sharedPreferences.getString("theme", "1"), "1");
        if (themeString != null && theme != null) theme.setValue(themeString);
        if (themeString != null && theme != null) theme.setValueIndex(Integer.parseInt(themeString)-1);
        if (themeString != null && theme != null) theme.setSummary(themes[Integer.parseInt(themeString)-1]);

        if (indentWidth != null) indentWidth.setText(sharedPreferences.getString("indent_width", ""));
        if (indentWidth != null) indentWidth.setSummary(sharedPreferences.getString("indent_width", ""));
        if (borderWidth != null) borderWidth.setText(sharedPreferences.getString("border_width", ""));
        if (borderWidth != null) borderWidth.setSummary(sharedPreferences.getString("border_width", ""));
        if (paddingWidth != null) paddingWidth.setText(sharedPreferences.getString("padding_width", ""));
        if (paddingWidth != null) paddingWidth.setSummary(sharedPreferences.getString("padding_width", ""));
        if (borderRadius != null) borderRadius.setText(sharedPreferences.getString("border_radius", ""));
        if (borderRadius != null) borderRadius.setSummary(sharedPreferences.getString("border_radius", ""));
        if (fontSize != null) fontSize.setText(sharedPreferences.getString("font_size", "48"));
        if (fontSize != null) fontSize.setSummary(sharedPreferences.getString("font_size", "48"));
        if (hintFontSize != null) hintFontSize.setText(sharedPreferences.getString("hint_font_size", "48"));
        if (hintFontSize != null) hintFontSize.setSummary(sharedPreferences.getString("hint_font_size", "48"));
        if (emoticonFontSize != null) emoticonFontSize.setText(sharedPreferences.getString("emoticon_font_size", "24"));
        if (emoticonFontSize != null) emoticonFontSize.setSummary(sharedPreferences.getString("emoticon_font_size", "24"));
        if (unicodeFontSize != null) unicodeFontSize.setText(sharedPreferences.getString("unicode_font_size", "24"));
        if (unicodeFontSize != null) unicodeFontSize.setSummary(sharedPreferences.getString("unicode_font_size", "24"));

        if (background != null) background.setColor(sharedPreferences.getInt("background_color", 0xFF000000));
        if (background != null) background.setSummary("#"+Integer.toHexString(sharedPreferences.getInt("background_color", 0xFF000000)).toUpperCase());
        if (foreground != null) foreground.setColor(sharedPreferences.getInt("foreground_color", 0xFFFFFFFF));
        if (foreground != null) foreground.setSummary("#"+Integer.toHexString(sharedPreferences.getInt("foreground_color", 0xFFFFFFFF)).toUpperCase());
        if (borderColor != null) borderColor.setColor(sharedPreferences.getInt("border_color", 0xFF000000));
        if (borderColor != null) borderColor.setSummary("#"+Integer.toHexString(sharedPreferences.getInt("border_color", 0xFF000000)).toUpperCase());

        if (emoticonRecents != null) emoticonRecents.setText(sharedPreferences.getString("emoticon_recents", ""));
        if (emoticonRecents != null) emoticonRecents.setSummary(sharedPreferences.getString("emoticon_recents", ""));
        if (unicodeRecents != null) unicodeRecents.setText(sharedPreferences.getString("unicode_recents", ""));
        if (unicodeRecents != null) unicodeRecents.setSummary(sharedPreferences.getString("unicode_recents", ""));
        if (emoticonFavorites != null) emoticonFavorites.setText(sharedPreferences.getString("emoticon_favorites", ""));
        if (emoticonFavorites != null) emoticonFavorites.setSummary(sharedPreferences.getString("emoticon_favorites", ""));
        if (unicodeFavorites != null) unicodeFavorites.setText(sharedPreferences.getString("unicode_favorites", ""));
        if (unicodeFavorites != null) unicodeFavorites.setSummary(sharedPreferences.getString("unicode_favorites", ""));
        if (customKeys != null) customKeys.setText(sharedPreferences.getString("custom_keys", ""));
        if (customKeys != null) customKeys.setSummary(sharedPreferences.getString("custom_keys", ""));

        if (popupFirst != null) popupFirst.setText(sharedPreferences.getString("popup_first", ""));
        if (popupFirst != null) popupFirst.setSummary(sharedPreferences.getString("popup_first", ""));
        if (popupSecond != null) popupSecond.setText(sharedPreferences.getString("popup_second", ""));
        if (popupSecond != null) popupSecond.setSummary(sharedPreferences.getString("popup_second", ""));
        if (popupThird != null) popupThird.setText(sharedPreferences.getString("popup_third", ""));
        if (popupThird != null) popupThird.setSummary(sharedPreferences.getString("popup_third", ""));
        if (name != null) name.setText(sharedPreferences.getString("title", ""));
        if (name != null) name.setSummary(sharedPreferences.getString("title", ""));
        if (email != null) email.setText(sharedPreferences.getString("email", ""));
        if (email != null) email.setSummary(sharedPreferences.getString("email", ""));
        if (phone != null) phone.setText(sharedPreferences.getString("phone", ""));
        if (phone != null) phone.setSummary(sharedPreferences.getString("phone", ""));
        if (address != null) address.setText(sharedPreferences.getString("address", ""));
        if (address != null) address.setSummary(sharedPreferences.getString("address", ""));

        String pwd = sharedPreferences.getString("password", "");
        if (pwd != null) {
            String redaction = new String(new char[pwd.length()]).replace("\0", "*");
            if (password != null) password.setText(pwd);
            if (password != null) password.setSummary(redaction);
        }

        if (k1 != null) k1.setText(sharedPreferences.getString("k1", ""));
        if (k1 != null) k1.setSummary(sharedPreferences.getString("k1", ""));
        if (k2 != null) k2.setText(sharedPreferences.getString("k2", ""));
        if (k2 != null) k2.setSummary(sharedPreferences.getString("k2", ""));
        if (k3 != null) k3.setText(sharedPreferences.getString("k3", ""));
        if (k3 != null) k3.setSummary(sharedPreferences.getString("k3", ""));
        if (k4 != null) k4.setText(sharedPreferences.getString("k4", ""));
        if (k4 != null) k4.setSummary(sharedPreferences.getString("k4", ""));
        if (k5 != null) k5.setText(sharedPreferences.getString("k5", ""));
        if (k5 != null) k5.setSummary(sharedPreferences.getString("k5", ""));
        if (k6 != null) k6.setText(sharedPreferences.getString("k6", ""));
        if (k6 != null) k6.setSummary(sharedPreferences.getString("k6", ""));
        if (k7 != null) k7.setText(sharedPreferences.getString("k7", ""));
        if (k7 != null) k7.setSummary(sharedPreferences.getString("k7", ""));
        if (k8 != null) k8.setText(sharedPreferences.getString("k8", ""));
        if (k8 != null) k8.setSummary(sharedPreferences.getString("k8", ""));

        if (resetClipboardHistory != null) {
            resetClipboardHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    resetClipboardHistory();
                    return true;
                }
            });
        }
        if (resetUnicodeHistory != null) {
            resetUnicodeHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    resetUnicodeHistory();
                    return true;
                }
            });
        }
        if (resetEmoticonHistory != null) {
            resetEmoticonHistory.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    resetEmoticonHistory();
                    return true;
                }
            });
        }
        if (resetAllPreferences != null) {
            resetAllPreferences.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    resetAllPreferences();
                    return true;
                }
            });
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Intent intent = new Intent("updateKeyboard");
        if (getContext() != null) getContext().sendBroadcast(intent);

        if (wordSeparators != null) wordSeparators.setText(sharedPreferences.getString("word_separators", ""));
        if (wordSeparators != null) wordSeparators.setSummary(sharedPreferences.getString("word_separators", ""));

        if (height != null) height.setValue(sharedPreferences.getInt("height", 100));
        if (height != null) height.setSummary(""+sharedPreferences.getInt("height", 100));
        if (transparency != null) transparency.setValue(sharedPreferences.getInt("transparency", 100));
        if (transparency != null) transparency.setSummary(""+sharedPreferences.getInt("transparency", 100));

        String themeString = Util.orNull(sharedPreferences.getString("theme", "1"), "1");
        if (themeString != null && theme != null) theme.setValue(themeString);
        if (themeString != null && theme != null) theme.setValueIndex(Integer.parseInt(themeString)-1);
        if (themeString != null && theme != null) theme.setSummary(themes[Integer.parseInt(themeString)-1]);

        if (indentWidth != null) indentWidth.setText(sharedPreferences.getString("indent_width", ""));
        if (indentWidth != null) indentWidth.setSummary(sharedPreferences.getString("indent_width", ""));
        if (borderWidth != null) borderWidth.setText(sharedPreferences.getString("border_width", ""));
        if (borderWidth != null) borderWidth.setSummary(sharedPreferences.getString("border_width", ""));
        if (paddingWidth != null) paddingWidth.setText(sharedPreferences.getString("padding_width", ""));
        if (paddingWidth != null) paddingWidth.setSummary(sharedPreferences.getString("padding_width", ""));
        if (borderRadius != null) borderRadius.setText(sharedPreferences.getString("border_radius", ""));
        if (borderRadius != null) borderRadius.setSummary(sharedPreferences.getString("border_radius", ""));
        if (fontSize != null) fontSize.setText(sharedPreferences.getString("font_size", "48"));
        if (fontSize != null) fontSize.setSummary(sharedPreferences.getString("font_size", "48"));
        if (hintFontSize != null) hintFontSize.setText(sharedPreferences.getString("hint_font_size", "48"));
        if (hintFontSize != null) hintFontSize.setSummary(sharedPreferences.getString("hint_font_size", "48"));
        if (emoticonFontSize != null) emoticonFontSize.setText(sharedPreferences.getString("emoticon_font_size", "24"));
        if (emoticonFontSize != null) emoticonFontSize.setSummary(sharedPreferences.getString("emoticon_font_size", "24"));
        if (unicodeFontSize != null) unicodeFontSize.setText(sharedPreferences.getString("unicode_font_size", "24"));
        if (unicodeFontSize != null) unicodeFontSize.setSummary(sharedPreferences.getString("unicode_font_size", "24"));

        if (background != null) background.setColor(sharedPreferences.getInt("background_color", 0xFF000000));
        if (background != null) background.setSummary("#"+Integer.toHexString(sharedPreferences.getInt("background_color", 0xFF000000)).toUpperCase());
        if (foreground != null) foreground.setColor(sharedPreferences.getInt("foreground_color", 0xFFFFFFFF));
        if (foreground != null) foreground.setSummary("#"+Integer.toHexString(sharedPreferences.getInt("foreground_color", 0xFFFFFFFF)).toUpperCase());
        if (borderColor != null) borderColor.setColor(sharedPreferences.getInt("border_color", 0xFF000000));
        if (borderColor != null) borderColor.setSummary("#"+Integer.toHexString(sharedPreferences.getInt("border_color", 0xFF000000)).toUpperCase());

        if (emoticonRecents != null) emoticonRecents.setText(sharedPreferences.getString("emoticon_recents", ""));
        if (emoticonRecents != null) emoticonRecents.setSummary(sharedPreferences.getString("emoticon_recents", ""));
        if (unicodeRecents != null) unicodeRecents.setText(sharedPreferences.getString("unicode_recents", ""));
        if (unicodeRecents != null) unicodeRecents.setSummary(sharedPreferences.getString("unicode_recents", ""));
        if (emoticonFavorites != null) emoticonFavorites.setText(sharedPreferences.getString("emoticon_favorites", ""));
        if (emoticonFavorites != null) emoticonFavorites.setSummary(sharedPreferences.getString("emoticon_favorites", ""));
        if (unicodeFavorites != null) unicodeFavorites.setText(sharedPreferences.getString("unicode_favorites", ""));
        if (unicodeFavorites != null) unicodeFavorites.setSummary(sharedPreferences.getString("unicode_favorites", ""));
        if (customKeys != null) customKeys.setText(sharedPreferences.getString("custom_keys", ""));
        if (customKeys != null) customKeys.setSummary(sharedPreferences.getString("custom_keys", ""));

        if (popupFirst != null) popupFirst.setText(sharedPreferences.getString("popup_first", ""));
        if (popupFirst != null) popupFirst.setSummary(sharedPreferences.getString("popup_first", ""));
        if (popupSecond != null) popupSecond.setText(sharedPreferences.getString("popup_second", ""));
        if (popupSecond != null) popupSecond.setSummary(sharedPreferences.getString("popup_second", ""));
        if (popupThird != null) popupThird.setText(sharedPreferences.getString("popup_third", ""));
        if (popupThird != null) popupThird.setSummary(sharedPreferences.getString("popup_third", ""));
        if (name != null) name.setText(sharedPreferences.getString("title", ""));
        if (name != null) name.setSummary(sharedPreferences.getString("title", ""));
        if (email != null) email.setText(sharedPreferences.getString("email", ""));
        if (email != null) email.setSummary(sharedPreferences.getString("email", ""));
        if (phone != null) phone.setText(sharedPreferences.getString("phone", ""));
        if (phone != null) phone.setSummary(sharedPreferences.getString("phone", ""));
        if (address != null) address.setText(sharedPreferences.getString("address", ""));
        if (address != null) address.setSummary(sharedPreferences.getString("address", ""));

        String pwd = sharedPreferences.getString("password", "");
        if (pwd != null) {
            String redaction = new String(new char[pwd.length()]).replace("\0", "*");
            if (password != null) password.setText(pwd);
            if (password != null) password.setSummary(redaction);
        }

        if (k1 != null) k1.setText(sharedPreferences.getString("k1", ""));
        if (k1 != null) k1.setSummary(sharedPreferences.getString("k1", ""));
        if (k2 != null) k2.setText(sharedPreferences.getString("k2", ""));
        if (k2 != null) k2.setSummary(sharedPreferences.getString("k2", ""));
        if (k3 != null) k3.setText(sharedPreferences.getString("k3", ""));
        if (k3 != null) k3.setSummary(sharedPreferences.getString("k3", ""));
        if (k4 != null) k4.setText(sharedPreferences.getString("k4", ""));
        if (k4 != null) k4.setSummary(sharedPreferences.getString("k4", ""));
        if (k5 != null) k5.setText(sharedPreferences.getString("k5", ""));
        if (k5 != null) k5.setSummary(sharedPreferences.getString("k5", ""));
        if (k6 != null) k6.setText(sharedPreferences.getString("k6", ""));
        if (k6 != null) k6.setSummary(sharedPreferences.getString("k6", ""));
        if (k7 != null) k7.setText(sharedPreferences.getString("k7", ""));
        if (k7 != null) k7.setSummary(sharedPreferences.getString("k7", ""));
        if (k8 != null) k8.setText(sharedPreferences.getString("k8", ""));
        if (k8 != null) k8.setSummary(sharedPreferences.getString("k8", ""));
    }
}
