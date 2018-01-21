package com.halfplatepoha.jisho.v2.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.apimodel.Word;
import com.halfplatepoha.jisho.base.BaseFragment;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.v2.detail.DetailsActivity;
import com.halfplatepoha.jisho.view.CustomEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by surjo on 20/12/17.
 */

public class SearchFragment extends BaseFragment<SearchContract.Presenter> implements SearchContract.View, TextView.OnEditorActionListener, TextWatcher, SwitchToOfflineDialog.Listener {

    public static final String EXTRA_SEARCH_STRING = "extra_search_string";
    public static final String EXTRA_SOURCE = "extra_source";

    @BindView(R.id.etSearch)
    CustomEditText etSearch;

    @BindView(R.id.rlWords)
    RecyclerView rlWords;

    @BindView(R.id.btnClear)
    ImageButton btnClear;

    @BindView(R.id.ivSearch)
    ImageView ivSearch;

    @BindView(R.id.zero_offline_search)
    View zeroOfflineSearch;

    @BindView(R.id.loader)
    LottieAnimationView loader;

    @Inject
    EntriesAdapter searchAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader.setRepeatMode(LottieDrawable.INFINITE);
        loader.setAnimation("loader.json");

        etSearch.setOnEditorActionListener(this);
        etSearch.addTextChangedListener(this);

        rlWords.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rlWords.setAdapter(searchAdapter);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            Analytics.getInstance().recordSearch(etSearch.getText().toString());

            presenter.search(v.getText().toString());
            hideKeyboard();
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        presenter.search(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void openDetails(String japanese, String furigana) {
        startActivity(DetailsActivity.getLaunchIntent(getContext(), japanese, furigana));
    }

    @Override
    public void showSpinner() {
        loader.setVisibility(View.VISIBLE);
        loader.playAnimation();
    }

    @Override
    public void hideSpinner() {
        loader.setVisibility(View.GONE);
    }

    @Override
    public void changeSearchListOrientation(int currentOrientation) {
        if(currentOrientation == EntriesAdapterPresenter.TYPE_VERTICAL)
            rlWords.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        else
            rlWords.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @OnClick(R.id.btnReportOffline)
    public void clickOffline() {
        presenter.report(etSearch.text());
    }

    @Override
    public void showVerticalSpace() {
//        verticalSpace.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideVerticalSpace() {
//        verticalSpace.setVisibility(View.GONE);
    }

    @Override
    public void openGmailForError(String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_EMAIL, IConstants.DEVELOPER_EMAIL);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        startActivity(intent);
    }

    @Override
    public void hideZeroOffline() {
        zeroOfflineSearch.setVisibility(View.GONE);
    }

    @Override
    public void showZeroOffline() {
        zeroOfflineSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOfflineSwitchConfirmation() {
        SwitchToOfflineDialog dlg = new SwitchToOfflineDialog();
        dlg.setListener(this);
        dlg.show(mChildFragmentManager, SwitchToOfflineDialog.TAG);
    }

    @Override
    public void onConfirm() {
        presenter.onOfflineSwitchConfirm(etSearch.text());
    }
}
