package com.shagalalab.foosballranking.view.result

import com.shagalalab.foosballranking.model.db.entity.ResultData

interface ResultsView {
    fun updateResultsSuccess(data: List<ResultData>)
    fun updateResultsFailed(throwable: Throwable)
}