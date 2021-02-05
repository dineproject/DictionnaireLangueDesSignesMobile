package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class CategoryRepository {

    private CategoryDao mCategoryDao;
    private LiveData<List<Category>> mAllCategories;

    CategoryRepository(Application application) {
        CategoryRoomDatabase db = CategoryRoomDatabase.getDatabase(application);
        mCategoryDao = db.categoryDao();
        mAllCategories = mCategoryDao.getAlphabetizedCategories();
    }

    LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    void insert(Category category) {
        CategoryRoomDatabase.databaseWriteExecutor.execute(() ->{
            mCategoryDao.insert(category);
        });
    }
}
