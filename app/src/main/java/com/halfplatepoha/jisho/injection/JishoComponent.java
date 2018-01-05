package com.halfplatepoha.jisho.injection;

import android.app.Application;

import com.halfplatepoha.jisho.Jisho;
import com.halfplatepoha.jisho.injection.modules.DataModule;
import com.halfplatepoha.jisho.injection.modules.NetworkModule;
import com.halfplatepoha.jisho.model.SearchApi;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;

/**
 * Created by surjo on 20/12/17.
 */

@Component(modules = {JishoModule.class, DataModule.class, NetworkModule.class})
@AppScope
public interface JishoComponent extends AndroidInjector<Jisho> {

    Application application();

    SearchApi searchApi();

    Realm realm();

    @Component.Builder
    interface Builder {

        @BindsInstance
        abstract Builder application(Jisho application);

        JishoComponent build();

    }

    @Override
    void inject(Jisho application);

}
