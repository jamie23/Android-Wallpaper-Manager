package com.wallpaperapp.jamie.wallpaperdownloader;

import android.support.v4.app.Fragment;

/**
 * Created by jamie on 12/04/2016.
 */
public class MainMenuActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return MainMenuFragment.newInstance();
    }
}
