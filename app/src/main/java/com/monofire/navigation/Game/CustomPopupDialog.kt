package com.monofire.navigation.Game

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import androidx.navigation.findNavController
import com.monofire.navigation.Database.FirebaseDb
import com.monofire.navigation.R
import kotlinx.android.synthetic.main.game_over_popup.btn_back_main
import kotlinx.android.synthetic.main.success_popup.*


class CustomPopupDialog(val context: Context) {
    private val database = FirebaseDb()
    private var topScore: Int = 0
    private var state: Int = 0
    private val prefences = context.getSharedPreferences("db",Context.MODE_PRIVATE)
    private val editor = prefences.edit()


    fun createDialog(state: Int, view: View, topScore: Int,time:Int) {
        this.topScore = topScore
        this.state = state
        val currentScore=(time*3)+topScore

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(state)

        if (state == R.layout.success_popup) {
            dialog.txt_successScore.visibility=View.VISIBLE
            dialog.txt_successScore.text=currentScore.toString()

            editor.putInt("topScore",prefences.getInt("topScore",0)+currentScore)
            editor.apply()
            database.updateScore(context,prefences.getInt("topScore",0), currentScore)
        }
        dialog.btn_back_main.setOnClickListener {
            dialog.dismiss()
            view.findNavController().popBackStack()
        }
        dialog.show()
    }
}