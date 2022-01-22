package cf.vandit.movie_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.CastActivity;
import cf.vandit.movie_app.network.movie.MovieCastBrief;
import cf.vandit.movie_app.utils.Constants;

public class MovieCastsAdapter extends RecyclerView.Adapter<MovieCastsAdapter.CastViewHolder> {
    private Context mContext;
    private List<MovieCastBrief> mCasts;

    public MovieCastsAdapter(Context mContext, List<MovieCastBrief> mCasts) {
        this.mContext = mContext;
        this.mCasts = mCasts;
    }

    @NonNull
    @Override
    public MovieCastsAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cast_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCastsAdapter.CastViewHolder holder, int position) {
        Glide.with(mContext.getApplicationContext())
                .load(Constants.IMAGE_LOADING_BASE_URL_342 + mCasts.get(position).getProfilePath())
                .centerCrop()
                .placeholder(R.drawable.cast_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.cast_imageView);

        if (mCasts.get(position).getName() != null)
            holder.cast_actor_name.setText(mCasts.get(position).getName());
        else
            holder.cast_actor_name.setText("");

        if (mCasts.get(position).getCharacter() != null)
            holder.cast_actor_alias.setText(mCasts.get(position).getCharacter());
        else
            holder.cast_actor_alias.setText("");
    }

    @Override
    public int getItemCount() {
        return mCasts.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout cast_linear_layout;
        private ImageView cast_imageView;
        private TextView cast_actor_name;
        private TextView cast_actor_alias;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);

            cast_linear_layout = itemView.findViewById(R.id.cast_root_view);
            cast_imageView = itemView.findViewById(R.id.cast_imageView);
            cast_actor_name = itemView.findViewById(R.id.cast_actor_name);
            cast_actor_alias = itemView.findViewById(R.id.cast_actor_alias);

            cast_linear_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CastActivity.class);
                    intent.putExtra("person_id", mCasts.get(getAdapterPosition()).getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
