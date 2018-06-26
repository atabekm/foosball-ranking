package com.shagalalab.foosballranking.presenter

import android.util.Log
import com.shagalalab.foosballranking.model.db.entity.Participant
import com.shagalalab.foosballranking.model.db.entity.Result
import com.shagalalab.foosballranking.model.repository.ResultsRepository
import com.shagalalab.foosballranking.model.repository.TeamsRepository
import com.shagalalab.foosballranking.view.addresult.AddResultView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class AddResultPresenter(private val teamsRepository: TeamsRepository, private val resultsRepository: ResultsRepository) {
    private lateinit var view: AddResultView

    fun init(view: AddResultView) {
        this.view = view
    }

    fun addResult(formation: Int, teamOneList: List<Participant>, teamTwoList: List<Participant>, score1: String, score2: String) {
        val single1 = Single.fromCallable { teamsRepository.addTeam(teamOneList) }
        val single2 = Single.fromCallable { teamsRepository.addTeam(teamTwoList) }

        Single.zip(single1, single2, BiFunction<Long, Long, Pair<Long, Long>> { t1, t2 -> Pair(t1, t2) })
            .flatMap {
                val result = Result(formation, it.first.toInt(), it.second.toInt(), score1.toInt(), score2.toInt())
                Log.d("mytest", "new result is $result")
                Single.fromCallable {
                    resultsRepository.addResult(result)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::resultAdded, view::resultFailed)
    }
}