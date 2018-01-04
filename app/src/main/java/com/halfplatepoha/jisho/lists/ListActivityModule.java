package com.halfplatepoha.jisho.lists;

import android.app.Activity;

import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.injection.ActivityScope;
import com.halfplatepoha.jisho.injection.DialogScope;
import com.halfplatepoha.jisho.injection.FragmentScope;
import com.halfplatepoha.jisho.injection.modules.views.ListModule;
import com.halfplatepoha.jisho.injection.modules.views.NewListModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by surjo on 03/01/18.
 */

@Module(includes = BaseActivityModule.class)
public abstract class ListActivityModule {

    @Binds
    @ActivityScope
    abstract Activity activity(ListsActivity activity);

    @Binds
    @ActivityScope
    abstract ListActivityContract.View view(ListsActivity activity);

    @Binds
    @ActivityScope
    abstract ListActivityContract.Presenter presenter(ListActivityPresenter presenter);

    @ContributesAndroidInjector(modules = {ListModule.class})
    @FragmentScope
    abstract ListsFragment listsFragment();

    @ContributesAndroidInjector(modules = {NewListModule.class})
    @DialogScope
    abstract NewListDialog newListDialog();

}
