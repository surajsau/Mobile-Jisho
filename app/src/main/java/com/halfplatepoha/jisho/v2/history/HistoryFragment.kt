package com.halfplatepoha.jisho.v2.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.halfplatepoha.jisho.R
import com.halfplatepoha.jisho.v2.base.BaseFragment
import com.halfplatepoha.jisho.v2.detail.DetailActivity
import com.halfplatepoha.jisho.v2.viewmodel.HistoryViewModel
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment() {

    val historyFragmentViewModel: HistoryFragmentViewModel by currentScope.viewModel(this)

    val historyViewModel: HistoryViewModel by viewModel()

    val historyAdapter: HistoryAdapter by currentScope.inject()

    override val layoutId: Int
        get() = R.layout.fragment_history

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyViewModel.historyItems.observe(this, Observer { historyAdapter.historyItems = it })
    }

    override fun setListeners() {
        historyAdapter.listener = { historyFragmentViewModel.onHistorySelected(it) }
    }

    override fun setViewModelObservers() {
        historyFragmentViewModel.selectedHistoryItem.observe(this, Observer {
            startActivity(DetailActivity.getLaunchIntent(context!!, it))
        })
    }

}