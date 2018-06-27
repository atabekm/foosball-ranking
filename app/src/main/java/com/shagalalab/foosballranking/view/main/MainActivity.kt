package com.shagalalab.foosballranking.view.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.shagalalab.foosballranking.Constants.Companion.SCREEN_DASHBOARD
import com.shagalalab.foosballranking.Constants.Companion.SCREEN_RANKINGS
import com.shagalalab.foosballranking.Constants.Companion.SCREEN_RESULTS
import com.shagalalab.foosballranking.R
import com.shagalalab.foosballranking.view.addresult.AddResultActivity
import com.shagalalab.foosballranking.view.dashboard.DashboardFragment
import com.shagalalab.foosballranking.view.ranking.RankingsFragment
import com.shagalalab.foosballranking.view.result.ResultsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, RouterHelper {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startActivity(Intent(this, AddResultActivity::class.java))
        }

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        switchToFragment(SCREEN_DASHBOARD)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_dashboard -> {
                switchToFragment(SCREEN_DASHBOARD)
            }
            R.id.nav_results -> {
                switchToFragment(SCREEN_RESULTS)
            }
            R.id.nav_ranking -> {
                switchToFragment(SCREEN_RANKINGS)
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun switchToFragment(screenId: String) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, when(screenId){
            SCREEN_DASHBOARD -> DashboardFragment()
            SCREEN_RESULTS -> ResultsFragment()
            SCREEN_RANKINGS -> RankingsFragment()
            else -> DashboardFragment()
        }).addToBackStack(screenId).commit()
    }
}
