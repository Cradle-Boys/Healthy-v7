package com.example.healthy_v_7.helper;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.healthy_v_7.R;
import com.example.healthy_v_7.authentication.StartActivity;

/**
 * Created by paolotormon on 29 May 2020
 */

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyHealthy");
//        Intent nextIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.stackoverflow.com/"));
        Intent nextIntent = new Intent(context, StartActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, nextIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.ic_run)
                .setContentTitle("Healthy")
                .setContentText("Wanna be healthy? Take a few steps today.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        Log.i("TAG","received");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200,builder.build());
    }
}