package cf.vandit.movie_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.SeriesBriefSmallAdapter;
import cf.vandit.movie_app.network.series.OnTheAirSeriesResponse;
import cf.vandit.movie_app.network.series.PopularSeriesResponse;
import cf.vandit.movie_app.network.series.SeriesBrief;
import cf.vandit.movie_app.network.series.TopRatedSeriesResponse;
import cf.vandit.movie_app.request.ApiClient;
import cf.vandit.movie_app.request.ApiInterface;
import cf.vandit.movie_app.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllSeriesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<SeriesBrief> mSeries;
    private SeriesBriefSmallAdapter mSeriesAdapter;

    private int mSeriesType;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    private Call<OnTheAirSeriesResponse> mOnAirSeriesCall;
    private Call<PopularSeriesResponse> mPopularSeriesCall;
    private Call<TopRatedSeriesResponse> mTopRatedSeriesCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_series);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_series_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receivedIntent = getIntent();
        mSeriesType = receivedIntent.getIntExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, -1);

        if (mSeriesType == -1) finish();

        switch (mSeriesType) {
            case Constants.ON_THE_AIR_TV_SHOWS_TYPE:
                setTitle("On The Air Series");
                break;
            case Constants.POPULAR_TV_SHOWS_TYPE:
                setTitle("Popular Series");
                break;
            case Constants.TOP_RATED_TV_SHOWS_TYPE:
                setTitle("Top Rated Series");
                break;
        }

        mRecyclerView = findViewById(R.id.view_series_recView);
        mSeries = new ArrayList<>();
        mSeriesAdapter = new SeriesBriefSmallAdapter(mSeries, ViewAllSeriesActivity.this);
        mRecyclerView.setAdapter(mSeriesAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(ViewAllSeriesActivity.this, 3);
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
                    loadSeries(mSeriesType);
                    loading = true;
                }
            }
        });

        loadSeries(mSeriesType);
    }

    private void loadSeries(int seriesType){
        ApiInterface apiInterface = ApiClient.getMovieApi();

        switch (seriesType){
            case Constants.ON_THE_AIR_TV_SHOWS_TYPE:
                mOnAirSeriesCall = apiInterface.getOnTheAirSeries(Constants.API_KEY, presentPage);
                mOnAirSeriesCall.enqueue(new Callback<OnTheAirSeriesResponse>() {
                    @Override
                    public void onResponse(Call<OnTheAirSeriesResponse> call, Response<OnTheAirSeriesResponse> response) {
                        if (!response.isSuccessful()) {
                            mOnAirSeriesCall = call.clone();
                            mOnAirSeriesCall.enqueue(this);
                            return;
                        }

                        if (response.body() == null) return;
                        if (response.body().getResults() == null) return;

                        for (SeriesBrief seriesBrief : response.body().getResults()) {
                            if (seriesBrief != null && seriesBrief.getName() != null && seriesBrief.getPosterPath() != null)
                                mSeries.add(seriesBrief);
                        }
                        mSeriesAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<OnTheAirSeriesResponse> call, Throwable t) {}
                });
                break;

            case Constants.POPULAR_TV_SHOWS_TYPE:
                mPopularSeriesCall = apiInterface.getPopularSeries(Constants.API_KEY, presentPage);
                mPopularSeriesCall.enqueue(new Callback<PopularSeriesResponse>() {
                    @Override
                    public void onResponse(Call<PopularSeriesResponse> call, Response<PopularSeriesResponse> response) {
                        if (!response.isSuccessful()) {
                            mPopularSeriesCall = call.clone();
                            mPopularSeriesCall.enqueue(this);
                            return;
                        }

                        if (response.body() == null) return;
                        if (response.body().getResults() == null) return;

                        for (SeriesBrief seriesBrief : response.body().getResults()) {
                            if (seriesBrief != null && seriesBrief.getName() != null && seriesBrief.getPosterPath() != null)
                                mSeries.add(seriesBrief);
                        }
                        mSeriesAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<PopularSeriesResponse> call, Throwable t) {}
                });
                break;

            case Constants.TOP_RATED_MOVIES_TYPE:
                mTopRatedSeriesCall = apiInterface.getTopRatedSeries(Constants.API_KEY, presentPage);
                mTopRatedSeriesCall.enqueue(new Callback<TopRatedSeriesResponse>() {
                    @Override
                    public void onResponse(Call<TopRatedSeriesResponse> call, Response<TopRatedSeriesResponse> response) {
                        if (!response.isSuccessful()) {
                            mTopRatedSeriesCall = call.clone();
                            mTopRatedSeriesCall.enqueue(this);
                            return;
                        }

                        if (response.body() == null) return;
                        if (response.body().getResults() == null) return;

                        for (SeriesBrief seriesBrief : response.body().getResults()) {
                            if (seriesBrief != null && seriesBrief.getName() != null && seriesBrief.getPosterPath() != null)
                                mSeries.add(seriesBrief);
                        }
                        mSeriesAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<TopRatedSeriesResponse> call, Throwable t) {}
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