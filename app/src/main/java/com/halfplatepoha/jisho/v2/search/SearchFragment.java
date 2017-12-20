package com.halfplatepoha.jisho.v2.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    Realm jdbRealm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jdbRealm = Realm.getInstance(getApplication().getJdbConfig());
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
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            Analytics.getInstance().recordSearch(etSearch.getText().toString());
            String searchString = v.getText().toString();

            RealmResults<Entry> entries = jdbRealm.where(Entry.class).equalTo("japanese", searchString).findAll();
            if(entries != null) {
                for(Entry entry : entries) {
                    Log.e("ENTRY", entry.japanese);
                }
            } else {
                Log.e("RESULTS", "no results");
            }
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
