package com.custom.keyboard.dialog;

import androidx.annotation.NonNull;

public class MultiSelection {
    private Boolean check = false;
    private String label;
    private String value;

    public MultiSelection() {

    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return getValue()+" "+getCheck();
    }
}
