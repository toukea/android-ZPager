package istat.android.freedev.pagers.adapters;

import java.util.ArrayList;
import java.util.List;

import istat.android.freedev.pagers.pages.Page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class PagerStateLooperAdapter extends PagerStateAdapter {
    public PagerStateLooperAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerStateLooperAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm, fragments);
    }

    @Override
    public int getCount() {
        if (pages.size() <= 1)
            return pages.size();
        return 1000;

    }

    public final int getSlidePageCount() {
        return pages.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= pages.size()) {
            Fragment frag = getItem(position % pages.size());
            return frag;
        }
        return super.getItem(position);

    }
}
