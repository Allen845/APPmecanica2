package com.uvdstudio.mecanica2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BienvenidaActivity : AppCompatActivity {
    override fun onCreate(savedUnstanceState: Bundle?) {
        super.onCreate(savedUnstanceState)
        setContentView(R.layout.loginactivity)
        var login_usuario = findViewById<TextView>(R.id.login_usuario)
        var id_usuario = findViewById<TextView>(R.id.id_usuario)
        var nombre_usuario = findViewById<TextView>(R.id.nombre_usuario)
        var correo_usuario = findViewById<TextView>(R.id.correo_usuario)
        var boton_salir = findViewById<Button>(R.id.boton_salir)


        var intent_params=getIntent()
        login_usuario.text=intent_params.getStringExtra("Usuario")
        id_usuario.text="ID: "+intent_params.getStringExtra("ID")
        nombre_usuario.text="Usuario: "+intent_params.getStringExtra("Usuario")
        correo_usuario.text="Correo :"+intent_params.getStringExtra("Correo")

        boton_salir.setOnClickListener {
            var intent= Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }
    }
}