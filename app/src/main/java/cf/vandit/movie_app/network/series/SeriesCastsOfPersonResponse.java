package cf.vandit.movie_app.network.series;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesCastsOfPersonResponse {
    @SerializedName("cast")
    private List<SeriesCastOfPerson> casts;
    //crew missing
    @SerializedName("id")
    private Integer id;

    public SeriesCastsOfPersonResponse(List<SeriesCastOfPerson> casts, Integer id) {
        this.casts = casts;
        this.id = id;
    }

    public List<SeriesCastOfPerson> getCasts() {
        return casts;
    }

    public void setCasts(List<SeriesCastOfPerson> casts) {
        this.casts = casts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
