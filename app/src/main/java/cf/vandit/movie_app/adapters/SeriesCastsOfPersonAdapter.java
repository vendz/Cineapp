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
import cf.vandit.movie_app.network.series.SeriesCastOfPerson;
import cf.vandit.movie_app.utils.Constants;

public class SeriesCastsOfPersonAdapter extends RecyclerView.Adapter<SeriesCastsOfPersonAdapter.TVShowViewHolder> {
    public Context mContext;
    private List<SeriesCastOfPerson> mSeriesCasts;

    public SeriesCastsOfPersonAdapter(Context mContext, List<SeriesCastOfPerson> mSeriesCasts) {
        this.mContext = mContext;
        this.mSeriesCasts = mSeriesCasts;
    }

    @NonNull
    @Override
    public SeriesCastsOfPersonAdapter.TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TVShowViewHolder(LayoutInflater.from(mContext).inflate(R.layout.casts_series_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesCastsOfPersonAdapter.TVShowViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mSeriesCasts.get(position).getPosterPath())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.tvShowImageView);

        if (mSeriesCasts.get(position).getName() != null)
            holder.tvShowTitle.setText(mSeriesCasts.get(position).getName());
        else
            holder.tvShowTitle.setText("");

        if (mSeriesCasts.get(position).getCharacter() != null && !mSeriesCasts.get(position).getCharacter().trim().isEmpty())
            holder.tvShowCharacter.setText("as " + mSeriesCasts.get(position).getCharacter());
        else
            holder.tvShowCharacter.setText("");
    }

    @Override
    public int getItemCount() {
        return mSeriesCasts.size();
    }

    public class TVShowViewHolder extends RecyclerView.ViewHolder {
        public CardView tvShowCard;
        public ImageView tvShowImageView;
        public TextView tvShowTitle;
        public TextView tvShowCharacter;

        public TVShowViewHolder(@NonNull View itemView) {
            super(itemView);

            tvShowCard = itemView.findViewById(R.id.tv_cast_card);
            tvShowImageView = itemView.findViewById(R.id.tv_cast_imageView);
            tvShowTitle = itemView.findViewById(R.id.tv_cast_title);
            tvShowCharacter = itemView.findViewById(R.id.tv_cast_character);

            tvShowImageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            tvShowImageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            tvShowCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SeriesDetailsActivity.class);
                    intent.putExtra("series_id", mSeriesCasts.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
