package com.wontanara.dictionnairedelanguedessignes.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("SELECT * FROM word_table ORDER BY name ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}