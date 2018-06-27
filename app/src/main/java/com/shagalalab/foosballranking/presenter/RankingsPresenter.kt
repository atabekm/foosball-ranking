package com.shagalalab.foosballranking.presenter

import com.shagalalab.foosballranking.model.repository.ResultsRepository
import com.shagalalab.foosballranking.view.ranking.RankingsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RankingsPresenter(private val resultsRepository: ResultsRepository) {
    private lateinit var view: RankingsView

    fun init(view: RankingsView) {
        this.view = view
    }

    fun getResults() {
        resultsRepository
            .getRankings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::updateRankingsSuccess, view::updateRankingsFailed)
    }
}