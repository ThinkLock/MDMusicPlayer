package com.yang.mdmusicplayer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yang.mdmusicplayer.MusicApplication;
import com.yang.mdmusicplayer.R;
import com.yang.mdmusicplayer.activitys.PlayMusicActivity;
import com.yang.mdmusicplayer.adapters.LocalMusicListAdapter;
import com.yang.mdmusicplayer.entitys.Audio;
import com.yang.mdmusicplayer.listeners.RecyclerViewClickListener;
import com.yang.mdmusicplayer.utils.AudioUtil;
import com.yang.mdmusicplayer.utils.DialogUtil;
import com.yang.mdmusicplayer.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang on 2017/5/29.
 */
public class LocalMusicFragment extends BaseFragment {


    private static final String TAG = "LocalMusicFragment";


    private MusicApplication application;

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;

    //本地所有音乐文件总数
    private int mMusicFileNum;

    private View mRootView;
    private RecyclerView recycler_view;
    private SwipeRefreshLayout swipe;
    private int mPagesize = 10;
    private int mPage = 0;
    private int contentQuantity = 10;
    private List<Audio> mData;
    private LocalMusicListAdapter mAdapter;
    private boolean mIsFirstTimeTouchBottom = true;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_local_music, container, false);
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
        DialogUtil.showProgressDialog(getActivity(),"加本地音乐中……");
        initAudioData();
        initView();
        initData();
    }

    private void initAudioData(){
        mMusicFileNum = AudioUtil.getInstance(getActivity().getApplicationContext()).getmLocalMusicList().size();
        Log.i(TAG,""+mMusicFileNum);
        DialogUtil.dismissProgressDialog();
    }

    private void initView(){
        recycler_view = (RecyclerView)mRootView.findViewById(R.id.android_recycler_view);
        swipe = (SwipeRefreshLayout)mRootView.findViewById(R.id.android_swipe);
    }

    private void initData(){
        mData = new ArrayList<>();
        application = (MusicApplication) getActivity().getApplication();
        layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new LocalMusicListAdapter(getActivity());
        recycler_view.setAdapter(mAdapter);
        recycler_view.setLayoutManager(layoutManager);

        getMusicData(false); //刚进入界面先刷新一次

        //点击事件
        recycler_view.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                application.getMusicBinder().startPlay(position, 0);
                startActivity(new Intent(getActivity(), PlayMusicActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    private void  getMusicData(boolean isLoadMore){
        try {
            swipe.measure(View.MEASURED_SIZE_MASK, View.MEASURED_HEIGHT_STATE_SHIFT);
            swipe.setRefreshing(true);

            mData = AudioUtil.getInstance(getActivity()).getmLocalMusicList();
            recycler_view.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setLocalMusicData(mData);
                    mAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                }
            });
        }catch (Throwable e){
            e.printStackTrace();
            swipe.setRefreshing(false);
        }
    }

}
