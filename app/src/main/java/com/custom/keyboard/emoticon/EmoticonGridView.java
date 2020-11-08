package com.custom.keyboard.emoticon;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.custom.keyboard.R;
import com.custom.keyboard.emoticon.categories.EmoticonCategories;

import java.util.Arrays;

public class EmoticonGridView {
    public View rootView;
    EmoticonPopup mEmoticonPopup;
    EmoticonRecents mRecents;
    Emoticon[] mData;

    public EmoticonGridView(Context context, Emoticon[] emoticons, EmoticonRecents recents, EmoticonPopup emoticonPopup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mEmoticonPopup = emoticonPopup;
        rootView = inflater.inflate(R.layout.emoticon_grid, null);
        setRecents(recents);
        GridView gridView = (GridView)rootView.findViewById(R.id.emoticon_grid_view);
        if (emoticons == null) {
            mData = EmoticonCategories.People;
        }
        else {
            Object[] o = (Object[])emoticons;
            mData = Arrays.asList(o).toArray(new Emoticon[o.length]);
        }
        EmoticonAdapter mAdapter = new EmoticonAdapter(rootView.getContext(), mData);
        mAdapter.setEmoticonClickedListener(new OnEmoticonClickedListener() {
            @Override
            public void onEmoticonClicked(Emoticon emoticon) {
                if (mEmoticonPopup.onEmoticonClickedListener != null) {
                    mEmoticonPopup.onEmoticonClickedListener.onEmoticonClicked(emoticon);
                }
                if (mRecents != null) {
                    mRecents.addRecentEmoticon(rootView.getContext(), emoticon);
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
        gridView.setAdapter(mAdapter);
    }

    private void setRecents(EmoticonRecents recents) {
        mRecents = recents;
    }

    public interface OnEmoticonClickedListener {
        void onEmoticonClicked(Emoticon emoticon);
    }

    public interface OnEmoticonLongClickedListener {
        void onEmoticonLongClicked(Emoticon emoticon);
    }

}
