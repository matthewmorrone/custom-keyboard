package com.custom.keyboard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ColorPreference extends DialogPreference {

    public int color;
    protected int defcolor;
    protected String attribute;
    public View colorView;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        if (colorView == null) return;
        final View myView = colorView.findViewById(R.id.currentcolor);
        if (myView != null) myView.setBackgroundColor(color);
    }

    // This is the constructor called by the inflater
    public ColorPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        attribute = attrs.getAttributeValue(1);

        // set the layout so we can see the preview color
        setWidgetLayoutResource(R.layout.pref_color);

        // figure out what the current color is
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());

	    if (attribute.equals("fontcolor"))  defcolor = 0xFFFFFFFF;
	    else                                defcolor = 0xFF000000;

        color = sharedPref.getInt(attribute, defcolor);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        colorView = view;
        // Set our custom views inside the layout
        final View myView = colorView.findViewById(R.id.currentcolor);
        if (myView != null) myView.setBackgroundColor(color);
    }

    @Override
    protected boolean callChangeListener(Object newValue) {
        // System.out.println(newValue);

        // setup the view
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View colorView = factory.inflate(R.layout.color_chooser, null);

        // set the background to the current color
        final View myView = colorView.findViewById(R.id.currentcolor);
        if (myView != null) myView.setBackgroundColor(color);


        notifyChanged();
        return super.callChangeListener(newValue);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        // Data has changed, notify so UI can be refreshed!
        builder.setTitle("Choose a color");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            // save the color
            public void onClick(DialogInterface dialog, int whichButton) {
                Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                editor.putInt(attribute, color);
                editor.apply();
                notifyChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            // set it back to original
            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                color = sharedPref.getInt(attribute, defcolor);
            }
        });

        // setup the view
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View colorView = factory.inflate(R.layout.color_chooser, null);
        final ImageView colormap = colorView.findViewById(R.id.colormap);

        // set the background to the current color
        colorView.setBackgroundColor(color);

        // setup the click listener
        colormap.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BitmapDrawable bd = (BitmapDrawable)colormap.getDrawable();
                Bitmap bitmap = bd.getBitmap();

                // get the color value.
                // scale the touch location
                int x = (int)((event.getX() - 15) * bitmap.getWidth()  / (colormap.getWidth()  - 30));
                int y = (int)((event.getY() - 15) * bitmap.getHeight() / (colormap.getHeight() - 30));

	            if (x >= bitmap.getWidth())  x = bitmap.getWidth() - 1;
	            if (x < 0) x = 0;

	            if (y >= bitmap.getHeight()) y = bitmap.getHeight() - 1;
	            if (y < 0) y = 0;

                // set the color
                color = bitmap.getPixel(x, y);
                colorView.setBackgroundColor(color);

                return true;
            }
        });
        builder.setView(colorView);
    }
}