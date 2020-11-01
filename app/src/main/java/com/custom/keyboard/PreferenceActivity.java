package com.custom.keyboard;

import android.app.Activity;
import android.os.Bundle;

public class PreferenceActivity extends Activity {
    @Override
    public void onCreate(Bundle h) {
        super.onCreate(h);
        setContentView(R.layout.pref);
        getFragmentManager().beginTransaction().replace(R.id.main, new PreferenceFragment()).commit();
    }
}
