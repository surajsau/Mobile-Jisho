package com.halfplatepoha.jisho.v2

const val KEY_IS_DB_LOADED = "is_db_loaded"

interface IJishoPreference {
    fun setInPref(key: String, value: Boolean)
    fun setInPref(key: String, value: String)
    fun setInPref(key: String, value: Float)
    fun setInPref(key: String, value: Int)
    fun getFromPref(key: String, defaultValue: String = ""): String
    fun getBooleanFromPref(key: String, defaultValue: Boolean = false): Boolean
    fun getLongFromPref(key: String, defaultValue: Long = 0L): Long
    fun getIntFromPref(key: String, defaultValue: Int = 0): Int
    fun removeFromPref(key: String)
}