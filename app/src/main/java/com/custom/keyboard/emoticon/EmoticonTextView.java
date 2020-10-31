package com.custom.keyboard.emoticon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.widget.TextView;

import com.custom.keyboard.R;

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

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            mEmoticonSize = (int)getTextSize();
        }
        else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Emoticon);
            mEmoticonSize = (int)a.getDimension(R.styleable.Emoticon_emoticonSize, getTextSize());
            mTextStart = a.getInteger(R.styleable.Emoticon_emoticonTextStart, 0);
            mTextLength = a.getInteger(R.styleable.Emoticon_emoticonTextLength, -1);
            a.recycle();
        }
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        // EmoticonHandler.addEmoticons(getContext(), builder, mEmoticonSize, mTextStart, mTextLength);
        super.setText(builder, type);
    }

    public void setEmoticonSize(int pixels) {
        mEmoticonSize = pixels;
    }
}
