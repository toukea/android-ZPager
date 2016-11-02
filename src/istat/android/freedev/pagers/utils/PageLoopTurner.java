package istat.android.freedev.pagers.utils;

import istat.android.freedev.pagers.pages.Page;

/**
 * Created by istat on 01/11/16.
 */

public class PageLoopTurner {
    int interval = 1000;
    PageTurner mTurner;

    public PageLoopTurner(int turnInterval, PageTurner turner) {
        this.interval = turnInterval;
        this.mTurner = turner;
    }
}
