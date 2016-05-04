package com.wallpaperapp.jamie.wallpaperdownloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamie on 31/03/2016.
 */
public class BrowseWallpaperFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";
    private RecyclerView mWallpaperRecyclerView;
    private List<WallpaperItem> mItems = new ArrayList<>();
    private WallpaperDownloader<WallpaperHolder> mWallpaperDownloader;
    private String searchQuery = "Moon";

    public static BrowseWallpaperFragment newInstance() {
        return new BrowseWallpaperFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        updateWallpapers();

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

        View v = inflater.inflate(R.layout.fragment_wallpaper, container, false);
        mWallpaperRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_wallpaper_recycler_view);
        mWallpaperRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_wallpaper_gallery, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "QueryTextSubmit: " + s);
                searchQuery = s;
                updateWallpapers();
                hideKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "QueryTextChange: " + s);
                return false;
            }
        });
    }

    private void updateWallpapers() {
        new FetchItemsTask().execute(searchQuery);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWallpaperDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
        mWallpaperDownloader.clearQueue();
    }

    private class FetchItemsTask extends AsyncTask<String, Void, List<WallpaperItem>> {
        private String searchQuery;

        @Override
        protected List<WallpaperItem> doInBackground(String... params) {
            searchQuery = params[0];
            return new BingImageFetcher().fetchItems(searchQuery);
        }

        @Override
        protected void onPostExecute(List<WallpaperItem> items) {
            mItems = items;
            mWallpaperRecyclerView.setAdapter(new WallpaperAdapter(mItems));
        }
    }

    private class WallpaperHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private WallpaperItem wallpaperSelected;

        private ImageView mWallpaperImageView;

        public WallpaperHolder(View itemView) {
            super(itemView);
            mWallpaperImageView = (ImageView) itemView.findViewById(R.id.fragment_wallpaper_image_view);
            itemView.setOnClickListener(this);
        }

        public void bindDrawable(Drawable drawable) {
            mWallpaperImageView.setImageDrawable(drawable);
        }

        public void bindWallpaperItem(WallpaperItem wallpaperItem) {
            wallpaperSelected = wallpaperItem;
        }

        @Override
        public void onClick(View v) {
            Intent i = WallpaperPageActivity.newIntent(getActivity(), wallpaperSelected.getURI());
            startActivity(i);
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
            photoHolder.bindWallpaperItem(wallpaperItem);
            mWallpaperDownloader.queueWallpaper(photoHolder, wallpaperItem.getThumbnailURL());

        }

        @Override
        public int getItemCount() {
            return mWallpaperItems.size();
        }
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

    }
}




