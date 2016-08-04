package istat.android.builder.pager.adapter;


import istat.android.base.fragments.ScreenPage;
import istat.android.builder.pager.indicator.IconPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * A simple pager adapter that represents a ScreenPageFragment objects, in
 * sequence.
 */
public class ScreenStatePagerAdapter extends IstatStatePagerAdapter/*FragmentPagerAdapter*/ implements IconPagerAdapter{
	protected List<Fragment> screens=new ArrayList<Fragment>();
	FragmentManager mFragmentManager;
	//-----------------------------------------------
	public void addScreen(Fragment screen){
		screens.add(screen);
	}
	public void addScreen(Fragment[] screen){
		for(Fragment fragment:screen)
			screens.add(fragment);
	}
	public void addScreen(List<Fragment> screen){
		for(Fragment fragment:screen)
			screens.add(fragment);
	}
	//------------------------------------------------
	public void addAndUpdateScreen(Fragment screen){
		screens.add(screen);
		notifyDataSetChanged();
	}
	public void addAndUpdateScreen(Fragment[] screen){
		for(Fragment fragment:screen)
		screens.add(fragment);
		notifyDataSetChanged();
	}
	public void addAndUpdateScreen(List<Fragment> screen){
		for(Fragment fragment:screen)
		screens.add(fragment);
		notifyDataSetChanged();
	}
	//---------------------------------------------
	public void removeScreen(int position){
		screens.remove(position);
		notifyDataSetChanged();
	}
	public void removeAllScreen(){
		screens=new ArrayList<Fragment>();
		
	}
	
    public ScreenStatePagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager=fm;
    }
    public FragmentManager getFragmentManager() {
		return mFragmentManager;
	}
   @Override
    public CharSequence getPageTitle(int position) {
    	// TODO Auto-generated method stub
	   if(getItem(position) instanceof ScreenPage)
    	return ((ScreenPage)getItem(position)).getTitle();
	   return "";
    }
   

    @Override
    public Fragment getItem(int position) {
    	
    	return screens.get(position);
       
    }

    @Override
    public int getCount() {
        return screens.size();
    }
    public List<Fragment> getScreens() {
		return screens;
	}
    @Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
    	 if(getItem(index) instanceof ScreenPage)
		return ((ScreenPage)getItem(index)).getIcone();
    	 else return 0;
	}
    public Fragment getScreenAtIndex(int index){
    	Fragment page=screens.get(index);
    	if (page.getTag() == null) {
			Fragment tmpPage = mFragmentManager
					.findFragmentByTag("SCREEN:" + index);
			if (tmpPage != null)
				return tmpPage;
		}
		return page;
    }
    
	
}