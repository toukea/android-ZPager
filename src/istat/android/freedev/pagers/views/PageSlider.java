package istat.android.freedev.pagers.views;

import istat.android.freedev.pagers.adapters.PagerLooperAdapter;
import istat.android.freedev.pagers.adapters.PagerStateLooperAdapter;
import istat.android.freedev.pagers.utils.PageTurner;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    void setCurrentItemInternally(int initPosition) {
        try {
            Field field = ViewPager.class.getDeclaredField("mCurItem");
            field.setAccessible(true);
            field.set(this, initPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setAdapter(int initPosition, PagerAdapter adapter) {
        setCurrentItemInternally(initPosition);
        this.setAdapter(adapter);
        //setCurrentItem(initPosition);
    }

    void setAdapterInternally(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof PagerStateLooperAdapter) {
            PagerStateLooperAdapter tmpAdapter = (PagerStateLooperAdapter) adapter;
            if (tmpAdapter.getSlidePageCount() <= 1)
                return;
            if (tmpAdapter.getSlidePageCount() % 2 == 0)
                this.setCurrentItem(500);
            else
                this.setCurrentItem(501);
        }
    }

    @Override
    public void setCurrentItem(int item) {
        if (getAdapter() instanceof PagerLooperAdapter) {
            PagerLooperAdapter adapter = (PagerLooperAdapter) getAdapter();
            FragmentTransaction t = adapter.getFragmentManager().beginTransaction();
            for (int i = 0; i < adapter.getPages().size(); i++) {
                t.remove(adapter.getPages().get(i));
            }
            t.commit();
        }
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (getAdapter() instanceof PagerLooperAdapter) {
            PagerLooperAdapter adapter = (PagerLooperAdapter) getAdapter();
            FragmentTransaction t = adapter.getFragmentManager().beginTransaction();
            for (int i = 0; i < adapter.getPages().size(); i++) {
                // t.detach(adapter.getPages().get(i));
                t.remove(adapter.getPages().get(i));
            }
            t.commit();
        }
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public int getCurrentItem() {
        if (getAdapter() instanceof PagerLooperAdapter) {
            PagerLooperAdapter adapter = (PagerLooperAdapter) getAdapter();
            return super.getCurrentItem() % adapter.getSlidePageCount();
        } else if (getAdapter() instanceof PagerStateLooperAdapter) {
            PagerStateLooperAdapter adapter = (PagerStateLooperAdapter) getAdapter();
            return super.getCurrentItem() % adapter.getSlidePageCount();
        }
        return super.getCurrentItem();
    }

    int getCurrentInternalItem() {
        return super.getCurrentItem();
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

    public final void next() {
        if (this.getAdapter() != null && this.getCurrentItem() < getAdapter().getCount() - 1) {
            setCurrentItem(this.getCurrentItem() + 1);
        }
    }

    public final void previous() {
        if (getCurrentItem() > 0) {
            setCurrentItem(this.getCurrentItem() - 1);
        }
    }

    public final void setPageTurnerConfiguration(int initialVelocity, int acceleration, int refreshTime) {
        PageTurner.TurnConfiguration config = new PageTurner.TurnConfiguration(initialVelocity, acceleration, refreshTime);
        setPageTurnerConfiguration(config);
    }

    public void setPageTurnerConfiguration(PageTurner.TurnConfiguration config) {
        mTurner.setTurnerConfiguration(config);
    }

    public PageInflater getPageInflater(FragmentManager fm) {
        return new PageInflater(fm) {
            @Override
            public PagerAdapter onApply(FragmentManager fm, List<Fragment> pages) {
                istat.android.freedev.pagers.adapters.PagerAdapter adapter = new istat.android.freedev.pagers.adapters.PagerAdapter(fm);
                for (Fragment page : pages) {
                    adapter.addPage(page);
                }
                return adapter;
            }
        };
    }

    public PageInflater getPageStateInflater(FragmentManager fm) {
        return new PageInflater(fm) {
            @Override
            public PagerAdapter onApply(FragmentManager fm, List<Fragment> pages) {
                istat.android.freedev.pagers.adapters.PagerStateAdapter adapter = new istat.android.freedev.pagers.adapters.PagerStateAdapter(fm);
                for (Fragment page : pages) {
                    adapter.addPage(page);
                }
                return adapter;
            }
        };
    }

    public PageTurner getPageTurner() {
        return mTurner;
    }

    public abstract class PageInflater {
        FragmentManager fragmentManager;
        private final List<Fragment> pages = new ArrayList<>();

        PageInflater(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        public final PageInflater add(Fragment... fragments) {
            for (Fragment fragment : fragments) {
                pages.add(fragment);
            }
            return this;
        }

        public final PagerAdapter apply() {
            return onApply(fragmentManager, pages);
        }

        protected abstract PagerAdapter onApply(FragmentManager fm, List<Fragment> pages);
    }

}
