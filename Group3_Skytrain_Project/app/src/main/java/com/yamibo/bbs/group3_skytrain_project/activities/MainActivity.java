package com.yamibo.bbs.group3_skytrain_project.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yamibo.bbs.group3_skytrain_project.R;

public class MainActivity extends AppBaseActivity{
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private android.app.Fragment tripFrag;
    private android.app.Fragment transitFrag;
    private android.app.Fragment feedFrag;
    private CollapsingToolbarLayout collapsyToolbar;
    private FragmentTransaction ft;
    private FragmentManager fragMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setTabsFragment(new Fragment_Tabs());
        initTabFragments();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NearbyActivity.class);
                startActivity(intent);
            }
        });
        bottomNav();
    }
    public void setToolbar(){
        toolbar=(Toolbar)findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("TransLink");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setSubtitle("Data by TransLink");
    }

    private void setTabsFragment(Fragment fragment){
        //Always use v4.app.FragmentTransaction
        if(fragment!=null){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rootViewPager,new Fragment_Tabs()).commit();
        }
    }
    private void initTabFragments(){
        tripFrag=getFragmentManager().findFragmentById(R.layout.tab_trip_planner);
        transitFrag=getFragmentManager().findFragmentById(R.layout.tab_transit);
        feedFrag=getFragmentManager().findFragmentById(R.layout.fragment_news_feed);
    }
    private void bottomNav(){
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_news:
                        toolbar.setTitle("What' New");
                        fragmentNewsFeed(new FragmentNewsFeed());
                        break;
                    case R.id.action_media:
                        toolbar.setTitle("TransLink Media Feed");
                        fragmentMediaFeed(new FragmentMediaFeed());
                    break;
                    case R.id.action_event:
                        toolbar.setTitle("TransLink Event Calendar");
                        fragmentEventFeed(new FragmentEventFeed());
                    break;
                }
                return true;
            }
        });
    }
    private void fragmentNewsFeed(Fragment fg){
        if(fg!=null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rootViewPager, new FragmentNewsFeed()).commit();
        }
    }

    private void fragmentMediaFeed(Fragment fg){
        if(fg!=null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rootViewPager, new FragmentMediaFeed()).commit();
        }

    }

    private void fragmentEventFeed(Fragment fg){
        if(fg!=null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.rootViewPager, new FragmentEventFeed()).commit();
        }

    }
}
