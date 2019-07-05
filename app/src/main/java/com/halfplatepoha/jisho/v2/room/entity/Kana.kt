package com.halfplatepoha.jisho.v2.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.realm.RealmObject

@Entity(tableName = "kana")
data class Kana(

    @PrimaryKey
    @ColumnInfo(name = "ROWID")
    var _id: Int? = null,

    @ColumnInfo(name = "Kana")
    var kana: String? = null,

    @ColumnInfo(name = "DerivedFrom")
    var derivedId: Int? = null,

    @ColumnInfo(name = "Strokes", typeAffinity = ColumnInfo.BLOB)
    var strokes: ByteArray? = null

)