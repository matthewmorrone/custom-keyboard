package com.custom.keyboard.dialog;

public interface SingleSelectionListener {
    void onSingleDialogItemSelected(String s, int position, String tag);
    void onSingleDialogError(String error, String tag);
}

