package istat.android.freedev.pagers.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import istat.android.freedev.pagers.pages.Page;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class PagerStateLooperAdapter extends PagerStateAdapter {
    public PagerStateLooperAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerStateLooperAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm, fragments);
    }

    public PagerStateLooperAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, fragments.toArray(new Fragment[fragments.size()]));
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
