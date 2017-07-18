package com.halfplatepoha.jisho;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.halfplatepoha.jisho.utils.UIUtils;

import butterknife.BindView;
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

    SearchApi api;

    MainPresenter presenter;

    SearchAdapter adapter;

    Realm realm;

    SearchFragmentActionListener listener;

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

        rlWords.setAdapter(adapter);
        rlWords.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        etSearch.setOnEditorActionListener(this);
        etSearch.addTextChangedListener(this);

        if(getArguments() != null) {
            String searchString = getArguments().getString(EXTRA_SEARCH_STRING);
            presenter.search(searchString);
            etSearch.setText(searchString);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        api = NetworkModule.provideRetrofit().create(SearchApi.class);

        presenter = new MainPresenter(this, api);
    }

    public void setSearchFragmentActionListener(SearchFragmentActionListener listener) {
        this.listener = listener;
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
        tvError.setText(R.string.no_result_error);
        tvError.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getActivity(), R.drawable.zero_results), null, null);
        tvError.setVisibility(View.VISIBLE);
        rlWords.setVisibility(View.GONE);
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

    @OnClick(R.id.btnFav)
    public void openFav() {
        Analytics.getInstance().recordClick("Search Fav", "Search Fav");
        if(listener != null)
            listener.openFav();
    }

    @OnClick(R.id.btnHistory)
    public void openHistory() {
        Analytics.getInstance().recordClick("Search History", "Search History");
        if(listener != null)
            listener.openHistory();
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

    public interface SearchFragmentActionListener {
        void openFav();
        void openHistory();
    }
}
