package com.example.words.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.words.R
import kotlinx.android.synthetic.main.info_word_row.view.*

class InfoWordRecyclerAdapter(private val infoWordsList: List<Pair<String, Int>>) : RecyclerView.Adapter<InfoWordRecyclerAdapter.InfoWordHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfoWordRecyclerAdapter.InfoWordHolder {
        return InfoWordHolder(LayoutInflater.from(parent.context).inflate(R.layout.info_word_row, parent, false))
    }


    override fun getItemCount(): Int {
        return infoWordsList.size
    }


    override fun onBindViewHolder(holder: InfoWordHolder, position: Int) {
        holder.bindItems(infoWordsList[position])
    }


    fun refresh() {
        notifyDataSetChanged()
    }


    class InfoWordHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bindItems(infoWord: Pair<String, Int>) {
            var repetition = itemView.itemValue.context.resources.getQuantityString(R.plurals.repetitions, infoWord.second, infoWord.second)
            itemView.itemValue.text = String.format("%s: %s", infoWord.first, repetition)

            /*itemView.setOnClickListener(View.OnClickListener {
                WijobsApplication.setLastSearch(recentSearch.localityIds, recentSearch.tagIds, recentSearch.sortedId, recentSearch.categories, recentSearch.onlyRemote, recentSearch.withFlexibleSchedule)

                val intent = Intent(context, MainActivity::class.java)
                ContextCompat.startActivity(itemView.context, intent, null)
            })*/
        }
    }
}