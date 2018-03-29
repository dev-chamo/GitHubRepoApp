package com.chamoapp.githubrepoapp;

import android.app.Application;
import android.util.Log;

import com.chamoapp.githubrepoapp.data.GitHubService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalApplication extends Application {
    private Retrofit mRetrofit;
    private GitHubService mGitHubService;

    @Override
    public void onCreate() {
        super.onCreate();
        initAPIClient();
    }

    private void initAPIClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("API LOG", message);
            }
        });

        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mGitHubService = mRetrofit.create(GitHubService.class);
    }

    public GitHubService getGitHubService() {
        return mGitHubService;
    }
}