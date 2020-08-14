package com.sankalpchauhan.topnews.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sankalpchauhan.topnews.databinding.ActivityNewsDetailBinding;

public class NewsDetailActivity extends AppCompatActivity {
    private ActivityNewsDetailBinding binding;
    private WebView mWebview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent()!=null){

        } else {
            this.finish();
        }
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mWebview  = new WebView(this);
        mWebview.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        mWebview .loadUrl("http://www.google.com");
        setContentView(mWebview );
    }
}
