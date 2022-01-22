package cf.vandit.movie_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.CastActivity;
import cf.vandit.movie_app.activities.MovieDetailsActivity;
import cf.vandit.movie_app.activities.SeriesDetailsActivity;
import cf.vandit.movie_app.network.search.SearchResult;
import cf.vandit.movie_app.utils.Constants;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ResultViewHolder> {

    private Context mContext;
    private List<SearchResult> mSearchResults;

    public SearchResultsAdapter(Context mContext, List<SearchResult> mSearchResults) {
        this.mContext = mContext;
        this.mSearchResults = mSearchResults;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(final ResultViewHolder holder, int position) {

        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mSearchResults.get(position).getPosterPath())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.posterImageView);

        if (mSearchResults.get(position).getName() != null && !mSearchResults.get(position).getName().trim().isEmpty())
            holder.nameTextView.setText(mSearchResults.get(position).getName());
        else
            holder.nameTextView.setText("");

        if (mSearchResults.get(position).getMediaType() != null && mSearchResults.get(position).getMediaType().equals("movie"))
            holder.mediaTypeTextView.setText("Movies");
        else if (mSearchResults.get(position).getMediaType() != null && mSearchResults.get(position).getMediaType().equals("tv"))
            holder.mediaTypeTextView.setText("Series");
        else if (mSearchResults.get(position).getMediaType() != null && mSearchResults.get(position).getMediaType().equals("person"))
            holder.mediaTypeTextView.setText("Person");
        else
            holder.mediaTypeTextView.setText("");

        if (mSearchResults.get(position).getOverview() != null && !mSearchResults.get(position).getOverview().trim().isEmpty())
            holder.overviewTextView.setText(mSearchResults.get(position).getOverview());
        else
            holder.overviewTextView.setText("");

        if (mSearchResults.get(position).getReleaseDate() != null && !mSearchResults.get(position).getReleaseDate().trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date releaseDate = sdf1.parse(mSearchResults.get(position).getReleaseDate());
                holder.yearTextView.setText(sdf2.format(releaseDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.yearTextView.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mSearchResults.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView posterImageView;
        public TextView nameTextView;
        public TextView mediaTypeTextView;
        public TextView overviewTextView;
        public TextView yearTextView;

        public ResultViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_search);
            posterImageView = itemView.findViewById(R.id.image_view_poster_search);
            nameTextView = itemView.findViewById(R.id.text_view_name_search);
            mediaTypeTextView = itemView.findViewById(R.id.text_view_media_type_search);
            overviewTextView = itemView.findViewById(R.id.text_view_overview_search);
            yearTextView = itemView.findViewById(R.id.text_view_year_search);

            posterImageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            posterImageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSearchResults.get(getAdapterPosition()).getMediaType().equals("movie")) {
                        Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                        intent.putExtra(Constants.MOVIE_ID, mSearchResults.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    } else if (mSearchResults.get(getAdapterPosition()).getMediaType().equals("tv")) {
                        Intent intent = new Intent(mContext, SeriesDetailsActivity.class);
                        intent.putExtra(Constants.SERIES_ID, mSearchResults.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    } else if (mSearchResults.get(getAdapterPosition()).getMediaType().equals("person")) {
                        Intent intent = new Intent(mContext, CastActivity.class);
                        intent.putExtra(Constants.PERSON_ID, mSearchResults.get(getAdapterPosition()).getId());
                        mContext.startActivity(intent);
                    }
                }
            });

        }
    }
}