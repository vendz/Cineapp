package cf.vandit.movie_app.database.search;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SearchDao {
    @Query("SELECT * FROM recentSearches")
    LiveData<List<RecentSearch>> getAllRecentSearch();

    @Query("SELECT * FROM recentSearches WHERE search_name = :name")
    RecentSearch getSearchesByName(String name);

    @Query("DELETE FROM recentSearches WHERE search_name = :name")
    void deleteSearchesByName(String name);

    @Insert(onConflict = REPLACE)
    void insertSearch(RecentSearch recentSearch);
}
