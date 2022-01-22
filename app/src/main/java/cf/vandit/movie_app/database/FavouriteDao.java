package cf.vandit.movie_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {
    @Query("SELECT * FROM favmovie")
    List<FavMovie> getAll();

    @Insert
    void insertMovie(FavMovie favMovie);

    @Delete
    void deleteMovie(FavMovie favMovie);
}
