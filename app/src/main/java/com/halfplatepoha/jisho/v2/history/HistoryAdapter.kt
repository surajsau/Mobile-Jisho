package com.halfplatepoha.jisho.v2.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.halfplatepoha.jisho.R
import com.halfplatepoha.jisho.db.v2.HistoryV2

class HistoryAdapter: RecyclerView.Adapter<HistoryViewHolder>() {

    var historyItems: List<HistoryV2>? = null

    var listener: ((id: String) -> Unit)? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_history, parent, false)
        return HistoryViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return historyItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        historyItems?.get(position)?.run { holder.bind(this) }
    }

}

class HistoryViewHolder(view: View, val listener: ((id: String) -> Unit)?): RecyclerView.ViewHolder(view) {

    fun bind(history: HistoryV2) {
        itemView.findViewById<TextView>(R.id.tvHistory).text = history.word
        itemView.tag = history.id
        itemView.setOnClickListener { listener?.apply { it.tag?.toString() } }
    }

}