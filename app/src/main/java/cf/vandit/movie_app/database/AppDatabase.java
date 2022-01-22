package cf.vandit.movie_app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavMovie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavouriteDao favouriteDao();
}
