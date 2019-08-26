package com.example.wallpapers.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.wallpapers.data.model.Result;

@Database(entities = {Result.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static final String DB_NAME = "photos.db";
    private static AppDataBase appDataBase;
    private static final Object LOCK = new Object();

    public static AppDataBase getInstance(Context context) {
        synchronized (LOCK) {
            if (appDataBase == null) {
                appDataBase = Room.databaseBuilder(context, AppDataBase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
            return appDataBase;
        }
    }

    public abstract PhotosDao photosDao();

}
