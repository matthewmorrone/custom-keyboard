package com.custom.keyboard.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.custom.keyboard.R;
import com.custom.keyboard.util.Calculator;
import com.custom.keyboard.util.Util;

public class SeekPreference extends Preference implements OnSeekBarChangeListener {
    private SeekBar mSeekBar;
    private TextView mMin;
    private TextView mMax;
    private TextView mValue;

    private String key = "";

    // private int mProgress;

    private int min = 0;
    private int max = 100;
    private int step = 5;
    private int value = 50;

    public int getMin() { return min; }
    public void setMin(int min) { this.min = min; }
    public int getMax() { return max; }
    public void setMax(int max) { this.max = max; }
    public int getStep() { return step; }
    public void setStep(int step) { this.step = step; }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        if (shouldPersist()) {
            persistInt(value);
        }
        if (value != this.value) {
            this.value = value;
            notifyChanged();
        }
    }

    String namespace = "http://schemas.android.com/apk/res-auto";

    public SeekPreference(Context context) {
        this(context, null, 0);
    }

    public SeekPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(R.layout.seek_dialog);

        key = Util.orNull(attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "key"), "");

        setMin(Integer.parseInt(Util.orNull(attrs.getAttributeValue(namespace, "min"), "0")));
        setMax(Integer.parseInt(Util.orNull(attrs.getAttributeValue(namespace, "max"), "100")));
        setStep(Integer.parseInt(Util.orNull(attrs.getAttributeValue(namespace, "step"), "5")));
        setValue(Integer.parseInt(Util.orNull(attrs.getAttributeValue(namespace, "value"), "100")));
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        mMin = (TextView)view.findViewById(R.id.min);
        mMax = (TextView)view.findViewById(R.id.max);
        mValue = (TextView)view.findViewById(R.id.value);
        mSeekBar = (SeekBar)view.findViewById(R.id.seekbar);

        // System.out.println(mSeekBar.getMin()+" "+mSeekBar.getMax()+" "+mSeekBar.getProgress());
        mSeekBar.setProgress(getValue());
        mSeekBar.setOnSeekBarChangeListener(this);

        mMin.setText(String.valueOf(getMin()));
        mSeekBar.setMin(getMin());

        mMax.setText(String.valueOf(getMax()));
        mSeekBar.setMax(getMax());

        mValue.setText(String.valueOf(getValue()));
        mSeekBar.setProgress(getValue());
        // mValue.setText(String.valueOf(scale(getValue(), 0, 100, getMin(), getMax())));
    }
    public static int scale(final int valueIn, final int baseMin, final int baseMax, final int limitMin, final int limitMax) {
        return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        setValue(Calculator.round(progress, getStep()));
        mValue.setText(String.valueOf(getValue()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        setValue(Calculator.round(seekBar.getProgress(), getStep()));
        mValue.setText(String.valueOf(getValue()));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        setValue(Calculator.round(seekBar.getProgress(), getStep()));
        mValue.setText(String.valueOf(getValue()));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedInt(this.value) : (Integer)defaultValue);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 0);
    }
}