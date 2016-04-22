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

/**
 * Created by jamie on 14/04/2016.
 */
public class WallpaperSchedulerFragment extends Fragment {
    private EditText queryText;
    private EditText daysToSwitch;
    private final int JOB_ID = 1;
    private final String TAG = "WallpaperSchedulerFrag";

    public static WallpaperSchedulerFragment newInstance() {
        return new WallpaperSchedulerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_wallpaper_scheduler,container,false);
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

            }
        });


        btnStartScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the time and search input, save them in shared preferences
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                Log.i(TAG, String.valueOf(queryText.getText().toString().equals("")));
                Log.i(TAG, String.valueOf(daysToSwitch.getText().toString().equals("")));
                if((queryText.getText().toString().equals(""))||(daysToSwitch.getText().toString().equals(""))) {
                    showSnackBar(v, getString(R.string.no_options_input));
                }else{
                    editor.putString(getString(R.string.saved_scheduler_query), queryText.getText().toString());
                    editor.commit();

                    int switchTime = Integer.parseInt(daysToSwitch.getText().toString());
                    JobInfo jobInfo = new JobInfo.Builder(
                            JOB_ID, new ComponentName(getActivity(), RetrieveWallpaperService.class))
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                            .setPeriodic(1000*switchTime)
                            .setPersisted(true)
                            .build();
                    scheduler.schedule(jobInfo);

                    showSnackBar(v, getString(R.string.wallpaper_switcher_on));
                }
            }
        });
        return v;
    }

    private void showSnackBar(View v, String message){
        Snackbar snackbar = Snackbar
                .make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
