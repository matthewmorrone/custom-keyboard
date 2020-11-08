package com.custom.keyboard.unicode;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.custom.keyboard.R;
import com.custom.keyboard.unicode.UnicodeGridView.OnUnicodeClickedListener;
import com.custom.keyboard.unicode.UnicodeGridView.OnUnicodeLongClickedListener;

import java.util.List;

class UnicodeAdapter extends ArrayAdapter<Unicode> {
    OnUnicodeClickedListener unicodeClickedListener;
    OnUnicodeLongClickedListener unicodeLongClickedListener;

    public UnicodeAdapter(Context context, List<Unicode> data) {
        super(context, R.layout.unicode_item, data);
    }

    public UnicodeAdapter(Context context, Unicode[] data) {
        super(context, R.layout.unicode_item, data);
    }

    public void setUnicodeClickedListener(UnicodeGridView.OnUnicodeClickedListener listener) {
        this.unicodeClickedListener = listener;
    }

    public void setUnicodeLongClickedListener(UnicodeGridView.OnUnicodeLongClickedListener listener) {
        this.unicodeLongClickedListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = View.inflate(getContext(), R.layout.unicode_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = v.findViewById(R.id.unicode_icon);
            v.setTag(holder);
        }
        Unicode unicode = getItem(position);
        ViewHolder holder = (ViewHolder)v.getTag();
        if (unicode != null) {
            if (unicode.description != null && !unicode.description.isEmpty()) {
                holder.icon.setText(unicode.description);
                holder.icon.setWidth(parent.getWidth());
                holder.icon.setGravity(Gravity.LEFT);
                holder.icon.setTextSize(20);
                holder.icon.setEllipsize(TextUtils.TruncateAt.END);
                holder.icon.setSingleLine();
            }
            else {
                holder.icon.setText(unicode.getUnicode());
            }
        }
        holder.icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                unicodeClickedListener.onUnicodeClicked(getItem(position));
            }
        });
        holder.icon.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                unicodeLongClickedListener.onUnicodeLongClicked(getItem(position));
                return true;
            }
        });
        return v;
    }

    static class ViewHolder {
        TextView icon;
    }
}