package istat.android.freedev.pagers.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import android.util.AttributeSet;

import java.util.List;

import istat.android.freedev.pagers.adapters.PagerLooperAdapter;
import istat.android.freedev.pagers.adapters.PagerStateLooperAdapter;
//import istat.android.freedev.pagers.interfaces.SlideAble;

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
    @Deprecated
    public void setAdapter(PagerAdapter adapter) {
        throw new RuntimeException("Sorry, you can't specify and custom adapter for a SlideView. it has alrady named as PagerLooperAdapter");
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
