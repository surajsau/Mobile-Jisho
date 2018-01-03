package com.halfplatepoha.jisho.lists;

import android.app.Activity;

import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.injection.ActivityScope;

import dagger.Binds;
import dagger.Module;

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
    abstract ListActivityContract.Presenter presenter(ListsPresenter presenter);

}
