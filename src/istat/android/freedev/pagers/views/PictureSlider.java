package istat.android.freedev.pagers.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

import java.util.List;

import istat.android.freedev.pagers.adapters.PagerLooperAdapter;
import istat.android.freedev.pagers.adapters.PagerStateLooperAdapter;
import istat.android.freedev.pagers.adapters.abs.AbsPagerStateAdapter;
import istat.android.freedev.pagers.pages.PicturePage;
import istat.android.freedev.pagers.utils.PageAutoTurner;


/**
 * Created by istat on 29/10/16.
 */

public class PictureSlider extends LoopPageSlider {
    public final static int MIN_LENGTH_SUPPORTED = 3;
    private static final int DEFAULT_SLIDE_INTERVAL = 1000;
    int slideInterval = DEFAULT_SLIDE_INTERVAL;

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
    }

    public int getSlideInterval() {
        return slideInterval;
    }

    @Override
    public void startSliding(FragmentManager fm, Fragment... fragments) {
        if (fragments.length >= MIN_LENGTH_SUPPORTED) {
            PagerStateLooperAdapter mSlideAdapter = new PagerStateLooperAdapter(fm, fragments);
            this.setAdapter(mSlideAdapter);
        } else {
            this.setAdapterInternally(new istat.android.freedev.pagers.adapters.PagerAdapter(fm, fragments));
        }
        PageAutoTurner turner = new PageAutoTurner(slideInterval, this);
        turner.start();
    }

    PageAutoTurner autoTurner;

    void start() {
        autoTurner = new PageAutoTurner(slideInterval, this);
        autoTurner.start();
    }

    public void stop() {
        autoTurner.stop();
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

    public final Object getPath(int index) {
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
            return ((PicturePage) page).getPath();
        }
        return null;
    }

    public final <T> T optPath(int index) {
        Object obj = getPath(index);
        return obj == null ? null : (T) obj;
    }

//    @Override
//    public SlideInflater getPageStateInflater(FragmentManager fm) {
//        return null;//super.getPageStateInflater(fm);
//    }
//
//    @Override
//    public SlideInflater getPageInflater(FragmentManager fm) {
//        return null;// super.getPageInflater(fm);
//    }
//
//    final class SlideInflater extends PageInflater {
//        SlideInflater(FragmentManager fragmentManager) {
//            super(fragmentManager);
//        }
//
//        public SlideInflater addSlideRessource(int ressource) {
//            return this;
//        }
//
//        public SlideInflater addSlideDrawable(Drawable drawable) {
//
//            return this;
//        }
//
////        public SlideInflater addSlideUri(Uri... uris) {
////            for (Uri uri : uris) {
////                slidePath.add(uri.getPath());
////            }
////            return this;
////        }
////
////        public SlideInflater addSlidePath(String... url_or_paths) {
////            for (String path : url_or_paths) {
////                path = path.startsWith("/") ? path : "/" + path;
////                slidePath.add("file://" + path);
////            }
////            return this;
////        }
//
//        @Override
//        protected PagerAdapter onApply(FragmentManager fm, List<Fragment> pages) {
//            return null;
//        }
//
////        public void start() {
////            slideAble.startSliding(this.fragmentManager, slideAble.getCurrentItem());
////        }
////
////        public void stop() {
////            slideAble.stopSliding();
////        }
////
////        public void slideTo(int index) {
////            slideAble.startSliding(this.fragmentManager, index);
////        }
////
////        void begin() {
////
////        }
////
////        void end() {
////
////        }
//    }

    public void setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.onPageClickListener = onPageClickListener;
    }

    OnPageClickListener onPageClickListener;

    public interface OnPageClickListener {

    }


}
