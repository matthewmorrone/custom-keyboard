package com.custom.keyboard.unicode;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.custom.keyboard.R;

public class NumberPickerPreference extends DialogPreference {
    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDialogLayoutResource(android.R.layout.simple_list_item_single_choice);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);

        setDialogIcon(null);
    }
}