package com.custom.keyboard;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


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

    static void print(Object ...a) {
        for (Object i: a) System.out.print(i + " ");
        System.out.println();

        // for(int i = 0; i < a.length; i++) {
        //     System.out.print(a[i] + " ");
        //     // if (i % 2 == 1) System.out.println();
        // }
    }

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
        if (sharedPreferences.getBoolean("accents",      true)) {layouts.add(new CustomKeyboard(this, R.layout.accents,     "accents",     "Accents",    "◌̀◌́◌̂"));}
        if (sharedPreferences.getBoolean("armenian",     true)) {layouts.add(new CustomKeyboard(this, R.layout.armenian,    "armenian",    "Armenian",   "աբգդեզ"));}
        if (sharedPreferences.getBoolean("arrows",       true)) {layouts.add(new CustomKeyboard(this, R.layout.arrows,      "arrows",      "Arrows",     "",                -4));}
        if (sharedPreferences.getBoolean("braille",      true)) {layouts.add(new CustomKeyboard(this, R.layout.braille,     "braille",     "Braille",    "⠟⠺⠑⠗⠞⠽"));}
        if (sharedPreferences.getBoolean("caps",         true)) {layouts.add(new CustomKeyboard(this, R.layout.caps,        "caps",        "Caps",       "ҩᴡᴇʀᴛʏ"));}
        if (sharedPreferences.getBoolean("cherokee_1",   true)) {layouts.add(new CustomKeyboard(this, R.layout.cherokee_1,  "cherokee_1",  "Cherokee",   "ꭰꭱꭲꭳꭴꭵ"));}
        if (sharedPreferences.getBoolean("coding",       true)) {layouts.add(new CustomKeyboard(this, R.layout.coding,      "coding",      "Coding",     "∅⊤⊥"));}
        if (sharedPreferences.getBoolean("coptic",       true)) {layouts.add(new CustomKeyboard(this, R.layout.coptic,      "coptic",      "Coptic",     "ⲑϣⲉⲣⲧⲯ"));}
        if (sharedPreferences.getBoolean("cree",         true)) {layouts.add(new CustomKeyboard(this, R.layout.cree,        "cree",        "Cree",       "ᐁᐯᑌᑫᒉᒣ"));}
        if (sharedPreferences.getBoolean("cyrillic",     true)) {layouts.add(new CustomKeyboard(this, R.layout.cyrillic,    "cyrillic",    "Cyrillic",   "йцукен"));}
        if (sharedPreferences.getBoolean("demorse",      true)) {layouts.add(new CustomKeyboard(this, R.layout.demorse,     "demorse",     "Demorse",    ""));}
        if (sharedPreferences.getBoolean("deseret",      true)) {layouts.add(new CustomKeyboard(this, R.layout.deseret,     "deseret",     "Deseret",    "𐐨𐐩𐐪𐐫𐐬𐐭"));}
        if (sharedPreferences.getBoolean("drawing",      true)) {layouts.add(new CustomKeyboard(this, R.layout.drawing,     "drawing",     "Drawing",    "├─┤"));}
        if (sharedPreferences.getBoolean("dvorak",       true)) {layouts.add(new CustomKeyboard(this, R.layout.dvorak,      "dvorak",      "Dvorak",     "pyfgcr"));}
        if (sharedPreferences.getBoolean("emoji",        true)) {layouts.add(new CustomKeyboard(this, R.layout.emoji,       "emoji",       "Emoji",      "😀😁😂"));}
        if (sharedPreferences.getBoolean("enmorse",      true)) {layouts.add(new CustomKeyboard(this, R.layout.enmorse,     "enmorse",     "Enmorse",    ""));}
        if (sharedPreferences.getBoolean("etruscan",     true)) {layouts.add(new CustomKeyboard(this, R.layout.etruscan,    "etruscan",    "Etruscan",   "𐌀𐌁𐌂𐌃𐌄𐌅"));}
        if (sharedPreferences.getBoolean("extra",        true)) {layouts.add(new CustomKeyboard(this, R.layout.extra,       "extra",       "Extra",      "☳ツᰄ",             -4));}
        if (sharedPreferences.getBoolean("fonts",        true)) {layouts.add(new CustomKeyboard(this, R.layout.fonts,       "fonts",       "Fonts",      "🄰🅐🄐𝔸𝕬𝒜"));}
        if (sharedPreferences.getBoolean("function",     true)) {layouts.add(new CustomKeyboard(this, R.layout.function,    "function",    "Function",   "ƒ(x)",            -2));} // ⒳
        if (sharedPreferences.getBoolean("futhark",      true)) {layouts.add(new CustomKeyboard(this, R.layout.futhark,     "futhark",     "Futhark",    "ᚠᚢᚦᚨᚱᚲ"));}
        if (sharedPreferences.getBoolean("georgian",     true)) {layouts.add(new CustomKeyboard(this, R.layout.georgian,    "georgian",    "Georgian",   "აბგდევ"));}
        if (sharedPreferences.getBoolean("glagolitic",   true)) {layouts.add(new CustomKeyboard(this, R.layout.glagolitic,  "glagolitic",  "Glagolitic", ""));}
        if (sharedPreferences.getBoolean("gothic",       true)) {layouts.add(new CustomKeyboard(this, R.layout.gothic,      "gothic",      "Gothic",     "𐌵𐍈𐌴𐍂𐍄𐍅"));}
        if (sharedPreferences.getBoolean("greek",        true)) {layouts.add(new CustomKeyboard(this, R.layout.greek,       "greek",       "Greek",      "ςερτυθ"));}
        if (sharedPreferences.getBoolean("hex",          true)) {layouts.add(new CustomKeyboard(this, R.layout.hex,         "hex",         "Hex",        "\\uabcd"));}
        if (sharedPreferences.getBoolean("insular",      true)) {layouts.add(new CustomKeyboard(this, R.layout.insular,     "insular",     "Insular",    "ꝺꝼᵹꞃꞅꞇ"));}
        if (sharedPreferences.getBoolean("ipa",          true)) {layouts.add(new CustomKeyboard(this, R.layout.ipa,         "ipa",         "IPA",        "ʔʕʘǁǂ"));}
        if (sharedPreferences.getBoolean("lisu",         true)) {layouts.add(new CustomKeyboard(this, R.layout.lisu,        "lisu",        "Lisu",       "ⵚꓟꓱꓤꓕ⅄"));}
        if (sharedPreferences.getBoolean("macros",       true)) {layouts.add(new CustomKeyboard(this, R.layout.macros,      "macros",      "Macros",     "✐",               -4));}
        if (sharedPreferences.getBoolean("math",         true)) {layouts.add(new CustomKeyboard(this, R.layout.math,        "math",        "Math",       "+−×÷=%"));}
        if (sharedPreferences.getBoolean("mirror",       true)) {layouts.add(new CustomKeyboard(this, R.layout.mirror,      "mirror",      "Mirror",     "poiuyt"));}
        if (sharedPreferences.getBoolean("ogham",        true)) {layouts.add(new CustomKeyboard(this, R.layout.ogham,       "ogham",       "Ogham",      "᚛ᚁᚆᚋᚐ᚜"));}
        if (sharedPreferences.getBoolean("rorrim",       true)) {layouts.add(new CustomKeyboard(this, R.layout.rorrim,      "rorrim",      "Rirrom",     "ytrewq"));}
        if (sharedPreferences.getBoolean("navigation",   true)) {layouts.add(new CustomKeyboard(this, R.layout.navigation,  "navigation",  "Navigation", "  →←↑↓",          -1));}
        if (sharedPreferences.getBoolean("numeric",      true)) {layouts.add(new CustomKeyboard(this, R.layout.numeric,     "numeric",     "Numeric",    "123456"));}
        if (sharedPreferences.getBoolean("pointy",       true)) {layouts.add(new CustomKeyboard(this, R.layout.pointy,      "pointy",      "Pointy",     "ᛩꟽⵉᚱⵜY"));}
        if (sharedPreferences.getBoolean("qwerty_plus",  true)) {layouts.add(new CustomKeyboard(this, R.layout.qwerty_plus, "qwerty_plus", "Qwerty+",    "qwerty+"));}
        if (sharedPreferences.getBoolean("rotated",      true)) {layouts.add(new CustomKeyboard(this, R.layout.rotated,     "rotated",     "Rotated",    "ʎʇɹəʍb"));}
        if (sharedPreferences.getBoolean("shift_1",      true)) {layouts.add(new CustomKeyboard(this, R.layout.shift_1,     "shift_1",     "Shift₁",     "qWeRtY"));}
        if (sharedPreferences.getBoolean("shift_2",      true)) {layouts.add(new CustomKeyboard(this, R.layout.shift_2,     "shift_2",     "Shift₂",     "QwErTy"));}
        if (sharedPreferences.getBoolean("stealth",      true)) {layouts.add(new CustomKeyboard(this, R.layout.stealth,     "stealth",     "Stealth",    "ԛԝеrtу"));}
        if (sharedPreferences.getBoolean("strike",       true)) {layouts.add(new CustomKeyboard(this, R.layout.strike,      "strike",      "Strike",     "ꝗwɇꞧⱦɏ"));}
        if (sharedPreferences.getBoolean("symbol",       true)) {layouts.add(new CustomKeyboard(this, R.layout.symbol,      "symbol",      "Symbol",     "!@#$%^"));}
        if (sharedPreferences.getBoolean("tails",        true)) {layouts.add(new CustomKeyboard(this, R.layout.tails,       "tails",       "Tails",      "ɋꝡҽɽʈƴ"));}
        if (sharedPreferences.getBoolean("unicode",      true)) {layouts.add(new CustomKeyboard(this, R.layout.unicode,     "unicode",     "Unicode",    "\\uxxxx"));}
        if (sharedPreferences.getBoolean("utility",      true)) {layouts.add(new CustomKeyboard(this, R.layout.utility,     "utility",     "Utility",    "/**/",            -3));}

        int layoutLayout = R.layout.layouts;
        
        Collections.sort(layouts);
        for (int i = 0; i < layouts.size(); i++) {
            if (layouts.get(i).order == 1024) {
                layouts.get(i).order = i;
            }
        }

        Collections.sort(layouts);
        layouts.add(new CustomKeyboard(this, layoutLayout, "Layouts", -1));

        if (getKeyboard("Layouts") != null) {
            for (Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                if (key.codes[0] <= -400 && key.codes[0] >= -449) {
                    try {
                        if (layouts.get(-key.codes[0] - 400) != null
                        && !layouts.get(-key.codes[0] - 400).title.equals("Layouts")
                        ) {
                            if (sharedPreferences.getBoolean("names", true)) {
                                key.label = layouts.get(-key.codes[0] - 400).title;
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

        if (sharedPreferences.getBoolean("relayout", true)) {
            int layoutCount = layouts.size()-2;
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
    
    // @todo instead of cancelling, concat contents
    public void toastIt(String text) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (sharedPreferences.getBoolean("debug", false)) return;
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
        try {
            if (newKeyboardID < layouts.size()) {
                currentKeyboardID = newKeyboardID;
                currentKeyboard = layouts.get(currentKeyboardID);
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
        return currentKeyboard.title; //+" "+currentKeyboard.order;
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
        setTheme();
        mComposing.setLength(0);
        mCompletions = null;


        kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);


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
        // ic.setSelection(0, Integer.MAX_VALUE);
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
                commitText("");
                updateCandidates();
            }
            else sendKey(KeyEvent.KEYCODE_DEL);
            if (Character.isUpperCase(prev.charAt(0))) {
                setCapsOn(true);
                firstCaps = true;
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
        catch (Exception ignored) {}
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
            commitText("");
            updateCandidates();
        }
        else sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    private Set<Map.Entry<String, String>> replacementData = Edit.getReplacements().entrySet();

    private Set<Map.Entry<String, String>> shortcutData = Edit.getShortcuts().entrySet();

    public void handleCharacter(int primaryCode) {
        ic = getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        primaryCode = KeyCodes.handleCharacter(kv, primaryCode);
        /*
        if (sharedPreferences.getBoolean("dot", false)) {
            if (primaryCode == 32) {
                if (String.valueOf(ic.getTextBeforeCursor(1, 0)).equals(" ")) {
                    ic.deleteSurroundingText(1, 0);
                    commitText(".");
                    setCapsOn(true);
                    firstCaps = true;
                }
            }
        }
        */
        if (sharedPreferences.getBoolean("pairs", true)) {
            if (Util.contains("({\"[", primaryCode)) {
                String code = String.valueOf((char)primaryCode);
                if (code.equals("("))  commitText("()");
                if (code.equals("["))  commitText("[]");
                if (code.equals("{"))  commitText("{}");
                if (code.equals("\"")) commitText("\"\"");
                sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
                return;
            }
        }
        if (sharedPreferences.getBoolean("auto", false)) {
            if (!Util.isAlphabet(primaryCode) || primaryCode == 32 || primaryCode == 10) {
                String escapedEntryKey;
                for (Map.Entry<String,String> entry : replacementData) {
                    escapedEntryKey = entry.getKey().replace("\\b", "").replace("\\n", "").replace("^",   "");
                    if (String.valueOf(ic.getTextBeforeCursor(escapedEntryKey.length(), 0)).matches(entry.getKey())) {
                        ic.deleteSurroundingText(escapedEntryKey.length(), 0);
                        commitText(entry.getValue());
                        break;
                    }
                    else if (Util.toTitleCase(String.valueOf(ic.getTextBeforeCursor(escapedEntryKey.length(), 0))).matches(Util.toTitleCase(entry.getKey()))) {
                        ic.deleteSurroundingText(escapedEntryKey.length(), 0);
                        commitText(Util.toTitleCase(entry.getValue()));
                        break;
                    }
                }
            }
        }
        if (sharedPreferences.getBoolean("shortcuts", true)) {
            if ((char)primaryCode == Edit.trigger) {
                for (Map.Entry<String,String> entry : shortcutData) {
                    if (String.valueOf(ic.getTextBeforeCursor(entry.getKey().length(), 0)).matches(entry.getKey())) {
                        ic.deleteSurroundingText(entry.getKey().length(), 0);
                        commitText(entry.getValue());
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
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
    /*
        if (keyCode == -400 || keyCode == -3 || keyCode == -4) {
            InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            if (imeManager != null) {
                imeManager.showInputMethodPicker();
                return true;
            }
        }
    */
        return false;
    }

    long time;
    
    
    @Override
    public void onPress(int primaryCode) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        time = System.nanoTime();
        if (sharedPreferences.getBoolean("vib", true)) {
            Vibrator v = (Vibrator)getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);

            v.vibrate(10);
        }
    }
    
    /*
    private void tap() {
        int ms = 10;
        int amp = 150; // VibrationEffect.EFFECT_TICK;
        if (Build.VERSION.SDK_INT >= 26) {
            v.vibrate(VibrationEffect.createOneShot(ms, amp));
        }
        else {
            v.vibrate(ms);
        }
    }
    */

    @Override
    public void onRelease(int primaryCode) {
        time = (System.nanoTime() - time) / 1000000;
        if (time > 300) {
    /*
            // try {
                if (getKey(primaryCode) != null && getKey(primaryCode).popupCharacters != null) {
                    if (getKey(primaryCode).popupCharacters.length() == 1) {
                        // cancel popup
                        // send key
                        // commitText(String.valueOf(getKey(primaryCode).popupCharacters.charAt(0)), 1);

                    }
                }
            // }
            // catch (Exception ignored) {}
    */
            switch (primaryCode) {
                // case  32: toastIt(findKeyboard("Layouts")); break;
                case -93: selectAll(); break;
                case -99:
                    ic = getCurrentInputConnection();
                    ic.deleteSurroundingText(MAX, MAX);
                break;
                // case  -76: toastIt(findKeyboard("Navigation")); break;
                // case -101: toastIt(findKeyboard("Utility")); break;
                // case -102: toastIt(findKeyboard("Function")); break;
                // case -400:
                // case   -3:
                // case   -4:
                //     InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                //     if (imeManager != null) imeManager.showInputMethodPicker();
                // break;
            }
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String record, currentKeyboardName = currentKeyboard.title;
        if (currentKeyboardName.equals("Enmorse") && !Util.charToMorse(String.valueOf((char)primaryCode)).equals("")) {
            String res = Util.charToMorse(String.valueOf((char)primaryCode));
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
                commitText(Util.morseToChar(res)+"");
                getKey(32).label = " ";
                redraw();
                return;
            }
            String res = String.valueOf((char)primaryCode);
            if (kv.isShifted()) res = res.toUpperCase();
            commitText(res);
            getKey(32).label = getLastMorse() + " " + Util.morseToChar(getLastMorse());
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
            case 10:
                EditorInfo curEditor = getCurrentInputEditorInfo();
                if (kv.getKeyboard().isShifted()) {commitText("\n", 1);}
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
                if (currentKeyboardID != 0) setKeyboardLayout(0);
                else setKeyboardLayout(layouts.size()-1);
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
                
                // int saveCursor = getSelectionStart();
                if (!isSelecting()) {
                    selectLine();
                    // ic.setSelection(saveCursor, saveCursor);
                }
                sendKey(KeyEvent.KEYCODE_COPY);
                toast.cancel();
                
                record = getClipboardEntry(0);
                if (!record.equals("")) {
                    clipboardHistory.add(record);
                }
                break;
            case -11:
                // record = clipboardHistoryHas() ? clipboardHistoryGet() : "";
                // if (!record.equals("")) commitText(record);
                // else {
                if (sharedPreferences.getBoolean("spaces", true)) {
                    String indent = Util.getIndentation(prevLine());
                    if (indent.length() > 0) {
                        commitText(getClipboardEntry(0).trim()); 
                        break;
                    }
                }
                sendKey(KeyEvent.KEYCODE_PASTE);
                // }
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
            case  -25: navigate(KeyEvent.KEYCODE_MOVE_HOME); break;
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
                if (!Util.buildTrigram(ic.getTextBeforeCursor(3, 0).toString()).equals("")) {
                    trigram = Util.buildTrigram(ic.getTextBeforeCursor(3, 0).toString());
                    ic.deleteSurroundingText(3, 0);
                    commitText(trigram);
                }
                if (!Util.buildTrigram(ic.getTextBeforeCursor(2, 0).toString()).equals("")) {
                    trigram = Util.buildTrigram(ic.getTextBeforeCursor(2, 0).toString());
                    ic.deleteSurroundingText(2, 0);
                    commitText(trigram);
                }
                if (!Util.buildDigram(ic.getTextBeforeCursor(2, 0).toString()).equals("")) {
                    trigram = Util.buildDigram(ic.getTextBeforeCursor(2, 0).toString());
                    ic.deleteSurroundingText(2, 0);
                    commitText(trigram);
                }
                if (!Util.buildHexagram(ic.getTextBeforeCursor(2, 0).toString()).equals("")) {
                    trigram = Util.buildHexagram(ic.getTextBeforeCursor(2, 0).toString());
                    ic.deleteSurroundingText(2, 0);
                    commitText(trigram);
                }
            break;
            case -104: 
                String text = getText(ic);
                toastIt("Chars: "+Util.countChars(text)+"\tWords: "+Util.countWords(text)+"\tLines: "+Util.countLines(text));
            break;
            case -105: performReplace(Util.sortLines(getText(ic))); break;
            case -106: performReplace(Util.reverseLines(getText(ic))); break;
            case -146: performReplace(Util.uniqueLines(getText(ic))); break;
            case -147: performReplace(Util.shuffleLines(getText(ic))); break;
            case -300: toastIt(findKeyboard("Default")); break;
            case -301: toastIt(findKeyboard("Function")); break;
            case -302: toastIt(findKeyboard("Utility")); break;
            case -303: toastIt(findKeyboard("Emoji")); break;
            case -304: toastIt(findKeyboard("Navigation")); break;
            case -305: toastIt(findKeyboard("Symbol")); break;
            case -306: toastIt(findKeyboard("Fonts")); break;
            case -307:
                currentKeyboard = new CustomKeyboard(this, R.layout.cherokee_1,  "cherokee_1",  "Cherokee", "ꭰꭱꭲꭳꭴꭵ");
                kv.setKeyboard(currentKeyboard);
                redraw();
            break;
            case -308:
                currentKeyboard = new CustomKeyboard(this, R.layout.cherokee_2,  "cherokee_2",  "Cherokee", "ꮛꮜꮝꮞꮟꮠ");
                kv.setKeyboard(currentKeyboard);
                redraw();
            break;
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
            // && primaryCode != 32
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
