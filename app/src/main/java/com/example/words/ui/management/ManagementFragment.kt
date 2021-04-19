package com.example.words.ui.management

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.words.R
import com.example.words.data.InfoWordRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_management.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import www.sanju.motiontoast.MotionToast


class ManagementFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel: ManagementViewModel by viewModel()
    private var infoWordsList = listOf<Pair<String, Int>>()
    private var wordsList = listOf<String>()

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

        wordsList = viewModel.getWordsList()

        val adapter = ArrayAdapter(activity?.baseContext!!, android.R.layout.simple_list_item_1, wordsList)
        autoTextView.setAdapter(adapter)

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

        autoTextView.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            MotionToast.darkToast(
                context as Activity,
                    this.context?.resources?.getString(R.string.selected_item),
                "$selectedItem",
                MotionToast.TOAST_SUCCESS,
                MotionToast.GRAVITY_CENTER,
                MotionToast.LONG_DURATION,
                this.context?.let { ResourcesCompat.getFont(it,R.font.helvetica_regular) })
        }
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