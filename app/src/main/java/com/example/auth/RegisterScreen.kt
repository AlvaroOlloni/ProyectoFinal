package com.example.auth

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomNav.HomeScreen
import com.example.dailyroutine3.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register_screen.*


class RegisterScreen : AppCompatActivity() {

    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        registrar()

        btn_Back.setOnClickListener {
            val iAuth = Intent(this, AuthActivity::class.java)
            startActivity(iAuth)
        }

    }

    private fun registrar() {
        btn_registrar.setOnClickListener {

            //En este "if" se comprueba que no haya ningun campo vacío, y en caso de que alguno no haya sido rellenado, aparece un mensaje por pantalla (Toast)
            //que indica que falta un campo por rellenar
            if (et_name.text.toString() == "" || et_username.text.toString() == "" || et_email.text.toString() == "" || et_password.text.toString() == "") {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()

                //A partir de aquí se empieza comienza la interacción con la base de datos
            } else {

                //Variables necesarias para almacenar temporalmente los datos del usuario (Nombre y nombre de usuario)
                val nombre: String = et_name.text.toString()
                val username: String = et_username.text.toString()

                //Código para crear una colección de datos en Cloud Firestore
                //Empieza:
                //Aquí se crea una agrupación de claves para luego "meterlas" en el documento asigando a cada usuario
                val info_users = hashMapOf(
                    "name" to nombre,
                    "username" to username
                )

                //Se crea la colección "usuarios" con su documento cuyo ID es el correo del usuario recién registrado
                //En caso de que la colección ya esté creada, simplemente se actualiza con un nuevo documento
                db.collection("usuarios").document(et_email.text.toString()).set(info_users)
                //Acaba

                //Este es un método propio de firebase que sirve para poder crear un nuevo usuario
                //pasándole las credenciales introducidas por el usuario
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    //Credenciales
                    et_email.text.toString(),
                    et_password.text.toString()
                ).addOnCompleteListener {
                    //En caso de exito se lanza la siguiente actividad
                    if (it.isSuccessful) {
                        val iHome = Intent(this, HomeScreen::class.java)
                        startActivity(iHome)
                    } else {
                        //En caso de fallo:
                        Toast.makeText(this, "Error de Registro", Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }
    }
}


