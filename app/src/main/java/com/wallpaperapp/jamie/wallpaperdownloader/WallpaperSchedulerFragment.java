package com.wallpaperapp.jamie.wallpaperdownloader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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


        Button btnSubmit = (Button) v.findViewById(R.id.btn_start_scheduler);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start job scheduler

            }
        });
        return v;
    }
}
