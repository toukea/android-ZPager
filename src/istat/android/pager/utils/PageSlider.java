package istat.android.pager.utils;

import istat.android.pager.adapter.PagerLoopAdapter;
import istat.android.pager.adapter.PagerLoopStateAdapter;
import istat.android.pager.utils.PageTurner.TurnConfiguration;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class PageSlider extends ViewPager {
	PageTurner mTurner;

	public PageSlider(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPageSlider();
	}

	public PageSlider(Context context) {
		super(context);
		initPageSlider();
	}

	void initPageSlider() {
		mTurner = new PageTurner(this);

	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		super.setAdapter(adapter);
		if (adapter instanceof PagerLoopAdapter) {
			PagerLoopAdapter tmpAdapter = (PagerLoopAdapter) adapter;
			if (tmpAdapter.getCount(true) <= 1)
				return;
			if (tmpAdapter.getCount(true) % 2 == 0)
				this.setCurrentItem(500);
			else
				this.setCurrentItem(501);
		}
		if (adapter instanceof PagerLoopStateAdapter) {
			PagerLoopStateAdapter tmpAdapter = (PagerLoopStateAdapter) adapter;
			if (tmpAdapter.getCount(true) <= 1)
				return;
			if (tmpAdapter.getCount(true) % 2 == 0)
				this.setCurrentItem(500);
			else
				this.setCurrentItem(501);
		}
	}

	public void slideToNextPage() {
		mTurner.turnPageLeft();
	}

	public void slideToPreviousPage() {
		mTurner.turnPageRight();
	}

	public void setPageTurnerConfiguration(TurnConfiguration config) {
		mTurner.setTurnerConfiguration(config);
	}

}
