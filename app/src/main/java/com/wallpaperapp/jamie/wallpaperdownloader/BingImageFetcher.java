package com.wallpaperapp.jamie.wallpaperdownloader;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie on 02/03/2016.
 */
public class BingImageFetcher {
    private static final String API_KEY = APIKey.API_KEY;
    private static final String TAG = "BingImageFetcher";
    private static final String URL = "https://api.datamarket.azure.com/Bing/Search/v1/Image?Query=";
    private static final String USER_NAME = "";

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        String userpass = "" + ":" + API_KEY;
        byte[] encodedBase64 = Base64.encode(userpass.getBytes(),Base64.NO_WRAP);
        String accKeyEncoded = new String(encodedBase64);
        String basicAuthorisation = "Basic " + accKeyEncoded;
        connection.setRequestProperty("Authorization", basicAuthorisation);

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND){
                //The file has been deleted from the file server

            }
            InputStream in = connection.getInputStream();

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally{
            connection.disconnect();
        }
    }

    public byte[] getUrlBytes2(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND){
                //The file has been deleted from the file server

            }
            InputStream in = connection.getInputStream();

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally{
            connection.disconnect();
        }
    }


    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public List<WallpaperItem> fetchItems(){
        List<WallpaperItem> items = new ArrayList<>();
        try{
            String url = Uri.parse("https://api.datamarket.azure.com/Bing/Search/v1/Image?Query=%27manchester%27&ImageFilters=%27Size%3AWidth%3A1920%2BSize%3AHeight%3A1080%27&$format=json").toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        }catch(JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        catch(IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        return items;
    }

    private void parseItems(List<WallpaperItem> items, JSONObject jsonBody)
        throws IOException, JSONException{
        JSONObject dataJsonObject = jsonBody.getJSONObject("d");
        JSONArray resultsJsonObject = dataJsonObject.getJSONArray("results");
        JSONObject thumbnailJsonObject;

        for(int i = 0;i<resultsJsonObject.length();i++){
            JSONObject wallpaperJsonObject = resultsJsonObject.getJSONObject(i);

            thumbnailJsonObject = wallpaperJsonObject.getJSONObject("Thumbnail");
            WallpaperItem item = new WallpaperItem();

            item.setUrl(wallpaperJsonObject.getString("MediaUrl"));
            item.setThumbnailURL(thumbnailJsonObject.getString("MediaUrl"));
            items.add(item);
        }
    }
}
