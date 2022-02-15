package cf.vandit.movie_app.database.movies;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM favMovies")
    LiveData<List<FavMovie>> getAllFavMovies();

    @Query("SELECT * FROM favMovies WHERE movie_id = :id")
    FavMovie getMovieById(int id);

    @Query("DELETE FROM favMovies WHERE movie_id = :id")
    void deleteMovieById(int id);

    @Insert(onConflict = REPLACE)
    void insertMovie(FavMovie favMovie);

    @Delete
    void deleteMovie(FavMovie favMovie);
}
