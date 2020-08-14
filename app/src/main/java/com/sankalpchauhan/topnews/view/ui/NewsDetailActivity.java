package com.sankalpchauhan.topnews.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sankalpchauhan.topnews.config.Constants;
import com.sankalpchauhan.topnews.databinding.ActivityNewsDetailBinding;
import com.sankalpchauhan.topnews.util.Utility;

import timber.log.Timber;

import static com.sankalpchauhan.topnews.util.Utility.isOnline;

public class NewsDetailActivity extends AppCompatActivity {
    String newsUrl;
    private ActivityNewsDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().hasExtra(Constants.NEWS_URL)) {
            newsUrl = getIntent().getStringExtra(Constants.NEWS_URL);
        } else {
            this.finish();
        }
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Article");
        }
        binding.mWebview.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = binding.mWebview.getSettings();
        settings.setDomStorageEnabled(true);
        final Activity activity = this;
        binding.mWebview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.progressHorizontal.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progressHorizontal.setVisibility(View.INVISIBLE);
            }
        });
        if (newsUrl != null) {
            binding.mWebview.loadUrl(newsUrl);
        } else {
            Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isOnline()) {
            Utility.setSnackBar(binding.getRoot(), "No Internet Connection");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}
