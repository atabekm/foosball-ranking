package com.shagalalab.foosballranking.model.db.entity

data class ResultData(
    val type: Int,
    val team1_scored: Int,
    val team2_scored: Int,
    val date: Long,
    val team1_participants: String,
    val team2_participants: String,
    val team1_id: Int,
    val team2_id: Int
)