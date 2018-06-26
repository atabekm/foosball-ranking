package com.shagalalab.foosballranking.model.repository

import com.shagalalab.foosballranking.model.db.FoosballDatabase
import com.shagalalab.foosballranking.model.db.entity.Participant
import io.reactivex.Completable
import io.reactivex.Single

class ParticipantsRepository(private val database: FoosballDatabase) {

    fun getParticipants(): Single<List<Participant>> {
        return database
            .participantsDao()
            .getParticipants()
    }

    fun addParticipant(participant: Participant): Completable {
        return Completable.fromCallable {
            database
                .participantsDao()
                .insertParticipant(participant)
        }
    }

}