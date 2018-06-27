package com.shagalalab.foosballranking.model.repository

import com.shagalalab.foosballranking.model.db.FoosballDatabase
import com.shagalalab.foosballranking.model.db.entity.Result
import com.shagalalab.foosballranking.model.db.entity.ResultData
import io.reactivex.Single

class ResultsRepository(private val database: FoosballDatabase) {

    fun addResult(result: Result): Long {
        return database
            .resultsDao()
            .insertResult(result)
    }

    fun getResults(): Single<List<ResultData>> {
        return database
            .resultsDao()
            .getResults()
    }

    fun getLast5Results(): Single<List<ResultData>> {
        return database
            .resultsDao()
            .getLast5Results()
    }

}