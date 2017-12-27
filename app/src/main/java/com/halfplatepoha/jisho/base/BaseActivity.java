package com.halfplatepoha.jisho.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.halfplatepoha.jisho.Jisho;
import com.halfplatepoha.jisho.R;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by surjo on 21/04/17.
 */

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements BaseView {

    @Nullable
    @BindView(R.id.background)
    protected View background;

    @Inject
    protected P presenter;

    @Override
    public void shortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);

        if(presenter != null)
            presenter.onCreate();
    }

    @Override
    protected void onStop() {

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
    protected void onResume() {
        super.onResume();

        if(presenter != null)
            presenter.onResume();

    }

    @Override
    protected void onDestroy() {

        if(presenter != null)
            presenter.onDestroy();

        super.onDestroy();
    }

    protected Jisho getJishoApplication() {
        return (Jisho)getApplicationContext();
    }

    protected abstract @LayoutRes int getLayoutRes();

}
