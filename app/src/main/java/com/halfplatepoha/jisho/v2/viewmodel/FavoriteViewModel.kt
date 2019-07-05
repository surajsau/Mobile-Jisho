package com.halfplatepoha.jisho.v2.viewmodel

import androidx.lifecycle.ViewModel
import com.halfplatepoha.jisho.db.v2.FavoriteV2
import com.halfplatepoha.jisho.v2.base.BaseDataViewModel
import com.halfplatepoha.jisho.v2.startTransaction
import io.realm.Realm
import io.realm.RealmConfiguration

class FavoriteViewModel(realmConfig: RealmConfiguration) : BaseDataViewModel(realmConfig) {

    fun favoriteEntry(id: String?, word: String?, furigana: String?) {
        id?.run {
            realm.startTransaction()

            val favorite = realm.createObject(FavoriteV2::class.java)
            favorite.id = id
            favorite.furigana = furigana
            favorite.word = word

            realm.commitTransaction()
        }
    }

    fun unFavoriteEntry(id: String?) {
        id?.run {
            realm.startTransaction()

            realm.where(FavoriteV2::class.java)
                    .equalTo("id", id)
                    .findFirst()?.deleteFromRealm()

            realm.commitTransaction()
        }
    }

}