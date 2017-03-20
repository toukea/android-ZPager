package istat.android.freedev.pagers.adapters;

import istat.android.freedev.pagers.adapters.abs.AbsPagerStateAdapter;
import istat.android.freedev.pagers.pages.Page;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * A simple pager adapter that represents a PagePageFragment objects, in
 * sequence.
 */
public class PagerStateAdapter extends AbsPagerStateAdapter {
    protected List<Fragment> pages = new ArrayList<Fragment>();

    // -----------------------------------------------
    public void addPage(Fragment screen) {
        pages.add(screen);
    }

    public void addPage(Fragment... screen) {
        for (Fragment fragment : screen) {
            pages.add(fragment);
        }
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

    public PagerStateAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerStateAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        for (Fragment fragment : fragments) {
            addPage(fragment);
        }
    }
    public PagerStateAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments.toArray(new Fragment[fragments.size()]));
    }


    @Override
    public CharSequence getPageTitle(int position) {
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
        if (getItem(index) instanceof Page)
            return ((Page) getItem(index)).getIcon();
        else
            return 0;
    }

    public Fragment getPageAtIndex(int index) {
        Fragment page = pages.get(index);
        if (page.getTag() == null) {
            Fragment tmpPage = getFragmentManager().findFragmentByTag("SCREEN:"
                    + index);
            if (tmpPage != null)
                return tmpPage;
        }
        return page;
    }

}