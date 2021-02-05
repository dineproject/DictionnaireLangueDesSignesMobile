package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository mRepository;

    private final LiveData<List<Category>> mAllCategories;

    public CategoryViewModel (Application application) {
        super(application);
        mRepository = new CategoryRepository(application);
        mAllCategories = mRepository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() { return mAllCategories; }

    public void insert(Category category) { mRepository.insert(category); }
}
