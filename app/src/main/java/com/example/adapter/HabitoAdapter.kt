package com.example.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyroutine3.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


open class HabitoAdapter(options: FirestoreRecyclerOptions<Habito?>) :
    FirestoreRecyclerAdapter<Habito, HabitoAdapter.ViewHolder>(options), View.OnClickListener {

    private var listener: View.OnClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_view_habitos, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, habito: Habito) {

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        val currentUser: FirebaseUser? = mAuth.currentUser

        holder.tv_nombre.text = habito.nombre
        holder.tv_descripcion.text = habito.descripcion
        holder.tv_tipo.text = habito.tipo
        holder.tv_fecha.text = habito.fecha

        //En este TextView se almacena el correo del usuario activo para poder obtenerlo mas adelante
        holder.textView4.text = currentUser?.email.toString()

        //Al pulsar el botón "Eliminar" (bt_acceder) creamos una consulta que nos permite eliminar el
        //hábito deseado a través de su nombre
        holder.bt_acceder.setOnClickListener{
            //Log para ver si el hábito pulsado es igual al hábito utilizado en las siguientes líneas
            Log.d("demo", "onClick: nombre clicked " + holder.tv_nombre.text.toString())

            val rootRef:FirebaseFirestore = FirebaseFirestore.getInstance()
            val itemsRef: CollectionReference = rootRef.collection(holder.textView4.text.toString())
            val query: Query = itemsRef.whereEqualTo("nombre", holder.tv_nombre.text.toString())

            //Si las líneas anteriores se ejecutan sin problemas, se procede a eliminar el hábito,
            //sino, se muestra el error en el Logcat
            query.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        itemsRef.document(document.id).delete()
                    }
                } else {
                    Log.d(
                        "fallo",
                        "Error getting documents: ",
                        task.exception
                    )
                }
            }
        }

    }

    override fun onClick(view: View) {
        if (listener != null) {
            listener!!.onClick(view)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_nombre: TextView = itemView.findViewById(R.id.tv_nombre)
        var tv_descripcion: TextView = itemView.findViewById(R.id.tv_Descripcion)
        var tv_tipo: TextView = itemView.findViewById(R.id.tv_Tipo)
        var tv_fecha: TextView = itemView.findViewById(R.id.tv_fechaInicio)

        var bt_acceder: Button = itemView.findViewById(R.id.btn_Acceder)

        var textView4:TextView = itemView.findViewById(R.id.textView4)

    }

}