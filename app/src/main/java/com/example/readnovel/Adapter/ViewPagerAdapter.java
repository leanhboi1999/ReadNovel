package com.example.readnovel.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.readnovel.Fragment.TopDayFragment;
import com.example.readnovel.Fragment.TopMonthFragment;
import com.example.readnovel.Fragment.TopWeekFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = new TopDayFragment();
                break;
            case 1:
                frag = new TopWeekFragment();
                break;
            case 2:
                frag = new TopMonthFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Ngày";
                break;
            case 1:
                title = "Tuần";
                break;
            case 2:
                title = "Tháng";
                break;
        }
        return title;
    }

}
