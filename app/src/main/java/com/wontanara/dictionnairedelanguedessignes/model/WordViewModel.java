package com.wontanara.dictionnairedelanguedessignes.model;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private final WordRepository mRepository;

    private final LiveData<List<Word>> mAllWords;

    private MutableLiveData<String> searchStringLiveData = new MutableLiveData<>("");

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
//        mAllWords = mRepository.getAllWords();
        mAllWords = Transformations.switchMap(searchStringLiveData, string -> {
            if (TextUtils.isEmpty(string)) {
                return mRepository.getAllWords();
            } else {
                return mRepository.getWordsByName(string);
            }
        });
    }

    public LiveData<List<Word>> getAllWords() { return mAllWords; }

    public LiveData<Word> getWord(int id) { return mRepository.getWord(id); }

    public void insert(Word word) { mRepository.insert(word); }

    public void searchStringChanged(String name) {
        searchStringLiveData.setValue(name);
    }

    public String getSearchString() { return searchStringLiveData.getValue(); }
}
