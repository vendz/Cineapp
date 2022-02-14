package cf.vandit.movie_app.database.movies;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favMovies")
public class FavMovie {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "movie_id")
    private Integer movie_id;

    @ColumnInfo(name = "poster_path")
    private String poster_path;

    @ColumnInfo(name = "name")
    private String name;

    public FavMovie(int id, Integer movie_id, String name, String poster_path) {
        this.id = id;
        this.movie_id = movie_id;
        this.poster_path = poster_path;
        this.name = name;
    }

    @Ignore
    public FavMovie(Integer movie_id, String name, String poster_path) {
        this.movie_id = movie_id;
        this.poster_path = poster_path;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
