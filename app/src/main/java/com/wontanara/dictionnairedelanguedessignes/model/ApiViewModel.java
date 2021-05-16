package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ApiViewModel extends AndroidViewModel {

    private final ApiRepository mRepository;

    public ApiViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ApiRepository(application);
    }

    public LiveData<Resource<List<DownloadableCategory>>> getAllDownloadableCategories() {
        return mRepository.getDownloadableCategories();
    }

    public LiveData<Resource<CategoryWithWords>> downloadCategory(DownloadableCategory category) {
        return mRepository.downloadCategory(category);
    }

    public LiveData<Resource<Boolean>> postSuggestion(String word, String definition, String imageUri, Uri videoUri) {
        return mRepository.postSuggestion(word, definition, imageUri, videoUri);
    }
}
