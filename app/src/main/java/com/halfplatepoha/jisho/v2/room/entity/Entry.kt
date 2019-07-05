package com.halfplatepoha.jisho.v2.room.entity

import androidx.room.*

@Entity(tableName = "entries",
        indices = [Index(name = "entry_entry", value = ["Entry"]),
            Index(name = "entry_furigana", value = ["Furigana"])])
data class Entry(

    @PrimaryKey
    @ColumnInfo(name = "ROWID")
    var _id: Int? = null,

    @ColumnInfo(name = "Entry")
    var entry: String? = null,

    @ColumnInfo(name = "Furigana")
    var furigana: String? = null,

    @ColumnInfo(name = "Summary")
    var summary: String? = null,

    @ColumnInfo(name = "Types", typeAffinity = ColumnInfo.BLOB)
    var types: ByteArray? = null,

    @ColumnInfo(name = "Frequency")
    var frequency: Double? = null

)