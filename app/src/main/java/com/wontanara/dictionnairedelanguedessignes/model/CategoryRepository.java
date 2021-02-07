package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

class CategoryRepository {

    private CategoryDao mCategoryDao;
    private LiveData<List<Category>> mAllCategories;
    private Category mCategory;

    CategoryRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mCategoryDao = db.categoryDao();
        mAllCategories = mCategoryDao.getAlphabetizedCategories();
    }

    LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    LiveData<CategoryWithWords> getCategoryWithWords(int id) { return mCategoryDao.getCategoryWithWords(id); }

    void insert(Category category) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mCategoryDao.insert(category));
    }
}
