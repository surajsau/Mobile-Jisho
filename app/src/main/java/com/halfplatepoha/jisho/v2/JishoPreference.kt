package com.halfplatepoha.jisho.v2

import android.content.Context
import android.content.SharedPreferences

import com.halfplatepoha.jisho.v2.IJishoPreference

class JishoPreference : IJishoPreference {

    private var sharedpreferences: SharedPreferences? = null

    constructor(context: Context, filename: String) {
        if (sharedpreferences == null)
            sharedpreferences = context.getSharedPreferences(filename,
                    Context.MODE_PRIVATE)
    }

    override fun setInPref(key: String, value: Boolean) {
        val editor = sharedpreferences!!.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun setInPref(key: String, value: String) {
        val editor = sharedpreferences!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun setInPref(key: String, value: Float) {
        val editor = sharedpreferences!!.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    override fun setInPref(key: String, value: Int) {
        val editor = sharedpreferences!!.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    override fun getFromPref(key: String, defaultValue: String): String {
        return sharedpreferences!!.getString(key, defaultValue)!!
    }

    override fun getBooleanFromPref(key: String, defaultValue: Boolean): Boolean {
        return sharedpreferences!!.getBoolean(key, defaultValue)
    }

    override fun getLongFromPref(key: String, defaultValue: Long): Long {
        return sharedpreferences!!.getLong(key, defaultValue)
    }

    override fun getIntFromPref(key: String, defaultValue: Int): Int {
        return sharedpreferences!!.getInt(key, defaultValue)
    }

    override fun removeFromPref(key: String) {
        sharedpreferences!!.edit().remove(key).apply()
    }

}