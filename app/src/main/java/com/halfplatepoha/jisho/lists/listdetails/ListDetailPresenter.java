package com.halfplatepoha.jisho.lists.listdetails;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.jdb.Schema;
import com.halfplatepoha.jisho.v2.search.EntriesAdapterContract;
import com.halfplatepoha.jisho.v2.search.EntriesAdapterPresenter;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;

/**
 * Created by surjo on 05/01/18.
 */

public class ListDetailPresenter extends BasePresenter<ListDetailContract.View> implements ListDetailContract.Presenter, EntriesAdapterPresenter.Listener {

    private Realm realm;

    private String listName;

    private EntriesAdapterContract.Presenter entriesAdapterPresenter;

    @Inject
    public ListDetailPresenter(ListDetailContract.View view,
                               @Named(ListDetailActivity.KEY_LIST_NAME) String listName,
                               EntriesAdapterContract.Presenter entriesAdapterPresenter) {
        super(view);
        this.listName = listName;
        this.entriesAdapterPresenter = entriesAdapterPresenter;

        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        entriesAdapterPresenter.attachListener(this);
        entriesAdapterPresenter.setItemViewType(EntriesAdapterPresenter.TYPE_VERTICAL);

        JishoList list = realm.where(JishoList.class).equalTo(Schema.JishoList.NAME, listName).findFirst();

        if(list != null) {
            view.setTitle(list.name);

            entriesAdapterPresenter.setResults(list.entries);
        }
    }

    @Override
    public void onItemClick(String japanese, String furigana) {
        view.openDetailScreen(japanese, furigana);
    }

    @Override
    public void onStop() {

        entriesAdapterPresenter.removeListener();

        super.onStop();
    }
}
