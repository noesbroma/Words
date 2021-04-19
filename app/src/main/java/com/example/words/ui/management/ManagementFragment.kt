package com.example.words.ui.management

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.words.R
import com.example.words.data.InfoWordRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_management.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ManagementFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: ManagementViewModel by viewModel()
    private var infoWordsList = listOf<Pair<String, Int>>()
    private var partialText = ""

    companion object {
        fun newInstance(): ManagementFragment {
            val fragment = ManagementFragment()

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_management, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getInfoWordList()
        viewModel.setInfoWordsList(viewModel.infoWordList)

        infoWordsRecycler.adapter = InfoWordRecyclerAdapter(viewModel.infoWordList)

        var li = listOf<String>()


        val adapter = ArrayAdapter(activity?.baseContext!!, android.R.layout.simple_list_item_1, viewModel.getWordsList())
        autoTextView.setAdapter(adapter)


        /*val arrayAdapter: ArrayAdapter<*>
        val list = arrayOf(
            "Cristiano Ronaldo",
            "Messi",
            "Neymar",
            "Isco",
            "Hazard",
            "Mbappe",
            "Hazard",
            "Ziyech",
            "Suarez"
        )
        // access the listView from xml file
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, list
        )
        autoTextView.setAdapter(arrayAdapter)*/

        setUI()
        setListeners()
        observeViewModel()
    }


    fun setUI() {
        wordsCount.text = String.format("%s %s", "Palabras totales: ", viewModel.infoWordList.size.toString())
    }


    fun setListeners() {
        orderByCount.setOnClickListener{
            viewModel.order(1)
            infoWordsRecycler.adapter?.notifyDataSetChanged()
        }

        orderByPosition.setOnClickListener{
            viewModel.order(2)
            infoWordsRecycler.adapter?.notifyDataSetChanged()
        }

        orderByWord.setOnClickListener{
            viewModel.order(3)
            infoWordsRecycler.adapter?.notifyDataSetChanged()
        }

        /*search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                partialText = newText

                if (newText.isEmpty()){
                    suggestView.visibility = View.GONE
                    //filters.visibility = View.VISIBLE

                } else{
                    suggestView.visibility = View.VISIBLE
                    //filters.visibility = View.GONE
                    suggestions.clear()

                    //suggestions.addAll(getSuggest(newText))
                    getSuggest(newText)

                    if (suggestions.isEmpty()){
                        //showing the empty textview when the list is empty
                        //tvEmpty.visibility= View.VISIBLE
                    }
                }

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //action when type Enter
                return false
            }
        })*/
    }


    private fun observeViewModel() {
        viewModel.onLoadInfoWordsEvent.observe(viewLifecycleOwner, androidx.lifecycle.Observer { infoWords ->
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