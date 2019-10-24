
mVoiceKey = (ImageButton)findViewById(R.id.suggestions_strip_voice_key);


<ImageButton
android:id="@+id/suggestions_strip_voice_key"
android:layout_width="@dimen/config_suggestions_strip_edge_key_width"
android:layout_height="fill_parent"
android:layout_alignParentEnd="true"
android:layout_alignParentRight="true"
android:layout_centerVertical="true"
android:contentDescription="@string/spoken_description_mic"
style="?attr/suggestionWordStyle" />


@Override
public void onClick(final View view) {
AudioAndHapticFeedbackManager.getInstance().performHapticAndAudioFeedback(
Constants.CODE_UNSPECIFIED, this);
if (view == mImportantNoticeStrip) {
mListener.showImportantNoticeContents();
return;
}
if (view == mVoiceKey) {
mListener.onCodeInput(Constants.CODE_SHORTCUT,
Constants.SUGGESTION_STRIP_COORDINATE, Constants.SUGGESTION_STRIP_COORDINATE,
false /* isKeyRepeat */);
return;
}


@Override
public void onResume() {
super.onResume();
final Preference voiceInputKeyOption = findPreference(Settings.PREF_VOICE_INPUT_KEY);
if (voiceInputKeyOption != null) {
RichInputMethodManager.getInstance().refreshSubtypeCaches();
voiceInputKeyOption.setEnabled(VOICE_IME_ENABLED);
voiceInputKeyOption.setSummary(VOICE_IME_ENABLED
? null : getText(R.string.voice_input_disabled_summary));
}
}



mVoiceKey.setOnClickListener(this);


import android.speech.RecognitionService;

import com.android.settings.VoiceInputOutputSettings;

new VoiceInputOutputSettings(this).onCreate();

// Triggered when the user clicks the enable keyboard button.
public void onKeyboardSettings(View view) {
startActivityForResult(new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS), 0);
}

btnAdd.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Intent in=new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
startActivityForResult(in);
}
private void startActivityForResult(Intent intent) {}
});

public class MainActivity extends AppCompatActivity {
private static final int INPUT_METHOD_RESULT = 947;


@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
Intent in = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
startActivityForResult(in, INPUT_METHOD_RESULT);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data)     {
super.onActivityResult(requestCode, resultCode, data);
Log.d(MainActivity.class.getSimpleName(), "Returning from input settings with code: " + requestCode);
}
}

I/System.out: How many layouts? 13
I/System.out: How many rows? 2

504 / 7 = 72
504 / 6 = 84
504 / 5 = 100.8
504 / 4 = 126
504 / 3 = 168
504 / 2 = 252

Each row will have 252 px

7 rows of 6:
height: 72
rows: 126 198 270 342 414 486 558

you have all the space between 126 and 630, 504 pixels, 7 rows



@@ -957,7 +961,7 @@ public class CustomInputMethodService extends InputMethodService implements Keyb
sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
ic = getCurrentInputConnection();
final int length = mComposing.length();
-
+        String indent = Util.getIndentation(prevLine());
String prev;
try {
prev = String.valueOf(ic.getTextBeforeCursor(1, 0).length() > 0 ? ic.getTextBeforeCursor(1, 0) : "");
@@ -970,9 +974,14 @@ public class CustomInputMethodService extends InputMethodService implements Keyb

try {
if (!isSelecting()
-                 && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals("    ")
-                 && sharedPreferences.getBoolean("spaces", true)) {
-                ic.deleteSurroundingText(4, 0);
+            && String.valueOf(ic.getTextBeforeCursor(4, 0)).equals("    ")
+            && sharedPreferences.getBoolean("spaces", true)) {
+                if (prevLine().trim().length() == 0) {
+                    ic.deleteSurroundingText(prevLine().length()+1, 0);
+                }
+                else {
+                    ic.deleteSurroundingText(4, 0);
+                }
}
else if (length > 1) {
mComposing.delete(length - 1, length);
@@ -1196,7 +1205,18 @@ public class CustomInputMethodService extends InputMethodService implements Keyb
if (kv.getKeyboard().isShifted()) {commitText("\n", 1);}
if (sharedPreferences.getBoolean("spaces", true)) {
String indent = Util.getIndentation(prevLine());
-                    if (indent.length() > 0) {commitText("\n"+indent); break;}
+                    if (indent.length() > 0) {
+                        String maybeLeftBracket = String.valueOf(ic.getTextBeforeCursor(1, 0));
+                        String maybeRightBracket = String.valueOf(ic.getTextAfterCursor(1, 0));
+                        commitText("\n"+indent);
+                        // break;
+                        if (maybeLeftBracket.equals("{")) {
+                            commitText("    ");
+                        }
+                        if (maybeRightBracket.equals("}")) {
+                            commitText("\n"+indent);
+                        }
+                    }
}



6 rows of 6:
height: 84
rows: 126


cut
copy
paste
select_all
select
left
right
up
down
home
end
page_up
page_down
clear
backspace
delete
enter
shift
capslock
tab




r.getDimensionPixelSize(R.dimen.candidate_vertical_padding);
popup_c.setOrder(0);
getKey(32).popupResId
// preference has getIntent for opening custom key editors
// preference also has getOrder for possible use in recycler view for layout order


popupWindow.setOutsideTouchable(true);
popupWindow.setTouchable(true);
popupWindow.setBackgroundDrawable(new BitmapDrawable());



public void Scratch() {
if (layouts.size() <=  5) {layoutLayout = R.layout.layouts_1;}
else if (layouts.size() <= 10) {layoutLayout = R.layout.layouts_2;}
else if (layouts.size() <= 15) {layoutLayout = R.layout.layouts_3;}
else if (layouts.size() <= 20) {layoutLayout = R.layout.layouts_4;}
else        {layoutLayout = R.layout.layouts_5;}

else {
String bg = sharedPreferences.getString("bg", "#FF000000");
int[] bgc = Util.fromColor(bg);
float[] sCustomColorArray = {
1.0f,      0,      0,       0,      bgc[1], // red
0,   1.0f,      0,       0,      bgc[2], // green
0,      0,   1.0f,       0,      bgc[3], // blue
0,      0,      0,    1.0f,      bgc[0]  // alpha
};
mDefaultFilter = sCustomColorArray;
kv.invalidateAllKeys();
kv.draw(new Canvas());
}

public String getNextWord() {
ic = getCurrentInputConnection();
String[] words = ic.getTextAfterCursor(MAX, 0).toString().split(" ");
if (words.length < 2) {
return ic.getTextAfterCursor(MAX, 0).toString();
}
return words[0];
}

public String[] getWords() {
ic = getCurrentInputConnection();
String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
return words;
}


public String[] getLines() {
ic = getCurrentInputConnection();
String[] lines = ic.getTextBeforeCursor(MAX, 0).toString().split("\n");
return lines;
}




public void lineLast(int n) {
try {
ic = getCurrentInputConnection();
for(int i = 0; i < n; i++) {
String[] lines = ic.getTextBeforeCursor(MAX, 0).toString().split("\n");
String lastLine = lines[lines.length - 1];
if (lines.length > 1) {
lastLine = lines[1];
}
int position = getSelectionStart() + lastLine.length() + 1;
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

public void lineNext(int n) {
try {
ic = getCurrentInputConnection();
for(int i = 0; i < n; i++) {
String[] lines = ic.getTextAfterCursor(MAX, 0).toString().split("\n");
String nextLine = lines[0];
if (lines.length > 1) {
nextLine = lines[1];
}
int position = getSelectionStart() + nextLine.length() + 1;
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



Override
public void onPress(int primaryCode)
{
Keyboard currentKeyboard = mInputView.getKeyboard();
List<Keyboard.Key> keys = currentKeyboard.getKeys();
mInputView.invalidateKey(primaryCode);

for(int i = 0; i < keys.size() - 1; i++ )
{
Keyboard.Key currentKey = keys.get(i);

//If your Key contains more than one code, then you will have to check if the codes array contains the primary code
if(currentKey.codes[0] == primaryCode)
{
currentKey.label = null;
currentKey.icon = getResources().getDrawable(android.R.drawable.ic_dialog_email);
break; // leave the loop once you find your match
}
}
}


     /*
      * Copyright (C) 2011 Google Inc.
      *
      * Licensed under the Apache License, Version 2.0 (the "License"); you may not
      * use this file except in compliance with the License. You may obtain a copy of
      * the License at
      *
      * http://www.apache.org/licenses/LICENSE-2.0
      *
      * Unless required by applicable law or agreed to in writing, software
      * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
      * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
      * License for the specific language governing permissions and limitations under
      * the License.
      */

     package com.mbanna.swiftbraille.voiceime;


     import android.content.BroadcastReceiver;
     import android.content.Context;
     import android.content.Intent;
     import android.content.IntentFilter;
     import android.inputmethodservice.InputMethodService;
     import android.net.ConnectivityManager;
     import android.net.NetworkInfo;
     import android.view.inputmethod.InputMethodSubtype;

/**
 * Triggers a voice recognition by using {@link ImeTrigger} or
 * {@link IntentApiTrigger}.
 */
public class VoiceRecognitionTrigger {

    private final InputMethodService mInputMethodService;

    private BroadcastReceiver mReceiver;

    private Trigger mTrigger;

    private ImeTrigger mImeTrigger;
    private IntentApiTrigger mIntentApiTrigger;

    public VoiceRecognitionTrigger(InputMethodService inputMethodService) {
        mInputMethodService = inputMethodService;
        mTrigger = getTrigger();
    }

    private Trigger getTrigger() {
        if (ImeTrigger.isInstalled(mInputMethodService)) {
            return getImeTrigger();
        } else if (IntentApiTrigger.isInstalled(mInputMethodService)) {
            return getIntentTrigger();
        } else {
            return null;
        }
    }

    private Trigger getIntentTrigger() {
        if (mIntentApiTrigger == null) {
            mIntentApiTrigger = new IntentApiTrigger(mInputMethodService);
        }
        return mIntentApiTrigger;
    }

    private Trigger getImeTrigger() {
        if (mImeTrigger == null) {
            mImeTrigger = new ImeTrigger(mInputMethodService);
        }
        return mImeTrigger;
    }

    public boolean isInstalled() {
        return mTrigger != null;
    }

    public boolean isEnabled() {
        return isNetworkAvailable();
    }

    /**
     * Starts a voice recognition. The language of the recognition will match
     * the voice search language settings, or the locale of the calling IME.
     */
    public void startVoiceRecognition() {
        startVoiceRecognition(null);
    }

    /**
     * Starts a voice recognition
     *
     * @param language The language in which the recognition should be done. If
     *            the recognition is done through the Google voice typing, the
     *            parameter is ignored and the recognition is done using the
     *            locale of the calling IME.
     * @see InputMethodSubtype
     */
    public void startVoiceRecognition(String language) {
        if (mTrigger != null) {
            mTrigger.startVoiceRecognition(language);
        }
    }

    public void onStartInputView() {
        if (mTrigger != null) {
            mTrigger.onStartInputView();
        }

        // The trigger is refreshed as the system may have changed in the meanwhile.
        mTrigger = getTrigger();
    }

    private boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) mInputMethodService
                 .getSystemService(
                      Context.CONNECTIVITY_SERVICE);
            final NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return info != null && info.isConnected();
        } catch (SecurityException e) {
            // The IME does not have the permission to check the networking
            // status. We hope for the best.
            return true;
        }
    }

    /**
     * Register a listener to receive a notification every time the status of
     * Voice IME may have changed. The {@link Listener} should
     * update the UI to reflect the current status of Voice IME. When
     * {@link Listener} is registered,
     * {@link #unregister(Context)} must be called when the IME is dismissed
     * {@link InputMethodService#onDestroy()}.
     */
    public void register(final Listener listener) {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    listener.onVoiceImeEnabledStatusChange();
                }
            }
        };
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mInputMethodService.registerReceiver(mReceiver, filter);
    }

    /**
     * Unregister the {@link Listener}.
     */
    public void unregister(Context context) {
        if (mReceiver != null) {
            mInputMethodService.unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    public interface Listener {

        /**
         * The enable status of Voice IME may have changed.
         */
        void onVoiceImeEnabledStatusChange();
    }
}
