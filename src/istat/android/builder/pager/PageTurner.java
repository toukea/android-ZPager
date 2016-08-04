package istat.android.builder.pager;

import android.os.Handler;
import android.support.v4.view.ViewPager;

public class PageTurner implements Runnable {
	public static int DIRECTION_LEFT = -1, DIRECTION_RIGTH = 1;
	int timeCount = 0;
	int turnDirection = -1;

	double xPosition = 0;
	TurnConfiguration configuration = new TurnConfiguration();
	private Handler handler = new Handler();
	private ViewPager pager;
	boolean run = true;

	private PageTurner(PageTurner turner) {
		this.pager = turner.pager;
		turnDirection = turner.turnDirection;
		configuration = turner.configuration;
		pager = turner.pager;

	}

	public PageTurner(ViewPager pager) {
		this.pager = pager;
	}

	public PageTurner(ViewPager pager, TurnConfiguration configuration) {
		this.pager = pager;
		this.configuration = configuration;
	}

	public PageTurner(Handler handler, ViewPager pager) {
		this.handler = handler;
		this.pager = pager;
	}

	TurnCallBack turnCallBack;

	public void turnPage(int direction) {
		turnPage(direction, null);
	}

	public void turnPage(int direction, TurnCallBack callback) {

		if (direction > 0) {
			turnDirection = 1;
		} else {
			turnDirection = -1;
		}
		run = true;
		timeCount = 0;
		pager.beginFakeDrag();
		handler.post(new PageTurner(this));
		this.turnCallBack = callback;

	}

	public void stopTurning() {
		run = false;
	}

	public void turnPageLeft(TurnCallBack callBack) {
		turnPage(DIRECTION_LEFT, callBack);
	}

	public void turnPageRight(TurnCallBack callBack) {
		turnPage(DIRECTION_RIGTH, callBack);
	}

	public void turnPageLeft() {
		turnPage(DIRECTION_LEFT);
	}

	public void turnPageRight() {
		turnPage(DIRECTION_RIGTH);
	}

	public boolean isTurning() {
		return run;
	}

	@Override
	public void run() {
		int previousIndex = pager.getCurrentItem();
		if (pager.isFakeDragging()) {
			int velocity = turnDirection * configuration.acceleration
					* timeCount + configuration.initialVelocity;
			if (xPosition < pager.getWidth() - Math.abs(velocity)) {
				timeCount++;
				double gap = pager.getWidth() - xPosition;
				if (gap < velocity)
					velocity = (int) (turnDirection * gap);

				pager.fakeDragBy(velocity);
				xPosition = Math.abs(velocity) + xPosition;
				if (run)
					handler.postDelayed(this, configuration.refreshTime);
			} else {
				run = false;
				pager.endFakeDrag();
				if (turnCallBack != null) {
					turnCallBack.onTurnComplete(pager, previousIndex,
							previousIndex + turnDirection, turnDirection);
				}
			}
		}

	}

	public void setTurnerConfiguration(TurnConfiguration configuration) {
		this.configuration = configuration;
	}

	public static class TurnConfiguration {
		int initialVelocity = 1, acceleration = 2, refreshTime = 50;

		public TurnConfiguration() {
		}

		public TurnConfiguration(int velocity, int acceleration, int refreshTime) {
			this.initialVelocity = velocity;
			this.acceleration = acceleration;
			this.refreshTime = refreshTime;
		}

		public TurnConfiguration(int velocity, int acceleration) {
			this.initialVelocity = velocity;
			this.acceleration = acceleration;

		}

		public TurnConfiguration setAcceleration(int acceleration) {
			this.acceleration = Math.abs(acceleration);
			return this;
		}

		public TurnConfiguration setInitialVelocity(int initialVelocity) {
			this.initialVelocity = Math.abs(initialVelocity);
			return this;
		}

		public TurnConfiguration setRefreshTime(int refreshTime) {
			this.refreshTime = Math.abs(refreshTime);
			return this;
		}
	}

	public static interface TurnCallBack {

		public void onTurnComplete(ViewPager pager, int previousIndex,
				int index, int direction);
	}
}