package istat.android.freedev.pagers.fragments;


import istat.android.freedev.pagers.pages.PicturePage;

/**
 * Created by istat on 29/10/16.
 */

public class PictureSlideFragment extends SlideFragment {
    public final void startSliding(Object... paths) {
        super.startSliding(PicturePage.newArrayInstances(paths));
    }
}
