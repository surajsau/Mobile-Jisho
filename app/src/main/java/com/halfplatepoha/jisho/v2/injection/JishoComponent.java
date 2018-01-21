package com.halfplatepoha.jisho.v2.injection;

import android.app.Application;

import com.halfplatepoha.jisho.Jisho;
import com.halfplatepoha.jisho.v2.injection.modules.DataModule;
import com.halfplatepoha.jisho.v2.injection.modules.NetworkModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * Created by surjo on 20/12/17.
 */

@Component(modules = {JishoModule.class, DataModule.class, NetworkModule.class})
@AppScope
public interface JishoComponent extends AndroidInjector<Jisho> {

    Application application();

    @Component.Builder
    interface Builder {

        @BindsInstance
        abstract Builder application(Jisho application);

        JishoComponent build();

    }

    @Override
    void inject(Jisho application);

}
