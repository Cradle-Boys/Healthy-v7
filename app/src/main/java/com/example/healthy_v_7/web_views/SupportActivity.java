package com.example.healthy_v_7.web_views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.healthy_v_7.R;
import com.example.healthy_v_7.helper.ReminderBroadcast;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        createNotificationChannel();

        Intent intent = new Intent(SupportActivity.this,ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SupportActivity.this,0,intent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        long timeAtButtonClick = System.currentTimeMillis();
        Log.i("TAG",String.valueOf(timeAtButtonClick));
        long tenSecondsInMillis = 1000*10;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+AlarmManager.INTERVAL_DAY,AlarmManager.INTERVAL_DAY,pendingIntent);
//        alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick+tenSecondsInMillis,pendingIntent);
        Log.i("TAG","reminder set!");


        WebView webView = findViewById(R.id.supportWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSedMLYJbKFd9AVLaM9gXutC4ydX-lDvICJColOjbGJr7BC1cw/viewform");

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
    public void createNotificationChannel(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "HealthyReminderChannel";
            String description = "Channel for Healthy Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyHealthy",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

}
