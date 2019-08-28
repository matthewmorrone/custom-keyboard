package com.vlath.keyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Preference extends AppCompatActivity {
  @Override
  public void onCreate(Bundle h) {
    super.onCreate(h);
    setContentView(R.layout.pref);
    getFragmentManager().beginTransaction().replace(R.id.main, new PreferenceFragment()).commit();
  }
}
