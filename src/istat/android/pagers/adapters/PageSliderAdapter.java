package istat.android.pagers.adapters;

import istat.android.pagers.pages.Page;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * A simple pager adapter that represents a PagePageFragment objects, in
 * sequence.
 */
public class PageSliderAdapter extends BasePagerAdapter
/* FragmentPagerAdapter */ {
    final static String TAG_PAGE_PREFIX = "SCREEN";
    protected List<Fragment> screens = new ArrayList<Fragment>();
    protected FragmentManager mFragmentManager;
    int fixedCount = -1;

    // -----------------------------------------------
    public void addPage(Fragment screen) {
        screens.add(screen);
    }

    public void addPage(Fragment... screens) {
        for (Fragment fragment : screens)
            addPage(fragment);
    }

    public void addPage(List<Fragment> screen) {
        for (Fragment fragment : screen)
            addPage(fragment);
    }

    // ---------------------------------------------
    public void removePage(int position) {
        screens.remove(position);
        FragmentTransaction t = mFragmentManager.beginTransaction();
        t.remove(mFragmentManager.findFragmentByTag(TAG_PAGE_PREFIX + position));
        t.commit();
        notifyDataSetChanged();
    }

    public void removeAllPage() {
        FragmentTransaction t = mFragmentManager.beginTransaction();
        for (int i = 0; i < screens.size(); i++) {
            t.remove(mFragmentManager.findFragmentByTag(TAG_PAGE_PREFIX + i));
        }
        t.commit();
        screens = new ArrayList<Fragment>();

    }

    public PageSliderAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (getItem(position) instanceof Page)
            return ((Page) getItem(position)).getTitle();
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return getPageAtIndex(position);// pages.get(position);

    }

    @Override
    public int getCount() {
        if (fixedCount > 0)
            return fixedCount;
        return screens.size();
    }

    public List<Fragment> getPages() {
        List<Fragment> screens = new ArrayList<Fragment>();
        for (int i = 0; i < this.screens.size(); i++) {
            screens.add(getPageAtIndex(i));
        }
        return screens;
    }

    public int getIconResId(int index) {
        if (getItem(index) instanceof Page)
            return ((Page) getItem(index)).getIcon();
        else
            return 0;
    }

    public Fragment getPageAtIndex(int index) {
        if (screens.size() <= index)
            return mFragmentManager.findFragmentByTag(TAG_PAGE_PREFIX + index);
        Fragment page = screens.get(index);
        if (page.getTag() == null) {
            Fragment tmpPage = mFragmentManager.findFragmentByTag(TAG_PAGE_PREFIX
                    + index);
            if (tmpPage != null)
                return tmpPage;
        }
        return page;
    }

    public void fixPageCount(int fixedCount) {
        this.fixedCount = fixedCount;
    }

}