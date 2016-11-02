package istat.android.freedev.pagers.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import istat.android.freedev.pagers.interfaces.SlideAble;
import istat.android.freedev.pagers.views.PageLoopSlider;

/**
 * Created by istat on 25/10/16.
 */

public class SlideFragment extends Fragment implements SlideAble {
    PageLoopSlider slideView;

    public PageLoopSlider getSlideView() {
        return slideView;
    }

    public void startSliding(String... pathOrURL) {

    }

    public final void startSliding(Fragment... fragments) {
        slideView.startSliding(getFragmentManager(), fragments);
    }

    @Override
    public void startSliding(FragmentManager fm, int slideIndex) {
        slideView.startSliding(fm, slideIndex);
    }

    public void startSliding() {
        startSliding(0);
    }

    public void startSliding(int index) {
        startSliding(getFragmentManager(), index);
    }

    @Override
    public void stopSliding() {
        slideView.stopSliding();
    }

    @Override
    public int getCurrentItem() {
        return slideView.getCurrentItem();
    }


}
