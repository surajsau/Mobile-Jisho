package com.halfplatepoha.jisho.v2.injection.modules.views;

import android.support.v4.app.Fragment;

import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.v2.injection.FragmentScope;
import com.halfplatepoha.jisho.v2.settings.SettingsContract;
import com.halfplatepoha.jisho.v2.settings.SettingsFragment;
import com.halfplatepoha.jisho.v2.settings.SettingsPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by surjo on 20/01/18.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class SettingsModule {

    @Named(BaseFragmentModule.FRAGMENT)
    @Binds
    @FragmentScope
    abstract Fragment fragment(SettingsFragment fragment);

    @Binds
    @FragmentScope
    abstract SettingsContract.View view(SettingsFragment fragment);

    @Binds
    @FragmentScope
    abstract SettingsContract.Presenter presenter(SettingsPresenter presenter);

    @Binds
    @FragmentScope
    abstract SettingsContract.File file(SettingsFragment fragment);
}
