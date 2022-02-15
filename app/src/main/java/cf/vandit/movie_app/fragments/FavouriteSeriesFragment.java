package cf.vandit.movie_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.FavSeriesAdapter;
import cf.vandit.movie_app.database.series.FavSeries;
import cf.vandit.movie_app.database.series.SeriesDatabase;

public class FavouriteSeriesFragment extends Fragment {

    private RecyclerView mFavSeriesRecyclerView;
    private LinearLayout mEmptyLayout;

    public FavouriteSeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFavSeriesRecyclerView = view.findViewById(R.id.fav_tv_shows_recycler_view);
        mEmptyLayout = view.findViewById(R.id.fav_tv_shows_linear_layout);

        final LiveData<List<FavSeries>> mFavSeries = SeriesDatabase.getInstance(getContext())
                .seriesDao()
                .getAllFavSeries();

        mFavSeries.observe(requireActivity(), new Observer<List<FavSeries>>() {
            @Override
            public void onChanged(List<FavSeries> favSeries) {
                FavSeriesAdapter mFavSeriesAdapter = new FavSeriesAdapter(favSeries, getContext());
                mFavSeriesRecyclerView.setAdapter(mFavSeriesAdapter);
                mFavSeriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

                if(favSeries.isEmpty()){
                    mEmptyLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}