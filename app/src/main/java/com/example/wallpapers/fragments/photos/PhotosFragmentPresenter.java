package com.example.wallpapers.fragments.photos;

import android.util.Log;

import com.example.wallpapers.data.model.Request;
import com.example.wallpapers.data.model.Result;
import com.example.wallpapers.data.retrofit.ApiFactory;
import com.example.wallpapers.data.retrofit.ApiService;
import com.example.wallpapers.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.wallpapers.util.Constants.CLIENT_ID;
import static com.example.wallpapers.util.Constants.PAGE;
import static com.example.wallpapers.util.Constants.QUERY;

public class PhotosFragmentPresenter {

    private CompositeDisposable compositeDisposable;
    private PhotosListView viewAllPhotos;

    public PhotosFragmentPresenter(PhotosListView viewAllPhotos) {
        this.viewAllPhotos = viewAllPhotos;
    }

    public void loadSimpleData(int page) {
        Log.i("i", "query 1");
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Map<String, String> params = new HashMap<>();
        params.put(PAGE, Integer.toString(page));
        params.put(CLIENT_ID, Utils.UNSPLASH_API_KEY);
        Disposable disposableSimpleData = apiService.getPhotos(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Result>>() {
                    @Override
                    public void accept(List<Result> examples) throws Exception {
                        viewAllPhotos.showData(examples);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        viewAllPhotos.showError();
                    }
                });
        compositeDisposable.add(disposableSimpleData);
    }

    public void loadSearchDate(int page, String query) {
        Log.i("i", "query 2");
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        Map<String, String> params = new HashMap<>();
        params.put(PAGE, Integer.toString(page));
        params.put(QUERY, query);
        params.put(CLIENT_ID, Utils.UNSPLASH_API_KEY);
        Disposable disposableSearchData = apiService.getSearchPhotos(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Request>() {
                    @Override
                    public void accept(Request request) throws Exception {
                        List<Result> list = request.getResults();
                        viewAllPhotos.showData(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        viewAllPhotos.showError();
                    }
                });
        compositeDisposable.add(disposableSearchData);
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

}
