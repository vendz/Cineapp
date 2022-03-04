package cf.vandit.movie_app.database.search;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recentSearches")
public class RecentSearch {
    @PrimaryKey(autoGenerate = true)
    private int search_id;

    @ColumnInfo(name = "search_name")
    private String search_name;

    public RecentSearch(int search_id, String search_name) {
        this.search_id = search_id;
        this.search_name = search_name;
    }

    @Ignore
    public RecentSearch(String search_name) {
        this.search_name = search_name;
    }

    public int getSearch_id() {
        return search_id;
    }

    public void setSearch_id(int search_id) {
        this.search_id = search_id;
    }

    public String getSearch_name() {
        return search_name;
    }

    public void setSearch_name(String search_name) {
        this.search_name = search_name;
    }
}
