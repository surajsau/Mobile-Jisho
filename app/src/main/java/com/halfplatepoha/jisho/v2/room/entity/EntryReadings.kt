package com.halfplatepoha.jisho.v2.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "entry_readings",
        indices = [Index(name = "entry_readings_index", value = ["EntryID, Position"]),
            Index(name = "entry_readings_kanji_furigana", value = ["Kanji, Furigana"])])
data class EntryReadings(

    @ColumnInfo(name = "EntryID")
    var _id: Int? = null,

    @ColumnInfo(name = "Position")
    var position: Int? = null,

    @ColumnInfo(name = "Kanji")
    var kanji: String? = null,

    @ColumnInfo(name = "Furigana")
    var furigana: String? = null,

    @ColumnInfo(name = "Chunks", typeAffinity = ColumnInfo.BLOB)
    var chunks: ByteArray? = null

)

