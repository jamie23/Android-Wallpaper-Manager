package com.wallpaperapp.jamie.wallpaperdownloader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jamie on 12/04/2016.
 */
public class MainMenuFragment extends Fragment {
    final String TAG = "MainMenuFragment";

    public static MainMenuFragment newInstance() {
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

        final Button wallpaperScheduler = (Button) v.findViewById(R.id.btn_wallpaper_switcher);
        wallpaperScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = WallpaperSchedulerActivity.newIntent(getContext());
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        updateSchedulerUI();
    }

    private void updateSchedulerUI() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        int schedulerDays = sharedPref.getInt(getString(R.string.saved_scheduler_days), 0);
        String schedulerQuery = sharedPref.getString(getString(R.string.saved_scheduler_query), "");

        Log.i(TAG, "Scheduler days: " + schedulerDays);
        Log.i(TAG, "Scheduler query: " + schedulerQuery);

        TextView txtSchedulerStatus = (TextView) getView().findViewById(R.id.current_status);
        TextView txtSchedulerTheme = (TextView) getView().findViewById(R.id.current_theme);
        TextView txtSchedulerDays = (TextView) getView().findViewById(R.id.current_days_switch);

        if (schedulerDays != 0) {
            //Scheduler on
            txtSchedulerStatus.setText("Current Status: On");
            txtSchedulerTheme.setText(schedulerQuery);
            txtSchedulerDays.setText(schedulerDays + " days");
        } else {
            txtSchedulerStatus.setText("Current Status: Off");
            txtSchedulerTheme.setText("");
            txtSchedulerDays.setText("");
        }
    }
}
