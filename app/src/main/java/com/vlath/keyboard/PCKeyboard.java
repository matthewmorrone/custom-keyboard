package com.vlath.keyboard;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.ExtractedText;
import android.view.textservice.TextInfo;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import static android.app.Activity.RESULT_OK;
import static com.vlath.keyboard.Variables.setAllEmOff;

public class PCKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener, SpellCheckerSession.SpellCheckerSessionListener {

    public InputMethodManager mInputMethodManager;

    public CustomKeyboard mInputView;
    public CandidateView mCandidateView;
    public CompletionInfo[] mCompletions;

    public StringBuilder mComposing = new StringBuilder();
    public boolean mPredictionOn;
    public boolean mCompletionOn;
    public int mLastDisplayWidth;
    public boolean mCapsLock;
    // public long mLastShiftTime;
    public long mMetaState;
    public int MAX = 65536;

    public LatinKeyboard currentKeyboard;
    public int currentKeyboardID = 0;

    public String mWordSeparators;

    public SpellCheckerSession mScs;
    public List<String> mSuggestions;

    public boolean firstCaps = false;
    public float[] mDefaultFilter;
    long shift_pressed = 0;

    public short rowNumber = 6;
    public CustomKeyboard kv;

    public Toast toast;

    public int selectionCase = 0;
    public static String hexBuffer = "";
    public static String morseBuffer = "";

    InputConnection ic = getCurrentInputConnection();
    SharedPreferences sharedPreferences;

    public static ArrayList<LatinKeyboard> layouts = new ArrayList<>(5);
    private int mForeground;
    private int mBackground;
    // public static ArrayList<LatinKeyboard> getLayouts() { return layouts; }

    public void populateLayouts() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        layouts.clear();
        layouts.add(new LatinKeyboard(this, R.layout.qwerty, "English", "abc"));
        if (sharedPreferences.getBoolean("symbol", true))   { layouts.add(new LatinKeyboard(this, R.layout.symbol,   "Symbol", "!@#")); }
        if (sharedPreferences.getBoolean("accents", true))  { layouts.add(new LatinKeyboard(this, R.layout.accents,  "Accents", "◌̀◌́◌̂")); }
        if (sharedPreferences.getBoolean("greek", true))    { layouts.add(new LatinKeyboard(this, R.layout.greek,    "Greek", "ςερ")); }
        if (sharedPreferences.getBoolean("cyrillic", true)) { layouts.add(new LatinKeyboard(this, R.layout.cyrillic, "Cyrillic", "йцу")); }
        if (sharedPreferences.getBoolean("coptic", true))   { layouts.add(new LatinKeyboard(this, R.layout.coptic,   "Coptic", "ⲑϣⲉ")); }
        if (sharedPreferences.getBoolean("gothic", true))   { layouts.add(new LatinKeyboard(this, R.layout.gothic,   "Gothic", "𐌵𐍈𐌴")); }
        if (sharedPreferences.getBoolean("etruscan", true)) { layouts.add(new LatinKeyboard(this, R.layout.etruscan, "Etruscan", "𐌀𐌁𐌂")); }
        if (sharedPreferences.getBoolean("futhark", true))  { layouts.add(new LatinKeyboard(this, R.layout.futhark,  "Futhark", "ᚠᚢᚦ")); }
        // if (sharedPreferences.getBoolean("cree", true))     { layouts.add(new LatinKeyboard(this, R.layout.cree,     "Cree")); }
        if (sharedPreferences.getBoolean("dvorak", true))   { layouts.add(new LatinKeyboard(this, R.layout.dvorak,   "Dvorak", "pyf")); }
        if (sharedPreferences.getBoolean("numeric", true))  { layouts.add(new LatinKeyboard(this, R.layout.numeric,  "Numeric", "123")); }
        if (sharedPreferences.getBoolean("math", true))     { layouts.add(new LatinKeyboard(this, R.layout.math,     "Math", "+−×")); }
        if (sharedPreferences.getBoolean("morse", true))    { layouts.add(new LatinKeyboard(this, R.layout.morse,    "Morse", "-·")); }
        // if (sharedPreferences.getBoolean("funthork", true)) { layouts.add(new LatinKeyboard(this, R.layout.funthork, "Funthork")); }
        // if (sharedPreferences.getBoolean("tails", true))    { layouts.add(new LatinKeyboard(this, R.layout.tails,    "Tails")); }
        if (sharedPreferences.getBoolean("unicode", true))  { layouts.add(new LatinKeyboard(this, R.layout.unicode,  "Unicode", "\\u")); }
        if (sharedPreferences.getBoolean("hex", true))      { layouts.add(new LatinKeyboard(this, R.layout.hex,      "Hex", "\\x")); }
        // if (sharedPreferences.getBoolean("strike", true))   { layouts.add(new LatinKeyboard(this, R.layout.strike,   "Strike")); }
        // if (sharedPreferences.getBoolean("extra", true))    { layouts.add(new LatinKeyboard(this, R.layout.extra,    "Extra")); }
        if (sharedPreferences.getBoolean("fonts", true))    { layouts.add(new LatinKeyboard(this, R.layout.fonts,    "Fonts", "🄰𝔸𝐀")); }
        if (sharedPreferences.getBoolean("emoji", true))    { layouts.add(new LatinKeyboard(this, R.layout.emoji,    "Emoji", "😃😉😆")); }
        if (sharedPreferences.getBoolean("function", true)) { layouts.add(new LatinKeyboard(this, R.layout.function, "Function", "ƒ(x)")); }
        if (sharedPreferences.getBoolean("utility", true))  { layouts.add(new LatinKeyboard(this, R.layout.utility,  "Utility", "/**/")); }
        layouts.add(new LatinKeyboard(this, R.layout.navigation, "Navigation", "→←↑↓"));
        layouts.add(new LatinKeyboard(this, R.layout.layouts,    "Layouts", "◰◱◲◳"));

        if (getKeyboard("Layouts") != null) {
            for (Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                if (key.codes[0] <= -400 && key.codes[0] >= -425) {
                    try {
                        if (layouts.get(-key.codes[0] - 400) != null) {
                            key.label = layouts.get(-key.codes[0] - 400).label;
                        }
                    }
                    catch (Exception e) {
                        key.label = "";
                        System.out.println(key.codes[0]);
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void toastIt(String text) {
        toast.cancel();
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public LatinKeyboard getKeyboard(String name) {
        int index = 0;
        for(LatinKeyboard layout : layouts) {
            if (layout.name.equals(name)) {
                break;
            }
            index++;
        }
        return layouts.get(index);
    }

    public String findKeyboard(String name) {
        populateLayouts();
        if (getRowNumber() == 7) {
            setRowNumber(6);
            currentKeyboard.setRowNumber(getRowNumber());
        }
        int index = 0;
        for(LatinKeyboard layout : layouts) {
            if (layout.name.equals(name)) {
                currentKeyboardID = index;
                break;
            }
            index++;
        }
        setKeyboard();
        return name;
    }

    public void setKeyboard() {
        currentKeyboard = layouts.get(currentKeyboardID);
        kv.setKeyboard(currentKeyboard);
        kv.draw(new Canvas());
        kv.invalidateAllKeys();
    }

    public void prevKeyboard() {
        if (getRowNumber() == 7) {
            setRowNumber(6);
            currentKeyboard.setRowNumber(getRowNumber());
        }
        populateLayouts();
        currentKeyboardID--;
        if (currentKeyboardID < 0) {currentKeyboardID = layouts.size()-2;}
        setKeyboard();
    }

    public void nextKeyboard() {
        if (getRowNumber() == 7) {
            setRowNumber(6);
            currentKeyboard.setRowNumber(getRowNumber());
        }
        populateLayouts();
        currentKeyboardID++;
        if (currentKeyboardID > layouts.size()-2) {currentKeyboardID = 0;}
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
        populateLayouts();
    }

    @Override
    public void onInitializeInterface() {
        populateLayouts();
        if (getKeyboard("English") != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) {
                return;
            }
            mLastDisplayWidth = displayWidth;
            // for (Keyboard.Key key : layouts.get(0).getKeys()) {
            //     key.popupCharacters = key.label+" "+key.popupCharacters;
            // }
        }

        if (getKeyboard("Function") != null) {
            for (Keyboard.Key key : getKeyboard("Function").getKeys()) {
                try {
                    if (key.codes[0] == -501) key.label = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("k1", "");
                    if (key.codes[0] == -502) key.label = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("k2", "");
                    if (key.codes[0] == -503) key.label = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("k3", "");
                    if (key.codes[0] == -504) key.label = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("k4", "");
                    if (key.codes[0] == -505) key.label = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("k5", "");
                    if (key.codes[0] == -506) key.label = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("k6", "");
                    if (key.codes[0] == -507) key.label = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("k7", "");
                    if (key.codes[0] == -508) key.label = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("k8", "");
                }
                catch (Exception ignored) {}
            }
        }
        if (getKeyboard("Layouts") != null) {
            for (Keyboard.Key key : getKeyboard("Layouts").getKeys()) {
                if (key.codes[0] <= -400 && key.codes[0] >= -424) {
                    try {
                        if (layouts.get(-key.codes[0] - 400) != null) { key.label = layouts.get(-key.codes[0] - 400).name; }
                        else { key.label = ""; }
                    }
                    catch (Exception e) { key.label = ""; }
                }
            }
        }

        // for(LatinKeyboard layout : layouts) {}
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateInputView() {
        populateLayouts();
        mInputView = (CustomKeyboard)getLayoutInflater().inflate(R.layout.qwerty, null);

        mInputView.setOnKeyboardActionListener(this);
        mInputView.setPreviewEnabled(true);

        return mInputView;
    }

    @Override
    public View onCreateCandidatesView() {
        mCandidateView = new CandidateView(this);
        mCandidateView.setService(this);
        setTheme();
        setForeground();
        setBackground();
        Paint mPaint = new Paint();

        ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(mDefaultFilter);
        mPaint.setColorFilter(filterInvert);
        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        populateLayouts();

        return mCandidateView;
    }

    @SuppressLint("InflateParams")
    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        setTheme();
        setForeground();
        setBackground();

        mComposing.setLength(0);
        // updateCandidates();

        if (!restarting) {
            mMetaState = 0;
        }
        mCompletions = null;

        if (sharedPreferences.getBoolean("bord", false)) {
            kv = (CustomKeyboard)getLayoutInflater().inflate(R.layout.key_back, null);
        }
        else {
            kv = (CustomKeyboard)getLayoutInflater().inflate(R.layout.keyboard, null);
        }
        
        setInputType();
        Paint mPaint = new Paint();
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setUnderlineText(true);
        ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(mDefaultFilter);
        mPaint.setColorFilter(filterInvert);

        mCandidateView = new CandidateView(this);
        mCandidateView.setService(this);

        kv.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        currentKeyboard.setRowNumber(getRowNumber());
        kv.setKeyboard(currentKeyboard);

        capsOnFirst();
        kv.setOnKeyboardActionListener(this);

        mPredictionOn = sharedPreferences.getBoolean("pred", false);
        mCompletionOn = false;

        setInputView(kv);

        setCandidatesView(mCandidateView);

        populateLayouts();
        setKeyboard();

        if (getRowNumber() == 7) {
            layouts.set(0, new LatinKeyboard(this, R.layout.qwerty_7, "English"));
            currentKeyboardID = 0;
            setKeyboard();
        }
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();

        mComposing.setLength(0);
        // updateCandidates();

        setCandidatesViewShown(false);

        if (mInputView != null) {
            mInputView.closing();
        }
    }

    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);

        if (mComposing.length() > 0 && (newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
            mComposing.setLength(0);
            // updateCandidates();
            ic = getCurrentInputConnection();
            if (ic != null) {
                ic.finishComposingText();
            }
        }
    }

    // @Override
    // public void onDisplayCompletions(CompletionInfo[] completions) {
    //     if (mCompletionOn) {
    //         mCompletions = completions;
    //         if (completions == null) {
    //             // setSuggestions(null, false, false);
    //             return;
    //         }
    //
    //         List<String> stringList = new ArrayList<>();
    //         for (CompletionInfo ci : completions) {
    //             if (ci != null) {
    //                 stringList.add(ci.getText().toString());
    //             }
    //         }
    //         // setSuggestions(stringList, true, true);
    //     }
    // }

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

    // public Keyboard.Key getKey(int primaryCode) {
    //     for(Keyboard.Key key : currentKeyboard.getKeys()) {
    //         if (key.codes[0] == primaryCode) {
    //             return key;
    //         }
    //     }
    //     return null;
    // }

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
                if (imeManager != null) {
                    imeManager.showInputMethodPicker();
                }
            }
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
        // setRowNumber(getRowNumber()-1);
        setRowNumber(6);
        currentKeyboard.setRowNumber(getRowNumber());
        layouts.set(0, new LatinKeyboard(this, R.layout.qwerty, "English"));
        currentKeyboardID = 0;
        setKeyboard();
    }

    @Override
    public void swipeUp() {
        // setRowNumber(getRowNumber()+1);
        setRowNumber(7);
        currentKeyboard.setRowNumber(getRowNumber());
        layouts.set(0, new LatinKeyboard(this, R.layout.qwerty_7, "English"));
        currentKeyboardID = 0;
        setKeyboard();
    }

    public void hide() {
        requestHideSelf(0);
    }

    public void commitTyped(InputConnection ic) {
        if (mComposing.length() > 0) {
            ic.commitText(mComposing, mComposing.length());
            mComposing.setLength(0);
            // updateCandidates();
        }
    }

    public void onText(CharSequence text) {
        ic = getCurrentInputConnection();
        if (ic == null) {
            return;
        }
        ic.beginBatchEdit();
        if (mComposing.length() > 0) {
            commitTyped(ic);
        }
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
            else {
                setSuggestions(null, false, false);
            }
        }
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

    public void pickSuggestionManually(int index) {
        ic = getCurrentInputConnection();
        if (mCompletionOn && mCompletions != null && index >= 0 && index < mCompletions.length) {
            CompletionInfo ci = mCompletions[index];
            ic.commitCompletion(ci);
            if (mCandidateView != null) {
                mCandidateView.clear();
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
        else if (mComposing.length() > 0) {
            if (mPredictionOn && mSuggestions != null && index >= 0) {
                mComposing.replace(0, mComposing.length(), mSuggestions.get(index));
            }
            commitTyped(ic);
        }
    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {
        final StringBuilder sb = new StringBuilder();

        for (SuggestionsInfo result : results) {
            final int len = result.getSuggestionsCount();
            sb.append('\n');

            for (int j = 0; j < len; ++j) {
                sb.append(",").append(result.getSuggestionAt(j));
            }

            sb.append(" (").append(len).append(")");
        }
    }

    public void dumpSuggestionsInfoInternal(final List<String> sb, final SuggestionsInfo si) {
        final int len = si.getSuggestionsCount();
        for (int j = 0; j < len; ++j) {
            sb.add(si.getSuggestionAt(j));
        }
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

    public void setForeground() {
        Context context = getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String foreground = sharedPreferences.getString("fg", "1");
        try {
            if (foreground != null) {
                switch (foreground) {
                    case "1": mForeground = Color.BLACK;   break;
                    case "2": mForeground = Color.WHITE;   break;
                    case "3": mForeground = Color.BLUE;    break;
                    case "4": mForeground = Color.CYAN;    break;
                    case "5": mForeground = Color.RED;     break;
                    case "6": mForeground = Color.MAGENTA; break;
                    case "7": mForeground = Color.YELLOW;  break;
                    case "8": mForeground = Color.GREEN;   break;
                }
            }
        }
        catch (Exception ignored) {}
    }

    public void setBackground() {
        Context context = getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String background = sharedPreferences.getString("bg", "1");
        try {
            if (background != null) {
                switch (background) {
                    case "1": mBackground = Color.BLACK;   break;
                    case "2": mBackground = Color.WHITE;   break;
                    case "3": mBackground = Color.BLUE;    break;
                    case "4": mBackground = Color.CYAN;    break;
                    case "5": mBackground = Color.RED;     break;
                    case "6": mBackground = Color.MAGENTA; break;
                    case "7": mBackground = Color.YELLOW;  break;
                    case "8": mBackground = Color.GREEN;   break;
                }
            }
        }
        catch (Exception ignored) {}
    }

    public void setTheme() {
        Context context = getBaseContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String theme = sharedPreferences.getString("theme", "1");
        try {
            if (theme != null) {
                switch (theme) {
                    case "1": mDefaultFilter = Themes.sNoneColorArray;         break;
                    case "2": mDefaultFilter = Themes.sNegativeColorArray;     break;
                    case "3": mDefaultFilter = Themes.sBlueWhiteColorArray;    break;
                    case "4": mDefaultFilter = Themes.sBlueBlackColorArray;    break;
                    case "5": mDefaultFilter = Themes.sRedWhiteColorArray;     break;
                    case "6": mDefaultFilter = Themes.sRedBlackColorArray;     break;
                    case "7": mDefaultFilter = Themes.sOrangeBlackColorArray;  break;
                    case "8": mDefaultFilter = Themes.sMaterialDarkColorArray; break;
                }
            }
        }
        catch (Exception ignored) {}
    }

    public void setInputType() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        EditorInfo attribute = getCurrentInputEditorInfo();
        if (Objects.equals(sharedPreferences.getString("start", "1"), "1")) {
            switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
                case InputType.TYPE_CLASS_NUMBER:
                case InputType.TYPE_CLASS_DATETIME:
                case InputType.TYPE_CLASS_PHONE:
                    currentKeyboard = new LatinKeyboard(this, R.layout.numeric);
                break;
                default:
                    currentKeyboard = layouts.get(0);
                break;
            }
        }
        if (kv != null) {
            kv.setKeyboard(currentKeyboard);
        }
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
        if (text == null) {
            return "";
        }
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

    public String setKeyboardLayout(int newKeyboardID) {
        try {
            if (newKeyboardID < layouts.size()) {
                currentKeyboardID = newKeyboardID;
                currentKeyboard = layouts.get(currentKeyboardID);
                kv.setKeyboard(currentKeyboard);
                kv.draw(new Canvas());
            }
        }
        catch (Exception ignored) {}
        return currentKeyboard.name;
    }

    public String getLastWord() {
        ic = getCurrentInputConnection();
        String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
        if (words.length < 2) {
            return ic.getTextBeforeCursor(MAX, 0).toString();
        }
        return words[words.length - 1];
    }

    public void wordBack(int n) {
        try {
            ic = getCurrentInputConnection();
            for(int i = 0; i < n; i++) {
                String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
                String lastWord = words[words.length - 1];
                int position = getSelectionStart() - lastWord.length() - 1;
                if(position < 0) {
                    position = 0;
                }
                if (Variables.isSelect()) {
                    ic.setSelection(position, Variables.cursorStart);
                }
                else {
                    ic.setSelection(position, position);
                }
            }
        } 
        catch (Exception ignored) {}
    }

    public void wordFore(int n) {
        try {
            ic = getCurrentInputConnection();
            for(int i = 0; i < n; i++) {
                String[] words = ic.getTextAfterCursor(MAX, 0).toString().split(" ");
                String nextWord = words[0];
                if (words.length > 1) {
                    nextWord = words[1];
                }
                
                int position = getSelectionStart() + nextWord.length() + 1;
                if (Variables.isSelect()) {
                    ic.setSelection(Variables.cursorStart, position);
                }
                else {
                    ic.setSelection(position, position);
                }
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

    public int getCursorPosition() {
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) {
            return -1;
        }
        return extracted.startOffset + extracted.selectionStart;
    }

    public int getSelectionStart() {
        // ic.getTextBeforeCursor(MAX, 0).length();
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) {
            return -1;
        }
        return extracted.selectionStart;
    }

    public int getSelectionEnd() {
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) {
            return -1;
        }
        return extracted.selectionEnd;
    }

    public int getSelectionLength() {
        // String text = ic.getSelectedText(0).toString();
        // ic.getSelectedText(0) == null ? 0 : text.length();
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) {
            return -1;
        }
        return extracted.selectionEnd-extracted.selectionStart;
    }    
    
    public void selectionInfo() {    
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) {
            return;
        }    
        String result = "";    
        result += extracted.selectionStart + " ";
        result += extracted.selectionEnd + " ";
        result += extracted.startOffset + " ";    
        result += getSelectionLength() + " ";    
        result += isSelecting() + " ";
        result += ic.getSelectedText(0).length() + " ";
        result += ic.getTextBeforeCursor(MAX, 0).length() + " ";
        result += ic.getTextAfterCursor(MAX, 0).length() + " ";    
        result += getCursorPosition() + " ";    
        // result +=  + " ";    
        // result +=  + " ";    
        // result +=  + " ";
    }

    public int getStartOffset() {
        // String text = ic.getSelectedText(0).toString();
        // ic.getSelectedText(0) == null ? 0 : text.length();
        ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) {
            return -1;
        }
        return extracted.startOffset;
    }

    public Boolean isSelecting() {
        // toastIt(getSelectionStart()+" "+getSelectionEnd()+" "+getStartOffset()+" "+getSelectionLength());
        return getSelectionStart() != getSelectionEnd();
    }

    public void navigate(int primaryCode) {
        ic = getCurrentInputConnection();
        if (Variables.isSelect()) {ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT));}
        sendKeyUpDown(primaryCode);
        if (Variables.isSelect()) {ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   KeyEvent.KEYCODE_SHIFT_LEFT));}
    }

    public String getClipboardEntry(int n) {
        try {
            ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = clipboardManager.getPrimaryClip();
            ClipData.Item item = null;
            if (clip != null) {
                item = clip.getItemAt(n);
            }
            CharSequence text = null;
            if (item != null) {
                text = item.getText();
            }
            if (text != null) {
                return text.toString();
            }
        }
        catch (Exception e) {
            return "";
        }
        return "";
    }

    public void setCapsOn(boolean on) {
        if (Variables.isShift()) {
            kv.getKeyboard().setShifted(true);
            kv.invalidateAllKeys();
            kv.draw(new Canvas());
        }
        else {
            kv.getKeyboard().setShifted(on);
            kv.invalidateAllKeys();
            kv.draw(new Canvas());
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
        if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
            caps = ic.getCursorCapsMode(attr.inputType);
        }
        return caps;
    }

    public void updateShiftKeyState(EditorInfo attr) {
        ic = getCurrentInputConnection();
        if (attr != null && mInputView != null && layouts.get(0) == mInputView.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != InputType.TYPE_NULL) {
                caps = ic.getCursorCapsMode(attr.inputType);
            }
            mInputView.setShifted(mCapsLock || caps != 0);
        }
    }

    public void handleBackspace() {
        // ic.deleteSurroundingText(1, 0);
        ic = getCurrentInputConnection();
        final int length = mComposing.length();
        if (length > 1) {
            mComposing.delete(length - 1, length);
            ic.setComposingText(mComposing, 1);
            updateCandidates();
        }
        else if (length > 0) {
            mComposing.setLength(0);
            ic.commitText("", 0);
            updateCandidates();
        }
        else {
            sendKeyUpDown(KeyEvent.KEYCODE_DEL);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void handleDelete() {
        // ic.deleteSurroundingText(0, 1);
        ic = getCurrentInputConnection();
        final int length = mComposing.length();
        if (length > 1) {
            mComposing.delete(length, length - 1);
            ic.setComposingText(mComposing, 1);
            updateCandidates();
        }
        else if (length > 0) {
            mComposing.setLength(0);
            ic.commitText("", 0);
            updateCandidates();
        }
        else {
            sendKeyUpDown(KeyEvent.KEYCODE_FORWARD_DEL);
        }
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

        if (sharedPreferences.getBoolean("auto", false)) {
            if (!Util.isAlphabet(primaryCode) || primaryCode == 32 || primaryCode == 10) {
                for (Map.Entry<String,String> entry : Replacements.getData().entrySet()) {
                    String escapedEntryKey = entry.getKey()
                        .replace("\\b", "")
                        .replace("\\n", "")
                        .replace("^",   "");
                    if (String.valueOf(ic.getTextBeforeCursor(escapedEntryKey.length(), 0)).matches(entry.getKey())) {
                        // toastIt(entry.getKey()+" "+escapedEntryKey+" "+String.valueOf(escapedEntryKey.length()));
                        ic.deleteSurroundingText(escapedEntryKey.length(), 0);
                        ic.commitText(entry.getValue(), 0);
                        // if (kv.getKeyboard().isShifted()) {}
                        // else {}
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
        if (mPredictionOn && !mWordSeparators.contains(String.valueOf((char) primaryCode))) {
            mComposing.append((char) primaryCode);
            ic.setComposingText(mComposing, 1);
            updateShiftKeyState(getCurrentInputEditorInfo());
            updateCandidates();
        }
        if (mPredictionOn && mWordSeparators.contains(String.valueOf((char)primaryCode))) {
            char code = (char) primaryCode;
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
    
            if (isSelecting()) {    
                ic.setSelection(Variables.cursorStart, getSelectionEnd());
            }
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int[] join = {KeyEvent.KEYCODE_MOVE_END, KeyEvent.KEYCODE_FORWARD_DEL};


        try {
            if (currentKeyboard.name.equals("Morse")) {
                if (!Util.morseToChar(getLastWord()).equals("")) {
                    if (primaryCode == 32) {
                        toastIt(getLastWord()+" "+Util.morseToChar(getLastWord()));
                        ic.commitText(Util.morseToChar(getLastWord()), 0);
                    }
                }
                kv.draw(new Canvas());
            }
        }
        catch (Exception ignored) {}

        if (currentKeyboard.name.equals("Unicode") && !Util.contains(Codes.hexPasses, primaryCode)) {
            if (Util.contains(Codes.hexCaptures, primaryCode)) {
                if (hexBuffer.length() > 3) {hexBuffer = "";}
                hexBuffer += (char)primaryCode;
                kv.draw(new Canvas());
            }
            if (primaryCode == -2001) {
                kv.draw(new Canvas());
            }
            if (primaryCode == -2002) {
                handleCharacter((char)(int)Integer.decode("0x"+StringUtils.leftPad(hexBuffer, 4,"0")));
                kv.draw(new Canvas());
            }
            if (primaryCode == -2003) {
                performReplace(Util.convertFromUnicodeToNumber(getText(ic)));
            }
            if (primaryCode == -2004) {
                performReplace(Util.convertFromNumberToUnicode(getText(ic)));
            }
            if (primaryCode == -2005) {
                if (hexBuffer.length() > 0) {hexBuffer = hexBuffer.substring(0, hexBuffer.length() - 1);}
                else {hexBuffer = "0000";}
                kv.draw(new Canvas());
            }
            if (primaryCode == -2006) {
                hexBuffer = "0000";
                kv.draw(new Canvas());
            }
            kv.draw(new Canvas());
            return;
        }

        switch (primaryCode) {
            case 10:
                EditorInfo curEditor = getCurrentInputEditorInfo();
                if (kv.getKeyboard().isShifted()) {
                    ic.commitText("\n", 1);    
                }
                switch (curEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
                    case EditorInfo.IME_ACTION_GO:     ic.performEditorAction(EditorInfo.IME_ACTION_GO); break;
                    case EditorInfo.IME_ACTION_SEARCH: ic.performEditorAction(EditorInfo.IME_ACTION_SEARCH); break;
                    default: sendKeyUpDown(66); break;
                }    
                break;
            case 7: ic.commitText("    ", 0); break;
            case -1:
                if (ic.getSelectedText(0) != null
                 && ic.getSelectedText(0).length() > 0
                 && PreferenceManager.getDefaultSharedPreferences(this).getBoolean("shift", false)) {
                    String text = ic.getSelectedText(0).toString();
                    int a = getSelectionStart();
                    int b = getSelectionEnd();
                    if (selectionCase == 0) {
                        text = text.toUpperCase();
                        selectionCase = 1;
                    }
                    // else if (selectionCase == 1) {
                    //     text = Util.toTitleCase(text);
                    //     selectionCase = 2;
                    // }
                    else {
                        text = text.toLowerCase();
                        selectionCase = 0;
                    }
                    ic.commitText(text, b);
                    ic.setSelection(a, b);
                    kv.draw(new Canvas());
                }
                else {
                    if (shift_pressed + 300 > System.currentTimeMillis()) {
                        Variables.setShiftOn();
                        setCapsOn(true);
                        kv.draw(new Canvas());
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
                    kv.invalidateAllKeys();
                    kv.draw(new Canvas());
                }
            break;
            case -2: hide(); break;
            case -5:
                if (!isSelecting() && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals("    ")) {
                    ic.deleteSurroundingText(4, 0);
                }
                else {
                    handleBackspace();
                }
            break;
            case -7:
                if (!isSelecting() && String.valueOf(ic.getTextAfterCursor(4, 0)).equals("    ")) {
                    ic.deleteSurroundingText(0, 4);
                }
                else {
                    handleDelete();
                }
            break;
            case -8: selectAll(); break;
            case -9: sendKeyUpDown(KeyEvent.KEYCODE_CUT); break;
            case -10: sendKeyUpDown(KeyEvent.KEYCODE_COPY); break;
            case -11: sendKeyUpDown(KeyEvent.KEYCODE_PASTE); break;
            case -12: Variables.toggleBolded(); break;
            case -13: Variables.toggleItalic(); break;
            case -14: setAllEmOff(); break;
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
            case -48: processKeyList(join); break;
            case -49: performReplace(Util.replaceLinebreaks(getText(ic))); break;
            case -50: Variables.toggleReflected(); break;
            case -51: performReplace(Util.convertNumberBase(getText(ic), 2, 10)); break;
            case -52: performReplace(Util.convertNumberBase(getText(ic), 10, 2)); break;
            case -53: performReplace(Util.convertNumberBase(getText(ic), 8, 10)); break;
            case -54: performReplace(Util.convertNumberBase(getText(ic), 10, 8)); break;
            case -55: performReplace(Util.convertNumberBase(getText(ic), 16, 10)); break;
            case -56: performReplace(Util.convertNumberBase(getText(ic), 10, 16)); break;
            case -57: Variables.toggleSmallcaps(); break;
            case -67:
                Variables.toggleSelect();
                Variables.cursorStart = getSelectionStart();
            break;
            case -68: Variables.toggle127280(); break;
            case -69: Variables.toggle127312(); break;
            case -70: Variables.toggle127344(); break;
            case -71: Variables.toggle127462(); break;
            case -72: Variables.toggle009372(); break;
            case -73: Variables.toggle009398(); break;
            case -74: performReplace(Util.splitWithSpaces(getText(ic))); break;
            case -75: performReplace(Util.joinWithSpaces(getText(ic))); break;
            case -76: selectNone(); break;
            case -77: wordBack(1); break;
            case -78: wordFore(1); break;
            case -79: performReplace(Util.reverse(getText(ic))); break;
            case -80: ic.commitText(getClipboardEntry(0), 0); break;
            case -81: ic.commitText(getClipboardEntry(1), 0); break;
            case -82: ic.commitText(getClipboardEntry(2), 0); break;
            case -83: ic.commitText(getClipboardEntry(3), 0); break;
            case -84: ic.commitText(getClipboardEntry(4), 0); break;
            case -85: ic.commitText(getClipboardEntry(5), 0); break;
            case -86: ic.commitText(getClipboardEntry(6), 0); break;
            case -87: ic.commitText(getClipboardEntry(7), 0); break;    
            // case -88: sendKeyUpDown(KeyEvent.KEYCODE_UNDO); break;
            // case -89: sendKeyUpDown(KeyEvent.KEYCODE_REDO); break;
            case -101: prevKeyboard(); break;
            case -102: nextKeyboard(); break;
            case -107: navigate(KeyEvent.KEYCODE_DPAD_UP); break;
            case -108: navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -109: navigate(KeyEvent.KEYCODE_DPAD_DOWN); break;
            case -111: navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -112: sendKeyUpDown(KeyEvent.KEYCODE_ESCAPE); break;
            case -113:
                if (Variables.isCtrl()) {
                    Variables.setCtrlOff();
                }
                else {
                    Variables.setCtrlOn();
                }
                kv.draw(new Canvas());
            break;
            case -114:
                if (Variables.isAlt()) {
                    Variables.setAltOff();
                }
                else {
                    Variables.setAltOn();
                }
                kv.draw(new Canvas());
            break;
            case -121:
                if (Variables.isCtrl() || Variables.isAlt()) {
                    if (Variables.isCtrl() && Variables.isAlt()) {
                        ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isAlt()) {
                        ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isCtrl()) {
                        ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_CTRL_ON));
                    }
                }
                else {
                    sendKeyUpDown(KeyEvent.KEYCODE_DEL);
                }
            break;
            case -122:
                if (Variables.isCtrl() || Variables.isAlt()) {
                    if (Variables.isCtrl() && Variables.isAlt()) {
                        ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isAlt()) {
                        ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isCtrl()) {
                        ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_CTRL_ON));
                    }
                }
                else {
                    sendKeyUpDown(61);
                }
            break;
            case -300: toastIt(findKeyboard("English"));    break;
            case -301: toastIt(findKeyboard("Function"));   break;
            case -302: toastIt(findKeyboard("Utility"));    break;
            case -303: toastIt(findKeyboard("Emoji"));      break;
            case -304: toastIt(findKeyboard("Navigation")); break;
            case -305: toastIt(findKeyboard("Symbol"));     break;
            case -306: toastIt(findKeyboard("Fonts"));      break;
            case -399: 
            case -400: toastIt(setKeyboardLayout(0));     break;
            case -401: toastIt(setKeyboardLayout(1));     break;
            case -402: toastIt(setKeyboardLayout(2));     break;
            case -403: toastIt(setKeyboardLayout(3));     break;
            case -404: toastIt(setKeyboardLayout(4));     break;
            case -405: toastIt(setKeyboardLayout(5));     break;
            case -406: toastIt(setKeyboardLayout(6));     break;
            case -407: toastIt(setKeyboardLayout(7));     break;
            case -408: toastIt(setKeyboardLayout(8));     break;
            case -409: toastIt(setKeyboardLayout(9));     break;
            case -410: toastIt(setKeyboardLayout(10));    break;
            case -411: toastIt(setKeyboardLayout(11));    break;
            case -412: toastIt(setKeyboardLayout(12));    break;
            case -413: toastIt(setKeyboardLayout(13));    break;
            case -414: toastIt(setKeyboardLayout(14));    break;
            case -415: toastIt(setKeyboardLayout(15));    break;
            case -416: toastIt(setKeyboardLayout(16));    break;
            case -417: toastIt(setKeyboardLayout(17));    break;
            case -418: toastIt(setKeyboardLayout(18));    break;
            case -419: toastIt(setKeyboardLayout(19));    break;
            case -420: toastIt(setKeyboardLayout(20));    break;
            case -421: toastIt(setKeyboardLayout(21));    break;
            case -422: toastIt(setKeyboardLayout(22));    break;
            case -423: toastIt(setKeyboardLayout(23));    break;
            case -424: toastIt(setKeyboardLayout(24));    break;
            case -498:
                InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                if (imeManager != null) {
                    imeManager.showInputMethodPicker();
                }
            break;
            case -499: setKeyboardLayout(layouts.size()-1);                 break;
            case -501: sendCustomKey("k1");                                 break;
            case -502: sendCustomKey("k2");                                 break;
            case -503: sendCustomKey("k3");                                 break;
            case -504: sendCustomKey("k4");                                 break;
            case -505: sendCustomKey("k5");                                 break;
            case -506: sendCustomKey("k6");                                 break;
            case -507: sendCustomKey("k7");                                 break;
            case -508: sendCustomKey("k8");                                 break;
            case -509: sendKeyUpDown(KeyEvent.KEYCODE_DPAD_UP_LEFT);        break;
            case -510: sendKeyUpDown(KeyEvent.KEYCODE_DPAD_UP_RIGHT);       break;
            case -511: sendKeyUpDown(KeyEvent.KEYCODE_DPAD_DOWN_LEFT);      break;
            case -512: sendKeyUpDown(KeyEvent.KEYCODE_DPAD_DOWN_RIGHT);     break;
            case -999: ic.deleteSurroundingText(MAX, MAX);                  break;
            case -1000: performReplace(getText(ic).toUpperCase());          break;
            case -1001: performReplace(Util.toTitleCase(getText(ic)));           break;
            case -1002: performReplace(getText(ic).toLowerCase());          break;
            case -1003: performReplace(Util.dashesToSpaces(getText(ic)));        break;
            case -1004: performReplace(Util.underscoresToSpaces(getText(ic)));   break;
            case -1005: performReplace(Util.spacesToDashes(getText(ic)));        break;
            case -1006: performReplace(Util.spacesToUnderscores(getText(ic)));   break;
            case -1007: performReplace(Util.camelToSnake(getText(ic)));          break;
            case -1008: performReplace(Util.snakeToCamel(getText(ic)));          break;
            case -1009: performReplace(Util.removeSpaces(getText(ic)));          break;
            case -1010: performReplace(Util.doubleCharacters(getText(ic)));          break;
            default:
                if (Variables.isCtrl() || Variables.isAlt()) {
                    processKeyCombo(primaryCode);
                }
                else {
                    try {
                        System.out.println("handleCharacter: "+primaryCode);
                        handleCharacter(primaryCode);
                        kv.draw(new Canvas());
                        if (Variables.isReflected()) {
                            sendKeyUpDown(KeyEvent.KEYCODE_DPAD_LEFT);
                        }
                    }
                    catch (Exception ignored) {}
                }
        }
        try {
            if (sharedPreferences.getBoolean("caps", true)) {
                if (Util.isWordSeparator(ic.getTextBeforeCursor(2, 0).toString())) {
                    setCapsOn(true);
                    firstCaps = true;
                }
            }
        }
        catch (Exception ignored) {}
    }

    public short getRowNumber() {return rowNumber;}

    public void setRowNumber(int number) {rowNumber = (short)number;}
}
