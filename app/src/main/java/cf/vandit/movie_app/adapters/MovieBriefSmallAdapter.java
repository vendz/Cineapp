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

import cf.vandit.movie_app.activities.MovieDetailsActivity;
import cf.vandit.movie_app.R;
import cf.vandit.movie_app.network.movie.MovieBrief;
import cf.vandit.movie_app.utils.Constants;

public class MovieBriefSmallAdapter extends RecyclerView.Adapter<MovieBriefSmallAdapter.MovieViewHolder> {
    private List<MovieBrief> mMovies;
    private Context mContext;

    public MovieBriefSmallAdapter(List<MovieBrief> mMovies, Context mContext) {
        this.mMovies = mMovies;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MovieBriefSmallAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieBriefSmallAdapter.MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.small_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieBriefSmallAdapter.MovieViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mMovies.get(position).getPosterPath())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.movie_imageView);

        if (mMovies.get(position).getTitle() != null)
            holder.movie_title.setText(mMovies.get(position).getTitle());
        else
            holder.movie_title.setText("");
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView movie_imageView;
        public TextView movie_title;
        public CardView movie_cardView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_imageView = itemView.findViewById(R.id.image_view_show_card);
            movie_title = itemView.findViewById(R.id.text_view_title_show_card);
            movie_cardView = itemView.findViewById(R.id.card_view_show_card);

            movie_imageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            movie_imageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            movie_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                    intent.putExtra("movie_id", mMovies.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
