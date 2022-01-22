package cf.vandit.movie_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.MovieBriefSmallAdapter;
import cf.vandit.movie_app.network.movie.MovieBrief;
import cf.vandit.movie_app.network.movie.PopularMoviesResponse;
import cf.vandit.movie_app.network.movie.TopRatedMoviesResponse;
import cf.vandit.movie_app.request.ApiClient;
import cf.vandit.movie_app.request.ApiInterface;
import cf.vandit.movie_app.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllMoviesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<MovieBrief> mMovies;
    private MovieBriefSmallAdapter mMoviesAdapter;

    private int mMovieType;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    private Call<PopularMoviesResponse> mPopularMoviesCall;
    private Call<TopRatedMoviesResponse> mTopRatedMoviesCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_movies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_movies_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receivedIntent = getIntent();
        mMovieType = receivedIntent.getIntExtra(Constants.VIEW_ALL_MOVIES_TYPE, -1);

        if (mMovieType == -1) finish();

        switch (mMovieType) {
            case Constants.POPULAR_MOVIES_TYPE:
                setTitle("Popular Movies");
                break;
            case Constants.TOP_RATED_MOVIES_TYPE:
                setTitle("Top Rated Movies");
                break;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.view_movies_recView);
        mMovies = new ArrayList<>();
        mMoviesAdapter = new MovieBriefSmallAdapter(mMovies, ViewAllMoviesActivity.this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewAllMoviesActivity.this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadMovies(mMovieType);
                    loading = true;
                }
            }
        });

        loadMovies(mMovieType);
    }

    private void loadMovies(int movieType) {
        if (pagesOver) return;

        ApiInterface apiService = ApiClient.getMovieApi();

        switch (movieType) {
            case Constants.POPULAR_MOVIES_TYPE:
                mPopularMoviesCall = apiService.getPopularMovies(Constants.API_KEY, presentPage);
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
                            if (movieBrief != null && movieBrief.getTitle() != null && movieBrief.getPosterPath() != null)
                                mMovies.add(movieBrief);
                        }
                        mMoviesAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {

                    }
                });
                break;
            case Constants.TOP_RATED_MOVIES_TYPE:
                mTopRatedMoviesCall = apiService.getTopRatedMovies(Constants.API_KEY, presentPage, "US");
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
                            if (movieBrief != null && movieBrief.getTitle() != null && movieBrief.getPosterPath() != null)
                                mMovies.add(movieBrief);
                        }
                        mMoviesAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<TopRatedMoviesResponse> call, Throwable t) {

                    }
                });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}