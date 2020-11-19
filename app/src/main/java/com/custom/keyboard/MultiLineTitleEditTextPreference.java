package com.custom.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import android.preference.EditTextPreference;

public class MultiLineTitleEditTextPreference extends EditTextPreference {
    public MultiLineTitleEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public MultiLineTitleEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiLineTitleEditTextPreference(Context context) {
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