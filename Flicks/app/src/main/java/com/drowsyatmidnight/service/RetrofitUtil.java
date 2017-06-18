package com.drowsyatmidnight.service;

import com.drowsyatmidnight.flicks.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by haint on 15/06/2017.
 */

public class RetrofitUtil {
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String BASE_URL_TRAILER = "https://api.themoviedb.org/3/movie/";

    public static Retrofit create(String s){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client())
                .baseUrl(s)
                .build();
    }

    private static OkHttpClient client(){
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url()
                                .newBuilder()
                                .addQueryParameter("api_key", BuildConfig.API_KEY)
                                .build();
                        request = request.newBuilder()
                                .url(url)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }
}
