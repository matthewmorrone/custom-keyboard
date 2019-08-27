package com.vlath.keyboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

  private ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activate);
    TextView t2 = findViewById(R.id.textView2);
    t2.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private void changeStatusBarColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
    }
  }

  private void normalStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }
  }

  /**
   * View pager adapter
   */
  public class MyViewPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;

    public MyViewPagerAdapter() {
    }

    @Override
    public int getCount() {
      return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
      return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      View view = (View)object;
      container.removeView(view);
    }
  }

  public boolean getPresentationShown() {

    try {
      return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("presentation", false);
    }
    catch (Exception e) {

      return false;
    }

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