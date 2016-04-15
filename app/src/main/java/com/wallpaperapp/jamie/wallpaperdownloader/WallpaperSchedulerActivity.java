package com.wallpaperapp.jamie.wallpaperdownloader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by jamie on 14/04/2016.
 */
public class WallpaperSchedulerActivity extends BrowseFragmentActivity {

    public static Intent newIntent(Context context){
        Intent i = new Intent(context, WallpaperSchedulerActivity.class);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return WallpaperSchedulerFragment.newInstance();
    }
}
