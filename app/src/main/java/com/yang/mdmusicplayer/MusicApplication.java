package com.yang.mdmusicplayer;

import android.app.Application;

import com.yang.mdmusicplayer.utils.ToastUtil;

/**
 * Created by Yang on 2017/5/29.
 */
public class MusicApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(getApplicationContext());
    }
}
