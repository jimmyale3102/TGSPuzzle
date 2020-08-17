package com.android.uptc.tgspuzzleproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.core.view.forEach
import com.android.uptc.tgspuzzleproject.R
import com.android.uptc.tgspuzzleproject.extensions.snack
import com.android.uptc.tgspuzzleproject.logic.GlobalValues
import com.google.android.material.textview.MaterialTextView
import com.rjbasitali.wordsearch.Word
import com.rjbasitali.wordsearch.WordSearchView
import kotlinx.android.synthetic.main.activity_cross_word.*
import kotlinx.android.synthetic.main.activity_search_word.*
import kotlinx.android.synthetic.main.activity_search_word.timer

class SearchWordActivity : AppCompatActivity() {

    private var randomDeck = 0
    private var wordsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_word)
        initComponents()
    }

    private fun initComponents() {

        search_word.setOnWordSearchedListener(WordSearchView.OnWordSearchedListener { word ->
            search_word_layout.snack("$word encontrada!")
            if(wordsList.isNotEmpty()) {
                for(i in 0 until words_content.childCount) {
                    val textView = words_content.getChildAt(i) as MaterialTextView
                    if(textView.text.toString() == word) {
                        textView.visibility = View.GONE
                        wordsList.remove(word)
                    }
                }
            }
        })
        search_word.setBackgroundColor(getColor(R.color.primaryLightColor))
        randomDeck = (0..4).random()
        search_word.setLetters(
            if(GlobalValues.levelGame == EASY) {
                when(randomDeck) {
                    1 -> getEasyDeckOne()
                    2 -> getEasyDeckTwo()
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
            Word("MODELO", false, 0, 5, 5, 5)
        )
        addWord("ELEMENTO")
        addWord("PROCESO")
        addWord("PARAMETRO")
        addWord("MODELO")
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
        addWord("RANGO")
        addWord("RELACION")
        addWord("VARIEDAD")
        addWord("VARIABLE")
        addWord("SERVICIO")
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
        addWord("ARMONIA")
        addWord("CONTEXTO")
        addWord("CIBERNETICA")
        addWord("ENERGIA")
        addWord("ATRIBUTO")
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
            Word("SISTEMAABIERTO", false, 0, 0, 13, 0),
            Word("RETROINPUT", false, 0, 2, 9, 2),
            Word("MORFOGENESIS", false, 12, 1, 12, 12),
            Word("SENTROPIA", false, 1, 1, 1, 9),
            Word("MORFOSTASIS", false, 2, 12, 12, 12),
            Word("ESTABILIDAD", false, 1, 11, 11, 1)
        )
        addWord("SISTEMAABIERTO")
        addWord("RETROINPUT")
        addWord("MORFOGENESIS")
        addWord("SENTROPIA")
        addWord("MORFOSTASIS")
        addWord("ESTABILIDAD")
        return arrayOf(
            "STROALIMENTACI".toCharArray(),
            "ISENTROPIAQEEE".toCharArray(),
            "SDTHFGQWEYSSMS".toCharArray(),
            "TERYADLAMTTDOE".toCharArray(),
            "ETORASMOAAISRQ".toCharArray(),
            "MAIESTABBLIDFD".toCharArray(),
            "ASNESTIIILIDOB".toCharArray(),
            "AEPSTLLPYESMSN".toCharArray(),
            "BOUSIIOJHDTQTW".toCharArray(),
            "ITTDDRIMENTAAI".toCharArray(),
            "EJAATJAHSYQISE".toCharArray(),
            "RDDENFGQWEYAIM".toCharArray(),
            "TMORFOGENESISG".toCharArray(),
            "OOALIMENTACION".toCharArray()
        )
    }

    private fun getHardDeckTwo(): Array<CharArray> {
        search_word.setWords(
            Word("ORGANIZACION", false, 1, 0, 1, 11),
            Word("PROCESO", false, 4, 1, 4, 7),
            Word("SUBSISTEMA", false, 7, 1, 7, 10),
            Word("ENTROPIA", false, 0, 3, 7, 10),
            Word("OPERADORES", false, 10, 2, 10, 11),
            Word("VARIABLE", false, 3, 10, 10, 10)
        )
        addWord("ORGANIZACION")
        addWord("PROCESO")
        addWord("SUBSISTEMA")
        addWord("ENTROPIA")
        addWord("VARIABLE")
        addWord("OPERADORES")
        return arrayOf(
            "ASCEEFGPROAS".toCharArray(),
            "ORGANIZACION".toCharArray(),
            "AACDETGHIJDF".toCharArray(),
            "DRCWEFRSISVO".toCharArray(),
            "CPROCESOIJAE".toCharArray(),
            "HHCGERGHPJRE".toCharArray(),
            "JICDEFDHIIIF".toCharArray(),
            "QSUBSISTEMAJ".toCharArray(),
            "OGCSOMEOPEBS".toCharArray(),
            "PNCDEFGHIJLJ".toCharArray(),
            "RGOPERADORES".toCharArray(),
            "ABCDEFGDESER".toCharArray()
        )
    }

    private fun getHardDeckThree(): Array<CharArray> {
        search_word.setWords(
            Word("ADAPTABILIDAD", false, 1, 0, 1, 12),
            Word("TELEOLOGIA", false, 5, 3, 5, 12),
            Word("SIMBIOTICAS", false, 9, 1, 9, 11),
            Word("AMBIENTE", false, 1, 0, 8, 7),
            Word("SUPERFLUA", false, 2, 0, 10, 0)
        )
        addWord("ADAPTABILIDAD")
        addWord("TELEOLOGIA")
        addWord("SIMBIOTICAS")
        addWord("AMBIENTE")
        addWord("SUPERFLUA")
        return arrayOf(
            "ISETROPIAQEEE".toCharArray(),
            "ADAPTABILIDAD".toCharArray(),
            "SMRYALAMTTDOE".toCharArray(),
            "UTBRASOAAISRQ".toCharArray(),
            "PAIISTABLIDFD".toCharArray(),
            "ESNTELEOLOGIA".toCharArray(),
            "REPSTNLPYSMSN".toCharArray(),
            "FOUSIITJHDQTW".toCharArray(),
            "LTTDDRIEENTAI".toCharArray(),
            "USIMBIOTICASI".toCharArray(),
            "ADDENFGQWEYAM".toCharArray(),
            "TMORFOGENESIG".toCharArray(),
            "OOALIMENTACIO".toCharArray()
        )
    }

    private fun addWord(word: String) {
        val textView = MaterialTextView(this)
        textView.text = word
        words_content.addView(textView)
        wordsList.add(word)
    }

    companion object {
        const val EASY = 0
        const val HARD = 1
    }
}