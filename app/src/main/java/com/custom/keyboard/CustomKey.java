package com.custom.keyboard;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

import static android.inputmethodservice.Keyboard.KEYCODE_CANCEL;

public class CustomKey extends Keyboard.Key {

    public CustomKey(Resources res, Keyboard.Row parent, int x, int y, XmlResourceParser parser) {
        super(res, parent, x, y, parser);
    }

    @Override
    public boolean isInside(int x, int y) {
        return super.isInside(x, codes[0] == KEYCODE_CANCEL ? y - 10 : y);
    }

    private final static int[] KEY_STATE_NORMAL_ON = {
        android.R.attr.state_checkable,
        android.R.attr.state_checked
    };

    private final static int[] KEY_STATE_PRESSED_ON = {
        android.R.attr.state_pressed,
        android.R.attr.state_checkable,
        android.R.attr.state_checked
    };

    private final static int[] KEY_STATE_NORMAL_OFF = {
        android.R.attr.state_checkable
    };

    private final static int[] KEY_STATE_PRESSED_OFF = {
        android.R.attr.state_pressed,
        android.R.attr.state_checkable
    };

    private final static int[] KEY_STATE_FUNCTION = {
        android.R.attr.state_single
    };

    private final static int[] KEY_STATE_FUNCTION_PRESSED = {
        android.R.attr.state_pressed,
        android.R.attr.state_single
    };

    private final static int[] KEY_STATE_NORMAL = {};

    private final static int[] KEY_STATE_PRESSED = {
        android.R.attr.state_pressed
    };

    @Override
    public int[] getCurrentDrawableState() {
        int[] states = KEY_STATE_NORMAL;

        if (on) {
            if (pressed) states = KEY_STATE_PRESSED_ON;
            else states = KEY_STATE_NORMAL_ON;
        }
        else {
            if (sticky) {
                if (pressed) states = KEY_STATE_PRESSED_OFF;
                else states = KEY_STATE_NORMAL_OFF;
            }
            else if (modifier) {
                if (pressed) states = KEY_STATE_FUNCTION_PRESSED;
                else states = KEY_STATE_FUNCTION;
            }
            else {
                if (pressed) states = KEY_STATE_PRESSED;
            }
        }
        return states;
    }
}
