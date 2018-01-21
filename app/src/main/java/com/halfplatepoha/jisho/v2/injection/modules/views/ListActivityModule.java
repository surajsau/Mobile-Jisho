package com.halfplatepoha.jisho.v2.injection.modules.views;

import android.app.Activity;

import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.v2.injection.ActivityScope;
import com.halfplatepoha.jisho.v2.injection.DialogScope;
import com.halfplatepoha.jisho.v2.injection.FragmentScope;
import com.halfplatepoha.jisho.lists.listactivity.ListActivityContract;
import com.halfplatepoha.jisho.lists.listactivity.ListActivityPresenter;
import com.halfplatepoha.jisho.lists.listactivity.ListsActivity;
import com.halfplatepoha.jisho.lists.listsfragment.ListsFragment;
import com.halfplatepoha.jisho.lists.newlistdialog.ListNameDialog;

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

    @ContributesAndroidInjector(modules = {ListsModule.class})
    @FragmentScope
    abstract ListsFragment listsFragment();

    @ContributesAndroidInjector(modules = {ListNameModule.class})
    @DialogScope
    abstract ListNameDialog newListDialog();

}
