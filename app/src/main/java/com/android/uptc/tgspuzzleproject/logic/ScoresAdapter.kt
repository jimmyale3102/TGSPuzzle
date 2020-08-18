package com.android.uptc.tgspuzzleproject.logic

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.uptc.tgspuzzleproject.R
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_scores.view.*


class ScoresAdapter(
    private val context: Context,
    private val scores: List<Player>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_scores, parent, false))
    }

    override fun getItemCount(): Int = scores.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scoreData = scores[position]
        holder.itemView.username.text = scoreData.username
        holder.itemView.score.text = scoreData.score.toString()
    }


}