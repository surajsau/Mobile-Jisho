package com.halfplatepoha.jisho.v2

import android.widget.EditText
import com.halfplatepoha.jisho.v2.realm.RealmLiveData
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults

private val hiragana = arrayOf('あ', 'い', 'う', 'え', 'お', 'か', 'き', 'く', 'け', 'こ', 'た', 'ち', 'つ', 'て', 'と', 'ら', 'り', 'る', 'れ', 'ろ', 'な', 'に', 'ぬ', 'ね', 'の', 'ま', 'み', 'む', 'め', 'も', 'や', 'ゆ', 'よ', 'を', 'は', 'ひ', 'ふ', 'へ', 'ほ', 'が', 'ぎ', 'ぐ', 'げ', 'ご', 'だ', 'ぢ', 'づ', 'で', 'ど', 'ぱ', 'ぴ', 'ぷ', 'ぺ', 'ぽ', 'ば', 'び', 'ぶ', 'べ', 'ぼ')

private val katakana = arrayOf('ア', 'イ', 'ウ', 'エ', 'オ', 'カ', 'キ', 'ク', 'ケ', 'コ', 'タ', 'チ', 'ツ', 'テ', 'ト', 'ラ', 'リ', 'ル', 'レ', 'ロ', 'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'マ', 'イ', 'ム', 'メ', 'モ', 'ヤ', 'ユ', 'ヨ', 'ヲ', 'ハ', 'ヒ', 'フ', 'ヘ', 'ホ', 'ガ', 'ギ', 'グ', 'ゲ', 'ゴ', 'ダ', 'デ', 'ヅ', 'デ', 'ド', 'パ', 'ピ', 'プ', 'ペ', 'ポ', 'バ', 'ビ', 'ブ', 'ベ', 'ボ')

fun <T: RealmModel> RealmResults<T>.toLiveData() = RealmLiveData<T>(this)

fun EditText.text() = this.text?.toString() ?: ""

fun String.toInt() = Int

fun Realm.startTransaction() = run {
    if(!isInTransaction)
        beginTransaction()
}