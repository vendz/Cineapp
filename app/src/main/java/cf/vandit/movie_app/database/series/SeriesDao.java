package cf.vandit.movie_app.database.series;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SeriesDao {

    @Query("SELECT * FROM favSeries")
    LiveData<List<FavSeries>> getAllFavSeries();

    @Query("SELECT * FROM favSeries WHERE series_tmdb_id = :id")
    FavSeries getSeriesById(int id);

    @Query("DELETE FROM favSeries WHERE series_tmdb_id = :id")
    void deleteSeriesById(int id);

    @Insert(onConflict = REPLACE)
    void insertSeries(FavSeries favSeries);

    @Delete
    void deleteSeries(FavSeries favSeries);
}
