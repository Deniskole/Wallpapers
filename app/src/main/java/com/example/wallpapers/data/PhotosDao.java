package com.example.wallpapers.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.wallpapers.data.model.Result;

import java.util.List;

@Dao
public interface PhotosDao {

    @Query("SELECT*FROM photos")
    LiveData<List<Result>> getAllPhotos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhotos(List<Result> photos);

    @Query("DELETE FROM photos")
    void deleteAllPhotos();

}
