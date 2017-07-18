package com.halfplatepoha.jisho;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.halfplatepoha.jisho.utils.IConstants;
import com.halfplatepoha.jisho.utils.UIUtils;
import com.halfplatepoha.jisho.viewholders.SettingsViewHolder;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements
        HistoryFragment.HistoryFragmentActionListener,
        OnTabSelectListener, SettingsViewHolder.SettingsActionListener {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.bottomSheet)
    View bottomSheet;

    private SettingsViewHolder settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(getIntent() != null) {
            String searchTerm = getIntent().getStringExtra(IConstants.EXTRA_SEARCH_TERM);
            openSearchFragment(searchTerm);
        }

        if(!JishoPreference.getBooleanFromPref(IConstants.PREF_SHOW_NEW, false))
            UIUtils.showNewItemsDialog(this, R.layout.dlg_new_items);
        JishoPreference.setInPref(IConstants.PREF_SHOW_NEW, true);

        settings = new SettingsViewHolder(bottomSheet);
        settings.setListener(this);

        TypedValue tv = new TypedValue();
        if(getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            settings.setPeekHeight(TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics()));
        } else {
            settings.setPeekHeight(300);
        }
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
                tvTitle.setText("");
                SearchFragment searchFragment = new SearchFragment();
                openFragment(searchFragment);
                break;

            case R.id.tab_history:
                tvTitle.setText("History");
                HistoryFragment historyFragment = new HistoryFragment();
                historyFragment.setHistoryFragmentActionListener(this);
                openFragment(historyFragment);
                break;

            case R.id.tab_favorites:
                tvTitle.setText("Favorite");
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                openFragment(favoriteFragment);
                break;

            case R.id.tab_options:
                settings.expand();
                break;
        }
    }
}
