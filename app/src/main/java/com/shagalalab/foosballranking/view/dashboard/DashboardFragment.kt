package com.shagalalab.foosballranking.view.dashboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.shagalalab.foosballranking.Constants
import com.shagalalab.foosballranking.FoosballApp
import com.shagalalab.foosballranking.R
import com.shagalalab.foosballranking.model.db.entity.ResultData
import com.shagalalab.foosballranking.model.db.entity.Statistics
import com.shagalalab.foosballranking.presenter.DashboardPresenter
import com.shagalalab.foosballranking.view.main.RouterHelper
import kotlinx.android.synthetic.main.fragment_dashboard.*
import javax.inject.Inject

class DashboardFragment : Fragment(), DashboardView {
    @Inject lateinit var presenter: DashboardPresenter
    private lateinit var resultsAdapter: ResultsAdapter
    private lateinit var rankingAdapter: RankingAdapter

    companion object {
        private val instance: DashboardFragment = DashboardFragment()

        fun getInstance(): DashboardFragment {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as FoosballApp).component.inject(this)

        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.title = "Dashboard"
        }

        presenter.init(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.getLatestResults()
        presenter.getLatestRankings()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        resultsAdapter = ResultsAdapter()
        with(view.findViewById<RecyclerView>(R.id.dashboardLastResultsList)) {
            adapter = resultsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        rankingAdapter = RankingAdapter()
        with(view.findViewById<RecyclerView>(R.id.dashboardTopRankingList)) {
            adapter = rankingAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
        view.findViewById<TextView>(R.id.dashboardLastResultsMore).setOnClickListener {
            if (activity is RouterHelper) {
                (activity as RouterHelper).switchToFragment(Constants.SCREEN_RESULTS)
            }
        }
        view.findViewById<TextView>(R.id.dashboardLastRankingMore).setOnClickListener {
            if (activity is RouterHelper) {
                (activity as RouterHelper).switchToFragment(Constants.SCREEN_RANKINGS)
            }
        }

        return view
    }

    override fun updateLastResults(result: List<ResultData>) {
        if (result.isEmpty()) {
            dashboardEmpty.visibility = VISIBLE
            dashboardMain.visibility = GONE
        } else {
            dashboardEmpty.visibility = GONE
            dashboardMain.visibility = VISIBLE
            resultsAdapter.update(result)
        }
    }

    override fun updateResultsFailed(throwable: Throwable) {
        Toast.makeText(activity, "Couldn't get last results, try again later", Toast.LENGTH_LONG).show()
    }

    override fun updateRanking(sortedBy: List<Statistics>) {
        if (sortedBy.isEmpty()) {
            dashboardEmpty.visibility = VISIBLE
            dashboardMain.visibility = GONE
        } else {
            dashboardEmpty.visibility = GONE
            dashboardMain.visibility = VISIBLE
            rankingAdapter.update(sortedBy.take(5))
        }
    }
}