package cf.vandit.movie_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.ViewAllMoviesActivity;
import cf.vandit.movie_app.adapters.MovieBriefSmallAdapter;
import cf.vandit.movie_app.adapters.MovieCarouselAdapter;
import cf.vandit.movie_app.network.movie.MovieBrief;
import cf.vandit.movie_app.network.movie.NowShowingMoviesResponse;
import cf.vandit.movie_app.network.movie.PopularMoviesResponse;
import cf.vandit.movie_app.network.movie.TopRatedMoviesResponse;
import cf.vandit.movie_app.request.ApiClient;
import cf.vandit.movie_app.request.ApiInterface;
import cf.vandit.movie_app.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private ProgressBar mProgressBar;

    private TextView mViewPopular;
    private TextView mViewTopRated;

    private RecyclerView mNowShowingRecyclerView;
    private List<MovieBrief> mNowShowingMovies;
    private MovieCarouselAdapter mNowShowingAdapter;

    private RecyclerView mPopularMoviesRecyclerView;
    private List<MovieBrief> mPopularMovies;
    private MovieBriefSmallAdapter mPopularMoviesAdapter;

    private RecyclerView mTopRatedRecyclerView;
    private List<MovieBrief> mTopRatedMovies;
    private MovieBriefSmallAdapter mTopRatedAdapter;

    private ConstraintLayout mPopularHeading;
    private ConstraintLayout mTopRatedHeading;

    private boolean mNowShowingMoviesLoaded;
    private boolean mPopularMoviesLoaded;
    private boolean mTopRatedMoviesLoaded;

    Call<NowShowingMoviesResponse> mNowShowingMoviesCall;
    Call<PopularMoviesResponse> mPopularMoviesCall;
    Call<TopRatedMoviesResponse> mTopRatedMoviesCall;

    public MovieFragment() {
        // Required empty public constructor
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

        mProgressBar = view.findViewById(R.id.movie_progressBar);

        mViewPopular = view.findViewById(R.id.view_popular);
        mViewTopRated = view.findViewById(R.id.view_top_rated);

        mNowShowingRecyclerView = view.findViewById(R.id.carousel_recView);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mNowShowingRecyclerView);

        mPopularMoviesRecyclerView = view.findViewById(R.id.popular_recView);
        mTopRatedRecyclerView = view.findViewById(R.id.top_rated_recView);

        mPopularHeading = view.findViewById(R.id.popular_heading);
        mTopRatedHeading = view.findViewById(R.id.top_rated_heading);

        mNowShowingMovies = new ArrayList<>();
        mPopularMovies = new ArrayList<>();
        mTopRatedMovies = new ArrayList<>();

        mNowShowingMoviesLoaded = false;
        mPopularMoviesLoaded = false;
        mTopRatedMoviesLoaded = false;

        mNowShowingAdapter = new MovieCarouselAdapter(mNowShowingMovies, getContext());
        mNowShowingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mPopularMoviesAdapter = new MovieBriefSmallAdapter(mPopularMovies, getContext());
        mPopularMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mTopRatedAdapter = new MovieBriefSmallAdapter(mTopRatedMovies, getContext());
        mTopRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mNowShowingRecyclerView.setAdapter(mNowShowingAdapter);
        mPopularMoviesRecyclerView.setAdapter(mPopularMoviesAdapter);
        mTopRatedRecyclerView.setAdapter(mTopRatedAdapter);

        mViewPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllMoviesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.POPULAR_MOVIES_TYPE);
                startActivity(intent);
            }
        });

        mViewTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllMoviesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.TOP_RATED_MOVIES_TYPE);
                startActivity(intent);
            }
        });

        loadNowShowingMovies();
        loadPopularMovies();
        loadTopRatedMovies();
    }

    private void loadNowShowingMovies() {
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mNowShowingMoviesCall = apiInterface.getNowShowingMovies(Constants.API_KEY, 1, "US");
        mNowShowingMoviesCall.enqueue(new Callback<NowShowingMoviesResponse>() {
            @Override
            public void onResponse(Call<NowShowingMoviesResponse> call, Response<NowShowingMoviesResponse> response) {

                if (!response.isSuccessful()) {
                    mNowShowingMoviesCall = call.clone();
                    mNowShowingMoviesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()) {
                    if (movieBrief != null && movieBrief.getBackdropPath() != null)
                        mNowShowingMovies.add(movieBrief);
                }
                mNowShowingAdapter.notifyDataSetChanged();
                mNowShowingMoviesLoaded = true;
                checkAllDataLoaded();
            }

            @Override
            public void onFailure(Call<NowShowingMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadPopularMovies() {
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mPopularMoviesCall = apiInterface.getPopularMovies(Constants.API_KEY, 1);
        mPopularMoviesCall.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                if (!response.isSuccessful()) {
                    mPopularMoviesCall = call.clone();
                    mPopularMoviesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null)
                        mPopularMovies.add(movieBrief);
                }
                mPopularMoviesAdapter.notifyDataSetChanged();
                mPopularMoviesLoaded = true;
                checkAllDataLoaded();
            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadTopRatedMovies() {
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mTopRatedMoviesCall = apiInterface.getTopRatedMovies(Constants.API_KEY, 1, "US");
        mTopRatedMoviesCall.enqueue(new Callback<TopRatedMoviesResponse>() {
            @Override
            public void onResponse(Call<TopRatedMoviesResponse> call, Response<TopRatedMoviesResponse> response) {
                if (!response.isSuccessful()) {
                    mTopRatedMoviesCall = call.clone();
                    mTopRatedMoviesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()) {
                    if (movieBrief != null && movieBrief.getPosterPath() != null)
                        mTopRatedMovies.add(movieBrief);
                }
                mTopRatedAdapter.notifyDataSetChanged();
                mTopRatedMoviesLoaded = true;
                checkAllDataLoaded();
            }

            @Override
            public void onFailure(Call<TopRatedMoviesResponse> call, Throwable t) {}
        });
    }

    private void checkAllDataLoaded(){
        if(mNowShowingMoviesLoaded && mPopularMoviesLoaded && mTopRatedMoviesLoaded) {
            mProgressBar.setVisibility(View.GONE);
            mNowShowingRecyclerView.setVisibility(View.VISIBLE);
            mPopularHeading.setVisibility(View.VISIBLE);
            mPopularMoviesRecyclerView.setVisibility(View.VISIBLE);
            mTopRatedHeading.setVisibility(View.VISIBLE);
            mTopRatedRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}