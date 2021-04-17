package com.example.words.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    var text: LiveData<String> = _text

    val txt = MutableLiveData<String>()

    fun setTxt(t: String) {
        txt.value = t
    }
}