package com.shagalalab.foosballranking.di.module

import com.shagalalab.foosballranking.model.db.FoosballDatabase
import com.shagalalab.foosballranking.model.repository.ParticipantsRepository
import com.shagalalab.foosballranking.model.repository.ResultsRepository
import com.shagalalab.foosballranking.model.repository.TeamsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesParticipantsRepository(database: FoosballDatabase) = ParticipantsRepository(database)

    @Provides
    @Singleton
    fun providesTeamsRepository(database: FoosballDatabase) = TeamsRepository(database)

    @Provides
    @Singleton
    fun providesResultsRepository(database: FoosballDatabase) = ResultsRepository(database)


}