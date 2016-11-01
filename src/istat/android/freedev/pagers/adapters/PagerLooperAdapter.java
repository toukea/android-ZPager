package istat.android.freedev.pagers.adapters;

import istat.android.freedev.pagers.pages.Page;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class PagerLooperAdapter extends PagerAdapter {
    public PagerLooperAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerLooperAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm, fragments);
    }


    @Override
    public int getCount() {
        if (pages.size() <= 1)
            return pages.size();
        return 1000;

    }

    public int getCount(boolean real) {
        if (!real)
            return getCount();
        else
            return pages.size();

    }

    public final int getSlidePageCount() {
        return pages.size();
    }


    @Override
    public final Fragment getItem(int position) {
        int loopPosition = position % pages.size();
        int lastRequestedPosition = getLastRequestedPosition();
        int direction = position - lastRequestedPosition ;
        direction = direction == 0 ? 0 : direction / Math.abs(direction);
        if (direction != lastDirection && lastDirection != 0) {
            onDirectionChanged(position, lastRequestedPosition, direction, lastDirection);
        }
        lastDirection = direction;
        if (direction > 0) {
            if (position >= pages.size() - 1) {
                int indexToRemove = (position + direction) % pages.size();
                Fragment fragmentToRemove = super.getItem(indexToRemove);
                FragmentTransaction t = getFragmentManager().beginTransaction();
                t.remove(fragmentToRemove);
                t.commit();
            }
        } else if (direction < 0) {
//            if (position > 0) {
//                position = pages.size();
//            }
            int indexToRemove = ((position + direction) % pages.size());
            if (indexToRemove < 0) {
                indexToRemove = pages.size() - 1;
            }
            Fragment fragmentToRemove = super.getItem(indexToRemove);
            FragmentTransaction t = getFragmentManager().beginTransaction();
            t.detach(fragmentToRemove);
            t.remove(fragmentToRemove);
            t.commit();
        }
        //-------------------------
        this.currentPosition = position;
        Fragment page = super.getItem(loopPosition);
        return page;
    }

    private void onDirectionChanged(int position, int lastRequestedPosition, int direction, int lastDirection) {
//        int indexToRemove = lastRequestedPosition % pages.size();
//        Fragment fragmentToRemove = super.getItem(indexToRemove);
//        FragmentTransaction t = getFragmentManager().beginTransaction();
//        t.remove(fragmentToRemove);
//        t.commit();
    }

    int lastDirection = 0;
    int currentPosition = 0;

    public int getLastDirection() {
        return lastDirection;
    }

    @Override
    public int getLastRequestedPosition() {
        return this.currentPosition;
    }
}
