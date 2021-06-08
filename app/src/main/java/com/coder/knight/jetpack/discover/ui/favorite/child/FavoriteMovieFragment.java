package com.coder.knight.jetpack.discover.ui.favorite.child;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.data.source.local.room.Movie;
import com.coder.knight.jetpack.discover.ui.favorite.FavoriteViewModel;
import com.coder.knight.jetpack.discover.viewmodel.ViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {
    private RecyclerView rvMovieFavorite;
    private FavMovieAdapter adapter;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    private static FavoriteViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(FavoriteViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovieFavorite = view.findViewById(R.id.rv_fav_movie);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            setUpRecyclerView();
            FavoriteViewModel favoriteViewModel = obtainViewModel(getActivity());
            favoriteViewModel.getAllFavoriteMovie().observe(getActivity(), movieObserver);
        }
    }

    private final Observer<PagedList<Movie>> movieObserver = new Observer<PagedList<Movie>>() {
        @Override
        public void onChanged(PagedList<Movie> movies) {
            if (movies != null) {
                adapter.setMovies(movies);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private void setUpRecyclerView() {
        if (adapter == null) {
            adapter = new FavMovieAdapter(getActivity());
            rvMovieFavorite.setHasFixedSize(true);
            rvMovieFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvMovieFavorite.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
