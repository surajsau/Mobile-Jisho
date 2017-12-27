package com.halfplatepoha.jisho.injection.modules;

import android.support.v4.app.Fragment;

import com.halfplatepoha.jisho.base.BaseAdapterPresenter;
import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.injection.FragmentScope;
import com.halfplatepoha.jisho.v2.search.SearchOfflineAdapter;
import com.halfplatepoha.jisho.v2.search.SearchAdapterContract;
import com.halfplatepoha.jisho.v2.search.SearchAdapterPresenter;
import com.halfplatepoha.jisho.v2.search.SearchContract;
import com.halfplatepoha.jisho.v2.search.SearchFragment;
import com.halfplatepoha.jisho.v2.search.SearchPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

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
    abstract SearchAdapterContract.Presenter adapterPresenter(SearchAdapterPresenter presenter);

    @Provides
    @FragmentScope
    static SearchOfflineAdapter searchAdapter(SearchAdapterContract.Presenter presenter) {
        return new SearchOfflineAdapter(presenter);
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

}
