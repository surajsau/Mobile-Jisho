package com.halfplatepoha.jisho.v2

import com.halfplatepoha.jisho.v2.realm.Counter
import com.halfplatepoha.jisho.v2.realm.Entry
import com.halfplatepoha.jisho.v2.realm.EntryReadings
import com.halfplatepoha.jisho.v2.realm.Example
import com.halfplatepoha.jisho.v2.realm.Kana
import com.halfplatepoha.jisho.v2.realm.Kanji
import com.halfplatepoha.jisho.v2.realm.Radicals

import io.realm.annotations.RealmModule

@RealmModule(classes = [Counter::class, Kanji::class, Entry::class, EntryReadings::class, Example::class, Kana::class, Radicals::class])
class OfflineModule
