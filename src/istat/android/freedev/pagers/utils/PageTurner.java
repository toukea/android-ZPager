package istat.android.freedev.pagers.utils;

import android.os.Handler;
import android.support.v4.view.ViewPager;

public final class PageTurner implements Runnable {
    public static int DIRECTION_LEFT = -1, DIRECTION_RIGHT = 1;
    int timeCount = 0;
    int turnDirection = -1;

    double xPosition = 0;
    TurnConfiguration configuration = new TurnConfiguration();
    final Handler handler;
    ViewPager pager;
    boolean run = true;
    PageTurner parentTurner;

    private PageTurner(PageTurner turner, TurnCallBack callBack) {
        this.parentTurner = turner;
        this.pager = turner.pager;
        this.turnDirection = turner.turnDirection;
        this.configuration = turner.configuration;
        this.pager = turner.pager;
        this.turnCallBack = callBack;
        this.handler = new Handler();
    }

    public PageTurner(ViewPager pager) {
        this(pager, new TurnConfiguration());
    }

    public PageTurner(ViewPager pager, TurnConfiguration configuration) {
        this(pager, configuration, new Handler());
    }

    public PageTurner(ViewPager pager, TurnConfiguration configuration, Handler handler) {
        this.pager = pager;
        this.configuration = configuration;
        this.handler = handler;
    }

    TurnCallBack turnCallBack;

    public void turnPage(int direction) {
        turnPage(direction, null);
    }

    public void turnPage(int direction, TurnCallBack callback) {

        if (direction > 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        run = true;
        timeCount = 0;
        pager.beginFakeDrag();
        handler.post(new PageTurner(this, callback));
        this.turnCallBack = callback;

    }

    public void stopTurning() {
        run = false;
    }

    public void turnPageLeft(TurnCallBack callBack) {
        turnPage(DIRECTION_LEFT, callBack);
    }

    public void turnPageRight(TurnCallBack callBack) {
        turnPage(DIRECTION_RIGHT, callBack);
    }

    public void turnPageLeft() {
        turnPage(DIRECTION_LEFT);
    }

    public void turnPageRight() {
        turnPage(DIRECTION_RIGHT);
    }

    public boolean isTurning() {
        return run;
    }

    @Override
    public void run() {
        final ViewPager pager = this.pager;
        try {
            if (pager == null) {
                return;
            }
            int previousIndex = pager.getCurrentItem();
            if (pager.isFakeDragging()) {
                double velocity = turnDirection * configuration.acceleration * timeCount + (configuration.initialVelocity * turnDirection);
                double virtualPosition = xPosition + Math.abs(velocity);
                if (virtualPosition > pager.getX() && virtualPosition < pager.getX() + pager.getWidth()) {
                    timeCount++;
//                    double remainingDistance = pager.getWidth() - virtualPosition;
////                    System.out.println("vp=" + virtualPosition + ", rem=" + remainingDistance + ", v=" + velocity);
//                    if (remainingDistance < Math.abs(velocity)) {
//                        velocity = turnDirection * remainingDistance;
//                    }
                    pager.fakeDragBy((float) (velocity));
                    xPosition = Math.abs(velocity) + xPosition;
                    if (run)
                        handler.postDelayed(this, configuration.refreshTime);
                } else {
                    run = false;
                    pager.endFakeDrag();
                    if (turnCallBack != null) {
                        turnCallBack.onTurnComplete(pager, previousIndex,
                                previousIndex - turnDirection, turnDirection);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            handler.removeCallbacks(null);
        }
    }

    public void setTurnerConfiguration(TurnConfiguration configuration) {
        this.configuration = configuration;
    }

    public static class TurnConfiguration {
        int initialVelocity = 1, acceleration = 2, refreshTime = 50;

        public TurnConfiguration() {
        }

        public TurnConfiguration(int velocity, int acceleration, int refreshTime) {
            this.initialVelocity = velocity;
            this.acceleration = acceleration;
            this.refreshTime = refreshTime;
        }

        public TurnConfiguration(int velocity, int acceleration) {
            this.initialVelocity = velocity;
            this.acceleration = acceleration;

        }

        public TurnConfiguration setAcceleration(int acceleration) {
            this.acceleration = Math.abs(acceleration);
            return this;
        }

        public TurnConfiguration setInitialVelocity(int initialVelocity) {
            this.initialVelocity = Math.abs(initialVelocity);
            return this;
        }

        public TurnConfiguration setRefreshTime(int refreshTime) {
            this.refreshTime = Math.abs(refreshTime);
            return this;
        }
    }

    public interface TurnCallBack {

        void onTurnComplete(ViewPager pager, int previousIndex,
                            int index, int direction);
    }

    public final void cancel() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (parentTurner != null) {
            parentTurner.handler.removeCallbacksAndMessages(null);
        }
    }
}