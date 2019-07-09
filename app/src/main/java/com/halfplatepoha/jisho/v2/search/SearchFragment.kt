package com.halfplatepoha.jisho.v2.search

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halfplatepoha.jisho.R
import com.halfplatepoha.jisho.v2.base.BaseFragment
import com.halfplatepoha.jisho.v2.detail.DetailActivity
import com.halfplatepoha.jisho.v2.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class SearchFragment : BaseFragment(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val searchFragmentViewModel: SearchFragmentViewModel by currentScope.viewModel(this)

    val searchViewModel: SearchViewModel by viewModel()

    val searchAdapter: SearchAdapter by currentScope.inject()

    override val layoutId: Int
        get() = R.layout.fragment_search

    override fun initUi() {
        searchAdapter.isVerticalScroll = true

        rlWords.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rlWords.adapter = searchAdapter
    }

    override fun setListeners() {
        searchAdapter.listener = {searchFragmentViewModel.onSearchResultSelected(it)}

        btnClear.setOnClickListener { searchFragmentViewModel.clearClicked() }

        etSearch.addTextChangedListener(object : TextWatcher {

            private var previousSearch: String? = ""

            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.run {
                    /*
                        do dynamic search only when length is more than 2.
                        else we'll let user click the search icon explicitly
                     */
                    if(length >= 2) {
                        val currentSearch = toString()
                        if (currentSearch.equals(previousSearch)) {
                            return
                        }

                        previousSearch = currentSearch

                        launch {
                            /*
                                debounce timeout
                             */
                            delay(300)
                            if (!currentSearch.equals(previousSearch))
                                return@launch

                            searchFragmentViewModel.onSearched(currentSearch)
                            searchViewModel.onSearched(currentSearch)
                        }
                    }
                }
            }
        })

    }

    override fun setViewModelObservers() {

        searchFragmentViewModel.searchText.observe(this, Observer { etSearch.setText(it) })

        searchFragmentViewModel.selectedSearchResult.observe(this, Observer {
            startActivity(DetailActivity.getLaunchIntent(context!!, it))
        })

        searchFragmentViewModel.searchResults.observe(this, Observer { searchAdapter.searchResults = it })

        searchViewModel.searchResult.observe(this, Observer { searchFragmentViewModel.onSearchResultsReceived(it) })

        searchFragmentViewModel.crossIconVisibility.observe(this, Observer { btnClear.visibility = if(it) View.VISIBLE else View.GONE })
    }

}