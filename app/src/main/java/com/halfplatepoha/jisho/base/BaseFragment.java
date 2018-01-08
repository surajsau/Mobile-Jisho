package com.halfplatepoha.jisho.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.halfplatepoha.jisho.Jisho;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by surjo on 22/04/17.
 */

public abstract class BaseFragment<P extends IPresenter> extends Fragment implements BaseView, HasSupportFragmentInjector {

    @Inject
    protected
    P presenter;

    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;

    @Named(BaseFragmentModule.CHILD_FRAGMENT_MANAGER)
    @Inject
    protected
    FragmentManager mChildFragmentManager;

    @Override
    public void onAttach(Context context) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        // Perform injection here for M (API 23) due to deprecation of onAttach(Activity).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if(presenter != null)
            presenter.onCreate();
    }

    @Override
    public void onStop() {

        if(presenter != null)
            presenter.onStop();

        super.onStop();
    }

    @Override
    public void onPause() {
        if(presenter != null)
            presenter.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(presenter != null)
            presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(presenter != null)
            presenter.onResume();
    }

    @Override
    public void shortToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishScreen() {
        if(getActivity() != null)
            getActivity().finish();
    }

    @Override
    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public Jisho getApplication() {
        return (Jisho)getContext().getApplicationContext();
    }

    protected abstract @LayoutRes int getLayoutRes();

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }

}
