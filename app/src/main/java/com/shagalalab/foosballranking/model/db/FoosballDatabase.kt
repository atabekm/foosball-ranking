package com.shagalalab.foosballranking.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.shagalalab.foosballranking.model.db.entity.Participant
import com.shagalalab.foosballranking.model.db.entity.Result
import com.shagalalab.foosballranking.model.db.entity.Team

@Database(version = 1, entities = arrayOf(Participant::class, Team::class, Result::class), exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class FoosballDatabase : RoomDatabase() {
    abstract fun participantsDao(): ParticipantDao
    abstract fun teamsDao(): TeamDao
    abstract fun resultsDao(): ResultDao
}