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
import cf.vandit.movie_app.adapters.FavMoviesAdapter;
import cf.vandit.movie_app.adapters.MovieBriefSmallAdapter;
import cf.vandit.movie_app.network.movie.MovieBrief;
import cf.vandit.movie_app.database.Favourite;

public class FavouriteMoviesFragment extends Fragment {

    private RecyclerView mFavMoviesRecyclerView;
    private List<MovieBrief> mFavMovies;
    private MovieBriefSmallAdapter mFavMoviesAdapter;

    private FavMoviesAdapter favMoviesAdapter;

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

        mFavMoviesRecyclerView = (RecyclerView) view.findViewById(R.id.fav_movies_recycler_view);
        mFavMovies = new ArrayList<>();
        mFavMoviesAdapter = new MovieBriefSmallAdapter(mFavMovies, getContext());
        mFavMoviesRecyclerView.setAdapter(mFavMoviesAdapter);
        mFavMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        mEmptyLayout = (LinearLayout) view.findViewById(R.id.fav_movies_linear_layout);

        loadFavMovies();
//        GetFavMovies getFavMovies = new GetFavMovies();
//        getFavMovies.execute();
    }

    private void loadFavMovies(){
        List<MovieBrief> favMovieBriefs = Favourite.getFavMovieBriefs(getContext());
        if (favMovieBriefs.isEmpty()) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            return;
        }

        for (MovieBrief movieBrief : favMovieBriefs) {
            mFavMovies.add(movieBrief);
        }
        mFavMoviesAdapter.notifyDataSetChanged();
    }

//    class GetFavMovies extends AsyncTask<Void, Void, List<FavMovie>> {
//        @Override
//        protected List<FavMovie> doInBackground(Void... voids) {
//            List<FavMovie> mFavMovie = DatabaseClient.getInstance(getContext())
//                    .getAppDatabase()
//                    .favouriteDao()
//                    .getAll();
//
//            return mFavMovie;
//        }
//
//        @Override
//        protected void onPostExecute(List<FavMovie> favMovies) {
//            super.onPostExecute(favMovies);
//
//            favMoviesAdapter = new FavMoviesAdapter(favMovies, getContext());
//            mFavMoviesRecyclerView.setAdapter(favMoviesAdapter);
//            mFavMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
//
//            if (favMovies.isEmpty()){
//                mEmptyLayout.setVisibility(View.VISIBLE);
//            }
//        }
//    }
}