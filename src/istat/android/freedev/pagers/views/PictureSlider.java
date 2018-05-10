package istat.android.freedev.pagers.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import istat.android.freedev.pagers.adapters.PagerLooperAdapter;
import istat.android.freedev.pagers.adapters.PagerStateLooperAdapter;
import istat.android.freedev.pagers.adapters.AbsPagerStateAdapter;
import istat.android.freedev.pagers.pages.PicturePage;
import istat.android.freedev.pagers.utils.PageAutoTurner;


/**
 * Created by istat on 29/10/16.
 */

public class PictureSlider extends LoopPageSlider {
    public final static int MIN_LENGTH_SUPPORTED = 3;
    private static final int DEFAULT_SLIDE_INTERVAL = 1000;
    int slideInterval = DEFAULT_SLIDE_INTERVAL;
    private final List<Fragment> fragmentList = new ArrayList<>();
    FragmentManager fragmentManager;

    public PictureSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureSlider(Context context) {
        super(context);
    }

    @Override
    @Deprecated
    public final void setAdapter(PagerLooperAdapter adapter) {
        throw new RuntimeException("Sorry, you can't specify and custom adapter for a SlideView. it has alrady named as PagerStateLooperAdapter");
    }

    public final void startSliding(int initialPosition, FragmentManager fm, Object... paths) {
        startSliding(fm, paths);
        setCurrentItem(initialPosition, true);
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
        this.startSliding(fm, PicturePage.newArrayInstances(tmpPaths));
    }

    public final void startSlidingPath(FragmentManager fm, String... paths) {
        startSliding(fm, (Object[]) paths);
    }

    public final void startSlidingDrawables(FragmentManager fm, Drawable... drawable) {
        startSliding(fm, (Object[]) drawable);
    }

    public final void startSlidingRessource(FragmentManager fm, int... ResourceId) {
        startSliding(fm, ResourceId);
    }

    public void setSlideInterval(int slideInterval) {
        this.slideInterval = slideInterval;
        autoSlideEnable = slideInterval > 0;
    }

    public int getSlideInterval() {
        return slideInterval;
    }

    void startSliding(FragmentManager fm, Fragment... fragments) {
        this.fragmentList.addAll(Arrays.asList(fragments));
        startSliding(fm);
    }

    public PictureSlider addSlideContent(String path) {
        fragmentList.add(PicturePage.newInstance(path));
        return this;
    }

    public PictureSlider addSlideContent(Drawable drawable) {
        fragmentList.add(PicturePage.newInstance(drawable));
        return this;
    }

    public PictureSlider addSlideContent(int drawableResource) {
        fragmentList.add(PicturePage.newInstance(drawableResource));
        return this;
    }

    public void clear() {
        fragmentList.clear();
    }

    private void initSlider(FragmentManager fm) {
        fragmentManager = fm;
        if (fragmentList.size() >= MIN_LENGTH_SUPPORTED) {
            PagerStateLooperAdapter mSlideAdapter = new PagerStateLooperAdapter(fm, fragmentList);
            this.setAdapter(mSlideAdapter);
        } else {
            this.setAdapterInternally(new istat.android.freedev.pagers.adapters.PagerAdapter(fm, fragmentList));
        }
    }

    public void startSliding(FragmentManager fm) {
        initSlider(fm);
        start();
    }

    PageAutoTurner autoTurner;
    boolean autoSlideEnable = true;

    void start() {
        start(autoSlideEnable);
    }

    void start(boolean autoTurnEnable) {
        if (autoTurner == null && autoTurner.isRunning()) {
            autoTurner.stop();
        }
        autoTurner = new PageAutoTurner(slideInterval, this);
        autoTurner.enableAutoTurn(autoTurnEnable);
        autoTurner.start();
    }

    public void setAutoMotionEnable(boolean state) {
        if (autoTurner != null) {
            stop();
            start(state);
        }
    }

    public void stop() {
        if (autoTurner != null) {
            autoTurner.stop();
        }
    }

    public void restart() {
        stop();
        setCurrentItem(500);
        start();
    }


    @Override
    void setAdapterInternally(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    public final PicturePage getPage(int index) {
        Fragment page = null;
        if (getAdapter() instanceof FragmentPagerAdapter) {
            page = ((FragmentPagerAdapter) getAdapter()).getItem(index);
        }
        if (getAdapter() instanceof FragmentStatePagerAdapter) {
            page = ((FragmentStatePagerAdapter) getAdapter()).getItem(index);
        }
        if (getAdapter() instanceof AbsPagerStateAdapter) {
            page = ((AbsPagerStateAdapter) getAdapter()).getItem(index);
        }
        if (page == null) {
            return null;
        }
        if (page instanceof PicturePage) {
            return (PicturePage) page;
        }
        return null;
    }

    public final Object getPath(int index) {
        PicturePage page = getPage(index);
        if (page != null) {
            return page.getPath();
        }
        return null;
    }

    public final <T> T optPageContent(int index) {
        Object obj = getPath(index);
        return obj == null ? null : (T) obj;
    }

    public void setOnPageClickListener(final OnPageClickListener onPageClickListener) {
        if (this.getAdapter() != null) {
            for (int i = 0; i < getAdapter().getCount(); i++) {
                final PicturePage page = getPage(i);
                final int finalI = i;
                page.getImageView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPageClickListener.onPageClicked(finalI, page);
                    }
                });
            }
        } else {
        }
    }

    public interface OnPageClickListener {
        void onPageClicked(int index, PicturePage page);

    }


}
