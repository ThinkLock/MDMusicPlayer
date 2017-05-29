package com.yang.mdmusicplayer.fragments;

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

import com.yang.mdmusicplayer.R;
import com.yang.mdmusicplayer.adapters.LocalMusicListAdapter;
import com.yang.mdmusicplayer.entitys.Audio;
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
        AudioUtil.getInstance(getActivity().getApplication()).initAudioList();
        mMusicFileNum = AudioUtil.getInstance(getActivity().getApplication()).getmLocalMusicList().size();
        Log.i(TAG,""+mMusicFileNum);
        DialogUtil.dismissProgressDialog();
    }

    private void initView(){
        recycler_view = (RecyclerView)mRootView.findViewById(R.id.android_recycler_view);
        swipe = (SwipeRefreshLayout)mRootView.findViewById(R.id.android_swipe);
    }

    private void initData(){
        mData = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new LocalMusicListAdapter(getActivity());
        recycler_view.setAdapter(mAdapter);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.addOnScrollListener(getOnBottomListener(layoutManager));

        getMusicData(false); //刚进入界面先刷新一次

        //刷新时执行的事件
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMusicData(true);
            }
        });
    }

    private void  getMusicData(boolean isLoadMore){
        try {
            swipe.measure(View.MEASURED_SIZE_MASK, View.MEASURED_HEIGHT_STATE_SHIFT);
            swipe.setRefreshing(true);
            if(!isLoadMore){
                mData.clear();
                contentQuantity =10;
            }

            if (isLoadMore) {
                contentQuantity += 10;
            }
            mData = AudioUtil.getInstance(getActivity()).getmLocalMusicList().subList(0, contentQuantity<mMusicFileNum?contentQuantity:mMusicFileNum-1);
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
            ToastUtil.show("到底了");
        }
    }

    RecyclerView.OnScrollListener getOnBottomListener(final RecyclerView.LayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (!swipe.isRefreshing() && lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                    if (!mIsFirstTimeTouchBottom) {
                        swipe.setRefreshing(true);
                        getMusicData(true);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }
}
