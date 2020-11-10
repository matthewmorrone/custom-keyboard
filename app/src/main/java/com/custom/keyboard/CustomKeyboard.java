package com.custom.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.preference.PreferenceManager;
import android.view.inputmethod.EditorInfo;

public class CustomKeyboard extends Keyboard implements Comparable<CustomKeyboard> {

    static final int KEYCODE_OPTIONS = -100;
    static final int KEYCODE_LAYOUT_SWITCH = -101;

    SharedPreferences sharedPreferences;
    int xmlLayoutResId = R.layout.primary;
    int order = 1024;


    String title;

    private Key mEnterKey;
    private Key mSpaceKey;
    private static short rowNumber = 6;

    private Key mModeChangeKey;

    private Key mLanguageSwitchKey;

    private Key mSavedModeChangeKey;

    private Key mSavedLanguageSwitchKey;

    public CustomKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
        this.xmlLayoutResId = xmlLayoutResId;
    }

    public CustomKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }

    public CustomKeyboard(Context context, int xmlLayoutResId, String title, String label, int order) {
        super(context, xmlLayoutResId);
        this.xmlLayoutResId = xmlLayoutResId;
        // this.key = Util.toLowerCase(title);
        this.title = !Util.containsLowerCase(title) ? title : Util.toTitleCase(title);
        // this.label = !Util.containsLowerCase(label) ? label : Util.toTitleCase(label);
        this.order = order;
        if (this.order < 0) {
            this.order = 1024 + this.order;
        }
        // this.layoutId = xmlLayoutResId;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public int getOrder() {
        return order;
    }

    public CustomKeyboard setOrder(int order) {
        this.order = order;
        return this;
    }

    @Override
    public int compareTo(CustomKeyboard kb) {
        if (this.order == 0) return -1024;
        if (sharedPreferences.getBoolean("custom_order", false)) {
            if (this.order != kb.order) {
                return this.order - kb.order;
            }
        }
        return this.title.compareTo(kb.title);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        Key key = new CustomKey(res, parent, x, y, parser);
        if (key.codes[0] == 10) {
            mEnterKey = key;
        }
        else if (key.codes[0] == ' ') {
            mSpaceKey = key;
        }
        else if (key.codes[0] == Keyboard.KEYCODE_MODE_CHANGE) {
            mModeChangeKey = key;
            mSavedModeChangeKey = new CustomKey(res, parent, x, y, parser);
        }
        else if (key.codes[0] == CustomKeyboard.KEYCODE_LAYOUT_SWITCH) {
            mLanguageSwitchKey = key;
            mSavedLanguageSwitchKey = new CustomKey(res, parent, x, y, parser);
        }
        return key;
    }

    /**
     * Dynamically change the visibility of the language switch key (a.k.a. globe key).
     *
     * @param visible True if the language switch key should be visible.
     */
    void setLanguageSwitchKeyVisibility(boolean visible) {
        if (visible) {
            // The language switch key should be visible. Restore the size of the mode change key
            // and language switch key using the saved layout.
            mModeChangeKey.width = mSavedModeChangeKey.width;
            mModeChangeKey.x = mSavedModeChangeKey.x;
            mLanguageSwitchKey.width = mSavedLanguageSwitchKey.width;
            mLanguageSwitchKey.icon = mSavedLanguageSwitchKey.icon;
            mLanguageSwitchKey.iconPreview = mSavedLanguageSwitchKey.iconPreview;
        }
        else {
            // The language switch key should be hidden. Change the width of the mode change key
            // to fill the space of the language key so that the user will not see any strange gap.
            mModeChangeKey.width = mSavedModeChangeKey.width + mSavedLanguageSwitchKey.width;
            mLanguageSwitchKey.width = 0;
            mLanguageSwitchKey.icon = null;
            mLanguageSwitchKey.iconPreview = null;
        }
    }

    void setImeOptions(Resources res, int options) {
        if (mEnterKey == null) {
            return;
        }

        switch (options & (EditorInfo.IME_MASK_ACTION | EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
            case EditorInfo.IME_ACTION_GO:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = Resources.getSystem().getDrawable(R.drawable.ic_go, null);
            break;
            case EditorInfo.IME_ACTION_NEXT:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = Resources.getSystem().getDrawable(R.drawable.ic_next, null);
            break;
            case EditorInfo.IME_ACTION_SEARCH:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = Resources.getSystem().getDrawable(R.drawable.ic_find, null);
            break;
            case EditorInfo.IME_ACTION_SEND:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = Resources.getSystem().getDrawable(R.drawable.ic_send, null);
            break;
            default:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = Resources.getSystem().getDrawable(R.drawable.ic_enter, null);
            break;
        }
    }

    public void setRowNumber(short number) {
        rowNumber = number;
    }

    void setSpaceIcon(final Drawable icon) {
        if (mSpaceKey != null) {
            mSpaceKey.icon = icon;
        }
    }

    public void changeKeyHeight(double height_modifier) {
        int height = 0;
        for (Keyboard.Key key : getKeys()) {
            key.height *= height_modifier;
            key.y *= height_modifier;
            height = key.height;
        }
        setKeyHeight(height);
        getNearestKeys(0, 0); //somehow adding this fixed a weird bug where bottom row keys could not be pressed if keyboard height is too tall.. from the Keyboard source code seems like calling this will recalculate some values used in keypress detection calculation
    }


    /**
     * This piece of code is intended to help us to resize the keyboard at runtime,
     * thus giving us the opportunity to customize its height. It's a bit tricky though.
     * And StackOverflow inspired me to be honest.
     **/

    @Override
    public int getHeight() {
        return getKeyHeight() * rowNumber;
    }

    public void setKeyHeight(int height) {
        super.setKeyHeight(height);
    }
}