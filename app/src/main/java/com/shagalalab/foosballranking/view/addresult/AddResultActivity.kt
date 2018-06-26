package com.shagalalab.foosballranking.view.addresult

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.View.VISIBLE
import android.widget.AdapterView
import com.shagalalab.foosballranking.Constants
import com.shagalalab.foosballranking.Constants.Companion.REQUEST_CODE_TEAM_1_1
import com.shagalalab.foosballranking.Constants.Companion.REQUEST_CODE_TEAM_1_2
import com.shagalalab.foosballranking.Constants.Companion.REQUEST_CODE_TEAM_2_1
import com.shagalalab.foosballranking.Constants.Companion.REQUEST_CODE_TEAM_2_2
import com.shagalalab.foosballranking.Constants.Companion.SELECTION_VALUE
import com.shagalalab.foosballranking.FoosballApp
import com.shagalalab.foosballranking.R
import com.shagalalab.foosballranking.model.db.entity.Participant
import com.shagalalab.foosballranking.presenter.AddResultPresenter
import com.shagalalab.foosballranking.view.participants.ParticipantsActivity
import kotlinx.android.synthetic.main.activity_add_result.*
import javax.inject.Inject

class AddResultActivity: AppCompatActivity(), AddResultView {
    @Inject lateinit var presenter: AddResultPresenter
    private var selectedFormation = -1
    private var selectedMembers = SparseArray<Participant>(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_result)
        (application as FoosballApp).component.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter.init(this)

        addResultFormation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedFormation = position
                setTeamButtonVisibility()
                validateAddResultButton()
            }
        }

        val intent = Intent(this, ParticipantsActivity::class.java)
        addResultTeam1Button1.setOnClickListener {
            intent.putExtra(Constants.CURRENT_VALUE, addResultTeam1Participant1.text.toString())
            startActivityForResult(intent, REQUEST_CODE_TEAM_1_1)
        }
        addResultTeam1Button2.setOnClickListener {
            intent.putExtra(Constants.CURRENT_VALUE, addResultTeam1Participant2.text.toString())
            startActivityForResult(intent, REQUEST_CODE_TEAM_1_2)
        }
        addResultTeam2Button1.setOnClickListener {
            intent.putExtra(Constants.CURRENT_VALUE, addResultTeam2Participant1.text.toString())
            startActivityForResult(intent, REQUEST_CODE_TEAM_2_1)
        }
        addResultTeam2Button2.setOnClickListener {
            intent.putExtra(Constants.CURRENT_VALUE, addResultTeam2Participant2.text.toString())
            startActivityForResult(intent, REQUEST_CODE_TEAM_2_2)
        }

        addResultButton.setOnClickListener {
            val teamOneList: MutableList<Participant> = ArrayList()
            val teamTwoList: MutableList<Participant> = ArrayList()
            when (selectedFormation) {
                Constants.FORMATION_1_VS_1 -> {
                    teamOneList.add(selectedMembers.get(0))
                    teamTwoList.add(selectedMembers.get(2))
                }
                Constants.FORMATION_1_VS_2 -> {
                    teamOneList.add(selectedMembers.get(0))
                    teamTwoList.add(selectedMembers.get(2))
                    teamTwoList.add(selectedMembers.get(3))
                }
                Constants.FORMATION_2_VS_2 -> {
                    teamOneList.add(selectedMembers.get(0))
                    teamOneList.add(selectedMembers.get(1))
                    teamTwoList.add(selectedMembers.get(2))
                    teamTwoList.add(selectedMembers.get(3))
                }
            }

            presenter.addResult(selectedFormation, teamOneList, teamTwoList, addResultTeam1Score.text.toString(), addResultTeam2Score.text.toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data == null) return

        val item : Participant = data.extras?.getSerializable(SELECTION_VALUE) as Participant
        when (requestCode) {
            REQUEST_CODE_TEAM_1_1 -> {
                selectedMembers.put(0, item)
                addResultTeam1Participant1.text = item.name
                addResultTeam1Participant1.visibility = VISIBLE
                addResultTeam1Button1.text = getString(R.string.change)
            }
            REQUEST_CODE_TEAM_1_2 -> {
                selectedMembers.put(1, item)
                addResultTeam1Participant2.text = item.name
                addResultTeam1Participant2.visibility = VISIBLE
                addResultTeam1Button2.text = getString(R.string.change)
            }
            REQUEST_CODE_TEAM_2_1 -> {
                selectedMembers.put(2, item)
                addResultTeam2Participant1.text = item.name
                addResultTeam2Participant1.visibility = VISIBLE
                addResultTeam2Button1.text = getString(R.string.change)
            }
            REQUEST_CODE_TEAM_2_2 -> {
                selectedMembers.put(3, item)
                addResultTeam2Participant2.text = item.name
                addResultTeam2Participant2.visibility = VISIBLE
                addResultTeam2Button2.text = getString(R.string.change)
            }
        }
        validateAddResultButton()
    }

    private fun setTeamButtonVisibility() {
        when (selectedFormation) {
            Constants.FORMATION_1_VS_1 -> {
                addResultTeam1Group2.visibility = View.INVISIBLE
                addResultTeam2Group2.visibility = View.INVISIBLE
            }
            Constants.FORMATION_1_VS_2 -> {
                addResultTeam1Group2.visibility = View.INVISIBLE
                addResultTeam2Group2.visibility = View.VISIBLE
            }
            Constants.FORMATION_2_VS_2 -> {
                addResultTeam1Group2.visibility = View.VISIBLE
                addResultTeam2Group2.visibility = View.VISIBLE
            }
        }
    }

    private fun validateAddResultButton() {
        val outcome: Boolean
        when (selectedFormation) {
            Constants.FORMATION_1_VS_1 -> {
                outcome = addResultTeam1Participant1.text.isNotEmpty()
                    && addResultTeam2Participant1.text.isNotEmpty()
            }
            Constants.FORMATION_1_VS_2 -> {
                outcome = addResultTeam1Participant1.text.isNotEmpty()
                    && addResultTeam2Participant1.text.isNotEmpty()
                    && addResultTeam2Participant2.text.isNotEmpty()
            }
            Constants.FORMATION_2_VS_2 -> {
                outcome = addResultTeam1Participant1.text.isNotEmpty()
                    && addResultTeam1Participant2.text.isNotEmpty()
                    && addResultTeam2Participant1.text.isNotEmpty()
                    && addResultTeam2Participant2.text.isNotEmpty()
            }
            else -> outcome = false
        }

        addResultButton.isEnabled = outcome
    }

    override fun resultAdded(id: Long) {
        Log.d("mytest", "result added with id $id")
        finish()
    }

    override fun resultFailed(throwable: Throwable) {
        Log.e("mytest", "result failed: ${throwable.localizedMessage}", throwable)
    }
}