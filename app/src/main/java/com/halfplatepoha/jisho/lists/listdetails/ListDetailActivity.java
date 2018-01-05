package com.halfplatepoha.jisho.lists.listdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseActivity;
import com.halfplatepoha.jisho.v2.detail.DetailsActivity;
import com.halfplatepoha.jisho.v2.search.EntriesAdapter;

import javax.inject.Inject;

import butterknife.BindView;

public class ListDetailActivity extends BaseActivity<ListDetailContract.Presenter> implements ListDetailContract.View {

    public static final String KEY_LIST_NAME = "list_name";

    @BindView(R.id.tvListTitle)
    TextView tvListTitle;

    @BindView(R.id.rlEntries)
    RecyclerView rlEntries;

    @Inject
    EntriesAdapter adapter;

    public static Intent getIntent(Context context, String listName) {
        Intent intent = new Intent(context, ListDetailActivity.class);
        intent.putExtra(KEY_LIST_NAME, listName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rlEntries.setLayoutManager(new LinearLayoutManager(this));
        rlEntries.setAdapter(adapter);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_list_detail;
    }

    @Override
    public void setTitle(String name) {
        tvListTitle.setText(name);
    }

    @Override
    public void openDetailScreen(String japanese, String furigana) {
        startActivity(DetailsActivity.getLaunchIntent(this, japanese, furigana));
    }
}
