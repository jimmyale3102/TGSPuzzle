package com.android.uptc.tgspuzzleproject.logic

import android.app.Application

class GlobalValues: Application() {
    companion object {
        var playerId = ""
        var username = ""
        /*
        EASY_CROSSWORD = 1
        HARD_CROSSWORD = 2
        EASY_SEARCH_WORD = 3
        HARD_SEARCH_WORD = 4
        */
        var scoreType = 0
        /*
        EASY = 0
        HARD = 1
        */
        var levelGame = -1
    }
}