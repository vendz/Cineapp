package cf.vandit.movie_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.utils.AdBlocker;
import cf.vandit.movie_app.utils.Constants;
import cf.vandit.movie_app.utils.MyBrowser;

public class SeriesStreamActivity extends AppCompatActivity {

    private WebView series_webView;
    private ImageButton series_backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_stream);

        AdBlocker.init(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_series_stream);

        series_backButton = findViewById(R.id.series_backButton);

        series_webView = findViewById(R.id.series_WebView);
        series_webView.setWebViewClient(new MyBrowser());
        series_webView.setWebChromeClient(new ChromeClient());
        series_webView.getSettings().setJavaScriptEnabled(true);

        Intent receivedIntent = getIntent();
        String seriesId = receivedIntent.getStringExtra("series_id");
        String season_number = receivedIntent.getStringExtra("season_number");
        String episode_number = receivedIntent.getStringExtra("episode_number");

        String season;
        String episode;

        if(Integer.parseInt(season_number) < 10) {
            season = "0" + season_number.toString();
        } else {
            season = season_number.toString();
        }

        if(Integer.parseInt(episode_number) < 10){
            episode = "0" + episode_number.toString();
        } else {
            episode = episode_number.toString();
        }
        System.out.println(Constants.SERIES_STREAM_URL + seriesId + "/" + season + "-" + episode);
        series_webView.loadUrl(Constants.SERIES_STREAM_URL + seriesId + "/" + season + "-" + episode);

        series_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    public void onBackPressed() {
        if(series_webView!=null && series_webView.canGoBack()) {
            series_webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}