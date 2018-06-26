package com.shagalalab.foosballranking.view.participants

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import com.shagalalab.foosballranking.Constants
import com.shagalalab.foosballranking.FoosballApp
import com.shagalalab.foosballranking.R
import com.shagalalab.foosballranking.model.db.entity.Participant
import com.shagalalab.foosballranking.presenter.ParticipantsPresenter
import kotlinx.android.synthetic.main.activity_participants.*
import javax.inject.Inject

class ParticipantsActivity: AppCompatActivity(), ParticipantsView {
    @Inject lateinit var presenter: ParticipantsPresenter

    private lateinit var adapter: ParticipantsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participants)
        (application as FoosballApp).component.inject(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        presenter.init(this)
        presenter.getParticipants()

        val currentValue = intent.getStringExtra(Constants.CURRENT_VALUE)

        participantsButton.setOnClickListener {
            intent.putExtra(Constants.SELECTION_VALUE, adapter.getSelectedSingle())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        adapter = ParticipantsAdapter(currentValue)
        participantsList.adapter = adapter
        participantsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.participants, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val view = layoutInflater.inflate(R.layout.dialog_add_participant, null)
                val editText = view.findViewById<EditText>(R.id.dialogAddParticipantEditText)
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.enter_participant_name))
                    .setView(view)
                    .setPositiveButton(getString(R.string.save)) { _, _ ->
                        presenter.addParticipant(editText.text.toString())
                    }
                    .setNegativeButton(getString(R.string.cancel), null)
                    .create()
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun updateParticipants(items: List<Participant>) {
        if (items.isEmpty()) {
            participantsEmpty.visibility = VISIBLE
            participantsList.visibility = GONE
        } else {
            participantsEmpty.visibility = GONE
            participantsList.visibility = VISIBLE
            adapter.updateData(items)
        }
    }
}