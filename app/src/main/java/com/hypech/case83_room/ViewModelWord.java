package com.hypech.case83_room;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelWord extends AndroidViewModel {

    private RepositoryWord mRepository;
    private final LiveData<List<EntityWord>> mAllWords;

    public ViewModelWord (Application application) {
        super(application);
        mRepository = new RepositoryWord(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<EntityWord>> getAllWords() {
        return mAllWords;
    }

    public void insert(EntityWord word) {
        mRepository.insert(word);
    }
}