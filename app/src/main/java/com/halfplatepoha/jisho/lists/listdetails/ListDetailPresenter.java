package com.halfplatepoha.jisho.lists.listdetails;

import com.halfplatepoha.jisho.base.BasePresenter;
import com.halfplatepoha.jisho.v2.data.IDataProvider;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.jdb.JishoList;
import com.halfplatepoha.jisho.v2.search.EntriesAdapterContract;
import com.halfplatepoha.jisho.v2.search.EntriesAdapterPresenter;
import com.halfplatepoha.jisho.v2.search.EntryModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by surjo on 05/01/18.
 */

public class ListDetailPresenter extends BasePresenter<ListDetailContract.View> implements ListDetailContract.Presenter, EntriesAdapterPresenter.Listener {

    private String listName;

    private IDataProvider dataProvider;

    private EntriesAdapterContract.Presenter entriesAdapterPresenter;

    @Inject
    public ListDetailPresenter(ListDetailContract.View view,
                               @Named(ListDetailActivity.KEY_LIST_NAME) String listName,
                               IDataProvider dataProvider,
                               EntriesAdapterContract.Presenter entriesAdapterPresenter) {
        super(view);
        this.listName = listName;
        this.dataProvider = dataProvider;
        this.entriesAdapterPresenter = entriesAdapterPresenter;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        entriesAdapterPresenter.attachListener(this);
        entriesAdapterPresenter.setItemViewType(EntriesAdapterPresenter.TYPE_VERTICAL);

        JishoList list = dataProvider.getJishoList(listName);

        if(list != null) {
            view.setTitle(list.name);

            if(list.entries != null) {
                List<EntryModel> entries = new ArrayList<>();

                for(Entry entry : list.entries) {
                    entries.add(EntryModel.newInstance(entry));
                }
            }
        }
    }

    @Override
    public void onItemClick(String tag) {
        String[] pieces = tag.split(":");
        view.openDetailScreen(pieces[0], pieces[1]);
    }

    @Override
    public void onStop() {

        entriesAdapterPresenter.removeListener();

        super.onStop();
    }
}
