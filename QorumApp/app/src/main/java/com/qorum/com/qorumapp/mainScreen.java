package com.qorum.com.qorumapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class mainScreen extends FragmentActivity {
    private List<Fragment> fragmentlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(3);
        fragmentlist = new ArrayList<Fragment>();

        fragmentlist.add(SearchFragment.newInstance("Hello from fragment Search"));
        fragmentlist.add(HomeFragment.newInstance("Hello from fragment Main Page"));
        fragmentlist.add(FollowsFragment.newInstance("Hello from fragment My follows"));
        pager.setCurrentItem(1);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
           return fragmentlist.get(pos);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
