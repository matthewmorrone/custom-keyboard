package com.custom.keyboard.unicode;

import android.content.Context;
import android.widget.GridView;

import com.custom.keyboard.R;

public class UnicodeRecentsGridView extends UnicodeGridView implements UnicodeRecents {
    UnicodeAdapter mAdapter;

    public UnicodeRecentsGridView(Context context, Unicode[] unicode, UnicodeRecents recents, UnicodePopup unicodePopup) {
        super(context, unicode, recents, unicodePopup);
        UnicodeRecentsManager recentsManager = UnicodeRecentsManager.getInstance(rootView.getContext());
        mAdapter = new UnicodeAdapter(rootView.getContext(), recentsManager);
        mAdapter.setUnicodeClickedListener(new OnUnicodeClickedListener() {
            @Override
            public void onUnicodeClicked(Unicode unicode) {
                if (mUnicodePopup.onUnicodeClickedListener != null) {
                    mUnicodePopup.onUnicodeClickedListener.onUnicodeClicked(unicode);
                }
            }
        });
        mAdapter.setUnicodeLongClickedListener(new OnUnicodeLongClickedListener() {
            @Override
            public void onUnicodeLongClicked(Unicode unicode) {
                if (mUnicodePopup.onUnicodeLongClickedListener != null) {
                    mUnicodePopup.onUnicodeLongClickedListener.onUnicodeLongClicked(unicode);
                }
            }
        });
        GridView gridView = rootView.findViewById(R.id.unicode_grid_view);
        gridView.setAdapter(mAdapter);
    }

    @Override
    public void addRecentUnicode(Context context, Unicode unicode) {
        UnicodeRecentsManager recents = UnicodeRecentsManager.getInstance(context);
        recents.push(unicode);

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void removeRecentUnicode(Context context, Unicode unicode) {
        UnicodeRecentsManager recents = UnicodeRecentsManager.getInstance(context);
        recents.remove(unicode);

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
}