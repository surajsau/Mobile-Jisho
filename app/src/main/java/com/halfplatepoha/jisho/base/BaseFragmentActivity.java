package com.halfplatepoha.jisho.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by surjo on 21/12/17.
 */

public abstract class BaseFragmentActivity<P extends IPresenter> extends BaseActivity<P> implements HasSupportFragmentInjector {

    @Inject
    protected DispatchingAndroidInjector<Fragment> mFragmentInjector;

    @Inject
    @Named(BaseActivityModule.ACTIVITY_FRAGMENT_MANAGER)
    protected FragmentManager supportFragmentManager;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }

}
