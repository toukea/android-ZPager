package istat.android.freedev.pagers.utils;

import android.support.v4.view.ViewPager;

import istat.android.freedev.pagers.views.PageSlider;

/**
 * Created by istat on 01/11/16.
 */

public class PageAutoTurner implements PageTurner.TurnCallBack, ViewPager.OnPageChangeListener {
    public static int DIRECTION_LEFT = PageTurner.DIRECTION_LEFT, DIRECTION_RIGHT = PageTurner.DIRECTION_RIGHT;
    int interval;
    PageTurner mTurner;
    boolean autoTurnEnable = true;
    boolean running = false;

    public PageAutoTurner(int turnInterval, PageSlider slider) {
        this.interval = turnInterval;
        this.mTurner = slider.getPageTurner();
        this.mTurner.pager.addOnPageChangeListener(this);
    }

    public PageAutoTurner(int turnInterval, PageTurner turner) {
        this.interval = turnInterval;
        this.mTurner = turner;
        this.mTurner.pager.addOnPageChangeListener(this);
    }

    public void start() {
        start(DIRECTION_LEFT);
    }

    int direction;

    public void start(final int direction) {
        this.direction = direction;
        this.running = true;
        mTurner.handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mTurner.turnPage(direction, PageAutoTurner.this);
            }
        }, interval);
    }

    public int getDirection() {
        return direction;
    }

    public void stop() {
        running = false;
        mTurner.cancel();
    }

    public void restart() {
        stop();
        mTurner.pager.setCurrentItem(0);
    }

    @Override
    public void onTurnComplete(ViewPager pager, int previousIndex, int index, final int direction) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (ViewPager.SCROLL_STATE_IDLE == state && autoTurnEnable) {
            start();
        }
    }

    public PageTurner getTurner() {
        return mTurner;
    }

    public void enableAutoTurn(boolean state) {
        this.autoTurnEnable = state;
    }

    public boolean isAutoTurnEnable() {
        return autoTurnEnable;
    }

    public boolean isRunning() {
        return running;
    }
}
