package com.halfplatepoha.jisho;

import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.halfplatepoha.jisho.model.SearchApi;
import com.halfplatepoha.jisho.model.SearchResponse;
import com.halfplatepoha.jisho.model.Word;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by surjo on 21/04/17.
 */

public class MainPresenter extends BasePresenter {

    MainView view;

    SearchApi api;

    public MainPresenter(MainView view, SearchApi api) {
        this.view = view;
        this.api = api;
    }

    public void search(String searchString) {
        if(!TextUtils.isEmpty(searchString)) {
            view.hideKeyboard();
            view.hideError();
            view.clearData();
            view.showLoader();
            view.hideClearButton();
            view.saveInHistory(searchString);

            api.search(searchString)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Consumer<SearchResponse>() {
                        @Override
                        public void accept(@NonNull SearchResponse searchResponse) throws Exception {
                            if (searchResponse != null && searchResponse.getData() != null && !searchResponse.getData().isEmpty()) {
                                view.showResults();
                                for (Word word : searchResponse.getData())
                                    view.addWordToAdapter(word);

                            } else {
                                view.showEmptyResultError();
                                view.hideLoader();
                                view.showClearButton();
                            }

                            view.hideLoader();
                            view.showClearButton();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            view.showInternetError();
                            view.hideLoader();
                            view.showClearButton();
                            Crashlytics.logException(throwable);
                        }
                    });
        }
    }
}
