package cf.vandit.movie_app.network.videos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailersResponse {
    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private List<Trailer> videos;

    public TrailersResponse(Integer id, List<Trailer> videos) {
        this.id = id;
        this.videos = videos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getVideos() {
        return videos;
    }

    public void setVideos(List<Trailer> videos) {
        this.videos = videos;
    }
}
