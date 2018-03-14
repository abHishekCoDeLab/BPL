package com.android.prince.bpl.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by prince on 11/3/18.
 */

public class SlideAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> mFragment = new ArrayList<>();
    ArrayList<String> mFragmentTitle = new ArrayList<>();

    public SlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    public void addFragment(Fragment fragment,String string){
        mFragment.add(fragment);
        mFragmentTitle.add(string);
    }

    public CharSequence getPageTitle(int position){
        return mFragmentTitle.get(position);
    }
}
