package com.shagalalab.foosballranking.model.repository

import com.shagalalab.foosballranking.model.db.FoosballDatabase
import com.shagalalab.foosballranking.model.db.entity.Participant
import com.shagalalab.foosballranking.model.db.entity.Team

class TeamsRepository(private val database: FoosballDatabase) {

    fun addTeam(participants: List<Participant>): Long {
        return database
            .teamsDao()
            .insertTeam(Team(participants))
    }

}