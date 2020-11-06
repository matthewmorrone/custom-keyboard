package com.custom.keyboard.unicode;

import android.content.Context;

public interface UnicodeRecents {
    void addRecentUnicode(Context context, Unicode unicode);
    void removeRecentUnicode(Context context, Unicode unicode);
}
