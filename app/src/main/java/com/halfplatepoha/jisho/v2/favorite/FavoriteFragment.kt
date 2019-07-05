package com.halfplatepoha.jisho.v2.favorite

import androidx.lifecycle.Observer
import com.halfplatepoha.jisho.R
import com.halfplatepoha.jisho.v2.base.BaseFragment
import com.halfplatepoha.jisho.v2.detail.DetailActivity
import com.halfplatepoha.jisho.v2.viewmodel.FavoriteViewModel
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment() {

    val favoriteFragmentViewModel: FavoriteFragmentViewModel by currentScope.viewModel(this)

    val favoriteViewModel: FavoriteViewModel by viewModel()

    val favoriteAdapter: FavoriteAdapter by currentScope.inject()

    override val layoutId: Int
        get() = R.layout.fragment_favorite

    override fun setListeners() {
        favoriteAdapter.listener = { favoriteFragmentViewModel.onFavoriteSelected(it) }
    }

    override fun setViewModelObservers() {
        favoriteFragmentViewModel.selectedFavorite.observe(this, Observer {
            startActivity(DetailActivity.getLaunchIntent(context!!, it))
        })
    }

}