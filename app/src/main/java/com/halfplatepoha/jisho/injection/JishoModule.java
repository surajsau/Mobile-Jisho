package com.halfplatepoha.jisho.injection;

import android.app.Application;

import com.halfplatepoha.jisho.Jisho;

import dagger.Binds;
import dagger.Module;

/**
 * Created by surjo on 20/12/17.
 */

@Module(includes = {ActivityModule.class})
public abstract class JishoModule {

    @Binds
    abstract Application application(Jisho application);

}
