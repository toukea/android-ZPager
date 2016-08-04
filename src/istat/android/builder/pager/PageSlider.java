package istat.android.builder.pager;

import istat.android.builder.pager.PageTurner.TurnConfiguration;
import istat.android.builder.pager.adapter.LooperPagerAdapter;
import istat.android.builder.pager.adapter.LooperStatePagerAdapter;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class PageSlider extends ViewPager {
	PageTurner mTurner;

	public PageSlider(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initPageSlider();
	}

	public PageSlider(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initPageSlider();
	}

	void initPageSlider() {
		mTurner = new PageTurner(this);

	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
		if (adapter instanceof LooperPagerAdapter) {
			LooperPagerAdapter tmpAdapter = (LooperPagerAdapter) adapter;
			if (tmpAdapter.getCount(true) <= 1)
				return;
			if (tmpAdapter.getCount(true) % 2 == 0)
				this.setCurrentItem(500);
			else
				this.setCurrentItem(501);
		}
		if (adapter instanceof LooperStatePagerAdapter) {
			LooperStatePagerAdapter tmpAdapter = (LooperStatePagerAdapter) adapter;
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
