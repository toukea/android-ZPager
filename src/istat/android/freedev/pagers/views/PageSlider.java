package istat.android.freedev.pagers.views;

import istat.android.freedev.pagers.adapters.PagerLooperAdapter;
import istat.android.freedev.pagers.adapters.PagerStateLooperAdapter;
import istat.android.freedev.pagers.utils.PageTurner;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.lang.reflect.Field;

public class PageSlider extends ViewPager {
    PageTurner mTurner;

    public PageSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        onPrepare(context);
    }

    public PageSlider(Context context, PageTurner turner) {
        super(context);
        this.mTurner = turner;
    }

    public PageSlider(Context context) {
        super(context);
        onPrepare(context);
    }

    @CallSuper
    protected void onPrepare(Context context) {
        mTurner = new PageTurner(this);

    }

    public void setAdapter(int initPosition, PagerAdapter adapter) {
        try {
            Field field = ViewPager.class.getDeclaredField("mCurItem");
            field.setAccessible(true);
            Object value = field.get(this);
            field.set(this, initPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setAdapter(adapter);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof PagerLooperAdapter) {
            PagerLooperAdapter tmpAdapter = (PagerLooperAdapter) adapter;
            if (tmpAdapter.getCount(true) <= 1)
                return;
            if (tmpAdapter.getCount(true) % 2 == 0)
                this.setCurrentItem(500);
            else
                this.setCurrentItem(501);
        }
        if (adapter instanceof PagerStateLooperAdapter) {
            PagerStateLooperAdapter tmpAdapter = (PagerStateLooperAdapter) adapter;
            if (tmpAdapter.getCount(true) <= 1)
                return;
            if (tmpAdapter.getCount(true) % 2 == 0)
                this.setCurrentItem(500);
            else
                this.setCurrentItem(501);
        }
    }

    void setAdapterInternally(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    public final boolean hasNexSlide() {
        return getCurrentItem() < (getAdapter() == null ? getChildCount() : getAdapter().getCount()) - 1;
    }

    public final boolean hasPreviousSlide() {
        return getCurrentItem() > 0;
    }

    public final void slideToNextPage() {
        mTurner.turnPageLeft();
    }

    public final void slideToPreviousPage() {
        mTurner.turnPageRight();
    }

    //int initialVelocity = 1, acceleration = 2, refreshTime = 50;
    public final void setPageTurnerConfiguration(int initialVelocity, int acceleration, int refreshTime) {
        PageTurner.TurnConfiguration config = new PageTurner.TurnConfiguration(initialVelocity, acceleration, refreshTime);
        setPageTurnerConfiguration(config);
    }

    public void setPageTurnerConfiguration(PageTurner.TurnConfiguration config) {
        mTurner.setTurnerConfiguration(config);
    }
}
