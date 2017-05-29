package com.yang.mdmusicplayer.receviers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.yang.mdmusicplayer.MusicApplication;
import com.yang.mdmusicplayer.services.MusicService;


/**
 * 定时停止播放，具体操作
 * Created by Idea on 2016/6/11.
 */
public class AlarmReceiver extends BroadcastReceiver
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[AlarmReceiver]#";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(TAG, SUB + "音乐自动停止播放了");

        MusicApplication application = (MusicApplication) context.getApplicationContext();
        MediaPlayer mediaPlayer = application.getMediaPlayer();
        MusicService.MusicBinder musicBinder = application.getMusicBinder();

        if ( mediaPlayer != null && mediaPlayer.isPlaying() )
        {
            musicBinder.pausePlay();
        }
    }
}
