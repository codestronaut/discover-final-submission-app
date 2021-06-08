package com.coder.knight.jetpack.discover.ui.favorite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.ui.favorite.child.FavoriteMovieFragment;
import com.coder.knight.jetpack.discover.ui.favorite.child.FavoriteTvFragment;

public class FavoriteTabAdapter extends FragmentStatePagerAdapter {
    private Fragment[] subFavoriteFragment;
    private Context context;

    FavoriteTabAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.context = context;
        subFavoriteFragment = new Fragment[]{
                new FavoriteMovieFragment(),
                new FavoriteTvFragment()
        };
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return subFavoriteFragment[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.movies);
            case 1:
                return context.getString(R.string.tv_shows);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
