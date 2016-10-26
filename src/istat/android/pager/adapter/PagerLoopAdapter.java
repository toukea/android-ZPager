package istat.android.pager.adapter;

import istat.android.pager.page.ScreenPage;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagerLoopAdapter extends ScreenPagerAdapter {
	List<ScreenPage> fakeItems = new ArrayList<ScreenPage>();

	public PagerLoopAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (screens.size() <= 1)
			return screens.size();
		return 1000;

	}

	public int getCount(boolean real) {
		// TODO Auto-generated method stub
		if (!real)
			return getCount();
		else
			return screens.size();

	}

	public int getFakeItemCount(boolean real) {
		// TODO Auto-generated method stub

		return fakeItems.size();

	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub

		/*
		 * if(screens.size()<4){ int i=screens.size()-1;
		 * while(screens.size()<4){ ScreenPage
		 * fakePage=FakePage.newInstance(screens.get(i));
		 * fakeDrags.add(fakePage); screens.add(fakePage);
		 * //screens.add(FakePage.newInstance("fake")); i--; }
		 * notifyDataSetChanged(); }
		 */
		if (position >= screens.size()) {

			Fragment frag = getItem(position % screens.size());
			// Log.e("FRAG_getItem", ""+frag+" TAG:"+frag.getTag());
			return frag;
		}

		return super.getItem(position);

	}

	public static Fragment copyFragment(Fragment fragment) {

		final ScreenPage tmpPage = (ScreenPage) fragment;
		ScreenPage out = new ScreenPage() {

			@Override
			public int getIcon() {
				// TODO Auto-generated method stub
				return tmpPage.getIcon();
			}

			@Override
			public String getTitle() {
				// TODO Auto-generated method stub
				return tmpPage.getTitle();
			}

			@Override
			public View onCreateView(LayoutInflater inflater,
					ViewGroup container, Bundle savedInstanceState) {
				// TODO Auto-generated method stub
				return tmpPage.getView();
			}
		};
		return out;
	}
}
