package com.example.wallpapers.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.wallpapers.data.model.Result;

import java.util.List;

public class PhotosViewModel extends AndroidViewModel {

    private static AppDataBase db;
    private LiveData<List<Result>> photos;

    public PhotosViewModel(@NonNull Application application) {
        super(application);

        db = AppDataBase.getInstance(application);
        photos = db.photosDao().getAllPhotos();
    }

    public LiveData<List<Result>> getPhotos() {
        return photos;
    }

    @SuppressWarnings("unchecked")
    public void insertPhotos(List<Result> photos) {
        new InsertPhotosTask().execute(photos);
    }

    public static class InsertPhotosTask extends AsyncTask<List<Result>, Void, Void> {
        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Result>... lists) {
            if (lists != null && lists.length > 0) {
                db.photosDao().insertPhotos(lists[0]);
            }
            return null;
        }
    }

    public void deleteAllPhotos() {
        new DeleteAllPhotos().execute();
    }

    private static class DeleteAllPhotos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            db.photosDao().deleteAllPhotos();
            return null;
        }
    }
}
