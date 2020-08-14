package com.sankalpchauhan.topnews.repository;

import com.sankalpchauhan.topnews.config.Constants;
import com.sankalpchauhan.topnews.model.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {
    @GET(Constants.TOP_HEADLINES)
    Call<APIResponse> getTopHeadlinesBySource(@Query("sources") String newsSource);
}
