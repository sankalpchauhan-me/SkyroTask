package com.sankalpchauhan.topnews.service;

import com.sankalpchauhan.topnews.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.sankalpchauhan.topnews.NewsApp.getAppContext;

public class AuthenticationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Authorization", getAppContext().getResources().getString(R.string.NEWS_API_KEY));

        Request request = builder.build();
        return chain.proceed(request);
    }
}
