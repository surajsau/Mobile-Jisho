package com.halfplatepoha.jisho.db

import io.realm.RealmObject

/**
 * Created by surjo on 22/04/17.
 */

open class FavJapanese : RealmObject() {
    var word: String? = null
    var reading: String? = null
}
