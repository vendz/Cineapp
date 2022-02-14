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

import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.SeriesDetailsActivity;
import cf.vandit.movie_app.database.series.FavSeries;
import cf.vandit.movie_app.utils.Constants;

public class FavSeriesAdapter extends RecyclerView.Adapter<FavSeriesAdapter.ViewHolder> {
    private List<FavSeries> mSeries;
    private Context mContext;

    public FavSeriesAdapter(List<FavSeries> mSeries, Context mContext) {
        this.mSeries = mSeries;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FavSeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavSeriesAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.small_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mSeries.get(position).getSeries_poster_path())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.movie_imageView);

        if (mSeries.get(position).getSeries_name() != null)
            holder.movie_title.setText(mSeries.get(position).getSeries_name());
        else
            holder.movie_title.setText("");
    }

    @Override
    public int getItemCount() {
        return mSeries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView movie_imageView;
        public TextView movie_title;
        public CardView movie_cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_imageView = itemView.findViewById(R.id.image_view_show_card);
            movie_title = itemView.findViewById(R.id.text_view_title_show_card);
            movie_cardView = itemView.findViewById(R.id.card_view_show_card);

            movie_imageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            movie_imageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            movie_cardView.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, SeriesDetailsActivity.class);
                intent.putExtra("series_id", mSeries.get(getAdapterPosition()).getSeries_tmdb_id());
                mContext.startActivity(intent);
            });
        }
    }
}
