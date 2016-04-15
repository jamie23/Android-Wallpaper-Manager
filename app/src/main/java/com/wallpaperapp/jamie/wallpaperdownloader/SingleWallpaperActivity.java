package com.wallpaperapp.jamie.wallpaperdownloader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SingleWallpaperActivity extends BrowseFragmentActivity {

    public static Intent newIntent(Context context){
        Intent i = new Intent(context, SingleWallpaperActivity.class);
        return i;
    }
    @Override
    protected Fragment createFragment(){
        return BrowseWallpaperFragment.newInstance();
    }
}
