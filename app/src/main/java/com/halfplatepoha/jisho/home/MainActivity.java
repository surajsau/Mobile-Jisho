package com.halfplatepoha.jisho.home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.halfplatepoha.jisho.Download;
import com.halfplatepoha.jisho.DownloadService;
import com.halfplatepoha.jisho.JishoPreference;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.base.BaseFragmentActivity;
import com.halfplatepoha.jisho.lists.listsfragment.ListsFragment;
import com.halfplatepoha.jisho.lists.listsfragment.ListsPresenter;
import com.halfplatepoha.jisho.settings.SettingsActivity;
import com.halfplatepoha.jisho.base.BaseFragment;
import com.halfplatepoha.jisho.history.HistoryFragment;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.UIUtils;
import com.halfplatepoha.jisho.utils.Utils;
import com.halfplatepoha.jisho.v2.search.SearchFragment;
import com.halfplatepoha.jisho.v2.settings.SettingsFragment;
import com.halfplatepoha.jisho.view.CustomTextView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;


import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseFragmentActivity<MainContract.Presenter> implements MainContract.View,
        HistoryFragment.HistoryFragmentActionListener, OnTabSelectListener {

    private static final String[] STORAGE_PERMS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQ_PERM_STORAGE = 103;
    private static final int REQ_SETTINGS = 102;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @BindView(R.id.tvDownloadProgress)
    CustomTextView tvDownloadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String fcmRegId = FirebaseInstanceId.getInstance().getToken();

        bottomBar.setOnTabSelectListener(this);

        if(Utils.isFileDowloaded()) {
            checkForStorageReadWritePermissions();
        } else if(!JishoPreference.getBooleanFromPref(IConstants.PREF_SHOW_NEW, false)) {
            UIUtils.showNewItemsDialog(this,
                    "Offline mode is here!",
                    R.layout.dlg_new_items,
                    "Go to settings",
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            bottomBar.selectTabAtPosition(3, true);
                        }
                    });

            JishoPreference.setInPref(IConstants.PREF_SHOW_NEW, true);

        }
    }

    private void checkForStorageReadWritePermissions() {
        if(!EasyPermissions.hasPermissions(this, STORAGE_PERMS)) {
            new MaterialDialog.Builder(this)
                    .content("Awesome! You already seem to be having the Offline Jisho with you. For proper functioning of the offline mode, the app would require permissions to read Jisho from that file.")
                    .positiveText("OK")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            EasyPermissions.requestPermissions(MainActivity.this,
                                    "Storage permission required to download Jisho for offline mode",
                                    REQ_PERM_STORAGE,
                                    STORAGE_PERMS);
                        }
                    })
                    .build()
                    .show();
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    private void openSearchFragment(String searchTerm) {
//        SearchFragment searchFragment = SearchFragment.getInstance(searchTerm, SearchFragment.SOURCE_BOTTOM_BAR);
//        openFragment(searchFragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(downloadBroadcastReceiver, new IntentFilter(IConstants.DOWNLOAD_BROADCAST_FILTER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(downloadBroadcastReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_SETTINGS:
                if(data != null && resultCode == RESULT_OK) {
                    boolean isOffline = data.getBooleanExtra(IConstants.EXTRA_OFFLINE_STATUS, false);

                    Intent offlineStatusIntent = new Intent(IConstants.OFFLINE_STATUS_BROADCAST_FILTER);
                    offlineStatusIntent.putExtra(IConstants.EXTRA_OFFLINE_STATUS, isOffline);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(offlineStatusIntent);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);

        Intent offlineStatusIntent = new Intent(IConstants.OFFLINE_STATUS_BROADCAST_FILTER);
        offlineStatusIntent.putExtra(IConstants.EXTRA_OFFLINE_STATUS, isOffline);
        LocalBroadcastManager.getInstance(this).sendBroadcast(offlineStatusIntent);
    }

    private void openFragment(BaseFragment fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onSearchHistoryStringSelected(String searchString) {
        bottomBar.removeOnTabSelectListener();
        bottomBar.selectTabAtPosition(0);
        bottomBar.setOnTabSelectListener(this);

//        SearchFragment searchFragment = SearchFragment.getInstance(searchString, SearchFragment.SOURCE_HISTORY);
//        openFragment(searchFragment);
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

            case R.id.tab_lists:
                ListsFragment listsFragment = ListsFragment.getInstance(ListsPresenter.MODE_OPEN_LIST);
                openFragment(listsFragment);
                break;

            case R.id.tab_options:
                SettingsFragment settingsFragment = new SettingsFragment();
                openFragment(settingsFragment);
                break;
        }
    }

    private BroadcastReceiver downloadBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent != null){

                Download download = intent.getParcelableExtra(DownloadService.EXTRA_DOWNLOAD);

                if(download.getProgress() == 100){
                    tvDownloadProgress.setVisibility(View.GONE);

                } else {
                    if(tvDownloadProgress.getVisibility() != View.VISIBLE)
                        tvDownloadProgress.setVisibility(View.VISIBLE);

                    tvDownloadProgress.setText(String.format(getString(R.string.download_progress), download.getProgress()));
                }
            }
        }
    };

}
