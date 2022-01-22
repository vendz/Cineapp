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
import cf.vandit.movie_app.network.movie.MovieCastOfPerson;
import cf.vandit.movie_app.utils.Constants;

public class MovieCastsOfPersonAdapter extends RecyclerView.Adapter<MovieCastsOfPersonAdapter.MovieViewHolder> {
    private Context mContext;
    private List<MovieCastOfPerson> mMovieCasts;

    public MovieCastsOfPersonAdapter(Context mContext, List<MovieCastOfPerson> mMovieCasts) {
        this.mContext = mContext;
        this.mMovieCasts = mMovieCasts;
    }

    @NonNull
    @Override
    public MovieCastsOfPersonAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.casts_movie_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCastsOfPersonAdapter.MovieViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext()).load(Constants.IMAGE_LOADING_BASE_URL_342 + mMovieCasts.get(position).getPosterPath())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.moviePosterImageView);

        if (mMovieCasts.get(position).getTitle() != null)
            holder.movieTitleTextView.setText(mMovieCasts.get(position).getTitle());
        else
            holder.movieTitleTextView.setText("");

        if (mMovieCasts.get(position).getCharacter() != null && !mMovieCasts.get(position).getCharacter().trim().isEmpty())
            holder.castCharacterTextView.setText("as " + mMovieCasts.get(position).getCharacter());
        else
            holder.castCharacterTextView.setText("");
    }

    @Override
    public int getItemCount() {
        return mMovieCasts.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public CardView movieCard;
        public ImageView moviePosterImageView;
        public TextView movieTitleTextView;
        public TextView castCharacterTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movieCard = (CardView) itemView.findViewById(R.id.card_view_show_cast);
            moviePosterImageView = (ImageView) itemView.findViewById(R.id.movie_cast_imageView);
            movieTitleTextView = (TextView) itemView.findViewById(R.id.movie_cast_title);
            castCharacterTextView = (TextView) itemView.findViewById(R.id.movie_cast_character);

            moviePosterImageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            moviePosterImageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            movieCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                    intent.putExtra("movie_id", mMovieCasts.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
