package com.custom.keyboard;

import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

public class CustomInputConnection extends InputConnectionWrapper {

    public InputConnection inputConnection;

    public CustomInputConnection(InputConnection inputConnection, boolean mutable) {
        super(inputConnection, mutable);
        this.inputConnection = inputConnection;
    }

    public CharSequence getTextBeforeCursor(int n) {
        return super.getTextBeforeCursor(n, 0);
    }

    @Override
    public CharSequence getTextBeforeCursor(int n, int flags) {
        return super.getTextBeforeCursor(n, flags);
    }

    public CharSequence getTextAfterCursor(int n) {
        return super.getTextAfterCursor(n, 0);
    }

    @Override
    public CharSequence getTextAfterCursor(int n, int flags) {
        return super.getTextAfterCursor(n, flags);
    }
}