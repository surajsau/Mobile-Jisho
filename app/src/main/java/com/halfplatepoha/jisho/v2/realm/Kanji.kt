package com.halfplatepoha.jisho.v2.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Kanji: RealmObject() {

    @PrimaryKey
    var _id: Int = 0

    var onyomi: String? = null
    var kunyomi: String? = null
    var nanori: String? = null
    var radical: Int? = null
    var grade: Int? = null
    var layout: Int? = null
    var skip: String? = null

}