package istat.android.freedev.pagers.utils;

import android.support.v4.view.ViewPager;

import istat.android.freedev.pagers.pages.Page;
import istat.android.freedev.pagers.views.PageSlider;

/**
 * Created by istat on 01/11/16.
 */

public class PageLoopTurner implements PageTurner.TurnCallBack, ViewPager.OnPageChangeListener {
    public static int DIRECTION_LEFT = PageTurner.DIRECTION_LEFT, DIRECTION_RIGHT = PageTurner.DIRECTION_RIGHT;
    int interval = 1000;
    PageTurner mTurner;

    public PageLoopTurner(int turnInterval, PageSlider slider) {
        this.interval = turnInterval;
        this.mTurner = slider.getPageTurner();
        this.mTurner.pager.addOnPageChangeListener(this);
    }

    public PageLoopTurner(int turnInterval, PageTurner turner) {
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
        mTurner.handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mTurner.turnPage(direction, PageLoopTurner.this);
            }
        }, interval);
    }

    public void stop() {
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
        if (ViewPager.SCROLL_STATE_IDLE == state) {
            start();
        }
    }
}
