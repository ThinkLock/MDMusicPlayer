package com.yang.mdmusicplayer.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yang.mdmusicplayer.R;
import com.yang.mdmusicplayer.fragments.LocalMusicFragment;

/**
 * Created by Yang on 2016/9/5.
 */
public class MainPageAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public MainPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment result = new LocalMusicFragment();

        switch (position){
            case 0:
                result = new LocalMusicFragment();
                break;
            case 1:
                result = new LocalMusicFragment();
                break;
        }

        return result;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = mContext.getString(R.string.drawer_menu_1);
        switch (position) {
            case 0:
                title = mContext.getString(R.string.drawer_menu_1);
                break;
            case 1:
                title = mContext.getString(R.string.drawer_menu_2);
                break;
        }
        return title;
    }
}
