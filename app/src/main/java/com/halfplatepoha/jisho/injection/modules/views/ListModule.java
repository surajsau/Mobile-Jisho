package com.halfplatepoha.jisho.injection.modules.views;

import android.support.v4.app.Fragment;

import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.injection.FragmentScope;
import com.halfplatepoha.jisho.lists.ListAdapterContract;
import com.halfplatepoha.jisho.lists.ListAdapterPresenter;
import com.halfplatepoha.jisho.lists.ListContract;
import com.halfplatepoha.jisho.lists.ListsAdapter;
import com.halfplatepoha.jisho.lists.ListsFragment;
import com.halfplatepoha.jisho.lists.ListsPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by surjo on 03/01/18.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class ListModule {

    @Named(BaseFragmentModule.FRAGMENT)
    @Binds
    @FragmentScope
    abstract Fragment fragment(ListsFragment fragment);

    @Binds
    @FragmentScope
    abstract ListContract.Presenter presenter(ListsPresenter presenter);

    @Binds
    @FragmentScope
    abstract ListContract.View view(ListsFragment fragment);

    @Binds
    @FragmentScope
    abstract ListContract.Bus bus(ListsFragment fragment);

    @Binds
    @FragmentScope
    abstract ListAdapterContract.Presenter listAdapterPresenter(ListAdapterPresenter presenter);

    @Provides
    @FragmentScope
    static ListsAdapter listAdapter(ListAdapterContract.Presenter presenter) {
        return new ListsAdapter(presenter);
    }
}
