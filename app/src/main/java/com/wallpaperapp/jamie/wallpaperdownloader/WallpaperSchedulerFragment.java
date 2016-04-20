package com.wallpaperapp.jamie.wallpaperdownloader;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by jamie on 14/04/2016.
 */
public class WallpaperSchedulerFragment extends Fragment {
    private EditText queryText;
    private EditText daysToSwitch;
    private final int JOB_ID = 1;

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
                Toast.makeText(getActivity(),"cancelled all", Toast.LENGTH_LONG).show();
            }
        });


        btnStartScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the time and search input, save them in shared preferences
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.saved_scheduler_query), queryText.getText().toString());
                editor.putInt(getString(R.string.saved_scheduler_days), Integer.parseInt(daysToSwitch.getText().toString()));
                editor.commit();

                Toast.makeText(getActivity(),"Setting up jobInfo", Toast.LENGTH_SHORT).show();

                JobInfo jobInfo = new JobInfo.Builder(
                        JOB_ID, new ComponentName(getActivity(), RetrieveWallpaperService.class))
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                        .setPeriodic(1000*30)
                        .setPersisted(true)
                        .build();
                scheduler.schedule(jobInfo);
                Toast.makeText(getActivity(),"started scheduler", Toast.LENGTH_LONG).show();

            }
        });
        return v;
    }
}
