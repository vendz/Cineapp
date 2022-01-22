package cf.vandit.movie_app.network.series;

import com.google.gson.annotations.SerializedName;

public class SeriesExternalIds {
    @SerializedName("freebase_mid")
    private Object freebaseMid;
    @SerializedName("freebase_id")
    private Object freebaseId;
    @SerializedName("tvdb_id")
    private Integer tvdbId;
    @SerializedName("tvrage_id")
    private Object tvrageId;

    public SeriesExternalIds(Object freebaseMid, Object freebaseId, Integer tvdbId, Object tvrageId) {
        this.freebaseMid = freebaseMid;
        this.freebaseId = freebaseId;
        this.tvdbId = tvdbId;
        this.tvrageId = tvrageId;
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
}
