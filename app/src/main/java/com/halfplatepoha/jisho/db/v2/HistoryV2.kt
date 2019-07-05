package com.halfplatepoha.jisho.db.v2

import io.realm.RealmObject

open class HistoryV2: RealmObject() {
    var id: String? = null
    var timestamp: Long? = null
    var word: String? = null

}