package com.custom.keyboard.unicode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import com.custom.keyboard.R;

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

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            mUnicodeSize = (int)getTextSize();
        }
        else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Unicode);
            mUnicodeSize = (int)a.getDimension(R.styleable.Unicode_unicodeSize, getTextSize());
            mTextStart = a.getInteger(R.styleable.Unicode_unicodeTextStart, 0);
            mTextLength = a.getInteger(R.styleable.Unicode_unicodeTextLength, -1);
            a.recycle();
        }
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        // UnicodeHandler.addUunicode(getContext(), builder, mUnicodeSize, mTextStart, mTextLength);
        super.setText(builder, type);
    }

    public void setUnicodeSize(int pixels) {
        mUnicodeSize = pixels;
    }
}
