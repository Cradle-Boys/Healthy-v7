 package com.example.healthy_v_7.web_views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.healthy_v_7.R;

 public class WebViewActivity extends AppCompatActivity {

     @Override
     public void finish() {
         super.finish();
         overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
     }

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        String page=intent.getStringExtra("page");

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        try {
            if (page.equals("workout")) {
                webView.loadUrl("https://cradle-boys.github.io/Healthy-web/workout.html");
            } else if (page.equals("diet")) {
                webView.loadUrl("https://cradle-boys.github.io/Healthy-web/diet-plan-pages/diet-plans.html");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

     @Override
     protected void onPause() {
         super.onPause();
//         overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
     }

}
