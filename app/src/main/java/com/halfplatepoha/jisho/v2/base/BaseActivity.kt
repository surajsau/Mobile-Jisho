package com.halfplatepoha.jisho.v2.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {

    abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setListeners()
        setViewModelObservers()
    }

    open fun setViewModelObservers() {}

    open fun setListeners() {}

}