package com.halfplatepoha.jisho.injection;

import com.halfplatepoha.jisho.home.MainActivity;
import com.halfplatepoha.jisho.injection.modules.views.DetailModule;
import com.halfplatepoha.jisho.injection.modules.views.MainModule;
import com.halfplatepoha.jisho.injection.modules.SettingsModule;
import com.halfplatepoha.jisho.settings.SettingsActivity;
import com.halfplatepoha.jisho.v2.detail.DetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by surjo on 20/12/17.
 */

@Module
public abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {SettingsModule.class})
    abstract SettingsActivity settingsActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {DetailModule.class})
    abstract DetailsActivity detailsActivity();

}
