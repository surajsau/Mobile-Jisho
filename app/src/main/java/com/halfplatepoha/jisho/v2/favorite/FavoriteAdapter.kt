package com.halfplatepoha.jisho.v2.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.halfplatepoha.jisho.R
import com.halfplatepoha.jisho.db.v2.FavoriteV2

class FavoriteAdapter: RecyclerView.Adapter<FavoriteViewHolder>() {

    var favorites: List<FavoriteV2>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ((id: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_favourite, parent, false)
        return FavoriteViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return favorites?.size ?: 0
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        favorites?.get(position)?.apply { holder.bind(this) }
    }

}

class FavoriteViewHolder(view: View, val listener: ((id: String) -> Unit)?): RecyclerView.ViewHolder(view) {

    fun bind(favorite: FavoriteV2) {
        itemView.findViewById<TextView>(R.id.tvHiragana).text = favorite.furigana
        itemView.findViewById<TextView>(R.id.tvJapanese).text = favorite.word
        itemView.tag = favorite.id
        itemView.setOnClickListener { listener?.apply { it.tag?.toString() } }
    }

}
