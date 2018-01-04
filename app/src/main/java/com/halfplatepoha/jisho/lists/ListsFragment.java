package com.halfplatepoha.jisho.lists;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseFragment;
import com.halfplatepoha.jisho.db.FavouriteWord;
import com.halfplatepoha.jisho.details.DetailsAcitivity;
import com.halfplatepoha.jisho.model.Word;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

public class ListsFragment extends BaseFragment<ListContract.Presenter> implements
        ListContract.View, ListContract.Bus {

    @BindView(R.id.rlLists)
    RecyclerView rlLists;

    @BindView(R.id.list_zero_state)
    View listZeroState;

    @Inject
    ListsAdapter listsAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlLists.setAdapter(listsAdapter);
        rlLists.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewListName(NewListName newListName) {
        presenter.onNewListName(newListName.name);
    }

    @Override
    public void hideZeroState() {
        listZeroState.setVisibility(View.GONE);
    }

    @Override
    public void showZeroState() {
        listZeroState.setVisibility(View.VISIBLE);
    }

    @Override
    public void pushListName(ListName ln) {
        EventBus.getDefault().post(ln);
    }
}
