package com.cardGame.n11


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.cardGame.n11.Base.BaseFragment
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adRequest = AdRequest.Builder().build()
        userStatus()

        btn_login.setOnClickListener {
            if (edt_login_mail.text.toString().isEmpty() && edt_login_password.text.toString().isEmpty()) {
                Toast.makeText(this.context, "Lütfen boş kutuları doldurunuz.", Toast.LENGTH_SHORT).show()

            } else {
                btn_login.isEnabled = false
                userLoginControl(edt_login_mail.text.toString().trim(), edt_login_password.text.toString())
            }

        }
        btn_register.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun userLoginControl(mail: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this.context, "Giriş yapılıyor...", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_dashboard)
            } else {
                btn_login.isEnabled = true
                Toast.makeText(this.context, "Giriş yapılamadı.Tekrar deneyiniz.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun userStatus() {
        if (firebaseAuth.currentUser != null) {
            findNavController()
                .navigate(
                    R.id.action_loginFragment_to_dashboard,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(
                            R.id.loginFragment,
                            true
                        ).build()
                )
        }
    }
}


