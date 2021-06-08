package com.coder.knight.jetpack.discover.ui.favorite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coder.knight.jetpack.discover.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private static final String PAGER_STATE = "pager_current_item";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        prepare();

        if (savedInstanceState != null) {
            mViewPager.setCurrentItem(savedInstanceState.getInt(PAGER_STATE, 0));
        }
    }

    private void init(View view) {
        mViewPager = view.findViewById(R.id.favorite_view_pager);
        mTabLayout = view.findViewById(R.id.favorite_tab);
    }

    private void prepare() {
        mViewPager.setAdapter(new FavoriteTabAdapter(getActivity(), getChildFragmentManager(), 1));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PAGER_STATE, mViewPager.getCurrentItem());
    }
}
