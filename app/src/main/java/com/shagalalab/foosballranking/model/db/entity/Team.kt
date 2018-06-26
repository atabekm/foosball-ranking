package com.shagalalab.foosballranking.model.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
    @ColumnInfo(name = "team_participants") val participants: List<Participant>
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "team_id") var id = 0
}