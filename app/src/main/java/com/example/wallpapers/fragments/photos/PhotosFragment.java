package com.example.wallpapers.fragments.photos;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wallpapers.data.PhotosViewModel;
import com.example.wallpapers.R;
import com.example.wallpapers.adapter.PhotoAdapder;
import com.example.wallpapers.data.model.Result;
import com.example.wallpapers.fragments.photo.PhotoViewFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.wallpapers.fragments.photo.PhotoViewFragment.PHOTO_URL;

public class PhotosFragment extends Fragment implements PhotosListView {

    public static final String TAG = "PhotosFragment";
    private final int SPAN_COUNT = 3;

    private RecyclerView recyclerView;
    private PhotoAdapder adapder;
    private ImageView imageViewSearch;
    private EditText editTextSearch;

    private int pageCounter = 1;
    private boolean isLoading = false;
    private boolean isSearch = false;

    private PhotoViewFragment photoViewFragment;
    private PhotosFragmentPresenter photosFragmentPresenter;
    private PhotosViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.photos_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewSearch = view.findViewById(R.id.search_icon);
        editTextSearch = view.findViewById(R.id.edit_text_search);
        editTextSearch.requestFocus();
        recyclerView = view.findViewById(R.id.recycler_view_photo);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        adapder = new PhotoAdapder();
        recyclerView.setAdapter(adapder);
        adapder.setPhotos(new ArrayList<Result>());
        photosFragmentPresenter = new PhotosFragmentPresenter(this);

        viewModel = ViewModelProviders.of(this).get(PhotosViewModel.class);
        viewModel.getPhotos().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                adapder.setPhotos(results);
            }
        });

        if (!isLoading && !isSearch) {
            photosFragmentPresenter.loadSimpleData(pageCounter);
        }

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.deleteAllPhotos();
                adapder.clear();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    imageViewSearch.setImageResource(R.drawable.ic_close_white_24dp);
                    isSearch = true;
                }

                if (s.length() == 0) {
                    imageViewSearch.setImageResource(R.drawable.ic_search_white_24dp);
                    isSearch = false;
                }

                if (isSearch && !isLoading) {
                    pageCounter = 1;
                    photosFragmentPresenter.loadSearchDate(pageCounter, editTextSearch.getText().toString());
                }

            }
        });

        adapder.setOnPhotoClickListener(new PhotoAdapder.OnPhotoClickListener() {
            @Override
            public void onDayClick(int position) {
                photoViewFragment = new PhotoViewFragment();
                //send link
                Bundle bundle = new Bundle();
                bundle.putString(PHOTO_URL, adapder.getPhotos().get(position).getUrls().getRegular());
                photoViewFragment.setArguments(bundle);
                //open big photo
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, photoViewFragment, PhotoViewFragment.TAG)
                        .addToBackStack(PhotoViewFragment.TAG)
                        .commit();
            }
        });

        adapder.setOnReachEndListener(new PhotoAdapder.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                if (!isLoading && isSearch) {
                    photosFragmentPresenter.loadSearchDate(pageCounter, editTextSearch.getText().toString());
                }
                if (!isLoading && !isSearch) {
                    photosFragmentPresenter.loadSimpleData(pageCounter);
                }
            }
        });

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAllPhotos();
                isSearch = false;
                adapder.clear();
                imageViewSearch.setImageResource(R.drawable.ic_search_white_24dp);
                editTextSearch.setText("");
                if (!isLoading && !isSearch) {
                    pageCounter = 1;
                    photosFragmentPresenter.loadSimpleData(pageCounter);
                }

            }
        });
    }

    @Override
    public void showData(List<Result> results) {
        viewModel.insertPhotos(results);
        adapder.setPhotos(results);
        pageCounter++;
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "Internet problem", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        photosFragmentPresenter.disposeDisposable();
        super.onDestroy();
    }
}
