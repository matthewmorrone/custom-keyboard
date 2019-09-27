package com.hathy.simplekeyboard;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;

public class SimpleIME extends InputMethodService implements OnKeyboardActionListener {
    
    private KeyboardView kv;
    private Keyboard keyboard;
    public StringBuilder mComposing = new StringBuilder();
    
    private boolean caps = false;

    public void toastIt(String text) {
        toast.cancel();
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    long time;

    @Override
    public void onPress(int primaryCode) {
        time = System.nanoTime();

        if (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("vib", false)) {
            Vibrator v = (Vibrator)getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(20);
        }
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
        InputConnection ic = getCurrentInputConnection();
        time = (System.nanoTime() - time) / 1000000;
        ic.setSelection(Variables.cursorStart, Variables.cursorEnd);
    }
    
    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        kv.invalidateAllKeys();
        return kv;
    }

    public void onUpdateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd, candidatesStart, candidatesEnd);

        if (Variables.isSelecting() && (PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean("debug", true))) {
            toastIt(Variables.cursorStart+" "+oldSelStart+" "+oldSelEnd+" "+newSelStart+" "+newSelEnd);
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

    public void sendKeyUpDown(int primaryCode) {
        InputConnection ic = getCurrentInputConnection();
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, primaryCode));
        ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   primaryCode));
    }

    public void selectAll() {
        InputConnection ic = getCurrentInputConnection();
        ic.setSelection(0, (ic.getExtractedText(new ExtractedTextRequest(), 0).text).length());
    }
    public void selectNone() {
        InputConnection ic = getCurrentInputConnection();
        try {
            int end = getSelectionEnd();
            ic.setSelection(end, end); 
        }
        catch (Exception ignored) {}
    }

    public void navigate(int primaryCode) {
        InputConnection ic = getCurrentInputConnection();
        if (caps) {ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SHIFT_LEFT));}
        sendKeyUpDown(primaryCode);
        if (caps) {ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,   KeyEvent.KEYCODE_SHIFT_LEFT));}
    }

    public void setShift() {
        caps = !caps;
        keyboard.setShifted(caps);
        for(Keyboard.Key key : keyboard.getKeys()) {
            if      (key.codes[0] == -1 && caps) {key.label = "▲";} 
            else if (key.codes[0] == -1)         {key.label = "△";}
            if      (key.codes[0] == 49 && caps) {key.label = "!"; key.codes[0] = 33;}
            else if (key.codes[0] == 33)         {key.label = "1"; key.codes[0] = 49;}
            if      (key.codes[0] == 50 && caps) {key.label = "@"; key.codes[0] = 64;}
            else if (key.codes[0] == 64)         {key.label = "2"; key.codes[0] = 50;}
            if      (key.codes[0] == 51 && caps) {key.label = "#"; key.codes[0] = 35;}
            else if (key.codes[0] == 35)         {key.label = "3"; key.codes[0] = 51;}
            if      (key.codes[0] == 52 && caps) {key.label = "$"; key.codes[0] = 36;}
            else if (key.codes[0] == 36)         {key.label = "4"; key.codes[0] = 52;}
            if      (key.codes[0] == 53 && caps) {key.label = "%"; key.codes[0] = 37;}
            else if (key.codes[0] == 37)         {key.label = "5"; key.codes[0] = 53;}
            if      (key.codes[0] == 54 && caps) {key.label = "^"; key.codes[0] = 94;}
            else if (key.codes[0] == 94)         {key.label = "6"; key.codes[0] = 54;}
            if      (key.codes[0] == 55 && caps) {key.label = "&"; key.codes[0] = 38;}
            else if (key.codes[0] == 38)         {key.label = "7"; key.codes[0] = 55;}
            if      (key.codes[0] == 56 && caps) {key.label = "*"; key.codes[0] = 42;}
            else if (key.codes[0] == 42)         {key.label = "8"; key.codes[0] = 56;}
            if      (key.codes[0] == 57 && caps) {key.label = "("; key.codes[0] = 40;}
            else if (key.codes[0] == 40)         {key.label = "9"; key.codes[0] = 57;}
            if      (key.codes[0] == 48 && caps) {key.label = ")"; key.codes[0] = 41;}
            else if (key.codes[0] == 41)         {key.label = "0"; key.codes[0] = 48;}
            if      (key.codes[0] == 59 && caps) {key.label = ":"; key.codes[0] = 58;}
            else if (key.codes[0] == 58)         {key.label = ";"; key.codes[0] = 59;}
            if      (key.codes[0] == 39 && caps) {key.label = "\""; key.codes[0] = 34;}
            else if (key.codes[0] == 34)         {key.label = "'"; key.codes[0] = 39;}
            if      (key.codes[0] == 46 && caps) {key.label = ">"; key.codes[0] = 62;}
            else if (key.codes[0] == 62)         {key.label = "."; key.codes[0] = 46;}
            if      (key.codes[0] == 44 && caps) {key.label = "<"; key.codes[0] = 60;}
            else if (key.codes[0] == 60)         {key.label = ","; key.codes[0] = 44;}
            if      (key.codes[0] == 47 && caps) {key.label = "?"; key.codes[0] = 63;}
            else if (key.codes[0] == 63)         {key.label = "/"; key.codes[0] = 47;}
        }
        kv.invalidateAllKeys();
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {        
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);
        switch(primaryCode) {
            case  -1: setShift(); break;
            case  -3: 
                InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                if (imeManager != null) { 
                    imeManager.showInputMethodPicker();
                }
            break;
            case  -4: ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER)); break;
            case  -5: ic.deleteSurroundingText(1, 0); break; 
            case  -7: ic.deleteSurroundingText(0, 1); break;
            case  -8: selectAll(); break;
            case  -9: sendKeyUpDown(KeyEvent.KEYCODE_CUT); break;
            case -10: sendKeyUpDown(KeyEvent.KEYCODE_COPY); break;
            case -11: sendKeyUpDown(KeyEvent.KEYCODE_PASTE); break;
            case -12: navigate(KeyEvent.KEYCODE_DPAD_UP); break;
            case -13: navigate(KeyEvent.KEYCODE_DPAD_DOWN); break;
            case -14: navigate(KeyEvent.KEYCODE_DPAD_LEFT); break;
            case -15: navigate(KeyEvent.KEYCODE_DPAD_RIGHT); break;
            case -16: navigate(KeyEvent.KEYCODE_MOVE_HOME); break;
            case -17: navigate(KeyEvent.KEYCODE_MOVE_END); break;
            case -18: navigate(KeyEvent.KEYCODE_PAGE_UP); break;
            case -19: navigate(KeyEvent.KEYCODE_PAGE_DOWN); break;   
            default:
                char code = (char)primaryCode;
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1); 
            break;                   
        }
    }

    @Override
    public void onPress(int primaryCode) {}

    @Override
    public void onRelease(int primaryCode) {}

    @Override
    public void onText(CharSequence text) {}

    @Override
    public void swipeDown() {}

    @Override
    public void swipeLeft() {}

    @Override
    public void swipeRight() {}

    @Override
    public void swipeUp() {
        caps = false;
        setShift();
        

    }

    private void playClick(int keyCode) {
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(keyCode) {
            case 32: am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR); break;
            case -4:
            case 10: am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);   break;
            case -5: am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);   break;                
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD); break;
        }        
    }
}
