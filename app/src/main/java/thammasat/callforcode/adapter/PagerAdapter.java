package thammasat.callforcode.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import thammasat.callforcode.fragment.NewsFragment;
import thammasat.callforcode.fragment.MapFragment;
import thammasat.callforcode.fragment.NearByFragment;
import thammasat.callforcode.fragment.StatsFragment;

/**
 * Created by Macintosh on 6/25/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NearByFragment nearBy = new NearByFragment();
                return nearBy;
            case 1:
                NewsFragment news = new NewsFragment();
                return news;
            case 2:
                MapFragment map = new MapFragment();
                return map;
            case 3:
                StatsFragment stats = new StatsFragment();
                return stats;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
