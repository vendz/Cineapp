package cf.vandit.movie_app.network.series;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesCreditsResponse {
    @SerializedName("cast")
    private List<SeriesCastBrief> casts;
    @SerializedName("crew")
    private List<SeriesCrewBrief> crews;
    @SerializedName("id")
    private Integer id;

    public SeriesCreditsResponse(List<SeriesCastBrief> casts, List<SeriesCrewBrief> crews, Integer id) {
        this.casts = casts;
        this.crews = crews;
        this.id = id;
    }

    public List<SeriesCastBrief> getCasts() {
        return casts;
    }

    public void setCasts(List<SeriesCastBrief> casts) {
        this.casts = casts;
    }

    public List<SeriesCrewBrief> getCrews() {
        return crews;
    }

    public void setCrews(List<SeriesCrewBrief> crews) {
        this.crews = crews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
