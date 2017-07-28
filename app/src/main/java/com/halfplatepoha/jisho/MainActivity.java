package com.halfplatepoha.jisho;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;

import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.UIUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements HistoryFragment.HistoryFragmentActionListener,
        OnTabSelectListener {

    public static final String MESSAGE_PROGRESS = "download_progress";

    private static final int REQ_SETTINGS = 1001;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent() != null) {
            String searchTerm = getIntent().getStringExtra(IConstants.EXTRA_SEARCH_TERM);
            openSearchFragment(searchTerm);
        }

        if(!JishoPreference.getBooleanFromPref(IConstants.PREF_SHOW_NEW, false))
            UIUtils.showNewItemsDialog(this, R.layout.dlg_new_items);
        JishoPreference.setInPref(IConstants.PREF_SHOW_NEW, true);

        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    private void openSearchFragment(String searchTerm) {
        SearchFragment searchFragment = SearchFragment.getInstance(searchTerm);
        openFragment(searchFragment);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent != null) {
            String searchString = intent.getStringExtra(IConstants.EXTRA_SEARCH_TERM);
            openSearchFragment(searchString);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);

        Intent offlineStatusIntent = new Intent(IConstants.OFFLINE_STATUS_BROADCAST_FILTER);
        offlineStatusIntent.putExtra(IConstants.EXTRA_OFFLINE_STATUS, isOffline);
        sendBroadcast(offlineStatusIntent);
    }

    private void openFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onSearchHistoryStringSelected(String searchString) {
        SearchFragment searchFragment = SearchFragment.getInstance(searchString);
        openFragment(searchFragment);
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_search:
                SearchFragment searchFragment = new SearchFragment();
                openFragment(searchFragment);
                break;

            case R.id.tab_history:
                HistoryFragment historyFragment = new HistoryFragment();
                historyFragment.setHistoryFragmentActionListener(this);
                openFragment(historyFragment);
                break;

            case R.id.tab_favorites:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                openFragment(favoriteFragment);
                break;

            case R.id.tab_options:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(MESSAGE_PROGRESS)){

                Download download = intent.getParcelableExtra("download");
                if(download.getProgress() == 100){

                    Log.e("Progress", "File Download Complete");

                } else {

                    Log.e("Progress", String.format("Downloaded (%d/%d) MB",download.getCurrentFileSize(),download.getTotalFileSize()));

                }
            }
        }
    };

}
