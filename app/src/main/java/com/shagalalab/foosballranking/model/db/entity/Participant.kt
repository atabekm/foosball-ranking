package com.shagalalab.foosballranking.model.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "participants")
data class Participant(
    @ColumnInfo(name = "participant_name") val name: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "participant_id") var id = 0
}