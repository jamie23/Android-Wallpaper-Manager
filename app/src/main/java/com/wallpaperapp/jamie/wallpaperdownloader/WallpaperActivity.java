package com.wallpaperapp.jamie.wallpaperdownloader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class WallpaperActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent i = new Intent(context, WallpaperActivity.class);
        return i;
    }
    @Override
    protected Fragment createFragment(){
        return WallpaperFragment.newInstance();
    }
}
