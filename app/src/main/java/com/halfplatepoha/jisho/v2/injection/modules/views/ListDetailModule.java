package com.halfplatepoha.jisho.v2.injection.modules.views;

import android.app.Activity;

import com.halfplatepoha.jisho.base.BaseActivityModule;
import com.halfplatepoha.jisho.v2.injection.ActivityScope;
import com.halfplatepoha.jisho.lists.listdetails.ListDetailActivity;
import com.halfplatepoha.jisho.lists.listdetails.ListDetailContract;
import com.halfplatepoha.jisho.lists.listdetails.ListDetailPresenter;
import com.halfplatepoha.jisho.v2.search.EntriesAdapter;
import com.halfplatepoha.jisho.v2.search.EntriesAdapterContract;
import com.halfplatepoha.jisho.v2.search.EntriesAdapterPresenter;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by surjo on 05/01/18.
 */

@Module(includes = BaseActivityModule.class)
public abstract class ListDetailModule {

    @Binds
    @ActivityScope
    abstract Activity activity(ListDetailActivity activity);

    @Binds
    @ActivityScope
    abstract ListDetailContract.View view(ListDetailActivity activity);

    @Binds
    @ActivityScope
    abstract ListDetailContract.Presenter presenter(ListDetailPresenter presenter);

    @Binds
    @ActivityScope
    abstract EntriesAdapterContract.Presenter adapterPresenter(EntriesAdapterPresenter presenter);

    @Provides
    @ActivityScope
    static EntriesAdapter adapter(EntriesAdapterContract.Presenter presenter) {
        return new EntriesAdapter(presenter);
    }

    @Named(ListDetailActivity.KEY_LIST_NAME)
    @Provides
    @ActivityScope
    static String listName(ListDetailActivity activity) {
        return activity.getIntent().getStringExtra(ListDetailActivity.KEY_LIST_NAME);
    }

}
