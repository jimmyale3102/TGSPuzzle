package com.android.uptc.tgspuzzleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.uptc.tgspuzzleproject.R
import com.android.uptc.tgspuzzleproject.logic.GlobalValues
import com.android.uptc.tgspuzzleproject.logic.Player
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_scores.*

class ScoresActivity : AppCompatActivity() {

    private val loadingView by lazy { findViewById<View>(R.id.loading_scores) }
    private val databaseInstance by lazy { FirebaseFirestore.getInstance() }
    private var scoresList = ArrayList<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)
        val linearManager = LinearLayoutManager(this)
        scores_recycler.layoutManager = linearManager
        getData()
    }

    private fun getData() {
        scoresList = ArrayList()
        databaseInstance.collection("players").get()
            .addOnSuccessListener { players ->
                players.forEach { player ->
                    val username = player.data.orEmpty().getValue("username").toString()
                    when(GlobalValues.scoreType) {
                        1 -> {
                            if(player.data.orEmpty().containsKey("crosswordEasyScore")) {
                                val playerScore = player.data.orEmpty().getValue("crosswordEasyScore")
                                    .toString().toInt()
                                scoresList.add(Player(username, playerScore))
                            }
                        }
                        2 -> {
                            if(player.data.orEmpty().containsKey("crosswordHardScore")) {
                                val playerScore = player.data.orEmpty().getValue("crosswordHardScore")
                                    .toString().toInt()
                                scoresList.add(Player(username, playerScore))
                            }
                        }
                        3 -> {
                            if(player.data.orEmpty().containsKey("searchWordEasyScore")) {
                                val playerScore = player.data.orEmpty().getValue("searchWordEasyScore")
                                    .toString().toInt()
                                scoresList.add(Player(username, playerScore))
                            }
                        }
                        else -> {
                            if(player.data.orEmpty().containsKey("searchWordHardScore")) {
                                val playerScore = player.data.orEmpty().getValue("searchWordHardScore")
                                    .toString().toInt()
                                scoresList.add(Player(username, playerScore))
                            }
                        }
                    }
                }
            }
            .addOnCompleteListener {
                scoresList.sortWith( compareBy { it.score } )
                loadingView.visibility = View.GONE
                scores_recycler.visibility = View.VISIBLE
            }
    }
}