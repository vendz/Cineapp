package cf.vandit.movie_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.SeriesDetailsActivity;
import cf.vandit.movie_app.network.series.SeriesBrief;
import cf.vandit.movie_app.utils.Constants;

public class SeriesCarouselAdapter extends RecyclerView.Adapter<SeriesCarouselAdapter.SeriesViewHolder> {
    private List<SeriesBrief> mSeries;
    private Context mContext;

    public SeriesCarouselAdapter(List<SeriesBrief> mSeries, Context mContext) {
        this.mSeries = mSeries;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SeriesCarouselAdapter.SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeriesCarouselAdapter.SeriesViewHolder(LayoutInflater.from(mContext).inflate(R.layout.carousel_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesCarouselAdapter.SeriesViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_780 + mSeries.get(position).getBackdropPath())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.series_imageView);

        if (mSeries.get(position).getName() != null)
            holder.series_title.setText(mSeries.get(position).getName());
        else
            holder.series_title.setText("");

        if (mSeries.get(position).getPopularity() != null)
            holder.series_rating.setText(String.format("%.1f", mSeries.get(position).getVoteAverage()));
        else
            holder.series_rating.setText("");

//        holder.movie_counter.setText(position + 1 + "/" + mMovies.size());
    }

    @Override
    public int getItemCount() {
        return mSeries.size();
    }

    public class SeriesViewHolder extends RecyclerView.ViewHolder {
        public ImageView series_imageView;
        public TextView series_title;
        public TextView series_rating;
        public CardView series_cardView;
        public FloatingActionButton series_fab;
//        public TextView series_counter;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            series_imageView = itemView.findViewById(R.id.carousel_imageView);
            series_title = itemView.findViewById(R.id.carousel_title);
            series_rating = itemView.findViewById(R.id.carousel_rating);
            series_cardView = itemView.findViewById(R.id.carousel_main_card);
            series_fab = itemView.findViewById(R.id.carousel_play_btn);
//            series_counter = itemView.findViewById(R.id.carousel_counter);

            series_title.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.7);

            series_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SeriesDetailsActivity.class);
                    intent.putExtra("series_id", mSeries.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });

            series_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SeriesDetailsActivity.class);
                    intent.putExtra("series_id", mSeries.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
