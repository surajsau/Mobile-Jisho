package com.halfplatepoha.jisho.v2.realm

import io.realm.RealmObject

open class EntryReadings: RealmObject() {

    var _id: Int = 0
    var position: Int? = null
    var kanji: String? = null
    var furigana: String? = null

}

