package cf.vandit.movie_app.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    public static final String FAV_MOVIES_TABLE_NAME = "FavouriteMoviesTable";
    public static final String FAV_TV_SHOWS_TABLE_NAME = "FavouriteTVShowsTable";
    public static final String RECENT_SEARCHES_TABLE = "RecentSearchesTable";
    public static final String ID = "id";
    public static final String MOVIE_ID = "movie_id";
    public static final String TV_SHOW_ID = "tv_show_id";
    public static final String POSTER_PATH = "poster_path";
    public static final String NAME = "name";
    public static final String QUERY = "query";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCreateMovieTable = "CREATE TABLE " + FAV_MOVIES_TABLE_NAME + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MOVIE_ID + " INTEGER, "
                + POSTER_PATH + " TEXT, "
                + NAME + " TEXT )";
        String queryCreateTVShowTable = "CREATE TABLE " + FAV_TV_SHOWS_TABLE_NAME + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TV_SHOW_ID + " INTEGER, "
                + POSTER_PATH + " TEXT, "
                + NAME + " TEXT )";
        String queryCreateRecentSearchesTable = "CREATE TABLE " + RECENT_SEARCHES_TABLE + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUERY + "TEXT)";
        sqLiteDatabase.execSQL(queryCreateMovieTable);
        sqLiteDatabase.execSQL(queryCreateTVShowTable);
        sqLiteDatabase.execSQL(queryCreateRecentSearchesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
