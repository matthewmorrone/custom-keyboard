package com.vlath.keyboard;

import android.view.MotionEvent;
import android.view.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;

import java.util.ArrayList;
import java.util.List;

public class CandidateView extends View {

	private static final int OUT_OF_BOUNDS = -1;

	private List<String> mSuggestions;
	private Drawable mSelectionHighlight;
	private boolean mTypedWordValid;

	private Rect mBgPadding;

	private static final int MAX_SUGGESTIONS = 32;
	private static final int SCROLL_PIXELS = 20;

	private int[] mWordWidth = new int[MAX_SUGGESTIONS];
	private int[] mWordX = new int[MAX_SUGGESTIONS];

	private static final int X_GAP = 60;

	private int mColorNormal;
	private int mColorRecommended;
	private int mColorOther;
	private int mVerticalPadding;
	private Paint mPaint;
	private boolean mScrolled;
	private int mTargetScrollX;

	private int mTotalWidth;

	public CandidateView(Context context) {
		super(context);
		mSelectionHighlight = context.getResources().getDrawable(android.R.drawable.list_selector_background);
		mSelectionHighlight.setState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused, android.R.attr.state_window_focused, android.R.attr.state_pressed});

		Resources r = context.getResources();

		setBackgroundColor(r.getColor(R.color.gray));

		mColorNormal = r.getColor(R.color.candidate_normal);
		mColorRecommended = r.getColor(R.color.candidate_recommended);
		mColorOther = r.getColor(R.color.candidate_other);
		mVerticalPadding = r.getDimensionPixelSize(R.dimen.candidate_vertical_padding);

		mPaint = new Paint();
		mPaint.setColor(mColorNormal);
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(r.getDimensionPixelSize(R.dimen.candidate_font_height));
		mPaint.setStrokeWidth(5);

		setHorizontalFadingEdgeEnabled(true);
		setWillNotDraw(false);
		setHorizontalScrollBarEnabled(false);
		setVerticalScrollBarEnabled(false);
	}

	@Override
	public int computeHorizontalScrollRange() {
		return mTotalWidth;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = resolveSize(50, widthMeasureSpec);
		Rect padding = new Rect();
		mSelectionHighlight.getPadding(padding);
		final int desiredHeight = ((int) mPaint.getTextSize()) + mVerticalPadding + padding.top + padding.bottom;
		setMeasuredDimension(measuredWidth, resolveSize(desiredHeight, heightMeasureSpec));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (canvas != null) {
			super.onDraw(canvas);
		}
		mTotalWidth = 0;
		if (mSuggestions == null) {
			return;
		}

		if (mBgPadding == null) {
			mBgPadding = new Rect(0, 0, 0, 0);
			if (getBackground() != null) {
				getBackground().getPadding(mBgPadding);
			}
		}
		int x = 0;
		final int count = mSuggestions.size();
		final int height = getHeight();
		final Rect bgPadding = mBgPadding;
		final Paint paint = mPaint;
		final int touchX = OUT_OF_BOUNDS;
		final int scrollX = getScrollX();
		final boolean scrolled = mScrolled;
		final boolean typedWordValid = mTypedWordValid;
		final int y = (int) (((height - mPaint.getTextSize()) / 2) - mPaint.ascent());

		for (int i = 0; i < count; i++) {
			String suggestion = mSuggestions.get(i);
			float textWidth = paint.measureText(suggestion);
			final int wordWidth = (int) textWidth + X_GAP * 2;

			mWordX[i] = x;
			mWordWidth[i] = wordWidth;
			paint.setColor(mColorNormal);
			if (touchX + scrollX >= x && touchX + scrollX < x + wordWidth && !scrolled) {
				if (canvas != null) {
					canvas.translate(x, 0);
					mSelectionHighlight.setBounds(0, bgPadding.top, wordWidth, height);
					mSelectionHighlight.draw(canvas);
					canvas.translate(-x, 0);
				}
			}

			if (canvas != null) {
				if ((i == 1 && !typedWordValid) || (i == 0 && typedWordValid)) {
					paint.setFakeBoldText(true);
					paint.setColor(mColorRecommended);
				}
				else if (i != 0) {
					paint.setColor(mColorOther);
				}
				canvas.drawText(suggestion, x + X_GAP, y, paint);
				paint.setColor(mColorOther);
				canvas.drawLine(x + wordWidth + 0.5f, bgPadding.top, x + wordWidth + 0.5f, height + 1, paint);
				paint.setFakeBoldText(false);
			}
			x += wordWidth;
		}
		mTotalWidth = x;
		if (mTargetScrollX != getScrollX()) {
			scrollToTarget();
		}
	}

	private void scrollToTarget() {
		int sx = getScrollX();
		if (mTargetScrollX > sx) {
			sx += SCROLL_PIXELS;
			if (sx >= mTargetScrollX) {
				sx = mTargetScrollX;
				requestLayout();
			}
		}
		else {
			sx -= SCROLL_PIXELS;
			if (sx <= mTargetScrollX) {
				sx = mTargetScrollX;
				requestLayout();
			}
		}
		scrollTo(sx, getScrollY());
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return super.onTouchEvent(me);
	}
}