package com.wontanara.dictionnairedelanguedessignes.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Query("DELETE FROM category_table")
    void deleteAll();

    @Query("SELECT * FROM category_table ORDER BY name ASC")
    LiveData<List<Category>> getAlphabetizedCategories();
}
