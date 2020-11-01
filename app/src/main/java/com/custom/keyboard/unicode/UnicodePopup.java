package com.custom.keyboard.unicode;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.custom.keyboard.R;
import com.custom.keyboard.unicode.UnicodeGridView.OnUnicodeClickedListener;

import java.util.Arrays;
import java.util.List;

public class UnicodePopup extends PopupWindow implements ViewPager.OnPageChangeListener, UnicodeRecents {
    private int mUnicodeTabLastSelectedIndex = -1;
    private View[] mUnicodeTabs;
    private PagerAdapter mUnicodeAdapter;
    private UnicodeRecentsManager mRecentsManager;
    private int keyboardHeight = 0;
    private Boolean pendingOpen = false;
    private Boolean isOpened = false;
    OnUnicodeCloseClickedListener onUnicodeCloseClickedListener;
    OnUnicodeTabClickedListener onUnicodeTabClickedListener;
    OnUnicodeClickedListener onUnicodeClickedListener;
    OnUnicodeBackspaceClickedListener onUnicodeBackspaceClickedListener;
    OnSoftKeyboardOpenCloseListener onSoftKeyboardOpenCloseListener;
    public View rootView;
    Context mContext;

    private ViewPager unicodePager;

    public UnicodePopup(View rootView, Context mContext) {
        super(mContext);
        this.mContext = mContext;
        this.rootView = rootView;
        View customView = createCustomView();
        setContentView(customView);
        setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //default size
        setSize((int)mContext.getResources().getDimension(R.dimen.keyboard_height), LayoutParams.MATCH_PARENT);
    }

    public void setOnSoftKeyboardOpenCloseListener(OnSoftKeyboardOpenCloseListener listener) {
        this.onSoftKeyboardOpenCloseListener = listener;
    }

    public void setOnUnicodeCloseClickedListener(OnUnicodeCloseClickedListener listener) {
        this.onUnicodeCloseClickedListener = listener;
    }

    public void setOnUnicodeTabClickedListener(OnUnicodeTabClickedListener listener) {
        this.onUnicodeTabClickedListener = listener;
    }

    public void setOnUnicodeClickedListener(OnUnicodeClickedListener listener) {
        this.onUnicodeClickedListener = listener;
    }

    public void setOnUnicodeBackspaceClickedListener(OnUnicodeBackspaceClickedListener listener) {
        this.onUnicodeBackspaceClickedListener = listener;
    }

    public void showAtBottom() {
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

    public void showAtBottomPending() {
        if (isKeyboardOpen()) {
            showAtBottom();
        }
        else {
            pendingOpen = true;
        }
    }

    public Boolean isKeyboardOpen() {
        return isOpened;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        UnicodeRecentsManager.getInstance(mContext).saveRecents();
    }

    public void setSizeForSoftKeyboard() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);

                int screenHeight = getUsableScreenHeight();
                int heightDifference = screenHeight - (r.bottom - r.top);
                int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    heightDifference -= mContext.getResources().getDimensionPixelSize(resourceId);
                }
                if (heightDifference > 100) {
                    keyboardHeight = heightDifference;
                    setSize(LayoutParams.MATCH_PARENT, keyboardHeight);
                    if (!isOpened) {
                        if (onSoftKeyboardOpenCloseListener != null) {
                            onSoftKeyboardOpenCloseListener.onKeyboardOpen(keyboardHeight);
                        }
                    }
                    isOpened = true;
                    if (pendingOpen) {
                        showAtBottom();
                        pendingOpen = false;
                    }
                }
                else {
                    isOpened = false;
                    if (onSoftKeyboardOpenCloseListener != null) {
                        onSoftKeyboardOpenCloseListener.onKeyboardClose();
                    }
                }
            }
        });
    }

    private int getUsableScreenHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);
            return metrics.heightPixels;
        }
        else {
            return rootView.getRootView().getHeight();
        }
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    private View createCustomView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        Unicode[] unicodeData1 = UnicodeData.getCount(   0, 2048);
        Unicode[] unicodeData2 = UnicodeData.getCount(2048, 2048);
        Unicode[] unicodeData3 = UnicodeData.getCount(4096, 2048);
        Unicode[] unicodeData4 = UnicodeData.getCount(6144, 2048);


        View view = inflater.inflate(R.layout.unicode_popup, null, false);
        unicodePager = (ViewPager)view.findViewById(R.id.unicode_pager);
        unicodePager.setOnPageChangeListener(this);
        UnicodeRecents recents = this;
        mUnicodeAdapter = new UnicodePagerAdapter(Arrays.asList(
            new UnicodeRecentsGridView(mContext, null, null, this),
            new UnicodeGridView(mContext, unicodeData1, recents, this),
            new UnicodeGridView(mContext, unicodeData2, recents, this),
            new UnicodeGridView(mContext, unicodeData3, recents, this),
            new UnicodeGridView(mContext, unicodeData4, recents, this)
        ));
        unicodePager.setAdapter(mUnicodeAdapter);
        mUnicodeTabs = new View[5];
        mUnicodeTabs[0] = view.findViewById(R.id.unicode_tab_0);
        mUnicodeTabs[1] = view.findViewById(R.id.unicode_tab_1);
        mUnicodeTabs[2] = view.findViewById(R.id.unicode_tab_2);
        mUnicodeTabs[3] = view.findViewById(R.id.unicode_tab_3);
        mUnicodeTabs[4] = view.findViewById(R.id.unicode_tab_4);
        for (int i = 0; i < mUnicodeTabs.length; i++) {
            final int position = i;
            mUnicodeTabs[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUnicodeTabClickedListener != null) {
                        onUnicodeTabClickedListener.onUnicodeTabClicked(v);
                    }
                    unicodePager.setCurrentItem(position);
                }
            });
        }
        view.findViewById(R.id.unicode_backspace).setOnTouchListener(new RepeatListener(1000, 50, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUnicodeBackspaceClickedListener != null) {
                    onUnicodeBackspaceClickedListener.onUnicodeBackspaceClicked(v);
                }
            }
        }));

        // Hide Unicode with keyboard icon.
        view.findViewById(R.id.unicode_keyboard_image).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUnicodeCloseClickedListener != null) {
                    onUnicodeCloseClickedListener.onUnicodeCloseClicked(v);
                }
                dismiss();
            }
        });

        // get last selected page
        mRecentsManager = UnicodeRecentsManager.getInstance(view.getContext());
        int page = mRecentsManager.getRecentPage();
        // last page was recents, check if there are recents to use
        // if none was found, go to page 1
        if (page == 0 && mRecentsManager.size() == 0) {
            page = 1;
        }

        if (page == 0) {
            onPageSelected(page);
        }
        else {
            unicodePager.setCurrentItem(page, false);
        }
        return view;
    }

    @Override
    public void addRecentUnicode(Context context, Unicode unicode) {
        UnicodeRecentsGridView fragment = ((UnicodePagerAdapter)unicodePager.getAdapter()).getRecentFragment();
        fragment.addRecentUnicode(context, unicode);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
    }

    @Override
    public void onPageSelected(int i) {
        if (mUnicodeTabLastSelectedIndex == i) {
            return;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                if (mUnicodeTabLastSelectedIndex >= 0 && mUnicodeTabLastSelectedIndex < mUnicodeTabs.length) {
                    mUnicodeTabs[mUnicodeTabLastSelectedIndex].setSelected(false);
                }
                mUnicodeTabs[i].setSelected(true);
                mUnicodeTabLastSelectedIndex = i;
                mRecentsManager.setRecentPage(i);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    private static class UnicodePagerAdapter extends PagerAdapter {
        private List<UnicodeGridView> views;

        public UnicodeRecentsGridView getRecentFragment() {
            for (UnicodeGridView it : views) {
                if (it instanceof UnicodeRecentsGridView) {
                    return (UnicodeRecentsGridView)it;
                }
            }
            return null;
        }

        public UnicodePagerAdapter(List<UnicodeGridView> views) {
            super();
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = views.get(position).rootView;
            ((ViewPager)container).addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            ((ViewPager)container).removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(View view, Object key) {
            return key == view;
        }
    }

    public static class RepeatListener implements View.OnTouchListener {

        private final Handler handler = new Handler();

        private final int initialInterval;
        private final int normalInterval;
        private final OnClickListener clickListener;

        private final Runnable handlerRunnable = new Runnable() {
            @Override
            public void run() {
                if (downView == null) {
                    return;
                }
                handler.removeCallbacksAndMessages(downView);
                handler.postAtTime(this, downView, SystemClock.uptimeMillis() + normalInterval);
                clickListener.onClick(downView);
            }
        };

        private View downView;

        public RepeatListener(int initialInterval, int normalInterval, OnClickListener clickListener) {
            if (clickListener == null) {
                throw new IllegalArgumentException("null runnable");
            }
            if (initialInterval < 0 || normalInterval < 0) {
                throw new IllegalArgumentException("negative interval");
            }
            this.initialInterval = initialInterval;
            this.normalInterval = normalInterval;
            this.clickListener = clickListener;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean result = false;
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downView = view;
                    handler.removeCallbacks(handlerRunnable);
                    handler.postAtTime(handlerRunnable, downView, SystemClock.uptimeMillis() + initialInterval);
                    clickListener.onClick(view);
                    result = true;
                break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    handler.removeCallbacksAndMessages(downView);
                    downView = null;
                    result = true;
                break;
            }
            return result;
        }
    }

    public interface OnUnicodeCloseClickedListener {
        void onUnicodeCloseClicked(View v);
    }

    public interface OnUnicodeTabClickedListener {
        void onUnicodeTabClicked(View v);
    }

    public interface OnUnicodeBackspaceClickedListener {
        void onUnicodeBackspaceClicked(View v);
    }

    public interface OnSoftKeyboardOpenCloseListener {
        void onKeyboardOpen(int keyBoardHeight);
        void onKeyboardClose();
    }
}
