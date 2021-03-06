package com.custom.keyboard.unicode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import com.custom.keyboard.R;
import com.custom.keyboard.util.Util;

@SuppressLint("AppCompatCustomView")
public class UnicodeTextView extends TextView {
    private int mUnicodeSize;
    private int mTextStart = 0;
    private int mTextLength = -1;

    public UnicodeTextView(Context context) {
        super(context);
        init(null);
    }

    public UnicodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public UnicodeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    float textSize = Float.parseFloat(Util.orNull(sharedPreferences.getString("unicode_font_size", "24"), "24"));

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            mUnicodeSize = (int)getTextSize();
        }
        else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.unicode);
            // mUnicodeSize = (int)a.getDimension(R.styleable.unicode_unicodeTextSize, getTextSize());
            mUnicodeSize = (int)textSize;
            mTextStart = a.getInteger(R.styleable.unicode_unicodeTextStart, 0);
            mTextLength = a.getInteger(R.styleable.unicode_unicodeTextLength, -1);
            a.recycle();
        }
        this.setTextSize(textSize);
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        super.setText(builder, type);
    }

    public void setUnicodeSize(int pixels) {
        mUnicodeSize = pixels;
    }
}
