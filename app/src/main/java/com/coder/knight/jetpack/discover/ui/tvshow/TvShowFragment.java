package com.coder.knight.jetpack.discover.ui.tvshow;


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
import com.coder.knight.jetpack.discover.data.source.local.entity.TvShowEntity;
import com.coder.knight.jetpack.discover.viewmodel.ViewModelFactory;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private RecyclerView rvTvShowList;
    private ProgressBar progressBar;
    private TvShowAdapter tvShowAdapter;
    private TvShowViewModel tvShowViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }

    private static TvShowViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(TvShowViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTvShowList = view.findViewById(R.id.rv_tv_show_list);
        progressBar = view.findViewById(R.id.progress_bar);

        setUpLoading();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            tvShowViewModel = obtainViewModel(getActivity());

            setUpRecyclerView();
            loadTvShow();
        }
    }

    private void loadTvShow() {
        tvShowViewModel.getTvShow().observe(getViewLifecycleOwner(), tvShowResponse -> {
            progressBar.setVisibility(View.GONE);
            ArrayList<TvShowEntity> tvShowEntities = tvShowResponse.getTvShowList();
            tvShowAdapter.setTvShowList(tvShowEntities);
            tvShowAdapter.notifyDataSetChanged();
        });
    }

    private void setUpLoading() {
        Sprite fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);
    }

    private void setUpRecyclerView() {
        if (tvShowAdapter == null) {
            tvShowAdapter = new TvShowAdapter(getActivity());
            rvTvShowList.setHasFixedSize(true);
            rvTvShowList.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvTvShowList.setAdapter(tvShowAdapter);
        } else {
            tvShowAdapter.notifyDataSetChanged();
        }
    }
}
