//package com.halfplatepoha.jisho.search;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.inputmethod.EditorInfo;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.afollestad.materialdialogs.DialogAction;
//import com.afollestad.materialdialogs.MaterialDialog;
//import com.halfplatepoha.jisho.JishoPreference;
//import com.halfplatepoha.jisho.R;
//import com.halfplatepoha.jisho.settings.SettingsActivity;
//import com.halfplatepoha.jisho.SwitchButton;
//import com.halfplatepoha.jisho.analytics.Analytics;
//import com.halfplatepoha.jisho.base.BaseFragment;
//import com.halfplatepoha.jisho.db.History;
//import com.halfplatepoha.jisho.details.DetailsAcitivity;
//import com.halfplatepoha.jisho.model.NetworkModule;
//import com.halfplatepoha.jisho.model.SearchApi;
//import com.halfplatepoha.jisho.model.Word;
//import com.halfplatepoha.jisho.offline.OfflineDbHelper;
//import com.halfplatepoha.jisho.offline.OfflineTask;
//import com.halfplatepoha.jisho.utils.IConstants;
//import com.halfplatepoha.jisho.utils.Utils;
//
//import javax.inject.Inject;
//
//import butterknife.BindView;
//import butterknife.OnCheckedChanged;
//import butterknife.OnClick;
//import io.realm.Realm;
//import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
//
//public class SearchFragment extends BaseFragment<SearchContract.Presenter> implements SearchContract.View,
//        TextView.OnEditorActionListener,
//        TextWatcher,
//        SearchAdapter.MainAdapterActionListener {
//
//    public static final String EXTRA_SEARCH_STRING = "extra_search_string";
//    public static final String EXTRA_SOURCE = "extra_source";
//
//    public static final String SOURCE_HISTORY = "source_history";
//    public static final String SOURCE_BOTTOM_BAR = "source_bottom_bar";
//
//    @BindView(R.id.etSearch)
//    EditText etSearch;
//
//    @BindView(R.id.rlWords)
//    RecyclerView rlWords;
//
//    @BindView(R.id.btnClear)
//    ImageButton btnClear;
//
//    @BindView(R.id.ivSearch)
//    ImageView ivSearch;
//
//    @BindView(R.id.progress)
//    MaterialProgressBar progress;
//
//    @BindView(R.id.tvError)
//    CustomTextView tvError;
//
//    @BindView(R.id.swtchOffline)
//    SwitchButton swtchOffline;
//
//    @BindView(R.id.offlineView)
//    android.view.View offlineView;
//
//    SearchAdapter adapter;
//
//    @
//    @Inject
//    Realm realm;
//
//    private BroadcastReceiver offlineStatusReceiver;
//
//    public static SearchFragment getInstance(String searchString, String source) {
//        SearchFragment fragment = new SearchFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(EXTRA_SOURCE, source);
//        bundle.putString(EXTRA_SEARCH_STRING, searchString);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
//
//    @Override
//    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        adapter = new SearchAdapter(getActivity());
//        adapter.setMainAdapterActionListener(this);
//
//        etSearch.setOnEditorActionListener(this);
//        etSearch.addTextChangedListener(this);
//
//        if (Utils.isFileDowloaded()) {
//            offlineView.setVisibility(android.view.View.VISIBLE);
//
//            boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);
//            swtchOffline.setBackColorRes(isOffline ? R.color.colorOn : R.color.colorOff);
//            swtchOffline.setChecked(isOffline);
//            adapter.setOffline(isOffline);
//        }
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        realm = Realm.getDefaultInstance();
//
//        OfflineTask offlineTask = OfflineTask.getInstance(OfflineDbHelper.getInstance());
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        rlWords.setAdapter(adapter);
//        rlWords.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//
//        offlineStatusReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (intent != null) {
//                    boolean isOffline = intent.getBooleanExtra(IConstants.EXTRA_OFFLINE_STATUS, false);
//
//                    adapter.setOffline(isOffline);
//
//                    if (Utils.isFileDowloaded()) {
//                        offlineView.setVisibility(android.view.View.VISIBLE);
//                        swtchOffline.setChecked(isOffline);
//                        swtchOffline.setBackColorRes(isOffline ? R.color.colorOn : R.color.colorOff);
//                    }
//                }
//            }
//        };
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(offlineStatusReceiver, new IntentFilter(IConstants.OFFLINE_STATUS_BROADCAST_FILTER));
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(offlineStatusReceiver);
//    }
//
//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//            Analytics.getInstance().recordSearch(etSearch.getText().toString());
//            presenter.search(etSearch.getText().toString());
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void addWordToAdapter(Word word) {
//        adapter.addWord(word);
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        float alpha = TextUtils.isEmpty(s) ? 0.5f : 1.0f;
//        ivSearch.setAlpha(alpha);
//        etSearch.setAlpha(alpha);
//        btnClear.setVisibility(TextUtils.isEmpty(s) ? android.view.View.GONE : android.view.View.VISIBLE);
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//    }
//
//    @Override
//    public void clearData() {
//        if (adapter != null)
//            adapter.clearWords();
//    }
//
//    @Override
//    public void saveInHistory(String searchString) {
//        if (realm != null && !realm.isClosed()) {
//            realm.beginTransaction();
//
//            History history = realm.where(History.class).findFirst();
//            if (history == null)
//                history = realm.createObject(History.class);
//
//            history.getHistory().add(searchString);
//
//            realm.commitTransaction();
//        }
//    }
//
//    @Override
//    public void showInternetError() {
//        rlWords.setVisibility(android.view.View.GONE);
//        tvError.setText(getString(R.string.no_internet));
//        tvError.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getActivity(), R.drawable.no_internet), null, null);
//        tvError.setVisibility(android.view.View.VISIBLE);
//    }
//
//    @Override
//    public void hideError() {
//        tvError.setVisibility(android.view.View.GONE);
//    }
//
//    @Override
//    public void showEmptyResultError() {
//        if (getActivity() != null) {
//            tvError.setText(getString(R.string.no_result_error));
//            tvError.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getActivity(), R.drawable.zero_results), null, null);
//            tvError.setVisibility(android.view.View.VISIBLE);
//            rlWords.setVisibility(android.view.View.GONE);
//        }
//    }
//
//    @Override
//    public void showResults() {
//        rlWords.setVisibility(android.view.View.VISIBLE);
//    }
//
//    @Override
//    public void setSearchString(String searchString) {
//        etSearch.setText(searchString);
//    }
//
//    @Override
//    public void onSearchResultClicked(Word word) {
//        Intent intent = DetailsAcitivity.getLaunchIntent(getActivity(), word);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onOfflineSearchResultClicked(int entryId) {
//        Intent intent = DetailsAcitivity.getOfflineLaunchIntent(getActivity(), entryId);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.btnClear)
//    public void clearText() {
//        Analytics.getInstance().recordClick("Clear", "Clear");
//        etSearch.setText("");
//    }
//
//    @OnCheckedChanged(R.id.swtchOffline)
//    public void offlineStatusChange(CompoundButton button, boolean checked) {
//        Analytics.getInstance().recordOfflineSwitch(checked);
//
//        if(checked && !Utils.isFileDowloaded()) {
//
//            showFileDeletedNeedsTobeDownloadedDialog();
//            swtchOffline.setCheckedNoEvent(false);
//
//        } else {
//            JishoPreference.setInPref(IConstants.PREF_OFFLINE_MODE, checked);
//
//            adapter.setOffline(checked);
//            swtchOffline.setBackColorRes(checked ? R.color.colorOn : R.color.colorOff);
//        }
//    }
//
//    private void showFileDeletedNeedsTobeDownloadedDialog() {
//        new MaterialDialog.Builder(getActivity())
//                .title("Offline Jisho missing")
//                .content("The offline Jisho required for using the offline mode seems to be missing from your phone storage. Don't worry you can always download it again! Just go to settings and enable Offline Mode.")
//                .positiveText("Go to settings")
//                .negativeText("Cancel")
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                        startActivity(new Intent(dialog.getContext(), SettingsActivity.class));
//                    }
//                })
//                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                    }
//                })
//                .build()
//                .show();
//    }
//
//    @Override
//    public void showLoader() {
//        progress.setVisibility(android.view.View.VISIBLE);
//    }
//
//    @Override
//    public void hideLoader() {
//        progress.setVisibility(android.view.View.GONE);
//    }
//
//    @Override
//    public void showClearButton() {
//        btnClear.setVisibility(android.view.View.VISIBLE);
//    }
//
//    @Override
//    public void hideClearButton() {
//        btnClear.setVisibility(android.view.View.GONE);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        realm.close();
//    }
//
//    @Override
//    public int getLayoutRes() {
//        return R.layout.fragment_search;
//    }
//
//}
