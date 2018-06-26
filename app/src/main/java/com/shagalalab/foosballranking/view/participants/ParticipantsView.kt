package com.shagalalab.foosballranking.view.participants

import com.shagalalab.foosballranking.model.db.entity.Participant

interface ParticipantsView {
    fun updateParticipants(items: List<Participant>)
}