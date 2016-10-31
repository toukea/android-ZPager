package istat.android.pagers.adapters;

import istat.android.pagers.pages.Page;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * A simple pager adapter that represents a PagePageFragment objects, in
 * sequence.
 */
public class PageStateSliderAdapter extends BasePagerStateAdapter {
	protected List<Fragment> pages = new ArrayList<Fragment>();
	FragmentManager mFragmentManager;

	// -----------------------------------------------
	public void addPage(Fragment screen) {
		pages.add(screen);
	}

	public void addPage(Fragment[] screen) {
		for (Fragment fragment : screen)
			pages.add(fragment);
	}

	public void addPage(List<Fragment> screen) {
		for (Fragment fragment : screen)
			pages.add(fragment);
	}
	
	// ---------------------------------------------
	public void removePage(int position) {
		pages.remove(position);
		notifyDataSetChanged();
	}

	public void removeAllPage() {
		pages = new ArrayList<Fragment>();

	}

	public PageStateSliderAdapter(FragmentManager fm) {
		super(fm);
		mFragmentManager = fm;
	}

	public FragmentManager getFragmentManager() {
		return mFragmentManager;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		if (getItem(position) instanceof Page)
			return ((Page) getItem(position)).getTitle();
		return "";
	}

	@Override
	public Fragment getItem(int position) {

		return pages.get(position);

	}

	@Override
	public int getCount() {
		return pages.size();
	}

	public List<Fragment> getPages() {
		return pages;
	}

	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		if (getItem(index) instanceof Page)
			return ((Page) getItem(index)).getIcon();
		else
			return 0;
	}

	public Fragment getPageAtIndex(int index) {
		Fragment page = pages.get(index);
		if (page.getTag() == null) {
			Fragment tmpPage = mFragmentManager.findFragmentByTag("SCREEN:"
					+ index);
			if (tmpPage != null)
				return tmpPage;
		}
		return page;
	}

}