package cf.vandit.movie_app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.EpisodesAdapter;
import cf.vandit.movie_app.adapters.SeriesBriefSmallAdapter;
import cf.vandit.movie_app.adapters.SeriesCastsAdapter;
import cf.vandit.movie_app.adapters.TrailerAdapter;
import cf.vandit.movie_app.database.Favourite;
import cf.vandit.movie_app.database.series.FavSeries;
import cf.vandit.movie_app.database.series.SeriesDatabase;
import cf.vandit.movie_app.network.series.EpisodeBrief;
import cf.vandit.movie_app.network.series.Genre;
import cf.vandit.movie_app.network.series.SeasonDetailsResponse;
import cf.vandit.movie_app.network.series.Series;
import cf.vandit.movie_app.network.series.SeriesBrief;
import cf.vandit.movie_app.network.series.SeriesCastBrief;
import cf.vandit.movie_app.network.series.SeriesCreditsResponse;
import cf.vandit.movie_app.network.series.SimilarSeriesResponse;
import cf.vandit.movie_app.network.videos.Trailer;
import cf.vandit.movie_app.network.videos.TrailersResponse;
import cf.vandit.movie_app.request.ApiClient;
import cf.vandit.movie_app.request.ApiInterface;
import cf.vandit.movie_app.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesDetailsActivity extends AppCompatActivity {

    private int seriesId;
    private String imdbId;
    private Integer number_of_seasons = 0;

    private ImageView series_poster;
    private AVLoadingIndicatorView series_progress_bar;
    private ImageView series_back_btn;
    private ImageView series_favourite_btn;

    private TextView series_title;
    private TextView series_year;
    private TextView series_genre;
    private TextView series_duration;
    private TextView series_story_line;

    private TextView series_year_separator;
    private TextView series_genre_separator;

    private TextView series_story_line_heading;
    private TextView series_trailer_heading;
    private TextView series_star_cast_heading;
    private TextView series_recommended_heading;

    private ConstraintLayout series_episodes_heading;
    private Spinner series_season_spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private EpisodesAdapter mEpisodesAdapter;

    private RecyclerView series_episodes;
    private RecyclerView series_trailers;
    private RecyclerView series_cast;
    private RecyclerView series_recommended;

    private Call<Series> mSeriesDetailsCall;
    private Call<TrailersResponse> mSeriesTrailersCall;
    private Call<SeriesCreditsResponse> mSeriesCreditsCall;
    private Call<SimilarSeriesResponse> mSimilarSeriesCall;
    private Call<SeasonDetailsResponse> mSeasonDetailsCall;

    private List<Trailer> mTrailers;
    private List<SeriesCastBrief> mCasts;
    private List<SeriesBrief> mSimilarSeries;
    private List<EpisodeBrief> mEpisodes;

    private TrailerAdapter mTrailerAdapter;
    private SeriesCastsAdapter mCastAdapter;
    private SeriesBriefSmallAdapter mSimilarMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_details);

        series_back_btn = findViewById(R.id.series_details_back_btn);
        series_favourite_btn = findViewById(R.id.series_details_favourite_btn);

        series_story_line_heading = findViewById(R.id.series_details_storyline_heading);

        series_progress_bar = findViewById(R.id.series_details_progress_bar);

        series_poster = findViewById(R.id.series_details_imageView);
        series_title = findViewById(R.id.series_details_title);
        series_year = findViewById(R.id.series_details_year);
        series_genre = findViewById(R.id.series_details_genre);
        series_duration = findViewById(R.id.series_details_duration);
        series_story_line = findViewById(R.id.series_details_storyline);
        series_trailers = findViewById(R.id.series_details_trailer);
        series_cast = findViewById(R.id.series_details_cast);
        series_year_separator = findViewById(R.id.series_details_year_separator);
        series_genre_separator = findViewById(R.id.series_details_genre_separator);

        series_trailer_heading = findViewById(R.id.series_details_trailer_heading);
        series_trailers = findViewById(R.id.series_details_trailer);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(series_trailers);
        mTrailers = new ArrayList<>();
        mTrailerAdapter = new TrailerAdapter(SeriesDetailsActivity.this, mTrailers);
        series_trailers.setAdapter(mTrailerAdapter);
        series_trailers.setLayoutManager(new LinearLayoutManager(SeriesDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));

        series_star_cast_heading = findViewById(R.id.series_details_cast_heading);
        series_cast = findViewById(R.id.series_details_cast);
        mCasts = new ArrayList<>();
        mCastAdapter = new SeriesCastsAdapter(SeriesDetailsActivity.this, mCasts);
        series_cast.setAdapter(mCastAdapter);
        series_cast.setLayoutManager(new LinearLayoutManager(SeriesDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));

        series_recommended_heading = findViewById(R.id.series_details_recommended_heading);
        series_recommended = findViewById(R.id.series_details_recommended);
        mSimilarSeries = new ArrayList<>();
        mSimilarMoviesAdapter = new SeriesBriefSmallAdapter(mSimilarSeries, SeriesDetailsActivity.this);
        series_recommended.setAdapter(mSimilarMoviesAdapter);
        series_recommended.setLayoutManager(new LinearLayoutManager(SeriesDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));

        series_episodes_heading = findViewById(R.id.series_details_episodes_heading_layout);
        series_season_spinner = findViewById(R.id.series_details_episodes_spinner);
        series_episodes = findViewById(R.id.series_details_episodes);
        mEpisodes = new ArrayList<>();

        Intent receivedIntent = getIntent();
        seriesId = receivedIntent.getIntExtra("series_id", -1);

        if (seriesId==-1) finish();

        series_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSeriesDetailsCall!=null) mSeriesDetailsCall.cancel();
        if(mSimilarSeriesCall!=null) mSimilarSeriesCall.cancel();
        if(mSeriesCreditsCall!=null) mSeriesCreditsCall.cancel();
        if(mSeriesTrailersCall!=null) mSeriesTrailersCall.cancel();
    }

    private void loadActivity(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mSeriesDetailsCall = apiInterface.getSeriesDetails(seriesId, Constants.API_KEY, "external_ids");
        mSeriesDetailsCall.enqueue(new Callback<Series>() {
            @Override
            public void onResponse(Call<Series> call, Response<Series> response) {
                if(!response.isSuccessful()){
                    mSeriesDetailsCall = call.clone();
                    mSeriesDetailsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;

                imdbId = response.body().getExternalIds().getImdbId();

                mEpisodesAdapter = new EpisodesAdapter(mEpisodes, imdbId, SeriesDetailsActivity.this);
                series_episodes.setAdapter(mEpisodesAdapter);
                series_episodes.setLayoutManager(new LinearLayoutManager(SeriesDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));

                Glide.with(getApplicationContext())
                        .load(Constants.IMAGE_LOADING_BASE_URL_1280 + response.body().getBackdropPath())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                series_progress_bar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                series_progress_bar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(series_poster);

                if (response.body().getName() != null)
                    series_title.setText(response.body().getName());
                else
                   series_title.setText("");

                if (response.body().getOverview() != null && !response.body().getOverview().trim().isEmpty()) {
                    series_story_line_heading.setVisibility(View.VISIBLE);
                    series_story_line.setText(response.body().getOverview());
                } else {
                    series_story_line.setText("");
                }

                setFavourite(response.body().getId(), response.body().getPosterPath(), response.body().getName());
                setYear(response.body().getFirstAirDate());
                setGenres(response.body().getGenres());
                setSeasons(response.body().getNumberOfSeasons());
                setEpisodes(response.body().getId());
                setTrailers();
                setCasts();
                setSimilarSeries();
            }

            @Override
            public void onFailure(Call<Series> call, Throwable t) {

            }
        });
    }

    private void setFavourite(final Integer SeriesId, final String posterPath, final String seriesTitle) {
        if (Favourite.isFavSeries(SeriesDetailsActivity.this, SeriesId)) {
            series_favourite_btn.setTag(Constants.TAG_FAV);
            series_favourite_btn.setImageResource(R.drawable.ic_favourite_filled);
            series_favourite_btn.setColorFilter(Color.argb(1, 236, 116, 85));
        } else {
            series_favourite_btn.setTag(Constants.TAG_NOT_FAV);
            series_favourite_btn.setImageResource(R.drawable.ic_favourite_outlined);
        }

        series_favourite_btn.setOnClickListener(view -> {

            class SaveSeries extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    FavSeries favSeries = new FavSeries(SeriesId, posterPath, seriesTitle);
                    SeriesDatabase.getInstance(getApplicationContext())
                            .seriesDao()
                            .insertSeries(favSeries);

                    return null;
                }
            }

            class DeleteSeries extends AsyncTask<Void, Void, Void>{

                @Override
                protected Void doInBackground(Void... voids) {
                    SeriesDatabase.getInstance(getApplicationContext())
                            .seriesDao()
                            .deleteSeriesById(SeriesId);

                    return null;
                }
            }

            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            if ((int) series_favourite_btn.getTag() == Constants.TAG_FAV) {
                series_favourite_btn.setTag(Constants.TAG_NOT_FAV);
                series_favourite_btn.setImageResource(R.drawable.ic_favourite_outlined);
                DeleteSeries deleteSeries = new DeleteSeries();
                deleteSeries.execute();
            } else {
                series_favourite_btn.setTag(Constants.TAG_FAV);
                series_favourite_btn.setImageResource(R.drawable.ic_favourite_filled);
                series_favourite_btn.setColorFilter(Color.argb(1, 236, 116, 85));
                SaveSeries saveSeries = new SaveSeries();
                saveSeries.execute();
            }
        });
    }

    private void setYear(String releaseDateString) {
        if (releaseDateString != null && !releaseDateString.trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date releaseDate = sdf1.parse(releaseDateString);
                series_year.setText(sdf2.format(releaseDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            series_year.setText("");
        }
    }

    private void setGenres(List<Genre> genresList) {
        String genres = "";
        if (genresList != null) {
            if(genresList.size() < 3) {
                for (int i = 0; i < genresList.size(); i++) {
                    if (genresList.get(i) == null) continue;
                    if (i == genresList.size() - 1) {
                        if(genresList.get(i).getGenreName().equals("Science Fiction")) {
                            genres = genres.concat("Sci-Fi");
                        } else {
                            genres = genres.concat(genresList.get(i).getGenreName());
                        }
                    } else {
                        if(genresList.get(i).getGenreName().equals("Science Fiction")) {
                            genres = genres.concat("Sci-Fi" + ", ");
                        } else {
                            genres = genres.concat(genresList.get(i).getGenreName() + ", ");
                        }
                    }
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    if (genresList.get(i) == null) continue;
                    if(genresList.get(i).getGenreName().equals("Science Fiction")) {
                        genres = genres.concat("Sci-Fi" + ", ");
                    } else {
                        genres = genres.concat(genresList.get(i).getGenreName() + ", ");
                    }
                }
            }
        }
        String x = series_year.getText().toString();
        if(!x.equals("") && !genres.equals("")){
            series_year_separator.setVisibility(View.VISIBLE);
        }
        series_genre.setText(genres);
    }

    private void setSeasons(Integer numberOfSeasons){
        if (numberOfSeasons == 1){
            series_duration.setText(numberOfSeasons.toString() + " season");
        } else {
            series_duration.setText(numberOfSeasons.toString() + " seasons");
        }
        number_of_seasons = numberOfSeasons;
        series_genre_separator.setVisibility(View.VISIBLE);
        series_episodes_heading.setVisibility(View.VISIBLE);
    }

    private void setEpisodes(Integer id){
        ArrayList season_number = new ArrayList();
        for(int i=1; i<=number_of_seasons; i++){
            season_number.add("Season " + i);
        }

        spinnerAdapter = new ArrayAdapter<String>(SeriesDetailsActivity.this, android.R.layout.simple_list_item_1, season_number);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        series_season_spinner.setAdapter(spinnerAdapter);
        series_season_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mEpisodes.clear();

                ApiInterface apiInterface = ApiClient.getMovieApi();
                mSeasonDetailsCall = apiInterface.getSeasonDetails(id, i+1, Constants.API_KEY);
                mSeasonDetailsCall.enqueue(new Callback<SeasonDetailsResponse>() {
                    @Override
                    public void onResponse(Call<SeasonDetailsResponse> call, Response<SeasonDetailsResponse> response) {
                        if(!response.isSuccessful()){
                            mSeasonDetailsCall.clone();
                            mSeasonDetailsCall.enqueue(this);
                        }

                        if (response.body() == null) return;

                        for (EpisodeBrief episodeBrief : response.body().getEpisodes()) {
                            if (episodeBrief != null && episodeBrief.getName() != null && episodeBrief.getEpisodeNumber() != null)
                                mEpisodes.add(episodeBrief);
                        }

                        if(mEpisodes.isEmpty()) {
                            series_episodes_heading.setVisibility(View.GONE);
                        }

                        mEpisodesAdapter.notifyDataSetChanged();
                        series_episodes.scrollToPosition(0);
                    }

                    @Override
                    public void onFailure(Call<SeasonDetailsResponse> call, Throwable t) {}
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void setTrailers(){
        ApiInterface apiService = ApiClient.getMovieApi();
        mSeriesTrailersCall = apiService.getSeriesVideos(seriesId, Constants.API_KEY);
        mSeriesTrailersCall.enqueue(new Callback<TrailersResponse>() {
            @Override
            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                if(!response.isSuccessful()){
                    mSeriesTrailersCall.clone();
                    mSeriesTrailersCall.enqueue(this);
                }

                if (response.body() == null) return;
                if (response.body().getVideos() == null) return;

                for (Trailer video : response.body().getVideos()) {
                    if (video != null && video.getSite() != null && video.getSite().equals("YouTube") && video.getType() != null && video.getType().equals("Trailer"))
                        mTrailers.add(video);
                }

                if(!mTrailers.isEmpty()) {
                    series_trailer_heading.setVisibility(View.VISIBLE);
                }

                mTrailerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrailersResponse> call, Throwable t) {}
        });
    }

    private void setCasts() {
        ApiInterface apiService = ApiClient.getMovieApi();
        mSeriesCreditsCall = apiService.getSeriesCredits(seriesId, Constants.API_KEY);
        mSeriesCreditsCall.enqueue(new Callback<SeriesCreditsResponse>() {
            @Override
            public void onResponse(Call<SeriesCreditsResponse> call, Response<SeriesCreditsResponse> response) {
                if (!response.isSuccessful()) {
                    mSeriesCreditsCall = call.clone();
                    mSeriesCreditsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getCasts() == null) return;

                for (SeriesCastBrief castBrief : response.body().getCasts()) {
                    if (castBrief != null && castBrief.getName() != null)
                        mCasts.add(castBrief);
                }

                if(!mCasts.isEmpty()) {
                    series_star_cast_heading.setVisibility(View.VISIBLE);
                }

                mCastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SeriesCreditsResponse> call, Throwable t) {}
        });
    }

    private void setSimilarSeries() {
        ApiInterface apiService = ApiClient.getMovieApi();
        mSimilarSeriesCall = apiService.getSimilarSeries(seriesId, Constants.API_KEY, 1);
        mSimilarSeriesCall.enqueue(new Callback<SimilarSeriesResponse>() {
            @Override
            public void onResponse(Call<SimilarSeriesResponse> call, Response<SimilarSeriesResponse> response) {
                if (!response.isSuccessful()) {
                    mSimilarSeriesCall = call.clone();
                    mSimilarSeriesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (SeriesBrief seriesBrief : response.body().getResults()) {
                    if (seriesBrief != null && seriesBrief.getName() != null && seriesBrief.getPosterPath() != null)
                        mSimilarSeries.add(seriesBrief);
                }

                if(!mSimilarSeries.isEmpty()) {
                    series_recommended_heading.setVisibility(View.VISIBLE);
                }

                mSimilarMoviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SimilarSeriesResponse> call, Throwable t) {}
        });
    }
}