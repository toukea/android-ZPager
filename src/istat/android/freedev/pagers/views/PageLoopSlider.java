package istat.android.freedev.pagers.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

import istat.android.freedev.pagers.adapters.PagerLooperAdapter;
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

    public final void setAdapter(PagerLooperAdapter adapter) {
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

    PagerLooperAdapter mSlideAdapter;
    Slider slider;

    public void startSliding(FragmentManager fm, Fragment... fragments) {
        mSlideAdapter = new PagerLooperAdapter(fm, fragments);
        this.setAdapter(mSlideAdapter);
    }

    public final void startSliding(FragmentManager fm) {
        mSlideAdapter = new PagerLooperAdapter(fm);
        this.setAdapter(mSlideAdapter);
    }

    @Override
    public void startSliding(FragmentManager fm, int slideIndex) {

    }

    @Override
    public void stopSliding() {

    }


}
