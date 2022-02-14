package cf.vandit.movie_app.database.movies;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavMovie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String MOVIES_DATABASE = "favMovies";
    private static MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class,
                        MovieDatabase.MOVIES_DATABASE)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
