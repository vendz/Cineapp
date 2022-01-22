package cf.vandit.movie_app.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.MovieCastsOfPersonAdapter;
import cf.vandit.movie_app.adapters.SeriesCastsOfPersonAdapter;
import cf.vandit.movie_app.network.cast.Person;
import cf.vandit.movie_app.network.movie.MovieCastOfPerson;
import cf.vandit.movie_app.network.movie.MovieCastsOfPersonResponse;
import cf.vandit.movie_app.network.series.SeriesCastOfPerson;
import cf.vandit.movie_app.network.series.SeriesCastsOfPersonResponse;
import cf.vandit.movie_app.request.ApiClient;
import cf.vandit.movie_app.request.ApiInterface;
import cf.vandit.movie_app.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastActivity extends AppCompatActivity {
    private int mPersonId;

    private ImageButton cast_backBtn;

    private AppBarLayout cast_appbar;
    private Toolbar cast_toolbar;
    private CollapsingToolbarLayout cast_collapsingToolbar;

    private ImageView cast_imageView;
    private AVLoadingIndicatorView cast_progress_bar;
    private TextView cast_name;
    private TextView cast_age_heading;
    private TextView cast_age;
    private TextView cast_birthplace_heading;
    private TextView cast_birthplace;

    private TextView cast_bio_heading;
    private TextView cast_bio;
    private TextView cast_read_more;
    private TextView cast_movie_heading;
    private RecyclerView cast_movie;
    private TextView cast_tv_heading;
    private RecyclerView cast_tv;

    private Call<Person> mPersonDetailsCall;
    private Call<MovieCastsOfPersonResponse> mMovieCastsOfPersonsCall;
    private Call<SeriesCastsOfPersonResponse> mTVCastsOfPersonsCall;

    private List<MovieCastOfPerson> mMovieCastOfPersons;
    private MovieCastsOfPersonAdapter mMovieCastsOfPersonAdapter;
    private List<SeriesCastOfPerson> mSeriesCastOfPeople;
    private SeriesCastsOfPersonAdapter mTVCastsOfPersonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);

        Intent receivedIntent = getIntent();
        mPersonId = receivedIntent.getIntExtra("person_id", -1);

        if(mPersonId == -1) finish();

        cast_backBtn = findViewById(R.id.cast_back_btn);

        cast_appbar = findViewById(R.id.cast_appBar);
        cast_toolbar = findViewById(R.id.cast_toolbar);
        cast_collapsingToolbar = findViewById(R.id.cast_collapsingToolbar);

        cast_imageView = findViewById(R.id.cast_imageView);
        cast_progress_bar = findViewById(R.id.cast_progress_bar);
        cast_name = findViewById(R.id.cast_name);
        cast_age_heading = findViewById(R.id.cast_age_heading);
        cast_age = findViewById(R.id.cast_age);
        cast_birthplace_heading = findViewById(R.id.cast_birthplace_heading);
        cast_birthplace = findViewById(R.id.cast_birthplace);

        cast_bio_heading = findViewById(R.id.cast_bio_heading);
        cast_bio = findViewById(R.id.cast_bio);

        cast_movie_heading = findViewById(R.id.cast_movie_heading);
        cast_movie = findViewById(R.id.cast_movie);
        cast_read_more = findViewById(R.id.cast_read_more);
        mMovieCastOfPersons = new ArrayList<>();
        mMovieCastsOfPersonAdapter = new MovieCastsOfPersonAdapter(CastActivity.this, mMovieCastOfPersons);
        cast_movie.setAdapter(mMovieCastsOfPersonAdapter);
        cast_movie.setLayoutManager(new LinearLayoutManager(CastActivity.this, LinearLayoutManager.HORIZONTAL, false));

        cast_tv_heading = findViewById(R.id.cast_tv_heading);
        cast_tv = findViewById(R.id.cast_tv);
        mSeriesCastOfPeople = new ArrayList<>();
        mTVCastsOfPersonAdapter = new SeriesCastsOfPersonAdapter(CastActivity.this, mSeriesCastOfPeople);
        cast_tv.setAdapter(mTVCastsOfPersonAdapter);
        cast_tv.setLayoutManager(new LinearLayoutManager(CastActivity.this, LinearLayoutManager.HORIZONTAL, false));

        setSupportActionBar(cast_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadActivity();

        cast_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadActivity(){
        ApiInterface apiInterface = ApiClient.getMovieApi();
        mPersonDetailsCall = apiInterface.getPersonDetails(mPersonId, Constants.API_KEY);
        mPersonDetailsCall.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (!response.isSuccessful()) {
                    mPersonDetailsCall = call.clone();
                    mPersonDetailsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;

                cast_appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
                    if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                        if (response.body().getName() != null)
                            cast_collapsingToolbar.setTitle(response.body().getName());
                        else
                            cast_collapsingToolbar.setTitle("");
                        cast_toolbar.setVisibility(View.VISIBLE);
                    } else {
                        cast_collapsingToolbar.setTitle("");
                        cast_toolbar.setVisibility(View.INVISIBLE);
                    }
                });

                Glide.with(getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_1280 + response.body().getProfilePath())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                cast_progress_bar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                cast_progress_bar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(cast_imageView);

                if (response.body().getName() != null)
                    cast_name.setText(response.body().getName());
                else
                    cast_name.setText("");

                if (response.body().getPlaceOfBirth() != null && !response.body().getPlaceOfBirth().trim().isEmpty())
                    cast_birthplace.setText(response.body().getPlaceOfBirth());

                if (response.body().getBiography() != null && !response.body().getBiography().trim().isEmpty()) {
                    cast_bio_heading.setVisibility(View.VISIBLE);
                    cast_read_more.setVisibility(View.VISIBLE);
                    cast_bio.setText(response.body().getBiography());
                    cast_read_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cast_bio.setMaxLines(Integer.MAX_VALUE);
                            cast_read_more.setVisibility(View.GONE);
                        }
                    });
                }

                setAge(response.body().getDateOfBirth());
                setMovieCast(response.body().getId());
                setTVShowCast(response.body().getId());
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {}
        });
    }

    private void setAge(String dateOfBirthString) {
        if (dateOfBirthString != null && !dateOfBirthString.trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date releaseDate = sdf1.parse(dateOfBirthString);
                cast_age.setText((Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(sdf2.format(releaseDate))) + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void setMovieCast(Integer personId) {
        ApiInterface apiService = ApiClient.getMovieApi();
        mMovieCastsOfPersonsCall = apiService.getMovieCastsOfPerson(personId, Constants.API_KEY);
        mMovieCastsOfPersonsCall.enqueue(new Callback<MovieCastsOfPersonResponse>() {
            @Override
            public void onResponse(Call<MovieCastsOfPersonResponse> call, Response<MovieCastsOfPersonResponse> response) {
                if (!response.isSuccessful()) {
                    mMovieCastsOfPersonsCall = call.clone();
                    mMovieCastsOfPersonsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getCasts() == null) return;

                for (MovieCastOfPerson movieCastOfPerson : response.body().getCasts()) {
                    if (movieCastOfPerson == null) return;
                    if (movieCastOfPerson.getTitle() != null && movieCastOfPerson.getPosterPath() != null) {
                        cast_movie_heading.setVisibility(View.VISIBLE);
                        mMovieCastOfPersons.add(movieCastOfPerson);
                    }
                }
                mMovieCastsOfPersonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieCastsOfPersonResponse> call, Throwable t) {

            }
        });
    }

    private void setTVShowCast(Integer personId) {
        ApiInterface apiService = ApiClient.getMovieApi();
        mTVCastsOfPersonsCall = apiService.getTVCastsOfPerson(personId, Constants.API_KEY);
        mTVCastsOfPersonsCall.enqueue(new Callback<SeriesCastsOfPersonResponse>() {
            @Override
            public void onResponse(Call<SeriesCastsOfPersonResponse> call, Response<SeriesCastsOfPersonResponse> response) {
                if (!response.isSuccessful()) {
                    mTVCastsOfPersonsCall = call.clone();
                    mTVCastsOfPersonsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getCasts() == null) return;

                for (SeriesCastOfPerson seriesCastOfPerson : response.body().getCasts()) {
                    if (seriesCastOfPerson == null) return;
                    if (seriesCastOfPerson.getName() != null && seriesCastOfPerson.getPosterPath() != null) {
                        cast_tv_heading.setVisibility(View.VISIBLE);
                        mSeriesCastOfPeople.add(seriesCastOfPerson);
                    }
                }
                mTVCastsOfPersonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SeriesCastsOfPersonResponse> call, Throwable t) {}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}