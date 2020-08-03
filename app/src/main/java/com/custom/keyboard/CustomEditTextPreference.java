package com.custom.keyboard;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import top.defaults.colorpicker.ColorPickerPopup;

public class CustomEditTextPreference extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_edit_text_preference);
        findViewById(R.id.editSpaceBar).setOnClickListener(this);
        findViewById(R.id.editTabKey).setOnClickListener(this);
        findViewById(R.id.editEnterKey).setOnClickListener(this);
        findViewById(R.id.editForegroundColor).setOnClickListener(this);
        findViewById(R.id.editBackgroundColor).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());


        if (view.getId() == R.id.editSpaceBar) {
            new EditSpacebarPopup(this, "space").show();
        }
        else if (view.getId() == R.id.editTabKey) {
            new EditSpacebarPopup(this, "tab").show();
        }
        else if (view.getId() == R.id.editEnterKey) {
            new EditSpacebarPopup(this, "enter").show();
        }
        else if (view.getId() == R.id.editForegroundColor) {
            new ColorPickerPopup.Builder(this)
                .initialColor(sharedPreferences.getInt("fg", -1677216)) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                // .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(view, new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                        sharedPreferenceEditor.putInt("fg", color);
                        sharedPreferenceEditor.apply();
                    }
                });
        }
        else if (view.getId() == R.id.editBackgroundColor) {
            new ColorPickerPopup.Builder(this)
                .initialColor(sharedPreferences.getInt("bg", -1)) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                // .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(view, new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                        sharedPreferenceEditor.putInt("bg", color);
                        sharedPreferenceEditor.apply();
                    }
                });
        }
    }
}
