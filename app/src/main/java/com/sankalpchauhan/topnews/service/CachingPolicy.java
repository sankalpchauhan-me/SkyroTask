package com.sankalpchauhan.topnews.service;

import com.sankalpchauhan.topnews.config.Constants;
import com.sankalpchauhan.topnews.service.AuthenticationInterceptor;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

import static com.sankalpchauhan.topnews.NewsApp.getAppContext;
import static com.sankalpchauhan.topnews.util.Utility.isOnline;

public class CachingPolicy {
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String PRAGMA = "Pragma";
    public static final String REVALIDATE_STRATEGY = "must-revalidate";
    public static final Integer MAX_AGE = 50*60;
    public static final Integer MAX_FILE_SIZE = 10 * 1024 * 1024; //10 MB
    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header(CACHE_CONTROL);
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains(REVALIDATE_STRATEGY) || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .removeHeader(PRAGMA)
                        .header(CACHE_CONTROL, "public, max-age=" + MAX_AGE)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };

    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!isOnline()) {
                request = request.newBuilder()
                        .removeHeader(PRAGMA)
                        .header(CACHE_CONTROL, "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        }
    };


    static OkHttpClient.Builder okClientBuilder() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File httpCacheDirectory = new File(getAppContext().getCacheDir(), "offlineCache");
        Cache cache = new Cache(httpCacheDirectory, MAX_FILE_SIZE);
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder()
                .cache(cache)
                //Retry Policy
                .addInterceptor(new ErrorInterceptor())
                //Logging
                .addInterceptor(loggingInterceptor)
                //Caching
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE);

        return clientBuilder;
    }

    //Retry Policy
    public static class ErrorInterceptor implements Interceptor {
        Response response;
        int tryCount = 0;

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            response = sendReqeust(chain, request);
            while (response == null && tryCount < Constants.MAX_LIMIT) {
                Timber.d("intercept"+"Request failed - " + tryCount);
                tryCount++;
                try {
                    Thread.sleep(Constants.WAIT_THRESHOLD);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response = sendReqeust(chain, request);
            }
            return chain.proceed(request);
        }

        private Response sendReqeust(Chain chain, Request request) {
            try {
                response = chain.proceed(request);
                if (!response.isSuccessful())
                    return null;
                else
                    return response;
            } catch (IOException e) {
                return null;
            }
        }
    }

}
