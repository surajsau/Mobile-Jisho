package com.halfplatepoha.jisho;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.db.History;
import com.halfplatepoha.jisho.db.RealmString;
import com.halfplatepoha.jisho.model.NetworkModule;
import com.halfplatepoha.jisho.model.SearchApi;
import com.halfplatepoha.jisho.model.Word;
import com.halfplatepoha.jisho.offline.OfflineDbHelper;
import com.halfplatepoha.jisho.offline.OfflineTask;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.Utils;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.realm.Realm;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class SearchFragment extends BaseFragment implements MainView, TextView.OnEditorActionListener,
        TextWatcher, SearchAdapter.MainAdapterActionListener {

    private static final String EXTRA_SEARCH_STRING = "search_string";

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

    SearchPresenter presenter;

    SearchAdapter adapter;

    Realm realm;

    private BroadcastReceiver offlineStatusReceiver;

    public static SearchFragment getInstance(String searchString) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SEARCH_STRING, searchString);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new SearchAdapter(getActivity());
        adapter.setMainAdapterActionListener(this);

        etSearch.setOnEditorActionListener(this);
        etSearch.addTextChangedListener(this);

        if(getArguments() != null) {
            String searchString = getArguments().getString(EXTRA_SEARCH_STRING);
            presenter.search(searchString);
            etSearch.setText(searchString);
        }

        if(Utils.isFileDowloaded()) {
            offlineView.setVisibility(View.VISIBLE);

            boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);
            swtchOffline.setBackColorRes(isOffline ? R.color.colorOn : R.color.colorOff);
            swtchOffline.setChecked(isOffline);
            adapter.setOffline(isOffline);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        SearchApi api = NetworkModule.provideRetrofit().create(SearchApi.class);
        OfflineTask offlineTask = OfflineTask.getInstance(OfflineDbHelper.getInstance());

        presenter = new SearchPresenter(this, api, offlineTask);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rlWords.setAdapter(adapter);
        rlWords.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        offlineStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent != null) {
                    boolean isOffline = intent.getBooleanExtra(IConstants.EXTRA_OFFLINE_STATUS, false);
                    boolean isDownloaded = intent.getBooleanExtra(IConstants.EXTRA_IS_FILE_DOWNLOADED, false);

                    adapter.setOffline(isOffline);

                    if(isDownloaded) {
                        offlineView.setVisibility(View.VISIBLE);
                        swtchOffline.setChecked(isOffline);
                        swtchOffline.setBackColorRes(isOffline ? R.color.colorOn : R.color.colorOff);
                    }
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(offlineStatusReceiver, new IntentFilter(IConstants.OFFLINE_STATUS_BROADCAST_FILTER));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(offlineStatusReceiver);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH) {
            Analytics.getInstance().recordSearch(etSearch.getText().toString());
            presenter.search(etSearch.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void addWordToAdapter(Word word) {
        adapter.addWord(word);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        float alpha = TextUtils.isEmpty(s) ? 0.5f : 1.0f;
        ivSearch.setAlpha(alpha);
        etSearch.setAlpha(alpha);
        btnClear.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void clearData() {
        if(adapter != null)
            adapter.clearWords();
    }

    @Override
    public void saveInHistory(String searchString) {
        if(realm != null && !realm.isClosed()) {
            realm.beginTransaction();

            History history = realm.where(History.class).findFirst();
            if (history == null)
                history = realm.createObject(History.class);

            RealmString rs = new RealmString();
            rs.setValue(searchString);
            history.getHistory().add(rs);

            realm.commitTransaction();
        }
    }

    @Override
    public void showInternetError() {
        rlWords.setVisibility(View.GONE);
        tvError.setText(getString(R.string.no_internet));
        tvError.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getActivity(), R.drawable.no_internet), null, null);
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyResultError() {
        if(getActivity() != null) {
            tvError.setText(getString(R.string.no_result_error));
            tvError.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getActivity(), R.drawable.zero_results), null, null);
            tvError.setVisibility(View.VISIBLE);
            rlWords.setVisibility(View.GONE);
        }
    }

    @Override
    public void showResults() {
        rlWords.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchResultClicked(Word word) {
        Intent intent = DetailsAcitivity.getLaunchIntent(getActivity(), word);
        startActivity(intent);
    }

    @Override
    public void onOfflineSearchResultClicked(int entryId) {
        Intent intent = DetailsAcitivity.getOfflineLaunchIntent(getActivity(), entryId);
        startActivity(intent);
    }

    @OnClick(R.id.btnClear)
    public void clearText() {
        Analytics.getInstance().recordClick("Clear", "Clear");
        etSearch.setText("");
    }

    @OnCheckedChanged({R.id.swtchOffline})
    public void offlineStatusChange(CompoundButton button, boolean isChecked) {
        switch (button.getId()) {
            case R.id.swtchOffline:
                JishoPreference.setInPref(IConstants.PREF_OFFLINE_MODE, isChecked);

                adapter.setOffline(isChecked);
                swtchOffline.setBackColorRes(isChecked ? R.color.colorOn : R.color.colorOff);
                break;
        }
    }

    @Override
    public void showLoader() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showClearButton() {
        btnClear.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideClearButton() {
        btnClear.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_search;
    }

}
