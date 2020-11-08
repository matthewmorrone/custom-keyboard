

package com.custom.keyboard.emoticon;

import android.content.Context;
import android.widget.GridView;

import com.custom.keyboard.R;

public class EmoticonRecentsGridView extends EmoticonGridView implements EmoticonRecents {
    EmoticonAdapter mAdapter;

    public EmoticonRecentsGridView(Context context, Emoticon[] emoticons, EmoticonRecents recents, EmoticonPopup emoticonsPopup) {
        super(context, emoticons, recents, emoticonsPopup);
        EmoticonRecentsManager recentsManager = EmoticonRecentsManager.getInstance(rootView.getContext());
        mAdapter = new EmoticonAdapter(rootView.getContext(), recentsManager);
        mAdapter.setEmoticonClickedListener(new OnEmoticonClickedListener() {
            @Override
            public void onEmoticonClicked(Emoticon emoticon) {
                if (mEmoticonPopup.onEmoticonClickedListener != null) {
                    mEmoticonPopup.onEmoticonClickedListener.onEmoticonClicked(emoticon);
                }
            }
        });
        mAdapter.setEmoticonLongClickedListener(new OnEmoticonLongClickedListener() {
            @Override
            public void onEmoticonLongClicked(Emoticon emoticon) {
                if (mEmoticonPopup.onEmoticonLongClickedListener != null) {
                    mEmoticonPopup.onEmoticonLongClickedListener.onEmoticonLongClicked(emoticon);
                }
            }
        });
        GridView gridView = (GridView)rootView.findViewById(R.id.emoticon_grid_view);
        gridView.setAdapter(mAdapter);
    }

    @Override
    public void addRecentEmoticon(Context context, Emoticon emoticon) {
        EmoticonRecentsManager recents = EmoticonRecentsManager.getInstance(context);
        recents.push(emoticon);

        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeRecentEmoticon(Context context, Emoticon emoticon) {
        EmoticonRecentsManager recents = EmoticonRecentsManager.getInstance(context);
        recents.remove(emoticon);

        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }
}
