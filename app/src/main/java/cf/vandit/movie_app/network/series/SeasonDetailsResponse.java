package cf.vandit.movie_app.network.series;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonDetailsResponse {
    @SerializedName("_id")
    private String _id;
    @SerializedName("air_date")
    private String airDate;
    @SerializedName("episodes")
    private List<EpisodeBrief> episodes;
    @SerializedName("name")
    private String name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("id")
    private Integer id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("season_number")
    private Integer seasonNumber;
    @SerializedName("external_ids")
    private SeriesExternalIds externalIds;

    public SeasonDetailsResponse(String _id, String airDate, List<EpisodeBrief> episodes, String name, String overview, Integer id, String posterPath, Integer seasonNumber, SeriesExternalIds externalIds) {
        this._id = _id;
        this.airDate = airDate;
        this.episodes = episodes;
        this.name = name;
        this.overview = overview;
        this.id = id;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
        this.externalIds = externalIds;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public List<EpisodeBrief> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeBrief> episodes) {
        this.episodes = episodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public SeriesExternalIds getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(SeriesExternalIds externalIds) {
        this.externalIds = externalIds;
    }
}
