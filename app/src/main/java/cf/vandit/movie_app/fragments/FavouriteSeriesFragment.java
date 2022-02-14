package cf.vandit.movie_app.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.FavMoviesAdapter;
import cf.vandit.movie_app.adapters.FavSeriesAdapter;
import cf.vandit.movie_app.database.series.FavSeries;
import cf.vandit.movie_app.database.series.SeriesDatabase;

public class FavouriteSeriesFragment extends Fragment {

    private RecyclerView mFavSeriesRecyclerView;
    private List<FavSeries> mFavSeries;

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
        mFavSeries = new ArrayList<>();

        mEmptyLayout = view.findViewById(R.id.fav_tv_shows_linear_layout);

        GetFavSeries getFavSeries = new GetFavSeries();
        getFavSeries.execute();
    }

    class GetFavSeries extends AsyncTask<Void, Void, List<FavSeries>> {
        @Override
        protected List<FavSeries> doInBackground(Void... voids) {
            mFavSeries.clear();

            mFavSeries = SeriesDatabase.getInstance(getContext())
                    .seriesDao()
                    .getAllFavSeries();

            return mFavSeries;
        }

        @Override
        protected void onPostExecute(List<FavSeries> favSeries) {
            super.onPostExecute(favSeries);

            FavSeriesAdapter mFavSeriesAdapter = new FavSeriesAdapter(favSeries, getContext());
            mFavSeriesRecyclerView.setAdapter(mFavSeriesAdapter);
            mFavSeriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

            if (mFavSeries.isEmpty()){
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        GetFavSeries getFavSeries = new GetFavSeries();
//        getFavSeries.execute();
    }
}