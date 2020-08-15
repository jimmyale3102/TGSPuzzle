package com.android.uptc.tgspuzzleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RawRes
import com.android.uptc.tgspuzzleproject.R
import com.android.uptc.tgspuzzleproject.extensions.snack
import kotlinx.android.synthetic.main.activity_cross_word.*
import org.akop.ararat.core.Crossword
import org.akop.ararat.core.buildCrossword
import org.akop.ararat.io.PuzFormatter
import org.akop.ararat.view.CrosswordView

class CrossWordActivity : AppCompatActivity(),
    CrosswordView.OnLongPressListener,
    CrosswordView.OnStateChangeListener,
    CrosswordView.OnSelectionChangeListener  {
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
        Toast.makeText(this, R.string.youve_solved_the_puzzle,
            Toast.LENGTH_SHORT).show()
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

    private fun initComponents() {
        crossword.crossword = readPuzzle()
        val crosswordSource = readPuzzle(R.raw.puzzle)

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
            crossword.setCellContents(
                crossword!!.selectedWord!!,
                crossword!!.selectedCell, "B", true
            )
            Log.d("==SelectedCell", crossword.selectedCell.toString())
            Log.d("==SelectedWord", crossword.selectedWord.toString())
            Log.d("==Correct letter", crossword.selectedWord[crossword.selectedCell].toString())
            Log.d("==Is Solved", crossword.isSolved.toString())
        }
    }
}