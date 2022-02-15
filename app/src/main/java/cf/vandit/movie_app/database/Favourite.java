package cf.vandit.movie_app.database;

import android.content.Context;

import cf.vandit.movie_app.database.movies.FavMovie;
import cf.vandit.movie_app.database.movies.MovieDatabase;
import cf.vandit.movie_app.database.series.FavSeries;
import cf.vandit.movie_app.database.series.SeriesDatabase;

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
