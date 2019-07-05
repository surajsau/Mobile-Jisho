package com.halfplatepoha.jisho.v2

import android.widget.EditText
import com.halfplatepoha.jisho.v2.realm.RealmLiveData
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

fun <T: RealmModel> RealmResults<T>.toLiveData() = RealmLiveData<T>(this)

fun EditText.text() = this.text?.toString() ?: ""

fun String.toInt() = Int

fun Realm.startTransaction() = run {
    if(!isInTransaction)
        beginTransaction()
}