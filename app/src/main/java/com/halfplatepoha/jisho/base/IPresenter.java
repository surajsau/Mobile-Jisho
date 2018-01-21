package com.halfplatepoha.jisho.base;

/**
 * Created by surjo on 20/12/17.
 */

public interface IPresenter {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onBack();

}
