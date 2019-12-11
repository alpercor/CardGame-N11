package com.cardGame.n11


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.cardGame.n11.Base.BaseFragment
import com.cardGame.n11.Database.FirebaseDb
import com.cardGame.n11.Listener.DataListener
import com.cardGame.n11.Modal.Player
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : BaseFragment(), DataListener {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDb()
    private var progressBar: ProgressBar? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)


        database.profileGetData()
        database.dataListener = this
        return  view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_play.isEnabled=false
        btn_leaders.isEnabled=false
        btn_sign_out.isEnabled=false


        if (progressBar!=null){
            progressBar!!.visibility = View.VISIBLE

        }
        val adRequest = AdRequest.Builder().build()
        btn_play.setOnClickListener {
            navigate(R.id.action_dashboard_to_gameFragment)
        }
        btn_leaders.setOnClickListener {
            navigate(R.id.action_dashboard_to_leadersFragment)
        }
        btn_sign_out.setOnClickListener {
            firebaseAuth.signOut()
            navigate(R.id.loginFragment)
            val prefences = context?.getSharedPreferences("db", Context.MODE_PRIVATE)
            prefences?.edit()?.clear()?.apply()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onPlayerList(playerList: Player) {
        Picasso.get().load(playerList.imgUrl).placeholder(R.drawable.ic_person_black_24dp).into(img_profile_dash)
        txt_name.text=playerList.mail
        txt_score.text="Skor :"+playerList.topScore.toString()
        txt_highscore.text="En Yüksek skor :"+playerList.highScore.toString()
        progressBar?.visibility=View.GONE
        btn_play.isEnabled=true
        btn_leaders.isEnabled=true
        btn_sign_out.isEnabled=true

    }
    private fun navigate(action: Int) {
        view?.let { _view ->
            Navigation.findNavController(_view).navigate(action)
        }
    }
}
