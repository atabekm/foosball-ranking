package com.shagalalab.foosballranking.view.dashboard

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shagalalab.foosballranking.R
import com.shagalalab.foosballranking.model.db.entity.ResultData
import com.shagalalab.foosballranking.parseTeamMembers

class ResultsAdapter: RecyclerView.Adapter<ResultViewHolder>() {
    private var results: List<ResultData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.populate(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun update(results: List<ResultData>) {
        this.results = results
        notifyDataSetChanged()
    }
}

class ResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun populate(result: ResultData) {
        itemView.findViewById<TextView>(R.id.item_result_score1).text = result.team1_scored.toString()
        itemView.findViewById<TextView>(R.id.item_result_score2).text = result.team2_scored.toString()
        itemView.findViewById<TextView>(R.id.item_result_team1).text = result.team1_participants.parseTeamMembers()
        itemView.findViewById<TextView>(R.id.item_result_team2).text = result.team2_participants.parseTeamMembers()
    }
}