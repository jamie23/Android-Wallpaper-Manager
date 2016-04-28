package com.wallpaperapp.jamie.wallpaperdownloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by jamie on 12/04/2016.
 */
public class MainMenuFragment extends Fragment {
    public static MainMenuFragment newInstance(){
        MainMenuFragment fragment = new MainMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_menu_fragment, container, false);

        Button browseWallpapers = (Button) v.findViewById(R.id.btn_browse_wallpapers);
        browseWallpapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = SingleWallpaperActivity.newIntent(getContext());
                startActivity(i);
            }
        });

        Button wallpaperScheduler = (Button) v.findViewById(R.id.btn_wallpaper_switcher);
        wallpaperScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = WallpaperSchedulerActivity.newIntent(getContext());
                startActivity(i);
            }
        });

        return v;
    }
}
