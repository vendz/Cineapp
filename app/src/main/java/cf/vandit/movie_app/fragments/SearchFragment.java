package cf.vandit.movie_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.SearchResultsActivity;

public class SearchFragment extends Fragment {

    private EditText searchView;
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

        search_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                intent.putExtra("query", searchView.getText().toString());
                startActivity(intent);
            }
        });
    }
}