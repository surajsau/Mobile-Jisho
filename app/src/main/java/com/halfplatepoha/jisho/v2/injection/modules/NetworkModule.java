package com.halfplatepoha.jisho.v2.injection.modules;

import com.halfplatepoha.jisho.BuildConfig;
import com.halfplatepoha.jisho.apimodel.SearchApi;
import com.halfplatepoha.jisho.v2.injection.AppScope;
import com.halfplatepoha.jisho.utils.IConstants;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by surjo on 20/12/17.
 */

@Module
public class NetworkModule {

    @Provides
    @AppScope
    HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    @Provides
    @AppScope
    OkHttpClient okHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    @AppScope
    CallAdapter.Factory rxJavaAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @AppScope
    Converter.Factory jacksonConvertorFactory() {
        return JacksonConverterFactory.create();
    }

    @Provides
    @AppScope
    Retrofit retrofit(OkHttpClient client, Converter.Factory converterFactory, CallAdapter.Factory calladapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(IConstants.BASE_URL)
                .addCallAdapterFactory(calladapterFactory)
                .addConverterFactory(converterFactory)
                .callFactory(client)
                .build();
    }

    @Provides
    @AppScope
    SearchApi searchApi(Retrofit retrofit) {
        return retrofit.create(SearchApi.class);
    }
}
