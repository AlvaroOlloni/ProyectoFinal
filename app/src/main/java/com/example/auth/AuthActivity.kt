package com.example.auth

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomNav.HomeScreen
import com.example.dailyroutine3.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        firebaseAuth = getInstance()
        checkUser()

        //Método para iniciar sesión con Correo y Contraseña
        login()

        //Acceder a la pantalla de registro

        btn_register.setOnClickListener {
            val iRegister = Intent(this, RegisterScreen::class.java)
            startActivity(iRegister)
        }

    }

    private fun login() {
        btn_logIn.setOnClickListener {

            if (et_email.text.toString()==""||et_password.text.toString()==""){

                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()

            }else{

                //Este es un método propio de firebase que sirve para poder iniicar sesión
                //pasándole las credenciales introducidas por el usuario
                getInstance()
                    //Le pasa las credenciales a Firebase
                    .signInWithEmailAndPassword(et_email.text.toString(), et_password.text.toString())
                    //Si son ciertas, lanza la siguiente pantalla
                    .addOnSuccessListener {
                        val iHome = Intent(this, HomeScreen::class.java)
                        startActivity(iHome)
                        //Si son erróneas, salta un error
                    }.addOnFailureListener {
                        Toast.makeText(applicationContext, "Error de autenticación", Toast.LENGTH_SHORT)
                            .show()
                    }

            }
        }
    }

    //Comprobar si ya se ha iniciado sesión, en ese caso se redirige al usuario a la pantalla Home
    private fun checkUser() {

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this@AuthActivity, HomeScreen::class.java))
            finish()
        }

    }
}