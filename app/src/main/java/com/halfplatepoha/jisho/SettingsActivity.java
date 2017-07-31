package com.halfplatepoha.jisho;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.CompoundButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.halfplatepoha.jisho.analytics.Analytics;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.Utils;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SettingsActivity extends BaseActivity {

    private static final int REQ_PERM_STORAGE = 101;
    private static final String[] STORAGE_PERMS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @BindView(R.id.swtchOffline)
    SwitchButton swtchOffline;

    @BindView(R.id.offlineView)
    View offlineView;

    @BindView(R.id.downloadView)
    View downloadView;

    @BindView(R.id.offlineWarning)
    View offlineWarning;

    @BindView(R.id.tvDownloading)
    View tvDownloading;

    @BindView(R.id.btnStartDownload)
    View downloadButton;

    private Snackbar downloadSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);

        swtchOffline.setChecked(isOffline);
        swtchOffline.setBackColorRes(isOffline ? R.color.colorOn : R.color.colorOff);

        if(Utils.isFileDowloaded()) {
            downloadView.setVisibility(View.GONE);
            offlineView.setVisibility(View.VISIBLE);
            offlineWarning.setVisibility(View.VISIBLE);
        } else {
            downloadView.setVisibility(View.VISIBLE);
            offlineView.setVisibility(View.GONE);
            offlineWarning.setVisibility(View.GONE);
        }

        downloadSnackbar = Snackbar.make(background, "Beginning download...", Snackbar.LENGTH_INDEFINITE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(downloadBroadcastReceiver, new IntentFilter(IConstants.DOWNLOAD_BROADCAST_FILTER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(downloadBroadcastReceiver);
    }

    @OnCheckedChanged({R.id.swtchOffline})
    public void offlineCheckedChange(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.swtchOffline:
                Analytics.getInstance().recordOfflineSwitch(isChecked);

                JishoPreference.setInPref(IConstants.PREF_OFFLINE_MODE, isChecked);

                swtchOffline.setBackColorRes(isChecked ? R.color.colorOn : R.color.colorOff);
                break;
        }
    }

    @AfterPermissionGranted(REQ_PERM_STORAGE)
    private void checkStoragePersmissionAndStartDownload() {
        if(EasyPermissions.hasPermissions(this, STORAGE_PERMS)) {
            Intent downloadIntent = new Intent(getApplicationContext(), DownloadService.class);
            startService(downloadIntent);
        } else {
            EasyPermissions.requestPermissions(this,
                    "Storage permission required to download Jisho for offline mode",
                    REQ_PERM_STORAGE,
                    STORAGE_PERMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void showDownloadFileDialog() {
        new MaterialDialog.Builder(this)
                .title("Download Offline Jisho")
                .content("To enable offline mode of Jisho, we\'ll have to download the offline dictionary. It\'ll be best if you\'ve good internet connection for uninterrupted download.")
                .positiveText("Download")
                .negativeText("Cancel")
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Analytics.getInstance().recordDownload();

                        if(!Utils.checkInternetConnection(dialog.getContext()))
                            showNoInternetDialog();
                        else
                            checkStoragePersmissionAndStartDownload();

                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showNoInternetDialog() {
        new MaterialDialog.Builder(this)
                .title("Check internet connection")
                .content("There seems to be some issue with the internet connection. Can you please check it once?")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText("OK")
                .build()
                .show();
    }

    @OnClick(R.id.back)
    public void back() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(IConstants.EXTRA_OFFLINE_STATUS, swtchOffline.isChecked());
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    @OnClick(R.id.tvAbout)
    public void openAbout() {
        showDownloadFileDialog();
//        startActivity(SingleFragmentActivity.getLaunchIntent(this, SingleFragmentActivity.FRAG_ABOUT, "About"));
    }

    @OnClick(R.id.tvLicense)
    public void openLicense() {
        startActivity(SingleFragmentActivity.getLaunchIntent(this, SingleFragmentActivity.FRAG_LICENSE, "License"));
    }

    @OnClick(R.id.btnStartDownload)
    public void startDownload() {
        showDownloadFileDialog();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_settings;
    }

    private BroadcastReceiver downloadBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent != null){

                Download download = intent.getParcelableExtra(DownloadService.EXTRA_DOWNLOAD);

                if(download.getProgress() == 100){
                    downloadSnackbar.dismiss();
                    downloadView.setVisibility(View.GONE);
                    offlineView.setVisibility(View.VISIBLE);

                    Snackbar.make(background, getString(R.string.download_completed), Snackbar.LENGTH_SHORT);
                } else {
                    downloadSnackbar.setText(String.format(getString(R.string.download_progress), download.getProgress()));
                }

                if(!downloadSnackbar.isShown()) {
                    downloadButton.setVisibility(View.GONE);
                    tvDownloading.setVisibility(View.VISIBLE);
                    downloadSnackbar.show();
                }
            }
        }
    };
}
