package cf.vandit.movie_app.network.series;

import com.google.gson.annotations.SerializedName;

public class Seasons {
    @SerializedName("season_number")
    private Integer season_number;
    @SerializedName("episode_count")
    private Integer episode_count;

    public Seasons(Integer season_number, Integer episode_count) {
        this.season_number = season_number;
        this.episode_count = episode_count;
    }

    public Integer getSeason_number() {
        return season_number;
    }

    public void setSeason_number(Integer season_number) {
        this.season_number = season_number;
    }

    public Integer getEpisode_count() {
        return episode_count;
    }

    public void setEpisode_count(Integer episode_count) {
        this.episode_count = episode_count;
    }
}
