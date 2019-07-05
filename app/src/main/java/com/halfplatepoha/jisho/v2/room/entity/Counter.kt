package com.halfplatepoha.jisho.v2.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import io.realm.RealmObject

@Entity(tableName = "counter",
        foreignKeys = [ForeignKey(entity = Entry::class,
                onDelete = ForeignKey.NO_ACTION,
                parentColumns = ["ROWID"],
                childColumns = ["ROWID"])])
data class Counter(

    @PrimaryKey
    @ColumnInfo(name = "ROWID")
    var _id: Int? = null,

    @ColumnInfo(name = "Japanese1")
    var count1: String? = null,

    @ColumnInfo(name = "Japanese2")
    var count2: String? = null,

    @ColumnInfo(name = "Japanese3")
    var count3: String? = null,

    @ColumnInfo(name = "Japanese4")
    var count4: String? = null,

    @ColumnInfo(name = "Japanese5")
    var count5: String? = null,

    @ColumnInfo(name = "Japanese6")
    var count6: String? = null,

    @ColumnInfo(name = "Japanese7")
    var count7: String? = null,

    @ColumnInfo(name = "Japanese8")
    var count8: String? = null,
    
    @ColumnInfo(name = "Japanese9")
    var count9: String? = null,
    
    @ColumnInfo(name = "Japanese10")
    var count10: String? = null
)
