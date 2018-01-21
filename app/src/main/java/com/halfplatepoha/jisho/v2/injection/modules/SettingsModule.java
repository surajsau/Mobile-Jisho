package com.halfplatepoha.jisho.v2.injection.modules;

import android.app.Activity;

import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.v2.injection.ActivityScope;
import com.halfplatepoha.jisho.settings.SettingsActivity;
import com.halfplatepoha.jisho.settings.SettingsContract;
import com.halfplatepoha.jisho.settings.SettingsPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by surjo on 20/12/17.
 */

@Module(includes = BaseActivityModule.class)
public abstract class SettingsModule {

    @Binds
    @ActivityScope
    abstract Activity activity(SettingsActivity activity);

    @Binds
    @ActivityScope
    abstract SettingsContract.View view(SettingsActivity activity);

    @Binds
    @ActivityScope
    abstract SettingsContract.Presenter presenter(SettingsPresenter presenter);

}
