package com.sankalpchauhan.topnews.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sankalpchauhan.topnews.model.APIResponse;
import com.sankalpchauhan.topnews.repository.NewsRepository;

public class MainActivityViewModel extends AndroidViewModel {
    private NewsRepository newsRepository;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        newsRepository = NewsRepository.getInstance();
    }

    public LiveData<APIResponse> getTopNewsBySource(String sourceId){
        return newsRepository.getTopNewsBySource(sourceId);
    }
}
