package com.shagalalab.foosballranking.model.db.entity

data class Statistics(
    val id: Int,
    val participants: String
) {
    var win = 0
    var draw = 0
    var lost = 0

    fun getWinRatio() = win * 100 / (win + draw + lost)
    fun getLostRatio() = lost * 100 / (win + draw + lost)
}