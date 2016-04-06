package com.wallpaperapp.jamie.wallpaperdownloader;

import android.support.v4.app.Fragment;

public class WallpaperActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return WallpaperFragment.newInstance();
    }
}
