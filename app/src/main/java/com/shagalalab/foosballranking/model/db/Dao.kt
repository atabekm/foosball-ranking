package com.shagalalab.foosballranking.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.shagalalab.foosballranking.model.db.entity.Participant
import com.shagalalab.foosballranking.model.db.entity.Result
import com.shagalalab.foosballranking.model.db.entity.Team
import io.reactivex.Single

@Dao
interface ParticipantDao {
    @Query("SELECT * FROM participants")
    fun getParticipants(): Single<List<Participant>>

    @Query("SELECT * FROM participants WHERE participant_id = :id")
    fun getParticipant(id: Long): Single<Participant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParticipant(participant: Participant): Long

    @Delete
    fun deleteParticipant(participant: Participant)
}

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getTeams(): Single<List<Team>>

    @Query("SELECT * FROM teams WHERE team_id = :id")
    fun getTeam(id: Long): Single<Team>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: Team): Long

    @Delete
    fun deleteTeam(team: Team)
}

@Dao
interface ResultDao {
    @Query("SELECT * FROM results")
    fun getResults(): Single<List<Result>>

    @Query("SELECT * FROM results WHERE id = :id")
    fun getResult(id: Long): Single<Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResult(result: Result): Long

    @Delete
    fun deleteResult(result: Result)
}