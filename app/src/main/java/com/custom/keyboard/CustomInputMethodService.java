package com.custom.keyboard;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.custom.keyboard.emoticon.Emoticon;
import com.custom.keyboard.emoticon.EmoticonGridView;
import com.custom.keyboard.emoticon.EmoticonPopup;
import com.custom.keyboard.unicode.Unicode;
import com.custom.keyboard.unicode.UnicodeGridView;
import com.custom.keyboard.unicode.UnicodePopup;
import com.custom.keyboard.util.ArrayUtils;
import com.custom.keyboard.util.Bounds;
import com.custom.keyboard.util.Calculator;
import com.custom.keyboard.util.IntentUtils;
import com.custom.keyboard.util.Sounds;
import com.custom.keyboard.util.StringUtils;
import com.custom.keyboard.util.TextUtils;
import com.custom.keyboard.util.Themes;
import com.custom.keyboard.util.TimeUtils;
import com.custom.keyboard.util.ToastIt;
import com.custom.keyboard.util.Util;
import com.custom.keyboard.util.Variables;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class CustomInputMethodService extends InputMethodService
    implements KeyboardView.OnKeyboardActionListener, SpellCheckerSession.SpellCheckerSessionListener {

    boolean debug = false;

    SharedPreferences sharedPreferences;

    private String mWordSeparators;

    long shiftPressed = 0;
    private boolean firstCaps = false;

    int MAX = 65536;
    private short rowNumber = 6;

    private float[] mDefaultFilter;

    private CustomKeyboardView mCustomKeyboardView;
    private CandidateView mCandidateView;
    private InputMethodManager mInputMethodManager;
    private CustomKeyboard mCurrentKeyboard;

    private boolean mPredictionOn;
    private int indentWidth;
    private String indentString;

    private PopupWindow popupWindow = null;
    private UnicodePopup unicodePopup = null;
    private EmoticonPopup emoticonPopup = null;

    private SpellChecker mSpellChecker;
    private SpellCheckerSession mScs;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {}
    };
    
    public Context context() {
        return getBaseContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (debug) System.out.println("onCreate");
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        mSpellChecker = new SpellChecker(context(), true);
        final TextServicesManager tsm = (TextServicesManager)getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        mScs = tsm.newSpellCheckerSession(null, Locale.ENGLISH, this, true);

        longPressTimer = new Timer();

        sharedPreferences = getPreferences(); // this?

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("KeyboardVisibility"));

        new ToastIt(sharedPreferences, getLayoutInflater());
    }

    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context()); // this?
    }

    @Override
    public void onInitializeInterface() {
        if (debug) System.out.println("onInitializeInterface");
    }

    @Override
    public View onCreateInputView() {
        if (debug) System.out.println("onCreateInputView");
        mCustomKeyboardView = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        mCustomKeyboardView.setOnKeyboardActionListener(this);
        
        int prevId = Util.orNull(sharedPreferences.getInt("current_layout", R.layout.primary), R.layout.primary);
        String prevTitle = Util.orNull(sharedPreferences.getString("current_layout_title", "Primary"), "Primary");
       
        setKeyboard(prevId, prevTitle);

        // setKeyboard(R.layout.primary, "Primary");
        // mCustomKeyboardView.setKeyboard(new CustomKeyboard(this, R.layout.primary));
        redraw();

        return mCustomKeyboardView;
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
        ViewGroup originalParent = (ViewGroup)mCustomKeyboardView.getParent();
        if (originalParent != null) {
            originalParent.setPadding(0, 0, 0, 0);
            mCustomKeyboardView.setPopupParent(originalParent);
        }

        mCustomKeyboardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                for (Keyboard.Key k : mCustomKeyboardView.getKeyboard().getKeys()) {
                    if (k.isInside((int)event.getX(), (int)event.getY())) {
                        // System.out.println("Key pressed: X=" + k.x + ", Y=" + k.y);
                        int centreX = (k.width/2) + k.x;
                        int centreY = (k.width/2) + k.y;
                        // System.out.println("Centre of the key pressed: X="+centreX+", Y="+centreY);
                    }
                }
                return false;
            }
        });
        redraw();
    }



    @Override
    public void onStartInput(EditorInfo editorInfo, boolean restarting) {
        super.onStartInput(editorInfo, restarting);
        if (debug) System.out.println("onStartInput: "+editorInfo+" "+restarting);

        // ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);


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
        int fontSize = Integer.parseInt(Util.orNull(sharedPreferences.getString("font_size", "48"), "48"));
        boolean mPreviewOn = sharedPreferences.getBoolean("preview", false);
        mPredictionOn = sharedPreferences.getBoolean("pred", false);
        // debug = sharedPreferences.getBoolean("debug", debug);

        mWordSeparators = sharedPreferences.getString("word_separators", getResources().getString(R.string.word_separators));

        updateCandidates();

        mCustomKeyboardView = (CustomKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
/*
        try {
            setKeyboard(sharedPreferences.getInt("current_layout", 0), sharedPreferences.getString("current_layout_title", ""));
        }
        catch(Exception e) {
            setKeyboard(R.layout.primary, "Primary");
        }
*/
        int prevId = Util.orNull(sharedPreferences.getInt("current_layout", R.layout.primary), R.layout.primary);
        String prevTitle = Util.orNull(sharedPreferences.getString("current_layout_title", "Primary"), "Primary");
        
        setKeyboard(prevId, prevTitle);

        // setKeyboard(R.layout.primary, "Primary");

        // if (sharedPreferences.getBoolean("subtypes", false))
        setInputType();
        capsOnFirst();

        mPaint.setTextSize(fontSize);

        mCustomKeyboardView.setBackgroundColor(Color.argb(transparency, background.red(), background.green(), background.blue()));
        mCustomKeyboardView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        mCustomKeyboardView.setOnKeyboardActionListener(this);

        mCustomKeyboardView.getCustomKeyboard().changeKeyHeight(getHeightKeyModifier());
        mCustomKeyboardView.setPreviewEnabled(mPreviewOn);

        mCurrentKeyboard.setRowNumber(getRowNumber());
        mCustomKeyboardView.setKeyboard(mCurrentKeyboard);

        setInputView(mCustomKeyboardView);

        mCandidateView = new CandidateView(this);
        mCandidateView.setService(this);
        mCandidateView.setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);
        if (isKeyboardVisible()) setCandidatesView(mCandidateView);

        if (mPredictionOn) setCandidatesViewShown(isKeyboardVisible());

        adjustTopRow(mCurrentKeyboard);
        setCustomKey(-27);
        // toggleDelete(mCurrentKeyboard);
        setInputType();
        setImeOptions(getKey(10), getCurrentInputEditorInfo().imeOptions & EditorInfo.IME_MASK_ACTION);

        redraw();
    }

    private void setInputType() {
        // int id = mCurrentKeyboard.xmlLayoutResId;
        // String title = mCurrentKeyboard.title;

        int id = Util.orNull(sharedPreferences.getInt("current_layout", R.layout.primary), R.layout.primary);
        String title = Util.orNull(sharedPreferences.getString("current_layout_title", "Primary"), "Primary");
        
        int webInputType = getCurrentInputEditorInfo().inputType & InputType.TYPE_MASK_VARIATION;

        /*
        switch (getCurrentInputEditorInfo().inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_DATETIME:
            case InputType.TYPE_CLASS_PHONE:
                setKeyboard(R.layout.numeric, "Numeric");
            break;
        */
            /*
            case InputType.TYPE_CLASS_TEXT:
                if (webInputType == InputType.TYPE_TEXT_VARIATION_URI
              // || webInputType == InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
                 || webInputType == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                 || webInputType == InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS) {
                    setKeyboard(R.layout.domain, "Domain");
                }
                else setKeyboard(id, title);
            break;
            */
        /*
            default:
                setKeyboard(id, title);
            break;
        }
        */
        setKeyboard(id, title);
        if (mCustomKeyboardView != null) mCustomKeyboardView.setKeyboard(mCurrentKeyboard);
    }

    public void setImeOptions(Keyboard.Key mEnterKey, int options) {
        if (mEnterKey == null) return;
        switch (options & (EditorInfo.IME_MASK_ACTION | EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
/*
            case EditorInfo.IME_ACTION_GO:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = getResources().getDrawable(R.drawable.ic_go, null);
            break;
            case EditorInfo.IME_ACTION_NEXT:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = getResources().getDrawable(R.drawable.ic_next, null);
            break;
*/
            case EditorInfo.IME_ACTION_SEARCH:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = getResources().getDrawable(R.drawable.ic_find, null);
            break;
            case EditorInfo.IME_ACTION_SEND:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = getResources().getDrawable(R.drawable.ic_send, null);
            break;
            default:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = getResources().getDrawable(R.drawable.ic_enter, null);
            break;
        }
    }

    public void setBackground() {
        mCustomKeyboardView.setBackgroundResource(Themes.randomBackground());
    }

    public void setCustomKey(int primaryCode) {
        Keyboard.Key customKey = getKey(primaryCode);
        String customKeyChoice = sharedPreferences.getString("custom_key", "");

        if (customKey != null && !customKeyChoice.isEmpty()) {
            customKey.codes = new int[]{Integer.parseInt(customKeyChoice)};
            switch(customKeyChoice) {
                case "-22":  customKey.icon = getResources().getDrawable(R.drawable.ic_settings); break;
                case "-23":  customKey.icon = getResources().getDrawable(R.drawable.ic_voice); break;
                case "-25":  customKey.icon = getResources().getDrawable(R.drawable.ic_input_method); break;
                case "-26":  customKey.icon = getResources().getDrawable(R.drawable.ic_android_settings); break;
                case "-27":  customKey.icon = getResources().getDrawable(R.drawable.ic_emoticon); break;
                case "-130": customKey.icon = getResources().getDrawable(R.drawable.ic_macro); break;
                case "-132": customKey.icon = getResources().getDrawable(R.drawable.ic_coding); break;
                case "-133": customKey.icon = getResources().getDrawable(R.drawable.ic_com); break;
                case "-134": customKey.icon = getResources().getDrawable(R.drawable.ic_keypad); break;
                case "-135": customKey.icon = getResources().getDrawable(R.drawable.ic_nav); break;
                case "-136": customKey.icon = getResources().getDrawable(R.drawable.ic_font); break;
                case "-137": customKey.icon = getResources().getDrawable(R.drawable.ic_schwa); break;
                case "-138": customKey.icon = getResources().getDrawable(R.drawable.ic_punctuation); break;
                case "-139": customKey.icon = getResources().getDrawable(R.drawable.ic_unicode); break;
                case "-140": customKey.icon = getResources().getDrawable(R.drawable.ic_accent); break;
                case "-142": customKey.icon = getResources().getDrawable(R.drawable.ic_function); break;
                case "-143": customKey.icon = getResources().getDrawable(R.drawable.ic_calc); break;
                case "-144": customKey.icon = getResources().getDrawable(R.drawable.ic_clipboard); break;
                case "-174": customKey.icon = getResources().getDrawable(R.drawable.ic_unicode_grid); break;
                default:     customKey.icon = getResources().getDrawable(R.drawable.ic_emoticon); break;
            }
        }
    }

    public void setKeyboard(int id, String title) {
        mCurrentKeyboard = new CustomKeyboard(context(), id);
        mCurrentKeyboard.setRowNumber(getStandardRowNumber());
        mCurrentKeyboard.title = title;
        mCustomKeyboardView.setKeyboard(mCurrentKeyboard);
        sharedPreferences.edit().putInt("current_layout", id).apply();
        sharedPreferences.edit().putString("current_layout_title", title).apply();
        if (!title.equals("Navigation")) adjustTopRow(mCurrentKeyboard);
        setCustomKey(-27);
        redraw();
    }

    private Timer longPressTimer = null;

    String prevBuffer = "";
    String nextBuffer = "";

    public void onKeyLongPress(int primaryCode) {
        switch (primaryCode) {
            case 32: handleAction();
            case -2: handleTab(); break;
            case -5: deletePrevWord(); break;
            case -7: deleteNextWord(); break;
            case -11: toggleSelection(); break;
            case -12: selectAll(); break;
            case -15: if (isSelecting()) selectPrevWord(); else moveLeftOneWord(); break;
            case -16: if (isSelecting()) selectNextWord(); else moveRightOneWord(); break;
            case -23: showInputMethodPicker(); break;
            case -200: clipboardToBuffer(getSelectedText()); break;
        }
    }

    @Override
    public void onPress(final int primaryCode) {
        if (debug) System.out.println("onPress: "+primaryCode);
        prevBuffer = getPrevWord();
        nextBuffer = getNextWord();

        longPressTimer.cancel();

        longPressTimer = new Timer();

        longPressTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Handler uiHandler = new Handler(Looper.getMainLooper());
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CustomInputMethodService.this.onKeyLongPress(primaryCode);
                            }
                            catch (Exception e) {
                                sendDataToErrorOutput(CustomInputMethodService.class.getSimpleName(), "uiHandler.run: " + e.getMessage());
                            }
                        }
                    };
                    uiHandler.post(runnable);
                }
                catch (Exception e) {
                    sendDataToErrorOutput(CustomInputMethodService.class.getSimpleName(), "Timer.run: " + e.getMessage());
                }
            }
        }, sharedPreferences.getInt("long_press_duration", ViewConfiguration.getLongPressTimeout()));

        if (sharedPreferences.getBoolean("vib", false)) {
            Vibrator v = (Vibrator)context().getSystemService(Context.VIBRATOR_SERVICE);
            if (v != null) v.vibrate(sharedPreferences.getInt("vibration_duration", 40));
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        if (debug) System.out.println("onRelease: "+primaryCode);
        longPressTimer.cancel();
    }

    @Override
    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);

        String prevLine = getPrevLine();
        int prevChar = 0;
        try {
            if (prevLine != null && prevLine.length() > 0) {
                ArrayList<Integer> prevChars = StringUtils.asUnicodeArray(prevLine);
                prevChar = prevChars.get(prevChars.size() - 1);
            }
        }
        catch (Exception e) {
            if (debug) sendDataToErrorOutput(e.toString());
        }

        String nextLine = getNextLine();
        int nextChar = 0;
        try {
            if (nextLine != null && nextLine.length() > 0) {
                ArrayList<Integer> nextChars = StringUtils.asUnicodeArray(nextLine);
                nextChar = nextChars.get(0);
            }
        }
        catch (Exception e) {
            if (debug) sendDataToErrorOutput(e.toString());
        }
        try {
            boolean isBold = TextUtils.isBold(prevChar) || TextUtils.isBold(nextChar);
            boolean isItalic = TextUtils.isItalic(prevChar) || TextUtils.isItalic(nextChar);
            boolean isEmphasized = TextUtils.isEmphasized(prevChar) || TextUtils.isEmphasized(nextChar);

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
            if (debug) sendDataToErrorOutput(e.toString());
        }
        if ((getSelectionStart() == 0) // || ic.getTextBeforeCursor(1, 0) == "\n"
            && sharedPreferences.getBoolean("caps", false)) {
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
        
        pairStack.clear();
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

        if (mCustomKeyboardView != null) {
            mCustomKeyboardView.closing();
        }
    }

    public boolean isKeyboardVisible() {
        return mInputMethodManager.isAcceptingText();
    }

    @Override
    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.beginBatchEdit();
        ic.commitText(text, 1);
        ic.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public void commitText(String text, boolean space) {
        commitText(text, 1, space);
    }
    public void commitText(String text) {
        commitText(text, 1);
    }
    public void commitText(String text, int offset, boolean space) {
        InputConnection ic = getCurrentInputConnection();
        ic.beginBatchEdit();
        ic.commitText(text, offset);
        if (sharedPreferences.getBoolean("afterspace", true) && space) {
            ic.commitText(" ", 1);
        }
        ic.endBatchEdit();
    }
    public void commitText(String text, int offset) {
        InputConnection ic = getCurrentInputConnection();
        ic.beginBatchEdit();
        ic.commitText(text, offset);
        ic.endBatchEdit();
    }

    public void replaceInSelection(String newText) {
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

    private void commitTyped() {
        if (getPrevWord().length() > 0) {
            commitText(getPrevWord());
            updateCandidates();
        }
    }

    public Bounds getBounds(@NonNull List<Keyboard.Key> keys) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = 0, maxY = 0;
        for (Keyboard.Key key : keys) {
            if (key.x < minX) minX = key.x;
            if (key.y < minY) minY = key.y;

            if (key.x + key.width > maxX) maxX = key.x + key.width;
            if (key.y + key.height > maxY) maxY = key.y + key.height;
        }
        return new Bounds(minX, minY, maxX, maxY);
    }

    public void toggleDelete(CustomKeyboard currentKeyboard) {

        Map<Integer, List<Keyboard.Key>> layoutRows = getKeyboardRows(currentKeyboard);
        List<Keyboard.Key> layoutRow = layoutRows.get(4);
        Bounds bounds = getBounds(layoutRow);

        System.out.println(layoutRow);
        System.out.println(bounds);


        int currentX = 0;
        int keyWidth = layoutRow.get(1).width;
        for(Keyboard.Key key : layoutRow) {
            if (key.codes[0] == -7) {
                key.width = 0;
                key.icon = null;
                key.label = "";
            }
            else if (key.codes[0] == -1) {
                key.width = keyWidth*2;
            }
            else {
                key.x += keyWidth;
            }
            /*
            if (Constants.topRowKeyDefault.contains(String.valueOf(key.codes[0]))) {
                if (!topRowKeys.contains(String.valueOf(key.codes[0]))) {
                    key.width = 0;
                    key.icon = null;
                    key.label = "";
                    continue;
                }
                key.width = bounds.dX/topRowKeys.size();
                key.x = currentX;
                currentX += bounds.dX/topRowKeys.size();
            }
            */

        }
        redraw();
    }

    public void adjustTopRow(CustomKeyboard currentKeyboard) {
        List<Integer> topRowKeys = StringUtils.deserializeInts(Util.notNull(sharedPreferences.getString("top_row_keys", "")));
        List<Keyboard.Key> layoutRow = getKeyboardRowsByHeight(currentKeyboard).get(0);

        Bounds bounds = getBounds(layoutRow);
        int currentX = 0;
        for(Keyboard.Key key : layoutRow) {
            if (StringUtils.contains(Constants.topRowKeyDefault, key.codes[0])) {
                if (!topRowKeys.contains(key.codes[0])) {
                    key.width = 0;
                    key.icon = null;
                    key.label = "";
                    continue;
                }
                key.width = bounds.dX/topRowKeys.size();
                key.x = currentX;
                currentX += bounds.dX/topRowKeys.size();
            }
        }
        redraw();
    }

    // @TODO: autoadjustment of key width by number of keys in row
    public void adjustKeys(CustomKeyboard currentKeyboard) {
        Bounds bounds = getBounds(currentKeyboard.getKeys());
        Map<Integer,List<Keyboard.Key>> layoutRows = getKeyboardRowsByHeight(currentKeyboard);
        for (Map.Entry<Integer, List<Keyboard.Key>> entry : layoutRows.entrySet()) {
            for(Keyboard.Key key : entry.getValue()) {
                key.width = bounds.dX / entry.getValue().size();
            }
        }
        redraw();
    }

    public Map<Integer,List<Keyboard.Key>> getKeyboardRows(CustomKeyboard keyboard) {
        Map<Integer, List<Keyboard.Key>> layoutRows = getKeyboardRowsByHeight(keyboard);
        Map<Integer, List<Keyboard.Key>> layoutRowsNew = getKeyboardRowsByHeight(keyboard);
        layoutRowsNew.clear();
        int i = 0;
        for (Map.Entry<Integer, List<Keyboard.Key>> entry : layoutRows.entrySet()) {
            layoutRowsNew.put(i, entry.getValue());
            i++;
        }
        return layoutRowsNew;
    }

    public Map<Integer,List<Keyboard.Key>> getKeyboardRowsByHeight(CustomKeyboard keyboard) {
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

    public String getSelectedText() {
        InputConnection ic = getCurrentInputConnection();
        CharSequence text = ic.getSelectedText(0);
        if (text == null) return "";
        return (String)text;
    }

    public String getAllText() {
        InputConnection ic = getCurrentInputConnection();
        return Util.orNull(ic.getTextBeforeCursor(MAX, 0), "").toString()
             + Util.orNull(ic.getTextAfterCursor( MAX, 0), "").toString();
    }

    public void sendCustomKey(String key) {
        InputConnection ic = getCurrentInputConnection();
        ic = getCurrentInputConnection();
        ic.requestCursorUpdates(3);
        int cursorLocation = getSelectionStart();
        String ins = sharedPreferences.getString(key, "");
        ic.beginBatchEdit();
        commitText(ins, cursorLocation + (ins != null ? ins.length() : 1));
        ic.endBatchEdit();
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

    public void selectWord() {
        InputConnection ic = getCurrentInputConnection();

        int start = getSelectionStart() - getPrevWord().length();
        int end   = getSelectionEnd() + getNextWord().length();

        try {
        if (getNextWord().length() > 0 && (int)getNextWord().charAt(getNextWord().length()-1) == 10) end--;
        if (end == -1) end++;
        if (String.valueOf(getNextWord().charAt(0)).equals(" ")) {
            end++;
        }
        }
        catch(Exception e) {}

        ic.setSelection(start, end);
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
    public void toggleSelection() {
        Variables.toggleSelecting(getSelectionStart());
    }
    public String getCurrentWord() {
        return getPrevWord()+getNextWord();
    }
    
    public boolean isEverythingSelected() {
        if (getSelectionStart() != 0) return false;
        if (getSelectionEnd() != getAllText().length()) return false;
        return true;
    }
    
    public String getPrevWord() {
        InputConnection ic = getCurrentInputConnection();
        String[] words = Util.orNull(ic.getTextBeforeCursor(MAX, 0), "").toString().split("\\b", -1); // \\b mWordSeparators);
        if (words.length < 1) return "";
        String prevWord = words[words.length - 1];
        if (words.length > 1 && prevWord.equals("")) prevWord = words[words.length - 2];
        return prevWord;
    }
    public String getNextWord() {
        InputConnection ic = getCurrentInputConnection();
        String[] words = Util.orNull(ic.getTextAfterCursor(MAX, 0), "").toString().split("\\b", -1); // \\b mWordSeparators);
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
        // selectionMode = 1;
    }
    public void selectNextWord() {
        String nextWord = getNextWord();
        if (!isSelecting()) Variables.setSelectingOn(getSelectionStart());
        int times = nextWord.length();
        while (times --> 0) {
            navigate(KeyEvent.KEYCODE_DPAD_RIGHT);
        }
        // selectionMode = 1;
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
        while (times --> 0) {
            handleBackspace();
        }
    }
    public void deleteNextWord() {
        String nextWord = nextBuffer; // getNextWord();
        if (debug) System.out.println(nextWord+" "+nextWord.length());
        int times = nextWord.length();
        while (times --> 0) {
            handleDelete();
        }
    }

    public void goToStart() {
        InputConnection ic = getCurrentInputConnection();
        ic.setSelection(0, 0);
        // selectionMode = 0;
    }

    public void goToEnd() {
        InputConnection ic = getCurrentInputConnection();
        ic.setSelection(
            (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length(),
            (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length()
        );
        // selectionMode = 0;
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
        return StringUtils.getLines(ic.getTextBeforeCursor(MAX, 0).toString()).length;
    }

    public int getLineCount() {
        InputConnection ic = getCurrentInputConnection();
        return StringUtils.getLines(getAllText()).length;
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


    public void redraw() {
        mCustomKeyboardView.invalidateAllKeys();
        mCustomKeyboardView.draw(new Canvas());
    }

    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null && mCustomKeyboardView != null && R.layout.primary == mCustomKeyboardView.getId()) {
            int caps = 0;
            EditorInfo editorInfo = getCurrentInputEditorInfo();
            if (editorInfo != null && editorInfo.inputType != InputType.TYPE_NULL) {
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            mCustomKeyboardView.setShifted(caps != 0);
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

    public void showVoiceInput() {
        if (mInputMethodManager != null) {
            mInputMethodManager.setInputMethod(getToken(), "com.google.android.googlequicksearchbox/com.google.android.voicesearch.ime.VoiceInputMethodService");
        }
    }

    private void showInputMethodPicker() {
        if (mInputMethodManager != null) mInputMethodManager.showInputMethodPicker();
    }

    public void sendDataToErrorOutput(String ...args) {
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
        sendDataToErrorOutput(text);
    }
    private void sendDataToErrorOutput(Exception exception) {
        HashMap<String, String> cursorData = new HashMap<>();
        cursorData.put("data", exception.toString()); // maybe change the key?
        sendMessageToActivity("DebugHelper", cursorData);
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
    private void sendMessageToActivity(String destination, HashMap<String, String> data) {
        Intent intent = new Intent(destination);
        for (Map.Entry<String,String> datum : data.entrySet()) {
            intent.putExtra(datum.getKey(), datum.getValue()); // data.getOrDefault("", "")
        }
        LocalBroadcastManager.getInstance(context()).sendBroadcast(intent);
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
        // sendDataToErrorOutput(sb.toString());
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
        // sendDataToErrorOutput(sb.toString());
        displaySuggestions(sb.toString());
    }

    private void displayCustomSuggestions() {
        String prevWord = getPrevWord();
        boolean isTitleCase = StringUtils.isMixedCase(prevWord);
        boolean isUpperCase = StringUtils.isUpperCase(prevWord) && prevWord.length() > 1;
        int suggestions = sharedPreferences.getInt("suggestions", 5);

        prevWord = prevWord.toLowerCase();
        ArrayList<String> results = new ArrayList<String>();

        // boolean inTrie = SpellChecker.inTrie(prevWord);
        boolean isPrefix = SpellChecker.isPrefix(prevWord);

        // results.add(prevWord);

        ArrayList<String> common = SpellChecker.getCommon(prevWord);
        if (isPrefix) {
            common.addAll(SpellChecker.getCompletions(prevWord, suggestions));
        }
        results.addAll(common);

        if (results.size() < 1) return;

        if (isUpperCase) {
            for(int i = 0; i < results.size(); i++) {
                results.set(i, StringUtils.toUpperCase(results.get(i)));
            }
        }
        else if (isTitleCase) {
            for(int i = 0; i < results.size(); i++) {
                results.set(i, StringUtils.toTitleCase(results.get(i)));
            }
        }

        results = ArrayUtils.unique(results);

        mCandidateView.setSuggestions(results, true, true);
    }

    private void displaySuggestions(final String suggestions) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                String prevWord = getPrevWord();
                String[] wordInfos = suggestions.toString().split("\n");
                String prevWordInfos = wordInfos[wordInfos.length-1];
                boolean isTitleCase = StringUtils.isMixedCase(prevWord);
                boolean isUpperCase = StringUtils.isUpperCase(prevWord) && prevWord.length() > 1;
                prevWord = prevWord.toLowerCase();
                ArrayList<String> results = new ArrayList<String>();

                int suggestions = sharedPreferences.getInt("suggestions", 5);
                boolean useSystem = sharedPreferences.getBoolean("use_system_spellcheck", false);
                boolean useCustom = sharedPreferences.getBoolean("use_custom_spellcheck", false);
                // System.out.println("useSystem: "+useSystem+", useCustom: "+useCustom);
                // System.out.println("suggestions: "+suggestions);

                if (useSystem) {
                    results.add(prevWord);
                    for(String s : prevWordInfos.toString().split(",")) {
                        if (s.isEmpty() || s.trim().isEmpty()) continue;
                        results.add(/*"#"+*/s);
                    }
                }
                if (useCustom) { /*wordInfos.length < 1 && */
                    // boolean inTrie = SpellChecker.inTrie(prevWord);
                    boolean isPrefix = SpellChecker.isPrefix(prevWord);

                    // results.add(prevWord);

                    ArrayList<String> common = SpellChecker.getCommon(prevWord);
                    if (isPrefix) {
                        common.addAll(SpellChecker.getCompletions(prevWord, suggestions));
                    }
                    results.addAll(common);
                }
                if (results.size() < 1) return;

                if (isUpperCase) {
                    for(int i = 0; i < results.size(); i++) {
                        results.set(i, StringUtils.toUpperCase(results.get(i)));
                    }
                }
                else if (isTitleCase) {
                    for(int i = 0; i < results.size(); i++) {
                        results.set(i, StringUtils.toTitleCase(results.get(i)));
                    }
                }

                results = ArrayUtils.unique(results);

                mCandidateView.setSuggestions(results, true, true);
            }
        });
    }

    private void fetchSuggestionsFor(String input) {
        // sendDataToErrorOutput(input);
        int suggestions = sharedPreferences.getInt("suggestions", 5);
        if(!input.isEmpty()) {
            try {
                // mScs.getSuggestions(new TextInfo(input), 5);
                if (mScs != null) {
                    mScs.getSentenceSuggestions(new TextInfo[]{new TextInfo(input)}, suggestions);
                }
                else {
                    displayCustomSuggestions();
                }
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

    public Cursor checkUserDictionary(String word) {
        return getContentResolver().query(
            UserDictionary.Words.CONTENT_URI,
            new String[]{
                UserDictionary.Words._ID,
                UserDictionary.Words.WORD
            },
            UserDictionary.Words.WORD + " = ?",
            new String[]{word},
            null
        );
    }
    public boolean inUserDictionary(String word) {
        return checkUserDictionary(word).getCount() == 0;
    }

    public void addToDictionary(String word) {
        if (word.trim().isEmpty()) return;
        if (!inUserDictionary(word)) {
            UserDictionary.Words.addWord(this, word, 1, null, Locale.getDefault());
            mSpellChecker.addToTrie(word);
            ToastIt.text(context(), word+" added to dictionary");
        }
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
    private void handleClose() {
        // commitTyped(getCurrentInputConnection());
        setCandidatesViewShown(false);
        requestHideSelf(0);
        mCustomKeyboardView.closing();
    }

    public int getGravity() {
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

    private void sendKeyEvent(int keyCode, int metaState) {
        InputConnection ic = getCurrentInputConnection();
        ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, getHardKeyCode(keyCode), 0, metaState));
        ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_UP,   getHardKeyCode(keyCode), 0, metaState));
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

        SharedPreferences.Editor editor = sharedPreferences.edit();
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
        if (Variables.isShift()) mCustomKeyboardView.getKeyboard().setShifted(true);
        else mCustomKeyboardView.getKeyboard().setShifted(on);
        mCustomKeyboardView.invalidateAllKeys();
    }

    private void capsOnFirst() {
        InputConnection ic = getCurrentInputConnection();
        if (sharedPreferences.getBoolean("caps", false)) {
            if (getCursorCapsMode(getCurrentInputEditorInfo()) != 0) {
                firstCaps = true;
                setCapsOn(true);
            }
        }
        else {
            firstCaps = false;
            setCapsOn(false);
        }
    }

    private int getCursorCapsMode(EditorInfo attr) {
        InputConnection ic = getCurrentInputConnection();
        int caps = 0;
        EditorInfo ei = getCurrentInputEditorInfo();
        if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
            caps = ic.getCursorCapsMode(attr.inputType);
        }
        return caps;
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
                commitText(String.valueOf((char)keyCode), 1); // Util.largeIntToChar(primaryCode)
            }
        }
    }

    public Keyboard.Key getKey(int primaryCode) {
        if (mCurrentKeyboard == null) return null;
        for (Keyboard.Key key : mCurrentKeyboard.getKeys()) {
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

    private boolean pairMatch(String c1, String c2) {
        if (c1.equals("{") && c2.equals("}")) return true;
        if (c1.equals("}") && c2.equals("{")) return true;
        if (c1.equals("(") && c2.equals(")")) return true;
        if (c1.equals(")") && c2.equals("(")) return true;
        if (c1.equals("{") && c2.equals("}")) return true;
        if (c1.equals("}") && c2.equals("{")) return true;
        if (c1.equals("[") && c2.equals("]")) return true;
        if (c1.equals("]") && c2.equals("[")) return true;
        if (c1.equals("<") && c2.equals(">")) return true;
        if (c1.equals(">") && c2.equals("<")) return true;
        if (c1.equals("'") && c2.equals("'")) return true;
        if (c1.equals("\"") && c2.equals("\"")) return true;
        return false;
    }

    private void handleDelete() {
        InputConnection ic = getCurrentInputConnection();
        final int length = getSelectionLength();

        ic.beginBatchEdit();

        if (sharedPreferences.getBoolean("pairs", false)
            && ic.getTextAfterCursor(1, 0) != null
            && String.valueOf(ic.getTextAfterCursor(1, 0)).length() >= 1
            && pairMatch(String.valueOf(ic.getTextBeforeCursor(1, 0)), String.valueOf(ic.getTextAfterCursor(1, 0)))
        ) {
            ic.deleteSurroundingText(1, 1);
        }
        else if (!isSelecting()
            && sharedPreferences.getBoolean("indent", false)
            && ic.getTextAfterCursor(indentWidth, 0) != null
            && String.valueOf(ic.getTextAfterCursor(indentWidth, 0)).length() >= indentWidth
            && String.valueOf(ic.getTextAfterCursor(indentWidth, 0)).equals(indentString)) {
            ic.deleteSurroundingText(0, indentWidth);
        }
        else if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            if (Variables.isAlt()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0, KeyEvent.META_ALT_ON));
            if (Variables.isCtrl()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL, 0, KeyEvent.META_CTRL_ON));
        }
        else if (length > 0) {
            commitText("", 1);
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
            && pairMatch(String.valueOf(ic.getTextBeforeCursor(1, 0)), String.valueOf(ic.getTextAfterCursor(1, 0)))
        ) {
            ic.deleteSurroundingText(1, 1);
        }
        else if (!isSelecting()
            && sharedPreferences.getBoolean("indent", false)
            && ic.getTextBeforeCursor(indentWidth, 0) != null
            && String.valueOf(ic.getTextBeforeCursor(indentWidth, 0)).length() >= indentWidth
            && String.valueOf(ic.getTextBeforeCursor(indentWidth, 0)).equals(indentString)) {
            ic.deleteSurroundingText(indentWidth, 0);
        }
        else if (Variables.isAnyOn()) {
            if (Variables.isCtrl() && Variables.isAlt()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_CTRL_ON | KeyEvent.META_ALT_ON));
            if (Variables.isAlt())  ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_ALT_ON));
            if (Variables.isCtrl()) ic.sendKeyEvent(new KeyEvent(100, 100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, KeyEvent.META_CTRL_ON));
        }
        else if (length == 0) {
            if (ic.getTextBeforeCursor(1, 0).length() > 0) {
                ic.deleteSurroundingText(Character.isSurrogate(ic.getTextBeforeCursor(1, 0).charAt(0)) ? 2 : 1, 0);
            }
        }
        else if (length > 0) {
            ic.commitText("", 1);
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
        if (suggestions.size() > 0 && sharedPreferences.getBoolean("auto", false)) {
            replaceText(getPrevWord(), suggestions.get(0));
        }
*/
    }
    public void handleTab() {
        InputConnection ic = getCurrentInputConnection();

        // @TODO: use variable for spaces
        if (sharedPreferences.getBoolean("indent", false)) {
            int spaceCount = (indentWidth - (getPrevLine().length() % indentWidth));
            if (spaceCount > 0 && spaceCount < indentWidth && getPrevLine().length() < indentWidth) {
                spaceCount = indentWidth;
            }
            commitText(indentString.substring(0, spaceCount), 0, false);
        }
        else {
            if (sharedPreferences.getBoolean("tabs", false)) {
                commitText("	", false);
                ToastIt.debug(context(), "tab");
            }
            else {
                commitText(StringUtils.repeat(" ", indentWidth), false);
                ToastIt.debug(context(), indentWidth+" spaces");
            }
        }
        if (isSelecting()) ic.setSelection(getSelectionStart(), getSelectionEnd() + indentString.length()-1);
        mCandidateView.clear();
    }

    Stack<String> pairStack = new Stack<>();
    private void handleCharacter(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        ic.beginBatchEdit();
        if (isInputViewShown() && mCustomKeyboardView.isShifted()) primaryCode = Character.toUpperCase(primaryCode);
        String code = String.valueOf((char)primaryCode);

        if (!pairStack.empty() && ((pairStack.peek().equals("(") && code.equals(")"))
         || (pairStack.peek().equals("[") && code.equals("]"))
         || (pairStack.peek().equals("{") && code.equals("}"))
         || (pairStack.peek().equals("'") && code.equals("'"))
         || (pairStack.peek().equals("\"") && code.equals("\""))
        )) {
            sendKey(KeyEvent.KEYCODE_DPAD_RIGHT);
            pairStack.pop();
            return;
        }
        else if (sharedPreferences.getBoolean("pairs", true)
         && (Util.orNull(ic.getTextAfterCursor(1, 0), "").length() == 0
         || !Character.isLetter(Util.orNull(ic.getTextAfterCursor(1, 0), "").charAt(0)))
        ) {
            if (StringUtils.contains("({\"[", code)) {
                if (code.equals("(")) commitText("()", false);
                if (code.equals("[")) commitText("[]", false);
                if (code.equals("{")) commitText("{}", false);
                if (code.equals("'")) commitText("'", 1, false);
                if (code.equals("\"")) commitText("\"\"", 1, false);
                sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
                pairStack.push(code);
                System.out.println(pairStack);
                return;
            }
        }

        if (Variables.isBold()) primaryCode = TextUtils.getBold(primaryCode);
        if (Variables.isItalic()) primaryCode = TextUtils.getItalic(primaryCode);
        if (Variables.isEmphasized()) primaryCode = TextUtils.getEmphasized(primaryCode);
        if (Variables.isBoldSerif()) primaryCode = TextUtils.toBoldSerif(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isItalicSerif()) primaryCode = TextUtils.toItalicSerif(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isBoldItalicSerif()) primaryCode = TextUtils.toBoldItalicSerif(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isScript()) primaryCode = TextUtils.toScript(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isScriptBold()) primaryCode = TextUtils.toScriptBold(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isFraktur()) primaryCode = TextUtils.toFraktur(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isFrakturBold()) primaryCode = TextUtils.toFrakturBold(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isSans()) primaryCode = TextUtils.toSans(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isMonospace()) primaryCode = TextUtils.toMonospace(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isDoublestruck()) primaryCode = TextUtils.toDoublestruck(primaryCode, mCustomKeyboardView.isShifted());
        if (Variables.isEnsquare()) primaryCode = TextUtils.ensquare(primaryCode);
        if (Variables.isCircularStampLetters()) primaryCode = TextUtils.toCircularStampLetters(primaryCode);
        if (Variables.isRectangularStampLetters()) primaryCode = TextUtils.toRectangularStampLetters(primaryCode);
        if (Variables.isSmallCaps()) primaryCode = TextUtils.toSmallCaps(primaryCode);
        if (Variables.isParentheses()) primaryCode = TextUtils.toParentheses(primaryCode);
        if (Variables.isEncircle()) primaryCode = TextUtils.encircle(primaryCode);
        if (Variables.isReflected()) primaryCode = TextUtils.toReflected(primaryCode);
        if (Variables.isCaps()) primaryCode = TextUtils.toCaps(primaryCode);

        if (sharedPreferences.getBoolean("caps", false) && Character.isLetter((char)primaryCode) && Variables.isShift() && firstCaps) {
            primaryCode = Character.toUpperCase(primaryCode);
        }
        commitText(String.valueOf((char)primaryCode), 1, false);

        if (Variables.isUnderlined()) commitText("̲", 1, false);
        if (Variables.isUnderscored()) commitText("꯭", 1, false);
        if (Variables.isStrikethrough()) commitText("̶", 1, false);

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

    private void handleCalculator(int primaryCode) {
        // InputConnection ic = getCurrentInputConnection();
        String sanitized = "", scriptResult = "", parserResult = "";
        switch(primaryCode) {
            case -200: commitText(calcBuffer); break;
            case -209: calcBuffer = calcBufferHistory.isEmpty() ? "" : calcBufferHistory.pop(); break;
            case -205: clipboardToBuffer(getSelectedText()); calcBufferHistory.push(calcBuffer); break;
            case -201: calcBuffer = ""; break;
            case -5: 
                if (calcBuffer.length() > 0) calcBuffer = calcBuffer.substring(0, calcBuffer.length() - 1);
                calcBufferHistory.push(calcBuffer);
            break;
            case -204:
                try {
                    sanitized = Calculator.sanitize(calcBuffer);
                    double result = Calculator.evalParser(sanitized);
                    if (Calculator.checkInteger(result)) parserResult = String.valueOf((int)result);
                    else parserResult = String.valueOf(result);
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
                if (Calculator.checkInteger(result)) calcBuffer = String.valueOf((int)result);
                else calcBuffer = String.valueOf(result);
                calcBufferHistory.push(calcBuffer);
            break;
            case -2:
                calcBuffer += " ";
                calcBufferHistory.push(calcBuffer);
            break;
            default:
                if (StringUtils.contains(Constants.calcOperators, primaryCode)) calcBuffer += " ";
                calcBuffer += (char)primaryCode;
                if (StringUtils.contains(Constants.calcOperators, primaryCode)) calcBuffer += " ";
                calcBufferHistory.push(calcBuffer);
            break;
        }
        // calcBufferHistory.push(calcBuffer);
        if (debug) System.out.println(calcBufferHistory);
        getKey(-200).label = calcBuffer;
        redraw();
    }

    static String hexBuffer = "";
    private void handleUnicode(int primaryCode) {
        // InputConnection ic = getCurrentInputConnection();
        String paddedHexBuffer = StringUtils.padLeft(hexBuffer, 4, "0");
        if (primaryCode == -174) {
            showUnicodePopup();
        }
        if (primaryCode == -201) {
            replaceInSelection(StringUtils.convertFromUnicodeToNumber(getSelectedText()));
            return;
        }
        if (primaryCode == -202) {
            replaceInSelection(StringUtils.convertFromNumberToUnicode(getSelectedText()));
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
            paddedHexBuffer = StringUtils.padLeft(hexBuffer, 4, "0");
            getKey(-203).label = hexBuffer.equals("0000") ? "" : paddedHexBuffer;
            getKey(-204).label = String.valueOf((char)(int)Integer.decode("0x" + paddedHexBuffer));
            redraw();
            return;
        }
        if (primaryCode == -206) {
            hexBuffer = "0000";
            paddedHexBuffer = StringUtils.padLeft(hexBuffer, 4, "0");
            getKey(-203).label = hexBuffer.equals("0000") ? "" : paddedHexBuffer;
            getKey(-204).label = String.valueOf((char)(int)Integer.decode("0x" + paddedHexBuffer));
            redraw();
            return;
        }

        if (StringUtils.contains(Constants.hexCaptures, primaryCode)) {
            if (hexBuffer.length() > 3) hexBuffer = "";
            hexBuffer += (char)primaryCode;
        }
        paddedHexBuffer = StringUtils.padLeft(hexBuffer, 4, "0");
        getKey(-203).label = hexBuffer.equals("0000") ? "" : paddedHexBuffer;
        getKey(-204).label = String.valueOf((char)(int)Integer.decode("0x" + paddedHexBuffer));
        redraw();
    }

    public void handleShift() {
        InputConnection ic = getCurrentInputConnection();
        sharedPreferences = getPreferences();

        if (ic.getSelectedText(0) != null && ic.getSelectedText(0).length() > 0 &&
            sharedPreferences.getBoolean("toggle_shift", false)) {
            String text = ic.getSelectedText(0).toString();

            int a = getSelectionStart();
            int b = getSelectionEnd();

            if (sharedPreferences.getBoolean("include_title_case", false)) {
                if      (StringUtils.isLowerCase(text)) text = StringUtils.toTitleCase(text);
                else if (StringUtils.isUpperCase(text)) text = StringUtils.toLowerCase(text);
                else if (StringUtils.isMixedCase(text)) text = StringUtils.toUpperCase(text);
            }
            else {
                if (StringUtils.containsUpperCase(text)) text = StringUtils.toLowerCase(text);
                else                                     text = StringUtils.toUpperCase(text);
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

    public void handleAction() {
        InputConnection ic = getCurrentInputConnection();
        EditorInfo editorInfo = getCurrentInputEditorInfo();
        switch (editorInfo.imeOptions & EditorInfo.IME_MASK_ACTION) {
/*
            case EditorInfo.IME_ACTION_DONE:
                ToastIt.debug(getContext(), "done");
                ic.performEditorAction(EditorInfo.IME_ACTION_DONE);
            break;
*/
            case EditorInfo.IME_ACTION_GO:
                ToastIt.debug(context(), "go");
                ic.performEditorAction(EditorInfo.IME_ACTION_GO);
            break;
            case EditorInfo.IME_ACTION_SEARCH:
                ToastIt.debug(context(), "search");
                ic.performEditorAction(EditorInfo.IME_ACTION_SEARCH);
            break;
            case EditorInfo.IME_ACTION_NEXT:
                ToastIt.debug(context(), "next");
                ic.performEditorAction(EditorInfo.IME_ACTION_NEXT);
            break;
            case EditorInfo.IME_ACTION_SEND:
                ToastIt.debug(context(), "send");
                ic.performEditorAction(EditorInfo.IME_ACTION_SEND);
            break;
            default:
                commitText("\n", 1);
                if (sharedPreferences.getBoolean("indent", false)) {
                    commitText(StringUtils.getIndentation(getPrevLine()), 0);
                }
            break;
        }
    }

    public void handleEnter() {
        handleAction();
/*
        EditorInfo editorInfo = getCurrentInputEditorInfo();
        commitText("\n", 1);
        if (sharedPreferences.getBoolean("indent", false)) {
            commitText(StringUtils.getIndentation(getPrevLine()), 0);
        }
*/
    }

    String clipboardHistory = new String();

    public void clearClipboardHistory() {
        clipboardHistory = "";
        sharedPreferences.edit().putString("clipboard_history", clipboardHistory).apply();
        redraw();
    }
    public void saveToClipboardHistory() {
        // InputConnection ic = getCurrentInputConnection();

        clipboardHistory = sharedPreferences.getString("clipboard_history", "");
        ArrayList<String> clipboardHistoryArray = new ArrayList<String>(StringUtils.deserialize(clipboardHistory));

        if (getSelectedText().isEmpty()) return;

        clipboardHistoryArray.add(getSelectedText());

        if (clipboardHistoryArray.size() > 16) {
            clipboardHistoryArray = new ArrayList<String>(clipboardHistoryArray.subList(1, clipboardHistoryArray.size()));
        }

        clipboardHistoryArray.removeAll(Arrays.asList("", null));

        clipboardHistory = StringUtils.serialize(clipboardHistoryArray);
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
        if (!isSelecting()) {
            sendKey(KeyEvent.KEYCODE_MOVE_END);
            commitText(" ");
            sendKey(KeyEvent.KEYCODE_FORWARD_DEL);
            sendKey(KeyEvent.KEYCODE_MOVE_END);
        }
        else {
            replaceInSelection(StringUtils.linebreaksToSpaces(getSelectedText()));
            // replaceInSelection(StringUtils.reduceSpaces(getSelectedText()));
        }
    }

    public void playClick() {
        playClick(0);
    }
    public void playClick(int primaryCode) {
        if (!sharedPreferences.getBoolean("sound", false)) return;
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (primaryCode) {
            case 32: am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR); break;
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
        int ere, aft;

        if (sharedPreferences.getBoolean("toast_debug", false)) {
            ToastIt.debug(context(), ""+primaryCode);
        }

        if (primaryCode > 0
         && mCurrentKeyboard.title != null
         && mCurrentKeyboard.title.equals("IPA")
         && sharedPreferences.getBoolean("ipa_desc", false)
         && Sounds.getIPA(primaryCode) != -1) {
            ToastIt.info(context(), "/"+String.valueOf((char)primaryCode)+"/", Sounds.getDescription(primaryCode));
        }

        if (primaryCode > 0
         && mCurrentKeyboard.title != null
         && mCurrentKeyboard.title.equals("IPA")
         && sharedPreferences.getBoolean("ipa", false)
         && Sounds.getIPA(primaryCode) != -1) {
            Sounds.playIPA(context(), primaryCode);
        }
        else {
            playClick(primaryCode);
        }

        if (mCurrentKeyboard.title != null && mCurrentKeyboard.title.equals("Unicode")
         && !StringUtils.contains(Constants.hexPasses, primaryCode)) {
            handleUnicode(primaryCode);
            return;
        }
        if (mCurrentKeyboard.title != null && mCurrentKeyboard.title.equals("Calculator")
         && !StringUtils.contains(Constants.calcPasses, primaryCode)) {
            handleCalculator(primaryCode);
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
            case  -1: handleShift(); break;
            case  -3: break;
            case  -4: break;
            case -24: handleClose(); break;
            case -28: clearAll(); break;
            case -13: navigate(KeyEvent.KEYCODE_DPAD_UP); break;
            case -14: navigate(KeyEvent.KEYCODE_DPAD_DOWN); break;
            case -15: navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -16: navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -17: navigate(KeyEvent.KEYCODE_DPAD_CENTER); break;
            case -18: navigate(KeyEvent.KEYCODE_PAGE_UP); break;
            case -19: navigate(KeyEvent.KEYCODE_PAGE_DOWN); break;
            case -21: navigate(KeyEvent.KEYCODE_MOVE_END); break;
            case -164: navigate(KeyEvent.KEYCODE_DPAD_UP,   KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -165: navigate(KeyEvent.KEYCODE_DPAD_UP,   KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -166: navigate(KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -167: navigate(KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -104: IntentUtils.showActivity(context(), Settings.ACTION_HARD_KEYBOARD_SETTINGS); break;
            case -105: IntentUtils.showActivity(context(), Settings.ACTION_LOCALE_SETTINGS); break;
            case -106: IntentUtils.showActivity(context(), Settings.ACTION_SETTINGS); break;
            case -107: IntentUtils.showActivity(context(), Settings.ACTION_USER_DICTIONARY_SETTINGS); break;
            case -108: IntentUtils.showActivity(context(), Settings.ACTION_WIFI_SETTINGS); break;
            case -109: IntentUtils.showActivity(context(), Settings.ACTION_WIRELESS_SETTINGS); break;
            case -110: IntentUtils.showActivity(context(), Settings.ACTION_VOICE_INPUT_SETTINGS); break;
            case -111: IntentUtils.showActivity(context(), Settings.ACTION_USAGE_ACCESS_SETTINGS); break;
            case -112: IntentUtils.showActivity(context(), Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE); break; // handleEsc();
            case -113: IntentUtils.showActivity(context(), Settings.ACTION_HOME_SETTINGS); break; // handleCtrl();
            case -114: IntentUtils.showActivity(context(), Settings.ACTION_INPUT_METHOD_SETTINGS); break; // handleAlt();
            case -115: IntentUtils.showActivity(context(), Settings.ACTION_AIRPLANE_MODE_SETTINGS); break; // handleTab();
            case -116: IntentUtils.showActivity(context(), Settings.ACTION_SOUND_SETTINGS);   break;
            case -117: IntentUtils.showActivity(context(), Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);   break;
            case -118: IntentUtils.showActivity(context(), Settings.ACTION_BLUETOOTH_SETTINGS); break;
            case -119: IntentUtils.showActivity(context(), Settings.ACTION_CAPTIONING_SETTINGS); break;
            case -120: IntentUtils.showActivity(context(), Settings.ACTION_DEVICE_INFO_SETTINGS); break;
            case -121: IntentUtils.showClipboard(context()); break;
            case -22: IntentUtils.showSettings(context()); break;
            case -23: showVoiceInput(); break;
            case -26: sendKey(KeyEvent.KEYCODE_SETTINGS); break;
            case -25: showInputMethodPicker(); break;
            case -27: showEmoticonPopup(); break;
            case -173: displayFindMenu(); break;
            case -174: showUnicodePopup(); break;
            case -93: 
                String unidata = StringUtils.unidata(getSelectedText());
                if (!unidata.isEmpty()) ToastIt.info(context(), unidata);
            break;
            case -35: commitText(TimeUtils.getDateString(sharedPreferences.getString("date_format", "yyyy-MM-dd"))); break;
            case -36: commitText(TimeUtils.getTimeString(sharedPreferences.getString("time_format", "HH:mm:ss"))); break;
            case -37: commitText(TimeUtils.nowAsLong() + " " + TimeUtils.nowAsInt()); break;
            case -73: commitText(TimeUtils.timemoji()); break;
            case -74: ic.performContextMenuAction(16908338); break; // undo
            case -75: ic.performContextMenuAction(16908339); break; // redo
            case -76: ic.performContextMenuAction(16908337); break; // pasteAsPlainText,
            case -77: ic.performContextMenuAction(16908323); break; // copyUrl
            case -78: ic.performContextMenuAction(16908355); break; // autofill
            case -79: ic.performContextMenuAction(16908330); break; // addToDictionary
            case -80: ic.performContextMenuAction(16908320); break; // cut
            case -81: ic.performContextMenuAction(16908321); break; // copy
            case -82: ic.performContextMenuAction(16908322); break; // paste
            case -83: ic.performContextMenuAction(16908319); break; // selectAll
            case -84: ic.performContextMenuAction(16908324); break; // switchInputMethod
            case -85: ic.performContextMenuAction(16908328); break; // startSelectingText
            case -86: ic.performContextMenuAction(16908329); break; // stopSelectingText
            case -87: ic.performContextMenuAction(16908326); break; // keyboardView
            case -88: ic.performContextMenuAction(16908333); break; // selectTextMode
            case -89: ic.performContextMenuAction(16908327); break; // closeButton
            case -90: ic.performContextMenuAction(16908316); break; // extractArea
            case -91: ic.performContextMenuAction(16908317); break; // candidatesArea
            case -122: replaceInSelection(StringUtils.convertNumberBase(getSelectedText(),  2, 10)); break;
            case -123: replaceInSelection(StringUtils.convertNumberBase(getSelectedText(), 10,  2)); break;
            case -124: replaceInSelection(StringUtils.convertNumberBase(getSelectedText(),  8, 10)); break;
            case -125: replaceInSelection(StringUtils.convertNumberBase(getSelectedText(), 10,  8)); break;
            case -126: replaceInSelection(StringUtils.convertNumberBase(getSelectedText(), 16, 10)); break;
            case -127: replaceInSelection(StringUtils.convertNumberBase(getSelectedText(), 10, 16)); break;
            case -168: replaceInSelection(StringUtils.hash(getSelectedText())); break;
            case -169: replaceInSelection(StringUtils.sha256(getSelectedText())); break;
            case -128: setKeyboard(R.layout.primary, "Primary"); break;
            case -129: setKeyboard(R.layout.menu, "Menu"); break;
            case -130: setKeyboard(R.layout.macros, "Macros"); break;
            case -131: setKeyboard(R.layout.greek, "Greek"); break;
            case -132: setKeyboard(R.layout.coding, "Coding"); break;
            case -133: setKeyboard(R.layout.domain, "Domain"); break;
            case -134: setKeyboard(R.layout.numeric, "Numeric"); break;
            case -135: setKeyboard(R.layout.navigation, "Navigation"); break;
            case -136: setKeyboard(R.layout.fonts, "Fonts"); break;
            case -137: setKeyboard(R.layout.ipa, "IPA"); break;
            case -138: setKeyboard(R.layout.symbol, "Symbol"); break;
            case -139: setKeyboard(R.layout.unicode, "Unicode"); break;
            case -140: setKeyboard(R.layout.accents, "Accents"); break;
            case -142: setKeyboard(R.layout.function, "Function"); break;
            case -143: setKeyboard(R.layout.calculator, "Calculator"); break;
            case -144: setKeyboard(R.layout.clipboard, "Clipboard"); break;
            case -185: setKeyboard(R.layout.utility, "Utility"); break;
            case -141:
                String customKeys = sharedPreferences.getString("custom_keys", "");
                if (customKeys != "") {
                    Keyboard customKeyboard = new Keyboard(this, R.layout.custom, customKeys, 10, 0);
                    mCustomKeyboardView.setKeyboard(customKeyboard);
                }
            break;
            case -94:
                if (Variables.isBold()) replaceInSelection(TextUtils.unbolden(getSelectedText()));
                else replaceInSelection(TextUtils.bolden(getSelectedText()));
                Variables.toggleBold();
            break;
            case -95:
                if (Variables.isItalic()) replaceInSelection(TextUtils.unitalicize(getSelectedText()));
                else replaceInSelection(TextUtils.italicize(getSelectedText()));
                Variables.toggleItalic();
            break;
            case -96:
                if (Variables.isEmphasized()) replaceInSelection(TextUtils.unemphasize(getSelectedText()));
                else replaceInSelection(TextUtils.emphasize(getSelectedText()));
                Variables.toggleEmphasized();
            break;
            case -97:
                if (getSelectionLength() == 0) Variables.toggleUnderlined();
                else replaceInSelection(TextUtils.underline(getSelectedText()));
            break;
            case -98:
                if (getSelectionLength() == 0) Variables.toggleUnderscored();
                else replaceInSelection(TextUtils.underscore(getSelectedText()));
            break;
            case -99:
                if (getSelectionLength() == 0) Variables.toggleStrikethrough();
                else replaceInSelection(TextUtils.strikethrough(getSelectedText()));
            break;
            case -100:
                Variables.setAllOff();
                replaceInSelection(TextUtils.unbolden(getSelectedText()));
                replaceInSelection(TextUtils.unitalicize(getSelectedText()));
                replaceInSelection(TextUtils.unemphasize(getSelectedText()));
                replaceInSelection(TextUtils.unstrikethrough(getSelectedText()));
                replaceInSelection(TextUtils.ununderline(getSelectedText()));
                replaceInSelection(TextUtils.ununderscore(getSelectedText()));
            break;
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
            case -177: IntentUtils.dialPhone(context(), getSelectedText()); break;
            case -178: IntentUtils.openWebpage(context(), getSelectedText()); break;
            case -179: IntentUtils.searchWeb(context(), getSelectedText()); break;
            case -180: IntentUtils.showLocationFromAddress(context(), getSelectedText()); break;
            case -181: IntentUtils.searchWikipedia(context(), getSelectedText()); break;
            case -182: IntentUtils.shareText(context(), getSelectedText()); break;




            case  -2: handleSpace(); break;
            case  10: handleEnter(); break;

            case  -5: handleBackspace(); break;
            case  -6: break;
            case  -7: handleDelete(); break;
            case  -8: handleCut(); break;
            case  -9: handleCopy(); break;
            case -10: handlePaste(); break;
            case -11: toggleSelection(); break;
            case -12:
                if (getSelectionStart() == getSelectionEnd()) selectWord();
                else selectLine();
            break;
            case -175: moveLeftOneWord(); break;
            case -176: moveRightOneWord(); break;
            case -20:
                navigate(KeyEvent.KEYCODE_MOVE_HOME);
                if (String.valueOf(ic.getTextBeforeCursor(1, 0)).contains("\n")) {
                    sendKey(KeyEvent.KEYCODE_DPAD_RIGHT, StringUtils.getIndentation(getNextLine()).length());
                }
            break;
            case -29: goToStart(); break;
            case -30: goToEnd(); break;
            case -31: selectNone(); break;
            case -32: selectPrevWord(); break;
            case -33: selectNextWord(); break;
            case -92:
                String text = getSelectedText();
                ToastIt.info(context(), "Chars: " + StringUtils.countChars(text) + "\nWords: " + StringUtils.countWords(text) + "\nLines: " + StringUtils.countLines(text));
            break;
            case -71:
                ere = StringUtils.countChars(getSelectedText());
                replaceInSelection(StringUtils.uniqueChars(getSelectedText()));
                aft = StringUtils.countChars(getSelectedText());
                ToastIt.text(context(), ere + " → " + aft);
            break;
            case -72:
                ere = StringUtils.countLines(getSelectedText());
                replaceInSelection(StringUtils.uniqueLines(getSelectedText()));
                aft = StringUtils.countLines(getSelectedText());
                ToastIt.text(context(), ere + " → " + aft);
            break;
            case -34: commitText(getNextLine() + "\n" + getPrevLine(), 1); break;
            case -70: joinLines(); break;
            case -38: replaceInSelection(StringUtils.toUpperCase(getSelectedText())); break;
            case -39: replaceInSelection(StringUtils.toTitleCase(getSelectedText())); break;
            case -40: replaceInSelection(StringUtils.toLowerCase(getSelectedText())); break;
            case -41: replaceInSelection(StringUtils.toAlterCase(getSelectedText())); break;
            case -42: replaceInSelection(StringUtils.camelToSnake(getSelectedText())); break;
            case -43: replaceInSelection(StringUtils.snakeToCamel(getSelectedText())); break;
            case -44: replaceInSelection(StringUtils.sortChars(getSelectedText())); break;
            case -45: replaceInSelection(StringUtils.reverseChars(getSelectedText())); break;
            case -46: replaceInSelection(StringUtils.shuffleChars(getSelectedText())); break;
            case -47: replaceInSelection(StringUtils.doubleChars(getSelectedText())); break;
            case -48: replaceInSelection(StringUtils.sortLines(getSelectedText())); break;
            case -49: replaceInSelection(StringUtils.reverseLines(getSelectedText())); break;
            case -50: replaceInSelection(StringUtils.shuffleLines(getSelectedText())); break;
            case -51: replaceInSelection(StringUtils.doubleLines(getSelectedText())); break;
            case -66: replaceInSelection(StringUtils.trimLeadingWhitespace(getSelectedText())); break;
            case -67: replaceInSelection(StringUtils.trimTrailingWhitespace(getSelectedText())); break;
            case -68: replaceInSelection(StringUtils.normalize(getSelectedText())); break;
            case -69: replaceInSelection(StringUtils.slug(getSelectedText())); break;
            case -186: replaceInSelection(StringUtils.addLineNumbers(getSelectedText())); break;
            case -187: replaceInSelection(StringUtils.addBullets(getSelectedText())); break;
            case -188:
                replaceInSelection(StringUtils.removeLineNumbers(getSelectedText()));
                replaceInSelection(StringUtils.removeBullets(getSelectedText()));
            break;
            case -101: 
                if (getSelectionLength() == 0) commitText("/");
                else replaceInSelection("/"+getSelectedText()+"/");       
            break;
            case -102: 
                if (getSelectionLength() == 0) commitText("(");
                else replaceInSelection("("+getSelectedText()+")");       
            break;
            case -103: 
                if (getSelectionLength() == 0) commitText("[");
                else replaceInSelection("["+getSelectedText()+"]");       
            break;
            case -52: replaceInSelection(StringUtils.dashesToSpaces(getSelectedText())); break;
            case -53: replaceInSelection(StringUtils.underscoresToSpaces(getSelectedText())); break;
            case -54: replaceInSelection(StringUtils.spacesToDashes(getSelectedText())); break;
            case -55: replaceInSelection(StringUtils.spacesToUnderscores(getSelectedText())); break;
            case -56: replaceInSelection(StringUtils.spacesToLinebreaks(getSelectedText())); break;
            case -57: replaceInSelection(StringUtils.linebreaksToSpaces(getSelectedText())); break;
            case -58: replaceInSelection(StringUtils.spacesToTabs(getSelectedText())); break;
            case -59: replaceInSelection(StringUtils.tabsToSpaces(getSelectedText())); break;
            case -60: replaceInSelection(StringUtils.splitWithLinebreaks(getSelectedText())); break;
            case -61: replaceInSelection(StringUtils.splitWithSpaces(getSelectedText())); break;
            case -62: replaceInSelection(StringUtils.removeSpaces(getSelectedText())); break;
            case -63: replaceInSelection(StringUtils.reduceSpaces(getSelectedText())); break;
            case -64:
                if (!isSelecting()) selectLine();
                replaceInSelection(StringUtils.increaseIndentation(getSelectedText(), "    "));
            break;
            case -65:
                if (!isSelecting()) selectLine();
                replaceInSelection(StringUtils.decreaseIndentation(getSelectedText(), "    "));
            break;
            case -163: replaceInSelection(StringUtils.replaceNbsp(getSelectedText())); break;
            case -170:
                if (!isSelecting()) selectLine();
                replaceInSelection(StringUtils.toggleHtmlComment(getSelectedText()));
            break;
            case -171:
                if (!isSelecting()) selectLine();
                replaceInSelection(StringUtils.toggleJavaComment(getSelectedText()));
            break;
            case -172:
                if (!isSelecting()) selectLine();
                replaceInSelection(StringUtils.toggleLineComment(getSelectedText()));
            break;
            case -183: 
                if (!isSelecting()) selectAll();
                replaceInSelection(StringUtils.removeCitations(getSelectedText()));
            break;
            case -184:
                if (!isSelecting()) selectAll();
                ToastIt.debug(context(), ""+StringUtils.containsNonPrintables(getSelectedText()));
                replaceInSelection(StringUtils.removeNonPrintables(getSelectedText()));
            break;
            case -189: 
                replaceInSelection(StringUtils.redact(getSelectedText()));
            break;
            case -190: 
                replaceInSelection(StringUtils.insertCommas(getSelectedText())); 
            break;
            case -191: 
                replaceInSelection(StringUtils.removeCommas(getSelectedText())); 
            break;

            /*
            case -192: break;
            case -193: break;
            case -194: break;
            case -195: break;
            case -196: break;
            case -197: break;
            case -198: break;
            case -199: break;
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

            case -299: commitText(".com"); /* popupKeyboard(getKey(-299)); */ break;
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
        View view = LayoutInflater.from(context()).inflate(R.layout.tld, new FrameLayout(context()));
        // PopupWindow popup = new PopupWindow(getContext());
        final PopupWindow popup = new PopupWindow(
            // LayoutInflater.from(getContext()).inflate(R.layout.tld, new FrameLayout(getContext())),
            view,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        );
        // popup.setContentView(view);
        // popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.showAtLocation(mCustomKeyboardView, Gravity.NO_GRAVITY, nextTo.x, nextTo.y);

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

    private Runnable delay = null;
    private int timer = 500;

    public void displayFindMenu() {

        final InputConnection inputConnection = getCurrentInputConnection();

        // if (getSelectionLength() == 0) selectAll();

        String val = "";
        if (getSelectionLength() == 0) {
            val = Util.orNull(String.valueOf(inputConnection.getTextBeforeCursor(MAX, 0)), "")
                + Util.orNull(String.valueOf(inputConnection.getTextAfterCursor(MAX, 0)), "");
        }
        else {
            val = (String)inputConnection.getSelectedText(0);
        }

        // registerReceiver(receiver, new IntentFilter("FindReplace"));

        setKeyboard(R.layout.primary, "Primary");

        LayoutInflater li = (LayoutInflater)context().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (li != null) {
            View wordbar = li.inflate(R.layout.wordbar, null);

            LinearLayout ll = (LinearLayout)wordbar.findViewById(R.id.wordsLayout);
            final EditText findEditText = (EditText)wordbar.findViewById(R.id.find);
            final EditText replEditText = (EditText)wordbar.findViewById(R.id.repl);
            final TextView go = (TextView)wordbar.findViewById(R.id.go);
            final TextView close = (TextView)wordbar.findViewById(R.id.close);

            popupWindow = new PopupWindow(wordbar, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                }
            });

            // popupWindow.setFocusable(false);
            // popupWindow.setOutsideTouchable(false);
            popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.black));
            // popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            popupWindow.showAtLocation(mCustomKeyboardView.getRootView(), Gravity.TOP, 0, 0);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (popupWindow.isShowing()) popupWindow.dismiss();
                }
            });

            final String finalVal = val;

            go.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    String subject = finalVal;
                    String find = findEditText.getText().toString();
                    String repl = replEditText.getText().toString();
                    final String result = subject.replace(find, repl);
                    if (delay == null) {
                        delay = new Runnable() {
                            @Override
                            public void run() {
                                System.out.println(result);
                                inputConnection.commitText(result, 1);
                                ToastIt.text(context(), result);
                                if (delay != null) {
                                    if (inputConnection.getHandler() != null) inputConnection.getHandler().postDelayed(delay, timer);
                                    if (timer > 100) {
                                        timer -= 200;
                                    }
                                }
                            }
                        };
                        if (inputConnection.getHandler() != null) inputConnection.getHandler().post(delay);
                    }
                    return false;
                }
            });
        }
    }

    public void showEmoticonPopup() {
        LayoutInflater layoutInflater = (LayoutInflater)context().getSystemService(LAYOUT_INFLATER_SERVICE);

        if (layoutInflater != null) {
            View popupView = layoutInflater.inflate(R.layout.emoticon_listview, null);
            setCandidatesViewShown(false); // setCandidatesViewShown(popupWindow != null);

            boolean wasCandOn = sharedPreferences.getBoolean("pred", false);

            emoticonPopup = new EmoticonPopup(popupView, this);
            emoticonPopup.setSizeForSoftKeyboard();

            emoticonPopup.setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            emoticonPopup.setHeight(mCustomKeyboardView.getHeight());
            emoticonPopup.showAtLocation(mCustomKeyboardView.getRootView(), Gravity.BOTTOM, 0, 0);

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
                    if (sharedPreferences.getBoolean("toast_emoji_info", false)) {
                        ToastIt.info(context(), "\t"+emoticon.getEmoticon()+"\t"+StringUtils.unidata(emoticon.getEmoticon()));
                    }
                    commitText(emoticon.getEmoticon());
                }
            });
            emoticonPopup.setOnEmoticonLongClickedListener(new EmoticonGridView.OnEmoticonLongClickedListener() {
                @Override
                public void onEmoticonLongClicked(Emoticon emoticon) {
                    playClick();
                    int tab = emoticonPopup.getCurrentTab();
                    String emoticonFavorites = new String(sharedPreferences.getString("emoticon_favorites", ""));
                    StringTokenizer tokenizer = new StringTokenizer(emoticonFavorites, "~");

                    if (tab == 0) {
                        emoticonPopup.removeRecentEmoticon(context(), emoticon);
                        if (sharedPreferences.getBoolean("toast_emoji_info", false)) {
                            ToastIt.info(context(), emoticon.getEmoticon()+" removed from emoticon recents");
                        }
                    }
                    else if (tab == 10) {

                        StringBuilder sb = new StringBuilder();
                        while (tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            if (token.equals(emoticon.getEmoticon())) continue;
                            sb.append(token+"~");
                        }
                        emoticonFavorites = sb.toString();
                        emoticonFavorites.replace("/~+/", "~");
                        emoticonFavorites.replace("/~$/", "");

                        sharedPreferences.edit().putString("emoticon_favorites", emoticonFavorites).apply();
                        if (sharedPreferences.getBoolean("toast_emoji_info", false)) {
                            ToastIt.info(context(), emoticon.getEmoticon()+" removed from emoticon favorites");
                        }
                    }
                    else {
                        StringBuilder sb = new StringBuilder();
                        while (tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            if (token.equals(emoticon.getEmoticon())) continue;
                            sb.append(token+"~");
                        }
                        sb.append(emoticon.getEmoticon());
                        emoticonFavorites = sb.toString();
                        emoticonFavorites.replace("/~+/", "~");
                        emoticonFavorites.replace("/~$/", "");

                        sharedPreferences.edit().putString("emoticon_favorites", new String(emoticonFavorites)).apply();
                        if (sharedPreferences.getBoolean("toast_emoji_info", false)) {
                            ToastIt.info(context(), emoticon.getEmoticon()+" added to emoticon favorites");
                        }
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
        LayoutInflater layoutInflater = (LayoutInflater)context().getSystemService(LAYOUT_INFLATER_SERVICE);
        if (layoutInflater != null) {
            View popupView = layoutInflater.inflate(R.layout.unicode_list_view, null);

            unicodePopup = new UnicodePopup(popupView, this);

            unicodePopup.setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            unicodePopup.setHeight(mCustomKeyboardView.getHeight());
            unicodePopup.showAtLocation(mCustomKeyboardView.getRootView(), Gravity.BOTTOM, 0, 0);

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
                    if (sharedPreferences.getBoolean("toast_unicode_info", false)) {
                        ToastIt.info(context(), unicode.getUnicode()+" "+StringUtils.unidata(unicode.getUnicode()));
                    }
                    commitText(unicode.getUnicode());
                }
            });
            unicodePopup.setOnUnicodeLongClickedListener(new UnicodeGridView.OnUnicodeLongClickedListener() {
                @Override
                public void onUnicodeLongClicked(Unicode unicode) {
                    playClick();
                    int tab = unicodePopup.getCurrentTab();
                    String unicodeFavorites = sharedPreferences.getString("unicode_favorites", "");
                    if (tab == 0) {
                        StringBuilder sb = new StringBuilder(unicodeFavorites);
                        if (unicodeFavorites.indexOf(unicode.getUnicode()) > -1) {
                            sb.deleteCharAt(unicodeFavorites.indexOf(unicode.getUnicode()));
                        }
                        sb.append(unicode.getUnicode());
                        unicodeFavorites = sb.toString();
                        sharedPreferences.edit().putString("unicode_favorites", unicodeFavorites).apply();
                        if (sharedPreferences.getBoolean("toast_unicode_info", false)) {
                            ToastIt.info(context(), unicode.getUnicode() + " added to unicode favorites");
                        }
                    }
                    if (tab == 1) {
                        unicodePopup.removeRecentUnicode(context(), unicode);
                        if (sharedPreferences.getBoolean("toast_unicode_info", false)) {
                            ToastIt.info(context(), unicode.getUnicode() + " removed from unicode recents");
                        }
                    }
                    if (tab == 2) {
                        StringBuilder sb = new StringBuilder(unicodeFavorites);
                        if (unicodeFavorites.indexOf(unicode.getUnicode()) > -1) {
                            sb.deleteCharAt(unicodeFavorites.indexOf(unicode.getUnicode()));
                        }
                        unicodeFavorites = sb.toString();
                        sharedPreferences.edit().putString("unicode_favorites", unicodeFavorites).apply();
                        if (sharedPreferences.getBoolean("toast_unicode_info", false)) {
                            ToastIt.info(context(), unicode.getUnicode() + " removed from favorites");
                        }
                    }
                }
            });
            unicodePopup.setOnUnicodeBackspaceClickedListener(new UnicodePopup.OnUnicodeBackspaceClickedListener() {
                @Override
                public void onUnicodeBackspaceClicked(View v) {
                    playClick(-7);
                    handleBackspace();
                }
            });
            unicodePopup.setOnUnicodeDeleteClickedListener(new UnicodePopup.OnUnicodeDeleteClickedListener() {
                @Override
                public void onUnicodeDeleteClicked(View v) {
                    playClick(-7);
                    handleDelete();
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
        return ((double)sharedPreferences.getInt("height", 100)) / 100;
    }

    public void showMacroDialog(int primaryCode) {
        // System.out.println("showMacroDialog");

        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            IntentUtils.startIntent(context(), intent);
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
            PixelFormat.OPAQUE
        );

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
