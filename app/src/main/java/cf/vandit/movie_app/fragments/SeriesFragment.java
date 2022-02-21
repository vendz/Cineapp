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
import java.util.Timer;
import java.util.TimerTask;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.ViewAllSeriesActivity;
import cf.vandit.movie_app.adapters.SeriesBriefSmallAdapter;
import cf.vandit.movie_app.adapters.SeriesCarouselAdapter;
import cf.vandit.movie_app.network.series.AiringTodaySeriesResponse;
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

public class SeriesFragment extends Fragment {

    private ProgressBar mProgressBar;

    private TextView mViewOnTheAir;
    private TextView mViewPopular;
    private TextView mViewTopRated;

    private Timer timer;
    private TimerTask timerTask;
    private int position;
    private LinearLayoutManager carouselLayoutManager;

    private RecyclerView mAiringTodayRecyclerView;
    private List<SeriesBrief> mAiringTodaySeries;
    private SeriesCarouselAdapter mAiringTodayAdapter;

    private RecyclerView mOnTheAirRecyclerView;
    private List<SeriesBrief> mOnTheAirSeries;
    private SeriesBriefSmallAdapter mOnTheAirAdapter;

    private RecyclerView mPopularSeriesRecyclerView;
    private List<SeriesBrief> mPopularSeries;
    private SeriesBriefSmallAdapter mPopularSeriesAdapter;

    private RecyclerView mTopRatedRecyclerView;
    private List<SeriesBrief> mTopRatedSeries;
    private SeriesBriefSmallAdapter mTopRatedAdapter;

    private ConstraintLayout mOnTheAirHeading;
    private ConstraintLayout mPopularHeading;
    private ConstraintLayout mTopRatedHeading;

    private boolean mAiringTodaySectionLoaded;
    private boolean mOnTheAirSectionLoaded;
    private boolean mPopularSectionLoaded;
    private boolean mTopRatedSectionLoaded;

    private Call<AiringTodaySeriesResponse> mAiringTodaySeriesCall;
    private Call<OnTheAirSeriesResponse> mOnTheAirSeriesCall;
    private Call<PopularSeriesResponse> mPopularSeriesCall;
    private Call<TopRatedSeriesResponse> mTopRatedSeriesCall;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = view.findViewById(R.id.tv_progressBar);
        mAiringTodaySectionLoaded = false;
        mOnTheAirSectionLoaded = false;
        mPopularSectionLoaded = false;
        mTopRatedSectionLoaded = false;

        mOnTheAirHeading = view.findViewById(R.id.on_the_air_heading);
        mPopularHeading = view.findViewById(R.id.popular_tv_heading);
        mTopRatedHeading = view.findViewById(R.id.top_rated_tv_heading);

        mViewOnTheAir = view.findViewById(R.id.view_on_the_air);
        mViewPopular = view.findViewById(R.id.view_popular_tv);
        mViewTopRated = view.findViewById(R.id.view_top_rated_tv);

        mAiringTodayRecyclerView = view.findViewById(R.id.carousel_tv_recView);
        mOnTheAirRecyclerView = view.findViewById(R.id.on_the_air_recView);
        mPopularSeriesRecyclerView = view.findViewById(R.id.popular_tv_recView);
        mTopRatedRecyclerView = view.findViewById(R.id.top_rated_tv_recView);

        mAiringTodaySeries = new ArrayList<>();
        mOnTheAirSeries = new ArrayList<>();
        mPopularSeries = new ArrayList<>();
        mTopRatedSeries = new ArrayList<>();

        mAiringTodayAdapter = new SeriesCarouselAdapter(mAiringTodaySeries, getContext());
        carouselLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mAiringTodayRecyclerView.setLayoutManager(carouselLayoutManager);
        mAiringTodayRecyclerView.setAdapter(mAiringTodayAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mAiringTodayRecyclerView);
        mAiringTodayRecyclerView.smoothScrollBy(5,0);

        mOnTheAirAdapter = new SeriesBriefSmallAdapter(mOnTheAirSeries, getContext());
        mOnTheAirRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mOnTheAirRecyclerView.setAdapter(mOnTheAirAdapter);

        mPopularSeriesAdapter = new SeriesBriefSmallAdapter(mPopularSeries, getContext());
        mPopularSeriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mPopularSeriesRecyclerView.setAdapter(mPopularSeriesAdapter);

        mTopRatedAdapter = new SeriesBriefSmallAdapter(mTopRatedSeries, getContext());
        mTopRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mTopRatedRecyclerView.setAdapter(mTopRatedAdapter);

        mAiringTodayRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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

        mViewOnTheAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllSeriesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.ON_THE_AIR_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });

        mViewPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllSeriesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.POPULAR_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });

        mViewTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllSeriesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.TOP_RATED_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });

        loadAiringToday();
        loadOnTheAir();
        loadPopular();
        loadTopRated();
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
                    if (position == mAiringTodaySeries.size() - 1) {
                        mAiringTodayRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                position = 0;
                                mAiringTodayRecyclerView.smoothScrollToPosition(position);
                                mAiringTodayRecyclerView.smoothScrollBy(5, 0);
                            }
                        });
                    } else {
                        position++;
                        mAiringTodayRecyclerView.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 4000, 4000);
        }
    }

    private void loadAiringToday(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mAiringTodaySeriesCall = apiInterface.getAiringTodaySeries(Constants.API_KEY, 1);
        mAiringTodaySeriesCall.enqueue(new Callback<AiringTodaySeriesResponse>() {
            @Override
            public void onResponse(Call<AiringTodaySeriesResponse> call, Response<AiringTodaySeriesResponse> response) {
                if (!response.isSuccessful()) {
                    mAiringTodaySeriesCall = call.clone();
                    mAiringTodaySeriesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (SeriesBrief SeriesBrief : response.body().getResults()) {
                    if (SeriesBrief != null && SeriesBrief.getBackdropPath() != null)
                        mAiringTodaySeries.add(SeriesBrief);
                }
                mAiringTodayAdapter.notifyDataSetChanged();
                mAiringTodaySectionLoaded = true;
                checkAllDataLoaded();
            }

            @Override
            public void onFailure(Call<AiringTodaySeriesResponse> call, Throwable t) {}
        });
    }

    private void loadOnTheAir(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mOnTheAirSeriesCall = apiInterface.getOnTheAirSeries(Constants.API_KEY, 1);
        mOnTheAirSeriesCall.enqueue(new Callback<OnTheAirSeriesResponse>() {
            @Override
            public void onResponse(Call<OnTheAirSeriesResponse> call, Response<OnTheAirSeriesResponse> response) {
                if (!response.isSuccessful()) {
                    mOnTheAirSeriesCall = call.clone();
                    mOnTheAirSeriesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (SeriesBrief SeriesBrief : response.body().getResults()) {
                    if (SeriesBrief != null && SeriesBrief.getBackdropPath() != null)
                        mOnTheAirSeries.add(SeriesBrief);
                }
                mOnTheAirAdapter.notifyDataSetChanged();
                mOnTheAirSectionLoaded = true;
                checkAllDataLoaded();
            }

            @Override
            public void onFailure(Call<OnTheAirSeriesResponse> call, Throwable t) {}
        });
    }

    private void loadPopular(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mPopularSeriesCall = apiInterface.getPopularSeries(Constants.API_KEY, 1);
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

                for (SeriesBrief SeriesBrief : response.body().getResults()) {
                    if (SeriesBrief != null && SeriesBrief.getBackdropPath() != null)
                        mPopularSeries.add(SeriesBrief);
                }
                mPopularSeriesAdapter.notifyDataSetChanged();
                mPopularSectionLoaded = true;
                checkAllDataLoaded();
            }

            @Override
            public void onFailure(Call<PopularSeriesResponse> call, Throwable t) {}
        });
    }

    private void loadTopRated(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mTopRatedSeriesCall = apiInterface.getTopRatedSeries(Constants.API_KEY, 1);
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

                for (SeriesBrief SeriesBrief : response.body().getResults()) {
                    if (SeriesBrief != null && SeriesBrief.getBackdropPath() != null)
                        mTopRatedSeries.add(SeriesBrief);
                }
                mTopRatedAdapter.notifyDataSetChanged();
                mTopRatedSectionLoaded = true;
                checkAllDataLoaded();
            }

            @Override
            public void onFailure(Call<TopRatedSeriesResponse> call, Throwable t) {}
        });
    }

    private void checkAllDataLoaded() {
        if(mAiringTodaySectionLoaded && mOnTheAirSectionLoaded && mPopularSectionLoaded && mTopRatedSectionLoaded) {
            mProgressBar.setVisibility(View.GONE);
            mAiringTodayRecyclerView.setVisibility(View.VISIBLE);
            mOnTheAirHeading.setVisibility(View.VISIBLE);
            mOnTheAirRecyclerView.setVisibility(View.VISIBLE);
            mPopularHeading.setVisibility(View.VISIBLE);
            mPopularSeriesRecyclerView.setVisibility(View.VISIBLE);
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