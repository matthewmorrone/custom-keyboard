package com.custom.keyboard;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class PreferenceActivity extends Activity {
    @Override
    public void onCreate(Bundle h) {
        super.onCreate(h);
        setContentView(R.layout.pref);
        getFragmentManager().beginTransaction().replace(R.id.main, new PreferenceFragment()).commit();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("RequestPermissions"));
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Util.orNull(intent.getAction(), "").equals("RequestPermissions")) {
                permissions();
            }
        }
    };

    public void permissions() {
        ActivityCompat.requestPermissions(PreferenceActivity.this, new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);
    }
}
