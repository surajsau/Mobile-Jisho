package com.halfplatepoha.jisho.viewholders;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.view.ViewGroup;

import com.halfplatepoha.jisho.AboutFragment;
import com.halfplatepoha.jisho.FavoriteFragment;
import com.halfplatepoha.jisho.HistoryFragment;
import com.halfplatepoha.jisho.R;
import com.halfplatepoha.jisho.SearchFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by surjo on 18/07/17.
 */

public class SettingsViewHolder implements OnTabSelectListener {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private SettingsActionListener listener;

    private BottomSheetBehavior bottomSheetBehavior;

    public SettingsViewHolder(View v) {
        ButterKnife.bind(this, v);

        bottomBar.setOnTabSelectListener(this);
        bottomSheetBehavior = BottomSheetBehavior.from(v);

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
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void collapse() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public interface SettingsActionListener {
        void onTabSelected(@IdRes int tabId);
    }
}
