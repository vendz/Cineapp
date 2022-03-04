package cf.vandit.movie_app.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.SearchResultsActivity;
import cf.vandit.movie_app.adapters.RecentSearchAdapter;
import cf.vandit.movie_app.database.DatabaseHelper;
import cf.vandit.movie_app.database.search.RecentSearch;
import cf.vandit.movie_app.database.search.SearchDatabase;

public class SearchFragment extends Fragment {

    private EditText searchView;
    private String query;
    private FloatingActionButton search_fab;
    private TextView search_recents;
    private RecyclerView search_recyclerView_recents;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);
        search_fab = view.findViewById(R.id.search_fab);
        search_recents = view.findViewById(R.id.search_recents_textView);
        search_recyclerView_recents = view.findViewById(R.id.search_recView_recents);

        LiveData<List<RecentSearch>> mRecentSearches = SearchDatabase.getInstance(requireContext())
                .searchDao()
                .getAllRecentSearch();

        mRecentSearches.observe(requireActivity(), new Observer<List<RecentSearch>>() {
            @Override
            public void onChanged(List<RecentSearch> recentSearches) {
                Collections.reverse(recentSearches);
                RecentSearchAdapter recentSearchAdapter = new RecentSearchAdapter(recentSearches, getActivity());
                search_recyclerView_recents.setAdapter(recentSearchAdapter);
                search_recyclerView_recents.setLayoutManager(new LinearLayoutManager(getContext()));

                if(recentSearches.isEmpty()){
                    search_recents.setVisibility(View.INVISIBLE);
                } else {
                    search_recents.setVisibility(View.VISIBLE);
                }
            }
        });

        search_fab.setOnClickListener(new View.OnClickListener() {

            class SaveSearch extends AsyncTask<Void, Void, Void>{
                @Override
                protected Void doInBackground(Void... voids) {
                    query =  searchView.getText().toString().trim().toLowerCase();
                    if(!DatabaseHelper.isRecentlySearched(requireContext(),query)){
                        RecentSearch recentSearch = new RecentSearch(query);
                        SearchDatabase.getInstance(requireContext())
                                .searchDao()
                                .insertSearch(recentSearch);
                    }

                    return null;
                }
            }

            @Override
            public void onClick(View view) {
                query =  searchView.getText().toString().trim().toLowerCase();
                SaveSearch saveSearch = new SaveSearch();
                saveSearch.execute();

                Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
            }
        });
    }
}