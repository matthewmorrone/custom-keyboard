package com.custom.keyboard;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import java.util.Arrays;
import java.util.List;

public class PreferenceActivity extends Activity {

    SharedPreferences sharedPreferences;

    ArrayList<String> colors = new ArrayList<>(Arrays.asList("White", "Black", "Red", "Green", "Blue", "Cyan", "Magenta", "Yellow"));
    ArrayList<String> topRowKeyChoices = new ArrayList<>(Arrays.asList("-20", "-21", "-13", "-14", "-15", "-16", "-8", "-9", "-10", "-11", "-12", "-23"));
    ArrayList<String> customKeyChoices = new ArrayList<>(Arrays.asList("-22", "-23", "-25", "-26", "-27", "-84", "-103", "-133", "-134", "-135", "-136", "-137", "-138", "-139", "-140", "-142", "-143", "-144", "-174", "-175"));

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
                if (intent.hasExtra("single"))     showSingleDialog();
                if (intent.hasExtra("multiple"))   showMultipleDialog();
                if (intent.hasExtra("topRowKeys")) showTopRowKeysDialog();
                if (intent.hasExtra("customKey"))  customKeyDialog();
            }
        }
    };

    public void customKeyDialog() {
        SingleSelectionDialog customKeyDialog = new SingleSelectionDialog.Builder(this, "Custom Key")
            .setTitle("Custom Key")
            .setContent(customKeyChoices)
            .setColor(getResources().getColor(R.color.colorPrimaryDark))
            .setTextColor(getResources().getColor(R.color.colorAccent))
            .setListener(new SingleSelectionListener() {
                @Override
                public void onSingleDialogItemSelected(String s, int position, String tag) {
                    sharedPreferences.edit().putString("custom_key", s).apply();
                    // System.out.println(sharedPreferences.getString("custom_key", ""));
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

    public void showTopRowKeysDialog() {
        MultiSelectionDialog topRowKeysDialog = new MultiSelectionDialog.Builder(this, "Top Row Keys")
            .setTitle("Top Row Keys")
            .setContent(topRowKeyChoices)
            .setColor(getResources().getColor(R.color.colorPrimaryDark))
            .setTextColor(getResources().getColor(R.color.colorAccent))
            .setListener(new MultiSelectionListener() {
                @Override
                public void onMultiDialogItemsSelected(String s, String tag, ArrayList<String> selectedItemList) {
                    sharedPreferences.edit().putString("top_row_keys", StringUtils.serialize(selectedItemList)).apply();
                    // System.out.println("onMultiDialogItemsSelected"+" "+s+" "+tag+" "+selectedItemList);
                    System.out.println(sharedPreferences.getString("top_row_keys", ""));
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

    public void showSingleDialog() {

        SingleSelectionDialog singleSelectionDialog = new SingleSelectionDialog.Builder(this, "")
            .setTitle("Select Number")
            .setContent(colors)
            .setColor(getResources().getColor(R.color.colorPrimaryDark))
            .setSelectedField("Green")
            .enableSearch(true, "Search")
            .setTextColor(getResources().getColor(R.color.black))
            .setListener(new SingleSelectionListener() {
                @Override
                public void onSingleDialogItemSelected(String s, int position, String tag) {
                    System.out.println("onDialogItemSelected"+" "+s+" "+position+" "+tag);
                }
                @Override
                public void onSingleDialogError(String error, String tag) {
                    System.out.println("onDialogError"+" "+error+" "+tag);
                }
            })
            .build();
        singleSelectionDialog.show();
    }

    public void showMultipleDialog() {

        MultiSelectionDialog multiSelectionDialog = new MultiSelectionDialog.Builder(this, "")
            .setTitle("Select Numbers")
            .setContent(colors)
            .setColor(getResources().getColor(R.color.colorPrimaryDark))
            .setTextColor(getResources().getColor(R.color.colorAccent))
            .setListener(new MultiSelectionListener() {
                @Override
                public void onMultiDialogItemsSelected(String s, String tag, ArrayList<String> selectedItemList) {
                    System.out.println("onMultiDialogItemsSelected"+" "+s+" "+tag+" "+selectedItemList);
                }
                @Override
                public void onMultiDialogError(String error, String tag) {
                    System.out.println("onMultiDialogError"+" "+error+" "+tag);
                }
            })
            .build();
        multiSelectionDialog.show();
    }

    public void permissions() {
        ActivityCompat.requestPermissions(PreferenceActivity.this, new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);
    }
}
