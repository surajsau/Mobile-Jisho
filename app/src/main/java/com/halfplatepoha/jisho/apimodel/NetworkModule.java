package com.halfplatepoha.jisho.apimodel;

import com.halfplatepoha.jisho.BuildConfig;
import com.halfplatepoha.jisho.utils.IConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by surjo on 21/04/17.
 */

public class NetworkModule {

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    private static void buildOkHttpClient() {
        if(okHttpClient == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClientBuilder.addInterceptor(loggingInterceptor);
            }

            okHttpClient = httpClientBuilder.build();
        }
    }

    public static Retrofit provideRetrofit() {
        buildOkHttpClient();

        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(IConstants.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .callFactory(okHttpClient)
                    .build();
        }

        return retrofit;
    }

}
