package com.shagalalab.foosballranking.model.repository

import com.shagalalab.foosballranking.model.db.FoosballDatabase
import com.shagalalab.foosballranking.model.db.entity.Result
import com.shagalalab.foosballranking.model.db.entity.ResultData
import com.shagalalab.foosballranking.model.db.entity.Statistics
import com.shagalalab.foosballranking.parseTeamMembers
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

    fun getRankings(): Single<List<Statistics>> {
        return database
            .resultsDao()
            .getResults()
            .flatMap {
                return@flatMap Single.fromCallable { processResults(it) }
            }
    }

    private fun processResults(data: List<ResultData>): List<Statistics> {
        val map = HashMap<String, Statistics>()

        for (d in data) {
            val participants1 = d.team1_participants.parseTeamMembers()
            if (!map.containsKey(participants1)) {
                map[participants1] = Statistics(d.team1_id, d.team1_participants)
            }
            when {
                d.team1_scored > d.team2_scored -> map[participants1]!!.win += 1
                d.team1_scored == d.team2_scored -> map[participants1]!!.draw += 1
                else -> map[participants1]!!.lost += 1
            }

            val participants2 = d.team2_participants.parseTeamMembers()
            if (!map.containsKey(participants2)) {
                map[participants2] = Statistics(d.team2_id, d.team2_participants)
            }
            when {
                d.team2_scored > d.team1_scored -> map[participants2]!!.win += 1
                d.team2_scored == d.team1_scored -> map[participants2]!!.draw += 1
                else -> map[participants2]!!.lost += 1
            }
        }

        return map.values.sortedWith(compareBy({it.getWinRatio()}, {it.getLostRatio()})).reversed()
    }

}