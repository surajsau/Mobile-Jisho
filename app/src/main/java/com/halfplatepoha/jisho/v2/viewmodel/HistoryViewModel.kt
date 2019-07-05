package com.halfplatepoha.jisho.v2.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.halfplatepoha.jisho.db.v2.HistoryV2
import com.halfplatepoha.jisho.v2.base.BaseDataViewModel
import com.halfplatepoha.jisho.v2.toLiveData
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort

class HistoryViewModel(realmConfig: RealmConfiguration): BaseDataViewModel(realmConfig) {

    val historyItems = Transformations.map(realm
            .where(HistoryV2::class.java)
            .sort("timestamp", Sort.DESCENDING)
            .findAll()
            .toLiveData()) { it.toList() }

}