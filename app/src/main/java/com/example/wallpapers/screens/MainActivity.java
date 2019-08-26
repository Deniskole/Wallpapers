package com.example.wallpapers.screens;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.example.wallpapers.R;
import com.example.wallpapers.util.Utils;
import com.example.wallpapers.fragments.photos.PhotosFragment;

public class MainActivity extends FragmentActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private PhotosFragment photosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.verifyStoragePermissions(this);
        photosFragment = new PhotosFragment();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        if (manager.findFragmentByTag(PhotosFragment.TAG) == null) {
            transaction.add(R.id.container, photosFragment, PhotosFragment.TAG);
        }
        transaction.addToBackStack(PhotosFragment.TAG);
        transaction.commit();
    }
}
