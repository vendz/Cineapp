package cf.vandit.movie_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.adapters.SearchResultsAdapter;
import cf.vandit.movie_app.network.search.SearchAsyncTaskLoader;
import cf.vandit.movie_app.network.search.SearchResponse;
import cf.vandit.movie_app.network.search.SearchResult;

public class SearchResultsActivity extends AppCompatActivity {

    private String mQuery;

    private RecyclerView mSearchResultsRecyclerView;
    private List<SearchResult> mSearchResults;
    private SearchResultsAdapter mSearchResultsAdapter;

    private ProgressBar progressBar;
    private TextView mEmptyTextView;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Toolbar toolbar = findViewById(R.id.view_search_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receivedIntent = getIntent();
        mQuery = receivedIntent.getStringExtra("query");
        if (mQuery == null || mQuery.trim().isEmpty()) finish();
        setTitle(mQuery.trim());

        mEmptyTextView = findViewById(R.id.text_view_empty_search);
        progressBar = findViewById(R.id.search_progressBar);

        mSearchResultsRecyclerView = findViewById(R.id.recycler_view_search);
        mSearchResults = new ArrayList<>();
        mSearchResultsAdapter = new SearchResultsAdapter(SearchResultsActivity.this, mSearchResults);
        mSearchResultsRecyclerView.setAdapter(mSearchResultsAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchResultsActivity.this, LinearLayoutManager.VERTICAL, false);
        mSearchResultsRecyclerView.setLayoutManager(linearLayoutManager);

        mSearchResultsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadSearchResults();
                    loading = true;
                }

            }
        });
        loadSearchResults();
    }

    private void loadSearchResults() {
        if (pagesOver) return;

        getLoaderManager().initLoader(presentPage, null, new LoaderManager.LoaderCallbacks<SearchResponse>() {

            @Override
            public Loader<SearchResponse> onCreateLoader(int i, Bundle bundle) {
                return new SearchAsyncTaskLoader(SearchResultsActivity.this, mQuery, String.valueOf(presentPage));
            }

            @Override
            public void onLoadFinished(Loader<SearchResponse> loader, SearchResponse searchResponse) {

                progressBar.setVisibility(View.GONE);
                if (searchResponse == null) return;
                if (searchResponse.getResults() == null) return;

                for (SearchResult searchResult : searchResponse.getResults()) {
                    if (searchResult != null)
                        mSearchResults.add(searchResult);
                }
                mSearchResultsAdapter.notifyDataSetChanged();
                if (mSearchResults.isEmpty()) mEmptyTextView.setVisibility(View.VISIBLE);
                if (searchResponse.getPage() == searchResponse.getTotalPages())
                    pagesOver = true;
                else
                    presentPage++;

            }

            @Override
            public void onLoaderReset(Loader<SearchResponse> loader) {

            }
        }).forceLoad();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}