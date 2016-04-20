package com.wallpaperapp.jamie.wallpaperdownloader;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by jamie on 15/04/2016.
 */
public class RetrieveWallpaperService extends JobService {
    private RetrieveWallpaperAsync currentTask;
    private int i = 0;

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(getApplicationContext(),"Started job", Toast.LENGTH_SHORT).show();
        new RetrieveWallpaperAsync(this).execute(params);
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
        return false;
    }

    private class RetrieveWallpaperAsync extends AsyncTask<JobParameters, Void, JobParameters>{
        private final JobService jobService;

        public RetrieveWallpaperAsync(JobService jobService) {
            this.jobService = jobService;
        }

        @Override
        protected JobParameters doInBackground(JobParameters... params) {
            //Get the user entered search word, stored in shared preferences, and retrieve results for it
//            JobParameters jobParams = params[0];

            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters params) {
            Toast.makeText(getApplicationContext(),Integer.toString(i), Toast.LENGTH_LONG).show();
            jobService.jobFinished(params, false);
        }
    }
}
