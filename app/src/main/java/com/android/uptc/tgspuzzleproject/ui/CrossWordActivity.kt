package com.android.uptc.tgspuzzleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RawRes
import com.android.uptc.tgspuzzleproject.R
import com.android.uptc.tgspuzzleproject.extensions.snack
import com.android.uptc.tgspuzzleproject.logic.GlobalValues
import kotlinx.android.synthetic.main.activity_cross_word.*
import org.akop.ararat.core.Crossword
import org.akop.ararat.core.buildCrossword
import org.akop.ararat.io.PuzFormatter
import org.akop.ararat.view.CrosswordView

class CrossWordActivity : AppCompatActivity(),
    CrosswordView.OnLongPressListener,
    CrosswordView.OnStateChangeListener,
    CrosswordView.OnSelectionChangeListener  {

    private var puzzleNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cross_word)
        initComponents()
    }

    private fun readPuzzle(): Crossword = resources.openRawResource(R.raw.puz).use { s ->
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

    override fun onCellLongPressed(view: CrosswordView,
                                   word: Crossword.Word, cell: Int) {
        cross_word_layout.snack("Show popup menu for " + word.hint!!)
    }

    override fun onCrosswordChanged(view: CrosswordView) {}

    override fun onCrosswordSolved(view: CrosswordView) {
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

    private fun setLetter(letter: String) {
        crossword.setCellContents(
            crossword!!.selectedWord!!,
            crossword!!.selectedCell, letter, true
        )
    }

    private fun getPuzzle(): Crossword {
        if(GlobalValues.levelGame == EASY) {
            when(puzzleNumber) {
                1 -> return readPuzzle(R.raw.puz)
                2 -> return readPuzzle(R.raw.puz)
                3 -> return readPuzzle(R.raw.puz)
                else -> return readPuzzle(R.raw.puz)
            }
        } else {
            return readPuzzle(R.raw.puz)
        }
    }

    private fun initComponents() {
        puzzleNumber = (0..5).random()
        Log.d("==SelectedCell", crossword.selectedCell.toString())
        Log.d("==SelectedWord", crossword.selectedWord.toString())
        Log.d("==Correct letter", crossword.selectedWord[crossword.selectedCell].toString())
        Log.d("==Is Solved", crossword.isSolved.toString())
        crossword.crossword = readPuzzle()
        val crosswordSource = getPuzzle()

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

    companion object {
        const val EASY = 0
        const val HARD = 1
    }
}