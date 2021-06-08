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
import com.coder.knight.jetpack.discover.data.source.local.room.TvShow;
import com.coder.knight.jetpack.discover.ui.favorite.FavoriteViewModel;
import com.coder.knight.jetpack.discover.viewmodel.ViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment {
    private RecyclerView rvFavTv;
    private FavTvAdapter adapter;

    public FavoriteTvFragment() {
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
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavTv = view.findViewById(R.id.rv_fav_tv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            setUpRecyclerView();
            FavoriteViewModel favoriteViewModel = obtainViewModel(getActivity());
            favoriteViewModel.getAllFavoriteTvShow().observe(getActivity(), tvShowObserver);
        }
    }

    private final Observer<PagedList<TvShow>> tvShowObserver = new Observer<PagedList<TvShow>>() {
        @Override
        public void onChanged(PagedList<TvShow> tvShows) {
            if (tvShows != null) {
                adapter.setTvShow(tvShows);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private void setUpRecyclerView() {
        if (adapter == null) {
            adapter = new FavTvAdapter(getActivity());
            rvFavTv.setHasFixedSize(true);
            rvFavTv.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvFavTv.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
