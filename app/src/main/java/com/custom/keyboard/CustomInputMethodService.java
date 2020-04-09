package com.custom.keyboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.UserDictionary;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CustomInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener, SpellCheckerSession.SpellCheckerSessionListener {

    int MAX = 65536;
    boolean f = false;
    boolean t = true;
    static String empty = "";

    String spaces = "    ";
    String tab = "	";

    public CustomKeyboardView kv;
    public CustomKeyboard currentKeyboard;
    public int currentKeyboardID = 0;
    public short rowNumber = 6;
    public InputMethodManager mInputMethodManager;
    public CustomKeyboardView mInputView;
    public int mLastDisplayWidth;
    public boolean mCapsLock;
    public float[] mDefaultFilter;
    public int mDefaultLayout;
    public String mWordSeparators;
    public StringBuilder mComposing = new StringBuilder();
    public boolean mPredictionOn;
    public boolean mCompletionOn;
    public SpellCheckerSession mScs;
    public List<String> mSuggestions;
    public CandidateView mCandidateView;
    public CompletionInfo[] mCompletions;

    boolean firstCaps = f;
    long shift_pressed = 0;
    boolean keepCtrlOpen = false;

    Toast toast;

    int selectionCase = 0;
    static String hexBuffer = empty;
    static String morseBuffer = empty;

    InputConnection ic = getCurrentInputConnection();
    SharedPreferences sharedPreferences;

    public static ArrayList<CustomKeyboard> layouts = new ArrayList<>(5);

    public ArrayList<String> clipboardHistory = new ArrayList<>(10);
    
    private GestureDetector mGestureDetector;

    Edit spellchecker;

    // String sPositiveColorBg = "#000000"
    // String sPositiveColorFg = "#ffffff"
    static final float[] sPositiveColorArray = {
            1.0f,      0,      0,       0,      0, // red
               0,   1.0f,      0,       0,      0, // green
               0,      0,   1.0f,       0,      0, // blue
               0,      0,      0,    1.0f,      1  // alpha
    };
    // String sNegativeColorBg = "#ffffff"
    // String sNegativeColorFg = "#000000"
    static final float[] sNegativeColorArray = {
            -1.0f,      0,      0,       0,    255, // red
                0,  -1.0f,      0,       0,    255, // green
                0,      0,  -1.0f,       0,    255, // blue
                0,      0,      0,    1.0f,      0  // alpha
    };

    public enum Category {Main, Util, Lang, Misc, Font}

    static void print(Object ...a) {
        for (Object i: a) System.out.print(i + " ");
        System.out.println();
    }


    public void toastIt(int num) {
        toastIt(String.valueOf(num));
    }
    public void toastIt(Exception e) {
        toastIt(e.toString());
    }
    public void toastIt(String ...args) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (!sharedPreferences.getBoolean("debug", f)) return;
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
        toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    public void populate() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
        layouts.clear();
        layouts.add(new CustomKeyboard(this, R.layout.primary,     "primary",     "qwerty").setCategory(Category.Main));
        if (sharedPreferences.getBoolean("accents",    t)) {layouts.add(new CustomKeyboard(this, R.layout.accents,     "accents",    "◌̀◌́◌̂").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("emoji",      t)) {layouts.add(new CustomKeyboard(this, R.layout.emoji,       "emoji",      "😀😁😂").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("fancy",      t)) {layouts.add(new CustomKeyboard(this, R.layout.fancy,       "fancy",      "ɋƿҽꝛþү").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("fonts",      t)) {layouts.add(new CustomKeyboard(this, R.layout.fonts,       "fonts",      "🄰🅐🄐𝔸𝕬𝒜").setCategory(Category.Font));}
        if (sharedPreferences.getBoolean("function",   t)) {layouts.add(new CustomKeyboard(this, R.layout.function,    "function",   "ƒ(x)").setCategory(Category.Util).setOrder(-2));}
        if (sharedPreferences.getBoolean("hex",        t)) {layouts.add(new CustomKeyboard(this, R.layout.hex,         "hex",        "\\uabcd").setCategory(Category.Util));}
        if (sharedPreferences.getBoolean("ipa",        t)) {layouts.add(new CustomKeyboard(this, R.layout.ipa,         "IPA",        "ɋʍəɹʈɥ").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("macros",     t)) {layouts.add(new CustomKeyboard(this, R.layout.macros,      "macros",     "✐").setCategory(Category.Util).setOrder(-4));}
        if (sharedPreferences.getBoolean("navigation", t)) {layouts.add(new CustomKeyboard(this, R.layout.navigation,  "navigation", "→←↑↓").setCategory(Category.Util).setOrder(-1));}
        if (sharedPreferences.getBoolean("numeric",    t)) {layouts.add(new CustomKeyboard(this, R.layout.numeric,     "numeric",    "123456").setCategory(Category.Util));}
        if (sharedPreferences.getBoolean("symbol",     t)) {layouts.add(new CustomKeyboard(this, R.layout.symbol,      "symbol",     "!@#$%^").setCategory(Category.Misc));}
        if (sharedPreferences.getBoolean("unicode",    t)) {layouts.add(new CustomKeyboard(this, R.layout.unicode,     "unicode",    "\\uxxxx").setCategory(Category.Util));}
        if (sharedPreferences.getBoolean("url",        t)) {layouts.add(new CustomKeyboard(this, R.layout.url,         "URL",        "@/.com").setCategory(Category.Util));}
        if (sharedPreferences.getBoolean("utility",    t)) {layouts.add(new CustomKeyboard(this, R.layout.utility,     "utility",    "/**/").setCategory(Category.Util).setOrder(-3));}

        int layoutLayout = R.layout.layouts;

        layouts.add(new CustomKeyboard(this, layoutLayout, "Layouts").setOrder(-1));

        if (getKeyboard("Layouts") != null) {
            for (Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                if (key.codes[0] <= layoutMin && key.codes[0] >= layoutMax) {
                    try {
                        CustomKeyboard layout = layouts.get(-key.codes[0] + layoutMin);
                        if (layout != null && !layout.title.equals("Layouts")) {
                            if (sharedPreferences.getBoolean("names", t)) {
                                key.label = layout.title;
                            }
                            else key.label = layout.label;
                        }
                        else key.label = empty;
                    }
                    catch (Exception e) {
                        key.label = empty;
                    }
                }
            }
        }

        adjustLayoutPage();

        StringBuilder autoLabel;
        for(CustomKeyboard layout : layouts) {
            autoLabel = new StringBuilder();
            for(Keyboard.Key key : layout.getKeys()) {
                if (key.label == null) continue;
                if (key.label == empty) continue;
                if (key.label.length() > 1) continue;
                if (Util.isNumeric(String.valueOf(key.label))) continue;
                if (",\";".contains(String.valueOf(key.label))) continue;
                autoLabel.append(key.label);
                if (autoLabel.length() > 2) break;
            }
            String label = autoLabel.toString().trim();
            if (layout.label == null || layout.label.equals(empty)) {
                layout.label = label;
            }
        }
    }

    int layoutMin = -400;
    int layoutMax = -454;

    public void adjustLayoutPage() {
        if (!sharedPreferences.getBoolean("relayout", t)) {return;}

        List<Keyboard.Key> layoutKeys = new ArrayList<>();
        for(Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
            if (key.codes[0] <= layoutMin && key.codes[0] >= layoutMax) {
                layoutKeys.add(key);
            }
        }
        Bounds bounds = getBounds(layoutKeys);

        int rowHeight = layoutKeys.get(0).height;
        int colCount = 6;
        int startRowCount = 9;
        int layoutCount = Math.max(layouts.size()-1, 1);
        double finalRowCount = Math.ceil((double)layoutCount / (double)colCount);

        // 1 row:  double moveBy = 448; //bounds.dY / (finalRowCount); 448 - (56 * 0) / 1
        // 2 rows: double moveBy = 196; //bounds.dY / (finalRowCount); 448 - (56 * 1) / 2
        // 3 rows: double moveBy = 112; //bounds.dY / (finalRowCount); 448 - (56 * 2) / 3
        double moveBy = (bounds.dY - (rowHeight * (finalRowCount - 1))) / finalRowCount;

        int row, index = 0;
        for (Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
            if (key.codes[0] <= layoutMin && key.codes[0] >= layoutMax) {
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

        int layoutMod = (layoutCount % colCount);
        if (layoutMod > 0) {
            int hi = layoutMin - layoutCount;
            int lo = hi + layoutMod;
            hi = lo - colCount;

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
                        key.label = empty;
                    }
                    else {
                        key.x += (moveBy * index);
                        key.width += moveBy;
                    }
                    index++;
                }
            }
        }

        try {redraw();}
        catch(Exception ignored) {}
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

    public void removeLayout(String key) {
        ArrayList<CustomKeyboard> deleteCandidates = new ArrayList<>();
        for (CustomKeyboard layout : layouts) {
            if (layout.key.equals("key")) {
                deleteCandidates.add(layout);
            }
        }
        for (CustomKeyboard deleteCandidate : deleteCandidates) {
            layouts.remove(deleteCandidate);
        }
    }

    public void performContextMenuAction(int id) {
        InputConnection ic = getCurrentInputConnection();
        ic.performContextMenuAction(id);
    }

    public void showActivity(String id) {
        Intent intent = new Intent(id).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent(intent);
    }

    private IBinder getToken() {
        final Dialog dialog = getWindow();
        if (dialog == null) {
            return null;
        }
        final Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }
        return window.getAttributes().token;
    }

    public void showVoiceInput() {
        InputMethodManager inputMethodManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.setInputMethod(getToken(), "com.google.android.googlequicksearchbox/com.google.android.voicesearch.ime.VoiceInputMethodService");
    }

    public void startIntent(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void showSettings() {
        Intent intent = new Intent(getApplicationContext(), Preference.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent(intent);
    }

    public void showClipboard() {
        try {
            Intent intent = new Intent("com.samsung.android.ClipboardUIService");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startIntent(intent);
        }
        catch (Exception e) {
            toastIt(e.toString());
        }
    }

    public void addToDictionary(String word) {
        UserDictionary.Words.addWord(this, word, 10, "Mad", Locale.getDefault());
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

    public void setKeyboard() {
        if (currentKeyboardID >= layouts.size()-1) currentKeyboardID = 0; // layouts.size()-2;
        setKeyboardLayout(currentKeyboardID);
    }

    public String setKeyboardLayout(int newKeyboardID) {
        boolean capsOn = Variables.isShift();
        try {
            if (newKeyboardID < layouts.size()) {
                currentKeyboardID = newKeyboardID;
                currentKeyboard = layouts.get(currentKeyboardID);
                layouts.get(currentKeyboardID).setOrder(1);
                if (sharedPreferences.getBoolean("respace", f)) {
                    String currentKeyboardLabel = currentKeyboard.label;
                    if (currentKeyboard.title.equals("Layouts")) {
                        if (layouts.size()-1 != 1) getKey(32).label = (layouts.size()-1)+" layouts";
                        else getKey(32).label = "1 layout";
                    }
                    else 
                    if (currentKeyboard.title.equals("Hex") || currentKeyboard.title.equals("Unicode")) {
                        kv.setShifted(true);
                    }
                    else if (!currentKeyboard.title.equals("Shift")) {
                        if (kv.isShifted()) currentKeyboardLabel = currentKeyboardLabel.toUpperCase();
                        else currentKeyboardLabel = currentKeyboardLabel.toLowerCase();
                        getKey(32).label = currentKeyboard.title +"\t•\t"+currentKeyboardLabel; // ·
                    }
                }
                kv.setKeyboard(currentKeyboard);
                setRowNumber(6);

                redraw();
                kv.capsHack();
            }
        }
        catch (Exception e) {
            toastIt(e.toString());
        }
        return currentKeyboard.title;
    }

    public void setInputType() {
        setKeyboard();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        mWordSeparators = getResources().getString(R.string.word_separators);
        final TextServicesManager tsm = (TextServicesManager)getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        mScs = tsm != null ? tsm.newSpellCheckerSession(null, null, this, true) : null;

        toast = new Toast(getBaseContext());
        populate();

        spellchecker = new Edit(getBaseContext());
    }

    @Override
    public void onInitializeInterface() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        populate();
        if (getKeyboard("Primary") != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateInputView() {
        populate();
        int layoutId;
        mInputView = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.primary, null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setPreviewEnabled(sharedPreferences.getBoolean("preview", f));
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
        mComposing.setLength(0);

        mCompletions = null;

        if (sharedPreferences.getBoolean("preview", f)) {
            kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard_with_previews, null);
        }
        else if (sharedPreferences.getBoolean("transparent", f)) {
            kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard_transparent, null);
        }
        else {
            kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        }

        setInputType();

        setTheme();
        Paint mPaint = new Paint();
        ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(mDefaultFilter);
        mPaint.setColorFilter(filterInvert);

        mCandidateView = new CandidateView(this);
        mCandidateView.setService(this);
        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        setCandidatesView(mCandidateView);

        kv.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        setRowNumber(getRowNumber());
        kv.setKeyboard(currentKeyboard);

        capsOnFirst();

        kv.setOnKeyboardActionListener(this);

        mPredictionOn = sharedPreferences.getBoolean("pred", f);
        mCompletionOn = sharedPreferences.getBoolean("auto", f);

        setInputView(kv);

        populate();

        setKeyboard();
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();
        mComposing.setLength(0);
        setCandidatesViewShown(f);
        Variables.setSelectOff();
        if (mInputView != null) mInputView.closing();
    }

    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);
        if (mComposing.length() > 0 && (newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
            mComposing.setLength(0);
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.finishComposingText();
            }
        }
    }

    @Override
    public void swipeLeft() { }

    @Override
    public void swipeRight() { }

    @Override
    public void swipeDown() { }

    @Override
    public void swipeUp() { }

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
                ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, Util.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   Util.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            }
            else {
                if (Variables.isCtrl()) {
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, Util.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   Util.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                }
                if (Variables.isAlt()) {
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, Util.getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   Util.getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                }
            }
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
        if (text == null) return empty;
        return (String)text;
    }

    public String getAllText(InputConnection ic) {
        return ic.getTextBeforeCursor(MAX, 0).toString()
             + ic.getTextAfterCursor(MAX,  0).toString();
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
        String ins = sharedPreferences.getString(key, empty);
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
        catch (Exception e) {
            toastIt(e.toString());
        }
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
        catch (Exception e) {
            toastIt(e.toString());
        }
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
        catch (Exception e) {
            toastIt(e.toString());
        }
    }

    public void selectLine() {
        sendKey(KeyEvent.KEYCODE_MOVE_HOME);
        Variables.setSelectOn(getSelectionStart());
        navigate(KeyEvent.KEYCODE_MOVE_END);
        Variables.setSelectOff();
    }

    public int getCurrentLine() {
        return Util.getLines(ic.getTextBeforeCursor(MAX, 0).toString()).length;
    }

    public int getLineCount() {
        return Util.getLines(getAllText(ic)).length;
    }

    public int[] getCursorLocation() {
        return new int[] { cursorLocationOnLine(), getCurrentLine() };
    }

    public int cursorLocationOnLine() {
        return prevLine().length();
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
        if (!isSelecting() && primaryCode == KeyEvent.KEYCODE_DPAD_LEFT && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals(spaces)) {
            sendKey(primaryCode, 4);
        }
        else if (!isSelecting() && primaryCode == KeyEvent.KEYCODE_DPAD_RIGHT && String.valueOf(ic.getTextAfterCursor(4, 0)).equals(spaces)) {
            sendKey(primaryCode, 4);
        }
        else {
            if (Variables.isSelect()) ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT));
            sendKey(primaryCode);
            if (Variables.isSelect()) ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SHIFT_LEFT));
        }
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
        if (sharedPreferences.getBoolean("autocaps", f)) {
            if (getCursorCapsMode(ic, getCurrentInputEditorInfo()) != 0) {
                firstCaps = t;
                setCapsOn(t);
            }
        }
        else {
            firstCaps = f;
            setCapsOn(f);
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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        try {
            String theme = sharedPreferences.getString("theme", "1");
            if (theme != null) {
                if (theme.equals("0")) theme = String.valueOf(Util.generateRandomInt(1, 22));
                switch (theme) {
                    // case  "1": mDefaultFilter = Themes.sPositiveColorArray;     break;
                    case  "2": mDefaultFilter = sNegativeColorArray;     break;
                    default:   mDefaultFilter = sPositiveColorArray;     break;
                }
            }
        }
        catch (Exception e) {
            toastIt(e.toString());
        }
    }

    public void replaceText(String src, String trg) {
        ic.deleteSurroundingText(src.length(), 0);
        commitText(trg);
    }

    public String getLastWord() {
        ic = getCurrentInputConnection();
        String prev = String.valueOf(ic.getTextBeforeCursor(20, 0));
        prev = prev.replaceAll("[\\s.,]+", " ");
        String[] words = prev.split(" ");
        return words[words.length-1];
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

    long time = 0;

    @Override
    public void onPress(int primaryCode) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        time = System.nanoTime() - time;
        if (sharedPreferences.getBoolean("vib", t)) {
            Vibrator v = (Vibrator)getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(4);
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        ic = getCurrentInputConnection();
        time = (System.nanoTime() - time) / 1000000;

        if (time > 300) {
            switch (primaryCode) {
                case 31: performContextMenuAction(16908330); break;
                // case 10: handleEnter(1); break;
                case -11: performContextMenuAction(16908337); break; // pasteAsPlainText
                case -93: selectAll(); break;
                case -99: ic.deleteSurroundingText(MAX, MAX); break;
                case -192: keepCtrlOpen = !keepCtrlOpen;
                case -2003: commitText(Util.unidata(getText(ic))); break;
            }
        }
    }

    public Boolean isAstralCharacter(String ch) {
        int value = Integer.parseInt(ch, 16);
        char[] codeUnits = Character.toChars(value);
        return codeUnits.length > 1;
    }

    public void handleBackspace() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ic = getCurrentInputConnection();
        final int length = mComposing.length();

        // if (!isSelecting()) selectLine();

        try {
            if (sharedPreferences.getBoolean("pairs", t)
            && ic.getTextBeforeCursor(1, 0) != null
            && String.valueOf(ic.getTextBeforeCursor(1, 0)).length() >= 1
            && Util.contains(")}\"]", String.valueOf(ic.getTextAfterCursor(1, 0)))
            && String.valueOf(ic.getTextBeforeCursor(1, 0)).equals(String.valueOf(ic.getTextAfterCursor(1, 0)))) {
                ic.deleteSurroundingText(0, 1);
            }
            if (!isSelecting()
            && ic.getTextBeforeCursor(4, 0) != null
            && String.valueOf(ic.getTextBeforeCursor(4, 0)).length() >= 4
            && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals(spaces)
            && sharedPreferences.getBoolean("spaces", t)) {
                ic.deleteSurroundingText((4 - (prevLine().length() % 4)), 0);
            }
            else sendKey(KeyEvent.KEYCODE_DEL);
            if (prevLine() != null && prevLine().length() > 0 && Character.isUpperCase(ic.getTextBeforeCursor(1, 0).charAt(0))) {
                setCapsOn(t);
                firstCaps = t;
            }
            else {
                setCapsOn(f);
                firstCaps = f;
            }
            if (length == 0) {
                setCandidatesViewShown(false);
            }
            else {
                spellcheck();
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
        
        }
        catch(Exception ignored) {}

    }

    public void handleDelete() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ic = getCurrentInputConnection();
        final int length = mComposing.length();

        if (!isSelecting() && String.valueOf(ic.getTextAfterCursor(4, 0)).equals(spaces) && sharedPreferences.getBoolean("spaces", t)) {
            ic.deleteSurroundingText(0, 4);
        }
        else if (length > 1) {
            mComposing.delete(length, length - 1);
            ic.setComposingText(mComposing, 1);
        }
        else if (length > 0) {
            mComposing.setLength(0);
            commitText(empty);
        }
        else sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
        if (length == 0) {
            setCandidatesViewShown(false);
        }
        else {
            spellcheck();
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void handleCharacter(int primaryCode) {
        ic = getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        primaryCode = Util.handleCharacter(kv, primaryCode);
        if (kv.isShifted() && !currentKeyboard.key.equals("shift_2")) {
            primaryCode = Character.toUpperCase(primaryCode);
        }
        if (sharedPreferences.getBoolean("show_data", f) && !currentKeyboard.title.equals("Pinyin")) {
            if (sharedPreferences.getBoolean("show_ascii_data", f) || primaryCode > 127) {
                toastIt(Util.unidata(primaryCode));
            }
        }
        if (sharedPreferences.getBoolean("pairs", t)) {
            if (Util.contains("({\"[", primaryCode)) {
                String code = String.valueOf((char)primaryCode);
                if (code.equals("("))  commitText("()");
                if (code.equals("["))  commitText("[]");
                if (code.equals("{"))  commitText("{}");
                if (code.equals("\"")) commitText("\"\"");
                if (sharedPreferences.getBoolean("cursor_left", f) || currentKeyboard.title.equals("Rotated") || currentKeyboard.title.equals("Lisu")) {
                    sendKey(KeyEvent.KEYCODE_DPAD_RIGHT);
                }
                else {
                    sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
                }
                return;
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
            firstCaps = f;
            setCapsOn(f);
        }
        if (!mPredictionOn) {
            ic.setComposingRegion(0, 0);
            commitText(String.valueOf(Character.toChars(primaryCode)), 1);
            firstCaps = f;
            setCapsOn(f);
        }
        if (sharedPreferences.getBoolean("pred", f)) {
            spellcheck(primaryCode);
        }
    }

    private void dumpSuggestionsInfoInternal(final List<String> sb, final SuggestionsInfo si, final int length, final int offset) {
        final int len = si.getSuggestionsCount();
        for (int j = 0; j < len; ++j) {
            sb.add(si.getSuggestionAt(j));
        }
    }

    public void spellcheck() {
        try {
            String lastWord = getLastWord();
            lastWord = lastWord.toLowerCase();
            if (lastWord.length() < 2) return;

            boolean isWord = Edit.inTrie(lastWord);

            ArrayList<String> completions;
            if (!isWord) {
                lastWord = Edit.check(lastWord);
            }
            completions = Edit.getCompletions(lastWord);
            List<String> stringList = new ArrayList<>();

            setSuggestions(completions, true, isWord);
        }
        catch (Exception ignored) {}
    }

    public void spellcheck(int primaryCode) {
        if (sharedPreferences.getBoolean("pred", f)) {
            return;
        }
        if (!(Util.isLetter(primaryCode) || primaryCode == 29 || primaryCode == 30 || primaryCode == 31 || primaryCode == 32)) return;
        spellcheck();
    }

    @Override
    public View onCreateCandidatesView() {
        mCandidateView = new CandidateView(this);
        mCandidateView.setService(this);
        setTheme();
        Paint mPaint = new Paint();
        ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(mDefaultFilter);
        mPaint.setColorFilter(filterInvert);
        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        return mCandidateView;
    }

    public void setSuggestion(String suggestion) {
        List<String> suggestions = new ArrayList<>();
        suggestions.add(suggestion);
        setSuggestions(suggestions, false, true);
    }

    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {
        if (suggestions != null && suggestions.size() > 0) {
            setCandidatesViewShown(true);
        }
        else if (isExtractViewShown()) {
            setCandidatesViewShown(true);
        }
        mSuggestions = suggestions;
        if (mCandidateView != null) {
            mCandidateView.setSuggestions(suggestions, completions, typedWordValid);
        }
    }

    public void clearCandidates() {
        if (mCandidateView != null) {
            mCandidateView.clear();
        }
    }

    public void pickSuggestionManually(int index) {
        if (mCompletionOn && mSuggestions != null && index >= 0 && index < mSuggestions.size()) {
            String completion = mSuggestions.get(index);

            ic.deleteSurroundingText(getLastWord().length(), 0);
            commitText(completion+"");

            if (mCandidateView != null) {
                mCandidateView.clear();
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {

    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {

    }
    


    public void handleShift() {
        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0 && PreferenceManager.getDefaultSharedPreferences(this).getBoolean("shift", f)) {
            String text = ic.getSelectedText(0).toString();
            int a = getSelectionStart();
            int b = getSelectionEnd();
            if (Util.containsUpperCase(text)) {
                text = text.toLowerCase();
            }
            else {
                text = text.toUpperCase();
            }
            commitText(text, b);
            ic.setSelection(a, b);
            setKeyboard();
            redraw();
        }
        else {
            kv.setKeyboard(currentKeyboard);
            if (shift_pressed + 300 > System.currentTimeMillis()) {
                Variables.setShiftOn();
                setCapsOn(t);
                redraw();
            }
            else {
                if (Variables.isShift()) {
                    Variables.setShiftOff();
                    firstCaps = f;
                    setCapsOn(f);
                    shift_pressed = System.currentTimeMillis();
                }
                else {
                    firstCaps = !firstCaps;
                    setCapsOn(firstCaps);
                    shift_pressed = System.currentTimeMillis();
                }
            }
            setKeyboard();
            redraw();
        }
    }
    
    public void handleCut() {
        if (!isSelecting()) {
            selectLine();
        }
        sendKey(KeyEvent.KEYCODE_CUT);
        toast.cancel();
    }
    public void handleCopy() {
        if (!isSelecting()) {
            selectLine();
        }
        sendKey(KeyEvent.KEYCODE_COPY);
        toast.cancel();
    }

    public void handlePaste() {
        sendKey(KeyEvent.KEYCODE_PASTE);
        if (mCandidateView != null) {
            mCandidateView.clear();
        }
    }
    
    public void handleSpace() {
        if (sharedPreferences.getBoolean("spaces", t)) {
            int spaceCount = (4 - (prevLine().length() % 4));
            if (spaceCount > 0 && spaceCount < 4 && prevLine().length() < 4) {
                spaceCount = 4;
            }
            commitText(spaces.substring(0, spaceCount));
            if (isSelecting()) {
                ic.setSelection(getSelectionStart(), getSelectionEnd() + spaces.length());
            }
        }
        else {
            commitText(tab);
            if (isSelecting()) {
                ic.setSelection(getSelectionStart(), getSelectionEnd() + tab.length());
            }
        }
    }
    public void handleEnter() {
        // if (noop == 0) return;
        EditorInfo curEditor = getCurrentInputEditorInfo();
    
        if (sharedPreferences.getBoolean("spaces", t)) {
            String indent = Util.getIndentation(prevLine());
            if (indent.length() > 0) {commitText("\n"+indent); return;}
        }
        
        switch (curEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
            case EditorInfo.IME_ACTION_GO:      ic.performEditorAction(EditorInfo.IME_ACTION_GO);     break;
            case EditorInfo.IME_ACTION_SEARCH:  ic.performEditorAction(EditorInfo.IME_ACTION_SEARCH); break;
            default:                            sendKey(KeyEvent.KEYCODE_ENTER);                      break;
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String record, currentKeyboardName = currentKeyboard.title;
        boolean capsOn = Variables.isShift();
        int ere, aft;
        CustomKeyboard prevKeyboard = currentKeyboard;

        if (currentKeyboard.title.equals("Unicode") && !Util.contains(Util.hexPasses, primaryCode)) {
            if (primaryCode == -2001) performReplace(Util.convertFromUnicodeToNumber(getText(ic)));
            if (primaryCode == -2002) performReplace(Util.convertFromNumberToUnicode(getText(ic)));
            if (Util.contains(Util.hexCaptures, primaryCode)) {
                if (hexBuffer.length() > 3) hexBuffer = empty;
                hexBuffer += (char)primaryCode;
            }
            if (primaryCode == -2003) commitText(StringUtils.leftPad(hexBuffer, 4, "0"));
            if (primaryCode == -2004) commitText(String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(hexBuffer, 4, "0"))));
            if (primaryCode == -2005) {
                if (hexBuffer.length() > 0) hexBuffer = hexBuffer.substring(0, hexBuffer.length() - 1);
                else hexBuffer = "0000";
            }
            if (primaryCode == -2006) hexBuffer = "0000";
            getKey(-2003).label = hexBuffer.equals("0000") ? empty : StringUtils.leftPad(hexBuffer, 4, "0");
            getKey(-2004).label = String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(hexBuffer, 4, "0")));
            redraw();
            return;
        }
        if (currentKeyboard.title.equals("Control") && !keepCtrlOpen) {
            findKeyboard(prevKeyboard.title);
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
            case  10: handleEnter(); break;
            case   7: handleSpace(); break;
            case  -1: handleShift(); break;
            case  -2: hide(); break;
            case  -3:
                InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                if (imeManager != null) imeManager.showInputMethodPicker();
            break;
            case  -4:
                // adjustLayoutPage();
                setKeyboardLayout(layouts.size()-1);
            break;
            case  -5: handleBackspace(); break;
            case  -7: handleDelete(); break;
            case  -8: selectAll(); break;
            case  -9: handleCut(); break;
            case -10: handleCopy(); break;
            case -11: handlePaste(); break;
            case -73: goToStart(); break;
            case -74: goToEnd(); break;
            case -76: Variables.toggleSelect(getSelectionStart()); break;
            case -75: selectNone(); break;
            case -77: prevWord(1); break;
            case -78: nextWord(1); break;
            case -93: selectLine(); break;
            case -94: commitText(nextLine()+"\n"+ prevLine()); break;
            case -123: clearAll(); break;
            case -107: navigate(KeyEvent.KEYCODE_DPAD_UP); break;
            case -108: navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -109: navigate(KeyEvent.KEYCODE_DPAD_DOWN); break;
            case -110: navigate(KeyEvent.KEYCODE_DPAD_CENTER); break;
            case -111: navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -22: navigate(KeyEvent.KEYCODE_PAGE_UP); break;
            case -23: navigate(KeyEvent.KEYCODE_PAGE_DOWN); break;
            case -25:
                navigate(KeyEvent.KEYCODE_MOVE_HOME);
                if (String.valueOf(ic.getTextBeforeCursor(1, 0)).contains("\n")) {
                    sendKey(KeyEvent.KEYCODE_DPAD_RIGHT, Util.getIndentation(nextLine()).length());
                }
            break;
            case -26: navigate(KeyEvent.KEYCODE_MOVE_END); break;
            case -148: showSettings(); break;
            case -149: showVoiceInput(); break;
            case -12:
                try {
                    if (Variables.isBold()) {
                        performReplace(Util.unbolden(getText(ic)));
                    }
                    else {
                        performReplace(Util.bolden(getText(ic))); 
                    }
                    Variables.toggleBolded();
                } 
                catch (Exception e) {
                    toastIt(e);
                }
            break;
            case -13: 
                try {
                    if (Variables.isItalic()) {
                        performReplace(Util.unitalicize(getText(ic)));
                    }
                    else {
                        performReplace(Util.italicize(getText(ic))); 
                    }
                    Variables.toggleItalic(); 
                } 
                catch (Exception e) {
                    toastIt(e);
                }
            break;
            case -14: Variables.setAllEmOff(); break;
            case -116: commitText(Util.nowAsLong()+" "+Util.nowAsInt()); break;
            case -124: performReplace(Util.toUpperCase(getText(ic))); break;
            case -125: performReplace(Util.toTitleCase(getText(ic))); break;
            case -126: performReplace(Util.toLowerCase(getText(ic))); break;
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
            case -97: commitText(Util.getDateString(sharedPreferences.getString("date_format", "yyyy-MM-dd"))); break;
            case -98: commitText(Util.getTimeString(sharedPreferences.getString("time_format", "HH:mm:ss"))); break;
            case -61: performReplace(Util.increaseIndentation(getText(ic))); break;
            case -62: performReplace(Util.decreaseIndentation(getText(ic))); break;
            case -79: performReplace(Util.reverse(getText(ic))); break;
            case -136:
                if (!isSelecting()) {
                    sendKey(KeyEvent.KEYCODE_MOVE_END);
                    commitText(" ");
                    sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
                    sendKey(KeyEvent.KEYCODE_MOVE_END);
                }
                else {
                    performReplace(Util.linebreaksToSpaces(getText(ic)));
                }
            break;
            case -147: performReplace(Util.shuffleLines(getText(ic))); break;
            case -105: performReplace(Util.sortLines(getText(ic))); break;
            case -106: performReplace(Util.reverseLines(getText(ic))); break;
            case -137: performReplace(Util.splitWithSpaces(getText(ic))); break;
            case -138: performReplace(Util.removeSpaces(getText(ic))); break;
            case -139: performReplace(Util.trimEndingWhitespace(getText(ic))); break;
            case -140: performReplace(Util.trimTrailingWhitespace(getText(ic))); break;
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
            case -145: 
                ere = Util.countChars(getText(ic));
                performReplace(Util.uniqueChars(getText(ic))); 
                aft = Util.countChars(getText(ic));
                toastIt(ere+" → "+aft);
            break;
            case -104:
                String text = getText(ic);
                toastIt("Chars: "+Util.countChars(text)+"\t\tWords: "+Util.countWords(text)+"\t\tLines: "+Util.countLines(text));
            break;
            case -146: 
                ere = Util.countLines(getText(ic));
                performReplace(Util.uniqueLines(getText(ic))); 
                aft = Util.countLines(getText(ic));
                toastIt(ere+" → "+aft);
            break;

            case -152: performContextMenuAction(16908337); break; // pasteAsPlainText
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
            case -174: toastIt(Util.unidata(getText(ic))); break;
            case -182: showClipboard(); break;
            case -175: showActivity(Settings.ACTION_INPUT_METHOD_SETTINGS); break;
            case -176: showActivity(Settings.ACTION_HARD_KEYBOARD_SETTINGS); break;
            case -177: showActivity(Settings.ACTION_LOCALE_SETTINGS); break;
            case -178: showActivity(Settings.ACTION_SETTINGS); break;
            case -179: showActivity(Settings.ACTION_USER_DICTIONARY_SETTINGS); break;
            case -186: showActivity(Settings.ACTION_VOICE_INPUT_SETTINGS); break;
            case -211: performReplace(Util.addLineNumbers(getText(ic))); break;
            case -212: performReplace(Util.removeLineNumbers(getText(ic))); break;
            case -189: performReplace(Util.normalize(getText(ic))); break;
            case -190: performReplace(Util.slug(getText(ic))); break;
            case -100: performReplace(Util.replaceNbsp(getText(ic))); break;
            case -191: performReplace(Util.toAlternatingCase(getText(ic))); break;
            case -193: performReplace(Util.strikethrough(getText(ic))); break;
            case -194: performReplace(Util.underline(getText(ic))); break;
            case -195: performReplace(Util.sortChars(getText(ic))); break;
            case -196: performReplace(Util.shuffleChars(getText(ic))); break;
            case -192: 
                prevKeyboard = currentKeyboard;
                currentKeyboard = new CustomKeyboard(this, R.layout.ctrl,  "control",  "Control", "").setCategory(Category.Main);
                kv.setKeyboard(currentKeyboard);
                layouts.set(currentKeyboardID, currentKeyboard);
                setShifted(capsOn);
                toastIt(prevKeyboard.title);
            break;
            case -300:
                keepCtrlOpen = false;
                toastIt(findKeyboard(prevKeyboard.title));
                setShifted(capsOn);
            break;
            case -301: toastIt(findKeyboard("Function")); break;
            case -302: toastIt(findKeyboard("Utility")); break;
            case -303: toastIt(findKeyboard("Emoji")); break;
            case -304: toastIt(findKeyboard("Navigation")); break;
            case -305: toastIt(findKeyboard("Symbol")); break;
            case -306: toastIt(findKeyboard("Fonts")); break;
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
            case -450: toastIt(setKeyboardLayout(50)); break;
            case -451: toastIt(setKeyboardLayout(51)); break;
            case -452: toastIt(setKeyboardLayout(52)); break;
            case -453: toastIt(setKeyboardLayout(53)); break;
            default:
                if (Variables.isCtrl() || Variables.isAlt()) {
                    processKeyCombo(primaryCode);
                }
                else {
                    try {
                        handleCharacter(primaryCode);
                        redraw();
                    }
                    catch (Exception ignored) {}
                }
        }
    }

    public short getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int number) {
        rowNumber = (short)number;
        currentKeyboard.setRowNumber(rowNumber);
    }

    public double getHeightKeyModifier() {
        return (double)sharedPreferences.getInt("height", 50) / (double)50;
    }
}
