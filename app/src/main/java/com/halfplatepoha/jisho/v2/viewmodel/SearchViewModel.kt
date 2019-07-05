package com.halfplatepoha.jisho.v2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.halfplatepoha.jisho.v2.base.BaseDataViewModel
import com.halfplatepoha.jisho.v2.realm.Entry
import com.halfplatepoha.jisho.v2.room.dao.EntryDao
import com.halfplatepoha.jisho.v2.toLiveData
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.kotlin.where

class SearchViewModel(entryDao: EntryDao) : ViewModel() {

    private val searchWord = MutableLiveData<String>()

    val searchResult = Transformations.switchMap(searchWord) {
        entryDao.searchWithFurigana("%$it%")
    }

    fun onSearched(currentSearch: String?) {
        currentSearch?.run { searchWord.value = this }
    }

}