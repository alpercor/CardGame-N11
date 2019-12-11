package com.monofire.navigation

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.monofire.navigation.Base.BaseFragment
import com.monofire.navigation.Database.FirebaseDb
import com.monofire.navigation.Modal.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.alert_select_photo.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*
import com.google.android.gms.ads.AdRequest


class RegisterFragment : BaseFragment(), View.OnClickListener {
    //private var mAdView: AdView? = null

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userEmail by lazy { edt_user_email.text.toString().trim() }
    private val userPassword by lazy { edt_user_password.text.toString() }
    lateinit var dialog: Dialog
    private var selectedPhotoUrl: Uri? = null
    private var db = FirebaseDb()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
        userStatus()


        img_select_profile.setOnClickListener {
            dialog = Dialog(this.context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.alert_select_photo)
            dialog.photo.setOnClickListener { uploadImage() }
            dialog.run {
                photo1.setOnClickListener(this@RegisterFragment)
                photo2.setOnClickListener(this@RegisterFragment)
                photo3.setOnClickListener(this@RegisterFragment)
                photo3.setOnClickListener(this@RegisterFragment)
                photo4.setOnClickListener(this@RegisterFragment)
                photo5.setOnClickListener(this@RegisterFragment)
                photo6.setOnClickListener(this@RegisterFragment)
                btn_select_photo.setOnClickListener { dialog.dismiss() }
            }
            dialog.show()
        }
        btn_register.setOnClickListener {
            btn_register.isEnabled=false
            if (userEmail.isEmpty() || userPassword.isEmpty() || selectedPhotoUrl==null){
                Toast.makeText(this.context, "Lüften boş kutucukları doldurunuz.", Toast.LENGTH_SHORT).show()
                btn_register.isEnabled=true

            }else{
                firebaseAuth.createUserWithEmailAndPassword(userEmail.toLowerCase().trim(), userPassword).addOnCompleteListener {
                    if (it.isSuccessful) {
                        userLogged()
                    } else {
                        Toast.makeText(this.context, "Hoşgeldiniz.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }

    private fun userLogged() {
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener {
            if (it.isSuccessful) {
                ImageUploadAndSaveData()
                Toast.makeText(this.context, "Giriş yapılıyor...", Toast.LENGTH_SHORT).show()

            } else {
                btn_register.isEnabled=true
                Toast.makeText(this.context, "Hata ile karşılaştınız.Lütfen tekrar deneyiniz." + it.exception, Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onClick(p0: View?) {
        val img: ImageView = p0 as ImageView
        val uri = Uri.parse("android.resource://com.monofire.navigation/drawable/" + img.tag)
        selectedPhotoUrl = uri
        img_select_profile.setImageResource(
            resources.getIdentifier(
                "@drawable/" + img.tag,
                null,
                activity!!.packageName
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Galery_Intent && resultCode == RESULT_OK && data != null) {
            selectedPhotoUrl = data.data
             val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, selectedPhotoUrl)
             img_select_profile.setImageBitmap(bitmap)

        }
    }

    private fun uploadImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Galery_Intent)
    }

    private fun ImageUploadAndSaveData() {
        if (selectedPhotoUrl == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("player").child("/images/$filename")
        ref.putFile(selectedPhotoUrl!!).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { img ->
                playerSaveData(img.toString())
            }.addOnFailureListener {
                Log.e("Error", "" + it.message)
            }
        }
    }

    private fun playerSaveData(imgUrl: String) {
        val player = Player(db.session.currentUser!!.uid, userEmail.substringBefore("@"), userPassword, 0, 0, imgUrl)
        db.createPlayer(player)
        findNavController().navigate(R.id.action_registerFragment_to_dashboard)
    }
    private fun userStatus() {
        if (firebaseAuth.currentUser != null) {
            findNavController()
                .navigate(R.id.action_registerFragment_to_dashboard,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.registerFragment,
                            true).build()
                )
        }
    }
    companion object {
        private const val Galery_Intent = 2
    }


}
