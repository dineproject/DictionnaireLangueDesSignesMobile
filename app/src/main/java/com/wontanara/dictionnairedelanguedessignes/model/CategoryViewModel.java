package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class CategoryViewModel extends AndroidViewModel {

    private final CategoryRepository mRepository;

    private final LiveData<List<Category>> mAllCategories;

    public CategoryViewModel (Application application) {
        super(application);
        mRepository = new CategoryRepository(application);
        mAllCategories = mRepository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() { return mAllCategories; }

    public LiveData<CategoryWithWords> getCategoryWithWords(int id) { return mRepository.getCategoryWithWords(id); }

    public void insert(Category category) { mRepository.insert(category); }

    public void insert(CategoryWithWords categoryWithWords) { mRepository.insert(categoryWithWords); }

    void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : Objects.requireNonNull(fileOrDirectory.listFiles()))
                deleteRecursive(child);

        boolean deleted = fileOrDirectory.delete();
        if (!deleted) Log.e("deleteRecursive", "Could not delete file or directory" + fileOrDirectory.getName());
    }

    public void delete(int id) {
        deleteRecursive(new File(getApplication().getExternalFilesDir("") + File.separator + id));
        File zip = new File(getApplication().getExternalFilesDir("") + File.separator + id + ".zip");
        if (zip.exists()) {
            boolean deleted = zip.delete();
            if (!deleted) Log.e("updateCategory", "Could not delete file or directory" + zip.getName());
        }
        mRepository.delete(id);
    }
}
