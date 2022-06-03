package com.example.adapter

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomNav.HomeScreen
import com.example.dailyroutine3.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MostrarDatos : AppCompatActivity() {

    private lateinit var recyclerViewHabitos: RecyclerView
    var mAdapter: HabitoAdapter? = null
    var mFirestore: FirebaseFirestore? = null
    var mAuth: FirebaseAuth? = null
    var listener: RecyclerView.RecyclerListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        title = "Hábitos para la " + recibirExtra()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_datos)

        //Asociar el recyclerView
        recyclerViewHabitos = findViewById(R.id.recyclerHabitos)
        recyclerViewHabitos.layoutManager = LinearLayoutManager(this)
        mFirestore = FirebaseFirestore.getInstance()

        //Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser

        //Asignar el correo del usuario activo a una variable de tipo String
        val txt_correo = currentUser!!.email

        //Crear la consulta
        val query = mFirestore!!.collection(txt_correo!!).whereEqualTo("horario", recibirExtra())

        //Ejecutar la consulta
        val firestoreRecyclerOptions = FirestoreRecyclerOptions.Builder<Habito>()
            .setQuery(query, Habito::class.java).build()
        mAdapter = HabitoAdapter(firestoreRecyclerOptions)

        //Mantener el recyclerView actualizado siempre que se esté en la app
        mAdapter!!.notifyDataSetChanged()
        recyclerViewHabitos.adapter = mAdapter

        //Método para volver a HomeScreen
        volver()
    }

    //Regresar al inicio de la app
    fun volver() {
        val btn_back = findViewById<Button>(R.id.button_Back2)
        btn_back.setOnClickListener {
            val iHome = Intent(this@MostrarDatos, HomeScreen::class.java)
            startActivity(iHome)
        }
    }

    //Método para recibir el horario, ya sea mañana, tarde o noche
    private fun recibirExtra(): String? {
        return intent.getStringExtra("horario")
    }

    private fun recibirExtra2(): String? {
        return intent.getStringExtra("filtroNombre")
    }

    override fun onStart() {
        super.onStart()
        mAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter!!.stopListening()
    }

}