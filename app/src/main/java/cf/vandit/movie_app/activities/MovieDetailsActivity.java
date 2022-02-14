package cf.vandit.movie_app.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.MovieBriefSmallAdapter;
import cf.vandit.movie_app.adapters.MovieCastsAdapter;
import cf.vandit.movie_app.database.movies.FavMovie;
import cf.vandit.movie_app.database.movies.MovieDatabase;
import cf.vandit.movie_app.network.movie.SimilarMoviesResponse;
import cf.vandit.movie_app.adapters.TrailerAdapter;
import cf.vandit.movie_app.network.movie.Genre;
import cf.vandit.movie_app.network.movie.Movie;
import cf.vandit.movie_app.network.movie.MovieBrief;
import cf.vandit.movie_app.network.movie.MovieCastBrief;
import cf.vandit.movie_app.network.movie.MovieCreditsResponse;
import cf.vandit.movie_app.network.videos.Trailer;
import cf.vandit.movie_app.network.videos.TrailersResponse;
import cf.vandit.movie_app.request.ApiClient;
import cf.vandit.movie_app.request.ApiInterface;
import cf.vandit.movie_app.utils.Constants;
import cf.vandit.movie_app.database.Favourite;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private int movieId;
    private String imdbId = "tt0137523";

    private ImageView movie_poster;
    private AVLoadingIndicatorView movie_progress_bar;
    private ImageView movie_back_btn;
    private ImageView movie_favourite_btn;

    private FloatingActionButton movie_stream_fab;

    private TextView movie_title;
    private TextView movie_year;
    private TextView movie_genre;
    private TextView movie_duration;
    private TextView movie_story_line;

    private TextView movie_year_separator;
    private TextView movie_genre_separator;

    private TextView movie_story_line_heading;
    private TextView movie_trailer_heading;
    private TextView movie_star_cast_heading;
    private TextView movie_recommended_heading;

    private RecyclerView movie_trailers;
    private RecyclerView movie_cast;
    private RecyclerView movie_recommended;

    private Call<Movie> mMovieDetailsCall;
    private Call<TrailersResponse> mMovieTrailersCall;
    private Call<MovieCreditsResponse> mMovieCreditsCall;
    private Call<SimilarMoviesResponse> mSimilarMoviesCall;

    private List<Trailer> mTrailers;
    private List<MovieCastBrief> mCasts;
    private List<MovieBrief> mSimilarMovies;

    private TrailerAdapter mTrailerAdapter;
    private MovieCastsAdapter mCastAdapter;
    private MovieBriefSmallAdapter mSimilarMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie_back_btn = findViewById(R.id.movie_details_back_btn);
        movie_favourite_btn = findViewById(R.id.movie_details_favourite_btn);

        movie_stream_fab = findViewById(R.id.movie_details_fab);
        movie_story_line_heading = findViewById(R.id.movie_details_storyline_heading);

        movie_progress_bar = findViewById(R.id.movie_details_progress_bar);

        movie_poster = findViewById(R.id.movie_details_imageView);
        movie_title = findViewById(R.id.movie_details_title);
        movie_year = findViewById(R.id.movie_details_year);
        movie_genre = findViewById(R.id.movie_details_genre);
        movie_duration = findViewById(R.id.movie_details_duration);
        movie_story_line = findViewById(R.id.movie_details_storyline);
        movie_trailers = findViewById(R.id.movie_details_trailer);
        movie_cast = findViewById(R.id.movie_details_cast);
        movie_recommended = findViewById(R.id.movie_details_recommended);

        movie_year_separator = findViewById(R.id.movie_details_year_separator);
        movie_genre_separator = findViewById(R.id.movie_details_genre_separator);

        movie_trailer_heading = (TextView) findViewById(R.id.movie_details_trailer_heading);
        movie_trailers = (RecyclerView) findViewById(R.id.movie_details_trailer);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(movie_trailers);
        mTrailers = new ArrayList<>();
        mTrailerAdapter = new TrailerAdapter(MovieDetailsActivity.this, mTrailers);
        movie_trailers.setAdapter(mTrailerAdapter);
        movie_trailers.setLayoutManager(new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));

        movie_star_cast_heading = (TextView) findViewById(R.id.movie_details_cast_heading);
        movie_cast = (RecyclerView) findViewById(R.id.movie_details_cast);
        mCasts = new ArrayList<>();
        mCastAdapter = new MovieCastsAdapter(MovieDetailsActivity.this, mCasts);
        movie_cast.setAdapter(mCastAdapter);
        movie_cast.setLayoutManager(new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));

        movie_recommended_heading = (TextView) findViewById(R.id.movie_details_recommended_heading);
        movie_recommended = (RecyclerView) findViewById(R.id.movie_details_recommended);
        mSimilarMovies = new ArrayList<>();
        mSimilarMoviesAdapter = new MovieBriefSmallAdapter(mSimilarMovies, MovieDetailsActivity.this);
        movie_recommended.setAdapter(mSimilarMoviesAdapter);
        movie_recommended.setLayoutManager(new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));

        Intent receivedIntent = getIntent();
        movieId = receivedIntent.getIntExtra("movie_id", -1);

        if (movieId==-1) finish();

        final FavMovie favMovie = (FavMovie) getIntent().getSerializableExtra("name");

        movie_stream_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StreamMovie();
            }
        });

        movie_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadActivity(favMovie);
    }

    private void loadActivity(FavMovie mFavMovie){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mMovieDetailsCall = apiInterface.getMovieDetails(movieId, Constants.API_KEY);
        mMovieDetailsCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                if (!response.isSuccessful()) {
                    mMovieDetailsCall = call.clone();
                    mMovieDetailsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;

                imdbId = response.body().getImdbId();
                movie_stream_fab.setEnabled(true);

                Glide.with(getApplicationContext())
                        .load(Constants.IMAGE_LOADING_BASE_URL_1280 + response.body().getBackdropPath())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                movie_progress_bar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                movie_progress_bar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(movie_poster);

                if (response.body().getTitle() != null)
                    movie_title.setText(response.body().getTitle());
                else
                    movie_title.setText("");

                if (response.body().getOverview() != null && !response.body().getOverview().trim().isEmpty()) {
                    movie_story_line_heading.setVisibility(View.VISIBLE);
                    movie_story_line.setText(response.body().getOverview());
                } else {
                    movie_story_line.setText("");
                }

                setFavourite(response.body().getId(), response.body().getPosterPath(), response.body().getTitle(), mFavMovie);
                setYear(response.body().getReleaseDate());
                setGenres(response.body().getGenres());
                setDuration(response.body().getRuntime());
                setTrailers();
                setCasts();
                setSimilarMovies();
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {}
        });
    }

    private void setFavourite(final Integer movieId, final String posterPath, final String movieTitle, final FavMovie mFavMovie) {
        if (Favourite.isFavMovie(MovieDetailsActivity.this, movieId)) {
            movie_favourite_btn.setTag(Constants.TAG_FAV);
            movie_favourite_btn.setImageResource(R.drawable.ic_favourite_filled);
            movie_favourite_btn.setColorFilter(Color.argb(1, 236, 116, 85));
        } else {
            movie_favourite_btn.setTag(Constants.TAG_NOT_FAV);
            movie_favourite_btn.setImageResource(R.drawable.ic_favourite_outlined);
        }

        movie_favourite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                class SaveMovie extends AsyncTask<Void, Void, Void>{

                    @Override
                    protected Void doInBackground(Void... voids) {
                        FavMovie favMovie = new FavMovie(movieId, movieTitle, posterPath);
                        MovieDatabase.getInstance(getApplicationContext())
                                .movieDao()
                                .insertMovie(favMovie);

                        return null;
                    }
                }

                class DeleteMovie extends AsyncTask<Void, Void, Void>{

                    @Override
                    protected Void doInBackground(Void... voids) {
                        MovieDatabase.getInstance(getApplicationContext())
                                .movieDao()
                                .deleteMovieById(movieId);

                        return null;
                    }
                }

                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if ((int) movie_favourite_btn.getTag() == Constants.TAG_FAV) {
                    movie_favourite_btn.setTag(Constants.TAG_NOT_FAV);
                    movie_favourite_btn.setImageResource(R.drawable.ic_favourite_outlined);
                    DeleteMovie deleteMovie = new DeleteMovie();
                    deleteMovie.execute();
                } else {
                    movie_favourite_btn.setTag(Constants.TAG_FAV);
                    movie_favourite_btn.setImageResource(R.drawable.ic_favourite_filled);
                    movie_favourite_btn.setColorFilter(Color.argb(1, 236, 116, 85));
                    SaveMovie saveMovie = new SaveMovie();
                    saveMovie.execute();
                }
            }
        });
    }

    private void setYear(String releaseDateString) {
        if (releaseDateString != null && !releaseDateString.trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date releaseDate = sdf1.parse(releaseDateString);
                movie_year.setText(sdf2.format(releaseDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            movie_year.setText("");
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
                for (int i = 0; i < 3; i++) {
                    if (genresList.get(i) == null) continue;
                    if (i == 2) {
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
            }
        }
        String x = movie_year.getText().toString();
        if(!x.equals("") && !genres.equals("")){
            movie_year_separator.setVisibility(View.VISIBLE);
        }
        movie_genre.setText(genres);
    }

    private void setDuration(Integer runtime){
        String detailsString = "";

        if (runtime != null && runtime != 0) {
            if (runtime < 60) {
                detailsString += runtime + " min(s)";
            } else {
                detailsString += runtime / 60 + " hr " + runtime % 60 + " mins";
            }
        }

        String x = movie_genre.getText().toString();
        if(!x.equals("") && !detailsString.equals("")){
            movie_genre_separator.setVisibility(View.VISIBLE);
        }
        movie_duration.setText(detailsString);
    }

    private void setTrailers() {
        ApiInterface apiService = ApiClient.getMovieApi();
        mMovieTrailersCall = apiService.getMovieVideos(movieId, Constants.API_KEY);
        mMovieTrailersCall.enqueue(new Callback<TrailersResponse>() {
            @Override
            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                if (!response.isSuccessful()) {
                    mMovieTrailersCall = call.clone();
                    mMovieTrailersCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getVideos() == null) return;

                for (Trailer video : response.body().getVideos()) {
                    if (video != null && video.getSite() != null && video.getSite().equals("YouTube") && video.getType() != null && video.getType().equals("Trailer"))
                        mTrailers.add(video);
                }

                if(!mTrailers.isEmpty()) {
                    movie_trailer_heading.setVisibility(View.VISIBLE);
                }

                mTrailerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrailersResponse> call, Throwable t) {

            }
        });
    }

    private void setCasts() {
        ApiInterface apiService = ApiClient.getMovieApi();
        mMovieCreditsCall = apiService.getMovieCredits(movieId, Constants.API_KEY);
        mMovieCreditsCall.enqueue(new Callback<MovieCreditsResponse>() {
            @Override
            public void onResponse(Call<MovieCreditsResponse> call, Response<MovieCreditsResponse> response) {
                if (!response.isSuccessful()) {
                    mMovieCreditsCall = call.clone();
                    mMovieCreditsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getCasts() == null) return;

                for (MovieCastBrief castBrief : response.body().getCasts()) {
                    if (castBrief != null && castBrief.getName() != null)
                        mCasts.add(castBrief);
                }

                if(!mCasts.isEmpty()) {
                    movie_star_cast_heading.setVisibility(View.VISIBLE);
                }

                mCastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieCreditsResponse> call, Throwable t) {

            }
        });
    }

    private void setSimilarMovies() {
        ApiInterface apiService = ApiClient.getMovieApi();
        mSimilarMoviesCall = apiService.getSimilarMovies(movieId, Constants.API_KEY, 1);
        mSimilarMoviesCall.enqueue(new Callback<SimilarMoviesResponse>() {
            @Override
            public void onResponse(Call<SimilarMoviesResponse> call, Response<SimilarMoviesResponse> response) {
                if (!response.isSuccessful()) {
                    mSimilarMoviesCall = call.clone();
                    mSimilarMoviesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                for (MovieBrief movieBrief : response.body().getResults()) {
                    if (movieBrief != null && movieBrief.getTitle() != null && movieBrief.getPosterPath() != null)
                        mSimilarMovies.add(movieBrief);
                }

                if(!mSimilarMovies.isEmpty()) {
                    movie_recommended_heading.setVisibility(View.VISIBLE);
                }

                mSimilarMoviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SimilarMoviesResponse> call, Throwable t) {

            }
        });
    }

    private void StreamMovie(){
        Intent intent = new Intent(this, MovieStreamActivity.class);
        intent.putExtra("movie_id", imdbId);
        startActivity(intent);
    }
}