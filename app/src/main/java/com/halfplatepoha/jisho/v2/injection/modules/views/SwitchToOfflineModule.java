package com.halfplatepoha.jisho.v2.injection.modules.views;

import com.halfplatepoha.jisho.v2.injection.DialogScope;
import com.halfplatepoha.jisho.v2.search.SwitchToOfflineContract;
import com.halfplatepoha.jisho.v2.search.SwitchToOfflineDialog;
import com.halfplatepoha.jisho.v2.search.SwitchToOfflinePresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by surjo on 20/01/18.
 */

@Module
public abstract class SwitchToOfflineModule {

    @DialogScope
    @Binds
    abstract SwitchToOfflineContract.View view(SwitchToOfflineDialog dialog);

    @DialogScope
    @Binds
    abstract SwitchToOfflineContract.Presenter presenter(SwitchToOfflinePresenter presenter);

}
