package com.halfplatepoha.jisho.v2.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.halfplatepoha.jisho.*
import com.halfplatepoha.jisho.v2.base.BaseActivity
import com.halfplatepoha.jisho.v2.favorite.FavoriteFragment
import com.halfplatepoha.jisho.v2.history.HistoryFragment
import com.halfplatepoha.jisho.v2.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.EasyPermissions

class MainActivity: BaseActivity() {

    val homeViewModel by currentScope.viewModel<HomeViewModel>(this)

    override val layout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            EasyPermissions.requestPermissions(this, "Need read permission", 101, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun setListeners() {
        bottomBar.setOnTabSelectListener { homeViewModel.onTabSelected(it) }
    }

    override fun setViewModelObservers() {
        homeViewModel.tabIndexLiveData.observe(this, Observer {
            when(it) {
                R.id.tab_search -> {
                    val searchFragment = SearchFragment()
                    openFragment(searchFragment)
                }

                R.id.tab_history -> {
                    val historyFragment = HistoryFragment()
                    openFragment(historyFragment)
                }

                R.id.tab_favorites -> {
                    val favoriteFragment = FavoriteFragment()
                    openFragment(favoriteFragment)
                }

                R.id.tab_options -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
            }
        })
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }
}