package com.custom.keyboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class Completions  extends AppCompatActivity {
    TextView result1;
    TextView result2;
    TextView result3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completions);

        result1 = (TextView) findViewById(R.id.textView1);
        result2 = (TextView) findViewById(R.id.textView2);
        result3 = (TextView) findViewById(R.id.textView3);


        result1.setOnClickListener((View.OnClickListener) v -> {
            //Creating the instance of PopupMenu

        });
    }
}