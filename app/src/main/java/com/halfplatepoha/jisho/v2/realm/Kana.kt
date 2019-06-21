package com.halfplatepoha.jisho.v2.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Kana : RealmObject() {

    @PrimaryKey
    var _id: Int = 0

    var kana: String? = null
    var derivedId: Int? = null

}