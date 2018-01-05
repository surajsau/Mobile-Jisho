package com.halfplatepoha.jisho.injection.modules.views;

import com.halfplatepoha.jisho.injection.DialogScope;
import com.halfplatepoha.jisho.lists.newlistdialog.NewListDialog;
import com.halfplatepoha.jisho.lists.newlistdialog.NewListDialogContract;
import com.halfplatepoha.jisho.lists.newlistdialog.NewListDialogPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by surjo on 04/01/18.
 */

@Module
public abstract class NewListModule {

    @Binds
    @DialogScope
    abstract NewListDialogContract.Presenter presenter(NewListDialogPresenter presenter);

    @Binds
    @DialogScope
    abstract NewListDialogContract.View view(NewListDialog dialog);

    @Binds
    @DialogScope
    abstract NewListDialogContract.Bus bus(NewListDialog dialog);

}
