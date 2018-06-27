package com.shagalalab.foosballranking.view.dashboard

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shagalalab.foosballranking.R
import com.shagalalab.foosballranking.model.db.entity.Statistics
import com.shagalalab.foosballranking.parseTeamMembers

class RankingAdapter: RecyclerView.Adapter<RankingViewHolder>() {
    private var results: List<Statistics> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.populate(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun update(results: List<Statistics>) {
        this.results = results
        notifyDataSetChanged()
    }
}

class RankingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun populate(result: Statistics) {
        itemView.findViewById<TextView>(R.id.item_ranking_title).text =
            "${result.participants.parseTeamMembers()} - ${result.getWinRatio()}% - ${result.getLostRatio()}%"
    }
}