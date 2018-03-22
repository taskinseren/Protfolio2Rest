package dhbw.pojo.result.search;

import java.util.List;

public class SearchResult {

    private String searchTerm;
    private String searchCategory;
    private List<SearchResultList> results;

    public SearchResult(String searchTerm, String searchCategory, List<SearchResultList> results) {
        this.searchTerm = searchTerm;
        this.searchCategory = searchCategory;
        this.results = results;
    }

    public SearchResult() {
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    public List<SearchResultList> getResults() {
        return results;
    }

    public void setResults(List<SearchResultList> results) {
        this.results = results;
    }
}
