package com.halfplatepoha.jisho.v2.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val tabIndexLiveData = MutableLiveData<Int>()

    fun onTabSelected(tabId: Int) {
        tabIndexLiveData.value = tabId
    }

}