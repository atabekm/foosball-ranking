package com.shagalalab.foosballranking.model.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.shagalalab.foosballranking.model.db.entity.Participant
import com.shagalalab.foosballranking.model.db.entity.Result
import com.shagalalab.foosballranking.model.db.entity.ResultData
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
    @Query("SELECT results.type, results.team1_scored, results.team2_scored, results.date, " +
        "team1.team_id AS team1_id, team1.team_participants AS team1_participants, " +
        "team2.team_id AS team2_id, team2.team_participants AS team2_participants " +
        "FROM results " +
        "INNER JOIN teams team1 ON team1.team_id=results.team1_id " +
        "INNER JOIN teams team2 ON team2.team_id=results.team2_id " +
        "ORDER BY results.date DESC")
    fun getResults(): Single<List<ResultData>>

    @Query("SELECT results.type, results.team1_scored, results.team2_scored, results.date, " +
        "team1.team_id AS team1_id, team1.team_participants AS team1_participants, " +
        "team2.team_id AS team2_id, team2.team_participants AS team2_participants " +
        "FROM results " +
        "INNER JOIN teams team1 ON team1.team_id=results.team1_id " +
        "INNER JOIN teams team2 ON team2.team_id=results.team2_id " +
        "ORDER BY results.date DESC LIMIT 5")
    fun getLast5Results(): Single<List<ResultData>>

    @Query("SELECT * FROM results WHERE id = :id")
    fun getResult(id: Long): Single<Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResult(result: Result): Long

    @Delete
    fun deleteResult(result: Result)
}