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
import cf.vandit.movie_app.network.series.SeriesBrief;
import cf.vandit.movie_app.utils.Constants;

public class SeriesBriefSmallAdapter extends RecyclerView.Adapter<SeriesBriefSmallAdapter.TVShowViewHolder> {
    private List<SeriesBrief> mSeries;
    private Context mContext;

    public SeriesBriefSmallAdapter(List<SeriesBrief> mSeries, Context mContext) {
        this.mSeries = mSeries;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SeriesBriefSmallAdapter.TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeriesBriefSmallAdapter.TVShowViewHolder(LayoutInflater.from(mContext).inflate(R.layout.small_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesBriefSmallAdapter.TVShowViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mSeries.get(position).getPosterPath())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.series_imageView);

        if (mSeries.get(position).getName() != null)
            holder.series_title.setText(mSeries.get(position).getName());
        else
            holder.series_title.setText("");
    }

    @Override
    public int getItemCount() {
        return mSeries.size();
    }

    public class TVShowViewHolder extends RecyclerView.ViewHolder {
        public ImageView series_imageView;
        public TextView series_title;
        public CardView series_cardView;

        public TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            series_imageView = itemView.findViewById(R.id.image_view_show_card);
            series_title = itemView.findViewById(R.id.text_view_title_show_card);
            series_cardView = itemView.findViewById(R.id.card_view_show_card);

            series_imageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            series_imageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            series_cardView.setOnClickListener(new View.OnClickListener() {
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
