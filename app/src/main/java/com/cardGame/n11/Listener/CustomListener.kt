package com.cardGame.n11.Listener

import android.view.View
import com.cardGame.n11.Modal.Card

interface CustomListener {
    fun onItemClick(results: Card, position: Int, img_view: View)


}