package com.custom.keyboard.dialog;

import java.util.ArrayList;

public interface MultiSelectionListener {
    void onMultiDialogItemsSelected(String s, String tag, ArrayList<String> selectedItemList);
    void onMultiDialogError(String error, String tag);
}