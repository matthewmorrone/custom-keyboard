package com.custom.keyboard;

import android.app.Dialog;
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
import android.media.AudioManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.*;
import android.provider.Settings;
import android.provider.UserDictionary;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CustomInputMethodService extends InputMethodService
    implements KeyboardView.OnKeyboardActionListener {

    static final boolean PROCESS_HARD_KEYS = true;

    private boolean mCapsLock;
    private int mLastDisplayWidth;
    private long mLastShiftTime;
    private long mMetaState;

    private String mWordSeparators;

    private boolean firstCaps = false;
    private boolean isSymbols = false;
    private boolean shiftSim = false;

    private float[] mDefaultFilter;
    long shift_pressed = 0;

    int MAX = 65536;

    InputConnection ic = this.getCurrentInputConnection();

    SharedPreferences sharedPreferences;
    Toast toast;

    private short rowNumber = 6;
    private CustomKeyboardView kv;
    private CandidateView mCandidateView;
    private ArrayList<String> suggestions = new ArrayList<>();
    private boolean mPredictionOn;
    private InputMethodManager mInputMethodManager;
    private CustomKeyboard currentKeyboard;
    private CustomKeyboard standardKeyboard;

    private SpellChecker spellChecker;

    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);

        mWordSeparators = this.getResources().getString(R.string.word_separators);

        toast = new Toast(getBaseContext());

        // EmojiManager.install(new GoogleEmojiProvider());

        spellChecker = new SpellChecker(getApplicationContext());
    }

    @Override
    public void onInitializeInterface() {
        if (standardKeyboard != null) {
            int displayWidth = this.getMaxWidth();
            if (displayWidth == mLastDisplayWidth) {
                return;
            }
            mLastDisplayWidth = displayWidth;
        }
        standardKeyboard = new CustomKeyboard(this, R.layout.primary);
    }

    @Override
    public View onCreateInputView() {
        kv = (CustomKeyboardView)this.getLayoutInflater().inflate(R.layout.keyboard, null);
        kv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    kv.closing(); // Close popup keyboard if it's showing
                }
                return false;
            }
        });

        kv.setOnKeyboardActionListener(this);
        // kv.setPreviewEnabled(false);
        kv.setKeyboard(standardKeyboard);

        return kv;
    }

    @Override
    public View onCreateCandidatesView() {
        this.mCandidateView = new CandidateView(this);
        this.mCandidateView.setService(this);
        Paint mPaint = new Paint();
        this.setTheme();
        ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(mDefaultFilter);
        mPaint.setColorFilter(filterInvert);
        this.mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);

        return mCandidateView;
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());

        // mComposing.setLength(0);
        // updateCandidates();

        if (!restarting) {
            mMetaState = 0;
        }

        kv = (CustomKeyboardView)this.getLayoutInflater().inflate(R.layout.keyboard, null);

        float transparency = sharedPreferences.getInt("transparency", 0) / 100f;
        kv.setBackgroundColor(Color.argb(transparency, 0, 0, 0));

        this.setInputType();
        Paint mPaint = new Paint();
        this.setTheme();
        ColorMatrixColorFilter filterInvert = new ColorMatrixColorFilter(mDefaultFilter);
        mPaint.setColorFilter(filterInvert);

        this.mCandidateView = new CandidateView(this);
        this.mCandidateView.setService(this);
        this.mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        this.setCandidatesView(mCandidateView);

        kv.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        this.currentKeyboard.setRowNumber(this.getRowNumber());

        // currentKeyboard.setImeOptions(this.getResources(), attribute.inputType & InputType.TYPE_MASK_CLASS);

        kv.setKeyboard(currentKeyboard);

        this.capsOnFirst();
        kv.setOnKeyboardActionListener(this);

        setInputView(kv);

        kv.getCustomKeyboard().changeKeyHeight(getHeightKeyModifier());

        boolean mPreviewOn = sharedPreferences.getBoolean("preview", false);
        kv.setPreviewEnabled(mPreviewOn);

        mPredictionOn = sharedPreferences.getBoolean("pred", false);

        if (mPredictionOn) {
            this.setCandidatesViewShown(true);
        }

    }

    public Bounds getBounds(@NonNull List<Keyboard.Key> keys) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = 0, maxY = 0;
        for (Keyboard.Key key : keys) {
            if (key.x < minX) minX = key.x;
            if (key.y < minY) minY = key.y;

            if (key.x + key.width > maxX) maxX = key.x;
            if (key.y + key.height > maxY) maxY = key.y;
        }
        return new Bounds(minX, minY, maxX, maxY);
    }

    // @TODO: autoadjustment of key width by number of keys in row
    public void adjustKeys() {
        Bounds bounds = this.getBounds(standardKeyboard.getKeys());
        Map<Integer,List<Keyboard.Key>> layoutRows = this.getKeyboardRows(standardKeyboard);
        for (Map.Entry<Integer, List<Keyboard.Key>> entry : layoutRows.entrySet()) {
            if (entry.getValue().size() > 8) {
                for(Keyboard.Key key : entry.getValue()) {
                    key.width = bounds.dX / entry.getValue().size();
                }
            }
        }
        redraw();
    }

    public Map<Integer,List<Keyboard.Key>> getKeyboardRows(CustomKeyboard keyboard) {
        Map<Integer,List<Keyboard.Key>> layoutRows = new TreeMap<>();
        for (Keyboard.Key key : keyboard.getKeys()) {
            if (!layoutRows.containsKey(key.y)) {
                // layoutRows.put(key.y, new ArrayList<>());
            }
            layoutRows.get(key.y).add(key);
        }
        return layoutRows;
    }

    public void clearAll() {
        // sendKey(KeyEvent.KEYCODE_CLEAR);
        ic = this.getCurrentInputConnection();
        ic.deleteSurroundingText(MAX, MAX);
    }

    public String getText(@NonNull InputConnection ic) {
        CharSequence text = ic.getSelectedText(0);
        if (text == null) return "";
        return (String) text;
    }

    public String getAllText(@NonNull InputConnection ic) {
        return ic.getTextBeforeCursor(MAX, 0).toString() + ic.getTextAfterCursor(MAX, 0).toString();
    }

    public void sendCustomKey(String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext()); // this?
        ic = this.getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        int cursorLocation = getSelectionStart();
        String ins = sharedPreferences.getString(key, "");
        ic.beginBatchEdit();
        this.commitText(ins, cursorLocation + (ins != null ? ins.length() : 0));
        ic.endBatchEdit();
    }

    public String getPrevWord() {
        try {
            ic = this.getCurrentInputConnection();
            String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
            String lastWord = words[words.length - 1];
            return lastWord;
        }
        catch (Exception e) {
            this.toastIt(e.toString());
        }
        return "";
    }
    public void selectPrevWord() {
        try {
            ic = this.getCurrentInputConnection();
            String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
            String lastWord = words[words.length - 1];
            int position = getSelectionStart() - lastWord.length() - 1;
            if (position < 0) position = 0;
            if (Variables.isSelecting()) ic.setSelection(position, Variables.cursorStart);
            else ic.setSelection(position, position);
        }
        catch (Exception e) {
            this.toastIt(e.toString());
        }
    }

    public String getNextWord() {
        try {
            ic = this.getCurrentInputConnection();
            String[] words = ic.getTextAfterCursor(MAX, 0).toString().split(" ");
            String nextWord = words[0];
            return nextWord;
        }
        catch (Exception e) {
            this.toastIt(e.toString());
        }
        return "";
    }

    public void selectNextWord() {
        try {
            ic = this.getCurrentInputConnection();
            String[] words = ic.getTextAfterCursor(MAX, 0).toString().split(" ");
            String nextWord = words[0];
            if (words.length > 1) nextWord = words[1];
            int position = this.getSelectionStart() + nextWord.length() + 1;
            if (Variables.isSelecting()) ic.setSelection(Variables.cursorStart, position);
            else ic.setSelection(position, position);
        }
        catch (Exception e) {
            this.toastIt(e.toString());
        }
    }

    public void goToStart() {
        ic = this.getCurrentInputConnection();
        ic.setSelection(0, 0);
    }

    public void goToEnd() {
        ic = this.getCurrentInputConnection();
        ic.setSelection(
            (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length(),
            (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length()
        );
    }

    public void selectNone() {
        ic = this.getCurrentInputConnection();
        try {
            int end = this.getSelectionEnd();
            ic.setSelection(end, end);
            Variables.setSelectingOff();
        }
        catch (Exception e) {
            this.toastIt(e.toString());
        }
    }

    public void selectLine() {
        this.sendKey(KeyEvent.KEYCODE_MOVE_HOME);
        Variables.setSelectingOn(getSelectionStart());
        this.navigate(KeyEvent.KEYCODE_MOVE_END);
        Variables.setSelectingOff();
    }

    public void selectAll() {
        ic = this.getCurrentInputConnection();
        ic.setSelection(0, (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length());
    }

    public String getPrevLine() {
        ic = this.getCurrentInputConnection();
        CharSequence textBeforeCursor = ic.getTextBeforeCursor(MAX, 0);
        if (textBeforeCursor == null) return "";
        if (textBeforeCursor.length() < 1) return "";
        String[] lines = textBeforeCursor.toString().split("\n");
        if (lines.length < 2) return textBeforeCursor.toString();
        return lines[lines.length - 1];
    }

    public String getNextLine() {
        ic = this.getCurrentInputConnection();
        CharSequence textAfterCursor = ic.getTextAfterCursor(MAX, 0);
        if (textAfterCursor == null) return "";
        if (textAfterCursor.length() < 1) return "";
        String[] lines = textAfterCursor.toString().split("\n");
        if (lines.length < 2) return textAfterCursor.toString();
        return lines[0];
    }

    public int getCurrentLine() {
        return Util.getLines(ic.getTextBeforeCursor(MAX, 0).toString()).length;
    }

    public int getLineCount() {
        return Util.getLines(this.getAllText(ic)).length;
    }

    public int[] getCursorLocation() {
        return new int[]{this.cursorLocationOnLine(), this.getCurrentLine()};
    }

    public int cursorLocationOnLine() {
        return this.getPrevLine().length();
    }

    public int getCursorPosition() {
        ic = this.getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.startOffset + extracted.selectionStart;
    }

    public int getStartOffset() {
        ic = this.getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.startOffset;
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();

        this.updateCandidates();

        this.setCandidatesViewShown(false);

        Variables.setSelectingOff();


        currentKeyboard = standardKeyboard;
        if (kv != null) {
            kv.closing();
        }
        this.setCandidatesViewShown(false);
    }

    @Override
    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);

        String prevLine = getPrevLine();
        int prevChar = 0;
        try {
            if (prevLine != null && prevLine.length() > 0) {
                ArrayList<Integer> prevChars = Util.asUnicodeArray(prevLine);
                prevChar = prevChars.get(prevChars.size() - 1);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        String nextLine = this.getNextLine();
        int nextChar = 0;
        try {
            if (nextLine != null && nextLine.length() > 0) {
                ArrayList<Integer> nextChars = Util.asUnicodeArray(nextLine);
                nextChar = nextChars.get(0);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        try {
            boolean isBold = Font.isBold(prevChar) || Font.isBold(nextChar);
            boolean isItalic = Font.isItalic(prevChar) || Font.isItalic(nextChar);
            boolean isEmphasized = Font.isEmphasized(prevChar) || Font.isEmphasized(nextChar);

            if (isBold) {
                Variables.setAllOff();
                Variables.setBoldOn();
            }
            else if (isItalic) {
                Variables.setAllOff();
                Variables.setItalicOn();
            }
            else if (isEmphasized) {
                Variables.setAllOff();
                Variables.setEmphasizedOn();
            }
            else {
                Variables.setAllOff();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        this.redraw();
        if ((this.getSelectionStart() == 0) // || ic.getTextBeforeCursor(1, 0) == "\n"
            && PreferenceManager.getDefaultSharedPreferences(this.getBaseContext()).getBoolean("caps", false)) {
            if (Variables.isShift()) {
                Variables.setShiftOff();
                firstCaps = false;
            }
            else {
                firstCaps = !firstCaps;
            }
            this.setCapsOn(firstCaps);
            this.redraw();
        }

        if ((newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
            // this.updateCandidates();
            InputConnection ic = this.getCurrentInputConnection();
            if (ic != null) {
                ic.finishComposingText();
            }
        }
    }

    public void redraw() {
        kv.invalidateAllKeys();
        kv.draw(new Canvas());
    }

    public void commitText(String text) {
        this.commitText(text, text.length()-1);
    }

    public void commitText(String text, int offset) {
        ic.beginBatchEdit();
        ic.commitText(text, offset);
        ic.endBatchEdit();
    }

    public void performReplace(String newText) {
        ic = this.getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0) {
            int a = this.getSelectionStart();
            int b = this.getSelectionStart() + newText.length();
            this.commitText(newText);
            ic.setSelection(a, b);
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
        if (this.getPrevWord().length() > 0) {
            this.commitText(getPrevWord());
            // this.updateCandidates();
        }
    }

    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null && kv != null && standardKeyboard == kv.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = this.getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != InputType.TYPE_NULL) {
                caps = this.getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            kv.setShifted(mCapsLock || caps != 0);
        }
    }

    public void onText(CharSequence text) {
        InputConnection ic = this.getCurrentInputConnection();
        if (ic == null) {
            return;
        }
        ic.beginBatchEdit();
        if (this.getPrevWord().length() > 0) {
            this.commitTyped(ic);
        }
        ic.endBatchEdit();
        this.updateShiftKeyState(this.getCurrentInputEditorInfo());
    }

    @Override
    public void swipeLeft() {
        InputConnection ic = this.getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        String prevWord = this.getPrevWord();
        ic.setSelection(this.getSelectionStart()-prevWord.length(), this.getSelectionStart()-prevWord.length());
    }

    @Override
    public void swipeRight() {
        InputConnection ic = this.getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        String nextWord = this.getNextWord();
        ic.setSelection(this.getSelectionEnd()+nextWord.length(), this.getSelectionEnd()+nextWord.length());
    }

    @Override
    public void swipeDown() {
        this.setCandidatesViewShown(false);
    }

    @Override
    public void swipeUp() {
        // setCandidatesViewShown(true);
        PopupWindow popupWindow = new PopupWindow();
        popupWindow = new PopupWindow(getBaseContext());
        popupWindow.setClippingEnabled(false);

    }


    public void showVoiceInput() {
        InputMethodManager inputMethodManager = (InputMethodManager)this.getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.setInputMethod(this.getToken(), "com.google.android.googlequicksearchbox/com.google.android.voicesearch.ime.VoiceInputMethodService");
        }
    }

    public void startIntent(Intent intent) {
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            this.startActivity(intent);
        }
    }

    public void showSettings() {
        Intent intent = new Intent(this.getApplicationContext(), Preference.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startIntent(intent);
    }

    private void updateCandidates() {

        String prevLine = this.getPrevLine();
        String prevWord = this.getPrevWord();
        String prevChar = "";
        if (prevWord.length() > 0) {
            prevChar = String.valueOf(prevLine.charAt(prevLine.length()-1));
            prevChar = prevChar.substring(0, 1);
            if (!Character.isLetter(prevChar.charAt(0))) {
                this.mCandidateView.clearSuggestions();
                return;
            }
        }

        boolean isTitleCase = Util.isTitleCase(prevWord);
        boolean isUpperCase = Util.isUpperCase(prevWord) && prevWord.length() > 1;

        prevWord = prevWord.toLowerCase();

        boolean inTrie = SpellChecker.inTrie(prevWord);
        boolean isPrefix = SpellChecker.isPrefix(prevWord);

        System.out.println("'"+prevLine+"'");
        System.out.println("'"+prevWord+"'");
        System.out.println("'"+prevChar+"'");

        System.out.println("isTitleCase: "+isTitleCase);
        System.out.println("isUpperCase: "+isUpperCase);
        System.out.println("inTrie: "+inTrie);
        System.out.println("isPrefix: "+isPrefix);

        ArrayList<String> results = new ArrayList<String>();

        if (this.mCandidateView != null) {
            if (isPrefix) {
                this.setCandidatesViewShown(true);
                suggestions = SpellChecker.getCompletions(prevWord);
                if (isUpperCase) {
                    for(int i = 0; i < suggestions.size(); i++) {
                        suggestions.set(i, Util.toUpperCase(suggestions.get(i)));
                    }
                }
                else if (isTitleCase) {
                    for(int i = 0; i < suggestions.size(); i++) {
                        suggestions.set(i, Util.toTitleCase(suggestions.get(i)));
                    }
                }
                this.mCandidateView.setSuggestions(suggestions, true, true);
            }
        }
    }

    public void addToDictionary(String word) {
        UserDictionary.Words.addWord(this, word, 1, null, Locale.getDefault());
    }

    public void pickSuggestionManually(int index) {
        String prevWord = this.getPrevWord();
        this.suggestions = mCandidateView.getSuggestions();
        if (this.suggestions != null && index >= 0 && index < this.suggestions.size()) {
            this.getCurrentInputConnection().deleteSurroundingText(prevWord.length(), 0);
            this.commitText(suggestions.get(index));
            this.commitText(" ");
            if (this.mCandidateView != null) {
                this.mCandidateView.clear();
            }
        }
        this.updateShiftKeyState(this.getCurrentInputEditorInfo());
        this.setCandidatesViewShown(false);
    }

    public void pickDefaultCandidate() {
        this.pickSuggestionManually(0);
    }

    private void handleDelete() {
        if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) { this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON)); }
            if (Variables.isAlt()) { this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0, KeyEvent.META_ALT_ON)); }
            if (Variables.isCtrl()) { this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0, KeyEvent.META_CTRL_ON)); }
        }
        else {
            this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0));
            this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   KeyEvent.KEYCODE_FORWARD_DEL, 0));
        }
        this.updateCandidates();
    }

    private void handleBackspace() {
        final int length = this.getPrevWord().length();

        if (sharedPreferences.getBoolean("pairs", true)
            && ic.getTextBeforeCursor(1, 0) != null
            && String.valueOf(ic.getTextBeforeCursor(1, 0)).length() >= 1
            && Util.contains(")}\"]", String.valueOf(ic.getTextAfterCursor(1, 0)))
            && String.valueOf(ic.getTextBeforeCursor(1, 0)).equals(String.valueOf(ic.getTextAfterCursor(1, 0)))) {
            ic.deleteSurroundingText(0, 1);
        }

        if (!this.isSelecting()
            && ic.getTextBeforeCursor(4, 0) != null
            && String.valueOf(ic.getTextBeforeCursor(4, 0)).length() >= 4
            && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals("    ")
            && sharedPreferences.getBoolean("spaces", false)) {
            ic.deleteSurroundingText((4 - (this.getPrevLine().length() % 4)), 0);
        }

        if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            if (Variables.isAlt())  this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_ALT_ON));
            if (Variables.isCtrl()) this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_CTRL_ON));
        }

        else if (length > 0) {
            ic.deleteSurroundingText(1, 0);
        }
        else if (length == 0) {
            this.getCurrentInputConnection().commitText("", 0);
        }
        else {
            this.sendKey(KeyEvent.KEYCODE_DEL);
        }
        this.updateShiftKeyState(this.getCurrentInputEditorInfo());

        this.updateCandidates();
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (this.isInputViewShown()) {
            if (kv.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
        }
        if (sharedPreferences.getBoolean("pairs", true)) {
            if (Util.contains("({\"[", primaryCode)) {
                String code = Util.largeIntToChar(primaryCode);
                if (code.equals("(")) commitText("()");
                if (code.equals("[")) commitText("[]");
                if (code.equals("{")) commitText("{}");
                if (code.equals("\"")) commitText("\"\"");
                this.sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
                return;
            }
        }

        if (Variables.isBold()) { primaryCode = Font.getBold(primaryCode);}
        if (Variables.isItalic()) { primaryCode = Font.getItalic(primaryCode);}
        if (Variables.isEmphasized()) { primaryCode = Font.getEmphasized(primaryCode);}
        // if (Variables.isUnderlined()) { primaryCode = Font.getUnderlined(primaryCode);}
        // if (Variables.isUnderscored()) { primaryCode = Font.getUnderscored(primaryCode);}
        // if (Variables.isStrikethrough()) { primaryCode = Font.getStrikethrough(primaryCode);}
        if (Variables.isBoldSerif()) { primaryCode = Font.toBoldSerif(primaryCode, kv.isShifted());}
        if (Variables.isItalicSerif()) { primaryCode = Font.toItalicSerif(primaryCode, kv.isShifted());}
        if (Variables.isBoldItalicSerif()) { primaryCode = Font.toBoldItalicSerif(primaryCode, kv.isShifted());}
        if (Variables.isScript()) { primaryCode = Font.toScript(primaryCode, kv.isShifted());}
        if (Variables.isScriptBold()) { primaryCode = Font.toScriptBold(primaryCode, kv.isShifted());}
        if (Variables.isFraktur()) { primaryCode = Font.toFraktur(primaryCode, kv.isShifted());}
        if (Variables.isFrakturBold()) { primaryCode = Font.toFrakturBold(primaryCode, kv.isShifted());}
        if (Variables.isSans()) { primaryCode = Font.toSans(primaryCode, kv.isShifted());}
        if (Variables.isMonospace()) { primaryCode = Font.toMonospace(primaryCode, kv.isShifted());}
        if (Variables.isDoublestruck()) { primaryCode = Font.toDoublestruck(primaryCode, kv.isShifted());}
        if (Variables.isEnsquare()) { primaryCode = Font.ensquare(primaryCode);}
        if (Variables.isCircularStampLetters()) { primaryCode = Font.toCircularStampLetters(primaryCode);}
        if (Variables.isRectangularStampLetters()) { primaryCode = Font.toRectangularStampLetters(primaryCode);}
        if (Variables.isSmallCaps()) { primaryCode = Font.toSmallCaps(primaryCode);}
        if (Variables.isParentheses()) { primaryCode = Font.toParentheses(primaryCode);}
        if (Variables.isEncircle()) { primaryCode = Font.encircle(primaryCode);}
        if (Variables.isReflected()) { primaryCode = Font.toReflected(primaryCode);}
        if (Variables.isCaps()) { primaryCode = Font.toCaps(primaryCode);}

        char code = (char)primaryCode; // Util.largeIntToChar(primaryCode)
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("caps", false) &&
            Character.isLetter(code) && firstCaps || Character.isLetter(code) && Variables.isShift()) {
            code = Character.toUpperCase(code);
        }
        this.commitText(String.valueOf(code), 1);
        this.firstCaps = false;
        this.setCapsOn(false);

        this.updateShiftKeyState(this.getCurrentInputEditorInfo());
        if (Util.isLetter(primaryCode)) this.updateCandidates();
    }

    public void hide() {
        this.handleClose();
    }

    private void handleClose() {
        this.commitTyped(this.getCurrentInputConnection());
        this.setCandidatesViewShown(false);
        this.requestHideSelf(0);
        this.setCandidatesViewShown(false);
        kv.closing();
    }


    static void print(@NonNull Object... a) {
        for (Object i : a) System.out.print(i + " ");
        System.out.println();
    }

    public int getGravity() {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        String xAxis = this.sharedPreferences.getString("x_axis", "BOTTOM").toUpperCase();
        String yAxis = this.sharedPreferences.getString("y_axis", "CENTER_HORIZONTAL").toUpperCase();
        boolean fillHorizontal = this.sharedPreferences.getBoolean("fill_horizontal", false);
        boolean fillVertical = this.sharedPreferences.getBoolean("fill_vertical", false);

        int result = 0;
        if (xAxis.equals("CENTER_HORIZONTAL")) result |= Gravity.CENTER_HORIZONTAL;
        if (xAxis.equals("START")) result |= Gravity.START;
        if (xAxis.equals("END")) result |= Gravity.END;
        if (yAxis.equals("CENTER_VERTICAL")) result |= Gravity.CENTER_VERTICAL;
        if (yAxis.equals("TOP")) result |= Gravity.TOP;
        if (yAxis.equals("BOTTOM")) result |= Gravity.BOTTOM;

        if (fillHorizontal) result |= Gravity.FILL_HORIZONTAL;
        if (fillVertical) result |= Gravity.FILL_VERTICAL;

        return result;
    }

    public void performContextMenuAction(int id) {
        InputConnection ic = this.getCurrentInputConnection();
        ic.performContextMenuAction(id);
    }

    public void showActivity(String id) {
        Intent intent = new Intent(id).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startIntent(intent);
    }

    public void showClipboard() {
        try {
            Intent intent = new Intent("com.samsung.android.ClipboardUIService");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startIntent(intent);
        }
        catch (Exception e) {
            this.toastIt(e.toString());
        }
    }

    private IBinder getToken() {
        final Dialog dialog = this.getWindow();
        if (dialog == null) {
            return null;
        }
        final Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }
        return window.getAttributes().token;
    }

    private void handleLanguageSwitch() {
        this.mInputMethodManager.switchToNextInputMethod(this.getToken(), false /* onlyCurrentIme */);
    }

    private String getWordSeparators() {
        return this.mWordSeparators;
    }

    public boolean isWordSeparator(String s) {
        return s.contains(". ") || s.contains("? ") || s.contains("! ");
    }


    long time = 0;

    public void onPress(int primaryCode) {
        ic = this.getCurrentInputConnection();
        time = System.nanoTime() - time;

        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("vib", false)) {
            Vibrator v = (Vibrator)this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
            if (v != null) {
                v.vibrate(40);
            }
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        ic = this.getCurrentInputConnection();
        time = (System.nanoTime() - time) / 1000000;

        if (time > 300) {
            switch (primaryCode) {
                case -12: this.selectAll(); break;
            }
        }
    }

    private void sendKeyEvent(int keyCode, int metaState) {
        this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, this.getHardKeyCode(keyCode), 0, metaState));
        this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   this.getHardKeyCode(keyCode), 0, metaState));
    }

    private void processKeyCombo(int keycode) {
        if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) this.sendKeyEvent(this.getHardKeyCode(keycode), KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON);
            else {
                if (Variables.isCtrl()) this.sendKeyEvent(this.getHardKeyCode(keycode), KeyEvent.META_CTRL_ON);
                if (Variables.isAlt())  this.sendKeyEvent(this.getHardKeyCode(keycode), KeyEvent.META_ALT_ON);
            }
        }
    }

    private int getHardKeyCode(int keycode) {
        PopupWindow p = new PopupWindow();
        char code = (char)keycode;
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
            default:  return keycode;
        }
    }


    public void setTheme() {
        System.out.println("Theme: "+PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("theme", "1"));
        switch (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("theme", "1")) {
            case "1": mDefaultFilter = Themes.sPositiveColorArray; break;
            case "2": mDefaultFilter = Themes.sNegativeColorArray; break;
            case "3": mDefaultFilter = Themes.sBlueWhiteColorArray; break;
            case "4": mDefaultFilter = Themes.sBlueBlackColorArray; break;
            case "5": mDefaultFilter = Themes.sGreenWhiteColorArray; break;
            case "6": mDefaultFilter = Themes.sGreenBlackColorArray; break;
            case "7": mDefaultFilter = Themes.sRedWhiteColorArray; break;
            case "8": mDefaultFilter = Themes.sRedBlackColorArray; break;
            case "9": mDefaultFilter = Themes.sCyanWhiteColorArray; break;
            case "10": mDefaultFilter = Themes.sCyanBlackColorArray; break;
            case "11": mDefaultFilter = Themes.sMagentaWhiteColorArray; break;
            case "12": mDefaultFilter = Themes.sMagentaBlackColorArray; break;
            case "13": mDefaultFilter = Themes.sYellowWhiteColorArray; break;
            case "14": mDefaultFilter = Themes.sYellowBlackColorArray; break;
            case "15": mDefaultFilter = Themes.sPurpleWhiteColorArray; break;
            case "16": mDefaultFilter = Themes.sPurpleBlackColorArray; break;
            case "17": mDefaultFilter = Themes.sPinkWhiteColorArray; break;
            case "18": mDefaultFilter = Themes.sPinkBlackColorArray; break;
            case "19": mDefaultFilter = Themes.sOrangeWhiteColorArray; break;
            case "20": mDefaultFilter = Themes.sOrangeBlackColorArray; break;
            case "21": mDefaultFilter = Themes.sMaterialLiteColorArray; break;
            case "22": mDefaultFilter = Themes.sMaterialDarkColorArray; break;
            default: mDefaultFilter = Themes.sPositiveColorArray; break;
        }
    }

    private void handleAction() {
        EditorInfo curEditor = this.getCurrentInputEditorInfo();
        switch (curEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
            case EditorInfo.IME_ACTION_DONE: this.getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_DONE); break;
            case EditorInfo.IME_ACTION_GO: this.getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_GO); break;
            case EditorInfo.IME_ACTION_NEXT: this.getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_NEXT); break;
            case EditorInfo.IME_ACTION_SEARCH: this.getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_SEARCH); break;
            case EditorInfo.IME_ACTION_SEND: this.getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_SEND); break;
            default: break;
        }
    }

    private void setInputType() {

        EditorInfo attribute = this.getCurrentInputEditorInfo();
        int webInputType = attribute.inputType & InputType.TYPE_MASK_VARIATION;

        switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_DATETIME:
            case InputType.TYPE_CLASS_PHONE:
                this.setKeyboard(R.layout.numeric);
                break;
            case InputType.TYPE_CLASS_TEXT:
                if (webInputType == InputType.TYPE_TEXT_VARIATION_URI
                 || webInputType == InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
                 || webInputType == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                 || webInputType == InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS) {
                    this.setKeyboard(R.layout.domain);
                }
                else {
                    this.setKeyboard(R.layout.primary);
                }
                break;
            default:
                this.setKeyboard(R.layout.primary);
                break;
        }
        if (kv != null) {
            kv.setKeyboard(currentKeyboard);
        }
    }

    private void checkToggleCapsLock() {
        long now = System.currentTimeMillis();
        if (this.mLastShiftTime + 300 > now) {
            this.mCapsLock = !this.mCapsLock;
            this.mLastShiftTime = 0;
        }
        else {
            this.mLastShiftTime = now;
        }
    }

    private void setCapsOn(boolean on) {
        if (Variables.isShift()) kv.getKeyboard().setShifted(true);
        else kv.getKeyboard().setShifted(on);
        kv.invalidateAllKeys();
    }

    private void capsOnFirst() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("caps", false)) {
            if (this.getCursorCapsMode(this.getCurrentInputConnection(), this.getCurrentInputEditorInfo()) != 0) {
                this.firstCaps = true;
                this.setCapsOn(true);
            }
        }
        else {
            this.firstCaps = false;
            this.setCapsOn(false);
        }
    }

    private int getCursorCapsMode(InputConnection ic, EditorInfo attr) {
        int caps = 0;
        EditorInfo ei = this.getCurrentInputEditorInfo();
        if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
            caps = ic.getCursorCapsMode(attr.inputType);
        }
        return caps;
    }

    public void toastIt(int num) {
        this.toastIt(String.valueOf(num));
    }
    public void toastIt(Exception e) {
        this.toastIt(e.toString());
    }
    public void toastIt(String ...args) {
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
        if (this.toast != null) this.toast.cancel();
        this.toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        this.toast.show();
    }

    private void sendRawKey(int keyCode) {
        if (keyCode == '\n') {
            this.sendKey(KeyEvent.KEYCODE_ENTER);
        }
        else {
            if (keyCode >= '0' && keyCode <= '9') {
                this.sendKey(keyCode - '0' + KeyEvent.KEYCODE_0);
            }
            else {
                this.getCurrentInputConnection().commitText(String.valueOf((char)keyCode), 1); // Util.largeIntToChar(primaryCode)
            }
        }
    }

    public Keyboard.Key getKey(int primaryCode) {
        if (this.currentKeyboard == null) return null;
        for (Keyboard.Key key : this.currentKeyboard.getKeys()) {
            if (key.codes[0] == primaryCode) return key;
        }
        return null;
    }

    public void sendKey(int primaryCode) {
        ic = this.getCurrentInputConnection();
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, primaryCode));
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   primaryCode));
    }

    public void sendKey(int primaryCode, int times) {
        ic = this.getCurrentInputConnection();
        while (times --> 0) {
            this.sendKey(primaryCode);
        }
    }

    public void sendKeys(@NonNull int[] keys) {
        ic = this.getCurrentInputConnection();
        for (int key : keys) {
            this.sendKey(key);
        }
    }

    public void navigate(int primaryCode) {
        ic = this.getCurrentInputConnection();
        if      (!this.isSelecting() && primaryCode == KeyEvent.KEYCODE_DPAD_LEFT && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals("    ")) {
            this.sendKey(primaryCode, 4);
        }
        else if (!this.isSelecting() && primaryCode == KeyEvent.KEYCODE_DPAD_RIGHT && String.valueOf(ic.getTextAfterCursor(4, 0)).equals("    ")) {
            this.sendKey(primaryCode, 4);
        }
        else {
            if (Variables.isSelecting()) ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT));
            this.sendKey(primaryCode);
            if (Variables.isSelecting()) ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SHIFT_LEFT));
        }
    }

    public void replaceText(@NonNull String src, String trg) {
        ic.deleteSurroundingText(src.length(), 0);
        this.commitText(trg);
    }

    public Boolean isSelecting() {
        return this.getSelectionStart() != this.getSelectionEnd();
    }

    public int getSelectionStart() {
        ic = this.getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.selectionStart;
    }

    public int getSelectionEnd() {
        ic = this.getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.selectionEnd;
    }

    public int getSelectionLength() {
        ic = this.getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.selectionEnd - extracted.selectionStart;
    }



    public void setKeyboard(int id) {
        currentKeyboard = new CustomKeyboard(this.getBaseContext(), id);
        currentKeyboard.setRowNumber(this.getStandardRowNumber());
        kv.setKeyboard(currentKeyboard);
        // kv.getCustomKeyboard().changeKeyHeight(this.getHeightKeyModifier());
    }

    static String hexBuffer = "";

    static final int[] hexPasses = new int[] {
        // 7,    9,   10,   32,  33,
        // -1,   -4,   -5,   -7,
        // -8,   -9,  -10,  -11,
        -101, // -102, -103,  -25,
        // -26,  -76,  -93, -107,
        // -108, -109, -110, -111,
        // -174
    };
    static final int[] hexCaptures = new int[] {
        48, 49, 50,  51,  52,  53, 54, 55,
        56, 57, 65,  66,  67,  68, 69, 70,
        97, 98, 99, 100, 101, 102
    };

    private void handleSpace() {
        this.commitText(" ");
        this.mCandidateView.clear();
        // if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("spaces", true)) {
        //     int spaceCount = (4 - (this.getPrevLine().length() % 4));
        //     if (spaceCount > 0 && spaceCount < 4 && this.getPrevLine().length() < 4) {
        //         spaceCount = 4;
        //     }
        //     commitText("    ".substring(0, spaceCount));
        //     if (isSelecting()) {
        //         ic.setSelection(this.getSelectionStart(), this.getSelectionEnd() + "    ".length());
        //     }
        // }
        // else {
        //     commitText("\t");
        //     if (this.isSelecting()) {
        //         ic.setSelection(this.getSelectionStart(), this.getSelectionEnd() + "\t".length());
        //     }
        // }
    }

    private void handleUnicode(int primaryCode) {
        if (primaryCode == -201) this.performReplace(Util.convertFromUnicodeToNumber(this.getText(ic)));
        if (primaryCode == -202) this.performReplace(Util.convertFromNumberToUnicode(this.getText(ic)));
        if (Util.contains(hexCaptures, primaryCode)) {
            if (this.hexBuffer.length() > 3) this.hexBuffer = "";
            this.hexBuffer += (char)primaryCode; // Util.largeIntToChar(primaryCode)
        }
        if (primaryCode == -203) this.commitText(StringUtils.leftPad(hexBuffer, 4, "0"));
        if (primaryCode == -204) {
            // Util.largeIntToChar(primaryCode)
            this.commitText(String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(this.hexBuffer, 4, "0"))));
        }
        if (primaryCode == -205) {
            if (this.hexBuffer.length() > 0) this.hexBuffer = this.hexBuffer.substring(0, hexBuffer.length() - 1);
            else this.hexBuffer = "0000";
        }
        if (primaryCode == -206) this.hexBuffer = "0000";
        this.getKey(-203).label = this.hexBuffer.equals("0000") ? "" : StringUtils.leftPad(hexBuffer, 4, "0");
        // Util.largeIntToChar(primaryCode)
        this.getKey(-204).label = String.valueOf((char)(int)Integer.decode("0x" + StringUtils.leftPad(this.hexBuffer, 4, "0")));
    }

    public void handleShift() {
        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0 &&
            PreferenceManager.getDefaultSharedPreferences(this).getBoolean("shift", false)) {
            String text = ic.getSelectedText(0).toString();
            int a = this.getSelectionStart();
            int b = this.getSelectionEnd();
            if (Util.containsUpperCase(text)) {
                text = text.toLowerCase();
            }
            else {
                text = text.toUpperCase();
            }
            this.commitText(text, b);
            ic.setSelection(a, b);
            this.redraw();
        }
        else {
            if (this.shift_pressed + 200 > System.currentTimeMillis()) {
                Variables.setShiftOn();
                this.setCapsOn(true);
            }
            else {
                if (Variables.isShift()) {
                    Variables.setShiftOff();
                    this.firstCaps = false;
                }
                else {
                    this.firstCaps = !this.firstCaps;
                }
                this.setCapsOn(this.firstCaps);
                this.shift_pressed = System.currentTimeMillis();
            }
            this.redraw();
        }
    }

    public void handleEnter() {
        // handleAction();

        EditorInfo currentEditor = this.getCurrentInputEditorInfo();
        switch (currentEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
            case EditorInfo.IME_ACTION_GO:
                this.getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_GO);
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                this.getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_SEARCH);
                break;
            case EditorInfo.IME_ACTION_DONE:
            case EditorInfo.IME_ACTION_NEXT:
            case EditorInfo.IME_ACTION_SEND:
            default:
                this.sendKey(66);
                break;
        }
    }

    public void handleCut() {
        if (!this.isSelecting()) this.selectLine();
        this.sendKey(KeyEvent.KEYCODE_CUT);
    }
    public void handleCopy() {
        if (!this.isSelecting()) this.selectLine();
        this.sendKey(KeyEvent.KEYCODE_COPY);
    }
    public void handlePaste() {
        this.sendKey(KeyEvent.KEYCODE_PASTE);
    }

    public void handleAlt() {
        if (Variables.isAlt()) Variables.setAltOff();
        else Variables.setAltOn();
        this.redraw();
    }
    public void handleCtrl() {
        if (Variables.isCtrl()) Variables.setCtrlOff();
        else Variables.setCtrlOn();
        this.redraw();
    }
    public void handleEsc() {
        this.sendKey(KeyEvent.KEYCODE_ESCAPE);
    }

    public void handleTab() {
        if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            if (Variables.isAlt())  this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_ALT_ON));
            if (Variables.isCtrl()) this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_CTRL_ON));
        }
        else {
            this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0));
            this.getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   KeyEvent.KEYCODE_TAB, 0));
        }
    }

    private void playClick(int primaryCode) {
        AudioManager am = (AudioManager)this.getSystemService(AUDIO_SERVICE);
        switch (primaryCode) {
            case 32: am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);break;
            case Keyboard.KEYCODE_DONE:
            case 10: am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN); break;
            case Keyboard.KEYCODE_DELETE: am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE); break;
            default:
                // am.playSoundEffect(AudioManager.FX_KEY_CLICK);
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                break;
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = this.getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int ere, aft;
        System.out.println(primaryCode);

        if (sharedPreferences.getBoolean("sound", false)) this.playClick(primaryCode);

        if (currentKeyboard.title != null && currentKeyboard.title.equals("Unicode") && !Util.contains(hexPasses, primaryCode)) {
            this.handleUnicode(primaryCode);
            this.redraw();
        }
        switch (primaryCode) {
            // case -501: System.out.println(primaryCode); break;
            // case -502: System.out.println(primaryCode); break;
            // case -503: System.out.println(primaryCode); break;
            // case -504: System.out.println(primaryCode); break;
            // case -505: System.out.println(primaryCode); break;
            // case -506: System.out.println(primaryCode); break;
            // case -507: System.out.println(primaryCode); break;
            // case -508: System.out.println(primaryCode); break;
            case 142: this.sendKey(KeyEvent.KEYCODE_F12); break;
            case 141: this.sendKey(KeyEvent.KEYCODE_F11); break;
            case 140: this.sendKey(KeyEvent.KEYCODE_F10); break;
            case 139: this.sendKey(KeyEvent.KEYCODE_F9); break;
            case 138: this.sendKey(KeyEvent.KEYCODE_F8); break;
            case 137: this.sendKey(KeyEvent.KEYCODE_F7); break;
            case 136: this.sendKey(KeyEvent.KEYCODE_F6); break;
            case 135: this.sendKey(KeyEvent.KEYCODE_F5); break;
            case 134: this.sendKey(KeyEvent.KEYCODE_F4); break;
            case 133: this.sendKey(KeyEvent.KEYCODE_F3); break;
            case 132: this.sendKey(KeyEvent.KEYCODE_F2); break;
            case 131: this.sendKey(KeyEvent.KEYCODE_F1); break;
            case -1: this.handleShift(); break;
            case 32: this.handleSpace(); break;
            case 10: this.handleEnter(); break;
            case -2:


                break;
            case -112: this.handleEsc(); break;
            case -113: this.handleCtrl(); break;
            case -114: this.handleAlt(); break;
            case -5: this.handleBackspace(); break;
            case -7: this.handleDelete(); break;
            case -122: this.handleTab(); break;
            case -8: this.handleCut(); break;
            case -9: this.handleCopy(); break;
            case -10: this.handlePaste(); break;
            case -11: Variables.toggleSelecting(this.getSelectionStart()); break;
            case -12: this.selectLine(); break;
            case -13: this.navigate(KeyEvent.KEYCODE_DPAD_UP); break;
            case -14: this.navigate(KeyEvent.KEYCODE_DPAD_DOWN); break;
            case -15: this.navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -16: this.navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -17: this.navigate(KeyEvent.KEYCODE_DPAD_CENTER); break;
            case -18: this.navigate(KeyEvent.KEYCODE_PAGE_UP); break;
            case -19: this.navigate(KeyEvent.KEYCODE_PAGE_DOWN); break;
            case -20: this.navigate(KeyEvent.KEYCODE_MOVE_HOME); break;
            case -21: this.navigate(KeyEvent.KEYCODE_MOVE_END); break;
            case -22: this.showSettings(); break;
            case -23: this.showVoiceInput(); break;
            case -24: this.handleClose(); break;
            case -25:
                InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                if (imeManager != null) imeManager.showInputMethodPicker();
            break;
            case -26: this.sendKey(KeyEvent.KEYCODE_SETTINGS); break;
            // case -27: this.showEmoticons(); break;
            case -28: this.clearAll(); break;
            case -29: this.goToStart(); break;
            case -30: this.goToEnd(); break;
            case -31: this.selectNone(); break;
            case -32: this.selectPrevWord(); break;
            case -33: this.selectNextWord(); break;
            case -34: this.commitText(this.getNextLine() + "\n" + this.getPrevLine()); break;
            case -35: this.commitText(Util.getDateString(sharedPreferences.getString("date_format", "yyyy-MM-dd"))); break;
            case -36: this.commitText(Util.getTimeString(sharedPreferences.getString("time_format", "HH:mm:ss"))); break;
            case -37: this.commitText(Util.nowAsLong() + " " + Util.nowAsInt()); break;
            case -38: this.performReplace(Util.toUpperCase(this.getText(ic))); break;
            case -39: this.performReplace(Util.toTitleCase(this.getText(ic))); break;
            case -40: this.performReplace(Util.toLowerCase(this.getText(ic))); break;
            case -41: this.performReplace(Util.toAlterCase(this.getText(ic))); break;
            case -42: this.performReplace(Util.camelToSnake(this.getText(ic))); break;
            case -43: this.performReplace(Util.snakeToCamel(this.getText(ic))); break;
            case -44: this.performReplace(Util.sortChars(this.getText(ic))); break;
            case -45: this.performReplace(Util.reverseChars(this.getText(ic))); break;
            case -46: this.performReplace(Util.shuffleChars(this.getText(ic))); break;
            case -47: this.performReplace(Util.doubleChars(this.getText(ic))); break;
            case -71:
                ere = Util.countChars(this.getText(ic));
                this.performReplace(Util.uniqueChars(this.getText(ic)));
                aft = Util.countChars(this.getText(ic));
                this.toastIt(ere + " → " + aft);
                break;
            case -48: this.performReplace(Util.sortLines(this.getText(ic))); break;
            case -49: this.performReplace(Util.reverseLines(this.getText(ic))); break;
            case -50: this.performReplace(Util.shuffleLines(this.getText(ic))); break;
            case -51: this.performReplace(Util.doubleLines(this.getText(ic))); break;
            case -72:
                ere = Util.countLines(this.getText(ic));
                this.performReplace(Util.uniqueLines(this.getText(ic)));
                aft = Util.countLines(this.getText(ic));
                this.toastIt(ere + " → " + aft);
                break;
            case -92:
                String text = this.getText(ic);
                this.toastIt("Chars: " + Util.countChars(text) + "\t\tWords: " + Util.countWords(text) + "\t\tLines: " + Util.countLines(text));
                break;
            case -93: this.toastIt(Util.unidata(this.getText(ic))); break;
            case -52: this.performReplace(Util.dashesToSpaces(this.getText(ic))); break;
            case -53: this.performReplace(Util.underscoresToSpaces(this.getText(ic))); break;
            case -54: this.performReplace(Util.spacesToDashes(this.getText(ic))); break;
            case -55: this.performReplace(Util.spacesToUnderscores(this.getText(ic))); break;
            case -56: this.performReplace(Util.spacesToLinebreaks(this.getText(ic))); break;
            case -57: this.performReplace(Util.linebreaksToSpaces(this.getText(ic))); break;
            case -58: this.performReplace(Util.spacesToTabs(this.getText(ic))); break;
            case -59: this.performReplace(Util.tabsToSpaces(this.getText(ic))); break;
            case -60: this.performReplace(Util.splitWithLinebreaks(this.getText(ic))); break;
            case -61: this.performReplace(Util.splitWithSpaces(this.getText(ic))); break;
            case -62: this.performReplace(Util.removeSpaces(this.getText(ic))); break;
            case -63: this.performReplace(Util.reduceSpaces(this.getText(ic))); break;
            case -64: this.performReplace(Util.increaseIndentation(this.getText(ic))); break;
            case -65: this.performReplace(Util.decreaseIndentation(this.getText(ic))); break;
            case -66: this.performReplace(Util.trimEndingWhitespace(this.getText(ic))); break;
            case -67: this.performReplace(Util.trimTrailingWhitespace(this.getText(ic))); break;
            case -68: this.performReplace(Util.normalize(this.getText(ic))); break;
            case -69: this.performReplace(Util.slug(this.getText(ic))); break;
            case -70:
                if (!this.isSelecting()) {
                    this.sendKey(KeyEvent.KEYCODE_MOVE_END);
                    this.commitText(" ");
                    this.sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
                    this.sendKey(KeyEvent.KEYCODE_MOVE_END);
                }
                else {
                    this.performReplace(Util.linebreaksToSpaces(this.getText(ic)));
                }
                break;
            case -73: this.commitText(Util.timemoji()); break;
            case -74: this.performContextMenuAction(16908338); break; // undo
            case -75: this.performContextMenuAction(16908339); break; // redo
            case -76: this.performContextMenuAction(16908337); break; // pasteAsPlainText,
            case -77: this.performContextMenuAction(16908323); break; // copyUrl
            case -78: this.performContextMenuAction(16908355); break; // autofill
            case -79: this.performContextMenuAction(16908330); break; // addToDictionary
            case -80: this.performContextMenuAction(16908320); break; // cut
            case -81: this.performContextMenuAction(16908321); break; // copy
            case -82: this.performContextMenuAction(16908322); break; // paste
            case -83: this.performContextMenuAction(16908319); break; // selectAll
            case -84: this.performContextMenuAction(16908324); break; // switchInputMethod
            case -85: this.performContextMenuAction(16908328); break; // startSelectingText
            case -86: this.performContextMenuAction(16908329); break; // stopSelectingText
            case -87: this.performContextMenuAction(16908326); break; // keyboardView
            case -88: this.performContextMenuAction(16908333); break; // selectTextMode
            case -89: this.performContextMenuAction(16908327); break; // closeButton
            case -90: this.performContextMenuAction(16908316); break; // extractArea
            case -91: this.performContextMenuAction(16908317); break; // candidatesArea
            case -94:
                if (Variables.isBold()) performReplace(Font.unbolden(this.getText(ic)));
                else this.performReplace(Font.bolden(this.getText(ic)));
                Variables.toggleBold();
                break;
            case -95:
                if (Variables.isItalic()) performReplace(Font.unitalicize(this.getText(ic)));
                else this.performReplace(Font.italicize(this.getText(ic)));
                Variables.toggleItalic();
                break;
            case -96:
                if (Variables.isEmphasized()) performReplace(Font.unemphasize(this.getText(ic)));
                else this.performReplace(Font.emphasize(this.getText(ic)));
                Variables.toggleEmphasized();
                break;
            case -97:
                if (getSelectionLength() == 0) Variables.toggleUnderlined();
                else this.performReplace(Font.underline(this.getText(ic)));
                break;
            case -98:
                if (getSelectionLength() == 0) Variables.toggleUnderscored();
                else this.performReplace(Font.underscore(this.getText(ic)));
                break;
            case -99:
                if (getSelectionLength() == 0) Variables.toggleStrikethrough();
                else this.performReplace(Font.strikethrough(this.getText(ic)));
                break;
            case -100:
                Variables.setAllOff();
                this.performReplace(Font.unbolden(this.getText(ic)));
                this.performReplace(Font.unitalicize(this.getText(ic)));
                this.performReplace(Font.unemphasize(this.getText(ic)));
                this.performReplace(Font.unstrikethrough(this.getText(ic)));
                this.performReplace(Font.ununderline(this.getText(ic)));
                this.performReplace(Font.ununderscore(this.getText(ic)));
                break;
            case -104: this.showActivity(Settings.ACTION_HARD_KEYBOARD_SETTINGS); break;
            case -105: this.showActivity(Settings.ACTION_LOCALE_SETTINGS); break;
            case -106: this.showActivity(Settings.ACTION_SETTINGS); break;
            case -107: this.showActivity(Settings.ACTION_USER_DICTIONARY_SETTINGS); break;
            case -108: this.showActivity(Settings.ACTION_WIFI_SETTINGS); break;
            case -109: this.showActivity(Settings.ACTION_WIRELESS_SETTINGS); break;
            case -110: this.showActivity(Settings.ACTION_VOICE_INPUT_SETTINGS); break;
            case -111: this.showActivity(Settings.ACTION_USAGE_ACCESS_SETTINGS); break;
            case -115: this.showActivity(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE); break;
            case -116: this.showActivity(Settings.ACTION_HOME_SETTINGS); break;
            case -118: this.showActivity(Settings.ACTION_INPUT_METHOD_SETTINGS); break;
            case -119: this.showActivity(Settings.ACTION_AIRPLANE_MODE_SETTINGS); break;
            case -120: this.showActivity(Settings.ACTION_SOUND_SETTINGS); break;
            case -121: this.showActivity(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS); break;
            case -123: this.showActivity(Settings.ACTION_BLUETOOTH_SETTINGS); break;
            case -124: this.showActivity(Settings.ACTION_CAPTIONING_SETTINGS); break;
            case -125: this.showActivity(Settings.ACTION_DEVICE_INFO_SETTINGS); break;
            case -126: this.performReplace(Util.convertNumberBase(this.getText(ic), 2, 10)); break;
            case -127: this.performReplace(Util.convertNumberBase(this.getText(ic), 10, 2)); break;
            case -128: this.performReplace(Util.convertNumberBase(this.getText(ic), 8, 10)); break;
            case -129: this.performReplace(Util.convertNumberBase(this.getText(ic), 10, 8)); break;
            case -130: this.performReplace(Util.convertNumberBase(this.getText(ic), 16, 10)); break;
            case -131: this.performReplace(Util.convertNumberBase(this.getText(ic), 10, 16)); break;
            case -101: this.setKeyboard(R.layout.primary); break;
            case -102: this.setKeyboard(R.layout.function); break;
            case -103: this.setKeyboard(R.layout.macros); break;
            case -133: this.setKeyboard(R.layout.hex); break;
            case -134: this.setKeyboard(R.layout.emoji); break;
            case -135: this.setKeyboard(R.layout.numeric); break;
            case -136: this.setKeyboard(R.layout.navigation); break;
            case -137: this.setKeyboard(R.layout.secondary); break;
            case -138: this.setKeyboard(R.layout.symbol); break;
            case -139:
                this.setKeyboard(R.layout.unicode);
                currentKeyboard.title = "Unicode";
            break;
            case -140: this.setKeyboard(R.layout.accents); break;
            case -141: this.setKeyboard(R.layout.ipa); break;
            case -142: this.setKeyboard(R.layout.fancy); break;
            case -143: this.setKeyboard(R.layout.function_2); break;
            case -144: this.setKeyboard(R.layout.fonts); break;

            case -145: Variables.toggleBoldSerif(); break;
            case -146: Variables.toggleItalicSerif(); break;
            case -147: Variables.toggleBoldItalicSerif(); break;
            case -148: Variables.toggleScript(); break;
            case -149: Variables.toggleScriptBold(); break;
            case -150: Variables.toggleFraktur(); break;
            case -151: Variables.toggleFrakturBold(); break;
            case -152: Variables.toggleSans(); break;
            case -153: Variables.toggleMonospace(); break;
            case -154: Variables.toggleDoublestruck(); break;
            case -155: Variables.toggleEnsquare(); break;
            case -156: Variables.toggleCircularStampLetters(); break;
            case -157: Variables.toggleRectangularStampLetters(); break;
            case -158: Variables.toggleSmallCaps(); break;
            case -159: Variables.toggleParentheses(); break;
            case -160: Variables.toggleEncircle(); break;
            case -161: Variables.toggleReflected(); break;
            case -162: Variables.toggleCaps(); break;
            case -163: performReplace(Util.replaceNbsp(this.getText(ic))); break;
            case -164:
                this.navigate(KeyEvent.KEYCODE_DPAD_UP);
                this.navigate(KeyEvent.KEYCODE_DPAD_LEFT);
                break;
            case -165:
                this.navigate(KeyEvent.KEYCODE_DPAD_UP);
                this.navigate(KeyEvent.KEYCODE_DPAD_RIGHT);
                break;
            case -166:
                this.navigate(KeyEvent.KEYCODE_DPAD_DOWN);
                this.navigate(KeyEvent.KEYCODE_DPAD_LEFT);
                break;
            case -167:
                this.navigate(KeyEvent.KEYCODE_DPAD_DOWN);
                this.navigate(KeyEvent.KEYCODE_DPAD_RIGHT);
                break;

            default:
                if (Variables.isAnyOn()) this.processKeyCombo(primaryCode);
                else this.handleCharacter(primaryCode, keyCodes);
                break;
        }
        this.redraw();
        try {
            if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("caps", false)) {
                if (isWordSeparator(ic.getTextBeforeCursor(2, 0).toString())) {
                    this.setCapsOn(true);
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

    public short getStandardRowNumber() {
        return rowNumber;
    }

    public double getHeightKeyModifier() {
        //   0 → 0.5
        //  50 → 1.0
        // 100 → 1.5
        return ((double)PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("height", 50) + 50)/100;
    }
}