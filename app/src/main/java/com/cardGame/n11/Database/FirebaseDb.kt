package com.cardGame.n11.Database

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.cardGame.n11.Listener.DataListener
import com.cardGame.n11.Modal.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FirebaseDb {
    val session: FirebaseAuth = FirebaseAuth.getInstance()
    val databaseReference: DatabaseReference? = FirebaseDatabase.getInstance().getReference("player")
    var dataListener: DataListener? = null

    fun createPlayer(player: Player) {
        databaseReference!!.child(session.currentUser!!.uid)
            .setValue(player).addOnCompleteListener {
                if (it.isSuccessful) {
                    //profileGetData()
                } else {
                }
            }
    }

    @SuppressLint("CommitPrefEdits")
    fun updateScore(context: Context, score: Int, highscore: Int) {

        val prefences = context.getSharedPreferences("db", Context.MODE_PRIVATE)
        val editor = prefences.edit()

        FirebaseDatabase.getInstance().getReference("player").child(session.currentUser!!.uid).run {

            val tempHighscore = prefences.getInt("high", 0)
            if (tempHighscore == 0 || highscore > tempHighscore) {
                editor.putInt("high", highscore)
                editor.apply()
                this.child("highScore").setValue(highscore)
            }
            this.child("topScore").setValue(score)
        }
    }


    fun profileGetData() {

        databaseReference!!.child(session.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("Error", "" + p0.message)
                }

                override fun onDataChange(p0: DataSnapshot) {

                    p0.run {
                        val player = p0.getValue(Player::class.java)
                        dataListener?.onPlayerList(player!!)

                    }

                }


            })
    }


}