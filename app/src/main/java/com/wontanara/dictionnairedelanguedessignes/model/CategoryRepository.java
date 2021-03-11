package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class CategoryRepository {

    private final CategoryDao mCategoryDao;
    private final WordDao mWordDao;
    private final LiveData<List<Category>> mAllCategories;

    CategoryRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mCategoryDao = db.categoryDao();
        mWordDao = db.wordDao();
        mAllCategories = mCategoryDao.getAlphabetizedCategories();
    }

    LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    LiveData<CategoryWithWords> getCategoryWithWords(int id) { return mCategoryDao.getCategoryWithWords(id); }

    void insert(Category category) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mCategoryDao.insert(category));
    }

    void insert(CategoryWithWords categoryWithWords) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            mCategoryDao.insert(categoryWithWords.category);
            for (int i = 0 ; i < categoryWithWords.words.size() ; i++) {
                mWordDao.insert(categoryWithWords.words.get(i));
            }
        });
    }

    void delete(int id) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            mCategoryDao.deleteCategory(id);
            mWordDao.deleteWordsFromCategory(id);
        });
    }
}
