package cf.vandit.movie_app.database.series;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favSeries")
public class FavSeries {
    @PrimaryKey(autoGenerate = true)
    private int series_id;

    @ColumnInfo(name = "series_tmdb_id")
    private Integer series_tmdb_id;

    @ColumnInfo(name = "series_poster_path")
    private String series_poster_path;

    @ColumnInfo(name = "series_name")
    private String series_name;

    public FavSeries(Integer series_id, int series_tmdb_id, String series_poster_path, String series_name) {
        this.series_id = series_id;
        this.series_tmdb_id = series_tmdb_id;
        this.series_poster_path = series_poster_path;
        this.series_name = series_name;
    }

    @Ignore
    public FavSeries(Integer series_tmdb_id, String series_poster_path, String series_name) {
        this.series_tmdb_id = series_tmdb_id;
        this.series_poster_path = series_poster_path;
        this.series_name = series_name;
    }

    public int getSeries_id() {
        return series_id;
    }

    public void setSeries_id(int series_id) {
        this.series_id = series_id;
    }

    public Integer getSeries_tmdb_id() {
        return series_tmdb_id;
    }

    public void setSeries_tmdb_id(Integer series_tmdb_id) {
        this.series_tmdb_id = series_tmdb_id;
    }

    public String getSeries_poster_path() {
        return series_poster_path;
    }

    public void setSeries_poster_path(String series_poster_path) {
        this.series_poster_path = series_poster_path;
    }

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }
}
