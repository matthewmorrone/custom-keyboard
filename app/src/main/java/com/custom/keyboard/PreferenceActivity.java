package com.custom.keyboard;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.custom.keyboard.dialog.MultiSelectionDialog;
import com.custom.keyboard.dialog.MultiSelectionListener;
import com.custom.keyboard.dialog.SingleSelectionDialog;
import com.custom.keyboard.dialog.SingleSelectionListener;
import com.custom.keyboard.util.Util;

import java.util.ArrayList;

public class PreferenceActivity extends Activity {

    ArrayList<String> choices = new ArrayList<>();

    @Override
    public void onCreate(Bundle h) {
        super.onCreate(h);
        setContentView(R.layout.pref);
        getFragmentManager().beginTransaction().replace(R.id.main, new PreferenceFragment()).commit();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("RequestPermissions"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ShowDialog"));

        choices.add("White");
        choices.add("Black");
        choices.add("Red");
        choices.add("Green");
        choices.add("Blue");
        choices.add("Cyan");
        choices.add("Magenta");
        choices.add("Yellow");
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Util.orNull(intent.getAction(), "").equals("RequestPermissions")) {
                permissions();
            }
            if (Util.orNull(intent.getAction(), "").equals("ShowDialog")) {
                if (intent.hasExtra("single")) {
                    showSingleDialog();
                }
                if (intent.hasExtra("multiple")) {
                    showMultipleDialog();
                }
            }
        }
    };

    public void showSingleDialog() {

        SingleSelectionDialog singleSelectionDialog = new SingleSelectionDialog.Builder(this, "TEST")
            .setTitle("Select Number")
            .setContent(choices)
            .setColor(getResources().getColor(R.color.colorPrimaryDark))
            .setSelectedField("Green")
            .enableSearch(true, "Search")
            .setTextColor(getResources().getColor(R.color.black))
            .setListener(new SingleSelectionListener() {
                @Override
                public void onDialogItemSelected(String s, int position, String tag) {
                    System.out.println("onDialogItemSelected"+" "+s+" "+position+" "+tag);
                }
                @Override
                public void onDialogError(String error, String tag) {
                    System.out.println("onDialogError"+" "+error+" "+tag);
                }
            })
            .build();
        singleSelectionDialog.show();
    }

    public void showMultipleDialog() {

        MultiSelectionDialog multiSelectionDialog = new MultiSelectionDialog.Builder(this, "TEST")
            .setTitle("Select Numbers")
            .setContent(choices)
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
        multiSelectionDialog.setSelectedFields("Blue,Green");
        multiSelectionDialog.show();
    }

    public void permissions() {
        ActivityCompat.requestPermissions(PreferenceActivity.this, new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 1);
    }
}
