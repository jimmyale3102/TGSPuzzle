package com.android.uptc.tgspuzzleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.annotation.RawRes
import androidx.appcompat.app.AlertDialog
import com.android.uptc.tgspuzzleproject.R
import com.android.uptc.tgspuzzleproject.extensions.snack
import com.android.uptc.tgspuzzleproject.logic.GlobalValues
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_cross_word.*
import kotlinx.android.synthetic.main.alert_level_game.view.*
import org.akop.ararat.core.Crossword
import org.akop.ararat.core.buildCrossword
import org.akop.ararat.io.PuzFormatter
import org.akop.ararat.view.CrosswordView

class CrossWordActivity : AppCompatActivity(),
    CrosswordView.OnLongPressListener,
    CrosswordView.OnStateChangeListener,
    CrosswordView.OnSelectionChangeListener  {

    private val databaseInstance by lazy { FirebaseFirestore.getInstance() }
    private var puzzleNumber = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cross_word)
        initComponents()
    }

    private fun readPuzzle(): Crossword = resources.openRawResource(getPuzzlePath()).use { s ->
        buildCrossword { PuzFormatter().read(this, s) }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        crossword!!.restoreState(savedInstanceState.getParcelable("state")!!)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("state", crossword!!.state)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onCellLongPressed(view: CrosswordView,
                                   word: Crossword.Word, cell: Int) {
        cross_word_layout.snack("Show popup menu for " + word.hint!!)
    }

    override fun onCrosswordChanged(view: CrosswordView) {}

    override fun onCrosswordSolved(view: CrosswordView) {
        val minutes = timer.text.toString().split(":")[0]
        val seconds = timer.text.toString().split(":")[1]
        score = (minutes + seconds).toInt()

        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_level_game, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = builder.create()

        alertDialog.setCancelable(false)
        // Set width alert
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val displayWidth: Int = displayMetrics.widthPixels
        val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(alertDialog.window?.attributes)
        val dialogWindowWidth = (displayWidth * 0.8f).toInt()
        layoutParams.width = dialogWindowWidth
        alertDialog.window?.attributes = layoutParams
        dialogView.easy_button.visibility = View.GONE
        dialogView.hard_button.text = getString(R.string.ok_button)
        dialogView.title.text = getString(R.string.youve_solved_the_puzzle)
        dialogView.loading.visibility = View.VISIBLE
        dialogView.description.text = getString(R.string.saving_score)
        dialogView.hard_button.visibility = View.GONE
        dialogView.hard_button.setOnClickListener {
            alertDialog.dismiss()
            finish()
        }
        alertDialog.show()

        databaseInstance.collection("players").document(GlobalValues.playerId).get()
            .addOnSuccessListener { player ->
                dialogView.loading.visibility = View.GONE
                dialogView.hard_button.visibility = View.VISIBLE
                dialogView.description.text = getString(R.string.score_saved)
                if(GlobalValues.levelGame == EASY
                    && player.data.orEmpty().containsKey("crosswordEasyScore")) {
                    val oldScore = player.data.orEmpty().getValue("crosswordEasyScore").toString()
                        .toInt()
                    if(score > oldScore) {
                        saveScore(dialogView)
                    }
                }
                if(GlobalValues.levelGame == HARD
                    && player.data.orEmpty().containsKey("crosswordHardScore")) {
                    val oldScore = player.data.orEmpty().getValue("crosswordHardScore").toString()
                        .toInt()
                    if(score > oldScore) {
                        saveScore(dialogView)
                    }
                }
            }

        cross_word_layout.snack(R.string.youve_solved_the_puzzle)
    }

    override fun onCrosswordUnsolved(view: CrosswordView) { }

    private fun readPuzzle(@RawRes resourceId: Int): Crossword =
        resources.openRawResource(resourceId).use { s ->
            buildCrossword { PuzFormatter().read(this, s) }
        }

    override fun onSelectionChanged(view: CrosswordView,
                                    word: Crossword.Word?, position: Int) {
        hint!!.text = when (word?.direction) {
            Crossword.Word.DIR_ACROSS -> getString(R.string.across, word.number, word.hint)
            Crossword.Word.DIR_DOWN -> getString(R.string.down, word.number, word.hint)
            else -> ""
        }
    }

    private fun saveScore(dialogView: View) {
        val scoreData = if(GlobalValues.levelGame == EASY) {
            hashMapOf(
                "crosswordEasyScore" to score
            )
        } else {
            hashMapOf(
                "crosswordHardScore" to score
            )
        }
        databaseInstance.collection("players").document(GlobalValues.playerId)
            .set(scoreData, SetOptions.merge())
            .addOnSuccessListener {
                dialogView.loading.visibility = View.GONE
                dialogView.hard_button.visibility = View.VISIBLE
                dialogView.description.text = getString(R.string.score_saved)
            }
    }

    private fun setLetter(letter: String) {
        crossword.setCellContents(
            crossword!!.selectedWord!!,
            crossword!!.selectedCell, letter, true
        )
        Log.d("==SelectedCell", crossword.selectedCell.toString())
        Log.d("==SelectedWord", crossword.selectedWord.toString())
        Log.d("==Correct letter", crossword.selectedWord[crossword.selectedCell].toString())
        Log.d("==Is Solved", crossword.isSolved.toString())
    }

    private fun getPuzzlePath(): Int {
        return if(GlobalValues.levelGame == EASY) {
            when(puzzleNumber) {
                1 -> R.raw.easy_puzzle_1
                2 -> R.raw.easy_puzzle_2
                3 -> R.raw.easy_puzzle_3
                else -> R.raw.easy_puzzle_4
            }
        } else {
            when(puzzleNumber) {
                1 -> R.raw.hard_puzzle_1
                2 -> R.raw.hard_puzzle_2
                else -> R.raw.hard_puzzle_3
            }
        }
    }

    private fun initComponents() {
        exit_button.setOnClickListener { finish() }
        puzzleNumber = (0..5).random()
        crossword.crossword = readPuzzle()
        val crosswordSource = readPuzzle(getPuzzlePath())

        crossword!!.let { cv ->
            cv.crossword = crosswordSource
            cv.setOnLongPressListener(this)
            cv.setOnStateChangeListener(this)
            cv.setOnSelectionChangeListener(this)
            cv.setInputValidator { ch ->
                !ch.first().isISOControl()
            }
            cv.undoMode = CrosswordView.UNDO_NONE
            cv.markerDisplayMode = CrosswordView.MARKER_CHEAT

            onSelectionChanged(cv, cv.selectedWord, cv.selectedCell)
        }
        // Timer
        timer.base = SystemClock.elapsedRealtime()
        timer.start()

        a_button.setOnClickListener {
            setLetter(getString(R.string.a_label))
        }
        b_button.setOnClickListener {
            setLetter(getString(R.string.b_label))
        }
        c_button.setOnClickListener {
            setLetter(getString(R.string.c_label))
        }
        d_button.setOnClickListener {
            setLetter(getString(R.string.d_label))
        }
        e_button.setOnClickListener {
            setLetter(getString(R.string.e_label))
        }
        f_button.setOnClickListener {
            setLetter(getString(R.string.f_label))
        }
        g_button.setOnClickListener {
            setLetter(getString(R.string.g_label))
        }
        h_button.setOnClickListener {
            setLetter(getString(R.string.h_label))
        }
        i_button.setOnClickListener {
            setLetter(getString(R.string.i_label))
        }
        j_button.setOnClickListener {
            setLetter(getString(R.string.j_label))
        }
        k_button.setOnClickListener {
            setLetter(getString(R.string.k_label))
        }
        l_button.setOnClickListener {
            setLetter(getString(R.string.l_label))
        }
        m_button.setOnClickListener {
            setLetter(getString(R.string.m_label))
        }
        n_button.setOnClickListener {
            setLetter(getString(R.string.n_label))
        }
        ñ_button.setOnClickListener {
            setLetter(getString(R.string.ñ_label))
        }
        o_button.setOnClickListener {
            setLetter(getString(R.string.o_label))
        }
        p_button.setOnClickListener {
            setLetter(getString(R.string.p_label))
        }
        q_button.setOnClickListener {
            setLetter(getString(R.string.q_label))
        }
        r_button.setOnClickListener {
            setLetter(getString(R.string.r_label))
        }
        s_button.setOnClickListener {
            setLetter(getString(R.string.s_label))
        }
        t_button.setOnClickListener {
            setLetter(getString(R.string.t_label))
        }
        u_button.setOnClickListener {
            setLetter(getString(R.string.u_label))
        }
        v_button.setOnClickListener {
            setLetter(getString(R.string.v_label))
        }
        w_button.setOnClickListener {
            setLetter(getString(R.string.w_label))
        }
        x_button.setOnClickListener {
            setLetter(getString(R.string.x_label))
        }
        y_button.setOnClickListener {
            setLetter(getString(R.string.y_label))
        }
        z_button.setOnClickListener {
            setLetter(getString(R.string.z_label))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_restart -> {
                crossword!!.reset()
                return true
            }
            R.id.menu_solve_cell -> {
                crossword!!.solveChar(crossword!!.selectedWord!!,
                    crossword!!.selectedCell)
                return true
            }
            R.id.menu_solve_word -> {
                crossword!!.solveWord(crossword!!.selectedWord!!)
                return true
            }
            R.id.menu_solve_puzzle -> {
                crossword!!.solveCrossword()
                Log.d("==Is Solved", crossword.isSolved.toString())
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EASY = 0
        const val HARD = 1
    }
}