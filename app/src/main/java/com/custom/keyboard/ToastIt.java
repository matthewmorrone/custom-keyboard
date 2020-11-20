package com.custom.keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastIt {
    static Toast mToast;
    static SharedPreferences sharedPreferences;
    static LayoutInflater layoutInflater;

    public ToastIt(SharedPreferences sharedPreferences, LayoutInflater layoutInflater) {
        ToastIt.sharedPreferences = sharedPreferences;
        ToastIt.layoutInflater = layoutInflater;
    }

    public static void text(Context context, String... args) {
        String text;
        if (args.length > 1) {
            StringBuilder result = new StringBuilder();
            for(String n: args) {
                result.append(n).append(" ");
            }
            text = result.toString().trim();
        }
        else {
            text = args[0];
        }
        text(context, text);
    }
    public static void text(Context context, String text) {
        if (!sharedPreferences.getBoolean("debug", false)) return;
        if (mToast != null) mToast.cancel();
        Spanned spanned = Html.fromHtml("<font color='#000000'><b>" + text + "</b></font>");
        mToast = Toast.makeText(context, spanned, Toast.LENGTH_LONG);
        // mToast.getView().setBackgroundColor(Color.parseColor("#00000000"));
        mToast.getView().setAlpha(.8f);
        mToast.show();
    }

    public static void info(Context context, String... args) {
        String text;
        if (args.length > 1) {
            StringBuilder result = new StringBuilder();
            for (String n: args) {
                result.append(n).append(" ");
            }
            text = result.toString().trim();
        }
        else text = args[0];
        info(context, text);
    }
    public static void info(Context context, String text) {
        if (!sharedPreferences.getBoolean("debug", false)) return;
        if (mToast != null) mToast.cancel();

        Spanned spanned = Html.fromHtml("<font color='#ffffff'><b>" + text + "</b></font>");
        mToast = Toast.makeText(context, spanned, Toast.LENGTH_LONG);

        View toastRoot = layoutInflater.inflate(R.layout.toast_info, null);
        TextView toastTextView = (TextView)toastRoot.findViewById(R.id.content);
        toastTextView.setText(spanned);
        mToast.setView(toastRoot);

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void error(Context context, Exception e) {
        error(context, e.toString());
    }
    public static void error(Context context, String... args) {
        String text;
        if (args.length > 1) {
            StringBuilder result = new StringBuilder();
            for(String n: args) {
                result.append(n).append(" ");
            }
            text = result.toString().trim();
        }
        else text = args[0];
        error(context, text);
    }
    public static void error(Context context, String text) {
        if (!sharedPreferences.getBoolean("debug", false)) return;
        if (mToast != null) mToast.cancel();

        Spanned spanned = Html.fromHtml("<font color='red'><b>" + text + "</b></font>");
        mToast = Toast.makeText(context, spanned, Toast.LENGTH_LONG);

        View toastRoot = layoutInflater.inflate(R.layout.toast_error, null);
        TextView toastTextView = toastRoot.findViewById(R.id.content);
        toastTextView.setText(spanned);
        mToast.setView(toastRoot);

        mToast.show();
    }

}
