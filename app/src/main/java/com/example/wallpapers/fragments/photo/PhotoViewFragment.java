package com.example.wallpapers.fragments.photo;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallpapers.R;
import com.example.wallpapers.util.Utils;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;


public class PhotoViewFragment extends Fragment implements com.example.wallpapers.fragments.photo.PhotoView, View.OnClickListener {

    public static final String TAG = "PhotoViewFragment";
    public static final String PHOTO_URL = "photo_url";
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private PhotoView photoView;
    private Vibrator vibe;
    private String photoUrl;
    private PhotoViewFragmentPresenter photoViewFragmentPresenter;
    private TextView textViewDownload;
    private TextView textViewSetWallpaper;
    private ImageView imageViewDownload;
    private ImageView imageViewSetAsWallpaper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.photo_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewDownload = view.findViewById(R.id.image_view_download);
        textViewDownload = view.findViewById(R.id.text_view_download);
        imageViewSetAsWallpaper = view.findViewById(R.id.image_view_set_wallpaper);
        textViewSetWallpaper = view.findViewById(R.id.text_view_set_wallpaper);
        imageViewDownload.setOnClickListener(this);
        textViewDownload.setOnClickListener(this);
        imageViewSetAsWallpaper.setOnClickListener(this);
        textViewSetWallpaper.setOnClickListener(this);
        photoView = (PhotoView) view.findViewById(R.id.photo_view);
        photoViewFragmentPresenter = new PhotoViewFragmentPresenter(this);
        photoUrl = getArguments().getString(PHOTO_URL, Utils.DEFAULT_WALLPAPER);
        Picasso.get().load(photoUrl).into(photoView);
    }

    public void vibe() {
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
    }

    public void setWallpaper(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getActivity());
                try {
                    wallpaperManager.setBitmap(bitmap);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Log.d("TAG", "onBitmapLoaded: ");
                Toast.makeText(getActivity(), "Wallpaper was installed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
                Toast.makeText(getActivity(), "Loading image failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
                Toast.makeText(getActivity(), "Downloading image", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void showError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCompleted() {
        Toast.makeText(getActivity(), "Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        Toast.makeText(getActivity(), "Loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Set As Wallpaper
            case R.id.text_view_download:
            case R.id.image_view_download:
                vibe();
                setWallpaper(photoUrl);
                break;
            //Download image
            case R.id.text_view_set_wallpaper:
            case R.id.image_view_set_wallpaper:
                vibe();
                Utils.verifyStoragePermissions(getActivity());
                photoViewFragmentPresenter.downloadImage(photoUrl);
                break;
        }
    }
}