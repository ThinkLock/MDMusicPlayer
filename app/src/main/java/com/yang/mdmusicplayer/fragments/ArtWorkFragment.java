package com.yang.mdmusicplayer.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yang.mdmusicplayer.MusicApplication;
import com.yang.mdmusicplayer.R;
import com.yang.mdmusicplayer.activitys.PlayMusicActivity;
import com.yang.mdmusicplayer.entitys.Audio;
import com.yang.mdmusicplayer.utils.AudioUtil;
import com.yang.mdmusicplayer.utils.ImageUtil;

import java.util.List;


/**
 * 歌曲专辑图片界面
 * Created by Idea on 2016/5/31.
 */
public class ArtWorkFragment extends Fragment
{
    private static final String TAG = "RhymeMusic";
    private static final String SUB = "[ArtWorkFragment]#";

    private Context context;

    private List<Audio> audioList; // 音乐列表

    private ImageView imageCover; // 封面图片

    private PlayMusicActivity playbackActivity;
    private MusicApplication application;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
        playbackActivity = (PlayMusicActivity) getActivity();
        application = (MusicApplication) playbackActivity.getApplication();
        audioList = AudioUtil.getInstance(getActivity().getApplicationContext()).getmLocalMusicList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_artwork, null);

        imageCover = (ImageView) view.findViewById(R.id.image_cover);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.d(TAG, SUB + "onActivityCreated");

        super.onActivityCreated(savedInstanceState);
        setArtWork();
    }

    /**
     * 设置专辑封面图片
     */
    public void setArtWork()
    {
        int currentMusic = application.getCurrentMusic();
        Bitmap bitmap = ImageUtil.getAlbumCover(context,
                audioList.get(currentMusic).getId());

        if ( bitmap != null )
        {
            Bitmap output = ImageUtil.getRoundCornerBitmap(bitmap, 30.0f);
            imageCover.setImageBitmap(output);
        }
        else
        {
            imageCover.setImageResource(R.drawable.defualt_or_error);
        }

    }


}
