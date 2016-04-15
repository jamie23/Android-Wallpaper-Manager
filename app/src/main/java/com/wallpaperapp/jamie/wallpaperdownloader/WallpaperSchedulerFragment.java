package com.wallpaperapp.jamie.wallpaperdownloader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jamie on 14/04/2016.
 */
public class WallpaperSchedulerFragment extends Fragment {

    public static WallpaperSchedulerFragment newInstance() {
        return new WallpaperSchedulerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_wallpaper_scheduler,container,false);


        return v;
    }
}
