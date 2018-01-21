package com.halfplatepoha.jisho.v2.injection.modules;

import android.support.v4.app.Fragment;

import com.halfplatepoha.jisho.base.BaseFragmentModule;
import com.halfplatepoha.jisho.history.HistoryContract;
import com.halfplatepoha.jisho.history.HistoryFragment;
import com.halfplatepoha.jisho.history.HistoryPresenter;
import com.halfplatepoha.jisho.v2.injection.FragmentScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * Created by surjo on 20/12/17.
 */

@Module(includes = BaseFragmentModule.class)
public abstract class HistoryModule {

    @Named(BaseFragmentModule.FRAGMENT)
    @Binds
    @FragmentScope
    abstract Fragment fragment(HistoryFragment fragment);

    @Binds
    @FragmentScope
    abstract HistoryContract.View view(HistoryFragment fragment);

    @Binds
    @FragmentScope
    abstract HistoryContract.Presenter presenter(HistoryPresenter presenter);

}
