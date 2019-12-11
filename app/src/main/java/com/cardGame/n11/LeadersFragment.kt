package com.cardGame.n11


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cardGame.n11.Adapter.LeadersAdapter
import com.cardGame.n11.Database.FirebaseDb
import com.cardGame.n11.Modal.Player
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_leaders.*

class LeadersFragment : Fragment() {
    val database = FirebaseDb()
    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_leaders, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (progressBar!=null){
            progressBar!!.visibility = View.VISIBLE

        }
        leadersList()
        img_back.setOnClickListener {
            findNavController().navigate(R.id.dashboard)
        }
    }
    fun leadersList() {
        val tempLeaders= mutableListOf<Player>()
        database.databaseReference!!.orderByChild("topScore").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("son",""+p0.message)
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (playerData: DataSnapshot in p0.children) {
                    Log.e("DATA",""+playerData.toString())
                    val player = playerData.getValue(Player::class.java)
                    tempLeaders.add(player!!)
                    val leaders= tempLeaders.sortedByDescending { Player->Player.topScore }.toMutableList()
                    setAdapter(leaders)
                    progressBar?.visibility=View.GONE

                }
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter(leaders: MutableList<Player>) {

        val llm = LinearLayoutManager(context)
        llm.orientation=LinearLayoutManager.VERTICAL
        rc_view_leaders?.layoutManager = llm
        val adapter = LeadersAdapter(leaders)
        rc_view_leaders?.adapter = adapter
    }
}
