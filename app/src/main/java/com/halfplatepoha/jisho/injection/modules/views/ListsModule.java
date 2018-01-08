package com.halfplatepoha.jisho.injection.modules.views;

import android.support.v4.app.Fragment;

import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.injection.DialogScope;
import com.halfplatepoha.jisho.injection.FragmentScope;
import com.halfplatepoha.jisho.lists.listsfragment.ListAdapterContract;
import com.halfplatepoha.jisho.lists.listsfragment.ListAdapterPresenter;
import com.halfplatepoha.jisho.lists.listsfragment.ListContract;
import com.halfplatepoha.jisho.lists.listsfragment.ListsAdapter;
import com.halfplatepoha.jisho.lists.listsfragment.ListsFragment;
import com.halfplatepoha.jisho.lists.listsfragment.ListsPresenter;
import com.halfplatepoha.jisho.lists.newlistdialog.ListNameDialog;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by surjo on 03/01/18.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class ListsModule {

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

    @Named(ListsFragment.KEY_LIST_MODE)
    @Provides
    @FragmentScope
    static int listMode(ListsFragment fragment) {
        if(fragment.getArguments() != null)
            return fragment.getArguments().getInt(ListsFragment.KEY_LIST_MODE, ListsPresenter.MODE_OPEN_LIST);
        return ListsPresenter.MODE_OPEN_LIST;
    }
}
