package com.example.words.ui.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.words.ui.WordsApplication

class UpdateViewModel : ViewModel() {
    var infoWordList = listOf<Pair<String, Int>>()
    val onLoadInforWordsEvent = MutableLiveData<List<Pair<String, Int>>>()

    fun setInfoWordsList(infoList: List<Pair<String, Int>>) {
        infoWordList = infoList
        onLoadInforWordsEvent.value = infoList
    }


    fun order() {
        infoWordList = infoWordList.sortedBy { it.second }
        onLoadInforWordsEvent.value = infoWordList
    }


    /*fun getInfoWordList() {
        var wordsText: String = WordsApplication.fileText
        wordsText = wordsText.replace("\\s+".toRegex(), " ")
        var list = wordsText.split(" ")

        infoWordList = list.groupBy {it}.mapValues{it.value.count ()}.toList()
    }*/
}