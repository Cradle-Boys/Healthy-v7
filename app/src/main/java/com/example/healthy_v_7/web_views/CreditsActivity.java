package com.example.healthy_v_7.web_views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.healthy_v_7.R;

public class CreditsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        Intent intent = getIntent();
        String page=intent.getStringExtra("page");

        WebView webView = findViewById(R.id.credits_webVew);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://cradle-boys.github.io/Healthy-web/profile-page.html");
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
