package com.yang.mdmusicplayer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yang.mdmusicplayer.R;

/**
 * Created by Yang on 2017/5/29.
 */
public class NetMusicFragment extends BaseFragment{

    private static final String TAG = "NetMusicFragment";
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;

    private View mRootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_net_music, container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        isPrepared = true;
        lazyLoad();
        return mRootView;
    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }
}
