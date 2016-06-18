package com.github.handioq.weatherapp.utils;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.handioq.weatherapp.R;

/**
 * Class for animating refresh button.
 */
public class AnimatingRefreshButtonManager {

    private final MenuItem mRefreshItem;
    private final Animation mRotationAnimation;

    private boolean mIsRefreshInProgress = false;

    public AnimatingRefreshButtonManager(Context context,
                                         MenuItem refreshItem)
    {
        // null checks omitted for brevity

        mRefreshItem = refreshItem;
        mRotationAnimation = AnimationUtils.loadAnimation(
                context, R.anim.spin_clockwise);

        mRotationAnimation.setAnimationListener(
                new Animation.AnimationListener()
                {
                    @Override public void onAnimationStart(Animation animation) {}

                    @Override public void onAnimationEnd(Animation animation) {}

                    @Override public void onAnimationRepeat(Animation animation)
                    {
                        // If a refresh is not in progress, stop the animation
                        // once it reaches the end of a full cycle
                        if (!mIsRefreshInProgress)
                            stopAnimation();
                    }
                }
        );

        mRotationAnimation.setRepeatCount(Animation.INFINITE);
    }

    public void onRefreshBeginning()
    {
        if (mIsRefreshInProgress)
            return;
        mIsRefreshInProgress = true;

        stopAnimation();
        MenuItemCompat.setActionView(mRefreshItem,
                R.layout.refresh_action_view);
        View actionView = MenuItemCompat.getActionView(mRefreshItem);
        if (actionView != null)
            actionView.startAnimation(mRotationAnimation);
    }

    public void onRefreshComplete()
    {
        mIsRefreshInProgress = false;
    }

    private void stopAnimation()
    {
        View actionView = MenuItemCompat.getActionView(mRefreshItem);
        if (actionView == null)
            return;
        actionView.clearAnimation();
        MenuItemCompat.setActionView(mRefreshItem, null);
    }

}
