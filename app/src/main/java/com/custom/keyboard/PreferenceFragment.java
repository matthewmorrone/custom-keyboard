package com.custom.keyboard;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.DOWNLOAD_SERVICE;

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
    Preference chooseImageBackground;

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

    Preference importPreferences;
    Preference exportPreferences;
    Preference resetClipboardHistory;
    Preference resetEmoticonHistory;
    Preference resetUnicodeHistory;
    Preference resetAllPreferences;
    Preference setDefaultPreferences;

    String[] themes;

    Toast toast;

    Preference[] preferenceArray;
    ListPreference[] listPreferenceArray;
    SeekPreference[] seekPreferenceArray;
    ColorPreference[] colorPreferenceArray;
    EditTextPreference[] editTextPreferenceArray;
    ArrayList<Preference> preferences;
    ArrayList<ListPreference> listPreferences;
    ArrayList<SeekPreference> seekPreferences;
    ArrayList<ColorPreference> colorPreferences;
    ArrayList<EditTextPreference> editTextPreferences;

    private static final int CHOOSE_FILE_REQUEST_CODE = 8777;
    private static final int PICK_FILE_RESULT_CODE = 8778;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public void toastIt(String ...args) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        // if (!sharedPreferences.getBoolean("debug", false)) return;
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

    public void chooseImageBackground() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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

    public void importPreferences() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*"); // "text/xml"
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, PICK_FILE_RESULT_CODE);
    }

    public void exportPreferences() {

        DownloadManager downloadManager = (DownloadManager)baseContext.getSystemService(DOWNLOAD_SERVICE);

        String settingsFileName = "com.custom.keyboard_preferences.xml";
        String sharedPreferencesDir = "shared_prefs";

        String sourcePathString = baseContext.getFilesDir().getParentFile().getPath() + "/" + sharedPreferencesDir + "/" + settingsFileName;
        File sourceFile = new File(sourcePathString);
        String sourceFileName = sourceFile.getName();
        String sourceContents = Util.readFile(sourcePathString);

        String targetPathString = baseContext.getExternalFilesDir(null)./*getParentFile().*/getPath() + "/" + settingsFileName;
        File targetFile = new File(targetPathString);
        String targetFileName = targetFile.getName();

        long fileId = downloadManager.addCompletedDownload(sourceFileName, sourceFileName, true, "text/plain", targetFile.getAbsolutePath(), sourceContents.length(), true);
        toastIt("saved to "+targetFile.getPath());

        /*
        // System.out.println(downloadManager.getUriForDownloadedFile(fileId).getPath());
        Uri uri = Uri.parse(sourcePathString);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, targetFileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // to notify when download is complete
        request.allowScanningByMediaScanner();// if you want to be available from media players
        DownloadManager manager = (DownloadManager)baseContext.getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);
        */

        try {
            if (targetFile.exists()) targetFile.delete();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Util.copyFile(sourceFile, targetFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetAllPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        sharedPreferences.edit().clear().apply();
        File root = baseContext.getFilesDir().getParentFile();
        String dir = "shared_prefs";
        String path = new File(root, dir).getPath();
        String[] sharedPreferencesFileNames = new File(root, dir).list();
        for (String fileName : sharedPreferencesFileNames) {
            String filePath = path+"/"+fileName;
            baseContext.getSharedPreferences(fileName.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().apply();
        }
        baseContext.deleteSharedPreferences("shared_prefs");
        setDefaultPreferences();
        toastIt("All Preferences Reset!");
        onSharedPreferenceChanged(sharedPreferences, "");
    }

    public void setDefaultPreferences() {
        sharedPreferences.edit().clear().apply();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);
        PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            // System.out.println(data);

            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = baseContext.getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

            // Do something with the bitmap

            new ImageSaver(baseContext)
                .setFileName("background.jpg")
                .setDirectoryName("background")
                .save(bitmap);

            cursor.close();
        }

        if (requestCode == PICK_FILE_RESULT_CODE && resultCode == RESULT_OK) {
            Uri content_describer = data.getData();
            BufferedReader reader = null;
            try {
                InputStream in = baseContext.getContentResolver().openInputStream(content_describer);
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null){
                    builder.append(line);
                }
                Document xml = Util.toXmlDocument(builder.toString());
                Node map = xml.getElementsByTagName("map").item(0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                for(Node n : XmlUtil.asList(map.getChildNodes())) {

                    String nodeName = n.getNodeName();
                    String nodeValue = n.getNodeValue() == null ? "\"\"" : n.getNodeValue();
                    String nodeTextContent = n.getTextContent().trim();

                    String nodeAttrName = (n.getAttributes() != null && n.getAttributes().getNamedItem("name") != null)
                                          ? n.getAttributes().getNamedItem("name").getNodeValue() : "[]";

                    String nodeAttrValue = (n.getAttributes() != null && n.getAttributes().getNamedItem("value") != null)
                                           ? n.getAttributes().getNamedItem("value").getNodeValue() : "[]";

                    switch(n.getNodeName()) {
                        case "int":
                            editor.putInt(nodeAttrName, Integer.parseInt(nodeAttrValue));
                            // System.out.println(nodeName+":"+nodeAttrName+" = "+nodeAttrValue);
                            break;
                        case "string":
                            editor.putString(nodeAttrName, nodeAttrValue);
                            // System.out.println(nodeName+":"+nodeAttrName+" = "+nodeTextContent);
                            break;
                        case "boolean":
                            editor.putBoolean(nodeAttrName, Boolean.parseBoolean(nodeAttrValue));
                            // System.out.println(nodeName+":"+nodeAttrName+" = "+nodeAttrValue);
                            break;
                        case "#text":

                            break;
                    }
                    editor.apply();

                }
                toastIt("Preferences Imported!");
            }
            catch (IOException e) {
                e.printStackTrace();
                toastIt("Preferences not imported");
            }
            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);

        themes = getResources().getStringArray(R.array.theme_names);

        baseContext = getActivity().getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext);

        addPreferencesFromResource(R.xml.preferences);
        // PreferenceManager.setDefaultValues(baseContext, R.xml.preferences, true);
        setDefaultPreferences();

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
        chooseImageBackground = (Preference)findPreference("choose_image_background");

        emoticonRecents = (EditTextPreference)findPreference("emoticon_recents");
        unicodeRecents = (EditTextPreference)findPreference("unicode_recents");
        emoticonFavorites = (EditTextPreference)findPreference("emoticon_favorites");
        unicodeFavorites = (EditTextPreference)findPreference("unicode_favorites");
        customKeys = (EditTextPreference)findPreference("custom_keys");
        name = (EditTextPreference)findPreference("name");
        email = (EditTextPreference)findPreference("email");
        phone = (EditTextPreference)findPreference("phone");
        address = (EditTextPreference)findPreference("address");
        password = (EditTextPreference)findPreference("password");

        importPreferences = findPreference("import_preferences");
        exportPreferences = findPreference("export_preferences");
        resetClipboardHistory = findPreference("reset_clipboard_history");
        resetEmoticonHistory = findPreference("reset_emoticon_history");
        resetUnicodeHistory = findPreference("reset_unicode_history");
        resetAllPreferences = findPreference("reset_all_preferences");
        setDefaultPreferences = findPreference("set_default_preferences");

        k1 = (EditTextPreference)findPreference("k1");
        k2 = (EditTextPreference)findPreference("k2");
        k3 = (EditTextPreference)findPreference("k3");
        k4 = (EditTextPreference)findPreference("k4");
        k5 = (EditTextPreference)findPreference("k5");
        k6 = (EditTextPreference)findPreference("k6");
        k7 = (EditTextPreference)findPreference("k7");
        k8 = (EditTextPreference)findPreference("k8");

        setupSharedPreferences();
        updateSharedPreferences();

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Intent intent = new Intent("updateKeyboard");
        if (getContext() != null) getContext().sendBroadcast(intent);

        updateSharedPreferences();
    }

    public void setupSharedPreferences() {

        preferenceArray = new Preference[]{chooseImageBackground, exportPreferences, importPreferences, resetAllPreferences, resetClipboardHistory, resetEmoticonHistory, resetUnicodeHistory, setDefaultPreferences};
        listPreferenceArray = new ListPreference[]{theme};
        seekPreferenceArray = new SeekPreference[]{height, transparency};
        colorPreferenceArray = new ColorPreference[]{background, borderColor, foreground};
        editTextPreferenceArray = new EditTextPreference[]{address, borderRadius, borderWidth, customKeys, email, emoticonFavorites, emoticonFontSize, emoticonRecents, fontSize, hintFontSize, indentWidth, k1, k2, k3, k4, k5, k6, k7, k8, name, paddingWidth, password, phone, unicodeFavorites, unicodeFontSize, unicodeRecents, wordSeparators};

        preferences = new ArrayList<>(Arrays.asList(preferenceArray));
        listPreferences = new ArrayList<>(Arrays.asList(listPreferenceArray));
        seekPreferences = new ArrayList<>(Arrays.asList(seekPreferenceArray));
        colorPreferences = new ArrayList<>(Arrays.asList(colorPreferenceArray));
        editTextPreferences = new ArrayList<>(Arrays.asList(editTextPreferenceArray));

        for(ListPreference listPreference : listPreferences) {
            listPreference.setDialogTitle(listPreference.getTitle());
            listPreference.setSummary(sharedPreferences.getString(listPreference.getKey(), ""));
            listPreference.setNegativeButtonText("BACK");
            listPreference.setPositiveButtonText("SAVE");
        }

        for(EditTextPreference editTextPreference : editTextPreferences) {
            editTextPreference.setDialogTitle(editTextPreference.getTitle());
            editTextPreference.setSummary(sharedPreferences.getString(editTextPreference.getKey(), ""));
            editTextPreference.setNegativeButtonText("BACK");
            editTextPreference.setPositiveButtonText("SAVE");
        }

        // wordSeparators.setDefaultValue(
        //     // Objects.equals(sharedPreferences.getString("word_separators", ""), "")
        //     // ?
        //     getResources().getString(R.string.word_separators)
        //     // : sharedPreferences.getString("word_separators", "")
        // );

        if (importPreferences != null) {
            importPreferences.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    importPreferences();
                    return true;
                }
            });
        }
        if (exportPreferences != null) {
            exportPreferences.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    exportPreferences();
                    return true;
                }
            });
        }
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
                    updateSharedPreferences();
                    return true;
                }
            });
        }
        if (setDefaultPreferences != null) {
            setDefaultPreferences.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    setDefaultPreferences();
                    updateSharedPreferences();
                    return true;
                }
            });
        }
        if (chooseImageBackground != null) {
            chooseImageBackground.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    chooseImageBackground();
                    return true;
                }
            });
        }
    }

    public void updateSharedPreferences() {

        if (wordSeparators != null) wordSeparators.setText(sharedPreferences.getString("word_separators", ""));
        if (wordSeparators != null) wordSeparators.setSummary(sharedPreferences.getString("word_separators", ""));

        if (height != null) height.setValue(sharedPreferences.getInt("height", 100));
        if (transparency != null) transparency.setValue(sharedPreferences.getInt("transparency", 100));

        String themeString = Util.orNull(sharedPreferences.getString("theme", "1"), "1");
        if (themeString != null && theme != null) theme.setValue(themeString);
        if (themeString != null && theme != null) theme.setValueIndex(Integer.parseInt(themeString)-1);
        if (themeString != null && theme != null) theme.setSummary(themes[Integer.parseInt(themeString)-1]);

        if (indentWidth != null) indentWidth.setText(sharedPreferences.getString("indent_width", "0"));
        if (indentWidth != null) indentWidth.setSummary(sharedPreferences.getString("indent_width", "0"));
        if (borderWidth != null) borderWidth.setText(sharedPreferences.getString("border_width", "0"));
        if (borderWidth != null) borderWidth.setSummary(sharedPreferences.getString("border_width", "0"));
        if (paddingWidth != null) paddingWidth.setText(sharedPreferences.getString("padding_width", "0"));
        if (paddingWidth != null) paddingWidth.setSummary(sharedPreferences.getString("padding_width", "0"));
        if (borderRadius != null) borderRadius.setText(sharedPreferences.getString("border_radius", "0"));
        if (borderRadius != null) borderRadius.setSummary(sharedPreferences.getString("border_radius", "0"));
        if (fontSize != null) fontSize.setText(sharedPreferences.getString("font_size", "48"));
        if (fontSize != null) fontSize.setSummary(sharedPreferences.getString("font_size", "48"));
        if (hintFontSize != null) hintFontSize.setText(sharedPreferences.getString("hint_font_size", "32"));
        if (hintFontSize != null) hintFontSize.setSummary(sharedPreferences.getString("hint_font_size", "32"));
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

        if (name != null) name.setText(sharedPreferences.getString("name", ""));
        if (name != null) name.setSummary(sharedPreferences.getString("name", ""));
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
