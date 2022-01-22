package cf.vandit.movie_app.network.series;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarSeriesResponse {
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<SeriesBrief> results;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public SimilarSeriesResponse(Integer page, List<SeriesBrief> results, Integer totalPages, Integer totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
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

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
