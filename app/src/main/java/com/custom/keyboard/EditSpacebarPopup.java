package com.custom.keyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import org.w3c.dom.Text;

public class EditSpacebarPopup extends Dialog {

    LayoutInflater layoutInflater;
    EditText labelTextView;
    EditText textTextView;
    EditText popupTextView;

    public EditSpacebarPopup(Context context) {
        super(context);

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = layoutInflater.inflate(R.layout.edit_space_bar_popup, null, false);
        setContentView(view);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(R.color.transparent);

        setCancelable(true);

        show();

        labelTextView = findViewById(R.id.editLabel);
        textTextView = findViewById(R.id.editText);
        popupTextView = findViewById(R.id.editPopup);

        findViewById(R.id.savePopup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                sharedPreferenceEditor.putString(Constants.SPACEBARLABEL, labelTextView.getText().toString());
                sharedPreferenceEditor.putString(Constants.SPACEBARTEXT,  textTextView.getText().toString());
                sharedPreferenceEditor.putString(Constants.SPACEBARPOPUP, popupTextView.getText().toString());
                sharedPreferenceEditor.commit();
                sharedPreferenceEditor.apply();
            }
        });
        findViewById(R.id.exitPopup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


}
