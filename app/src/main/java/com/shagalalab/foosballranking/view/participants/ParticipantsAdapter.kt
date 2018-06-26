package com.shagalalab.foosballranking.view.participants

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.shagalalab.foosballranking.R
import com.shagalalab.foosballranking.model.db.entity.Participant

class ParticipantsAdapter(var currentValue: String) : RecyclerView.Adapter<SingleParticipantViewHolder>() {
    private var items: List<Participant> = ArrayList()
    private var selectedItemId = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleParticipantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_single, parent, false)
        return SingleParticipantViewHolder(view)
    }

    override fun onBindViewHolder(holder: SingleParticipantViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.radio.isChecked = (position == selectedItemId || currentValue == item.name)
//        holder.itemView.isEnabled = position == 0
        holder.itemView.setOnClickListener {
            currentValue = ""
            selectedItemId = holder.adapterPosition
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(items: List<Participant>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getSelectedSingle() = items[selectedItemId]
}

class SingleParticipantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.item_name)
    val radio: RadioButton = itemView.findViewById(R.id.item_radio)
}