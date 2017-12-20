package com.halfplatepoha.jisho.injection.modules;

import android.support.v4.app.Fragment;

import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.injection.FragmentScope;
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

    @Named(SearchFragment.EXTRA_SEARCH_STRING)
    @Provides
    static String searchString(SearchFragment fragment) {
        return fragment.getArguments().getString(SearchFragment.EXTRA_SEARCH_STRING);
    }

    @Named(SearchFragment.EXTRA_SOURCE)
    @Provides
    static String searchSource(SearchFragment fragment) {
        return fragment.getArguments().getString(SearchFragment.EXTRA_SEARCH_STRING);
    }

}
