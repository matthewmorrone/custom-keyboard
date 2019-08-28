package com.vlath.keyboard;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activate);
    TextView t2 = findViewById(R.id.textView2);
    t2.setMovementMethod(LinkMovementMethod.getInstance());
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
}