package com.halfplatepoha.jisho.v2.injection.modules.views;

import com.halfplatepoha.jisho.v2.injection.DialogScope;
import com.halfplatepoha.jisho.lists.newlistdialog.ListNameDialog;
import com.halfplatepoha.jisho.lists.newlistdialog.NewListDialogContract;
import com.halfplatepoha.jisho.lists.newlistdialog.NewListDialogPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by surjo on 04/01/18.
 */

@Module
public abstract class ListNameModule {

    @Binds
    @DialogScope
    abstract NewListDialogContract.Presenter presenter(NewListDialogPresenter presenter);

    @Binds
    @DialogScope
    abstract NewListDialogContract.View view(ListNameDialog dialog);

    @Binds
    @DialogScope
    abstract NewListDialogContract.Bus bus(ListNameDialog dialog);

}
