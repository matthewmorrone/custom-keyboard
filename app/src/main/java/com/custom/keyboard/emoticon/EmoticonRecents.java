package com.custom.keyboard.emoticon;

import android.content.Context;

public interface EmoticonRecents {
    void addRecentEmoticon(Context context, Emoticon emoticon);
    void removeRecentEmoticon(Context context, Emoticon emoticon);
}
