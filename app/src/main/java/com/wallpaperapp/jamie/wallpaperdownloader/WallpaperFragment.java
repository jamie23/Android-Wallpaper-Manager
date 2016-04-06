package com.wallpaperapp.jamie.wallpaperdownloader;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamie on 31/03/2016.
 */
public class WallpaperFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";
    private RecyclerView mWallpaperRecyclerView;
    private List<WallpaperItem> mItems = new ArrayList<>();
    private WallpaperDownloader<WallpaperHolder> mWallpaperDownloader;

    public static WallpaperFragment newInstance() {
        return new WallpaperFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        new FetchItemsTask().execute();

        /*
            Passing a handler through to downloader so we can update
            view in this thread (UI) when image is downloaded.
        */
        Handler responseHandler = new Handler();

        mWallpaperDownloader = new WallpaperDownloader<>(responseHandler);
        mWallpaperDownloader.setWallpaperDownloadListener(
                new WallpaperDownloader.WallpaperDownloadListener<WallpaperHolder>() {
                    @Override
                    public void onWallpaperDownloadListener(WallpaperHolder wallpaperHolder, Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        wallpaperHolder.bindDrawable(drawable);
                    }
                }
        );
        mWallpaperDownloader.start();
        mWallpaperDownloader.getLooper();
        Log.i(TAG, "background thread started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wallpaper, container, false);
        mWallpaperRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_wallpaper_recycler_view);
        mWallpaperRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return v;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mWallpaperDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    @Override
    public void onDestroyView(){
        super.onDestroy();
        mWallpaperDownloader.clearQueue();
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<WallpaperItem>> {
        @Override
        protected List<WallpaperItem> doInBackground(Void... params) {
            return new BingImageFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<WallpaperItem> items) {
            mItems = items;
            mWallpaperRecyclerView.setAdapter(new WallpaperAdapter(mItems));
        }
    }

    private class WallpaperHolder extends RecyclerView.ViewHolder{
        private ImageView mWallpaperImageView;

        public WallpaperHolder(View itemView){
            super(itemView);
            mWallpaperImageView = (ImageView) itemView.findViewById(R.id.fragment_wallpaper_image_view);
        }

        public void bindDrawable(Drawable drawable){
            mWallpaperImageView.setImageDrawable(drawable);
        }
    }

    private class WallpaperAdapter extends RecyclerView.Adapter<WallpaperHolder> {
        private List<WallpaperItem> mWallpaperItems;

        public WallpaperAdapter(List<WallpaperItem> wallpaperItems) {
            mWallpaperItems = wallpaperItems;
        }

        @Override
        public WallpaperHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.wallpaper_item, viewGroup, false);
            return new WallpaperHolder(view);
        }

        @Override
        public void onBindViewHolder(WallpaperHolder photoHolder, int position) {
            WallpaperItem wallpaperItem = mWallpaperItems.get(position);
            mWallpaperDownloader.queueWallpaper(photoHolder,wallpaperItem.getThumbnailURL());
        }

        @Override
        public int getItemCount() {
            return mWallpaperItems.size();
        }
    }
}




