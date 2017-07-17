package com.example.qiaowenhao.freshlistapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by qiaowenhao on 17-7-17.
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    private View mHeader;
    private int mHeaderHeight;
    private int mfirstVisibleItem;
    private int mScrollState;

    private boolean mIsRemark;
    private int mStartY;

    private int mState;
    private final int NONE = 0;
    private final int PULL = 1;
    private final int RELEASE = 2;
    private final int REFRESHING = 3;

    private RefreshListener mRefreshListener;


    public RefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mHeader = layoutInflater.inflate(R.layout.header_layout, null);
        measureView(mHeader);
        mHeaderHeight = mHeader.getMeasuredHeight();
        topPadding(-mHeaderHeight);
        addHeaderView(mHeader);
        setOnScrollListener(this);
    }

    //设置mHeader的上边距
    private void topPadding(int topPadding) {
        mHeader.setPadding(mHeader.getPaddingLeft(), topPadding, mHeader.getPaddingRight(), mHeader.getPaddingBottom());
        mHeader.invalidate();
    }

<<<<<<< HEAD
    //attention 测量过程
=======
    //attention 测量过程，通知父布局占有多大地
>>>>>>> 87ee782d6d9e55a2359ab37b0d3eb08d6b462f2c
    private void measureView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
<<<<<<< HEAD
        //由layoutParams得到宽的MeasureSpec
        int width = getChildMeasureSpec(0, 0, layoutParams.width);
        int height;
        int tempHeight = layoutParams.height;
        //layoutParams得到高的MeasureSpec
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            //attention
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        //实际的测量，根据宽和高
=======
        int width = getChildMeasureSpec(0, 0, layoutParams.width);
        int height;
        int tempHeight = layoutParams.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
>>>>>>> 87ee782d6d9e55a2359ab37b0d3eb08d6b462f2c
        view.measure(width, height);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mScrollState = scrollState;

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mfirstVisibleItem = firstVisibleItem;
    }

    public void setOnRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            if (mfirstVisibleItem == 0) {
                mIsRemark = true;
                mStartY = (int) ev.getY();
            }
            break;
            case MotionEvent.ACTION_MOVE:
                 onMove(ev);
                 break;
            case MotionEvent.ACTION_UP:
                if (mState == RELEASE) {
                    mState = REFRESHING;
                    refreshViewByState();
                    mRefreshListener.onRefresh();
                } else if (mState == PULL) {
                    mState = NONE;
                    mIsRemark = false;
                    refreshViewByState();
                }
        }
        return super.onTouchEvent(ev);
    }

    private void onMove(MotionEvent ev) {
        if (!mIsRemark) {
            return;
        }
        int tempY = (int) ev.getY();
        int space = tempY - mStartY;
        int topPadding = space - mHeaderHeight;
        switch (mState) {
            case NONE:
                if (space > 0) {
                    mState = PULL;
                    refreshViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > mHeaderHeight + 30 && mScrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    mState = RELEASE;
                    refreshViewByState();
                }
                break;
            case RELEASE:
                topPadding(topPadding);
                if (space < mHeaderHeight + 30) {
                    mState = PULL;
                    refreshViewByState();
                } else if (space <= 0) {
                    mState = NONE;
                    mIsRemark = false;
                    refreshViewByState();
                }
                break;

        }
    }

    private void refreshViewByState() {
        TextView tip = (TextView) mHeader.findViewById(R.id.tip);
        ProgressBar progress = (ProgressBar) mHeader.findViewById(R.id.progress);
        ImageView arrow = (ImageView) findViewById(R.id.arrow);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        RotateAnimation rotateAnimation1 = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation1.setDuration(500);
        rotateAnimation1.setFillAfter(true);
        switch (mState) {
            case NONE:
                topPadding(-mHeaderHeight);
                arrow.clearAnimation();
                break;
            case PULL:
                arrow.setVisibility(VISIBLE);
                progress.setVisibility(GONE);
                tip.setText("下拉可以刷新");
                arrow.clearAnimation();
                arrow.setAnimation(rotateAnimation1);
                break;
            case RELEASE:
                arrow.setVisibility(VISIBLE);
                progress.setVisibility(GONE);
                tip.setText("松开可以刷新");
                arrow.clearAnimation();
                arrow.setAnimation(rotateAnimation);
                break;
            case REFRESHING:
                arrow.clearAnimation();
                topPadding(0);
                arrow.setVisibility(GONE);
                progress.setVisibility(VISIBLE);
                tip.setText("正在刷新");
                break;
        }
    }

    public void refreshComplete() {
        mState = NONE;
        mIsRemark = false;
        refreshViewByState();
    }
}
