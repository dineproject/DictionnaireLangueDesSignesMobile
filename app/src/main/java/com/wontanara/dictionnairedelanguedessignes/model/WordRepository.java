package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    LiveData<List<Word>> getAllWords() { return mAllWords; }

    LiveData<List<Word>> getWordsByName(String string) { return mWordDao.getAlphabetizedWordsByName(string); }

    LiveData<Word> getWord(int id) { return mWordDao.getWord(id); }

    void insert(Word word) {
        RoomDatabase.databaseWriteExecutor.execute(() -> mWordDao.insert(word));
    }
}
