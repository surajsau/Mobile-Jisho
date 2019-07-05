package com.halfplatepoha.jisho.v2.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.halfplatepoha.jisho.db.v2.HistoryV2
import com.halfplatepoha.jisho.v2.toLiveData
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

class HistoryFragmentViewModel (): ViewModel() {

    var selectedHistoryItem = MutableLiveData<String>()

    fun onHistorySelected(id: String) {
        selectedHistoryItem.value = id
    }

}