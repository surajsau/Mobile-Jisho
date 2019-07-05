package com.halfplatepoha.jisho.v2.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.realm.RealmObject

@Entity(tableName = "examples")
data class Example (

    @PrimaryKey
    @ColumnInfo(name = "ROWID")
    var _id: Int? = null,

    @ColumnInfo(name = "Japanese")
    var japanese: String = "",

    @ColumnInfo(name = "Readings")
    var readings: String = "",

    @ColumnInfo(name = "Tokens", typeAffinity = ColumnInfo.BLOB)
    var tokens: ByteArray? = null,

    @ColumnInfo(name = "English")
    var english: String = "",

    @ColumnInfo(name = "Verified")
    var verified: Boolean? = null

)