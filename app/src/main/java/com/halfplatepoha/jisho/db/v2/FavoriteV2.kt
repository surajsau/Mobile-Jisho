package com.halfplatepoha.jisho.db.v2

import io.realm.RealmObject

open class FavoriteV2: RealmObject() {

    var id: String? = null
    var word: String? = null
    var furigana: String? = null

}