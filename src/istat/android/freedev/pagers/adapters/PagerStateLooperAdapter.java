package istat.android.freedev.pagers.adapters;

import java.util.ArrayList;
import java.util.List;

import istat.android.freedev.pagers.pages.Page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class PagerStateLooperAdapter extends PagerStateAdapter {
    List<Page> fakeItems = new ArrayList<Page>();

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
        /*
         * if(pages.size()<4){ int i=pages.size()-1;
		 * while(pages.size()<4){ Page
		 * fakePage=FakePage.newInstance(pages.get(i));
		 * fakeDrags.add(fakePage); pages.add(fakePage);
		 * //pages.add(FakePage.newInstance("fake")); i--; }
		 * notifyDataSetChanged(); }
		 */
        if (position >= pages.size()) {
            Fragment frag = getItem(position % pages.size());
            // Log.e("FRAG_getItem", ""+frag+" TAG:"+frag.getTag());
            return frag;
        }

        return super.getItem(position);

    }
}
