package com.halfplatepoha.jisho;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.halfplatepoha.jisho.utils.IConstants;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.swtchOffline)
    SwitchButton swtchOffline;

    @BindView(R.id.offlineStatus)
    TextView offlineStatus;

    @BindView(R.id.offlineView)
    View offlineView;

    @BindView(R.id.downloadView)
    View downloadView;

    @BindView(R.id.offlineWarning)
    View offlineWarning;

    private Snackbar downloadSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);

        swtchOffline.setChecked(isOffline);
        swtchOffline.setBackColorRes(isOffline ? R.color.colorOn : R.color.colorOff);

        offlineStatus.setText(isOffline ? "On" : "Off");

        if(isFileDowloaded()) {
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
        LocalBroadcastManager.getInstance(this).registerReceiver(downloadBroadcastReceiver, new IntentFilter(IConstants.DOWNLOAD_BROADCAST_FILTER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(downloadBroadcastReceiver);
    }

    @OnCheckedChanged({R.id.swtchOffline})
    public void offlineCheckedChange(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.swtchOffline:
                JishoPreference.setInPref(IConstants.PREF_OFFLINE_MODE, isChecked);

                offlineStatus.setText(isChecked ? "On" : "Off");
                swtchOffline.setBackColorRes(isChecked ? R.color.colorOn : R.color.colorOff);
                break;
        }
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
                        Intent downloadIntent = new Intent(getApplicationContext(), DownloadService.class);
                        startService(downloadIntent);
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

    @OnClick(R.id.back)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.tvAbout)
    public void openAbout() {
        startActivity(SingleFragmentActivity.getLaunchIntent(this, SingleFragmentActivity.FRAG_ABOUT, "About"));
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

    private boolean isFileDowloaded() {
        File externalDirectory = new File(IConstants.STORAGE_DIRECTORY);
        File dbFile = new File(externalDirectory, IConstants.DICTIONARY_FILE_NAME);

        return externalDirectory.exists() && dbFile.exists();
    }

    private BroadcastReceiver downloadBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent != null){

                Download download = intent.getParcelableExtra(DownloadService.EXTRA_DOWNLOAD);
                Log.e("Progress", download.getProgress() + "%");

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
