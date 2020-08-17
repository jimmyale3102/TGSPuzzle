package com.android.uptc.tgspuzzleproject.logic

import android.app.Application

class GlobalValues: Application() {
    companion object {
        var playerId = ""
        var username = ""
        /*
        EASY = 0
        HARD = 1
        */
        var levelGame = -1
    }
}