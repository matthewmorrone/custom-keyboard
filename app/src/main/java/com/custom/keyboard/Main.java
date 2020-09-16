package com.custom.keyboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate);
    }

    public void settings(View v) {
        Intent intent = new Intent(this, Preference.class);
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
        else {
            Toast.makeText(this, "Not possible", Toast.LENGTH_LONG).show();
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

    /*
    public boolean getPresentationShown() {
        try {
            return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("presentation", false);
        }
        catch (Exception e) {
            return false;
        }
    }

    public void setPresentationShown() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("presentation", true).apply();
    }
    */

    /*
    public void showTwitterDialog() {
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("shown",false)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.up))
                .setMessage(getString(R.string.follow))
                .setPositiveButton("Follow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/VlathXDA"));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("shown",true).apply();
        }
    }
    */




}