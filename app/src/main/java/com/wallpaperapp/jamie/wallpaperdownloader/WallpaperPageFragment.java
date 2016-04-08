package com.wallpaperapp.jamie.wallpaperdownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Jamie on 08/04/2016.
 */
public class WallpaperPageFragment extends Fragment{
    private static final String ARG_URI = "wallpaper_url";
    private static final String TAG = "WallpaperPageFragment";

    private Uri mUri;
    private ImageView wallpaperView;
    private Bitmap wallpaperBitmap;

    public static WallpaperPageFragment newInstance(Uri uri){
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, uri);

        WallpaperPageFragment fragment = new WallpaperPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUri = getArguments().getParcelable(ARG_URI);
        new FetchWallpaperTask().execute(mUri.toString());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_wallpaper_page, container, false);
        wallpaperView = (ImageView) v.findViewById(R.id.image_wallpaper);
        return v;
    }



    private class FetchWallpaperTask extends AsyncTask<String, Void, Bitmap> {
        private String wallpaperURL;
        private Bitmap wallpaperBitmapDownload;

        @Override
        protected Bitmap doInBackground(String... params) {
            wallpaperURL = params[0];

            try{
                byte[] bitmapBytes = new BingImageFetcher().getUrlBytes(wallpaperURL, false);
                wallpaperBitmapDownload = BitmapFactory
                        .decodeByteArray(bitmapBytes,0,bitmapBytes.length);
                Log.i(TAG, "Bitmap created for " + wallpaperURL);
            }catch(IOException ioe){
                Log.e(TAG, "Error downloading image", ioe);
            }
            return wallpaperBitmapDownload;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
           wallpaperBitmap = bitmap;
            wallpaperView.setImageBitmap(wallpaperBitmap);
        }
    }
}
