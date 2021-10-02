package com.greymatter.sesmo.Adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.greymatter.sesmo.Fragment.FragmentA;
import com.greymatter.sesmo.Fragment.FragmentB;
import com.greymatter.sesmo.Fragment.FragmentC;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new FragmentA();
        }
        else if (position == 1)
        {
            fragment = new FragmentB();
        }
        else if (position == 2)
        {
            fragment = new FragmentC();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Significant";
        }
        else if (position == 1)
        {
            title = "Near Me";
        }
        else if (position == 2)
        {
            title = "All";
        }
        return title;
    }

}

