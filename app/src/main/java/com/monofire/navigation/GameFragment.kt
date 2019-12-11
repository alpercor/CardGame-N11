package com.monofire.navigation


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.monofire.navigation.Adapter.CardAdapter
import com.monofire.navigation.Base.BaseFragment
import com.monofire.navigation.Game.CardChange
import com.monofire.navigation.Game.CustomPopupDialog
import com.monofire.navigation.Game.LevelLists
import com.monofire.navigation.Listener.CustomListener
import com.monofire.navigation.Modal.Card
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.view.*
import java.util.concurrent.TimeUnit
import com.google.android.gms.ads.reward.RewardedVideoAd


class GameFragment : BaseFragment() {


    private var LEVELID: Int = 1
    private val selectLevel = LevelLists()
    private lateinit var cardChange: CardChange
    lateinit var gameover: CustomPopupDialog
    lateinit var rc_view: RecyclerView
    private lateinit var mInterstitialAd: InterstitialAd


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        mInterstitialAd = InterstitialAd(activity)
        mInterstitialAd.adUnitId = "ca-app-pub-9883187927734086/4763575660"

        cardChange = CardChange(this.context!!, view)
        gameover = CustomPopupDialog(this.context!!)
        rc_view = view.findViewById(R.id.rc_view)
        setNewLevel(LEVELID)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        Log.e("SONUC", "geldi")

        time = object : CountDownTimer(180000, 1000) {
            override fun onFinish() {
                gameover.createDialog(R.layout.game_over_popup, view, 0, 0)
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {

                // Log.e("TIME",""+p0)
                view.txt_timer.text = String.format(
                    "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(p0) % 60, TimeUnit.MILLISECONDS.toSeconds(p0) % 60
                )
            }
        }.start()
    }

    private fun adapterSetList(liste: MutableList<Card>, spanCount: Int) {
        val gridLayout = GridLayoutManager(context, spanCount)
        rc_view.layoutManager = gridLayout
        rc_view.setHasFixedSize(true)
        val adapter = CardAdapter(liste, object : CustomListener {
            override fun onItemClick(results: Card, position: Int, img_view: View) {
                cardChange.cardSizeControl(results, img_view, liste.size)
                cardFinishControle()
            }
        })
        rc_view.adapter = adapter
    }

    private fun cardFinishControle() {

        if (CardChange.isFinishing) {
            ++LEVELID
            val handler = Handler()
            handler.postDelayed({
                setNewLevel(LEVELID)

            }, 1500)
            CardChange.isFinishing = false
        }
    }

    private fun setNewLevel(level: Int) {


        when (level) {
            1 -> adapterSetList(selectLevel.level(3), 3)


            2 ->{ adapterSetList(selectLevel.level(4), 4)
                getAd()
            }
            3 -> adapterSetList(selectLevel.level(6), 4)

            4 -> {adapterSetList(selectLevel.level(8), 4)
            getAd()
        }
        }
    }

    private fun getAd() {
        if (mInterstitialAd.isLoaded) {

            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("DURUM", "start")


    }

    override fun onStop() {
        super.onStop()
        Log.e("DURUM", "stop")


    }

    override fun onDetach() {
        super.onDetach()
        Log.e("DURUM", "detach")
        time?.cancel()
    }

    override fun onResume() {
        super.onResume()
        Log.e("DURUM", "resume")

    }


    companion object {

        var time: CountDownTimer? = null
    }


}
