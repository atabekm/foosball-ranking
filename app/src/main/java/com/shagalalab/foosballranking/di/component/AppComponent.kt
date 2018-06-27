package com.shagalalab.foosballranking.di.component

import com.shagalalab.foosballranking.FoosballApp
import com.shagalalab.foosballranking.di.module.AppModule
import com.shagalalab.foosballranking.di.module.DataModule
import com.shagalalab.foosballranking.di.module.PresenterModule
import com.shagalalab.foosballranking.di.module.RepositoryModule
import com.shagalalab.foosballranking.view.addresult.AddResultActivity
import com.shagalalab.foosballranking.view.dashboard.DashboardFragment
import com.shagalalab.foosballranking.view.participants.ParticipantsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, PresenterModule::class, RepositoryModule::class))
interface AppComponent {
    fun inject(app: FoosballApp)
    fun inject(activity: AddResultActivity)
    fun inject(activity: ParticipantsActivity)
    fun inject(fragment: DashboardFragment)
}