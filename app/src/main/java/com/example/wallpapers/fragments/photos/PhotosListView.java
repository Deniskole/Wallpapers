package com.example.wallpapers.fragments.photos;

import com.example.wallpapers.data.model.Result;

import java.util.List;

public interface PhotosListView {

    void showData (List<Result> results);

    void showError();

}
