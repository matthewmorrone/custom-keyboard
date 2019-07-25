package com.vlath.keyboard;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Vlad on 6/20/2017.
 */

public class Preference extends AppCompatActivity {
   @Override
    public void onCreate(Bundle h){
       super.onCreate(h);
       setContentView(R.layout.pref);
       getFragmentManager().beginTransaction().replace(R.id.main, new PreferenceFragment()).commit();
    }
}
