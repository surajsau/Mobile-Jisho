package com.halfplatepoha.jisho.v2.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Entry: RealmObject() {

    @PrimaryKey
    var _id: Int = 0

    var entry: String? = null
    var furigana: String? = null
    var summary: String? = null
    var types: String? = null
    var frequency: Double? = null

}