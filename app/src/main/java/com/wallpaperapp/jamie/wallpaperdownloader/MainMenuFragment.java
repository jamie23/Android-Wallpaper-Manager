package com.wallpaperapp.jamie.wallpaperdownloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        View browseWallpapers = (View) v.findViewById(R.id.browse_wallpapers_layout);
        browseWallpapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = WallpaperActivity.newIntent(getContext());
                startActivity(i);
            }
        });

        return v;
    }
}
