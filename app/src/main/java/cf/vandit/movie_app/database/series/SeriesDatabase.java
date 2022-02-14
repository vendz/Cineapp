package cf.vandit.movie_app.database.series;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavSeries.class}, version = 1, exportSchema = false)
public abstract class SeriesDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String SERIES_DATABASE = "favSeries";
    private static SeriesDatabase sInstance;

    public static SeriesDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        SeriesDatabase.class,
                        SeriesDatabase.SERIES_DATABASE)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract SeriesDao seriesDao();
}
