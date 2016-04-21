package com.wallpaperapp.jamie.wallpaperdownloader;

import android.app.WallpaperManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by jamie on 15/04/2016.
 */
public class RetrieveWallpaperService extends JobService {
    private static final String TAG = "RetrieveService";
    private RetrieveWallpaperAsync currentTask;
    private int i = 0;

    @Override
    public boolean onStartJob(JobParameters params) {
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
            List<WallpaperItem> items = new BingImageFetcher().fetchItems("pandas");

            Random rand = new Random();
            int randomNum = rand.nextInt((items.size()));

            WallpaperItem randomWallpaper = items.get(randomNum);
            try{
                byte[] bitmapBytes = new BingImageFetcher().getUrlBytes(randomWallpaper.getUrl(), false);
                Bitmap bitmap = BitmapFactory
                        .decodeByteArray(bitmapBytes,0,bitmapBytes.length);
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                try{
                    if(bitmap!=null){
                        wallpaperManager.setBitmap(bitmap);
                    }
                }catch(IOException ioe){
                    Log.e(TAG, "Error setting the wallpaper", ioe);
                }
            }catch(IOException e){
                Log.e(TAG, "Error downloading image", e);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters params) {

            jobService.jobFinished(params, false);
        }
    }
}
