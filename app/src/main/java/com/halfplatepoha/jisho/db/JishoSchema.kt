package com.halfplatepoha.jisho.db

import com.halfplatepoha.jisho.db.v2.FavoriteV2
import com.halfplatepoha.jisho.db.v2.HistoryV2
import io.realm.annotations.RealmModule

@RealmModule(classes = [HistoryV2::class, FavoriteV2::class])
class JishoSchema