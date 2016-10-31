package istat.android.freedev.pagers.interfaces;

import android.support.v4.app.FragmentManager;

/**
 * Created by istat on 27/10/16.
 */

public interface SlideAble {

    public void startSliding(FragmentManager fm, int slideIndex);

    public void stopSliding();

    public int getCurrentItem();
}
