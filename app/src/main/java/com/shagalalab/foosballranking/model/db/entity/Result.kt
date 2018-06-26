package com.shagalalab.foosballranking.model.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "results")
data class Result(
    val type: Int,
    val team1_id: Int,
    val team2_id: Int,
    val team1_scored: Int,
    val team2_scored: Int
) {
    @PrimaryKey(autoGenerate = true) var id = 0
    var date: Long = Date().time

    fun hasTeam1Won() = team1_scored > team2_scored
}