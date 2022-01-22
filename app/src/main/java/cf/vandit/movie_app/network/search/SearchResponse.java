package cf.vandit.movie_app.network.search;

import java.util.List;

public class SearchResponse {
    private Integer page;
    private List<SearchResult> results;
    private Integer totalPages;

    public SearchResponse() {}

    public SearchResponse(Integer page, List<SearchResult> results, Integer totalPages) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
