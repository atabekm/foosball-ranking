package com.shagalalab.foosballranking.presenter

import com.shagalalab.foosballranking.model.repository.ResultsRepository
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
            .getRankings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::updateRanking, view::updateResultsFailed)
    }
}