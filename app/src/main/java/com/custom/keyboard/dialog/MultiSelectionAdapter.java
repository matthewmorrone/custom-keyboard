package com.custom.keyboard.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.custom.keyboard.R;

import java.util.List;

public class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionAdapter.MulitpleSelectionHolder> {
    private Context context;
    private List<MultiSelection> dataList;
    private List<String> selectedFields;
    private int color, textColor;
    private MultiSelectionListener multiSelectionListener;
    private String tag;

    public static class MulitpleSelectionHolder extends RecyclerView.ViewHolder {

        public CheckBox checkBox;
        View line;

        MulitpleSelectionHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox_dialog);
            line = view.findViewById(R.id.linear_multi_dialog);
        }
    }

    public MultiSelectionAdapter(
        Context context,
        MultiSelectionListener listener,
        List<MultiSelection> dataList,
        List<String> selectedFields,
        String tag,
        int color,
        int textColor
    ) {
        this.context = context;
        this.multiSelectionListener = listener;
        this.dataList = dataList;
        this.selectedFields = selectedFields; // checkExist(selectedItems);
        this.tag = tag;
        this.color = color;
        this.textColor = textColor;
    }

    @Override
    public MulitpleSelectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_multi_selection, parent, false);
        return new MulitpleSelectionHolder(itemView);
    }

    public void onBindViewHolder(final MulitpleSelectionHolder holder, final int position) {
        holder.checkBox.setText(dataList.get(position).getValue());
        if (color != 0) {
            try {
                holder.checkBox.setButtonTintList(ColorStateList.valueOf(color));
                holder.line.setBackgroundColor(color);
            }
            catch (Exception e) {
                if (multiSelectionListener != null) {
                    multiSelectionListener.onMultiDialogError(e.toString(), tag);
                }
            }
        }
        if (textColor != 0) {
            try {
                holder.checkBox.setTextColor(ColorStateList.valueOf(textColor));
            }
            catch (Exception e) {
                if (multiSelectionListener != null) {
                    multiSelectionListener.onMultiDialogError(e.toString(), tag);
                }
            }
        }

        holder.setIsRecyclable(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.get(position).setCheck(holder.checkBox.isChecked());
            }
        });
        holder.checkBox.setChecked(dataList.get(position).getCheck());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /*
    public boolean checkExist(String dataString) {
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                List<String> assets = new ArrayList<String>(Arrays.asList(selectedItems.split(",")));
                if (assets.size() > 0 && !selectedItems.equals("")) {
                    for (int j = 0; j < assets.size(); j++) {
                        if (dataList.get(i).getTitle().equals(assets.get(j))) {
                            dataList.get(i).setCheck(true);
                        }
                    }
                }
            }
        }
        else {
            if (multiSelectionListener != null) {
                multiSelectionListener.onMultiDialogError("List is null or empty", tag);
            }
        }
        return false;
    }
    */
}