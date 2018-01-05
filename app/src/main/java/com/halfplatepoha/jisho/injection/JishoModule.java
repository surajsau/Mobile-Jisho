package com.halfplatepoha.jisho.injection;

import android.app.Application;

import com.halfplatepoha.jisho.Jisho;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;
import io.realm.Realm;

/**
 * Created by surjo on 20/12/17.
 */

@Module(includes = {AndroidSupportInjectionModule.class, ActivityModule.class})
public abstract class JishoModule {

    @Binds
    abstract Application application(Jisho application);

    @Provides
    static Realm realm() {
        return Realm.getDefaultInstance();
    }

}
