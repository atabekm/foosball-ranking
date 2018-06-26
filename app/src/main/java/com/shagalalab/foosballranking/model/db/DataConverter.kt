package com.shagalalab.foosballranking.model.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shagalalab.foosballranking.model.db.entity.Participant
import java.util.Collections

class DataConverter {
    private val gson = Gson()
    private val type = object : TypeToken<List<Participant>>() {}.type

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Participant> {
        if (data == null) {
            return Collections.emptyList()
        }

        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Participant>): String {
        return gson.toJson(someObjects, type)
    }
}