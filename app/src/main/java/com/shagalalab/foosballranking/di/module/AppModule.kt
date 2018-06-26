package com.shagalalab.foosballranking.di.module

import android.content.Context
import com.shagalalab.foosballranking.FoosballApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: FoosballApp) {

    @Provides
    @Singleton
    fun providesApp() = app

    @Provides
    @Singleton
    fun providesContext(): Context = app.applicationContext

}