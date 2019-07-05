package com.halfplatepoha.jisho.v2.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteFragmentViewModel : ViewModel() {

    var selectedFavorite = MutableLiveData<String>()

    fun onFavoriteSelected(id: String) {
        selectedFavorite.value = id
    }


}