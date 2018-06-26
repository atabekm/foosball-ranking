package com.shagalalab.foosballranking.view.addresult

interface AddResultView {
    fun resultAdded(id: Long)
    fun resultFailed(throwable: Throwable)
}