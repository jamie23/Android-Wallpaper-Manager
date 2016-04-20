package com.wallpaperapp.jamie.wallpaperdownloader;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

/**
 * Created by jamie on 15/04/2016.
 */
public class RetrieveWallpaperService extends JobService {
    private RetrieveWallpaperAsync currentTask;

    @Override
    public boolean onStartJob(JobParameters params) {
        currentTask = new RetrieveWallpaperAsync();
        currentTask.execute(params);
        /* MUST BE DONE INSIDE AN ASYNC TASK.
            Retrieve what the user has set as the theme of their wallpaper.
            Kick off the retrieval of the results for that search
            Choose random number from that to get a wallpaper
            set wallpaper.
         */



        //False meaning job has finished.
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        //System has stopped job
        if(currentTask!=null){
            currentTask.cancel(true);
        }
        return true;
    }





    private class RetrieveWallpaperAsync extends AsyncTask<JobParameters, Void, Void>{

        @Override
        protected Void doInBackground(JobParameters... params) {
            //Get the user entered search word, stored in shared preferences, and retrieve results for it
            JobParameters jobParams = params[0];



            return null;
        }
    }
}
