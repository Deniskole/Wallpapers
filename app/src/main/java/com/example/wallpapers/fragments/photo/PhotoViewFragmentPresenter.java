package com.example.wallpapers.fragments.photo;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.wallpapers.util.Constants.IMAGE_FORMAT;
import static com.example.wallpapers.util.Constants.IMAGE_NAME;

public class PhotoViewFragmentPresenter {

    private PhotoView photoView;

    public PhotoViewFragmentPresenter(PhotoView photoView) {
        this.photoView = photoView;
    }

    public void downloadImage(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {

                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fname = IMAGE_NAME + timeStamp + IMAGE_FORMAT;

                File file = new File(root, fname);
                if (file.exists()) file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    photoView.showCompleted();

                } catch (Exception e) {
                    e.printStackTrace();
                    photoView.showError();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                photoView.showError();
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                photoView.showLoading();
            }
        });
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
