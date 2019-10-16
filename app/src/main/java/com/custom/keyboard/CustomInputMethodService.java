package com.custom.keyboard;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Toast;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

public class CustomInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener, SpellCheckerSession.SpellCheckerSessionListener {

    enum Category {Main, Util, Font, Misc}
    
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
    

    public ArrayList<String> clipboardHistory = new ArrayList<>(10);

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




    public static ArrayList<CustomKeyboard> layouts = new ArrayList<>(5);
    
    public ArrayList<CustomKeyboard> getLayouts() {
        return layouts;
    }

    public void populate() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        layouts.clear();

        layouts.add(new CustomKeyboard(this, R.layout.qwerty, "Default", "qwerty", 0));
        if (sharedPreferences.getBoolean("accents", true))        {layouts.add(new CustomKeyboard(this, R.layout.accents, "Accents", "◌̀◌́◌̂"));}
        if (sharedPreferences.getBoolean("arrow_keys", true))     {layouts.add(new CustomKeyboard(this, R.layout.arrow_keys, "Arrows"));}
        if (sharedPreferences.getBoolean("braille", true))        {layouts.add(new CustomKeyboard(this, R.layout.braille, "Braille", "⠟⠺⠑⠗⠞⠽"));}
        if (sharedPreferences.getBoolean("coding", true))         {layouts.add(new CustomKeyboard(this, R.layout.coding, "Coding", "∅⊤⊥"));}
        if (sharedPreferences.getBoolean("coptic", true))         {layouts.add(new CustomKeyboard(this, R.layout.coptic, "Coptic", "ⲑϣⲉⲣⲧⲯ"));}
        if (sharedPreferences.getBoolean("coptic_2", true))       {layouts.add(new CustomKeyboard(this, R.layout.coptic_2, "Coptic 2", "ⲳⲵⲷⲹⲽⲿ"));}
        if (sharedPreferences.getBoolean("cree", true))           {layouts.add(new CustomKeyboard(this, R.layout.cree, "Cree", "ᐁᐯᑌᑫᒉᒣ"));}
        if (sharedPreferences.getBoolean("cree_2", true))         {layouts.add(new CustomKeyboard(this, R.layout.cree_2, "Cree 2", "ᑫᗯᗴᖇTY"));}
        if (sharedPreferences.getBoolean("cyrillic", true))       {layouts.add(new CustomKeyboard(this, R.layout.cyrillic, "Cyrillic", "йцукен"));}
        if (sharedPreferences.getBoolean("demorse", true))        {layouts.add(new CustomKeyboard(this, R.layout.demorse, "Demorse"));}
        if (sharedPreferences.getBoolean("dvorak", true))         {layouts.add(new CustomKeyboard(this, R.layout.dvorak, "Dvorak", "pyfgcr"));}
        if (sharedPreferences.getBoolean("emoji", true))          {layouts.add(new CustomKeyboard(this, R.layout.emoji, "Emoji", "😀😁😂"));}
        if (sharedPreferences.getBoolean("enmorse", true))        {layouts.add(new CustomKeyboard(this, R.layout.enmorse, "Enmorse"));}
        if (sharedPreferences.getBoolean("etruscan", true))       {layouts.add(new CustomKeyboard(this, R.layout.etruscan, "Etruscan", "𐌀𐌁𐌂𐌃𐌄𐌅"));}
        if (sharedPreferences.getBoolean("extra", true))          {layouts.add(new CustomKeyboard(this, R.layout.extra, "Extra", "☳ツᰄ"));}
        if (sharedPreferences.getBoolean("fonts", true))          {layouts.add(new CustomKeyboard(this, R.layout.fonts, "Fonts", "🄰🅐🄐𝔸𝕬𝒜"));}
        if (sharedPreferences.getBoolean("function", true))       {layouts.add(new CustomKeyboard(this, R.layout.function, "Function", "ƒ⒳"));}
        if (sharedPreferences.getBoolean("futhark", true))        {layouts.add(new CustomKeyboard(this, R.layout.futhark, "Futhark", "ᚠᚢᚦᚨᚱᚲ"));}
        if (sharedPreferences.getBoolean("gothic", true))         {layouts.add(new CustomKeyboard(this, R.layout.gothic, "Gothic", "𐌵𐍈𐌴𐍂𐍄𐍅"));}
        if (sharedPreferences.getBoolean("greek", true))          {layouts.add(new CustomKeyboard(this, R.layout.greek, "Greek", "ςερτυθ"));}
        if (sharedPreferences.getBoolean("hex", true))            {layouts.add(new CustomKeyboard(this, R.layout.hex, "Hex", "\\uabcd"));}
        if (sharedPreferences.getBoolean("insular", true))        {layouts.add(new CustomKeyboard(this, R.layout.insular, "Insular", "ꝺꝼᵹꞃꞅꞇ"));}
        if (sharedPreferences.getBoolean("ipa", true))            {layouts.add(new CustomKeyboard(this, R.layout.ipa, "IPA", "ʔʕʘǁǂ"));}
        if (sharedPreferences.getBoolean("lisu", true))           {layouts.add(new CustomKeyboard(this, R.layout.lisu, "Lisu", "ⵚꓟꓱꓤꓕ⅄"));}
        if (sharedPreferences.getBoolean("macros", true))         {layouts.add(new CustomKeyboard(this, R.layout.macros, "Macros", "✐"));}
        if (sharedPreferences.getBoolean("math", true))           {layouts.add(new CustomKeyboard(this, R.layout.math, "Math", "+−×÷=%"));}
        if (sharedPreferences.getBoolean("mirror", true))         {layouts.add(new CustomKeyboard(this, R.layout.mirror, "Mirror", "poiuyt"));}
        if (sharedPreferences.getBoolean("navigation", true))     {layouts.add(new CustomKeyboard(this, R.layout.navigation, "Navigation", "⇄↑↓"));}
        if (sharedPreferences.getBoolean("numeric", true))        {layouts.add(new CustomKeyboard(this, R.layout.numeric, "Numeric", "123456"));}
        if (sharedPreferences.getBoolean("pointy", true))         {layouts.add(new CustomKeyboard(this, R.layout.pointy, "Pointy", "ᛩꟽⵉᚱⵜY"));}
        if (sharedPreferences.getBoolean("qwerty_plus", true))    {layouts.add(new CustomKeyboard(this, R.layout.qwerty_plus, "Qwerty+", "qwerty+"));}
        if (sharedPreferences.getBoolean("qwerty_symbols", true)) {layouts.add(new CustomKeyboard(this, R.layout.qwerty_symbols, "Qwerty&", "qwerty&"));}
        if (sharedPreferences.getBoolean("rotated", true))        {layouts.add(new CustomKeyboard(this, R.layout.rotated, "Rotated", "ʎʇɹəʍb"));}
        if (sharedPreferences.getBoolean("small_caps", true))     {layouts.add(new CustomKeyboard(this, R.layout.small_caps, "Caps", "ҩᴡᴇʀᴛʏ"));}
        if (sharedPreferences.getBoolean("stealth", true))        {layouts.add(new CustomKeyboard(this, R.layout.stealth, "Stealth", "ԛԝеrtу"));}
        if (sharedPreferences.getBoolean("strike", true))         {layouts.add(new CustomKeyboard(this, R.layout.strike, "Strike", "ꝗwɇꞧⱦɏ"));}
        if (sharedPreferences.getBoolean("symbol", true))         {layouts.add(new CustomKeyboard(this, R.layout.symbol, "Symbol", "!@#$%^"));}
        if (sharedPreferences.getBoolean("symbol_2", true))       {layouts.add(new CustomKeyboard(this, R.layout.symbol_2, "Symbol 2", "!@#$%^"));}
        if (sharedPreferences.getBoolean("tails", true))          {layouts.add(new CustomKeyboard(this, R.layout.tails, "Tails", "ɋꝡҽɽʈƴ"));}
        if (sharedPreferences.getBoolean("unicode", true))        {layouts.add(new CustomKeyboard(this, R.layout.unicode, "Unicode", "\\uxxxx"));}
        if (sharedPreferences.getBoolean("utility", true))        {layouts.add(new CustomKeyboard(this, R.layout.utility, "Utility", "/**/"));}

        int layoutLayout = R.layout.layouts;
        Editor editor = sharedPreferences.edit(); 
        StringBuilder layoutOrder = new StringBuilder();
        StringBuilder autoLabel;

        Collections.sort(layouts);

        layouts.add(new CustomKeyboard(this, R.layout.layouts, "Layouts"));

        for (int i = 0; i < layouts.size(); i++) {
            if (layouts.get(i).order == Integer.MAX_VALUE) {
                layouts.get(i).order = i;
            }
        }

        if (getKeyboard("Layouts") != null) {
            for (Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                if (key.codes[0] <= -400 && key.codes[0] >= -442) {
                    try {
                        if (layouts.get(-key.codes[0] - 400) != null && !layouts.get(-key.codes[0] - 400).name.equals("Layouts")) {
                            if (sharedPreferences.getBoolean("names", true)) {
                                key.label = layouts.get(-key.codes[0] - 400).name;
                            }
                            else key.label = layouts.get(-key.codes[0] - 400).label;
                        }
                        else key.label = "";
                    }
                    catch (Exception e) {
                        key.label = "";
                    }
                }
            }
        }


        int layoutCount = layouts.size()-1;
        int colCount = 6; // hardcoded for now
        int startRowCount = 7;
        int finalRowCount = (int)Math.ceil((double)layouts.size() / colCount);
        int row;
        int[] bounds = getBounds(getKeyboard("Layouts").getKeys());
        System.out.println("How many layouts? "+layoutCount);
        System.out.println("How many current rows? "+startRowCount);
        System.out.println("How many desired rows? "+finalRowCount);
        System.out.println("Bounds: "+Arrays.toString(bounds));

        int index = 0;
        for (Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
            if (key.codes[0] <= -400 && key.codes[0] >= -442) {
                row = (index / colCount);
                // 504 measured, need to filter keys to find value programmatically
                // key.y += ((504/(finalRowCount)) * row);
                // 504 / 3 = 168
                // row 0: 0, row 1: 168, row 2: 168*2
                // key.height = 504 / finalRowCount;
                if (row >= (startRowCount-(startRowCount-finalRowCount))) {
                    key.y += key.height;
                    key.height = 0;
                }
                index++;
            }
        }
        try {
            redraw();
        }
        catch (Exception ignored) {}

        for(CustomKeyboard layout : layouts) {
            layoutOrder.append(layout.name).append(":").append(layout.order).append("\n");
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
        editor.putString("layout_order", layoutOrder.toString().substring(0, layoutOrder.toString().length() - 1));
        editor.apply();
    }

    public int[] getBounds(List<Keyboard.Key> keys) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = 0, maxY = 0;
        for (Keyboard.Key key : keys) {
            // System.out.println(key.x+" "+key.y+" "+key.width+" "+key.height);
            if (key.x < minX) minX = key.x;
            if (key.y < minY) minY = key.y;

            if (key.x+key.width  > maxX) maxX = key.x;
            if (key.y+key.height > maxY) maxY = key.y;
        }
        return new int[] {minX, minY, maxX, maxY};
    }
    
    public void toastIt(String text) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (sharedPreferences.getBoolean("debug", false)) return;
        toast.cancel();
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public CustomKeyboard getKeyboard(String name) {
        int index = 0;
        for(CustomKeyboard layout : layouts) {
            if (layout.name.equals(name)) break;
            index++;
        }
        return layouts.get(index);
    }

    public String findKeyboard(String name) {
        populate();
        if (getRowNumber() == 7) {
            setRowNumber(6);
            currentKeyboard.setRowNumber(getRowNumber());
        }
        int index = 0;
        for(CustomKeyboard layout : layouts) {
            if (layout.name.equals(name)) {
                currentKeyboardID = index;
                break;
            }
            index++;
        }
        setKeyboard();
        return name;
    }

    public String setKeyboardLayout(int newKeyboardID) {
        try {
            if (newKeyboardID < layouts.size()) {
                currentKeyboardID = newKeyboardID;
                currentKeyboard = layouts.get(currentKeyboardID);
                String currentKeyboardLabel = currentKeyboard.label;
                if (currentKeyboard.name.equals("Layouts")) {
                    getKey(32).label = (layouts.size()-1)+" layouts";
                }
                else {
                    if (kv.isShifted()) currentKeyboardLabel = currentKeyboardLabel.toUpperCase();
                    else currentKeyboardLabel = currentKeyboardLabel.toLowerCase();
                    getKey(32).label = currentKeyboard.name+"\t•\t"+currentKeyboardLabel; // ·
                }
                kv.setKeyboard(currentKeyboard);
                kv.getCustomKeyboard().changeKeyHeight(getHeightKeyModifier());
                redraw();
            }
        }
        catch (Exception ignored) {}
        return currentKeyboard.name;
    }

    public void setKeyboard() {
        if (currentKeyboardID >= layouts.size()-1) currentKeyboardID = 0;
        setKeyboardLayout(currentKeyboardID);
    }

    public void prevKeyboard() {
        if (getRowNumber() == 7) {
            setRowNumber(6);
            currentKeyboard.setRowNumber(getRowNumber());
        }
        populate();
        currentKeyboardID--;
        if (currentKeyboardID < 0) currentKeyboardID = layouts.size()-2;
        setKeyboard();
    }

    public void nextKeyboard() {
        if (getRowNumber() == 7) {
            setRowNumber(6);
            currentKeyboard.setRowNumber(getRowNumber());
        }
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
        mScs = tsm.newSpellCheckerSession(null, null, this, true);
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
        if (getKeyboard("Function") != null) {
            for (Keyboard.Key key : getKeyboard("Function").getKeys()) {
                try {
                    if (key.codes[0] == -501) {key.label = Util.truncate(sharedPreferences.getString("k1", ""), 10);}
                    if (key.codes[0] == -502) {key.label = Util.truncate(sharedPreferences.getString("k2", ""), 10);}
                    if (key.codes[0] == -503) {key.label = Util.truncate(sharedPreferences.getString("k3", ""), 10);}
                    if (key.codes[0] == -504) {key.label = Util.truncate(sharedPreferences.getString("k4", ""), 10);}
                    if (key.codes[0] == -505) {key.label = Util.truncate(sharedPreferences.getString("k5", ""), 10);}
                    if (key.codes[0] == -506) {key.label = Util.truncate(sharedPreferences.getString("k6", ""), 10);}
                    if (key.codes[0] == -507) {key.label = Util.truncate(sharedPreferences.getString("k7", ""), 10);}
                    if (key.codes[0] == -508) {key.label = Util.truncate(sharedPreferences.getString("k8", ""), 10);}
                }
                catch(Exception e) {
                    key.label = "";
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateInputView() {
        populate();
        mInputView = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.qwerty, null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setPreviewEnabled(false);
        return mInputView;
    }

    @Override
    public View onCreateCandidatesView() {
        mCandidateView = new CustomView(this);
        mCandidateView.setService(this);
        setTheme();
        Paint mPaint = new Paint();
        ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(mDefaultFilter);
        mPaint.setColorFilter(filterInvert);
        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        populate();
        return mCandidateView;
    }

    public Keyboard.Key getKey(int primaryCode) {
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
        setTheme();
        mComposing.setLength(0);
        mCompletions = null;
        if (sharedPreferences.getBoolean("bord", false)) kv = (CustomKeyboardView) getLayoutInflater().inflate(R.layout.keyboard_borders, null);
        else kv = (CustomKeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
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
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == -400 || keyCode == -498 || keyCode == -499) {
            InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            if (imeManager != null) {
                imeManager.showInputMethodPicker();
                return true;
            }
        }
        return false;
    }

    long time;

    @Override
    public void onPress(int primaryCode) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        time = System.nanoTime();
        if (sharedPreferences.getBoolean("vib", false)) {
            Vibrator v = (Vibrator)getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(20);
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        time = (System.nanoTime() - time) / 1000000;
        if (time > 300) {
            if (primaryCode == -400 || primaryCode == -498 || primaryCode == -499) {
                InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                if (imeManager != null) imeManager.showInputMethodPicker();
            }
            else if (primaryCode == -95) selectAll();
            else if (primaryCode == -99) {
                ic = getCurrentInputConnection();
                ic.deleteSurroundingText(MAX, MAX);
            }
            else if (primaryCode == -101) toastIt(findKeyboard("Utility"));
            else if (primaryCode == -102) toastIt(findKeyboard("Function"));
            else if (primaryCode == -67)  toastIt(findKeyboard("Navigation"));
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
            ic.commitText(mComposing, mComposing.length());
            mComposing.setLength(0);
        }
    }

    public void onText(CharSequence text) {
        ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.beginBatchEdit();
        if (mComposing.length() > 0) commitTyped(ic);
        ic.commitText(text, 0);
        ic.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void updateCandidates() {
        if (!mCompletionOn) {
            if (mComposing.length() > 0) {
                ArrayList<String> list = new ArrayList<>();
                list.add(mComposing.toString());
                mScs.getSentenceSuggestions(new TextInfo[]{new TextInfo(mComposing.toString())}, 5);
                setSuggestions(list, true, true);
            }
            else setSuggestions(null, false, false);
        }
    }

    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {
        if (suggestions != null && suggestions.size() > 0) setCandidatesViewShown(true);
        else if (isExtractViewShown()) setCandidatesViewShown(true);
        mSuggestions = suggestions;
        if (mCandidateView != null) mCandidateView.setSuggestions(suggestions, completions, typedWordValid);
    }

    public void pickSuggestionManually(int index) {
        ic = getCurrentInputConnection();
        if (mCompletionOn && mCompletions != null && index >= 0 && index < mCompletions.length) {
            CompletionInfo ci = mCompletions[index];
            ic.commitCompletion(ci);
            if (mCandidateView != null) mCandidateView.clear();
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
        else if (mComposing.length() > 0) {
            if (mPredictionOn && mSuggestions != null && index >= 0) mComposing.replace(0, mComposing.length(), mSuggestions.get(index));
            commitTyped(ic);
        }
    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {
        final StringBuilder sb = new StringBuilder();
        for (SuggestionsInfo result : results) {
            final int len = result.getSuggestionsCount();
            sb.append('\n');
            int j;
            for (j = 0; j < len; ++j) sb.append(",").append(result.getSuggestionAt(j));
            sb.append(" (").append(len).append(")");
        }
    }

    public void dumpSuggestionsInfoInternal(final List<String> sb, final SuggestionsInfo si) {
        final int len = si.getSuggestionsCount();
        for (int j = 0; j < len; ++j) sb.add(si.getSuggestionAt(j));
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {
        try {
            final List<String> sb = new ArrayList<>();
            for (final SentenceSuggestionsInfo ssi : results) {
                for (int j = 0; j < ssi.getSuggestionsCount(); ++j) {
                    dumpSuggestionsInfoInternal(sb, ssi.getSuggestionsInfoAt(j));
                }
            }
            setSuggestions(sb, true, true);
        }
        catch (Exception ignored) {}
    }

    public void processKeyCombo(int keycode) {
        ic = getCurrentInputConnection();
        if (Variables.isCtrl() || Variables.isAlt()) {
            if (Variables.isCtrl() && Variables.isAlt()) {
                ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, Codes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   Codes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            }
            else {
                if (Variables.isCtrl()) {
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, Codes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   Codes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                }
                if (Variables.isAlt()) {
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, Codes.getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                    ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   Codes.getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                }
            }
        }
    }

    public void setInputType() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        EditorInfo attribute = getCurrentInputEditorInfo();
        if (Objects.equals(sharedPreferences.getString("start", "1"), "1")) {
            switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
                case InputType.TYPE_TEXT_VARIATION_URI:
                case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
                case InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS: currentKeyboard = new CustomKeyboard(this, R.layout.function); break;
                case InputType.TYPE_CLASS_NUMBER:
                case InputType.TYPE_CLASS_DATETIME:
                case InputType.TYPE_CLASS_PHONE: currentKeyboard = new CustomKeyboard(this, R.layout.numeric); break;
                default: currentKeyboard = layouts.get(0); break;
            }
        }
        if (kv != null) {kv.setKeyboard(currentKeyboard);}
    }

    public void sendCustomKey(String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext()); // this?
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        int cursorLocation = getSelectionStart();
        CharSequence ins = sharedPreferences.getString(key, "");
        ic.beginBatchEdit();
        ic.commitText(ins, cursorLocation + (ins != null ? ins.length() : 0));
        ic.endBatchEdit();
    }

    public void performReplace(String newText) {
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0) {
            int a = getSelectionStart();
            int b = getSelectionStart()+newText.length();
            ic.commitText(newText, b);
            ic.setSelection(a, b);
        }
    }

    public String getText(InputConnection ic) {
        CharSequence text = ic.getSelectedText(0);
        if (text == null) return "";
        return (String)text;
    }

    public void processKeyList(int[] keys) {
        ic = getCurrentInputConnection();
        for (int key : keys) {
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, key));
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, key));
        }
    }

    public void sendKeyUpDown(int primaryCode) {
        ic = getCurrentInputConnection();
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, primaryCode));
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   primaryCode));
    }

    public String getLastLine() {
        ic = getCurrentInputConnection();
        String[] lines = ic.getTextBeforeCursor(MAX, 0).toString().split("\n");
        if (lines.length < 2) return ic.getTextBeforeCursor(MAX, 0).toString();
        return lines[lines.length - 1];
    }

    public String getNextLine() {
        ic = getCurrentInputConnection();
        String[] lines = ic.getTextAfterCursor(MAX, 0).toString().split("\n");
        if (lines.length < 2) return ic.getTextAfterCursor(MAX, 0).toString();
        return lines[0];
    }

    public void wordLast(int n) {
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

    public void wordNext(int n) {
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
        sendKeyUpDown(KeyEvent.KEYCODE_MOVE_HOME);
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
            sendKeyUpDown(primaryCode);
            sendKeyUpDown(primaryCode);
            sendKeyUpDown(primaryCode);
            sendKeyUpDown(primaryCode);
        }
        else if (!isSelecting() && primaryCode == KeyEvent.KEYCODE_DPAD_RIGHT && String.valueOf(ic.getTextAfterCursor(4, 0)).equals("    ")) {
            sendKeyUpDown(primaryCode);
            sendKeyUpDown(primaryCode);
            sendKeyUpDown(primaryCode);
            sendKeyUpDown(primaryCode);
        }
        else {
            if (Variables.isSelect()) ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT));
            sendKeyUpDown(primaryCode);
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
        if (sharedPreferences.getBoolean("caps", false)) {
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
        if (!isSelecting()
             && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals("    ")
             && sharedPreferences.getBoolean("spaces", true)) {
            ic.deleteSurroundingText(4, 0);
        }
        else if (length > 1) {
            mComposing.delete(length - 1, length);
            ic.setComposingText(mComposing, 1);
            updateCandidates();
        }
        else if (length > 0) {
            mComposing.setLength(0);
            ic.commitText("", 0);
            updateCandidates();
        }
        else sendKeyUpDown(KeyEvent.KEYCODE_DEL);
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void handleDelete() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ic = getCurrentInputConnection();
        final int length = mComposing.length();
        if (!isSelecting()
             && String.valueOf(ic.getTextAfterCursor(4, 0)).equals("    ")
             && sharedPreferences.getBoolean("spaces", true)) {
            ic.deleteSurroundingText(0, 4);
        }
        else if (length > 1) {
            mComposing.delete(length, length - 1);
            ic.setComposingText(mComposing, 1);
            updateCandidates();
        }
        else if (length > 0) {
            mComposing.setLength(0);
            ic.commitText("", 0);
            updateCandidates();
        }
        else sendKeyUpDown(KeyEvent.KEYCODE_FORWARD_DEL);
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void handleCharacter(int primaryCode) {
        ic = getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        primaryCode = Codes.handleCharacter(kv, primaryCode);
        if (sharedPreferences.getBoolean("dot", false)) {
            if (primaryCode == 32) {
                if (String.valueOf(ic.getTextBeforeCursor(1, 0)).equals(" ")) {
                    ic.deleteSurroundingText(1, 0);
                    ic.commitText(".", 0);
                    setCapsOn(true);
                    firstCaps = true;
                }
            }
        }
        if (sharedPreferences.getBoolean("pairs", false)) {
            if (Util.contains("({\"[", primaryCode)) {
                String code = String.valueOf((char)primaryCode);
                int cursorOffset = 0;
                if (code.equals("("))  ic.commitText("()",   cursorOffset);
                if (code.equals("["))  ic.commitText("[]",   cursorOffset);
                if (code.equals("{"))  ic.commitText("{}",   cursorOffset);
                if (code.equals("\"")) ic.commitText("\"\"", cursorOffset);
                sendKeyUpDown(KeyEvent.KEYCODE_DPAD_LEFT);
                return;
            }
        }
        if (sharedPreferences.getBoolean("auto", false)) {
            if (!Util.isAlphabet(primaryCode) || primaryCode == 32 || primaryCode == 10) {
                for (Map.Entry<String,String> entry : Replacements.getData().entrySet()) {
                    String escapedEntryKey = entry.getKey().replace("\\b", "").replace("\\n", "").replace("^",   "");
                    if (String.valueOf(ic.getTextBeforeCursor(escapedEntryKey.length(), 0)).matches(entry.getKey())) {
                        ic.deleteSurroundingText(escapedEntryKey.length(), 0);
                        ic.commitText(entry.getValue(), 0);
                        break;
                    }
                    else if (Util.toTitleCase(String.valueOf(ic.getTextBeforeCursor(escapedEntryKey.length(), 0))).matches(Util.toTitleCase(entry.getKey()))) {
                        ic.deleteSurroundingText(escapedEntryKey.length(), 0);
                        ic.commitText(Util.toTitleCase(entry.getValue()), 0);
                        break;
                    }
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
            updateCandidates();
        }
        if (mPredictionOn && Util.isWordSeparator(primaryCode)) {
            char code = (char)primaryCode;
            if (Character.isLetter(code) && firstCaps || Character.isLetter(code) && Variables.isShift()) {
                code = Character.toUpperCase(code);
            }
            ic.setComposingRegion(0, 0);
            ic.commitText(String.valueOf(code), 1);
            firstCaps = false;
            setCapsOn(false);
        }
        if (!mPredictionOn) {
            ic.setComposingRegion(0, 0);
            ic.commitText(String.valueOf(Character.toChars(primaryCode)), 1);
            firstCaps = false;
            setCapsOn(false);
        }
        if (isSelecting()) ic.setSelection(Variables.cursorStart, getSelectionEnd());
    }


    public String getLastWord() {
        ic = getCurrentInputConnection();
        String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
        if (words.length < 2) return ic.getTextBeforeCursor(MAX, 0).toString();
        return words[words.length - 1];
    }

    public String getLastMorse() {
        ic = getCurrentInputConnection();
        return ic.getTextBeforeCursor(8, 0).toString().replaceAll("[^·-]", "");
    }

    public void redraw() {
        kv.invalidateAllKeys();
        kv.draw(new Canvas());
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String record;
        if (currentKeyboard.name.equals("Enmorse") && !Util.charToMorse(String.valueOf((char)primaryCode)).equals("")) {
            String res = Util.charToMorse(String.valueOf((char)primaryCode));
            if (kv.isShifted()) res = res.toUpperCase();
            toastIt((char)primaryCode+" "+res);
            ic.commitText(res+" ", 0);
            return;
        }
        if (currentKeyboard.name.equals("Enmorse") && primaryCode == 32) {
            ic.commitText("  ", 0);
            return;
        }
        if (currentKeyboard.name.equals("Demorse") && "·- ".contains(String.valueOf((char)primaryCode))) {
            if (primaryCode == 32) {
                String res = getLastMorse();
                if (kv.isShifted()) res = res.toUpperCase();
                ic.deleteSurroundingText(res.length(), 0);
                ic.commitText(Util.morseToChar(res)+"", 0);
                getKey(32).label = " ";
                redraw();
                return;
            }
            String res = String.valueOf((char)primaryCode);
            if (kv.isShifted()) res = res.toUpperCase();
            ic.commitText(res, 0);
            getKey(32).label = getLastMorse() + " " + Util.morseToChar(getLastMorse());
            redraw();
            return;
        }
        if (currentKeyboard.name.equals("Unicode") && !Util.contains(Codes.hexPasses, primaryCode)) {
            if (primaryCode == -2001) performReplace(Util.convertFromUnicodeToNumber(getText(ic)));
            if (primaryCode == -2002) performReplace(Util.convertFromNumberToUnicode(getText(ic)));
            if (Util.contains(Codes.hexCaptures, primaryCode)) {
                if (hexBuffer.length() > 3) hexBuffer = "";
                hexBuffer += (char)primaryCode;
            }
            if (primaryCode == -2003) ic.commitText(StringUtils.leftPad(hexBuffer, 4, "0"), 0);

            if (primaryCode == -2004) ic.commitText(String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(hexBuffer, 4, "0"))), 0);
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
            case 10:
                EditorInfo curEditor = getCurrentInputEditorInfo();
                if (kv.getKeyboard().isShifted()) {ic.commitText("\n", 1);}
                if (sharedPreferences.getBoolean("spaces", true)) {
                    // String indent = Util.getIndentation(getLastLine());
                    // if (indent.length() > 0) {ic.commitText("\n"+indent, 0); break;}
                }
                switch (curEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
                    case EditorInfo.IME_ACTION_GO: ic.performEditorAction(EditorInfo.IME_ACTION_GO); break;
                    case EditorInfo.IME_ACTION_SEARCH: ic.performEditorAction(EditorInfo.IME_ACTION_SEARCH); break;
                    default: sendKeyUpDown(KeyEvent.KEYCODE_ENTER); break;
                }
            break;
            case 7:
                if (sharedPreferences.getBoolean("spaces", true)) {ic.commitText("    ", 0);}
                else {ic.commitText("    ", 0);}
            break;
            case 6: ic.commitText("'s", 0); break;
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
                    ic.commitText(text, b);
                    ic.setSelection(a, b);
                    setKeyboard();
                    redraw();
                }
                else {
                    switch (currentKeyboard.name) {
                        case "Rotated":
                            if (kv.isShifted()) {currentKeyboard = new CustomKeyboard(this, R.layout.rotated, "Rotated", "bʍəɹʇʎ");}
                            else {currentKeyboard = new CustomKeyboard(this, R.layout.rotated_shift, "Rotated", "Ò𐊰ƎꓤꞱ⅄");}
                            kv.setKeyboard(currentKeyboard);
                        break;
                        case "Caps":
                            if (kv.isShifted()) {currentKeyboard = new CustomKeyboard(this, R.layout.small_caps, "Caps", "ҩᴡᴇʀᴛʏ");}
                            else {currentKeyboard = new CustomKeyboard(this, R.layout.small_caps_shift, "Caps", "ҩᴡᴇʀᴛʏ");}
                            kv.setKeyboard(currentKeyboard);
                        break;
                        case "Stealth":
                            if (kv.isShifted()) {currentKeyboard = new CustomKeyboard(this, R.layout.stealth, "Stealth", "ԛԝеrtу");}
                            else {currentKeyboard = new CustomKeyboard(this, R.layout.stealth_shift, "Stealth", "ԚԜЕꓣТҮ");}
                            kv.setKeyboard(currentKeyboard);
                        break;
                    }
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
                    if (!currentKeyboard.name.equals("Full_Width")
                     && !currentKeyboard.name.equals("Rotated")
                     && !currentKeyboard.name.equals("Sans")
                     && !currentKeyboard.name.equals("Caps")
                     && !currentKeyboard.name.equals("Stealth")
                    ) {
                        setKeyboard();
                    }
                    redraw();
                }
            break;
            case -2: hide(); break;
            case -5: 
                if (currentKeyboard.name.equals("Rotated") || currentKeyboard.name.equals("Lisu")) handleDelete();
                else handleBackspace();
            break;
            case -7: 
                if (currentKeyboard.name.equals("Rotated") || currentKeyboard.name.equals("Lisu")) handleBackspace();
                else handleDelete();
            break;
            case -8: selectAll(); break;
            case -9:
                if (!isSelecting()) selectLine();
                record = ic.getSelectedText(0) != null ? ic.getSelectedText(0).toString() : "";
                toastIt(record);
                if (!record.equals("")) {
                    clipboardHistory.add(record);
                    performReplace("");
                }
                else {
                    // sendKeyUpDown(KeyEvent.KEYCODE_CUT);
                }
            break;
            case -10:
                if (!isSelecting()) selectLine();
                record = ic.getSelectedText(0) != null ? ic.getSelectedText(0).toString() : "";
                toastIt(record);
                if (!record.equals("")) {
                    clipboardHistory.add(record);
                }
                else {
                    // sendKeyUpDown(KeyEvent.KEYCODE_COPY);
                }
            break;
            case -11:
                record = clipboardHistoryHas() ? clipboardHistoryGet() : "";
                toastIt(record);
                if (!record.equals("")) {
                    ic.commitText(record, 0);
                }
                else {
                    // sendKeyUpDown(KeyEvent.KEYCODE_PASTE);
                }
            break;
            case -12: Variables.toggleBolded(); break;
            case -13: Variables.toggleItalic(); break;
            case -14: Variables.setAllEmOff(); break;
            case -15: sendKeyUpDown(KeyEvent.KEYCODE_VOLUME_DOWN); break;
            case -16: sendKeyUpDown(KeyEvent.KEYCODE_VOLUME_UP); break;
            case -17: sendKeyUpDown(KeyEvent.KEYCODE_CAMERA); break;
            case -18: sendKeyUpDown(KeyEvent.KEYCODE_EXPLORER); break;
            case -19: sendKeyUpDown(KeyEvent.KEYCODE_MENU); break;
            case -20: sendKeyUpDown(KeyEvent.KEYCODE_NOTIFICATION); break;
            case -21: sendKeyUpDown(KeyEvent.KEYCODE_SEARCH); break;
            case -22: navigate(KeyEvent.KEYCODE_PAGE_UP); break;
            case -23: navigate(KeyEvent.KEYCODE_PAGE_DOWN); break;
            case -24: sendKeyUpDown(KeyEvent.KEYCODE_BUTTON_START); break;
            case -25: navigate(KeyEvent.KEYCODE_MOVE_HOME); break;
            case -26: navigate(KeyEvent.KEYCODE_MOVE_END); break;
            case -27: sendKeyUpDown(KeyEvent.KEYCODE_SETTINGS); break;
            case -28: sendKeyUpDown(KeyEvent.KEYCODE_APP_SWITCH); break;
            case -29: sendKeyUpDown(KeyEvent.KEYCODE_LANGUAGE_SWITCH); break;
            case -30: sendKeyUpDown(KeyEvent.KEYCODE_BRIGHTNESS_DOWN); break;
            case -31: sendKeyUpDown(KeyEvent.KEYCODE_BRIGHTNESS_UP); break;
            case -32: sendKeyUpDown(KeyEvent.KEYCODE_NAVIGATE_PREVIOUS); break;
            case -33: sendKeyUpDown(KeyEvent.KEYCODE_NAVIGATE_NEXT); break;
            case -34: sendKeyUpDown(KeyEvent.KEYCODE_CLEAR); break;
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
            case -48: performReplace(Util.increaseIndentation(getText(ic))); break;
            case -49: performReplace(Util.decreaseIndentation(getText(ic))); break;
            case -50: Variables.toggleReflected(); break;
            case -51: performReplace(Util.convertNumberBase(getText(ic), 2, 10)); break;
            case -52: performReplace(Util.convertNumberBase(getText(ic), 10, 2)); break;
            case -53: performReplace(Util.convertNumberBase(getText(ic), 8, 10)); break;
            case -54: performReplace(Util.convertNumberBase(getText(ic), 10, 8)); break;
            case -55: performReplace(Util.convertNumberBase(getText(ic), 16, 10)); break;
            case -56: performReplace(Util.convertNumberBase(getText(ic), 10, 16)); break;
            case -57: Variables.toggleCaps(); break;
            case -58: performReplace(Util.camelToSnake(getText(ic))); break;
            case -59: performReplace(Util.snakeToCamel(getText(ic))); break;
            case -60: performReplace(Util.doubleCharacters(getText(ic))); break;
            case -67: Variables.toggleSelect(getSelectionStart()); break;
            case -68: Variables.toggle127280(); break;
            case -69: Variables.toggle127312(); break;
            case -70: Variables.toggle127344(); break;
            case -71: Variables.toggle127462(); break;
            case -72: Variables.toggle009372(); break;
            case -73: Variables.toggle009398(); break;
            case -76: selectNone(); break;
            case -77: wordLast(1); break;
            case -78: wordNext(1); break;
            case -79: performReplace(Util.reverse(getText(ic))); break;
            case -80: ic.commitText(getClipboardEntry(0), 0); break;
            case -81: ic.commitText(getClipboardEntry(1), 0); break;
            case -82: ic.commitText(getClipboardEntry(2), 0); break;
            case -83: ic.commitText(getClipboardEntry(3), 0); break;
            case -84: ic.commitText(getClipboardEntry(4), 0); break;
            case -85: ic.commitText(getClipboardEntry(5), 0); break;
            case -86: ic.commitText(getClipboardEntry(6), 0); break;
            case -87: ic.commitText(getClipboardEntry(7), 0); break;    
            case -88: sendKeyUpDown(KeyEvent.KEYCODE_DPAD_CENTER); break;
            case -89: sendKeyUpDown(KeyEvent.ACTION_UP); break;
            case -90: sendKeyUpDown(KeyEvent.ACTION_DOWN); break;
            case -91: 
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleJavaComment(getText(ic))); 
            break; 
            case -92: 
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleHtmlComment(getText(ic))); 
            break;
            case -93: ic.commitText(Util.flipACoin(), 0); break;
            case -94: ic.commitText(Util.rollADie(), 0); break;
            case -95: selectLine(); break;
            case -96: ic.commitText(getNextLine()+"\n"+getLastLine(), 0); break;
            case -97: ic.commitText(Util.getDateString(sharedPreferences.getString("date_format", "yyyy-MM-dd")), 0); break;
            case -98: ic.commitText(Util.getTimeString(sharedPreferences.getString("time_format", "HH:mm:ss")), 0); break;
            case -99:
                if (!isSelecting()) selectLine();
                sendKeyUpDown(KeyEvent.KEYCODE_DEL);
            break;
            case -101: prevKeyboard(); break;
            case -102: nextKeyboard(); break;
            case -103:
                ic.commitText(Util.castALot(), 0);
                String trigram;
                if (!Util.buildTrigram(ic.getTextBeforeCursor(3, 0).toString()).equals("")) {
                    trigram = Util.buildTrigram(ic.getTextBeforeCursor(3, 0).toString());
                    ic.deleteSurroundingText(3, 0);
                    ic.commitText(trigram, 0);
                }
                if (!Util.buildTrigram(ic.getTextBeforeCursor(2, 0).toString()).equals("")) {
                    trigram = Util.buildTrigram(ic.getTextBeforeCursor(2, 0).toString());
                    ic.deleteSurroundingText(2, 0);
                    ic.commitText(trigram, 0);
                }
                if (!Util.buildDigram(ic.getTextBeforeCursor(2, 0).toString()).equals("")) {
                    trigram = Util.buildDigram(ic.getTextBeforeCursor(2, 0).toString());
                    ic.deleteSurroundingText(2, 0);
                    ic.commitText(trigram, 0);
                }
                if (!Util.buildHexagram(ic.getTextBeforeCursor(2, 0).toString()).equals("")) {
                    trigram = Util.buildHexagram(ic.getTextBeforeCursor(2, 0).toString());
                    ic.deleteSurroundingText(2, 0);
                    ic.commitText(trigram, 0);
                }
            break;
            case -107: navigate(KeyEvent.KEYCODE_DPAD_UP); break;
            case -108: navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -109: navigate(KeyEvent.KEYCODE_DPAD_DOWN); break;
            case -111: navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -112: sendKeyUpDown(KeyEvent.KEYCODE_ESCAPE); break;
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
                    if (Variables.isAlt()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_ALT_ON));
                    if (Variables.isCtrl()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_CTRL_ON));
                }
                else {sendKeyUpDown(KeyEvent.KEYCODE_DEL);}
            break;
            case -122:
                if (Variables.isCtrl() || Variables.isAlt()) {
                    if (Variables.isCtrl() && Variables.isAlt()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                    if (Variables.isAlt()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_ALT_ON));
                    if (Variables.isCtrl()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_CTRL_ON));
                }
                else sendKeyUpDown(KeyEvent.KEYCODE_TAB);
            break;
            case -300: toastIt(findKeyboard("Default")); break;
            case -301: toastIt(findKeyboard("Function")); break;
            case -302: toastIt(findKeyboard("Utility")); break;
            case -303: toastIt(findKeyboard("Emoji")); break;
            case -304: toastIt(findKeyboard("Navigation")); break;
            case -305: toastIt(findKeyboard("Symbol")); break;
            case -306: toastIt(findKeyboard("Fonts")); break;
            case -399:
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
            case -498:
                InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                if (imeManager != null) {imeManager.showInputMethodPicker();}
            break;
            case -499: 
                if (currentKeyboardID != 0) {
                    setKeyboardLayout(0); 
                }
                else {
                    setKeyboardLayout(layouts.size()-1); 
                }
            break;
            case -509: navigate(KeyEvent.KEYCODE_DPAD_UP); navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -510: navigate(KeyEvent.KEYCODE_DPAD_UP); navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -511: navigate(KeyEvent.KEYCODE_DPAD_DOWN); navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -512: navigate(KeyEvent.KEYCODE_DPAD_DOWN); navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -999: clearAll(); break;
            case -1000: performReplace(getText(ic).toUpperCase()); break;
            case -1001: performReplace(Util.toTitleCase(getText(ic))); break;
            case -1002: performReplace(getText(ic).toLowerCase()); break;
            case -1003: performReplace(Util.underscoresToSpaces(getText(ic))); break;
            case -1004: performReplace(Util.spacesToUnderscores(getText(ic))); break;
            case -1005: performReplace(Util.dashesToSpaces(getText(ic))); break;
            case -1006: performReplace(Util.spacesToDashes(getText(ic))); break;
            case -1007: performReplace(Util.spacesToLinebreaks(getText(ic))); break;
            case -1008: performReplace(Util.linebreaksToSpaces(getText(ic))); break;
            case -1009: performReplace(Util.spacesToTabs(getText(ic))); break;
            case -1010: performReplace(Util.tabsToSpaces(getText(ic))); break;
            case -1011: performReplace(Util.splitWithLinebreaks(getText(ic))); break;
            case -1012:
                if (!isSelecting()) {
                    sendKeyUpDown(KeyEvent.KEYCODE_MOVE_END);
                    sendKeyUpDown(KeyEvent.KEYCODE_FORWARD_DEL);
                }
                else {
                    performReplace(Util.removeLinebreaks(getText(ic)));
                }
            break;
            case -1013: performReplace(Util.splitWithSpaces(getText(ic))); break;
            case -1014: performReplace(Util.removeSpaces(getText(ic))); break;
            case -1015: performReplace(Util.trimEndingWhitespace(getText(ic))); break;
            case -1016: performReplace(Util.trimTrailingWhitespace(getText(ic))); break;
            default:
                if (Variables.isCtrl() || Variables.isAlt()) { processKeyCombo(primaryCode); }
                else {
                    try {
                        handleCharacter(primaryCode);
                        // getKey(32).popupResId = "";
                        // getKey(32).height = (int)(getKey(32).height * 0.9);
                        // getKey(32).width = (int)(getKey(32).width * 0.8);
                        // getKey(32).gap = (int)(getKey(32).gap * 0.2);
                        redraw();
                        if (Variables.isReflected() || sharedPreferences.getBoolean("cursor_left", false) || currentKeyboard.name.equals("Rotated") || currentKeyboard.name.equals("Lisu")) {
                            sendKeyUpDown(KeyEvent.KEYCODE_DPAD_LEFT);
                        }
                    }
                    catch (Exception ignored) {}
                }
        }
        try {
            if (sharedPreferences.getBoolean("caps", true)) {
                if (ic.getTextBeforeCursor(2, 0).toString().contains(". ") || ic.getTextBeforeCursor(2, 0).toString().contains("? ") || ic.getTextBeforeCursor(2, 0).toString().contains("! ")) {
                    setCapsOn(true);
                    firstCaps = true;
                }
            }
        }
        catch (Exception ignored) {}
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
