package com.wallpaperapp.jamie.wallpaperdownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jamie on 04/04/2016.
 */
public class WallpaperDownloader<T> extends HandlerThread {

    private static final String TAG = "WallpaperDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    private Handler mRequestHandler;
    private ConcurrentMap<T, String> mRequestMap = new ConcurrentHashMap<>();

    //Main thread handler (UI)
    private Handler mResponseHandler;
    private WallpaperDownloadListener<T> mWallpaperDownloadListener;

    public interface WallpaperDownloadListener<T>{
        void onWallpaperDownloadListener(T target, Bitmap wallpaper);
    }

    public void setWallpaperDownloadListener(WallpaperDownloadListener<T> listener){
        mWallpaperDownloadListener = listener;
    }

    public WallpaperDownloader(Handler responseHandler){
        super(TAG);
        mResponseHandler = responseHandler;
    }

    @Override
    protected void onLooperPrepared(){
        mRequestHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == MESSAGE_DOWNLOAD){
                    T target = (T) msg.obj;
                    Log.i(TAG, "Got a request for URL: " + mRequestMap.get(target));
                    handleRequest(target);
                }
            }
        };
    }

    public void queueWallpaper(T target, String url){
        Log.i(TAG, "URL: " + url);

        if(url==null){
            return;
        }else{
            mRequestMap.put(target,url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                    .sendToTarget();
        }
    }

    public void clearQueue(){
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }

    private void handleRequest(final T target){
        try{
            final String url = mRequestMap.get(target);

            if(url==null){
                return;
            }

            byte[] bitmapBytes = new BingImageFetcher().getUrlBytes2(url);
            final Bitmap bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes,0,bitmapBytes.length);
            Log.i(TAG, "Bitmap created for " + mRequestMap.get(target));

            mResponseHandler.post(new Runnable(){
                public void run(){
                    if(mRequestMap.get(target) != url){
                        return;
                    }

                    mRequestMap.remove(target);
                    mWallpaperDownloadListener.onWallpaperDownloadListener(target,bitmap);
                }

            });
        }catch(IOException ioe){
            Log.e(TAG, "Error downloading image", ioe);
        }
    }
}
