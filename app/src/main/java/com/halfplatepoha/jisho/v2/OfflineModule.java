package com.halfplatepoha.jisho.v2;

import com.halfplatepoha.jisho.v2.realm.Counter;
import com.halfplatepoha.jisho.v2.realm.Kanji;

import io.realm.annotations.RealmModule;

@RealmModule(classes = {Counter.class, Kanji.class})
public class OfflineModule {}
