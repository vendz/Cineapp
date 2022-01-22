package cf.vandit.movie_app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.SeriesBriefSmallAdapter;
import cf.vandit.movie_app.network.series.SeriesBrief;
import cf.vandit.movie_app.database.Favourite;

public class FavouriteSeriesFragment extends Fragment {

    private RecyclerView mFavTVShowsRecyclerView;
    private List<SeriesBrief> mFavTVShows;
    private SeriesBriefSmallAdapter mFavTVShowsAdapter;

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

        mFavTVShowsRecyclerView = (RecyclerView) view.findViewById(R.id.fav_tv_shows_recycler_view);
        mFavTVShows = new ArrayList<>();
        mFavTVShowsAdapter = new SeriesBriefSmallAdapter(mFavTVShows, getContext());
        mFavTVShowsRecyclerView.setAdapter(mFavTVShowsAdapter);
        mFavTVShowsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        mEmptyLayout = (LinearLayout) view.findViewById(R.id.fav_tv_shows_linear_layout);

        loadTvShows();
    }

    private void loadTvShows(){
        List<SeriesBrief> favSeriesBriefs = Favourite.getFavTVShowBriefs(getContext());
        if (favSeriesBriefs.isEmpty()) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            return;
        }

        for (SeriesBrief seriesBrief : favSeriesBriefs) {
            mFavTVShows.add(seriesBrief);
        }
        mFavTVShowsAdapter.notifyDataSetChanged();
    }
}