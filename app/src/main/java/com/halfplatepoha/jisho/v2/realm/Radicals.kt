package com.halfplatepoha.jisho.v2.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Radicals: RealmObject() {

    @PrimaryKey
    var _id: Int = 0

    var number: Int? = null
    var character: String? = null
    var kanji: String? = null
    var english: String? = null
    var strokes: Int? = null
    var alternates: String? = null
    var isSimplified: Boolean? = null

}