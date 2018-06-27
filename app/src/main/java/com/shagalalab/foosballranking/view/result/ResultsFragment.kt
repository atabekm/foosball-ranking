package com.shagalalab.foosballranking.view.result

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shagalalab.foosballranking.FoosballApp
import com.shagalalab.foosballranking.R
import com.shagalalab.foosballranking.model.db.entity.ResultData
import com.shagalalab.foosballranking.presenter.ResultsPresenter
import com.shagalalab.foosballranking.view.dashboard.ResultsAdapter
import javax.inject.Inject

class ResultsFragment: Fragment(), ResultsView {
    @Inject lateinit var presenter: ResultsPresenter
    private lateinit var resultsAdapter: ResultsAdapter

    companion object {
        private val instance: ResultsFragment = ResultsFragment()

        fun getInstance(): ResultsFragment {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as FoosballApp).component.inject(this)

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.title = "Results"
        }

        presenter.init(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_results, container, false)

        resultsAdapter = ResultsAdapter()
        with(view.findViewById<RecyclerView>(R.id.resultsList)) {
            adapter = resultsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.getResults()
    }

    override fun updateResultsSuccess(data: List<ResultData>) {
        resultsAdapter.update(data)
    }

    override fun updateResultsFailed(throwable: Throwable) {
        Toast.makeText(activity, "Couldn't get results, try again later", Toast.LENGTH_LONG).show()
    }
}