package com.wallpaperapp.jamie.wallpaperdownloader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * Created by jamie on 08/04/2016.
 */
public class WallpaperPageActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, Uri uri){
        Intent i = new Intent(context, WallpaperPageActivity.class);
        i.setData(uri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return WallpaperPageFragment.newInstance(getIntent().getData());
    }
}
