package istat.android.builder.pager.adapter;

import istat.android.base.fragments.ScreenPage;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * A simple pager adapter that represents a ScreenPageFragment objects, in
 * sequence.
 */
public class ScreenPagerAdapter extends IstatPagerAdapter
/* FragmentPagerAdapter */{
	protected List<Fragment> screens = new ArrayList<Fragment>();
	protected FragmentManager mFragmentManager;
	int fixedCount = -1;

	// -----------------------------------------------
	public void addScreen(Fragment screen) {
		screens.add(screen);
	}

	public void addScreen(Fragment[] screen) {
		for (Fragment fragment : screen)
			addScreen(fragment);
	}

	public void addScreen(List<Fragment> screen) {
		for (Fragment fragment : screen)
			addScreen(fragment);
	}

	// ------------------------------------------------
	public void addAndUpdateScreen(Fragment screen) {
		addScreen(screen);
		notifyDataSetChanged();
	}

	public void addAndUpdateScreen(Fragment[] screen) {
		for (Fragment fragment : screen)
			screens.add(fragment);
		notifyDataSetChanged();
	}

	public void addAndUpdateScreen(List<Fragment> screen) {
		for (Fragment fragment : screen)
			addScreen(fragment);
		notifyDataSetChanged();
	}

	// ---------------------------------------------
	public void removeScreen(int position) {
		screens.remove(position);
		FragmentTransaction t = mFragmentManager.beginTransaction();
		t.remove(mFragmentManager.findFragmentByTag("SCREEN:" + position));
		t.commit();
		notifyDataSetChanged();
	}

	public void removeAllScreen() {
		FragmentTransaction t = mFragmentManager.beginTransaction();
		for (int i = 0; i < screens.size(); i++) {
			t.remove(mFragmentManager.findFragmentByTag("SCREEN:" + i));
		}
		t.commit();
		screens = new ArrayList<Fragment>();

	}

	public ScreenPagerAdapter(FragmentManager fm) {
		super(fm);
		mFragmentManager = fm;
	}

	public FragmentManager getFragmentManager() {
		return mFragmentManager;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		if (getItem(position) instanceof ScreenPage)
			return ((ScreenPage) getItem(position)).getTitle();
		return "";
	}

	@Override
	public Fragment getItem(int position) {
		return getScreenAtIndex(position);// screens.get(position);

	}

	@Override
	public int getCount() {
		if (fixedCount > 0)
			return fixedCount;
		return screens.size();
	}

	public List<Fragment> getScreens() {
		List<Fragment> screens = new ArrayList<Fragment>();
		for (int i = 0; i < this.screens.size(); i++) {
			screens.add(getScreenAtIndex(i));
		}
		return screens;
	}

	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		if (getItem(index) instanceof ScreenPage)
			return ((ScreenPage) getItem(index)).getIcone();
		else
			return 0;
	}

	public Fragment getScreenAtIndex(int index) {
		if (screens.size() <= index)
			return mFragmentManager.findFragmentByTag("SCREEN:" + index);
		Fragment page = screens.get(index);
		if (page.getTag() == null) {
			Fragment tmpPage = mFragmentManager.findFragmentByTag("SCREEN:"
					+ index);
			if (tmpPage != null)
				return tmpPage;
		}
		return page;
	}

	public void fixScreenCount(int fixedCount) {
		this.fixedCount = fixedCount;
	}

}