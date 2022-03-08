package cf.vandit.movie_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Timer;
import java.util.TimerTask;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.ViewAllMoviesActivity;
import cf.vandit.movie_app.adapters.MovieBriefSmallAdapter;
import cf.vandit.movie_app.adapters.MovieCarouselAdapter;
import cf.vandit.movie_app.adapters.MoviesNestedRecViewAdapter;
import cf.vandit.movie_app.network.movie.GenreMoviesResponse;
import cf.vandit.movie_app.network.movie.MovieBrief;
import cf.vandit.movie_app.network.movie.NowShowingMoviesResponse;
import cf.vandit.movie_app.network.movie.PopularMoviesResponse;
import cf.vandit.movie_app.network.movie.TopRatedMoviesResponse;
import cf.vandit.movie_app.request.ApiClient;
import cf.vandit.movie_app.request.ApiInterface;
import cf.vandit.movie_app.utils.Constants;
import cf.vandit.movie_app.utils.NestedRecViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private ProgressBar mProgressBar;

    private TextView mViewPopular;
    private TextView mViewTopRated;

    private Timer timer;
    private TimerTask timerTask;
    private int position;
    private LinearLayoutManager carouselLayoutManager;

    private RecyclerView mNowShowingRecyclerView;
    private List<MovieBrief> mNowShowingMovies;
    private MovieCarouselAdapter mNowShowingAdapter;

    private RecyclerView mPopularMoviesRecyclerView;
    private List<MovieBrief> mPopularMovies;
    private MovieBriefSmallAdapter mPopularMoviesAdapter;

    private RecyclerView mTopRatedRecyclerView;
    private List<MovieBrief> mTopRatedMovies;
    private MovieBriefSmallAdapter mTopRatedAdapter;

    private List<MovieBrief> mActionMovies;
    private List<MovieBrief> mAdventureMovies;
    private List<MovieBrief> mAnimatedMovies;
    private List<MovieBrief> mComedyMovies;
    private List<MovieBrief> mCrimeMovies;
    private List<MovieBrief> mDocumentaryMovies;
    private List<MovieBrief> mDramaMovies;
    private List<MovieBrief> mFamilyMovies;
    private List<MovieBrief> mFantasyMovies;
    private List<MovieBrief> mHistoryMovies;
    private List<MovieBrief> mHorrorMovies;
    private List<MovieBrief> mMusicMovies;
    private List<MovieBrief> mMysteryMovies;
    private List<MovieBrief> mRomanceMovies;
    private List<MovieBrief> mSciFiMovies;
    private List<MovieBrief> mTvMovies;
    private List<MovieBrief> mThrillerMovies;
    private List<MovieBrief> mWarMovies;
    private List<MovieBrief> mWesternMovies;


    private RecyclerView mNestedRecView;
    private List<NestedRecViewModel> mNestedList;
    private MoviesNestedRecViewAdapter mMoviesNestedRecViewAdapter;

    private ConstraintLayout mPopularHeading;
    private ConstraintLayout mTopRatedHeading;

    private boolean mNowShowingMoviesLoaded;
    private boolean mPopularMoviesLoaded;
    private boolean mTopRatedMoviesLoaded;

    Call<NowShowingMoviesResponse> mNowShowingMoviesCall;
    Call<PopularMoviesResponse> mPopularMoviesCall;
    Call<TopRatedMoviesResponse> mTopRatedMoviesCall;
    Call<GenreMoviesResponse> mGenreMoviesResponseCall;

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

        mPopularMoviesRecyclerView = view.findViewById(R.id.popular_recView);
        mTopRatedRecyclerView = view.findViewById(R.id.top_rated_recView);

        mPopularHeading = view.findViewById(R.id.popular_heading);
        mTopRatedHeading = view.findViewById(R.id.top_rated_heading);

        mNestedRecView = view.findViewById(R.id.movie_nested_recView);

        mNowShowingMovies = new ArrayList<>();
        mPopularMovies = new ArrayList<>();
        mTopRatedMovies = new ArrayList<>();

        mActionMovies = new ArrayList<>();
        mAnimatedMovies = new ArrayList<>();
        mAdventureMovies = new ArrayList<>();
        mComedyMovies = new ArrayList<>();
        mCrimeMovies = new ArrayList<>();
        mDocumentaryMovies = new ArrayList<>();
        mDramaMovies = new ArrayList<>();
        mFamilyMovies = new ArrayList<>();
        mFantasyMovies = new ArrayList<>();
        mHistoryMovies = new ArrayList<>();
        mHorrorMovies = new ArrayList<>();
        mMusicMovies = new ArrayList<>();
        mMysteryMovies = new ArrayList<>();
        mRomanceMovies = new ArrayList<>();
        mSciFiMovies = new ArrayList<>();
        mTvMovies = new ArrayList<>();
        mThrillerMovies = new ArrayList<>();
        mWarMovies = new ArrayList<>();
        mWesternMovies = new ArrayList<>();

        mNestedList = new ArrayList<>();

        mNowShowingMoviesLoaded = false;
        mPopularMoviesLoaded = false;
        mTopRatedMoviesLoaded = false;

        mNowShowingAdapter = new MovieCarouselAdapter(mNowShowingMovies, getContext());
        carouselLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mNowShowingRecyclerView.setLayoutManager(carouselLayoutManager);
        mNowShowingRecyclerView.setAdapter(mNowShowingAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mNowShowingRecyclerView);
        mNowShowingRecyclerView.smoothScrollBy(5,0);

        mPopularMoviesAdapter = new MovieBriefSmallAdapter(mPopularMovies, getContext());
        mPopularMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mPopularMoviesRecyclerView.setAdapter(mPopularMoviesAdapter);

        mTopRatedAdapter = new MovieBriefSmallAdapter(mTopRatedMovies, getContext());
        mTopRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mTopRatedRecyclerView.setAdapter(mTopRatedAdapter);

        mMoviesNestedRecViewAdapter = new MoviesNestedRecViewAdapter(mNestedList, getContext());
        mNestedRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNestedRecView.setAdapter(mMoviesNestedRecViewAdapter);

        mNowShowingRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 1) {
                    stopAutoScrollCarousel();
                } else if (newState == 0) {
                    position = carouselLayoutManager.findFirstCompletelyVisibleItemPosition();
                    runAutoScrollingCarousel();
                }
            }
        });

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

        initViews();
    }

    private void stopAutoScrollCarousel(){
        if (timer != null && timerTask != null) {
            timerTask.cancel();
            timer.cancel();
            timer = null;
            timerTask = null;
            position = carouselLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }

    private void runAutoScrollingCarousel(){
        if (timer == null && timerTask == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (position == mNowShowingMovies.size() - 1) {
                        mNowShowingRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                position = 0;
                                mNowShowingRecyclerView.smoothScrollToPosition(position);
                                mNowShowingRecyclerView.smoothScrollBy(5, 0);
                            }
                        });
                    } else {
                        position++;
                        mNowShowingRecyclerView.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 4000, 4000);
        }
    }

    private void initViews(){
        loadNowShowingMovies();
        loadPopularMovies();
        loadTopRatedMovies();
        loadActionMovies();
        loadAdventureMovies();
        loadAnimatedMovies();
        loadComedyMovies();
        loadCrimeMovies();
        loadDocumentaryMovies();
        loadDramaMovies();
        loadFamilyMovies();
        loadFantasyMovies();
        loadHistoryMovies();
        loadHorrorMovies();
        loadMusicMovies();
        loadMysteryMovies();
        loadRomanceMovies();
        loadSciFiMovies();
        loadTvMovies();
        loadThriller();
        loadWarMovies();
        loadWesternMovies();
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

    private void loadActionMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.ACTION_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mActionMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mActionMovies, Constants.ACTION_MOVIES_TYPE));
                mNestedRecView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadAdventureMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.ADVENTURE_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mAdventureMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mAdventureMovies, Constants.ADVENTURE_MOVIES_TYPE));
                mNestedRecView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadAnimatedMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.ANIMATION_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mAnimatedMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mAnimatedMovies, Constants.ANIMATION_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadComedyMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.COMEDY_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mComedyMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mComedyMovies, Constants.COMEDY_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadCrimeMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.CRIME_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mCrimeMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mCrimeMovies, Constants.CRIME_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadDocumentaryMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.DOCUMENTARY_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mDocumentaryMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mDocumentaryMovies, Constants.DOCUMENTARY_MOVIES_TYPE));
                mMoviesNestedRecViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadDramaMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.DRAMA_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mDramaMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mDramaMovies, Constants.DRAMA_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadFamilyMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.FAMILY_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mFamilyMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mFamilyMovies, Constants.FAMILY_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadFantasyMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.FANTASY_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mFantasyMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mFantasyMovies, Constants.FANTASY_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadHistoryMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.HISTORY_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mHistoryMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mHistoryMovies, Constants.HISTORY_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadHorrorMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.HORROR_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mHorrorMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mHorrorMovies, Constants.HORROR_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadMusicMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.MUSIC_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mMusicMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mMusicMovies, Constants.MUSIC_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadMysteryMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.MYSTERY_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mMysteryMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mMysteryMovies, Constants.MYSTERY_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadRomanceMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.ROMANCE_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mRomanceMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mRomanceMovies, Constants.ROMANCE_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadSciFiMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.SCIFI_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mSciFiMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mSciFiMovies, Constants.SCIFI_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadTvMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.TV_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mTvMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mTvMovies, Constants.TV_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadThriller(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.THRILLER_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mThrillerMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mThrillerMovies, Constants.THRILLER_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadWarMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.WAR_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mWarMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mWarMovies, Constants.WAR_MOVIES_TYPE));
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
        });
    }

    private void loadWesternMovies(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mGenreMoviesResponseCall = apiInterface.getMoviesByGenre(Constants.API_KEY, Constants.WESTERN_MOVIES_TYPE, 1);
        mGenreMoviesResponseCall.enqueue(new Callback<GenreMoviesResponse>() {
            @Override
            public void onResponse(Call<GenreMoviesResponse> call, Response<GenreMoviesResponse> response) {
                if (!response.isSuccessful()){
                    mGenreMoviesResponseCall = call.clone();
                    mGenreMoviesResponseCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()){
                    if(movieBrief != null && movieBrief.getPosterPath() != null){
                        mWesternMovies.add(movieBrief);
                    }
                }
                mNestedList.add(new NestedRecViewModel(mWesternMovies, Constants.WESTERN_MOVIES_TYPE));
                mMoviesNestedRecViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GenreMoviesResponse> call, Throwable t) {}
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

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScrollCarousel();
    }

    @Override
    public void onResume() {
        super.onResume();
        runAutoScrollingCarousel();
    }
}