package com.halfplatepoha.jisho.injection.modules;

import android.app.Activity;

import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.favorite.FavoriteFragment;
import com.halfplatepoha.jisho.history.HistoryFragment;
import com.halfplatepoha.jisho.home.MainActivity;
import com.halfplatepoha.jisho.home.MainContract;
import com.halfplatepoha.jisho.home.MainPresenter;
import com.halfplatepoha.jisho.injection.ActivityScope;
import com.halfplatepoha.jisho.injection.FragmentScope;
import com.halfplatepoha.jisho.v2.search.SearchFragment;
import com.halfplatepoha.jisho.v2.search.SearchFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by surjo on 20/12/17.
 */

@Module(includes = BaseActivityModule.class)
public abstract class MainModule {

    @Binds
    @ActivityScope
    abstract Activity activity(MainActivity activity);

    @Binds
    @ActivityScope
    abstract MainContract.View view(MainActivity activity);

    @Binds
    @ActivityScope
    abstract MainContract.Presenter presenter(MainPresenter presenter);

    @ContributesAndroidInjector(modules = SearchModule.class)
    @FragmentScope
    abstract SearchFragment searchFragment();

    @ContributesAndroidInjector(modules = HistoryModule.class)
    @FragmentScope
    abstract HistoryFragment historyFragment();

    @ContributesAndroidInjector(modules = FavoriteModule.class)
    @FragmentScope
    abstract FavoriteFragment favoriteFragment();

}
