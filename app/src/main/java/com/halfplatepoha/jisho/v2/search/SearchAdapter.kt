package com.halfplatepoha.jisho.v2.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.halfplatepoha.jisho.R

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    var listener: ((id: String?) -> Unit)? = null

    var searchResults: List<Word>? = null
        set(value) {
            if(field != null) {
                val length = field!!.size
                notifyItemRangeRemoved(0, length)
            }
            field = value
            if(field != null) {
                val length = field!!.size
                notifyItemRangeInserted(0, length)
            }
        }

    var isVerticalScroll = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val layoutId
        get() = if(isVerticalScroll) R.layout.row_search_vertical else R.layout.row_search

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SearchViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return searchResults?.size ?: 0
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        searchResults?.get(position)?.apply { holder.bind(this) }
    }

}

data class Word(val id: String,
                val hiragana: String?,
                val japanese: String?,
                val meaning: String?)

class SearchViewHolder(view: View, val listener: ((id: String?) -> Unit)?) : RecyclerView.ViewHolder(view) {

    fun bind(word: Word) {
        itemView.tag = word.id
        itemView.findViewById<TextView>(R.id.tvHiragana).text = word.hiragana
        itemView.findViewById<TextView>(R.id.tvJapanese).text = word.japanese
        itemView.findViewById<TextView>(R.id.tvMeaning).text = word.meaning
        itemView.setOnClickListener { listener?.apply { it.tag?.toString() } }
    }

}