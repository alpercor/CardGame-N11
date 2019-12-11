package com.monofire.navigation.Game

class Score {

    var scoreNum: Int = 0

    fun topScore(level: Int): Int {
        scoreNum += (level * 2)
        return scoreNum
    }

}