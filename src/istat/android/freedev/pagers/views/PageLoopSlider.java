package istat.android.freedev.pagers.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

import istat.android.freedev.pagers.adapters.PagerLooperAdapter;
import istat.android.freedev.pagers.adapters.PagerStateLooperAdapter;
import istat.android.freedev.pagers.interfaces.SlideAble;

/**
 * Created by istat on 26/10/16.
 */

public class PageLoopSlider extends PageSlider implements SlideAble {
    private FragmentManager fragmentManager;

    public PageLoopSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PageLoopSlider(Context context) {
        super(context);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        throw new RuntimeException("Sorry, you can't specify and custom adapter for a SlideView. it has alrady named as PagerLooperAdapter");
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
    protected void onPrepare(Context context) {
        super.onPrepare(context);
        if (context instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) context;
            setFragmentManager((activity.getSupportFragmentManager()));
        }

    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

    }

    public final Slider getSlider(FragmentManager fm) {
        if (slider != null && slider.fragmentManager != fm) {
            return slider;
        } else {
            return new Slider(fm, this);
        }
    }

    public final Slider newSlider(FragmentManager fm) {
        slider = new Slider(fm, this);
        return slider;
    }

    public Slider optSlider() {
        return slider;
    }

    Slider slider;

    public final void startSliding(int initialPosition, FragmentManager fm, Fragment... fragments) {
        startSliding(fm, fragments);
        setCurrentItemInternally(initialPosition);
    }

    public void startSliding(FragmentManager fm, Fragment... fragments) {
        if (fragments.length >= 4) {
            // PagerLooperAdapter mSlideAdapter = new PagerLooperAdapter(fm, fragments);
            PagerStateLooperAdapter mSlideAdapter = new PagerStateLooperAdapter(fm, fragments);
            this.setAdapter(mSlideAdapter);
        } else {
            this.setAdapterInternally(new istat.android.freedev.pagers.adapters.PagerAdapter(fm, fragments));
        }
    }

    public final void startSliding(FragmentManager fm) {
        PagerLooperAdapter mSlideAdapter = new PagerLooperAdapter(fm);
        this.setAdapter(mSlideAdapter);
    }

    @Override
    public void startSliding(FragmentManager fm, int slideIndex) {

    }

    @Override
    public void stopSliding() {

    }

    @Override
    public final PageInflater getFragmentPageInflater(FragmentManager fm) {
        return new PageInflater(fm) {
            @Override
            public PagerAdapter onApply(FragmentManager fm) {
                PagerLooperAdapter adapter = new PagerLooperAdapter(fm);
                for (Fragment page : pages) {
                    adapter.addPage(page);
                }
                return adapter;
            }
        };
    }

    @Override
    public final PageInflater getFragmentStatePageInflater(FragmentManager fm) {
        return new PageInflater(fm) {
            @Override
            public PagerAdapter onApply(FragmentManager fm) {
                PagerStateLooperAdapter adapter = new PagerStateLooperAdapter(fm);
                for (Fragment page : pages) {
                    adapter.addPage(page);
                }
                return adapter;
            }
        };
    }



}
