package com.example.bottomNav

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dailyroutine3.R
import com.example.habitos.Habitos
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_screen.*
import kotlinx.android.synthetic.main.activity_homescreen.bottomNavigationView


class AddScreen : AppCompatActivity() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_screen)

        val view: View = bottomNavigationView.findViewById(R.id.item1)
        view.performClick()

        navegacionInferior()

        val fragment = Fragment()

        escogerOpcion()

    }

    /*Método para escoger el tipo de hábito seleccionado
    Hay que pasar en cada onClick que información aporta cada botón a la siguiente pantalla;
    es decir, si presiono hábito 1, le paso por el intent el tipo de hábito que es, para que así,
    en las notificaciones, pantalla HomeScreen y demás, el usuario pueda ver la información adecuada
    */
    fun escogerOpcion() {

        btn_hab1.setOnClickListener {
            val nombre: String = "estudio"
            val iHabito = Intent(this, Habitos::class.java)
            //nombre del dato y valor del dato
            iHabito.putExtra("tipo de habito", nombre)
            startActivity(iHabito)

        }

        btn_hab2.setOnClickListener {
            val nombre: String = "deportes"
            val iHabito = Intent(this, Habitos::class.java)
            iHabito.putExtra("tipo de habito", nombre)
            startActivity(iHabito)
        }

        btn_hab3.setOnClickListener {
            val nombre: String = "nutricion"
            val iHabito = Intent(this, Habitos::class.java)
            iHabito.putExtra("tipo de habito", nombre)
            startActivity(iHabito)
        }

        btn_hab4.setOnClickListener {
            val nombre: String = "salud"
            val iHabito = Intent(this, Habitos::class.java)
            iHabito.putExtra("tipo de habito", nombre)
            startActivity(iHabito)
        }

        btn_hab5.setOnClickListener {
            val nombre: String = "trabajo"
            val iHabito = Intent(this, Habitos::class.java)
            iHabito.putExtra("tipo de habito", nombre)
            startActivity(iHabito)
        }

        btn_hab6.setOnClickListener {
            val nombre: String = "hogar"
            val iHabito = Intent(this, Habitos::class.java)
            iHabito.putExtra("tipo de habito", nombre)
            startActivity(iHabito)
        }

    }

    //Barra de navegación entre los distintos Fragments (Home, Calendar, ...)
    private fun navegacionInferior() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    val iHome = Intent(this, HomeScreen::class.java)
                    bottomNavigationView.selectedItemId
                    startActivity(iHome)
                }
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