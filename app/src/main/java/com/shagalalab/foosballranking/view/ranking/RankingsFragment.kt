package com.shagalalab.foosballranking.view.ranking

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
import com.shagalalab.foosballranking.model.db.entity.Statistics
import com.shagalalab.foosballranking.presenter.RankingsPresenter
import com.shagalalab.foosballranking.view.dashboard.RankingAdapter
import javax.inject.Inject

class RankingsFragment: Fragment(), RankingsView {
    @Inject lateinit var presenter: RankingsPresenter
    private lateinit var rankingAdapter: RankingAdapter

    companion object {
        private val instance: RankingsFragment = RankingsFragment()

        fun getInstance(): RankingsFragment {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as FoosballApp).component.inject(this)

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.title = "Rankings"
        }

        presenter.init(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_results, container, false)

        rankingAdapter = RankingAdapter()
        with(view.findViewById<RecyclerView>(R.id.resultsList)) {
            adapter = rankingAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.getResults()
    }

    override fun updateRankingsSuccess(data: List<Statistics>) {
        rankingAdapter.update(data)
    }

    override fun updateRankingsFailed(throwable: Throwable) {
        Toast.makeText(activity, "Couldn't get rankings, try again later", Toast.LENGTH_LONG).show()
    }
}