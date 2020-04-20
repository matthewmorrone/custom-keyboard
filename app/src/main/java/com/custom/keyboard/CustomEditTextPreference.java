package com.custom.keyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CustomEditTextPreference extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_edit_text_preference);
        findViewById(R.id.editSpaceBar).setOnClickListener(this);
        findViewById(R.id.editTabKey).setOnClickListener(this);
        findViewById(R.id.editEnterKey).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.editSpaceBar) {
            new EditSpacebarPopup(this, "space").show();
        }
        else if (view.getId() == R.id.editTabKey) {
            new EditSpacebarPopup(this, "tab").show();
        }
        else if (view.getId() == R.id.editEnterKey) {
            new EditSpacebarPopup(this, "enter").show();
        }
    }
}
