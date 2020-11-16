package com.custom.keyboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.UserDictionary;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InlineSuggestionsRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.custom.keyboard.emoticon.Emoticon;
import com.custom.keyboard.emoticon.EmoticonGridView;
import com.custom.keyboard.emoticon.EmoticonPopup;
import com.custom.keyboard.unicode.Unicode;
import com.custom.keyboard.unicode.UnicodeGridView;
import com.custom.keyboard.unicode.UnicodePopup;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class CustomInputMethodService extends InputMethodService
    implements KeyboardView.OnKeyboardActionListener,
               SpellCheckerSession.SpellCheckerSessionListener {

    boolean debug = false;

    SharedPreferences sharedPreferences;
    Toast toast;

    private String mWordSeparators;

    long shiftPressed = 0;
    private boolean firstCaps = false;

    int MAX = 65536;
    private short rowNumber = 6;

    private float[] mDefaultFilter;

    private CustomKeyboardView kv;
    private CandidateView mCandidateView;
    private InputMethodManager mInputMethodManager;
    private CustomKeyboard currentKeyboard;
    // private CustomKeyboard standardKeyboard;

    private boolean mPredictionOn;
    private int indentWidth;
    private String indentString;

    private PopupWindow popupWindow = null;
    private UnicodePopup unicodePopup = null;
    private EmoticonPopup emoticonPopup = null;

    private SpellChecker spellChecker;
    private SpellCheckerSession mScs;

    @Override
    public void onCreate() {
        super.onCreate();
        if (debug) System.out.println("onCreate");
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        toast = new Toast(getBaseContext());

        spellChecker = new SpellChecker(getApplicationContext(), true);

        final TextServicesManager tsm = (TextServicesManager)getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        mScs = tsm.newSpellCheckerSession(null, Locale.ENGLISH, this, true);
    }

    @Override
    public void onInitializeInterface() {
        if (debug) System.out.println("onInitializeInterface");
    }

    @Override
    public View onCreateInputView() {
        if (debug) System.out.println("onCreateInputView");
        kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        kv.setOnKeyboardActionListener(this);
        kv.setKeyboard(new CustomKeyboard(this, R.layout.primary));
        return kv;
    }

    @Override
    public View onCreateCandidatesView() {
        setTheme();
        Paint mPaint = new Paint();
        if (mCandidateView == null) {
            mCandidateView = new CandidateView(this);
            mCandidateView.setService(this);
        }
        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        return mCandidateView;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        if (debug) System.out.println("onStartInputView: "+" "+info+" "+restarting);
        ViewGroup originalParent = (ViewGroup)kv.getParent();
        if (originalParent != null) {
            originalParent.setPadding(0, 0, 0, 0);
            kv.setPopupParent(originalParent);
        }
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        if (debug) System.out.println("onStartInput"+" "+attribute+" "+restarting);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (!sharedPreferences.getString("indent_width", "4").isEmpty()) {
            try {
                indentWidth = Integer.valueOf(sharedPreferences.getString("indent_width", "4"));
            }
            catch(Exception e) {
                indentWidth = 4;
            }
            indentString = new String(new char[indentWidth]).replace("\0", " ");
        }

        Paint mPaint = setTheme();

        int bg = (int)Long.parseLong(Themes.extractBackgroundColor(mDefaultFilter), 16);
        Color background = Color.valueOf(bg);
        float transparency = sharedPreferences.getInt("transparency", 100) / 100f;
        int fontSize;
        try {
            fontSize = Integer.parseInt(sharedPreferences.getString("font_size", "48"));
        }
        catch(Exception e) {
            fontSize = 48;
        }
        boolean mPreviewOn = sharedPreferences.getBoolean("preview", false);
        mPredictionOn = sharedPreferences.getBoolean("pred", false);
        // debug = sharedPreferences.getBoolean("debug", debug);

        mWordSeparators = sharedPreferences.getString("word_separators", getResources().getString(R.string.word_separators));

        updateCandidates();

        kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);

        try {
            setKeyboard(
                sharedPreferences.getInt("current_layout", 0),
                sharedPreferences.getString("current_layout_title", "")
            );
        }
        catch(Exception e) {
            setKeyboard(R.layout.primary, "Primary");
        }

        setInputType();
        capsOnFirst();

        kv.setBackgroundColor(Color.argb(transparency, background.red(), background.green(), background.blue()));
        mPaint.setTextSize(fontSize);
        kv.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        kv.setOnKeyboardActionListener(this);

        kv.getCustomKeyboard().changeKeyHeight(getHeightKeyModifier());
        kv.setPreviewEnabled(mPreviewOn);

        currentKeyboard.setRowNumber(getRowNumber());
        kv.setKeyboard(currentKeyboard);

        setInputView(kv);

        mCandidateView = new CandidateView(this);
        mCandidateView.setService(this);
        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        if (isKeyboardVisible()) setCandidatesView(mCandidateView);

        if (mPredictionOn) setCandidatesViewShown(isKeyboardVisible());

        // kv.setBackgroundResource(Themes.randomBackground());
    }

    public void setKeyboard(int id, String title) {
        currentKeyboard = new CustomKeyboard(getBaseContext(), id);
        currentKeyboard.setRowNumber(getStandardRowNumber());
        currentKeyboard.title = title;
        kv.setKeyboard(currentKeyboard);
        sharedPreferences.edit().putInt("current_layout", id).apply();
        sharedPreferences.edit().putString("current_layout_title", title).apply();
        redraw();
    }

    private void setInputType() {
        int id = currentKeyboard.xmlLayoutResId;
        String title = currentKeyboard.title;

        EditorInfo attribute = getCurrentInputEditorInfo();
        int webInputType = attribute.inputType & InputType.TYPE_MASK_VARIATION;

        switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_DATETIME:
            case InputType.TYPE_CLASS_PHONE:
                setKeyboard(R.layout.numeric, "Numeric");
            break;
            case InputType.TYPE_CLASS_TEXT:
                if (webInputType == InputType.TYPE_TEXT_VARIATION_URI
                    || webInputType == InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
                    || webInputType == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    || webInputType == InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS) {
                    setKeyboard(R.layout.domain, "Domain");
                }
                else setKeyboard(id, title);
            break;
            default:
                setKeyboard(id, title);
            break;
        }
        if (kv != null) kv.setKeyboard(currentKeyboard);
    }

    long time = 0;

    String prevBuffer = "";
    String nextBuffer = "";

    @Override
    public void onPress(int primaryCode) {
        if (debug) System.out.println("onPress: "+primaryCode);
        prevBuffer = getPrevWord();
        nextBuffer = getNextWord();
        time = System.nanoTime() - time;

        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("vib", false)) {
            Vibrator v = (Vibrator)getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
            if (v != null) v.vibrate(40);
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        if (debug) System.out.println("onRelease: "+primaryCode);
        time = (System.nanoTime() - time) / 1000000;
        if (time > 300) {
            InputConnection ic = getCurrentInputConnection();
            switch (primaryCode) {
                case -2: handleTab(); break;
                // showInputMethodPicker()
                case -12: selectAll(); break;
                case -15: if (isSelecting()) selectPrevWord(); else moveLeftOneWord(); break;
                case -16: if (isSelecting()) selectNextWord(); else moveRightOneWord(); break;
                case -200: clipboardToBuffer(getSelectedText(ic)); break;
            }
        }
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
            if (debug) System.out.println(e);
        }

        String nextLine = getNextLine();
        int nextChar = 0;
        try {
            if (nextLine != null && nextLine.length() > 0) {
                ArrayList<Integer> nextChars = Util.asUnicodeArray(nextLine);
                nextChar = nextChars.get(0);
            }
        }
        catch (Exception e) {
            if (debug) System.out.println(e);
        }
        try {
            boolean isBold = FontVariants.isBold(prevChar) || FontVariants.isBold(nextChar);
            boolean isItalic = FontVariants.isItalic(prevChar) || FontVariants.isItalic(nextChar);
            boolean isEmphasized = FontVariants.isEmphasized(prevChar) || FontVariants.isEmphasized(nextChar);

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
            if (debug) System.out.println(e);
        }
        if ((getSelectionStart() == 0) // || ic.getTextBeforeCursor(1, 0) == "\n"
            && PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("caps", false)) {
            if (Variables.isShift()) {
                Variables.setShiftOff();
                firstCaps = false;
            }
            else {
                firstCaps = !firstCaps;
            }
            setCapsOn(firstCaps);
        }
        redraw();

        updateCandidates();

        if ((newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
            InputConnection ic = getCurrentInputConnection();

            if (ic != null) {
                ic.finishComposingText();
            }
        }
    }

    @Override
    public void onComputeInsets(InputMethodService.Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }
    }

    @Override
    public void swipeLeft() {
        selectPrevWord();
    }

    @Override
    public void swipeRight() {
        selectNextWord();
    }

    @Override
    public void swipeDown() {
        setCandidatesViewShown(false);
    }

    @Override
    public void swipeUp() {
        setCandidatesViewShown(true);
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();

        updateCandidates();

        setCandidatesViewShown(false);

        Variables.setSelectingOff();

        if (kv != null) {
            kv.closing();
        }
    }

    public boolean isKeyboardVisible() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();
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
    public void adjustKeys(CustomKeyboard currentKeyboard) {
        Bounds bounds = getBounds(currentKeyboard.getKeys());
        Map<Integer,List<Keyboard.Key>> layoutRows = getKeyboardRows(currentKeyboard);
        for (Map.Entry<Integer, List<Keyboard.Key>> entry : layoutRows.entrySet()) {
            for(Keyboard.Key key : entry.getValue()) {
                key.width = bounds.dX / entry.getValue().size();
            }
        }
        redraw();
    }

    public Map<Integer,List<Keyboard.Key>> getKeyboardRows(CustomKeyboard keyboard) {
        Map<Integer,List<Keyboard.Key>> layoutRows = new TreeMap<>();
        for (Keyboard.Key key : keyboard.getKeys()) {
            if (!layoutRows.containsKey(key.y)) {
                layoutRows.put(key.y, new ArrayList<Keyboard.Key>());
            }
            layoutRows.get(key.y).add(key);
        }
        return layoutRows;
    }

    public void clearAll() {
        // sendKey(KeyEvent.KEYCODE_CLEAR);
        InputConnection ic = getCurrentInputConnection();
        ic = getCurrentInputConnection();
        ic.deleteSurroundingText(MAX, MAX);
    }

    public String getSelectedText(@NonNull InputConnection ic) {
        CharSequence text = ic.getSelectedText(0);
        if (text == null) return "";
        return (String) text;
    }

    public String getAllText(@NonNull InputConnection ic) {
        return Util.orNull(ic.getTextBeforeCursor(MAX, 0), "").toString()
             + Util.orNull(ic.getTextAfterCursor(MAX, 0),  "").toString();
    }

    public void sendCustomKey(String key) {
        InputConnection ic = getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext()); // this?
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        int cursorLocation = getSelectionStart();
        String ins = sharedPreferences.getString(key, "");
        ic.beginBatchEdit();
        commitText(ins, cursorLocation + (ins != null ? ins.length() : 1));
        ic.endBatchEdit();
    }

    public String getCurrentWord() {
        // @TODO: if at word boundary, should return the word it's actually touching:
        // "one| two" should return one, "one |two" should return two
        return getPrevWord()+getNextWord();
    }
    public String getPrevWord() {
        InputConnection ic = getCurrentInputConnection();
        String[] words = Util.orNull(ic.getTextBeforeCursor(MAX, 0), "").toString().split("\\b", -1); // mWordSeparators);
        if (words.length < 1) return "";
        String prevWord = words[words.length - 1];
        if (words.length > 1 && prevWord.equals("")) prevWord = words[words.length - 2];
        return prevWord;
    }
    public String getNextWord() {
        InputConnection ic = getCurrentInputConnection();
        String[] words = Util.orNull(ic.getTextAfterCursor(MAX, 0), "").toString().split("\\b", -1); // mWordSeparators);
        if (words.length < 1) return "";
        String nextWord = words[0];
        if (words.length > 1 && nextWord.equals("")) nextWord = words[1];
        return nextWord;
    }
    public void selectPrevWord() {
        String prevWord = getPrevWord();
        if (!isSelecting()) Variables.setSelectingOn(getSelectionStart());
        int times = prevWord.length();
        while (times --> 0) {
            navigate(KeyEvent.KEYCODE_DPAD_LEFT);
        }
    }
    public void selectNextWord() {
        String nextWord = getNextWord();
        if (!isSelecting()) Variables.setSelectingOn(getSelectionStart());
        int times = nextWord.length();
        while (times --> 0) {
            navigate(KeyEvent.KEYCODE_DPAD_RIGHT);
        }
    }

    public void moveLeftOneWord() {
        String prevWord = getPrevWord();
        int times = prevWord.length();
        while (times --> 0) {
            navigate(KeyEvent.KEYCODE_DPAD_LEFT);
        }
    }
    public void moveRightOneWord() {
        String nextWord = getNextWord();
        int times = nextWord.length();
        while (times --> 0) {
            navigate(KeyEvent.KEYCODE_DPAD_RIGHT);
        }
    }

    public void deletePrevWord() {
        String prevWord = prevBuffer;
        if (debug) System.out.println(prevWord+" "+prevWord.length());
        int times = prevWord.length();
        while (times --> 1) {
            handleBackspace();
        }
    }
    public void deleteNextWord() {
        String nextWord = nextBuffer; // getNextWord();
        if (debug) System.out.println(nextWord+" "+nextWord.length());
        int times = nextWord.length();
        while (times --> 1) {
            handleDelete();
        }
    }

    public void goToStart() {
        InputConnection ic = getCurrentInputConnection();
        ic.setSelection(0, 0);
    }

    public void goToEnd() {
        InputConnection ic = getCurrentInputConnection();
        ic.setSelection(
            (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length(),
            (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length()
        );
    }

    public void selectNone() {
        InputConnection ic = getCurrentInputConnection();
        try {
            int end = getSelectionEnd();
            ic.setSelection(end, end);
            Variables.setSelectingOff();
        }
        catch (Exception e) {
            sendDataToErrorOutput(e.toString());
        }
    }

    public void selectLine() {
        sendKey(KeyEvent.KEYCODE_MOVE_HOME);
        Variables.setSelectingOn(getSelectionStart());
        navigate(KeyEvent.KEYCODE_MOVE_END);
        Variables.setSelectingOff();
    }

    public void selectAll() {
        InputConnection ic = getCurrentInputConnection();
        ic.setSelection(0, (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length());
    }

    public String getPrevLine() {
        InputConnection ic = getCurrentInputConnection();
        CharSequence textBeforeCursor = ic.getTextBeforeCursor(MAX, 0);
        if (textBeforeCursor == null) return "";
        if (textBeforeCursor.length() < 1) return "";
        String[] lines = textBeforeCursor.toString().split("\n");
        if (lines.length < 2) return textBeforeCursor.toString();
        return lines[lines.length - 1];
    }

    public String getNextLine() {
        InputConnection ic = getCurrentInputConnection();
        CharSequence textAfterCursor = ic.getTextAfterCursor(MAX, 0);
        if (textAfterCursor == null) return "";
        if (textAfterCursor.length() < 1) return "";
        String[] lines = textAfterCursor.toString().split("\n");
        if (lines.length < 2) return textAfterCursor.toString();
        return lines[0];
    }

    public int getCurrentLine() {
        InputConnection ic = getCurrentInputConnection();
        return Util.getLines(ic.getTextBeforeCursor(MAX, 0).toString()).length;
    }

    public int getLineCount() {
        InputConnection ic = getCurrentInputConnection();
        return Util.getLines(getAllText(ic)).length;
    }

    public int[] getCursorCoordinates() {
        return new int[]{getCursorLocationOnLine(), getCurrentLine()};
    }

    public int getCursorLocationOnLine() {
        return getPrevLine().length();
    }

    public int getCursorLocation() {
        return getSelectionStart();
    }

    public int getCursorPosition() {
        InputConnection ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.startOffset + extracted.selectionStart;
    }

    public int getStartOffset() {
        InputConnection ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.startOffset;
    }

    public void redraw() {
        kv.invalidateAllKeys();
        kv.draw(new Canvas());
    }

    public void commitText(String text) {
        commitText(text, 1);
    }

    public void commitText(String text, int offset) {
        InputConnection ic = getCurrentInputConnection();
        ic.beginBatchEdit();
        ic.commitText(text, offset);
        ic.endBatchEdit();
    }

    public void performReplace(String newText) {
        InputConnection ic = getCurrentInputConnection();
        ic.beginBatchEdit();
        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0) {
            int a = getSelectionStart();
            int b = getSelectionStart() + newText.length();
            ic.commitText(newText, 1);
            ic.setSelection(a, b);
        }
        ic.endBatchEdit();
    }

    private void commitTyped(InputConnection inputConnection) {
        if (getPrevWord().length() > 0) {
            commitText(getPrevWord());
            updateCandidates();
        }
    }

    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null && kv != null && R.layout.primary == kv.getId()/*.getKeyboard()*/) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != InputType.TYPE_NULL) {
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            kv.setShifted(/*mCapsLock || */caps != 0);
        }
    }

    @Override
    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) {
            return;
        }
        ic.beginBatchEdit();
        ic.commitText(text, 1);
        ic.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void showVoiceInput() {
        InputMethodManager inputMethodManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.setInputMethod(getToken(), "com.google.android.googlequicksearchbox/com.google.android.voicesearch.ime.VoiceInputMethodService");
        }
    }

    private void showInputMethodPicker() {
        InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imeManager != null) imeManager.showInputMethodPicker();
    }

    private void sendDataToErrorOutput(String output) {
        HashMap<String, String> cursorData = new HashMap<>();
        cursorData.put("data", output);
        sendMessageToActivity("DebugHelper", cursorData);
    }
    private void clearErrorOutput() {
        HashMap<String, String> cursorData = new HashMap<>();
        cursorData.put("clear", "");
        sendMessageToActivity("DebugHelper", cursorData);
    }

    @Override
    public void onGetSuggestions(SuggestionsInfo[] suggestionsInfos) {
        final StringBuffer sb = new StringBuffer("");
        for(SuggestionsInfo suggestionsInfo : suggestionsInfos) {
            int n = suggestionsInfo.getSuggestionsCount();
            for (int i = 0; i < n; i++) {
                if ((suggestionsInfo.getSuggestionsAttributes() & SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO) != SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO) {
                    continue;
                }
                sb.append(suggestionsInfo.getSuggestionAt(i));
                if (i < n-1) sb.append(",");
            }
        }
        sendDataToErrorOutput(sb.toString());
        displaySuggestions(sb.toString());
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] sentenceSuggestionsInfos) {
        final StringBuffer sb = new StringBuffer("");
        for (SentenceSuggestionsInfo result : sentenceSuggestionsInfos) {
            int n = result.getSuggestionsCount();
            for (int i = 0; i < n; i++) {
                int m = result.getSuggestionsInfoAt(i).getSuggestionsCount();
                if ((result.getSuggestionsInfoAt(i).getSuggestionsAttributes() & SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO) != SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO) {
                    continue;
                }
                for (int k = 0; k < m; k++) {
                    sb.append(result.getSuggestionsInfoAt(i).getSuggestionAt(k));
                    if (k < m-1) sb.append(",");
                }
            }
        }
        sendDataToErrorOutput(sb.toString());
        displaySuggestions(sb.toString());
    }

    private void displaySuggestions(final String suggestions) {


        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                String[] wordInfos = suggestions.toString().split("\n");
                String prevWord = getPrevWord();
                String prevWordInfos = wordInfos[wordInfos.length-1];
                boolean isTitleCase = Util.isTitleCase(prevWord);
                boolean isUpperCase = Util.isUpperCase(prevWord) && prevWord.length() > 1;
                prevWord = prevWord.toLowerCase();
                ArrayList<String> results = new ArrayList<String>();

                if (wordInfos.length < 1) {
                    // boolean inTrie = SpellChecker.inTrie(prevWord);
                    boolean isPrefix = SpellChecker.isPrefix(prevWord);

                    results.add(prevWord);

                    ArrayList<String> common = SpellChecker.getCommon(prevWord);
                    results.addAll(common);
                    if (isPrefix) {
                        results.addAll(SpellChecker.getCompletions(prevWord));
                    }
                }
                else {
                    results.add(prevWord);
                    for(String s : prevWordInfos.toString().split(",")) {
                        if (s.isEmpty() || s.trim().isEmpty()) continue;
                        results.add(s);
                    }
                }

                if (isUpperCase) {
                    for(int i = 0; i < results.size(); i++) {
                        results.set(i, Util.toUpperCase(results.get(i)));
                    }
                }
                else if (isTitleCase) {
                    for(int i = 0; i < results.size(); i++) {
                        results.set(i, Util.toTitleCase(results.get(i)));
                    }
                }

                mCandidateView.setSuggestions(results, true, true);
            }
        });
    }

    private void fetchSuggestionsFor(String input) {
        sendDataToErrorOutput(input);
        if(!input.isEmpty()) {
            try {
                mScs.getSuggestions(new TextInfo(input), 5);
                // mScs.getSentenceSuggestions(new TextInfo[]{new TextInfo(input)}, 5);
            }
            catch(Exception e) {
                sendDataToErrorOutput(e.toString());
            }
        }
    }

    private void updateCandidates() {

        if (!mPredictionOn) return;
        if (mCandidateView == null) return;
        if (emoticonPopup != null && emoticonPopup.isShowing()) return;

        String prevLine = getPrevLine();
        String prevWord = getPrevWord();

        if (prevWord.trim().equals("")
            || prevLine.length() == 0
            || prevWord.length() == 0) {
            mCandidateView.clear();
            return;
        }

        String prevChar = String.valueOf(prevLine.charAt(prevLine.length()-1)).substring(0, 1);
        if (!Character.isLetter(prevChar.charAt(0))) {
            mCandidateView.clear();
            return;
        }

        if (mPredictionOn) setCandidatesViewShown(true);
        try {
            fetchSuggestionsFor(prevWord);
        }
        catch(Exception e) {
            sendDataToErrorOutput(e.toString());
        }
    }

    public void addToDictionary(String word) {
        if (word.trim().isEmpty()) return;
        UserDictionary.Words.addWord(this, word, 1, null, Locale.getDefault());
        spellChecker.addToTrie(word);
        toastIt(word+" added to dictionary");
    }
    public void pickDefaultCandidate() {
        pickSuggestionManually(0);
    }
    public void pickSuggestionManually(int index) {
        String prevWord = getPrevWord();
        ArrayList<String> suggestions = mCandidateView.getSuggestions();
        if (suggestions != null && index >= 0 && index < suggestions.size()) {
            getCurrentInputConnection().deleteSurroundingText(prevWord.length(), 0);
            commitText(suggestions.get(index));
            commitText(" ");
            if (mCandidateView != null) {
                mCandidateView.clear();
            }
            playClick(32);
            if (index == 0 && !SpellChecker.inTrie(prevWord)) {
                addToDictionary(prevWord);
            }
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void hide() {
        handleClose(); 
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void handleClose() {
        commitTyped(getCurrentInputConnection());
        setCandidatesViewShown(false);
        requestHideSelf(0);
        kv.closing();
    }

    public int getGravity() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String xAxis = sharedPreferences.getString("x_axis", "BOTTOM").toUpperCase();
        String yAxis = sharedPreferences.getString("y_axis", "CENTER_HORIZONTAL").toUpperCase();
        boolean fillHorizontal = sharedPreferences.getBoolean("fill_horizontal", false);
        boolean fillVertical = sharedPreferences.getBoolean("fill_vertical", false);

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
        InputConnection ic = getCurrentInputConnection();
        ic.performContextMenuAction(id);
    }

    private IBinder getToken() {
        final Dialog dialog = getWindow();
        if (dialog == null) return null;
        final Window window = dialog.getWindow();
        if (window == null) return null;
        return window.getAttributes().token;
    }

    private void handleLanguageSwitch() {
        mInputMethodManager.switchToNextInputMethod(getToken(), false /* onlyCurrentIme */);
    }

    private void sendKeyEvent(int keyCode, int metaState) {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, getHardKeyCode(keyCode), 0, metaState));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   getHardKeyCode(keyCode), 0, metaState));
    }

    private void processKeyCombo(int keycode) {
        if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) sendKeyEvent(getHardKeyCode(keycode), KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON);
            else {
                if (Variables.isCtrl()) sendKeyEvent(getHardKeyCode(keycode), KeyEvent.META_CTRL_ON);
                if (Variables.isAlt())  sendKeyEvent(getHardKeyCode(keycode), KeyEvent.META_ALT_ON);
            }
        }
    }

    private int getHardKeyCode(int keycode) {
        // PopupWindow p = new PopupWindow();
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


    public Paint setTheme() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        switch (sharedPreferences.getString("theme", "1")) {
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

        boolean inverted = sharedPreferences.getBoolean("invert", false);
        float m = inverted ? -1.0f : 1.0f;

        float[] sCustomColorArray = {
            m, 0, 0, 0.0f, mDefaultFilter[4],
            0, m, 0, 0.0f, mDefaultFilter[9],
            0, 0, m, 0.0f, mDefaultFilter[14],
            0, 0, 0, 1.0f, 1
        };

        mDefaultFilter = sCustomColorArray;

/*        
        int bg = (int)Long.parseLong(Themes.extractBackgroundColor(mDefaultFilter), 16);
        int fg = (int)Long.parseLong(Themes.extractForegroundColor(mDefaultFilter), 16);

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putInt(background_color, bg);
        editor.putInt(foreground_color, fg);
        editor.apply();
*/

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(mDefaultFilter);
        Paint mPaint = new Paint();
        mPaint.setColorFilter(colorFilter);

        return mPaint;
    }


    private void setCapsOn(boolean on) {
        if (Variables.isShift()) kv.getKeyboard().setShifted(true);
        else kv.getKeyboard().setShifted(on);
        kv.invalidateAllKeys();
    }

    private void capsOnFirst() {
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

    private int getCursorCapsMode(InputConnection ic, EditorInfo attr) {
        int caps = 0;
        EditorInfo ei = getCurrentInputEditorInfo();
        if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
            caps = ic.getCursorCapsMode(attr.inputType);
        }
        return caps;
    }

    public void toastIt(int num) {
        toastIt(String.valueOf(num));
    }
    public void toastIt(Exception e) {
        toastIt(e.toString());
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

        // sendDataToErrorOutput(text);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (!sharedPreferences.getBoolean("debug", false)) return;
        if (toast != null) toast.cancel();
        toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }
    static void print(@NonNull Object... a) {
        for (Object i : a) System.out.print(i + " ");
        System.out.println();
    }

    // BACK BUTTON - 4
    // "0" - "9"-> 7 - 16
    // UP-19, DOWN-20, LEFT-21, RIGHT-22
    // SELECT (MIDDLE) BUTTON - 23
    // a - z-> 29 - 54
    // SHIFT - 59, SPACE - 62, ENTER - 66, BACKSPACE - 67
    // MENU BUTTON - 82

    private void sendRawKey(int keyCode) {
        if (keyCode == '\n') {
            sendKey(KeyEvent.KEYCODE_ENTER);
        }
        else {
            if (keyCode >= '0' && keyCode <= '9') {
                sendKey(keyCode - '0' + KeyEvent.KEYCODE_0);
            }
            else {
                getCurrentInputConnection().commitText(String.valueOf((char)keyCode), 1); // Util.largeIntToChar(primaryCode)
            }
        }
    }

    public Keyboard.Key getKey(int primaryCode) {
        if (currentKeyboard == null) return null;
        for (Keyboard.Key key : currentKeyboard.getKeys()) {
            if (key.codes[0] == primaryCode) return key;
        }
        return null;
    }

    public void sendKey(int primaryCode) {
        InputConnection ic = getCurrentInputConnection();
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, primaryCode));
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   primaryCode));
    }

    public void sendKey(int primaryCode, int times) {
        // InputConnection ic = getCurrentInputConnection();
        while (times --> 0) {
            sendKey(primaryCode);
        }
    }

    public void sendKeys(@NonNull int[] keys) {
        // InputConnection ic = getCurrentInputConnection();
        for (int key : keys) {
            sendKey(key);
        }
    }

    public void navigate(int primaryCode, int secondaryCode) {
        navigate(primaryCode);
        navigate(secondaryCode);
    }

    public void navigate(int primaryCode) {
        InputConnection ic = getCurrentInputConnection();
        if      (!isSelecting() && primaryCode == KeyEvent.KEYCODE_DPAD_LEFT && String.valueOf(ic.getTextBeforeCursor(indentWidth, 0)).equals(indentString) && sharedPreferences.getBoolean("indent", false)) {
            sendKey(primaryCode, indentWidth);
        }
        else if (!isSelecting() && primaryCode == KeyEvent.KEYCODE_DPAD_RIGHT && String.valueOf(ic.getTextAfterCursor(indentWidth, 0)).equals(indentString) && sharedPreferences.getBoolean("indent", false)) {
            sendKey(primaryCode, indentWidth);
        }
        else {
            if (Variables.isSelecting()) ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT));
            sendKey(primaryCode);
            if (Variables.isSelecting()) ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   KeyEvent.KEYCODE_SHIFT_LEFT));
        }
    }

    public void replaceText(@NonNull String src, String trg) {
        InputConnection ic = getCurrentInputConnection();
        ic.deleteSurroundingText(src.length(), 0);
        commitText(trg);
    }

    public Boolean isSelecting() {
        return getSelectionStart() != getSelectionEnd();
    }

    public int getSelectionStart() {
        InputConnection ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.selectionStart;
    }

    public int getSelectionEnd() {
        InputConnection ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.selectionEnd;
    }

    public int getSelectionLength() {
        InputConnection ic = getCurrentInputConnection();
        ExtractedText extracted = ic.getExtractedText(new ExtractedTextRequest(), 0);
        if (extracted == null) return -1;
        return extracted.selectionEnd - extracted.selectionStart;
    }

    private void handleDelete() {
        InputConnection ic = getCurrentInputConnection();
        final int length = getSelectionLength();

        ic.beginBatchEdit();

        if (sharedPreferences.getBoolean("pairs", false)
            && ic.getTextAfterCursor(1, 0) != null
            && String.valueOf(ic.getTextAfterCursor(1, 0)).length() >= 1
            && Util.contains("({\"[", String.valueOf(ic.getTextBeforeCursor(1, 0)))
            && String.valueOf(ic.getTextAfterCursor(1, 0)).equals(String.valueOf(ic.getTextBeforeCursor(1, 0)))) {
            ic.deleteSurroundingText(1, 0);
        }
        else if (!isSelecting()
            && sharedPreferences.getBoolean("indent", false)
            && ic.getTextAfterCursor(indentWidth, 0) != null
            && String.valueOf(ic.getTextAfterCursor(indentWidth, 0)).length() >= indentWidth
            && String.valueOf(ic.getTextAfterCursor(indentWidth, 0)).equals(indentString)) {
            ic.deleteSurroundingText(0, indentWidth);
        }
        else if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) {getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));}
            if (Variables.isAlt()) {getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0, KeyEvent.META_ALT_ON));}
            if (Variables.isCtrl()) {getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0, KeyEvent.META_CTRL_ON));}
        }
        else if (length > 0) {
            getCurrentInputConnection().commitText("", 1);
        }
        else if (length == 0) {
            if (ic.getTextAfterCursor(1, 0).length() > 0) {
                ic.deleteSurroundingText(0, Character.isSurrogate(ic.getTextAfterCursor(1, 0).charAt(0)) ? 2 : 1);
            }
        }
        else {
            sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());

        // updateCandidates();

        ic.endBatchEdit();
    }

    private void handleBackspace() {
        InputConnection ic = getCurrentInputConnection();
        final int length = getSelectionLength();

        ic.beginBatchEdit();

        if (sharedPreferences.getBoolean("pairs", false)
            && ic.getTextBeforeCursor(1, 0) != null
            && String.valueOf(ic.getTextBeforeCursor(1, 0)).length() >= 1
            && Util.contains(")}\"]", String.valueOf(ic.getTextAfterCursor(1, 0)))
            && String.valueOf(ic.getTextBeforeCursor(1, 0)).equals(String.valueOf(ic.getTextAfterCursor(1, 0)))) {
            ic.deleteSurroundingText(0, 1);
        }
        else if (!isSelecting()
            && sharedPreferences.getBoolean("indent", false)
            && ic.getTextBeforeCursor(indentWidth, 0) != null
            && String.valueOf(ic.getTextBeforeCursor(indentWidth, 0)).length() >= indentWidth
            && String.valueOf(ic.getTextBeforeCursor(indentWidth, 0)).equals(indentString)) {
            ic.deleteSurroundingText(indentWidth, 0);
        }
        else if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            if (Variables.isAlt())  getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_ALT_ON));
            if (Variables.isCtrl()) getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_CTRL_ON));
        }
        else if (length == 0) {
            if (ic.getTextBeforeCursor(1, 0).length() > 0) {
                ic.deleteSurroundingText(Character.isSurrogate(ic.getTextBeforeCursor(1, 0).charAt(0)) ? 2 : 1, 0);
            }
        }
        else if (length > 0) {
            getCurrentInputConnection().commitText("", 1);
        }
        else {
            sendKey(KeyEvent.KEYCODE_DEL);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());

        // updateCandidates();

        ic.endBatchEdit();
    }

    private void handleSpace() {
        commitText(" ");
        // mCandidateView.clear();
        updateCandidates();
/*
        ArrayList<String> suggestions = SpellChecker.getCommon(getPrevWord());
        if (suggestions.size() > 0 && PreferenceManager.getDefaultSharedPreferences(this).getBoolean("auto", false)) {
            replaceText(getPrevWord(), suggestions.get(0));
        }
*/
    }
    public void handleTab() {
        InputConnection ic = getCurrentInputConnection();
        // @TODO: use variable for spaces
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("indent", false)) {
            int spaceCount = (indentWidth - (getPrevLine().length() % indentWidth));
            if (spaceCount > 0 && spaceCount < indentWidth && getPrevLine().length() < indentWidth) {
                spaceCount = indentWidth;
            }
            commitText(indentString.substring(0, spaceCount), 0);
        }
        else {
            commitText(" ");
        }
        if (isSelecting()) {
            ic.setSelection(getSelectionStart(), getSelectionEnd() + indentString.length()-1);
        }
        mCandidateView.clear();
/*
        if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            if (Variables.isAlt())  getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_ALT_ON));
            if (Variables.isCtrl()) getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_CTRL_ON));
        }
        else {
            getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0));
            getCurrentInputConnection().sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   KeyEvent.KEYCODE_TAB, 0));
        }
*/
    }
    private void handleCharacter(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        ic.beginBatchEdit();

        if (isInputViewShown()) {
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
                if (code.equals("\"")) commitText("\"\"", 1);
                sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
                return;
            }
        }

        if (Variables.isBold()) { primaryCode = FontVariants.getBold(primaryCode);}
        if (Variables.isItalic()) { primaryCode = FontVariants.getItalic(primaryCode);}
        if (Variables.isEmphasized()) { primaryCode = FontVariants.getEmphasized(primaryCode);}
        // if (Variables.isUnderlined()) { primaryCode = Font.getUnderlined(primaryCode);}
        // if (Variables.isUnderscored()) { primaryCode = Font.getUnderscored(primaryCode);}
        // if (Variables.isStrikethrough()) { primaryCode = Font.getStrikethrough(primaryCode);}
        if (Variables.isBoldSerif()) { primaryCode = FontVariants.toBoldSerif(primaryCode, kv.isShifted());}
        if (Variables.isItalicSerif()) { primaryCode = FontVariants.toItalicSerif(primaryCode, kv.isShifted());}
        if (Variables.isBoldItalicSerif()) { primaryCode = FontVariants.toBoldItalicSerif(primaryCode, kv.isShifted());}
        if (Variables.isScript()) { primaryCode = FontVariants.toScript(primaryCode, kv.isShifted());}
        if (Variables.isScriptBold()) { primaryCode = FontVariants.toScriptBold(primaryCode, kv.isShifted());}
        if (Variables.isFraktur()) { primaryCode = FontVariants.toFraktur(primaryCode, kv.isShifted());}
        if (Variables.isFrakturBold()) { primaryCode = FontVariants.toFrakturBold(primaryCode, kv.isShifted());}
        if (Variables.isSans()) { primaryCode = FontVariants.toSans(primaryCode, kv.isShifted());}
        if (Variables.isMonospace()) { primaryCode = FontVariants.toMonospace(primaryCode, kv.isShifted());}
        if (Variables.isDoublestruck()) { primaryCode = FontVariants.toDoublestruck(primaryCode, kv.isShifted());}
        if (Variables.isEnsquare()) { primaryCode = FontVariants.ensquare(primaryCode);}
        if (Variables.isCircularStampLetters()) { primaryCode = FontVariants.toCircularStampLetters(primaryCode);}
        if (Variables.isRectangularStampLetters()) { primaryCode = FontVariants.toRectangularStampLetters(primaryCode);}
        if (Variables.isSmallCaps()) { primaryCode = FontVariants.toSmallCaps(primaryCode);}
        if (Variables.isParentheses()) { primaryCode = FontVariants.toParentheses(primaryCode);}
        if (Variables.isEncircle()) { primaryCode = FontVariants.encircle(primaryCode);}
        if (Variables.isReflected()) { primaryCode = FontVariants.toReflected(primaryCode);}
        if (Variables.isCaps()) { primaryCode = FontVariants.toCaps(primaryCode);}

        char code = (char)primaryCode; // Util.largeIntToChar(primaryCode)
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("caps", false) &&
            Character.isLetter(code) && firstCaps || Character.isLetter(code) && Variables.isShift()) {
            code = Character.toUpperCase(code);
        }
        commitText(String.valueOf(code), 1);

        firstCaps = false;
        setCapsOn(false);

        updateShiftKeyState(getCurrentInputEditorInfo());

        ic.endBatchEdit();
    }

    public void clipboardToBuffer(String text) {
        if (debug) System.out.println(text);
        calcBuffer = text;
        redraw();
    }
    static String calcBuffer = "";
    Stack<String> calcBufferHistory = new Stack<String>();
    int[] calcPasses = new int[] {-101, -22, -12, 10,};
    int[] calcCaptures = new int[] {-200, -201, -202, -203, -204, -205, -206, -207, -208, -209,
                                    -5, -7, -8, -9, -10, -11, 32, 37, 43, 45, 46, 48, 49, 50, 51,
                                    52, 53, 54, 55, 56, 57, 61, 94, 215, 247};
    int[] calcOperators = new int[] {43, 45, 215, 37, 247, 94, 61};
    private void handleCalc(int primaryCode) {
        InputConnection ic = getCurrentInputConnection();
        String sanitized = "", scriptResult = "", parserResult = "";
        switch(primaryCode) {
            case -200: commitText(calcBuffer); break;
            case -209:
                calcBuffer = calcBufferHistory.isEmpty() ? "" : calcBufferHistory.pop();
            break;
            case -205: clipboardToBuffer(getSelectedText(ic)); calcBufferHistory.push(calcBuffer); break;
            case -201: calcBuffer = ""; break;
            case -5:
                if (calcBuffer.length() > 0) {
                    calcBuffer = calcBuffer.substring(0, calcBuffer.length() - 1);
                }
                calcBufferHistory.push(calcBuffer);
            break;
            case -204:
                try {
                    sanitized = Calculator.sanitize(calcBuffer);
                    double result = Calculator.evalParser(sanitized);
                    if (Calculator.checkInteger(result)) {
                        int resultInt = (int)result;
                        parserResult = String.valueOf(resultInt);
                    }
                    else {
                        parserResult = String.valueOf(result);
                    }
                }
                catch (Exception e) {
                    sendDataToErrorOutput(parserResult+"\n"+e);
                }
                calcBuffer = parserResult;
                calcBufferHistory.push(calcBuffer);
            break;
            case -206:
                sanitized = Calculator.sanitize(calcBuffer);
                try {
                    scriptResult = Calculator.evalScript(sanitized);
                }
                catch (Exception e) {
                    sendDataToErrorOutput(scriptResult+"\n"+e);
                }
                calcBuffer = scriptResult;
                calcBufferHistory.push(calcBuffer);
            break;
            case -207:
                Expression e = new Expression(Calculator.sanitize(calcBuffer));
                double result = e.calculate();
                if (Calculator.checkInteger(result)) {
                    int resultInt = (int)result;
                    calcBuffer = String.valueOf(resultInt);
                }
                else {
                    calcBuffer = String.valueOf(result);
                }
                calcBufferHistory.push(calcBuffer);
            break;
            case -2:
                calcBuffer += " ";
                calcBufferHistory.push(calcBuffer);
            break;
            default:
                if (Util.contains(calcOperators, primaryCode)) calcBuffer += " ";
                calcBuffer += (char)primaryCode;
                if (Util.contains(calcOperators, primaryCode)) calcBuffer += " ";
                calcBufferHistory.push(calcBuffer);
            break;
        }
        // calcBufferHistory.push(calcBuffer);
        if (debug) System.out.println(calcBufferHistory);
        getKey(-200).label = calcBuffer;
        redraw();
    }

    static String hexBuffer = "";
    int[] hexPasses = new int[] {
        -175, -101, -23, -22, -20, -21, -13, -14, -15, -16, -12, -11, -10, -9, -7, -5, -8, 10, -2,
        -126, -127, -128, -129, -130, -131

    };
    int[] hexCaptures = new int[] {
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
        97, 98, 99, 100, 101, 102,
        -201, -202, -203, -204, -205, -206
    };
    private void handleUnicode(int primaryCode) {
        InputConnection ic = getCurrentInputConnection();
        String paddedHexBuffer = Util.padLeft(hexBuffer, 4, "0");
        if (primaryCode == -175) {
            showUnicodePopup();
        }

        if (primaryCode == -201) {
            performReplace(Util.convertFromUnicodeToNumber(getSelectedText(ic)));
            return;
        }
        if (primaryCode == -202) {
            performReplace(Util.convertFromNumberToUnicode(getSelectedText(ic)));
            return;
        }
        if (primaryCode == -203) {
            commitText(paddedHexBuffer);
            return;
        }
        if (primaryCode == -204) {
            commitText(String.valueOf((char)(int)Integer.decode("0x" + paddedHexBuffer)));
            return;
        }
        if (primaryCode == -205) {
            if (hexBuffer.length() > 0) hexBuffer = hexBuffer.substring(0, hexBuffer.length() - 1);
            else hexBuffer = "0000";
            paddedHexBuffer = Util.padLeft(hexBuffer, 4, "0");
            getKey(-203).label = hexBuffer.equals("0000") ? "" : paddedHexBuffer;
            getKey(-204).label = String.valueOf((char)(int)Integer.decode("0x" + paddedHexBuffer));
            redraw();
            return;
        }
        if (primaryCode == -206) {
            hexBuffer = "0000";
            paddedHexBuffer = Util.padLeft(hexBuffer, 4, "0");
            getKey(-203).label = hexBuffer.equals("0000") ? "" : paddedHexBuffer;
            getKey(-204).label = String.valueOf((char)(int)Integer.decode("0x" + paddedHexBuffer));
            redraw();
            return;
        }

        if (Util.contains(hexCaptures, primaryCode)) {
            if (hexBuffer.length() > 3) hexBuffer = "";
            hexBuffer += (char)primaryCode;
        }
        paddedHexBuffer = Util.padLeft(hexBuffer, 4, "0");
        getKey(-203).label = hexBuffer.equals("0000") ? "" : paddedHexBuffer;
        getKey(-204).label = String.valueOf((char)(int)Integer.decode("0x" + paddedHexBuffer));
        redraw();
    }

    public void handleShift() {
        InputConnection ic = getCurrentInputConnection();
        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0 &&
            PreferenceManager.getDefaultSharedPreferences(this).getBoolean("shift", false)) {
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
        }
        else {
            if (shiftPressed + 200 > System.currentTimeMillis()) {
                Variables.setShiftOn();
                setCapsOn(true);
            }
            else {
                if (Variables.isShift()) {
                    Variables.setShiftOff();
                    firstCaps = false;
                }
                else {
                    firstCaps = !firstCaps;
                }
                setCapsOn(firstCaps);
                shiftPressed = System.currentTimeMillis();
            }
        }
        redraw();
    }
    public void handleEnter() {
        // handleAction();
        EditorInfo currentEditor = getCurrentInputEditorInfo();
        switch (currentEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
            case EditorInfo.IME_ACTION_GO:
                getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_GO);
            break;
            case EditorInfo.IME_ACTION_SEARCH:
                getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_SEARCH);
            break;
            case EditorInfo.IME_ACTION_DONE:
            case EditorInfo.IME_ACTION_NEXT:
            case EditorInfo.IME_ACTION_SEND:
            default:
                commitText("\n", 1);
                if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("indent", false)) {
                    commitText(Util.getIndentation(getPrevLine()), 0);
                    return;
                } //  sendKey(66);
            break;
        }
    }

/*
    private void handleAction() {
        EditorInfo curEditor = getCurrentInputEditorInfo();
        switch (curEditor.imeOptions & EditorInfo.IME_MASK_ACTION) {
            case EditorInfo.IME_ACTION_DONE: getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_DONE); break;
            case EditorInfo.IME_ACTION_GO: getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_GO); break;
            case EditorInfo.IME_ACTION_NEXT: getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_NEXT); break;
            case EditorInfo.IME_ACTION_SEARCH: getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_SEARCH); break;
            case EditorInfo.IME_ACTION_SEND: getCurrentInputConnection().performEditorAction(EditorInfo.IME_ACTION_SEND); break;
            default: break;
        }
    }
*/

    String clipboardHistory = new String();

    public void clearClipboardHistory() {
        clipboardHistory = "";
        sharedPreferences.edit().putString("clipboard_history", clipboardHistory).apply();
        redraw();
    }
    public void saveToClipboardHistory() {
        InputConnection ic = getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        clipboardHistory = sharedPreferences.getString("clipboard_history", "");
        ArrayList<String> clipboardHistoryArray = new ArrayList<String>(Util.deserialize(clipboardHistory));

        if (getSelectedText(ic).isEmpty()) return;

        clipboardHistoryArray.add(getSelectedText(ic));

        if (clipboardHistoryArray.size() > 16) {
            clipboardHistoryArray = new ArrayList<String>(clipboardHistoryArray.subList(1, clipboardHistoryArray.size()));
        }

        clipboardHistoryArray.removeAll(Arrays.asList("", null));

        clipboardHistory = Util.serialize(clipboardHistoryArray);
        sharedPreferences.edit().putString("clipboard_history", clipboardHistory).apply();
    }
    public void handleCut() {
        if (!isSelecting()) selectLine();
        saveToClipboardHistory();
        sendKey(KeyEvent.KEYCODE_CUT);
    }
    public void handleCopy() {
        if (!isSelecting()) selectLine();
        saveToClipboardHistory();
        sendKey(KeyEvent.KEYCODE_COPY);
    }
    public void handlePaste() {
        sendKey(KeyEvent.KEYCODE_PASTE);
    }

    public void handleAlt() {
        if (Variables.isAlt()) Variables.setAltOff();
        else Variables.setAltOn();
    }
    public void handleCtrl() {
        if (Variables.isCtrl()) Variables.setCtrlOff();
        else Variables.setCtrlOn();
    }
    public void handleEsc() {
        sendKey(KeyEvent.KEYCODE_ESCAPE);
    }

    public void joinLines() {
        InputConnection ic = getCurrentInputConnection();
        if (!isSelecting()) {
            sendKey(KeyEvent.KEYCODE_MOVE_END);
            commitText(" ");
            sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
            sendKey(KeyEvent.KEYCODE_MOVE_END);
        }
        else {
            performReplace(Util.linebreaksToSpaces(getSelectedText(ic)));
        }
    }

    public void playClick() {
        playClick(0);
    }
    public void playClick(int primaryCode) {
        if (!sharedPreferences.getBoolean("sound", false)) {
            return;
        }
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (primaryCode) {
            case 32: am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);break;
            case -4:
            case 10: am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN); break;
            case -5:
            case -7: am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE); break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD); break;
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        if (debug) System.out.println("onKey: "+primaryCode);
        InputConnection ic = getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int ere, aft;

        if (primaryCode > 0
            && currentKeyboard.title != null
            && currentKeyboard.title.equals("IPA")
            && sharedPreferences.getBoolean("ipa_desc", false)
            && Sounds.getIPA(primaryCode) != -1) {
            toastIt("/"+String.valueOf((char)primaryCode)+"/", Sounds.getDescription(primaryCode));
        }

        if (primaryCode > 0
            && currentKeyboard.title != null
            && currentKeyboard.title.equals("IPA")
            && sharedPreferences.getBoolean("ipa", false)
            && Sounds.getIPA(primaryCode) != -1) {
            Sounds.playIPA(getBaseContext(), primaryCode);
        }
        else {
            playClick(primaryCode);
        }

        if (currentKeyboard.title != null && currentKeyboard.title.equals("Unicode")
            && !Util.contains(hexPasses, primaryCode)) {
            handleUnicode(primaryCode);
            return;
        }
        if (currentKeyboard.title != null && currentKeyboard.title.equals("Calculator")
            && !Util.contains(calcPasses, primaryCode)) {
            handleCalc(primaryCode);
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
            case 10: handleEnter(); break;
            case -1: handleShift(); break;
            case -2: handleSpace(); break;
            case -3: break;
            case -4: break;
            case -5: handleBackspace(); break;
            case -6: break;
            case -7: handleDelete(); break;
            case -8: handleCut(); break;
            case -9: handleCopy(); break;
            case -10: handlePaste(); break;
            case -11: Variables.toggleSelecting(getSelectionStart()); break;
            case -12: selectLine(); break;
            case -13: navigate(KeyEvent.KEYCODE_DPAD_UP); break;
            case -14: navigate(KeyEvent.KEYCODE_DPAD_DOWN); break;
            case -15: navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -16: navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -17: navigate(KeyEvent.KEYCODE_DPAD_CENTER); break;
            case -18: navigate(KeyEvent.KEYCODE_PAGE_UP); break;
            case -19: navigate(KeyEvent.KEYCODE_PAGE_DOWN); break;
            case -20:
                navigate(KeyEvent.KEYCODE_MOVE_HOME);
                if (String.valueOf(ic.getTextBeforeCursor(1, 0)).contains("\n")) {
                    sendKey(KeyEvent.KEYCODE_DPAD_RIGHT, Util.getIndentation(getNextLine()).length());
                }
            break;
            case -21: navigate(KeyEvent.KEYCODE_MOVE_END); break;
            case -22: Intents.showSettings(getBaseContext()); break;
            case -23: showVoiceInput(); break;
            case -24: handleClose(); break;
            case -25: showInputMethodPicker(); break;
            case -26: sendKey(KeyEvent.KEYCODE_SETTINGS); break;
            case -27: showEmoticonPopup(); break;
            case -28: clearAll(); break;
            case -29: goToStart(); break;
            case -30: goToEnd(); break;
            case -31: selectNone(); break;
            case -32: selectPrevWord(); break;
            case -33: selectNextWord(); break;
            case -34: commitText(getNextLine() + "\n" + getPrevLine(), 1); break;
            case -35: commitText(Util.getDateString(sharedPreferences.getString("date_format", "yyyy-MM-dd"))); break;
            case -36: commitText(Util.getTimeString(sharedPreferences.getString("time_format", "HH:mm:ss"))); break;
            case -37: commitText(Util.nowAsLong() + " " + Util.nowAsInt()); break;
            case -38: performReplace(Util.toUpperCase(getSelectedText(ic))); break;
            case -39: performReplace(Util.toTitleCase(getSelectedText(ic))); break;
            case -40: performReplace(Util.toLowerCase(getSelectedText(ic))); break;
            case -41: performReplace(Util.toAlterCase(getSelectedText(ic))); break;
            case -42: performReplace(Util.camelToSnake(getSelectedText(ic))); break;
            case -43: performReplace(Util.snakeToCamel(getSelectedText(ic))); break;
            case -44: performReplace(Util.sortChars(getSelectedText(ic))); break;
            case -45: performReplace(Util.reverseChars(getSelectedText(ic))); break;
            case -46: performReplace(Util.shuffleChars(getSelectedText(ic))); break;
            case -47: performReplace(Util.doubleChars(getSelectedText(ic))); break;
            case -48: performReplace(Util.sortLines(getSelectedText(ic))); break;
            case -49: performReplace(Util.reverseLines(getSelectedText(ic))); break;
            case -50: performReplace(Util.shuffleLines(getSelectedText(ic))); break;
            case -51: performReplace(Util.doubleLines(getSelectedText(ic))); break;
            case -52: performReplace(Util.dashesToSpaces(getSelectedText(ic))); break;
            case -53: performReplace(Util.underscoresToSpaces(getSelectedText(ic))); break;
            case -54: performReplace(Util.spacesToDashes(getSelectedText(ic))); break;
            case -55: performReplace(Util.spacesToUnderscores(getSelectedText(ic))); break;
            case -56: performReplace(Util.spacesToLinebreaks(getSelectedText(ic))); break;
            case -57: performReplace(Util.linebreaksToSpaces(getSelectedText(ic))); break;
            case -58: performReplace(Util.spacesToTabs(getSelectedText(ic))); break;
            case -59: performReplace(Util.tabsToSpaces(getSelectedText(ic))); break;
            case -60: performReplace(Util.splitWithLinebreaks(getSelectedText(ic))); break;
            case -61: performReplace(Util.splitWithSpaces(getSelectedText(ic))); break;
            case -62: performReplace(Util.removeSpaces(getSelectedText(ic))); break;
            case -63: performReplace(Util.reduceSpaces(getSelectedText(ic))); break;
            case -64:
                if (!isSelecting()) selectLine();
                performReplace(Util.decreaseIndentation(getSelectedText(ic), indentString));
            break;
            case -65:
                if (!isSelecting()) selectLine();
                performReplace(Util.increaseIndentation(getSelectedText(ic), indentString));
            break;
            case -66: performReplace(Util.trimEndingWhitespace(getSelectedText(ic))); break;
            case -67: performReplace(Util.trimTrailingWhitespace(getSelectedText(ic))); break;
            case -68: performReplace(Util.normalize(getSelectedText(ic))); break;
            case -69: performReplace(Util.slug(getSelectedText(ic))); break;
            case -70: joinLines(); break;
            case -71:
                ere = Util.countChars(getSelectedText(ic));
                performReplace(Util.uniqueChars(getSelectedText(ic)));
                aft = Util.countChars(getSelectedText(ic));
                toastIt(ere + " → " + aft);
            break;
            case -72:
                ere = Util.countLines(getSelectedText(ic));
                performReplace(Util.uniqueLines(getSelectedText(ic)));
                aft = Util.countLines(getSelectedText(ic));
                toastIt(ere + " → " + aft);
            break;
            case -73: commitText(Util.timemoji()); break;
            case -74: performContextMenuAction(16908338); break; // undo
            case -75: performContextMenuAction(16908339); break; // redo
            case -76: performContextMenuAction(16908337); break; // pasteAsPlainText,
            case -77: performContextMenuAction(16908323); break; // copyUrl
            case -78: performContextMenuAction(16908355); break; // autofill
            case -79: performContextMenuAction(16908330); break; // addToDictionary
            case -80: performContextMenuAction(16908320); break; // cut
            case -81: performContextMenuAction(16908321); break; // copy
            case -82: performContextMenuAction(16908322); break; // paste
            case -83: performContextMenuAction(16908319); break; // selectAll
            case -84: performContextMenuAction(16908324); break; // switchInputMethod
            case -85: performContextMenuAction(16908328); break; // startSelectingText
            case -86: performContextMenuAction(16908329); break; // stopSelectingText
            case -87: performContextMenuAction(16908326); break; // keyboardView
            case -88: performContextMenuAction(16908333); break; // selectTextMode
            case -89: performContextMenuAction(16908327); break; // closeButton
            case -90: performContextMenuAction(16908316); break; // extractArea
            case -91: performContextMenuAction(16908317); break; // candidatesArea
            case -92:
                String text = getSelectedText(ic);
                toastIt("Chars: " + Util.countChars(text) + "\nWords: " + Util.countWords(text) + "\nLines: " + Util.countLines(text));
            break;
            case -93: toastIt(Util.unidata(getSelectedText(ic))); break;
            case -94:
                if (Variables.isBold()) performReplace(FontVariants.unbolden(getSelectedText(ic)));
                else performReplace(FontVariants.bolden(getSelectedText(ic)));
                Variables.toggleBold();
            break;
            case -95:
                if (Variables.isItalic()) performReplace(FontVariants.unitalicize(getSelectedText(ic)));
                else performReplace(FontVariants.italicize(getSelectedText(ic)));
                Variables.toggleItalic();
            break;
            case -96:
                if (Variables.isEmphasized()) performReplace(FontVariants.unemphasize(getSelectedText(ic)));
                else performReplace(FontVariants.emphasize(getSelectedText(ic)));
                Variables.toggleEmphasized();
            break;
            case -97:
                if (getSelectionLength() == 0) Variables.toggleUnderlined();
                else performReplace(FontVariants.underline(getSelectedText(ic)));
            break;
            case -98:
                if (getSelectionLength() == 0) Variables.toggleUnderscored();
                else performReplace(FontVariants.underscore(getSelectedText(ic)));
            break;
            case -99:
                if (getSelectionLength() == 0) Variables.toggleStrikethrough();
                else performReplace(FontVariants.strikethrough(getSelectedText(ic)));
            break;
            case -100:
                Variables.setAllOff();
                performReplace(FontVariants.unbolden(getSelectedText(ic)));
                performReplace(FontVariants.unitalicize(getSelectedText(ic)));
                performReplace(FontVariants.unemphasize(getSelectedText(ic)));
                performReplace(FontVariants.unstrikethrough(getSelectedText(ic)));
                performReplace(FontVariants.ununderline(getSelectedText(ic)));
                performReplace(FontVariants.ununderscore(getSelectedText(ic)));
            break;
            case -101: setKeyboard(R.layout.primary, "Primary"); break;
            case -102: setKeyboard(R.layout.menu, "Menu"); break;
            case -103: setKeyboard(R.layout.macros, "Macros"); break;
            case -104: Intents.showActivity(getBaseContext(), Settings.ACTION_HARD_KEYBOARD_SETTINGS); break;
            case -105: Intents.showActivity(getBaseContext(), Settings.ACTION_LOCALE_SETTINGS); break;
            case -106: Intents.showActivity(getBaseContext(), Settings.ACTION_SETTINGS); break;
            case -107: Intents.showActivity(getBaseContext(), Settings.ACTION_USER_DICTIONARY_SETTINGS); break;
            case -108: Intents.showActivity(getBaseContext(), Settings.ACTION_WIFI_SETTINGS); break;
            case -109: Intents.showActivity(getBaseContext(), Settings.ACTION_WIRELESS_SETTINGS); break;
            case -110: Intents.showActivity(getBaseContext(), Settings.ACTION_VOICE_INPUT_SETTINGS); break;
            case -111: Intents.showActivity(getBaseContext(), Settings.ACTION_USAGE_ACCESS_SETTINGS); break;
            case -112: Intents.showActivity(getBaseContext(), Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE); break; // handleEsc();
            case -113: Intents.showActivity(getBaseContext(), Settings.ACTION_HOME_SETTINGS); break; // handleCtrl();
            case -114: Intents.showActivity(getBaseContext(), Settings.ACTION_INPUT_METHOD_SETTINGS); break; // handleAlt();
            case -115: Intents.showActivity(getBaseContext(), Settings.ACTION_AIRPLANE_MODE_SETTINGS); break; // handleTab();
            case -116: Intents.showActivity(getBaseContext(), Settings.ACTION_SOUND_SETTINGS);   break;
            case -117: Intents.showActivity(getBaseContext(), Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);   break;
            case -118: Intents.showActivity(getBaseContext(), Settings.ACTION_BLUETOOTH_SETTINGS); break;
            case -119: Intents.showActivity(getBaseContext(), Settings.ACTION_CAPTIONING_SETTINGS); break;
            case -120: Intents.showActivity(getBaseContext(), Settings.ACTION_DEVICE_INFO_SETTINGS); break;
            case -121: Intents.showClipboard(getBaseContext()); break;
            case -122: break;
            case -123: break;
            case -124: break;
            case -125: break;
            case -126: performReplace(Util.convertNumberBase(getSelectedText(ic), 2, 10)); break;
            case -127: performReplace(Util.convertNumberBase(getSelectedText(ic), 10, 2)); break;
            case -128: performReplace(Util.convertNumberBase(getSelectedText(ic), 8, 10)); break;
            case -129: performReplace(Util.convertNumberBase(getSelectedText(ic), 10, 8)); break;
            case -130: performReplace(Util.convertNumberBase(getSelectedText(ic), 16, 10)); break;
            case -131: performReplace(Util.convertNumberBase(getSelectedText(ic), 10, 16)); break;
            case -132: break;
            case -133: setKeyboard(R.layout.domain, "Domain"); break;
            case -134: setKeyboard(R.layout.numeric, "Numeric"); break;
            case -135: setKeyboard(R.layout.navigation, "Navigation"); break;
            case -136: setKeyboard(R.layout.fonts, "Fonts"); break;
            case -137: setKeyboard(R.layout.ipa, "IPA"); break;
            case -138: setKeyboard(R.layout.symbol, "Symbol"); break;
            case -139: setKeyboard(R.layout.unicode, "Unicode"); break;
            case -140: setKeyboard(R.layout.accents, "Accents"); break;
            case -141:
                String customKeys = sharedPreferences.getString("custom_keys", "");
                if (customKeys != "") {
                    Keyboard customKeyboard = new Keyboard(this, R.layout.custom, customKeys, 10, 0);
                    kv.setKeyboard(customKeyboard);
                }
            break;
            case -142: setKeyboard(R.layout.function, "Function"); break;
            case -143: setKeyboard(R.layout.calc, "Calc"); break;
            case -144: setKeyboard(R.layout.clipboard, "Clipboard"); break;
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
            case -163: performReplace(Util.replaceNbsp(getSelectedText(ic))); break;
            case -164: navigate(KeyEvent.KEYCODE_DPAD_UP,   KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -165: navigate(KeyEvent.KEYCODE_DPAD_UP,   KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -166: navigate(KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -167: navigate(KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -168: break;
            case -169: break;
            case -170:
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleHtmlComment(getSelectedText(ic)));
            break;
            case -171:
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleJavaComment(getSelectedText(ic)));
            break;
            case -172:
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleLineComment(getSelectedText(ic)));
            break;
            case -173: displayFindMenu(); break;
            case -174: setKeyboard(R.layout.coding, "Coding"); break;
            case -175: showUnicodePopup(); break;
            case -176: moveLeftOneWord(); break;
            case -177: moveRightOneWord(); break;
            case -178: Intents.dialPhone(getBaseContext(), getSelectedText(ic)); break;
            case -179: Intents.openWebpage(getBaseContext(), getSelectedText(ic)); break;
            case -180: Intents.searchWeb(getBaseContext(), getSelectedText(ic)); break;
            case -181: Intents.showLocationFromAddress(getBaseContext(), getSelectedText(ic)); break;
            case -182: break;
            case -183: break;
            case -184: break;
            case -185: break;
            case -186: break;
            case -187: break;
            case -188: break;
            case -189: break;
            case -190: break;
            case -191: break;
            case -192: break;
            case -193: break;
            case -194: break;
            case -195: break;
            case -196: break;
            case -197: break;
            case -198: break;
            case -199: break;

            /*
            case -200: break;
            case -201: break;
            case -202: break;
            case -203: break;
            case -204: break;
            case -205: break;
            case -206: break;
            case -207: break;
            case -208: break;
            case -209: break;
            */


            // case -299: popupKeyboard(getKey(-299)); break;
            case -300: clearClipboardHistory(); break;

            case -301: commitText(String.valueOf(Util.orNull(getKey(-301).label, ""))); break;
            case -302: commitText(String.valueOf(Util.orNull(getKey(-302).label, ""))); break;
            case -303: commitText(String.valueOf(Util.orNull(getKey(-303).label, ""))); break;
            case -304: commitText(String.valueOf(Util.orNull(getKey(-304).label, ""))); break;
            case -305: commitText(String.valueOf(Util.orNull(getKey(-305).label, ""))); break;
            case -306: commitText(String.valueOf(Util.orNull(getKey(-306).label, ""))); break;
            case -307: commitText(String.valueOf(Util.orNull(getKey(-307).label, ""))); break;
            case -308: commitText(String.valueOf(Util.orNull(getKey(-308).label, ""))); break;
            case -309: commitText(String.valueOf(Util.orNull(getKey(-309).label, ""))); break;
            case -310: commitText(String.valueOf(Util.orNull(getKey(-310).label, ""))); break;
            case -311: commitText(String.valueOf(Util.orNull(getKey(-311).label, ""))); break;
            case -312: commitText(String.valueOf(Util.orNull(getKey(-312).label, ""))); break;
            case -313: commitText(String.valueOf(Util.orNull(getKey(-313).label, ""))); break;
            case -314: commitText(String.valueOf(Util.orNull(getKey(-314).label, ""))); break;
            case -315: commitText(String.valueOf(Util.orNull(getKey(-315).label, ""))); break;
            case -316: commitText(String.valueOf(Util.orNull(getKey(-316).label, ""))); break;

            case -501: commitText(sharedPreferences.getString("k1", getResources().getString(R.string.k1))); break;
            case -502: commitText(sharedPreferences.getString("k2", getResources().getString(R.string.k2))); break;
            case -503: commitText(sharedPreferences.getString("k3", getResources().getString(R.string.k3))); break;
            case -504: commitText(sharedPreferences.getString("k4", getResources().getString(R.string.k4))); break;
            case -505: commitText(sharedPreferences.getString("k5", getResources().getString(R.string.k5))); break;
            case -506: commitText(sharedPreferences.getString("k6", getResources().getString(R.string.k6))); break;
            case -507: commitText(sharedPreferences.getString("k7", getResources().getString(R.string.k7))); break;
            case -508: commitText(sharedPreferences.getString("k8", getResources().getString(R.string.k8))); break;
            case -509: commitText(sharedPreferences.getString("name", getResources().getString(R.string.name))); break;
            case -510: commitText(sharedPreferences.getString("email", getResources().getString(R.string.email))); break;
            case -511: commitText(sharedPreferences.getString("phone", getResources().getString(R.string.phone))); break;
            case -512: commitText(sharedPreferences.getString("address", getResources().getString(R.string.address))); break;
            case -513: commitText(sharedPreferences.getString("password", getResources().getString(R.string.password))); break;

            default:
                if (Variables.isAnyOn()) processKeyCombo(primaryCode);
                else handleCharacter(primaryCode, keyCodes);
            break;
        }
        redraw();

        if (sharedPreferences.getBoolean("caps", false)) {
            if (ic.getTextBeforeCursor(2, 0).toString().contains(". ")
             || ic.getTextBeforeCursor(2, 0).toString().contains("? ")
             || ic.getTextBeforeCursor(2, 0).toString().contains("! ")
            ) {
                setCapsOn(true);
                firstCaps = true;
            }
        }
    }

    public void popupKeyboard(Keyboard.Key nextTo) {
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.tld, new FrameLayout(getBaseContext()));
        // PopupWindow popup = new PopupWindow(getBaseContext());
        final PopupWindow popup = new PopupWindow(
            // LayoutInflater.from(getBaseContext()).inflate(R.layout.tld, new FrameLayout(getBaseContext())),
            view,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        );
        // popup.setContentView(view);
        // popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.showAtLocation(kv, Gravity.NO_GRAVITY, nextTo.x, nextTo.y);

        // popup.setOutsideTouchable(true);
        // popup.setFocusable(true);
        // popup.setBackgroundDrawable(new BitmapDrawable());
        // popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // popup.setBackgroundDrawable(getResources().getDrawable(R.color.background));

        popup.getContentView().findViewById(R.id.closeButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popup.dismiss();
                return false;
            }
        });
        ViewGroup vg = (ViewGroup)popup.getContentView().findViewById(R.id.tldPopup);
        for(int i = 0; i < vg.getChildCount()-1; i++) {
            final TextView tv = (TextView)vg.getChildAt(i);
            tv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    commitText(tv.getText().toString());
                    return false;
                }
            });
        }


    }

    public void displayFindMenu() {
        final InputConnection ic = getCurrentInputConnection();
        final CustomInputConnection cic = new CustomInputConnection(ic, false);
        // if (getSelectionLength() == 0) selectAll();

        String val = "";
        if (getSelectionLength() == 0) {
            val = Util.orNull(String.valueOf(ic.getTextBeforeCursor(MAX, 0)), "")
                + Util.orNull(String.valueOf(ic.getTextAfterCursor(MAX, 0)), "");
        }
        else {
            val = (String)ic.getSelectedText(0);
        }

        LayoutInflater li = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (li != null) {

            View wordbar = li.inflate(R.layout.wordbar, null);


            ConstraintLayout ll = (ConstraintLayout)wordbar.findViewById(R.id.wordsLayout);
            final EditText findEditText = (EditText)wordbar.findViewById(R.id.find);
            final EditText replEditText = (EditText)wordbar.findViewById(R.id.repl);
            final TextView go = (TextView)wordbar.findViewById(R.id.go);
            final TextView close = (TextView)wordbar.findViewById(R.id.close);

            popupWindow = new PopupWindow(wordbar, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.black));
            // popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            // popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            // popupWindow.setFocusable(true);
            // popupWindow.setTouchable(true);
            // popupWindow.setOutsideTouchable(false);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });
            // popupWindow.showAsDropDown(kv);
            popupWindow.showAtLocation(kv.getRootView(), Gravity.TOP, 0, -getKey(32).height);

            final String finalVal = val;
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // System.out.println("TextView: "+view);
                    // System.out.println("ConstraintLayout: "+view.getParent());
                    // System.out.println("PopupWindow: "+view.getParent().getParent());
                    // System.out.println("ViewRootImpl: "+view.getParent().getParent().getParent().;

                    String subject = finalVal;
                    String find = findEditText.getText().toString();
                    String repl = replEditText.getText().toString();
                    String result = subject.replace(find, repl);

                    performReplace(result);
                    cic.commitText(result, 1); // performReplace(result);
                    cic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, EditorInfo.IME_ACTION_SEND, 0, 0));

                    HashMap<String, String> cursorData = new HashMap<>();
                    cursorData.put("oldText", subject);
                    cursorData.put("newText", result);
                    sendMessageToActivity("FindReplace", cursorData);

                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (popupWindow.isShowing()) popupWindow.dismiss();
                }
            });
        }
    }

    private void sendMessageToActivity(String destination, HashMap<String, String> data) {
        Intent intent = new Intent(destination);
        for (Map.Entry<String,String> datum : data.entrySet()) {
            intent.putExtra(datum.getKey(), datum.getValue()); // data.getOrDefault("", "")
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public void showEmoticonPopup() {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (layoutInflater != null) {
            View popupView = layoutInflater.inflate(R.layout.emoticon_listview, null);
            setCandidatesViewShown(false); // setCandidatesViewShown(popupWindow != null);

            boolean wasCandOn = sharedPreferences.getBoolean("pred", false);

            emoticonPopup = new EmoticonPopup(popupView, this);
            emoticonPopup.setSizeForSoftKeyboard();

            emoticonPopup.setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            emoticonPopup.setHeight(kv.getHeight());
            emoticonPopup.showAtLocation(kv.getRootView(), Gravity.BOTTOM, 0, 0);

            emoticonPopup.rootView.setHorizontalFadingEdgeEnabled(true);
            emoticonPopup.rootView.setHorizontalScrollBarEnabled(false);
            emoticonPopup.rootView.setVerticalScrollBarEnabled(false);

            emoticonPopup.setOnSoftKeyboardOpenCloseListener(new EmoticonPopup.OnSoftKeyboardOpenCloseListener() {
                @Override
                public void onKeyboardOpen(int keyboardHeight) {
                    playClick();
                }

                @Override
                public void onKeyboardClose() {
                    closeEmoticons();
                    playClick();
                }
            });
            emoticonPopup.setOnEmoticonCloseClickedListener(new EmoticonPopup.OnEmoticonCloseClickedListener() {
                @Override
                public void onEmoticonCloseClicked(View v) {
                    playClick();
                }
            });
            emoticonPopup.setOnEmoticonTabClickedListener(new EmoticonPopup.OnEmoticonTabClickedListener() {
                @Override
                public void onEmoticonTabClicked(View v) {
                    playClick();
                }
            });
            emoticonPopup.setOnEmoticonClickedListener(new EmoticonGridView.OnEmoticonClickedListener() {
                @Override
                public void onEmoticonClicked(Emoticon emoticon) {
                    playClick();
                    toastIt(emoticon.getEmoticon()+" "+Util.unidata(emoticon.getEmoticon()));
                    commitText(emoticon.getEmoticon());
                }
            });
            emoticonPopup.setOnEmoticonLongClickedListener(new EmoticonGridView.OnEmoticonLongClickedListener() {
                @Override
                public void onEmoticonLongClicked(Emoticon emoticon) {
                    playClick();
                    int tab = emoticonPopup.getCurrentTab();
                    String emoticonFavorites = new String(sharedPreferences.getString("emoticon_favorites", ""));
                    if (tab == 0) {
                        emoticonPopup.removeRecentEmoticon(getBaseContext(), emoticon);
                        toastIt(emoticon.getEmoticon()+" removed from emoticon recents");
                    }
                    else if (tab == 10) {
                        StringBuilder sb = new StringBuilder(new String(emoticonFavorites));
                        if (emoticonFavorites.indexOf(emoticon.getEmoticon()) > -1) {
                            sb.deleteCharAt(emoticonFavorites.indexOf(emoticon.getEmoticon()));
                        }
                        emoticonFavorites = sb.toString();
                        sharedPreferences.edit().putString("emoticon_favorites", emoticonFavorites).apply();
                        toastIt(emoticon.getEmoticon()+" removed from emoticon favorites");
                    }
                    else {
                        StringBuilder sb = new StringBuilder(new String(emoticonFavorites));
                        if (emoticonFavorites.indexOf(emoticon.getEmoticon()) > -1) {
                            sb.deleteCharAt(emoticonFavorites.indexOf(emoticon.getEmoticon()));
                        }
                        sb.append(emoticon.getEmoticon());
                        emoticonFavorites = new String(sb.toString());
                        sharedPreferences.edit().putString("emoticon_favorites", new String(emoticonFavorites)).apply();
                        toastIt(emoticon.getEmoticon()+" added to emoticon favorites");
                    }
                }
            });
            emoticonPopup.setOnEmoticonBackspaceClickedListener(new EmoticonPopup.OnEmoticonBackspaceClickedListener() {
                @Override
                public void onEmoticonBackspaceClicked(View v) {
                    playClick(-7);
                    handleBackspace();
                }
            });
        }
    }

    public void showUnicodePopup() {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            View popupView = layoutInflater.inflate(R.layout.unicode_list_view, null);

            unicodePopup = new UnicodePopup(popupView, this);

            unicodePopup.setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            unicodePopup.setHeight(kv.getHeight());
            unicodePopup.showAtLocation(kv.getRootView(), Gravity.BOTTOM, 0, 0);

            unicodePopup.rootView.setHorizontalFadingEdgeEnabled(true);
            unicodePopup.rootView.setHorizontalScrollBarEnabled(false);
            unicodePopup.rootView.setVerticalScrollBarEnabled(false);

            unicodePopup.setOnSoftKeyboardOpenCloseListener(new UnicodePopup.OnSoftKeyboardOpenCloseListener() {
                @Override
                public void onKeyboardOpen(int keyboardHeight) {
                    playClick();
                }

                @Override
                public void onKeyboardClose() {
                    closeUnicode();
                    playClick();
                }
            });
            unicodePopup.setOnUnicodeCloseClickedListener(new UnicodePopup.OnUnicodeCloseClickedListener() {
                @Override
                public void onUnicodeCloseClicked(View v) {
                    playClick();
                }
            });
            unicodePopup.setOnUnicodeTabClickedListener(new UnicodePopup.OnUnicodeTabClickedListener() {
                @Override
                public void onUnicodeTabClicked(View v) {
                    playClick();
                }
            });
            unicodePopup.setOnUnicodeClickedListener(new UnicodeGridView.OnUnicodeClickedListener() {
                @Override
                public void onUnicodeClicked(Unicode unicode) {
                    int tab = unicodePopup.getCurrentTab();
                    if (tab == 3) {
                        unicodePopup.setPage(0);
                        unicodePopup.scrollTo(unicode.codePoint/*+(16*6)*/, true);
                        return;
                    }
                    playClick();
                    toastIt(unicode.getUnicode()+" "+Util.unidata(unicode.getUnicode()));
                    commitText(unicode.getUnicode());
                    if (debug) System.out.println("recents: "+sharedPreferences.getString("unicode_recents", ""));
                    if (debug) System.out.println("favorites: "+sharedPreferences.getString("unicode_favorites", ""));
                }
            });
            unicodePopup.setOnUnicodeLongClickedListener(new UnicodeGridView.OnUnicodeLongClickedListener() {
                @Override
                public void onUnicodeLongClicked(Unicode unicode) {
                    playClick();
                    int tab = unicodePopup.getCurrentTab();
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String unicodeFavorites = sharedPreferences.getString("unicode_favorites", "");
                    if (tab == 0) {
                        StringBuilder sb = new StringBuilder(unicodeFavorites);
                        if (unicodeFavorites.indexOf(unicode.getUnicode()) > -1) {
                            sb.deleteCharAt(unicodeFavorites.indexOf(unicode.getUnicode()));
                        }
                        sb.append(unicode.getUnicode());
                        unicodeFavorites = sb.toString();
                        sharedPreferences.edit().putString("unicode_favorites", unicodeFavorites).apply();
                        toastIt(unicode.getUnicode()+" added to unicode favorites");
                    }
                    if (tab == 1) {
                        unicodePopup.removeRecentUnicode(getBaseContext(), unicode);
                        toastIt(unicode.getUnicode()+" removed from unicode recents");
                    }
                    if (tab == 2) {
                        StringBuilder sb = new StringBuilder(unicodeFavorites);
                        if (unicodeFavorites.indexOf(unicode.getUnicode()) > -1) {
                            sb.deleteCharAt(unicodeFavorites.indexOf(unicode.getUnicode()));
                        }
                        unicodeFavorites = sb.toString();
                        sharedPreferences.edit().putString("unicode_favorites", unicodeFavorites).apply();
                        toastIt(unicode.getUnicode()+" removed from favorites");
                    }
                    if (debug) System.out.println("recents: "+sharedPreferences.getString("unicode_recents", ""));
                    if (debug) System.out.println("favorites: "+sharedPreferences.getString("unicode_favorites", ""));
                }
            });
            unicodePopup.setOnUnicodeBackspaceClickedListener(new UnicodePopup.OnUnicodeBackspaceClickedListener() {
                @Override
                public void onUnicodeBackspaceClicked(View v) {
                    playClick(-7);
                    handleBackspace();
                }
            });
        }
    }

    public void closeEmoticons() {
        playClick();
        if (emoticonPopup != null) emoticonPopup.dismiss();
    }

    public void closeUnicode() {
        playClick();
        if (unicodePopup != null) unicodePopup.dismiss();
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
        return ((double)PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("height", 100)) / 100;
    }

    public void showMacroDialog(int primaryCode) {
        // System.out.println("showMacroDialog");

        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            Intents.startIntent(getBaseContext(), intent);
            return;
        }

        WindowManager windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.wordbar, null);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
            PixelFormat.OPAQUE);

        params.gravity = Gravity.TOP | Gravity.CENTER;
        // params.x = 0;
        // params.y = 0;
        windowManager.addView(view, params);

        /*
        EditText editText = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);

        AlertDialog alert = new AlertDialog.Builder(this)
            .setTitle("Title")
            .setMessage("Message")
            .setView(editText)
            .setIcon(R.drawable.ic_macro)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String string = editText.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com/"));
                    startActivity(intent);
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        */
        /*
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                alert.show();
            }
        });
        */
        /*
        new Handler().postDelayed(new Runnable() {
            public void run() {
                alert.show();
            }
        }, 100);
        */
    }

/*
    @Nullable
    @Override
    public InlineSuggestionsRequest onCreateInlineSuggestionsRequest(@NonNull Bundle uiExtras) {
        if (debug) System.out.println("onCreateInlineSuggestionsRequest: "+uiExtras);
        return super.onCreateInlineSuggestionsRequest(uiExtras);
    }

    @Override
    public AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface() {
        if (debug) System.out.println("onCreateInputMethodSessionInterface");
        return super.onCreateInputMethodSessionInterface();
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        if (debug) System.out.println("onFinishInputView: "+finishingInput);
        super.onFinishInputView(finishingInput);
    }

    @Override
    public void onStartCandidatesView(EditorInfo info, boolean restarting) {
        if (debug) System.out.println("onStartCandidatesView: "+info+" "+restarting);
        super.onStartCandidatesView(info, restarting);
    }

    @Override
    public boolean onShowInputRequested(int flags, boolean configChange) {
        if (debug) System.out.println("onShowInputRequested: "+flags+" "+configChange);
        return super.onShowInputRequested(flags, configChange);
    }

    @Override
    public void onViewClicked(boolean focusChanged) {
        if (debug) System.out.println("onViewClicked: "+focusChanged);
        super.onViewClicked(focusChanged);
    }

    @Override
    public void onExtractingInputChanged(EditorInfo ei) {
        if (debug) System.out.println("onExtractingInputChanged: "+ei);
        super.onExtractingInputChanged(ei);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (debug) System.out.println("onKey: "+keyCode+" "+event);
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        if (debug) System.out.println("onKeyMultiple: "+keyCode+" "+count+" "+event);
        return super.onKeyMultiple(keyCode, count, event);
    }
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (debug) System.out.println("onKeyLongPress"+" "+keyCode+" "+event);
        return super.onKeyLongPress(keyCode, event);
    }
    @Override
    public boolean onKeyUp(int primaryCode, KeyEvent event) {
        if (debug) System.out.println("onKeyUp: "+primaryCode+" "+event);
        return super.onKeyUp(primaryCode, event);
    }
    @Override
    public boolean onKeyDown(int primaryCode, KeyEvent event) {
        if (debug) System.out.println("onKeyDown: "+primaryCode+" "+event);
        return super.onKeyDown(primaryCode, event);
    }

    @Override
    public void onWindowShown() {
        if (debug) System.out.println("onWindowShown");
        super.onWindowShown();
    }
    @Override
    public void onWindowHidden() {
        if (debug) System.out.println("onWindowHidden");
        super.onWindowHidden();
    }
    */
}