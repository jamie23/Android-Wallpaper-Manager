package com.wallpaperapp.jamie.wallpaperdownloader;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Jamie on 08/04/2016.
 */
public class WallpaperPageFragment extends Fragment{
    private static final String ARG_URI = "wallpaper_url";

    private Uri mUri;
    private ImageView wallpaperView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_wallpaper_page, container, false);
        wallpaperView = (ImageView) v.findViewById(R.id.image_wallpaper);

        return v;
    }
}
