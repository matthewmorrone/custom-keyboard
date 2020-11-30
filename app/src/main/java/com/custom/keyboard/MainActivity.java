package com.custom.keyboard;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.custom.keyboard.util.KeyboardUtils;
import com.custom.keyboard.util.ToastIt;
import com.custom.keyboard.util.Util;

import java.util.Objects;

public class MainActivity extends Activity {

    EditText editText;
    TextView errorOutput;
    LinearLayout errorLayout;
    LinearLayout inputLayout;
    public boolean keyboardVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        editText = findViewById(R.id.edit_text);
        errorOutput = findViewById(R.id.error_output);
        errorLayout = findViewById(R.id.error_layout);
        inputLayout = findViewById(R.id.text_inputs);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("FindReplace"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("DebugHelper"));

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                keyboardVisible = isVisible;
                // System.out.println("keyboard visible: "+keyboardVisible);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Util.orNull(intent.getAction(), "").equals("FindReplace")) {
                // @TODO: implement ability to limit to selected text
                String oldText = Util.orNull(intent.getStringExtra("oldText"), "");
                String newText = Util.orNull(intent.getStringExtra("newText"), "");
                ToastIt.text(context, newText);
                editText.setText(newText);
            }
            if (Util.orNull(intent.getAction(), "").equals("DebugHelper")) {
                if (intent.hasExtra("clear")) {
                    errorOutput.setText("");
                }
                if (intent.hasExtra("data")) {
                    if (Objects.requireNonNull(intent.getStringExtra("data")).trim().isEmpty()) {
                        return;
                    }
                    errorLayout.setVisibility(View.VISIBLE);
                    if (errorOutput.getText().length() > 0) {
                        errorOutput.setText(errorOutput.getText()+"\n");
                    }
                    errorOutput.setText(errorOutput.getText()+intent.getStringExtra("data"));
                }
            }
        }
    };

    public void settings(View v) {
        Intent intent = new Intent(this, PreferenceActivity.class);
        startActivity(intent);
    }

    public void enable(View v) {
        this.startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
    }

    public void select(View v) {
        InputMethodManager imeManager = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imeManager != null) {
            imeManager.showInputMethodPicker();
        }
    }

    private void launchHomeScreen() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void changeStatusBarColor(boolean on) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (on) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        else {
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    public void toggleDetails(View view) {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
        }
        else {
            errorLayout.setVisibility(View.VISIBLE);
        }
    }

    public void toggleExtras(View view) {
        if (inputLayout.getVisibility() == View.VISIBLE) {
            inputLayout.setVisibility(View.GONE);
        }
        else {
            inputLayout.setVisibility(View.VISIBLE);
        }
    }
}