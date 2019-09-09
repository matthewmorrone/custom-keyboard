package com.vlath.keyboard;

import android.inputmethodservice.KeyboardView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.vlath.keyboard.PCKeyboard.hexBuffer;

public class CustomKeyboard extends KeyboardView {

	Paint mPaint = new Paint();
	private static Context kcontext;

	public void assignCustomKeys() {
		kcontext = getContext();
		List<Key> keys = getKeyboard().getKeys();

		for (Key key : keys) {
			try {
				if (key.codes[0] == -501) {
					key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k1", "");
				}
				if (key.codes[0] == -502) {
					key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k2", "");
				}
				if (key.codes[0] == -503) {
					key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k3", "");
				}
				if (key.codes[0] == -504) {
					key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k4", "");
				}
				if (key.codes[0] == -505) {
					key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k5", "");
				}
				if (key.codes[0] == -506) {
					key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k6", "");
				}
				if (key.codes[0] == -507) {
					key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k7", "");
				}
				if (key.codes[0] == -508) {
					key.label = PreferenceManager.getDefaultSharedPreferences(kcontext).getString("k8", "");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public CustomKeyboard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomKeyboard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected boolean onLongPress(Key key) {
		if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
			getOnKeyboardActionListener().onKey(-100, null);
			return true;
		}
		return super.onLongPress(key);
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		System.out.println("Redrawing Canvas: " + canvas);
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setTextSize(28);
		mPaint.setColor(Color.parseColor("#a5a7aa"));

		assignCustomKeys();

		List<Key> keys = getKeyboard().getKeys();
		for (Key key : keys) {
			if (hexBuffer.length() > 0) {
				if (key.codes[0] == -2001) {
					try {
						key.label = StringUtils.leftPad(hexBuffer, 4, "0");
					}
					catch (NumberFormatException e) {
						key.label = "0x0000";
					}
				}
				if (key.codes[0] == -2002) {
					try {
						key.label = String.valueOf((char) (int) Integer.decode("0x" + StringUtils.leftPad(hexBuffer, 4, "0")));
					}
					catch (NumberFormatException e) {
						key.label = "";
					}
				}
			}
		}
	}
}