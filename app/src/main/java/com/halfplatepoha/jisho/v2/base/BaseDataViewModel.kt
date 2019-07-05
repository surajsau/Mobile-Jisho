package com.halfplatepoha.jisho.v2.base

import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.RealmConfiguration

open class BaseDataViewModel : ViewModel {

    val realm: Realm

    constructor(realmConfiguration: RealmConfiguration): super() {
        realm = Realm.getInstance(realmConfiguration)
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

}