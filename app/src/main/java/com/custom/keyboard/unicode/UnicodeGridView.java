package com.custom.keyboard.unicode;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.custom.keyboard.R;

import java.util.Arrays;

public class UnicodeGridView {
    public View rootView;
    UnicodePopup mUnicodePopup;
    UnicodeRecents mRecents;
    Unicode[] mData;

    public UnicodeGridView(Context context, Unicode[] unicode, UnicodeRecents recents, UnicodePopup unicodePopup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mUnicodePopup = unicodePopup;
        rootView = inflater.inflate(R.layout.unicode_grid, null);
        setRecents(recents);
        GridView gridView = rootView.findViewById(R.id.Unicode_GridView);
        if (unicode == null) {
            mData = UnicodeData.getData();
        }
        else {
            Object[] o = unicode;
            mData = Arrays.asList(o).toArray(new Unicode[o.length]);
        }
        UnicodeAdapter mAdapter = new UnicodeAdapter(rootView.getContext(), mData);
        mAdapter.setUnicodeClickedListener(new OnUnicodeClickedListener() {
            @Override
            public void onUnicodeClicked(Unicode unicode) {
                if (mUnicodePopup.onUnicodeClickedListener != null) {
                    mUnicodePopup.onUnicodeClickedListener.onUnicodeClicked(unicode);
                }
                if (mRecents != null) {
                    mRecents.addRecentUnicode(rootView.getContext(), unicode);
                }
            }
        });
        gridView.setAdapter(mAdapter);
    }

    private void setRecents(UnicodeRecents recents) {
        mRecents = recents;
    }

    public interface OnUnicodeClickedListener {
        void onUnicodeClicked(Unicode unicode);
    }

}
