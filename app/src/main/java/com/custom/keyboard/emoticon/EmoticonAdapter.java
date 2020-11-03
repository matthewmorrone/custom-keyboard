package com.custom.keyboard.emoticon;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.custom.keyboard.R;
import com.custom.keyboard.emoticon.EmoticonGridView.OnEmoticonClickedListener;

import java.util.List;

class EmoticonAdapter extends ArrayAdapter<Emoticon> {
    OnEmoticonClickedListener emoticonClickedListener;

    public EmoticonAdapter(Context context, List<Emoticon> data) {
        super(context, R.layout.emoticon_item, data);
    }

    public EmoticonAdapter(Context context, Emoticon[] data) {
        super(context, R.layout.emoticon_item, data);
    }

    public void setEmoticonClickedListener(OnEmoticonClickedListener listener) {
        this.emoticonClickedListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = View.inflate(getContext(), R.layout.emoticon_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = v.findViewById(R.id.emoticon_icon);
            v.setTag(holder);
        }
        Emoticon emoticon = getItem(position);
        ViewHolder holder = (ViewHolder)v.getTag();
        holder.icon.setText(emoticon.getEmoticon());
        holder.icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                emoticonClickedListener.onEmoticonClicked(getItem(position));
            }
        });
        return v;
    }

    static class ViewHolder {
        TextView icon;
    }
}