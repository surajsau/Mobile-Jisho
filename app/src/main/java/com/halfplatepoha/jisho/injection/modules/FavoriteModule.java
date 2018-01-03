package com.halfplatepoha.jisho.injection.modules;

import android.support.v4.app.Fragment;

import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.lists.ListContract;
import com.halfplatepoha.jisho.lists.ListsFragment;
import com.halfplatepoha.jisho.lists.ListsPresenter;
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
    abstract Fragment fragment(ListsFragment fragment);

    @Binds
    @FragmentScope
    abstract ListContract.View view(ListsFragment fragment);

    @Binds
    @FragmentScope
    abstract ListContract.Presenter presenter(ListsPresenter presenter);

}
