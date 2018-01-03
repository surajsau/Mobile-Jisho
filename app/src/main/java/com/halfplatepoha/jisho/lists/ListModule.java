package com.halfplatepoha.jisho.lists;

import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.injection.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by surjo on 03/01/18.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class ListModule {

    @Binds
    @FragmentScope
    abstract ListContract.Presenter presenter(ListsPresenter presenter);

    @Binds
    @FragmentScope
    abstract ListContract.View view(ListsFragment fragment);

    @Binds
    @FragmentScope
    abstract ListAdapterContract.Presenter listAdapterPresenter(ListAdapterPresenter presenter);

    @Provides
    @FragmentScope
    static ListsAdapter listAdapter(ListAdapterContract.Presenter presenter) {
        return new ListsAdapter(presenter);
    }
}
