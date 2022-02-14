package cf.vandit.movie_app.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.database.movies.FavMovie;
import cf.vandit.movie_app.database.movies.MovieDatabase;
import cf.vandit.movie_app.database.series.FavSeries;
import cf.vandit.movie_app.database.series.SeriesDatabase;
import cf.vandit.movie_app.network.movie.MovieBrief;
import cf.vandit.movie_app.network.series.SeriesBrief;

public class Favourite {
    public static boolean isFavMovie(Context context, Integer movieId){
        if(movieId == null) return false;
        FavMovie f = MovieDatabase.getInstance(context).movieDao().getMovieById(movieId);
        return f != null;
    }

    public static boolean isFavSeries(Context context, Integer seriesId){
        if(seriesId == null) return false;
        FavSeries f = SeriesDatabase.getInstance(context).seriesDao().getSeriesById(seriesId);
        return f != null;
    }
}
