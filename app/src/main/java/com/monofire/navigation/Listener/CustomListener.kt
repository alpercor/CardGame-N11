package com.monofire.navigation.Listener

import android.view.View
import com.monofire.navigation.Modal.Card

interface CustomListener {
    fun onItemClick(results: Card, position: Int, img_view: View)


}