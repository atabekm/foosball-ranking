package com.shagalalab.foosballranking.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.shagalalab.foosballranking.model.db.FoosballDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context) = Room.databaseBuilder(context, FoosballDatabase::class.java, "qarejet.db").build()

}