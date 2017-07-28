package com.halfplatepoha.jisho;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.UIUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements HistoryFragment.HistoryFragmentActionListener,
        OnTabSelectListener, OnTabReselectListener {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private Snackbar downloadSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent() != null) {
            String searchTerm = getIntent().getStringExtra(IConstants.EXTRA_SEARCH_TERM);
            openSearchFragment(searchTerm);
        }

        if(!JishoPreference.getBooleanFromPref(IConstants.PREF_SHOW_NEW, false))
            UIUtils.showNewItemsDialog(this,
                    "Offline mode!",
                    R.layout.dlg_new_items,
                    "Go to settings",
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                           startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        }
                    });
        JishoPreference.setInPref(IConstants.PREF_SHOW_NEW, true);

        bottomBar.setOnTabSelectListener(this);
        bottomBar.setOnTabReselectListener(this);

        downloadSnackbar = Snackbar.make(background, "Beginning download...", Snackbar.LENGTH_INDEFINITE);
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
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(downloadBroadcastReceiver, new IntentFilter(IConstants.DOWNLOAD_BROADCAST_FILTER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(downloadBroadcastReceiver);
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

    @Override
    public void onTabReSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_options:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }

    private BroadcastReceiver downloadBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent != null){

                Download download = intent.getParcelableExtra(DownloadService.EXTRA_DOWNLOAD);

                if(download.getProgress() == 100){
                    downloadSnackbar.dismiss();

                    Snackbar.make(background, getString(R.string.download_completed), Snackbar.LENGTH_SHORT);
                } else {
                    downloadSnackbar.setText(String.format(getString(R.string.download_progress), download.getProgress()));
                }

                if(!downloadSnackbar.isShown())
                    downloadSnackbar.show();
            }
        }
    };

}
