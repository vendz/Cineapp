package cf.vandit.movie_app.network.series;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeriesExternalIdsResponse {
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("freebase_mid")
    @Expose
    private Object freebaseMid;
    @SerializedName("freebase_id")
    @Expose
    private Object freebaseId;
    @SerializedName("tvdb_id")
    @Expose
    private Integer tvdbId;
    @SerializedName("tvrage_id")
    @Expose
    private Object tvrageId;
    @SerializedName("facebook_id")
    @Expose
    private String facebookId;
    @SerializedName("instagram_id")
    @Expose
    private String instagramId;
    @SerializedName("twitter_id")
    @Expose
    private String twitterId;

    public SeriesExternalIdsResponse(String imdbId, Object freebaseMid, Object freebaseId, Integer tvdbId, Object tvrageId, String facebookId, String instagramId, String twitterId) {
        this.imdbId = imdbId;
        this.freebaseMid = freebaseMid;
        this.freebaseId = freebaseId;
        this.tvdbId = tvdbId;
        this.tvrageId = tvrageId;
        this.facebookId = facebookId;
        this.instagramId = instagramId;
        this.twitterId = twitterId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Object getFreebaseMid() {
        return freebaseMid;
    }

    public void setFreebaseMid(Object freebaseMid) {
        this.freebaseMid = freebaseMid;
    }

    public Object getFreebaseId() {
        return freebaseId;
    }

    public void setFreebaseId(Object freebaseId) {
        this.freebaseId = freebaseId;
    }

    public Integer getTvdbId() {
        return tvdbId;
    }

    public void setTvdbId(Integer tvdbId) {
        this.tvdbId = tvdbId;
    }

    public Object getTvrageId() {
        return tvrageId;
    }

    public void setTvrageId(Object tvrageId) {
        this.tvrageId = tvrageId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getInstagramId() {
        return instagramId;
    }

    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }
}
