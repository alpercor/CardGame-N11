package com.monofire.navigation.Game

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import com.monofire.navigation.Modal.Card
import com.monofire.navigation.R
import kotlinx.android.synthetic.main.fragment_game.view.*
import kotlinx.android.synthetic.main.item_row.view.*
import com.monofire.navigation.GameFragment
import com.monofire.navigation.Modal.SelectedCard
import java.util.concurrent.TimeUnit


class CardChange(val context: Context, view: View) {
    private var size = 0
    private val view = view
    private var step: Int = 0
    private val selectedCard = mutableListOf<SelectedCard>()
    private var adim = 0
    private val score = Score()
    private val succesDialog = CustomPopupDialog(context)
    private var lastLevel: Int = 4
    private var nextLevelCount = 1
    //private val stepCountControl=StepCountControl()

    fun cardSizeControl(results: Card, img_view: View, size: Int) {
        this.size = size

        img_view.img_card?.setImageResource(results.name)

        if (selectedCard.size == 2) {
            if (selectedCard[0].id == selectedCard[1].id && selectedCard[0].imgView != selectedCard[1].imgView) {
                selectedCard.forEach { cardAnimate(it.imgView) }
                clearLists()
                addItemList(results, img_view)
            } else {
                //stepCountControl()
                Log.e("AdimSayisi", "" + (++adim))
                selectedCard.forEach {
                    it.imgView.img_card.setImageResource(DEFAULT_BG)
                }
                clearLists()
                addItemList(results, img_view)
            }
        } else if (selectedCard.size == 1) {

            addItemList(results, img_view)
            if (selectedCard[0].id == selectedCard[1].id && selectedCard[0].imgView != selectedCard[1].imgView) {
                selectedCard.forEach {
                    cardAnimate(it.imgView.img_card)
                }
                Log.e("AdimSayisi", "" + (++adim))
                //openedCardList.forEach { cardAnimate(it) }
                step++
                cardFinishControl()
                clearLists()
            }
        } else {
            selectedCard.forEach {
                Log.e("AdimSayisi", "" + (++adim))
                it.imgView.img_card.setImageResource(DEFAULT_BG)
            }
            //openedCardList.forEach { it.img_card.setImageResource(DEFAULT_BG) }
            addItemList(results, img_view)
        }
    }

    @SuppressLint("SetTextI18n")
    fun cardFinishControl(): Boolean {

        if ((size / 2) == step) {
            score.topScore(nextLevelCount)
            view.txt_score.text = "Score: ${score.scoreNum}"
            nextLevelCount += 1
            step = 0
            isFinishing = true
            gameSuccesFinish()
        } else {
            score.topScore(nextLevelCount)
            view.txt_score.text = "Score: ${score.scoreNum}"
            isFinishing = false
        }
        return isFinishing
    }

    private fun clearLists() {
        //sameList.clear()
        selectedCard.forEach {
            it.imgView.isEnabled = true
        }

        selectedCard.clear()
    }

    private fun addItemList(results: Card, imgView: View) {
        selectedCard.add(SelectedCard(results.id, imgView))
        imgView.isEnabled = false
    }

    private fun cardAnimate(it: View) {
        it.animate()
            .translationY(it.height.toFloat())
            .alpha(0.0f)
            .setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    it.isEnabled = false
                }
            })
    }

    private fun gameSuccesFinish() {
        if (--lastLevel == 0) {
            succesDialog.createDialog(R.layout.success_popup, view, score.scoreNum, timeReformat())
            GameFragment.time?.cancel()
        }
    }

    private fun timeReformat(): Int {
        val time = view.txt_timer.text
        val min = Integer.parseInt(time.substring(0, 2)).toLong()
        val sec = Integer.parseInt(time.substring(3)).toLong()
        val t = min * 60L + sec
        return TimeUnit.SECONDS.toSeconds(t).toInt()

    }

    companion object {
        var DEFAULT_BG: Int = R.drawable.card_bg
        var isFinishing: Boolean = false
    }
}