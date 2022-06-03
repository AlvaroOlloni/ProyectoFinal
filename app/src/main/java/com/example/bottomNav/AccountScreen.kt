package com.example.bottomNav

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.auth.AuthActivity
import com.example.dailyroutine3.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_account_screen.*


class AccountScreen : AppCompatActivity() {

    private val db = Firebase.firestore

    private var mAuth: FirebaseAuth? = null

    var mRootReference: DatabaseReference? = null

    private var txt_correoActual: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_screen)

        navegacionInferior()

        mRootReference = FirebaseDatabase.getInstance().reference

        //Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = mAuth!!.currentUser

        //Asignar al TextView del XML el valor del correo del usuario logueado
        val txt_correo: String? = currentUser?.email
        tv_email.text = txt_correo

        //Cerrar Sesión
        btn_logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val iMain = Intent(this, AuthActivity::class.java)
            startActivity(iMain)
        }

        //Asignar el correo del usuario actual al texto para el metodo obtenerDatos()
        txt_correoActual = txt_correo.toString()

        obtenerDatos()

    }

    //Barra de navegación entre los distintos Fragments (Home, Calendar, ...)
    private fun navegacionInferior() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    val iHome = Intent(this, HomeScreen::class.java)
                    startActivity(iHome)
                }
                R.id.item3 -> {
                    val iAdd = Intent(this, AddScreen::class.java)
                    startActivity(iAdd)
                }
                R.id.item5 -> {
                    val iAccount = Intent(this, AccountScreen::class.java)
                    startActivity(iAccount)
                }
            }
            true
        }
    }

    //Método para obtener datos del usuario activo
    private fun obtenerDatos() {

        //Crear una variable cuya función sea crear una conexión con información de cada usuario
        //(Nombre de usuario y nombre y apellidos) mediante el correo electrónico
        val docRef = db.collection("usuarios").document(tv_email.text.toString())

        //Asignar la información del usuario desde firebase a la variable local para mostrarla por pantalla
        docRef.get().addOnSuccessListener { document ->

            if (document != null) {
                val nombre = document.data?.get("name").toString()
                tv_nameFinal.text = nombre
                val username = document.data?.get("username").toString()
                tv_user.text = username
            }

        }

    }

}