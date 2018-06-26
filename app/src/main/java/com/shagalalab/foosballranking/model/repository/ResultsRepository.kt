package com.shagalalab.foosballranking.model.repository

import com.shagalalab.foosballranking.model.db.FoosballDatabase
import com.shagalalab.foosballranking.model.db.entity.Result

class ResultsRepository(private val database: FoosballDatabase) {

    fun addResult(result: Result): Long {
        return database
            .resultsDao()
            .insertResult(result)
    }

    fun getResults() {
        database
            .resultsDao()
            .getResults()
    }

}