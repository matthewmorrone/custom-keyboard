package com.custom.keyboard;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.TextServicesManager;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

enum Category {Main, Lang, Util, Font, Misc}

class Bounds {
    public int minX;
    public int minY;
    public int maxX;
    public int maxY;

    public int dX;
    public int dY;

    public Bounds(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        this.dX = Math.abs(this.maxX - this.minX);
        this.dY = Math.abs(this.maxY - this.minY);
    }

    public Bounds(int[] bounds) {
        if (bounds.length < 4) return;
        this.minX = bounds[0];
        this.minY = bounds[1];
        this.maxX = bounds[2];
        this.maxY = bounds[3];

        this.dX = Math.abs(this.maxX - this.minX);
        this.dY = Math.abs(this.maxY - this.minY);
    }

    @Override
    public String toString() {
        return "minX: "+minX+" minY: "+minY+" maxX: "+maxX+" maxY: "+maxY;
    }
}

public class CustomInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    public InputMethodManager mInputMethodManager;
    public CustomKeyboardView mInputView;
    public int mLastDisplayWidth;
    public boolean mCapsLock;
    public int MAX = 65536;
    CustomKeyboardView kv;
    CustomKeyboard currentKeyboard;
    int currentKeyboardID = 0;
    short rowNumber = 6;
    public String mWordSeparators;
    public StringBuilder mComposing = new StringBuilder();
    public boolean mPredictionOn;
    public boolean mCompletionOn;
    SpellCheckerSession mScs;
    List<String> mSuggestions;
    public CustomView mCandidateView;
    public CompletionInfo[] mCompletions;

    boolean firstCaps = false;
    float[] mDefaultFilter;
    long shift_pressed = 0;

    Toast toast;

    int selectionCase = 0;
    static String hexBuffer = "";
    static String morseBuffer = "";

    InputConnection ic = getCurrentInputConnection();
    SharedPreferences sharedPreferences;

    public static ArrayList<CustomKeyboard> layouts = new ArrayList<>(5);

    public ArrayList<String> clipboardHistory = new ArrayList<>(10);
    boolean fals = false;


    public void populate() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        layouts.clear();
                                                                 layouts.add(new CustomKeyboard(this, R.layout.primary,     "primary",     "Primary",    "primary").setCategory(Category.Main).setOrder(0));
        if (sharedPreferences.getBoolean("accents",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.accents,     "accents",     "Accents",    "◌̀◌́◌̂").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("armenian",     fals)) {layouts.add(new CustomKeyboard(this, R.layout.armenian,    "armenian",    "Armenian",   "աբգդեզ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("braille",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.braille,     "braille",     "Braille",    "⠟⠺⠑⠗⠞⠽").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("caps",         fals)) {layouts.add(new CustomKeyboard(this, R.layout.caps,        "caps",        "Caps",       "ҩᴡᴇʀᴛʏ").setCategory(Category.Font));}
        if (sharedPreferences.getBoolean("cherokee",     fals)) {layouts.add(new CustomKeyboard(this, R.layout.cherokee,    "cherokee",    "Cherokee",   "ꭰꭱꭲꭳꭴꭵ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("coding",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.coding,      "coding",      "Coding",     "∅⊤⊥").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("coptic",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.coptic,      "coptic",      "Coptic",     "ⲑϣⲉⲣⲧⲯ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("cree",         fals)) {layouts.add(new CustomKeyboard(this, R.layout.cree,        "cree",        "Cree",       "ᐁᐯᑌᑫᒉᒣ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("cyrillic",     fals)) {layouts.add(new CustomKeyboard(this, R.layout.cyrillic,    "cyrillic",    "Cyrillic",   "йцукен").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("demorse",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.demorse,     "demorse",     "Demorse",    "-·-·").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("deseret",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.deseret,     "deseret",     "Deseret",    "𐐨𐐩𐐪𐐫𐐬𐐭").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("drawing",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.drawing,     "drawing",     "Drawing",    "├─┤").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("dvorak",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.dvorak,      "dvorak",      "Dvorak",     "pyfgcr").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("emoji",        fals)) {layouts.add(new CustomKeyboard(this, R.layout.emoji,       "emoji",       "Emoji",      "😀😁😂").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("enmorse",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.enmorse,     "enmorse",     "Enmorse",    "qwerty").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("etruscan",     fals)) {layouts.add(new CustomKeyboard(this, R.layout.etruscan,    "etruscan",    "Etruscan",   "𐌀𐌁𐌂𐌃𐌄𐌅").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("extra",        fals)) {layouts.add(new CustomKeyboard(this, R.layout.extra,       "extra",       "Extra",      "☳ツᰄ").setCategory(Category.Util).setOrder(-4));}
        if (sharedPreferences.getBoolean("fonts",        fals)) {layouts.add(new CustomKeyboard(this, R.layout.fonts,       "fonts",       "Fonts",      "🄰🅐🄐𝔸𝕬𝒜").setCategory(Category.Font));}
        if (sharedPreferences.getBoolean("function",     fals)) {layouts.add(new CustomKeyboard(this, R.layout.function,    "function",    "Function",   "ƒ(x)").setCategory(Category.Util).setOrder(-2));}
        if (sharedPreferences.getBoolean("futhark",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.futhark,     "futhark",     "Futhark",    "ᚠᚢᚦᚨᚱᚲ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("georgian",     fals)) {layouts.add(new CustomKeyboard(this, R.layout.georgian,    "georgian",    "Georgian",   "აბგდევ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("glagolitic",   fals)) {layouts.add(new CustomKeyboard(this, R.layout.glagolitic,  "glagolitic",  "Glagolitic", "ⰀⰁⰂⰃⰄⰅ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("gothic",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.gothic,      "gothic",      "Gothic",     "𐌵𐍈𐌴𐍂𐍄𐍅").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("greek",        fals)) {layouts.add(new CustomKeyboard(this, R.layout.greek,       "greek",       "Greek",      "ςερτυθ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("hex",          fals)) {layouts.add(new CustomKeyboard(this, R.layout.hex,         "hex",         "Hex",        "\\uabcd").setCategory(Category.Util));}
        if (sharedPreferences.getBoolean("hiragana",     fals)) {layouts.add(new CustomKeyboard(this, R.layout.hiragana,    "hiragana",    "Hiragana",   "あいうえお").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("katakana",     fals)) {layouts.add(new CustomKeyboard(this, R.layout.katakana,    "katakana",    "Katakana",   "アイウエオ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("insular",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.insular,     "insular",     "Insular",    "ꝺꝼᵹꞃꞅꞇ").setCategory(Category.Font));}
        if (sharedPreferences.getBoolean("ipa",          fals)) {layouts.add(new CustomKeyboard(this, R.layout.ipa,         "ipa",         "IPA",        "ʔʕʘǁǂ").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("lisu",         fals)) {layouts.add(new CustomKeyboard(this, R.layout.lisu,        "lisu",        "Lisu",       "ⵚꓟꓱꓤꓕ⅄").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("macros",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.macros,      "macros",      "Macros",     "✐").setCategory(Category.Util).setOrder(-4));}
        if (sharedPreferences.getBoolean("math",         fals)) {layouts.add(new CustomKeyboard(this, R.layout.math,        "math",        "Math",       "+−×÷=%").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("mirror",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.mirror,      "mirror",      "Mirror",     "qwerty").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("ogham",        fals)) {layouts.add(new CustomKeyboard(this, R.layout.ogham,       "ogham",       "Ogham",      "᚛ᚁᚆᚋᚐ᚜").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("navigation",   fals)) {layouts.add(new CustomKeyboard(this, R.layout.navigation,  "navigation",  "Navigation", "  →←↑↓").setCategory(Category.Util).setOrder(-1));}
                                                                 layouts.add(new CustomKeyboard(this, R.layout.numeric,     "numeric",     "Numeric",    "123456").setCategory(Category.Main));
        if (sharedPreferences.getBoolean("pinyin",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.pinyin,      "pinyin",      "Pinyin",     "").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("pointy",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.pointy,      "pointy",      "Pointy",     "ᛩꟽⵉᚱⵜY").setCategory(Category.Font));}
        if (sharedPreferences.getBoolean("qwerty",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.qwerty,      "qwerty",      "Qwerty",     "qwerty").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("rotated",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.rotated,     "rotated",     "Rotated",    "ʎʇɹəʍb").setCategory(Category.Font));}
        if (sharedPreferences.getBoolean("shift_1",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.shift_1,     "shift_1",     "Shift₁",     "qWeRtY").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("shift_2",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.shift_2,     "shift_2",     "Shift₂",     "QwErTy").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("stealth",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.stealth,     "stealth",     "Stealth",    "ԛԝеrtу").setCategory(Category.Font));}
        if (sharedPreferences.getBoolean("strike",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.strike,      "strike",      "Strike",     "ꝗwɇꞧⱦɏ").setCategory(Category.Font));}
        if (sharedPreferences.getBoolean("symbol",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.symbol,      "symbol",      "Symbol",     "!@#$%^").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("tails",        fals)) {layouts.add(new CustomKeyboard(this, R.layout.tails,       "tails",       "Tails",      "ɋꝡҽɽʈƴ").setCategory(Category.Font));}
        if (sharedPreferences.getBoolean("tifinagh",     fals)) {layouts.add(new CustomKeyboard(this, R.layout.tifinagh,    "tifinagh",    "Tifinagh",   "ⴰⴱⴳⴷⴹⴻ").setCategory(Category.Lang));}
        if (sharedPreferences.getBoolean("unicode",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.unicode,     "unicode",     "Unicode",    "\\uxxxx").setCategory(Category.Util));}
                                                                 layouts.add(new CustomKeyboard(this, R.layout.url,         "url",         "URL",        "@").setCategory(Category.Main));
        if (sharedPreferences.getBoolean("utility",      fals)) {layouts.add(new CustomKeyboard(this, R.layout.utility,     "utility",     "Utility",    "/**/").setCategory(Category.Util).setOrder(-3));}
        if (sharedPreferences.getBoolean("zhuyin",       fals)) {layouts.add(new CustomKeyboard(this, R.layout.zhuyin,      "zhuyin",      "Zhuyin",     "ㄅㄆㄇㄈ").setCategory(Category.Lang));}

        int layoutLayout = R.layout.layouts;
        
        Collections.sort(layouts);
        for (int i = 0; i < layouts.size(); i++) {
            if (layouts.get(i).order == 1024) {
                layouts.get(i).order = i;
            }
        }

        Collections.sort(layouts);
        layouts.add(new CustomKeyboard(this, layoutLayout, "Layouts").setOrder(-1));

        if (getKeyboard("Layouts") != null) {
            for (Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                if (key.codes[0] <= -400 && key.codes[0] >= -449) {
                    try {
                        CustomKeyboard layout = layouts.get(-key.codes[0] - 400);
                        if (layout != null && !layout.title.equals("Layouts")) {
                            if (sharedPreferences.getBoolean("names", true)) {
                                key.label = layout.title;
                            }
                            else key.label = layout.label;
                        }
                        else key.label = "";
                    }
                    catch (Exception e) {
                        key.label = "";
                    }
                }
            }
        }

        if (sharedPreferences.getBoolean("relayout", true)) {
            int layoutCount = Math.min(layouts.size()-2, 47);
            
            int colCount = 6;
            int startRowCount = 8;
            int finalRowCount = (int)Math.ceil(layoutCount / colCount) + 1;

            List<Keyboard.Key> layoutKeys = new ArrayList<>();
            for(Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                if (key.codes[0] <= -400 && key.codes[0] >= -449) {
                    layoutKeys.add(key);
                }
            }
            Bounds bounds = getBounds(layoutKeys);
            int rowHeight = layoutKeys.get(0).height;
            int areaHeight = bounds.dY;
            int usedHeight = rowHeight * (finalRowCount + 1) - bounds.minY;
            int freeHeight = areaHeight - usedHeight;
            int moveBy = (int)Math.ceil(freeHeight / finalRowCount);
            int row, index = 0;

            for (Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                if (key.codes[0] <= -400 && key.codes[0] >= -449) {
                    row = (index / colCount);
                    if (row >= (startRowCount-(startRowCount-finalRowCount))) {
                        key.y = bounds.maxY;
                        key.height = 0;
                    }
                    else {
                        key.y += (moveBy * row);
                        key.height += moveBy;
                    }
                    index++;
                }
            }
            try {
                layoutCount = layouts.size()-1;
                int layoutMod = (layoutCount % 6);
                if (layoutMod > 0) {
                    int hi = -400 - layoutCount; // -447
                    int lo = hi + layoutMod; // -442
                    hi = lo - 6;

                    layoutKeys = new ArrayList<>();
                    for(Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                        if (key.codes[0] <= lo && key.codes[0] >= hi) {
                            layoutKeys.add(key);
                        }
                    }
                    bounds = getBounds(layoutKeys);
                    int keyWidth = layoutKeys.get(0).width;
                    int rowWidth = bounds.dX;

                    int usedWidth = keyWidth * layoutMod;
                    int freeWidth = rowWidth - usedWidth;

                    moveBy = (int)Math.ceil(freeWidth / layoutMod) + (keyWidth / layoutMod);
                    index = 0;

                    for(Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                        if (key.codes[0] <= lo && key.codes[0] >= hi) {
                            if (index >= layoutMod) {
                                key.x = bounds.maxX;
                                key.width = 0;
                                key.label = "";
                            }
                            else {
                                key.x += (moveBy * index);
                                key.width += moveBy;
                            }
                            index++;
                        }
                    }
                }
            }
            catch (Exception ignored) {}
            
            try {redraw();}
            catch (Exception ignored) {}
        }

        StringBuilder autoLabel;
        for(CustomKeyboard layout : layouts) {
            autoLabel = new StringBuilder();
            for(Keyboard.Key key : layout.getKeys()) {
                if (key.label == null) continue;
                if (key.label == "") continue;
                if (key.label.length() > 1) continue;
                if (Util.isNumeric(String.valueOf(key.label))) continue;
                if (",\";".contains(String.valueOf(key.label))) continue;
                autoLabel.append(key.label);
                if (autoLabel.length() > 2) break;
            }
            String label = autoLabel.toString().trim();
            if (layout.label == null || layout.label.equals("")) {
                layout.label = label;
            }
        }
    }

    public Bounds getBounds(List<Keyboard.Key> keys) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = 0, maxY = 0;
        for (Keyboard.Key key : keys) {
            if (key.x < minX) minX = key.x;
            if (key.y < minY) minY = key.y;

            if (key.x+key.width  > maxX) maxX = key.x;
            if (key.y+key.height > maxY) maxY = key.y;
        }
        return new Bounds(minX, minY, maxX, maxY);
    }
    
    static void print(Object ...a) {
        for (Object i: a) System.out.print(i + " ");
        System.out.println();
    }
    
    // @todo instead of cancelling, concat contents
    public void toastIt(String ...args) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (sharedPreferences.getBoolean("debug", false)) return;
        String text;
        if (args.length > 1) {
            StringBuilder result = new StringBuilder();
            for(String n: args){
                result.append(n).append(" ");
            }
            text = result.toString().trim();
        }
        else {
            text = args[0];
        }
        toast.cancel();
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
    
    public void toastIt(int num) {
        String text = String.valueOf(num);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (sharedPreferences.getBoolean("debug", false)) return;
        toast.cancel();
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
    
    public void crispIt(String text) {
        // sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        // if (sharedPreferences.getBoolean("debug", false)) return;
        toast.cancel();
        toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    public int clipboardHistoryAdd(String str) {
        clipboardHistory.add(str);
        return clipboardHistory.size();
    }
    public String clipboardHistoryGet() {
        if (!clipboardHistory.isEmpty()) {
            return clipboardHistory.remove(0);
        }
        return "";
    }
    public Boolean clipboardHistoryHas() {
        return clipboardHistory.size() > 0;
    }

    public void performContextMenuAction(int id) {
        InputConnection ic = getCurrentInputConnection();
        ic.performContextMenuAction(id);
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        Intent explicitIntent = new Intent(implicitIntent);

        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    public void showSettings() {
        try {
            Intent intent = new Intent("com.custom.keyboard.Preference");
            this.startActivity(intent);
        }
        catch (Exception e) {
            toastIt("Couldn't launch settings.\n"+e.toString());
        }
    }

    public void showVoiceInput() {
        try {
            this.switchToNextInputMethod(false);
            /*
            InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            if (imeManager != null) {
                imeManager.showInputMethodPicker();
                return true;
            }
            imeManager.switchInputMethod("com.google.android.voicesearch.ime.VoiceInputMethodService");
            */
        }
        catch(Exception e) {
            toastIt("Couldn't launch voice input.\n"+e.toString());
        }
    }

    public ArrayList<CustomKeyboard> getLayouts() {
        return layouts;
    }

    public CustomKeyboard getKeyboard(String name) {
        int index = 0;
        for(CustomKeyboard layout : layouts) {
            if (layout.title.equals(name)) break;
            index++;
        }
        try {
            return layouts.get(index);
        }
        catch (Exception e) {
            return layouts.get(0);
        }
    }

    public String findKeyboard(String name) {
        populate();
        int index = 0;
        for(CustomKeyboard layout : layouts) {
            if (layout.title.equals(name)) {
                currentKeyboardID = index;
                break;
            }
            index++;
        }
        setKeyboard();
        return name;
    }

    public String setKeyboardLayout(int newKeyboardID) {
        boolean capsOn = Variables.isShift();
        try {
            if (newKeyboardID < layouts.size()) {
                currentKeyboardID = newKeyboardID;
                currentKeyboard = layouts.get(currentKeyboardID);
                layouts.get(currentKeyboardID).setOrder(1);
                if (sharedPreferences.getBoolean("respace", false)) {
                    String currentKeyboardLabel = currentKeyboard.label;
                    if (currentKeyboard.title.equals("Layouts")) {
                        if (layouts.size()-1 != 1) getKey(32).label = (layouts.size()-1)+" layouts";
                        else getKey(32).label = "1 layout";
                    }
                    else if (!currentKeyboard.title.equals("Shift")) {
                        if (kv.isShifted()) currentKeyboardLabel = currentKeyboardLabel.toUpperCase();
                        else currentKeyboardLabel = currentKeyboardLabel.toLowerCase();
                        getKey(32).label = currentKeyboard.title +"\t•\t"+currentKeyboardLabel; // ·
                    }
                }
                kv.setKeyboard(currentKeyboard);
                kv.getCustomKeyboard().changeKeyHeight(getHeightKeyModifier());
                redraw();
            }
        }
        catch (Exception ignored) {}
        return currentKeyboard.title;
    }

    public void setKeyboard() {
        if (currentKeyboardID >= layouts.size()-1) currentKeyboardID = 0;
        setKeyboardLayout(currentKeyboardID);
    }

    public void prevKeyboard() {
        populate();
        currentKeyboardID--;
        if (currentKeyboardID < 0) currentKeyboardID = layouts.size()-2;
        setKeyboard();
    }

    public void nextKeyboard() {
        populate();
        currentKeyboardID++;
        if (currentKeyboardID > layouts.size()-2) currentKeyboardID = 0;
        setKeyboard();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        mWordSeparators = getResources().getString(R.string.word_separators);
        final TextServicesManager tsm = (TextServicesManager)getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        toast = new Toast(getBaseContext());
        populate();
    }

    @Override
    public void onInitializeInterface() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        populate();
        if (getKeyboard("Default") != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateInputView() {
        populate();
        mInputView = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.qwerty, null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setPreviewEnabled(sharedPreferences.getBoolean("preview", false));
        return mInputView;
    }

    public Keyboard.Key getKey(int primaryCode) {
        if (currentKeyboard == null) return null;
        for(Keyboard.Key key : currentKeyboard.getKeys()) {
            if (key.codes[0] == primaryCode) return key;
        }
        return null;
    }

    @SuppressLint("InflateParams")
    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        // setTheme();
        mDefaultFilter = Themes.sPositiveColorArray;
        mComposing.setLength(0);
        mCompletions = null;
        
        if (sharedPreferences.getBoolean("preview", false)) {
            kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard_with_previews, null);
        }
        else {
            kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        }
        

        setInputType();
        Paint mPaint = new Paint();
        ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(mDefaultFilter);
        mPaint.setColorFilter(filterInvert);

        mCandidateView = new CustomView(this);
        mCandidateView.setService(this);
        kv.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);

        currentKeyboard.setRowNumber(getRowNumber());
        kv.setKeyboard(currentKeyboard);

        capsOnFirst();

        kv.setOnKeyboardActionListener(this);

        mPredictionOn = sharedPreferences.getBoolean("pred", false);
        mCompletionOn = false;

        setInputView(kv);

        kv.getCustomKeyboard().changeKeyHeight(getHeightKeyModifier());

        setCandidatesView(mCandidateView);

        populate();

        setKeyboard();
        /*
        String text = ic.getTextBeforeCursor(MAX, 0).toString()
                    + ic.getTextAfterCursor(MAX, 0).toString()
        String[] tokens = Util.getWords(text);
        */

        /*
        String generated = "ㅂㅃ ㅈㅉ ㄷㄸ ㄱㄲ ㅅㅆ ㅛ ㅕ ㅑ ㅐㅒ ㅔㅖ _ ㅁ ㄴ ㅇ ㄹ ㅎ ㅗ ㅓ ㅏ ㅣ _ ㅋ ㅌ ㅊ ㅍ ㅠ ㅜ ㅡ";
        if (generated != null && !generated.equals("")) {
            System.out.println(Generator.toKeyboard(generated));
        }
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        */
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();
        mComposing.setLength(0);
        setCandidatesViewShown(false);
        Variables.setSelectOff();
        if (mInputView != null) mInputView.closing();
    }

    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
        if (mComposing.length() > 0 && (newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
            mComposing.setLength(0);
            ic = getCurrentInputConnection();
            if (ic != null) ic.finishComposingText();
        }
    }

    @Override
    public void swipeLeft() {
        nextKeyboard();
    }

    @Override
    public void swipeRight() {
        prevKeyboard();
    }

    @Override
    public void swipeDown() {
        hide();
    }

    @Override
    public void swipeUp() {
        setKeyboardLayout(layouts.size()-1);
    }

    public void hide() {
        requestHideSelf(0);
    }

    public void commitTyped(InputConnection ic) {
        if (mComposing.length() > 0) {
            commitText(String.valueOf(mComposing), mComposing.length());
            mComposing.setLength(0);
        }
    }

    public void onText(CharSequence text) {
        ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.beginBatchEdit();
        if (mComposing.length() > 0) commitTyped(ic);
        commitText((String)text);
        ic.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void processKeyCombo(int keycode) {
        ic = getCurrentInputConnection();
        if (Variables.isCtrl() || Variables.isAlt()) {
            if (Variables.isCtrl() && Variables.isAlt()) {
                ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyCodes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   KeyCodes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            }
            else {
                if (Variables.isCtrl()) {
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyCodes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   KeyCodes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                }
                if (Variables.isAlt()) {
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyCodes.getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   KeyCodes.getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                }
            }
        }
    }

    public void setInputType() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        EditorInfo attribute = getCurrentInputEditorInfo();
        switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_TEXT_VARIATION_URI:
            case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
            case InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS:
                findKeyboard("URL");
            break;
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_DATETIME:
            case InputType.TYPE_CLASS_PHONE:
                findKeyboard("Numeric");
            break;
            default:
                findKeyboard("Default");
            break;
        }
    }

    public void performReplace(String newText) {
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0) {
            int a = getSelectionStart();
            int b = getSelectionStart()+newText.length();
            commitText(newText);
            ic.setSelection(a, b);
        }
    }

    public String getText(InputConnection ic) {
        CharSequence text = ic.getSelectedText(0);
        if (text == null) return "";
        return (String)text;
    }
    
    public String getAllText(InputConnection ic) {
        return ic.getTextBeforeCursor(MAX, 0).toString()
             + ic.getTextAfterCursor(MAX, 0).toString();
    }

    public void sendKey(int primaryCode) {
        ic = getCurrentInputConnection();
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, primaryCode));
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   primaryCode));
    }

    public void sendKey(int primaryCode, int times) {
        ic = getCurrentInputConnection();
        while (times --> 0) {
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, primaryCode));
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   primaryCode));
        }
    }

    public void sendKeys(int[] keys) {
        ic = getCurrentInputConnection();
        for (int key : keys) {
            sendKey(key);
        }
    }

    public void sendCustomKey(String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext()); // this?
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        int cursorLocation = getSelectionStart();
        String ins = sharedPreferences.getString(key, "");
        ic.beginBatchEdit();
        commitText(ins, cursorLocation + (ins != null ? ins.length() : 0));
        ic.endBatchEdit();
    }

    public String prevLine() {
        ic = getCurrentInputConnection();
        String[] lines = ic.getTextBeforeCursor(MAX, 0).toString().split("\n");
        if (lines.length < 2) return ic.getTextBeforeCursor(MAX, 0).toString();
        return lines[lines.length - 1];
    }

    public String nextLine() {
        ic = getCurrentInputConnection();
        String[] lines = ic.getTextAfterCursor(MAX, 0).toString().split("\n");
        if (lines.length < 2) return ic.getTextAfterCursor(MAX, 0).toString();
        return lines[0];
    }

    public void prevWord(int n) {
        try {
            ic = getCurrentInputConnection();
            for(int i = 0; i < n; i++) {
                String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
                String lastWord = words[words.length - 1];
                int position = getSelectionStart() - lastWord.length() - 1;
                if (position < 0) position = 0;
                if (Variables.isSelect()) ic.setSelection(position, Variables.cursorStart);
                else ic.setSelection(position, position);
            }
        } 
        catch (Exception ignored) {}
    }

    public void nextWord(int n) {
        try {
            ic = getCurrentInputConnection();
            for(int i = 0; i < n; i++) {
                String[] words = ic.getTextAfterCursor(MAX, 0).toString().split(" ");
                String nextWord = words[0];
                if (words.length > 1) nextWord = words[1];
                int position = getSelectionStart() + nextWord.length() + 1;
                if (Variables.isSelect()) ic.setSelection(Variables.cursorStart, position);
                else ic.setSelection(position, position);
            }
        } 
        catch (Exception ignored) {}
    }
    
    public void goToStart() {
        ic = getCurrentInputConnection();
        ic.setSelection(0, 0);
    }
    public void goToEnd() {
        ic = getCurrentInputConnection();
        ic.setSelection(
            (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length(),
            (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length()
        );
    }
    public void selectAll() {
        ic = getCurrentInputConnection();
        ic.setSelection(0, (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length());
    }

    public void selectNone() {
        ic = getCurrentInputConnection();
        try {
            int end = getSelectionEnd();
            ic.setSelection(end, end);
            Variables.setSelectOff();
        }
        catch (Exception ignored) {}
    }
    
    public void selectLine() {
        sendKey(KeyEvent.KEYCODE_MOVE_HOME);
        Variables.setSelectOn(getSelectionStart());
        navigate(KeyEvent.KEYCODE_MOVE_END);
        Variables.setSelectOff();
    }
    
    public Boolean isSelecting() {
        return getSelectionStart() != getSelectionEnd();    
    }
    
    public void clearAll() {
        ic = getCurrentInputConnection();
        ic.deleteSurroundingText(MAX, MAX);
    }

    public int getCursorPosition() {
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.startOffset + extracted.selectionStart;
    }

    public int getSelectionStart() {
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.selectionStart;
    }

    public int getSelectionEnd() {
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.selectionEnd;
    }

    public int getSelectionLength() {
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.selectionEnd-extracted.selectionStart;
    }

    public int getStartOffset() {
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.startOffset;
    }

    public void navigate(int primaryCode) {
        ic = getCurrentInputConnection();
        if (!isSelecting() && primaryCode == KeyEvent.KEYCODE_DPAD_LEFT && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals("    ")) {
            sendKey(primaryCode, 4);
        }
        else if (!isSelecting() && primaryCode == KeyEvent.KEYCODE_DPAD_RIGHT && String.valueOf(ic.getTextAfterCursor(4, 0)).equals("    ")) {
            sendKey(primaryCode, 4);
        }
        else {
            if (Variables.isSelect()) ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT));
            sendKey(primaryCode);
            if (Variables.isSelect()) ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SHIFT_LEFT));
        }
    }

    public String getClipboardEntry(int n) {
        try {
            ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = clipboardManager.getPrimaryClip();
            ClipData.Item item = null;
            if (clip != null) item = clip.getItemAt(n);
            CharSequence text = null;
            if (item != null) text = item.getText();
            if (text != null) return text.toString();
        }
        catch (Exception e) {return "";}
        return "";
    }

    public void setCapsOn(boolean on) {
        if (Variables.isShift()) {
            kv.getKeyboard().setShifted(true);
            redraw();
        }
        else {
            kv.getKeyboard().setShifted(on);
            redraw();
        }
    }

    public void capsOnFirst() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ic = getCurrentInputConnection();
        if (sharedPreferences.getBoolean("autocaps", false)) {
            if (getCursorCapsMode(ic, getCurrentInputEditorInfo()) != 0) {
                firstCaps = true;
                setCapsOn(true);
            }
        }
        else {
            firstCaps = false;
            setCapsOn(false);
        }
    }

    public int getCursorCapsMode(InputConnection ic, EditorInfo attr) {
        int caps = 0;
        EditorInfo ei = getCurrentInputEditorInfo();
        if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) caps = ic.getCursorCapsMode(attr.inputType);
        return caps;
    }

    public void updateShiftKeyState(EditorInfo attr) {
        ic = getCurrentInputConnection();
        if (attr != null && mInputView != null && layouts.get(0) == mInputView.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != InputType.TYPE_NULL) caps = ic.getCursorCapsMode(attr.inputType);
            mInputView.setShifted(mCapsLock || caps != 0);
        }
    }

    public void setTheme() {
        Context context = getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            String theme = sharedPreferences.getString("theme", "1");
            if (theme != null) {
                if (theme.equals("0")) theme = String.valueOf(Util.generateRandomInt(1, 22));
                switch (theme) {
                    case  "1": mDefaultFilter = Themes.sPositiveColorArray;     break;
                    case  "2": mDefaultFilter = Themes.sNegativeColorArray;     break;
                    case  "3": mDefaultFilter = Themes.sBlueWhiteColorArray;    break;
                    case  "4": mDefaultFilter = Themes.sBlueBlackColorArray;    break;
                    case  "5": mDefaultFilter = Themes.sGreenWhiteColorArray;   break;
                    case  "6": mDefaultFilter = Themes.sGreenBlackColorArray;   break;
                    case  "7": mDefaultFilter = Themes.sRedWhiteColorArray;     break;
                    case  "8": mDefaultFilter = Themes.sRedBlackColorArray;     break;
                    case  "9": mDefaultFilter = Themes.sCyanWhiteColorArray;    break;
                    case "10": mDefaultFilter = Themes.sCyanBlackColorArray;    break;
                    case "11": mDefaultFilter = Themes.sMagentaWhiteColorArray; break;
                    case "12": mDefaultFilter = Themes.sMagentaBlackColorArray; break;
                    case "13": mDefaultFilter = Themes.sYellowWhiteColorArray;  break;
                    case "14": mDefaultFilter = Themes.sYellowBlackColorArray;  break;
                    case "15": mDefaultFilter = Themes.sPurpleWhiteColorArray;  break;
                    case "16": mDefaultFilter = Themes.sPurpleBlackColorArray;  break;
                    case "17": mDefaultFilter = Themes.sPinkWhiteColorArray;    break;
                    case "18": mDefaultFilter = Themes.sPinkBlackColorArray;    break;
                    case "19": mDefaultFilter = Themes.sOrangeWhiteColorArray;  break;
                    case "20": mDefaultFilter = Themes.sOrangeBlackColorArray;  break;
                    case "21": mDefaultFilter = Themes.sMaterialLiteColorArray; break;
                    case "22": mDefaultFilter = Themes.sMaterialDarkColorArray; break;
                }
                Editor editor = sharedPreferences.edit(); 
                editor.putString("bg", Util.toColor((int)mDefaultFilter[4], (int)mDefaultFilter[9], (int)mDefaultFilter[14])); 
                editor.putString("fg", Integer.parseInt(theme) % 2 == 0 ? "#000000" : "#FFFFFF"); 
                editor.apply();
            }
        }
        catch (Exception ignored) {}
    }

    public void handleBackspace() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ic = getCurrentInputConnection();
        final int length = mComposing.length();
        
        String prev;
        try {
            prev = String.valueOf(ic.getTextBeforeCursor(1, 0).length() > 0 ? ic.getTextBeforeCursor(1, 0) : "");
        }
        catch (Exception e) {
            prev = "";
        }
        
        try {
            if (!isSelecting() && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals("    ") && sharedPreferences.getBoolean("spaces", true)) {
                ic.deleteSurroundingText(4, 0);
            }
            else if (sharedPreferences.getBoolean("pairs", true) && Util.contains(")}\"]", String.valueOf(ic.getTextAfterCursor(1, 0))) && String.valueOf(ic.getTextBeforeCursor(1, 0)).equals(String.valueOf(ic.getTextAfterCursor(1, 0)))) {
                ic.deleteSurroundingText(0, 1);
            }
            if (length > 1) {
                mComposing.delete(length - 1, length);
                ic.setComposingText(mComposing, 1);
            }
            else if (length > 0) {
                mComposing.setLength(0);
                commitText("");
            }
            else {
                sendKey(KeyEvent.KEYCODE_DEL);
            }
            if (Character.isUpperCase(prev.charAt(0))) {
                setCapsOn(true);
                firstCaps = true;
            }
            else {
                setCapsOn(false);
                firstCaps = false;
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
        catch (Exception ignored) {}
    }

    public void handleDelete() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ic = getCurrentInputConnection();
        final int length = mComposing.length();
        if (!isSelecting() && String.valueOf(ic.getTextAfterCursor(4, 0)).equals("    ") && sharedPreferences.getBoolean("spaces", true)) {
            ic.deleteSurroundingText(0, 4);
        }
        else if (length > 1) {
            mComposing.delete(length, length - 1);
            ic.setComposingText(mComposing, 1);
        }
        else if (length > 0) {
            mComposing.setLength(0);
            commitText("");
        }
        else sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    private Map<String, String> replacementData = Edit.getReplacements();

    public void handleCharacter(int primaryCode) {
        ic = getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        primaryCode = KeyCodes.handleCharacter(kv, primaryCode);
        if (sharedPreferences.getBoolean("show_data", false) && !currentKeyboard.title.equals("Pinyin")) {
            if (sharedPreferences.getBoolean("show_ascii_data", false) || primaryCode > 127) {
                crispIt(Util.unidata(primaryCode));
            }
        }
        if (sharedPreferences.getBoolean("pairs", true)) {
        
            if (Util.contains("({\"[", primaryCode)) {
                
                String code = String.valueOf((char)primaryCode);
                if (code.equals("("))  commitText("()");
                if (code.equals("["))  commitText("[]");
                if (code.equals("{"))  commitText("{}");
                if (code.equals("\"")) commitText("\"\"");
                if (Variables.isReflected() || sharedPreferences.getBoolean("cursor_left", false) || currentKeyboard.title.equals("Rotated") || currentKeyboard.title.equals("Lisu")) {
                    sendKey(KeyEvent.KEYCODE_DPAD_RIGHT);
                }
                else {
                    sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
                }
                return;
            }
        }

        if (sharedPreferences.getBoolean("auto", true)) {
            System.out.println("auto"+" "+primaryCode);
            if (!Util.isAlphabet(primaryCode) || primaryCode == 32 || primaryCode == 10) {
                String lastWord = " "+getLastWord();
                System.out.println(lastWord);
                String replacement = replacementData.get(lastWord);
                System.out.println(replacement);
                if (replacement != null) {
                    ic.deleteSurroundingText(lastWord.length(), 0);
                    commitText(replacement);
                }
            }
        }

        if (isInputViewShown()) {
            if (kv.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
        }
        if (mPredictionOn && !Util.isWordSeparator(primaryCode)) {
            mComposing.append((char)primaryCode);
            ic.setComposingText(mComposing, 1);
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
        if (mPredictionOn && Util.isWordSeparator(primaryCode)) {
            char code = (char)primaryCode;
            if (Character.isLetter(code) && firstCaps || Character.isLetter(code) && Variables.isShift()) {
                code = Character.toUpperCase(code);
            }
            ic.setComposingRegion(0, 0);
            commitText(String.valueOf(code), 1);
            firstCaps = false;
            setCapsOn(false);
        }
        if (!mPredictionOn) {
            ic.setComposingRegion(0, 0);
            commitText(String.valueOf(Character.toChars(primaryCode)), 1);
            firstCaps = false;
            setCapsOn(false);
        }
        if (isSelecting()) ic.setSelection(Variables.cursorStart, getSelectionEnd());
    }


    public String getLastWord() {
        ic = getCurrentInputConnection();
        String prev = String.valueOf(ic.getTextBeforeCursor(MAX, 0));
        prev = prev.trim();
        String[] words = prev.split(" ");
        if (words.length < 2) return ic.getTextBeforeCursor(MAX, 0).toString();
        return words.length > 1 ? words[words.length-1] : words[0];
    }

    public String getLastMorse() {
        ic = getCurrentInputConnection();
        return ic.getTextBeforeCursor(8, 0).toString().replaceAll("[^·-]", "");
    }
    
    public void setShifted(boolean capsOn) {
        kv.setShifted(capsOn);
        firstCaps = capsOn;
        setCapsOn(capsOn);
        redraw();
    }

    public void redraw() {
        kv.invalidateAllKeys();
        kv.draw(new Canvas());
    }

    public void commitText(String text) {
        ic.beginBatchEdit();
        ic.commitText(text, 0);
        ic.endBatchEdit();
    }
    public void commitText(String text, int offset) {
        ic.beginBatchEdit();
        ic.commitText(text, offset);
        ic.endBatchEdit();
    }
    
    @Override
    public boolean onKeyLongPress(int primaryCode, KeyEvent event) {
        return false;
    }

    long time;
    
    @Override
    public void onPress(int primaryCode) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        time = System.nanoTime();
        if (sharedPreferences.getBoolean("vib", true)) {
            Vibrator v = (Vibrator)getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);

            v.vibrate(5);
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        ic = getCurrentInputConnection();
        time = (System.nanoTime() - time) / 1000000;
        if (time > 300) {
            switch (primaryCode) {
                case -93: selectAll(); break;
                case -99: ic.deleteSurroundingText(MAX, MAX); break;
                case -174:
                case -2003:
                    commitText(Util.unidata(getText(ic)));
                break;
            }
        }
    }
    
    private Map<String,String> pinyin = Pinyin.getCharMap();
    private Map<String,String> pinyinSlugs = Pinyin.getSlugMap();
    public String pinyinSearch(String text) {
        String ch = pinyin.get(text);
        if (ch == null) {
            ch = pinyinSlugs.get(Util.normalize(text));
        }
        if (ch == null || ch.trim() == null || ch.trim().equals("")) {
            return "";
        }
        return ch;
    }

    public Boolean pinyinHandle(int primaryCode) {
        try {
            String prev = String.valueOf(ic.getTextBeforeCursor(8, 0))+(char)primaryCode;
            prev = prev.trim();
            String[] lcs = prev.split(" ");
            String lw = lcs.length > 1 ? lcs[lcs.length-1] : lcs[0];
            String ch = pinyinSearch(lw);
            String[] choices = ch.split("(?!^)");
            return false;
            /*
            if (choices != null && ch.length() > 0) {
                ic.deleteSurroundingText(lw.length()-1, 0);
                commitText(choices[0]);
                return true;
            }

            if (primaryCode == 32 && getKey(32).label != "" && getKey(32).label != null) {
                // ic.deleteSurroundingText(lw.length(), 0);
                commitText(String.valueOf(getKey(32).label));
                getKey(32).icon = getResources().getDrawable(R.drawable.ic_space, getBaseContext().getTheme());
                getKey(32).label = null;
                getKey(32).popupCharacters = " ";
                return true;
            }

            if (ch != null) {
                String[] choices = ch.split("");
                if (choices != null & choices.length > 0) {
                    getKey(32).label = choices[0];
                    getKey(32).popupCharacters = ch;
                }
            }
            else {
                getKey(32).icon = getResources().getDrawable(R.drawable.ic_space, getBaseContext().getTheme());
                getKey(32).label = null;
                getKey(32).popupCharacters = " ";
            }
            */
        } 
        catch (Exception e) {
            crispIt(e.toString());
        }
        return false;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String record, currentKeyboardName = currentKeyboard.title;
        boolean capsOn = Variables.isShift();
        if (currentKeyboardName.equals("Pinyin")) {
            if (pinyinHandle(primaryCode)) {
                return;
            }
        }
        if (currentKeyboardName.equals("Enmorse") && !Morse.fromChar(String.valueOf((char)primaryCode)).equals("")) {
            String res = Morse.fromChar(String.valueOf((char)primaryCode));
            if (kv.isShifted()) res = res.toUpperCase();
            toastIt((char)primaryCode+" "+res);
            commitText(res+" ");
            return;
        }
        if (currentKeyboardName.equals("Enmorse") && primaryCode == 32) {
            commitText("  ");
            return;
        }
        if (currentKeyboardName.equals("Demorse") && "·- ".contains(String.valueOf((char)primaryCode))) {
            if (primaryCode == 32) {
                String res = getLastMorse();
                if (kv.isShifted()) res = res.toUpperCase();
                ic.deleteSurroundingText(res.length(), 0);
                commitText(Morse.toChar(res)+"");
                getKey(32).label = " ";
                redraw();
                return;
            }
            String res = String.valueOf((char)primaryCode);
            if (kv.isShifted()) res = res.toUpperCase();
            commitText(res);
            getKey(32).label = getLastMorse() + " " + Morse.toChar(getLastMorse());
            redraw();
            return;
        }
        if (currentKeyboard.title.equals("Unicode") && !Util.contains(KeyCodes.hexPasses, primaryCode)) {
            if (primaryCode == -2001) performReplace(Util.convertFromUnicodeToNumber(getText(ic)));
            if (primaryCode == -2002) performReplace(Util.convertFromNumberToUnicode(getText(ic)));
            if (Util.contains(KeyCodes.hexCaptures, primaryCode)) {
                if (hexBuffer.length() > 3) hexBuffer = "";
                hexBuffer += (char)primaryCode;
            }
            if (primaryCode == -2003) commitText(StringUtils.leftPad(hexBuffer, 4, "0"));

            if (primaryCode == -2004) commitText(String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(hexBuffer, 4, "0"))));
            if (primaryCode == -2005) {
                if (hexBuffer.length() > 0) hexBuffer = hexBuffer.substring(0, hexBuffer.length() - 1);
                else hexBuffer = "0000";
            }
            if (primaryCode == -2006) hexBuffer = "0000";
            getKey(-2003).label = hexBuffer.equals("0000") ? "" : StringUtils.leftPad(hexBuffer, 4, "0");
            getKey(-2004).label = String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(hexBuffer, 4, "0")));
            redraw();
            return;
        }
        switch (primaryCode) {
            case 142: sendKey(KeyEvent.KEYCODE_F12); break;
            case 141: sendKey(KeyEvent.KEYCODE_F11); break;
            case 140: sendKey(KeyEvent.KEYCODE_F10); break;
            case 139: sendKey(KeyEvent.KEYCODE_F9); break;
            case 138: sendKey(KeyEvent.KEYCODE_F8); break;
            case 137: sendKey(KeyEvent.KEYCODE_F7); break;
            case 136: sendKey(KeyEvent.KEYCODE_F6); break;
            case 135: sendKey(KeyEvent.KEYCODE_F5); break;
            case 134: sendKey(KeyEvent.KEYCODE_F4); break;
            case 133: sendKey(KeyEvent.KEYCODE_F3); break;
            case 132: sendKey(KeyEvent.KEYCODE_F2); break;
            case 131: sendKey(KeyEvent.KEYCODE_F1); break;
            case 10:
                EditorInfo curEditor = getCurrentInputEditorInfo();
                // if (kv.getKeyboard().isShifted()) {commitText("\n", 1);}
                if (sharedPreferences.getBoolean("spaces", true)) {
                    String indent = Util.getIndentation(prevLine());
                    if (indent.length() > 0) {commitText("\n"+indent); break;}
                }
                switch (curEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
                    case EditorInfo.IME_ACTION_GO:     ic.performEditorAction(EditorInfo.IME_ACTION_GO);     break;
                    case EditorInfo.IME_ACTION_SEARCH: ic.performEditorAction(EditorInfo.IME_ACTION_SEARCH); break;
                    default: sendKey(KeyEvent.KEYCODE_ENTER); break;
                }
            break;
            case 7:
                if (sharedPreferences.getBoolean("spaces", true)) {
                    commitText("    ");
                    if (isSelecting()) {
                        ic.setSelection(getSelectionStart(), getSelectionEnd() + 4);
                    }
                }
                else {
                    commitText("	");
                    if (isSelecting()) {
                        ic.setSelection(getSelectionStart(), getSelectionEnd() + 1);
                    }
                }
            break;
            case -1:
                if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0 && PreferenceManager.getDefaultSharedPreferences(this).getBoolean("shift", false)) {
                    String text = ic.getSelectedText(0).toString();
                    int a = getSelectionStart();
                    int b = getSelectionEnd();
                    if (selectionCase == 0) {
                        text = text.toUpperCase();
                        selectionCase = 1;
                    }
                    else {
                        text = text.toLowerCase();
                        selectionCase = 0;
                    }
                    commitText(text, b);
                    ic.setSelection(a, b);
                    setKeyboard();
                    redraw();
                }
                else {
                    switch (currentKeyboard.title) {
                        case "Caps":
                            if (kv.isShifted()) {currentKeyboard = new CustomKeyboard(this, R.layout.caps, "Caps", "ҩᴡᴇʀᴛʏ");}
                            else {currentKeyboard = new CustomKeyboard(this, R.layout.caps_shift, "Caps", "ҩᴡᴇʀᴛʏ");}
                        break;
                        case "Rotated":
                            if (kv.isShifted()) {currentKeyboard = new CustomKeyboard(this, R.layout.rotated, "Rotated", "bʍəɹʇʎ");}
                            else {currentKeyboard = new CustomKeyboard(this, R.layout.rotated_shift, "Rotated", "Ò𐊰ƎꓤꞱ⅄");}
                        break;
                        case "Stealth":
                            if (kv.isShifted()) {currentKeyboard = new CustomKeyboard(this, R.layout.stealth, "Stealth", "ԛԝеrtу");}
                            else {currentKeyboard = new CustomKeyboard(this, R.layout.stealth_shift, "Stealth", "ԚԜЕꓣТҮ");}
                        break;
                    }
                    kv.setKeyboard(currentKeyboard);
                    if (shift_pressed + 300 > System.currentTimeMillis()) {
                        Variables.setShiftOn();
                        setCapsOn(true);
                        redraw();
                    }
                    else {
                        if (Variables.isShift()) {
                            Variables.setShiftOff();
                            firstCaps = false;
                            setCapsOn(false);
                            shift_pressed = System.currentTimeMillis();
                        }
                        else {
                            firstCaps = !firstCaps;
                            setCapsOn(firstCaps);
                            shift_pressed = System.currentTimeMillis();
                        }
                    }
                    if (!currentKeyboard.title.equals("Caps")
                     && !currentKeyboard.title.equals("Cherokee")
                     && !currentKeyboard.title.equals("Rotated")
                     && !currentKeyboard.title.equals("Stealth")
                    ) {
                        setKeyboard();
                    }
                    redraw();
                }
            break;
            case -2: hide(); break;
            case -3:
                InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                if (imeManager != null) imeManager.showInputMethodPicker();
            break;
            case -4:
                setKeyboardLayout(layouts.size()-1);
            break;
            case -5:
                if (currentKeyboard.title.equals("Rotated") || currentKeyboard.title.equals("Lisu")) handleDelete();
                else handleBackspace();
            break;
            case -6:
            case -7:
                if (currentKeyboard.title.equals("Rotated") || currentKeyboard.title.equals("Lisu")) handleBackspace();
                else handleDelete();
            break;
            case -8: selectAll(); break;
            case -9:
                boolean wasntSelecting = false;
                if (!isSelecting()) {
                    selectLine();
                    wasntSelecting = true;
                }
                sendKey(KeyEvent.KEYCODE_CUT);
                toast.cancel();
                if (wasntSelecting) {
                    sendKey(KeyEvent.KEYCODE_DEL);
                }

                record = getClipboardEntry(0);
                if (!record.equals("")) {
                    clipboardHistory.add(record);
                }
                break;
            case -10:
                if (!isSelecting()) {
                    selectLine();
                }
                sendKey(KeyEvent.KEYCODE_COPY);
                toast.cancel();
                
                record = getClipboardEntry(0);
                if (!record.equals("")) {
                    clipboardHistory.add(record);
                }
                break;
            case -11:
                if (sharedPreferences.getBoolean("spaces", true)) {
                    String indent = Util.getIndentation(prevLine());
                    if (indent.length() > 0) {
                        commitText(getClipboardEntry(0).trim()); 
                        break;
                    }
                }
                sendKey(KeyEvent.KEYCODE_PASTE);
            break;
            case -15: sendKey(KeyEvent.KEYCODE_VOLUME_DOWN); break;
            case -16: sendKey(KeyEvent.KEYCODE_VOLUME_UP); break;
            case -17: sendKey(KeyEvent.KEYCODE_CAMERA); break;
            case -18: sendKey(KeyEvent.KEYCODE_EXPLORER); break;
            case -19: sendKey(KeyEvent.KEYCODE_MENU); break;
            case -20: sendKey(KeyEvent.KEYCODE_NOTIFICATION); break;
            case -21: sendKey(KeyEvent.KEYCODE_SEARCH); break;
            case -24: sendKey(KeyEvent.KEYCODE_BUTTON_START); break;
            case -27: sendKey(KeyEvent.KEYCODE_SETTINGS); break;
            case -28: sendKey(KeyEvent.KEYCODE_APP_SWITCH); break;
            case -29: sendKey(KeyEvent.KEYCODE_LANGUAGE_SWITCH); break;
            case -30: sendKey(KeyEvent.KEYCODE_BRIGHTNESS_DOWN); break;
            case -31: sendKey(KeyEvent.KEYCODE_BRIGHTNESS_UP); break;
            case -32: sendKey(KeyEvent.KEYCODE_NAVIGATE_PREVIOUS); break;
            case -33: sendKey(KeyEvent.KEYCODE_NAVIGATE_NEXT); break;
            case -34: sendKey(KeyEvent.KEYCODE_CLEAR); break;
            case -88: sendKey(KeyEvent.KEYCODE_DPAD_CENTER); break;
            case -89: sendKey(KeyEvent.ACTION_UP); break;
            case -90: sendKey(KeyEvent.ACTION_DOWN); break;
            case -48: sendKey(KeyEvent.KEYCODE_MANNER_MODE); break;
            case -49: sendKey(KeyEvent.KEYCODE_PICTSYMBOLS); break;
            case -63: sendKey(KeyEvent.KEYCODE_ESCAPE); break;
            case -64: sendKey(KeyEvent.KEYCODE_CALCULATOR); break;
            case -65: sendKey(KeyEvent.KEYCODE_CONTACTS); break;
            case -67: sendKey(KeyEvent.KEYCODE_CALENDAR); break;
            case -73: goToStart(); break;
            case -74: goToEnd(); break;
            case -12: Variables.toggleBolded(); break;
            case -13: Variables.toggleItalic(); break;
            case -14: Variables.setAllEmOff(); break;
            case -72: Variables.toggle009372(); break;
            case -66: Variables.toggle009398(); break;
            case -35: Variables.toggle119808(); break;
            case -36: Variables.toggle119860(); break;
            case -37: Variables.toggle119912(); break;
            case -38: Variables.toggle119964(); break;
            case -39: Variables.toggle120016(); break;
            case -40: Variables.toggle120068(); break;
            case -41: Variables.toggle120120(); break;
            case -42: Variables.toggle120172(); break;
            case -43: Variables.toggle120224(); break;
            case -44: Variables.toggle120276(); break;
            case -45: Variables.toggle120328(); break;
            case -46: Variables.toggle120380(); break;
            case -47: Variables.toggle120432(); break;
            case -68: Variables.toggle127280(); break;
            case -69: Variables.toggle127312(); break;
            case -70: Variables.toggle127344(); break;
            case -71: Variables.toggle127462(); break;
            case -57: Variables.toggleCaps(); break;
            case -50: Variables.toggleReflected(); break;
            case -76: Variables.toggleSelect(getSelectionStart()); break;
            case -75: selectNone(); break;
            case -77: prevWord(1); break;
            case -78: nextWord(1); break;
            case -80: commitText(getClipboardEntry(0)); break;
            case -81: commitText(getClipboardEntry(1)); break;
            case -82: commitText(getClipboardEntry(2)); break;
            case -83: commitText(getClipboardEntry(3)); break;
            case -84: commitText(getClipboardEntry(4)); break;
            case -85: commitText(getClipboardEntry(5)); break;
            case -86: commitText(getClipboardEntry(6)); break;
            case -87: commitText(getClipboardEntry(7)); break;
            case -100:
            case -101: prevKeyboard(); break;
            case -102: nextKeyboard(); break;
            case -107: navigate(KeyEvent.KEYCODE_DPAD_UP); break;
            case -108: navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -109: navigate(KeyEvent.KEYCODE_DPAD_DOWN); break;
            case -110: navigate(KeyEvent.KEYCODE_DPAD_CENTER); break;
            case -111: navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case  -22: navigate(KeyEvent.KEYCODE_PAGE_UP); break;
            case  -23: navigate(KeyEvent.KEYCODE_PAGE_DOWN); break;
            case  -25:
                navigate(KeyEvent.KEYCODE_MOVE_HOME);
                if (String.valueOf(ic.getTextBeforeCursor(1, 0)).contains("\n")) {
                    sendKey(KeyEvent.KEYCODE_DPAD_RIGHT, Util.getIndentation(nextLine()).length());
                }
            break;
            case  -26: navigate(KeyEvent.KEYCODE_MOVE_END); break;
            case -117: navigate(KeyEvent.KEYCODE_DPAD_UP); navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -118: navigate(KeyEvent.KEYCODE_DPAD_UP); navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -119: navigate(KeyEvent.KEYCODE_DPAD_DOWN); navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -120: navigate(KeyEvent.KEYCODE_DPAD_DOWN); navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -112:
                if (Variables.isMeta()) Variables.setMetaOff();
                else Variables.setMetaOn();
                redraw();
            case -113:
                if (Variables.isCtrl()) Variables.setCtrlOff();
                else Variables.setCtrlOn();
                redraw();
            break;
            case -114:
                if (Variables.isAlt()) Variables.setAltOff();
                else Variables.setAltOn();
                redraw();
            break;
            case -121:
                if (Variables.isCtrl() || Variables.isAlt()) {
                    if (Variables.isCtrl() && Variables.isAlt()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                    if (Variables.isAlt())  ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_ALT_ON));
                    if (Variables.isCtrl()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_CTRL_ON));
                }
                else sendKey(KeyEvent.KEYCODE_DEL);
            break;
            case -122:
                if (Variables.isCtrl() || Variables.isAlt()) {
                    if (Variables.isCtrl() && Variables.isAlt()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                    if (Variables.isAlt())  ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_ALT_ON));
                    if (Variables.isCtrl()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_CTRL_ON));
                }
                else sendKey(KeyEvent.KEYCODE_TAB);
            break;
            case -93: selectLine(); break;
            case -94: commitText(nextLine()+"\n"+ prevLine()); break;
            case -99:
                if (!isSelecting()) selectLine();
                sendKey(KeyEvent.KEYCODE_DEL);
            break;
            case -123: clearAll(); break;
            case -115: commitText(Util.generateRandomInt(1, 10) + " "); break;
            case -116: commitText(Util.nowAsLong()+" "+Util.nowAsInt()); break;
            case -124: performReplace(getText(ic).toUpperCase()); break;
            case -125: performReplace(Util.toTitleCase(getText(ic))); break;
            case -126: performReplace(getText(ic).toLowerCase()); break;
            case -127: performReplace(Util.underscoresToSpaces(getText(ic))); break;
            case -128: performReplace(Util.spacesToUnderscores(getText(ic))); break;
            case -129: performReplace(Util.dashesToSpaces(getText(ic))); break;
            case -130: performReplace(Util.spacesToDashes(getText(ic))); break;
            case -131: performReplace(Util.spacesToLinebreaks(getText(ic))); break;
            case -132: performReplace(Util.linebreaksToSpaces(getText(ic))); break;
            case -133: performReplace(Util.spacesToTabs(getText(ic))); break;
            case -134: performReplace(Util.tabsToSpaces(getText(ic))); break;
            case -135: performReplace(Util.splitWithLinebreaks(getText(ic))); break;
            case -51: performReplace(Util.convertNumberBase(getText(ic), 2, 10)); break;
            case -52: performReplace(Util.convertNumberBase(getText(ic), 10, 2)); break;
            case -53: performReplace(Util.convertNumberBase(getText(ic), 8, 10)); break;
            case -54: performReplace(Util.convertNumberBase(getText(ic), 10, 8)); break;
            case -55: performReplace(Util.convertNumberBase(getText(ic), 16, 10)); break;
            case -56: performReplace(Util.convertNumberBase(getText(ic), 10, 16)); break;
            case -58: performReplace(Util.camelToSnake(getText(ic))); break;
            case -59: performReplace(Util.snakeToCamel(getText(ic))); break;
            case -60: performReplace(Util.doubleCharacters(getText(ic))); break;
            case -95: commitText(Util.flipACoin()); break;
            case -96: commitText(Util.rollADie()); break;
            case -97: commitText(Util.getDateString(sharedPreferences.getString("date_format", "yyyy-MM-dd"))); break;
            case -98: commitText(Util.getTimeString(sharedPreferences.getString("time_format", "HH:mm:ss"))); break;
            case -61: performReplace(Util.increaseIndentation(getText(ic))); break;
            case -62: performReplace(Util.decreaseIndentation(getText(ic))); break;
            case -136:
                if (!isSelecting()) {
                    sendKey(KeyEvent.KEYCODE_MOVE_END);
                    sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
                }
                else {
                    performReplace(Util.linebreaksToSpaces(getText(ic)));
                }
            break;
            case -137: performReplace(Util.splitWithSpaces(getText(ic))); break;
            case -138: performReplace(Util.removeSpaces(getText(ic))); break;
            case -139: performReplace(Util.trimEndingWhitespace(getText(ic))); break;
            case -140: performReplace(Util.trimTrailingWhitespace(getText(ic))); break;
            case -79: performReplace(Util.reverse(getText(ic))); break;
            case -91: 
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleJavaComment(getText(ic))); 
            break; 
            case -92: 
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleHtmlComment(getText(ic))); 
            break;
            case -141:
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleLineComment(getText(ic)));
            break;
            case -142: setKeyboardLayout(0); break;
            case -143: performReplace(Util.rot13(getText(ic))); break;
            case -144: commitText(Util.pickALetter()); break;
            case -145: performReplace(Util.removeDuplicates(getText(ic))); break;
            case -103:
                commitText(Util.castALot());
                String trigram;
                if (!Hexagram.buildTrigram(ic.getTextBeforeCursor(3, 0).toString()).equals("")) {
                    trigram = Hexagram.buildTrigram(ic.getTextBeforeCursor(3, 0).toString());
                    ic.deleteSurroundingText(3, 0);
                    commitText(trigram);
                }
                if (!Hexagram.buildTrigram(ic.getTextBeforeCursor(2, 0).toString()).equals("")) {
                    trigram = Hexagram.buildTrigram(ic.getTextBeforeCursor(2, 0).toString());
                    ic.deleteSurroundingText(2, 0);
                    commitText(trigram);
                }
                if (!Hexagram.buildDigram(ic.getTextBeforeCursor(2, 0).toString()).equals("")) {
                    trigram = Hexagram.buildDigram(ic.getTextBeforeCursor(2, 0).toString());
                    ic.deleteSurroundingText(2, 0);
                    commitText(trigram);
                }
                if (!Hexagram.buildHexagram(ic.getTextBeforeCursor(2, 0).toString()).equals("")) {
                    trigram = Hexagram.buildHexagram(ic.getTextBeforeCursor(2, 0).toString());
                    ic.deleteSurroundingText(2, 0);
                    commitText(trigram);
                }
            break;
            case -104: 
                String text = getText(ic);
                toastIt("Chars: "+Util.countChars(text)+"\t\tWords: "+Util.countWords(text)+"\t\tLines: "+Util.countLines(text));
            break;
            case -105: performReplace(Util.sortLines(getText(ic))); break;
            case -106: performReplace(Util.reverseLines(getText(ic))); break;
            case -146: performReplace(Util.uniqueLines(getText(ic))); break;
            case -147: performReplace(Util.shuffleLines(getText(ic))); break;
            case -148: showSettings(); break;
            case -149: showVoiceInput(); break;
            case -152: performContextMenuAction(16908337); break; // pasteAsPlainText,
            case -150: performContextMenuAction(16908338); break; // undo
            case -151: performContextMenuAction(16908339); break; // redo
            case -153: performContextMenuAction(16908323); break; // copyUrl
            case -154: performContextMenuAction(16908355); break; // autofill
            case -155: performContextMenuAction(16908330); break; // addToDictionary
            case -156: performContextMenuAction(16908320); break; // cut
            case -157: performContextMenuAction(16908321); break; // copy
            case -158: performContextMenuAction(16908322); break; // paste
            case -159: performContextMenuAction(16908319); break; // selectAll
            case -160: performContextMenuAction(16908324); break; // switchInputMethod
            case -161: performContextMenuAction(16908328); break; // startSelectingText
            case -162: performContextMenuAction(16908329); break; // stopSelectingText
            case -163: performContextMenuAction(16908326); break; // keyboardView
            case -164: performContextMenuAction(16908333); break; // selectTextMode
            case -165: performContextMenuAction(16908327); break; // closeButton
            case -166: performContextMenuAction(16908316); break; // extractArea
            case -167: performContextMenuAction(16908317); break; // candidatesArea
            case -168: performReplace(Util.spaceReplace(getText(ic))); break;
            case -169:
                sendKey(KeyEvent.KEYCODE_MOVE_END);
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT));
                sendKey(KeyEvent.KEYCODE_MOVE_HOME);
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SHIFT_LEFT));
                sendKey(KeyEvent.KEYCODE_CUT);
                sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
                sendKey(KeyEvent.KEYCODE_DPAD_UP);
                sendKey(KeyEvent.KEYCODE_ENTER);
                sendKey(KeyEvent.KEYCODE_DPAD_UP);
                sendKey(KeyEvent.KEYCODE_PASTE);
            break;
            case -170:
                sendKey(KeyEvent.KEYCODE_MOVE_END);
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT));
                sendKey(KeyEvent.KEYCODE_MOVE_HOME);
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SHIFT_LEFT));
                sendKey(KeyEvent.KEYCODE_CUT);
                sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
                sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
                sendKey(KeyEvent.KEYCODE_ENTER);
                sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
                sendKey(KeyEvent.KEYCODE_PASTE);
            break;
            case -171: performReplace(Util.rot13(getText(ic))); break;
            case -172: 
                String toFind = getText(ic);
                selectAll();
                performReplace(Util.replaceZWSP(getText(ic), toFind)); 
            
            break;
            case -173: performReplace(Util.removeZWSP(getText(ic))); break;
            case -174: toastIt(Util.unidata(getText(ic)));
            
            /*
            
            here
            
            */
            
            case -300: toastIt(findKeyboard("Default")); break;
            case -301: toastIt(findKeyboard("Function")); break;
            case -302: toastIt(findKeyboard("Utility")); break;
            case -303: toastIt(findKeyboard("Emoji")); break;
            case -304: toastIt(findKeyboard("Navigation")); break;
            case -305: toastIt(findKeyboard("Symbol")); break;
            case -306: toastIt(findKeyboard("Fonts")); break;
            case -307:
                currentKeyboard = new CustomKeyboard(this, R.layout.cherokee,  "cherokee",  "Cherokee", "ꭰꭱꭲꭳꭴꭵ");
                kv.setKeyboard(currentKeyboard);
                setShifted(capsOn);
            break;
            case -308:
                currentKeyboard = new CustomKeyboard(this, R.layout.cherokee_2,  "cherokee_2",  "Cherokee", "ꮛꮜꮝꮞꮟꮠ");
                kv.setKeyboard(currentKeyboard);
                setShifted(capsOn);
            break;
            case -309: 
                currentKeyboard = new CustomKeyboard(this, R.layout.rorrim, "rorrim", "Rorrim", "poiuyt");
                kv.setKeyboard(currentKeyboard);
                setShifted(capsOn);
                toastIt("Rorrim");
            break;
            case -310:
                currentKeyboard = new CustomKeyboard(this, R.layout.mirror, "mirror", "Mirror", "qwerty");
                kv.setKeyboard(currentKeyboard);
                setShifted(capsOn);
                toastIt("Mirror"); 
            break;
            case -311: toastIt(findKeyboard("Shift₁")); break;
            case -312: toastIt(findKeyboard("Shift₂")); break;
            case -313: toastIt(findKeyboard("Katakana")); break;
            case -314: toastIt(findKeyboard("Hiragana")); break;
            case -400: toastIt(setKeyboardLayout(0)); break;
            case -401: toastIt(setKeyboardLayout(1)); break;
            case -402: toastIt(setKeyboardLayout(2)); break;
            case -403: toastIt(setKeyboardLayout(3)); break;
            case -404: toastIt(setKeyboardLayout(4)); break;
            case -405: toastIt(setKeyboardLayout(5)); break;
            case -406: toastIt(setKeyboardLayout(6)); break;
            case -407: toastIt(setKeyboardLayout(7)); break;
            case -408: toastIt(setKeyboardLayout(8)); break;
            case -409: toastIt(setKeyboardLayout(9)); break;
            case -410: toastIt(setKeyboardLayout(10)); break;
            case -411: toastIt(setKeyboardLayout(11)); break;
            case -412: toastIt(setKeyboardLayout(12)); break;
            case -413: toastIt(setKeyboardLayout(13)); break;
            case -414: toastIt(setKeyboardLayout(14)); break;
            case -415: toastIt(setKeyboardLayout(15)); break;
            case -416: toastIt(setKeyboardLayout(16)); break;
            case -417: toastIt(setKeyboardLayout(17)); break;
            case -418: toastIt(setKeyboardLayout(18)); break;
            case -419: toastIt(setKeyboardLayout(19)); break;
            case -420: toastIt(setKeyboardLayout(20)); break;
            case -421: toastIt(setKeyboardLayout(21)); break;
            case -422: toastIt(setKeyboardLayout(22)); break;
            case -423: toastIt(setKeyboardLayout(23)); break;
            case -424: toastIt(setKeyboardLayout(24)); break;
            case -425: toastIt(setKeyboardLayout(25)); break;
            case -426: toastIt(setKeyboardLayout(26)); break;
            case -427: toastIt(setKeyboardLayout(27)); break;
            case -428: toastIt(setKeyboardLayout(28)); break;
            case -429: toastIt(setKeyboardLayout(29)); break;
            case -430: toastIt(setKeyboardLayout(30)); break;
            case -431: toastIt(setKeyboardLayout(31)); break;
            case -432: toastIt(setKeyboardLayout(32)); break;
            case -433: toastIt(setKeyboardLayout(33)); break;
            case -434: toastIt(setKeyboardLayout(34)); break;
            case -435: toastIt(setKeyboardLayout(35)); break;
            case -436: toastIt(setKeyboardLayout(36)); break;
            case -437: toastIt(setKeyboardLayout(37)); break;
            case -438: toastIt(setKeyboardLayout(38)); break;
            case -439: toastIt(setKeyboardLayout(39)); break;
            case -440: toastIt(setKeyboardLayout(40)); break;
            case -441: toastIt(setKeyboardLayout(41)); break;
            case -442: toastIt(setKeyboardLayout(42)); break;
            case -443: toastIt(setKeyboardLayout(43)); break;
            case -444: toastIt(setKeyboardLayout(44)); break;
            case -445: toastIt(setKeyboardLayout(45)); break;
            case -446: toastIt(setKeyboardLayout(46)); break;
            case -447: toastIt(setKeyboardLayout(47)); break;
            case -448: toastIt(setKeyboardLayout(48)); break;
            case -449: toastIt(setKeyboardLayout(49)); break;

            default:
                if (Variables.isCtrl() || Variables.isAlt()) { processKeyCombo(primaryCode); }
                else {
                    try {
                        handleCharacter(primaryCode);
                        redraw();
                        if (Variables.isReflected() || sharedPreferences.getBoolean("cursor_left", false) || currentKeyboard.title.equals("Rotated") || currentKeyboard.title.equals("Lisu")) {
                            sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
                        }
                    }
                    catch (Exception ignored) {}
                }
        }
        try {
            if (sharedPreferences.getBoolean("caps", false)
            && (ic.getTextBeforeCursor(2, 0).toString().contains(". ") 
             || ic.getTextBeforeCursor(2, 0).toString().contains("? ") 
             || ic.getTextBeforeCursor(2, 0).toString().contains("! "))
            ) {
                setCapsOn(true);
                firstCaps = true;
            }
        }
        catch (Exception ignored) {}
        if (sharedPreferences.getBoolean("debug", false)) toastIt(KeyEvent.keyCodeToString(primaryCode));
    }

    public short getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int number) {
        rowNumber = (short)number;
    }
    
    public double getHeightKeyModifier() {
        return (double)PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("height", 50) / (double)50;
    }

}
