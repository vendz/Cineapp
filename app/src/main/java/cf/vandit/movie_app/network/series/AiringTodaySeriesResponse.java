package cf.vandit.movie_app.network.series;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AiringTodaySeriesResponse {
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<SeriesBrief> results;
    @SerializedName("total_results")
    private Integer totalResults;
    @SerializedName("total_pages")
    private Integer totalPages;

    public AiringTodaySeriesResponse(Integer page, List<SeriesBrief> results, Integer totalResults, Integer totalPages) {
        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<SeriesBrief> getResults() {
        return results;
    }

    public void setResults(List<SeriesBrief> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
