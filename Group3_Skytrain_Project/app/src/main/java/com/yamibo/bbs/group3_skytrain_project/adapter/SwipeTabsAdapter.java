package com.yamibo.bbs.group3_skytrain_project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yamibo.bbs.group3_skytrain_project.activities.FragmentFavorites;
import com.yamibo.bbs.group3_skytrain_project.activities.FragmentTransit;
import com.yamibo.bbs.group3_skytrain_project.activities.FragmentTripPlan;
import com.yamibo.bbs.group3_skytrain_project.activities.FragmentTripPlan;

public class SwipeTabsAdapter extends FragmentPagerAdapter {
    private String[] titles={"Trip Planner","Schedule & Maps","Favorites" };

    public SwipeTabsAdapter(FragmentManager fragMg){
        super(fragMg);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentTripPlan();
            case 1:
                return new FragmentTransit();
          /*  case 2:
                return new FragmentNewsFeed();*/
            case 2:
                return new FragmentFavorites();

        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int pos){
        return titles[pos];
        //return each tab's title
    }
    @Override
    public int getCount() {
        return titles.length;
    }
}
