package com.yang.mdmusicplayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yang.mdmusicplayer.R;
import com.yang.mdmusicplayer.entitys.Audio;
import com.yang.mdmusicplayer.utils.FormatUtil;
import com.yang.mdmusicplayer.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang on 2017/5/29.
 */
public class LocalMusicListAdapter extends RecyclerView.Adapter<LocalMusicListAdapter.LocalMusicViewHolder>{

    private Context mContext;
    private List<Audio> localMusicData = new ArrayList<>();

    public LocalMusicListAdapter(Context context){
        this.mContext = context;
    }

    public List<Audio> getLocalMusicData() {
        return localMusicData;
    }

    public void setLocalMusicData(List<Audio> localMusicData) {
        this.localMusicData = localMusicData;
    }

    @Override
    public LocalMusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recv_music_list,parent,false);
        LocalMusicViewHolder vh = new LocalMusicViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(LocalMusicViewHolder holder, int position) {
        holder.setDataToView(localMusicData.get(position));
    }

    @Override
    public int getItemCount() {
        return localMusicData.size();
    }


    class LocalMusicViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title;
        private TextView tv_author;
        private TextView tv_length;
        private ImageView iv_img;

        public LocalMusicViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView)itemView.findViewById(R.id.iv_music_img);
            tv_title = (TextView)itemView.findViewById(R.id.tv_music_title);
            tv_length = (TextView)itemView.findViewById(R.id.tv_music_length);
            tv_author = (TextView)itemView.findViewById(R.id.tv_music_author);
        }

        public void setDataToView(Audio musicData){
            tv_title.setText(musicData.getTitle());
            tv_author.setText(musicData.getArtist());
            tv_length.setText(FormatUtil.formatDuration(musicData.getDuration()));
            Picasso.with(mContext)
                    .load(ImageUtil.getCoverUri(mContext,musicData.getAlbumId()))
                    .error(R.drawable.defualt_or_error)
                    .fit()
                    .centerCrop()
                    .into(iv_img);
        }
    }
}
