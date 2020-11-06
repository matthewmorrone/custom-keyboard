package com.custom.keyboard.emoticon;

import android.content.Context;

public interface EmoticonRecents {
    public void addRecentEmoticon(Context context, Emoticon emoticon);
    public void removeRecentEmoticon(Context context, Emoticon emoticon);
}
