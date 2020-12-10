package com.custom.keyboard;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import com.custom.keyboard.dialog.MultiSelectionDialog;
import com.custom.keyboard.dialog.MultiSelectionListener;
import com.custom.keyboard.dialog.SingleSelectionDialog;
import com.custom.keyboard.dialog.SingleSelectionListener;
import com.custom.keyboard.util.StringUtils;
import com.custom.keyboard.util.Util;

import java.util.ArrayList;
import java.util.List;

public class PreferenceActivity extends Activity {

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle h) {
        super.onCreate(h);
        setContentView(R.layout.pref);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        getFragmentManager().beginTransaction().replace(R.id.main, new PreferenceFragment()).commit();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("RequestPermissions"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ShowDialog"));
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Util.orNull(intent.getAction(), "").equals("RequestPermissions")) {
                permissions();
            }
            if (Util.orNull(intent.getAction(), "").equals("ShowDialog")) {
                if (intent.hasExtra("topRowKeys"))     showTopRowKeysDialog();
                if (intent.hasExtra("customKey"))      customKeyDialog();
                if (intent.hasExtra("showTextEditor")) showTextEditor();
            }
        }
    };

    private PopupWindow popupWindow = null;

    public void showTextEditor() {
        LayoutInflater li = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (li != null) {
            View textEditor = li.inflate(R.layout.text_editor, null);
            final ViewGroup viewGroup = (ViewGroup)((ViewGroup)this.findViewById(android.R.id.content)).getChildAt(0);

            popupWindow = new PopupWindow(textEditor, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT, true);
            popupWindow.showAtLocation(viewGroup, Gravity.CENTER, 0, 0); // Util.orNull(-getKey(32).height, 0)
        }
    }

    public void showTopRowKeysDialog() {
        MultiSelectionDialog topRowKeysDialog = new MultiSelectionDialog.Builder(this, "Top Row Keys")
            .setTitle("Top Row Keys")
            .setContent(Constants.topRowKeyChoices)
            .setColor(getResources().getColor(R.color.colorPrimaryDark))
            .setTextColor(getResources().getColor(R.color.colorAccent))
            .setListener(new MultiSelectionListener() {
                @Override
                public void onMultiDialogItemsSelected(String s, String tag, ArrayList<String> selectedItemList) {
                    sharedPreferences.edit().putString("top_row_keys", StringUtils.serialize(selectedItemList)).apply();
                }
                @Override
                public void onMultiDialogError(String error, String tag) {
                }
            })
            .build();
        List<String> selectedFields = StringUtils.deserialize(Util.notNull(sharedPreferences.getString("top_row_keys", "")));
        topRowKeysDialog.setSelectedFields(selectedFields);
        topRowKeysDialog.show();
    }

    public void customKeyDialog() {
        SingleSelectionDialog customKeyDialog = new SingleSelectionDialog.Builder(this, "Custom Key")
            .setTitle("Custom Key")
            .setContent(Constants.customKeyChoices)
            .setColor(getResources().getColor(R.color.colorPrimaryDark))
            .setTextColor(getResources().getColor(R.color.colorAccent))
            .setListener(new SingleSelectionListener() {
                @Override
                public void onSingleDialogItemSelected(String s, int position, String tag) {
                    sharedPreferences.edit().putString("custom_key", s).apply();
                }
                @Override
                public void onSingleDialogError(String error, String tag) {
                }
            })
            .build();
        String selectedField = Util.notNull(sharedPreferences.getString("custom_key", ""));
        customKeyDialog.setSelectedField(selectedField);
        customKeyDialog.show();
    }

    public void permissions() {
        ActivityCompat.requestPermissions(PreferenceActivity.this, new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);
    }
}
