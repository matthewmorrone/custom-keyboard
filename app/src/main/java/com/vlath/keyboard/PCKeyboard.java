package com.vlath.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Vibrator;
import android.preference.*;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
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
import java.util.List;

import static android.text.Selection.extendUp;
import static com.vlath.keyboard.Variables.setAllOff;

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
    public long mLastShiftTime;
    public long mMetaState;

    ArrayList<LatinKeyboard> layouts = new ArrayList<>(5);

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

    public void populateLayouts() {
        layouts.clear();
            layouts.add(new LatinKeyboard(this, R.xml.qwerty, "English"));
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("symbol", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.symbol, "Symbol"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("unicode", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.unicode, "Unicode"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("hex", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.hex, "Hexadecimal"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("ipa", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.ipa, "IPA"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("greek", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.greek, "Greek"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("futhark", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.futhark, "Futhark"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("fonts", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.fonts, "Fonts"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("dvorak", false) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.dvorak, "Dvorak"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("numbers", false) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.numbers, "Numbers"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("utility", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.utility, "Utility"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("nav", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.navigation, "Navigation"));
        }
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("function", true) == true) {
            layouts.add(new LatinKeyboard(this, R.xml.function, "Function"));
        }
        layouts.add(new LatinKeyboard(this, R.xml.layouts, "Layouts"));
    }

    public void toastIt(String text) {
        toast.cancel();
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void prevKeyboard() {
        populateLayouts();
        currentKeyboardID--;
        if (currentKeyboardID < 0) currentKeyboardID = layouts.size()-2;
        System.out.println(currentKeyboardID);
        currentKeyboard = layouts.get(currentKeyboardID);
        kv.setKeyboard(currentKeyboard);
        toastIt(currentKeyboard.name+" "+currentKeyboardID);
    }
    public void nextKeyboard() {
        populateLayouts();
        currentKeyboardID++;
        if (currentKeyboardID > layouts.size()-2) currentKeyboardID = 0;
        System.out.println(currentKeyboardID);
        currentKeyboard = layouts.get(currentKeyboardID);
        kv.setKeyboard(currentKeyboard);
        toastIt(currentKeyboard.name+" "+currentKeyboardID);
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


        if (layouts.get(0) != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) {
                return;
            }
            mLastDisplayWidth = displayWidth;

            // for (Keyboard.Key key : layouts.get(0).getKeys()) {
            //     System.out.println(key.codes[0]+"\t"+key.label);
            //     if (key.codes[0] == 65 || key.codes[0] == 97) {
            //         key.label = "AAA";
            //     }
            // }
        }

        if (layouts.get(1) != null) {
            for (Keyboard.Key key : layouts.get(1).getKeys()) {
                // System.out.println(key.codes[0]+"\t"+key.label);
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
                catch (Exception e) {
                    e.printStackTrace();
                }
                // mFunctionKeyboard.getKeys().
            }
        }
    }

    @Override
    public View onCreateInputView() {
        mInputView = (CustomKeyboard)getLayoutInflater().inflate(R.layout.keyboard, null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setPreviewEnabled(false);
        populateLayouts();
        
        if (currentKeyboardID > 0) {
            setLatinKeyboard(layouts.get(currentKeyboardID));
            currentKeyboard = layouts.get(currentKeyboardID);
        }
        else {
            setLatinKeyboard(layouts.get(0));
            currentKeyboard = layouts.get(0);
        }
        return mInputView;
    }

    public void setLatinKeyboard(LatinKeyboard nextKeyboard) {
        mInputView.setKeyboard(nextKeyboard);
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
        populateLayouts();
        return mCandidateView;
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);

        setTheme();

        mComposing.setLength(0);
        updateCandidates();

        if (!restarting) {
            mMetaState = 0;
        }
        mCompletions = null;

        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("bord", true)) {
            kv = (CustomKeyboard) getLayoutInflater().inflate(R.layout.keyboard_key_back, null);
        }
        else {
            kv = (CustomKeyboard) getLayoutInflater().inflate(R.layout.keyboard, null);
        }
        populateLayouts();
        setInputType();
        Paint mPaint = new Paint();
        ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(mDefaultFilter);
        mPaint.setColorFilter(filterInvert);
        mCandidateView = new CandidateView(this);
        mCandidateView.setService(this);

        kv.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        currentKeyboard.setRowNumber(getRowNumber());
        kv.setKeyboard(currentKeyboard);

        capsOnFirst();
        kv.setOnKeyboardActionListener(this);

        mPredictionOn = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("pred", false);
        mCompletionOn = false;

        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);

        setInputView(kv);
        // kv.getLatinKeyboard().changeKeyHeight(getHeightKeyModifier());

        setCandidatesView(mCandidateView);
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();

        mComposing.setLength(0);
        updateCandidates();

        setCandidatesViewShown(false);

        if (mInputView != null) {
            mInputView.closing();
        }
    }

    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);

        // if (Variables.isSelecting()) {
        //    toastIt(Variables.cursorStart+" "+oldSelStart+" "+oldSelEnd+" "+newSelStart+" "+newSelEnd);
        // }
        
        if (mComposing.length() > 0 && (newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
            mComposing.setLength(0);
            updateCandidates();
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.finishComposingText();
            }
        }
    }

    @Override
    public void onDisplayCompletions(CompletionInfo[] completions) {
        if (mCompletionOn) {
            mCompletions = completions;
            if (completions == null) {
                setSuggestions(null, false, false);
                return;
            }

            List<String> stringList = new ArrayList<>();
            for (CompletionInfo ci : completions) {
                if (ci != null) {
                    stringList.add(ci.getText().toString());
                }
            }
            setSuggestions(stringList, true, true);
        }
    }

    // @Override
    // public boolean onKeyUp(int keyCode, KeyEvent event) {
    //     System.out.println("onKeyUp: "+keyCode+" "+event+" "+event.isTracking());
    //     // return super.onKeyUp(keyCode, event);
    //     return true;
    // }

    // @Override
    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    //     System.out.println(event.getAction());
    //     if (keyCode == KeyEvent.KEYCODE_DPAD_UP
    //     ||  keyCode == KeyEvent.KEYCODE_DPAD_LEFT
    //     ||  keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
    //     ||  keyCode == KeyEvent.KEYCODE_DPAD_DOWN
    //     ) {
    //         event.startTracking();
    //         return true;
    //     }
    //     System.out.println("onKeyDown: "+keyCode+" "+event+" "+event.isTracking());
    //     // return super.onKeyDown(keyCode, event);
    //     return true;
    // }
    //
    // @Override
    // public boolean onKeyLongPress(int keyCode, KeyEvent event) {
    //     System.out.println("onKeyLongPress: "+keyCode+" "+event+" "+event.isTracking());
    //     return true;
    // }

    long time;

    @Override
    public void onPress(int primaryCode) {
        System.out.println("onPress: "+primaryCode);
        time = System.nanoTime();

        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("vib", false)) {
            Vibrator v = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(40);
        }

        // if (currentKeyboard.name == "Unicode") {
        //     // toastIt(String.valueOf(primaryCode));
        //     // if (primaryCode >= 48 && primaryCode <= 57) { }
        //     // if (primaryCode >= 65 && primaryCode <= 91) { }
        // }
    }

    public Keyboard.Key getKey(int primaryCode) {
        for(Keyboard.Key key : currentKeyboard.getKeys()) {
            if (key.codes[0] == primaryCode) {
                return key;
            }
        }
        return null;
    }

    @Override
    public void onRelease(int primaryCode) {
        time = (System.nanoTime() - time) / 1000000;
        System.out.println("onRelease: "+primaryCode);
        // Keyboard.Key currentKey = getKey(primaryCode);
        // if (currentKey != null) {
        //     System.out.println(currentKey.codes[0]+" "+currentKey.popupCharacters+" "+currentKey.on+" "+currentKey.sticky);
        //
        //     if (time > 300) {
        //         if (primaryCode == -107) {sendKeyUpDown(KeyEvent.KEYCODE_PAGE_UP); }
        //         if (primaryCode == -108) {sendKeyUpDown(KeyEvent.KEYCODE_MOVE_HOME); }
        //         if (primaryCode == -109) {sendKeyUpDown(KeyEvent.KEYCODE_PAGE_DOWN); }
        //         if (primaryCode == -111) {sendKeyUpDown(KeyEvent.KEYCODE_MOVE_END); }
        //         // if (Codes.isAlphabet(primaryCode)) {
        //         //     sendKeyUpDown(currentKey.codes[1]);
        //         // }
        //     }
        //     if (currentKeyboard.name.equals("Unicode")) {
        //         // toastIt(String.valueOf(primaryCode));
        //         if (primaryCode >= 48 && primaryCode <= 57) {handleCharacter(primaryCode); }
        //         if (primaryCode >= 65 && primaryCode <= 91) {handleCharacter(primaryCode); }
        //     }
        // }
        getCurrentInputConnection().setSelection(Variables.cursorStart, Variables.cursorEnd);
    }

    @Override
    public void swipeLeft() {
        prevKeyboard();
    }

    @Override
    public void swipeRight() {
        nextKeyboard();
    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public void commitTyped(InputConnection inputConnection) {
        if (mComposing.length() > 0) {
            inputConnection.commitText(mComposing, mComposing.length());
            mComposing.setLength(0);
            updateCandidates();
        }
    }

    public void updateShiftKeyState(EditorInfo attr) {
        if (attr != null && mInputView != null && layouts.get(0) == mInputView.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != InputType.TYPE_NULL) {
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            mInputView.setShifted(mCapsLock || caps != 0);
        }
    }

    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
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


    public void handleBackspace() {
        final int length = mComposing.length();
        if (length > 1) {
            mComposing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            updateCandidates();
        }
        else if (length > 0) {
            mComposing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
            updateCandidates();
        }
        else {
            getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, 67));
            getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   67));
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void handleCharacter(int primaryCode) {
        primaryCode = Codes.handleCharacter(kv, primaryCode);
        // isGreek
        if (isInputViewShown()) {
            if (kv.isShifted()) primaryCode = Character.toUpperCase(primaryCode);
        }
        if (mPredictionOn && !mWordSeparators.contains(String.valueOf((char) primaryCode))) {
            mComposing.append((char) primaryCode);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            updateShiftKeyState(getCurrentInputEditorInfo());
            updateCandidates();
        }
        if (mPredictionOn && mWordSeparators.contains(String.valueOf((char)primaryCode))) {
            char code = (char) primaryCode;
            if (Character.isLetter(code) && firstCaps || Character.isLetter(code) && Variables.isShift()) {
                code = Character.toUpperCase(code);
            }
            getCurrentInputConnection().setComposingRegion(0, 0);
            getCurrentInputConnection().commitText(String.valueOf(code), 1);
            firstCaps = false;
            setCapsOn(false);
        }
        if (!mPredictionOn) {
            // char code = (char) primaryCode;
            // if (Character.isLetter(code) && firstCaps || Character.isLetter(code) && Variables.isShift()) {
            //     code = Character.toUpperCase(code);
            // }
            getCurrentInputConnection().setComposingRegion(0, 0);
            getCurrentInputConnection().commitText(String.valueOf(Character.toChars(primaryCode)), 1);
            firstCaps = false;
            setCapsOn(false);
        }
    }

    // public void checkToggleCapsLock() {
    //     long now = System.currentTimeMillis();
    //     if (mLastShiftTime + 800 > now) {
    //         mCapsLock = !mCapsLock;
    //         mLastShiftTime = 0;
    //     }
    //     else {
    //         mLastShiftTime = now;
    //     }
    // }

    public boolean isWordSeparator(String s) {
        return s.contains(". ") || s.contains("? ") || s.contains("! ");
    }

    public void pickSuggestionManually(int index) {
        if (mCompletionOn && mCompletions != null && index >= 0 && index < mCompletions.length) {
            CompletionInfo ci = mCompletions[index];
            getCurrentInputConnection().commitCompletion(ci);
            if (mCandidateView != null) {
                mCandidateView.clear();
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
        else if (mComposing.length() > 0) {
            if (mPredictionOn && mSuggestions != null && index >= 0) {
                mComposing.replace(0, mComposing.length(), mSuggestions.get(index));
            }
            commitTyped(getCurrentInputConnection());
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
        catch (Exception ignored) {
        }
    }

    public void setCapsOn(boolean on) {
        if (Variables.isShift()) {
            kv.getKeyboard().setShifted(true);
            kv.invalidateAllKeys();
        }
        else {
            kv.getKeyboard().setShifted(on);
            kv.invalidateAllKeys();
        }
    }

    // public void toggleCapsOn() {
    //     kv.getKeyboard().setShifted(!kv.getKeyboard().isShifted());
    // }


    public void processKeyCombo(int keycode) {
        if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) {
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, Codes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP, Codes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            }
            else {
                if (Variables.isCtrl()) {
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, Codes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP, Codes.getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                }
                if (Variables.isAlt()) {
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, Codes.getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP, Codes.getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                }
            }
        }
    }

    public void setTheme() {
        Context context = getBaseContext();
        SharedPreferences DefaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String theme = DefaultSharedPreferences.getString("theme", "1");
        try {
            switch (theme) {
                case "1": mDefaultFilter = Themes.sNoneColorArray; break;
                case "2": mDefaultFilter = Themes.sNegativeColorArray; break;
                case "3": mDefaultFilter = Themes.sBlueWhiteColorArray; break;
                case "4": mDefaultFilter = Themes.sBlueBlackColorArray; break;
                case "5": mDefaultFilter = Themes.sRedWhiteColorArray; break;
                case "6": mDefaultFilter = Themes.sRedBlackColorArray; break;
                case "7": mDefaultFilter = Themes.sOrangeBlackColorArray; break;
                case "8": mDefaultFilter = Themes.sMaterialDarkColorArray; break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setInputType() {
        EditorInfo attribute = getCurrentInputEditorInfo();
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("start", "1").equals("1")) {
            switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
                case InputType.TYPE_CLASS_NUMBER:
                case InputType.TYPE_CLASS_DATETIME:
                case InputType.TYPE_CLASS_PHONE:
                    currentKeyboard = new LatinKeyboard(this, R.xml.numbers);
                    break;
                default:
                    currentKeyboard = new LatinKeyboard(this, R.xml.qwerty);
                    break;
            }
        }
        else {
            currentKeyboard = new LatinKeyboard(this, R.xml.arrow_keys);
            setRowNumber(5);
            currentKeyboard.setRowNumber(getRowNumber());
        }
        if (kv != null) {
            kv.setKeyboard(currentKeyboard);
        }
    }

    public void capsOnFirst() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("caps", false)) {
            if (getCursorCapsMode(getCurrentInputConnection(), getCurrentInputEditorInfo()) != 0) {
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
    public String replaceLinebreaks(String text) { return text.replaceAll("\n", ""); }
    public String underscoresToSpaces(String text) { return text.replaceAll("_", " "); }
    public String dashesToSpaces(String text) { return text.replaceAll("-", " "); }
    public String spacesToUnderscores(String text) { return text.replaceAll(" ", "_"); }
    public String spacesToDashes(String text) { return text.replaceAll(" ", "-"); }
    public String removeSpaces(String text) { return text.replaceAll(" ", ""); }

    public static String toTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            }
            else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            }
            else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    public void sendCustomKey(String key) {
        InputConnection ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        int cursorLocation = getSelectionStart();
        CharSequence ins = PreferenceManager.getDefaultSharedPreferences(this).getString(key, "");
        ic.beginBatchEdit();
        ic.commitText(ins, cursorLocation+ins.length());
        ic.endBatchEdit();
    }

    public void performReplace(String newText) {
        InputConnection ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0) {
            int a = getSelectionStart();
            int b = newText.length();
            ic.commitText(newText, b);
            ic.setSelection(a, b);
        }
    }

    public int getSelectionStart() {
        InputConnection ic = getCurrentInputConnection();
        return ic.getTextBeforeCursor(1024, 0).length();
    }
    public int getSelectionLength() {
        InputConnection ic = getCurrentInputConnection();
        String text = ic.getSelectedText(0).toString();
        return ic.getSelectedText(0).toString() == null ? 0 : text.length();
    }
    public int getSelectionEnd() {
        return getSelectionStart() + getSelectionLength();
    }

    public int selectionCase = 0;

    public String getText(InputConnection ic) {
        CharSequence text = ic.getSelectedText(0);
        if (text == null) {
            return "";
        }
        return (String)text;
    }

    public void processKeyList(int[] keys) {
        for(int i = 0; i < keys.length; i++) {
            getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keys[i]));
            getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   keys[i]));
        }
    }

    public static final int[] hexPasses = {
         -5, -7, -107, -108, -109, -111, -101, -102, -103, -8, -9, -10, -11, -499, 32, 9, 10
    };
    public static final int[] hexFunctions = {
         -2001, -2002, -2003, -2004, -2005, -2006, -2007, -2008,
    };
    public static final int[] hexCaptures = {
         48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70
    };

    public static boolean contains(int[] arr, int item) {
        for (int n : arr) {
            if (item == n) {
                return true;
            }
        }
        return false;
    }

    public void sendKeyUpDown(int primaryCode) {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, primaryCode));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   primaryCode));
    }

    public static String hexBuffer = "";

    public String convertNumberBase(String number, int base1, int base2) {
        try {
            return Integer.toString(Integer.parseInt(number, base1), base2).toUpperCase();
        }
        catch (Exception e) {
            return number;
        }
    }

    public String convertFromUnicodeToNumber(String glyph) {
        try {
            toastIt(String.valueOf((int)glyph.codePointAt(0)));
            return String.valueOf((int)glyph.codePointAt(0));
        }
        catch (Exception e) {
            return glyph;
        }
    }

    public String convertFromNumberToUnicode(String number) {
        try {
            return String.valueOf((char)(int)Integer.decode("0x"+StringUtils.leftPad(number, 4,"0")));
        }
        catch (Exception e) {
            return number;
        }
    }

    public String setKeyboardLayout(int newKeyboardID) {
        if (newKeyboardID < layouts.size()) {
            currentKeyboardID = newKeyboardID;
            currentKeyboard = layouts.get(currentKeyboardID);
            kv.setKeyboard(currentKeyboard);
        }
        return currentKeyboard.name;
    }

    public void setSelection() {
        InputConnection ic = getCurrentInputConnection();
        int a = Variables.cursorStart;
        int b = Variables.cursorEnd;
        int min = Math.min(a, b);
        int max = Math.max(a, b);
        if (Variables.isSelecting()) {
            toastIt(min+" "+max);
        }
    }

    public String splitWithSpaces(String str) {
        return str.replaceAll("(.)", "$1 ");
    }

    public String joinWithSpaces(String str) {
        return str.replaceAll(" ", "");
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();

        ic.requestCursorUpdates(3);
        // mInputMethodManager.toggleSoftInput();
        int[] choice49 = {KeyEvent.KEYCODE_MOVE_END, KeyEvent.KEYCODE_FORWARD_DEL};

        if (currentKeyboard.name == "Unicode" && !contains(hexPasses, primaryCode)) {
            System.out.println(currentKeyboard.name+" "+primaryCode);
            if (contains(hexCaptures, primaryCode)) {
                if (hexBuffer.length() > 3) hexBuffer = "";
                hexBuffer += (char)primaryCode;
                System.out.println(hexBuffer);
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
                performReplace(convertFromUnicodeToNumber(getText(ic)));
            }
            if (primaryCode == -2004) {
                performReplace(convertFromNumberToUnicode(getText(ic)));
            }

            if (primaryCode == -2005) {
                if (hexBuffer.length() > 0) { hexBuffer = hexBuffer.substring(0, hexBuffer.length() - 1); }
                else { hexBuffer = "0000"; }
                System.out.println(hexBuffer);
                kv.draw(new Canvas());
            }
            if (primaryCode == -2006) {
                hexBuffer = "0000";
                System.out.println(hexBuffer);
                kv.draw(new Canvas());
            }
            kv.draw(new Canvas());
            return;
        }

        switch (primaryCode) {
            case  -5: handleBackspace(); break;
            case  -7: ic.deleteSurroundingText(0, 1); break;
            case  -8: ic.setSelection(0, (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length()); break;
            case  -9: sendKeyUpDown(KeyEvent.KEYCODE_CUT); break;
            case -10: sendKeyUpDown(KeyEvent.KEYCODE_COPY); break;
            case -11: sendKeyUpDown(KeyEvent.KEYCODE_PASTE); break;
            case -12: Variables.toggleIsBold(); break;
            case -13: Variables.toggleIsItalic(); break;
            case -15: sendKeyUpDown(KeyEvent.KEYCODE_VOLUME_DOWN); break;
            case -16: sendKeyUpDown(KeyEvent.KEYCODE_VOLUME_UP); break;
            case -17: sendKeyUpDown(KeyEvent.KEYCODE_CAMERA); break;
            case -18: sendKeyUpDown(KeyEvent.KEYCODE_EXPLORER); break;
            case -19: sendKeyUpDown(KeyEvent.KEYCODE_MENU); break;
            case -20: sendKeyUpDown(KeyEvent.KEYCODE_NOTIFICATION); break;
            case -21: sendKeyUpDown(KeyEvent.KEYCODE_SEARCH); break;

            case -67:
                Variables.toggleSelecting();
                if (Variables.isSelecting()) {
                    Variables.cursorStart = getSelectionStart();
                    Variables.cursorEnd = getSelectionStart();
                }
                else {
                    Variables.cursorStart = -1;
                    Variables.cursorEnd = -1;
                }
                break;
            case -22:
            sendKeyUpDown(KeyEvent.KEYCODE_PAGE_UP);
            break;
            case -23:
            sendKeyUpDown(KeyEvent.KEYCODE_PAGE_DOWN);
            break;
            case -25:
            sendKeyUpDown(KeyEvent.KEYCODE_MOVE_HOME);
            break;
            case -26:
            sendKeyUpDown(KeyEvent.KEYCODE_MOVE_END);
            break;
            case -108:

                sendKeyUpDown(KeyEvent.KEYCODE_DPAD_LEFT);
                if (Variables.isSelecting()) {
                    Variables.cursorEnd--;
                    setSelection();
                }
            break;
            case -111:
                sendKeyUpDown(KeyEvent.KEYCODE_DPAD_RIGHT);
                if (Variables.isSelecting()) {
                    Variables.cursorEnd++;
                    setSelection();
                }
            break;
            case -107:
            sendKeyUpDown(KeyEvent.KEYCODE_DPAD_UP);
            break;
            case -109:
            sendKeyUpDown(KeyEvent.KEYCODE_DPAD_DOWN);
            break;


            case -24: sendKeyUpDown(KeyEvent.KEYCODE_BUTTON_START); break;
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
            case -48: processKeyList(choice49); break;
            case -49: performReplace(replaceLinebreaks(getText(ic))); break;
            case -50: Variables.toggleIsReflected(); break;
            case -51: performReplace(convertNumberBase(getText(ic), 2, 10)); break;
            case -52: performReplace(convertNumberBase(getText(ic), 10, 2)); break;
            case -53: performReplace(convertNumberBase(getText(ic), 8, 10)); break;
            case -54: performReplace(convertNumberBase(getText(ic), 10, 8)); break;
            case -55: performReplace(convertNumberBase(getText(ic), 16, 10)); break;
            case -56: performReplace(convertNumberBase(getText(ic), 10, 16)); break;
            case -57: Variables.toggleIsSmallcaps(); break;
            case -58: toastIt(setKeyboardLayout(0)); break;
            case -59: toastIt(setKeyboardLayout(1)); break;
            case -60: toastIt(setKeyboardLayout(2)); break;
            case -61: toastIt(setKeyboardLayout(3)); break;
            case -62: toastIt(setKeyboardLayout(4)); break;
            case -63: toastIt(setKeyboardLayout(5)); break;
            case -64: toastIt(setKeyboardLayout(6)); break;
            case -65: toastIt(setKeyboardLayout(7)); break;
            case -66: toastIt(setKeyboardLayout(8)); break;
            case -68: Variables.toggle127280(); break;
            case -69: Variables.toggle127312(); break;
            case -70: Variables.toggle127344(); break;
            case -71: Variables.toggle127462(); break;
            case -72: Variables.toggle9372(); break;
            case -73: Variables.toggle9398(); break;
            case -74: performReplace(splitWithSpaces(getText(ic))); break;
            case -75: performReplace(joinWithSpaces(getText(ic))); break;
            
            case -101: prevKeyboard(); break;
            case -102: nextKeyboard(); break;
            case -112: sendKeyUpDown(KeyEvent.KEYCODE_ESCAPE); break;
            case -501: sendCustomKey("k1"); break;
            case -502: sendCustomKey("k2"); break;
            case -503: sendCustomKey("k3"); break;
            case -504: sendCustomKey("k4"); break;
            case -505: sendCustomKey("k5"); break;
            case -506: sendCustomKey("k6"); break;
            case -507: sendCustomKey("k7"); break;
            case -508: sendCustomKey("k8"); break;
            case -509: sendKeyUpDown(KeyEvent.KEYCODE_DPAD_UP_LEFT); break;
            case -510: sendKeyUpDown(KeyEvent.KEYCODE_DPAD_UP_RIGHT); break;
            case -511: sendKeyUpDown(KeyEvent.KEYCODE_DPAD_DOWN_LEFT); break;
            case -512: sendKeyUpDown(KeyEvent.KEYCODE_DPAD_DOWN_RIGHT); break;
            case -999: ic.deleteSurroundingText(1024, 1024); break;
            case -1000: performReplace(getText(ic).toUpperCase()); break;
            case -1001: performReplace(toTitleCase(getText(ic))); break;
            case -1002: performReplace(getText(ic).toLowerCase()); break;
            case -1003: performReplace(dashesToSpaces(getText(ic))); break;
            case -1004: performReplace(underscoresToSpaces(getText(ic))); break;
            case -1005: performReplace(spacesToDashes(getText(ic))); break;
            case -1006: performReplace(spacesToUnderscores(getText(ic))); break;
            case -1007: performReplace(removeSpaces(getText(ic))); break;
            case -1:
                if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0) {
                    String text = ic.getSelectedText(0).toString();
                    int a = getSelectionStart();
                    int b = getSelectionEnd();
                    if (selectionCase == 0) {
                        text = text.toUpperCase();
                        selectionCase = 1;
                    }
                    else if (selectionCase == 1) {
                        text = toTitleCase(text);
                        selectionCase = 2;
                    }
                    else {
                        text = text.toLowerCase();
                        selectionCase = 0;
                    }
                    ic.commitText(text, b);
                    ic.setSelection(a, b);
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
                }
                break;
            case -2:
                currentKeyboard = new LatinKeyboard(this, R.xml.qwerty);
                kv.setKeyboard(currentKeyboard);
                break;
            case -14:
                Variables.setBoldOff();
                Variables.setItalicOff();
                setAllOff();
                break;
            case -113:
                if (Variables.isCtrl()) { Variables.setCtrlOff(); }
                else { Variables.setCtrlOn(); }
                kv.draw(new Canvas());
                break;
            case -114:
                if (Variables.isAlt()) { Variables.setAltOff(); }
                else { Variables.setAltOn(); }
                kv.draw(new Canvas());
                break;
            case -121:
                if (Variables.isAnyOn()) {
                    if (Variables.isCtrl() && Variables.isAlt()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isAlt()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isCtrl()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 67, 0, KeyEvent.META_CTRL_ON));
                    }
                }
                else {
                    sendKeyUpDown(67);
                }
                break;
            case -122:
                if (Variables.isAnyOn()) {
                    if (Variables.isCtrl() && Variables.isAlt()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isAlt()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isCtrl()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, 61, 0, KeyEvent.META_CTRL_ON));
                    }
                }
                else {
                    sendKeyUpDown(61);
                }
                break;
            case -499:
                setKeyboardLayout(layouts.size()-1);
                break;
            case -498:
                InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                if (imeManager != null) { imeManager.showInputMethodPicker(); }
                break;
            case 10:
                EditorInfo curEditor = getCurrentInputEditorInfo();
                switch (curEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
                    case EditorInfo.IME_ACTION_GO: getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_GO); break;
                    case EditorInfo.IME_ACTION_SEARCH: getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_SEARCH); break;
                    default: sendKeyUpDown(66); break;
                }
                break;
            default:
                if (Variables.isAnyOn()) { processKeyCombo(primaryCode); }
                else {
                    try {
                        System.out.println("handleCharacter: "+primaryCode);
                        handleCharacter(primaryCode);
                        kv.draw(new Canvas());
                        if (Variables.isReflected()) {
                            sendKeyUpDown(KeyEvent.KEYCODE_DPAD_LEFT);
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Exception Handling Character: "+primaryCode);
                    }
                }
        }
        try {
            if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("caps", true)) {
                if (isWordSeparator(ic.getTextBeforeCursor(2, 0).toString())) {
                    setCapsOn(true);
                    firstCaps = true;
                }
            }
        }
        catch (Exception e) {}
    }

    public short getRowNumber() { return rowNumber; }

    public void setRowNumber(int number) { rowNumber = (short) number; }
}
