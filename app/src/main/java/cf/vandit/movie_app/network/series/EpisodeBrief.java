package cf.vandit.movie_app.network.series;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EpisodeBrief {
    @SerializedName("air_date")
    private String airDate;
    @SerializedName("episode_number")
    private Integer episodeNumber;
    @SerializedName("crew")
    private List<Crew> crew;
    @SerializedName("guest_stars")
    private List<GuestStar> guestStars;
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("overview")
    private String overview;
    @SerializedName("production_code")
    private String productionCode;
    @SerializedName("season_number")
    private Integer seasonNumber;
    @SerializedName("still_path")
    private Object stillPath;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private Integer voteCount;

    public EpisodeBrief(String airDate, Integer episodeNumber, List<Crew> crew, List<GuestStar> guestStars, Integer id, String name, String overview, String productionCode, Integer seasonNumber, Object stillPath, Double voteAverage, Integer voteCount) {
        this.airDate = airDate;
        this.episodeNumber = episodeNumber;
        this.crew = crew;
        this.guestStars = guestStars;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.productionCode = productionCode;
        this.seasonNumber = seasonNumber;
        this.stillPath = stillPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public List<GuestStar> getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(List<GuestStar> guestStars) {
        this.guestStars = guestStars;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Object getStillPath() {
        return stillPath;
    }

    public void setStillPath(Object stillPath) {
        this.stillPath = stillPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
