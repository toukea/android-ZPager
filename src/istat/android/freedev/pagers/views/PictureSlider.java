package istat.android.freedev.pagers.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

import istat.android.freedev.pagers.pages.PicturePage;


/**
 * Created by istat on 29/10/16.
 */

public class PictureSlider extends PageLoopSlider {
    public final static int MIN_LENGTH_SUPPORTED = 4;

    public PictureSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureSlider(Context context) {
        super(context);
    }

    public final void startSliding(FragmentManager fm, Object... paths) {
        Object[] tmpPaths = paths;
        if (paths.length < MIN_LENGTH_SUPPORTED) {
            tmpPaths = new Object[MIN_LENGTH_SUPPORTED];
            for (int i = 0; i < MIN_LENGTH_SUPPORTED; i++) {
                int index = i;
                if (i >= paths.length) {
                    index = (i + 1) % paths.length;
                }
                tmpPaths[i] = paths[index];
            }
        }
        super.startSliding(fm, PicturePage.newArrayInstances(tmpPaths));
    }

    public final void startSlidingPath(FragmentManager fm, String... paths) {
        startSliding(fm, paths);
    }

    public final void startSlidingDrawables(FragmentManager fm, Drawable... drawable) {
        startSliding(fm, drawable);
    }

    public final void startSlidingRessource(FragmentManager fm, int... ResourceId) {
        startSliding(fm, ResourceId);
    }

    @Override
    void setAdapterInternally(PagerAdapter adapter) {
        super.setAdapterInternally(adapter);
    }
}
