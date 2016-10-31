package istat.android.pagers.adapters;

import java.util.ArrayList;
import java.util.List;

import istat.android.pagers.pages.Page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class PageStateLooperAdapter extends PageStateSliderAdapter {
    List<Page> fakeItems = new ArrayList<Page>();

    public PageStateLooperAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (pages.size() <= 1)
            return pages.size();
        return 1000;

    }

    public int getCount(boolean real) {
        // TODO Auto-generated method stub
        if (!real)
            return getCount();
        else
            return pages.size();

    }

    public int getFakeItemCount(boolean real) {
        // TODO Auto-generated method stub

        return fakeItems.size();

    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub

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
