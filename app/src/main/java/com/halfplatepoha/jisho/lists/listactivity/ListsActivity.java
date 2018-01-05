package com.halfplatepoha.jisho.lists.listactivity;

import android.content.Intent;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseActivity;
import com.halfplatepoha.jisho.base.BaseFragmentActivity;
import com.halfplatepoha.jisho.lists.listsfragment.ListName;
import com.halfplatepoha.jisho.lists.listsfragment.ListsFragment;
import com.halfplatepoha.jisho.lists.listsfragment.ListsPresenter;
import com.halfplatepoha.jisho.lists.newlistdialog.NewListDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

/**
 * Created by surjo on 03/01/18.
 */

public class ListsActivity extends BaseFragmentActivity<ListActivityContract.Presenter> implements ListActivityContract.View {

    public static final String RESULT_LIST_NAME = "list_name";

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_lists;
    }

    @Override
    public void openListFragment() {

        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, ListsFragment.getInstance(ListsPresenter.MODE_ADD_LIST))
                .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListNameReceived(ListName listName) {
        presenter.onListNameReceived(listName.name);
    }

    @Override
    public void setResult(String listName) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_LIST_NAME, listName);
        setResult(RESULT_OK, intent);
    }

    @OnClick(R.id.btnAddToList)
    public void clickAddListName() {
        new NewListDialog().show(supportFragmentManager, NewListDialog.TAG);
    }

}
