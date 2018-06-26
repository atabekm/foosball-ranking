package com.shagalalab.foosballranking.presenter

import com.shagalalab.foosballranking.model.db.entity.Participant
import com.shagalalab.foosballranking.model.repository.ParticipantsRepository
import com.shagalalab.foosballranking.view.participants.ParticipantsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ParticipantsPresenter(private val repository: ParticipantsRepository) {
    lateinit var view: ParticipantsView

    fun init(view: ParticipantsView) {
        this.view = view
    }

    fun addParticipant(name: String) {
        repository
            .addParticipant(Participant(name))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { getParticipants() }
    }

    fun getParticipants() {
        repository
            .getParticipants()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(view::updateParticipants)
    }
}