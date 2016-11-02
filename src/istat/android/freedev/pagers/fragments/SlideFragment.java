package istat.android.freedev.pagers.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import istat.android.freedev.pagers.interfaces.SlideAble;
import istat.android.freedev.pagers.views.LoopPageSlider;

/**
 * Created by istat on 25/10/16.
 */

public class SlideFragment extends Fragment {
    LoopPageSlider slideView;

    public LoopPageSlider getSlideView() {
        return slideView;
    }

    public void startSliding(String... pathOrURL) {

    }

    public final void startSliding(Fragment... fragments) {
        slideView.startSliding(getFragmentManager(), fragments);
    }

}
