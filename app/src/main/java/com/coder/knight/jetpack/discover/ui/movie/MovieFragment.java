package com.coder.knight.jetpack.discover.ui.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.coder.knight.jetpack.discover.R;
import com.coder.knight.jetpack.discover.data.source.local.entity.MovieEntity;
import com.coder.knight.jetpack.discover.viewmodel.ViewModelFactory;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rvMovieList;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;

    public MovieFragment() {
        // Required empty public constructor
    }

    private static MovieViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MovieViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovieList = view.findViewById(R.id.rv_movie_list);
        progressBar = view.findViewById(R.id.progress_bar);

        setUpLoading();
        setUpRecyclerView();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            movieViewModel = obtainViewModel(getActivity());
            loadMovie();
        }
    }

    private void loadMovie() {
        movieViewModel.getMovies().observe(getViewLifecycleOwner(), movieResponse -> {
            progressBar.setVisibility(View.GONE);
            ArrayList<MovieEntity> movieEntities = movieResponse.getMovieList();
            movieAdapter.setMovieList(movieEntities);
            movieAdapter.notifyDataSetChanged();
        });
    }

    private void setUpLoading() {
        Sprite fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);
    }

    private void setUpRecyclerView() {
        if (movieAdapter == null) {
            movieAdapter = new MovieAdapter(getActivity());
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvMovieList.setLayoutManager(mLayoutManager);
            rvMovieList.setAdapter(movieAdapter);
        } else {
            movieAdapter.notifyDataSetChanged();
        }
    }
}

