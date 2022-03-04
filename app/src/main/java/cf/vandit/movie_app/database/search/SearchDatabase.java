package cf.vandit.movie_app.database.search;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RecentSearch.class}, version = 1, exportSchema = false)
public abstract class SearchDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String SEARCH_DATABASE = "recentSearches";
    private static SearchDatabase sInstance;

    public static SearchDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        SearchDatabase.class,
                        SearchDatabase.SEARCH_DATABASE)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract SearchDao searchDao();
}
