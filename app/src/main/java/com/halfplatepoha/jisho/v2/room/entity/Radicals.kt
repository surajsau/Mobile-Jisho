package com.halfplatepoha.jisho.v2.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "radicals",
        indices = [Index(name = "radical_number", value = ["Number"])])
data class Radicals(

        @PrimaryKey
        @ColumnInfo(name = "ROWID")
        var _id: Int? = null,

        @ColumnInfo(name = "Number")
        var number: Int? = null,

        @ColumnInfo(name = "Character")
        var character: String? = null,

        @ColumnInfo(name = "CharacterKanji")
        var characterKanji: String? = null,

        @ColumnInfo(name = "NameEnglish")
        var english: String? = null,

        @ColumnInfo(name = "Strokes")
        var strokes: Int? = null,

        @ColumnInfo(name = "Alternates")
        var alternates: String? = null,

        @ColumnInfo(name = "IsSimplified")
        var isSimplified: Boolean? = null,

        @ColumnInfo(name = "Kanji", typeAffinity = ColumnInfo.BLOB)
        var kanji: ByteArray? = null

)