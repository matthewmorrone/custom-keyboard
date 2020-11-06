

package com.custom.keyboard.emoticon;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
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
import com.custom.keyboard.Util;
import com.custom.keyboard.emoticon.EmoticonGridView.OnEmoticonClickedListener;
import com.custom.keyboard.emoticon.EmoticonGridView.OnEmoticonLongClickedListener;
import com.custom.keyboard.emoticon.categories.EmoticonCategories;

import java.util.Arrays;
import java.util.List;

public class EmoticonPopup extends PopupWindow implements ViewPager.OnPageChangeListener, EmoticonRecents {
    private int mEmoticonTabLastSelectedIndex = -1;
    private View[] mEmoticonTabs;
    private PagerAdapter mEmoticonsAdapter;
    private EmoticonRecentsManager mRecentsManager;
    private int keyboardHeight = 0;
    private Boolean pendingOpen = false;
    private Boolean isOpened = false;
    OnEmoticonCloseClickedListener onEmoticonCloseClickedListener;
    OnEmoticonTabClickedListener onEmoticonTabClickedListener;
    OnEmoticonClickedListener onEmoticonClickedListener;
    OnEmoticonLongClickedListener onEmoticonLongClickedListener;

    OnEmoticonBackspaceClickedListener onEmoticonBackspaceClickedListener;
    OnSoftKeyboardOpenCloseListener onSoftKeyboardOpenCloseListener;
    public View rootView;
    Context mContext;

    private ViewPager emoticonPager;

    public EmoticonPopup(View rootView, Context mContext) {
        super(mContext);
        this.mContext = mContext;
        this.rootView = rootView;
        View customView = createCustomView();
        setContentView(customView);
        setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setSize((int)mContext.getResources().getDimension(R.dimen.keyboard_height), LayoutParams.MATCH_PARENT);
    }

    public int getCurrentTab() {
        return emoticonPager.getCurrentItem();
    }

    public void setOnSoftKeyboardOpenCloseListener(OnSoftKeyboardOpenCloseListener listener) {
        this.onSoftKeyboardOpenCloseListener = listener;
    }

    public void setOnEmoticonCloseClickedListener(OnEmoticonCloseClickedListener listener) {
        this.onEmoticonCloseClickedListener = listener;
    }

    public void setOnEmoticonTabClickedListener(OnEmoticonTabClickedListener listener) {
        this.onEmoticonTabClickedListener = listener;
    }

    public void setOnEmoticonClickedListener(OnEmoticonClickedListener listener) {
        this.onEmoticonClickedListener = listener;
    }

    public void setOnEmoticonLongClickedListener(OnEmoticonLongClickedListener listener) {
        this.onEmoticonLongClickedListener = listener;
    }

    public void setOnEmoticonBackspaceClickedListener(OnEmoticonBackspaceClickedListener listener) {
        this.onEmoticonBackspaceClickedListener = listener;
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
        EmoticonRecentsManager.getInstance(mContext).saveRecents();
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

    public static Emoticon[] stringToEmoticons(String input) {
        Emoticon[] data = new Emoticon[input.length()];
        for(int i = 0; i < input.length(); i++) {
            data[i] = Emoticon.fromCodePoint(input.charAt(i));
        }
        return data;
    }

    private View createCustomView() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Emoticon[] emoticonFavorites = stringToEmoticons(Util.orNull(sharedPreferences.getString("emoticon_favorites", ""), ""));

        View view = inflater.inflate(R.layout.emoticon_popup, null, false);
        emoticonPager = view.findViewById(R.id.emoticons_pager);
        emoticonPager.setOnPageChangeListener(this);
        EmoticonRecents recents = this;
        mEmoticonsAdapter = new EmoticonsPagerAdapter(Arrays.asList(
            new EmoticonRecentsGridView(mContext, null, null, this),
            new EmoticonGridView(mContext, EmoticonCategories.Default, recents, this),
            new EmoticonGridView(mContext, EmoticonCategories.People, recents, this),
            new EmoticonGridView(mContext, EmoticonCategories.Nature, recents, this),
            new EmoticonGridView(mContext, EmoticonCategories.FoodAndDrink, recents, this),
            new EmoticonGridView(mContext, EmoticonCategories.Places, recents, this),
            new EmoticonGridView(mContext, EmoticonCategories.Activities, recents, this),
            new EmoticonGridView(mContext, EmoticonCategories.Objects, recents, this),
            new EmoticonGridView(mContext, EmoticonCategories.Symbols, recents, this),
            new EmoticonGridView(mContext, EmoticonCategories.Flags, recents, this),
            new EmoticonGridView(mContext, emoticonFavorites, recents, this)
        ));
        emoticonPager.setAdapter(mEmoticonsAdapter);
        mEmoticonTabs = new View[11];
        mEmoticonTabs[0] = view.findViewById(R.id.emoticons_tab_0_recents);
        mEmoticonTabs[1] = view.findViewById(R.id.emoticons_tab_1_default);
        mEmoticonTabs[2] = view.findViewById(R.id.emoticons_tab_2_people);
        mEmoticonTabs[3] = view.findViewById(R.id.emoticons_tab_3_nature);
        mEmoticonTabs[4] = view.findViewById(R.id.emoticons_tab_4_food_and_drink);
        mEmoticonTabs[5] = view.findViewById(R.id.emoticons_tab_5_places);
        mEmoticonTabs[6] = view.findViewById(R.id.emoticons_tab_6_activities);
        mEmoticonTabs[7] = view.findViewById(R.id.emoticons_tab_7_objects);
        mEmoticonTabs[8] = view.findViewById(R.id.emoticons_tab_8_symbols);
        mEmoticonTabs[9] = view.findViewById(R.id.emoticons_tab_9_flags);
        mEmoticonTabs[10] = view.findViewById(R.id.emoticons_tab_favorites);
        for (int i = 0; i < mEmoticonTabs.length; i++) {
            final int position = i;
            mEmoticonTabs[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEmoticonTabClickedListener != null) {
                        onEmoticonTabClickedListener.onEmoticonTabClicked(v);
                    }
                    emoticonPager.setCurrentItem(position);
                }
            });
        }

        view.findViewById(R.id.emoticons_backspace).setOnTouchListener(new RepeatListener(1000, 50, new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEmoticonBackspaceClickedListener != null) {
                    onEmoticonBackspaceClickedListener.onEmoticonBackspaceClicked(v);
                }
            }
        }));

        view.findViewById(R.id.emoticons_keyboard_image).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEmoticonCloseClickedListener != null) {
                    onEmoticonCloseClickedListener.onEmoticonCloseClicked(v);
                }
                dismiss();
            }
        });

        mRecentsManager = EmoticonRecentsManager.getInstance(view.getContext());
        int page = mRecentsManager.getRecentPage();

        if (page == 0 && mRecentsManager.size() == 0) page = 1;

        if (page == 0) onPageSelected(page);
        else emoticonPager.setCurrentItem(page, false);
        return view;
    }

    @Override
    public void addRecentEmoticon(Context context, Emoticon emoticon) {
        EmoticonRecentsGridView fragment = ((EmoticonsPagerAdapter)emoticonPager.getAdapter()).getRecentFragment();
        fragment.addRecentEmoticon(context, emoticon);
    }

    @Override
    public void removeRecentEmoticon(Context context, Emoticon emoticon) {
        EmoticonRecentsGridView fragment = ((EmoticonPopup.EmoticonsPagerAdapter)emoticonPager.getAdapter()).getRecentFragment();
        fragment.removeRecentEmoticon(context, emoticon);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
    }

    @Override
    public void onPageSelected(int i) {
        if (mEmoticonTabLastSelectedIndex == i) {
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
            case 9:
            case 10:
                if (mEmoticonTabLastSelectedIndex >= 0 && mEmoticonTabLastSelectedIndex < mEmoticonTabs.length) {
                    mEmoticonTabs[mEmoticonTabLastSelectedIndex].setSelected(false);
                }
                mEmoticonTabs[i].setSelected(true);
                mEmoticonTabLastSelectedIndex = i;
                mRecentsManager.setRecentPage(i);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    private static class EmoticonsPagerAdapter extends PagerAdapter {
        private List<EmoticonGridView> views;

        public EmoticonRecentsGridView getRecentFragment() {
            for (EmoticonGridView it : views) {
                if (it instanceof EmoticonRecentsGridView) {
                    return (EmoticonRecentsGridView)it;
                }
            }
            return null;
        }

        public EmoticonsPagerAdapter(List<EmoticonGridView> views) {
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
            container.addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View)view);
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

    public interface OnEmoticonCloseClickedListener {
        void onEmoticonCloseClicked(View v);
    }

    public interface OnEmoticonTabClickedListener {
        void onEmoticonTabClicked(View v);
    }

    public interface OnEmoticonBackspaceClickedListener {
        void onEmoticonBackspaceClicked(View v);
    }

    public interface OnSoftKeyboardOpenCloseListener {
        void onKeyboardOpen(int keyBoardHeight);
        void onKeyboardClose();
    }
}
