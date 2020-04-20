package com.custom.keyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class EditSpacebarPopup extends Dialog {

    SharedPreferences sharedPreferences;
    LayoutInflater layoutInflater;
    String type;
    EditText labelTextView;
    EditText textTextView;
    EditText popupTextView;
    EditText iconTextView;

    public EditSpacebarPopup(Context context, String type) {
        super(context);
        this.type = type;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setValues(String type) {
        if (type.equals("space")) {
            labelTextView.setText(sharedPreferences.getString(Constants.SPACEBARLABEL, ""));
            iconTextView.setText(sharedPreferences.getString(Constants.SPACEBARICON, "ic_space"));
            textTextView.setText(sharedPreferences.getString(Constants.SPACEBARLABEL, ""));
            popupTextView.setText(sharedPreferences.getString(Constants.SPACEBARTEXT, getContext().getString(R.string.popup_first)));
        }
        if (type.equals("tab")) {
            labelTextView.setText(sharedPreferences.getString(Constants.TABKEYLABEL, ""));
            iconTextView.setText(sharedPreferences.getString(Constants.TABKEYICON, "ic_tab"));
            textTextView.setText(sharedPreferences.getString(Constants.TABKEYLABEL, ""));
            popupTextView.setText(sharedPreferences.getString(Constants.TABKEYTEXT, getContext().getString(R.string.popup_second)));
        }
        if (type.equals("enter")) {
            labelTextView.setText(sharedPreferences.getString(Constants.ENTERKEYLABEL, ""));
            iconTextView.setText(sharedPreferences.getString(Constants.ENTERKEYICON, "ic_enter"));
            textTextView.setText(sharedPreferences.getString(Constants.ENTERKEYLABEL, ""));
            popupTextView.setText(sharedPreferences.getString(Constants.ENTERKEYTEXT, getContext().getString(R.string.popup_third)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = layoutInflater.inflate(R.layout.edit_key_popup, null, false);
        setContentView(view);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();

        labelTextView = findViewById(R.id.editLabel);
        textTextView = findViewById(R.id.editText);
        popupTextView = findViewById(R.id.editPopup);
        iconTextView = findViewById(R.id.editIcon);

        findViewById(R.id.savePopup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("space")) {
                    sharedPreferenceEditor.putString(Constants.SPACEBARLABEL, labelTextView.getText().toString());
                    sharedPreferenceEditor.putString(Constants.SPACEBARTEXT,  textTextView.getText().toString());
                    sharedPreferenceEditor.putString(Constants.SPACEBARPOPUP, popupTextView.getText().toString());
                    sharedPreferenceEditor.putString(Constants.SPACEBARICON,  iconTextView.getText().toString());
                }
                if (type.equals("tab")) {
                    sharedPreferenceEditor.putString(Constants.TABKEYLABEL, labelTextView.getText().toString());
                    sharedPreferenceEditor.putString(Constants.TABKEYTEXT,  textTextView.getText().toString());
                    sharedPreferenceEditor.putString(Constants.TABKEYPOPUP, popupTextView.getText().toString());
                    sharedPreferenceEditor.putString(Constants.TABKEYICON,  iconTextView.getText().toString());
                }
                if (type.equals("enter")) {
                    sharedPreferenceEditor.putString(Constants.ENTERKEYLABEL, labelTextView.getText().toString());
                    sharedPreferenceEditor.putString(Constants.ENTERKEYTEXT,  textTextView.getText().toString());
                    sharedPreferenceEditor.putString(Constants.ENTERKEYPOPUP, popupTextView.getText().toString());
                    sharedPreferenceEditor.putString(Constants.ENTERKEYICON,  iconTextView.getText().toString());
                }
                sharedPreferenceEditor.apply();
                Toast.makeText(view.getContext(), "Values Saved", Toast.LENGTH_SHORT).show();
                dismiss();
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
