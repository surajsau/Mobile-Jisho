package com.halfplatepoha.jisho.v2.settings;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.SingleFragmentActivity;
import com.halfplatepoha.jisho.SwitchButton;
import com.halfplatepoha.jisho.UpdateDBService;
import com.halfplatepoha.jisho.base.BaseFragment;
import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.view.CustomTextView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment<SettingsContract.Presenter> implements SettingsContract.View, SettingsContract.File {

    private static final int REQ_PERM_STORAGE = 101;
    private static final String[] STORAGE_PERMS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @BindView(R.id.offlineView)
    View offlineView;

    @BindView(R.id.downloadView)
    View downloadView;

    @BindView(R.id.offlineWarning)
    View offlineWarning;

    @BindView(R.id.tvDownloading)
    CustomTextView tvDownloading;

    @BindView(R.id.btnUpdate)
    View downloadButton;

    @BindView(R.id.btnExportDB)
    View btnExportDB;

    @BindView(R.id.tvVersion)
    CustomTextView tvVersion;

    public SettingsFragment() {}

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @OnClick(R.id.tvAbout)
    public void openAbout() {
        startActivity(SingleFragmentActivity.getLaunchIntent(getContext(), SingleFragmentActivity.FRAG_ABOUT, "About"));
    }

    @OnClick(R.id.tvLicense)
    public void openLicense() {
        startActivity(SingleFragmentActivity.getLaunchIntent(getContext(), SingleFragmentActivity.FRAG_LICENSE, "License"));
    }

    @OnClick(R.id.btnUpdate)
    public void clickUpdate() {
        presenter.clickUpdate();
    }

    @OnClick(R.id.btnExportDB)
    public void exportDB() {
        presenter.exportDB();
    }

    @OnClick(R.id.tvReport)
    public void report() {
        presenter.report();
    }

    @Override
    @AfterPermissionGranted(REQ_PERM_STORAGE)
    public void checkStoragePersmissionAndStartDownload() {
        if(EasyPermissions.hasPermissions(getContext(), STORAGE_PERMS)) {

            Intent downloadIntent = new Intent(getApplication(), UpdateDBService.class);
            getApplication().startService(downloadIntent);

        } else {
            EasyPermissions.requestPermissions(this,
                    "Storage permission required to download Jisho for offline mode",
                    REQ_PERM_STORAGE,
                    STORAGE_PERMS);
        }
    }

    @Override
    public File getRealmFile() {
        final File file = new File(Environment.getExternalStorageDirectory().getPath().concat("/jdb.realm"));
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }

        return file;
    }

    @Override
    public void showExportDB() {
        btnExportDB.setVisibility(View.VISIBLE);
    }

    @Override
    public int colorOn() {
        return R.color.colorOn;
    }

    @Override
    public int colorOff() {
        return R.color.colorOff;
    }

    @Override
    public void setOfflineColor(int color) {

    }

    @Override
    public void setOfflineCheck(boolean isOffline) {

    }

    @Override
    public void openGmail(String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, IConstants.DEVELOPER_EMAIL);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);

        startActivity(Intent.createChooser(intent, "Report issue"));
    }

    @Override
    public void setVersion(String version) {
        tvVersion.setText(version);
    }
}
