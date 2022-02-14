package cf.vandit.movie_app.fragments;

import android.os.AsyncTask;
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
import cf.vandit.movie_app.adapters.FavMoviesAdapter;
import cf.vandit.movie_app.database.movies.FavMovie;
import cf.vandit.movie_app.database.movies.MovieDatabase;

public class FavouriteMoviesFragment extends Fragment {

    private RecyclerView mFavMoviesRecyclerView;
    private List<FavMovie> mFavMovies;

    private LinearLayout mEmptyLayout;

    public FavouriteMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFavMoviesRecyclerView = view.findViewById(R.id.fav_movies_recycler_view);
        mFavMovies = new ArrayList<>();

        mEmptyLayout = view.findViewById(R.id.fav_movies_linear_layout);

        GetFavMovies getFavMovies = new GetFavMovies();
        getFavMovies.execute();
    }

    class GetFavMovies extends AsyncTask<Void, Void, List<FavMovie>> {
        @Override
        protected List<FavMovie> doInBackground(Void... voids) {
            mFavMovies.clear();

            mFavMovies = MovieDatabase.getInstance(getContext())
                    .movieDao()
                    .getAllFavMovies();

            return mFavMovies;
        }

        @Override
        protected void onPostExecute(List<FavMovie> favMovies) {
            super.onPostExecute(favMovies);

            FavMoviesAdapter mFavMoviesAdapter = new FavMoviesAdapter(favMovies, getContext());
            mFavMoviesRecyclerView.setAdapter(mFavMoviesAdapter);
            mFavMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

            if (mFavMovies.isEmpty()){
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        GetFavMovies getFavMovies = new GetFavMovies();
//        getFavMovies.execute();
    }
}