package com.halfplatepoha.jisho.v2.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.SwitchButton;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.base.BaseFragment;
import com.halfplatepoha.jisho.injection.modules.DataModule;
import com.halfplatepoha.jisho.jdb.Entry;
import com.halfplatepoha.jisho.v2.detail.DetailsActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by surjo on 20/12/17.
 */

public class SearchFragment extends BaseFragment<SearchContract.Presenter> implements SearchContract.View, TextView.OnEditorActionListener, TextWatcher {

    public static final String EXTRA_SEARCH_STRING = "extra_search_string";
    public static final String EXTRA_SOURCE = "extra_source";

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.rlWords)
    RecyclerView rlWords;

    @BindView(R.id.btnClear)
    ImageButton btnClear;

    @BindView(R.id.ivSearch)
    ImageView ivSearch;

    @BindView(R.id.progress)
    MaterialProgressBar progress;

    @BindView(R.id.tvError)
    TextView tvError;

    @BindView(R.id.swtchOffline)
    SwitchButton swtchOffline;

    @BindView(R.id.offlineView)
    View offlineView;

    @Inject
    SearchOfflineAdapter searchAdapter;

    Realm jdbRealm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jdbRealm = Realm.getDefaultInstance();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etSearch.setOnEditorActionListener(this);
        etSearch.addTextChangedListener(this);

        rlWords.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rlWords.setAdapter(searchAdapter);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            Analytics.getInstance().recordSearch(etSearch.getText().toString());

            presenter.search(v.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void openDetails(String japanese) {
        startActivity(DetailsActivity.getLaunchIntent(getContext(), japanese));
    }

    @Override
    public void showSpinner() {

    }

    @Override
    public void hideSpinner() {

    }
}
