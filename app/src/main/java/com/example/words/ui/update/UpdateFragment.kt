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
import com.example.words.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class UpdateFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: UpdateViewModel by viewModel()
    private var infoWordsList = listOf<Pair<String, Int>>()

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

        viewModel.getInfoWordList()
        viewModel.setInfoWordsList(viewModel.infoWordList)

        infoWordsRecycler.adapter = InfoWordRecyclerAdapter(viewModel.infoWordList)

        setUI()
        setListeners()
        observeViewModel()
    }


    fun setUI() {
        wordsCount.text = String.format("%s %s", "Palabras totales: ", viewModel.infoWordList.size.toString())
    }


    fun setListeners() {
        order.setOnClickListener{
            viewModel.order()
            infoWordsRecycler.adapter?.notifyDataSetChanged()
        }
    }


    private fun observeViewModel() {
        viewModel.onLoadInforWordsEvent.observe(viewLifecycleOwner, androidx.lifecycle.Observer { infoWords ->
            linearLayoutManager = LinearLayoutManager(context)
            infoWordsRecycler.layoutManager = linearLayoutManager
            infoWordsRecycler.hasFixedSize()

            infoWordsList = infoWords

            if (infoWords.size > 0) {
                infoWordsRecycler.adapter?.notifyDataSetChanged()
                infoWordsRecycler.adapter =  InfoWordRecyclerAdapter(infoWords)
            }
        })
    }
}