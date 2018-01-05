package com.halfplatepoha.jisho.lists.listsfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseFragment;
import com.halfplatepoha.jisho.lists.listdetails.ListDetailActivity;
import com.halfplatepoha.jisho.lists.newlistdialog.NewListName;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;

public class ListsFragment extends BaseFragment<ListContract.Presenter> implements
        ListContract.View, ListContract.Bus {

    public static final String KEY_LIST_MODE = "list_mode";

    @BindView(R.id.rlLists)
    RecyclerView rlLists;

    @BindView(R.id.list_zero_state)
    View listZeroState;

    @Inject
    ListsAdapter listsAdapter;

    public static ListsFragment getInstance(int mode) {
        ListsFragment fragment = new ListsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LIST_MODE, mode);
        fragment.setArguments(bundle);

        return fragment;
    }

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
    public void openListDetailsScreen(String listName) {
        startActivity(ListDetailActivity.getIntent(getContext(), listName));
    }

    @Override
    public void pushListName(ListName ln) {
        EventBus.getDefault().post(ln);
    }
}