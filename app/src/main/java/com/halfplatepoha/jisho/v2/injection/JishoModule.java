package com.halfplatepoha.jisho.v2.injection;

import android.app.Application;

import com.halfplatepoha.jisho.Jisho;

import dagger.Binds;
import dagger.Module;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by surjo on 20/12/17.
 */

@Module(includes = {AndroidSupportInjectionModule.class, ActivityModule.class})
public abstract class JishoModule {

    @Binds
    @AppScope
    abstract Application application(Jisho application);

}
