package com.wallpaperapp.jamie.wallpaperdownloader;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Jamie on 08/04/2016.
 */
public class WallpaperPageFragment extends Fragment{
    private static final String ARG_URI = "wallpaper_url";
    private static final String TAG = "WallpaperPageFragment";

    private Uri mUri;
    private ImageView wallpaperView;
    private TextView txtImageDeleted;
    private TextView txtImageLoading;
    private Bitmap wallpaperBitmap;
    private Button btnSetWallpaper;

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
        txtImageDeleted = (TextView) v.findViewById(R.id.txt_image_deleted);
        txtImageLoading= (TextView) v.findViewById(R.id.txt_image_loading);

        btnSetWallpaper = (Button) v.findViewById(R.id.btn_set_wallpaper);
        btnSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getActivity());
                try{
                    if(wallpaperBitmap==null){
                        Snackbar snackbar = Snackbar
                                .make(v, R.string.wallpaper_not_loaded, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }else{
                        wallpaperManager.setBitmap(wallpaperBitmap);
                        Snackbar snackbar = Snackbar
                                .make(v, R.string.wallpaper_added, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }catch(IOException ioe){
                    Log.e(TAG, "Error setting the wallpaper", ioe);
                }
            }
        });
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
            if(bitmap==null){
                //The server no longer has this image
                wallpaperView.setVisibility(View.GONE);
                txtImageLoading.setVisibility(View.GONE);1`
                txtImageDeleted.setVisibility(View.VISIBLE);
                btnSetWallpaper.setVisibility(View.GONE);
            }else {
                txtImageDeleted.setVisibility(View.GONE);
                txtImageLoading.setVisibility(View.GONE);
                wallpaperView.setVisibility(View.VISIBLE);
                wallpaperBitmap = bitmap;
                wallpaperView.setImageBitmap(wallpaperBitmap);
            }
        }
    }
}
