package cf.vandit.movie_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.ViewAllMoviesActivity;
import cf.vandit.movie_app.network.movie.MovieBrief;
import cf.vandit.movie_app.utils.Constants;
import cf.vandit.movie_app.utils.NestedRecViewModel;

public class MoviesNestedRecViewAdapter extends RecyclerView.Adapter<MoviesNestedRecViewAdapter.ViewHolder> {
    private List<NestedRecViewModel> mNestedList;
    private Context mContext;

    public MoviesNestedRecViewAdapter(List<NestedRecViewModel> mNestedList, Context mContext) {
        this.mNestedList = mNestedList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MoviesNestedRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesNestedRecViewAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.nested_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesNestedRecViewAdapter.ViewHolder holder, int position) {
        switch (mNestedList.get(position).getmGenreId()){
            case 28:
                holder.nested_heading.setText("Action");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.ACTION_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 12:
                holder.nested_heading.setText("Adventure");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.ADVENTURE_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 16:
                holder.nested_heading.setText("Animation");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.ANIMATION_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 35:
                holder.nested_heading.setText("Comedy");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.COMEDY_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 80:
                holder.nested_heading.setText("Crime");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.CRIME_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 99:
                holder.nested_heading.setText("Documentary");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.DOCUMENTARY_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 18:
                holder.nested_heading.setText("Drama");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.DRAMA_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 10751:
                holder.nested_heading.setText("Family");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.FAMILY_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 14:
                holder.nested_heading.setText("Fantasy");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.FANTASY_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 36:
                holder.nested_heading.setText("History");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.HISTORY_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 27:
                holder.nested_heading.setText("Horror");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.HORROR_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 10402:
                holder.nested_heading.setText("Music");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.MUSIC_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 9648:
                holder.nested_heading.setText("Mystery");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.MYSTERY_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 10749:
                holder.nested_heading.setText("Romance");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.ROMANCE_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 878:
                holder.nested_heading.setText("Science Fiction");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.SCIFI_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 10770:
                holder.nested_heading.setText("TV Movie");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.TV_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 53:
                holder.nested_heading.setText("Thriller");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.THRILLER_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 10752:
                holder.nested_heading.setText("War");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.WAR_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
            case 37:
                holder.nested_heading.setText("Western");
                holder.nested_view_all.setOnClickListener(view -> {
                    Intent intent = new Intent(mContext, ViewAllMoviesActivity.class);
                    intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.WESTERN_MOVIES_TYPE);
                    mContext.startActivity(intent);
                });
                break;
        }

        setMovieRecView(holder.nested_recView, mNestedList.get(position).getmMovies());
    }

    @Override
    public int getItemCount() {
        return mNestedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout nested_heading_layout;
        public TextView nested_heading;
        public TextView nested_view_all;
        public RecyclerView nested_recView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nested_heading_layout = itemView.findViewById(R.id.nested_constraint_layout);
            nested_heading = itemView.findViewById(R.id.nested_heading);
            nested_view_all = itemView.findViewById(R.id.nested_view_all);
            nested_recView = itemView.findViewById(R.id.nested_recView);
        }
    }

    private void setMovieRecView(RecyclerView recyclerView, List<MovieBrief> mMovies){
        MovieBriefSmallAdapter mMoviesAdapter = new MovieBriefSmallAdapter(mMovies, mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mMoviesAdapter);
    }
}
