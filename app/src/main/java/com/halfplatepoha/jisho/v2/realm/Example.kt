package com.halfplatepoha.jisho.v2.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Example: RealmObject() {

    @PrimaryKey
    var _id: Int = 0

    var japanese: String? = null
    var english: String? = null

}