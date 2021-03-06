package com.custom.keyboard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.custom.keyboard.R;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectionDialog extends AppCompatActivity {

    Dialog dialog;
    private Context context;
    private ArrayList<String> list;
    private ArrayList<MultiSelection> multiList = new ArrayList<>();
    private ArrayList<MultiSelection> temp_data_list = new ArrayList<>();
    private List<String> selectedFields;
    private String headerTitle;
    MultiSelectionAdapter dialogAdapter;
    private String currentValue = "", currentPosition = "", tag, hintText;
    private int headerColor, textColor;
    MultiSelectionListener multiSelectionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_selection_dialog);
    }

    private MultiSelectionDialog(Builder builder) {
        context = builder.context;
        list = builder.list;
        temp_data_list.clear();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                MultiSelection multiSelection = new MultiSelection();
                multiSelection.setValue(list.get(i));
                multiList.add(multiSelection);
                temp_data_list.add(multiSelection);
            }
        }
        headerTitle = builder.headerTitle;
        tag = builder.tag;
        hintText = builder.hintText;
        selectedFields = builder.selectedFields;
        headerColor = builder.headerColor;
        textColor = builder.textColor;
        multiSelectionListener = builder.multiSelectionListener;
    }

    public void setSelectedFields(List<String> selectedFields) {
        if (multiList != null && multiList.size() > 0) {
            for (int i = 0; i < multiList.size(); i++) {
                if (selectedFields.contains(multiList.get(i).getValue())) {
                    multiList.get(i).setCheck(true);
                }
            }
        }
    }

    public void show() {
        try {
            dialog = new Dialog(context);
            final View convertView = LayoutInflater.from(context).inflate(R.layout.multi_selection_dialog, null);
            dialog.setContentView(convertView);
            TextView tvTitle = convertView.findViewById(R.id.tv_title_multi_dialog);
            ImageView imgDone = convertView.findViewById(R.id.img_done_multi_dialog);
            final CheckBox masterCheckBox = convertView.findViewById(R.id.checkbox_master);

            final RecyclerView recyclerView = convertView.findViewById(R.id.recycler_multi_dialog);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            LinearLayout header = convertView.findViewById(R.id.linear_multi_dialog);
            tvTitle.setText(headerTitle);

            if (headerColor != 0) {
                try {
                    header.setBackgroundColor(headerColor);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            masterCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < recyclerView.getChildCount(); i++) {
                        CheckBox checkBox = recyclerView.getChildAt(i).findViewById(R.id.checkbox_dialog);
                        checkBox.setChecked(masterCheckBox.isChecked());
                        multiList.get(i).setCheck(masterCheckBox.isChecked());
                    }
                }
            });

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
                @Override
                public void onItemLongClick(View view, int position) {
                    dialog.dismiss();
                }
            }));
            imgDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringBuilder getAssetsValue = new StringBuilder();
                    String assetValue = "";
                    ArrayList<String> temp_string_list = new ArrayList<>();
                    if (multiList != null && multiList.size() > 0) {
                        for (int i = 0; i < multiList.size(); i++) {
                            if (multiList.get(i).getCheck()) {
                                temp_string_list.add(multiList.get(i).getValue());
                                getAssetsValue.append(multiList.get(i).getValue()).append(",");
                                assetValue = getAssetsValue.substring(0, getAssetsValue.length() - 1);
                            }
                        }
                        if (multiSelectionListener != null) {
                            multiSelectionListener.onMultiDialogItemsSelected(assetValue, tag, temp_string_list);
                        }
                    }
                    else {
                        if (multiSelectionListener != null) {
                            multiSelectionListener.onMultiDialogError("List is null or empty", tag);
                        }
                    }
                    dialog.dismiss();
                }
            });

            if (list != null && list.size() > 0) {
                dialogAdapter = new MultiSelectionAdapter(
                    context,
                    multiSelectionListener,
                    multiList,
                    selectedFields,
                    tag,
                    headerColor,
                    textColor
                );
                recyclerView.setAdapter(dialogAdapter);
                dialog.show();
            }
            else {
                if (multiSelectionListener != null) {
                    multiSelectionListener.onMultiDialogError("List is null or empty", tag);
                }
            }
            dialog.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Builder {
        //required

        //optional

        Dialog dialog;
        private Context context;
        private ArrayList<String> list = new ArrayList<>();
        private List<String> selectedFields = new ArrayList<>();
        private String headerTitle = "Select";
        private String tag, hintText = "Search here";
        private int headerColor, textColor;
        MultiSelectionListener multiSelectionListener;

        public Builder(Context ctx, String tag) {
            this.context = ctx;
            this.tag = tag;
        }

        public Builder setContent(int[] list) {
            ArrayList<String> result = new ArrayList<>();
            for(int i : list) {
                result.add(String.valueOf(i));
            }
            this.list = result;
            return this;
        }

        public Builder setContent(ArrayList<String> provider) {
            this.list = provider;
            return this;
        }

        public Builder setTitle(String value) {
            if (value != null && !value.equals("")) this.headerTitle = value;
            else this.headerTitle = "Select";
            return this;
        }

        public Builder setTextColor(int color) {
            this.textColor = color;
            return this;
        }

        public Builder setColor(int color) {
            headerColor = color;
            return this;
        }

        public Builder setListener(MultiSelectionListener listener) {
            multiSelectionListener = listener;
            return this;
        }

        public Builder setSelectedField(List<String> selectedFields) {
            this.selectedFields = selectedFields;
            return this;
        }

        public MultiSelectionDialog build() {
            return new MultiSelectionDialog(this);
        }
    }
}
