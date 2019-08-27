package com.vlath.keyboard;

import android.content.Context;
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
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;

import java.util.ArrayList;
import java.util.List;

public class PCKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener, SpellCheckerSession.SpellCheckerSessionListener {

    private static final float[] sNoneColorArray = {
            1.0f, 0, 0, 0, 0, // red
            0, 1.0f, 0, 0, 0, // green
            0, 0, 1.0f, 0, 0, // blue
            0, 0, 0, 1.0f, 0 // alpha
    };

    private static final float[] sNegativeColorArray = {
            -1.0f, 0, 0, 0, 255, // red
            0, -1.0f, 0, 0, 255, // green
            0, 0, -1.0f, 0, 255, // blue
            0, 0, 0, 1.0f, 0 // alpha
    };
    private static final float[] sBlueBlackColorArray = {
            -0.6f, 0, 0, 0, 41, // red
            0, -0.6f, 0, 0, 128, // green
            0, 0, -0.6f, 0, 185, // blue
            0, 0, 0, 1.0f, 0 // alpha
    };
    private static final float[] sBlueWhiteColorArray = {
            1.0f, 0, 0, 0, 41, // red
            0, 1.0f, 0, 0, 128, // green
            0, 0, 1.0f, 0, 185, // blue
            0, 0, 0, 1.0f, 1 // alpha
    };
    private static final float[] sRedWhiteColorArray = {
            1.0f, 0, 0, 0, 192, // red
            0, 1.0f, 0, 0, 57, // green
            0, 0, 1.0f, 0, 43, // blue
            0, 0, 0, 1.0f, 0 // alpha
    };
    private static final float[] sRedBlackColorArray = {
            -0.6f, 0, 0, 0, 192, // red
            0, -0.6f, 0, 0, 57, // green
            0, 0, -0.6f, 0, 43, // blue
            0, 0, 0, 1.0f, 0 // alpha
    };
    private static final float[] sOrangeBlackColorArray = {
            1.0f, 0, 0, 0, 230, // red
            0, 1.0f, 0, 0, 126, // green
            0, 0, 1.0f, 0, 34, // blue
            0, 0, 0, 1.0f, 0 // alpha
    };
    private static final float[] sMaterialDarkColorArray = {
            1.0f, 0, 0, 0, 55, // red
            0, 1.0f, 0, 0, 71, // green
            0, 0, 1.0f, 0, 79, // blue
            0, 0, 0, 1.0f, 1 // alpha
    };

    private InputMethodManager mInputMethodManager;

    private CustomKeyboard mInputView;
    private CandidateView mCandidateView;
    private CompletionInfo[] mCompletions;

    private StringBuilder mComposing = new StringBuilder();
    private boolean mPredictionOn;
    private boolean mCompletionOn;
    private int mLastDisplayWidth;
    private boolean mCapsLock;
    private long mLastShiftTime;
    private long mMetaState;

    private LatinKeyboard mQwertyKeyboard;
    private LatinKeyboard mSymbolKeyboard;
    private LatinKeyboard mFunctionKeyboard;
    private LatinKeyboard mFutharkKeyboard;

    private String mWordSeparators;

    private SpellCheckerSession mScs;
    private List<String> mSuggestions;

    private boolean firstCaps = false;
    private float[] mDefaultFilter;
    long shift_pressed = 0;

    private short rowNumber = 6;
    private CustomKeyboard kv;

    private LatinKeyboard currentKeyboard;
    private LatinKeyboard mCurKeyboard;

    private int standardKeyboardID = R.xml.qwerty;
    private int currentKeyboardID = R.xml.qwerty;

    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mWordSeparators = getResources().getString(R.string.word_separators);
        final TextServicesManager tsm = (TextServicesManager) getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        mScs = tsm.newSpellCheckerSession(null, null, this, true);
    }

    @Override
    public void onInitializeInterface() {
        if (mQwertyKeyboard != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) {
                return;
            }
            mLastDisplayWidth = displayWidth;
        }
        mQwertyKeyboard = new LatinKeyboard(this, R.xml.qwerty);
        mSymbolKeyboard = new LatinKeyboard(this, R.xml.symbol);
        mFutharkKeyboard = new LatinKeyboard(this, R.xml.futhark);
    }

    @Override
    public View onCreateInputView() {
        mInputView = (CustomKeyboard) getLayoutInflater().inflate(R.layout.keyboard, null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setPreviewEnabled(false);
        setLatinKeyboard(mQwertyKeyboard);
        return mInputView;
    }

    private void setLatinKeyboard(LatinKeyboard nextKeyboard) {
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

        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("bord", false)) {
            kv = (CustomKeyboard) getLayoutInflater().inflate(R.layout.keyboard_key_back, null);
        }
        else {
            kv = (CustomKeyboard) getLayoutInflater().inflate(R.layout.keyboard, null);
        }
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
        kv.getLatinKeyboard().changeKeyHeight(getHeightKeyModifier());

        setCandidatesView(mCandidateView);

    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();

        mComposing.setLength(0);
        updateCandidates();

        setCandidatesViewShown(false);

        mCurKeyboard = mQwertyKeyboard;
        if (mInputView != null) {
            mInputView.closing();
        }
    }

    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);

        // If the current selection in the text view changes, we should
        // clear whatever candidate text we have.
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

            List<String> stringList = new ArrayList<String>();
            for (int i = 0; i < completions.length; i++) {
                CompletionInfo ci = completions[i];
                if (ci != null) {
                    stringList.add(ci.getText().toString());
                }
            }
            setSuggestions(stringList, true, true);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void commitTyped(InputConnection inputConnection) {
        if (mComposing.length() > 0) {
            inputConnection.commitText(mComposing, mComposing.length());
            mComposing.setLength(0);
            updateCandidates();
        }
    }

    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null && mInputView != null && mQwertyKeyboard == mInputView.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != InputType.TYPE_NULL) {
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            mInputView.setShifted(mCapsLock || caps != 0);
        }
    }

    private boolean isDigit(int code) {
        if (Character.isDigit(code)) {
            return true;
        }
        else {
            return false;
        }
    }

    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }

    private void sendKey(int keyCode) {
        switch (keyCode) {
            case '\n':
                keyDownUp(KeyEvent.KEYCODE_ENTER);
                break;
            default:
                if (keyCode >= '0' && keyCode <= '9') {
                    keyDownUp(keyCode - '0' + KeyEvent.KEYCODE_0);
                }
                else {
                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                }
                break;
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

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private void updateCandidates() {
        if (!mCompletionOn) {
            if (mComposing.length() > 0) {
                ArrayList<String> list = new ArrayList<String>();
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

    private void handleBackspace() {
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
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown() && !isDigit(primaryCode)) {
            if (kv.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
                if (Variables.isBold() && Variables.isItalic()) {
                    primaryCode += 120315;
                }
                else if (Variables.isBold()) {
                    primaryCode += 120211;
                }
                else if (Variables.isItalic()) {
                    primaryCode += 120263;
                }
            }
            else {
                if (Variables.isBold() && Variables.isItalic()) {
                    primaryCode += 120309;
                }
                else if (Variables.isBold()) {
                    primaryCode += 120205;
                }
                else if (Variables.isItalic()) {
                    primaryCode += 120257;
                }
            }
        }

        // isGreek

        if (mPredictionOn && !mWordSeparators.contains(String.valueOf((char) primaryCode))) {
            mComposing.append((char) primaryCode);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            updateShiftKeyState(getCurrentInputEditorInfo());
            updateCandidates();
        }
        if (mPredictionOn && mWordSeparators.contains(String.valueOf((char) primaryCode))) {
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
            char code = (char) primaryCode;
            if (Character.isLetter(code) && firstCaps || Character.isLetter(code) && Variables.isShift()) {
                code = Character.toUpperCase(code);
            }
            getCurrentInputConnection().setComposingRegion(0, 0);
            getCurrentInputConnection().commitText(String.valueOf(Character.toChars(primaryCode)), 1);
            firstCaps = false;
            setCapsOn(false);
        }
    }

    public boolean isWordSeparator(String s) {
        if (s.contains(". ") || s.contains("? ") || s.contains("! ")) {
            return true;
        }
        return false;
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

    public void onPress(int primaryCode) {
        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("vib", false)) {
            Vibrator v = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(40);
        }
    }

    public void onRelease(int primaryCode) {

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

    private void dumpSuggestionsInfoInternal(final List<String> sb, final SuggestionsInfo si, final int length, final int offset) {
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
                    dumpSuggestionsInfoInternal(sb, ssi.getSuggestionsInfoAt(j), ssi.getOffsetAt(j), ssi.getLengthAt(j));
                }
            }
            setSuggestions(sb, true, true);
        } catch (Exception ignored) {
        }
    }

    private void setCapsOn(boolean on) {
        if (Variables.isShift()) {
            kv.getKeyboard().setShifted(true);
//      kv.invalidateAllKeys();
        }
        else {
            kv.getKeyboard().setShifted(on);
//      kv.invalidateAllKeys();
        }
    }

    private void processKeyCombo(int keycode) {
        if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) {
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP, getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            }
            else {
                if (Variables.isCtrl()) {
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP, getHardKeyCode(keycode), 0, KeyEvent.META_CTRL_ON));
                }
                if (Variables.isAlt()) {
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP, getHardKeyCode(keycode), 0, KeyEvent.META_ALT_ON));
                }
            }
        }
    }

    private int getHardKeyCode(int keycode) {
        char code = (char) keycode;
        switch (String.valueOf(code)) {
            case "a": return KeyEvent.KEYCODE_A;
            case "b": return KeyEvent.KEYCODE_B;
            case "c": return KeyEvent.KEYCODE_C;
            case "d": return KeyEvent.KEYCODE_D;
            case "e": return KeyEvent.KEYCODE_E;
            case "f": return KeyEvent.KEYCODE_F;
            case "g": return KeyEvent.KEYCODE_G;
            case "h": return KeyEvent.KEYCODE_H;
            case "i": return KeyEvent.KEYCODE_I;
            case "j": return KeyEvent.KEYCODE_J;
            case "k": return KeyEvent.KEYCODE_K;
            case "l": return KeyEvent.KEYCODE_L;
            case "m": return KeyEvent.KEYCODE_M;
            case "n": return KeyEvent.KEYCODE_N;
            case "o": return KeyEvent.KEYCODE_O;
            case "p": return KeyEvent.KEYCODE_P;
            case "q": return KeyEvent.KEYCODE_Q;
            case "r": return KeyEvent.KEYCODE_R;
            case "s": return KeyEvent.KEYCODE_S;
            case "t": return KeyEvent.KEYCODE_T;
            case "u": return KeyEvent.KEYCODE_U;
            case "v": return KeyEvent.KEYCODE_V;
            case "w": return KeyEvent.KEYCODE_W;
            case "x": return KeyEvent.KEYCODE_X;
            case "y": return KeyEvent.KEYCODE_Y;
            case "z": return KeyEvent.KEYCODE_Z;
            default: return keycode;
        }
    }

    public void setTheme() {
        switch (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("theme", "2")) {
            case "1": mDefaultFilter = sNoneColorArray; break;
            case "2": mDefaultFilter = sNegativeColorArray; break;
            case "3": mDefaultFilter = sBlueWhiteColorArray; break;
            case "4": mDefaultFilter = sBlueBlackColorArray; break;
            case "5": mDefaultFilter = sRedWhiteColorArray; break;
            case "6": mDefaultFilter = sRedBlackColorArray; break;
            case "7": mDefaultFilter = sOrangeBlackColorArray; break;
            case "8": mDefaultFilter = sMaterialDarkColorArray; break;
        }
    }

    private void setInputType() {
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

    private void capsOnFirst() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("caps", true)) {
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

    private int getCursorCapsMode(InputConnection ic, EditorInfo attr) {
        int caps = 0;
        EditorInfo ei = getCurrentInputEditorInfo();
        if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
            caps = ic.getCursorCapsMode(attr.inputType);
        }
        return caps;
    }

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

    private int getSelectionStart() {
        InputConnection ic = getCurrentInputConnection();
        return ic.getTextBeforeCursor(1024, 0).length();
    }

    private int getSelectionLength() {
        InputConnection ic = getCurrentInputConnection();
        return ic.getSelectedText(0).toString().length();
    }

    private int getSelectionEnd() {
        return getSelectionStart() + getSelectionLength();
    }

    private int selectionCase = 0;

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        switch (primaryCode) {
            case -7:
                ic.deleteSurroundingText(0, 1);
                break;
            case -8:
                ic.setSelection(0, (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length());
                break;
            case -9:
                keyDownUp(KeyEvent.KEYCODE_CUT);
                break;
            case -10:
                keyDownUp(KeyEvent.KEYCODE_COPY);
                break;
            case -11:
                keyDownUp(KeyEvent.KEYCODE_PASTE);
                break;
            case -12:
                Variables.toggleIsBold();
                break;
            case -13:
                Variables.toggleIsItalic();
                break;
            case -14:
                Variables.setBoldOff();
                Variables.setItalicOff();
                break;
            case Keyboard.KEYCODE_DELETE:
                handleBackspace();
                break;
            case Keyboard.KEYCODE_SHIFT:
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
                    if (shift_pressed + 200 > System.currentTimeMillis()) {
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
            case 10:
                EditorInfo curEditor = getCurrentInputEditorInfo();
                switch (curEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
                    case EditorInfo.IME_ACTION_GO:
                        getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_GO);
                        break;
                    case EditorInfo.IME_ACTION_SEARCH:
                        getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_SEARCH);
                        break;
                    default:
                        keyDownUp(66);
                        break;
                }
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
//        kv.invalidateAllKeys();
                currentKeyboard = new LatinKeyboard(this, R.xml.qwerty);
                kv.setKeyboard(currentKeyboard);
                kv.getLatinKeyboard().changeKeyHeight(getHeightKeyModifier());
                break;
            case LatinKeyboard.KEYCODE_LAYOUT_SWITCH:
            case LatinKeyboard.KEYCODE_STANDARD_SWITCH:
//        kv.invalidateAllKeys();
                if (currentKeyboardID == R.xml.qwerty) {
                    currentKeyboardID = R.xml.symbol;
                }
                else if (currentKeyboardID == R.xml.symbol) {
                    currentKeyboardID = R.xml.futhark;
                }
                else if (currentKeyboardID == R.xml.futhark) {
                    currentKeyboardID = R.xml.qwerty;
                }

                currentKeyboard = new LatinKeyboard(getBaseContext(), currentKeyboardID);
                kv.setKeyboard(currentKeyboard);

                kv.getLatinKeyboard().changeKeyHeight(getHeightKeyModifier());
                break;
            case LatinKeyboard.KEYCODE_DPAD_L:
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_LEFT));
                break;
            case LatinKeyboard.KEYCODE_DPAD_R:
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_RIGHT));
                break;
            case LatinKeyboard.KEYCODE_DPAD_U:
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP));
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_UP));
                break;
            case LatinKeyboard.KEYCODE_DPAD_D:
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_DOWN));
                break;
            case LatinKeyboard.KEYCODE_ESCAPE:
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ESCAPE, 0));
                getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ESCAPE, 0));
                break;
            case LatinKeyboard.KEYCODE_CTRL:
                if (Variables.isCtrl()) {
                    Variables.setCtrlOff();
                    kv.draw(new Canvas());
                }
                else {
                    Variables.setCtrlOn();
                    kv.draw(new Canvas());
                }
                break;
            case LatinKeyboard.KEYCODE_ALT:
                if (Variables.isAlt()) {
                    Variables.setAltOff();
                    kv.draw(new Canvas());
                }
                else {
                    Variables.setAltOn();
                    kv.draw(new Canvas());
                }
                break;
            case LatinKeyboard.KEYCODE_DEL_PROCESS:
                if (Variables.isAnyOn()) {
                    if (Variables.isCtrl() && Variables.isAlt()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isAlt()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isCtrl()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_CTRL_ON));
                    }
                }
                else {
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0));
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL, 0));
                }
                break;
            case LatinKeyboard.KEYCODE_I_DONT_KNOW_WHY_I_PUT_THAT_HERE:
                if (Variables.isAnyOn()) {
                    if (Variables.isCtrl() && Variables.isAlt()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isAlt()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_ALT_ON));
                    }
                    if (Variables.isCtrl()) {
                        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_CTRL_ON));
                    }
                }
                else {
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0));
                    getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_TAB, 0));
                }
                break;
            default:
                if (Variables.isAnyOn()) {
                    processKeyCombo(primaryCode);
                }
                else {
                    handleCharacter(primaryCode, keyCodes);
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
        catch (Exception e) {
        }
    }

    public short getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int number) {
        rowNumber = (short) number;
    }

    public double getHeightKeyModifier() {
        return (double) PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("height", 50) / (double) 50;
    }
}
