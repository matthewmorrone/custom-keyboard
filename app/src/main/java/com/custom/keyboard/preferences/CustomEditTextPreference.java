package com.custom.keyboard.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import android.preference.EditTextPreference;

import com.custom.keyboard.R;

public class CustomEditTextPreference extends EditTextPreference {
    public CustomEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public CustomEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomEditTextPreference(Context context) {
        super(context);
    }
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        TextView textView = view.findViewById(R.id.title);
        if (textView != null) {
            textView.setSingleLine(false);
        }
    }
}