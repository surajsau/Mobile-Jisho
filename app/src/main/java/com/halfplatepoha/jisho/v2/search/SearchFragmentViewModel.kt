package com.halfplatepoha.jisho.v2.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halfplatepoha.jisho.v2.room.entity.Entry
import io.realm.Realm
import io.realm.RealmResults

class SearchFragmentViewModel(): ViewModel() {

    val selectedSearchResult = MutableLiveData<String>()

    val searchResults = MutableLiveData<List<Word>>()

    fun onSearchResultSelected(entryId: String?) {
        entryId?.run { selectedSearchResult.value = this }
    }

    fun onSearchResultsReceived(result: List<Entry>) {
        searchResults.value = result.map { Word(it._id.toString(), it.furigana, it.entry, it.summary) }
    }

}