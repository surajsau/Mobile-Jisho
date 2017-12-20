package com.halfplatepoha.jisho.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.halfplatepoha.jisho.injection.FragmentScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by surjo on 20/12/17.
 */

@Module
public class BaseFragmentModule {

    public static final String FRAGMENT = "BaseFragmentModule.fragment";

    public static final String CHILD_FRAGMENT_MANAGER = "BaseFragmentModule.childFragmentManager";

    @Provides
    @Named(CHILD_FRAGMENT_MANAGER)
    @FragmentScope
    static FragmentManager childFragmentManager(@Named(FRAGMENT) Fragment fragment) {
        return fragment.getChildFragmentManager();
    }

}
