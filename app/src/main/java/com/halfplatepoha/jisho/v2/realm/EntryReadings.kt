package com.halfplatepoha.jisho.v2.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class EntryReadings: RealmObject() {

    @PrimaryKey
    var _id: Int = 0

    var position: Int? = null
    var kanji: String? = null
    var furigana: String? = null

}

