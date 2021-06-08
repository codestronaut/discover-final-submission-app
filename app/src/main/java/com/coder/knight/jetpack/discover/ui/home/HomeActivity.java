package com.coder.knight.jetpack.discover.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.ui.favorite.FavoriteFragment;
import com.coder.knight.jetpack.discover.ui.movie.MovieFragment;
import com.coder.knight.jetpack.discover.ui.tvshow.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpToolbar();
        loadFragment(new MovieFragment());
        BottomNavigationView homeBnv = findViewById(R.id.home_bnv);
        homeBnv.setOnNavigationItemSelectedListener(this);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.movie_menu:
                fragment = new MovieFragment();
                break;
            case R.id.tv_show_menu:
                fragment = new TvShowFragment();
                break;
            case R.id.favorite_menu:
                fragment = new FavoriteFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
