package com.halfplatepoha.jisho.v2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.halfplatepoha.jisho.v2.base.BaseDataViewModel
import com.halfplatepoha.jisho.v2.realm.Entry
import com.halfplatepoha.jisho.v2.toLiveData
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where

class DetailsViewModel(realmConfiguration: RealmConfiguration) : BaseDataViewModel(realmConfiguration) {

    var entryId = MutableLiveData<String>()

    val details = Transformations.map(entryId) {
        realm.where(Entry::class.java)
                .equalTo("_id", it)
                .findAll()
                .toLiveData()
    }

}