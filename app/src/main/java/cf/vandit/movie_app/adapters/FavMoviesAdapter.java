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
import cf.vandit.movie_app.activities.MovieDetailsActivity;
import cf.vandit.movie_app.database.FavMovie;
import cf.vandit.movie_app.utils.Constants;

public class FavMoviesAdapter extends RecyclerView.Adapter<FavMoviesAdapter.ViewHolder> {
    private List<FavMovie> mMovies;
    private Context mContext;

    public FavMoviesAdapter(List<FavMovie> mMovies, Context mContext) {
        this.mMovies = mMovies;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FavMoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavMoviesAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.small_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavMoviesAdapter.ViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mMovies.get(position).getPoster_path())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.movie_imageView);

        if (mMovies.get(position).getName() != null)
            holder.movie_title.setText(mMovies.get(position).getName());
        else
            holder.movie_title.setText("");
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
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

            movie_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                    intent.putExtra("movie_id", mMovies.get(getAdapterPosition()).getMovie_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
