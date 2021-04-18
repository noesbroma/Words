package com.example.words.ui.update

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.words.R
import com.example.words.data.InfoWord
import com.example.words.data.InfoWordRecyclerAdapter
import com.example.words.ui.WordsApplication
import com.example.words.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_update.*
import java.io.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class UpdateFragment : Fragment() {
    val FILE = "file.ser"
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun newInstance(): UpdateFragment {
            val fragment = UpdateFragment()

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutManager = LinearLayoutManager(activity?.baseContext)
        infoWordsRecycler.layoutManager = linearLayoutManager

        infoWordsRecycler.adapter = InfoWordRecyclerAdapter(getInfoWordList())

        setUI()
    }


    fun setUI() {
        //wordsList.text = WordsApplication.fileText
        //var wordsList = getList()
        var infoWordList = getInfoWordList()

        wordsCount.text = String.format("%s %s", "Palabras totales: ", infoWordList.size.toString())
    }


    /*fun getList(): List<String> {
        var wordsText: String = WordsApplication.fileText
        wordsText = wordsText.replace("\\s+".toRegex(), " ")
        var list = wordsText.split(" ")
        list = list.distinct()

        return list
    }*/

    fun getInfoWordList(): List<Pair<String, Int>> {
        var wordsText: String = WordsApplication.fileText
        wordsText = wordsText.replace("\\s+".toRegex(), " ")
        var list = wordsText.split(" ")

        //var lista = list.groupingBy { it }.eachCount().filter { it.value > 1 }
        var infoWordList = list.groupBy {it}.mapValues{it.value.count ()}
        //val pairKeyValueList = infoWordList.toList()

        return infoWordList.toList()
    }
}