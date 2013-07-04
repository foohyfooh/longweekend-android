package com.foohyfooh.longweekend;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Fragment[] fragments = {new LongWeekend(), new HolidaysFrom(), new HolidaysBetween(), new About()};
        String[] fragmentTitles = {"Long Weekend", "Holidays From", "Holidays Between", "About"};
        AppSectionsPagerAdapter appSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(), fragments);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(appSectionsPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for(int i = 0; i  < fragments.length; i++){
            actionBar.addTab(actionBar.newTab().setText(fragmentTitles[i]).setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    private class AppSectionsPagerAdapter extends FragmentPagerAdapter {


        private final Fragment[] fragments;

        public AppSectionsPagerAdapter(FragmentManager fragmentManager, Fragment[] fragments){
            super(fragmentManager);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return i < fragments.length ? fragments[i] : null;
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
