package com.halfplatepoha.jisho;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements DrawerAdapter.DrawerListener,
        HistoryFragment.HistoryFragmentActionListener,
        SearchFragment.SearchFragmentActionListener,
        FavoriteFragment.FavoriteFragmentActionListener{

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.drawer)
    RecyclerView drawer;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    DrawerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new DrawerAdapter(this);
        adapter.setDrawerListener(this);
        drawer.setLayoutManager(new LinearLayoutManager(this));
        drawer.setAdapter(adapter);

        drawerItemSelected(DrawerAdapter.DrawerItem.HOME);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.btnBurger)
    public void toggleDrawer() {
        if(drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            drawerLayout.openDrawer(Gravity.START);
        }
    }

    @Override
    public void drawerItemSelected(DrawerAdapter.DrawerItem item) {
        drawerLayout.closeDrawer(Gravity.START);
        switch (item) {
            case HOME:
                tvTitle.setText("");
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setSearchFragmentActionListener(this);
                openFragment(searchFragment);
                break;

            case ABOUT:
                tvTitle.setText("About");
                openFragment(new AboutFragment());
                break;

            case HISTORY:
                tvTitle.setText("History");
                HistoryFragment historyFragment = new HistoryFragment();
                historyFragment.setHistoryFragmentActionListener(this);
                openFragment(historyFragment);
                break;

            case FAVORITE:
                tvTitle.setText("Favorite");
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                favoriteFragment.setFavoriteFragmentActionListener(this);
                openFragment(favoriteFragment);
                break;
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
        searchFragment.setSearchFragmentActionListener(this);
        openFragment(searchFragment);
    }

    @Override
    public void openFav() {
        drawerItemSelected(DrawerAdapter.DrawerItem.FAVORITE);
    }

    @Override
    public void openHistory() {
        drawerItemSelected(DrawerAdapter.DrawerItem.HISTORY);
    }

    @Override
    public void kanjiCLicked(String kanji) {
        SearchFragment searchFragment = SearchFragment.getInstance(kanji);
        searchFragment.setSearchFragmentActionListener(this);
        openFragment(searchFragment);
    }

    @Override
    public void seeAlso(String seeAlso) {
        SearchFragment searchFragment = SearchFragment.getInstance(seeAlso);
        searchFragment.setSearchFragmentActionListener(this);
        openFragment(searchFragment);
    }
}
