package com.example.wallpapers.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.wallpapers.data.model.Urls;
import com.google.gson.Gson;

public class Conventer {

    @TypeConverter
    public String photosUrlsToString(Urls urls) {
        return new Gson().toJson(urls);
    }

    @TypeConverter
    public Urls stringToUrls(String s) {
        Gson gson = new Gson();
        Urls urls = gson.fromJson(s, Urls.class);
        return urls;
    }

}
