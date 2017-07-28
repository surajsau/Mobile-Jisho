package com.halfplatepoha.jisho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.halfplatepoha.jisho.utils.IConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.swtchOffline)
    SwitchButton swtchOffline;

    @BindView(R.id.offlineStatus)
    TextView offlineStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);
        boolean themeDark = JishoPreference.getBooleanFromPref(IConstants.PREF_THEME_DARK, true);

        swtchOffline.setChecked(isOffline);
        swtchOffline.setBackColorRes(isOffline ? R.color.colorOn : R.color.colorOff);

        offlineStatus.setText(isOffline ? "On" : "Off");
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

    @Override
    public int getLayoutRes() {
        return R.layout.activity_settings;
    }
}
