package com.sankalpchauhan.topnews.repository;

import androidx.lifecycle.MutableLiveData;

import com.sankalpchauhan.topnews.model.APIResponse;
import com.sankalpchauhan.topnews.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NewsRepository {
    public static NewsRepository newsRepository;
    private NewsAPI newsAPI;

    public NewsRepository() {
        newsAPI = RetrofitService.createService(NewsAPI.class);
    }

    public static NewsRepository getInstance() {
        if (newsRepository == null) {
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    public MutableLiveData<APIResponse> getTopNewsBySource(String sourceId) {
        final MutableLiveData<APIResponse> topNewsLiveData = new MutableLiveData<>();
        newsAPI.getTopHeadlinesBySource(sourceId).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful() && response.code() < 300 && response.code() >= 200) {
                    if (response.body().getStatus().equals("ok")) {
                        topNewsLiveData.setValue(response.body());
                    } else {
                        topNewsLiveData.setValue(null);
                    }
                } else {
                    topNewsLiveData.setValue(null);
                    Timber.d("API Request failed with error" + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                t.printStackTrace();
                topNewsLiveData.setValue(null);
            }
        });

        return topNewsLiveData;
    }
}
