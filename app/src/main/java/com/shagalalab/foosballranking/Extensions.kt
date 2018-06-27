package com.shagalalab.foosballranking

import com.shagalalab.foosballranking.model.db.DataConverter

fun String.parseTeamMembers(): String {
    val data = DataConverter().stringToSomeObjectList(this)
    var result = ""
    for (d in data) {
        result += d.name + "/"
    }
    return result.substringBeforeLast("/")
}