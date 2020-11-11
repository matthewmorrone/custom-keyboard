package com.custom.keyboard;

import android.Manifest;
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

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.mozilla.javascript.tools.jsc.Main;

public class MainActivity extends Activity {

    EditText editText;
    TextView errorOutput;
    LinearLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        // requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, 1);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);

        // ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        editText = findViewById(R.id.editText);
        errorOutput = findViewById(R.id.errorOutput);
        errorLayout = findViewById(R.id.errorLayout);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("FindReplace"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("DebugHelper"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        // LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("FindReplace"));
        // LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("DebugHelper"));
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
                if (intent.hasExtra("clear")) {
                    errorOutput.setText("");
                }
                if (intent.hasExtra("data")) {
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
}