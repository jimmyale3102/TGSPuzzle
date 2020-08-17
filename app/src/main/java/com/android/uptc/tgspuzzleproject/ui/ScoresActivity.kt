package com.android.uptc.tgspuzzleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.uptc.tgspuzzleproject.R
import com.google.firebase.firestore.FirebaseFirestore

class ScoresActivity : AppCompatActivity() {

    private val databasaeInstance by lazy { FirebaseFirestore.getInstance() }
    //private val adapter = GroupAdapter<RecyclerView.ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)
    }
}