package com.shagalalab.foosballranking.di.module

import com.shagalalab.foosballranking.model.repository.ParticipantsRepository
import com.shagalalab.foosballranking.model.repository.ResultsRepository
import com.shagalalab.foosballranking.model.repository.TeamsRepository
import com.shagalalab.foosballranking.presenter.AddResultPresenter
import com.shagalalab.foosballranking.presenter.DashboardPresenter
import com.shagalalab.foosballranking.presenter.ParticipantsPresenter
import com.shagalalab.foosballranking.presenter.RankingsPresenter
import com.shagalalab.foosballranking.presenter.ResultsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Singleton
    @Provides
    fun providesAddResultPresenter(teamsRepository: TeamsRepository, resultsRepository: ResultsRepository)
        = AddResultPresenter(teamsRepository, resultsRepository)

    @Singleton
    @Provides
    fun providesParticipantsPresenter(repository: ParticipantsRepository) = ParticipantsPresenter(repository)

    @Singleton
    @Provides
    fun providesMainPresenter(resultsRepository: ResultsRepository) = DashboardPresenter(resultsRepository)

    @Singleton
    @Provides
    fun providesResultsPresenter(resultsRepository: ResultsRepository) = ResultsPresenter(resultsRepository)

    @Singleton
    @Provides
    fun providesRankingsPresenter(resultsRepository: ResultsRepository) = RankingsPresenter(resultsRepository)
}