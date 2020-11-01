package com.custom.keyboard.unicode;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.custom.keyboard.R;
import com.custom.keyboard.unicode.UnicodeGridView.OnUnicodeClickedListener;

import java.util.List;

class UnicodeAdapter extends ArrayAdapter<Unicode> {
    OnUnicodeClickedListener unicodeClickedListener;

    public UnicodeAdapter(Context context, List<Unicode> data) {
        super(context, R.layout.unicode_item, data);
    }

    public UnicodeAdapter(Context context, Unicode[] data) {
        super(context, R.layout.unicode_item, data);
    }

    public void setUnicodeClickedListener(OnUnicodeClickedListener listener) {
        this.unicodeClickedListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = View.inflate(getContext(), R.layout.unicode_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (TextView)v.findViewById(R.id.unicode_icon);
            v.setTag(holder);
        }
        Unicode unicode = getItem(position);
        ViewHolder holder = (ViewHolder)v.getTag();
        holder.icon.setText(unicode.getUnicode());
        holder.icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                unicodeClickedListener.onUnicodeClicked(getItem(position));
            }
        });
        return v;
    }

    static class ViewHolder {
        TextView icon;
    }
}