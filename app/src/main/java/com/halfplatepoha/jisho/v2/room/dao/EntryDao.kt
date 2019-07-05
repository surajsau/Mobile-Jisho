package com.halfplatepoha.jisho.v2.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.halfplatepoha.jisho.v2.room.entity.Entry

@Dao
interface EntryDao {

    @Query("SELECT * FROM entries WHERE Furigana LIKE :query ORDER BY Frequency DESC")
    fun searchWithFurigana(query: String): LiveData<List<Entry>>

}
