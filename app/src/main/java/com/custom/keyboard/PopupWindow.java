package com.custom.keyboard;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.custom.keyboard.R;

public class PopupWindow extends android.widget.PopupWindow {
    Context ctx;
    TextView btnDismiss;
    TextView lblText;
    View popupView;

    public PopupWindow(Context context) {
        super(context);

        ctx = context;
        popupView = LayoutInflater.from(context).inflate(R.layout.popup, null);
        setContentView(popupView);

        btnDismiss = (TextView)popupView.findViewById(R.id.closeButton);
        lblText = (TextView)popupView.findViewById(R.id.text);

        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

        // Closes the popup window when touch outside of it - when looses focus
        setOutsideTouchable(true);
        setFocusable(true);

        // Removes default black background
        setBackgroundDrawable(new BitmapDrawable());

        btnDismiss.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Closes the popup window when touch it
        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    dismiss();
                }
                return true;
            }
        });
    }

    public void show(View anchor, int x, int y) {
        showAtLocation(anchor, Gravity.CENTER, x, y);
    }
}