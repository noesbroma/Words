package com.example.words.ui.management

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.words.ui.WordsApplication

class ManagementViewModel : ViewModel() {
    var infoWordList = listOf<Pair<String, Int>>()
    val onLoadInfoWordsEvent = MutableLiveData<List<Pair<String, Int>>>()

    fun setInfoWordsList(infoList: List<Pair<String, Int>>) {
        infoWordList = infoList
        onLoadInfoWordsEvent.value = infoList
    }


    fun order(type: Int) {
        when (type) {
            1 -> {
                //count
                getInfoWordList()
                infoWordList = infoWordList.sortedBy { it.second }
            }

            2 -> {
                //position
                getInfoWordList()
            }

            3 -> {
                //word
                getInfoWordList()
                infoWordList = infoWordList.sortedBy { it.first }
            }

            else -> getInfoWordList()
        }

        onLoadInfoWordsEvent.value = infoWordList
    }


    fun getInfoWordList() {
        var wordsText: String = WordsApplication.fileText
        wordsText = wordsText.replace("\\s+".toRegex(), " ")
        var list = wordsText.split(" ")

        infoWordList = list.groupBy {it}.mapValues{it.value.count ()}.toList()
    }
}