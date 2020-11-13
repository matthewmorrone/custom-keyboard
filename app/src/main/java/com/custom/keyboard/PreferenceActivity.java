package com.custom.keyboard;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

public class PreferenceActivity extends Activity {
    @Override
    public void onCreate(Bundle h) {
        super.onCreate(h);
        setContentView(R.layout.pref);
        getFragmentManager().beginTransaction().replace(R.id.main, new PreferenceFragment()).commit();
        permissions();
    }

    public void permissions() {
        ActivityCompat.requestPermissions(PreferenceActivity.this, new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);
    }
}
