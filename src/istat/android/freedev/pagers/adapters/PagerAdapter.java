package istat.android.freedev.pagers.adapters;

import istat.android.freedev.pagers.adapters.abs.AbsPagerAdapter;
import istat.android.freedev.pagers.pages.Page;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.json.JSONArray;

/**
 * A simple pager adapter that represents a PagePageFragment objects, in
 * sequence.
 */
public class PagerAdapter extends AbsPagerAdapter
/* FragmentPagerAdapter */ {
    protected List<Fragment> pages = new ArrayList<Fragment>();
    int fixedCount = -1;

    // -----------------------------------------------
    public void addPage(Fragment screen) {
        pages.add(screen);
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
        pages.remove(position);
        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.remove(getFragmentManager().findFragmentByTag(TAG_PAGE_PREFIX + position));
        t.commit();
        notifyDataSetChanged();
    }

    public void removeAllPage() {
        FragmentTransaction t = getFragmentManager().beginTransaction();
        for (int i = 0; i < pages.size(); i++) {
            t.remove(getFragmentManager().findFragmentByTag(TAG_PAGE_PREFIX + i));
        }
        t.commit();
        pages = new ArrayList<Fragment>();

    }

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        for (Fragment fragment : fragments) {
            addPage(fragment);
        }
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
        return pages.size();
    }

    public List<Fragment> getPages() {
        List<Fragment> screens = new ArrayList<Fragment>();
        for (int i = 0; i < this.pages.size(); i++) {
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
        if (pages.size() <= index)
            return getFragmentManager().findFragmentByTag(TAG_PAGE_PREFIX + index);
        Fragment page = pages.get(index);
        if (page.getTag() == null) {
            Fragment tmpPage = getFragmentManager().findFragmentByTag(TAG_PAGE_PREFIX
                    + index);
            if (tmpPage != null)
                return tmpPage;
        }
        return page;
    }

    public void fixPageCount(int fixedCount) {
        this.fixedCount = fixedCount;
    }

    public final void saveInstanceBundle(Bundle bundle) {
        onSaveInstanceBundle(bundle);
    }

    public final void restoreInstanceFromBundle(Bundle bundle) {
        onRestoreInstanceFromBundle(bundle);
    }

    @CallSuper
    protected void onRestoreInstanceFromBundle(Bundle bundle) {
        JSONArray json = new JSONArray();
        for (Fragment page : pages) {
            json.put(page.getTag());
        }
        bundle.putString(TAG_PAGE_PREFIX, json.toString());

    }

    public final static boolean canRestoreInstance(Bundle bundle) {
        return bundle.containsKey(TAG_PAGE_PREFIX);
    }

    final static String TAG_PAGE_PREFIX = "istat.android.freedev.pagers.SCREEN";

    @CallSuper
    protected void onSaveInstanceBundle(Bundle bundle) {
        if (bundle.containsKey(TAG_PAGE_PREFIX)) {
            try {
                String jsonString = bundle.getString(TAG_PAGE_PREFIX);
                JSONArray jArray = new JSONArray(jsonString);
                for (int i = 0; i < jArray.length(); i++) {
                    Fragment page = getFragmentManager().findFragmentByTag(jArray.getString(i));
                    addPage(page);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}