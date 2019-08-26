package com.example.wallpapers.data.retrofit;


import com.example.wallpapers.data.model.Request;
import com.example.wallpapers.data.model.Result;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("photos/")
    Observable<List<Result>> getPhotos(@QueryMap Map<String, String> params);


    @GET("search/photos/")
    Observable<Request> getSearchPhotos(@QueryMap Map<String, String> params);

}
