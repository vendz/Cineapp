package cf.vandit.movie_app.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.database.DatabaseHelper;
import cf.vandit.movie_app.network.movie.MovieBrief;
import cf.vandit.movie_app.network.series.SeriesBrief;
import cf.vandit.movie_app.utils.QueryModelClass;

public class Favourite {
    public static void addMovieToFav(Context context, Integer movieId, String posterPath, String name) {
        if (movieId == null) return;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        if (!isMovieFav(context, movieId)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.MOVIE_ID, movieId);
            contentValues.put(DatabaseHelper.POSTER_PATH, posterPath);
            contentValues.put(DatabaseHelper.NAME, name);
            database.insert(DatabaseHelper.FAV_MOVIES_TABLE_NAME, null, contentValues);
        }
        database.close();
    }

    public static void removeMovieFromFav(Context context, Integer movieId) {
        if (movieId == null) return;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        if (isMovieFav(context, movieId)) {
            database.delete(DatabaseHelper.FAV_MOVIES_TABLE_NAME, DatabaseHelper.MOVIE_ID + " = " + movieId, null);
        }
        database.close();
    }

    public static boolean isMovieFav(Context context, Integer movieId) {
        if (movieId == null) return false;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        boolean isMovieFav;
        Cursor cursor = database.query(DatabaseHelper.FAV_MOVIES_TABLE_NAME, null, DatabaseHelper.MOVIE_ID + " = " + movieId, null, null, null, null);
        if (cursor.getCount() == 1)
            isMovieFav = true;
        else
            isMovieFav = false;

        cursor.close();
        database.close();
        return isMovieFav;
    }

    public static List<MovieBrief> getFavMovieBriefs(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        List<MovieBrief> favMovies = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.FAV_MOVIES_TABLE_NAME, null, null, null, null, null, DatabaseHelper.ID + " DESC");
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int movieId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MOVIE_ID));
            @SuppressLint("Range") String posterPath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.POSTER_PATH));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
            favMovies.add(new MovieBrief(null, movieId, null, null, name, null, posterPath, null, null, null, null, null, null, null));
        }
        cursor.close();
        database.close();
        return favMovies;
    }

    //TV SHOWS

    public static void addTVShowToFav(Context context, Integer tvShowId, String posterPath, String name) {
        if (tvShowId == null) return;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        if (!isTVShowFav(context, tvShowId)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.TV_SHOW_ID, tvShowId);
            contentValues.put(DatabaseHelper.POSTER_PATH, posterPath);
            contentValues.put(DatabaseHelper.NAME, name);
            database.insert(DatabaseHelper.FAV_TV_SHOWS_TABLE_NAME, null, contentValues);
        }
        database.close();
    }

    public static void removeTVShowFromFav(Context context, Integer tvShowId) {
        if (tvShowId == null) return;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        if (isTVShowFav(context, tvShowId)) {
            database.delete(DatabaseHelper.FAV_TV_SHOWS_TABLE_NAME, DatabaseHelper.TV_SHOW_ID + " = " + tvShowId, null);
        }
        database.close();
    }

    public static boolean isTVShowFav(Context context, Integer tvShowId) {
        if (tvShowId == null) return false;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        boolean isTVShowFav;
        Cursor cursor = database.query(DatabaseHelper.FAV_TV_SHOWS_TABLE_NAME, null, DatabaseHelper.TV_SHOW_ID + " = " + tvShowId, null, null, null, null);
        if (cursor.getCount() == 1)
            isTVShowFav = true;
        else
            isTVShowFav = false;

        cursor.close();
        database.close();
        return isTVShowFav;
    }

    public static List<SeriesBrief> getFavTVShowBriefs(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        List<SeriesBrief> favTVShows = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.FAV_TV_SHOWS_TABLE_NAME, null, null, null, null, null, DatabaseHelper.ID + " DESC");
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int tvShowId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TV_SHOW_ID));
            @SuppressLint("Range") String posterPath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.POSTER_PATH));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
            favTVShows.add(new SeriesBrief(null, tvShowId, name, null, null, posterPath, null, null, null, null, null, null, null));
        }
        cursor.close();
        database.close();
        return favTVShows;
    }

    // Search

    public static void insertQuery(Context context, String query){
        if (query == null) return;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.QUERY, query);
        database.insert(DatabaseHelper.RECENT_SEARCHES_TABLE, null, contentValues);
        database.close();
    }

//    public static void deleteQuery(Context context, String query){
//        if (query == null) return;
//        DatabaseHelper databaseHelper = new DatabaseHelper(context);
//        SQLiteDatabase database = databaseHelper.getWritableDatabase();
//
//        database.delete(DatabaseHelper.RECENT_SEARCHES_TABLE, DatabaseHelper.QUERY + " = " + query, null);
//        database.close();
//    }

    public static List<QueryModelClass> getQueries(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        List<QueryModelClass> queries = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.RECENT_SEARCHES_TABLE, null, null, null, null, null, DatabaseHelper.ID + " DESC");
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String query = cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUERY));
            queries.add(new QueryModelClass(query));
        }
        cursor.close();
        database.close();
        return queries;
    }
}
