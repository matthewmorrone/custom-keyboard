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
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class Main extends Activity {

    EditText editText;
    TextView errorOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        // requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, 1);

        editText = findViewById(R.id.editText);
        errorOutput = findViewById(R.id.errorOutput);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("FindReplace"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("DebugHelper"));
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Util.orNull(intent.getAction(), "").equals("FindReplace")) {
                // @TODO: implement ability to limit to selected text
                String oldText = Util.orNull(intent.getStringExtra("oldText"), "");
                String newText = Util.orNull(intent.getStringExtra("newText"), "");
                editText.setText(newText);
            }
            if (Util.orNull(intent.getAction(), "").equals("DebugHelper")) {
                errorOutput.setText("");
                if (intent.hasExtra("exception")) {
                    errorOutput.setText(errorOutput.getText()+"exception: "+intent.getStringExtra("output"));
                }
                if (intent.hasExtra("message")) {
                    errorOutput.setText(errorOutput.getText()+"\nmessage: "+intent.getStringExtra("output"));
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
}