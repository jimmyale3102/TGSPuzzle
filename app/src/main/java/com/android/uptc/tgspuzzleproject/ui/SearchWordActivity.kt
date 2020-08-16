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
                    1 -> getEasyDeckThree()
                    2 -> getEasyDeckThree()
                    else -> getEasyDeckThree()
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
            Word("PROCESO", false, 7, 1, 7, 7),
            Word("PARAMETRO", false, 3, 0, 3, 8),
            Word("MODELO", false, 5, 0, 5, 5)
        )
        return arrayOf(
            "ISELEMENTO".toCharArray(),
            "CNJKFOOOIJ".toCharArray(),
            "POQWEDZUQQ".toCharArray(),
            "PARAMETROU".toCharArray(),
            "PALSCLEZAN".toCharArray(),
            "ÑLAOCOEYEQ".toCharArray(),
            "AARDJCPROY".toCharArray(),
            "MPROCESORC".toCharArray(),
            "UJNRHBWSVQ".toCharArray(),
            "PLPUTAXSDD".toCharArray()
        )
    }

    private fun getEasyDeckTwo(): Array<CharArray> {
        search_word.setWords(
            Word("RANGO", false, 6, 11, 2, 11),
            Word("RELACION", false, 4, 4, 4, 11),
            Word("VARIEDAD", false, 9, 0, 9, 7),
            Word("VARIABLE", false, 2, 4, 9, 4),
            Word("SERVICIO", false, 0, 9, 7, 9)
        )
        return arrayOf(
            "AAKJHCSDOSFS".toCharArray(),
            "AUEJRPJGYEHW".toCharArray(),
            "PPAHVYNLARSO".toCharArray(),
            "PQWEAAIUAVSG".toCharArray(),
            "NMZXRELACION".toCharArray(),
            "ASDQIEZXCCBA".toCharArray(),
            "LKJDAGIUEITR".toCharArray(),
            "POASBRIISOTQ".toCharArray(),
            "BBSHLFWTATRA".toCharArray(),
            "VARIEDADLKSA".toCharArray(),
            "LAHEYXNNAPRO".toCharArray(),
            "ASKRANGDOAUS".toCharArray()
        )
    }

    private fun getEasyDeckThree(): Array<CharArray> {
        search_word.setWords(
            Word("ARMONIA", false, 0, 1, 0, 7),
            Word("CONTEXTO", false, 1, 4, 1, 11),
            Word("CIBERNETICA", false, 6, 0, 6, 10),
            Word("ENERGIA", false, 3, 4, 9, 4),
            Word("ATRIBUTO", false, 0, 7, 7, 7)
        )
        return arrayOf(
            "SARMONIASNSD".toCharArray(),
            "AIOACONTEXTO".toCharArray(),
            "PALSKUHREDCA".toCharArray(),
            "ZXGHEEAIPESS".toCharArray(),
            "PWOENRUBYAQC".toCharArray(),
            "MMANEKGUTSSV".toCharArray(),
            "CIBERNETICAH".toCharArray(),
            "APORGQTOBXCY".toCharArray(),
            "AOWUINXCBHAR".toCharArray(),
            "MKISAHAGEEQQ".toCharArray(),
            "CDEMKOSADAWQ".toCharArray(),
            "ATRIBUTDOAAO".toCharArray()
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