package com.android.uptc.tgspuzzleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.android.uptc.tgspuzzleproject.R
import com.android.uptc.tgspuzzleproject.extensions.snack
import com.android.uptc.tgspuzzleproject.logic.GlobalValues
import com.rjbasitali.wordsearch.Word
import com.rjbasitali.wordsearch.WordSearchView
import kotlinx.android.synthetic.main.activity_cross_word.*
import kotlinx.android.synthetic.main.activity_search_word.*
import kotlinx.android.synthetic.main.activity_search_word.timer

class SearchWordActivity : AppCompatActivity() {

    private var randomDeck = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_word)
        initComponents()
        search_word.setOnWordSearchedListener(WordSearchView.OnWordSearchedListener { word ->
            search_word_layout.snack("$word encontrada!")
        })
    }

    private fun initComponents() {
        randomDeck = (0..4).random()
        search_word.setLetters(
            if(GlobalValues.levelGame == EASY) {
                when(randomDeck) {
                    1 -> getEasyDeckTwo()
                    2 -> getEasyDeckTwo()
                    else -> getEasyDeckTwo()
                }
            } else {
                when(randomDeck) {
                    1 -> getHardDeckOne()
                    2 -> getHardDeckTwo()
                    else -> getHardDeckThree()
                }
            }
        )

        // Timer
        timer.base = SystemClock.elapsedRealtime()
        timer.start()
    }

    private fun getEasyDeckOne(): Array<CharArray> {
        search_word.setWords(
            Word("ELEMENTO", false, 0, 2, 0, 9),
            Word("PROCESO", false, 1, 7, 7, 1),
            Word("PARAMETRO", false, 3, 0, 3, 8),
            Word("MODELO", false, 5, 0, 5, 5)
        )
        return arrayOf(
            "ISELEMENTO".toCharArray(),
            "CNJKFOOOIJ".toCharArray(),
            "POQWEDSUQQ".toCharArray(),
            "PARAMETROU".toCharArray(),
            "PALSCLESAN".toCharArray(),
            "ÑLAOCOEYEQ".toCharArray(),
            "AARDJCPROY".toCharArray(),
            "MPZAOQWERC".toCharArray(),
            "UJNRHBWSVQ".toCharArray(),
            "PLPUTAXSDD".toCharArray()
        )
    }

    private fun getEasyDeckTwo(): Array<CharArray> {
        search_word.setWords(
            Word("RANGO", false, 4, 4, 0, 8),
            Word("RELACION", false, 4, 4, 4, 11),
            Word("VARIEDAD", false, 9, 0, 9, 7),
            Word("VARIABLE", false, 2, 4, 9, 4),
            Word("SERVICIO", false, 0, 9, 7, 9)
        )
        return arrayOf(
            "AAKJHCSDOSFS".toCharArray(),
            "AUEJRPJGYEHW".toCharArray(),
            "PPAHVYNLARSD".toCharArray(),
            "PQWEAAIUAVSG".toCharArray(),
            "NMZXRELACION".toCharArray(),
            "ASDQIEZXCCBN".toCharArray(),
            "LKJDAGIUEITT".toCharArray(),
            "POASBRIISOTQ".toCharArray(),
            "BBSHLFWTATRA".toCharArray(),
            "VARIEDADLKSA".toCharArray(),
            "VARIEDADLKSA".toCharArray(),
            "VARIEDADLKSA".toCharArray()
        )
    }

    private fun getEasyDeckThree(): Array<CharArray> {
        search_word.setWords(
            Word("WORD", false, 3, 3, 6, 6),
            Word("SOME", false, 8, 3, 8, 6),
            Word("SEARCHING", false, 0, 1, 8, 1),
            Word("FOG", false, 3, 5, 5, 3)
        )
        return arrayOf(
            "ASCDEFGHIJ".toCharArray(),
            "AECDEFGHIJ".toCharArray(),
            "AACDEFGHIJ".toCharArray(),
            "ARCWEFGHIJ".toCharArray(),
            "ACCDOFGHIJ".toCharArray(),
            "AHCGERGHIJ".toCharArray(),
            "AICDEFDHIJ".toCharArray(),
            "ANCDEFGHIJ".toCharArray(),
            "AGCSOMEHIJ".toCharArray(),
            "ABCDEFGHIJ".toCharArray()
        )
    }

    private fun getHardDeckOne(): Array<CharArray> {
        search_word.setWords(
            Word("WORD", false, 3, 3, 6, 6),
            Word("SOME", false, 8, 3, 8, 6),
            Word("SEARCHING", false, 0, 1, 8, 1),
            Word("FOG", false, 3, 5, 5, 3)
        )
        return arrayOf(
            "ASCDEFGHIJ".toCharArray(),
            "AECDEFGHIJ".toCharArray(),
            "AACDEFGHIJ".toCharArray(),
            "ARCWEFGHIJ".toCharArray(),
            "ACCDOFGHIJ".toCharArray(),
            "AHCGERGHIJ".toCharArray(),
            "AICDEFDHIJ".toCharArray(),
            "ANCDEFGHIJ".toCharArray(),
            "AGCSOMEHIJ".toCharArray(),
            "ABCDEFGHIJ".toCharArray()
        )
    }

    private fun getHardDeckTwo(): Array<CharArray> {
        search_word.setWords(
            Word("WORD", false, 3, 3, 6, 6),
            Word("SOME", false, 8, 3, 8, 6),
            Word("SEARCHING", false, 0, 1, 8, 1),
            Word("FOG", false, 3, 5, 5, 3)
        )
        return arrayOf(
            "ASCDEFGHIJ".toCharArray(),
            "AECDEFGHIJ".toCharArray(),
            "AACDEFGHIJ".toCharArray(),
            "ARCWEFGHIJ".toCharArray(),
            "ACCDOFGHIJ".toCharArray(),
            "AHCGERGHIJ".toCharArray(),
            "AICDEFDHIJ".toCharArray(),
            "ANCDEFGHIJ".toCharArray(),
            "AGCSOMEHIJ".toCharArray(),
            "ABCDEFGHIJ".toCharArray()
        )
    }

    private fun getHardDeckThree(): Array<CharArray> {
        search_word.setWords(
            Word("WORD", false, 3, 3, 6, 6),
            Word("SOME", false, 8, 3, 8, 6),
            Word("SEARCHING", false, 0, 1, 8, 1),
            Word("FOG", false, 3, 5, 5, 3)
        )
        return arrayOf(
            "ASCDEFGHIJ".toCharArray(),
            "AECDEFGHIJ".toCharArray(),
            "AACDEFGHIJ".toCharArray(),
            "ARCWEFGHIJ".toCharArray(),
            "ACCDOFGHIJ".toCharArray(),
            "AHCGERGHIJ".toCharArray(),
            "AICDEFDHIJ".toCharArray(),
            "ANCDEFGHIJ".toCharArray(),
            "AGCSOMEHIJ".toCharArray(),
            "ABCDEFGHIJ".toCharArray()
        )
    }

    companion object {
        const val EASY = 0
        const val HARD = 1
    }
}