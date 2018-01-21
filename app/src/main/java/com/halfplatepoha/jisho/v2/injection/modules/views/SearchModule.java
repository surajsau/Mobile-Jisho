package com.halfplatepoha.jisho.v2.injection.modules.views;

import android.support.v4.app.Fragment;

import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.v2.injection.DialogScope;
import com.halfplatepoha.jisho.v2.injection.FragmentScope;
import com.halfplatepoha.jisho.v2.search.EntriesAdapter;
import com.halfplatepoha.jisho.v2.search.EntriesAdapterContract;
import com.halfplatepoha.jisho.v2.search.EntriesAdapterPresenter;
import com.halfplatepoha.jisho.v2.search.SearchContract;
import com.halfplatepoha.jisho.v2.search.SearchFragment;
import com.halfplatepoha.jisho.v2.search.SearchPresenter;
import com.halfplatepoha.jisho.v2.search.SwitchToOfflineDialog;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by surjo on 20/12/17.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class SearchModule {

    @Named(BaseFragmentModule.FRAGMENT)
    @Binds
    @FragmentScope
    abstract Fragment fragment(SearchFragment fragment);

    @Binds
    @FragmentScope
    abstract SearchContract.View view(SearchFragment fragment);

    @Binds
    @FragmentScope
    abstract SearchContract.Presenter presenter(SearchPresenter presenter);

    @Binds
    @FragmentScope
    abstract EntriesAdapterContract.Presenter adapterPresenter(EntriesAdapterPresenter presenter);

    @Provides
    @FragmentScope
    static EntriesAdapter searchAdapter(EntriesAdapterContract.Presenter presenter) {
        return new EntriesAdapter(presenter);
    }

    @Named(SearchFragment.EXTRA_SEARCH_STRING)
    @Provides
    @FragmentScope
    static String searchString(SearchFragment fragment) {
        return fragment.getArguments().getString(SearchFragment.EXTRA_SEARCH_STRING);
    }

    @Named(SearchFragment.EXTRA_SOURCE)
    @Provides
    @FragmentScope
    static String searchSource(SearchFragment fragment) {
        return fragment.getArguments().getString(SearchFragment.EXTRA_SEARCH_STRING);
    }

    @ContributesAndroidInjector(modules = {SwitchToOfflineModule.class})
    @DialogScope
    abstract SwitchToOfflineDialog switchToOfflineDialog();

}
