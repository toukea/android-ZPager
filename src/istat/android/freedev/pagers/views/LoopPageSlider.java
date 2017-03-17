package istat.android.freedev.pagers.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

import java.util.List;

import istat.android.freedev.pagers.adapters.PagerLooperAdapter;
import istat.android.freedev.pagers.adapters.PagerStateLooperAdapter;
import istat.android.freedev.pagers.interfaces.SlideAble;

/**
 * Created by istat on 26/10/16.
 */

public class LoopPageSlider extends PageSlider {

    public LoopPageSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoopPageSlider(Context context) {
        super(context);
    }

    @Override
    public int getCurrentItem() {
        return super.getCurrentItem();
    }

    public void setAdapter(PagerLooperAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void setAdapter(PagerStateLooperAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        throw new RuntimeException("Sorry, you can't specify and custom adapter for a SlideView. it has alrady named as PagerLooperAdapter");
    }

    final void startSliding(int initialPosition, FragmentManager fm, Fragment... fragments) {
        startSliding(fm, fragments);
        setCurrentItemInternally(initialPosition);
    }

    void startSliding(FragmentManager fm, Fragment... fragments) {
        if (fragments.length >= 4) {
            // PagerLooperAdapter mSlideAdapter = new PagerLooperAdapter(fm, fragments);
            PagerStateLooperAdapter mSlideAdapter = new PagerStateLooperAdapter(fm, fragments);
            this.setAdapter(mSlideAdapter);
        } else {
            this.setAdapterInternally(new istat.android.freedev.pagers.adapters.PagerAdapter(fm, fragments));
        }
    }

    final void startSliding(FragmentManager fm) {
        PagerLooperAdapter mSlideAdapter = new PagerLooperAdapter(fm);
        this.setAdapter(mSlideAdapter);
    }

    @Override
    public PageInflater getPageInflater(FragmentManager fm) {
        return new PageInflater(fm) {
            @Override
            public PagerAdapter onApply(FragmentManager fm, List<Fragment> pages) {
                PagerLooperAdapter adapter = new PagerLooperAdapter(fm);
                for (Fragment page : pages) {
                    adapter.addPage(page);
                }
                return adapter;
            }
        };
    }

    @Override
    public PageInflater getPageStateInflater(FragmentManager fm) {
        return new PageInflater(fm) {
            @Override
            public PagerAdapter onApply(FragmentManager fm, List<Fragment> pages) {
                PagerStateLooperAdapter adapter = new PagerStateLooperAdapter(fm);
                for (Fragment page : pages) {
                    adapter.addPage(page);
                }
                return adapter;
            }
        };
    }


}
