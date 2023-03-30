package com.example.finalproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class background_music extends Service {

    MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.onthelevel);
        player.setVolume(100,100);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        player.start();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        player.stop();
    }

}