package cf.vandit.movie_app.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.utils.Constants;

public class YoutubeActivity extends YouTubeBaseActivity {
    private YouTubePlayerView youTubePlayerView;
    private FloatingActionButton youtube_close;

    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youTubePlayerView = findViewById(R.id.youtube_view);
        youtube_close = findViewById(R.id.youtube_close);

        Intent receivedIntent = getIntent();
        String youtube_id = receivedIntent.getStringExtra("youtube_id");

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(!b) {
                    youTubePlayer.loadVideo(youtube_id);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {}
        };

        youTubePlayerView.initialize(Constants.YOUTUBE_API_KEY, onInitializedListener);

        youtube_close.setOnClickListener(view -> onBackPressed());
    }
}