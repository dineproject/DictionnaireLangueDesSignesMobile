package com.wontanara.dictionnairedelanguedessignes.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Query("SELECT * FROM category_table ORDER BY name ASC")
    LiveData<List<Category>> getAlphabetizedCategories();

    @Transaction
    @Query("SELECT * FROM category_table WHERE id = :id")
    LiveData<CategoryWithWords> getCategoryWithWords(int id);
}
