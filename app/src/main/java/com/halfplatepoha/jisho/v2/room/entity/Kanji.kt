package com.halfplatepoha.jisho.v2.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.realm.RealmObject

@Entity(tableName = "kanji")
data class Kanji (

    @PrimaryKey
    @ColumnInfo(name = "ROWID")
    var _id: Int? = null,

    @ColumnInfo(name = "OnYomi")
    var onyomi: String? = null,

    @ColumnInfo(name = "OnYomiGroups", typeAffinity = ColumnInfo.BLOB)
    var onyommiGroups: ByteArray? = null,

    @ColumnInfo(name = "KunYomi")
    var kunyomi: String? = null,

    @ColumnInfo(name = "KunYomiGroups", typeAffinity = ColumnInfo.BLOB)
    var kyunyomiGroups: ByteArray? = null,

    @ColumnInfo(name = "Nanori")
    var nanori: String? = null,

    @ColumnInfo(name = "NanoriGroups", typeAffinity = ColumnInfo.BLOB)
    var nanoriGroups: ByteArray? = null,

    @ColumnInfo(name = "Radical")
    var radical: Int? = null,

    @ColumnInfo(name = "Grade")
    var grade: Int? = null,

    @ColumnInfo(name = "StrokeCount")
    var strokeCount: Int? = null,

    @ColumnInfo(name = "Strokes", typeAffinity = ColumnInfo.BLOB)
    var strokes: ByteArray? = null,

    @ColumnInfo(name = "Layout")
    var layout: Int? = null,

    @ColumnInfo(name = "Parts", typeAffinity = ColumnInfo.BLOB)
    var parts: ByteArray? = null,

    @ColumnInfo(name = "Skip")
    var skip: String? = null

)