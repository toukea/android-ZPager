package istat.android.freedev.pagers.utils;

/**
 * Created by istat on 27/10/16.
 */

import android.os.Handler;
import android.widget.ScrollView;

public final  class PageScroller implements Runnable {
    public static int DIRECTION_UP = -1, DIRECTION_DOWN = 1;
    int timeCount = 0;
    int turnDirection = -1;

    double xPosition = 0;
    ScrollConfiguration configuration = new ScrollConfiguration();
    private Handler handler = new Handler();
    private ScrollView scrollView;
    boolean run = true;
    ScrollCallback mScrollCallback;

    private PageScroller(PageScroller scroller) {
        this.scrollView = scroller.scrollView;
        turnDirection = scroller.turnDirection;
        configuration = scroller.configuration;
        scrollView = scroller.scrollView;

    }

    public PageScroller(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public PageScroller(ScrollView scrollView, ScrollConfiguration configuration) {
        this.scrollView = scrollView;
        this.configuration = configuration;
    }

    public PageScroller(Handler handler, ScrollView scrollView) {
        this.handler = handler;
        this.scrollView = scrollView;
    }

    public void scroll(int direction) {

        if (direction > 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        run = true;
        timeCount = 0;
        handler.post(new PageScroller(this).setScrollCallbak(mScrollCallback));

    }

    public void stopScrolling() {
        run = false;
    }

    public void scrollUp() {
        scroll(DIRECTION_UP);
    }

    public void scrollDown() {
        scroll(DIRECTION_DOWN);
    }

    public boolean isTurning() {
        return run;
    }

    @Override
    public void run() {

        if (xPosition < scrollView.getHeight()) {
            timeCount++;
            int velocity = turnDirection * configuration.acceleration
                    * timeCount + configuration.initialVelocity;
            double gap = scrollView.getHeight() - xPosition;
            if (gap < velocity)
                velocity = (int) (turnDirection * gap);

            scrollView.scrollBy(0, velocity);
            xPosition = Math.abs(velocity) + xPosition;
            if (run)
                handler.postDelayed(this, configuration.refreshTime);
            if (mScrollCallback != null)
                mScrollCallback.onScrollProcess(scrollView);
            // Log.e("RUUN", ""+mScrollCallback);
        } else {
            run = false;
            if (mScrollCallback != null)
                mScrollCallback.onScrollComplete(scrollView);
        }

    }

    public void setTurnerConfiguration(ScrollConfiguration configuration) {
        this.configuration = configuration;
    }

    public PageScroller setScrollCallbak(ScrollCallback mScrollCallbak) {
        this.mScrollCallback = mScrollCallbak;
        // Log.e("SETCALBACK", ""+mScrollCallback);
        return this;

    }

    public static class ScrollConfiguration {
        int initialVelocity = 1, acceleration = 2, refreshTime = 50;

        public ScrollConfiguration() {
        }

        public ScrollConfiguration(int velocity, int acceleration,
                                   int refreshTime) {
            this.initialVelocity = velocity;
            this.acceleration = acceleration;
            this.refreshTime = refreshTime;
        }

        public ScrollConfiguration(int velocity, int acceleration) {
            this.initialVelocity = velocity;
            this.acceleration = acceleration;

        }

        public ScrollConfiguration setAcceleration(int acceleration) {
            this.acceleration = Math.abs(acceleration);
            return this;
        }

        public ScrollConfiguration setInitialVelocity(int initialVelocity) {
            this.initialVelocity = Math.abs(initialVelocity);
            return this;
        }

        public ScrollConfiguration setRefreshTime(int refreshTime) {
            this.refreshTime = Math.abs(refreshTime);
            return this;
        }
    }

    public static interface ScrollCallback {
        public abstract void onScrollComplete(ScrollView scrollView);

        public abstract void onScrollProcess(ScrollView scrollView);
    }
}
