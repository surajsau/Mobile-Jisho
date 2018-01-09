package com.halfplatepoha.jisho.base;

import android.view.ViewGroup;

import com.halfplatepoha.jisho.kanji.KanjiNode;

/**
 * Created by surjo on 20/12/17.
 */

public interface IPresenter {

    void onCreate();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onBack();

}
