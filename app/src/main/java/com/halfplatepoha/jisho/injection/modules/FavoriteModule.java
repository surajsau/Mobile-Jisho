package com.halfplatepoha.jisho.injection.modules;

import android.support.v4.app.Fragment;

import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.favorite.FavoriteContract;
import com.halfplatepoha.jisho.favorite.FavoriteFragment;
import com.halfplatepoha.jisho.favorite.FavoritePresenter;
import com.halfplatepoha.jisho.injection.FragmentScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by surjo on 20/12/17.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class FavoriteModule {

    @Named(BaseFragmentModule.FRAGMENT)
    @Binds
    @FragmentScope
    abstract Fragment fragment(FavoriteFragment fragment);

    @Binds
    @FragmentScope
    abstract FavoriteContract.View view(FavoriteFragment fragment);

    @Binds
    @FragmentScope
    abstract FavoriteContract.Presenter presenter(FavoritePresenter presenter);

}
