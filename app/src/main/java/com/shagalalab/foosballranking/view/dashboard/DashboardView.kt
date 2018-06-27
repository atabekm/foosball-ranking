package com.shagalalab.foosballranking.view.dashboard

import com.shagalalab.foosballranking.model.db.entity.ResultData
import com.shagalalab.foosballranking.model.db.entity.Statistics

interface DashboardView {
    fun updateLastResults(result: List<ResultData>)
    fun updateResultsFailed(throwable: Throwable)
    fun updateRanking(sortedBy: List<Statistics>)
}