package com.wallpaperapp.jamie.wallpaperdownloader;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by jamie on 15/04/2016.
 */
public class RetrieveWallpaperService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {

        /* MUST BE DONE INSIDE AN ASYNC TASK.
            Retrieve what the user has set as the theme of their wallpaper.
            Kick off the retrieval of the results for that search
            Choose random number from that to get a wallpaper
            set wallpaper.
           */

        //False meaning job has finished.
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
