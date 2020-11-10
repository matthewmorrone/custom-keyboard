package com.custom.keyboard.emoticon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import com.custom.keyboard.R;
import com.custom.keyboard.Util;

@SuppressLint("AppCompatCustomView")
public class EmoticonTextView extends TextView {
    private int mEmoticonSize;
    private int mTextStart = 0;
    private int mTextLength = -1;

    public EmoticonTextView(Context context) {
        super(context);
        init(null);
    }

    public EmoticonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmoticonTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
    float textSize = Float.parseFloat(Util.orNull(sharedPreferences.getString("emoticon_font_size", "24"), "24"));

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            mEmoticonSize = (int)getTextSize();
        }
        else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.emoticon);
            // mEmoticonSize = (int)a.getDimension(R.styleable.emoticon_emoticonSize, getTextSize());
            mEmoticonSize = (int)textSize;
            System.out.println(mEmoticonSize);
            mTextStart = a.getInteger(R.styleable.emoticon_emoticonTextStart, 0);
            mTextLength = a.getInteger(R.styleable.emoticon_emoticonTextLength, -1);
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

    public void setEmoticonSize(int pixels) {
        mEmoticonSize = pixels;
    }
}
