package com.sankalpchauhan.topnews.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sankalpchauhan.topnews.config.Constants.BASE_URL;

public class RetrofitService {

    private static Retrofit retrofit;
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = CachingPolicy.okClientBuilder();
        AuthenticationInterceptor interceptor = new AuthenticationInterceptor();
        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
