package com.shagalalab.foosballranking

import android.app.Application
import com.shagalalab.foosballranking.di.component.AppComponent
import com.shagalalab.foosballranking.di.component.DaggerAppComponent
import com.shagalalab.foosballranking.di.module.AppModule

class FoosballApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}