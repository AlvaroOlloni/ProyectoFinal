package com.example.habitos

import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomNav.AddScreen
import com.example.bottomNav.HomeScreen
import com.example.dailyroutine3.R
import com.example.fragments.DatePickerFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_habitos.*

class Habitos : AppCompatActivity() {

    private val db = Firebase.firestore

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habitos)

        recibirExtra()

        tv_tipoHabito.text = "Tipo: "+recibirExtra()

        //Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        showDatePickerDialog()

        guardar()

        volver()

    }

    //Método para mostrar por pantalla el cuadro para escoger la fecha de fin para los eventos
    private fun showDatePickerDialog() {
        et_Date.setOnClickListener {
            val newFragment =
                DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    // +1 because January is zero
                    val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                    et_Date.setText(selectedDate)

                })

            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun guardar() {

        val currentUser: FirebaseUser? = mAuth!!.currentUser

        val txt_correo: String? = currentUser?.email

        button_Guardar.setOnClickListener {

            //Llamar a la colección que contiene los hábitos
            val Ref = db.collection(txt_correo.toString())

            //Crear los parametros del hábito
            val ggbData = mapOf(
                "nombre" to et_nombreHabito.text.toString(),
                "descripcion" to et_descripcion.text.toString(),
                "fecha" to et_Date.text.toString(),
                "tipo" to recibirExtra().toString(),
                "horario" to "mañana"
            )

            //Implementar dichos parámetros (ID del documento generado por firebase)
            Ref.add(ggbData)

            val iHome = Intent(this, HomeScreen::class.java)
            Toast.makeText(this, "Hábito creado con éxito", Toast.LENGTH_SHORT).show()
            startActivity(iHome)

        }

        button_Guardar2.setOnClickListener {

            val Ref = db.collection(txt_correo.toString())

            val ggbData = mapOf(
                "nombre" to et_nombreHabito.text.toString(),
                "descripcion" to et_descripcion.text.toString(),
                "fecha" to et_Date.text.toString(),
                "tipo" to recibirExtra().toString(),
                "horario" to "tarde"
            )
            Ref.add(ggbData)

            val iHome = Intent(this, HomeScreen::class.java)
            Toast.makeText(this, "Hábito creado con éxito", Toast.LENGTH_SHORT).show()
            startActivity(iHome)

        }

        button_Guardar3.setOnClickListener {

            val Ref = db.collection(txt_correo.toString())

            val ggbData = mapOf(
                "nombre" to et_nombreHabito.text.toString(),
                "descripcion" to et_descripcion.text.toString(),
                "fecha" to et_Date.text.toString(),
                "tipo" to recibirExtra().toString(),
                "horario" to "noche"
            )
            Ref.add(ggbData)

            val iHome = Intent(this, HomeScreen::class.java)
            Toast.makeText(this, "Hábito creado con éxito", Toast.LENGTH_SHORT).show()
            startActivity(iHome)

        }

    }

    //Recibir datos de la clase AddScreen(Nombre del hábito seleccionado)
    fun recibirExtra(): String? {
        val bundle = intent.extras
        return bundle?.getString("tipo de habito")

    }

    private fun volver() {
        button_Back.setOnClickListener {
            val iHome = Intent(this, AddScreen::class.java)
            Toast.makeText(this, "Hábito cancelado con éxito", Toast.LENGTH_SHORT).show()
            startActivity(iHome)
        }
    }

}