package com.shagalalab.foosballranking.view.ranking

import com.shagalalab.foosballranking.model.db.entity.Statistics

interface RankingsView {
    fun updateRankingsSuccess(data: List<Statistics>)
    fun updateRankingsFailed(throwable: Throwable)
}