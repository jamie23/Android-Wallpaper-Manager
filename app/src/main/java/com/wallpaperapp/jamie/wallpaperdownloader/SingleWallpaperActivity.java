package com.wallpaperapp.jamie.wallpaperdownloader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SingleWallpaperActivity extends BrowseFragmentActivity {

    public static Intent newIntent(Context context){
        return new Intent(context, SingleWallpaperActivity.class);
    }
    @Override
    protected Fragment createFragment(){
        return BrowseWallpaperFragment.newInstance();
    }
}
