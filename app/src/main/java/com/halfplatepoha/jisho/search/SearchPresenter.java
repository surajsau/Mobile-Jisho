//package com.halfplatepoha.jisho.search;
//
//import android.text.TextUtils;
//
//import com.crashlytics.android.Crashlytics;
//import com.halfplatepoha.jisho.JishoPreference;
//import com.halfplatepoha.jisho.base.BasePresenter;
//import com.halfplatepoha.jisho.model.SearchApi;
//import com.halfplatepoha.jisho.model.SearchResponse;
//import com.halfplatepoha.jisho.model.Word;
//import com.halfplatepoha.jisho.offline.OfflineTask;
//import com.halfplatepoha.jisho.offline.model.ListEntry;
//import com.halfplatepoha.jisho.offline.utils.SearchTask;
//import com.halfplatepoha.jisho.utils.IConstants;
//
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.inject.Named;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * Created by surjo on 21/04/17.
// */
//
//public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {
//
//    SearchApi api;
//
//    OfflineTask offlineTask;
//
//    private String source;
//
//    private String searchString;
//
//    @Inject
//    public SearchPresenter(SearchContract.View view,
//                           SearchApi api,
//                           OfflineTask offlineTask,
//                           @Named(SearchFragment.EXTRA_SOURCE) String source,
//                           @Named(SearchFragment.EXTRA_SEARCH_STRING) String searchString) {
//        super(view);
//        this.api = api;
//        this.source = source;
//        this.offlineTask = offlineTask;
//        this.searchString = searchString;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        view.setSearchString(searchString);
//        search();
//    }
//
//    private void search() {
//        if(!TextUtils.isEmpty(searchString)) {
//            view.hideKeyboard();
//            view.hideError();
//            view.clearData();
//            view.showLoader();
//            view.hideClearButton();
//
//            if(!SearchFragment.SOURCE_HISTORY.equalsIgnoreCase(source))
//                view.saveInHistory(searchString);
//
//            boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);
//
//            if(isOffline) {
//
//                offlineTask.search(searchString, new SearchTask.SearchResultListener() {
//                    @Override
//                    public void onResult(List<ListEntry> entries) {
//                        if(entries != null && !entries.isEmpty()) {
//                            for(ListEntry entry : entries) {
//                                Word word = Word.fromOfflineListEntry(entry);
//                                view.addWordToAdapter(word);
//                            }
//                        } else {
//                            view.showEmptyResultError();
//                        }
//
//                        view.hideLoader();
//                        view.showClearButton();
//                    }
//                });
//
//            } else {
//                api.search(searchString)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.newThread())
//                        .subscribe(new Consumer<SearchResponse>() {
//                            @Override
//                            public void accept(@NonNull SearchResponse searchResponse) throws Exception {
//                                if (searchResponse != null && searchResponse.getData() != null && !searchResponse.getData().isEmpty()) {
//                                    view.showResults();
//                                    for (Word word : searchResponse.getData())
//                                        view.addWordToAdapter(word);
//
//                                } else {
//                                    view.showEmptyResultError();
//                                    view.showClearButton();
//                                }
//
//                                view.hideLoader();
//                                view.showClearButton();
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(@NonNull Throwable throwable) throws Exception {
//                                view.showInternetError();
//                                view.hideLoader();
//                                view.showClearButton();
//                                Crashlytics.logException(throwable);
//                            }
//                        });
//            }
//        }
//    }
//
//    @Override
//    public void search(String searchString) {
//        this.searchString = searchString;
//        search();
//    }
//}
