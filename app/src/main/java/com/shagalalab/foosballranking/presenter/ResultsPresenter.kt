package com.shagalalab.foosballranking.presenter

import com.shagalalab.foosballranking.model.repository.ResultsRepository
import com.shagalalab.foosballranking.view.result.ResultsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ResultsPresenter(private val resultsRepository: ResultsRepository) {
    private lateinit var view: ResultsView

    fun init(view: ResultsView) {
        this.view = view
    }

    fun getResults() {
        resultsRepository
            .getResults()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::updateResultsSuccess, view::updateResultsFailed)
    }
}