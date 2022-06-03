package com.example.bottomNav

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.adapter.MostrarDatos
import com.example.dailyroutine3.R
import kotlinx.android.synthetic.main.activity_homescreen.*


class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)

        val view:View = bottomNavigationView.findViewById(R.id.item1)
        view.performClick()

        navegacionInferior()

        navegacionSuperior()

    }

    //Control de los 3 botones superiores (Mañana, Tarde, Noche)
    private fun navegacionSuperior(){

        buttonManana.setOnClickListener {
            val nombre:String = "mañana"
            val iManana = Intent(this, MostrarDatos::class.java)
            iManana.putExtra("horario", nombre)
            startActivity(iManana)
        }

        buttonTarde.setOnClickListener {
            val nombre:String = "tarde"
            val iTarde = Intent(this, MostrarDatos::class.java)
            iTarde.putExtra("horario", nombre)
            startActivity(iTarde)
        }

        buttonNoche.setOnClickListener {
            val nombre:String = "noche"
            val iNoche = Intent(this, MostrarDatos::class.java)
            iNoche.putExtra("horario", nombre)
            startActivity(iNoche)
        }

    }

    //Barra de navegación entre los distintos Fragments (Home, Calendar, ...)
    private fun navegacionInferior() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.item3 -> {
                    val iAdd = Intent(this, AddScreen::class.java)

                    bottomNavigationView.selectedItemId

                    startActivity(iAdd)
                }

                R.id.item5 -> {
                    val iAccount = Intent(this, AccountScreen::class.java)
                    bottomNavigationView.selectedItemId
                    startActivity(iAccount)
                }
            }
            true
        }
    }

}
