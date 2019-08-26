package com.example.wallpapers.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class Utils {
    //API KEY UNSPLASH.COM
    public static final String UNSPLASH_API_KEY = "1e98a80d950427341a5bc69ad45fce6693567db85862e64a237e5324049d3959";
    public static final String DEFAULT_WALLPAPER = "http://wallpaperswide.com/download/the_amazing_spider_man_2012_film-wallpaper-1600x900.jpg";
    //For API 23+ you need to request the read/write permissions even if they are already in your manifest.
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
