package istat.android.pagers.adapters;

import istat.android.pagers.pages.Page;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class PageSliderLooperAdapter extends PageSliderAdapter {
    List<Page> fakeItems = new ArrayList<Page>();

    public PageSliderLooperAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public int getCount() {
        if (screens.size() <= 1)
            return screens.size();
        return 1000;

    }

    public int getCount(boolean real) {
        if (!real)
            return getCount();
        else
            return screens.size();

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
        if (position >= screens.size()) {

            Fragment frag = getItem(position % screens.size());
            // Log.e("FRAG_getItem", ""+frag+" TAG:"+frag.getTag());
            return frag;
        }

        return super.getItem(position);

    }
}
