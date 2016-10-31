package istat.android.freedev.pagers.adapters;

import istat.android.freedev.pagers.pages.Page;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class PagerLooperAdapter extends PagerAdapter {
    List<Page> fakeItems = new ArrayList<Page>();

    public PagerLooperAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerLooperAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm, fragments);
    }


    @Override
    public int getCount() {
        if (pages.size() <= 1)
            return pages.size();
        return 1000;

    }

    public int getCount(boolean real) {
        if (!real)
            return getCount();
        else
            return pages.size();

    }

    public int getFakeItemCount(boolean real) {
        return fakeItems.size();

    }

    @Override
    public Fragment getItem(int position) {
        if (position >= pages.size()) {

            Fragment frag = getItem(position % pages.size());
            // Log.e("FRAG_getItem", ""+frag+" TAG:"+frag.getTag());
            return frag;
        }
        return super.getItem(position);
    }
}
