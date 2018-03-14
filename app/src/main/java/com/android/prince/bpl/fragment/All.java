package com.android.prince.bpl.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.prince.bpl.R;
import com.android.prince.bpl.adapter.SlideAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class All extends Fragment {


    public All() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SlideAdapter adapter = new SlideAdapter(getChildFragmentManager());
        adapter.addFragment(new Upcoming(),"Upcoming");
        adapter.addFragment(new Recent(),"Recent");

        Activity context = getActivity();
        ViewPager pager =  (ViewPager)context.findViewById(R.id.AllFragmentPager);
        TabLayout tab = (TabLayout)context.findViewById(R.id.MatchDetailTab);

        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
    }
}
