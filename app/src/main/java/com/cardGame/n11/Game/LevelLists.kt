package com.cardGame.n11.Game


import com.cardGame.n11.Modal.Card
import com.cardGame.n11.R


class LevelLists {
    private val list = mutableListOf<Card>()
    private val images = mutableListOf(
        R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5
        , R.drawable.a6, R.drawable.a7, R.drawable.a8)


    fun level(item: Int): MutableList<Card> {
        val list1 = defaultList().take(item).shuffled().toMutableList()
        for (i in list1.indices) {
            list1.add(i, Card(i, images[i]))
        }
        list1.shuffle()
        return list1
    }

    private fun defaultList(): MutableList<Card> {
        list.add(Card(0, images[0]))
        list.add(Card(1, images[1]))
        list.add(Card(2, images[2]))
        list.add(Card(3, images[3]))
        list.add(Card(4, images[4]))
        list.add(Card(5, images[5]))
        list.add(Card(6, images[6]))
        list.add(Card(7, images[7]))
        return list
    }


}