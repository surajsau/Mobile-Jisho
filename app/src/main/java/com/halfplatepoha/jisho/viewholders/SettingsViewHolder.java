package com.halfplatepoha.jisho.viewholders;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.halfplatepoha.jisho.AboutFragment;
import com.halfplatepoha.jisho.FavoriteFragment;
import com.halfplatepoha.jisho.HistoryFragment;
import com.halfplatepoha.jisho.JishoPreference;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.SearchFragment;
import com.halfplatepoha.jisho.SwitchButton;
import com.halfplatepoha.jisho.utils.IConstants;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by surjo on 18/07/17.
 */

public class SettingsViewHolder implements OnTabSelectListener, CompoundButton.OnCheckedChangeListener,
        OnTabReselectListener{

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @BindView(R.id.swtch)
    SwitchButton swtch;

    @BindView(R.id.offlineStatus)
    TextView offlineStatus;

    private SettingsActionListener listener;

    private BottomSheetBehavior bottomSheetBehavior;

    public SettingsViewHolder(View v) {
        ButterKnife.bind(this, v);

        bottomBar.setOnTabSelectListener(this);
        bottomBar.setOnTabReselectListener(this);
        bottomSheetBehavior = BottomSheetBehavior.from(v);

        boolean isOffline = JishoPreference.getBooleanFromPref(IConstants.PREF_OFFLINE_MODE, false);
        swtch.setChecked(isOffline);
        swtch.setBackColorRes(isOffline ? R.color.colorOn : R.color.colorOff);
        offlineStatus.setText(isOffline ? "On" : "Off");

        swtch.setOnCheckedChangeListener(this);

        collapse();
    }

    public void setListener(SettingsActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        if(listener != null)
            listener.onTabSelected(tabId);
    }

    public void setPeekHeight(int height) {
        bottomSheetBehavior.setPeekHeight(height);
    }

    public void expand() {
        if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void collapse() {
        if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(listener != null)
            listener.onCheckedChange(isChecked);

        offlineStatus.setText(isChecked ? "On" : "Off");
        swtch.setBackColorRes(isChecked ? R.color.colorOn : R.color.colorOff);
    }

    @Override
    public void onTabReSelected(@IdRes int tabId) {
        if(listener != null)
            listener.onTabReselected(tabId);
    }

    @OnClick(R.id.tvAbout)
    public void openAbout() {
        if(listener != null)
            listener.openAbout();
    }

    @OnClick(R.id.tvLicense)
    public void openLicense() {
        if(listener != null)
            listener.openLicense();
    }

    public interface SettingsActionListener {
        void onTabSelected(@IdRes int tabId);
        void onTabReselected(@IdRes int tabId);
        void onCheckedChange(boolean isChecked);
        void openAbout();
        void openLicense();
    }
}
