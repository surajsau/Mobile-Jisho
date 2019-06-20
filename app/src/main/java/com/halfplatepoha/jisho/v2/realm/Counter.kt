package com.halfplatepoha.jisho.v2.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Counter : RealmObject() {

    @PrimaryKey
    var _id: String = ""

    var count1: String? = null
    var count2: String? = null
    var count3: String? = null
    var count4: String? = null
    var count5: String? = null
    var count6: String? = null
    var count7: String? = null
    var count8: String? = null
    var count9: String? = null
    var count10: String? = null
}
