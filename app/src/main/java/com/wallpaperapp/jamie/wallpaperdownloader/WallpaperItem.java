package com.wallpaperapp.jamie.wallpaperdownloader;

/**
 * Created by jamie on 3/4/2016.
 */
public class WallpaperItem {
    private String mUrl;
    private String mThumbnailURL;

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        mThumbnailURL = thumbnailURL;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public String toString(){
        return mUrl;
    }
}
