package com.wallpaperapp.jamie.wallpaperdownloader;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by jamie on 14/04/2016.
 */
public class WallpaperSchedulerFragment extends Fragment {
    private EditText queryText;
    private EditText daysToSwitch;
    private final int JOB_ID = 1;
    private final String TAG = "WallpaperSchedulerFrag";
    private final int valueOfDay = (1000 * 60 * 60 * 24);

    public static WallpaperSchedulerFragment newInstance() {
        return new WallpaperSchedulerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wallpaper_scheduler, container, false);
        final JobScheduler scheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);

        queryText = (EditText) v.findViewById(R.id.search_query);
        daysToSwitch = (EditText) v.findViewById(R.id.days_to_switch);

        Button btnStartScheduler = (Button) v.findViewById(R.id.btn_start_scheduler);
        Button btnStop = (Button) v.findViewById(R.id.btn_stop_scheduler);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduler.cancelAll();
                showSnackBar(v, getString(R.string.wallpaper_switcher_off));

                //Clear user preferences
                updateSchedulerSettings("", 0);
                updateSchedulerUI();
            }
        });

        btnStartScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the time and search input, save them in shared preferences
                Log.i(TAG, String.valueOf(queryText.getText().toString().equals("")));
                Log.i(TAG, String.valueOf(daysToSwitch.getText().toString().equals("")));
                if ((queryText.getText().toString().equals("")) || (daysToSwitch.getText().toString().equals(""))) {
                    showSnackBar(v, getString(R.string.no_options_input));
                } else {
                    int daysSwitch = Integer.parseInt(daysToSwitch.getText().toString());
                    updateSchedulerSettings(queryText.getText().toString(), daysSwitch);

                    JobInfo jobInfo = new JobInfo.Builder(
                            JOB_ID, new ComponentName(getActivity(), RetrieveWallpaperService.class))
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                            .setPeriodic(valueOfDay * daysSwitch)
                            .setPersisted(true)
                            .build();
                    scheduler.schedule(jobInfo);

                    showSnackBar(v, getString(R.string.wallpaper_switcher_on));
                    updateSchedulerUI();
                }
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        updateSchedulerUI();
    }

    private void updateSchedulerSettings(String searchQuery, int userDaysSwitch) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.saved_scheduler_query), searchQuery);
        editor.putInt(getString(R.string.saved_scheduler_days), userDaysSwitch);

        editor.apply();
    }

    private void showSnackBar(View v, String message) {
        Snackbar snackbar = Snackbar
                .make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void updateSchedulerUI() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        int schedulerDays = sharedPref.getInt(getString(R.string.saved_scheduler_days), 0);
        String schedulerQuery = sharedPref.getString(getString(R.string.saved_scheduler_query), "");

        Log.i(TAG, "Scheduler days: " + schedulerDays);
        Log.i(TAG, "Scheduler query: " + schedulerQuery);

        TextView txtSchedulerStatus = (TextView) getView().findViewById(R.id.scheduler_current_status);
        TextView txtSchedulerTheme = (TextView) getView().findViewById(R.id.scheduler_current_query);
        TextView txtSchedulerDays = (TextView) getView().findViewById(R.id.scheduler_current_days);

        if (schedulerDays != 0) {
            //Scheduler on
            txtSchedulerStatus.setText(getString(R.string.main_menu_scheduler_status," On"));
            txtSchedulerTheme.setText(getString(R.string.scheduler_current_theme_title, schedulerQuery));
            txtSchedulerDays.setText(getString(R.string.scheduler_current_days_title, getResources().getQuantityString(R.plurals.scheduler_days_plural, schedulerDays, schedulerDays)));
        } else {
            txtSchedulerStatus.setText(getString(R.string.main_menu_scheduler_status," Off"));
            txtSchedulerTheme.setText("");
            txtSchedulerDays.setText("");
        }
    }
}
