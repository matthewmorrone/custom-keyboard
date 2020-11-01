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
import android.graphics.Typeface;
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


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.custom.keyboard.emoticon.Emoticon;
import com.custom.keyboard.emoticon.EmoticonGridView;
import com.custom.keyboard.emoticon.EmoticonPopup;

import org.apache.commons.lang3.StringUtils;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
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

    SharedPreferences sharedPreferences;
    Toast toast;

    private short rowNumber = 6;
    private CustomKeyboardView kv;
    private CandidateView mCandidateView;
    private boolean mPredictionOn;
    private InputMethodManager mInputMethodManager;
    private CustomKeyboard currentKeyboard;
    private CustomKeyboard standardKeyboard;

    private int indentWidth;
    private String indentString;

    private SpellChecker spellChecker;
    // private ArrayList<String> suggestions = new ArrayList<>();

    boolean debug = false;

    private PopupWindow popupWindow = null;
    private EmoticonPopup emoticonPopup = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        mWordSeparators = getResources().getString(R.string.word_separators);

        toast = new Toast(getBaseContext());

        spellChecker = new SpellChecker(getApplicationContext(), true);
    }

    @Override
    public void onInitializeInterface() {
        if (standardKeyboard != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) {
                return;
            }
            mLastDisplayWidth = displayWidth;
        }
        standardKeyboard = new CustomKeyboard(this, R.layout.primary);
    }

    @Override
    public View onCreateInputView() {
        kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        kv.setOnKeyboardActionListener(this);
        kv.setKeyboard(standardKeyboard);
        return kv;
    }

    @Override
    public View onCreateCandidatesView() {
        setTheme();
        Paint mPaint = new Paint();
        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        if (mCandidateView == null) {
            mCandidateView = new CandidateView(this);
            mCandidateView.setService(this);
        }
        return mCandidateView;
        // return displayFindMenu();
    }

    // @Override
    // public void onStartInputView(EditorInfo info, boolean restarting) {
    //     ViewGroup originalParent = (ViewGroup)kv.getParent();
    //     if (originalParent != null) {
    //         originalParent.setPadding(0, 0, 0, 0);
    //         kv.setPopupParent(originalParent);
    //     }
    // }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        indentWidth = Integer.valueOf(sharedPreferences.getString("indentWidth", "4"));
        indentString = new String(new char[indentWidth]).replace("\0", " ");

        Paint mPaint = setTheme();

        // mComposing.setLength(0);
        updateCandidates();

        if (!restarting) {
            mMetaState = 0;
        }

        kv = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);


        setInputType();

        int bg = (int)Long.parseLong(Themes.extractBackgroundColor(mDefaultFilter), 16);
        Color background = Color.valueOf(bg);
        float transparency = sharedPreferences.getInt("transparency", 100) / 100f;
        kv.setBackgroundColor(Color.argb(transparency, background.red(), background.green(), background.blue()));

        kv.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);

        currentKeyboard.setRowNumber(getRowNumber());

        kv.setKeyboard(currentKeyboard);

        capsOnFirst();
        kv.setOnKeyboardActionListener(this);

        setInputView(kv);

        kv.getCustomKeyboard().changeKeyHeight(getHeightKeyModifier());

        boolean mPreviewOn = sharedPreferences.getBoolean("preview", false);
        kv.setPreviewEnabled(mPreviewOn);



        mCandidateView = new CandidateView(this);
        mCandidateView.setService(this);
        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        if (isKeyboardVisible()) setCandidatesView(mCandidateView);

        mPredictionOn = sharedPreferences.getBoolean("pred", false);
        if (mPredictionOn) setCandidatesViewShown(isKeyboardVisible());

        // LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        // mCandidateView.setLayoutParams(layoutParams);
    }

    @Override
    public void onComputeInsets(InputMethodService.Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }
    }

    @Override
    public void onWindowShown() {
        super.onWindowShown();
    }

    @Override
    public void onWindowHidden() {
        super.onWindowHidden();
        // netCandidatesViewShown(false);
    }

    public boolean isKeyboardVisible() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();
    }

    // @Override
    // public void onWindowShown() {
    //     super.onWindowShown();
    // }
    //
    // @Override
    // public void onWindowHidden() {
    //     super.onWindowHidden();
    //     setCandidatesViewShown(false);
    // }



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
            // if (entry.getValue().size() > 8) {}
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
        InputConnection ic = getCurrentInputConnection();
        // sendKey(KeyEvent.KEYCODE_CLEAR);
        ic = getCurrentInputConnection();
        ic.deleteSurroundingText(MAX, MAX);
    }

    public String getText(@NonNull InputConnection ic) {
        CharSequence text = ic.getSelectedText(0);
        if (text == null) return "";
        return (String) text;
    }

    public String getAllText(@NonNull InputConnection ic) {
        return Util.orNull(ic.getTextBeforeCursor(MAX, 0), "").toString() + Util.orNull(ic.getTextAfterCursor(MAX, 0), "").toString();
    }

    public void sendCustomKey(String key) {
        InputConnection ic = getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext()); // this?
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        int cursorLocation = getSelectionStart();
        String ins = sharedPreferences.getString(key, "");
        ic.beginBatchEdit();
        commitText(ins, cursorLocation + (ins != null ? ins.length() : 0));
        ic.endBatchEdit();
    }

    public String getCurrentWord() {
        InputConnection ic = getCurrentInputConnection();
/*
        int start = getCursorPosition()-getPrevWord().length();
        int ender = getCursorPosition()+getNextWord().length();

        ic.requestCursorUpdates(0);
        ic.setComposingRegion(start, ender);
        ic.requestCursorUpdates(3);
*/

/*
        String[] prevWords = ic.getTextBeforeCursor(MAX, 0).toString().split("\\b");
        String[] nextWords = ic.getTextBeforeCursor(MAX, 0).toString().split("\\b");
        String prevWord = prevWords[prevWords.length-1];
        String nextWord = nextWords[0];

        if (prevWord.equals(" ")) return nextWord;
        if (nextWord.equals(" ")) return prevWord;
*/
        // @TODO: if at word boundary, should return the word it's actually touching:
        // "one| two" should return one, "one |two" should return two
        return getPrevWord()+getNextWord();
    }

    public String getPrevWord() {
        InputConnection ic = getCurrentInputConnection();
        try {
            String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
            // System.out.println(Arrays.toString(words));
            if (words.length < 1) return "";
            String lastWord = words[words.length - 1];
            return lastWord;
        }
        catch (Exception e) {
            return "";
        }
        // return "";
    }

    public String getNextWord() {
        InputConnection ic = getCurrentInputConnection();
        try {
            String[] words = ic.getTextAfterCursor(MAX, 0).toString().split(" ");
            words = Arrays.copyOfRange(words, 1, words.length-1);
            System.out.println(Arrays.toString(words));
            if (words.length < 1) return "";
            String nextWord = words[0];
            return nextWord;
        }
        catch (Exception e) {
            return "";
        }
        // return "";
    }

    public void selectPrevWord() {
        InputConnection ic = getCurrentInputConnection();
        try {
            String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
            if (words.length < 1) return;
            String lastWord = words[words.length - 1];
            int position = getSelectionStart() - lastWord.length() - 1;
            if (position < 0) position = 0;
            if (Variables.isSelecting()) ic.setSelection(position, Variables.cursorStart);
            else ic.setSelection(position, position);
        }
        catch (Exception e) {
            //  toastIt(e.toString());
        }
    }

    public void selectNextWord() {
        InputConnection ic = getCurrentInputConnection();
        try {
            String[] words = ic.getTextAfterCursor(MAX, 0).toString().split(" ");
            words = Arrays.copyOfRange(words, 1, words.length-1);
            if (words.length < 1) return;
            String nextWord = words[0];
            if (words.length > 1) nextWord = words[1];
            int position = getSelectionStart() + nextWord.length() + 1;
            if (Variables.isSelecting()) ic.setSelection(Variables.cursorStart, position);
            else ic.setSelection(position, position);
        }
        catch (Exception e) {
            //  toastIt(e.toString());
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
            toastIt(e.toString());
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

    public int[] getCursorLocation() {
        return new int[]{getCursorLocationOnLine(), getCurrentLine()};
    }

    public int getCursorLocationOnLine() {
        return getPrevLine().length();
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

    @Override
    public void onFinishInput() {
        super.onFinishInput();

        updateCandidates();

        setCandidatesViewShown(false);

        Variables.setSelectingOff();

        currentKeyboard = standardKeyboard;
        if (kv != null) {
            kv.closing();
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
            System.out.println(e);
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
            System.out.println(e);
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
            System.out.println(e);
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

        updateCandidates(getCurrentWord());

        if ((newSelStart != candidatesEnd || newSelEnd != candidatesEnd)) {
            InputConnection ic = getCurrentInputConnection();

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
        commitText(text, 0);
    }

    public void commitText(String text, int offset) {
        InputConnection ic = getCurrentInputConnection();
        ic.beginBatchEdit();
        ic.commitText(text, offset);
        ic.endBatchEdit();
    }

    public void performReplace(String newText) {
        InputConnection ic = getCurrentInputConnection();
        //  ic.requestCursorUpdates(3);
        ic.beginBatchEdit();
        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0) {
            int a = getSelectionStart();
            int b = getSelectionStart() + newText.length();
            ic.commitText(newText, 0);
            ic.setSelection(a, b);
        }
        ic.endBatchEdit();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (debug) System.out.println("onKeyUp"+" "+keyCode);
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (debug) System.out.println("onKeyDown"+" "+keyCode);
        return super.onKeyDown(keyCode, event);
    }

    private void commitTyped(InputConnection inputConnection) {
        if (getPrevWord().length() > 0) {
            commitText(getPrevWord());
            updateCandidates();
        }
    }

    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null && kv != null && standardKeyboard == kv.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != InputType.TYPE_NULL) {
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            kv.setShifted(mCapsLock || caps != 0);
        }
    }

    @Override
    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) {
            return;
        }
        ic.beginBatchEdit();
        ic.commitText(text, 0);
        ic.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    @Override
    public void swipeLeft() {
        InputConnection ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        String prevWord = getPrevWord();
        ic.setSelection(getSelectionStart()-prevWord.length(), getSelectionStart()-prevWord.length());
    }

    @Override
    public void swipeRight() {
        InputConnection ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        String nextWord = getNextWord();
        ic.setSelection(getSelectionEnd()+nextWord.length(), getSelectionEnd()+nextWord.length());
    }

    @Override
    public void swipeDown() {
        setCandidatesViewShown(false);
    }

    @Override
    public void swipeUp() {
        setCandidatesViewShown(true);
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

    public void startIntent(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
    }

    public void showSettings() {
        Intent intent = new Intent(getApplicationContext(), Preference.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent(intent);
    }

    private void updateCandidates() {
        if (!mPredictionOn) return;
        if (mCandidateView == null) return;

        String prevLine = getPrevLine();
        String prevWord = getPrevWord();
        String prevChar = "";

        if (prevLine.length() == 0 && prevWord.length() == 0) {
            mCandidateView.clear();
            return;
        }

        prevChar = String.valueOf(prevLine.charAt(prevLine.length()-1));
        prevChar = prevChar.substring(0, 1);

        if (!Character.isLetter(prevChar.charAt(0))) {
            // || prevChar.charAt(0) == '\''
            mCandidateView.clear();
            return;
        }

        updateCandidates(prevWord);
    }

    private void updateCandidates(String word) {
        boolean isTitleCase = Util.isTitleCase(word);
        boolean isUpperCase = Util.isUpperCase(word) && word.length() > 1;

        word = word.toLowerCase();

        boolean inTrie = SpellChecker.inTrie(word);
        boolean isPrefix = SpellChecker.isPrefix(word);

        ArrayList<String> completions = new ArrayList<>();
        ArrayList<String> suggestions = new ArrayList<>();

        suggestions = SpellChecker.getSuggestions(word, 1);
        if (suggestions.size() > 1) {
            completions.addAll(suggestions);
        }

        if (isPrefix) {
            completions.addAll(SpellChecker.getCompletions(word));
            if (isUpperCase) {
                for(int i = 0; i < completions.size(); i++) {
                    completions.set(i, Util.toUpperCase(completions.get(i)));
                }
            }
            else if (isTitleCase) {
                for(int i = 0; i < completions.size(); i++) {
                    completions.set(i, Util.toTitleCase(completions.get(i)));
                }
            }
        }
        completions.remove(word);
        completions.add(word);
        mCandidateView.setSuggestions(completions, true, true);
        if (mPredictionOn) setCandidatesViewShown(true);
    }

    public void addToDictionary(String word) {
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
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
        // setCandidatesViewShown(false);
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

    public void showActivity(String id) {
        Intent intent = new Intent(id).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    long time = 0;

    public void onPress(int primaryCode) {
        if (debug) System.out.println("onPress: "+primaryCode);
        InputConnection ic = getCurrentInputConnection();
        time = System.nanoTime() - time;

        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("vib", false)) {
            Vibrator v = (Vibrator)getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
            if (v != null) v.vibrate(40);
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        if (debug) System.out.println("onRelease: "+primaryCode);

        InputConnection ic = getCurrentInputConnection();
        time = (System.nanoTime() - time) / 1000000;

        if (time > 300) {
            switch (primaryCode) {
                case 32: handleTab(); break;
                case -12: selectAll(); break;
                case -200: clipboardToBuffer(getText(ic)); break;
            }
        }
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

        int bg = (int)Long.parseLong(Themes.extractBackgroundColor(mDefaultFilter), 16);
        int fg = (int)Long.parseLong(Themes.extractForegroundColor(mDefaultFilter), 16);

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        editor.putInt("bgcolor", bg);
        editor.putInt("fgcolor", fg);
        editor.apply();

        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(mDefaultFilter);
        Paint mPaint = new Paint();
        mPaint.setColorFilter(colorFilter);

        return mPaint;
    }

    private void setInputType() {

        EditorInfo attribute = getCurrentInputEditorInfo();
        int webInputType = attribute.inputType & InputType.TYPE_MASK_VARIATION;

        switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_DATETIME:
            case InputType.TYPE_CLASS_PHONE:
                setKeyboard(R.layout.numeric);
            break;
            case InputType.TYPE_CLASS_TEXT:
                if (webInputType == InputType.TYPE_TEXT_VARIATION_URI
                 || webInputType == InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
                 || webInputType == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                 || webInputType == InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS) {
                    setKeyboard(R.layout.domain);
                }
                else {
                    setKeyboard(R.layout.primary);
                }
                break;
            default:
                setKeyboard(R.layout.primary);
            break;
        }
        if (kv != null) {
            kv.setKeyboard(currentKeyboard);
        }
    }

    private void checkToggleCapsLock() {
        long now = System.currentTimeMillis();
        if (mLastShiftTime + 300 > now) {
            mCapsLock = !mCapsLock;
            mLastShiftTime = 0;
        }
        else {
            mLastShiftTime = now;
        }
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (!sharedPreferences.getBoolean("debug", false)) return;
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
        InputConnection ic = getCurrentInputConnection();
        while (times --> 0) {
            sendKey(primaryCode);
        }
    }

    public void sendKeys(@NonNull int[] keys) {
        InputConnection ic = getCurrentInputConnection();
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

    public void setKeyboard(int id, String title) {
        currentKeyboard = new CustomKeyboard(getBaseContext(), id);
        currentKeyboard.setRowNumber(getStandardRowNumber());
        currentKeyboard.title = title;
        kv.setKeyboard(currentKeyboard);
        redraw();
    }

    public void setKeyboard(int id) {
        currentKeyboard = new CustomKeyboard(getBaseContext(), id);
        currentKeyboard.setRowNumber(getStandardRowNumber());
        kv.setKeyboard(currentKeyboard);
        redraw();
    }

    private void handleDelete() {
        InputConnection ic = getCurrentInputConnection();
        final int length = getSelectionLength();

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
            getCurrentInputConnection().commitText("", 0);
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
        updateCandidates();
    }

    private void handleBackspace() {
        InputConnection ic = getCurrentInputConnection();
        final int length = getSelectionLength();

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
            getCurrentInputConnection().commitText("", 0);
        }
        else {
            sendKey(KeyEvent.KEYCODE_DEL);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
        updateCandidates();
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
                if (code.equals("\"")) commitText("\"\"");
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
        commitText(String.valueOf(code), 0);

        firstCaps = false;
        setCapsOn(false);

        updateShiftKeyState(getCurrentInputEditorInfo());
        updateCandidates();
/*
        ArrayList<String> suggestions = SpellChecker.getSuggestions(getPrevWord());
        if (suggestions.size() > 0 && PreferenceManager.getDefaultSharedPreferences(this).getBoolean("auto", false)) {
            System.out.println(suggestions);
            replaceText(getPrevWord(), suggestions.get(0));
        }
*/
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
                                    52, 53, 54, 55, 56, 57, 61, 94, 215, 247,};
    int[] calcOperators = new int[] {43, 45, 215, 37, 247, 94, 61};
    private void handleCalc(int primaryCode) {
        InputConnection ic = getCurrentInputConnection();
        String sanitized = "", scriptResult = "", parserResult = "";
        switch(primaryCode) {
            case -200: commitText(calcBuffer); break;
            case -209:
                calcBuffer = calcBufferHistory.isEmpty() ? "" : calcBufferHistory.pop();
            break;
            case -205: clipboardToBuffer(getText(ic)); calcBufferHistory.push(calcBuffer); break;
            case -201: calcBuffer = ""; break;
            case -5:
                if (calcBuffer.length() > 0) {
                    calcBuffer = calcBuffer.substring(0, calcBuffer.length() - 1);
                }
                calcBufferHistory.push(calcBuffer);
            break;
            case -204:
                try {
                    sanitized = Util.sanitize(calcBuffer);
                    double result = Util.evalParser(sanitized);
                    if (Util.checkInteger(result)) {
                        int resultInt = (int)result;
                        parserResult = String.valueOf(resultInt);
                    }
                    else {
                        parserResult = String.valueOf(result);
                    }
                }
                catch (Exception e) {
                    toastIt(parserResult+"\n"+e);
                }
                calcBuffer = parserResult;
                calcBufferHistory.push(calcBuffer);
            break;
            case -206:
                sanitized = Util.sanitize(calcBuffer);
                try {
                    scriptResult = Util.evalScript(sanitized);
                }
                catch (Exception e) {
                    toastIt(scriptResult+"\n"+e);
                }
                calcBuffer = scriptResult;
                calcBufferHistory.push(calcBuffer);
            break;
            case -207:
                Expression e = new Expression(Util.sanitize(calcBuffer));
                double result = e.calculate();
                if (Util.checkInteger(result)) {
                    int resultInt = (int)result;
                    calcBuffer = String.valueOf(resultInt);
                }
                else {
                    calcBuffer = String.valueOf(result);
                }
                calcBufferHistory.push(calcBuffer);
            break;
            case 32:
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
        System.out.println(calcBufferHistory);
        getKey(-200).label = calcBuffer;
        redraw();
    }

    static String hexBuffer = "";
    int[] hexPasses = new int[] {
        -101, -23, -22, -20, -21, -13, -14, -15, -16, -12, -11, -10, -9, -7, -5, -8, 10, 32
    };
    int[] hexCaptures = new int[] {
        48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
        97, 98, 99, 100, 101, 102,
        -201, -202, -203, -204, -205, -206
    };
    private void handleUnicode(int primaryCode) {
        InputConnection ic = getCurrentInputConnection();
        String paddedHexBuffer = StringUtils.leftPad(hexBuffer, 4, "0");

        if (primaryCode == -201) {
            performReplace(Util.convertFromUnicodeToNumber(getText(ic)));
            return;
        }
        if (primaryCode == -202) {
            performReplace(Util.convertFromNumberToUnicode(getText(ic)));
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
            paddedHexBuffer = StringUtils.leftPad(hexBuffer, 4, "0");
            getKey(-203).label = hexBuffer.equals("0000") ? "" : paddedHexBuffer;
            getKey(-204).label = String.valueOf((char)(int)Integer.decode("0x" + paddedHexBuffer));
            redraw();
            return;
        }
        if (primaryCode == -206) {
            hexBuffer = "0000";
            paddedHexBuffer = StringUtils.leftPad(hexBuffer, 4, "0");
            getKey(-203).label = hexBuffer.equals("0000") ? "" : paddedHexBuffer;
            getKey(-204).label = String.valueOf((char)(int)Integer.decode("0x" + paddedHexBuffer));
            redraw();
            return;
        }

        if (Util.contains(hexCaptures, primaryCode)) {
            if (hexBuffer.length() > 3) hexBuffer = "";
            hexBuffer += (char)primaryCode;
        }
        paddedHexBuffer = StringUtils.leftPad(hexBuffer, 4, "0");
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
            if (shift_pressed + 200 > System.currentTimeMillis()) {
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
                shift_pressed = System.currentTimeMillis();
            }
        }
        redraw();
    }

    private void handleSpace() {
        commitText(" ");
        // mCandidateView.clear();
        updateCandidates();
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
                commitText("\n", 0);
                if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("indent", false)) {
                    commitText(Util.getIndentation(getPrevLine()), 0);
                    return;
                } //  sendKey(66);
            break;
        }
    }
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

    HashSet<String> clipboardHistory = new HashSet<>();

    public void saveToClipboardHistory() {
        InputConnection ic = getCurrentInputConnection();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        clipboardHistory = new HashSet<String>(sharedPreferences.getStringSet("clipboardHistory", new HashSet<String>()));
        if (getText(ic).isEmpty()) return;
        clipboardHistory.add(getText(ic));
        sharedPreferences.edit().putStringSet("clipboardHistory", clipboardHistory).apply();
    }
    public void handleCut() {
        if (!isSelecting()) selectLine();
        // saveToClipboardHistory();
        sendKey(KeyEvent.KEYCODE_CUT);
    }
    public void handleCopy() {
        if (!isSelecting()) selectLine();
        // saveToClipboardHistory();
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
            performReplace(Util.linebreaksToSpaces(getText(ic)));
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
            && sharedPreferences.getBoolean("ipaDesc", false)
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
            case 32: handleSpace(); break;
            case 10: handleEnter(); break;
            case -1: handleShift(); break;
            case -2:

            break;
            case -5: handleBackspace(); break;
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
            case -22: showSettings(); break;
            case -23: showVoiceInput(); break;
            case -24: handleClose(); break;
            case -25: showInputMethodPicker(); break;
            case -26: sendKey(KeyEvent.KEYCODE_SETTINGS); break;
            case -27: showEmoticons(); break;
            case -28: clearAll(); break;
            case -29: goToStart(); break;
            case -30: goToEnd(); break;
            case -31: selectNone(); break;
            case -32: selectPrevWord(); break;
            case -33: selectNextWord(); break;
            case -34: commitText(getNextLine() + "\n" + getPrevLine(), 0); break;
            case -35: commitText(Util.getDateString(sharedPreferences.getString("date_format", "yyyy-MM-dd"))); break;
            case -36: commitText(Util.getTimeString(sharedPreferences.getString("time_format", "HH:mm:ss"))); break;
            case -37: commitText(Util.nowAsLong() + " " + Util.nowAsInt()); break;
            case -38: performReplace(Util.toUpperCase(getText(ic))); break;
            case -39: performReplace(Util.toTitleCase(getText(ic))); break;
            case -40: performReplace(Util.toLowerCase(getText(ic))); break;
            case -41: performReplace(Util.toAlterCase(getText(ic))); break;
            case -42: performReplace(Util.camelToSnake(getText(ic))); break;
            case -43: performReplace(Util.snakeToCamel(getText(ic))); break;
            case -44: performReplace(Util.sortChars(getText(ic))); break;
            case -45: performReplace(Util.reverseChars(getText(ic))); break;
            case -46: performReplace(Util.shuffleChars(getText(ic))); break;
            case -47: performReplace(Util.doubleChars(getText(ic))); break;
            case -48: performReplace(Util.sortLines(getText(ic))); break;
            case -49: performReplace(Util.reverseLines(getText(ic))); break;
            case -50: performReplace(Util.shuffleLines(getText(ic))); break;
            case -51: performReplace(Util.doubleLines(getText(ic))); break;
            case -52: performReplace(Util.dashesToSpaces(getText(ic))); break;
            case -53: performReplace(Util.underscoresToSpaces(getText(ic))); break;
            case -54: performReplace(Util.spacesToDashes(getText(ic))); break;
            case -55: performReplace(Util.spacesToUnderscores(getText(ic))); break;
            case -56: performReplace(Util.spacesToLinebreaks(getText(ic))); break;
            case -57: performReplace(Util.linebreaksToSpaces(getText(ic))); break;
            case -58: performReplace(Util.spacesToTabs(getText(ic))); break;
            case -59: performReplace(Util.tabsToSpaces(getText(ic))); break;
            case -60: performReplace(Util.splitWithLinebreaks(getText(ic))); break;
            case -61: performReplace(Util.splitWithSpaces(getText(ic))); break;
            case -62: performReplace(Util.removeSpaces(getText(ic))); break;
            case -63: performReplace(Util.reduceSpaces(getText(ic))); break;
            case -64:
                if (!isSelecting()) selectLine();
                performReplace(Util.decreaseIndentation(getText(ic), indentString));
            break;
            case -65:
                if (!isSelecting()) selectLine();
                performReplace(Util.increaseIndentation(getText(ic), indentString));
            break;
            case -66: performReplace(Util.trimEndingWhitespace(getText(ic))); break;
            case -67: performReplace(Util.trimTrailingWhitespace(getText(ic))); break;
            case -68: performReplace(Util.normalize(getText(ic))); break;
            case -69: performReplace(Util.slug(getText(ic))); break;
            case -70:
                joinLines();
            break;
            case -71:
                ere = Util.countChars(getText(ic));
                performReplace(Util.uniqueChars(getText(ic)));
                aft = Util.countChars(getText(ic));
                toastIt(ere + " → " + aft);
            break;
            case -72:
                ere = Util.countLines(getText(ic));
                performReplace(Util.uniqueLines(getText(ic)));
                aft = Util.countLines(getText(ic));
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
                String text = getText(ic);
                toastIt("Chars: " + Util.countChars(text) + "\nWords: " + Util.countWords(text) + "\nLines: " + Util.countLines(text));
                break;
            case -93: toastIt(Util.unidata(getText(ic))); break;
            case -94:
                if (Variables.isBold()) performReplace(FontVariants.unbolden(getText(ic)));
                else performReplace(FontVariants.bolden(getText(ic)));
                Variables.toggleBold();
            break;
            case -95:
                if (Variables.isItalic()) performReplace(FontVariants.unitalicize(getText(ic)));
                else performReplace(FontVariants.italicize(getText(ic)));
                Variables.toggleItalic();
            break;
            case -96:
                if (Variables.isEmphasized()) performReplace(FontVariants.unemphasize(getText(ic)));
                else performReplace(FontVariants.emphasize(getText(ic)));
                Variables.toggleEmphasized();
            break;
            case -97:
                if (getSelectionLength() == 0) Variables.toggleUnderlined();
                else performReplace(FontVariants.underline(getText(ic)));
            break;
            case -98:
                if (getSelectionLength() == 0) Variables.toggleUnderscored();
                else performReplace(FontVariants.underscore(getText(ic)));
            break;
            case -99:
                if (getSelectionLength() == 0) Variables.toggleStrikethrough();
                else performReplace(FontVariants.strikethrough(getText(ic)));
            break;
            case -100:
                Variables.setAllOff();
                performReplace(FontVariants.unbolden(getText(ic)));
                performReplace(FontVariants.unitalicize(getText(ic)));
                performReplace(FontVariants.unemphasize(getText(ic)));
                performReplace(FontVariants.unstrikethrough(getText(ic)));
                performReplace(FontVariants.ununderline(getText(ic)));
                performReplace(FontVariants.ununderscore(getText(ic)));
            break;
            case -104: showActivity(Settings.ACTION_HARD_KEYBOARD_SETTINGS); break;
            case -105: showActivity(Settings.ACTION_LOCALE_SETTINGS); break;
            case -106: showActivity(Settings.ACTION_SETTINGS); break;
            case -107: showActivity(Settings.ACTION_USER_DICTIONARY_SETTINGS); break;
            case -108: showActivity(Settings.ACTION_WIFI_SETTINGS); break;
            case -109: showActivity(Settings.ACTION_WIRELESS_SETTINGS); break;
            case -110: showActivity(Settings.ACTION_VOICE_INPUT_SETTINGS); break;
            case -111: showActivity(Settings.ACTION_USAGE_ACCESS_SETTINGS); break;
            case -112: handleEsc(); break;
            case -113: handleCtrl(); break;
            case -114: handleAlt(); break;
            case -115: showActivity(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE); break;
            case -116: showActivity(Settings.ACTION_HOME_SETTINGS); break;
            case -118: showActivity(Settings.ACTION_INPUT_METHOD_SETTINGS); break;
            case -119: showActivity(Settings.ACTION_AIRPLANE_MODE_SETTINGS); break;
            case -120: showActivity(Settings.ACTION_SOUND_SETTINGS); break;
            case -121: showActivity(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS); break;
            case -122: handleTab(); break;
            case -123: showActivity(Settings.ACTION_BLUETOOTH_SETTINGS); break;
            case -124: showActivity(Settings.ACTION_CAPTIONING_SETTINGS); break;
            case -125: showActivity(Settings.ACTION_DEVICE_INFO_SETTINGS); break;
            case -126: performReplace(Util.convertNumberBase(getText(ic), 2, 10)); break;
            case -127: performReplace(Util.convertNumberBase(getText(ic), 10, 2)); break;
            case -128: performReplace(Util.convertNumberBase(getText(ic), 8, 10)); break;
            case -129: performReplace(Util.convertNumberBase(getText(ic), 10, 8)); break;
            case -130: performReplace(Util.convertNumberBase(getText(ic), 16, 10)); break;
            case -131: performReplace(Util.convertNumberBase(getText(ic), 10, 16)); break;
            case -101: setKeyboard(R.layout.primary); break;
            case -102: setKeyboard(R.layout.menu); break;
            case -103: setKeyboard(R.layout.macros); break;
            case -133: setKeyboard(R.layout.hex); break;
            case -134: setKeyboard(R.layout.numeric); break;
            case -135: setKeyboard(R.layout.navigation); break;
            case -136: setKeyboard(R.layout.fonts); break;
            case -137: setKeyboard(R.layout.ipa, "IPA"); break;
            case -138: setKeyboard(R.layout.symbol); break;
            case -139: setKeyboard(R.layout.unicode, "Unicode"); break;
            case -140: setKeyboard(R.layout.accents); break;
            case -141:
                String customKeys = sharedPreferences.getString("custom_keys", "");
                if (customKeys != "") {
                    Keyboard customKeyboard = new Keyboard(this, R.layout.custom, customKeys, 10, 0);
                    kv.setKeyboard(customKeyboard);
                }
            break;
            case -142: setKeyboard(R.layout.function); break;
            case -143: setKeyboard(R.layout.calc, "Calculator"); break;
            case -144: setKeyboard(R.layout.clipboard); break;
            case -174: setKeyboard(R.layout.coding); break;
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
            case -163: performReplace(Util.replaceNbsp(getText(ic))); break;
            case -164: navigate(KeyEvent.KEYCODE_DPAD_UP,   KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -165: navigate(KeyEvent.KEYCODE_DPAD_UP,   KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -166: navigate(KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -167: navigate(KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -168: break;
            case -169: break;
            case -170:
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleHtmlComment(getText(ic)));
            break;
            case -171:
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleJavaComment(getText(ic)));
            break;
            case -172:
                if (!isSelecting()) selectLine();
                performReplace(Util.toggleLineComment(getText(ic)));
            break;
            case -173:
                displayFindMenu();
            break;
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
            case -501: commitText(getResources().getString(R.string.k1)); break;
            case -502: commitText(getResources().getString(R.string.k2)); break;
            case -503: commitText(getResources().getString(R.string.k3)); break;
            case -504: commitText(getResources().getString(R.string.k4)); break;
            case -505: commitText(getResources().getString(R.string.k5)); break;
            case -506: commitText(getResources().getString(R.string.k6)); break;
            case -507: commitText(getResources().getString(R.string.k7)); break;
            case -508: commitText(getResources().getString(R.string.k8)); break;
            case -509: commitText(getResources().getString(R.string.name)); break;
            case -510: commitText(getResources().getString(R.string.email)); break;
            case -511: commitText(getResources().getString(R.string.phone)); break;
            case -512: commitText(getResources().getString(R.string.address)); break;
            case -513: commitText(getResources().getString(R.string.password)); break;
            default:
                if (Variables.isAnyOn()) processKeyCombo(primaryCode);
                else handleCharacter(primaryCode, keyCodes);
            break;
        }
        redraw();
        try {
            if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("caps", false)) {
                if (ic.getTextBeforeCursor(2, 0).toString().contains(". ")
                 || ic.getTextBeforeCursor(2, 0).toString().contains("? ")
                 || ic.getTextBeforeCursor(2, 0).toString().contains("! ")
                ) {
                    setCapsOn(true);
                    firstCaps = true;
                }
            }
        }
        catch (Exception ignored) {}
        updateCandidates();

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
                    cic.commitText(result, 0); // performReplace(result);
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


    public void showEmoticons() {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            View popupView = layoutInflater.inflate(R.layout.emoticon_listview_layout, null);
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

            // sharedPreferences.edit().putBoolean("pred", false).apply();

            // If the text keyboard closes, also dismiss the emoji popup
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
                    // String renderable = emoticon.isRenderable() ? "✓" : "✗";
                    toastIt(emoticon.getEmoticon()+" "+Util.unidata(emoticon.getEmoticon()));
                    commitText(emoticon.getEmoticon());
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

    public void closeEmoticons() {
        playClick();
        if (emoticonPopup != null) emoticonPopup.dismiss();
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
}