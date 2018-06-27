package com.shagalalab.foosballranking.presenter

import com.shagalalab.foosballranking.model.db.entity.ResultData
import com.shagalalab.foosballranking.model.db.entity.Statistics
import com.shagalalab.foosballranking.model.repository.ResultsRepository
import com.shagalalab.foosballranking.parseTeamMembers
import com.shagalalab.foosballranking.view.dashboard.DashboardView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DashboardPresenter(private val resultsRepository: ResultsRepository) {
    private lateinit var view: DashboardView

    fun init(view: DashboardView) {
        this.view = view
    }

    fun getLatestResults() {
        resultsRepository
            .getLast5Results()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::updateLastResults, view::updateResultsFailed)
    }

    fun getLatestRankings() {
        resultsRepository
            .getResults()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                processResults(it)
            }, {
                view.updateResultsFailed(it)
            })
    }

    private fun processResults(data: List<ResultData>) {
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

        val sortedBy = map.values.sortedWith(compareBy({it.getWinRatio()}, {it.getLostRatio()})).reversed().take(5)
        view.updateRanking(sortedBy)
    }
}