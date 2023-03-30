package com.example.finalproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class FireNotification extends Service {
    public FireNotification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {
        NotificationManager notifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n;

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channel = "3333";
            CharSequence name = "channel3333";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel nChannel = new NotificationChannel(channel, name, importance);
            notifyMgr.createNotificationChannel(nChannel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId(channel)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setTicker("Ticker Notify")
                    .setContentTitle("Come Back!")
                    .setContentText("We miss you! come beat the high score")
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent);
            n = builder.build();

        }
        else
        {
            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("Ticker Notify")
                    .setContentTitle("New Message")
                    .setContentText("You've recieved a new message")
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent);
            n = builder.build();
        }
        notifyMgr.notify(1,n);
    }


}